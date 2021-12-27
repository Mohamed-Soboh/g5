package common;

import java.io.Serializable;

public class BussinessUser implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String FirstName;
	private String LastName;
	private String PhoneNumber;
	private String Email;
	private float W4C;
	private String Company;
	private int Confirm;
	private W4CBussiness w4c;
	private int group_code;
	private int lock;
	private Order order;
	private double OverAllPrice;//for group delivery


	public double getOverAllPrice() {
		return OverAllPrice;
	}

	public void setOverAllPrice(double overAllPrice) {
		OverAllPrice = overAllPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public synchronized int getLock() {
		return lock;
	}

	public synchronized  void setLock(int lock) {
		this.lock = lock;
	}

	public int getGroup_code() {
		return group_code;
	}

	public void setGroup_code(int group_code) {
		this.group_code = group_code;
	}

	public W4CBussiness getW4c() {
		return w4c;
	}

	public void setW4c(W4CBussiness w4c) {
		this.w4c = w4c;
	}

	public BussinessUser(String iD, String firstName, String lastName, String phoneNumber, String email, float w4c,
			String company, int Confirm) {
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		W4C = w4c;
		Company = company;
		Confirm = Confirm;
	}

	public BussinessUser(String iD, String firstName, String lastName, String phoneNumber, String email, String company,
			W4CBussiness w4c) {
		ID = iD;
		FirstName = firstName;
		LastName = lastName;
		PhoneNumber = phoneNumber;
		Email = email;
		Company = company;
		this.w4c = w4c;
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

	public float getW4C() {
		return W4C;
	}

	public void setW4C(float w4c) {
		W4C = w4c;
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

	@Override
	public String toString() {
		return "BussinessUser [ID=" + ID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", PhoneNumber="
				+ PhoneNumber + ", Email=" + Email + ", W4C=" + W4C + ", Company=" + Company + ", Confirm=" + Confirm
				+ "]";
	}
}
