package qss.nodoubt.sounds;

import static org.lwjgl.openal.AL10.*;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;

import qss.nodoubt.utils.FileUtils;

public class Sound {
	private static Sound s_Instance = null;
	private Map<String, SoundSource> m_Sources = new HashMap<String, SoundSource>();
	private long m_SoundDevice;
	private long m_Context;
	
	public static Sound getInstance() {
		if(s_Instance == null) {
			s_Instance = new Sound();
		}
		return s_Instance;
	}
	
	private Sound() {
		IntBuffer ib = null;
		ByteBuffer bb = null;
		m_SoundDevice = ALC10.alcOpenDevice(bb);
		m_Context = ALC10.alcCreateContext(m_SoundDevice, ib);
		ALC10.alcMakeContextCurrent(m_Context);
		AL.createCapabilities(ALC.createCapabilities(m_SoundDevice));
		alListener3f(AL_POSITION, 0, 0, 0);
		alListener3f(AL_VELOCITY, 0, 0, 0);
	}
	
	public void shutdown() {
		ALC10.alcDestroyContext(m_Context);
		ALC10.alcCloseDevice(m_SoundDevice);
	}
	
	/**
	 * 음악 로딩
	 * @param name 음악 이름
	 * @param path 음악파일 경로
	 */
	public void loadSound(String name, String path){
		int buffer = alGenBuffers();
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		IntBuffer sample_rate = BufferUtils.createIntBuffer(1);
		ShortBuffer data = FileUtils.loadSoundFile(path, channels, sample_rate);
		
		if(channels.get(0) == 1) {
			alBufferData(buffer, AL_FORMAT_MONO16, data, sample_rate.get(0));
		}else if(channels.get(0) == 2) {
			alBufferData(buffer, AL_FORMAT_STEREO16, data, sample_rate.get(0));
		}
		
		SoundSource source = new SoundSource(buffer);
		m_Sources.put(name, source);
	}
	
	public void play(String name) {
		m_Sources.get(name).play();
	}
	
	public void pause(String name) {
		m_Sources.get(name).pause();
	}
	
	public void stop(String name) {
		m_Sources.get(name).stop();
	}
	
	public void setLoop(String name, boolean isLoop) {
		m_Sources.get(name).setLoop(isLoop);
	}
}
