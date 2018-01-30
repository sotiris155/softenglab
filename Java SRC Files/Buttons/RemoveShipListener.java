package me.Sotiris.CargoShip.Buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import me.Sotiris.CargoShip.BoardManager;
import me.Sotiris.CargoShip.ShipManager;

public class RemoveShipListener implements MouseListener {
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
			ShipManager.removeShip(Integer.valueOf(BoardManager.cargoshipsTable.getModel().getValueAt(BoardManager.cargoshipsTable.getSelectedRow(), 0).toString()));
		}
		else {
			JOptionPane.showMessageDialog(BoardManager.frame, "You have to select a ship from the table above.");
		}
	}
}
