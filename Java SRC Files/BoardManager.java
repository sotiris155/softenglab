package me.Sotiris.CargoShip;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import me.Sotiris.CargoShip.Buttons.AddShipListener;
import me.Sotiris.CargoShip.Buttons.EditPersonListener;
import me.Sotiris.CargoShip.Buttons.EditShipListener;
import me.Sotiris.CargoShip.Buttons.RemovePersonListener;
import me.Sotiris.CargoShip.Buttons.AddPersonListener;
import me.Sotiris.CargoShip.Buttons.RemoveShipListener;
import me.Sotiris.CargoShip.Utils.ForceSingleSelection;
import me.Sotiris.CargoShip.Utils.SQLite;

public class BoardManager {
	public static JFrame frame = new JFrame();
	public static JPanel pane = new JPanel();
	
	public static JTabbedPane tabs = new JTabbedPane();
	public static JPanel workers = new JPanel();
	public static JPanel cargoships = new JPanel();
	public static JButton addship = new JButton("Add Ship");
	public static JButton removeship = new JButton("Remove selected ship");
	public static JButton editship = new JButton("Edit selected ship");
	public static JButton addperson = new JButton("Add person");
	public static JButton removeperson = new JButton("Remove selected person");
	public static JButton editperson = new JButton("Edit selected person");
	public static JPanel stats = new JPanel();
	
	public static Object[] workersCols = {"Name", "Password", "Division"};
	public static Object[] cargoshipsCols = {"ID", "Name", "Departure", "Destination", "Resupply", "Status"};
	
	public static JTable workersTable, cargoshipsTable;
	
	private static boolean isCargoButtonsRegistered = false;
	private static boolean isWorkersButtonsRegistered = false;
	public static String userDivision = null;
	
	public static int parkedCounter = 0;
	public static int resupplyingCounter = 0;
	public static int ongoingCounter = 0;
	
	
	public static void setupBoard(String division) {
		//--------------------------------Visual Stuff--------------------------------//
		userDivision = "Worker";
		frame.setTitle("Cargo Ship");
		frame.setSize(900, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(new BoardManager().getClass().getResource("anchor.png")).getImage());
		//--------------------------------Choose which board will every division see--------------------------------//
		if(division.equalsIgnoreCase("Admin")) {
			userDivision = "Admin";
			setupWorkersBoard();
		}
		setupCargoShipsBoard();
		//--------------------------------More Visual Stuff--------------------------------//
		tabs.setFocusable(false);
		pane.add(tabs);
		frame.add(pane);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setVisible(true);
		if(division.equalsIgnoreCase("Admin")) {
			tabs.addTab("Workers", workers);
		}
		tabs.addTab("Cargo Ships", cargoships);
		if(division.equalsIgnoreCase("Admin")) {
			setupStatisticsBoard();
			tabs.addTab("Statistics", stats);
		}
	}
	@SuppressWarnings("serial")
	public static void setupWorkersBoard() {
		//------------------------------------Table Stuff----------------------------------//
		workersTable = new JTable(fetchWorkersData(), workersCols) { public boolean isCellEditable(int row, int column) { return false; }};
		JScrollPane scrollPane = new JScrollPane(workersTable);
		workers.setPreferredSize(new Dimension(850, 600));
		scrollPane.setPreferredSize(new Dimension(850, 550));
		workersTable.setDragEnabled(false);
		workersTable.getTableHeader().setReorderingAllowed(false);
		workersTable.setSelectionModel(new ForceSingleSelection());
		workers.add(scrollPane);
		//------------------------------------Button Stuff------------------------------------//
		workers.add(addperson);
		addperson.setFocusable(false);
		workers.add(removeperson);
		removeperson.setFocusable(false);
		workers.add(editperson);
		editperson.setFocusable(false);
		if(!isWorkersButtonsRegistered) {
			isWorkersButtonsRegistered = true;
			addperson.addMouseListener(new AddPersonListener());
			removeperson.addMouseListener(new RemovePersonListener());
			editperson.addMouseListener(new EditPersonListener());
		}
		//------------------------------------Tab Stuff------------------------------------//
		tabs.revalidate();
	}
	@SuppressWarnings("serial")
	public static void setupCargoShipsBoard() {
		//------------------------------------Table Stuff----------------------------------//
		cargoshipsTable = new JTable(fetchCargoShipsData(), cargoshipsCols) { public boolean isCellEditable(int row, int column) { return false; }};
		JScrollPane scrollPane = new JScrollPane(cargoshipsTable);
		cargoships.setPreferredSize(new Dimension(850, 600));
		scrollPane.setPreferredSize(new Dimension(850, 550));
		cargoshipsTable.setDragEnabled(false);
		cargoshipsTable.getTableHeader().setReorderingAllowed(false);
		cargoshipsTable.setSelectionModel(new ForceSingleSelection());
		cargoships.add(scrollPane);
		//------------------------------------Button Stuff------------------------------------//
		addship.setFocusable(false);
		cargoships.add(addship);
		removeship.setFocusable(false);
		cargoships.add(removeship);
		editship.setFocusable(false);
		cargoships.add(editship);
		if(!isCargoButtonsRegistered) {
			isCargoButtonsRegistered = true;
			addship.addMouseListener(new AddShipListener());
			removeship.addMouseListener(new RemoveShipListener());
			editship.addMouseListener(new EditShipListener());
		}
		//------------------------------------Tab Stuff------------------------------------//
		tabs.revalidate();
	}
	public static void setupStatisticsBoard() {
		if(userDivision.equals("Admin")) {
			Statistics.parkedPublic = parkedCounter;
			Statistics.ongoingPublic = ongoingCounter;
			Statistics.resupplyingPublic = resupplyingCounter;
			Statistics.putStatisticsToPane(stats);
		}
	}
	public static void updateWorkersBoard() { //Refreshes the workers board after data entry/removal
		workers.removeAll();
		setupWorkersBoard();
		tabs.setSelectedIndex(0);
	}
	public static void updateCargoShipsBoard() { //Refreshes the cargo ship board after data entry/removal and the Statistics
		cargoships.removeAll();
		setupCargoShipsBoard();
		if(userDivision.equals("Admin")) {
			tabs.setSelectedIndex(1);
			Statistics.parkedPublic = parkedCounter;
			Statistics.ongoingPublic = ongoingCounter;
			Statistics.resupplyingPublic = resupplyingCounter;
			Statistics.refresh();
		}
		else { 
			tabs.setSelectedIndex(0);
		}
	}
	public static Object[][] fetchWorkersData() { //Fetches worker data from database and returns it to table form
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			ResultSet res = stmt.executeQuery("SELECT * FROM login;");
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> passwords = new ArrayList<String>();
			ArrayList<String> divisions = new ArrayList<String>();
			int i = 0;
			while(res.next()) {
				names.add(res.getString("username"));
				passwords.add(res.getString("password"));
				divisions.add(res.getString("division"));
				i++;
			}
			String[][] finalData = new String[i][3];
			for(int e = 0; e < i; e++) {
				finalData[e][0] = names.get(e);
				finalData[e][1] = passwords.get(e);
				finalData[e][2] = divisions.get(e);
			}
			return finalData;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object[][] fetchCargoShipsData() { //Fetches cargo ship data from database and returns it to table form
		parkedCounter = 0;
		resupplyingCounter = 0;
		ongoingCounter = 0;
		try (Connection conn = DriverManager.getConnection(SQLite.url); 
				Statement stmt = conn.createStatement()) {
			ResultSet res = stmt.executeQuery("SELECT * FROM Ships;");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> destinations = new ArrayList<String>();
			ArrayList<String> departures = new ArrayList<String>();
			ArrayList<String> resupplies = new ArrayList<String>();
			ArrayList<String> statuses = new ArrayList<String>();
			int i = 0;
			while(res.next()) {
				ids.add(res.getInt("ID"));
				names.add(res.getString("Name"));
				departures.add(res.getString("Departure"));
				destinations.add(res.getString("Destination"));
				resupplies.add(res.getString("Resupply"));
				statuses.add(res.getString("Status"));
				i++;
			}
			Object[][] finalData = new Object[i][6];
			for(int e = 0; e < i; e++) {
				finalData[e][0] = ids.get(e);
				finalData[e][1] = names.get(e);
				finalData[e][2] = departures.get(e);
				finalData[e][3] = destinations.get(e);
				finalData[e][4] = resupplies.get(e);
				finalData[e][5] = statuses.get(e);
				if(statuses.get(e).equals("Ongoing")) {
					ongoingCounter++;
				}
				else if(statuses.get(e).equals("Resupplying")) {
					resupplyingCounter++;
				}
				else if(statuses.get(e).equals("Parked")) {
					parkedCounter++;
				}
			}
			return finalData;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
	