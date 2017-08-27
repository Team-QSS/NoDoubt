package qss.nodoubt.game.object;

import qss.nodoubt.graphics.FontManager;
import qss.nodoubt.graphics.Texture;
import qss.nodoubt.graphics.TextureManager;
import qss.nodoubt.graphics.VertexArray;
import qss.nodoubt.input.Input;
import qss.nodoubt.input.KeyListener;
import qss.nodoubt.input.MouseListener;

import static org.lwjgl.opengl.GL20.glUniform4f;

import java.util.LinkedList;
import java.util.Queue;

import org.joml.*;

public abstract class GameObject {
	private Vector2f m_Position = new Vector2f(0.0f, 0.0f);
	private Vector2f m_Size = new Vector2f(1.0f, 1.0f);
	private float m_Angle = 0.0f;
	private final float m_Depth;
	private VertexArray m_VertexArray;
	private Texture m_Texture;
	private KeyListener m_KeyListener = null;
	private MouseListener m_MouseListener = null;
	private boolean m_IsActive = false;
	
	private Queue<Text> m_TextDrawingQueue = new LinkedList<Text>();
	
	private class Text
	{
		String str;
		String font;
		Vector2f pos;
		Vector3f color;
		Text(String s, String f, Vector2f p, Vector3f c) {str = s; font = f; pos = p; color = c;}
	}
	
	/**
	 * 오브젝트 생성
	 * @param textureName 초기 텍스쳐 이름
	 * @param depth 깊이값 (변하지 않음)
	 */
	public GameObject(String textureName, float depth) {
		if(textureName != null) {
			m_Texture = TextureManager.getInstance().getTexture(textureName);
			m_VertexArray = new VertexArray(m_Texture.getWidth(), m_Texture.getHeight(), 0.0f, 1.0f, 0.0f, 1.0f);
		}
		m_Depth = depth;
	}
	
	public abstract void update(float deltaTime);
	
	public final void draw() {
		m_Texture.bind();
		
		glUniform4f(5, 0.0f, 0.0f, 0.0f, 0.0f);
		
		m_VertexArray.draw(getWorldMatrix());
		
		while(!m_TextDrawingQueue.isEmpty()) {
			Text t = m_TextDrawingQueue.poll();
			FontManager.getInstance().getFont(t.font).draw(t.pos, t.str, t.color);
		}
	}
	
	private Matrix4f getWorldMatrix() {
		Matrix4f translate = new Matrix4f().translate(m_Position.x, m_Position.y, 0.0f);
		Matrix4f rotate = new Matrix4f().rotate(m_Angle, new Vector3f(0.0f, 0.0f, 1.0f));
		Matrix4f scale = new Matrix4f().scale(m_Size.x, m_Size.y, 0.0f);
		
		Matrix4f world = scale.mul(rotate).mul(translate);
		return world;
	}

	protected final void setPosition(float x, float y) {
		m_Position.set(x, y);
	}
	
	protected final void setPosition(Vector2f position) {
		m_Position.set(position);
	}
	
	protected final Vector2f getPosition() {
		return m_Position;
	}
	
	public final float getDepth() {
		return m_Depth;
	}
	
	protected final void setAngle(float angle) {
		m_Angle = angle;
	}
	
	protected final float getAngle() {
		return m_Angle;
	}
	
	protected final void setSize(float xSize, float ySize) {
		m_Size.set(xSize, ySize);
	}
	
	protected final void setSize(Vector2f size) {
		m_Size.set(size);
	}
	
	protected final Vector2f getSize() {
		return m_Size;
	}
	
	/**
	 * 오브젝트 삭제되기 직전에 호출되어야 할 함수
	 * 엔진이 알아서 호출하니 호출하지 말것
	 */
	public final void destroyObject() {
		Input input = Input.getInstance();
		if(m_KeyListener != null) {
			input.removeKeyListener(m_KeyListener);
		}
		if(m_MouseListener != null) {
			input.removeMouseListener(m_MouseListener);
		}
	}
	
	/**
	 * 이벤트 리스너 설정
	 * 현재 두번호출하면 상태 이상하니 한번만 호출할것
	 * @param key 키보드 리스너, 없으면 null넣으셈
	 * @param mouse 마우스 버튼 리스터, 없으면 null넣으셈
	 */
	protected final void setEventListener(KeyListener key, MouseListener mouse) {
		Input input = Input.getInstance();
		
		m_KeyListener = key;
		m_MouseListener = mouse;
		
		if(key != null && m_IsActive) {
			input.addKeyListener(key);
		}
		if(mouse != null && m_IsActive) {
			input.addMouseListener(mouse);
		}
	}
	
	/**
	 * 텍스쳐 설정
	 * @param textureName 텍스쳐 이름
	 */
	protected final void setTexture(String textureName) {
		m_Texture = TextureManager.getInstance().getTexture(textureName);
		m_VertexArray.setSize(m_Texture.getWidth(), m_Texture.getHeight());
	}
	
	/**
	 * 오브젝트 초기화
	 * 해당 오브젝트가 포함된 레벨이 처음 동작하기 전 프레임의 마지막에 호출
	 * 엔진이 호출하니 쓰지 말 것
	 */
	public final void act() {
		m_IsActive = true;
		setEventListener(m_KeyListener, m_MouseListener);
	}
	
	/**
	 * 텍스트 그리기 요청, 미리 받아두었다가 draw()함수에서 호출함
	 * @param fontName 폰트 이름 일반폰트는 "fontR" 볼드체 폰트는 "fontB"로 넣으면 됨
	 * @param str 출력할 텍스트
	 * @param position 위치
	 * @param color 색상
	 */
	protected final void drawTextCall(String fontName, String str, Vector2f position, Vector3f color) {
		m_TextDrawingQueue.offer(new Text(str, fontName, position, color));
	}
}
