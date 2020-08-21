package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;

public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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

	public boolean addCornonaKitOrderDetails(CoronaKit ckit, List<KitDetail> kitDetails) throws SQLException {

		String insertCoronaKit = "INSERT INTO coronakit (id, personname, personalemail,personmobile,personaddress,orderamount,orderdate,orderconfirmed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		this.connect();
		PreparedStatement pstmt = jdbcConnection.prepareStatement(insertCoronaKit);
		pstmt.setInt(1, ckit.getId());
		pstmt.setString(2, ckit.getPersonName());
		pstmt.setString(3, ckit.getEmail());
		pstmt.setString(4, ckit.getContactNumber());
		pstmt.setString(5, ckit.getDeliveryAddress());
		pstmt.setDouble(6, ckit.getTotalAmount());
		pstmt.setString(7, ckit.getOrderDate());
		pstmt.setBoolean(8, ckit.isOrderFinalized());
		boolean result = pstmt.executeUpdate() > 0;

		for (KitDetail kit : kitDetails) {
			String insertKitData = "INSERT INTO kitdetails (coronakitid, productid,quantity,amount) VALUES (?, ?, ?, ?)";
			pstmt = jdbcConnection.prepareStatement(insertKitData);
			pstmt.setInt(1, ckit.getId());
			pstmt.setInt(2, kit.getProduct().getId());
			pstmt.setInt(3, kit.getQuantity());
			pstmt.setDouble(4, kit.getAmount());
			result = pstmt.executeUpdate() > 0;
		}

		pstmt.close();
		this.disconnect();

		return result;
	}

}