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
</head>

<body id="indexBody">

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
				
		         
		         <div align="center">
			        <table border="1" cellpadding="5">
			        	<c:if test="${items != null}">	        
				       		<tr>
					            <th>Task Creation Date</th>
					            <th>Task Description</th>
					            <th>Task Target Date</th>
					            <th></th>
					        </tr>
					    </c:if>
			            <c:forEach var="item" items="${items}">
			                <tr>
			                    <td><c:out value="${item.getCreationDate()}" /></td>
			                    <td><c:out value="${item.getContent()}" /></td> 
			                    <td><c:out value="${item.getTargetDate()}" /></td> 
			                    <td>
			                        <a href="<%=path %>/AppController/Edit?item=<c:out value='${item.getId()}' />">Edit</a>
			                        &nbsp;&nbsp;&nbsp;&nbsp;
			                        <a href="<%=path %>/AppController/Delete?itemID=<c:out value='${item.getId()}' />">Delete</a>                     
			                    </td>
			                </tr>
			            </c:forEach>
			        </table>
			    </div>   
		
			
				<div id="message">
						<c:out value="${message}"/>
				</div>
					
			</section>
		</main>
			
	</div>

	
</body>
</html>
