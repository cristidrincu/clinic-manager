package VIEW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ACTIONS.WelcomePanelsActions;
import MODEL.DatabaseBrowseLatestSupplies;
import MODEL.DatabaseBrowseLatestDoctorActivity;

public class PanelMasterSuppliesDoctorActivity extends JPanel {
	
	private JLabel panelTitleSupplies;
	private JLabel panelTitleDoctorActivity;
	
	private static JPanel masterContainerSDA = new JPanel();
	private JPanel updateSupplies = new JPanel();
	private JPanel updateDoctorActivity = new JPanel();
	
	private JMenuBar menuContainer1 = new JMenuBar();
	
	private JMenu fileMenuPanel = new JMenu("File");
	private JMenu helpMenuPanel = new JMenu("Help");
	
	private DatabaseBrowseLatestSupplies browseSupplies;
	private DatabaseBrowseLatestDoctorActivity browseDoctors;
	
	//private WelcomePanelsActions closeAction = new WelcomePanelsActions("Close",masterContainerSDA,3);
	
	public PanelMasterSuppliesDoctorActivity(){
		super();
		
		browseSupplies = new DatabaseBrowseLatestSupplies();
		browseDoctors = new DatabaseBrowseLatestDoctorActivity();
		
		masterContainerSDA.setLayout(new BoxLayout(masterContainerSDA, BoxLayout.Y_AXIS));
		masterContainerSDA.setBorder(BorderFactory.createMatteBorder(0,2,0,0, Color.gray));
		
		menuContainer1.setLayout(new FlowLayout(FlowLayout.LEFT) );
		menuContainer1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		menuContainer1.add(fileMenuPanel);
			//fileMenuPanel.add(new JMenuItem(closeAction));
		menuContainer1.add(helpMenuPanel);
		
		panelTitleSupplies = new JLabel("Latest stock of supplies",JLabel.CENTER);
		panelTitleSupplies.setLayout(new BorderLayout());
		panelTitleSupplies.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panelTitleDoctorActivity = new JLabel("Latest doctor activity", JLabel.CENTER);
		panelTitleDoctorActivity.setLayout(new BorderLayout());
		panelTitleDoctorActivity.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		updateSupplies.setPreferredSize(new Dimension(300,50));
		updateSupplies.setLayout(new BorderLayout());
		updateSupplies.add(panelTitleSupplies, BorderLayout.PAGE_START);
		updateSupplies.add(browseSupplies.getPane());
		
		updateDoctorActivity.setPreferredSize(new Dimension(300,100));
		updateDoctorActivity.setLayout(new BorderLayout());
		updateDoctorActivity.add(panelTitleDoctorActivity, BorderLayout.PAGE_START);
		updateDoctorActivity.add(browseDoctors.getPane());
		
		
		masterContainerSDA.setPreferredSize(new Dimension(350,350));
		masterContainerSDA.add(menuContainer1);
		masterContainerSDA.add(updateSupplies);
		masterContainerSDA.add(updateDoctorActivity);
		
		
	}

	public static JPanel getMasterContainer() {
		return masterContainerSDA;
	}

	public void setMasterContainer(JPanel masterContainer) {
		this.masterContainerSDA = masterContainer;
	}
	
	public static void showMasterContainer(){
		masterContainerSDA.setVisible(true);
	}
	
	public static void hideMasterContainer(){
		masterContainerSDA.setVisible(false);
	}

}
