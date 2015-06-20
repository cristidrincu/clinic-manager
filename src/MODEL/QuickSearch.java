package MODEL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ACTIONS.ViewActions;
import VIEW.MainWindowInterface;

public class QuickSearch implements ActionListener {
	//elements for constructing a quick search feature
	private JPanel quickSearch = new JPanel();
	private JPanel searchResultsPanel = new JPanel();
	private JLabel quickSearchTitle= new JLabel("Quick Search: ");
	private String[] DBEntities = {"--Please select an entity to search for--", "Pacient", "Doctor", "Diagnostics", "Prescription" };//model for combo box
	private JComboBox entityOptions = new JComboBox(DBEntities);
	private JTextField quickSearchText = new JTextField();
	private JButton searchBtn = new JButton("Go!");
	private String selectedChoice;
		
	//db elements for quick search
	DatabaseBrowse dbBrowse;
	DatabaseConnector dbConnector = new DatabaseConnector();
	
	public QuickSearch(){
		//quick search container and actions for searching
		quickSearch.setLayout(new FlowLayout());
		quickSearch.add(quickSearchTitle);
		quickSearchText.setPreferredSize(new Dimension(300,30));
		entityOptions.addActionListener(this);
		entityOptions.setSelectedIndex(0);
		quickSearch.add(entityOptions);
		quickSearch.add(quickSearchText);
		searchBtn.addActionListener(this);
		quickSearch.add(searchBtn);
	}

		//action for combo box
		public void actionPerformed(ActionEvent e) {
			String query=null;
			searchResultsPanel = new JPanel();
			searchResultsPanel.setLayout(new BorderLayout());
			if(e.getSource()==entityOptions){
				selectedChoice=(String)entityOptions.getSelectedItem();
			}
			if(e.getSource()==searchBtn){
				//check for existing tab panes opened - close them and display the quick search tab
				//remove default tab(aka welcome tab) from view and insert view tab (aka main tab)
				if(MainWindowInterface.getTabOpenedFlag()==0){
					MainWindowInterface.getWelcomeTab().removeAll();//removes all tabs on the welcome tab
					MainWindowInterface.removeComponent(MainWindowInterface.getWelcomeTab());//by default, this is the tab that is loaded and we remove it if it is opened
					MainWindowInterface.getMainContainer().add(MainWindowInterface.getQuickSearchTab());//add main database container
					MainWindowInterface.setTabOpenedFlag(4);
				}else if(MainWindowInterface.getTabOpenedFlag()==1){
					MainWindowInterface.getMainTab().removeAll();//remove all tabs on main tab presenting view actions
					MainWindowInterface.removeComponent(MainWindowInterface.getMainTab());//remove main tab component
					ViewActions.setExistingDepartmentsTabView(1);//reset the existing tab counter
					ViewActions.setExistingDiagnosticsTabView(1);//reset the existing tab counter
					ViewActions.setExistingDoctorsTabView(1);//reset the existing tab counter
					ViewActions.setExistingPacientsTabView(1);//reset the existing tab counter
					ViewActions.setExistingPrescriptionsTabView(1);//reset the existing tab counter
					MainWindowInterface.getMainContainer().add(MainWindowInterface.getQuickSearchTab());
					MainWindowInterface.setTabOpenedFlag(4);
				}else if(MainWindowInterface.getTabOpenedFlag()==2){
					MainWindowInterface.getOpenedFilesTab().removeAll();//remove all tabs on the files container 
					MainWindowInterface.removeComponent(MainWindowInterface.getOpenedFilesTab());//this is the files tab, and we remove it if it's opened
					MainWindowInterface.getMainContainer().add(MainWindowInterface.getQuickSearchTab());//add main database container
					MainWindowInterface.setTabOpenedFlag(4);
				}else if(MainWindowInterface.getTabOpenedFlag()==3){//reports tab is opened
					MainWindowInterface.getReportsTab().removeAll();
					MainWindowInterface.removeComponent(MainWindowInterface.getReportsTab());
					MainWindowInterface.getMainContainer().add(MainWindowInterface.getQuickSearchTab());
					MainWindowInterface.setTabOpenedFlag(4);
				}
				if(entityOptions.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(null, "You must select an entity to search for!");
					return;
				}
				switch(selectedChoice){
				case "Pacient":
					query="SELECT firstName, lastName, address, sex, age, idCard, phoneNumber, email, occupation FROM clinic.Patients WHERE lastName LIKE '%"+quickSearchText.getText()+
					"%' OR firstName LIKE '%"+quickSearchText.getText()+"%'";
					dbBrowse = new DatabaseBrowse(dbConnector,query);
					break;
				case "Doctor":
					query="SELECT firstName, lastName, department, address, age, emailAddress, phoneNumber FROM clinic.Employee WHERE lastName LIKE '%"+quickSearchText.getText()+
					"%' OR firstName LIKE '%"+quickSearchText.getText()+"%'";
					dbBrowse = new DatabaseBrowse(dbConnector,query);
					break;
				case "Diagnostics":
					query = "SELECT name, analysis FROM clinic.Diagnostics WHERE name LIKE '%"+quickSearchText.getText()+"%'";
					dbBrowse = new DatabaseBrowse(dbConnector,query);
					break;
				case "Prescription":
					query="SELECT summary, prescriptionDate FROM clinic.Prescriptions WHERE summary LIKE '%"+quickSearchText.getText()+"%'";
					dbBrowse = new DatabaseBrowse(dbConnector,query);
					break;
				}
				searchResultsPanel.add(dbBrowse.getPane());
				MainWindowInterface.getQuickSearchTab().addTab("Quick search for " +(String)entityOptions.getSelectedItem()+" "+quickSearchText.getText(), searchResultsPanel);
				MainWindowInterface.setTabOpenedFlag(4);
			}
		}

		//getters and setters
		public JPanel getQuickSearch() {
			return quickSearch;
		}

		public void setQuickSearch(JPanel quickSearch) {
			this.quickSearch = quickSearch;
		}
		
		
}
