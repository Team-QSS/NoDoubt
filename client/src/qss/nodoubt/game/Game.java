package qss.nodoubt.game;

import qss.nodoubt.game.level.GameLevel;
import qss.nodoubt.game.level.TLevel;
import qss.nodoubt.graphics.Graphic;
import qss.nodoubt.network.Message;
import qss.nodoubt.network.Network;
import qss.nodoubt.sounds.Sound;
import qss.nodoubt.utils.GameTimer;

public class Game {
	private static Game s_Instance = null;
	private GameTimer m_FrameTimer = null;
	private Graphic m_Graphic = null;
	private GameWindow m_Window = null;
	private Sound m_Sound = null;
	private Network m_Network = null;
	private GameLevel m_CurLevel = null;
	private GameLevel m_NextLevel = null;
	private int m_FrameCounter;
	
	public static Game getInstance() {
		if(s_Instance == null) {
			s_Instance = new Game(new TLevel());
		}
		
		return s_Instance;
	}
	
	private Game() {
		m_FrameTimer = new GameTimer();
		m_FrameCounter = 0;
		m_Window = GameWindow.getInstance();
		m_Graphic = Graphic.getInstance();
		m_Sound = Sound.getInstance();
		m_Network = Network.getInstance();
	}
	
	private Game(GameLevel level) {
		m_FrameTimer = new GameTimer();
		m_FrameCounter = 0;
		m_Window = GameWindow.getInstance();
		m_Graphic = Graphic.getInstance();
		m_Sound = Sound.getInstance();
		m_Network = Network.getInstance();
		m_CurLevel = level;
	}

	/**
	 * 게임 종료
	 */
	public void shutdown() {
		m_Window.shutdown();
		m_Sound.shutdown();
	}
	
	/**
	 * 게임 진행
	 */
	public void run() {
		boolean running = true;
		
		m_FrameTimer.reset();
		m_FrameTimer.start();
		
		while(running) {
			m_FrameTimer.tick();
			m_FrameCounter += 1;
			
			if(m_FrameTimer.totalTime() > 1.0f) {
				System.out.println("FPS : " + m_FrameCounter + ", mSPF : " + (1000.0f * m_FrameTimer.totalTime() / m_FrameCounter));
				m_FrameTimer.reset();
				m_FrameCounter = 0;
			}
			
			//Message msg = m_Network.pollMessage();
			
			m_Window.pollEvents();
			
			m_CurLevel.update(m_FrameTimer.deltaTime());
			
			m_Graphic.beginDraw();
			
			m_CurLevel.draw(m_Graphic.getOrtho());
			
			m_Window.updateWindow();
			
			if(m_Window.getWindowShouldClose()) {
				running = false;
			}
			
			if(m_NextLevel != null) {
				m_CurLevel = m_NextLevel;
				m_NextLevel = null;
			}
		}
	}
	
	public void setNextLevel(GameLevel level) {
		m_NextLevel = level;
	}
}
