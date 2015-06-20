package entities;

public class Doctor extends Person {

	//private PacientCases cases[];
	//private Pacient pacients[];
	private String department;
	private String specializedIn;
	private String experience;
	
	//default constructor
	public Doctor(){}
	
	//detailed constructor
	public Doctor(String specializedN, String departmentN, String experienceN){
		this.specializedIn = specializedN;
		this.department = departmentN;
		this.experience = experienceN;
	}

	public String getSpecializedIn() {
		return specializedIn;
	}

	public String getExperience() {
		return experience;
	}

	public void setSpecializedIn(String specializedIn) {
		this.specializedIn = specializedIn;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
	
	
}
