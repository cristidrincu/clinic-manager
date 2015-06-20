package CONSTANTS;

import MODEL.DatabaseBrowse;

public class InitialAppPanelsQueryConstants {
	
	//initial queries in order to populate initial tables when the app is started
	
	private final String SELECT_LATEST_PATIENTS= "SELECT patientId, firstName, lastName, address, sex, age, idCard, phoneNumber, email, occupation FROM clinic.Patients";//id = 1 //all latest pacients, sorted by date
	private final String SELECT_LATEST_DIAGNOSTICS = "SELECT idDiagnostic, name, analysis FROM clinic.Diagnostics";//id = 2 //all latest diagnostics, sorted by date
	private final String SELECT_LATEST_DOCTOR_ACTIVITY = "SELECT * FROM clinic.Departments";// id = 4 //all doctor activity
	private final String SELECT_ALL_PACIENTS_ALPHA = "SELECT * FROM clinic.Patients ORDER BY lastName"; // id = 5 //all pacients
	private final String SELECT_ALL_DOCTORS_ALPHA = "SELECT * FROM clinic.Employee ORDER BY firstName, lastName"; // id = 6 //all doctors
	private final String SELECT_ALL_CLEANING_ALPHA = "SELECT * FROM clinic.Jobs ORDER BY jobTitle";//id = 8 - all cleaning personnel
	private final String SELECT_ALL_DEPARTMENTS_ALPHA = "SELECT * FROM clinic.Departments ORDER BY name"; //ID = 10 - all departments
	private final String SELECT_EMAIL_ADDRESSES = "SELECT email FROM clinic.Employee"; //ID = 11
	private final String SELECT_ALL_DIAGNOSTICS = "SELECT idDiagnostic, name, analysis FROM clinic.Diagnostics";//id = 12
	private final String SELECT_ALL_PRESCRIPTIONS = "SELECT idPrescription, prescriptionDate, summary FROM clinic.Prescriptions";//id=13
	
//	Must review these statements
//	private final String SELECT_SUPPLY_STOCK = "SELECT * FROM HR.COUNTRIES";//id = 3 //all supply stock
//	private final String SELECT_ALL_ASSISTANTS_ALPHA = "SELECT * FROM HR.COUNTRIES ORDER BY COUNTRY_NAME";//id = 7 //all assistants
//	private final String SELECT_ALL_SUPPLIERS_ALPHA = "SELECT * FROM HR.REGIONS ORDER BY REGION_NAME"; //id = 9 - all suppliers
	
	//getters and setters
	public String getSELECT_LATEST_PACIENTS() {
		return SELECT_LATEST_PATIENTS;
	}

	public String getSELECT_LATEST_DIAGNOSTICS() {
		return SELECT_LATEST_DIAGNOSTICS;
	}

//	public String getSELECT_SUPPLY_STOCK() {
//		return SELECT_SUPPLY_STOCK;
//	}

	public String getSELECT_LATEST_DOCTOR_ACTIVITY() {
		return SELECT_LATEST_DOCTOR_ACTIVITY;
	}

	public String getSELECT_ALL_PACIENTS_ALPHA() {
		return SELECT_ALL_PACIENTS_ALPHA;
	}

	public String getSELECT_ALL_DOCTORS_ALPHA() {
		return SELECT_ALL_DOCTORS_ALPHA;
	}

//	public String getSELECT_ALL_ASSISTANTS_ALPHA() {
//		return SELECT_ALL_ASSISTANTS_ALPHA;
//	}

	public String getSELECT_ALL_CLEANING_ALPHA() {
		return SELECT_ALL_CLEANING_ALPHA;
	}

//	public String getSELECT_ALL_SUPPLIERS_ALPHA() {
//		return SELECT_ALL_SUPPLIERS_ALPHA;
//	}

	public String getSELECT_ALL_DEPARTMENTS_ALPHA() {
		return SELECT_ALL_DEPARTMENTS_ALPHA;
	}
	
	public String getSELECT_EMAIL_ADDRESSES(){
		return SELECT_EMAIL_ADDRESSES;
	}

	public String getSELECT_ALL_DIAGNOSTICS() {
		return SELECT_ALL_DIAGNOSTICS;
	}

	public String getSELECT_ALL_PRESCRIPTIONS() {
		return SELECT_ALL_PRESCRIPTIONS;
	}
}
