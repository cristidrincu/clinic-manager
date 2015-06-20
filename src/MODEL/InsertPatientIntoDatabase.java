package MODEL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Person;

import MODEL.DatabaseConnector;

public class InsertPatientIntoDatabase extends InsertEntityIntoDatabase {
	
	private Box pacientBox;
	
	private JPanel mainContainer;
	
	//pacient text fields and other components	
	private JTextField pacientOccupationHolder;
	private JTextField pacientDiagnosticsHolder;
	
	//pacient labels	
	private JLabel pacientOccupationLabel;
	private JLabel pacientDiagnosticsLabel;
	
	//database connector
	private DatabaseConnector DBConnector;
	private DatabaseBrowse DBBrowse;
	private String query;
	
	//Person entity;
	private Person person;
	
	//save to file when inserting object in database
	private SaveEntityToFile saveEntity;
	
	public InsertPatientIntoDatabase(){
		pacientBox = Box.createVerticalBox();		
				
		pacientOccupationLabel = new JLabel("Pacient Occupation: ");
		pacientDiagnosticsLabel = new JLabel("Pacient Diagnostics: ");
		
		pacientOccupationHolder = new JTextField();
		pacientDiagnosticsHolder = new JTextField();
				
		pacientBox.add(pacientOccupationLabel);
		pacientBox.add(Box.createHorizontalStrut(5));
		pacientBox.add(pacientOccupationHolder);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
	
		mainContainer.add(super.getInputBox());
		mainContainer.add(pacientBox);
		}
	
	//getters and setters
	public JTextField getPacientOccupationHolder() {
		return pacientOccupationHolder;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public void executeInsert() {
		Object msg[] = {mainContainer};
		Object type[] = {"Insert", "Cancel"};
		
		int okBtnOptionPane=JOptionPane.showOptionDialog(null, msg, "Insert NEW PACIENT OBJECT IN THE DATABASE!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
		if(okBtnOptionPane==JOptionPane.OK_OPTION){
				//insert into database text field values
				query = "INSERT INTO clinic.Patients(firstName, lastName, address, sex, age, idCard, phoneNumber, email, occupation) VALUES("
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
						+this.getPacientOccupationHolder().getText()
						+"'"
						+")";
				//connect to database and execute query
				DBConnector = new DatabaseConnector();
				DBBrowse = new DatabaseBrowse();
				DBBrowse.executeUpdate(DBConnector, query);
		}
		
	}
	@Override
	public void writeEntityToFile() {
		//get the data from the textfields and create a person object with them
		person = new Person(
				super.getInputFirstNameHolder().getText(),
				super.getInputLastNameHolder().getText(),
				super.getInputAgeHolder().getText(),
				super.getInputPersonSexHolder().getText(),
				super.getInputIDHolder().getText(),
				super.getInputHomeAddressHolder().getText(),
				super.getInputPhoneNumberHolder().getText(),
				super.getInputEmailAddressHolder().getText(),
				this.getPacientOccupationHolder().getText()
				);
		
		saveEntity = new SaveEntityToFile();
		saveEntity.writePacientToFile(person);
		
	}
}
