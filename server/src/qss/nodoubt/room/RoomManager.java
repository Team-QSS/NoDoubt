package qss.nodoubt.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import qss.nodoubt.util.Util;

public class RoomManager {
	
	private static RoomManager r;
	
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
	
	public void removeRoom(double id){
		if(id!=LOBBY)
			list.remove(id);
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
	
	public Room getRoom(Predicate<Room> p){
		for(double id:list.keySet()){
			Room room=list.get(id);
			if(p.test(room))
				return room;
		}
		Util.printDebugLog("해당 방을 찾을수 없습니다.");
		return null;
	}
	
	public ArrayList<User> getUsers(Predicate<User> p){
		ArrayList<User> users=new ArrayList<>();
		for(double id:list.keySet()){
			Room room=list.get(id);
			for(String userID:room.list.keySet()){
				User user=room.list.get(userID);
				if(p.test(user))
					users.add(user);
			}
		}
		return users;
	}
	
	public User getUser(Predicate<User> p){
		for(double id:list.keySet()){
			Room room=list.get(id);
			for(String userID:room.list.keySet()){
				User user=room.list.get(userID);
				if(p.test(user))
					return user;
			}
		}
		Util.printDebugLog("해당 유저를 찾을수 없습니다.");
		return null;
	}
}
