package room;

import java.util.Objects;

public class User {
	
	private boolean online=false;
	
	private String ID;
	private String password;
    private String name;
    
	private double currentRoomId;
	
	transient private Room currentRoom=null;
	
	public User(String id,String password){
		this.ID=id;
		this.password=password;
	}
	
	public User(String id,String password, String name){
		this(id, password);
		this.name = name;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
