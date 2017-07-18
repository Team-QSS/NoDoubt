package qss.nodoubt.utils;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.stb.STBTruetype.*;

import java.io.*;
import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLXARBGetProcAddress;
import org.lwjgl.stb.STBTTBakedChar;

import qss.nodoubt.graphics.Texture;
import qss.nodoubt.graphics.TextureManager;

public class FileUtils {

	/**
	 * 텍스트 파일을 읽음
	 * @param path 텍스트 파일 경로
	 * @return 텍스트 파일 내용
	 */
	public static String loadTextFile(String path) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String buffer;
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer + '\n');
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result.toString();
	}
	
	/**
	 * 이미지(.png) 파일 읽음
	 * @param path 이미지 파일 경로
	 * @param x 이미지 너비
	 * @param y 이미지 높이
	 * @param comp 이미지 색상 구성 개수(1 - 흑백, 2 - 투명값있는 흑백, 3 - 컬러, 4 - 투명값있는 컬러)
	 * @return 이미지 버퍼
	 */
	public static ByteBuffer loadImageFile(String path, IntBuffer x, IntBuffer y, IntBuffer comp) {
		ByteBuffer imageBuffer = null;
		imageBuffer = stbi_load(path, x, y, comp, STBI_rgb_alpha);
		return imageBuffer;
	}
	
	/**
	 * 음악(.ogg)파일 읽음
	 * @param path 음악 파일 경로
	 * @param channels 채널 개수(1 - 모노, 2 - 스테레오)
	 * @param sample_rate 샘플링 레이트
	 * @return 음악 파일 버퍼
	 */
	public static ShortBuffer loadSoundFile(String path, IntBuffer channels, IntBuffer sample_rate) {
		ShortBuffer soundBuffer = null;
		soundBuffer = stb_vorbis_decode_filename(path, channels, sample_rate);
		return soundBuffer;
	}
	
	public static STBTTBakedChar.Buffer loatFontFile(String path, int height) {
		File fontFile = new File(path);
		long size = fontFile.length();
		ByteBuffer fontBuffer = BufferUtils.createByteBuffer((int) size);
		
		try {
			int len;
			FileInputStream fis = new FileInputStream(fontFile);
			byte buffer[] = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				fontBuffer.put(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ByteBuffer bitmap = BufferUtils.createByteBuffer(1024 * 1024);
		STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(96);
		stbtt_BakeFontBitmap(fontBuffer, height, bitmap, 1024, 1024, 32, cdata);
		
		Texture tex = new Texture(bitmap, 1024, 1024);
		TextureManager.getInstance().addTexture("Font" + height, tex);
		
		return cdata;
	}
}
