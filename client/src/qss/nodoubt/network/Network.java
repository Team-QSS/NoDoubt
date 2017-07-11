package qss.nodoubt.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import qss.nodoubt.game.GameConstants;

public class Network {
	private static Network s_Instance = null;
	
	private Socket m_Socket = null;
	private DataInputStream m_InputStream = null;
	private DataOutputStream m_OutputStream = null;
	private Thread m_InputThread = null;
	private Thread m_OutputThread = null;
	private Queue<Message> m_InputQueue = new ConcurrentLinkedQueue<Message>();
	private Queue<Message> m_OutputQueue = new ConcurrentLinkedQueue<Message>();
	
	public static Network getInstance() {
		if(s_Instance == null) {
			s_Instance = new Network();
		}
		return s_Instance;
	}
	
	public static void release() {
		if(s_Instance != null) {
			s_Instance.shutdown();
			s_Instance = null;
		}
	}
	
	private void shutdown() {
		try {
			m_InputThread.interrupt();
			m_OutputThread.interrupt();
			
			while(!m_OutputQueue.isEmpty()) {
				try {
					m_OutputStream.writeChars(m_OutputQueue.poll().toJSONString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			m_InputStream.close();
			m_OutputStream.close();
			m_Socket.close();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Network() {
		
	}
	
	public void connect() {
		try {
			m_Socket = new Socket(GameConstants.SERVER_IP, GameConstants.NETWORK_PORT);
			m_InputStream = new DataInputStream(m_Socket.getInputStream());
			m_OutputStream = new DataOutputStream(m_Socket.getOutputStream());
			
			m_InputThread = new Thread( () -> {
				while(true) {
					try {
						m_InputQueue.offer(new Message(m_InputStream.readUTF()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			m_InputThread.start();
			
			m_OutputThread = new Thread( () -> {
				while(true) {
					if(!m_OutputQueue.isEmpty()) {
						try {
							m_OutputStream.writeChars(m_OutputQueue.poll().toJSONString());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			m_OutputThread.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message pollMessage() {
		return m_InputQueue.poll();
	}
	
	public void pushMessage(Message msg) {
		m_OutputQueue.add(msg);
	}
}
