package entities;

public class Person {
	//access atribute protected - class data members needed when working with pacient and doctor subclasses
	protected String ID;
	protected String firstName;
	protected String lastName;
	protected String age;
	protected String sex;
	protected String idCard;
	protected String address;
	protected String phoneNumber;
	protected String email;
	private String occupation;
	
	//default constructor
	public Person(){}
	
	//class constructor for firstName,lastName,address,sex,age,id card, phone number, email address - used for extracting a doctor
	public Person(String firstN,String lastN, String address, String sex, String age, String idCard, String phoneNumber, String email){
		this.firstName=firstN;
		this.lastName=lastN;
		this.address=address;
		this.sex=sex;
		this.age = age;
		this.idCard=idCard;
		this.phoneNumber=phoneNumber;
	}
	
	//class constructor for extracting a complete detailed person
	public Person(String IDN,String firstN, String lastN, String ageN, String sexN, String idN, String addressN, String phoneN, String emailN, String occupationN){
		this.ID = IDN; 
		this.firstName=firstN;
		this.lastName=lastN;
		this.age=ageN;
		this.sex=sexN;
		this.idCard=idN;
		this.address=addressN;
		this.phoneNumber=phoneN;
		this.email = emailN;
		this.occupation = occupationN;
	}
	
	//class constructor for extracting a complete person detailed person
		public Person(String firstN, String lastN, String ageN, String sexN, String idN, String addressN, String phoneN, String emailN, String occupationN){
			
			this.firstName=firstN;
			this.lastName=lastN;
			this.age=ageN;
			this.sex=sexN;
			this.idCard=idN;
			this.address=addressN;
			this.phoneNumber=phoneN;
			this.email = emailN;
			this.occupation = occupationN;
		}

	public Person(String ID){
		this.ID=ID;
	}	
		
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
}
