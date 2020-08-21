package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.iiht.evaluation.coronokit.model.ProductMaster;



public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	

	public List<ProductMaster> getProductRecords() throws ClassNotFoundException, SQLException {
		String sql = "select * from products";
		this.connect();
		Statement st = jdbcConnection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		List<ProductMaster> products = new ArrayList<ProductMaster>();
		while (rs.next()) {
			ProductMaster product = new ProductMaster(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
			products.add(product);
		}
		rs.close();
		st.close();
		this.disconnect();
		return products;
	}

	public int addNewProduct(ProductMaster product) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO products (product_name, product_desc, product_cost) VALUES (?, ?, ?)";
		this.connect();
		PreparedStatement pstmt = jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, product.getProductName());
		pstmt.setString(2, product.getProductDescription());
		pstmt.setDouble(3, product.getCost());
		int rows = pstmt.executeUpdate();
		pstmt.close();
		this.disconnect();
		return rows;
	}

	public ProductMaster getProductData(String id) throws ClassNotFoundException, SQLException {
		String sql = "select * from products where product_id='" + id + "'";
		this.connect();
		Statement st = jdbcConnection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ProductMaster product;
		rs.next();
		product = new ProductMaster(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
		rs.close();
		st.close();
		this.disconnect();
		return product;
	}

	public int updateProduct(ProductMaster product) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE products SET product_desc = ?, product_cost = ? WHERE product_id = ?";
		this.connect();
		PreparedStatement pstmt = jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, product.getProductDescription());
		pstmt.setDouble(2, product.getCost());
		pstmt.setInt(3, product.getId());
		int rows = pstmt.executeUpdate();
		pstmt.close();
		this.disconnect();
		return rows;
	}

	public int deleteProduct(int id) throws ClassNotFoundException, SQLException {
		String sql = "delete from products WHERE product_id = ?";
		this.connect();
		PreparedStatement pstmt = jdbcConnection.prepareStatement(sql);
		pstmt.setInt(1, id);
		int rows = pstmt.executeUpdate();
		pstmt.close();
		this.disconnect();
		return rows;
	}

	// add DAO methods as per requirements
}