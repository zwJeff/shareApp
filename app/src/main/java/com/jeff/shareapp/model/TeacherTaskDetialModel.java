package com.jeff.shareapp.model;

public class TeacherTaskDetialModel {


	private int userId;
	private String userName;
	private int score;
	
	
	public TeacherTaskDetialModel(int userId, String userName, int score) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.score = score;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
