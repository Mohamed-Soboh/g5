package common;

import java.io.Serializable;

public class Visa implements Serializable{
	private String UserID;
	private String Number;
	private int CVV;
	private int Year;
	private String CardHolderName;
	private int Month;
	
	
	public Visa(String userID, String number, int cVV, int year, String cardHolderName, int month) {
		UserID = userID;
		Number = number;
		CVV = cVV;
		Year = year;
		CardHolderName = cardHolderName;
		Month = month;
	}
	public String getUserID() { 
		return UserID;
	} 
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public int getCVV() {
		return CVV;
	}
	public void setCVV(int cVV) {
		CVV = cVV;
	}
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public String getCardHolderName() {
		return CardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		CardHolderName = cardHolderName;
	}
	public int getMonth() {
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public String toString() {
		return "Visa [UserID=" + UserID + ", Number=" + Number + ", CVV=" + CVV + ", Year="
				+ Year + ", CardHolderName=" + CardHolderName + ", Month=" + Month + "]";
	}
	
	
	

}
