package qss.nodoubt.utils;

import qss.nodoubt.graphics.TextureManager;

public class ResourceUtils {
	public static void loadImages(String str) {
		str = str.replace('\n', ':');
		String[] names = str.split(":");
		TextureManager tman = TextureManager.getInstance();
		
		for(int i = 0; i < names.length; i += 2)
		{
			tman.addTexture(names[i], names[i+1]);
		}
	}
}
