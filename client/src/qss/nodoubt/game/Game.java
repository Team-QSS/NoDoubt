package qss.nodoubt.game;

import qss.nodoubt.game.level.*;
import qss.nodoubt.graphics.Graphic;
import qss.nodoubt.network.Message;
import qss.nodoubt.network.Network;
import qss.nodoubt.sounds.Sound;
import qss.nodoubt.utils.FileUtils;
import qss.nodoubt.utils.GameTimer;
import qss.nodoubt.utils.ResourceUtils;

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
	private boolean m_ShouldClose = false;
	
	public static Game getInstance() {
		if(s_Instance == null) {
			s_Instance = new Game();
			s_Instance.m_CurLevel = new CreditLevel();
		}
		
		return s_Instance;
	}
	
	private Game() {
		m_Window = GameWindow.getInstance();
		m_Graphic = Graphic.getInstance();
		m_Sound = Sound.getInstance();
		m_Network = Network.getInstance();
		m_FrameTimer = new GameTimer();
		m_FrameCounter = 0;
		
		String str = FileUtils.loadTextFile("ImagePaths");
		ResourceUtils.loadImages(str);
	}

	/**
	 * 寃뚯엫 醫낅즺
	 */
	public void shutdown() {
		m_Window.shutdown();
		m_Sound.shutdown();
	}
	
	/**
	 * 寃뚯엫 吏꾪뻾
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
			
			m_CurLevel.draw();
			
			m_Window.updateWindow();
			
			if(m_Window.getWindowShouldClose()) {
				running = false;
			}
			
			if(m_ShouldClose) {
				running = false;
			}

			
			if(m_NextLevel != null) {
				m_CurLevel.destroyLevel();
				m_CurLevel = m_NextLevel;
				m_NextLevel = null;
			}
		}
	}
	
	/**
	 * �떎�쓬 �젅踰� �꽕�젙
	 * �쁽�옱 吏꾪뻾以묒씤 �봽�젅�엫�씠 �걹�굹怨� �젅踰⑥씠 諛붾��
	 * @param level �떎�쓬 �젅踰�
	 */
	public void setNextLevel(GameLevel level) {
		m_NextLevel = level;
	}
	
	/**
	 * 寃뚯엫 爰쇰쾭由�.
	 * good night
	 */
	public void goodBye() {
		m_ShouldClose = true;
	}
}
