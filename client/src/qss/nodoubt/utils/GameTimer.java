package qss.nodoubt.utils;

import static org.lwjgl.glfw.GLFW.*;

public class GameTimer {
	private long m_CurrentTime;
	private long m_PreviousTime;
	private long m_BaseTime;
	private long m_PausedTime;
	private long m_StoppedTime;
	private boolean m_IsPaused;
	
	public GameTimer() {
		m_CurrentTime = glfwGetTimerValue();
		m_PreviousTime = 0;
		m_BaseTime = glfwGetTimerValue();
		m_PausedTime = 0;
		m_StoppedTime = 0;
		m_IsPaused = false;
	}
	
	/**
	 * 매 프레임마다 호출되어야 하는 메소드
	 * 이 메소드가 호출된 시점을 통해 시간을 계산
	 */
	public void tick() {
		m_PreviousTime = m_CurrentTime;
		m_CurrentTime = glfwGetTimerValue();
	}
	
	/**
	 * 누적 시간 계산
	 * @return 리셋부터 지금까지 정지된 시간 제외한 모든 시간 총합(초단위)
	 */
	public float totalTime() {
		if(m_IsPaused) {
			return (float)(1 / glfwGetTimerFrequency()) * (m_StoppedTime - m_BaseTime - m_PausedTime);
		}
		else {
			return (float)(1 / glfwGetTimerFrequency()) * (m_CurrentTime - m_BaseTime - m_PausedTime);
		}
	}
	
	/**
	 * 프레임당 걸린 시간 계산
	 * @return 저번 프레임에서 걸린 시간
	 */
	public float deltaTime() {
		if(m_IsPaused) {
			return 0.0f;
		}
		else {
			return (float)(1 / glfwGetTimerFrequency()) * (m_CurrentTime - m_PreviousTime);
		}
	}
	
	/**
	 * 멈춘 타이머를 진행시킴
	 * 타이머가 멈추지 않은 상황에선 아무 일도 하지 않음
	 */
	public void start() {
		if(m_IsPaused) {
			m_PausedTime += m_CurrentTime - m_StoppedTime;
			m_IsPaused = false;
		}
	}
	
	/**
	 * 진행중인 타이머를 멈춤
	 * 타이머가 멈춘 상태에선 아무 일도 하지 않음
	 */
	public void stop() {
		if(!m_IsPaused) {
			m_StoppedTime = m_CurrentTime;
			m_IsPaused = true;
		}
	}
	
	/**
	 * 타이머를 초기화
	 * totalTime메소드는 이 시점을 기준으로 계산됨
	 */
	public void reset() {
		m_BaseTime = m_CurrentTime;
	}
}
