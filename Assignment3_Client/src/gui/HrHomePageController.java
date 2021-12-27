package gui;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.District;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.BussinessUser;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HrHomePageController implements Initializable {

	String sqltable = "bussinessuser";
	@FXML
	private Text HRusername;

	@FXML
	private TableView<BussinessUser> table;

	@FXML
	private TableColumn<BussinessUser, String> IDcl;

	@FXML
	private TableColumn<BussinessUser, String> firstnamecl;

	@FXML
	private TableColumn<BussinessUser, String> lastnamecl;

	@FXML
	private TableColumn<BussinessUser, String> emailcl;

	@FXML
	private TableColumn<BussinessUser, String> phonecl;

	@FXML
	private TableColumn<BussinessUser, String> usernamecl;

	@FXML
	private TableColumn<BussinessUser, String> passwordcl;

	@FXML
	private Text statustext;
	
	private float w4c;

	@FXML
	private TableColumn<BussinessUser, String> districtcl;
	private static ObservableList<BussinessUser> temptable = FXCollections.observableArrayList();
	ObservableList<BussinessUser> listM;
	ObservableList<BussinessUser> dataList;
	private URL location;
	private ResourceBundle resources;
	@FXML
	private TextField getID;

	@FXML
	void acceptbt(ActionEvent event) {
		String ID, Status;
		ID = getID.getText();
		if (!ID.isEmpty()) {
			User user = new User(ID, null, null, null, 0, 0,null);
			System.out.println(user);

 			MessagesClass msg1 = new MessagesClass(Messages.updateandinsidebussinesstousers, user);
			ClientUI.chat.accept(msg1);
			statustext.setText("Updated To ");
			initialize(location, resources);
		} else
			statustext.setText("Please put ID .");
	}

	@FXML
	void deletbt(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			statustext.setText("Please,Put ID.");
		} else {
			MessagesClass msg1 = new MessagesClass(Messages.deleteid, getID.getText(), sqltable);
			ClientUI.chat.accept(msg1);
			statustext.setText("Deleted");
			table.getItems().clear();
			initialize(location, resources);
		}
	}

	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/HRHomePage.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void logOut(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		stage.close();
		// ClientUI.chat.accept(new MessagesClass(Messages.updateStatus,
		// ChatClient.user));
		firstHRpage aFrame = new firstHRpage(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(firstHRpage.HRManager.getCompnay()+"jdtfdcvassanisbubas");

		MessagesClass msg1 = new MessagesClass(Messages.GettempData, firstHRpage.HRManager.getCompnay());
		ClientUI.chat.accept(msg1);
		System.out.println(ChatClient.bussinessUser);
		listM = FXCollections.observableArrayList(ChatClient.bussinessUser);
		IDcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("ID"));
		firstnamecl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("FirstName"));
		lastnamecl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("LastName"));
		emailcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("PhoneNumber"));
		passwordcl.setCellValueFactory(new PropertyValueFactory<BussinessUser, String>("Company"));
		table.setItems(listM);
		settext();
	}

	public void Updatetable(String iD, String firstName, String lastName, String phoneNumber, String email, float w4c,
			String company) {

		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				BussinessUser temp = new BussinessUser(iD, firstName, lastName, phoneNumber, email, w4c, company, 0);
				System.out.println("updated: " + temp);

			}
		});
	}

	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				BussinessUser w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText(w.getID());
			}

		});

	}

}