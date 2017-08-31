package qss.nodoubt.game;

import room.User;

public class GameState {
	private static GameState s_Instance = null;
	public User m_Me = null;
	public boolean m_IsGameGoing = true;
	
	public static GameState getInstance() {
		if(s_Instance == null) {
			s_Instance = new GameState();
		}
		return s_Instance;
	}
	
	private GameState() {
		
	}
}
