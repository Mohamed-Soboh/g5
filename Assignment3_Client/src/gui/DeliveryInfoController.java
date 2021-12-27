package gui;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeliveryInfoController implements Initializable {
	@FXML
	private DatePicker datetxt;

	@FXML
	private TextField nametxt;

	@FXML
	private TextField adresstxt;

	@FXML
	private TextField phonenumbertxt;
	
	@FXML
	private ComboBox<String> deliverytxt;

	@FXML
	private TextArea opentxt;

	@FXML
	private ComboBox<String> hourtxt;

	@FXML
	private ComboBox<String> minutetxt;
	
	public static boolean Delivery;

	@FXML
	void BackBtn(ActionEvent event) {
		
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		TypeOfOrderController TOOC = new TypeOfOrderController();
		try {
			TOOC.start(primaryStage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	void goToPayment(ActionEvent event) throws Exception {
		String datevalue= datetxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if(nametxt.getText().equals("") || adresstxt.getText().equals("") || phonenumbertxt.getText().equals("") || datevalue.equals(""))
		{
			System.out.println("enter details");
			return;
		}
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		if(ChatClient.user.getUserType().equals("Normal"))
		{
			Delivery=true;
			PaymentNormalUserController PNUC=new PaymentNormalUserController();
			PNUC.start(primaryStage);
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Delivery=false;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/DeliveryInfoFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Delivery Info");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		phonenumbertxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}?")) {
					phonenumbertxt.setText(oldValue);
				}
			}
		});
		
		nametxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\sa-zA-Z*")) {
					nametxt.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			}
		});
		
		int i;
		for (i = 0; i < 24; i++) {
			if (i < 10)
				hourtxt.getItems().add("0" + String.valueOf(i));
			else
				hourtxt.getItems().add(String.valueOf(i));
		}
		
		for (i = 0; i < 60; i++) {
			if (i < 10)
				minutetxt.getItems().add("0" + i);
			else
				minutetxt.getItems().add(String.valueOf(i));
		}
		
		deliverytxt.getItems().add("Basic 25$");
		if(ChatClient.user.getUserType().equals("Bussiness"))
			deliverytxt.getItems().add("Shared");
		deliverytxt.getItems().add("Robot");
		
	}

}
