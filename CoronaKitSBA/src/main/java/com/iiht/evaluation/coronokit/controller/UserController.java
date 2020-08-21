package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;
import com.iiht.evaluation.coronokit.model.UserDetails;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;
	private HttpSession session;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;
			case "ordersummary":
				viewName = showOrderSummary(request, response);
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

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		return "ordersummary.jsp";
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, SQLException {

		List<KitDetail> kitDetails = (List<KitDetail>) session.getAttribute("CartData");
		UserDetails user = (UserDetails) session.getAttribute("UserDetails");
		user.setDeliveryAddress(request.getParameter("AddressDetails"));
		
		CoronaKit cKit = new CoronaKit((int) session.getAttribute("CoronaKitId"), user.getName(), user.getEmailId(),
				user.getContactNumber(), (double) session.getAttribute("TotalOrderAmount"), user.getDeliveryAddress(),
				LocalDateTime.now().toString(), true);

		boolean result = this.kitDAO.addCornonaKitOrderDetails(cKit, kitDetails);

		if (!result) {
			throw new ServletException("Order is not added successfully. Please try again");
		}

		OrderSummary orderSummary = new OrderSummary(cKit, kitDetails);
		
		session.setAttribute("orderSummary", orderSummary);
		
		session.removeAttribute("CartData");
		session.removeAttribute("UserDetails");
		session.removeAttribute("CoronaKitId");
		session.removeAttribute("TotalOrderAmount");
		session.removeAttribute("ProductDetails");
		return "user?action=ordersummary";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		return "showkit.jsp";
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		List<KitDetail> cart = (List<KitDetail>) session.getAttribute("CartData");
		int productId = Integer.parseInt(request.getParameter("productId"));
		int index = isExistingKitProduct(productId, cart);
		int parmQuantity = Integer.parseInt(request.getParameter("Quantity"));
		if (parmQuantity == 1) {
			int quantity = cart.get(index).getQuantity() - 1;
			if (quantity == 0) {
				cart.remove(index);
			} else {
				cart.get(index).setQuantity(quantity);
				cart.get(index).setAmount(quantity * cart.get(index).getProduct().getCost());
			}
		} else if (parmQuantity == 0) {
			cart.remove(index);
		}
		session.setAttribute("CartData", cart);
		if (cart.size() == 0) {
			session.removeAttribute("CartData");
			return "user?action=showproducts";
		}
		return "user?action=showkit";
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		KitDetail kit;
		session = request.getSession();
		ProductMaster product = this.productMasterDao.getProductData(request.getParameter("ProductId"));

		if (session.getAttribute("CartData") == null) {
			Random r = new Random();
			int ckitId = (int) (1+(r.nextDouble() *(Integer.MAX_VALUE-1)));
			List<KitDetail> cart = new ArrayList<KitDetail>();
			kit = new KitDetail(1, ckitId, product, 1, product.getCost());
			cart.add(kit);
			session.setAttribute("CartData", cart);
			session.setAttribute("CoronaKitId", kit.getCoronaKitId());
		} else {
			List<KitDetail> cart = (List<KitDetail>) session.getAttribute("CartData");
			int index = isExistingKitProduct(product.getId(), cart);
			if (index == -1) {
				kit = new KitDetail(cart.get(cart.size() - 1).getId() + 1, (int) session.getAttribute("CoronaKitId"),
						product, 1, product.getCost());
				cart.add(kit);
			} else {
				int quantity = cart.get(index).getQuantity() + 1;
				cart.get(index).setQuantity(quantity);
				cart.get(index).setAmount(quantity * product.getCost());
			}
			session.setAttribute("CartData", cart);
		}

		return "user?action=showkit";
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		List<ProductMaster> products = this.productMasterDao.getProductRecords();
		request.setAttribute("products", products);
		session = request.getSession();
		session.setAttribute("ProductDetails", products);
		return "showproductstoadd.jsp";
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		UserDetails user = new UserDetails(request.getParameter("userName"), request.getParameter("userMailId"),
				request.getParameter("UserMobileNumber"), null);
		session.setAttribute("UserDetails", user);
		return "user?action=showproducts";
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		return "newuser.jsp";
	}

	private int isExistingKitProduct(int productId, List<KitDetail> cart) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProduct().getId() == productId) {
				return i;
			}
		}
		return -1;
	}
}