package gui;

import java.awt.Button;
import java.net.URL;
import java.util.Locale.Category;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Addition;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditUpdateMenu implements Initializable {
	private static ObservableList<Item> temptable = FXCollections.observableArrayList();
	ObservableList<Item> listM;
	ObservableList<Item> dataList;
	ObservableList List = FXCollections.observableArrayList();
	ObservableList List1 = FXCollections.observableArrayList();
	private URL location;
	private ResourceBundle resources;
	@FXML
	private Text pathtext;

	@FXML
	private ListView<Item> itemslist;
	@FXML
	private TableView<Item> SaladTable;

	@FXML
	private TableView<Item> MaindishTable;

	@FXML
	private TableColumn<Item, Integer> MainDishID;

	@FXML
	private TableColumn<Item, String> MainDishName;

	@FXML
	private TableColumn<Item, Double> MainDishPrice;

	@FXML
	private TableColumn<Item, Integer> MainDishquantity;

	@FXML
	private TableColumn<Item, Integer> saladid;

	@FXML
	private TableColumn<Item, String> saladname;
	@FXML
	private Text ErrorText;
	@FXML
	private TableColumn<Item, Double> saladprice;

	@FXML
	private TableColumn<Item, Integer> saladquantity;

	@FXML
	private TableColumn<Item, String> categorycl;
	@FXML
	private ListView<Addition> listofaddition;

	@FXML
	private CheckBox checkaddition;
	private boolean flagaddition = false;
	private boolean flagnewitem = false;
	@FXML
	private ComboBox<String> searchcombox;

	@FXML
	private TextField additiontxt;

	@FXML
	private CheckBox addnewmaindishchekbox;

	@FXML
	private TextField pricetextmaindish;

	@FXML
	private TextField nameitemmaindishtxt;

	@FXML
	private TextField itemidtext;

	@FXML
	private TextField quantitymaintext;
	private Item item1;
	@FXML
	private TextField quantity;
	@FXML
	private ComboBox<String> catogerycmbox;
	@FXML
	private TextField Additiontextfield;

	@FXML
	private TextField price;
	@FXML
	private TextField idi;
	private String catogery;
	@FXML
	private TextField name;

	@FXML
	void removebt(ActionEvent event) {
		Item items = new Item(Integer.parseInt(idi.getText()), name.getText(), Double.parseDouble(price.getText()),
				Integer.parseInt(quantity.getText()));
		MessagesClass msg1 = new MessagesClass(Messages.RemoveItem, items,
				RestaurantManagerHomePage.restaurantmanager.getIDRestaurant(), catogerycmbox.getValue());
		ClientUI.chat.accept(msg1);
		initialize(location, resources);
		ErrorText.setText("The Item Removed.");
	}

	@FXML
	void addbut(ActionEvent event) {
		if (checkaddition.isSelected() && addnewmaindishchekbox.isSelected()) {
			Addition add = new Addition(additiontxt.getText());
			Item item = new Item(Integer.parseInt(itemidtext.getText()), nameitemmaindishtxt.getText(),
					Double.parseDouble(pricetextmaindish.getText()), Integer.parseInt(quantitymaintext.getText()));
			MessagesClass msg1 = new MessagesClass(Messages.AddItems, item, add, catogerycmbox.getValue());
			ClientUI.chat.accept(msg1);
			initialize(location, resources);
			ErrorText.setText("The Item Added to Menu");

		} else {
			if (checkaddition.isSelected()) {
//				 Item item=new Item(Integer.parseInt(itemidtext.getText()),nameitemmaindishtxt.getText(),
//		        		  Double.parseDouble(pricetextmaindish.getText()),Integer.parseInt(quantitymaintext.getText()));
				Addition add = new Addition(additiontxt.getText());
				MessagesClass msg1 = new MessagesClass(Messages.AddItems, item1, add,
						RestaurantManagerHomePage.restaurantmanager.getIDRestaurant(), catogerycmbox.getValue());
				ClientUI.chat.accept(msg1);
				initialize(location, resources);
				ErrorText.setText("The Addition Added to menu.");

			} else {
				Item item = new Item(Integer.parseInt(itemidtext.getText()), nameitemmaindishtxt.getText(),
						Double.parseDouble(pricetextmaindish.getText()), Integer.parseInt(quantitymaintext.getText()));
				MessagesClass msg1 = new MessagesClass(Messages.AddItems, item, null,
						RestaurantManagerHomePage.restaurantmanager.getIDRestaurant(), catogerycmbox.getValue());
				ClientUI.chat.accept(msg1);
				initialize(location, resources);
				ErrorText.setText("The Item Added to menu.");

			}
		}

	}

	@FXML
	void addnewmaindishchekbox(ActionEvent event) {
		flagnewitem = !flagnewitem;
		pricetextmaindish.setVisible(flagnewitem);
		nameitemmaindishtxt.setVisible(flagnewitem);
		itemidtext.setVisible(flagnewitem);
		quantitymaintext.setVisible(flagnewitem);
		catogerycmbox.setVisible(flagnewitem);
	}

	@FXML
	void checkaddition(ActionEvent event) {
		flagaddition = !flagaddition;
		additiontxt.setVisible(flagaddition);
	}

	@FXML
	void removeAdditionbt(ActionEvent event) {
		Item items = new Item(Integer.parseInt(idi.getText()), name.getText(), Double.parseDouble(price.getText()),
				Integer.parseInt(quantity.getText()));

		MessagesClass msg1 = new MessagesClass(Messages.RemoveItemAddition, items, Additiontextfield.getText());
		ClientUI.chat.accept(msg1);
		ErrorText.setText("The Item Removed.");
		initialize(location, resources);
	}

	@FXML
	void editbut(ActionEvent event) {
		Item items = new Item(Integer.parseInt(idi.getText()), name.getText(), Double.parseDouble(price.getText()),
				Integer.parseInt(quantity.getText()));
		MessagesClass msg1 = new MessagesClass(Messages.UpdateItem, items, catogery);
		ClientUI.chat.accept(msg1);
		initialize(location, resources);

	}

	@FXML
	void logout(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		RestaurantManagerHomePage aFrame = new RestaurantManagerHomePage();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/EditUpdateMenu.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Edit Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		pathtext.setText("RestaurantManager/Edit Menu");
		additiontxt.setVisible(false);
		quantitymaintext.setVisible(false);
		pricetextmaindish.setVisible(false);
		nameitemmaindishtxt.setVisible(false);
		catogerycmbox.setVisible(false);
		itemidtext.setVisible(false);
		searchcombox.getItems().removeAll(searchcombox.getItems());
		searchcombox.getItems().addAll("MainDish", "Salad", "Drink", "Dessert");
		catogerycmbox.getItems().removeAll(catogerycmbox.getItems());

		catogerycmbox.getItems().addAll("MainDish", "Salad", "Drink", "Dessert");
		if (RestaurantManagerHomePage.restaurantmanager != null) {
			String type = "MainDish";
			MessagesClass msg1 = new MessagesClass(Messages.GetAllitemsfromitem,
					RestaurantManagerHomePage.restaurantmanager.getIDRestaurant(), type);// get all items from tables
			ClientUI.chat.accept(msg1);
			listM = FXCollections.observableArrayList(ChatClient.Items);
			dataList = FXCollections.observableArrayList(ChatClient.Items);
			MainDishID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("item_ID"));
			MainDishName.setCellValueFactory(new PropertyValueFactory<Item, String>("item_Name"));
			MainDishPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
			MainDishquantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
			categorycl.setCellValueFactory(new PropertyValueFactory<Item, String>("cate"));
			MaindishTable.setItems(listM);
			load();
			settext();
			FilteredList<Item> filteredData = new FilteredList<>(listM, b -> true);
			searchcombox.valueProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(Item -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String lowerCaseFilter = newValue.toLowerCase();

					if (String.valueOf(Item.getCat()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(Item.getItem_Name()).indexOf(lowerCaseFilter) != -1)
						return true;
					else
						return false;
				});
			});

			SortedList<Item> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(MaindishTable.comparatorProperty());
			MaindishTable.setItems(sortedData);
		}
	}

	private void load() {
		itemslist.getItems().clear();
		MessagesClass msg1 = new MessagesClass(Messages.GetAllitems,
				RestaurantManagerHomePage.restaurantmanager.getIDRestaurant());// get all items from tables
		ClientUI.chat.accept(msg1);

		List.removeAll(List);
		for (int i = 0; i < ChatClient.allItems.size(); i++) {
			List.addAll("Name: " + ChatClient.allItems.get(i).getItem_Name() + "  " + "Quantity: "
					+ ChatClient.allItems.get(i).getQuantity());
		}
		itemslist.getItems().addAll(List);
	}

	private void setextfromlist() {
		MaindishTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Addition addition = listofaddition.getItems()
						.get(listofaddition.getSelectionModel().getSelectedIndex());
				System.out.println(addition + "the addition from list");
				Additiontextfield.setText(addition.getName());

			}
		});
	}

	private void settext() {
		MaindishTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Item w = MaindishTable.getItems().get(MaindishTable.getSelectionModel().getSelectedIndex());
				idi.setText("" + w.getItem_ID());
				quantity.setText("" + w.getQuantity());
				price.setText("" + w.getPrice());
				name.setText(w.getItem_Name());
				catogery = w.getCate();
				item1 = new Item(w.getItem_ID(), w.getItem_Name(), w.getPrice(), w.getQuantity());
				loadlist(w.getItem_ID());

			}
		});

	}

	public void loadlist(int id) {
		listofaddition.getItems().clear();

		List1.removeAll(List1);
		for (int i = 0; i < ChatClient.Items.size(); i++) {
			if (id == ChatClient.Items.get(i).getItem_ID()) {
				for (int j = 0; j < ChatClient.Items.get(i).getAdditions().size(); j++) {
					List1.addAll(ChatClient.Items.get(i).getAdditions().get(j).getName());
				}
			}
		}
		listofaddition.getItems().addAll(List1);

	}
}
