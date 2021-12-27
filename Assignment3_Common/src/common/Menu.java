package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> items;
	private int menu_ID;
	private static int menuIdCounter=1;
	private HashMap<Item,Integer> menu;
	
	public Menu(HashMap<Item,Integer> menu) {
		this.menu_ID = menuIdCounter++;
		this.menu = menu;
		setItems(menu);
	}

	public HashMap<Item, Integer> getMenu() {
		return menu;
	}

	public void setMenu(HashMap<Item, Integer> menu) {
		this.menu = menu;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(HashMap<Item,Integer> m) {
		ArrayList<Item> tmp=new ArrayList<Item>();
		for(Map.Entry<Item, Integer> e:m.entrySet())
		{
			tmp.add(e.getKey());
		}
		this.items = tmp;
	}

	public int getMenu_ID() {
		return menu_ID;
	}

	public void setMenu_ID(int menu_ID) {
		this.menu_ID = menu_ID;
	}

	@Override
	public String toString() {
		return "Menu [items=" + items.toString() + ", menu_ID="	+ menu_ID + "]";	
	}	
}
