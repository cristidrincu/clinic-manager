package VIEW;
import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPacientsCertainPrescription extends PanelViewEntity implements ListSelectionListener {
	private JPanel dialogContainer;
	private JLabel pacientLabel;
	private JTextField prescriptionName;
	private JList pacientsList;
	private JScrollPane scrollPane;
	private int btnStepPacientName;
	private int pacientID;
	private String pacientName;
	
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String query;
	private String populatePacientsList = "SELECT LAST_NAME FROM HR.PACIENTS";
	private Vector<String> vectorPacientsList = new Vector<String>();
	
	public PanelPacientsCertainPrescription(){
		//setup UI
		dialogContainer = new JPanel();
		dialogContainer.setLayout(new BorderLayout());
		
		pacientLabel = new JLabel("Select pacient name: ");
		
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		vectorPacientsList = dbBrowse.createVectorModel(dbConnector, populatePacientsList, "LAST_NAME", vectorPacientsList);
		pacientsList = new JList(vectorPacientsList);
		pacientsList.addListSelectionListener(this);
		scrollPane = new JScrollPane(pacientsList);
		
		dialogContainer.add(pacientLabel, BorderLayout.PAGE_START);
		dialogContainer.add(scrollPane, BorderLayout.CENTER);
		
		Object dialogMessage[] = {dialogContainer};
		Object dialogButtons[] = {"OK", "Cancel"};
		
		btnStepPacientName=JOptionPane.showOptionDialog(null, dialogMessage, "Select a pacient in order to get his/her prescriptions",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dialogButtons,
		        null);
		if(btnStepPacientName==JOptionPane.OK_OPTION){
			pacientID=dbBrowse.getPacientID(dbConnector, pacientName);
			//connect to database and execute query containing pacient name/id, also setup the query
			query = "SELECT PR.PRESCRIPTION_DATE, PR.SUMMARY,P.FIRST_NAME,P.LAST_NAME,P.AGE,P.ID_CARD FROM HR.PACIENTS P, HR.DIAGNOSTICS D, HR.PRESCRIPTIONS PR WHERE P.PACIENT_ID="+
			"'"+pacientID+"'"+"AND D.PACIENT_ID=P.PACIENT_ID AND PR.DIAGNOSTIC_ID = D.DIAGNOSTIC_ID";
			dbConnector = new DatabaseConnector();
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
		}
	}

	public DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelPacientsCertainPrescription.dbBrowse = dbBrowse;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			pacientName=(String) pacientsList.getSelectedValue();
	    }
	}

	public JList getPacientsList() {
		return pacientsList;
	}

	public void setPacientsList(JList pacientsList) {
		this.pacientsList = pacientsList;
	}
	
	public static void printTablePacientsCertainPrescription(){
		dbBrowse.printTable();
	}
}
