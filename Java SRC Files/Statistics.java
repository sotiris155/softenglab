package me.Sotiris.CargoShip;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Statistics {
	private static ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
	private static PieChart pieChart;
	public static int parkedPublic = 0;
	public static int ongoingPublic = 0;
	public static int resupplyingPublic = 0;
	
	public static void putStatisticsToPane(JPanel pane) {
		pane.setLayout(new GridLayout(2, 3, 10, 10));
	    JFXPanel dataPaneel = new JFXPanel();
	    
	    details.addAll(new PieChart.Data("Parked (" + parkedPublic + ")", parkedPublic),
	            new PieChart.Data("Ongoing (" + ongoingPublic + ")", ongoingPublic),
	            new PieChart.Data("Resupplying (" + resupplyingPublic + ")", resupplyingPublic)
	            );
	    pieChart = new PieChart();
	    pieChart.setData(details);
	    pieChart.setTitle("Ships");
	    pieChart.setLegendSide(Side.BOTTOM);
	    pieChart.setLabelsVisible(true);
	    Scene scene = new Scene(pieChart);
	    dataPaneel.setScene(scene);
	    pane.add(dataPaneel);
	    pane.setPreferredSize(new Dimension(800, 550));
	}
	public static void refresh() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				details.set(0, new PieChart.Data("Parked (" + parkedPublic + ")", parkedPublic));
				details.set(1, new PieChart.Data("Ongoing (" + ongoingPublic + ")", ongoingPublic));
				details.set(2, new PieChart.Data("Resupplying (" + resupplyingPublic + ")", resupplyingPublic));
			}
		});
	}
}