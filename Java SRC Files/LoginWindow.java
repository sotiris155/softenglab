package me.Sotiris.CargoShip;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.Sotiris.CargoShip.Utils.SQLite;

public class LoginWindow implements ActionListener {
	static JFrame frame = new JFrame();
	public static JPanel pane = new JPanel();
	public static JLabel dbstatus = new JLabel();
	public static JTextField jusername = new JTextField(22);
	public static JPasswordField jpassword = new JPasswordField(22);
	public static String[] options = {"Admin", "Worker"};
	public static JButton connect = new JButton("Connect");
	@SuppressWarnings("rawtypes") public static JComboBox joptions = new JComboBox();
	

	@SuppressWarnings("unchecked")
	public static void createWindow() {
		//------------------------------Display model------------------------------//
		frame.setTitle("Login Window");
		frame.setPreferredSize(new Dimension(300, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(new LoginWindow().getClass().getResource("anchor.png")).getImage());
		//------------------------------User and Pass------------------------------//
		pane.add(new JLabel("Username:"));
		pane.add(jusername);
		pane.add(new JLabel("Password:"));
		pane.add(jpassword);
		//------------------------------Options------------------------------//
		for(int i = 0; i < options.length; i++) {
			joptions.addItem(options[i]);
		}
		pane.add(joptions);
		//------------------------------Connect Button------------------------------//
		pane.add(connect);
		connect.setFocusable(false);
		connect.addActionListener(new LoginWindow());
		connect.setActionCommand("Connect");
		//------------------------------Database status------------------------------//
		if(SQLite.isConnected == true) {
			dbstatus.setText("Database status: connected");
		}
		else { 
			dbstatus.setText("Database status: not connected");
		}
		pane.add(dbstatus);
		//------------------------------Other modeling stuff------------------------------//
		pane.setBorder(new EmptyBorder(0, 5, 5, 5));
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();
		if(cmd.equals("Connect")) {
			if(!Authenticator.isRight(jusername.getText(), jpassword.getPassword(), joptions.getSelectedItem().toString())) {
				JOptionPane.showMessageDialog(frame, "Wrong credentials", "Connection error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				frame.setVisible(false);
				BoardManager.setupBoard(joptions.getSelectedItem().toString());
			}
		}
	}
}