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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditDepartmentDatabase implements ListSelectionListener {
	private JPanel mainContainer;
	private JPanel newDepartmentElementsContainer;
	private JPanel departmentsListContainer;
	
	private JLabel newDepartmentNameLabel;
	private JLabel selectADepartmentLabel;
	
	private JTextField newDepartmentNameText;
	private JList departmentsList;
	private JScrollPane scrollPane;
	
	private Vector<String> vectorModelDepList = new Vector<String>();
	
	private DatabaseBrowse dbBrowse;
	private DatabaseConnector dbConnector;
	private String queryVectorModel = "SELECT NAME FROM HR.DEPARTMENT";
	private String updateDepQuery;
	
	private String departmentName;
	private int departmentID;
	
	public EditDepartmentDatabase(){
		//connect to database and prepare the model for the list
		dbBrowse = new DatabaseBrowse();
		dbConnector = new DatabaseConnector();
		vectorModelDepList = dbBrowse.createVectorModel(dbConnector, queryVectorModel, "NAME", vectorModelDepList);
		
		//now prepare the UI - starting with the JPanels
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		mainContainer.setPreferredSize(new Dimension(300,300));
		
		newDepartmentElementsContainer = new JPanel();
		newDepartmentElementsContainer.setLayout(new BorderLayout());
		
		departmentsListContainer = new JPanel();
		departmentsListContainer.setLayout(new BorderLayout());
		
		//setup the label, text field and list
		newDepartmentNameLabel = new JLabel("Insert new name for department (current name displayed below): ");
		selectADepartmentLabel = new JLabel("Select department you want modified: ");
		newDepartmentNameText = new JTextField();
		departmentsList = new JList(vectorModelDepList);
		departmentsList.addListSelectionListener(this);
		departmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(departmentsList);
		
		//add components to containers
		newDepartmentElementsContainer.add(newDepartmentNameLabel, BorderLayout.PAGE_START);
		newDepartmentElementsContainer.add(newDepartmentNameText, BorderLayout.CENTER);
		
		departmentsListContainer.add(selectADepartmentLabel, BorderLayout.PAGE_START);
		departmentsListContainer.add(scrollPane, BorderLayout.CENTER);
		
		mainContainer.add(newDepartmentElementsContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(departmentsListContainer);
		
		
	}

	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			departmentID = dbBrowse.getDepartmentID(dbConnector, (String)departmentsList.getSelectedValue());
			departmentName = dbBrowse.getEntityDetails("NAME", "HR.DEPARTMENT", "DEPARTMENT_ID", dbConnector, departmentID);
			newDepartmentNameText.setText(departmentName);
		}
		
	}
	
	public void executeEdit(){
		//create objects for JOptionPane
		Object[] dialogs = {mainContainer};
		Object[] buttons = {"Update","Cancel"};
		
		int okOption = JOptionPane.showOptionDialog(null, dialogs, "Insert Dialog",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons,
		        null);
		if(okOption==JOptionPane.OK_OPTION){
			//connect to database and execute update
			updateDepQuery = "UPDATE HR.DEPARTMENT SET NAME="
			+"'"+newDepartmentNameText.getText()+"'"
			+" WHERE DEPARTMENT_ID="+"'"+departmentID+"'";
			
			dbBrowse.executeUpdate(dbConnector, updateDepQuery);
		}else if(okOption==JOptionPane.CANCEL_OPTION){
			return;
		}
	}
}
