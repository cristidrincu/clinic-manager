package ACTIONS;

import java.awt.event.ActionEvent;

import VIEW.MainWindowInterface;
import VIEW.PanelAppointmentsCertainDoctor;
import VIEW.PanelPacientsAgeRange;
import VIEW.PanelPacientsCertainDiagnostic;
import VIEW.PanelPacientsCertainOccupation;
import VIEW.PanelPacientsCertainPrescription;
import VIEW.PanelPrescriptionsCertainDiagnostic;
import VIEW.PanelPrescriptionsCertainDoctor;

public class GenerateReportsActions extends ActionBaseClass {

	private String actionID;
	private PanelPacientsCertainDiagnostic pacientDiagnostic;
	private PanelPacientsCertainPrescription pacientPrescription;
	private PanelPrescriptionsCertainDiagnostic prescriptionDiagnostic;
	private PanelPrescriptionsCertainDoctor prescriptionCertainDoctor;
	private PanelAppointmentsCertainDoctor appointmentsCertainDoctor;
	private PanelPacientsCertainOccupation pacientOccupation;
	private PanelPacientsAgeRange pacientsAgeRange;

	public GenerateReportsActions(String actionName){
		super(actionName);
	}
	
	public GenerateReportsActions(String actionName, String tooltip, String actionID){
		this(actionName);
		if(tooltip!=null){
			putValue(SHORT_DESCRIPTION, tooltip);
		}
		this.actionID=actionID;
	}
	
	public GenerateReportsActions(String actionName, String actionID){
		super(actionName);
		this.actionID=actionID;
	}
	
	//getters and setters
	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(MainWindowInterface.getTabOpenedFlag()==0){
			MainWindowInterface.getWelcomeTab().removeAll();//removes all tabs on the welcome tab
			MainWindowInterface.removeComponent(MainWindowInterface.getWelcomeTab());//by default, this is the tab that is loaded and we remove it if it is opened
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getReportsTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(3);
		}else if(MainWindowInterface.getTabOpenedFlag()==1){//remove main tab
			MainWindowInterface.getMainTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getMainTab());
			ViewActions.setExistingDepartmentsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDiagnosticsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDoctorsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPacientsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPrescriptionsTabView(1);//reset the existing tab counter
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getReportsTab());
			MainWindowInterface.setTabOpenedFlag(3);
		}else if(MainWindowInterface.getTabOpenedFlag()==2){
			MainWindowInterface.getOpenedFilesTab().removeAll();//remove all tabs on the files container 
			MainWindowInterface.removeComponent(MainWindowInterface.getOpenedFilesTab());//this is the files tab, and we remove it if it's opened
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getMainTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(3);
		}else if(MainWindowInterface.getTabOpenedFlag()==4){//remove quick search tab
			MainWindowInterface.getQuickSearchTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getQuickSearchTab());
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getReportsTab());
			MainWindowInterface.setTabOpenedFlag(3);
		}
		switch(actionID){
			case "getPacientsWithDiagnostic":
				pacientDiagnostic = new PanelPacientsCertainDiagnostic();
				MainWindowInterface.getReportsTab().addTab("Results for pacients suffering from "+pacientDiagnostic.getDiagnosticsList().getSelectedValue(), PanelPacientsCertainDiagnostic.getPanelPacientsCertDiag());
				//MainWindowInterface.getReportsTab().setVisible(true);
				break;
			case "getPacientsWithSubscription":
				pacientPrescription = new PanelPacientsCertainPrescription();
				MainWindowInterface.getReportsTab().addTab("Prescription results for pacient "+pacientPrescription.getPacientsList().getSelectedValue(), pacientPrescription.getDbBrowse().getPane());
				//MainWindowInterface.getMainTab().setVisible(true);
				break;
			case "getPacientsForOccupation":
				pacientOccupation = new PanelPacientsCertainOccupation();
				MainWindowInterface.getReportsTab().addTab("Results for pacients with occupation: "+pacientOccupation.getOccupationsList().getSelectedValue(),PanelPacientsCertainOccupation.getPacientsCertainOccup());
				break;
			case "getPacientsAgeRange":
				pacientsAgeRange = new PanelPacientsAgeRange();
				MainWindowInterface.getReportsTab().addTab("Results for pacients in a user supplied age range " , PanelPacientsAgeRange.getPacientsCertainAge());
				break;
			case "getPrescriptionsForDiagnostic":
				prescriptionDiagnostic = new PanelPrescriptionsCertainDiagnostic();
				MainWindowInterface.getReportsTab().add("Prescription results for diagnostic "+prescriptionDiagnostic.getDiagnosticsList().getSelectedValue(), PanelPrescriptionsCertainDiagnostic.getPrescriptionsCertDiag());
				//MainWindowInterface.getMainTab().setVisible(true);
				break;
			case "getPrescriptionsForDoctor":
				prescriptionCertainDoctor = new PanelPrescriptionsCertainDoctor();
				MainWindowInterface.getReportsTab().addTab("Prescription results for doctor "+prescriptionCertainDoctor.getDoctorsList().getSelectedValue(), PanelPrescriptionsCertainDoctor.getPrescriptionsCertDoc());
				//MainWindowInterface.getMainTab().setVisible(true);
				break;
			case "getAppointmentsForDoctor":
				appointmentsCertainDoctor = new PanelAppointmentsCertainDoctor();
				MainWindowInterface.getReportsTab().addTab("Appointments results for doctor "+appointmentsCertainDoctor.getDoctorsList().getSelectedValue(), PanelAppointmentsCertainDoctor.getPanelAppointmentsDoc());
				//MainWindowInterface.getMainTab().setVisible(true);
				break;
		}
		MainWindowInterface.setTabOpenedFlag(3);
	}
}
