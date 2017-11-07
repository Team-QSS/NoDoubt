package room;

import java.util.Objects;

public class User {
	
	private boolean online=false;
	
	private String ID;
	private String password;
	
	private double currentRoomId;
	
	transient private Room currentRoom=null;
	
	//방에 들어갔을시 자신의 자리인덱스//초기값은 -1;
	private int roomIndex=-1;
	
	public User(String id,String password){
		this.ID=id;
		this.password=password;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof User){
			return ((User)obj).getID().equals(this.getID())&&((User)obj).getPassword().equals(this.getPassword());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(ID,password);
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public double getCurrentRoomId() {
		return currentRoomId;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
		this.currentRoomId=currentRoom.id;
	}

	public int getRoomIndex() {
		return roomIndex;
	}

	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}
	
	
	
}
