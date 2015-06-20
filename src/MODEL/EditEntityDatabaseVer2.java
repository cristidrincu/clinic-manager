package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class EditEntityDatabaseVer2 implements ListSelectionListener {

	protected JPanel mainContainer;
	protected JSplitPane splitPane;
	protected JPanel pacientsListContainer;
	protected JPanel textFieldsInfoContainer;
	
	//panels for holding labels and textfields that will be populated with initial pacient data
	protected JPanel containerFirstName = new JPanel();
	protected JPanel containerLastName = new JPanel();
	protected JPanel containerAddress = new JPanel();
	protected JPanel containerSex = new JPanel();
	protected JPanel containerAge = new JPanel();
	protected JPanel containerIDCard = new JPanel();
	protected JPanel containerPhoneNumber = new JPanel();
	protected JPanel containerEmail = new JPanel();
	
	protected String pacientsListContainerLabel;
	
	protected JTextField personFirstName = new JTextField();
	protected JTextField personLastName = new JTextField();
	protected JTextField personAddress = new JTextField();
	protected JTextField personSex = new JTextField();
	protected JTextField personAge = new JTextField();
	protected JTextField personIDCard = new JTextField();
	protected JTextField personPhoneNumber = new JTextField();
	protected JTextField personEmail = new JTextField();
	protected JTextField personOccupation = new JTextField();
	
	protected JList personList;
	protected JScrollPane scrollPane;
	
	//database stuff
	protected DatabaseConnector dbConnector;
	protected DatabaseBrowse dbBrowse;
	protected String selectPersonQuery;//this should be empty and populated in subclasses
	protected String personLastNameFromList = null;
	protected int personID;
	protected String updateQuery;
	
	protected String personFirstNameField;
	protected String personLastNameField;
	protected String personAddressField;
	protected String personSexField;
	protected String personAgeField;
	protected String personIDCardField;
	protected String personPhoneNumberField;
	protected String personEmailField;
	protected String personOccupationField;
	
	protected Vector<String> personListVector = new Vector<String>();
	
	public EditEntityDatabaseVer2(){
		//setup the ui
		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());
		
		pacientsListContainer = new JPanel();
		pacientsListContainer.setLayout(new BorderLayout());
		
		textFieldsInfoContainer = new JPanel();
		textFieldsInfoContainer.setLayout(new BoxLayout(textFieldsInfoContainer, BoxLayout.Y_AXIS));
		textFieldsInfoContainer.setPreferredSize(new Dimension(200,400));
		
		containerFirstName.setLayout(new BorderLayout());
		containerFirstName.add(new JLabel("First name:"), BorderLayout.PAGE_START);
		containerFirstName.add(personFirstName, BorderLayout.CENTER);
		
		containerLastName.setLayout(new BorderLayout());
		containerLastName.add(new JLabel("Last name:"), BorderLayout.PAGE_START);
		containerLastName.add(personLastName, BorderLayout.CENTER);
		
		containerAddress.setLayout(new BorderLayout());
		containerAddress.add(new JLabel("Address:"), BorderLayout.PAGE_START);
		containerAddress.add(personAddress, BorderLayout.CENTER);
		
		containerSex.setLayout(new BorderLayout());
		containerSex.add(new JLabel("Sex:"), BorderLayout.PAGE_START);
		containerSex.add(personSex, BorderLayout.CENTER);
		
		containerAge.setLayout(new BorderLayout());
		containerAge.add(new JLabel("Age:"), BorderLayout.PAGE_START);
		containerAge.add(personAge, BorderLayout.CENTER);
		
		containerIDCard.setLayout(new BorderLayout());
		containerIDCard.add(new JLabel("ID Card:"), BorderLayout.PAGE_START);
		containerIDCard.add(personIDCard, BorderLayout.CENTER);
		
		containerPhoneNumber.setLayout(new BorderLayout());
		containerPhoneNumber.add(new JLabel("Phone number:"), BorderLayout.PAGE_START);
		containerPhoneNumber.add(personPhoneNumber, BorderLayout.CENTER);
		
		containerEmail.setLayout(new BorderLayout());
		containerEmail.add(new JLabel("Email:"), BorderLayout.PAGE_START);
		containerEmail.add(personEmail, BorderLayout.CENTER);
		
		textFieldsInfoContainer.add(containerFirstName);
		textFieldsInfoContainer.add(containerLastName);
		textFieldsInfoContainer.add(containerAddress);
		textFieldsInfoContainer.add(containerSex);
		textFieldsInfoContainer.add(containerAge);
		textFieldsInfoContainer.add(containerIDCard);
		textFieldsInfoContainer.add(containerPhoneNumber);
		textFieldsInfoContainer.add(containerEmail);
	}
	
	//abstract method for updating values in text fields when list entities are selected
	public abstract void valueChanged(ListSelectionEvent e);
	
	public abstract JList createVectorModelForList();
	public abstract void executeEdit();
	
	public JPanel getTextFieldsInfoContainer() {
		return textFieldsInfoContainer;
	}

	public void setTextFieldsInfoContainer(JPanel textFieldsInfoContainer) {
		this.textFieldsInfoContainer = textFieldsInfoContainer;
	}

	public String getSelectPacientsQuery() {
		return selectPersonQuery;
	}

	public void setSelectPacientsQuery(String selectPacientsQuery) {
		this.selectPersonQuery = selectPacientsQuery;
	}
}
