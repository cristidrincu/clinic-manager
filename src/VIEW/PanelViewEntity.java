package VIEW;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ACTIONS.ViewPanelActions;

public class PanelViewEntity {
	private static JPanel databaseResultsContainer = new JPanel();

	private JMenuBar menuBar;
	protected JMenu panelMenuFile;
	protected JMenu panelMenuSort;
	protected JMenu panelMenuHelp;
	
	//main actions for the table
	ViewPanelActions exportTableAction;
	ViewPanelActions printTableAction;
	ViewPanelActions closeTableAction;
			
	//sort actions for table
	ViewPanelActions sortByFirstNameAction;
	ViewPanelActions sortByLastNameAction;
	ViewPanelActions sortByDiagnostic;
	ViewPanelActions sortByID;
	
	//tracker var
	private static int isObject=0;//keeps track of class instances/objects
	protected String tableType;
	
	public PanelViewEntity(){
		databaseResultsContainer.setLayout(new BorderLayout());
		databaseResultsContainer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		//panel menu build
		menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		panelMenuFile = new JMenu("File");
		panelMenuSort = new JMenu("Sort"); 
		panelMenuHelp = new JMenu("Help");
	}

	public static JPanel getViewPanel() {
		return databaseResultsContainer;
	}
	
	public static void hideViewOptionsPanel(){
		databaseResultsContainer.setVisible(false);
	}
	
	public static void showViewOptionsPanel(){
		databaseResultsContainer.setVisible(true);
	}

	public static int getIsObject() {
		return isObject;
	}

	public static void setIsObject(int isObject) {
		PanelViewEntity.isObject = isObject;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
}
