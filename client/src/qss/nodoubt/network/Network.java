package qss.nodoubt.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import qss.nodoubt.game.GameConstants;
import qss.nodoubt.game.GameState;

public class Network {
	
	public static Gson gson = new Gson();
	public static JSONParser parser = new JSONParser();
	
	private static Network s_Instance = null;
	
	private Socket m_Socket = null;
	private BufferedReader m_InputStream = null;
	private BufferedWriter m_OutputStream = null;
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
			
			//m_Socket.getInputStream().close();
			//m_Socket.getOutputStream().close();
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
			m_InputStream = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));
			m_OutputStream = new BufferedWriter(new OutputStreamWriter(m_Socket.getOutputStream()));
			
			m_InputThread = new Thread( () -> {
				while(GameState.getInstance().m_IsGameGoing) {
					try {
						if(m_InputStream.ready())
							m_InputQueue.offer((JSONObject) new JSONParser().parse(m_InputStream.readLine()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					m_InputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			m_InputThread.start();
			
			m_OutputThread = new Thread( () -> {
				while(GameState.getInstance().m_IsGameGoing) {
					if(!m_OutputQueue.isEmpty()) {
						try {
							String s=m_OutputQueue.poll().toJSONString();
							m_OutputStream.write(s);
							m_OutputStream.newLine();
							m_OutputStream.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					m_OutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
