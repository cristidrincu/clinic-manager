package CONSTANTS;

import MODEL.DatabaseBrowse;

public class InitialAppPanelsQueryConstants {
	
	//initial queries in order to populate initial tables when the app is started
	
	private final String SELECT_LATEST_PACIENTS= "SELECT PACIENT_ID, FIRST_NAME,LAST_NAME,ADDRESS,SEX,AGE,ID_CARD,PHONE_NUMBER,EMAIL,OCCUPATION FROM HR.PACIENTS";//id = 1 //all latest pacients, sorted by date
	private final String SELECT_LATEST_DIAGNOSTICS = "SELECT DIAGNOSTIC_ID,NAME,ANALYSIS FROM HR.DIAGNOSTICS";//id = 2 //all latest diagnostics, sorted by date
	private final String SELECT_SUPPLY_STOCK = "SELECT * FROM HR.COUNTRIES";//id = 3 //all supply stock
	private final String SELECT_LATEST_DOCTOR_ACTIVITY = "SELECT * FROM HR.DEPARTMENTS";// id = 4 //all doctor activity
	
	private final String SELECT_ALL_PACIENTS_ALPHA = "SELECT*FROM HR.PACIENTS ORDER BY LAST_NAME"; // id = 5 //all pacients
	private final String SELECT_ALL_DOCTORS_ALPHA = "SELECT * FROM HR.EMPLOYEE ORDER BY FIRST_NAME,LAST_NAME"; // id = 6 //all doctors
	private final String SELECT_ALL_ASSISTANTS_ALPHA = "SELECT * FROM HR.COUNTRIES ORDER BY COUNTRY_NAME";//id = 7 //all assistants
	private final String SELECT_ALL_CLEANING_ALPHA = "SELECT * FROM HR.JOBS ORDER BY JOB_TITLE";//id = 8 - all cleaning personnel
	private final String SELECT_ALL_SUPPLIERS_ALPHA = "SELECT * FROM HR.REGIONS ORDER BY REGION_NAME"; //id = 9 - all suppliers
	private final String SELECT_ALL_DEPARTMENTS_ALPHA = "SELECT * FROM HR.DEPARTMENT ORDER BY NAME"; //ID = 10 - all departments
	
	private final String SELECT_EMAIL_ADDRESSES = "SELECT EMAIL FROM HR.EMPLOYEES"; //ID = 11
	
	private final String SELECT_ALL_DIAGNOSTICS = "SELECT DIAGNOSTIC_ID,NAME,ANALYSIS FROM HR.DIAGNOSTICS";//id = 12
	private final String SELECT_ALL_PRESCRIPTIONS = "SELECT PRESCRIPTION_ID,PRESCRIPTION_DATE, SUMMARY FROM HR.PRESCRIPTIONS";//id=13
	
	//getters and setters
	public String getSELECT_LATEST_PACIENTS() {
		return SELECT_LATEST_PACIENTS;
	}

	public String getSELECT_LATEST_DIAGNOSTICS() {
		return SELECT_LATEST_DIAGNOSTICS;
	}

	public String getSELECT_SUPPLY_STOCK() {
		return SELECT_SUPPLY_STOCK;
	}

	public String getSELECT_LATEST_DOCTOR_ACTIVITY() {
		return SELECT_LATEST_DOCTOR_ACTIVITY;
	}

	public String getSELECT_ALL_PACIENTS_ALPHA() {
		return SELECT_ALL_PACIENTS_ALPHA;
	}

	public String getSELECT_ALL_DOCTORS_ALPHA() {
		return SELECT_ALL_DOCTORS_ALPHA;
	}

	public String getSELECT_ALL_ASSISTANTS_ALPHA() {
		return SELECT_ALL_ASSISTANTS_ALPHA;
	}

	public String getSELECT_ALL_CLEANING_ALPHA() {
		return SELECT_ALL_CLEANING_ALPHA;
	}

	public String getSELECT_ALL_SUPPLIERS_ALPHA() {
		return SELECT_ALL_SUPPLIERS_ALPHA;
	}

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
