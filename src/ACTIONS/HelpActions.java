package ACTIONS;

import java.awt.event.ActionEvent;

import VIEW.HelpView;

public class HelpActions extends ActionBaseClass {

	private String actionID;
	private HelpView help;
	
	public HelpActions(String actionName, String tooltip, String actionID){
		super(actionName);
		this.actionID=actionID;
		if(tooltip!=null){
			putValue(SHORT_DESCRIPTION, tooltip);
		}
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){
		switch(actionID){
			case "viewHelpFiles":
				setHelp(new HelpView());
			break;
		}
	}

	//getters and setters
	public HelpView getHelp() {
		return help;
	}

	public void setHelp(HelpView help) {
		this.help = help;
	}
}
