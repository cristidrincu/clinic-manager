package MAIN_APP;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

//import MODEL.*;

import VIEW.MainWindowInterface;

public class Main_App {
	
	static MainWindowInterface appInterface;

	public static void main(String[] args) {
		//sets the look and feel of the application according to the Operating System it runs on
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			try {
	        // Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}catch (UnsupportedLookAndFeelException ulfe) {
				System.err.println(ulfe);
			}catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
		    }
		    catch (InstantiationException ie) {
		    	System.err.println(ie);
		    }
		    catch (IllegalAccessException iae) {
		    	System.err.println(iae);
		    }
		}
		//separate thread for the app
		 SwingUtilities.invokeLater(new Runnable(){
			 public void run(){
				 appInterface = new MainWindowInterface();
			 }
		 });       
	}
}
