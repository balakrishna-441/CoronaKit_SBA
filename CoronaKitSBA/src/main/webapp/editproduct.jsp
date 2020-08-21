<%@page import="com.iiht.evaluation.coronokit.model.ProductMaster"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Edit Product(Admin)</title>
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
	ProductMaster product = (ProductMaster)request.getAttribute("Product"); 
%>
	<form action="adminlogin?action=updateproduct&ProductId=<%=product.getId()%>" method="post">
		<div align="center">
			<table>
				<tbody>			
					<tr><td><label>Product Id:</label></td><td><div><%=product.getId()%></div></td></tr> 
					<tr><td><label>Product Name:</label></td><td><div><%=product.getProductName()%></div></td></tr> 
					<tr><td><label>Product Description:</label></td><td><input type="text" value='<%=product.getProductDescription()%>' name="ProductDescription"></td></tr>  
					<tr><td><label>Product Cost:</label></td><td><input type="text" value='<%=product.getCost()%>' name="ProductCost"></td></tr> 	
				</tbody>
			</table>			
		</div>
		<div align="center"><input type=submit title="Update Product"></div>
	</form>

<hr/>	
	<jsp:include page="footer.jsp"/>
	
</body>
</html>