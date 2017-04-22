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

import qss.nodoubt.room.RoomManager;
import qss.nodoubt.room.User;
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
	private final int MAX_THREAD_NUM=4;
	ExecutorService threadPool=Executors.newFixedThreadPool(MAX_THREAD_NUM);
	
	private ArrayList<Client> clients=new ArrayList<>();
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
	//gui
	private final int WIDTH=640,HEIGHT=480;
	private JTextArea mainTextArea=new JTextArea();
	private JTextArea enterTextArea=new JTextArea();
	
	public Server(){
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
				case "Register":{
					User user=new User((String) data.get("ID"),(String) data.get("password"));
					sendData.put("Protocol", "Register");
					sendData.put("isExist", false);
					for(String key:users.keySet()){
						User u=users.get(key);
						if(u.getID().equals(user.getID())){
							sendData.put("isExist", true);
							break;
						}
					}
					//만약 이전에 같은 유저가 존재하지않을시
					if(!(boolean)sendData.get("isExist")){
						users.put(user.getID(), user);
					}
					client.send(sendData);
				}break;
				
				case "Login":{
					User user=new User((String) data.get("ID"),(String) data.get("password"));
					sendData.put("Protocol", "Login");
					sendData.put("isExist", false);
					for(String key:users.keySet()){
						User u=users.get(key);
						if(!u.isOnline()&&u.equals(user)){
							u.setOnline(true);
							u.setCurrentClient(client);
							u.setCurrentRoom(roomManager.getRoom((double)data.get("roomNum")));
							sendData.put("isExist", true);
							sendData.put("user", gson.toJson(u));
							break;
						}
					}
					
					//존재하면
					if((boolean) sendData.get("isExist")){
						roomManager.getRoom(RoomManager.LOBBY).addUser(user);
					}
					
					client.send(sendData);
				}break;
				
				case "Chat":{// 클라이언트는 자신의 user정보와 채팅정보를 보냄 그러면 서버에서는 user가 속한방의 유저에게 채팅정보를 전달함
					User user=gson.fromJson(data.get("user").toString(), User.class);
					sendData=data;
					for(String key:users.keySet()){
						User u=users.get(key);
						if(u.isOnline()&&u.getCurrentRoomId()==user.getCurrentRoomId()){
							u.getCurrentClient().send(sendData);
						}
					}
				}break;
				
				case "EnterRoom":{
					
				}break;
			
				case "ExitRoom":{
					
				}break;
				
				default:{
					Util.printLog(mainTextArea, "알지못하는 프로토콜입니다.");
				}
			}
			
			//디버깅용 로그 출력
			Util.printLog(mainTextArea, (String)data.get("Protocol"));
			
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
