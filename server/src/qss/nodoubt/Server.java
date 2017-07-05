package qss.nodoubt;

import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import qss.nodoubt.room.Room;
import qss.nodoubt.room.RoomManager;
import qss.nodoubt.room.User;
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
	//유저의 정보를 담으며 서버시작시 DB를 통해 유저 데이터를 불러온다.
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
	//gui
	private final int WIDTH=640,HEIGHT=480;
	private JTextArea mainTextArea=new JTextArea();
	private JTextArea enterTextArea=new JTextArea();
	
	//Debug
	private boolean DEBUG_MODE;
	
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
			roomManager=new RoomManager();
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
					User user=new User((String) data.get("ID"),(String) data.get("password"));
					sendData.put("Protocol", Protocol.REGISTER_RESULT);
					sendData.put("Value", false);
					for(String key:users.keySet()){
						User u=users.get(key);
						if(u.getID().equals(user.getID())){
							sendData.put("Value", true);
							break;
						}
					}
					//만약 이전에 같은 유저가 존재하지않을시
					if(!(boolean)sendData.get("Value")){
						users.put(user.getID(), user);
					}
					client.send(sendData);
				}break;
				
				case Protocol.LOGIN_REQUEST:{
					User user=new User((String) data.get("ID"),(String) data.get("password"));
					sendData.put("Protocol", Protocol.LOGIN_RESULT);
					sendData.put("Value", false);
					for(String key:users.keySet()){
						User u=users.get(key);
						if(!u.isOnline()&&u.equals(user)){
							u.setOnline(true);
							u.setCurrentClient(client);
							u.setCurrentRoom(roomManager.getRoom((String)data.get("roomName")));
							sendData.put("Value", true);
							sendData.put("user", gson.toJson(u));
							break;
						}
					}
					
					//존재하면
					if((boolean) sendData.get("Value")){
						roomManager.getRoom("Lobby").addUser(user);
					}
					
					client.send(sendData);
				}break;
				
				//방관련 메서드
				
				case Protocol.CREATE_ROOM_REQUEST:{
					User user=gson.fromJson(data.get("user").toString(), User.class);
					sendData=new JSONObject();
					Room newRoom=new Room((String)data.get("roomName"));
					roomManager.addRoom(newRoom);
					
					users.get(user.getID()).setCurrentRoom(newRoom);
					
					sendData.put("Protocol", Protocol.CREATE_ROOM_RESULT);
					sendData.put("createdRoom",gson.toJson(newRoom));
					
					client.send(sendData);
				}break;
				
				case "EnterRoom":{
					User user=gson.fromJson(data.get("user").toString(), User.class);
					sendData=new JSONObject();
					Room newRoom=new Room((String)data.get("roomName"));
					roomManager.addRoom(newRoom);
					
					users.get(user.getID()).setCurrentRoom(newRoom);
					
					sendData.put("Protocol", "EnterRoom");
					sendData.put("",gson.toJson(newRoom));
					
					client.send(sendData);
				}break;
				
				//방안에서 사용하는 메서드
				
				case "Chat":{// 클라이언트는 자신의 user정보와 채팅정보를 보냄 그러면 서버에서는 user가 속한방의 유저에게 채팅정보를 전달함
					User user=gson.fromJson(data.get("user").toString(), User.class);
					sendData=data;
					for(String key:users.keySet()){
						User u=users.get(key);
						if(u.isOnline()&&u.getCurrentRoomName().equals(user.getCurrentRoomName())){
							u.getCurrentClient().send(sendData);
						}
					}
				}break;
				
				case "ExitRoom":{
					
				}break;
				
				default:{
					Util.printLog(mainTextArea, "알지못하는 프로토콜입니다.");
				}
				
			}
			
			//디버깅용 로그 출력
			Util.printDebugLog(DEBUG_MODE,mainTextArea, (String)data.get("Protocol"));
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
	
}
