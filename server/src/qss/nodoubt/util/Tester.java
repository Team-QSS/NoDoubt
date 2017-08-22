package qss.nodoubt.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import qss.nodoubt.Network;
import qss.nodoubt.room.Room;
import qss.nodoubt.room.RoomManager;
import qss.nodoubt.room.User;

public class Tester extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH=640,HEIGHT=480;
	
	private Gson gson=Network.gson;
	private JSONParser parser=Network.jsonParser;
	private BufferedWriter writer;
	private BufferedReader reader;
	private Socket socket;
	
	private RoomManager roomManager;
	
	private User user;
	
	//gui
	private MyPanel contentPane;
	private JTextArea mainTextArea=new JTextArea();
	private JList list;
	private String currentSelectRoom;
	
	private boolean[] keyInput=new boolean[300];
	
	//Debug
	private boolean DEBUG_MODE;
	
	public Tester(){
		DEBUG_MODE=false;
		setDisplay();
		networking();
		Thread loop=new Thread(new GameLoop());
		Thread sendDataThread=new Thread(new SendData());
		Thread readerThread=new Thread(new InDataReader());
		loop.start();
		sendDataThread.start();
		readerThread.start();
	}
	
	private void setDisplay(){
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		contentPane=new MyPanel();
		setContentPane(contentPane);
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setVisible(true);
	}
	
	private void networking(){
		try{
			socket = new Socket("localhost",5000);//���ϻ���
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//���� ���� ����
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	class SendData implements Runnable{
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
	
	class GameLoop implements Runnable{
		public void run(){
			try{
				while(true){
					if(keyInput[27]){
						System.exit(0);
					}
					Thread.sleep(1000/60);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	class InDataReader implements Runnable{
		public void run(){
			JSONObject receiveData;
			try{
				while(true){
					String data=reader.readLine();
					receiveData=(JSONObject)parser.parse(data);
					
					//처리
					process(receiveData);
					
					//디버깅용 로그 출력
					Util.printDebugLog(DEBUG_MODE,mainTextArea, data);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void createBtn(String name,int x,int y,int width,int height,Function f){
		//버튼생성
		JButton btn=new JButton(name);
		btn.setBounds(x, y, width, height);
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				f.execute();
			}
		});
		contentPane.add(btn);
	}
	
	private void process(JSONObject data){
		switch((String)data.get("Protocol")){
			case "Connect":{
				Util.printLog(mainTextArea,data.get("connectMessage"));
			}break;
			
			case Protocol.DUMMY_PACKET:{
			//씬을 변환하면 그에따라 함수도 같이 변하는데 이과정에서 패킷이 도착하지않으면 다음씬으로 넘어갔지만 계속 InDataReader는 블록 상태이기때문에 문제가 생길수 있다.
			//그러므로 씬을 변환할때는 더미 패킷을 보내 블로킹 상태를 해제시킨다.
			}break;
		
			case Protocol.REGISTER_RESULT:{
				if((boolean)data.get("Value")){
					Util.printLog(mainTextArea, "회원가입완료");
				}else{
					Util.printLog(mainTextArea, "회원가입실패");
				}
			}break;
			
			case Protocol.LOGIN_RESULT:{
				if((boolean)data.get("Value")){
					Util.printLog(mainTextArea, "로그인성공");
					user=gson.fromJson((String) data.get("User"), User.class);
					//초기 방정보 불러옴
					roomManager=gson.fromJson((String)data.get("RoomManager"), RoomManager.class);
					
					Util.printLog(mainTextArea, "로비입장");
					//채팅창 생성
					contentPane.chattingForm=new JTextField();
					contentPane.chattingForm.setBounds(300,100,100,50);
					contentPane.add(contentPane.chattingForm);
					
					
					//버튼생성
					createBtn("chat",200,100,100,100,()->{
						JSONObject sendData=new JSONObject();
						sendData=Util.packetGenerator(
								"Chat",
								new KeyValue("User", gson.toJson(user)),
								new KeyValue("Content", contentPane.chattingForm.getText())
								);
						Network.send(writer,sendData);
						contentPane.chattingForm.setText("");
					});
					
					
					//버튼생성
					createBtn("createRoom",200,200,100,100,()->{
						JSONObject sendData;
						sendData=Util.packetGenerator(
								Protocol.CREATE_ROOM_REQUEST,
								new KeyValue("MasterID", user.getID()),
								new KeyValue("RoomName", contentPane.chattingForm.getText()),
								new KeyValue("Password", null)
								);
						Network.send(writer,sendData);
						
						contentPane.chattingForm.setText("");
					});
					
					
					//버튼생성
					createBtn("joinRoom",200,300,100,100,()->{
						JSONObject sendData;
						double id=roomManager.getRooms(room->room.getName().equals(this.currentSelectRoom)).get(0).id;
						sendData=Util.packetGenerator(
								Protocol.JOIN_ROOM_REQUEST,
								new KeyValue("User", gson.toJson(user)),
								new KeyValue("RoomID", id)
								);
						Network.send(writer,sendData);
						//클라이언트에서 처리
						roomManager.getRoom(id).enterUser(user);
						
						contentPane.chattingForm.setText("");
					});
					
					//방리스트 생성
					list=new JList(new DefaultListModel());
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
					list.addListSelectionListener(new ListSelectionListener(){

						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(e.getSource()==list)
								currentSelectRoom=list.getSelectedValue().toString();
						}

					});
					
					JScrollPane scroll=new JScrollPane();
					scroll.setViewportView(list);
					scroll.setBounds(0,300,70,100);
					contentPane.add(scroll);
					
					contentPane.repaint();
				}else{
					Util.printLog(mainTextArea, "로그인실패");
				}
			}break;
			
			case "Chat":{
				User user=gson.fromJson((String)data.get("User"), User.class);
				String content=(String)data.get("Content");
				Util.printLog(mainTextArea,user.getID()+":"+content);
			}break;
			
			case Protocol.CREATE_ROOM_RESULT:{
				Room createdRoom=gson.fromJson((String)data.get("Room"), Room.class);
				roomManager.addRoom(createdRoom);
				createdRoom.enterUser(user);
				
				DefaultListModel listModel=(DefaultListModel)list.getModel();
				listModel.clear();
				for(double key:roomManager.list.keySet()){
					String roomName=roomManager.list.get(key).getName();
					listModel.addElement(roomName);
				}
//1의 방에 2가 들어옴:2_JoinRequest->1//그리고 2는 자신의 방에 속해있지 않으면서 자신의 방으로 들어온 3의 메세지는 받을수 있었다.
				//2의방에 3이 들어옴:3_JoinRequest->2
				
				Util.printLog(mainTextArea,"CreateRoom Success");
			}break;
			
			case Protocol.ADD_ROOM:{
				Room createdRoom=gson.fromJson((String)data.get("Room"), Room.class);
				roomManager.addRoom(createdRoom);
				
				DefaultListModel listModel=(DefaultListModel)list.getModel();
				listModel.clear();
				for(double key:roomManager.list.keySet()){
					String roomName=roomManager.list.get(key).getName();
					listModel.addElement(roomName);
				}
				
				Util.printLog(mainTextArea,"방추가");
			}break;
			
			case Protocol.JOIN_ROOM_RESULT:{
				User joinUser=gson.fromJson((String)data.get("User"),User.class);
				user.getCurrentRoom().enterUser(joinUser);
			}break;
			
			default:{
				Util.printLog(mainTextArea, "알지못하는 프로토콜입니다.");
			}
		}
	}

	//gui 관련코드
	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public JTextField chattingForm;
		
		public MyPanel(){
			setBackground(Color.WHITE);
			setLayout(null);
			//�̺�Ʈ �߰�
			addKeyListener(new MyKeyListener());
			addMouseListener(new MyMouseListener());
			addMouseMotionListener(new MyMouseMotionListener());
			
			setFocusable(true);
			requestFocus();
			
			setComponent();
		}
		
		private void setComponent(){
			//addTextArea
			addJScrollPane(this,mainTextArea,Tester.WIDTH-200, 0, 200, Tester.HEIGHT);
			
			//addTextField
			JTextField ID=new JTextField();
			ID.setBounds(0,200,100,20);
			add(ID);
			
			JTextField password=new JTextField();
			password.setBounds(0,225,100,20);
			add(password);
			
			//addButton
			JButton register=new JButton("register");
			register.setBounds(000, 100, 100, 100);
			register.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String idText = ID.getText();
					String passwordText = password.getText();
					
					if (!idText.equals("") && !passwordText.equals("")) {
						JSONObject data=new JSONObject();
						data=Util.packetGenerator(
							Protocol.REGISTER_REQUEST,
							new KeyValue("ID", ID.getText()),
							new KeyValue("Password", password.getText())
						);
						Network.send(writer,data);
					} else {
						Util.printLog(mainTextArea, "아이디, 비밀번호 미입력");
					}
				}
			});
			add(register);
			
			JButton login=new JButton("login");
			login.setBounds(100, 100, 100, 100);
			login.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String idText = ID.getText();
					String passwordText = password.getText();
					
					if (!idText.equals("") && !passwordText.equals("")) {
						JSONObject data=new JSONObject();
						data=Util.packetGenerator(
							Protocol.LOGIN_REQUEST,
							new KeyValue("ID", ID.getText()),
							new KeyValue("Password", password.getText())
						);
						Network.send(writer,data);
					} else {
						Util.printLog(mainTextArea, "아이디, 비밀번호 미입력");
					}
				}
				
			});
			add(login);
			
		}
		
		private void addJScrollPane(Container contentPane,JTextArea jTextArea,int x,int y,int width,int height){
			jTextArea.setLineWrap(true);
			jTextArea.setEditable(false);
			JScrollPane scrollPane=new JScrollPane(jTextArea);
			scrollPane.setBounds(x,y,width,height);
			contentPane.add(scrollPane);
		}
	}
	
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			keyInput[e.getKeyCode()]=true;	
		}
		
		public void keyReleased(KeyEvent e){
			keyInput[e.getKeyCode()]=false;
		}
	}
	
	class MyMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e){
		}
		
		public void mouseReleased(MouseEvent e){
		}
		
		public void mouseClicked(MouseEvent e){
			
		}
		
	}
	
	class MyMouseMotionListener extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e){
			
		}
		
		public void mouseDragged(MouseEvent e){
			
		}
	}
	
	public static void main(String args[]){
		new Tester();
	}
}