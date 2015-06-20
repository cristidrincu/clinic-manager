package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InsertDiagnosticIntoDatabase implements ListSelectionListener{
	
	private JPanel mainContainer;
	
	private JPanel diagnosticNameElements;
	private JPanel diagnosticAnalysisElements;
	private JPanel pacientListElements;
	private JPanel employeeListElements;
	
	private JPanel containerFNamePacient;
	private JPanel containerFNameDoctor;
	
	private JLabel diagnosticNameLabel;
	private JLabel diagnosticAnalysisLabel;
	private JLabel labelPacientFName;
	private JLabel labelDoctorFName;
	
	private String pacientFirstName;
	private String doctorFirstName;
	
	private JTextField diagnosticNameTextField;
	private JTextArea diagnosticAnalysisTextField;
	
	private DatabaseConnector DBConnector;
	private DatabaseBrowse DBBrowse;
	
	private JLabel pacientListLabel;
	private JLabel employeeListLabel;
	
	private JList pacientList;
	private JList employeeList;
	
	Vector<String> vectorPacientModel = new Vector<String>();
	Vector<String> vectorEmployeeModel = new Vector<String>();
	
	private String query;
	private int pacientID;
	private int employeeID;
	
	public InsertDiagnosticIntoDatabase(){
		mainContainer = new JPanel();
		mainContainer.setPreferredSize(new Dimension(500,450));
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		diagnosticNameElements = new JPanel();
		diagnosticNameElements.setLayout(new BorderLayout());
		
		diagnosticAnalysisElements = new JPanel();
		diagnosticAnalysisElements.setLayout(new BorderLayout());
		
		pacientListElements = new JPanel();
		pacientListElements.setLayout(new BorderLayout());
		
		employeeListElements = new JPanel();
		employeeListElements.setLayout(new BorderLayout());
		
		containerFNamePacient = new JPanel();
		containerFNamePacient.setLayout(new BorderLayout());
		
		containerFNameDoctor = new JPanel();
		containerFNameDoctor.setLayout(new BorderLayout());
		
		//setup the labels
		diagnosticNameLabel = new JLabel("Diagnostic name: ");
		diagnosticAnalysisLabel = new JLabel("Diagnostic Analysis: ");
		pacientListLabel = new JLabel("This diagnostic belongs to pacient: ");
		employeeListLabel = new JLabel("This diagnostic has been given by doctor: ");
		
		//setup the textfields and the textarea
		diagnosticNameTextField = new JTextField();
		diagnosticAnalysisTextField = new JTextArea(10,1);
		diagnosticAnalysisTextField.setLineWrap(true);
		
		//populate lists
		String query1Pacients = "SELECT LAST_NAME FROM HR.PACIENTS";
		String query2Employees = "SELECT LAST_NAME FROM HR.EMPLOYEE";
		
		//connect to database and populate list for pacients
		DBConnector = new DatabaseConnector();
		DBBrowse = new DatabaseBrowse();
		vectorPacientModel = DBBrowse.createVectorModel(DBConnector, query1Pacients, "LAST_NAME", vectorPacientModel);
		pacientList = new JList(vectorPacientModel);
		pacientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pacientList.addListSelectionListener(this);
		
		//connect to database and populate list for doctors
		vectorEmployeeModel = DBBrowse.createVectorModel(DBConnector, query2Employees, "LAST_NAME", vectorEmployeeModel);
		employeeList = new JList(vectorEmployeeModel);
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeList.addListSelectionListener(this);
		
		//populate JPanels with elements
		diagnosticNameElements.add(diagnosticNameLabel, BorderLayout.PAGE_START);
		diagnosticNameElements.add(diagnosticNameTextField, BorderLayout.CENTER);
		
		diagnosticAnalysisElements.add(diagnosticAnalysisLabel, BorderLayout.PAGE_START);
		diagnosticAnalysisElements.add(diagnosticAnalysisTextField, BorderLayout.CENTER);
		
		pacientListElements.add(pacientListLabel, BorderLayout.PAGE_START);
		pacientListElements.add(pacientList, BorderLayout.CENTER);
		
		employeeListElements.add(employeeListLabel, BorderLayout.PAGE_START);
		employeeListElements.add(employeeList, BorderLayout.CENTER);
		
		
		mainContainer.add(diagnosticNameElements);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(diagnosticAnalysisElements);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(pacientListElements);
		mainContainer.add(containerFNamePacient);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(employeeListElements);
		mainContainer.add(containerFNameDoctor);
		
		
		Object msg[] = {mainContainer};
		Object type[] = {"Insert", "Cancel"};
		
		int okOption = JOptionPane.showOptionDialog(null, msg, "Insert new diagnostic",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
		if(okOption == JOptionPane.OK_OPTION){
			//queries for populating the lists
			query = "INSERT INTO HR.DIAGNOSTICS (NAME,ANALYSIS,PACIENT_ID,EMPLOYEE_ID) VALUES ("
					+"'"+diagnosticNameTextField.getText()+"'"+","
					+"'"+diagnosticAnalysisTextField.getText()+"'"+","
					+"'"+pacientID+"'"+","
					+"'"+employeeID+"'"
					+")";
					;
			DBBrowse.executeUpdate(DBConnector, query);
		}else if(okOption==JOptionPane.CANCEL_OPTION){
			return;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == pacientList) {
			if (e.getValueIsAdjusting() == false){
				pacientID = DBBrowse.getPacientID(DBConnector, (String)pacientList.getSelectedValue());
				//JOptionPane.showMessageDialog(null, DBBrowse.getFirstNamePacient(DBConnector, pacientID, "HR.PACIENTS"));
			}
	    }
		if(e.getSource()==employeeList){
			if(e.getValueIsAdjusting()==false){
				employeeID =DBBrowse.getEmployeeID(DBConnector, (String)employeeList.getSelectedValue());
				//JOptionPane.showMessageDialog(null, DBBrowse.getFirstNameEmployee(DBConnector,employeeID,"HR.EMPLOYEE"));
			}
		}
	}

	public String getPacientFirstName() {
		return pacientFirstName;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setPacientFirstName(String pacientFirstName) {
		this.pacientFirstName = pacientFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}
	
	
}
