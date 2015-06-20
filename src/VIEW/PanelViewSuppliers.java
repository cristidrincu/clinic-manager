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

public class PanelViewSuppliers extends PanelViewEntity{
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:orcl", "HR","crusader2");
	private static JPanel allSuppliersPanel = new JPanel();
	
	//accesible from methods in any class in the same package and from any subclass anywhere
	protected JLabel menuIndicator;
	
	//custom action for suppliers
	private ViewPanelActions sortByBusinessName;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	//default constructor
	public PanelViewSuppliers(){
		//databaseBrowser = new DatabaseBrowse(databaseConnector, 9);
		allSuppliersPanel.setLayout(new BorderLayout());
		allSuppliersPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(supplier)", "exportExcelTableSupplier");
		printTableAction = new ViewPanelActions("Print this table(supplier)", "printTableSupplier");
		closeTableAction = new ViewPanelActions("Close this table(supplier)", "closeTableSupplier");
				
		sortByFirstNameAction = new ViewPanelActions("... by first name(supplier)", "sortByFirstNameSupplier");
		sortByLastNameAction = new ViewPanelActions("... by last name(supplier)", "sortByLastNameSupplier");
		sortByID = new ViewPanelActions("... by rate,prices(supplier)", "sortByRatePricesSupplier");
		sortByBusinessName = new ViewPanelActions("... by business name(supplier)", "sortByBusinessNameSupplier");
			
		menuIndicator= new JLabel("You are viewing the supplier table :: ");
		menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		super.panelMenuFile.add(new JMenuItem(closeTableAction));
	
		super.panelMenuSort.add(new JMenuItem(sortByFirstNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByLastNameAction));
		super.panelMenuSort.add(new JMenuItem(sortByID));
		super.panelMenuSort.add(new JMenuItem(sortByBusinessName));
		
		super.getMenuBar().add(menuIndicator);
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuSort);
		super.getMenuBar().add(panelMenuHelp);
	}

	//constructor based on default sql query constants
	public PanelViewSuppliers(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,defaultSQLQuery);
		allSuppliersPanel.add(databaseBrowser.getPane());
	}
	
	//constructor based on default sql query constants
	public PanelViewSuppliers(String  userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector,userChoice);
		allSuppliersPanel.add(databaseBrowser.getPane());
	}
	
	public static JPanel getAllSuppliersPanel() {
		return allSuppliersPanel;
	}

	public static void setAllSuppliersPanel(JPanel allSuppliersPanel) {
		PanelViewSuppliers.allSuppliersPanel = allSuppliersPanel;
	}
	
	public static void printTableSuppliers(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing suppliers to an excel file
	public static void createExcelSuppliers(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_SUPPLIERS_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Suppliers List");
		    resultSetToExcel.generate(new File("D:/suppliers.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for suppliers table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
