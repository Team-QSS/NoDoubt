package qss.nodoubt.game;

import qss.nodoubt.graphics.Graphic;
import qss.nodoubt.utils.GameTimer;

public class Game {
	private Game() {}
	private static GameTimer s_FrameTimer = null;
	private static int s_FrameCounter;
	
	/**
	 * 게임 초기화
	 */
	public static void initialize() {
		GameWindow.initialize();
		Graphic.initialize();
		s_FrameTimer = new GameTimer();
		s_FrameCounter = 0;
	}

	/**
	 * 게임 종료
	 */
	public static void shutdown() {
		Graphic.shutdown();
		GameWindow.shutdown();
	}
	
	/**
	 * 게임 진행
	 */
	public static void run() {
		boolean running = true;
		
		s_FrameTimer.reset();
		s_FrameTimer.start();
		
		while(running) {
			s_FrameTimer.tick();
			s_FrameCounter += 1;
			
			if(s_FrameTimer.totalTime() > 1.0f) {
				System.out.println("FPS : " + s_FrameCounter + ", mSPF : " + (1000.0f * s_FrameTimer.totalTime() / s_FrameCounter));
				s_FrameTimer.reset();
				s_FrameCounter = 0;
			}
			
			GameWindow.pollEvents();
			
			Graphic.beginDraw();
			
			GameWindow.updateWindow();
			
			if(GameWindow.getWindowShouldClose()) {
				running = false;
			}
		}
	}

}
