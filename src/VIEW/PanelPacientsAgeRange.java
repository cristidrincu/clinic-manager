package VIEW;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ACTIONS.ViewPanelActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPacientsAgeRange extends PanelViewEntity {

	private static JPanel pacientsCertainAge;
	private JPanel mainContainer;
	private JPanel minAgeFieldsContainer;
	private JPanel maxAgeFieldsContainer;
	private JLabel insertMinAgeLabel;
	private JLabel insertMaxAgeLabel;
	private JLabel menuIndicator;
	private static JTextField minAgeTextField;
	private static JTextField maxAgeTextField;
	
	//database objects
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	
	private static Connection conn;
	
	public PanelPacientsAgeRange(){
		dbConnector = new DatabaseConnector();
		
		pacientsCertainAge = new JPanel();
		pacientsCertainAge.setLayout(new BorderLayout());
		pacientsCertainAge.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file", "exportExcelTablePacientsAgeRange");
		printTableAction = new ViewPanelActions("Print this table", "printTablePacientsAgeRange");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer,BoxLayout.Y_AXIS));
		
		minAgeFieldsContainer = new JPanel();
		minAgeFieldsContainer.setLayout(new BorderLayout());
		
		maxAgeFieldsContainer = new JPanel();
		maxAgeFieldsContainer.setLayout(new BorderLayout());
		
		insertMinAgeLabel = new JLabel("Insert minimum age: ");
		insertMaxAgeLabel = new JLabel("Insert maximum age: ");
		
		minAgeTextField = new JTextField();
		maxAgeTextField = new JTextField();
		
		minAgeFieldsContainer.add(insertMinAgeLabel, BorderLayout.PAGE_START);
		minAgeFieldsContainer.add(minAgeTextField, BorderLayout.CENTER);
		
		maxAgeFieldsContainer.add(insertMaxAgeLabel, BorderLayout.PAGE_START);
		maxAgeFieldsContainer.add(maxAgeTextField, BorderLayout.CENTER);
		
		mainContainer.add(minAgeFieldsContainer);
		mainContainer.add(Box.createVerticalStrut(10));
		mainContainer.add(maxAgeFieldsContainer);
		
		Object[] components = {mainContainer};
		Object[] btns = {"OK", "Cancel"};
		
		int okBtn=JOptionPane.showOptionDialog(null, components, "Please insert min. and max. age range!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, btns,
		        null);
		if(okBtn==JOptionPane.OK_OPTION){
			String query="SELECT FIRST_NAME,LAST_NAME,ADDRESS,SEX,EMAIL,ID_CARD,PHONE_NUMBER,AGE FROM HR.PACIENTS WHERE AGE BETWEEN "+"'"+minAgeTextField.getText()+"' AND "+"'"+maxAgeTextField.getText()+"'";
			setDbBrowse(new DatabaseBrowse(dbConnector,query));
			pacientsCertainAge.add(dbBrowse.getPane());
			menuIndicator= new JLabel("You are viewing the pacients table for ages between "+ minAgeTextField.getText() +" and "+maxAgeTextField.getText()+" :: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
		}
	}

	public static JPanel getPacientsCertainAge() {
		return pacientsCertainAge;
	}

	public JTextField getMinAgeTextField() {
		return minAgeTextField;
	}

	public JTextField getMaxAgeTextField() {
		return maxAgeTextField;
	}

	public static void setPacientsCertainAge(JPanel pacientsCertainAge) {
		PanelPacientsAgeRange.pacientsCertainAge = pacientsCertainAge;
	}

	public void setMinAgeTextField(JTextField minAgeTextField) {
		PanelPacientsAgeRange.minAgeTextField = minAgeTextField;
	}

	public void setMaxAgeTextField(JTextField maxAgeTextField) {
		PanelPacientsAgeRange.maxAgeTextField = maxAgeTextField;
	}

	public static DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public static void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelPacientsAgeRange.dbBrowse = dbBrowse;
	}
	
	public static void printTable(){
		dbBrowse.printTable();
	}
	
	//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
	public static void createExcel(){
		try{
			Class.forName(dbConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement("SELECT FIRST_NAME,LAST_NAME,ADDRESS,SEX,EMAIL,ID_CARD,PHONE_NUMBER,AGE FROM HR.PACIENTS WHERE AGE BETWEEN "+"'"+minAgeTextField.getText()+"' AND "+"'"+maxAgeTextField.getText()+"'");
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Prescription List");
		    resultSetToExcel.generate(new File("D:/pacientsAgeRange.xls"));
		    JOptionPane.showMessageDialog(null, "File exported succesfully");
		}catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(null, cnfe.getMessage());
		}catch(SQLException sqle){
			JOptionPane.showMessageDialog(null, sqle.getMessage());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}		
	  }
}
