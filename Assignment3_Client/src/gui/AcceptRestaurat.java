package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.RestaurantManager;
import common.Resturaunt;
import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author moham
 *
 */
public class AcceptRestaurat implements Initializable {

	/**
	 * listm for table view 
	 */
	ObservableList<Resturaunt> listM;
	ObservableList<Resturaunt> dataList;
	private URL location;
	String sqltable = "restaurant";
	private ResourceBundle resources;
	@FXML
	private Text HRusername;
	/**
	 * text field the name of restaurant
	 */
	@FXML
	private TextField getRustaurantname;

	/**
	 * combobox to know or to choose the location of restaurant
	 */
	@FXML
	private ComboBox<String> Locationco;

	/**
	 * table view of all restaurant in manager location
	 */
	@FXML
	private TableView<Resturaunt> table;

	/**
	 * id of restaurant
	 */
	@FXML
	private TableColumn<Resturaunt, Integer> IDcl;

	/**
	 * restaurant name
	 */
	@FXML
	private TableColumn<Resturaunt, String> resnamecl;

	/**
	 * restaurant location
	 */
	@FXML
	private TableColumn<Resturaunt, String> locationcl;

	/**
	 * new id to add new restaurant
	 */
	@FXML
	private TextField getID;
	/**
	 * path text
	 */
	@FXML
	private Text pathtext;
	/**
	 * same to error message tell the manager if there is the same id in table 
	 */
	@FXML
	private Text statustext;

	/**
	 * @param primaryStage 
	 * @throws Exception
	 * start method to open the accept restaurant gui and show us all the buttons and table
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/AcceptRestaurant.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("accept resturaunt Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param event button action reset
	 * reset all text fields
	 */
	@FXML
	void ResetBT(ActionEvent event) {
		getRustaurantname.setText("");
		getID.setText("");
		
	}


	/**
	 * @param event accept action button
	 * this method take all the information of new restaurant and send it to server 
	 * check the id if exists in table or no 
	 * if exists send error message and put it is status text if not add new restaurant and all the restaurant manager that 
	 * come from external table can inside to there page and create menu and addition
	 */
	@FXML
	void AcceptBT(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			statustext.setText("Please,Put ID.");

		} else {

			Resturaunt resturaunt = new Resturaunt(Integer.parseInt(getID.getText()), getRustaurantname.getText(),
					Locationco.getValue());
			MessagesClass msg1 = new MessagesClass(Messages.Createaccepttresturaunt, resturaunt);
			ClientUI.chat.accept(msg1);
			if (ChatClient.ErrorMessage.equals("updated")) {
				statustext.setText("Updated");
				table.getItems().clear();
				initialize(location, resources);
			} else {
				statustext.setText(ChatClient.ErrorMessage);
				return;

			}
		}
	}

	/**
	 * @param event back button action 
	 * return back to the branch manager home page 
	 */
	@FXML
	void logOut(ActionEvent event) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			BranchManagerHomePageController aFrame = new BranchManagerHomePageController();
			Stage primaryStage = new Stage();
			try {
				aFrame.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("ManagerPage->acceptRestaurant");
		Locationco.getItems().removeAll(Locationco.getItems());
		Locationco.getItems().addAll("North", "Center", "South");/**put in combox*/
		Locationco.getSelectionModel().select("North");
		this.location = location;
		this.resources = resources;/** send to server to get all the list*/
		MessagesClass msg1 = new MessagesClass(Messages.getallrestaurant,
				BranchManagerHomePageController.branchManager.getLocation());
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.getallresturant);/**get all data of (branchManager.getLocation()) restaurant*/
		IDcl.setCellValueFactory(new PropertyValueFactory<Resturaunt, Integer>("resturauntID"));
		resnamecl.setCellValueFactory(new PropertyValueFactory<Resturaunt, String>("resturaunt_Name"));
		locationcl.setCellValueFactory(new PropertyValueFactory<Resturaunt, String>("location"));
		table.setItems(listM);
		settext();

	}

	
	/**
	 * mouse action if i click in table i can hold all the information of restaunat
	 */
	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Resturaunt w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText("" + w.getResturauntID() + "");
				getRustaurantname.setText( w.getResturaunt_Name());
			}

		});

	}
}
