package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ±«±Ÿ≈√
 * */
public class Server {
	
	private ServerSocket sv_socket;
	private final int PORT = 7799;
	
	public Server() {
		
		try {
			sv_socket = new ServerSocket(PORT);
			while(true){
				Socket client = sv_socket.accept();
				System.out.println("clinet connected");
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				
				Thread t = new Thread(new ServerThread(ois, oos, client));
				t.start();
			}//while
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//cons
	
}//class
