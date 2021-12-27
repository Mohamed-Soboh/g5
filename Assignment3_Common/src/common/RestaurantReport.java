package common;

import java.io.Serializable;

public class RestaurantReport  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ItemID;
	private String ItemName;
	private int Quantity;
	private float Price1;
	private int sold;
	private float InCome;
	public RestaurantReport(int itemID, String itemName, int quantity, float price1, int sold, float inCome) {
		ItemID = itemID;
		ItemName = itemName;
		Quantity = quantity;
		Price1 = price1;
		this.sold = sold;
		InCome = inCome;
	}
	public int getItemID() {
		return ItemID;
	}
	public void setItemID(int itemID) {
		ItemID = itemID;
	}
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public float getPrice1() {
		return Price1;
	}
	public void setPrice1(float price1) {
		Price1 = price1;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public float getInCome() {
		return InCome;
	}
	public void setInCome(float inCome) {
		InCome = inCome;
	}
	@Override
	public String toString() {
		return "RestaurantReport [ItemID=" + ItemID + ", ItemName=" + ItemName + ", Quantity=" + Quantity + ", Price1="
				+ Price1 + ", sold=" + sold + ", InCome=" + InCome + "]";
	}
	
}
