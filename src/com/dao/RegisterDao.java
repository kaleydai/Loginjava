package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Member;

public class RegisterDao {

	private String dbURL = "jdbc:mysql://localhost:3306/registerdb";
	private String dbUName = "...";
	private String dbPwd = "....";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";

	/**
	 * Method to load MySQL Driver Connector
	 */
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get connected with MySQl by passing url, user name and password of
	 * database
	 */
	public Connection getConnection() {
		Connection con = null;

		try {
			con = DriverManager.getConnection(dbURL, dbUName, dbPwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public boolean validate(Member m) {

		loadDriver(dbDriver);
		Connection con = getConnection();
		boolean status = false;

		String query = "select email, password from registerform where email = ? and password = ?";

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, m.getEmail());
			ps.setString(2, m.getPsswrd());

			ResultSet rs = ps.executeQuery();
			status = rs.next();

		} catch (SQLException e) {
		
			e.printStackTrace();
		}

		return status;

	}

	public String insert(Member member) {
		loadDriver(dbDriver);
		Connection con = getConnection();
		String result = "Data Successfully Entered";

		String qry = "insert into registerform values(?,?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(qry);
			ps.setString(1, member.getFirstName());
			ps.setString(2, member.getLastName());
			ps.setString(3, member.getEmail());
			ps.setString(4, member.getPhone());
			ps.setString(5, member.getPsswrd());

			ps.executeUpdate();

		}

		catch (SQLException e) {
			e.printStackTrace();
			result = "Data Not Entered";
		}

		return result;

	}

}
