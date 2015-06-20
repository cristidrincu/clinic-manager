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
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ACTIONS.ViewPanelActions;
import CONSTANTS.InitialAppPanelsQueryConstants;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelViewDoctors extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:orcl", "HR","crusader2");
	private static JPanel allDoctorsPanel = new JPanel();
	protected JLabel menuIndicator;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	public PanelViewDoctors(){
		
		allDoctorsPanel.setLayout(new BorderLayout());
		allDoctorsPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(doctor)", "exportExcelTableDoctors");
		printTableAction = new ViewPanelActions("Print this table(doctor)", "printTableDoctors");
		//closeTableAction = new ViewPanelActions("Close this table(doctor)", "closeTableDoctors");
				
		sortByFirstNameAction = new ViewPanelActions("... by first name(doctor)", "sortByFirstNameDoctors");
		sortByLastNameAction = new ViewPanelActions("... by last name(doctor)", "sortByLastNameDoctors");
		//sortByDiagnostic = new ViewPanelActions("... by pacients(doctor)", "sortByDiagnosticDoctors");
		sortByID = new ViewPanelActions("... by ID(doctor)", "sortByIDDoctors");
			
		menuIndicator= new JLabel("You are viewing the doctors table :: ");
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
	
	//constructor for viewing pacients based on default sql query
	public PanelViewDoctors(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, 6);
		allDoctorsPanel.add(databaseBrowser.getPane());
	}
	
	//constructor for viewing doctors based on action chosen by user
	public PanelViewDoctors(String userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, userChoice);
		allDoctorsPanel.add(databaseBrowser.getPane());
	}

	public static JPanel getAllPacientsPanel() {
		return allDoctorsPanel;
	}

	public static void setAllPacientsPanel(JPanel allPacientsPanel) {
		PanelViewDoctors.allDoctorsPanel = allPacientsPanel;
	}
	
	public static void printTableDoctors(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing doctors to an excel file
	public static void createExcelDoctors(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_DOCTORS_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Doctors List");
		    resultSetToExcel.generate(new File("D:/doctors.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for doctors table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
