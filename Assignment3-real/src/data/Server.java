package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	private Vector<ServerThread> serverThreads;
	
	public Server(int port) throws IOException{
		serverThreads = new Vector<ServerThread>();
		ServerSocket ss = new ServerSocket(port);
		ss.setReuseAddress(true);
		while(true){
			System.out.println("waiting for connection...");
			Socket s = ss.accept();
			System.out.println("connection from: " + s.getInetAddress());
			ServerThread st = new ServerThread(s, this);
		}
	}
}
