package qss.nodoubt.game.object;

import java.util.LinkedList;

public class RoomList extends GameObject{
	
	private LinkedList<RoomObject> RoomList = new LinkedList<RoomObject>();
	
	private float distance=110;
	
	public RoomList(String textureName, float depth) {
		super(textureName, depth);
	}

	@Override
	public void update(float deltaTime) {
		for(RoomObject R : RoomList){
			R.update(deltaTime);
		}
	}
	
	public void addRoomObject(RoomObject roomObj){
		roomObj
		RoomList.add(roomObj);
	}
	
	public void addRoomObject(int index,RoomObject roomObj){
		RoomList.add(index,roomObj);
	}
	
	public void removeRoomObject(int index){
		RoomList.remove(index);
	}

}
