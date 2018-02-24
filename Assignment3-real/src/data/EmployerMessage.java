package data;

public class EmployerMessage {
	private String message;
	private String sender;
	public EmployerMessage(String s, String m) {
		sender = s;
		message = m;
	}
	public String getMessage() {
		return this.message;
	}
	public String getSender() {
		return this.sender;
	}
}
