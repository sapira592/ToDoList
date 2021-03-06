<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<%@ page isErrorPage="true" %>
<% String path = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="windows-1255">
	
	<title>Login</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	

	<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="<%=path %>/style.css">
</head>
<body id="indexBody">
	
	<div id="wrapper">

		<header>
			Registration
		</header>

		<main>
			<section id="Registration">			 
				 <form method="post" id="loginForm" class="form-group" action="<%=path %>/AppController/Registration">
						<input type="text" id="Ruser" name="user" placeholder="Username" >
						<input type="password" id="Rpass" name="pass" placeholder="Password" >
						<input type="password" id="RCpass" name="cpass" placeholder="Confirm password" >
						<input type="submit" name="login" class="btn btn-info" value="Register">
				 </form>
				 <div id="message">
					${message }
				 </div>
			</section>
		</main>
	
		
	</div>

	
</body>
</html>