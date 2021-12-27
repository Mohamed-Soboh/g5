package common;

import java.io.Serializable;

public class BussinessUser implements Serializable {
	public BussinessUser(String iD, String firstName, String lastName, String phoneNumber, String email, float w4c,
			String company) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		Company = company;
		this.w4c=w4c;
	}

	public BussinessUser(String iD, String firstName, String lastName, String phoneNumber, String email,
			String company) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		Company = company;
	}

	public float getW4C() {
		return w4c;
	}

	public void setW4C(float w4c) {
		this.w4c = w4c;
	}

	@Override
	public String toString() {
		return "BussinessUser [ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", PhoneNumber="
				+ PhoneNumber + ", Email=" + Email + ", W4C=" + ", Company=" + Company + ", Confirm=" + Confirm
				+ "]";
	}

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String FirstName;
	private String LastName;
	private String PhoneNumber;
	private String Email;
	private String Company;
	private int Confirm;
	private float w4c;
	W4CBussiness W4CBussiness;
	public BussinessUser(String iD, String firstName, String lastName, String phoneNumber, String email,float w4c,
			String company,int Confirm) {
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		Company = company;
		this.w4c=w4c;
		this.Confirm=Confirm;
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

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getConfirm() {
		return Confirm;
	}

	public void setConfirm(int confirm) {
		Confirm = confirm;
	}
	
	
}
