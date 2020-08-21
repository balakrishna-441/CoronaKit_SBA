package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet("/adminlogin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;
	private HttpSession session;

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {

		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login":
				viewName = adminLogin(request, response);
				break;
			case "newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;
			case "logout":
				viewName = adminLogout(request, response);
				break;
			default:
				viewName = "notfound.jsp";
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);

	}

	/**
	 * This method will logout user and remove user information
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		session.removeAttribute("uName");
		session.invalidate();
		return "index.jsp";
	}

	/**
	 * This method will display all the available product details from Database to
	 * Admin Dash board
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private String listAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		session = request.getSession();
		List<ProductMaster> products = this.productMasterDao.getProductRecords();
		request.setAttribute("products", products);
		session.setAttribute("ProductDetails", products);
		return "listproducts.jsp";
	}

	/**
	 * This method will update the product information to Dasabase for Admin updated
	 * changes
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private String updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ServletException {
		session = request.getSession();
		ProductMaster product = new ProductMaster(Integer.parseInt(request.getParameter("ProductId")),
				request.getParameter("ProductDescription"), Double.parseDouble(request.getParameter("ProductCost")));

		int updateResult = this.productMasterDao.updateProduct(product);

		if (!(updateResult > 0)) {
			throw new ServletException("Product is not updated successfully. Please try again");
		}
		session.removeAttribute("EditProduct");
		return "adminlogin?action=list";
	}

	/**
	 * This method will navigate to Edit product form page for selected product
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		session = request.getSession();
		ProductMaster product = this.productMasterDao.getProductData(request.getParameter("ProductId"));
		request.setAttribute("Product", product);
		session.setAttribute("EditProduct", product);
		return "editproduct.jsp";
	}

	/**
	 * This method will delete record in Database for the selected product by Admin
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private String deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ClassNotFoundException, SQLException, ServletException {
		int deleteResult = this.productMasterDao.deleteProduct(Integer.parseInt(request.getParameter("ProductId")));
		if (!(deleteResult > 0)) {

			throw new ServletException("Product is not deleted successfully. Please try again");
		}
		return "adminlogin?action=list";
	}

	/**
	 * This method will add the new product added by Admin to DataBase
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private String insertProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, ClassNotFoundException, SQLException, IOException {

		ProductMaster product = new ProductMaster(request.getParameter("ProductName"),
				request.getParameter("ProductDescription"), Double.parseDouble(request.getParameter("ProductCost")));

		int insertresult = this.productMasterDao.addNewProduct(product);

		if (!(insertresult > 0)) {
			throw new ServletException("Product is not added successfully. Please try again");
		}

		return "adminlogin?action=list";
	}

	/**
	 * This method will return the page to add product by Admin
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		return "newproduct.jsp";
	}

	/**
	 * This method will validate login details and on success will navigate to
	 * Products dash board or will redirect to relogin/home page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String adminLogin(HttpServletRequest request, HttpServletResponse response) {

		String uName = request.getParameter("loginid");
		String pWord = request.getParameter("password");
		HttpSession session = request.getSession();
		if (uName.equals("admin") && pWord.equals("admin")) {
			session.setAttribute("uName", uName);
			return "adminlogin?action=list";
		}
		return "invalidlogin.jsp";
	}

}