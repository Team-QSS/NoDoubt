package qss.nodoubt.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class RoomManager {
	
	public static RoomManager r;
	
	public static RoomManager getInstance(){
		if(r==null)
			r=new RoomManager();
		return r;
	}
	
	public static final double LOBBY=1.1;
	
	public ConcurrentHashMap<Double,Room> list=new ConcurrentHashMap<>();
	
	private RoomManager(){
		//lobby
		Room lobby=new Room("Lobby");
		lobby.id=LOBBY;
		addRoom(lobby);
	}
	
	public void addRoom(Room room){
		list.put(room.id, room);
	}
	
	public Room getRoom(double id){
		return list.get(id);
	}
	
	public ArrayList<Room> getRooms(Predicate<Room> p){
		ArrayList<Room> rooms=new ArrayList<>();
		for(double id:list.keySet()){
			Room room=list.get(id);
			if(p.test(room))
				rooms.add(room);
		}
		return rooms;
	}
	
	public void removeRoom(double id){
		list.remove(id);
	}
}
