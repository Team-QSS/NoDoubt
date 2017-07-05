package qss.nodoubt.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import qss.nodoubt.Network;
import qss.nodoubt.room.Room;
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
	
	private User user;
	
	//gui
	private MyPanel contentPane;
	private JTextArea mainTextArea=new JTextArea();
	
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
	
	private void process(JSONObject data){
		switch((String)data.get("Protocol")){
			case "Connect":{
				Util.printLog(mainTextArea,data.get("connectMessage"));
			}break;
		
			case Protocol.REGISTER_RESULT:{
				if(!(boolean)data.get("Value")){
					Util.printLog(mainTextArea, "회원가입완료");
				}else{
					Util.printLog(mainTextArea, "회원가입실패");
				}
			}break;
			
			case Protocol.LOGIN_RESULT:{
				if((boolean)data.get("Value")){
					Util.printLog(mainTextArea, "로그인성공");
					user=(User) gson.fromJson((String) data.get("user"), User.class);
					
					Util.printLog(mainTextArea, user.getCurrentRoomName());
					//채팅창 생성
					contentPane.chattingForm=new JTextField();
					contentPane.chattingForm.setBounds(300,100,100,50);
					contentPane.add(contentPane.chattingForm);
					
					//버튼생성
					JButton chat=new JButton("chat");
					chat.setBounds(200, 100, 100, 100);
					chat.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							JSONObject data=new JSONObject();
							data.put("Protocol", "Chat");
							data.put("user", gson.toJson(user));
							data.put("content", contentPane.chattingForm.getText());
							Network.send(writer,data);
							contentPane.chattingForm.setText("");
						}
						
					});
					contentPane.add(chat);
					
					//버튼생성
					JButton createRoom=new JButton("createRoom");
					createRoom.setBounds(200, 200, 100, 100);
					createRoom.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							JSONObject data;
							data=Util.packetGenerator(Protocol.CREATE_ROOM_REQUEST,
									new KeyValue("MasterName", gson.toJson(user)),
									new KeyValue("roomName", contentPane.chattingForm.getText())
									);
							Network.send(writer,data);
							contentPane.chattingForm.setText("");
						}
						
					});
					contentPane.add(createRoom);
					
					contentPane.repaint();
				}else{
					Util.printLog(mainTextArea, "로그인실패");
				}
			}break;
			
			case "Chat":{
				User user=(User) gson.fromJson((String) data.get("user"), User.class);
				String content=(String) data.get("content");
				Util.printLog(mainTextArea,user.getID()+":"+content);
			}break;
			
			case "CreateRoom":{
				Room createdRoom=(Room)gson.fromJson((String)data.get("createdRoom"), Room.class);
				user.setCurrentRoom(createdRoom);
				Util.printLog(mainTextArea,"CreateRoom Success");
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
			
			JTextField roomName=new JTextField();
			roomName.setBounds(110,200,30,20);
			add(roomName);
			
			//addButton
			JButton register=new JButton("register");
			register.setBounds(000, 100, 100, 100);
			register.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JSONObject data=new JSONObject();
					data.put("Protocol", "Register");
					data.put("ID", ID.getText());
					data.put("password", password.getText());
					Network.send(writer,data);
				}
				
			});
			add(register);
			
			JButton login=new JButton("login");
			login.setBounds(100, 100, 100, 100);
			login.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JSONObject data=new JSONObject();
					data.put("Protocol", "Login");
					data.put("ID", ID.getText());
					data.put("password", password.getText());
					data.put("roomName", roomName.getText());
					Network.send(writer,data);
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