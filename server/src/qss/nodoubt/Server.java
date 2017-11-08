package qss.nodoubt;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import qss.nodoubt.database.Database;
import qss.nodoubt.database.UserService;
import qss.nodoubt.room.Room;
import qss.nodoubt.room.RoomManager;
import qss.nodoubt.room.User;
import qss.nodoubt.util.KeyValue;
import qss.nodoubt.util.Protocol;
import qss.nodoubt.util.Util;


public class Server extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Socket socket;
	private Gson gson=Network.gson;
	private JSONParser parser=Network.jsonParser;
	
	private RoomManager roomManager;
	//현재 최대 동시접속 인원수는 4명으로 제한
	private final int MAX_THREAD_NUM=4;
	ExecutorService threadPool=Executors.newFixedThreadPool(MAX_THREAD_NUM);
	
	//현재 접속된클라이언트의 정보를 담는다
	private ArrayList<Client> clients=new ArrayList<>();
	//gui
	private final int WIDTH=640,HEIGHT=480;
	public static JTextArea mainTextArea=new JTextArea();
	private JTextArea enterTextArea=new JTextArea();
	
	//Debug
	public static boolean DEBUG_MODE;
	
	public Server(){
		DEBUG_MODE=true;
		setDisplay();
		init();
	}
	
	private void setDisplay(){
		setTitle("Nodoubt Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane=this.getContentPane();
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
		
		setSize(500,500);
		setResizable(false);
		setVisible(true);
		
		addJScrollPane(contentPane,mainTextArea,200,0,WIDTH-200,HEIGHT);
		addJScrollPane(contentPane,enterTextArea,0,0,200,HEIGHT);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
            	Util.printLog(mainTextArea, "종료 처리(유저의 모든 online을 false로 만든다.)");
            	Database.getInstance().executeAndUpdate("UPDATE users SET is_online = ?", false);
            	System.exit(0);
            }
		});
	}
	
	private void addJScrollPane(Container contentPane,JTextArea jTextArea,int x,int y,int width,int height){
		jTextArea.setLineWrap(true);
		jTextArea.setEditable(false);
		JScrollPane scrollPane=new JScrollPane(jTextArea);
		scrollPane.setBounds(x,y,width,height);
		contentPane.add(scrollPane);
	}
	
	private void init(){
		try{
			// 서버소켓을 생성
			ServerSocket serverSocket = new ServerSocket(5000);
			Util.printLog(mainTextArea,"server start port:"+serverSocket.getLocalPort());
			roomManager=RoomManager.getInstance();
			Thread gameLoop=new Thread(new GameLoop());
			gameLoop.start();
			while(true){
				// 서버 소켓의 accept() 메서드를 호출하여
				// 접속감시 상태로 들어감.
				// 접속이 일어나면 소켓 클래스 객체가 생성됨
				socket = serverSocket.accept();
				 // 소켓 클래스 객체에서 PrintStream을 생성함
				if(clients.size()<MAX_THREAD_NUM){
					Util.printLog(enterTextArea,"클라이언트접속");
					Client client=new Client(socket);
					clients.add(client);
					
					JSONObject data=new JSONObject();
					data.put("Protocol", "Connect");
					data.put("connectMessage", "hello Nodoubt");
					
					client.send(data);
					threadPool.execute(new ClientManager(client));
				}else{
					Util.printLog(mainTextArea, "최대 접속 클라이언트수를 초과하였습니다.");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class GameLoop implements Runnable{
		public void run(){
			try{
				while(true){
					Thread.sleep(1000/60);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//Client가 listen하는 클래스
	class ClientManager implements Runnable{
		Client client;
		BufferedReader reader;
		
		ClientManager(Client client){
			try{
				this.client=client;
				reader=new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void run(){
			JSONObject receiveData;
			try{
				while(client.getSocket().isConnected()){
					String data=reader.readLine();
					//종료
					if(data.equals("exit")){
						break;
					}
					
					receiveData=(JSONObject)parser.parse(data);
					
					//처리로직 및 전송
					process(receiveData,client);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				client.exit(clients);
				Util.printLog(enterTextArea,"Exit");
			}
		}
		//key:첫글자 대문자
		private void process(JSONObject data,Client client){
			JSONObject sendData = new JSONObject();
			switch((String)data.get("Protocol")){
			
				case Protocol.REGISTER_REQUEST:{
					User user=new User((String) data.get("ID"),(String) data.get("Password"));
					
					int result = UserService.getInstance().create(user);
					sendData.put("Protocol", Protocol.REGISTER_RESULT);
					
					if (result == 1) {
						sendData.put("Value", true);
					} else {
						sendData.put("Value", false);
					}
					
					client.send(sendData);
				}break;
				
				case Protocol.LOGIN_REQUEST:{
					User user=new User((String) data.get("ID"),(String) data.get("Password"));
					user = UserService.getInstance().login(user);
					
					if (user != null) {
						sendData.put("Protocol", Protocol.LOGIN_RESULT);
						sendData.put("Value", true);
						user.setOnline(true);
						client.setCurrentUser(user);
						roomManager.getRoom(RoomManager.LOBBY).enterUser(user);
						sendData.put("User", gson.toJson(user));
						sendData.put("RoomManager", gson.toJson(roomManager));
					} else {
						sendData.put("Protocol", Protocol.LOGIN_RESULT);
						sendData.put("Value", false);
					}
					
					client.send(sendData);
				}break;
				
				//방관련 메서드
				
				case Protocol.GET_ROOMMANAGER:{
					
					sendData.put("Protocol", Protocol.GET_ROOMMANAGER);
					sendData.put("RoomManager",gson.toJson(roomManager));
					
					client.send(sendData);

				}break;
				
				case Protocol.CREATE_ROOM_REQUEST:{
					sendData=new JSONObject();
					String roomName = (String) data.get("RoomName");
					
					// 중복된 이름의 방이 없을 시 방 생성 성공
					if (roomManager.getRoom((r)->{return r.getName().equals(roomName);}) == null) {
						
						Room newRoom=new Room((String)data.get("RoomName"));
						roomManager.addRoom(newRoom);
						
						//사실상 Password 안씀
						newRoom.setPassword((String)data.get("Password"));
						User user=client.getCurrentUser();
						
						double roomID=user.getCurrentRoomId();
						
						newRoom.enterUser(user);
						newRoom.setMaster(user);
						//처음 입장하므로 룸인덱스를 0으로 설정한다.
						user.setRoomIndex(0);
						
						sendData.put("Protocol", Protocol.CREATE_ROOM_RESULT);
						sendData.put("Value", true);
						sendData.put("Room",gson.toJson(newRoom));
						
						client.send(sendData);
						
						//다른애들한태 방이 생성됨을 알림
						sendData=new JSONObject();
						sendData.put("Protocol", Protocol.ADD_ROOM);
						sendData.put("Room",gson.toJson(newRoom));
						
						//lobby에 있는 모든유저에게 전송
						send(sendData,c->{
							User u=c.getCurrentUser();
							return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
						});
						
					} else {
						// 중복된 이름의 방이 았을 시 방 생성 실패
						sendData.put("Protocol", Protocol.CREATE_ROOM_RESULT);
						sendData.put("Value", false);
						
						client.send(sendData);
					}
				}break;
				
				case Protocol.JOIN_ROOM_REQUEST:{
					User user=client.getCurrentUser();
					double roomID=(double)data.get("RoomID");
					
					//현재들어온 유저의 룸인덱스를 정하는 과정
					int minRoomIndex=100;
					boolean arr[]=new boolean[6];
					for(String key:roomManager.getRoom(roomID).list.keySet()){
						User u=roomManager.getRoom(roomID).list.get(key);
						arr[u.getRoomIndex()]=true;
					}
					int i=0;
					for(i=0;i<6;i++){
						if(!arr[i])
							break;
					}
					user.setRoomIndex(i);
					//
					
					roomManager.getRoom(roomID).enterUser(user);
					
					sendData=Util.packetGenerator(
							Protocol.JOIN_ROOM_RESULT,
							new KeyValue("User",gson.toJson(user))
							);

					//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId();
					});
					
					//lobby에 있는 유저에게 방인원변경을 통지한다.
					int currentUserNum=roomManager.getRoom(roomID).list.size();
					sendData=Util.packetGenerator(
							Protocol.UPDATE_ROOM_CURRENT_USER_NUM,
							new KeyValue("RoomID",roomID),
							new KeyValue("UserNum",currentUserNum)
							);
					
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
					});
				}break;

				case Protocol.QUIT_ROOM_REQUEST:{
					User user=client.getCurrentUser();
					double roomID=(double)data.get("RoomID");
					//user의 룸인덱스를 초기화
					user.setRoomIndex(-1);
					
					//방고유 아이디에 해당하는 방을찾아 유저를 제거한후
					Room room=roomManager.getRoom(roomID);
					room.removeUser(user.getID());
					
					//로비로 보낸다.//사실 enterUser메소드 자체가 전에 있던 방에서 나가고 들어가기 때문에 위의 함수는 굳이 필요 없을 수도 있다.
					roomManager.getRoom(RoomManager.LOBBY).enterUser(user);
					
					//만약 방의 인원이 0이면
					if(room.isEmpty){
						//방을 제거
						roomManager.removeRoom(roomID);
						
						sendData=Util.packetGenerator(
								Protocol.REMOVE_ROOM,
								new KeyValue("RoomID",roomID)
								);
						
						//로비의 모든 유저에게 전달
						send(sendData,c->{
							User u=c.getCurrentUser();
							return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
						});
						
						return;
					}
					
					//만약 나간 사람이 방장이면
					if(user.getID()==room.getMaster().getID()){
						
						sendData=Util.packetGenerator(
								Protocol.REMOVE_ROOM,
								new KeyValue("RoomID",roomID)
								);
						
						//로비의 모든 유저에게 전달
						send(sendData,c->{
							User u=c.getCurrentUser();
							return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
						});
						
						sendData=Util.packetGenerator(
								Protocol.KICK_ROOM_REPORT
								);

						//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
						send(sendData,c->{
							User u=c.getCurrentUser();
							return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==roomID;
						});
						
						//방의 모든 인원을 lobby로 이동 
						for(String key:room.list.keySet()){			
							User quitUser=room.list.get(key);
							roomManager.getRoom(RoomManager.LOBBY).enterUser(quitUser);
						}
						
						//방을 제거
						roomManager.removeRoom(roomID);
						
						return;
					}
					
					sendData=Util.packetGenerator(
							Protocol.QUIT_ROOM_REPORT,
							new KeyValue("UserID",user.getID())
							);

					//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==roomID;
					});
					
					//lobby에 있는 유저에게 방인원변경을 통지한다.
					int currentUserNum=roomManager.getRoom(roomID).list.size();
					sendData=Util.packetGenerator(
							Protocol.UPDATE_ROOM_CURRENT_USER_NUM,
							new KeyValue("RoomID",roomID),
							new KeyValue("UserNum",currentUserNum)
							);
					
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
					});
					
				}break;
				
				case Protocol.READY_ROOM_REQUEST:{
					User user=client.getCurrentUser();
					
					sendData=Util.packetGenerator(
							Protocol.READY_ROOM_REPORT,
							new KeyValue("Value",data.get("Value"))
							);

					//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId();
					});
				}break;
				
				case Protocol.KICK_ROOM_REQUEST:{
					User user=client.getCurrentUser();
					
					Room room=roomManager.getRoom(user.getCurrentRoomId());
					
					User targetUser=room.getUser((String)data.get("TargetID"));
					//킥 대상 유저를 현재 방에서 로비로 이동
					roomManager.getRoom(RoomManager.LOBBY).enterUser(targetUser);
					
					sendData=Util.packetGenerator(
							Protocol.KICK_ROOM_REPORT
							);

					//킥 당한 사람에게 보냄
					send(sendData,c->{
						User u=c.getCurrentUser();
						return u.equals(targetUser);
					});
					
					//킥을 당한 나머지 사람한테 보냄
					sendData=Util.packetGenerator(
							Protocol.QUIT_ROOM_REPORT,
							new KeyValue("UserID",user.getID())
							);
					
					//자신의 유저와 같은방에있는 애들에게 보냄//킥 당한 사람제외
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(targetUser)&&u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId();
					});
					
					//lobby에 있는 유저에게 방인원변경을 통지한다.
					int currentUserNum=room.list.size();
					sendData=Util.packetGenerator(
							Protocol.UPDATE_ROOM_CURRENT_USER_NUM,
							new KeyValue("RoomID",room.id),
							new KeyValue("UserNum",currentUserNum)
							);
					
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
					});
				}break;
				
				case Protocol.GET_ROOM_DATA:{
					
					sendData=Util.packetGenerator(
							Protocol.GET_ROOM_DATA,
							new KeyValue("Room",gson.toJson(roomManager.getRoom((double)data.get("RoomID"))))
					);

					//자신에게 리턴
					client.send(sendData);
				}break;
				
				//방안에서 사용하는 메서드
				
				case "Chat":{// 클라이언트는 자신의 user정보와 채팅정보를 보냄 그러면 서버에서는 user가 속한방의 유저에게 채팅정보를 전달함
					User user=gson.fromJson(data.get("User").toString(), User.class);
					sendData=data;
					
					//자신의 유저와 같은방에있는 애들에게 보냄
					send(sendData,c->{
						User u=c.getCurrentUser();
						return u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId();
					});
				}break;
				
				//여기부터 inGameProtocol이다.
				case Protocol.DECLARE_REQUEST:{
					User user=client.getCurrentUser();
					
					sendData=Util.packetGenerator(
							Protocol.DECLARE_REPORT,
							new KeyValue("Player",user.getID()),
							new KeyValue("Value",data.get("Value"))
							);

					//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
					send(sendData,c->{
						User u=c.getCurrentUser();
						return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId();
					});
				}
				
				case Protocol.DOUBT_REQUEST
				
				default:{
					Util.printLog(mainTextArea, "알지못하는 프로토콜입니다.");
				}
				
			}
			
			//디버깅용 로그 출력
			Util.printDebugLog(DEBUG_MODE, mainTextArea, (String)data.get("Protocol"));
		}
		
	}
	
	//브로드캐스트
	private void broadCast(Object content){
		for(Client client:clients){
			client.send(content);
		}
	}
	//나자신을 제외한 나머지에게 전송
	private void sendExceptSelf(Object content,Client me){
		for(Client client:clients){
			if(client!=me){
				client.send(content);
			}
		}
	}
	
	//필터링 기능 
	private void send(Object content,Predicate<Client> p){
		for(Client client:clients){
			if(p.test(client)){
				client.send(content);
			}
		}
	}
	
}
