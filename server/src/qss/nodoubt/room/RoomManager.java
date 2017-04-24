package qss.nodoubt.room;

import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {
	
	public static final double LOBBY=1.1;
	
	public ConcurrentHashMap<Double,Room> list=new ConcurrentHashMap<Double,Room>();
	
	public RoomManager(){
		//lobby
		Room lobby=new Room();
		lobby.setId(1.1);
		addRoom(lobby);
		
		//lobby2
		Room lobby2=new Room();
		lobby2.setId(1.2);
		addRoom(lobby2);
	}
	
	public void addRoom(Room room){
		list.put(room.getId(), room);
	}
	
	public Room getRoom(double id){
		return list.get(id);
	}
	
	public void removeRoom(double id){
		list.remove(id);
	}
}
