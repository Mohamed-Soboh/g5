package common;

import java.io.Serializable;

public class Delivery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstname;
	private String phonenumber;
	private String address;
	private String deliveryType;
	private String date;
	private String hour;
	private String minute;
	private String clientText;
	private int orderNum;
	private int deliveryNum;
	
	public int getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(int deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Delivery(String firstname, String phonenumber, String address, String deliveryType, String date, String hour,
			String minute, String clientText,int orderNum) {
		this.firstname = firstname;
		this.phonenumber = phonenumber;
		this.address = address;
		this.deliveryType = deliveryType;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.clientText = clientText;
		this.orderNum=orderNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getClientText() {
		return clientText;
	}

	public void setClientText(String clientText) {
		this.clientText = clientText;
	}	
	
	@Override
	public String toString() {
		return "Delivery [firstname=" + firstname + ", phonenumber=" + phonenumber + ", address=" + address
				+ ", deliveryType=" + deliveryType + ", date=" + date + ", hour=" + hour + ", minute=" + minute
				+ ", clientText=" + clientText + "]";
	}
}
