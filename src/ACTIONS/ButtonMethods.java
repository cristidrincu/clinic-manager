package ACTIONS;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;

public interface ButtonMethods {
	//add a button to a toolbar
	JButton addToolbarBtn(Action action, String icon, String tooltip);
	Dimension addSeparatorSpace();		
}
