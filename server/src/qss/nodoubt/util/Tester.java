package qss.nodoubt.util;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

import org.*;

import com.google.gson.Gson;

public class Tester extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH=640,HEIGHT=480;
	
	private Gson gson=new Gson();
	private BufferedWriter writer;
	private BufferedReader reader;
	private Socket socket;
	
	//gui
	private JTextArea mainTextArea=new JTextArea();
	
	private boolean[] keyInput=new boolean[300];
	
	public Tester(){
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
		
		setContentPane(new MyPanel());
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
//					contentPane.repaint();
					Thread.sleep(1000/60);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	class InDataReader implements Runnable{
		public void run(){
			try{
				while(true){
					String data;
					data=reader.readLine();
					printLog(data);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
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
			
			//addButton
			JButton j=new JButton("shit");
			j.setBounds(100, 100, 200, 200);
			j.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						writer.write("shit");
						writer.newLine();
						writer.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			add(j);
			
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
	
	//모든 서버상의 로그는 이함수를 이용하여 출력
	private void printLog(Object content){
		mainTextArea.append(content.toString()+"\n");
		System.out.println(content);
		mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
	}

	public static void main(String args[]){
		new Tester();
	}
}