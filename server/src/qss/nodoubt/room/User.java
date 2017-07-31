package qss.nodoubt.room;

import java.util.Objects;

import qss.nodoubt.Client;

public class User {
	
	private boolean online=false;
	
	private String ID;
	private String password;

	private double currentRoomId;
	
	transient private Room currentRoom=null;
	transient private Client currentClient=null;
	
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

	public Client getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}
	
}
