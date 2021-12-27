package common;

import java.io.Serializable;


public class User implements Serializable{
 
	@Override
	public String toString() {
		return "User [ID=" + ID + ", UserName=" + UserName + ", Password=" + Password + ", UserType=" + UserType
				+ ", IsLoggedIn=" + IsLoggedIn + ", Confirm=" + Confirm + ", Status=" + Status + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String UserName;
	private String Password;
	private String UserType;
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
	public User(String UserName,String Password) {
		this.UserName=UserName;
		this.Password=Password;
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
