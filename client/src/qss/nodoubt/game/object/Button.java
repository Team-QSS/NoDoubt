package qss.nodoubt.game.object;

public class Button extends GameObject {
	public interface ClickListener {
		void onClick();
	}
	
	private ClickListener m_Listener;

	public Button(String textureName, float x, float y, ClickListener listener) {
		super(textureName, 0);
		setPosition(x, y);
		m_Listener = listener;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}
	
	public void click() {
		m_Listener.onClick();
	}
}
