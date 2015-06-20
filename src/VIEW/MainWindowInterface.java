package VIEW;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import java.awt.*;
import ACTIONS.FileMenuAction;
import ACTIONS.GenerateReportsActions;
import ACTIONS.HelpActions;
import ACTIONS.NewActions;
import ACTIONS.EditActions;
import ACTIONS.ViewActions;
import ACTIONS.ButtonMethods;//interface
import ACTIONS.WelcomePanelsActions;

import MODEL.*;

public class MainWindowInterface extends JFrame implements ButtonMethods {
	
	private PanelTextMessages textMessagesPanel = new PanelTextMessages();
	
	private PanelLatestPacients latestPacientsPanel = new PanelLatestPacients(1);
	private PanelLatestDiagnostics latestDiagnosticsPanel = new PanelLatestDiagnostics(2);
	
	private static final int MAX_WIDTH_SPRT=18;
	private static final int MAX_HEIGHT_SPRT=5;
	private static int welcomeTabObject = 1;
	
	private Dimension dim;
	private Toolkit kit;
	
	private static JPanel mainContainer = new JPanel();
	private JToolBar toolbar = new JToolBar();
	
	//flag starts from 0
	private static JTabbedPane welcomeTab = new JTabbedPane();//flag 0
	private static JTabbedPane mainTab = new JTabbedPane();//flag 1
	private static JTabbedPane openedFilesTab = new JTabbedPane();//flag 2
	private static JTabbedPane reportsTab = new JTabbedPane(); //flag 3
	private static JTabbedPane quickSearchTab = new JTabbedPane();//flag 4
	
	
	private FileMenuAction openAction, saveAction, saveAsAction, printAction, exitAction;
	private NewActions pacientAction, doctorAction, diagnosticAction, sendEmailAction, appointmentAction, prescriptionAction;
	private EditActions pacientEdit, doctorEdit, departmentEdit, diagnosticsEdit;
	private ViewActions allPacientsView, allDoctorsView, allDepartmentsView, allDiagnosticsView, allPrescriptionsView;
	private GenerateReportsActions 
			getDiagnosticForPacient,getPrescriptionForPacient,getDiagnosticsAndDoctorForPacient, 
			getAllPrescriptionsForDiagnostic, getAllPrescriptionsForDoctor, getAllAppointmentsDoctor, getAllPacientsCertainOccupation,
			getAllPacientsAgeRange;
	private WelcomePanelsActions viewPanelLatestPacients;//, viewPanelLatestDiagnostics;
	private HelpActions helpView;
	
	private static int tabOpenedFlag;
	
	//testing
	private static AppointmentDisplay appDisplay = new AppointmentDisplay();
	private QuickSearch quickSearchElement = new QuickSearch();
	
	
	
	//class constructor
	public MainWindowInterface(){
		super("ClinicManager ver. 1.0");
		this.setBackground(Color.darkGray);
		
		kit = Toolkit.getDefaultToolkit();
		dim=kit.getScreenSize();	
		
		JMenuBar mainMenu = new JMenuBar();
		setJMenuBar(mainMenu);
		toolbar.setRollover(true);		
		
		//Main items on the menu
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu viewMenu = new JMenu("View");
		JMenu viewAllEntities = new JMenu("View");
		JMenu viewPanels = new JMenu("Panels");	
		JMenu generateReportsMenu = new JMenu("ReportsDBEntities");
		JMenu generateReportsPacientsMenu = new JMenu("Reports for pacients");
		JMenu generatePrescriptionsMenu = new JMenu("Reports for prescriptions");
		JMenu generateAppointmentsMenu = new JMenu("Reports for appointments");
		JMenu helpMenu = new JMenu("Help");
		JMenu newOptions = new JMenu("New");
		
		//-------------------------------NEW ACTIONS -----------------------------
		
		JMenu employeeActions = new JMenu("Employee");
		doctorAction = new NewActions("Doctor", "This will insert a new doctor object in the database!", "createDoctorObject");
		//medicalAssistantAction = new NewActions("Medical Assistant", "This will insert a new medical assistant in the database!", "createMedicalAssistantObject" );
		diagnosticAction = new NewActions("Diagnostic", "This will insert a new diagnostic in the database!","createDiagnostic");
		sendEmailAction = new NewActions("Create E-Mail", "Sends email to pacients", "createEmailClient");
		appointmentAction = new NewActions("Create appointment", "Creates an appointment", "createAppointment");
		prescriptionAction = new NewActions("Prescription", "This will insert a prescription into the database!", "createPrescription");
		
		employeeActions.add(new JMenuItem(doctorAction));
		
		
		//employeeActions.add(new JMenuItem(medicalAssistantAction));
		
		pacientAction=new NewActions("Patient", "This will insert a new pacient inside the database", "createNewPacientObject");
		
		newOptions.add(new JMenuItem(pacientAction));
		newOptions.add(new JMenuItem(diagnosticAction));
		newOptions.add(employeeActions);
		newOptions.add(new JMenuItem(sendEmailAction));
		newOptions.add(new JMenuItem(appointmentAction));
		newOptions.add(new JMenuItem(prescriptionAction));
		

		
		//------------------------FILE ACTIONS---------------------------------------------
		
		openAction = new FileMenuAction("Open", KeyStroke.getKeyStroke('O'),"Opens a file", "openFile"); //opens an xml document
		saveAction = new FileMenuAction("Save", KeyStroke.getKeyStroke('S'), "Saves a file", "saveFile"); //saves the current open document
		saveAsAction = new FileMenuAction("Save as...", "Saves a file as...","saveFileAs");
		printAction = new FileMenuAction("Print", KeyStroke.getKeyStroke('P'), "Prints a file","printFile");
		exitAction = new FileMenuAction("Exit", "Exits the current program","exitProgram");
			
		fileMenu.add(newOptions);
			fileMenu.add(new JMenuItem(openAction));
			fileMenu.add(new JMenuItem(saveAction));
			fileMenu.add(new JMenuItem(saveAsAction));
			fileMenu.add(new JMenuItem(printAction));
			fileMenu.add(new JMenuItem(exitAction));
			
		//---------------EDIT ACTIONS ----------------
		pacientEdit = new EditActions("Patient", "Edits a pacient object from the database!", "editPacientData");
		doctorEdit = new EditActions("Doctor", "Edits a doctor object from the database!", "editDoctorData");
		
		departmentEdit = new EditActions("Department", "Edits department object from the database!","editDepartmentData");
		diagnosticsEdit = new EditActions("Diagnostics", "Edits a diagnostic for a pacient!","editDiagnosticData");
		
		editMenu.add(pacientEdit);
		editMenu.add(doctorEdit);
		
		editMenu.addSeparator();
		
		editMenu.add(departmentEdit);
		editMenu.add(diagnosticsEdit);
		
		
		viewMenu.add(viewAllEntities);
		
		//------------------------VIEW ACTIONS----------------
		allPacientsView = new ViewActions("Patients", "This action will retrieve all pacients from the database, sorted alphabetically after their last name!", "viewAllPacients");
		allDoctorsView = new ViewActions("Doctors", "This action will retrieve all doctors from the database, sorted alphabetically!", "viewAllDoctors");
		//allMedicalAssistantsView = new ViewActions("Medical Assistants", "This action will retrieve all medical assistants from the database, sorted alphabetically!", "viewAllAssistants");
		allDepartmentsView = new ViewActions("Departments", "This action will retrieve all departments from the database, sorted alphabetically!", "viewAllDepartments");
		allDiagnosticsView = new ViewActions("Diagnostics", "This action will retrieve all diagnostics from the database, sorted by pacient!", "viewAllDiagnostics");
		allPrescriptionsView = new ViewActions("Prescriptions", "This action will retrieve all prescriptions from the database, sorted by doctor! ", "viewAllPrescriptions");
		
		viewPanelLatestPacients = new WelcomePanelsActions("Welcome panels", "showWelcomePanels");
//		viewPanelLatestDiagnostics = new WelcomePanelsActions("Latest Diagnostics", latestDiagnosticsPanel.getPanelLatestDiagnostics(),21);
//		viewSuppliesDoctorActivityAction = new WelcomePanelsActions("Latest Supplies And Doctor Activity", latestMasterSuppliesDoctor.getMasterContainer(),31);
		
		viewAllEntities.add(new JMenuItem(allPacientsView));
		viewAllEntities.add(new JMenuItem(allDoctorsView));
		//viewAllEntities.add(new JMenuItem(allMedicalAssistantsView));
		viewAllEntities.add(new JMenuItem(allDepartmentsView));
		viewAllEntities.add(new JMenuItem(allDiagnosticsView));
		viewAllEntities.add(new JMenuItem(allPrescriptionsView));
		
		viewMenu.addSeparator();
		
		viewPanels.add(new JMenuItem(viewPanelLatestPacients));
		//viewPanels.add(new JMenuItem(viewPanelLatestDiagnostics));
		//viewPanels.add(new JMenuItem(viewSuppliesDoctorActivityAction));
		
		viewMenu.add(viewPanels);
		
		//----------------------GENERATE REPORTS ACTIONS--------------------------
		getDiagnosticForPacient = new GenerateReportsActions("Get all patients with user supplied diagnostic", "getPacientsWithDiagnostic");
		getPrescriptionForPacient = new GenerateReportsActions("Get all prescriptions with user supplied patient", "getPacientsWithSubscription");
		getDiagnosticsAndDoctorForPacient = new GenerateReportsActions("Get all patients with user supplied doctor","getPacientsWithDoctor");
		getAllPacientsCertainOccupation = new GenerateReportsActions("Get all patients with a certain occupation","getPacientsForOccupation");
		getAllPacientsAgeRange = new GenerateReportsActions("Get all patients between user supplied age range", "getPacientsAgeRange");
		getAllPrescriptionsForDiagnostic = new GenerateReportsActions("Retrieve all prescriptions for user supplied diagnostic", "getPrescriptionsForDiagnostic");
		getAllPrescriptionsForDoctor = new GenerateReportsActions("Retrieve all prescriptions for user supplied doctor","getPrescriptionsForDoctor");
		getAllAppointmentsDoctor = new GenerateReportsActions("Retrieve all appointments for supplied doctor", "getAppointmentsForDoctor");
		
		
		generateReportsMenu.add(generateReportsPacientsMenu);//create submenu
		generateReportsPacientsMenu.add(new JMenuItem(getDiagnosticForPacient));
		generateReportsPacientsMenu.add(new JMenuItem(getPrescriptionForPacient));
		generateReportsPacientsMenu.add(new JMenuItem(getDiagnosticsAndDoctorForPacient));
		generateReportsPacientsMenu.add(new JMenuItem(getAllPacientsCertainOccupation));
		generateReportsPacientsMenu.add(new JMenuItem(getAllPacientsAgeRange));
		generateReportsMenu.addSeparator();
		generatePrescriptionsMenu.add(new JMenuItem(getAllPrescriptionsForDiagnostic));
		generatePrescriptionsMenu.add(new JMenuItem(getAllPrescriptionsForDoctor));
		generateReportsMenu.add(generatePrescriptionsMenu);//submenu for prescriptions
		generateReportsMenu.addSeparator();
		generateAppointmentsMenu.add(new JMenuItem(getAllAppointmentsDoctor));
		generateReportsMenu.add(generateAppointmentsMenu);
		
		//---------------------HELP ACTIONS------------------
		helpView = new HelpActions("View Help Manual","Access the help files for the application", "viewHelpFiles");
		helpMenu.add(new JMenuItem(helpView));
		
		
		
		mainMenu.add(fileMenu);
		mainMenu.add(editMenu);
		mainMenu.add(viewMenu);
		mainMenu.add(generateReportsMenu);
		mainMenu.add(helpMenu);
		
		toolbar.add(addToolbarBtn(pacientAction, "newAction", "Add a new pacient"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(openAction, "openAction", "Open a file"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(allDiagnosticsView, "viewDiagnostics", "Displays all diagnostics from the database"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(pacientEdit, "pacientEdit", "Edit a pacient"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(doctorEdit, "doctorEdit", "Edit a doctor"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(departmentEdit, "departmentEdit", "Edit a department"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(getDiagnosticForPacient,"pacient_report","Generate pacient report regarding diagnostics"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(getAllPrescriptionsForDoctor,"prescription_report","Generate prescription report for selected doctor"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(addToolbarBtn(getAllAppointmentsDoctor,"appointment_report","Generate appointment report for selected doctor"));
		toolbar.addSeparator(addSeparatorSpace());
		toolbar.add(quickSearchElement.getQuickSearch());
		
		mainContainer.setLayout(new BorderLayout());
		mainTab.setVisible(false);
		
		getContentPane().add(toolbar, BorderLayout.NORTH);
		mainContainer.add(textMessagesPanel.getMasterPanel(), BorderLayout.PAGE_END);
		
		welcomeTab.addTab("Appointments", appDisplay.getPanelAllAppointments());
//		welcomeTab.addTab("Latest pacients added", latestPacientsPanel.getPanelLatestResultsPacients());
//		welcomeTab.addTab("Latest diagnostics added",latestDiagnosticsPanel.getPanelLatestResultsDiagnostics());
		
		
		
		mainContainer.add(welcomeTab, BorderLayout.PAGE_START);
		//welcomeTabObject++;
		MainWindowInterface.setTabOpenedFlag(0);
		
		getContentPane().add(mainContainer);
		
		setBounds(0,0,dim.width, (dim.height-45));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//pack();
	}


	
	public JButton addToolbarBtn(Action action, String icon, String tooltip) {
		JButton btn = new JButton(action);
		btn.setAlignmentY(TOP_ALIGNMENT);
		btn.setBorder(null);
		btn.setText(null);
		btn.setIcon(new ImageIcon("../APLICATIE_POLICLINCA/Images/" + icon + ".png"));
		btn.setToolTipText(tooltip);
		return btn;
	}


	
	public Dimension addSeparatorSpace() {
		Dimension dim = new Dimension();
		dim.width=MAX_WIDTH_SPRT;
		dim.height=MAX_HEIGHT_SPRT;
		return dim;
	}

	public Dimension getDim() {
		return dim;
	}


	public void setDim(Dimension dim) {
		this.dim = dim;
	}


	public static JPanel getMainContainer() {
		return mainContainer;
	}

	public void setMainContainer(JPanel mainContainer) {
		MainWindowInterface.mainContainer = mainContainer;
	}
	
	public static void addPanelToMaster(JPanel aPanel){
		mainContainer.add(aPanel);
	}
	
	public static void removePanelFromMaster(JPanel aPanel){
		mainContainer.remove(aPanel);
	}

	public static JTabbedPane getWelcomeTab() {
		return welcomeTab;
	}


	public static void setWelcomeTab(JTabbedPane welcomeTab) {
		MainWindowInterface.welcomeTab = welcomeTab;
	}


	public static JTabbedPane getMainTab() {
		//mainTab.setVisible(true);
		return mainTab;
	}
	
	public static JTabbedPane hideMainTab(){
		mainTab.setVisible(false);
		return mainTab;
	}
	
	public static void removeComponent(Component aComponent){
		mainContainer.remove(aComponent);
		mainContainer.revalidate();
		mainContainer.repaint();
	}

	public static int getWelcomeTabObject() {
		return welcomeTabObject;
	}


	public static void setWelcomeTabObject(int welcomeTabObject) {
		MainWindowInterface.welcomeTabObject = welcomeTabObject;
	}


	public static JTabbedPane getOpenedFilesTab() {
		return openedFilesTab;
	}


	public static void setOpenedFilesTab(JTabbedPane openedFilesTab) {
		MainWindowInterface.openedFilesTab = openedFilesTab;
	}
	

	public static JTabbedPane getQuickSearchTab() {
		return quickSearchTab;
	}

	public static JTabbedPane getReportsTab() {
		return reportsTab;
	}


	public static void setReportsTab(JTabbedPane reportsTab) {
		MainWindowInterface.reportsTab = reportsTab;
	}


	public static void setQuickSearchTab(JTabbedPane quickSearchTab) {
		MainWindowInterface.quickSearchTab = quickSearchTab;
	}


	public static int getTabOpenedFlag() {
		return tabOpenedFlag;
	}


	public static void setTabOpenedFlag(int tabOpenedFlag) {
		MainWindowInterface.tabOpenedFlag = tabOpenedFlag;
	}


	public static AppointmentDisplay getAppDisplay() {
		return appDisplay;
	}


	public static void setAppDisplay(AppointmentDisplay appDisplay) {
		MainWindowInterface.appDisplay = appDisplay;
	}
}
