<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	if ((session.getAttribute("uName") != null) && session.getAttribute("uName").toString().equals("admin")) {
	%>
	 <a href="adminlogin?action=logout">Logout</a>
	<%
	} 
%>
</body>
</html>