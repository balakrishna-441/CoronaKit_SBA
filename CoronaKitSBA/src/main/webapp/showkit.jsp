<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
	<div align="center"><h1>Please check the cart list...</h1></div>
		<br/>
	<hr/>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store");
		List<KitDetail> kits = (List<KitDetail>) session.getAttribute("CartData");
	%>	
		<div align="center">
			<table border="1">
				<thead style="background-color: #FFFFE0;">
					<th>Kit Id</th>
					<th>Product Name</th>
					<th>Product Cost</th>
					<th>Product Quantity</th>
					<th>Sub Total Cost</th>
					<th>Option</th>
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
						<td>							
								<a href="user?action=deleteitem&productId=<%=kit.getProduct().getId()%>&Quantity=1"><input type="button" value="-"></a>							
								 &nbsp;<button><%=kit.getQuantity()%></button>&nbsp;							
								<a href="user?action=addnewitem&ProductId=<%=kit.getProduct().getId()%>"><input type="button" value="+"></a>
						</td>
						<td><%=kit.getAmount()%></td>
						<td>
							<div align="center">
								<a href="user?action=deleteitem&productId=<%=kit.getProduct().getId()%>&Quantity=0" onclick="return confirm('Are you sure to delete product')">
									<button>Delete</button>
								</a>
							</div>
						</td>
					</tr>
						<%	
						totalAmount = totalAmount+kit.getAmount();
							}
						%>				
					</tbody>
				</table>
				<br/>
				<div align="center">&emsp;&emsp;&emsp;Total Cost = <%=totalAmount%></div>
		</div><br/>
		<div align="center">
			<a href="user?action=showproducts"><button>Continue Shopping...</button></a>&nbsp;
			<a href="user?action=placeorder"><button>Place Order</button></a>
		</div>
	<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>