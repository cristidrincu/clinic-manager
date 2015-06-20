package MODEL;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import VIEW.PanelLatestBaseClass;

public class AppointmentDisplay extends PanelLatestBaseClass {
	private static DatabaseConnector databaseConnector=new DatabaseConnector("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/clinic", "root","crusader2");
	private static DatabaseBrowse browseAllAppointments;
	private static JPanel panelAllAppointments = new JPanel();
	private String allAppointmentsQuery;
	private static String panelName = new String("All appointments");
	
	public AppointmentDisplay(){
		super();
		allAppointmentsQuery = "SELECT FIRST_NAME,LAST_NAME,APP_HOUR,APP_DATE,EMAIL_ADDRESS,PHONE_NUMBER FROM HR.APPOINTMENTS ORDER BY LAST_NAME";
		browseAllAppointments = new DatabaseBrowse(databaseConnector, allAppointmentsQuery);
		panelAllAppointments.setLayout(new BorderLayout());
		panelAllAppointments.add(new JLabel("Latest Diagnostics", JLabel.CENTER), BorderLayout.PAGE_START);
		panelAllAppointments.add(super.menuContainer, BorderLayout.PAGE_START);
		panelAllAppointments.add(browseAllAppointments.getPane());
		
	}

	public static JPanel getPanelAllAppointments() {
		return panelAllAppointments;
	}

	public static void setPanelAllAppointments(JPanel panelAllAppointments) {
		AppointmentDisplay.panelAllAppointments = panelAllAppointments;
	}

	public static String getPanelName() {
		return panelName;
	}

	public static void setPanelName(String panelName) {
		AppointmentDisplay.panelName = panelName;
	}

	public static DatabaseBrowse getBrowseAllAppointments() {
		return browseAllAppointments;
	}

	public static void setBrowseAllAppointments(DatabaseBrowse browseAllAppointments) {
		AppointmentDisplay.browseAllAppointments = browseAllAppointments;
	}
	
	
	
	
}
