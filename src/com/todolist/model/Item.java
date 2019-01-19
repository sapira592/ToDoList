package com.todolist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Entity;

/**
 * This is a model Class Item it represents the task of the todolist app. 
 * @param id the id of the item
 * @param content the content of the task to complete.
 * @param userId the user id of the this task.
 * @param creationDate the creation date this task.
 */

@Entity
@Table(name = "Items")
public class Item {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name="targetDate")
	private Date targetDate;
	
	public Item(){}
		
	public Item(String content, int userId, Date creationDate, Date targetDate) {
		this.content = content;
		this.userId = userId;
		this.creationDate = creationDate;
		this.targetDate = targetDate;
	}
	
	public Item(int id, String content, int userId, Date creationDate, Date targetDate) {
		this.id = id;
		this.content = content;
		this.userId = userId;
		this.creationDate = creationDate;
		this.targetDate = targetDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	
	public String toString() {
		return "Item [id=" + id + ", content=" + content + ",userId=" + userId + ",creationDate=" + creationDate + "]";
	}
	    
}