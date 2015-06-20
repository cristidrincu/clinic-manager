package MODEL;

public class DatabaseConnector  {

	//private String databaseName;
	private String databaseLocation;
	private String databaseDriver;
	private String userName;
	private String password;
	
	//default database connector for the application at hand, using a Oracle Database to manipulate data
	public DatabaseConnector(){
//		this.databaseDriver = "oracle.jdbc.driver.OracleDriver";
//		this.databaseLocation="jdbc:oracle:thin:@localhost:1521:orcl";
//		this.userName = "HR";
//		this.password = "crusader2";
		
		this.databaseDriver = "com.mysql.jdbc.Driver";
		this.databaseLocation="jdbc:mysql://localhost:3306/clinic";
		this.userName = "root";
		this.password = "crusader2";
	}
	
	//class constructor for a certain type of connector - MySQL, PostGRE, etc
	public DatabaseConnector(String databaseDriver,String databaseLocation,  String userName, String password){
		this.databaseLocation=databaseLocation;
		this.databaseDriver=databaseDriver;
		this.userName=userName;
		this.password=password;
	}

	//getters and setters
	public String getDatabaseLocation() {
		return databaseLocation;
	}

	public void setDatabaseLocation(String databaseLocation) {
		this.databaseLocation = databaseLocation;
	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
