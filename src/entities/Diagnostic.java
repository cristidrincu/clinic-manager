package entities;

public class Diagnostic {
	private String diagnosticID;
	private String diagnosticName;
	private String diagnosticAnalysis;
	private String pacientID;
	private String employeeID;
	
	public Diagnostic(String diagnosticID){
		this.diagnosticID = diagnosticID;
	}
	
	public Diagnostic(String diagnosticID, String diagnosticName, String diagnosticAnalysis, String pacientID, String employeeID){
		this.diagnosticID=diagnosticID;
		this.diagnosticName = diagnosticName;
		this.diagnosticAnalysis=diagnosticAnalysis;
		this.pacientID = pacientID;
		this.employeeID=employeeID;
	}
	
	public Diagnostic(String diagnosticName, String diagnosticAnalysis){
		this.diagnosticName=diagnosticName;
		this.diagnosticAnalysis=diagnosticAnalysis;
	}

	
	//getters and setters
	public String getDiagnosticID() {
		return diagnosticID;
	}

	public String getDiagnosticName() {
		return diagnosticName;
	}

	public String getDiagnosticAnalysis() {
		return diagnosticAnalysis;
	}

	public String getPacientID() {
		return pacientID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setDiagnosticID(String diagnosticID) {
		this.diagnosticID = diagnosticID;
	}

	public void setDiagnosticName(String diagnosticName) {
		this.diagnosticName = diagnosticName;
	}

	public void setDiagnosticAnalysis(String diagnosticAnalysis) {
		this.diagnosticAnalysis = diagnosticAnalysis;
	}

	public void setPacientID(String pacientID) {
		this.pacientID = pacientID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
}
