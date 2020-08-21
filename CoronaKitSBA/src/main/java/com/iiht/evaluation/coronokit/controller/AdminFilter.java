package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

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

@WebFilter("/adminlogin")
public class AdminFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String action = request.getParameter("action");

		switch (action) {
		case "insertproduct": {

			if (StringUtils.isNotBlank(request.getParameter("ProductName"))
					&& StringUtils.isNotBlank(request.getParameter("ProductDescription"))
					&& StringUtils.isNotBlank(request.getParameter("ProductCost"))) {
				HttpSession session = req.getSession();
				List<ProductMaster> products = (List<ProductMaster>) session.getAttribute("ProductDetails");

				int size = products.stream()
						.filter(product -> product.getProductName().equals(request.getParameter("ProductName")))
						.collect(Collectors.toList()).size();

				if (size == 0) {
					chain.doFilter(request, response);
				} else {
					PrintWriter out = response.getWriter();
					out.print(
							"<div align =\"center\"><p style=\"color:red\">Entered product name already exist please check..<p></div>");
					RequestDispatcher rd = req.getRequestDispatcher("newproduct.jsp");
					rd.include(request, response);
				}
			} else {
				PrintWriter out = response.getWriter();
				out.print(
						"<div align =\"center\"><p style=\"color:red\">Please enter all product fields to add..<p></div>");
				RequestDispatcher rd = req.getRequestDispatcher("newproduct.jsp");
				rd.include(request, response);
			}
		}
			break;
		case "updateproduct": {
			HttpSession session = req.getSession();
			if (StringUtils.isNotBlank(req.getParameter("ProductDescription"))
					&& StringUtils.isNotBlank(req.getParameter("ProductCost"))) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.print(
						"<div align =\"center\"><p style=\"color:red\">Please enter all product fields to update..<p></div>");
				ProductMaster product = (ProductMaster) session.getAttribute("EditProduct");
				request.setAttribute("Product", product);
				RequestDispatcher rd = req.getRequestDispatcher("editproduct.jsp");
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
