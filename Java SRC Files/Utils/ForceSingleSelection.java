package me.Sotiris.CargoShip.Utils;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

public class ForceSingleSelection extends DefaultListSelectionModel {
	private static final long serialVersionUID = 1L;
	
	 public ForceSingleSelection () {
		 setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 }
}