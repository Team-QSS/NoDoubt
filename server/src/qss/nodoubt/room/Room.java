package qss.nodoubt.room;

import java.util.concurrent.ConcurrentHashMap;

public class Room {
	
	private boolean isEmpty=false;
	
	private double id=Math.random();
	
	public ConcurrentHashMap<String,User> list=new ConcurrentHashMap<>();
	
	public Room(){
		
	}
	
	public void addUser(User user){
		list.put(user.getID(), user);
		isEmpty=false;
	}
	
	public User getUser(double id){
		return list.get(id);
	}
	
	public void removeUser(double id){
		list.remove(id);
		if(list.size()==0){
			isEmpty=true;
		}
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}


}
