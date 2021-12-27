package common;

import java.io.Serializable;

public class HRUser implements Serializable {
	
	private String ID;
	private String FirstName;
	private String LastName;
	private String Compnay;
	public HRUser(String iD, String firstName, String lastName, String compnay) {
		super();
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		Compnay = compnay;
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
	@Override
	public String toString() {
		return "HRUser [ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Compnay=" + Compnay
				+ "]";
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
	public String getCompnay() {
		return Compnay;
	}
	public void setCompnay(String compnay) {
		Compnay = compnay;
	}

}
