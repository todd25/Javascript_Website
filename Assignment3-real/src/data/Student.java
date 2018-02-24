package data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import com.sun.java_cup.internal.runtime.Scanner;

public class Student extends Thread{
	//required
	private String username;
	private String password;
	private String fullname;
	private String school;
	private String major;
	private String email;
	private String city;
	private String resume;
	private String image;
	private String gradYear;
	private boolean sponsorship;
	
	//optional
	private String GPA;
	private String phone;
	
	private Vector<Employer> likedList;
	private Vector<Employer> dislikedList;
	private Vector<Employer> matchedList;
	
	private Vector<String> likedListString;
	private Vector<String> dislikedListString;
	private Vector<String> matchedListString;
	
	//FULLNAME,USERNAME,PW,SCHOOL,MAJOR, GRADYEAR, CITY, EMAIL, RESUMELINK, GPA, PHONENUMBER
	
	public Student(){
		likedList = new Vector<Employer>();
		matchedList = new Vector<Employer>();
		dislikedList = new Vector<Employer>();
		
		likedListString = new Vector<String>();
		dislikedListString = new Vector<String>();
		matchedListString = new Vector<String>();
	}
	
	
	public Student(String fullname, String username, String password, String school, String major, String gradYear,
			String city, String email, String resume, String image, String GPA, String phone, String sponsorship){
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.school = school;
		this.major = major;
		this.gradYear = gradYear;
		this.city = city;
		this.email = email;
		this.resume = resume;
		this.image = image;
		this.GPA = GPA;
		this.phone = phone;
		if(sponsorship==null){
			this.sponsorship = false;
		}
		else if(sponsorship.equals("on")){
			this.sponsorship = true;
		}
		//this.sponsorship = sponsorship;
		likedList = new Vector<Employer>();
		matchedList = new Vector<Employer>();
		dislikedList = new Vector<Employer>();
		
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
	
	public void setFullname(String fullname){
		this.fullname = fullname;
	}
	
	public void setSchool(String school){
		this.school = school;
	}
	
	public void setMajor(String major){
		this.major = major;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setGradYear(String gradYear){
		this.gradYear = gradYear;
	}
	
	public void setResume(String resume){
		this.resume = resume;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public void setSponsorship(boolean sponsorship){
		this.sponsorship = sponsorship;
	}
	
	public void setGPA(String GPA){
		this.GPA = GPA;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public void addLiked(Employer employer){
		likedList.add(employer);
	}
	
	public void addDisliked(Employer e) {
		dislikedList.add(e);
	}
	
	public void addMatched(Employer employer){
		matchedList.add(employer);
	}
	
	public void setLikedList(Vector<Employer> likedList){
		this.likedList = likedList;
	}
	
	public void setDislikedList(Vector<Employer> dislikedList){
		this.dislikedList = dislikedList;
	}
	
	public void setLikedListString(Vector<String> likedList){
		this.likedListString = likedList;
	}
	
	public void setDislikedListString(Vector<String> dislikedList){
		this.dislikedListString = dislikedList;
	}
	
	public void setMatchedListString(Vector<String> matchedList){
		this.matchedListString = matchedList;
	}
	
	public Vector<String> getMatchedListString(){
		return this.matchedListString;
	}
	
	//getters
	public String getUsername(){
		return username;
	}
	
	public String getFullname(){
		return fullname;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getSchool(){
		return school;
	}
	
	public String getMajor(){
		return major;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getResume(){
		return resume;
	}
	
	public boolean getSponsorship(){
		return sponsorship;
	}
	
	public String getGPA(){
		return GPA;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getGradYear(){
		return gradYear;
	}
	
	public Employer getUserFromLikedList(String username){
		Employer employer = null;
		for(Employer emp : likedList){
			if(emp.getUsername().equals(username)){
				return emp;
			}
		}
		return employer;
	}
	
	public Employer getUserFromDislikedList(String username) {
		Employer employer = null;
		for(Employer emp : dislikedList){
			if(emp.getUsername().equals(username)){
				return emp;
			}
		}
		return employer;
	}
	
	public Employer getUserFromMatchedList(String username){
		Employer employer = null;
		for(Employer emp : matchedList){
			if(emp.getUsername().equals(username)){
				return emp;
			}
		}
		return employer;
	}
	
	public Vector<Employer> getMatchedList() {
		return this.matchedList;
	}
	
	public Vector<Employer> getDislikedList() {
		return this.dislikedList;
	}
	
	public Vector<Employer> getLikedList() {
		return this.likedList;
	}
	
	public Vector<String> getLikedListString(){
		return this.likedListString;
	}
	
	public Vector<String> getDislikedListString(){
		return this.dislikedListString;
	}
	//other
	//test whether the user is in the liked list
	public boolean testLiked(String username){
		boolean exist = false;
		if(!likedList.isEmpty()){
			for(Employer employer : likedList){
				if(employer.getUsername().equals(username)){
					exist = true;
				}
			}
		}
		
		return exist;
	}
	
	// test whether the user is in the disliked list
	public boolean testDisliked(String username) {
		boolean exist = false;
		if(!dislikedList.isEmpty()) {
			for(Employer e : dislikedList) {
				if(e.getUsername().equals(username)) {
					exist = true;
				}
			}
		}
		return exist;
	}
	

}
