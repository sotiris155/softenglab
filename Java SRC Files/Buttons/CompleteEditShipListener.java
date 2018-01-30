package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.ShipManager;

public class CompleteEditShipListener implements MouseListener {
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
		if(ShipManager.isForEdit == true) {
			if(!ShipManager.jname.getText().isEmpty() 
					&& !ShipManager.jdeparture.getText().isEmpty() 
					&& !ShipManager.jdestination.getText().isEmpty() 
					&& !ShipManager.jresupply.getText().isEmpty()) { // Checks if all fields are filled
				ShipManager.editShip(ShipManager.editID, ShipManager.jname.getText(), ShipManager.jdeparture.getText(), ShipManager.jdestination.getText(), ShipManager.jresupply.getText(), ShipManager.jstatus.getSelectedItem().toString());
				ShipManager.clean();
			}
			else {
				JOptionPane.showMessageDialog(BoardManager.frame, "Error. All fields must be filled.", "Data entry error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}