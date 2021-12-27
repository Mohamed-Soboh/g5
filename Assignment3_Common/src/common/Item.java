package common;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int item_ID;
	private String item_Name;
	private Double price;
	ArrayList<Addition> additions;
	private Category category;
	private int quantity;
	private int orderNum;
	private int index;
	private String additions_names;
	
	public String getAdditions_names() {
		return additions_names;
	}

	public void setAdditions_names(String additions_names) {
		this.additions_names = additions_names;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Item(int item_ID, String item_Name, Double price,Category category,ArrayList<Addition> additions) {
		this.item_ID = item_ID;
		this.item_Name = item_Name;
		this.price = price;
		this.category=category;
		this.additions=additions;
	}
	
	public Item (int orderNum,int item_ID,ArrayList<Addition> additions,int index)
	{
		this.orderNum=orderNum;
		this.item_ID=item_ID;
		this.additions=additions;
		this.index=index;
	}
	
	public Item (int orderNum,int item_ID,String additions,int index)
	{
		this.orderNum=orderNum;
		this.item_ID=item_ID;
		this.additions_names=additions;
		this.index=index;
	}
	
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Item(int item_ID,int quantity)
	{
		this.item_ID=item_ID;
		this.quantity=quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public ArrayList<Addition> getAdditions() {
		return additions;
		
	}

	public void setAdditions(ArrayList<Addition> additions) {
		this.additions = additions;
	}

	public int getItem_ID() {
		return item_ID;
	}
	public void setItem_ID(int item_ID) {
		this.item_ID = item_ID;
	}
	public String getItem_Name() {
		return item_Name;
	}
	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCat() {
		return category;
	}

	public void setCat(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Item [item_ID=" + item_ID + ", item_Name=" + item_Name + ", price=" + price + ", category=" + category + "]";		
	}
}
