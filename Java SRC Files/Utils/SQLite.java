package me.Sotiris.CargoShip.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {
	public static boolean isConnected = false;
	public static String url = "jdbc:sqlite:CargoShip.sqlite";
	
	static Connection conn = null;
	
	public static void connect() {
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("SQLite connection has been established.");
			isConnected = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}