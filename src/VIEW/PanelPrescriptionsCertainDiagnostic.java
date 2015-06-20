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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ACTIONS.ViewPanelActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPrescriptionsCertainDiagnostic extends PanelViewEntity implements ListSelectionListener {
	private static JPanel prescriptionsCertDiag;
	private JPanel dialogContainer;
	private JLabel pacientLabel;
	private JTextField prescriptionName;
	private JList diagnosticsList;
	private JScrollPane scrollPane;
	private int btnStepDiagnosticsName;
	private static int diagnosticID;
	private static String diagnosticName;
	private JLabel menuIndicator;
	
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String query;
	private String populateDiagnosticsList = "SELECT NAME FROM HR.DIAGNOSTICS";
	private Vector<String> vectorDiagnosticsList = new Vector<String>();
	
	private static Connection conn;
	
	public PanelPrescriptionsCertainDiagnostic(){
		//setup UI
		prescriptionsCertDiag = new JPanel();
		prescriptionsCertDiag.setLayout(new BorderLayout());
		prescriptionsCertDiag.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file", "exportExcelTablePrescriptionsCertainDiagnostic");
		printTableAction = new ViewPanelActions("Print this table", "printTablePrescriptionsDiagnostic");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		super.panelMenuFile.add(new JMenuItem(closeTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		dialogContainer = new JPanel();
		dialogContainer.setLayout(new BorderLayout());
		
		pacientLabel = new JLabel("Select pacient name: ");
		
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		vectorDiagnosticsList = dbBrowse.createVectorModel(dbConnector, populateDiagnosticsList, "NAME", vectorDiagnosticsList);
		diagnosticsList = new JList(vectorDiagnosticsList);
		diagnosticsList.addListSelectionListener(this);
		scrollPane = new JScrollPane(diagnosticsList);
		
		dialogContainer.add(pacientLabel, BorderLayout.PAGE_START);
		dialogContainer.add(scrollPane, BorderLayout.CENTER);
		
		Object dialogMessage[] = {dialogContainer};
		Object dialogButtons[] = {"OK", "Cancel"};
		
		btnStepDiagnosticsName=JOptionPane.showOptionDialog(null, dialogMessage, "Select a diagnostic in order to get prescriptions released",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogButtons,
		        null);
		if(btnStepDiagnosticsName==JOptionPane.OK_OPTION){
			diagnosticID=dbBrowse.getDiagnosticID(dbConnector, diagnosticName);
			//connect to database and execute query containing pacient name/id, also setup the query
			query = "SELECT PR.SUMMARY, PR.PRESCRIPTION_DATE FROM HR.PRESCRIPTIONS PR, HR.DIAGNOSTICS D, HR.EMPLOYEE E WHERE PR.DIAGNOSTIC_ID="+
			"'"+diagnosticID+"'"+"AND D.EMPLOYEE_ID=E.EMPLOYEE_ID AND D.NAME = "+"'"+diagnosticName+"'";
			dbConnector = new DatabaseConnector();
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
			prescriptionsCertDiag.add(dbBrowse.getPane());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			diagnosticName=(String) diagnosticsList.getSelectedValue();
			menuIndicator= new JLabel("You are viewing the prescriptions table for diagnostic "+ (String)diagnosticsList.getSelectedValue() +":: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
	    }	
	}

	public DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public void setDbBrowse(DatabaseBrowse dbBrowse) {
		this.dbBrowse = dbBrowse;
	}

	public JList getDiagnosticsList() {
		return diagnosticsList;
	}

	public void setDiagnosticsList(JList diagnosticsList) {
		this.diagnosticsList = diagnosticsList;
	}	
	
	public static JPanel getPrescriptionsCertDiag() {
		return prescriptionsCertDiag;
	}

	public static void setPrescriptionsCertDiag(JPanel prescriptionsCertDiag) {
		PanelPrescriptionsCertainDiagnostic.prescriptionsCertDiag = prescriptionsCertDiag;
	}

	public static void printTable(){
		dbBrowse.printTable();
	}
	
	//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
	public static void createExcelPrescriptionsCertainDiagnostic(){
		try{
			Class.forName(dbConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement("SELECT PR.SUMMARY, PR.PRESCRIPTION_DATE FROM HR.PRESCRIPTIONS PR, HR.DIAGNOSTICS D, HR.EMPLOYEE E WHERE PR.DIAGNOSTIC_ID="+
					"'"+diagnosticID+"'"+"AND D.EMPLOYEE_ID=E.EMPLOYEE_ID AND D.NAME = "+"'"+diagnosticName+"'");
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescription List");
		    resultSetToExcel.generate(new File("D:/prescriptionsCertainDiagnostic.xls"));
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
