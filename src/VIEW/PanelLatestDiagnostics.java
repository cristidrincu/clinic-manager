package VIEW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.print.PrinterException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ACTIONS.WelcomePanelsActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;

public class PanelLatestDiagnostics extends PanelLatestBaseClass {
	
	private static DatabaseConnector databaseConnector=new DatabaseConnector();
	private static DatabaseBrowse browseLatestEntitiesDB;
	private static JPanel panelLatestResultsDiagnostics = new JPanel();
	private int latestDiagnosticsQuery;
	private static String panelName = new String("LatestDiagnostics");
	
	private WelcomePanelsActions closeWelcomePanelDiagnostics;
	private WelcomePanelsActions printWelcomePanelDiagnostics;
	
	public PanelLatestDiagnostics(int latestDiagnosticsQuery){
		super();
		this.latestDiagnosticsQuery = latestDiagnosticsQuery;
		browseLatestEntitiesDB = new DatabaseBrowse(databaseConnector, this.latestDiagnosticsQuery);
		panelLatestResultsDiagnostics.setLayout(new BorderLayout());
		panelLatestResultsDiagnostics.add(new JLabel("Latest Diagnostics", JLabel.CENTER), BorderLayout.PAGE_START);
		panelLatestResultsDiagnostics.add(super.menuContainer, BorderLayout.PAGE_START);
		panelLatestResultsDiagnostics.add(browseLatestEntitiesDB.getPane());
		
		printWelcomePanelDiagnostics = new WelcomePanelsActions("Print latest pacients table", "printWelcomePanelsDiagnostics");
		closeWelcomePanelDiagnostics = new WelcomePanelsActions("Close panel", "closeWelcomePanelsDiagnostics");
		
		fileMenuPanel.add(new JMenuItem(printWelcomePanelDiagnostics));
		fileMenuPanel.add(new JMenuItem(closeWelcomePanelDiagnostics));
	}

	public static JPanel getPanelLatestResultsDiagnostics() {
		return panelLatestResultsDiagnostics;
	}

	public static void setPanelLatestResultsDiagnostics(
			JPanel panelLatestResultsDatabase) {
		PanelLatestDiagnostics.panelLatestResultsDiagnostics = panelLatestResultsDatabase;
	}

	public static String getPanelName() {
		return panelName;
	}

	public static void setPanelName(String panelName) {
		PanelLatestDiagnostics.panelName = panelName;
	}
	
	//printing the table containing database data
	public static void printTableDiagnostics(){
		browseLatestEntitiesDB.printTable();
	}
	
	
}
