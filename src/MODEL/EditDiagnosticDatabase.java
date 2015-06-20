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

public class EditDiagnosticDatabase implements ListSelectionListener {
	private JPanel mainContainer;
	private JPanel newDiagnosticElementsContainer;
	private JPanel diagnosticsListContainer;
	private JPanel diagnosticsAnalysisContainer;
	
	private JLabel newDiagnosticNameLabel;
	private JLabel newAnalysisLabel;
	private JLabel selectADiagnosticLabel;
	
	private JTextField newDiagnosticNameText;
	private JTextArea newDiagnosticAnalysisText;
	private JList diagnosticsList;
	private JScrollPane scrollPane;
	private JScrollPane textAreaScroll;
	
	private Vector<String> vectorModelDiagList = new Vector<String>();
	
	private DatabaseBrowse dbBrowse;
	private DatabaseConnector dbConnector;
	private String queryVectorModel = "SELECT NAME FROM HR.DIAGNOSTICS";
	private String updateDiagQuery;
	
	private String getDiagnosticNameText;
	private String getAnalysisText;
	
	private int diagID;
	
	public EditDiagnosticDatabase(){
		//connect to database and prepare the model for the list
		dbBrowse = new DatabaseBrowse();
		dbConnector = new DatabaseConnector();
		vectorModelDiagList = dbBrowse.createVectorModel(dbConnector, queryVectorModel, "NAME", vectorModelDiagList);
		
		//now prepare the UI - starting with the JPanels
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		mainContainer.setPreferredSize(new Dimension(400,300));
		
		newDiagnosticElementsContainer = new JPanel();
		newDiagnosticElementsContainer.setLayout(new BorderLayout());
		
		diagnosticsAnalysisContainer = new JPanel();
		diagnosticsAnalysisContainer.setLayout(new BorderLayout());
		
		diagnosticsListContainer = new JPanel();
		diagnosticsListContainer.setLayout(new BorderLayout());
		
		//setup the label, text field and list
		newDiagnosticNameLabel = new JLabel("Insert new name for diagnostic(current name displayed below): ");
		selectADiagnosticLabel = new JLabel("Select the diagnostic you want modified: ");
		newAnalysisLabel =  new JLabel("Current diagnostic analysis: ");
		
		newDiagnosticNameText = new JTextField();
		newDiagnosticAnalysisText = new JTextArea(10,1);
		newDiagnosticAnalysisText.setLineWrap(true);
		newDiagnosticAnalysisText.setPreferredSize(new Dimension(400,200));
		textAreaScroll = new JScrollPane(newDiagnosticAnalysisText);
		
		diagnosticsList = new JList(vectorModelDiagList);
		diagnosticsList.addListSelectionListener(this);
		diagnosticsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(diagnosticsList);
		
		//add components to containers
		diagnosticsListContainer.add(selectADiagnosticLabel, BorderLayout.PAGE_START);
		diagnosticsListContainer.add(scrollPane, BorderLayout.CENTER);
		
		newDiagnosticElementsContainer.add(newDiagnosticNameLabel, BorderLayout.PAGE_START);
		newDiagnosticElementsContainer.add(newDiagnosticNameText, BorderLayout.CENTER);
		
		diagnosticsAnalysisContainer.add(newAnalysisLabel, BorderLayout.PAGE_START);
		diagnosticsAnalysisContainer.add(textAreaScroll, BorderLayout.CENTER);
		
		mainContainer.add(diagnosticsListContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(newDiagnosticElementsContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(diagnosticsAnalysisContainer);
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			diagID = dbBrowse.getDiagnosticID(dbConnector, (String)diagnosticsList.getSelectedValue());
			
			getAnalysisText = dbBrowse.getEntityDetails("ANALYSIS", "HR.DIAGNOSTICS", "DIAGNOSTIC_ID", dbConnector, diagID);
			newDiagnosticAnalysisText.setText(getAnalysisText);
			
			getDiagnosticNameText = dbBrowse.getEntityDetails("NAME", "HR.DIAGNOSTICS", "DIAGNOSTIC_ID", dbConnector, diagID);
			newDiagnosticNameText.setText(getDiagnosticNameText);
		}
	}
	
	public void executeEdit(){
		//create objects for JOptionPane and execute the edit for a diagnostic
		Object[] dialogs = {mainContainer};
		Object[] buttons = {"Update","Cancel"};
		
		int okOption = JOptionPane.showOptionDialog(null, dialogs, "Insert new Diagnostic",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons,
		        null);
		if(okOption==JOptionPane.OK_OPTION){
			//connect to database and execute update
			updateDiagQuery = "UPDATE HR.DIAGNOSTICS SET NAME="
			+"'"+newDiagnosticNameText.getText()+"'"+", ANALYSIS="+"'"+newDiagnosticAnalysisText.getText()+"'"
			+" WHERE DIAGNOSTIC_ID="+"'"+diagID+"'";
			
			dbBrowse.executeUpdate(dbConnector, updateDiagQuery);
		}else if(okOption==JOptionPane.CANCEL_OPTION){
			return;
		}
	}
}
