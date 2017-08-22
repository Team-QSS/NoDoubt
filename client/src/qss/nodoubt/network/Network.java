package qss.nodoubt.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import qss.nodoubt.game.GameConstants;

public class Network {
	private static Network s_Instance = null;
	
	private Socket m_Socket = null;
	private DataInputStream m_InputStream = null;
	private DataOutputStream m_OutputStream = null;
	private Thread m_InputThread = null;
	private Thread m_OutputThread = null;
	private Queue<JSONObject> m_InputQueue = new ConcurrentLinkedQueue<JSONObject>();
	private Queue<JSONObject> m_OutputQueue = new ConcurrentLinkedQueue<JSONObject>();
	
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
	
	public void shutdown() {
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
		connect();
	}
	
	public void connect() {
		try {
			m_Socket = new Socket(GameConstants.SERVER_IP, GameConstants.NETWORK_PORT);
			m_InputStream = new DataInputStream(m_Socket.getInputStream());
			m_OutputStream = new DataOutputStream(m_Socket.getOutputStream());
			
			m_InputThread = new Thread( () -> {
				while(true) {
					try {
						m_InputQueue.offer((JSONObject) new JSONParser().parse(m_InputStream.readUTF()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
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
							String s=m_OutputQueue.poll().toJSONString();
							m_OutputStream.writeChars(s);
							System.out.println(s);
						} catch (IOException e) {
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
	
	/**
	 * 메세지 받기
	 * @return 받은 메세지 없으면 null리턴
	 */
	public JSONObject pollMessage() {
		return m_InputQueue.poll();
	}
	
	/**
	 * 메세지 보내기
	 * @param msg 보낼 메세지
	 */
	public void pushMessage(JSONObject msg) {
		m_OutputQueue.add(msg);
	}
}
