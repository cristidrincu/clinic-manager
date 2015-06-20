package MODEL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import entities.Doctor;
import entities.Person;

public class SaveEntityToFile implements Serializable {
	
	//calendar object for knowing when the file was created - this information comes at the top of the file
	private static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
	private static final String FILE_DATE_FORMAT = "dd-MM-yyyy";
	
	private File file;
	private String pacientDirectory = "../APLICATIE_POLICLINCA/Pacients";
	private String doctorDirectory = "../APLICATIE_POLICLINCA/Doctors";
	private FileOutputStream f;
	
	private FileWriter fStream;
	private BufferedWriter out;
	
	public static String getTodaysDate(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	public static String getFileDate(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(FILE_DATE_FORMAT);
		return sdf.format(cal.getTime());
	}
	
	//class method for writing a person object to a file
	public void writePacientToFile(Person person){
		//check to see object type and write accordingly
		//new File(pacientDirectory+"/"+SaveEntityToFile.getTodaysDate());
		file = new File(pacientDirectory+"/"+person.getFirstName()+person.getLastName()+"_"+SaveEntityToFile.getFileDate()+".txt");
		if(!file.exists()){
			try {
				file.createNewFile();
				fStream=new FileWriter(file);
				out = new BufferedWriter(fStream);
				out.write(
						"Entity inserted into database at:" +" "+
						SaveEntityToFile.getTodaysDate()+"\n"+
						"First name: "+
						person.getFirstName()+"\n"+
						"Last name: "+
						person.getLastName()+"\n"+
						"Address: "+
						person.getAddress()+"\n"+
						"Sex: "+
						person.getSex() + "\n"+
						"Age: " + 
						person.getAge() +"\n"+
						"ID Card Number: " +
						person.getIdCard()+"\n"+
						"Phone number: " + 
						person.getPhoneNumber()+ "\n"+
						"Email: " + 
						person.getEmail() + "\n"+
						"Occupation: " + 
						person.getOccupation()
						);
				out.close();
				System.out.println("File created and written to!");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else{//let the user know that the file already exists and return
			JOptionPane.showMessageDialog(null, "File already exists!");
			return;
		}
	}
	public void writeDoctorToFile(Person person,Doctor doctor){
		file = new File(doctorDirectory+"/"+person.getFirstName()+person.getLastName()+"_"+SaveEntityToFile.getFileDate()+".txt");
		if(!file.exists()){
			try{
				file.createNewFile();
				fStream=new FileWriter(file);
				out = new BufferedWriter(fStream);
				out.write(
						"Doctor inserted into database at:"+ " "+
						SaveEntityToFile.getTodaysDate()+"\n"+
						"Doctor's First Name: "+
						person.getFirstName()+"\n"+
						"Doctor's Last Name: "+person.getLastName()+"\n"+
						"Doctor's Email Address: " + person.getEmail()+"\n"+
						"Doctor's Phone Number: " + person.getPhoneNumber()+"\n"
						+"Doctor's specialty: " + doctor.getSpecializedIn() +"\n"
						+"Doctor's department: " + doctor.getDepartment() + "\n"
						+"Doctor's experience: " + doctor.getExperience()
						);
				out.close();
				System.out.println("File created and written to!");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
