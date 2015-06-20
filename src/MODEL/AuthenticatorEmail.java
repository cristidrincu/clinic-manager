package MODEL;

import javax.mail.PasswordAuthentication;

public class AuthenticatorEmail  extends javax.mail.Authenticator {
	private PasswordAuthentication authentication;
	
	public AuthenticatorEmail() {
	String username = "cristian.drincu@globe-studios.com";
	String password = "cristian81";
	authentication = new PasswordAuthentication(username, password);
	
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	
	}	
}

