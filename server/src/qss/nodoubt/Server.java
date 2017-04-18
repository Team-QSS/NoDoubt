package qss.nodoubt;

import java.awt.Color;
import java.awt.Container;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.gson.Gson;
import com.google.gson.JsonParser;


public class Server extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Socket socket;
	Gson gson=new Gson();
	JsonParser parser=new JsonParser();
	ArrayList<BufferedWriter> writers=new ArrayList<BufferedWriter>();
	
	JTextArea ta=new JTextArea();
	
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
		
		ta.setSize(this.getWidth(), this.getHeight());
		JScrollPane scrollPane=new JScrollPane(ta);
		contentPane.add(scrollPane);
		
		setSize(500,500);
		setResizable(false);
		setVisible(true);
	}
	
	private void init(){
		try{
			// ���������� ����
			ServerSocket serverSocket = new ServerSocket(5000);
			printLog("server start port:"+serverSocket.getLocalPort());
			Thread gameLoop=new Thread(new GameLoop());
			gameLoop.start();
			while(true){
				// ���� ������ accept() �޼��带 ȣ���Ͽ�
				// ���Ӱ��� ���·� ��.
				// ������ �Ͼ�� ���� Ŭ���� ��ü�� ������
				socket = serverSocket.accept();
				 // ���� Ŭ���� ��ü���� PrintStream�� ������
				BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				writers.add(writer);
				
				writer.write("s");
				writer.newLine();
				writer.flush();
				ClientManager t=new ClientManager(socket,writer);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class GameLoop implements Runnable{
		public void run(){
			try{
				while(true){
					printLog("���ȣ");
					Thread.sleep(1000/60);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	class ClientManager extends Thread{
		Socket clientSocket;
		BufferedReader reader;
		BufferedWriter writer;
		
		ClientManager(Socket clientSocket,BufferedWriter writer){
			try{
				this.clientSocket=clientSocket;
				reader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				this.writer=writer;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void run(){
			try{
				while(clientSocket.isConnected()){
					String data;
					data=reader.readLine();
					
					//ó������
					
					writer.write("s");
					writer.newLine();
					writer.flush();
				}
			}catch(Exception e){
				this.interrupt();
			}
		}
	}
	
	private void broadCast(String content){
		for(BufferedWriter writer:writers){
			try {
				writer.write(content);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void printLog(Object content){
		ta.append(content.toString()+"\n");
		System.out.println(content);
		ta.setCaretPosition(ta.getDocument().getLength());
	}
	
	
}
