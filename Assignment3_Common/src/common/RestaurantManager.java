package common;

import java.io.Serializable;

public class RestaurantManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String UserID;
	private String FirstName;
	private String LastName;
	private String UserName;
	private String Password;
	private String Email;
	private String PhoneNumber;
	private int IsLoogedIn;
	private int IDRestaurant;
	
	
	public RestaurantManager(String userID, String firstName, String lastName, String userName, String password,
			String email, String phoneNumber, int isLoogedIn, int iDRestaurant) {
		super();
		UserID = userID;
		FirstName = firstName;
		LastName = lastName;
		UserName = userName;
		Password = password;
		Email = email;
		PhoneNumber = phoneNumber;
		IsLoogedIn = isLoogedIn;
		IDRestaurant = iDRestaurant;
	}
	@Override
	public String toString() {
		return "RestaurantManager [UserID=" + UserID + ", FirstName=" + FirstName + ", LastName=" + LastName
				+ ", UserName=" + UserName + ", Password=" + Password + ", Email=" + Email + ", PhoneNumber="
				+ PhoneNumber + ", IsLoogedIn=" + IsLoogedIn + ", IDRestaurant=" + IDRestaurant + "]";
	}
	public RestaurantManager(String userID, String firstName, String lastName, String email, String phoneNumber,
			int iDRestaurant) {
		super();
		UserID = userID;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
		PhoneNumber = phoneNumber;
		IDRestaurant = iDRestaurant;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
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
	public int getIsLoogedIn() {
		return IsLoogedIn;
	}
	public void setIsLoogedIn(int isLoogedIn) {
		IsLoogedIn = isLoogedIn;
	}
	public int getIDRestaurant() {
		return IDRestaurant;
	}
	public void setIDRestaurant(int iDRestaurant) {
		IDRestaurant = iDRestaurant;
	}
	
	

}
