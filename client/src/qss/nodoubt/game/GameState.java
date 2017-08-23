package qss.nodoubt.game;

public class GameState {
	private static GameState s_Instance = null;
	public String m_myID = null;
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
