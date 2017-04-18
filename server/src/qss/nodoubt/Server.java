package qss.nodoubt;

import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.gson.Gson;
import com.google.gson.JsonParser;


public class Server extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Socket socket;
	private Gson gson=new Gson();
	private JsonParser parser=new JsonParser();
	
	private final int MAX_THREAD_NUM=20;
	private ExecutorService threadPool=Executors.newFixedThreadPool(MAX_THREAD_NUM);
	private ArrayList<Client> clients=new ArrayList<>();
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
			printLog("server start port:"+serverSocket.getLocalPort());
			Thread gameLoop=new Thread(new GameLoop());
			gameLoop.start();
			while(true){
				// 서버 소켓의 accept() 메서드를 호출하여
				// 접속감시 상태로 들어감.
				// 접속이 일어나면 소켓 클래스 객체가 생성됨
				socket = serverSocket.accept();
				 // 소켓 클래스 객체에서 PrintStream을 생성함
				printLog(enterTextArea,"클라이언트접속");
				Client client=new Client(socket);
				clients.add(client);
				
				client.send("hello Nodoubt");
				threadPool.execute(new ClientManager(client));
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
			try{
				while(client.getSocket().isConnected()){
					String data;
					data=reader.readLine();
					
					printLog(data);
					
					//처리로직
					
					
					//전송
					client.send(data);
					broadCast(data);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	private void broadCast(String content){
		for(Client client:clients){
			client.send(content);
		}
	}
	
	//모든 서버상의 로그는 이함수를 이용하여 출력
	private void printLog(Object content){
		mainTextArea.append(content.toString()+"\n");
		System.out.println(content);
		mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
	}
	
	private void printLog(JTextArea textArea,Object content){
		textArea.append(content.toString()+"\n");
		System.out.println(content);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	
}
