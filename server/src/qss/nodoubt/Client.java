package qss.nodoubt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONObject;

import qss.nodoubt.database.UserService;
import qss.nodoubt.room.Room;
import qss.nodoubt.room.RoomManager;
import qss.nodoubt.room.User;
import qss.nodoubt.util.KeyValue;
import qss.nodoubt.util.Protocol;
import qss.nodoubt.util.Util;

//Client클래스는 실제 연결된 소켓에 관한 정보를 담는 클래스이다.
public class Client {
	private Socket socket;
	private BufferedWriter writer;
	
	transient private User currentUser;
	
	public Client(Socket socket){
		this.socket=socket;
		try {
			writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCurrentUser(User currentUser){
		this.currentUser=currentUser;
		currentUser.setCurrentClient(this);
	}
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public BufferedWriter getWriter() {
		return writer;
	}
	
	public void send(Object obj){
		Network.send(writer, obj);
	}
	
	private void send(ArrayList<Client> clients,Object content,Predicate<Client> p){
		for(Client client:clients){
			if(p.test(client)){
				client.send(content);
			}
		}
	}
	
	public void exit(ArrayList<Client> clients){
		try {
			clients.remove(this);
			socket.close();
			writer.close();
			
//			for(Client client:clients){
//				//로비에 있지않고//roomID가 같으면
//				if(getCurrentUser().getCurrentRoomId()!=RoomManager.LOBBY&&client.getCurrentUser().getCurrentRoomId()==getCurrentUser().getCurrentRoomId()){
//					
//					JSONObject msg=Util.packetGenerator(Protocol.QUIT_ROOM_REPORT, new KeyValue("UserID",getCurrentUser().getID()));
//					client.send(msg);
//					//만약 방의 인원이 0이거나 나간사람이 방장이면
//					if(getCurrentUser().getCurrentRoom().isEmpty){
//						
//					}
//				}
//			}
			
			//QUIT_ROOM_REQUEST와 동일한 작업 수행
			
			JSONObject sendData=new JSONObject();
			
			User user=getCurrentUser();
			double roomID=user.getCurrentRoomId();
			//user의 룸인덱스를 초기화
			user.setRoomIndex(-1);
			
			RoomManager roomManager=RoomManager.getInstance();
			
			//방고유 아이디에 해당하는 방을찾아 유저를 제거한후
			Room room=roomManager.getRoom(roomID);
			room.removeUser(user.getID());
			
			//로비로 보낸다.//사실 enterUser메소드 자체가 전에 있던 방에서 나가고 들어가기 때문에 위의 함수는 굳이 필요 없을 수도 있다.
			roomManager.getRoom(RoomManager.LOBBY).enterUser(user);
			
			//만약 방의 인원이 0이면
			if(room.isEmpty){
				//방을 제거
				roomManager.removeRoom(roomID);
				
				sendData=Util.packetGenerator(
						Protocol.REMOVE_ROOM,
						new KeyValue("RoomID",roomID)
						);
				
				//로비의 모든 유저에게 전달
				send(clients,sendData,c->{
					User u=c.getCurrentUser();
					return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
				});
			} else if(room.getMaster() != null && user.getID()==room.getMaster().getID()){
				//만약 나간 사람이 방장이면
				sendData=Util.packetGenerator(
						Protocol.REMOVE_ROOM,
						new KeyValue("RoomID",roomID)
						);
				
				//로비의 모든 유저에게 전달
				send(clients,sendData,c->{
					User u=c.getCurrentUser();
					return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
				});
				
				sendData=Util.packetGenerator(
						Protocol.KICK_ROOM_REPORT
						);

				//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
				send(clients,sendData,c->{
					User u=c.getCurrentUser();
					return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==roomID;
				});
				
				//방의 모든 인원을 lobby로 이동 
				for(String key:room.list.keySet()){			
					User quitUser=room.list.get(key);
					roomManager.getRoom(RoomManager.LOBBY).enterUser(quitUser);
				}
				
				//방을 제거
				roomManager.removeRoom(roomID);
			} else {
				sendData=Util.packetGenerator(
						Protocol.QUIT_ROOM_REPORT,
						new KeyValue("UserID",user.getID())
						);

				//자신의 유저와 같은방에있는 애들에게 보냄//자신제외
				send(clients,sendData,c->{
					User u=c.getCurrentUser();
					return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==roomID;
				});
				
				//lobby에 있는 유저에게 방인원변경을 통지한다.
				int currentUserNum=roomManager.getRoom(roomID).list.size();
				sendData=Util.packetGenerator(
						Protocol.UPDATE_ROOM_CURRENT_USER_NUM,
						new KeyValue("RoomID",roomID),
						new KeyValue("UserNum",currentUserNum)
						);
				
				send(clients,sendData,c->{
					User u=c.getCurrentUser();
					return !u.equals(user)&&u.isOnline()&&u.getCurrentRoomId()==RoomManager.LOBBY;
				});
			}
			
			//유저를 roomManager상의 방에서 제거한다.
			user.setOnline(false);
			roomManager.getRoom(RoomManager.LOBBY).removeUser(user.getID());
			UserService.getInstance().setIsOnline(user, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
