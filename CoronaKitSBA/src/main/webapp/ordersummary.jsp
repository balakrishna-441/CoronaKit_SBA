<%@page import="com.iiht.evaluation.coronokit.model.OrderSummary"%>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
	<div align="center"><h1>Your Order Summary Details</h1></div>
		<br/>
	<hr/>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store");
		OrderSummary orderData = (OrderSummary)session.getAttribute("orderSummary");
	%>
	<div align="center">
		<table>
			<tbody>
				<tr><td>Order Id</td><td>:</td><td><%=orderData.getCoronaKit().getId()%></td></tr>
				<tr><td>Order Amount</td><td>:</td><td><%=orderData.getCoronaKit().getTotalAmount()%></td></tr>
				<tr><td>Ordered Products</td><td>:</td>
					<td>
						<table border="1">
							<thead style="background-color: #FFFFE0;">
								<th>Product Name</th>
								<th>Product Quantity</th>
								<th>Sub Total Cost</th>
							</thead>
							<tbody>
							<% for (KitDetail kit : orderData.getKitDetails()) {%>
								<tr style="background-color: #BDB76B; color: #ffffff;">	
									<td><%=kit.getProduct().getProductName()%></td>
									<td><%=kit.getQuantity()%></td>
									<td><%=kit.getAmount()%></td>							
								</tr>
							<%}	%>					
							</tbody>
						</table>
					</td>
				</tr>
				<tr><td>Delivery Address</td><td>:</td>
					<td>
						<table>
							<tr><td><div  align="center"><%=orderData.getCoronaKit().getPersonName()%></div></td></tr>							
							<tr><td><div  align="center"><%=orderData.getCoronaKit().getDeliveryAddress()%></div></td></tr>
							<tr><td><div  align="center">Mobile Number:<%=orderData.getCoronaKit().getContactNumber()%></div></td></tr>
						</table>
					</td>
					
			</tbody>
		</table>
	</div>
	<hr/>
	<%
		session.removeAttribute("orderSummary");
		session.invalidate();
	%>		
	<jsp:include page="footer.jsp"/>
</body>
</html>