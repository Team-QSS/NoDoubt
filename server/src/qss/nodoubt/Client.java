package qss.nodoubt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
		try {
			writer.write(obj.toString());
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
