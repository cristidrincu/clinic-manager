package ACTIONS;

import java.awt.event.ActionEvent;

import MODEL.EditDepartmentDatabase;
import MODEL.EditDiagnosticDatabase;
import MODEL.EditDoctorDB;
import MODEL.EditPacientDB;
//import MODEL.EditDoctorDatabase;
import MODEL.EditEntityDatabaseVer2;

public class EditActions extends ActionBaseClass {
	
	private EditDoctorDB editDoctor;
	private EditDepartmentDatabase editDep;
	private EditDiagnosticDatabase editDiag;
	private EditEntityDatabaseVer2 editPerson;
	private String actionID;
	
	//class constructor
	public EditActions(String actionName, String tooltip, String actionID){
		super(actionName);
		this.actionID=actionID;
		if(tooltip!=null){
			putValue(SHORT_DESCRIPTION, tooltip);
		}
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){
		switch(actionID){
			case "editPacientData":
				editPerson = new EditPacientDB();
				editPerson.executeEdit();
				break;
			case "editDoctorData":
				editPerson = new EditDoctorDB();
				editPerson.executeEdit();
				break;
			case "editDepartmentData":
				editDep = new EditDepartmentDatabase();
				editDep.executeEdit();
				break;
			case "editDiagnosticData":
				editDiag = new EditDiagnosticDatabase();
				editDiag.executeEdit();
				break;
		}
	}
	
	
}
