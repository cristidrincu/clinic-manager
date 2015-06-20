package ACTIONS;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;

public abstract class ActionBaseClass extends AbstractAction {
	
	public ActionBaseClass(String actionName){
		super(actionName);
	}
	
	public ActionBaseClass(String actionName, String tooltip){
		this(actionName);
		if(tooltip!=null){
			putValue(SHORT_DESCRIPTION, tooltip);
		}
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){}//abstract method
}
