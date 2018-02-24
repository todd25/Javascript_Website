package data;

import java.util.Vector;

public class Employer{
	//required
	private String username;
	private String password;
	private String company;
	private String email;
	private String image;
	private String city;
	private String industry;
	private boolean sponsorship;
	private String position;
	private String recruiter;
	private String phone;
	
	private Vector<Student> likedList;
	private Vector<Student> dislikedList;
	private Vector<Student> matchedList;
	
	private Vector<String> likedListString;
	private Vector<String> dislikedListString;
	private Vector<String> matchedListString;
	//COMPANY,USERNAME,PW,CITY,EMAIL, PHONENUMBER, RECRUITER
	
	public Employer(){
		likedList = new Vector<Student>();
		matchedList = new Vector<Student>();
		dislikedList = new Vector<Student>();
		
		likedListString = new Vector<String>();
		dislikedListString = new Vector<String>();
		matchedListString = new Vector<String>();
	}
	
	public Employer(String username, String password, String company, String email, String phone, String image, String city, 
			String industry, String sponsorship, String recruiter, String position){
		this.username = username;
		this.password = password;
		this.company = company;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.city = city;
		this.industry = industry;
		
		if(sponsorship==null){
			this.sponsorship = false;
		}
		else if(sponsorship.equals("on")){
			this.sponsorship = true;
		}

		this.recruiter = recruiter;
		this.position = position;
		likedList = new Vector<Student>();
		matchedList = new Vector<Student>();
		dislikedList = new Vector<Student>();
		
		likedListString = new Vector<String>();
		dislikedListString = new Vector<String>();
		matchedListString = new Vector<String>();
	}
	
	//setters
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setCompany(String company){
		this.company = company;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setIndustry(String industry){
		this.industry = industry;
	}
	
	public void setSponsorship(boolean sponsorship){
		this.sponsorship = sponsorship;
	}
	
	public void setRecruiter(String recruiter){
		this.recruiter = recruiter;
	}
	
	public void setPosition(String position){
		this.position = position;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public void setLikedList(Vector<Student> likedList){
		this.likedList = likedList;
	}
	
	public void setDislikedList(Vector<Student> dislikedList){
		this.dislikedList = dislikedList;
	}
	
	
	public void setLikedListString(Vector<String> likedList){
		this.likedListString = likedList;
	}
	
	public void setDislikedListString(Vector<String> dislikedList){
		this.dislikedListString = dislikedList;
	}
	
	public void addLiked(Student student){
		likedList.add(student);
	}
	
	public void addDisliked(Student student) {
		dislikedList.add(student);
	}
	
	public void addMatched(Student student){
		matchedList.add(student);
	}
	
	//getters
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getCompany(){
		return company;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getIndustry(){
		return industry;
	}
	public String getImage(){
		return image;
	}
	
	public boolean getSponsorship(){
		return sponsorship;
	}
	
	public String getPosition(){
		return position;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getRecruiter(){
		return recruiter;
	}
	
	public Vector<String> getLikedListString(){
		return this.likedListString;
	}
	
	public Vector<String> getDislikedListString(){
		return this.dislikedListString;
	}
	
	public Student getUserFromLiked(String username){
		Student student = null;
		for(Student stu : likedList){
			if(stu.getUsername().equals(username)){
				return stu;
			}
		}
		return student;
	}
	
	public Student getUserFromDisliked(String username) {
		Student student = null;
		for(Student s : dislikedList) {
			if(s.getUsername().equals(username)) {
				return s;
			}
		}
		return student;
	}
	
	public Student getUserFromMatched(String username){
		Student student = null;
		for(Student stu : matchedList){
			if(stu.getUsername().equals(username)){
				return stu;
			}
		}
		return student;
	}
	
	public Vector<Student> getLikedList() {
		return this.likedList;
	}
	
	public Vector<Student> getDislikedList() {
		return this.dislikedList;
	}
	
	public Vector<Student> getMatchedList() {
		return this.matchedList;
	}
	
	public void setMatchedListString(Vector<String> matchedList){
		this.matchedListString = matchedList;
	}
	
	public Vector<String> getMatchedListString(){
		return this.matchedListString;
	}
	
	//other
	//test whether the user is in the liked list
	public boolean testLiked(String username){
		boolean exist = false;
		if(!likedList.isEmpty()){
			for(Student student : likedList){
				if(student.getUsername().equals(username)){
					exist = true;
				}
			}
		}
		
		return exist;
	}
	
	public boolean testDisliked(String username) {
		boolean exist = false;
		if(!dislikedList.isEmpty()){
			for(Student student : dislikedList){
				if(student.getUsername().equals(username)){
					exist = true;
				}
			}
		}
		return exist;
	}
}
