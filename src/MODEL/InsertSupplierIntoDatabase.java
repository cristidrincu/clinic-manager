package MODEL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class InsertSupplierIntoDatabase extends InsertEntityIntoDatabase {
	private Box supplierBox;
	private JPanel mainContainer;
	private JLabel inputEntityTitle;
	
	//supplier text fields and other components
	private JTextField supplierBusinessNameHolder;
	private JTextField supplierBusinessAddressHolder;
	
	//supplier labels
	private JLabel supplierBusinessNameLabel;
	private JLabel supplierBusinessAddressLabel;
	
	
	public InsertSupplierIntoDatabase(){
		supplierBox = Box.createVerticalBox();
		
		supplierBusinessNameLabel = new JLabel("Business name: ");
		supplierBusinessAddressLabel = new JLabel("Business address: ");
		
		supplierBusinessNameHolder = new JTextField();
		supplierBusinessAddressHolder = new JTextField();
		
		supplierBox.add(supplierBusinessNameLabel);
		supplierBox.add(Box.createVerticalStrut(5));
		supplierBox.add(supplierBusinessNameHolder);
		
		supplierBox.add(Box.createVerticalStrut(5));
		
		supplierBox.add(supplierBusinessAddressLabel);
		supplierBox.add(Box.createVerticalStrut(5));
		supplierBox.add(supplierBusinessAddressHolder);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
	
		mainContainer.add(inputEntityTitle=new JLabel("INSERT NEW SUPPLIER"));
		mainContainer.add(super.getInputBox());
		mainContainer.add(supplierBox);
		
		Object msg[] = {mainContainer};
		Object type[] = {"Insert", "Cancel"};
		
		JOptionPane.showOptionDialog(null, msg, "Insert Dialog",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
	}
}
