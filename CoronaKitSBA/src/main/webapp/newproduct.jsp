<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
	<jsp:include page="header.jsp"/> || 
	<jsp:include page="logoutlink.jsp"/>
	<hr/>
<%
	response.setHeader("Cache-Control", "no-cache, no-store");
	if (session.getAttribute("uName")== null) {
		response.sendRedirect("index.jsp");
	}
	%>

	<form action="adminlogin?action=insertproduct" method="post">
		<div align="center">
			<table>
				<tbody>
					<tr><td><label>Enter Product Name:</label></td><td><input type="text" name="ProductName"></td></tr> 
					<tr><td><label>Enter Product Description:</label></td><td><input type="text" name="ProductDescription"></td></tr>
					<tr><td><label>Enter Product Cost:</label></td><td><input type="text" name="ProductCost"></td></tr>
				</tbody>
			</table>
		</div>
		<div align="center"><input type=submit title="Add Product"></div>
	</form>

	<hr/>	
	<jsp:include page="footer.jsp"/>
	
</body>
</html>