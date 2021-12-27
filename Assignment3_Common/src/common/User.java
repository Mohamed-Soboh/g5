package common;

import java.io.Serializable;


public class User implements Serializable{
 


	@Override
	public String toString() {
		return "User [ID=" + ID + ", UserName=" + UserName + ", Password=" + Password + ", UserType=" + UserType
				+ ", firstname=" + firstname + ", email=" + email + ", phonenumber=" + phonenumber + ", lastname="
				+ lastname + ", company=" + company + ", location=" + location + ", idrestaurant=" + idrestaurant
				+ ", visaavailable=" + visaavailable + ", IsLoggedIn=" + IsLoggedIn + ", Confirm=" + Confirm
				+ ", Status=" + Status + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String UserName;
	private String Password;
	private String UserType;
	private String firstname;
	private String email;
	private String phonenumber;
	private String lastname;
	private String company;
	private String location;
private int idrestaurant;
private int visaavailable;

	public int getVisaavailable() {
	return visaavailable;
}
public void setVisaavailable(int visaavailable) {
	this.visaavailable = visaavailable;
}
	private int IsLoggedIn;
	private int Confirm;
	private String Status;
	private W4CNormal w4c;
	
	public W4CNormal getW4c() {
		return w4c;
	}
	public void setW4c(W4CNormal w4c) {
		this.w4c = w4c;
	}
	
	public User(String iD, String userName, String password, String userType, int isLoggedIn, int confirm,
			String status) {
		super();
		ID = iD;
		UserName = userName;
		Password = password;
		UserType = userType;
		IsLoggedIn = isLoggedIn;
		Confirm = confirm;
		Status = status;
	}
	public User(String iD, String userName, String password, String userType, String firstname, String email,
			String phonenumber, String lastname, String company, String location, int isLoggedIn, int confirm,
			String status,int visaavailable,int idrestaurant) {
		super();
		ID = iD;
		UserName = userName;
		Password = password;
		UserType = userType;
		this.firstname = firstname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.lastname = lastname;
		this.company = company;
		this.location = location;
		IsLoggedIn = isLoggedIn;
		Confirm = confirm;
		Status = status;
		this.idrestaurant=idrestaurant;
	}
	public int getIdrestaurant() {
		return idrestaurant;
	}
	public void setIdrestaurant(int idrestaurant) {
		this.idrestaurant = idrestaurant;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getLastname() {
		return lastname;
	}
	public User(String iD, String userName, String password, String firstname, String email, String phonenumber,
			String lastname) {
		super();
		ID = iD;
		UserName = userName;
		Password = password;
		this.firstname = firstname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.lastname = lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public User(String UserName,String Password) {
		this.UserName=UserName;
		this.Password=Password;
	}
	public User( String userName, String password, String userType) {
		super();
		UserName = userName;
		Password = password;
		UserType = userType;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}
	public int getIsLoggedIn() {
		return IsLoggedIn;
	}
	public void setIsLoggedIn(int isLoggedIn) {
		IsLoggedIn = isLoggedIn;
	}
	public int getConfirm() {
		return Confirm;
	}
	public void setConfirm(int confirm) {
		Confirm = confirm;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
