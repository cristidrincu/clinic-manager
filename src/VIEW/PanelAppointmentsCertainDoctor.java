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
public class PanelAppointmentsCertainDoctor extends PanelViewEntity implements ListSelectionListener {
	private static JPanel panelAppointmentsDoc;
	private JPanel dialogContainer;
	private JLabel doctorLabel;
	private JLabel menuIndicator;
	private JList doctorsList;
	private JScrollPane scrollPane;
	private int btnStepDoctorsName;
	private static int doctorID;
	private String doctorName;
	
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String query;
	private String populateDoctorsList = "SELECT LAST_NAME FROM HR.EMPLOYEE";
	private Vector<String> vectorDiagnosticsList = new Vector<String>();
	
	private static Connection conn;
	
	public PanelAppointmentsCertainDoctor(){
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		
		//setup UI
		panelAppointmentsDoc = new JPanel();
		panelAppointmentsDoc.setLayout(new BorderLayout());
		panelAppointmentsDoc.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file", "exportExcelTableAppointmentsDoctor");
		printTableAction = new ViewPanelActions("Print this table", "printTableAppointmentsDoctor");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		dialogContainer = new JPanel();
		dialogContainer.setLayout(new BorderLayout());
		
		doctorLabel = new JLabel("Select doctor name: ");
		
		vectorDiagnosticsList = dbBrowse.createVectorModel(dbConnector, populateDoctorsList, "LAST_NAME", vectorDiagnosticsList);
		doctorsList = new JList(vectorDiagnosticsList);
		doctorsList.addListSelectionListener(this);
		scrollPane = new JScrollPane(doctorsList);
		
		dialogContainer.add(doctorLabel, BorderLayout.PAGE_START);
		dialogContainer.add(scrollPane, BorderLayout.CENTER);
		
		Object dialogMessage[] = {dialogContainer};
		Object dialogButtons[] = {"OK", "Cancel"};
		
		btnStepDoctorsName=JOptionPane.showOptionDialog(null, dialogMessage, "Select a doctor in order to his/her prescriptions",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogButtons,
		        null);
		if(btnStepDoctorsName==JOptionPane.OK_OPTION){
			doctorID=dbBrowse.getEmployeeID(dbConnector, doctorName);
			//connect to database and execute query containing pacient name/id, also setup the query
			query = "SELECT AP.FIRST_NAME, AP.LAST_NAME, AP.APP_HOUR, AP.APP_DATE FROM HR.APPOINTMENTS AP,HR.EMPLOYEE E WHERE E.EMPLOYEE_ID="+"'"+doctorID+"'"+ "AND AP.APPOINTMENT_ID=E.EMPLOYEE_ID";
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
			panelAppointmentsDoc.add(dbBrowse.getPane());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){//user finished selecting elements in the list
			doctorName = (String)doctorsList.getSelectedValue();
			menuIndicator= new JLabel("You are viewing the appointments table for doctor "+ doctorName +" :: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
		}	
	}

	public JList getDoctorsList() {
		return doctorsList;
	}

	public DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public void setDoctorsList(JList doctorsList) {
		this.doctorsList = doctorsList;
	}

	public void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelAppointmentsCertainDoctor.dbBrowse = dbBrowse;
	}
	
	public static JPanel getPanelAppointmentsDoc() {
		return panelAppointmentsDoc;
	}

	public static void setPanelAppointmentsDoc(JPanel panelAppointmentsDoc) {
		PanelAppointmentsCertainDoctor.panelAppointmentsDoc = panelAppointmentsDoc;
	}
	
	public static void printTable(){
		dbBrowse.printTable();
	}

	//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
	public static void createExcel(){
		try{
			Class.forName(dbConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement("SELECT AP.FIRST_NAME, AP.LAST_NAME, AP.APP_HOUR, AP.APP_DATE FROM HR.APPOINTMENTS AP,HR.EMPLOYEE E WHERE E.EMPLOYEE_ID="+"'"+doctorID+"'"+ "AND AP.APPOINTMENT_ID=E.EMPLOYEE_ID");
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescription List");
		    resultSetToExcel.generate(new File("D:/appointmentsCertainDoctor.xls"));
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
