package com.todolist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a model Class User it represents the user of the todolist app. 
 * @param id the id of the user
 * @param userName the user name.
 * @param userId the user id of the this task.
 * @param password the password of the user.
 */

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	
	public User(){}
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public User(int id, String userName, String password) {
	
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return "User [id=" + id + ", userName=" + userName +", password="+ password + "]";
	}
	
	

}