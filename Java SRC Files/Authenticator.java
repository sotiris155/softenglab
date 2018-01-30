package me.Sotiris.CargoShip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.Sotiris.CargoShip.Utils.SQLite;

public class Authenticator {
	public static boolean isRight(String username, char[] pass, String opt) {
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			ResultSet res = stmt.executeQuery("SELECT * FROM login WHERE username = '" + username + "';");
			while(res.next()) {
				String user = res.getString("username");
				if(user == null) {
					return false;
				}
				else {
					String correctpass = res.getString("password");
					String correctopt = res.getString("division");
					if(String.valueOf(pass).equals(correctpass) && opt.equals(correctopt)) {
						return true;
					}
					else {
						return false;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}