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

public class PanelViewPrescriptions extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector();
	private static JPanel allPrescriptionsPanel = new JPanel();
	
	//accesible from methods in any class in the same package and from any subclass anywhere
	protected JLabel menuIndicator;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	//default constructor
	public PanelViewPrescriptions(){
		allPrescriptionsPanel.setLayout(new BorderLayout());
		allPrescriptionsPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(prescriptions)", "exportExcelTablePrescriptions");
		printTableAction = new ViewPanelActions("Print this table(prescriptions)", "printTablePrescriptions");
		//closeTableAction = new ViewPanelActions("Close this table(prescriptions)", "closeTablePrescriptions");
				
		sortByFirstNameAction = new ViewPanelActions("... by summary(prescriptions)", "sortBySummaryPrescriptions");
		sortByLastNameAction = new ViewPanelActions("... by prescription ID(prescriptions)", "sortByIDPrescriptions");
			
		menuIndicator= new JLabel("You are viewing the prescriptions table :: ");
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
	public PanelViewPrescriptions(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,defaultSQLQuery);	
		allPrescriptionsPanel.add(databaseBrowser.getPane());
	}

	//constructor based on default sql query constants
	public PanelViewPrescriptions(String  userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,userChoice);
		allPrescriptionsPanel.add(databaseBrowser.getPane());
	}
	
	public static JPanel getAllPrescriptionsPanel() {
		return allPrescriptionsPanel;
	}

	public static void setAllPrescriptionsPanel(JPanel allPrescriptionsPanel) {
		PanelViewPrescriptions.allPrescriptionsPanel = allPrescriptionsPanel;
	}
	
	public static void printTablePrescriptions(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing departments to an excel file
	public static void createExcelPrescriptions(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_PRESCRIPTIONS());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescriptions List");
		    resultSetToExcel.generate(new File("D:/prescriptions.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for prescriptions table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
