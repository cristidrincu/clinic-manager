package VIEW;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ACTIONS.ViewPanelActions;
import ACTIONS.WelcomePanelsActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;


import VIEW.PanelTextMessages;

public class PanelLatestPacients extends PanelLatestBaseClass {
	
	private static DatabaseConnector databaseConnector = new DatabaseConnector();
	private static DatabaseBrowse browseLatestEntitiesDB;
	private static JPanel panelLatestResultsPacients = new JPanel();
	private int latestPacientsQuery;
	private static String panelName = new String("LatestPacients");
	
	private WelcomePanelsActions closeWelcomePanelPacients;
	private WelcomePanelsActions printWelcomePanelPacients;
	
	//class constructor
	public PanelLatestPacients(int latestPacientsQuery){
		super();
		this.latestPacientsQuery=latestPacientsQuery;
		browseLatestEntitiesDB = new DatabaseBrowse(databaseConnector, this.latestPacientsQuery);
		panelLatestResultsPacients.setLayout(new BorderLayout());
		panelLatestResultsPacients.add(new JLabel("Latest Pacients", JLabel.CENTER), BorderLayout.PAGE_START);
		panelLatestResultsPacients.add(super.menuContainer, BorderLayout.PAGE_START);
		panelLatestResultsPacients.add(browseLatestEntitiesDB.getPane());
		
		printWelcomePanelPacients = new WelcomePanelsActions("Print latest pacients table", "printWelcomePanelsPacients");
		closeWelcomePanelPacients = new WelcomePanelsActions("Close panel", "closeWelcomePanelsPacients");
		
		fileMenuPanel.add(new JMenuItem(printWelcomePanelPacients));
		fileMenuPanel.add(new JMenuItem(closeWelcomePanelPacients));
	}

	public static JPanel getPanelLatestResultsPacients() {
		return panelLatestResultsPacients;
	}

	public void setPanelLatestResultsPacients(
			JPanel panelLatestResultsDatabase) {
		PanelLatestPacients.panelLatestResultsPacients = panelLatestResultsDatabase;
	}

	public static String getPanelName() {
		return panelName;
	}

	public static void setPanelName(String panelName) {
		PanelLatestPacients.panelName = panelName;
	}
	
	//printing table data
	public static void printTablePacients(){
		browseLatestEntitiesDB.printTable();
	}
}


