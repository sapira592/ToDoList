package com.todolist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.todolist.model.Item;
import com.todolist.model.User;
import com.todolist.exception.TodoException;



/**
 * The Class provide connectivity to the database. 
 * the class uses the singleton design pattern and utilizes the IToDoListDAO interface
 */

@SuppressWarnings("unused")
public class HibernateToDoListDAO implements IToDoListDAO{
 
	private static HibernateToDoListDAO hibernateToDoListDAO = null;
	private static SessionFactory factory = null;
	private Session session = null;
	
	private HibernateToDoListDAO() {};

	public static HibernateToDoListDAO getHibernateToDoListDAO()
	{
		if (hibernateToDoListDAO == null) {
			hibernateToDoListDAO = new HibernateToDoListDAO();
		}
		
		return hibernateToDoListDAO;
	}
	
    private static Session getSession() {
        //creating factory for getting sessions
        factory = new AnnotationConfiguration().configure().buildSessionFactory();

        //creating a new session for adding products
        return factory.openSession();
   }
    
    
//////////////////////// User related ///////////////////////
    /**
     * This method return a user class instance. 
     * @param userName the user name used in the query
     * @param password the password used in the query
     * @return User a user instance created by retrived data
     * @exception throws TodoException
     * @see User
     */
    @Override
	public User fetchUserByUserNameAndPassword(String userName, String password) throws Exception {
		
		session = getSession();
        session.beginTransaction();
        
        User user = null;
        
        try{
        		user = (User) session.createCriteria(User.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("password", password)).uniqueResult();
                session.getTransaction().commit();
            
            if (user == null) {
                throw new Exception("user" + userName + "was not found"); //Exception("user" + userName + "was not found");
            }
            
        }catch (Exception e){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw e;
            
        }finally {
            session.close();
        }
        
       return user;
	}
    
    /**
     * This method return a user class instance. 
     * @param userName the user name used in the query
     * @return User a user instance created by retrived data
     * @exception throws TodoException
     * @see User
     */
    @Override
	public User fetchUserByUserName(String userName) throws TodoException {
		
		session = getSession();
        session.beginTransaction();
        
        User user = null;
        
        try{
        		user = (User) session.createCriteria(User.class).add(Restrictions.eq("userName", userName)).uniqueResult();
                session.getTransaction().commit();
            
            if (user == null) {
                throw new TodoException("user" + userName + "was not found");
            }
            
        }catch (TodoException e){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw e;
            
        }finally {
            session.close();
        }
        
       return user;
    }
	
    /**
     * This method creates new user by user name and password. 
     * @param userName the user name used in the query
     * @param password the password used in the query
     * @exception throws TodoException
     * @return void
     */
	@Override
	public void createNewUser(String userName, String password) throws TodoException {
       
		session = getSession();
        session.beginTransaction();

        User user = new User(userName,password);
        
        try{
        	
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            
        }catch (Exception e){
        	
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("was unable to save");
            
        }finally {
            session.close();
        }
	}
	
	
	////////////////////// Item related ////////////////
    /**
     * This method return a list of items fetched by user id. 
     * @param userID the user id used in the query
     * @return List<Item> a list of items fetch from database.
     * @exception throws TodoException
     * @see Item
     */
	@Override
	public List<Item> fetchItemsListByUserId(int userID) throws TodoException {
		session = getSession();
        session.beginTransaction();

        
        try{
        	
        	List<Item> itemList =  (List<Item>) session.createCriteria(Item.class).add(Restrictions.eq("userId", userID)).list();
            session.getTransaction().commit();
            return itemList;
            
        }catch (Exception e){
        	
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("Was unable to find item list by user id");
            
        }finally {
            session.close();
        }
	}
	
    /**
     * This method fetches a single item by item id. 
     * @param itemID the item id used in the query.
     * @return item instance created from data fetched from database.
     * @exception throws TodoException
     * @see Item
     */
	@Override
	public Item fetchItemByItemID(String itemID) throws TodoException {
		session = getSession();
        session.beginTransaction();

        int id = Integer.parseInt(itemID);
        
        try{
        	
        	Item item =  (Item) session.createCriteria(Item.class).add(Restrictions.eq("id", id)).uniqueResult();
            session.getTransaction().commit();
            return item;
            
        }catch (Exception e){
        	
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("Was unable to find item by id");
            
        }finally {
            session.close();
        }
	}
	
	 /**
     * This method saves provided item data in database. 
     * @param item the item instance provided to save.
     * @return void.
     * @exception throws TodoException
     * @see Item
     */
	@Override
	public void saveNewItem(Item item) throws TodoException {

		session = getSession();
        session.beginTransaction();

        
        try{
        	
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            
        }catch (Exception e){
        	
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("Was unable save new item");
            
        }finally {
            session.close();
        }
	}
	
	 /**
     * This method updates provided item data in database. 
     * @param item the item instance provided to be updated.
     * @param content the content of the item to be updated.
	 * @param date the date of the item to be updated.
     * @return void.
     * @exception throws TodoException
     * @see Item
     */
	@Override
	public void updateItemByID(Item item, String content, Date date) throws TodoException {
		
		session = getSession();
        session.beginTransaction();

        try{
        	
        	item.setContent(content);
        	item.setCreationDate(date);
        	session.merge(item);
            session.getTransaction().commit();
            
            if (item == null) {
                throw new TodoException("Item" + item + "was not found");
            }
            
        }catch (Exception e){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("Was unable to update item by id");
            
        }finally {
            session.close();
        }

	}
	
	 /**
     * This method deletes itemby id. 
     * @param item the item instance provided to be deletd.
     * @return void.
     * @exception throws TodoException
     * @see Item
     */
	@Override
	public void deleteItemByID(Item item) throws TodoException {
		session = getSession();
        session.beginTransaction();

        try{
        	
        		session.delete(item);
            session.getTransaction().commit();
            
            if (item == null) {
                throw new TodoException("Item" + item + "was not found");
            }
            
        }catch (Exception e){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            
            throw new TodoException("Was unable to delete item by id");
            
        }finally {
            session.close();
        }
		
	}
	
	
}
