package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
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

public class AcceptRestaurat implements Initializable {
	//String username = ChatClient.branchManager.getFistName();
	private static ObservableList<Resturaunt> temptable = FXCollections.observableArrayList();
	ObservableList<Resturaunt> listM;
	ObservableList<Resturaunt> dataList;
	private URL location;
	String sqltable = "restaurant";
	private ResourceBundle resources;
	@FXML
	private Text HRusername;
	@FXML
    private TextField getRustaurantname;

    @FXML
    private ComboBox<String> Locationco;

	@FXML
	private TableView<Resturaunt> table;

	@FXML
	private TableColumn<Resturaunt, Integer> IDcl;

	@FXML
	private TableColumn<Resturaunt, String> resnamecl;

	@FXML
	private TableColumn<Resturaunt, String> locationcl;

	@FXML
	private TextField getID;

	@FXML
	private Text statustext;

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource("/gui/AcceptRestaurant.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("accept resturaunt Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
 
  
    @FXML
    void ResetBT(ActionEvent event) {
    	getRustaurantname.setText("");
    	getID.setText("");
    }
	@FXML
	void AcceptBT(ActionEvent event) {
		if (getID.getText().isEmpty()) {
			statustext.setText("Please,Put ID.");

		} else {
			Resturaunt resturaunt = new Resturaunt(0, null, null,null);
			resturaunt = new Resturaunt(Integer.parseInt(getID.getText()), getRustaurantname.getText(), Locationco.getValue(),null);
			MessagesClass msg1 = new MessagesClass(Messages.Createaccepttresturaunt, resturaunt);// insert messageclass to help
																							// me send information to																			// server
			System.out.println(msg1);
			ClientUI.chat.accept(msg1);
			statustext.setText("Updated");
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
    			e.printStackTrace();
    		}}
    		else
    		{
    			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    			stage.close();		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Locationco.getItems().removeAll(Locationco.getItems());
		Locationco.getItems().addAll("North", "Center", "South");
		Locationco.getSelectionModel().select("North");
		//HRusername.setText(username);
		this.location = location;
		this.resources = resources;
 		//System.out.println(ChatClient.branchManager.getLocation());
		MessagesClass msg1 = new MessagesClass(Messages.getallrestaurant,null);
		ClientUI.chat.accept(msg1);
		listM = FXCollections.observableArrayList(ChatClient.getallresturant);
		
		IDcl.setCellValueFactory(new PropertyValueFactory<Resturaunt, Integer>("resturauntID"));
		resnamecl.setCellValueFactory(new PropertyValueFactory<Resturaunt, String>("resturaunt_Name"));
		locationcl.setCellValueFactory(new PropertyValueFactory<Resturaunt, String>("location"));
		table.setItems(listM);
		settext();

	}

	public void Updatetable(int iD, String resturaunt_Name, String location) {
		//
//			
		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// clientArrayList.add(new clientDetails(Host.toString(), IP, Status));
				Resturaunt temp = new Resturaunt(iD, resturaunt_Name, location);
//	public User(String firstName, String lastName, String iD, String email, String phoneN, String status) {

				System.out.println("updated: " + temp);
//					//clients.addAll(clientArrayList);new 
//					temptable.add(temp);
//					//tableServer.setItems(clients);
//					///tableServer.refresh();
//					
			}
		});
	}

	private void settext() {
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Resturaunt w = table.getItems().get(table.getSelectionModel().getSelectedIndex());
				getID.setText("" + w.getResturauntID() + "");
			}

		});

	}
}
