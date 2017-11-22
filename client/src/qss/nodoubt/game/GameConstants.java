package qss.nodoubt.game;

import org.joml.Vector3f;

public class GameConstants {
	public static final int WIDTH = 16;
	public static final int HEIGHT = 9;
	public static final String SERVER_IP = "35.160.125.239";
	public static final int NETWORK_PORT = 5000;
	
	public static final boolean IS_FULLSCREEN = false;
	
	public static final Vector3f[] BIKE_COLORS = new Vector3f[]{
			new Vector3f(0xc5 / 255f, 0x00 / 255f, 0x00 / 255f), 
			new Vector3f(0x00 / 255f, 0x5a / 255f, 0xb8 / 255f), 
			new Vector3f(0x00 / 255f, 0xbb / 255f, 0x48 / 255f), 
			new Vector3f(0xd7 / 255f, 0xca / 255f, 0x00 / 255f), 
			new Vector3f(0xf0 / 255f, 0xf0 / 255f, 0xf0 / 255f), 
			new Vector3f(0x60 / 255f, 0x00 / 255f, 0x97 / 255f)
	};
}