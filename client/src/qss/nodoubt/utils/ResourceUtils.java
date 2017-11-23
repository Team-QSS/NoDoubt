package qss.nodoubt.utils;

import qss.nodoubt.graphics.FontManager;
import qss.nodoubt.graphics.TextureManager;
import qss.nodoubt.sounds.Sound;

public class ResourceUtils {
	public static void loadImages(String str) {
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

	public static void loadFonts(String str) {
		String[] lines = str.split("\n");
		FontManager fman = FontManager.getInstance();
		
		for(int i = 0; i < lines.length; i++)
		{
			String s = lines[i];
			if(s.length() < 3) continue;
			if(s.startsWith("//")) continue;
			
			String[] s2 = s.split(":");
			if(s2.length != 2) {
				System.out.println("[폰트 경로 파일 오류] " + (i + 1) + " : 콜론 수가 1개가 아닙니다.");
				continue;
			}
			
			fman.addFont(s2[0], s2[1]);
			
		}
	}

	public static void loadSounds(String str) {
		String[] lines = str.split("\n");
		Sound fman = Sound.getInstance();
		
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
			
			fman.loadSound(s2[0], s2[1]);
			
		}
	}
}
