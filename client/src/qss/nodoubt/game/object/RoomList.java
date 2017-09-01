package qss.nodoubt.game.object;

import java.util.LinkedList;

public class RoomList extends GameObject{
	
	private LinkedList<RoomObject> RoomList = new LinkedList<RoomObject>();
	
	public RoomList() {
		super("Blank", 0);
	}

	@Override
	public void update(float deltaTime) {
		for(RoomObject R : RoomList){
			R.update(deltaTime);
		}
	}
	
	//구현중
	public void addRoomObject(RoomObject roomObj){
		roomObj.setIndex(RoomList.size());
		RoomList.add(roomObj);
	}
	
	public void removeRoomObject(int index){
		RoomList.remove(index);
		
		for(int i=index;i<RoomList.size();i++){
			RoomList.get(i).setIndex(i);
		}
	}
	
	public void addRoomObject(int index,RoomObject roomObj){
		roomObj.setIndex(index);
		RoomList.add(index,roomObj);
	}

}
