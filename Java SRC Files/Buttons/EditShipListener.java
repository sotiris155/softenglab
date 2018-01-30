package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.ShipManager;

public class EditShipListener implements MouseListener {
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
		if(!(BoardManager.cargoshipsTable.getSelectedRow() == -1)) { //Checks if user has a ship selected or not
			ShipManager.isForEdit = true;
			ShipManager.editID = Integer.valueOf(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 0).toString());
			ShipManager.jname.setText(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 1).toString());
			ShipManager.jdeparture.setText(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 2).toString());
			ShipManager.jdestination.setText(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 3).toString());
			ShipManager.jresupply.setText(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 4).toString());
			ShipManager.showEntryWindow();
			ShipManager.jstatus.setSelectedItem(BoardManager.cargoshipsTable.getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 5));
		}
		else {
			JOptionPane.showMessageDialog(BoardManager.frame, "You have to select a ship from the table above.");
		}
	}
}