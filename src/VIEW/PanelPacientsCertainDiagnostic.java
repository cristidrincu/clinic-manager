package VIEW;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ACTIONS.ViewPanelActions;
import CONSTANTS.InitialAppPanelsQueryConstants;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPacientsCertainDiagnostic extends PanelViewEntity implements ListSelectionListener {
	private static JPanel panelPacientsCertDiag;
	private JPanel dialogContainer;
	private JPanel labelListContainer;
	private JLabel diagnosticLabel;
	private JLabel menuIndicator;
	private JList diagnosticsList;
	private JScrollPane scrollPane;
	private Vector<String> modelListVector = new Vector<String>();
	//private JTextField diagnosticName;
	private int btnStepDiagnosticName;
	
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String query = "SELECT NAME FROM HR.DIAGNOSTICS";
	private static String diangosticName;
	
	private static Connection conn;
	
	public PanelPacientsCertainDiagnostic(){
		//create vector model for list
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		modelListVector = dbBrowse.createVectorModel(dbConnector, query, "NAME", modelListVector);
		
		//setup user interface
		panelPacientsCertDiag = new JPanel();
		panelPacientsCertDiag.setLayout(new BorderLayout());
		panelPacientsCertDiag.add(super.getMenuBar(),BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file", "exportExcelTablePacientsCertainDiag");
		printTableAction = new ViewPanelActions("Print this table", "printTablePacientsCertainDiag");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		dialogContainer = new JPanel();
		dialogContainer.setLayout(new BoxLayout(dialogContainer,BoxLayout.Y_AXIS));
		
		labelListContainer = new JPanel();
		labelListContainer.setLayout(new BorderLayout());
		
		diagnosticLabel = new JLabel("Select a diagnostic:");
		diagnosticsList = new JList(modelListVector);
		diagnosticsList.addListSelectionListener(this);
		diagnosticsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(diagnosticsList);
		
		labelListContainer.add(diagnosticLabel, BorderLayout.PAGE_START);
		labelListContainer.add(scrollPane, BorderLayout.CENTER);
		
		dialogContainer.add(labelListContainer);
		
		Object dialogMessage[] = {dialogContainer};
		Object dialogButtons[] = {"OK", "Cancel"};
		
		btnStepDiagnosticName=JOptionPane.showOptionDialog(null, dialogMessage, "Select a diagnostic!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogButtons,
		        null);
		if(btnStepDiagnosticName==JOptionPane.OK_OPTION){
			//connect to database and execute query containing diagnostics name, also setup the query
			query = "SELECT P.FIRST_NAME, P.LAST_NAME,P.AGE,P.ID_CARD, D.NAME, D.ANALYSIS FROM HR.PACIENTS P, HR.DIAGNOSTICS D WHERE P.PACIENT_ID=D.PACIENT_ID AND D.NAME LIKE"+
			"'%"+diangosticName+"%'";
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
			panelPacientsCertDiag.add(dbBrowse.getPane());
		}else if(btnStepDiagnosticName==JOptionPane.CANCEL_OPTION){
			return;
		}
	}

	public DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelPacientsCertainDiagnostic.dbBrowse = dbBrowse;
	}

	public static JPanel getPanelPacientsCertDiag() {
		return panelPacientsCertDiag;
	}

	public JList getDiagnosticsList() {
		return diagnosticsList;
	}

	public static void setPanelPacientsCertDiag(JPanel panelPacientsCertDiag) {
		PanelPacientsCertainDiagnostic.panelPacientsCertDiag = panelPacientsCertDiag;
	}

	public void setDiagnosticsList(JList diagnosticsList) {
		this.diagnosticsList = diagnosticsList;
	}

	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			diangosticName = (String)diagnosticsList.getSelectedValue();
			menuIndicator= new JLabel("You are viewing the pacients table for diagnostic "+ (String)diagnosticsList.getSelectedValue() +":: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
		}
	}
	
	public static void printTable(){
		dbBrowse.printTable();
	}
	
	//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
	public static void createExcel(){
		try{
			Class.forName(dbConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement("SELECT P.FIRST_NAME, P.LAST_NAME,P.AGE,P.ID_CARD, D.NAME, D.ANALYSIS FROM HR.PACIENTS P, HR.DIAGNOSTICS D WHERE P.PACIENT_ID=D.PACIENT_ID AND D.NAME LIKE"+
					"'%"+diangosticName+"%'");
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescription List");
		    resultSetToExcel.generate(new File("D:/pacientsCertainDiagnostic.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
