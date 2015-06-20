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

public class PanelViewDepartments extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector();
	private static JPanel allDepartmentsPanel = new JPanel();
	
	//accesible from methods in any class in the same package and from any subclass anywhere
	protected JLabel menuIndicator;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	//default constructor
	public PanelViewDepartments(){
		allDepartmentsPanel.setLayout(new BorderLayout());
		allDepartmentsPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(department)", "exportExcelTableDepartments");
		printTableAction = new ViewPanelActions("Print this table(department)", "printTableDepartments");
		//closeTableAction = new ViewPanelActions("Close this table(department)", "closeTableDepartments");
				
		sortByFirstNameAction = new ViewPanelActions("... by department name(department)", "sortByDepartmentName");
		sortByLastNameAction = new ViewPanelActions("... by department ID(department)", "sortDepartmentID");
			
		menuIndicator= new JLabel("You are viewing the departments table :: ");
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
	public PanelViewDepartments(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,defaultSQLQuery);	
		allDepartmentsPanel.add(databaseBrowser.getPane());
	}

	//constructor based on default sql query constants
	public PanelViewDepartments(String  userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,userChoice);
		allDepartmentsPanel.add(databaseBrowser.getPane());
	}
	
	public static JPanel getAllDepartmentsPanel() {
		return allDepartmentsPanel;
	}

	public static void setAllDepartmentsPanel(JPanel allDepartmentsPanel) {
		PanelViewDepartments.allDepartmentsPanel = allDepartmentsPanel;
	}
	
	public static void printTableDepartments(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing departments to an excel file
	public static void createExcelDepartments(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_DEPARTMENTS_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Departments List");
		    resultSetToExcel.generate(new File("D:/departments.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for departments table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
