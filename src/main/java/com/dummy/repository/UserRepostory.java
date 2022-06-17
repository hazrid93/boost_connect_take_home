package com.dummy.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dummy.model.User;
import com.dummy.model.UserDetails;

public class UserRepostory {

	public static String INSERT_QUERY = "INSERT INTO user (userid,firstname,lastname,title,picture) "
			+ "VALUES ('%uid%','%fn%','%ln%','%title%','%img%');";

	public static String UPDATE_QUERY = "update user set  firstname = '%fn%' , lastname = '%ln%' , "
			+ "title='%title%' , picture='%img%' , updatedOn = '%date%' where userid ='%uid%';";

	private static volatile UserRepostory userRepostory = null;
	private static Object lock = new Object();
	private Connection conn = null;
	private String pattern = "yyyy-MM-dd";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	public static UserRepostory getInstance() {

		if (userRepostory == null) {

			synchronized (lock) {
				if (userRepostory == null) {
					userRepostory = new UserRepostory();
				}
			}
		}
		return userRepostory;

	}

	public void insertUser(User user) {
		Statement statement = null;
		try {

			conn = getDBConnection();
			statement = conn.createStatement();
			for (UserDetails userDetails : user.data) {
				userDetails.picture.replace(".jpg", "");
				String sql = INSERT_QUERY.replace("%uid%", userDetails.id)
						.replace("%fn%", userDetails.firstName).replace("%ln%", userDetails.lastName)
						.replace("%title%", userDetails.title).replace("%img%", userDetails.picture.replace("https://","").replace(".jpg", ""));
				System.out.println(sql);
				statement.executeUpdate(sql);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("User inserted into databse successfully");
	}

	public void updateUser(User user) {
		Statement statement = null;
		try {

			conn = getDBConnection();
			statement = conn.createStatement();

			String modifiedDate = simpleDateFormat.format(new Date());

			for (UserDetails userDetails : user.data) {

				String sql = UPDATE_QUERY.replace("%uid%", userDetails.id)
						.replace("%fn%", userDetails.firstName).replace("%ln%", userDetails.lastName)
						.replace("%title%", userDetails.title).replace("%img%", userDetails.picture.replace("https://","").replace(".jpg", ""))
						.replace("%date%", modifiedDate);
				statement.executeUpdate(sql);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("User updated successfully");
	}

	private Connection getDBConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dummyuser?user=root&password=");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return connection;
	}

}