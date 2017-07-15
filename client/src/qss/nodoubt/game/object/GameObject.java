package qss.nodoubt.game.object;

import qss.nodoubt.graphics.Texture;
import qss.nodoubt.graphics.TextureManager;
import qss.nodoubt.graphics.VertexArray;
import qss.nodoubt.input.Input;
import qss.nodoubt.input.KeyListener;
import qss.nodoubt.input.MouseListener;

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
	
	/**
	 * �삤釉뚯젥�듃 �깮�꽦
	 * @param textureName 珥덇린 �뀓�뒪爾� �씠由�
	 * @param depth 源딆씠媛� (蹂��븯吏� �븡�쓬)
	 */
	public GameObject(String textureName, float depth) {
		m_Texture = TextureManager.getInstance().getTexture(textureName);
		m_VertexArray = new VertexArray(m_Texture.getWidth(), m_Texture.getHeight(), 0.0f, 1.0f, 0.0f, 1.0f);
		m_Depth = depth;
	}
	
	public abstract void update(float deltaTime);
	
	public final void draw() {
		Matrix4f translate = new Matrix4f().translate(m_Position.x, m_Position.y, 0.0f);
		Matrix4f rotate = new Matrix4f().rotate(m_Angle, new Vector3f(0.0f, 0.0f, 1.0f));
		Matrix4f scale = new Matrix4f().scale(m_Size.x, m_Size.y, 0.0f);
		
		Matrix4f world = scale.mul(rotate).mul(translate);
		m_Texture.bind();
		
		m_VertexArray.draw(world);
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
	 * �삤釉뚯젥�듃 �궘�젣�릺湲� 吏곸쟾�뿉 �샇異쒕릺�뼱�빞 �븷 �븿�닔
	 * �뿏吏꾩씠 �븣�븘�꽌 �샇異쒗븯�땲 �샇異쒗븯吏� 留먭쾬
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
	 * �씠踰ㅽ듃 由ъ뒪�꼫 �꽕�젙
	 * �쁽�옱 �몢踰덊샇異쒗븯硫� �긽�깭 �씠�긽�븯�땲 �븳踰덈쭔 �샇異쒗븷寃�
	 * @param key �궎蹂대뱶 由ъ뒪�꼫, �뾾�쑝硫� null�꽔�쑝�뀍
	 * @param mouse 留덉슦�뒪 踰꾪듉 由ъ뒪�꽣, �뾾�쑝硫� null�꽔�쑝�뀍
	 */
	protected final void setEventListener(KeyListener key, MouseListener mouse) {
		Input input = Input.getInstance();
		if(key != null) {
			input.addKeyListener(key);
			m_KeyListener = key;
		}
		if(mouse != null) {
			input.addMouseListener(mouse);
			m_MouseListener = mouse;
		}
	}
	
	/**
	 * �뀓�뒪爾� �꽕�젙
	 * @param textureName �뀓�뒪爾� �씠由�
	 */
	protected final void setTexture(String textureName) {
		m_Texture = TextureManager.getInstance().getTexture(textureName);
		m_VertexArray.setSize(m_Texture.getWidth(), m_Texture.getHeight());
	}
}
