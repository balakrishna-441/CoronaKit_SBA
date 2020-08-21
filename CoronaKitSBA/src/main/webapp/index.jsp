<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit Portal</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

	if (session.getAttribute("uName") == null ) {
	%>
<a href="user?action=newuser">Create Corona Kit</a>
<hr>
	<form action="adminlogin?action=login" method="post">
	<div align="center">
		<table>
			<tbody>
				<tr><td><label for="loginid">Enter login Id :</label></td><td><input type="text" id="loginid" name="loginid"></td></tr>
				<tr><td><label for="password">Enter password :</label></td><td><input type="text" id="password" name="password"></td></tr>
			</tbody>
		</table>
	</div>
	<div align="center"><br/><input type=submit value="Login"></div>
	</form>
<hr>
<jsp:include page="footer.jsp"/>
<%
	} else if(session.getAttribute("uName").toString().equals("admin")){
		session.setAttribute("uName","admin");
		request.getRequestDispatcher("adminlogin?action=list").forward(request, response);
}else{
	session.setAttribute("uName",null);
	response.sendRedirect("index.jsp");
}

%>
</body>
</html>
