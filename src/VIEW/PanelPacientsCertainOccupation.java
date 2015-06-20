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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ACTIONS.ViewPanelActions;
import MODEL.DatabaseBrowse;
import MODEL.DatabaseConnector;
import MODEL.ResultSetToExcel;

public class PanelPacientsCertainOccupation extends PanelViewEntity implements ListSelectionListener {
	private static JPanel pacientsCertainOccup;
	private JPanel mainContainer;
	private JPanel occupationsElementsContainer;
	private JLabel occupationsLabel;
	private JLabel menuIndicator;
	private JList occupationsList;
	private JScrollPane scrollPane;
	private Vector<String> vectorModelOccupation = new Vector<String>();
	
	//database objects
	private static DatabaseConnector dbConnector;
	private static DatabaseBrowse dbBrowse;
	private String vectorQuery = "SELECT OCCUPATION FROM HR.PACIENTS";
	private static String occupationSelected;
	private static Connection conn;
	
	//class constructor
	public PanelPacientsCertainOccupation(){
		//get the vector model for populating the list
		dbConnector = new DatabaseConnector();
		dbBrowse = new DatabaseBrowse();
		vectorModelOccupation = dbBrowse.createVectorModel(dbConnector, vectorQuery, "OCCUPATION", vectorModelOccupation);
		
		//setup UI
		pacientsCertainOccup=new JPanel();
		pacientsCertainOccup.setLayout(new BorderLayout());
		pacientsCertainOccup.add(super.getMenuBar(), BorderLayout.NORTH);
		
		//actions for the menu
		exportTableAction = new ViewPanelActions("Export this table as Microsoft Excel file", "exportExcelTablePacientsCertainOccupation");
		printTableAction = new ViewPanelActions("Print this table", "printTablePacientsCertainOccupation");
		
		super.panelMenuFile.add(new JMenuItem(exportTableAction));
		super.panelMenuFile.add(new JMenuItem(printTableAction));
		
		super.getMenuBar().add(panelMenuFile);
		super.getMenuBar().add(panelMenuHelp);	
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer,BoxLayout.Y_AXIS));
		
		occupationsElementsContainer = new JPanel();
		occupationsElementsContainer.setLayout(new BorderLayout());
		
		occupationsLabel = new JLabel("Select an occupation below:");
		occupationsList = new JList(vectorModelOccupation);
		occupationsList.addListSelectionListener(this);
		occupationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(occupationsList);
		
		//add components to JPanels
		occupationsElementsContainer.add(occupationsLabel, BorderLayout.PAGE_START);
		occupationsElementsContainer.add(scrollPane, BorderLayout.CENTER);
		mainContainer.add(occupationsElementsContainer);
		
		Object[] components = {mainContainer};
		Object[] btns = {"OK","Cancel"};
		
		int okBtn=JOptionPane.showOptionDialog(null, components, "Select patient occupation!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, btns,
		        null);
		if(okBtn==JOptionPane.OK_OPTION){
			String queryTemp="SELECT FIRST_NAME,LAST_NAME, ADDRESS,SEX,AGE,ID_CARD,PHONE_NUMBER,EMAIL FROM HR.PACIENTS WHERE OCCUPATION="+"'"+occupationSelected+"'";
			setDbBrowse(new DatabaseBrowse(dbConnector,queryTemp));
			pacientsCertainOccup.add(dbBrowse.getPane());
		}else if(okBtn==JOptionPane.CANCEL_OPTION){
			return;
		}	
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()==false){
			occupationSelected = (String)occupationsList.getSelectedValue();
			menuIndicator= new JLabel("You are viewing the prescriptions table for diagnostic "+ (String)occupationsList.getSelectedValue() +":: ");
			menuIndicator.setFont(new Font("Arial", Font.BOLD, 13));
			super.getMenuBar().add(menuIndicator);
		}
	}

	public static DatabaseBrowse getDbBrowse() {
		return dbBrowse;
	}

	public static void setDbBrowse(DatabaseBrowse dbBrowse) {
		PanelPacientsCertainOccupation.dbBrowse = dbBrowse;
	}

	public static JPanel getPacientsCertainOccup() {
		return pacientsCertainOccup;
	}

	public static void setPacientsCertainOccup(JPanel pacientsCertainOccup) {
		PanelPacientsCertainOccupation.pacientsCertainOccup = pacientsCertainOccup;
	}
	
	public JList getOccupationsList() {
		return occupationsList;
	}

	public void setOccupationsList(JList occupationsList) {
		this.occupationsList = occupationsList;
	}

	public static void printTable(){
		dbBrowse.printTable();
	}
	
	//method for exporting the resultset containing prescriptions based on a user supplied doctor to an excel file
	public static void createExcel(){
		try{
			Class.forName(dbConnector.getDatabaseDriver());
			conn = DriverManager.getConnection(dbConnector.getDatabaseLocation(), dbConnector.getUserName(), dbConnector.getPassword());
		    PreparedStatement stmt = conn.prepareStatement("SELECT FIRST_NAME,LAST_NAME, ADDRESS,SEX,AGE,ID_CARD,PHONE_NUMBER,EMAIL FROM HR.PACIENTS WHERE OCCUPATION="+"'"+occupationSelected+"'");
		    ResultSet resultSet = stmt.executeQuery();
		    ResultSetToExcel resultSetToExcel = new ResultSetToExcel(resultSet,"Pacients List");//the string stands for the sheet name
		    resultSetToExcel.generate(new File("D:/pacientsOccupation.xls"));
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
