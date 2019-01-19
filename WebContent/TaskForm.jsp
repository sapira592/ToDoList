<%@ page language="java" import="com.todolist.model.*"
	contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<%@ page isErrorPage="true" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% String path = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="windows-1255">
	
	<title>Task List</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	

	<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="<%=path %>/style.css">
	
	 <script type="text/javascript">
	      var datefield = document.createElement("input")
	      datefield.setAttribute("type", "date")
	      if (datefield.type!="date"){ //if browser doesn't support input type="date", load files for jQuery UI Date Picker
	        document.write('<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />\n')
	        document.write('<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"><\/script>\n')
	        document.write('<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"><\/script>\n')
	      }
     </script>
</head>



<body id="indexBody">

 <script>
    if (datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:
       jQuery(function($){ 
           $('#date').datepicker({ dateFormat: 'yy-mm-dd' }).val();
       }); 
    }
 </script>
	
	<div id="wrapper">
	
		<nav id="taskList">		
			<div id="tasklistWelcome">
				Hello <%= session.getAttribute("user") %>
			</div>			
			
			<div id="logoutLink">	
				<a href="/Todolist/AppController/Logout">Logout</a>
			</div>			
		</nav>
	
		<header>
				My Task List
		</header>		
			
		<main>
			<section id="TaskList">
			    <div id="nav">
					<form method="post" id="ShowTasksForm" class="form-group" action="<%=path %>/AppController/List">
							<input type="submit" name="TaskList" class="btn btn-info" value="All Tasks">				
					</form>	
					
					<form method="post" id="AddTaskForm" class="form-group" action="<%=path %>/AppController/New">
							<input type="submit" name="AddTask" class="btn btn-info" value="Add Task">				
					</form>	
				</div>
				 <c:if test="${item != null}">
		            <form id="TaskForm" class="form-group" method="post" action="<%=path %>/AppController/Update">
		         </c:if>
		         <c:if test="${item == null}">
		            <form id="TaskForm" class="form-group"  method="post" action="<%=path %>/AppController/Insert">
		         </c:if>
		         
		         <h5>
		             <c:if test="${item != null}">
		                   Edit Task
		             </c:if>
		             <c:if test="${item == null}">
		                  Add New Task
		             </c:if>
		         </h5>
		             <label>Task Target Date:</label>
		         	 <input id="date" type="date" name="date" 
		                            value="<c:out value='${item.getTargetDate()}'/>"
		                        />	
		                        
		             <label> Task Description:</label>                        
		         	 <input type="text" id="task" name="content"
		                            value="<c:out value='${item.getContent()}'/>"
		                        />
		                        
		         	 <input type="submit" value="save" />
		        </form>
				
								
				<div id="message">
						<c:out value="${message}"/>
				</div>
					
			</section>
		</main>
			
	</div>

	
</body>
</html>
