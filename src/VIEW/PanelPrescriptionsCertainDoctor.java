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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ACTIONS.ViewPanelActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPrescriptionsCertainDoctor extends PanelViewEntity implements ListSelectionListener {
	private static JPanel prescriptionsCertDoc;
	private JPanel dialogContainer;
	private JLabel doctorLabel;
	//private JTextField prescriptionName;
	private JList doctorsList;
	private JScrollPane scrollPane;
	private int btnStepDoctorsName;
	private static int doctorID;
	private String doctorName;
	private JLabel menuIndicator;
	
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String query;
	private String populateDoctorsList = "SELECT LAST_NAME FROM HR.EMPLOYEE";
	private Vector<String> vectorDiagnosticsList = new Vector<String>();
	
	private static Connection conn;
	
	public PanelPrescriptionsCertainDoctor(){
		//setup UI
		prescriptionsCertDoc = new JPanel();
		prescriptionsCertDoc.setLayout(new BorderLayout());
		prescriptionsCertDoc.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file(doctor)", "exportExcelTablePrescriptionsDoctor");
		printTableAction = new ViewPanelActions("Print this table(doctor)", "printTablePrescriptionsDoctor");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		super.panelMenuFile.add(new JMenuItem(closeTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		dialogContainer = new JPanel();
		dialogContainer.setLayout(new BorderLayout());
		
		doctorLabel = new JLabel("Select doctor name: ");
		
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		vectorDiagnosticsList = dbBrowse.createVectorModel(dbConnector, populateDoctorsList, "LAST_NAME", vectorDiagnosticsList);
		doctorsList = new JList(vectorDiagnosticsList);
		doctorsList.addListSelectionListener(this);
		scrollPane = new JScrollPane(doctorsList);
		
		dialogContainer.add(doctorLabel, BorderLayout.PAGE_START);
		dialogContainer.add(scrollPane, BorderLayout.CENTER);
		
		Object dialogMessage[] = {dialogContainer};
		Object dialogButtons[] = {"OK", "Cancel"};
		
		btnStepDoctorsName=JOptionPane.showOptionDialog(null, dialogMessage, "Select a doctor in order to get prescriptions released",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogButtons,
		        null);
		if(btnStepDoctorsName==JOptionPane.OK_OPTION){
			doctorID=dbBrowse.getEmployeeID(dbConnector, doctorName);
			//connect to database and execute query containing pacient name/id, also setup the query
			query = "SELECT PR.SUMMARY, PR.PRESCRIPTION_DATE FROM HR.PRESCRIPTIONS PR, HR.DIAGNOSTICS D, HR.EMPLOYEE E WHERE PR.DIAGNOSTIC_ID=D.DIAGNOSTIC_ID AND " +
					"D.EMPLOYEE_ID = E.EMPLOYEE_ID AND D.EMPLOYEE_ID="+"'"+doctorID+"'";
			dbConnector = new DatabaseConnector();
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
			prescriptionsCertDoc.add(dbBrowse.getPane());
		}else if(btnStepDoctorsName==JOptionPane.CANCEL_OPTION){
			return;
		}
	}

	//class methods
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			doctorName = (String)doctorsList.getSelectedValue();
			menuIndicator= new JLabel("You are viewing the prescriptions table for doctor "+ (String)doctorsList.getSelectedValue() +":: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
	    }	
	}
	
		//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
		public static void createExcelPrescriptionDoctor(){
			try{
				Class.forName(dbConnector.getDatabaseDriver());
				conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
			    PreparedStatement stmt = conn.prepareStatement("SELECT PR.SUMMARY, PR.PRESCRIPTION_DATE FROM HR.PRESCRIPTIONS PR, HR.DIAGNOSTICS D, HR.EMPLOYEE E WHERE PR.DIAGNOSTIC_ID=D.DIAGNOSTIC_ID AND " +
						"D.EMPLOYEE_ID = E.EMPLOYEE_ID AND D.EMPLOYEE_ID="+"'"+doctorID+"'");
			    ResultSet resultSet = stmt.executeQuery();
			    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescription List");
			    resultSetToExcel.generate(new File("D:/prescriptionsDoctor.xls"));
			    JOptionPane.showMessageDialog(null, "File exported succesfully");
			}catch(ClassNotFoundException cnfe){
				JOptionPane.showMessageDialog(null, cnfe.getMessage());
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getMessage());
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}		
		  }
	//getters and setters
	public JList getDoctorsList() {
		return doctorsList;
	}


	public DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}



	public void setDoctorsList(JList doctorsList) {
		this.doctorsList = doctorsList;
	}


	public static void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelPrescriptionsCertainDoctor.dbBrowse = dbBrowse;
	}

	public static JPanel getPrescriptionsCertDoc() {
		return prescriptionsCertDoc;
	}

	public static void setPrescriptionsCertDoc(JPanel prescriptionsCertDoc) {
		PanelPrescriptionsCertainDoctor.prescriptionsCertDoc = prescriptionsCertDoc;
	}
	
	public static void printTable(){
		dbBrowse.printTable();
	}
}
