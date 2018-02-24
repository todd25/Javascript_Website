package data;

public class chat {
	private static String currentUser;
	private String message;
	private String username;
	public static String discussion="";
	public static void setCurrentUser(String user)
	{
		currentUser = user;
	}
	public static String getCurrentUser()
	{
		return currentUser;
	}
	
	public void setMessage(String s)
	{
		message = s;
	}
	public void setUsername(String s)
	{
		username = s;
	}
	public String getUsername()
	{
		return username;
	}
	public String getMessage()
	{
		return username;
	}
	
	
}
