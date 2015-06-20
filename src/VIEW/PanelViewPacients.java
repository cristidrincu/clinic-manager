package VIEW;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
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

import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;
import ACTIONS.ViewPanelActions;
import CONSTANTS.InitialAppPanelsQueryConstants;


public class PanelViewPacients extends PanelViewEntity {
	private static DatabaseBrowse databaseBrowser;
	private static DatabaseConnector databaseConnector=new DatabaseConnector();
	private static JPanel allPacientsPanel = new JPanel();
	protected JLabel menuIndicator;
	
	private static InitialAppPanelsQueryConstants queryConstant;
	private static Connection conn;
	
	private ViewPanelActions sendPacientEmail;
	
	public PanelViewPacients(){
		allPacientsPanel.setLayout(new BorderLayout());
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(pacient)", "exportExcelTablePacients");
		//sendPacientEmail = new ViewPanelActions("Send this table by email", "sendTableByEmail");
		printTableAction = new ViewPanelActions("Print this table(pacient)", "printTablePacients");
		//closeTableAction = new ViewPanelActions("Close this table(pacient)", "closeTablePacients");
				
		sortByFirstNameAction = new ViewPanelActions("...by first name(pacient)", "sortByFirstNamePacients");
		sortByLastNameAction = new ViewPanelActions("... by last name(pacient)", "sortByLastNamePacients");
		//sortByDiagnostic = new ViewPanelActions("... by diagnostic(pacient)", "sortByDiagnosticPacients");
		sortByID = new ViewPanelActions("... by ID(pacient)", "sortByIDPacients");
		
		allPacientsPanel.add(super.getMenuBar(), BorderLayout.NORTH);
		
		menuIndicator= new JLabel("You are viewing the pacients table :: ");
		menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(sendPacientEmail));
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
	public PanelViewPacients(int defaultSQLQuery){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, defaultSQLQuery);
		allPacientsPanel.add(databaseBrowser.getPane());
	}
	
	//constructor for viewing pacients based on action chosen by user
	public PanelViewPacients(String userChoice){
		this();
		databaseBrowser = new DatabaseBrowse(databaseConnector, userChoice);
		allPacientsPanel.add(databaseBrowser.getPane());
	}

	public static JPanel getAllPacientsPanel() {
		return allPacientsPanel;
	}

	public static void setAllPacientsPanel(JPanel allPacientsPanel) {
		PanelViewPacients.allPacientsPanel = allPacientsPanel;
	}
	
	public static void printTablePacients(){
		databaseBrowser.printTable();
	}
	
	//method for exporting the resultset containing pacients to an excel file
	public static void createExcelPacients(){
		try{
			queryConstant = new InitialAppPanelsQueryConstants();
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_PACIENTS_ALPHA());
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Pacient List");
		    resultSetToExcel.generate(new File("D:/pacients.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully for pacients table!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
