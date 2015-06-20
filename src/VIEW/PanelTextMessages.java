package VIEW;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

//import VIEW.TextAreaOutputStream;

public class PanelTextMessages extends JPanel {
	
	private JLabel labelTextMessages;
	private String labelTitle = new String("Application Messages and Error Messages");
	
	private JTextArea txtConsole = new JTextArea(10,10);
	
	
	private String appErrorMessage = new String();
	private JPanel masterPanel = new JPanel();
	private JPanel textMessagesContainer = new JPanel();
	//private JTextArea textMessages = new JTextArea(20,20);
	
	//class constructor
	public PanelTextMessages(){
		super();
		
		masterPanel=new JPanel();
		masterPanel.setLayout(new BorderLayout());
		masterPanel.setPreferredSize(new Dimension(500,200));
		masterPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		labelTextMessages = new JLabel(labelTitle, JLabel.LEFT);
		labelTextMessages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		txtConsole.setEditable(false);
		
		textMessagesContainer = new JPanel();
		textMessagesContainer.setLayout(new BoxLayout(textMessagesContainer, BoxLayout.Y_AXIS));
		textMessagesContainer.add(txtConsole);
		
		masterPanel.add(labelTextMessages, BorderLayout.PAGE_START);
		masterPanel.add(txtConsole);
		
	}

	//getters and setters
	public JPanel getMasterPanel() {
		return masterPanel;
	}

	public void setMasterPanel(JPanel masterPanel) {
		this.masterPanel = masterPanel;
	}


	public JTextArea getTxtConsole() {
		return txtConsole;
	}


	public void setTxtConsole(JTextArea txtConsole) {
		this.txtConsole = txtConsole;
	}
	
	
	
	
}

