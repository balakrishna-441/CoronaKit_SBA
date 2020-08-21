<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body>
<hr/>
<h1 align="center">Please enter details to proceed...</h1>
<form action="user?action=insertuser" method="post">
		<div align="center">
		<table>
			<tbody>
				<tr><td><label>Enter Name:</label></td><td> <input type="text" name="userName"></td></tr> 
				<tr><td><label>Enter Mail ID:</label></td><td> <input type="text" name="userMailId"></td></tr> 
				<tr><td><label>Enter Mobile Number:</label></td><td> <input type="text" name="UserMobileNumber"></td></tr> 
			</tbody>
		</table>
		</div>
		
		<div align="center"><input type=submit value="Click to Shop"></div>
</form>

<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>