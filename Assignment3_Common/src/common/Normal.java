package common;

import java.io.Serializable;

/**
 * @author moham
 *
 */
public class Normal implements Serializable{
	public Normal(String iD, String firstName, String lastName, String email, String phoneNumber, int visaIsAvailable) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		PhoneNumber = phoneNumber;
		VisaIsAvailable = visaIsAvailable;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String FirstName;
	private String LastName;
	private String Email;
	private String PhoneNumber;
	private int VisaIsAvailable;
	private float W4C;
	public Normal(String iD, String firstName, String lastName, String email, String phoneNumber,int visaIsAvailable, float w4c) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		PhoneNumber = phoneNumber;
		VisaIsAvailable = visaIsAvailable;
		W4C = w4c;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public int getVisaIsAvailable() {
		return VisaIsAvailable;
	}
	public void setVisaIsAvailable(int visaIsAvailable) {
		VisaIsAvailable = visaIsAvailable;
	}
	public float getW4C() {
		return W4C;
	}
	public void setW4C(float w4c) {
		W4C = w4c;
	}
	

}
