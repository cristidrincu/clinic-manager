package VIEW;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.BevelBorder;

public class PanelLatestBaseClass {
	
	protected JMenuBar menuContainer = new JMenuBar();
	protected JMenu fileMenuPanel = new JMenu("File");
	protected JMenu helpMenuPanel = new JMenu("Help");
	
	//class constructor
		public PanelLatestBaseClass() {
			menuContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
			menuContainer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			menuContainer.add(fileMenuPanel);
			menuContainer.add(helpMenuPanel);		
		}
}
