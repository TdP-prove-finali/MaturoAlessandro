package it.polito.tdp.FootballManager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	public static Connection getConnection() {
		
		String url = "jdbc:mysql://localhost/footballmanager2020?user=root&password=root";
		
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

}
