package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TypeOfOrderController {
	@FXML
	private Button DeliveryBtn;

	@FXML
	private Button PickUpBtn;

	@FXML
	private Button backBtn;

	@FXML
	void BackToRestPage(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		ShowOrderController SOC=new ShowOrderController();
		try {
			SOC.start(primaryStage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void DeliveryFunction(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		DeliveryInfoController DIC=new DeliveryInfoController();
		DIC.start(primaryStage);
	}

	@FXML
	void PickUpFunction(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		PickUpFormController PUFC=new PickUpFormController();
		PUFC.start(primaryStage);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/TypeOfOrderFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Type Of Order");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
