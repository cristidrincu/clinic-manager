package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditDoctorDB extends EditEntityDatabaseVer2 {
	
	private JPanel containerEmployeeDepartment;
	private JPanel containerEmployeeSpecialization;
	private JPanel containerEmployeeExperience;
	
	private String doctorDepartmentField;
	private String doctorSpecializationField;
	private String doctorExperienceField;
	
	private JTextField employeeDepartment = new JTextField();
	private JTextField employeeSpecialization = new JTextField();
	private JTextField employeeExperience = new JTextField();
	
	public EditDoctorDB(){
		textFieldsInfoContainer.setPreferredSize(new Dimension(200,490));//increase the height of the textfields container to 490 in order to accomdate 3 text fields for doctors
		
		pacientsListContainerLabel="Select a doctor from the list below: ";
		pacientsListContainer.add(new JLabel(pacientsListContainerLabel), BorderLayout.PAGE_START);
		
		//connect to database in order to create the model for the doctor list
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		selectPersonQuery = "SELECT LAST_NAME FROM HR.EMPLOYEE ORDER BY LAST_NAME";
		scrollPane = new JScrollPane(this.createVectorModelForList());
		pacientsListContainer.add(scrollPane);
		
		//add the new elements to the split pane and the split pane to the main container
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pacientsListContainer,textFieldsInfoContainer);
		mainContainer.add(splitPane);
		
		//create ui elements specific to EditDoctorDB class
		containerEmployeeDepartment = new JPanel();
		containerEmployeeDepartment.setLayout(new BorderLayout());
		containerEmployeeDepartment.add(new JLabel("Department:"), BorderLayout.PAGE_START);
		containerEmployeeDepartment.add(employeeDepartment, BorderLayout.CENTER);

		containerEmployeeSpecialization = new JPanel();
		containerEmployeeSpecialization.setLayout(new BorderLayout());
		containerEmployeeSpecialization.add(new JLabel("Specialization:"), BorderLayout.PAGE_START);
		containerEmployeeSpecialization.add(employeeSpecialization, BorderLayout.CENTER);

		containerEmployeeExperience = new JPanel();
		containerEmployeeExperience.setLayout(new BorderLayout());
		containerEmployeeExperience.add(new JLabel("Experience"), BorderLayout.PAGE_START);
		containerEmployeeExperience.add(employeeExperience, BorderLayout.CENTER);
		
		//add elements to the textFieldsInfoContainer
		textFieldsInfoContainer.add(containerEmployeeDepartment);
		textFieldsInfoContainer.add(containerEmployeeSpecialization);
		textFieldsInfoContainer.add(containerEmployeeExperience);
				
		
	}
	
	//class methods
	public JList createVectorModelForList(){
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		personListVector = dbBrowse.createVectorModel(dbConnector, selectPersonQuery, "LAST_NAME", personListVector);
		personList = new JList(personListVector);
		personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		personList.addListSelectionListener(this);
		return personList;
	}
	
	@Override
	public void executeEdit() {
		Object[] component = {mainContainer};
		Object[] buttons = {"OK","Cancel"};
		
		int okOption=JOptionPane.showOptionDialog(null, component, "Edit a doctor!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons,
		        null);
		if(okOption==JOptionPane.OK_OPTION){
			updateQuery = "UPDATE HR.EMPLOYEE SET FIRST_NAME="+
					"'"+personFirstName.getText()+"'"+","+"LAST_NAME="+"'"+personLastName.getText()+"'"+","+
					"ADDRESS="+"'"+personAddress.getText()+"'"+","+
					"SEX="+"'"+personSex.getText()+"'"+","+
					"AGE="+"'"+personAge.getText()+"'"+","+
					"CARD_ID="+"'"+personIDCard.getText()+"'"+","+
					"PHONE_NUMBER="+"'"+personPhoneNumber.getText()+"'"+","+
					"EMAIL_ADDRESS="+"'"+personEmail.getText()+"'"+","+
					"DEPARTMENT="+"'"+employeeDepartment.getText()+"'"+","+
					"SPECIALIZATION="+"'"+employeeSpecialization.getText() +"'"+","+
					"EXPERIENCE="+"'"+employeeExperience.getText()+"'"+
					"WHERE EMPLOYEE_ID="+"'"+personID+"'";
					dbBrowse.executeUpdate(dbConnector, updateQuery);
		}else if(okOption==JOptionPane.CANCEL_OPTION){
			return;
		}	
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			personLastNameFromList = (String)personList.getSelectedValue();
			personID = dbBrowse.getEmployeeID(dbConnector, personLastNameFromList);
			//doctorDepartmentID = dbBrowse.getDepartmentIDEmployee(dbConnector, personID, "HR.EMPLOYEE");
			//set text field with doctor's first name
			personFirstNameField = dbBrowse.getEntityDetails("FIRST_NAME", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personFirstName.setText(personFirstNameField);
			//set text field with doctor's last name
			personLastNameField = dbBrowse.getEntityDetails("LAST_NAME", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personLastName.setText(personLastNameField);
			//set text field with doctor's address
			personAddressField = dbBrowse.getEntityDetails("ADDRESS", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personAddress.setText(personAddressField);
			//set text field with doctor's sex
			personSexField = dbBrowse.getEntityDetails("SEX", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personSex.setText(personSexField);
			//set text field with doctor's age
			personAgeField = dbBrowse.getEntityDetails("AGE", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personAge.setText(personAgeField);
			//set text field with doctor's ID Card
			personIDCardField = dbBrowse.getEntityDetails("CARD_ID", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personIDCard.setText(personIDCardField);
			//set text field with doctor's phone number
			personPhoneNumberField = dbBrowse.getEntityDetails("PHONE_NUMBER", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personPhoneNumber.setText(personPhoneNumberField);
			//set text field with doctor's email address
			personEmailField = dbBrowse.getEntityDetails("EMAIL_ADDRESS", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			personEmail.setText(personEmailField);
//			//set text field with doctor's department
			doctorDepartmentField = dbBrowse.getEntityDetails("DEPARTMENT", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			employeeDepartment.setText(doctorDepartmentField);
			//set text field with doctor specialization
			doctorSpecializationField = dbBrowse.getEntityDetails("SPECIALIZATION", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			employeeSpecialization.setText(doctorSpecializationField);
			//set text field with doctor's experience
			doctorExperienceField = dbBrowse.getEntityDetails("EXPERIENCE", "HR.EMPLOYEE", "EMPLOYEE_ID", dbConnector, personID);
			employeeExperience.setText(doctorExperienceField);
		}
		
	}

	
}
