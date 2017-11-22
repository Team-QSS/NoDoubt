package room;

import java.util.concurrent.ConcurrentHashMap;

public class Room {
	public boolean isEmpty=false;
	
	public double id=Math.random();
	
	private User master;
	
	private String name;
	
	private String password;
	
	public ConcurrentHashMap<String,User> list=new ConcurrentHashMap<>();
	
	private boolean isPlaying=false;

	public Room(String name){
		this.name=name;
	}
	
	@Deprecated
	public void addUser(User user){
		list.put(user.getID(), user);
		user.setCurrentRoom(this);
		isEmpty=false;
	}
	
	public void enterUser(User user){
		list.put(user.getID(), user);
		if(user.getCurrentRoom()!=null){
			user.getCurrentRoom().removeUser(user.getID());
		}else{
			Room room=RoomManager.getInstance().list.get(user.getCurrentRoomId());
			if(room!=null)
				room.removeUser(user.getID());
		}
		user.setCurrentRoom(this);
		isEmpty=false;
	}
	
	public User getUser(String ID){
		return list.get(ID);
	}

	public void removeUser(String ID){
		list.remove(ID);
		if(list.size()==0){
			isEmpty=true;
		}
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
