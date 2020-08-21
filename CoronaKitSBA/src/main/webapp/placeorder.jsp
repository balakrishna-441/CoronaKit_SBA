<%@page import="com.iiht.evaluation.coronokit.model.UserDetails"%>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
	<div align="center"><h1>Your cart details...</h1></div>
		<br/>
	<hr/>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store");
		List<KitDetail> kits = (List<KitDetail>) session.getAttribute("CartData");
	%>
	
	<form action="user?action=saveorder" method="post">
	<div align="center">
		<table border="1">
			<thead style="background-color: #FFFFE0;">
				<th>Kit Id</th>
				<th>Product Name</th>
				<th>Product Cost</th>
				<th>Product Quantity</th>
				<th>Sub Total Cost</th>
			</thead>
			<tbody>
	<%
		double totalAmount = 0.0;
		for (KitDetail kit : kits) {
	%>
				<tr style="background-color: #BDB76B; color: #ffffff;">						
					<td><%=kit.getId()%></td>
					<td><%=kit.getProduct().getProductName()%></td>
					<td><%=kit.getProduct().getCost()%></td>
					<td><%=kit.getQuantity()%></td>
					<td><%=kit.getAmount()%></td>							
				</tr>
	<%	totalAmount = totalAmount+kit.getAmount();
			}
	%>						
			</tbody>
		</table>
		<br/><div align="center">&emsp;&emsp;&emsp;Total Cost = <%=totalAmount%></div>
	</div><br/>
	<% session.setAttribute("TotalOrderAmount", totalAmount); %>
	<div align="center">
		<% UserDetails user = (UserDetails)session.getAttribute("UserDetails");%>
		<table>
			<tbody>
				<tr><td><label>Name:</label></td><td><%=user.getName()%></td></tr>
				<tr><td><label>Email ID:</label></td><td><%=user.getEmailId()%></td></tr>
				<tr><td><label>Mobile Number:</label></td><td><%=user.getContactNumber()%></td></tr>
				<tr><td><label>Please enter Delivery Address details:</label></td><td><textarea rows="5" cols="20" name="AddressDetails"></textarea></td></tr>			
			</tbody>
		</table>
	</div><br/><br/>
	<div align="center">
		<input type="submit" value="Confirm Order">
	</div>		
	</form>
	<div align="center">
		<p> OR </p>
	</div>	
	<div align="center">
		<form action="user?action=showproducts" method= "post">
			<input type="submit" value="Continue Shopping...">
		</form>
	</div>
	<hr/>
	<jsp:include page="footer.jsp"/>
</body>
</html>