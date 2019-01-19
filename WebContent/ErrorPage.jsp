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
	
	<title>Error</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	

	<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="<%=path %>/style.css">
</head>

<body id="indexBody">
	
	<div id="wrapper">

		<header>
				Error 404
		</header>	
			
		<main>
			<section id="error">
			
				Cannot display this page. Please login 
				<a href="http://localhost:8080/Todolist/AppController/Login.jsp">Login</a>
					
			</section>
		</main>
			
	</div>

	
</body>
</html>
