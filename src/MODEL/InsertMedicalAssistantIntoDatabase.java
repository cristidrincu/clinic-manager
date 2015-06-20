package MODEL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertMedicalAssistantIntoDatabase extends InsertEntityIntoDatabase {
	private Box assistantBox;
	private JPanel mainContainer;
	private JLabel inputEntityTitle;
	
	private JLabel assistantExperienceLabel;
	
	private JTextField assistantExperienceHolder;
	
	public InsertMedicalAssistantIntoDatabase(){
		assistantBox = Box.createVerticalBox();
		assistantExperienceLabel = new JLabel("Assistant's experience: ");
		assistantExperienceHolder = new JTextField();
		
		assistantBox.add(Box.createVerticalStrut(5));
		assistantBox.add(assistantExperienceLabel);
		assistantBox.add(Box.createVerticalStrut(5));
		assistantBox.add(assistantExperienceHolder);
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		mainContainer.add(inputEntityTitle=new JLabel("INSERT NEW MEDICAL ASSISTANT!"));
		mainContainer.add(super.getInputBox());
		mainContainer.add(assistantBox);
		
		Object msg[] = {mainContainer};
		Object type[] = { "Insert", "Cancel"};
		
		JOptionPane.showOptionDialog(null, msg, "Insert Dialog",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, type,
		        null);
		
	}
}
