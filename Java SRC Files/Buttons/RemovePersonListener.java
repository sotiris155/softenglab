package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.PeopleManager;

public class RemovePersonListener implements MouseListener {
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
		if(!(BoardManager.workersTable.getSelectedRow() == -1)) { //Checks if user has a person selected or not
			PeopleManager.removePerson(String.valueOf(BoardManager.workersTable.getModel().getValueAt(BoardManager.workersTable.getSelectedRow(), 0)));
		}
		else {
			JOptionPane.showMessageDialog(BoardManager.frame, "You have to select a person from the table above.");
		}
	}	
}