package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.Normal;
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
public class ChangeUserPermissionsPage implements Initializable {
	String sqltable;
	/**
	 * observablelist used in table view
	 */
	ObservableList<User> listM;
	ObservableList<User> dataList;
	private URL location;
	private ResourceBundle resources;
//    @FXML
//    private Text HRusername;
//    
	/**
	 * table view kind user hold all information about users that confirmed in bite
	 * me
	 */
	@FXML
	private TableView<User> table;

	/**
	 * id column
	 */
	@FXML
	private TableColumn<User, String> IDcl;

	/**
	 * first name of normal user
	 */
	@FXML
	private TableColumn<User, String> firstnamecl;
	/**
	 * path text
	 */
	@FXML
	private Text pathtext;

	/**
	 * normal user last name
	 */
	@FXML
	private TableColumn<User, String> lastnamecl;

	/**
	 * email of user
	 */
	@FXML
	private TableColumn<User, String> emailcl;

	/**
	 * phone number
	 */
	@FXML
	private TableColumn<User, String> phonecl;

	/**
	 * his status in bite me
	 */
	@FXML
	private TableColumn<User, String> status;

	/**
	 * get the id that we want to change
	 */
	@FXML
	private TextField getID;

	/**
	 * same to error message
	 */
	@FXML
	private Text statustext;

	/**
	 * combobox that include the status
	 */
	@FXML
	private ComboBox<String> statustype;

	/**
	 * @param primaryStage to start the gui of change permission
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/ChangeUserPermissionsPage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Create new account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param event action of change button when click in change send to server to
	 *              reach to normal user and change there status in bite me
	 */
	@FXML
	void changebt(ActionEvent event) {
		String ID, Status;
		ID = getID.getText();
		Status = statustype.getValue();
		if (!ID.isEmpty()) {
			User user = new User(ID, null, null, null, 0, 0, Status);
			MessagesClass msg1 = new MessagesClass(Messages.updateStatusofusers, user);// insert messageclass to help me
																						// send information to server
			System.out.print("update status");
			ClientUI.chat.accept(msg1);
			statustext.setText("Updated To " + Status);
			initialize(location, resources);
		} else
			statustext.setText("Please put ID .");/** error message to tell the manager to put id */

	}

	/**
	 * @param event action of back button return back to branchhomepage
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

	/**
	 * in this method we send to server to get all the information about the normal
	 * user that confirmed in bite me by array list that exists in chatclient and
	 * put it in table that branch can see and change it
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("ManagerPage->ChangeStatusPage");
		this.location = location;
		this.resources = resources;
		statustype.getItems().removeAll(statustype.getItems());
		statustype.getItems().addAll("Active", "Locked", "Frozen");/** combobox of the status */
		statustype.getSelectionModel().select("Active");
		MessagesClass msg1 = new MessagesClass(Messages.getallusers, sqltable);// insert messageclass to help me send
																				// information to server
		System.out.println(msg1);
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.getlistofnormalaccount);/** array list of normal users */
		IDcl.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
		firstnamecl.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
		lastnamecl.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		emailcl.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
		table.setItems(listM);
		settext();/** help the branch manager to click in table and put the id in text field */
	}

	/**
	 * mouse action method take all information when clicked in table
	 */
	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				User w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText(w.getID());
			}

		});

	}
}
