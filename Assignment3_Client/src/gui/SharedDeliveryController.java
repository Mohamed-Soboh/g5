package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.clientDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SharedDeliveryController implements Initializable {
	private static URL location;
	private static ResourceBundle resources;
	@FXML
	private Text group_code;

	@FXML
	private Button finishBtn;

	@FXML
	private TableView<clientDetails> tableGroup;

	@FXML
	private TableColumn<clientDetails, String> colName;

	@FXML
	private TableColumn<clientDetails, String> colStatus;
	public static ObservableList<clientDetails> clients;
	public static boolean c = false;

	@FXML
	void EndSharedDelivery(ActionEvent event) {
		if(clients.size()==DeliveryInfoController.gp.getGroupSize())
		{
			MessagesClass msg=new MessagesClass(Messages.closeGroupDelivery,DeliveryInfoController.randGroup);
			ClientUI.chat.accept(msg);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			PaymentSuccessfulController PSC = new PaymentSuccessfulController();
			try {
				PSC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		clients=FXCollections.observableArrayList();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SharedDeliveryFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Shared Delivery");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		group_code.setText(String.valueOf(DeliveryInfoController.randGroup));
		colName.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("hostName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("Status"));
		tableGroup.setItems(clients);
	}
}
