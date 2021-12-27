package gui;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AcceptNewNormalAccountM implements Initializable {

	String tablesql = "normaluser";

	@FXML
	private Text HRusername;

	@FXML
	private TableView<Normal> table;

	@FXML
	private TableColumn<Normal, String> IDcl;

	@FXML
	private TableColumn<Normal, String> firstnamecl;

	@FXML
	private TableColumn<Normal, String> lastnamecl;

	@FXML
	private TableColumn<Normal, String> emailcl;

	@FXML
	private TableColumn<Normal, String> phonecl;

	@FXML
	private TableColumn<Normal, Integer> usernamecl;

	@FXML
	private TableColumn<Normal, Float> passwordcl;

	@FXML
	private TextField getID;

	@FXML
	private Text statustext;

	private static ObservableList<User> temptable = FXCollections.observableArrayList();
	ObservableList<Normal> listM;
	ObservableList<User> dataList;
	private URL location;
	private ResourceBundle resources;

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/AcceptNewNormalAcccount.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("accept accounts");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void acceptbt(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			statustext.setText("Please,Put ID.");
			return;
		}
		User user1 = new User(getID.getText(), null, null, null, 0, 0, null);
		MessagesClass msg1 = new MessagesClass(Messages.acceptnewnormaluser, user1);
		ClientUI.chat.accept(msg1);
		statustext.setText("Updated");
		table.getItems().clear();
		initialize(location, resources);
	}


	@FXML
	void deletbt(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			statustext.setText("Please,Put ID.");
		} else {
			MessagesClass msg1 = new MessagesClass(Messages.deleteid, getID.getText(), tablesql);
			ClientUI.chat.accept(msg1);
			statustext.setText("Deleted");
			table.getItems().clear();
			initialize(location, resources);
		}
	}

	@FXML
	void logOut(ActionEvent event) {
		if(CeoHomePageController.GetCEO==null) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		BranchManagerHomePageController aFrame = new BranchManagerHomePageController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			CeoHomePageController aFrame = new CeoHomePageController();
			Stage primaryStage = new Stage();
			try {
				aFrame.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		MessagesClass msg1 = new MessagesClass(Messages.getthenormalendifainedacoount, null);
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.ArrListToAcceptNewAccounts);
		IDcl.setCellValueFactory(new PropertyValueFactory<Normal, String>("ID"));
		firstnamecl.setCellValueFactory(new PropertyValueFactory<Normal, String>("FirstName"));
		lastnamecl.setCellValueFactory(new PropertyValueFactory<Normal, String>("LastName"));
		emailcl.setCellValueFactory(new PropertyValueFactory<Normal, String>("Email"));
		phonecl.setCellValueFactory(new PropertyValueFactory<Normal, String>("PhoneNumber"));
		usernamecl.setCellValueFactory(new PropertyValueFactory<Normal, Integer>("VisaIsAvailable"));
		passwordcl.setCellValueFactory(new PropertyValueFactory<Normal, Float>("W4C"));
		table.setItems(listM);
		settext();
	}

	public void Updatetable(String iD, String userName, String password, String userType, int isLoggedIn, int confirm,
			String status) {
		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				User temp = new User(iD, userName, password, userType, isLoggedIn, confirm, status);

			}
		});
	}

	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Normal w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText(w.getID());
			}

		});

	}
}