package qss.nodoubt.room;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
	
	public static final double LOBBY=1.1;
	
	public ConcurrentHashMap<String,Room> list=new ConcurrentHashMap<>();
	
	public RoomManager(){
		//lobby
		Room lobby=new Room("Lobby");
		addRoom(lobby);
	}
	
	public void addRoom(Room room){
		list.put(room.getName(), room);
	}
	
	public Room getRoom(String name){
		return list.get(name);
	}
	
	public void removeRoom(String name){
		list.remove(name);
	}
}
