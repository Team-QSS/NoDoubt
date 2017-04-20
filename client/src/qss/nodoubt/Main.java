package qss.nodoubt;

import qss.nodoubt.game.Game;

public class Main {

	public static void main(String[] args) {
		Game.initialize();
		Game.run();
		Game.shutdown();
	}

}