package ACTIONS;

import java.awt.event.ActionEvent;
import MODEL.AppointmentApp;
import MODEL.InsertDoctorIntoDatabase;
import MODEL.InsertDiagnosticIntoDatabase;
import MODEL.InsertEntityIntoDatabase;
import MODEL.InsertPacientIntoDatabase;
import MODEL.InsertPrescriptionIntoDatabase;
import MODEL.SendEmailClient;

public class NewActions extends ActionBaseClass {
	
	private String actionID;
	private InsertEntityIntoDatabase dialogBox;
	private InsertDiagnosticIntoDatabase diagnostic;
	private SendEmailClient emailClient;
	private AppointmentApp appointment;
	private InsertPrescriptionIntoDatabase insertPrescription;
	
	//class constructor
	public NewActions(String actionName){
		super(actionName);
	}
	
	public NewActions(String actionName, String tooltip, String actionID){
		this(actionName);
		if(tooltip!=null){
			putValue(SHORT_DESCRIPTION, tooltip);
		}
		this.actionID=actionID;
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){	
		switch(actionID){
		case "createNewPacientObject":
			//setDialogBox(new InsertPacientIntoDatabase());
			dialogBox = new InsertPacientIntoDatabase();
			dialogBox.executeInsert();
			dialogBox.writeEntityToFile();
			break;
		case "createDiagnostic":
			setDiagnostic(new InsertDiagnosticIntoDatabase());
			break;
		case "createDoctorObject":
			dialogBox = new InsertDoctorIntoDatabase();
			dialogBox.executeInsert();
			dialogBox.writeEntityToFile();
			break;
		case "createEmailClient":
			emailClient = new SendEmailClient();
			emailClient.sendEmail();
			break;
		case "createAppointment":
			appointment = new AppointmentApp();
			appointment.executeInsertAppointment();
			break;
		case "createPrescription":
			insertPrescription = new InsertPrescriptionIntoDatabase();
			insertPrescription.executeInsert();
			break;
		}
	}

	//getters and setters
	public InsertEntityIntoDatabase getDialogBox() {
		return dialogBox;
	}

	public void setDialogBox(InsertEntityIntoDatabase dialogBox) {
		this.dialogBox = dialogBox;
	}

	public InsertDiagnosticIntoDatabase getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(InsertDiagnosticIntoDatabase diagnostic) {
		this.diagnostic = diagnostic;
	}

	public AppointmentApp getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentApp appointment) {
		this.appointment = appointment;
	}

	public InsertPrescriptionIntoDatabase getInsertPrescription() {
		return insertPrescription;
	}

	public void setInsertPrescription(
			InsertPrescriptionIntoDatabase insertPrescription) {
		this.insertPrescription = insertPrescription;
	}
}
