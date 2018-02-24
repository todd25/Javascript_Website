package data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server ss;
	public ServerThread(Socket s, Server ss){
		try{
			this.ss = ss;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch(IOException ioe){
			System.out.println("ioe in ServerThread: " + ioe.getMessage());
		}
	}
	
	public void run(){
		while(true){
			
		}
	}
}
