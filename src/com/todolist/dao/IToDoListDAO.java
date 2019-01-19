package com.todolist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todolist.exception.TodoException;
import com.todolist.model.Item;
import com.todolist.model.User;

/**
 * This is a DAO interface of the todolist app.
 * Method details provided in the HibernateToDoListDAO implementation.
 */

public interface IToDoListDAO {
	
	//User related
		public abstract User fetchUserByUserNameAndPassword(String userName, String password) throws Exception;
		public abstract User fetchUserByUserName(String userName) throws TodoException;
		public abstract void createNewUser(String userName, String Password) throws TodoException; 
		
		
	//Item related	
		public abstract List<Item> fetchItemsListByUserId(int userID) throws TodoException; //might need pagination
		public abstract Item fetchItemByItemID(String itemID) throws TodoException;
		public abstract void saveNewItem(Item item) throws TodoException;
		public abstract void updateItemByID(Item item, String content, Date date) throws TodoException;
		public abstract void deleteItemByID(Item item) throws TodoException;

}
