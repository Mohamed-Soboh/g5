package common;

import java.io.Serializable;

public class BranchManager implements Serializable{

	private String ManagerID;
	private String FistName;
	private String LastName;
	private String Email;
	private String PhoneNumber;
	private String location;
	
	public BranchManager(String managerID, String fistName, String lastName, String email, String phoneNumber,
			String location) {
		ManagerID = managerID;
		FistName = fistName;
		LastName = lastName;
		Email = email;
		PhoneNumber = phoneNumber;
		this.location = location;
	}

	public String getManagerID() {
		return ManagerID;
	}

	public void setManagerID(String managerID) {
		ManagerID = managerID;
	}

	public String getFistName() {
		return FistName;
	}

	public void setFistName(String fistName) {
		FistName = fistName;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(int location) {
		location = location;
	}
	
	

}
