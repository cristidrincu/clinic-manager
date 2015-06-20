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

public class PanelViewAssistants extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:orcl", "HR","crusader2");
	private static JPanel allAssistantsPanel = new JPanel();
	
	//accesible from methods in any class in the same package and from any subclass anywhere
	protected JLabel menuIndicator;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	//default constructor
	public PanelViewAssistants(){
		allAssistantsPanel.setLayout(new BorderLayout());
		allAssistantsPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(assistant)", "exportExcelTableAssistants");
		printTableAction = new ViewPanelActions("Print this table(assistant)", "printTableAssistants");
		closeTableAction = new ViewPanelActions("Close this table(assistant)", "closeTableAssistants");
				
		sortByFirstNameAction = new ViewPanelActions("... by first name(assistant)", "sortByFirstNameAssistants");
		sortByLastNameAction = new ViewPanelActions("... by last name(assistant)", "sortByLastNameAssistants");
		sortByDiagnostic = new ViewPanelActions("... by pacients(assistant)", "sortByDiagnosticAssistants");
		sortByID = new ViewPanelActions("... by ID(assistant)", "sortByIDAssistants");
			
		menuIndicator= new JLabel("You are viewing the assistants table :: ");
		menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		super.panelMenuFile.add(new JMenuItem(closeTableAction));
	
		super.panelMenuSort.add(new JMenuItem(sortByFirstNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByLastNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByDiagnostic));
		super.panelMenuSort.add(new JMenuItem(sortByID));
		
		super.getMenuBar().add(menuIndicator);
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuSort);
		super.getMenuBar().add(panelMenuHelp);
		
	}
		
	public PanelViewAssistants(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, defaultSQLQuery);
		allAssistantsPanel.add(databaseBrowser.getPane());
	}
	
	//constructor for viewing assistants based on action chosen by user
	public PanelViewAssistants(String userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, userChoice);
		allAssistantsPanel.add(databaseBrowser.getPane());
	}

	public static JPanel getAllAssistantsPanel() {
		return allAssistantsPanel;
	}

	public static void setAllAssistantsPanel(JPanel allAssistantsPanel) {
		PanelViewAssistants.allAssistantsPanel = allAssistantsPanel;
	}
	
	//print table contained in this object
	public static void printTableAssistants(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing assistants to an excel file
	public static void createExcelAssistants(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_ASSISTANTS_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Assistants List");
		    resultSetToExcel.generate(new File("D:/assistants.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for assistants table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
