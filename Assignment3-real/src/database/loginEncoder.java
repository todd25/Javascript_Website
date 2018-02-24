package database;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.json.JSONObject;
import data.Student;
import data.chat;

public class loginEncoder implements Encoder.Text<chat> {

  @Override
  public String encode(chat c) throws EncodeException {
	  System.out.println(c.getMessage() + " " + c.getUsername());

	  String jsonString;
	try {
		jsonString = new JSONObject()
		            .put("username", c.getUsername())
		            .put("message",c.getMessage())
		            .toString();
		return jsonString;
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return null;
  }

  @Override
  public void init(EndpointConfig ec) {
    System.out.println("loginEncoder Initialization");
  }

  @Override
  public void destroy() {
    System.out.println("loginEncoder Detroyed");
  }

}