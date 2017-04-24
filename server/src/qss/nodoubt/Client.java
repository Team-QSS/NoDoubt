package qss.nodoubt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

//Client클래스는 실제 연결된 소켓에 관한 정보를 담는 클래스이다.
public class Client {
	private Socket socket;
	private BufferedWriter writer;
	
	public Client(Socket socket){
		this.socket=socket;
		try {
			writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
