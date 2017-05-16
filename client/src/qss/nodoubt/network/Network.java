package qss.nodoubt.network;

import java.io.*;
import java.net.*;

import qss.nodoubt.game.GameConstants;

public class Network {
	private static Network s_Instance = null;
	
	private Socket m_Socket;
	private DataInputStream m_InputStream;
	private DataOutputStream m_OutputStream;
	
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
	
	public void send(Message msg) {
		try {
			m_OutputStream.writeChars(msg.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		try {
			m_Socket = new Socket(GameConstants.SERVER_IP, GameConstants.NETWORK_PORT);
			m_InputStream = new DataInputStream(m_Socket.getInputStream());
			m_OutputStream = new DataOutputStream(m_Socket.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
