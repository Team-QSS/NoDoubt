package qss.nodoubt.network;

import java.io.*;
import java.net.*;
import java.util.*;

import qss.nodoubt.game.GameConstants;

public class Network {
	private static Network s_Instance = null;
	
	private Socket m_Socket = null;
	private DataInputStream m_InputStream = null;
	private DataOutputStream m_OutputStream = null;
	private Thread m_InputThread = null;
	private Thread m_OutputThread = null;
	private Queue<Message> m_InputQueue = new LinkedList<Message>();
	private Queue<Message> m_OutputQueue = new LinkedList<Message>();
	
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
	
	public Message pollMessage() {
		Message msg = null;
		try {
			if(m_InputStream.available() > 0) {
				msg = new Message(m_InputStream.readUTF());
			}else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public void send(Message msg) {
		try {
			m_OutputStream.writeChars(msg.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void shutdown() {
		try {
			m_InputStream.close();
			m_OutputStream.close();
			m_Socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Network() {
		
	}
	
	public void Connect() {
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
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
