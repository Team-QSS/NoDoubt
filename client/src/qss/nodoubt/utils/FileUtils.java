package qss.nodoubt.utils;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBVorbis.*;
import java.io.*;
import java.nio.*;

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
}
