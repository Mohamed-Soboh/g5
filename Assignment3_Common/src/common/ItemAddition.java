package common;

import java.io.Serializable;

public class ItemAddition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String item;
	private String addition;

	public String getItem() {
		return item;
	}

	@Override
	public String toString() {
		return "ItemAddition [item=" + item + ", addition=" + addition + "]";
	}

	public ItemAddition(String item, String addition) {
		super();
		this.item = item;
		this.addition = addition;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

}
