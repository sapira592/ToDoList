package com.todolist.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todolist.dao.HibernateToDoListDAO;
import com.todolist.exception.TodoException;
import com.todolist.model.User;
import com.todolist.model.Item;

/**
 * Servlet implementation class AppController
 */
@WebServlet("/AppController/*")
public class AppController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private HibernateToDoListDAO hDao;
	private HttpSession session;
	private RequestDispatcher dispatcher;

	/**
     * This method initializes the: Hibernate, HttpSession, RequestDispatcher objects.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws ServletException AND IOException
     */
	private void Init(HttpServletRequest request, HttpServletResponse response) {		
		
		response.setContentType("text/html"); // Display HTML tags

		hDao = HibernateToDoListDAO.getHibernateToDoListDAO();
		session = request.getSession();
		dispatcher = null;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Init(request, response);
	
		String url = request.getRequestURI();
		String action = url.substring(url.lastIndexOf('/') + 1);

		switch (action) {
	
		case "Login":
			login(request, response);
			break;

		case "Logout":
			logout(request, response);
			break;
			
		case "Registration":
			registration(request, response);
			break;

		case "List":
			showAllItems(request, response);
			break;
	
		case "New":
			showNewForm(request, response);
			break;
		
		case "Insert":
			addNewItem(request, response);
			break;
		
		case "Edit":
			showEditForm(request, response);
			break;

		case "Update":
			updateItem(request, response);
			break;
		
		case "Delete":
			deleteItem(request, response);
			break;

		// First entrance
		default:
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			break;

		}

	}
  

	/**
     * This method clear the session attribute "message". 
     */
	private void clearMessageAtt(){
		if(session.getAttribute("message") != null) {
			session.setAttribute("message", "");
		}
	}
	
	/**
     * This method clear the session attribute "Item". 
     */
	private void clearItemAtt(){
		if(session.getAttribute("item") != null) {
			session.setAttribute("item", null);
		}
	}
		
	/**
     * This method checks if the session attribute is set,
     * i.e, if the user is logged in to the system. 
     * If no - redirect to Error page.
     * @param request is the HttpServletRequest object
     * @param response is the HttpServletResponse object
     * @return void

     * @exception throws ServletException AND IOException
     */
	private void cheakSetSessionAtt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{		
		if(session.getAttribute("user") == null){
			dispatcher = request.getRequestDispatcher("/ErrorPage.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	
/**
     * This method receives a user name and password from the user 
     * and checks whether the user is exists, i.e, is in DB 
     * @param request is the HttpServletRequest object
     * @param response is the HttpServletResponse object
     * @return void
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		clearMessageAtt();
		
		String username, pass;
		User user;
		
		username = request.getParameter("user");
		pass = request.getParameter("pass");

		int valid = 0;
		
		//=== Parameters Validation
		try {
			if(username.isEmpty()) {
				throw new Exception("Username not provided");
			}
		
			if(pass.isEmpty()) {
				throw new Exception("Password not provided");
			}
			valid = 1;
		}catch(Exception e){
			session.setAttribute("message", e.getMessage());
			dispatcher = request.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
		
		//=== Parameters Correctness
		if(valid ==1 ) {
			try {
				user = hDao.fetchUserByUserNameAndPassword(username, pass);
				session.setAttribute("user", user.getUserName());
				session.setAttribute("pass", user.getPassword());
				if (session.getAttribute("message") != null) {
					session.setAttribute("message", "");
				}
				dispatcher = request.getRequestDispatcher("/List.jsp");
				dispatcher.forward(request, response);
				// alert: successful login
		
			} catch (Exception e) {
				// alert: fail to login
				session.setAttribute("message", "Account's Invalid");
				dispatcher = request.getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
				e.printStackTrace();
			}
		}
	}

	/**
     * This method performs Logout from the system
     * by removing the current User from session object
     * @param request is the HttpServletRequest object
     * @param response is the HttpServletResponse object
     * @return void
     * @exception throws ServletException AND IOException
     */
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{	
		session.removeAttribute("user");
		session.removeAttribute("pass");
		session.invalidate();
		response.sendRedirect("Login.jsp"); // new session				
	}
	
	/**
     * This method performs Registration to the system.
     * Receives a user name and password from the user.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void registration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String username, pass, cpass;
		User user;
		
		username = request.getParameter("user");
		pass = request.getParameter("pass");
		cpass = request.getParameter("cpass");
		
		int valid = 0;
				
		// === Parameters Validation
		try {
			if(username.isEmpty()) {
				throw new Exception("Username not provided");
			}
		
			if(pass.isEmpty() || cpass.isEmpty()) {
				throw new Exception("Password not provided");
			}
			
			valid = 1;
			
		}catch(Exception e){
			session.setAttribute("message", e.getMessage());
			dispatcher = request.getRequestDispatcher("/Registration.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
		
		//=== Parameters Correctness
		if(valid == 1) {
			try {
				user = hDao.fetchUserByUserName(username);
				// if success => user already exist! => error
				session.setAttribute("message", "User already exist");
				dispatcher = request.getRequestDispatcher("/Registration.jsp");
				dispatcher.forward(request, response);
	
			} catch (Exception e1) {
				if (Objects.equals(pass, cpass)) {
					try {
						hDao.createNewUser(username, pass);
						session.setAttribute("message", "Registration success. Please Login");
						dispatcher = request.getRequestDispatcher("/Login.jsp");
						dispatcher.forward(request, response);
						// Registration success
	
					} catch (Exception e) {
						session.setAttribute("message", "Internal error. Please try again");
						dispatcher = request.getRequestDispatcher("/Registration.jsp");
						dispatcher.forward(request, response);
						e.printStackTrace();
					}
				}
	
				else {
					session.setAttribute("message", "Not equals passwords");
					dispatcher = request.getRequestDispatcher("/Registration.jsp");
					dispatcher.forward(request, response);
					// Not equals passwords
				}
			}
		}
	}
	
	
	/**
     * This method passes the list of items belonging to 
     * the user who just logged in to session object.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void showAllItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		String username, pass;
		User user;
		
		username = (String) session.getAttribute("user");
		pass = (String) session.getAttribute("pass");
		
		try {
			user = hDao.fetchUserByUserNameAndPassword(username, pass);
			List<Item> items = new ArrayList<Item>();
			items = hDao.fetchItemsListByUserId(user.getId());
			session.setAttribute("items", items);
			dispatcher = request.getRequestDispatcher("/List.jsp");
			dispatcher.forward(request, response);
					
		} catch (Exception e) {
			// alert: failed to load from DB
			//session.setAttribute("message", "Failed to load from DB");
			e.printStackTrace();
		} 

	}

	/**
     * This method dispatches to the TaskForm page in "Add" mode. 
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws ServletException AND IOException
     */
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
     * This method receives new task description and a target date 
     * from the user and add it to his task list.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void addNewItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		String username, pass, item, targetDateInput;
		User user;
		
		username = (String) session.getAttribute("user");
		pass = (String) session.getAttribute("pass");
		item = request.getParameter("content");			
		targetDateInput = request.getParameter("date"); System.out.println(targetDateInput);
		int valid = 0;
		
		// === Parameters Validation
		try {
			if (targetDateInput.isEmpty()) {
				throw new Exception("Target Date not provided");
			}

			if (item.isEmpty()) {
				throw new Exception("Task description not provided");
			}
			valid = 1;
		} catch (Exception e) {
			session.setAttribute("message", e.getMessage());
			dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
		
		if(valid == 1) {
			LocalDate currentDateInput = LocalDate.now();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
			Date targetDate = null;
			Date currentDate = null;
	
			try { 		
				currentDate = Date.from(currentDateInput.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				targetDate = (Date) sdf.parse(targetDateInput);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
				
			try {
				user = hDao.fetchUserByUserNameAndPassword(username, pass);
				Item itemObj = new Item(item, user.getId(),currentDate, targetDate);
				hDao.saveNewItem(itemObj);
				session.setAttribute("message", "Task has been added");
				dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
				dispatcher.forward(request, response);
									
			} catch (Exception e) {
				// alert: failed to add task
				session.setAttribute("message", "Failed to add Task");
				e.printStackTrace();
			}		
		}
	}
	
	
	/**
     * This method dispatches to the TaskForm page in "Edit" mode. 
     * Displays a task that already exists in his Task List
     * and the user has requested to edit, in a edit form.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		String itemID = request.getParameter("item");
		
		try {
			Item item = hDao.fetchItemByItemID(itemID);
			session.setAttribute("item", item);
			dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
			dispatcher.forward(request, response);
		}
		catch (TodoException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
			e.printStackTrace();
		}					
	}
	
	
	/**
     * This method saves the changes a user has made to a 
     * particular task that was already in the list.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void updateItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		Item currentItem = (Item) session.getAttribute("item");
		clearItemAtt();
		
		String content = request.getParameter("content");
		String dateString = request.getParameter("date");
		
		int valid = 0;
		
		// === Parameters Validation
		try {
			if (dateString.isEmpty()) {
				throw new Exception("Target Date not provided");
			}

			if (content.isEmpty()) {
				throw new Exception("Task description not provided");
			}
			valid = 1;
		} catch (Exception e) {
			session.setAttribute("message", e.getMessage());
			dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
		
		if(valid == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
			Date date = null;
			try {
				date = (Date) sdf.parse(dateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				hDao.updateItemByID(currentItem, content, date);
				session.setAttribute("message", "Task has been updated");
				dispatcher = request.getRequestDispatcher("/TaskForm.jsp");
				dispatcher.forward(request, response);
	
			} catch (TodoException e) {
				// alert: failed to load from DB
				session.setAttribute("message", "Failed to load from DB");
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
     * This method deletes a task that from users' Task List.
     * @param request is the HttpServletRequest object.
     * @param response is the HttpServletResponse object.
     * @return void.
     * @exception throws TodoException inside the method
     * @exception throws ServletException AND IOException
     */
	private void deleteItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		cheakSetSessionAtt(request, response);
		
		clearMessageAtt();
		
		String itemID = request.getParameter("itemID");
		
		try {
			Item item = hDao.fetchItemByItemID(itemID);
			hDao.deleteItemByID(item);
			session.setAttribute("message", "Task has been deleted.");
			dispatcher = request.getRequestDispatcher("/List.jsp");
			dispatcher.forward(request, response);
		}
		catch (TodoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
		
}

















