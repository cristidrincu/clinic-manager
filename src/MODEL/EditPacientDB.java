package MODEL;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class EditPacientDB extends EditEntityDatabaseVer2 {
	
	private JPanel containerOccupation = new JPanel();
	
	public EditPacientDB(){
		pacientsListContainerLabel="Select a pacient from the list below: ";
		pacientsListContainer.add(new JLabel(pacientsListContainerLabel), BorderLayout.PAGE_START);
		
		containerOccupation.setLayout(new BorderLayout());
		containerOccupation.add(new JLabel("Occupation:"), BorderLayout.PAGE_START);
		containerOccupation.add(personOccupation, BorderLayout.CENTER);
		
		textFieldsInfoContainer.add(containerOccupation);
		
		selectPersonQuery = "SELECT LAST_NAME FROM HR.PACIENTS ORDER BY LAST_NAME";
		scrollPane = new JScrollPane(this.createVectorModelForList());
		pacientsListContainer.add(scrollPane);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pacientsListContainer,textFieldsInfoContainer);
		mainContainer.add(splitPane);
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
	
	//method for updating values in text fields when list entities are selected
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			personLastNameFromList = (String)personList.getSelectedValue();
			personID = dbBrowse.getPacientID(dbConnector, personLastNameFromList);
			//set text field with pacient's first name
			personFirstNameField = dbBrowse.getEntityDetails("FIRST_NAME", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personFirstName.setText(personFirstNameField);
			//set text field with pacient's last name
			personLastNameField = dbBrowse.getEntityDetails("LAST_NAME", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personLastName.setText(personLastNameField);
			//set text field with pacient's address
			personAddressField = dbBrowse.getEntityDetails("ADDRESS", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personAddress.setText(personAddressField);
			//set text field with pacient's sex
			personSexField = dbBrowse.getEntityDetails("SEX", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personSex.setText(personSexField);
			//set text field with pacient's age
			personAgeField = dbBrowse.getEntityDetails("AGE", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personAge.setText(personAgeField);
			//set text field with pacient's ID Card
			personIDCardField = dbBrowse.getEntityDetails("ID_CARD", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personIDCard.setText(personIDCardField);
			//set text field with pacient's phone number
			personPhoneNumberField = dbBrowse.getEntityDetails("PHONE_NUMBER", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personPhoneNumber.setText(personPhoneNumberField);
			//set text field with pacient's email address
			personEmailField = dbBrowse.getEntityDetails("EMAIL", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personEmail.setText(personEmailField);
			//set text field with pacient's occupation
			personOccupationField = dbBrowse.getEntityDetails("OCCUPATION", "HR.PACIENTS", "PACIENT_ID", dbConnector, personID);
			personOccupation.setText(personOccupationField);
		}		
	}
	
	public void executeEdit(){
		Object[] component = {mainContainer};
		Object[] buttons = {"OK","Cancel"};
		
		int okOption=JOptionPane.showOptionDialog(null, component, "Edit a pacient!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons,
		        null);
		
		if(okOption==JOptionPane.OK_OPTION){
			updateQuery = "UPDATE HR.PACIENTS SET FIRST_NAME="+
			"'"+personFirstName.getText()+"'"+","+"LAST_NAME="+"'"+personLastName.getText()+"'"+","+
			"ADDRESS="+"'"+personAddress.getText()+"'"+","+
			"SEX="+"'"+personSex.getText()+"'"+","+
			"AGE="+"'"+personAge.getText()+"'"+","+
			"ID_CARD="+"'"+personIDCard.getText()+"'"+","+
			"PHONE_NUMBER="+"'"+personPhoneNumber.getText()+"'"+","+
			"EMAIL="+"'"+personEmail.getText()+"'"+","+
			"OCCUPATION="+"'"+personOccupation.getText()+"'"+
			"WHERE PACIENT_ID="+"'"+personID+"'";
			dbBrowse.executeUpdate(dbConnector, updateQuery);
		}else if(okOption==JOptionPane.CANCEL_OPTION){
			return;
		}
	}
}
