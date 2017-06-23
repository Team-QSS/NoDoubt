package qss.nodoubt.utils;

import qss.nodoubt.graphics.TextureManager;

public class ResourceUtils {
	public static void loadImages(String str) {
//		str = str.replace('\n', ':');
//		String[] names = str.split(":");
//		TextureManager tman = TextureManager.getInstance();
//		
//		for(int i = 0; i < names.length; i += 2)
//		{
//			tman.addTexture(names[i], names[i+1]);
//		}
		
		String[] lines = str.split("\n");
		TextureManager tman = TextureManager.getInstance();
		for(int i = 0; i < lines.length; i++)
		{
			String s = lines[i];
			if(s.length() < 3) continue;
			if(s.startsWith("//")) continue;
			
			String[] s2 = s.split(":");
			if(s2.length != 2) {
				System.out.println("[이미지 경로 파일 오류] " + (i + 1) + " : 콜론 수가 1개가 아닙니다.");
				continue;
			}
			
			tman.addTexture(s2[0], s2[1]);
			
		}
	}
}
