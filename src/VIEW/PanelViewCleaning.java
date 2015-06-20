package VIEW;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ACTIONS.ViewPanelActions;
import CONSTANTS.InitialAppPanelsQueryConstants;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelViewCleaning extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector();
	private static JPanel allCleaningPanel = new JPanel();
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	//accesible from methods in any class in the same package and from any subclass anywhere
	protected JLabel menuIndicator;
	
	//default constructor
	public PanelViewCleaning(){
		allCleaningPanel.setLayout(new BorderLayout());
		allCleaningPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(cleaning)", "exportExcelTableCleaning");
		printTableAction = new ViewPanelActions("Print this table(cleaning)", "printTableCleaning");
		closeTableAction = new ViewPanelActions("Close this table(cleaning)", "closeTableCleaning");
				
		sortByFirstNameAction = new ViewPanelActions("... by first name(cleaning)", "sortByFirstNameCleaning");
		sortByLastNameAction = new ViewPanelActions("... by last name(cleaning)", "sortByLastNameCleaning");
		sortByID = new ViewPanelActions("... by ID(cleaning)", "sortByIDCleaning");
			
		menuIndicator= new JLabel("You are viewing the cleaning personnel table :: ");
		menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		super.panelMenuFile.add(new JMenuItem(closeTableAction));
	
		super.panelMenuSort.add(new JMenuItem(sortByFirstNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByLastNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByID));
		
		super.getMenuBar().add(menuIndicator);
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuSort);
		super.getMenuBar().add(panelMenuHelp);
	}
	
		//constructor based on default sql query constants
		public PanelViewCleaning(int defaultSQLQuery){
			this();
			databaseBrowser = new DatabaseBrowse(databaseConnector,defaultSQLQuery);
			allCleaningPanel.add(databaseBrowser.getPane());
		}
		
		//constructor based on default sql query constants
		public PanelViewCleaning(String  userChoice){
			this();
			databaseBrowser = new DatabaseBrowse(databaseConnector,userChoice);
			allCleaningPanel.add(databaseBrowser.getPane());
		}

	public static JPanel getAllCleaningPanel() {
		return allCleaningPanel;
	}

	public static void setAllCleaningPanel(JPanel allCleaningPanel) {
		PanelViewCleaning.allCleaningPanel = allCleaningPanel;
	}
	
	public static void printTableCleaning(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing cleaning personnel to an excel file
	public static void createExcelCleaning(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_CLEANING_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Cleaning Personnel List");
		    resultSetToExcel.generate(new File("D:/cleaning_personnel.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for cleaning table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
