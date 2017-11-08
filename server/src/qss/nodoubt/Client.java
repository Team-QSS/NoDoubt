package qss.nodoubt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import qss.nodoubt.database.UserService;
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
	
	public void exit(ArrayList<Client> clients){
		try {
			clients.remove(this);
			socket.close();
			writer.close();
			
			for(Client client:clients){
				//로비에 있지않고//roomID가 같으면
				if(getCurrentUser().getCurrentRoomId()!=RoomManager.LOBBY&&client.getCurrentUser().getCurrentRoomId()==getCurrentUser().getCurrentRoomId()){
					JSONObject msg=Util.packetGenerator(Protocol.QUIT_ROOM_REPORT, new KeyValue("UserID",getCurrentUser().getID()));
					client.send(msg);
				}
			}
			
			//유저를 roomManager상의 방에서 제거한다.
			RoomManager.getInstance().getUser((u)->{
				return u.getID().equals(getCurrentUser().getID());
			}).getCurrentRoom().removeUser(getCurrentUser().getID());
			UserService.getInstance().setIsOnline(currentUser, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
