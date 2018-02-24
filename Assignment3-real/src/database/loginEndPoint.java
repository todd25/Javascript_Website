package database;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import data.Employer;
import data.Student;
import data.chat;

@ServerEndpoint(
		value = "/ws",
		encoders = { loginEncoder.class }, 
	    decoders = { loginDecoder.class }
)

public class loginEndPoint {
	@OnOpen
	public void open(Session session) throws IOException {
		System.out.println("opening: " + session.getId());
		MySQLDriver.sessionVector.add(session);
	}
	@OnMessage
	public void onMessage(chat c, Session session) 
	      throws IOException, EncodeException {
		try {
			for (Session s : MySQLDriver.sessionVector) {
				s.getBasicRemote().sendText(chat.discussion);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("closing");
			MySQLDriver.sessionVector.remove(session);
	}
	
	@OnError
	public void onError(Throwable error) {}
}
	
