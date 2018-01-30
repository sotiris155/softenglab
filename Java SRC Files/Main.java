package me.Sotiris.CargoShip;

import me.Sotiris.CargoShip.Utils.SQLite;

public class Main {
	public static void main(String[] args) {
		SQLite.connect();
		//Run the login window
		LoginWindow.createWindow();
	}
}