package MODEL;
import oracle.jdbc.util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import oracle.jdbc.*;

import org.jdesktop.swingx.JXDatePicker;

import VIEW.MainWindowInterface;

public class AppointmentApp implements ActionListener,ListSelectionListener {
	
	//panels for holding labels, textfields etc
	private JPanel mainContainerPanel;
	private JPanel firstNameElements;
	private JPanel lastNameElements;
	private JPanel hourElements;
	private JPanel emailElements;
	private JPanel phoneNumberElements;
	private JPanel doctorListElements;
	private JPanel datePickerElements;
	
	//date picker
	private JXDatePicker datePicker;
	
	//text fields
	private JTextField firstName;
	private JTextField lastName;
	private JTextField hour;
	private JTextField email;
	private JTextField phoneNumber;
	//labels
	private JLabel datePickerLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel hourLabel;
	private JLabel emailLabel;
	private JLabel phoneNumberLabel;
	private JLabel doctorNamesLabel;
	
	//list component
	private JList doctorsList;
	private Vector<String> listModelVector = new Vector<String>();
	private int employeeID;
	
	//database elements
	private DatabaseConnector DBConnector;
	private DatabaseBrowse DBBrowse;
	private String query;
	private String getDoctorsQuery;//needed to select doctor for appointment
	
	//private static final String FILE_DATE_FORMAT = "dd-MM-yyyy";
	
	public AppointmentApp(){
		//connecting to database in order to create the model for the list
		DBConnector = new DatabaseConnector();
		DBBrowse = new DatabaseBrowse();
		
		getDoctorsQuery = "SELECT LAST_NAME FROM EMPLOYEE";
		listModelVector = DBBrowse.createVectorModel(DBConnector, getDoctorsQuery, "LAST_NAME", listModelVector);
		doctorsList = new JList(listModelVector);
		doctorsList.addListSelectionListener(this);
		doctorsList.setPreferredSize(new Dimension(300,300));
		
		//starting building the appointment interface for creating new appointments - JPanels/Containers
		mainContainerPanel = new JPanel();
		mainContainerPanel.setPreferredSize(new Dimension(300,500));
		mainContainerPanel.setLayout(new BoxLayout(mainContainerPanel,BoxLayout.Y_AXIS));
		
		firstNameElements = new JPanel();
		firstNameElements.setLayout(new BorderLayout());
		
		lastNameElements = new JPanel();
		lastNameElements.setLayout(new BorderLayout());
		
		hourElements = new JPanel();
		hourElements.setLayout(new BorderLayout());
		
		emailElements = new JPanel();
		emailElements.setLayout(new BorderLayout());
		
		phoneNumberElements = new JPanel();
		phoneNumberElements.setLayout(new BorderLayout());
		
		doctorListElements = new JPanel();
		doctorListElements.setLayout(new BorderLayout());
		
		datePickerElements = new JPanel();
		datePickerElements.setLayout(new BorderLayout());
		
		//setup for labels
		datePickerLabel = new JLabel("Choose Date by selecting below.");
		firstNameLabel = new JLabel("First name: ");
		lastNameLabel = new JLabel("Last name: ");
		hourLabel = new JLabel("Hour for appointment: ");
		emailLabel = new JLabel("Email address: ");
		phoneNumberLabel = new JLabel("Phone number: ");
		doctorNamesLabel = new JLabel("Select a doctor: ");
		

		//setup for fields
		firstName = new JTextField();
		lastName = new JTextField();
		hour = new JTextField();
		email = new JTextField();
		phoneNumber = new JTextField();
		datePicker = new JXDatePicker();
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datePickerLabel.setText(datePicker.getDate().toString());
			}
		});
		
		//adding elements to JPanels
		firstNameElements.add(firstNameLabel, BorderLayout.PAGE_START);
		firstNameElements.add(firstName, BorderLayout.CENTER);

		lastNameElements.add(lastNameLabel, BorderLayout.PAGE_START);
		lastNameElements.add(lastName, BorderLayout.CENTER);
		
		hourElements.add(hourLabel, BorderLayout.PAGE_START);
		hourElements.add(hour, BorderLayout.CENTER);
		
		emailElements.add(emailLabel, BorderLayout.PAGE_START);
		emailElements.add(email, BorderLayout.CENTER);
		
		phoneNumberElements.add(phoneNumberLabel, BorderLayout.PAGE_START);
		phoneNumberElements.add(phoneNumber, BorderLayout.CENTER);
		
		doctorListElements.add(doctorNamesLabel, BorderLayout.PAGE_START);
		doctorListElements.add(doctorsList, BorderLayout.CENTER);
		
		datePickerElements.add(datePickerLabel, BorderLayout.PAGE_START);
		datePickerElements.add(datePicker, BorderLayout.CENTER);
		
		mainContainerPanel.add(firstNameElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(lastNameElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(hourElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(emailElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(phoneNumberElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(doctorListElements);
		mainContainerPanel.add(Box.createVerticalStrut(10));
		mainContainerPanel.add(datePickerElements);
	}

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	
	public JPanel getaPanel() {
		return mainContainerPanel;
	}

	public JLabel getLabel() {
		return datePickerLabel;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			//retrieve from the list the employee id based on the name selected from the list
			employeeID = DBBrowse.getEmployeeID(DBConnector, (String)doctorsList.getSelectedValue());
	    }
	}
	
	public void executeInsertAppointment(){
		Object msg[] = {mainContainerPanel};
		Object type[] = {"Create", "Cancel"};
		int okBtn = JOptionPane.showOptionDialog(null, msg, "Create new appointment!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
		
		if(okBtn==JOptionPane.OK_OPTION){
			query = "INSERT INTO HR.APPOINTMENTS (FIRST_NAME,LAST_NAME,APP_HOUR,APP_DATE,EMAIL_ADDRESS,PHONE_NUMBER,EMPLOYEE_ID) " +
					"VALUES" +
					" (" + 
					"'"+firstName.getText()+"'"+","+"'"+lastName.getText()+"'"+","+"'"+hour.getText()+"'"+","+
					//+"'"+datePicker.getDate()+"'"+","+
					"'"+email.getText()+"'"+","+
					"'"+phoneNumber.getText()+"'"+","+"'"+employeeID+"'"
					+")";
			DBBrowse.executeUpdate(DBConnector, query);
		}
	}
	
}
