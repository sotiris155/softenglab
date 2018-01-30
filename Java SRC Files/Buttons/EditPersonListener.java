package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.PeopleManager;

public class EditPersonListener implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(!(BoardManager.workersTable.getSelectedRow() == -1)) { //Checks if user has a ship selected or not
			PeopleManager.isForEdit = true;
			PeopleManager.editName = BoardManager.workersTable.getValueAt(BoardManager.workersTable.getSelectedRow(), 0).toString();
			PeopleManager.jname.setText(BoardManager.workersTable.getValueAt(BoardManager.workersTable.getSelectedRow(), 0).toString());
			PeopleManager.jname.setEditable(false);
			PeopleManager.jpassword.setText(BoardManager.workersTable.getValueAt(BoardManager.workersTable.getSelectedRow(), 1).toString());
			PeopleManager.setupEntryWindow();
			PeopleManager.jdivision.setSelectedItem(BoardManager.workersTable.getValueAt(BoardManager.workersTable.getSelectedRow(), 2));
		}
		else {
			JOptionPane.showMessageDialog(BoardManager.frame, "You have to select a ship from the table above.");
		}
	}
}