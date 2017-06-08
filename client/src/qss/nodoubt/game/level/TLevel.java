package qss.nodoubt.game.level;

import qss.nodoubt.game.object.TObject;
import qss.nodoubt.graphics.TextureManager;

public class TLevel extends GameLevel{
	private TObject tobj;
	public TLevel() {
		TextureManager tman = TextureManager.getInstance();
		
		tman.addTexture("a", "../res/image/Button/1.png");
		
		tobj = new TObject();
		
		addObject(tobj);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		drawObjects();
	}

}
