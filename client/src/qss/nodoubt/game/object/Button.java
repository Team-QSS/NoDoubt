package qss.nodoubt.game.object;

public class Button extends GameObject {
	public interface ClickListener {
		void onClick();
	}
	
	private ClickListener m_Listener = null;

	public Button(String textureName, float x, float y) {
		super(textureName, 0);
		setPosition(x, y);
	}
	
	public void setListner(ClickListener listener){
		m_Listener = listener;
	}
	
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}
	
	public void click() {
		if(m_Listener != null){
			m_Listener.onClick();
		}
	}
}
