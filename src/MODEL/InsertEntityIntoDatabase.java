package MODEL;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public abstract class InsertEntityIntoDatabase {
	
	private Box inputBox;
	
	private JPanel mainContainer;
	
	private JTextField inputFirstNameHolder;
	private JTextField inputLastNameHolder;
	private JTextField inputHomeAddressHolder;
	private JTextField inputPersonSexHolder;
	
	private JTextField inputAgeHolder;
	private JTextField inputIDHolder;
	
	private JTextField inputPhoneNumberHolder;
	private JTextField inputEmailAddressHolder;
	
	private JLabel allFieldsAreMandatory;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel homeAddressLabel;
	private JLabel personSexLabel;
	private JLabel ageLabel;
	private JLabel idLabel;
	private JLabel phoneNumberLabel;
	private JLabel emailAddressLabel;
	
	//class constructor
	public InsertEntityIntoDatabase(){
		
		inputFirstNameHolder = new JTextField();
		inputLastNameHolder = new JTextField();
		inputHomeAddressHolder = new JTextField();
		inputPersonSexHolder = new JTextField();
		
		inputAgeHolder = new JTextField();
		inputIDHolder = new JTextField();
		
		inputPhoneNumberHolder = new JTextField();
		inputEmailAddressHolder = new JTextField();
		
		allFieldsAreMandatory = new JLabel("All fields are mandatory: ");
		firstNameLabel = new JLabel("First name: ", JLabel.LEFT);
		lastNameLabel = new JLabel("Last name: ", JLabel.LEFT);
		homeAddressLabel = new JLabel("Home address: ", JLabel.LEFT);
		personSexLabel = new JLabel("Person sex: ", JLabel.LEFT);
		ageLabel = new JLabel("Age:", JLabel.LEFT);
		idLabel = new JLabel("ID: ", JLabel.LEFT);
		phoneNumberLabel = new JLabel("Phone Number: ", JLabel.LEFT);
		emailAddressLabel = new JLabel("Email address: ", JLabel.LEFT);

		
		inputBox = Box.createVerticalBox();
		inputBox.setPreferredSize(new Dimension(400,500));
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(allFieldsAreMandatory);
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(firstNameLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputFirstNameHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(lastNameLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputLastNameHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(homeAddressLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputHomeAddressHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(personSexLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputPersonSexHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(ageLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputAgeHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(idLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputIDHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(phoneNumberLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputPhoneNumberHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		
		inputBox.add(emailAddressLabel);
		inputBox.add(Box.createVerticalStrut(5));
		inputBox.add(inputEmailAddressHolder);
		
		inputBox.add(Box.createVerticalStrut(5));
		

	}

	public abstract void executeInsert();
	public abstract void writeEntityToFile();
	
	public Box getInputBox() {
		return inputBox;
	}

	public JTextField getInputFirstNameHolder() {
		return inputFirstNameHolder;
	}

	public JTextField getInputLastNameHolder() {
		return inputLastNameHolder;
	}

	public JTextField getInputHomeAddressHolder() {
		return inputHomeAddressHolder;
	}

	public JTextField getInputPersonSexHolder() {
		return inputPersonSexHolder;
	}

	public JTextField getInputAgeHolder() {
		return inputAgeHolder;
	}

	public JTextField getInputIDHolder() {
		return inputIDHolder;
	}

	public JTextField getInputPhoneNumberHolder() {
		return inputPhoneNumberHolder;
	}

	public JTextField getInputEmailAddressHolder() {
		return inputEmailAddressHolder;
	}
}
