package me.Sotiris.CargoShip;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.Sotiris.CargoShip.Buttons.CompleteEditPersonListener;
import me.Sotiris.CargoShip.Buttons.EntryPersonListener;
import me.Sotiris.CargoShip.Utils.SQLite;

public class PeopleManager {
	public static JFrame frame = new JFrame();
	public static JPanel pane = new JPanel();
	public static JTextField jname = new JTextField(24);
	public static JTextField jpassword = new JTextField(24);
	@SuppressWarnings("rawtypes") public static JComboBox jdivision = new JComboBox();
	public static JButton entry = new JButton("Entry");
	public static String[] divisions = {"Admin", "Worker"};
	
	public static boolean isAlreadyOpen = false;
	public static boolean isRegistered = false;
	public static boolean isForEdit = false;
	public static String editName = null;
	
	@SuppressWarnings("unchecked")
	public static void setupEntryWindow() {
		if(!isAlreadyOpen) {
			isAlreadyOpen = true;
			if(isForEdit == true) {
				frame.setTitle("Edit");
			} else {
				frame.setTitle("Entry");
			}
			frame.setSize(300, 250);
			frame.setResizable(false);
			frame.setIconImage(new ImageIcon(new PeopleManager().getClass().getResource("anchor.png")).getImage());
			pane.add(new JLabel("Name:"));
			pane.add(jname);
			pane.add(new JLabel("Password"));
			pane.add(jpassword);
			pane.add(new JLabel("Divison:"));
			for(int i = 0; i < divisions.length; i++) {
				jdivision.addItem(divisions[i]);
			}
			pane.add(jdivision);
			pane.add(entry);
			if(!isRegistered) {
				isRegistered = true;
				entry.addMouseListener(new EntryPersonListener());
				entry.addMouseListener(new CompleteEditPersonListener());
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
	public static void addPerson(String name, String password, String division) {
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			ResultSet res = stmt.executeQuery("SELECT * FROM login;");
			while(res.next()) {
				if(res.getString("username").equalsIgnoreCase(name)) {
					JOptionPane.showMessageDialog(BoardManager.frame, "Error. Person with the same name already exists.", "Data entry error", JOptionPane.ERROR_MESSAGE);
				}
			}
			stmt.executeUpdate("INSERT INTO login (username, password, division) VALUES ('" + name + "', '" + password + "', '" + division + "');");
			BoardManager.updateWorkersBoard();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void removePerson(String name) {
		Object[] options = {"Yes", "Cancel"};
		int n = JOptionPane.showOptionDialog(BoardManager.frame,
			    "Are you sure you want to remove the person with name: " + name + "?",
			    "Confirmation",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,        // Do not use a custom Icon
			    options,     // The titles of buttons
			    options[0]); // Default button title
		if(n == JOptionPane.YES_OPTION) {
			try (Connection conn = DriverManager.getConnection(SQLite.url); 
					Statement stmt = conn.createStatement()) {
				stmt.executeUpdate("DELETE FROM login WHERE username = '" + name + "';");
				BoardManager.updateWorkersBoard();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			return;
		}
	}
	public static void editPerson(String name, String newPassword, String newDivison) {
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("UPDATE login SET password='" + newPassword + "', division='" + newDivison + "' WHERE username='" + name + "';");
			BoardManager.updateWorkersBoard();
			JOptionPane.showMessageDialog(BoardManager.frame, "Data of person with name " + name + " has been successfully edited.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void clean() {
		frame.setVisible(false);
		pane.removeAll();
		isAlreadyOpen = false;
		editName = null;
		isForEdit = false;
		jdivision.removeAllItems();
		jname.setText("");
		jpassword.setText("");
		jname.setEditable(true);
	}
}