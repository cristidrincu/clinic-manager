package VIEW;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {

	private static final TextAreaOutputStream INSTANCE = new TextAreaOutputStream();
	private static final PrintStream OUT;
	private static JTextArea outWriter;
	private static boolean TAINTED = false;
	static{
		OUT = System.out;
		System.setOut(new PrintStream(new TextAreaOutputStream()));
	}
	
	//creates a new instance of TextAreaOutputStream
	//private TextAreaOutputStream(){}
	
	public static TextAreaOutputStream getInstance(JTextArea textArea){
		outWriter = textArea;
		return INSTANCE;
	}
	
	public static PrintStream getOldSystemOut(){
		return OUT;
	}
	
	//determines if output has occured
	public static boolean isTainted(){
		return TAINTED;
	}
	
	//write output to the text area
	public void write(int param){
		outWriter.setText(outWriter.getText() + (char)param);
		TAINTED = true;
	}

}
