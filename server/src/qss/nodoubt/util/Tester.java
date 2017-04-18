package qss.nodoubt.util;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.*;

import com.google.gson.Gson;

public class Tester extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int WIDTH=640,HEIGHT=480;
	
	private Gson gson=new Gson();
	private BufferedWriter writer;
	private BufferedReader reader;
	private Socket socket;
	
	private MyPanel contentPane=new MyPanel();
	
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
		
		setContentPane(contentPane);
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setVisible(true);
	}
	
	private void networking(){
		try{
			socket = new Socket("10.156.145.110",5000);//소켓생성
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//쓰는 라인 생성
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	class SendData implements Runnable{
		public void run(){
			try{
				while(true){
					writer.write("s");
					writer.newLine();
					writer.flush();
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
			//이벤트 추가
			addKeyListener(new MyKeyListener());
			addMouseListener(new MyMouseListener());
			addMouseMotionListener(new MyMouseMotionListener());
			
			setFocusable(true);
			requestFocus();
			
			setButton();
		}
		
		private void setButton(){
			JButton j=new JButton("shit");
			j.setBounds(100, 100, 200, 200);
			this.add(j);
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