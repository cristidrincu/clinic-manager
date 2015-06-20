package MODEL;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entities.Doctor;
import entities.Person;

public class InsertDoctorIntoDatabase extends InsertEntityIntoDatabase implements ListSelectionListener {
	
	private Box doctorBox;
	private JPanel mainContainer;
	private JList<String> departments;
	private JScrollPane scrollPane;
	private Vector<String> vectorListModel = new Vector<String>();
	
	private JLabel doctorSpecLabel;
	private JLabel doctorExperienceLabel;
	private JLabel departmentLabel;
	
	private JTextField doctorSpecHolder;
	private JTextField doctorExperienceHolder;
	private JTextField inputDepartmentHolder;
	
	//database connector
	private DatabaseConnector DBConnector;
	private DatabaseBrowse DBBrowse;
	private String queryDepartmentsNames;
	private String query;
	private int departmentID;
	
	//doctor object
	private Person person;
	private Doctor doctor;
	
	private SaveEntityToFile saveEntity;
	
	public InsertDoctorIntoDatabase(){
		//connect to database and populate vector
		queryDepartmentsNames = "SELECT NAME FROM HR.DEPARTMENT";
		DBConnector = new DatabaseConnector();
		DBBrowse = new DatabaseBrowse();
		vectorListModel = DBBrowse.createVectorModel(DBConnector, queryDepartmentsNames, "NAME", vectorListModel);
		departments = new JList<String>(vectorListModel);
		departments.addListSelectionListener(this);
		scrollPane = new JScrollPane(departments);
		scrollPane.setPreferredSize(new Dimension(350,100));
		
		doctorBox=Box.createVerticalBox();
		
		doctorSpecLabel = new JLabel("Doctor specialty: ");
		doctorExperienceLabel = new JLabel("Doctor's experience: ");
		departmentLabel = new JLabel("Department: ", JLabel.LEFT);
		
		doctorSpecHolder = new JTextField();
		doctorExperienceHolder = new JTextField();
		inputDepartmentHolder = new JTextField();
		
		doctorBox.add(Box.createVerticalStrut(5));
		doctorBox.add(doctorSpecLabel);
		doctorBox.add(Box.createVerticalStrut(5));
		doctorBox.add(doctorSpecHolder);
		
		doctorBox.add(Box.createVerticalStrut(5));
		
		doctorBox.add(doctorExperienceLabel);
		doctorBox.add(Box.createVerticalStrut(5));
		doctorBox.add(doctorExperienceHolder);
		
		doctorBox.add(Box.createVerticalStrut(5));
		doctorBox.add(scrollPane);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		mainContainer.add(new JLabel("INSERT NEW DOCTOR!"));
		mainContainer.add(super.getInputBox());
		mainContainer.add(doctorBox);
	}	
	//getters and setters - encapsulation
	public JTextField getDoctorSpecHolder() {
		return doctorSpecHolder;
	}

	public JTextField getDoctorExperienceHolder() {
		return doctorExperienceHolder;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			departmentID = DBBrowse.getDepartmentID(DBConnector, (String)departments.getSelectedValue());
	    }		
	}
	
	public void executeInsert(){
		Object msg[] = {mainContainer};
		Object type[] = {"Insert", "Cancel"};
		
		int okBtnOptionPane = JOptionPane.showOptionDialog(null, msg, "Insert Dialog",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
		if(okBtnOptionPane==JOptionPane.OK_OPTION){
			
			//insert into database text field values
			query = "INSERT INTO clinic.Employee(DEPARTMENT_ID, FIRST_NAME, LAST_NAME, ADDRESS, SEX, AGE, CARD_ID, PHONE_NUMBER,EMAIL_ADDRESS, DEPARTMENT, SPECIALIZATION,EXPERIENCE) VALUES("
					+"'"
					+departmentID+"'"
					+","
					+"'"
					+super.getInputFirstNameHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputLastNameHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputHomeAddressHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputPersonSexHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputAgeHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputIDHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputPhoneNumberHolder().getText()
					+"'"
					+","
					+"'"
					+super.getInputEmailAddressHolder().getText()
					+"'"
					+","
					+"'"
					+(String)departments.getSelectedValue()
					+"'"
					+","
					+"'"
					+doctorSpecHolder.getText()
					+"'"
					+","
					+"'"
					+doctorExperienceHolder.getText()
					+"'"
					+")";
		}
		//insert a condition for blocking query execution if mandatory fields are empty
		DBBrowse.executeUpdate(DBConnector, query);
	}
	
	public void writeEntityToFile(){
		person = new Person(
				super.getInputFirstNameHolder().getText(),
				super.getInputLastNameHolder().getText(),
				super.getInputAgeHolder().getText(),
				super.getInputPersonSexHolder().getText(),
				super.getInputIDHolder().getText(),
				super.getInputHomeAddressHolder().getText(),
				super.getInputPhoneNumberHolder().getText(),
				super.getInputEmailAddressHolder().getText()
				);
		doctor = new Doctor(
				doctorSpecHolder.getText(),
				(String)departments.getSelectedValue(),
				doctorExperienceHolder.getText()
				);
		
		saveEntity = new SaveEntityToFile();
		saveEntity.writeDoctorToFile(person,doctor);
	}
}
