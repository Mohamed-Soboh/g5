package common;

import java.io.Serializable;

public class Company implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String companyname;
private int confirm;
@Override
public String toString() {
	return "Company [companyname=" + companyname + ", confirm=" + confirm + "]";
}
public Company(String companyname, int confirm) {
	super();
	this.companyname = companyname;
	this.confirm = confirm;
}
public String getCompanyname() {
	return companyname;
}
public void setCompanyname(String companyname) {
	this.companyname = companyname;
}
public int getConfirm() {
	return confirm;
}
public void setConfirm(int confirm) {
	this.confirm = confirm;
}
}
