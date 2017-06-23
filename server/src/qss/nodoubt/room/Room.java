package qss.nodoubt.room;

import java.util.concurrent.ConcurrentHashMap;

public class Room {
	
	private boolean isEmpty=false;
	
	private double id=Math.random();
	
	private String name;
	
	public ConcurrentHashMap<String,User> list=new ConcurrentHashMap<>();
	
	public Room(String name){
		this.name=name;
	}
	
	public void addUser(User user){
		list.put(user.getID(), user);
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
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
