package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SendEmailClient implements ListSelectionListener {
	private JPanel mainContainer;
	private JPanel fieldsContainer;
	private JPanel emailAddresses;
	
	private JPanel panelToField;
	private JPanel panelSubjectField;
	private JPanel panelMessageTextArea;
	
	private JLabel clientTitle;
	private JLabel toLabel;
	private JLabel subjectLabel;
	private JLabel messageLabel;
	private JLabel emailAddressesLabel;
	
	private JTextField toField;
	private JTextField subjectField;
	private JTextArea messageArea;
	
	
	
	private DatabaseConnector dbConnector;
	private DatabaseBrowse dbBrowse;
	private String query;
	
	private int okBtnOptionPane=0;
	
	//a list that contains email addresses
	JList emailList;
	JScrollPane emailListScrollPane;
	
	//email data source for the list, containing pacients emails - retrieved from the database
	private Vector<String> emailDataSource = new Vector<String>();//vector that holds String[] elements that store row data

	
	public SendEmailClient(){
		
		//establish connection to database in order to create list model based on user choice - to whom should the app send email - pacient, doctor, assistant
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		query = "SELECT EMAIL FROM HR.PACIENTS";
		emailDataSource = dbBrowse.createVectorModel(dbConnector, query, "EMAIL", emailDataSource);
		
		emailList = new JList(emailDataSource);
		emailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		emailList.addListSelectionListener(this);
		
		emailListScrollPane = new JScrollPane(emailList);
		
		clientTitle = new JLabel("Email Client");
		clientTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		toLabel = new JLabel("To:");
		toLabel.setHorizontalAlignment(JLabel.LEFT);
		toLabel.setLayout(new BorderLayout());
		subjectLabel = new JLabel("Subject:");
		messageLabel = new JLabel("Message:");
		emailAddressesLabel = new JLabel("Email Address List:");
		
		toField = new JTextField();
		toField.setPreferredSize(new Dimension(200,30));
		subjectField = new JTextField();
		subjectField.setPreferredSize(new Dimension(200,30));
		messageArea = new JTextArea();
		messageArea.setPreferredSize(new Dimension(200, 300));
		messageArea.setLineWrap(true);
		
		panelToField = new JPanel();
		panelToField.setLayout(new BorderLayout());
		panelToField.add(toLabel, BorderLayout.PAGE_START);
		panelToField.add(toField,BorderLayout.PAGE_END);
		
		panelSubjectField = new JPanel();
		panelSubjectField.setLayout(new BorderLayout());
		panelSubjectField.add(subjectLabel, BorderLayout.PAGE_START);
		panelSubjectField.add(subjectField, BorderLayout.PAGE_END);
		
		panelMessageTextArea = new JPanel();
		panelMessageTextArea.setLayout(new BorderLayout());
		panelMessageTextArea.add(messageLabel, BorderLayout.PAGE_START);
		panelMessageTextArea.add(messageArea, BorderLayout.CENTER);
		
		fieldsContainer = new JPanel();
		fieldsContainer.setPreferredSize(new Dimension(300,420));
		fieldsContainer.setLayout(new BoxLayout(fieldsContainer, BoxLayout.Y_AXIS));
		fieldsContainer.add(panelToField);
		fieldsContainer.add(Box.createVerticalStrut(10));
		fieldsContainer.add(panelSubjectField);
		fieldsContainer.add(Box.createVerticalStrut(10));
		fieldsContainer.add(panelMessageTextArea);
		
		emailAddresses = new JPanel();
		emailAddresses.setLayout(new BoxLayout(emailAddresses, BoxLayout.Y_AXIS));
		emailAddresses.add(emailAddressesLabel);
		emailAddresses.add(emailListScrollPane);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		mainContainer.add(clientTitle, BorderLayout.NORTH);
		mainContainer.add(fieldsContainer, BorderLayout.LINE_START);
		mainContainer.add(emailAddresses, BorderLayout.CENTER);
		
		
		
		Object msg[] = {mainContainer};
		Object type[] = {"Send","Cancel"};
		
		okBtnOptionPane = JOptionPane.showOptionDialog(null, msg, "Send Email!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
	}

	//class methods
	private Session getSession() {
		AuthenticatorEmail authenticator = new AuthenticatorEmail();
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", "mail.globe-studios.com");
		properties.setProperty("mail.smtp.port", "25");
		return Session.getInstance(properties, authenticator);
		
		}
	
	public void sendEmail(){
		if(okBtnOptionPane == JOptionPane.OK_OPTION){
				Message message = new MimeMessage(getSession());
				try {
					message.addRecipient(RecipientType.TO, new InternetAddress(toField.getText()));
					message.addFrom(new InternetAddress[] { new InternetAddress("cristian.drincu@globe-studios.com") });
					message.setSubject(subjectField.getText());
					message.setContent(messageArea.getText(), "text/plain");
					Transport.send(message);
					JOptionPane.showMessageDialog(null, "Message sent!");
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			String selectedEmail = (String) emailList.getSelectedValue();
			toField.setText(selectedEmail);
	    }
		
	}
}
