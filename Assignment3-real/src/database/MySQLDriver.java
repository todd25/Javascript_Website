package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.websocket.Session;

import data.Employer;
import data.Student;
import data.chat;

public class MySQLDriver {
	private String localhost;
	private String db;
	private String username;
	private String password;
	private Connection conn;
	public static Vector<Session> sessionVector = new Vector<Session>();
	public static Vector<chat> userVector = new Vector<chat>();
	private final static String addStudent = "INSERT INTO STUDENTS(FULLNAME,USERNAME,PW,SCHOOL,MAJOR, GRADYEAR, CITY, EMAIL, RESUMELINK,IMAGEURL, GPA, PHONENUMBER, SPONSORSHIP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	private final static String addEmployer = "INSERT INTO EMPLOYERS(USERNAME,PW,COMPANY,EMAIL, IMAGEURL, CITY, INDUSTRY, SPONSORSHIP, RECRUITER, POSITION, PHONENUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; 
	private final static String addMatch = "INSERT INTO MATCHES(STUDENTUSERNAME, EMPLOYERUSERNAME) VALUES(?,?)";
	
	//Student
	private final static String addStudentLiked = "UPDATE STUDENTS SET LIKED=? WHERE USERNAME=?";
	private final static String addStudentDisliked = "UPDATE STUDENTS SET DISLIKED=? WHERE USERNAME=?";

	private final static String addEmployerLiked = "UPDATE EMPLOYERS SET LIKED=? WHERE USERNAME=?";
	private final static String addEmployerDisliked = "UPDATE EMPLOYERS SET DISLIKED=? WHERE USERNAME=?";
	
	private final static String checkStudentUsername = "SELECT * FROM STUDENTS WHERE USERNAME = ?";
	private final static String checkEmployerUsername = "SELECT * FROM EMPLOYERS WHERE USERNAME = ?";
	private final static String authenticateStudent = "SELECT * FROM STUDENTS WHERE USERNAME = ? AND PW = ?";
	private final static String authenticateEmployer = "SELECT * FROM EMPLOYERS WHERE USERNAME = ? AND PW = ?";
	private final static String retrieveAllStudents = "SELECT * FROM STUDENTS";
	private final static String retrieveAllEmployers = "SELECT * FROM EMPLOYERS";
	
	//MATCH
	private final static String getMatchID = "SELECT * FROM MATCHES WHERE STUDENTUSERNAME = ? AND EMPLOYERUSERNAME = ?";
	private final static String getMatchesForStudent = "SELECT * FROM MATCHES WHERE STUDENTUSERNAME = ?";
	private final static String getMatchesForEmployer = "SELECT * FROM MATCHES WHERE EMPLOYERUSERNAME = ?";

	//CHAT
	private final static String getChatHistory = "SELECT * FROM CHATS WHERE MATCHID = ?";
	private final static String insertMessage = "INSERT INTO CHATS(MATCHID, DIRECTION, MESSAGE) VALUES(?,?,?)";

	
	//GETTERS
	private final static String getStudent = "SELECT * FROM STUDENTS WHERE USERNAME = ?";
	private final static String getEmployer = "SELECT * FROM EMPLOYERS WHERE USERNAME = ?";
	private final static String port = "3306";
	
	public MySQLDriver(String localhost, String db, String username, String password){
		try{
			new com.mysql.jdbc.Driver();
			this.localhost = localhost;
			this.db = db;
			this.username = username;
			this.password = password;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void connect(){
		try{
			String protocol = "jdbc:mysql://";
			String url = protocol + localhost + ":" + port + "/" + db + "?" + "user="+username + "&password="+password + "&useSSL=false";
			System.out.println("url: " + url);
			conn = DriverManager.getConnection(url);
			System.out.println("MADE IT");
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	public Vector<Student> getAllStudents(){
		
		Vector<Student> allStudents = new Vector<Student>();
		try{
			PreparedStatement ps = conn.prepareStatement(retrieveAllStudents);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				Student s1 = new Student();
				
				s1.setFullname(result.getString(2));
				s1.setUsername(result.getString(3));
				s1.setPassword(result.getString(4));
				s1.setSchool(result.getString(5));
				s1.setMajor(result.getString(6));
				s1.setGradYear(result.getString(7));
				s1.setCity(result.getString(8));
				s1.setEmail(result.getString(9));
				s1.setResume(result.getString(10));
				s1.setImage(result.getString(11));
				s1.setGPA(result.getString(12));
				s1.setPhone(result.getString(13));
				Vector<String> likedList = getStudentLikedList(username);
				s1.setLikedListString(likedList);
				Vector<String> dislikedList = getStudentDislikedList(username);
				s1.setDislikedListString(dislikedList);
				s1.setMatchedListString(getStudentMatchUsernames(username));
				allStudents.add(s1);
			}
			}catch(SQLException e){
				e.printStackTrace();
			}
		System.out.println(allStudents.size() + " results.");
		return allStudents;
	}
	
	public Vector<Employer> getAllEmployers(){
		Vector<Employer> allEmployers = new Vector<Employer>();
		try{
			PreparedStatement ps = conn.prepareStatement(retrieveAllEmployers);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				Employer e1 = new Employer();
				e1.setUsername(result.getString(2));
				e1.setPassword(result.getString(3));
				e1.setCompany(result.getString(4));
				e1.setEmail(result.getString(5));
				e1.setImage(result.getString(6));
				e1.setCity(result.getString(7));
				e1.setIndustry(result.getString(8));
				e1.setSponsorship(result.getBoolean(9));
				e1.setRecruiter(result.getString(10));
				e1.setPosition(result.getString(11));
				e1.setPhone(result.getString(12));
				Vector<String> likedList = getEmployerLikedList(username);
				e1.setLikedListString(likedList);
				Vector<String> dislikedList = getEmployerDislikedList(username);
				e1.setDislikedListString(dislikedList);
				e1.setMatchedListString(getEmployerMatchUsernames(username));
				allEmployers.add(e1);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println(allEmployers.size() + " results.");
		
		return allEmployers;
	}
	
	public void addStudent(Student s1){		
		try{
			PreparedStatement ps = conn.prepareStatement(addStudent);
			ps.setString(1, s1.getFullname());
			ps.setString(2, s1.getUsername());
			ps.setString(3, s1.getPassword());
			ps.setString(4, s1.getSchool());
			ps.setString(5, s1.getMajor());
			ps.setString(6, s1.getGradYear());
			ps.setString(7, s1.getCity());
			ps.setString(8, s1.getEmail());
			ps.setString(9, s1.getResume());
			ps.setString(10, s1.getImage());
			ps.setString(11, s1.getGPA());
			ps.setString(12, s1.getPhone());
			ps.setBoolean(13, s1.getSponsorship());
			ps.executeUpdate();
			
			System.out.println("Adding student: " + s1.getFullname());
			
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	//username, password, company, email, city, sponsership, recruiter, position
	public void addEmployer(Employer e1){
		try{
			PreparedStatement ps = conn.prepareStatement(addEmployer);
			ps.setString(1, e1.getUsername());
			ps.setString(2, e1.getPassword());
			ps.setString(3, e1.getCompany());
			ps.setString(4, e1.getEmail());
			ps.setString(5, e1.getImage());
			ps.setString(6, e1.getCity());
			ps.setString(7, e1.getIndustry());
			ps.setBoolean(8, e1.getSponsorship());
			ps.setString(9, e1.getRecruiter());
			ps.setString(10, e1.getPosition());
			ps.setString(11, e1.getPhone());
			ps.executeUpdate();
			
			System.out.println("Adding Employer: " + e1.getCompany());
			
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean studentUsernameExist(String username){
		boolean usernameExist = true;
		try{
			PreparedStatement ps = conn.prepareStatement(checkStudentUsername);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			
			if(result.next()){
				
			}
			else{
				usernameExist = false;
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return usernameExist;
	}

	public boolean employerUsernameExist(String username){
		try{
			PreparedStatement ps = conn.prepareStatement(checkEmployerUsername);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			
			while(result.next()){
				System.out.println(result.getString(2));
				return true;
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		//System.out.println("Employer username is okay!");
		return false;
	}

	public void addMatch(String studentUsername, String employerUsername){
		try{
			PreparedStatement ps = conn.prepareStatement(addMatch);
			ps.setString(1, studentUsername);
			ps.setString(2, employerUsername);
			
			ps.executeUpdate();
			
			System.out.println("Adding Match between: " + studentUsername + " and " + employerUsername );
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean authenticateStudent(String username, String password){
		try{
			PreparedStatement ps = conn.prepareStatement(authenticateStudent);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			
			while(result.next()){
				System.out.println("user exists!" + result.getString(2));
				return true;
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("incorrect student login information!");
		return false;
	}
	
	public boolean authenticateEmployer(String username, String password){
		try{
			PreparedStatement ps = conn.prepareStatement(authenticateEmployer);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			
			while(result.next()){
				System.out.println("user exists!" + result.getString(2));
				return true;
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("incorrect employer login information!");
		return false;
	}
	
	//FULLNAME,USERNAME,PW,SCHOOL,MAJOR, GRADYEAR, CITY, EMAIL, RESUMELINK, GPA, PHONENUMBER

	public Student getStudent(String username){
		Student s1 = new Student();
		try{
			PreparedStatement ps = conn.prepareStatement(getStudent);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				s1.setFullname(result.getString(2));
				s1.setUsername(result.getString(3));
				s1.setPassword(result.getString(4));
				s1.setSchool(result.getString(5));
				s1.setMajor(result.getString(6));
				s1.setGradYear(result.getString(7));
				s1.setCity(result.getString(8));
				s1.setEmail(result.getString(9));
				s1.setResume(result.getString(10));
				s1.setImage(result.getString(11));
				s1.setGPA(result.getString(12));
				s1.setPhone(result.getString(13));
				Vector<String> likedList = getStudentLikedList(username);
				s1.setLikedListString(likedList);
				Vector<String> dislikedList = getStudentDislikedList(username);
				s1.setDislikedListString(dislikedList);
				s1.setMatchedListString(getStudentMatchUsernames(username));
//				String[] likedList = result.getString(14).split(":");
//				//Setting Liked List
//				Vector<Employer> finalLiked = new Vector<Employer>();
//				for(String liked: likedList){
//					Employer temp = getEmployer(liked);
//					finalLiked.add(temp);
//				}
//				s1.setLikedList(finalLiked);
//				//Setting Disliked List
//				String[] dislikedList = result.getString(11).split(":");
//				Vector<Employer>finalDisliked = new Vector<Employer>();
//				for(String disliked : dislikedList){
//					Employer temp = getEmployer(disliked);
//					finalDisliked.add(temp);
//				}
//				s1.setDislikedList(finalDisliked);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return s1;
	}
	
	public Employer getEmployer(String username){
		Employer e1 = new Employer();
		try{
			PreparedStatement ps = conn.prepareStatement(getEmployer);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				e1.setUsername(result.getString(2));
				e1.setPassword(result.getString(3));
				e1.setCompany(result.getString(4));
				e1.setEmail(result.getString(5));
				e1.setImage(result.getString(6));
				e1.setCity(result.getString(7));
				e1.setIndustry(result.getString(8));
				e1.setSponsorship(result.getBoolean(9));
				e1.setRecruiter(result.getString(10));
				e1.setPosition(result.getString(11));
				e1.setPhone(result.getString(12));
				Vector<String> likedList = getEmployerLikedList(username);
				e1.setLikedListString(likedList);
				Vector<String> dislikedList = getEmployerDislikedList(username);
				e1.setDislikedListString(dislikedList);
				e1.setMatchedListString(getEmployerMatchUsernames(username));
//				String[] likedList = result.getString(10).split(":");
//				//Setting Liked List
//				Vector<Student> finalLiked = new Vector<Student>();
//				for(String liked: likedList){
//					Student temp = getStudent(liked);
//					finalLiked.add(temp);
//				}
//				e1.setLikedList(finalLiked);
//				//Setting Disliked List
//				String[] dislikedList = result.getString(11).split(":");
//				Vector<Student>finalDisliked = new Vector<Student>();
//				for(String disliked : dislikedList){
//					Student temp = getStudent(disliked);
//					finalDisliked.add(temp);
//				}
//				e1.setDislikedList(finalDisliked);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return e1;
	}
	public Vector<String> getStudentLikedList(String username){
		Vector<String> likedList = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(checkStudentUsername);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				String[] likedArray = result.getString(15).split(":");
				for(String liked : likedArray){
					likedList.add(liked);
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return likedList;
	}
	
	public Vector<String> getStudentDislikedList(String username){
		Vector<String> likedList = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(checkStudentUsername);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				String[] likedArray = result.getString(16).split(":");
				for(String liked : likedArray){
					likedList.add(liked);
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return likedList;
	}
	
	
	public void addStudentLiked(String newLike, String username){
		String liked = newLike + ":";
		Student temp = getStudent(username);
		for(String like : temp.getLikedListString()){
			if(!like.equals("")){
				liked = liked + like + ":";
			}
		}
		System.out.println("liked string: " + liked);
		try{
			PreparedStatement ps = conn.prepareStatement(addStudentLiked);
			ps.setString(1, liked);
			ps.setString(2, username);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addStudentDisliked(String newLike, String username){
		String liked = newLike + ":";
		Student temp = getStudent(username);
		for(String like : temp.getDislikedListString()){
			if(!like.equals("")){
				liked = liked + like + ":";
			}
		}
		System.out.println("disliked string: " + liked);
		try{
			PreparedStatement ps = conn.prepareStatement(addStudentDisliked);
			ps.setString(1, liked);
			ps.setString(2, username);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> getEmployerLikedList(String username){
		Vector<String> likedList = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(checkEmployerUsername);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				String[] likedArray = result.getString(13).split(":");
				for(String liked : likedArray){
					likedList.add(liked);
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return likedList;
	}
	
	public Vector<String> getEmployerDislikedList(String username){
		Vector<String> likedList = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(checkEmployerUsername);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				String[] likedArray = result.getString(14).split(":");
				for(String liked : likedArray){
					likedList.add(liked);
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return likedList;
	}
	
	public void addEmployerLiked(String newLike, String username){
		String liked = newLike + ":";
		Employer temp = getEmployer(username);
		for(String like : temp.getLikedListString()){
			if(!like.equals("")){
				liked = liked + like + ":";
			}
		}
		System.out.println("liked string: " + liked);
		try{
			PreparedStatement ps = conn.prepareStatement(addEmployerLiked);
			ps.setString(1, liked);
			ps.setString(2, username);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addEmployerDisliked(String newLike, String username){
		String liked = newLike + ":";
		Employer temp = getEmployer(username);
		for(String like : temp.getDislikedListString()){
			if(!like.equals("")){
				liked = liked + like + ":";
			}
		}
		System.out.println("disliked string: " + liked);
		try{
			PreparedStatement ps = conn.prepareStatement(addEmployerDisliked);
			ps.setString(1, liked);
			ps.setString(2, username);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> getStudentMatchUsernames(String username){
		Vector<String> usernames = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(getMatchesForStudent);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				usernames.add(result.getString(3));
				System.out.println("Company: " + result.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return usernames;
	}

	public Vector<String> getEmployerMatchUsernames(String username){
		Vector<String> usernames = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(getMatchesForEmployer);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				usernames.add(result.getString(2));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return usernames;
	}

	
	public Vector<String> getChatHistory(String studentUsername, String employerUsername){
		int matchedID = getMatchedID(studentUsername, employerUsername);
		Vector<String> chatHistory = new Vector<String>();
		try{
			PreparedStatement ps = conn.prepareStatement(getChatHistory);
			ps.setInt(1, matchedID);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				chatHistory.add(result.getString(5)+";" + result.getBoolean(4) + ";" + result.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}

		return chatHistory;
	}

	public void addMessage(String studentUsername, String employerUsername, Boolean direction, String message){
		try{
			int id = getMatchedID(studentUsername, employerUsername);
			PreparedStatement ps = conn.prepareStatement(insertMessage);
			ps.setInt(1, id);
			ps.setBoolean(2, direction);
			ps.setString(3, message);
			ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}
	}	
	public int getMatchedID(String studentUsername, String employerUsername){ //helper function
		int id=0;
		try{
			PreparedStatement ps = conn.prepareStatement(getMatchID);
			ps.setString(1, studentUsername);
			ps.setString(2, employerUsername);
			ResultSet result = ps.executeQuery();
			if(result.next()){
				id = result.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	
	public void stop(){
		try{
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
