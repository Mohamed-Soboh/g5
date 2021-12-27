package common;

import java.util.Arrays;

import javafx.scene.control.CheckBox;

public class ViewOrderDetails {
	private CheckBox[] CB;
	private Item item;

	public ViewOrderDetails(CheckBox[] cB, Item item) {
		CB = cB;
		this.item = item;
	}

	public CheckBox[] getCB() {
		return CB;
	}

	@Override
	public String toString() {
		return "ViewOrderDetails [CB=" + Arrays.toString(CB) + ", item=" + item + "]";
	}

	public void setCB(CheckBox[] cB) {
		CB = cB;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
