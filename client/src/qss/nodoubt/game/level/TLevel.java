package qss.nodoubt.game.level;

import qss.nodoubt.game.object.TObject;

public class TLevel extends GameLevel{
	private TObject tobj;
	public TLevel() {
		
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
