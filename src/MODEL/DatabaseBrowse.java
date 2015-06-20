package MODEL;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import CONSTANTS.InitialAppPanelsQueryConstants;

public class DatabaseBrowse extends JPanel {
	
	//class members
	private ResultsModel theModel;
	private ResultSet resultSet;
	private InitialAppPanelsQueryConstants queryConstant;
	private Connection conn;
	private PreparedStatement stmt;
	
	private static JTable table;
	private JScrollPane pane;
	
	//simple constructor of type DatabaseBrowse
	public DatabaseBrowse(){}
	
	//class constructor for usage when displaying initial database data at application welcome screen
	public DatabaseBrowse(DatabaseConnector databaseConnector,int queryID){	//this constructor is used for default tables - e.g. show latest pacients etc
		//create a ResultsModel object/reference + table+pane
		theModel = new ResultsModel();
		queryConstant = new InitialAppPanelsQueryConstants();
		
		try{			
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			//statement = conn.createStatement();
			switch(queryID){
				case 1:
					stmt = conn.prepareStatement(queryConstant.getSELECT_LATEST_PACIENTS());
					resultSet = stmt.executeQuery(queryConstant.getSELECT_LATEST_PACIENTS());
					theModel.setResultSet(resultSet);
				break;
				case 2:
					stmt = conn.prepareStatement(queryConstant.getSELECT_LATEST_DIAGNOSTICS());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
//				case 3:
//					stmt = conn.prepareStatement(queryConstant.getSELECT_SUPPLY_STOCK());
//					resultSet = stmt.executeQuery();
//					theModel.setResultSet(resultSet);
//					break;
				case 4:
					stmt = conn.prepareStatement(queryConstant.getSELECT_LATEST_DOCTOR_ACTIVITY());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
				case 5:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_PACIENTS_ALPHA());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
				case 6:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_DOCTORS_ALPHA());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
//				case 7:
//					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_ASSISTANTS_ALPHA());
//					resultSet = stmt.executeQuery();
//					theModel.setResultSet(resultSet);
//					break;
				case 8:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_CLEANING_ALPHA());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
//				case 9:
//					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_SUPPLIERS_ALPHA());
//					resultSet = stmt.executeQuery();
//					theModel.setResultSet(resultSet);
//					break;
				case 10:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_DEPARTMENTS_ALPHA());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
				case 12:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_DIAGNOSTICS());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
				case 13:
					stmt = conn.prepareStatement(queryConstant.getSELECT_ALL_PRESCRIPTIONS());
					resultSet = stmt.executeQuery();
					theModel.setResultSet(resultSet);
					break;
			}
			
			table = new JTable(theModel);
			table.setRowHeight(40);
			pane = new JScrollPane(table);
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}
		
		try{
			conn.close();
			System.out.println("Connection closed for query ID " + queryID);
		}catch(SQLException sqle){
			System.out.println(sqle);
		}
	}
	
	//constructor that accepts a random query for sorting entities in the database, based on user option
	public DatabaseBrowse(DatabaseConnector databaseConnector, String query){
			//create a ResultsModel object/reference + table+pane
			theModel = new ResultsModel();
			queryConstant = new InitialAppPanelsQueryConstants();		
			try{
				Class.forName(databaseConnector.getDatabaseDriver());
				conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
				stmt = conn.prepareStatement(query);
				resultSet = stmt.executeQuery();
				theModel.setResultSet(resultSet);
				table = new JTable(theModel);
				table.setRowHeight(40);
				pane = new JScrollPane(table);
			}catch(ClassNotFoundException cnfe){
				JOptionPane.showMessageDialog(null, cnfe.getMessage());
				System.out.println(cnfe);
			}
			catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getMessage());
				System.out.println(sqle.getMessage());
			}
			
			try{
				conn.close();
				//System.out.println("Connection closed for sorting database browse object!");
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
				System.out.println(sqle);
			}
			
	}
	
	
	//method for updating, deleting, inserting, sorting etc in the database
	public void executeUpdate(DatabaseConnector databaseConnector,String query){
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Database updated succesfully and connection closed!");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for updating database!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
	}
	
	//method for getting a resultset object in order to create models for lists etc - the vector is the model, actually
	public Vector<String> createVectorModel(DatabaseConnector databaseConnector,String query,String columnName, Vector<String> vector){
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				vector.add(resultSet.getString(columnName));
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		
		return vector;
	}
	
	public int getEmployeeID(DatabaseConnector databaseConnector, String lastNameEmployee){
		String query = "SELECT EMPLOYEE_ID FROM HR.EMPLOYEE WHERE LAST_NAME="+"'"+lastNameEmployee+"'";
		int employeeID = 0;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				employeeID = resultSet.getInt("EMPLOYEE_ID");
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return employeeID;
	}
	
	public int getPacientID(DatabaseConnector databaseConnector, String lastNamePacient){
		String query = "SELECT PACIENT_ID FROM HR.PACIENTS WHERE LAST_NAME="+"'"+lastNamePacient+"'";
		int pacientID = 0;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				pacientID = resultSet.getInt("PACIENT_ID");
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return pacientID;
	}
	
	public int getDepartmentID(DatabaseConnector databaseConnector, String departmentName){
		String query = "SELECT DEPARTMENT_ID FROM HR.DEPARTMENT WHERE NAME="+"'"+departmentName+"'";
		int departmentID = 0;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				departmentID = resultSet.getInt("DEPARTMENT_ID");
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return departmentID;
	}
	
	public int getDepartmentIDEmployee(DatabaseConnector databaseConnector, int entityID, String tableName){
		String query = "SELECT DEPARTMENT_ID FROM "+tableName+" WHERE EMPLOYEE_ID="+"'"+entityID+"'";
		int entityName=0;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				entityName = resultSet.getInt("DEPARTMENT_ID");
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return entityName;
	}
	
	public int getDiagnosticID(DatabaseConnector databaseConnector, String diagnosticName){
		String query = "SELECT DIAGNOSTIC_ID FROM HR.DIAGNOSTICS WHERE NAME="+"'"+diagnosticName+"'";
		int diagnosticID = 0;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				diagnosticID = resultSet.getInt("DIAGNOSTIC_ID");
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return diagnosticID;
	}
	
	//METHOD FOR OBTAINING DETAILS FOR ENTITIES IN THE DATABASE
	public String getEntityDetails(String detailWanted, String tableName, String entityIDColumn,DatabaseConnector databaseConnector, int entityID){
		//for example: "SELECT NAME FROM HR.DIAGNOSTICS WHERE DIAGNOSTIC_ID=entityID"; - NAME-detailWanted, HR.DIAGNOSTICS - tableName, DIAGNOSTIC_ID - columnName
		String query = "SELECT "+ detailWanted + " FROM " +tableName+ " WHERE "+ entityIDColumn +"="+"'"+entityID+"'";
		String entityDetails=null;
		try{
			Class.forName(databaseConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(databaseConnector.getDatabaseLocation(), databaseConnector.getUserName(), databaseConnector.getPassword());
			stmt = conn.prepareStatement(query);
			resultSet=stmt.executeQuery();
			while(resultSet.next()){
				entityDetails = resultSet.getString(detailWanted);
			}
			
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}
		catch(SQLException sqle){
			System.out.println("Something went wrong with your command: " + sqle.getMessage());
		}
		
		try{
			conn.close();	
			System.out.println("Connection closed for create result set!");
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, "A problem occured: " + sqle.getMessage());
		}
		return entityDetails;
	}

	public JScrollPane getPane() {
		return pane;
	}

	public void setPane(JScrollPane pane) {
		this.pane = pane;
	}
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public ResultsModel getTheModel() {
		return theModel;
	}

	public void setTheModel(ResultsModel theModel) {
		this.theModel = theModel;
	}

	public void printTable(){
		try{
			DatabaseBrowse.table.print();
		}catch(PrinterException pe){
			JOptionPane.showMessageDialog(null, "There is a printing error" + pe.getMessage());
		}	
	}
}
