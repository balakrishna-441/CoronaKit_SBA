package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebFilter("/user")
public class UserFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;		
		String action = request.getParameter("action");

		switch (action) {
		case "insertuser": {
			if (StringUtils.isNotBlank(request.getParameter("userName"))
					&& StringUtils.isNotBlank(request.getParameter("userMailId"))
					&& StringUtils.isNotBlank(request.getParameter("UserMobileNumber"))) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.print(
						"<div align =\"center\"><p style=\"color:red\">Please enter all the details before proceeding to shop<p></div>");
				RequestDispatcher rd = req.getRequestDispatcher("newuser.jsp");
				rd.include(request, response);
			}
		}
			break;
		case "placeorder": {
			HttpSession session = req.getSession();
			if (!(session.getAttribute("CartData") == null)) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.write(
						"<div align =\"center\"><p style=\"color:red\">Your cart is empty please add items to place order<p></div>");
				List<ProductMaster> products = (List<ProductMaster>) session.getAttribute("ProductDetails");
				request.setAttribute("products", products);
				RequestDispatcher rd = req.getRequestDispatcher("showproductstoadd.jsp");
				rd.include(request, response);
			}

		}
			break;
		case "saveorder": {
			String addressDetials = request.getParameter("AddressDetails");
			if (StringUtils.isNotBlank(addressDetials)) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.write(
						"<div align =\"center\"><p style=\"color:red\">Please enter delivery address details to confirm order<p></div>");
				RequestDispatcher rd = req.getRequestDispatcher("placeorder.jsp");
				rd.include(request, response);
			}

		}
			break;
		default: {
			chain.doFilter(request, response);
		}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
