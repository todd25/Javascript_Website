package database;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;
import data.chat;

public class loginDecoder implements Decoder.Text<chat>{
	//"{\"username\":\"N95\",\"password\":\"WP\"}"
	  @Override
	  public chat decode(String jsonMessage) throws DecodeException {
		try {
			JSONObject jsonObject = new JSONObject(jsonMessage);
			System.out.println(jsonObject.get("boardMessage").toString()+ jsonObject.get("username").toString());
			chat c = new chat();
			c.setMessage(jsonObject.get("boardMessage").toString());
			c.setUsername(jsonObject.get("username").toString());
			chat.discussion+=("<p>" + jsonObject.get("boardMessage").toString() +"<br></p>");
			return c;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	  }

	  @Override
	  public void init(EndpointConfig ec) {
	    System.out.println("loginDecoder Initialization");
	  }

	  @Override
	  public void destroy() {
	    System.out.println("loginDecoder Destroyed");
	  }
	  
	@Override
	public boolean willDecode(String arg0) {
		return true;
	}
}
