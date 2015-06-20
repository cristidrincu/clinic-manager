package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InsertPrescriptionIntoDatabase implements ListSelectionListener {
	private JPanel mainContainer;
	private JPanel dateContainer;
	private JPanel summaryContainer;
	private JPanel listContainer;
	
	private JLabel dateLabel;
	private JLabel prescriptionSummaryLabel;
	private JLabel diagnosticsLabel;
	
	private JTextField insertDate;
	private JTextArea prescriptionSummary;
	
	private JList diagnosticsList;
	private JScrollPane scrollPane;
	private Vector<String> vectorModelDiagnostics = new Vector<String>();
	
	private DatabaseBrowse dbBrowse;
	private DatabaseConnector dbConnector;
	private String queryPopulateListDiagnostics = "SELECT NAME FROM HR.DIAGNOSTICS";
	private int diagnosticsID; 
	private String queryInsertPrescription;
	
	public InsertPrescriptionIntoDatabase(){
		//connect to database, setup model for list then setup the UserInterface
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		vectorModelDiagnostics = dbBrowse.createVectorModel(dbConnector, queryPopulateListDiagnostics, "NAME", vectorModelDiagnostics);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		dateContainer = new JPanel();
		dateContainer.setLayout(new BorderLayout());
		
		summaryContainer = new JPanel();
		summaryContainer.setLayout(new BorderLayout());
		
		listContainer = new JPanel();
		listContainer.setLayout(new BorderLayout());
		
		dateLabel = new JLabel("Insert prescription date: ");
		prescriptionSummaryLabel = new JLabel("Insert prescription summary: ");
		diagnosticsLabel = new JLabel("Select a diagnostic for the prescription: ");
		
		insertDate = new JTextField();
		prescriptionSummary = new JTextArea(10,1);
		
		diagnosticsList = new JList(vectorModelDiagnostics);
		diagnosticsList.addListSelectionListener(this);
		diagnosticsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(diagnosticsList);
		scrollPane.setPreferredSize(new Dimension(300,175));
		
		dateContainer.add(dateLabel, BorderLayout.PAGE_START);
		dateContainer.add(insertDate, BorderLayout.CENTER);
		
		summaryContainer.add(prescriptionSummaryLabel, BorderLayout.PAGE_START);
		summaryContainer.add(prescriptionSummary, BorderLayout.CENTER);
		
		listContainer.add(diagnosticsLabel, BorderLayout.PAGE_START);
		listContainer.add(scrollPane, BorderLayout.CENTER);
		
		mainContainer.add(dateContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(summaryContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(listContainer);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){//user is done with selecting an item from the list
			diagnosticsID = dbBrowse.getDiagnosticID(dbConnector, (String)diagnosticsList.getSelectedValue());
		}
	}
	
	public void executeInsert(){
		Object[] panelPrescription={mainContainer};
		Object[] panelButtons = {"Insert", "Cancel"};
		
		int okButton=JOptionPane.showOptionDialog(null, panelPrescription, "Send Email!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, panelButtons,
		        null);
		if(okButton==JOptionPane.OK_OPTION){
			//connect to database and insert prescription
			queryInsertPrescription="INSERT INTO HR.PRESCRIPTIONS (PRESCRIPTION_DATE, SUMMARY, DIAGNOSTIC_ID) VALUES ("
			+"'"+insertDate.getText()+"'"+","
			+"'"+prescriptionSummary.getText()+"'"+","
			+"'"+diagnosticsID+"'"
			+")";
			dbBrowse.executeUpdate(dbConnector, queryInsertPrescription);
		}
	}
}
