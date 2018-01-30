package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.PeopleManager;

public class CompleteEditPersonListener implements MouseListener {
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
		if(PeopleManager.isForEdit == true) {
			if(!PeopleManager.jpassword.getText().isEmpty()) { // Checks if password field is filled
				PeopleManager.editPerson(PeopleManager.editName, PeopleManager.jpassword.getText(), PeopleManager.jdivision.getSelectedItem().toString());
				PeopleManager.clean();
			}
			else {
				JOptionPane.showMessageDialog(BoardManager.frame, "Error. All fields must be filled.", "Data entry error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}