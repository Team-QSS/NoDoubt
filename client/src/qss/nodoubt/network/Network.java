package qss.nodoubt.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

import qss.nodoubt.game.GameConstants;

public class Network {
	private static Network s_Instance = null;
	
	private Socket m_Socket;
	
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
			m_Socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Network() {
		
		try {
			m_Socket = new Socket(GameConstants.SERVER_IP, GameConstants.NETWORK_PORT);
			InputStream is = m_Socket.getInputStream();
			DataInputStream ds = new DataInputStream(is);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
