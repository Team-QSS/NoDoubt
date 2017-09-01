package qss.nodoubt.game.object;

import java.util.LinkedList;

public class RoomList extends GameObject{
	
	private LinkedList<RoomObject> roomList = new LinkedList<RoomObject>();
	
	public RoomList() {
		super("Blank", 0);
	}

	@Override
	public void update(float deltaTime) {
		for(RoomObject R : roomList){
			R.update(deltaTime);
		}
	}
	
	//구현중
	public void addRoomObject(RoomObject roomObj){
		roomObj.setIndex(roomList.size());
		roomList.add(roomObj);
	}
	
	public void removeRoomObject(int index){
		roomList.remove(index);
		
		for(int i=index;i<roomList.size();i++){
			roomList.get(i).setIndex(i);
		}
	}
	
	public void addRoomObject(int index, RoomObject roomObj){
		roomObj.setIndex(index);
		roomList.add(index,roomObj);
	}
	
	public RoomObject getIndex(int index){
		return roomList.get(index);
	}

}
