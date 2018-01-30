package me.Sotiris.CargoShip;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.Sotiris.CargoShip.Buttons.CompleteEditShipListener;
import me.Sotiris.CargoShip.Buttons.EntryShipListener;
import me.Sotiris.CargoShip.Utils.SQLite;

public class ShipManager {
	public static JFrame frame = new JFrame();
	public static JPanel pane = new JPanel();
	public static JTextField jname = new JTextField(24);
	public static JTextField jdeparture = new JTextField(24);
	public static JTextField jdestination = new JTextField(24);
	public static JTextField jresupply = new JTextField(24);
	@SuppressWarnings("rawtypes")
	public static JComboBox jstatus = new JComboBox();
	public static String[] statuses = {"Parked", "Ongoing", "Resupplying"};
	public static JButton entry = new JButton("Entry");
	
	public static boolean isAlreadyOpen = false;
	public static boolean isRegistered = false;
	public static boolean isForEdit = false;
	public static int editID = 0;
	
	@SuppressWarnings("unchecked")
	public static void showEntryWindow() {
		if(isAlreadyOpen == false) {
			isAlreadyOpen = true;
			if(isForEdit == true) {
				frame.setTitle("Edit");
			} else {
				frame.setTitle("Entry");
			}
			frame.setSize(300, 350);
			frame.setResizable(false);
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/anchor.png"));
			frame.setIconImage(new ImageIcon(new ShipManager().getClass().getResource("anchor.png")).getImage());
			pane.add(new JLabel("Name:"));
			pane.add(jname);
			pane.add(new JLabel("Departure location:"));
			pane.add(jdeparture);
			pane.add(new JLabel("Destination:"));
			pane.add(jdestination);
			pane.add(new JLabel("Resupply position:"));
			pane.add(jresupply);
			pane.add(new JLabel("Status:"));
			for(int i = 0; i < statuses.length; i++) {
				jstatus.addItem(statuses[i]);
			}
			pane.add(jstatus);
			pane.add(entry);
			if(!isRegistered) {
				isRegistered = true;
				entry.addMouseListener(new EntryShipListener());
				entry.addMouseListener(new CompleteEditShipListener());
			}
			frame.add(pane);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
			frame.setLocation(x, y);
			frame.setVisible(true);
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent windowEvent) {
			    	clean();
			    }
			});
		}
	}
	public static void addShip(String name, String departure, String destination, String resupply, String status) {
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			ResultSet res = stmt.executeQuery("SELECT * FROM Ships;");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while(res.next()) {
				ids.add(res.getInt("ID"));
			}
			//Finding a new valid id
			int NEWID = 0;
			for(int e = 0; e < Integer.MAX_VALUE; e++) {
				if(!ids.contains(e)) {
					NEWID = e;
					break;
				}
			}
			stmt.executeUpdate("INSERT INTO Ships (ID, Name, Departure, Destination, Resupply, Status) VALUES (" + NEWID + ", '" + name + "', '" + departure + "', '" + destination + "', '" + resupply + "', '" + status + "');");
			BoardManager.updateCargoShipsBoard();
			JOptionPane.showMessageDialog(BoardManager.frame, "Ship added. ID: " + NEWID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void removeShip(int id) {
		Object[] options = {"Yes", "Cancel"};
		int n = JOptionPane.showOptionDialog(BoardManager.frame,
			    "Are you sure you want to remove the ship with ID: " + id + "?",
			    "Confirmation",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,        // Do not use a custom Icon
			    options,     // The titles of buttons
			    options[0]); // Default button title
		if(n == JOptionPane.YES_OPTION) {
			try (Connection conn = DriverManager.getConnection(SQLite.url); 
					Statement stmt = conn.createStatement()) {
				stmt.executeUpdate("DELETE FROM Ships WHERE ID = '" + id + "';");
				BoardManager.updateCargoShipsBoard();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			return;
		}
	}
	public static void editShip(int id, String newName, String newDeparture, String newDestination, String newResupply, String newStatus) {
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("UPDATE Ships SET Name='" + newName + "', Departure='" + newDeparture + "', Destination='" + newDestination + "', Resupply='" + newResupply + "', Status='" + newStatus + "' WHERE ID='" + id + "';");
			BoardManager.updateCargoShipsBoard();
			JOptionPane.showMessageDialog(BoardManager.frame, "Ship with id " + id + " has been successfully edited.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	public static void clean() {
		pane.removeAll();
		frame.setVisible(false);
    	isAlreadyOpen = false;
    	isForEdit = false;
    	editID = 0;
		jname.setText("");
		jdeparture.setText("");
		jdestination.setText("");
		jresupply.setText("");
		jstatus.removeAllItems();
	}
}