package qss.nodoubt.sounds;

import static org.lwjgl.openal.AL10.*;

import java.nio.*;

import org.lwjgl.openal.*;

public class Sound {
	private static Sound s_Instance = null;
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
}
