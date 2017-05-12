package qss.nodoubt.sounds;

import static org.lwjgl.openal.AL10.*;

public class SoundSource {
	private int m_Source;
	private int m_Buffer;
	
	SoundSource() {
		m_Source = alGenSources();
		alSourcef(m_Source, AL_GAIN, 1);
		alSourcef(m_Source, AL_PITCH, 1);
		alSource3f(m_Source, AL_POSITION, 0, 0, 0);
	}
	
	SoundSource(int buffer) {
		m_Source = alGenSources();
		alSourcef(m_Source, AL_GAIN, 1);
		alSourcef(m_Source, AL_PITCH, 1);
		alSource3f(m_Source, AL_POSITION, 0, 0, 0);
		m_Buffer = buffer;
	}
	
	void setBuffer(int buffer) {
		m_Buffer = buffer;
	}
	
	void play() {
		alSourcei(m_Source, AL_BUFFER, m_Buffer);
		alSourcePlay(m_Source);
	}
	
	void setLoop(boolean isLoop) {
		if(isLoop)
			alSourcei(m_Source, AL_LOOPING, AL_TRUE);
		else
			alSourcei(m_Source, AL_LOOPING, AL_FALSE);
	}
	
	void pause() {
		alSourcePause(m_Source);
	}
	
	void stop() {
		alSourceStop(m_Source);
	}
}
