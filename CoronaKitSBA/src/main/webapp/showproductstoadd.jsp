<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>
<%@page import="com.iiht.evaluation.coronokit.model.KitDetail"%>
<%@page import="com.iiht.evaluation.coronokit.model.UserDetails"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store");
	%>
	<div align="left"><h1>Welcome : <%=((UserDetails)session.getAttribute("UserDetails")).getName()%>, Please select require products to order</h1></div>
	<br/>
	<hr/>	
	<div align="right">
		<% if(!(session.getAttribute("CartData") == null))
				{
					int items = ((List<KitDetail>) session.getAttribute("CartData")).size();
		%>
			<a href="user?action=showkit">
				<button>Items in Cart(<%= items %>)</button>
			</a>
		<%
			}else{				
		%>	
			<button>Items in Cart(0)</button>	
		<%
			}
		%>
	</div>
		<%
			List<ProductMaster> products = (List<ProductMaster>) request.getAttribute("products");
		%>
	<div align="center">		
			<div align="center">
				<table border="1">
					<thead style="background-color: #FFFFE0;">
						<th>Product Name</th>
						<th>Product Description</th>
						<th>Product Cost</th>
						<th>Select Product</th>
					</thead>
					<tbody>
						<%
							for (ProductMaster product : products) {
						%>
						<tr style="background-color: #BDB76B; color: #ffffff;">						
							<td><%=product.getProductName()%></td>
							<td><%=product.getProductDescription()%></td>
							<td><%=product.getCost()%></td>
							<td>
								<div align="center">
									<a href="user?action=addnewitem&ProductId=<%=product.getId()%>">
										<button>Add To Cart</button>
									</a>
								</div>
							</td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
			<br/>
			<div align="center">
				<a href="user?action=placeorder">
					<button>Place Order</button>
				</a>
			</div>
	</div>
	<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>