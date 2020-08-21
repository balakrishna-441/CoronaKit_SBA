<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Products Module --- Exception</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div>
		<h3>Something went wrong. We regret for inconvenience!</h3>
		<br />
		<h1>
			Error Message:
			<%=exception.getMessage()%></h1>
		<br />
		<h2>Please contact system administrator</h2>
		<br />
	</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>