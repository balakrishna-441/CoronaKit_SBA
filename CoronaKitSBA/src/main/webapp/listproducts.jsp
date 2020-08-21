<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit available Products : Dashboard</title>
</head>
<body>

	<%
	response.setHeader("Cache-Control", "no-cache, no-store");
	if (session.getAttribute("uName")== null) {
		response.sendRedirect("index.jsp");
	}
	%>

	<p align="left">
		Welcome :
		<%=session.getAttribute("uName")%></p>
	<br />

	<jsp:include page="logoutlink.jsp"></jsp:include>
	|| <a href="adminlogin?action=newproduct">Add New Product</a>
	<hr />
	<%
		List<ProductMaster> products = (List<ProductMaster>) request.getAttribute("products");
	%>
	<div>
		<table border="1">
			<thead style="background-color: #FFFFE0;">
				<th>Product Name</th>
				<th>Product Description</th>
				<th>Product Cost</th>
			</thead>
			<tbody>
				<%
					for (ProductMaster product : products) {
				%>
				<tr style="background-color: #BDB76B; color: #ffffff;">
					<td><%=product.getProductName()%></td>
					<td><%=product.getProductDescription()%></td>
					<td><%=product.getCost()%></td>
					<td><a
						href="adminlogin?action=editproduct&ProductId=<%=product.getId()%>">Edit</a></td>
					<td><a
						href="adminlogin?action=deleteproduct&ProductId=<%=product.getId()%>">Delete</a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	

</body>
</html>