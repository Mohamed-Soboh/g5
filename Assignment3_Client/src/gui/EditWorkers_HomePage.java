package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.User;
import common.WorkerUser;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditWorkers_HomePage implements Initializable{
	String sqltable;
    private static ObservableList<WorkerUser> temptable=FXCollections.observableArrayList();
	ObservableList<WorkerUser> listM;
	ObservableList<WorkerUser> dataList;
    @FXML
    private TableView<WorkerUser> workertable;
    @FXML
    private TableColumn<WorkerUser, String> id;
    @FXML
    private Text pathtext;

    @FXML
    private TableColumn<WorkerUser, String> firstname;

    @FXML
    private TableColumn<WorkerUser, String> lastname;

    @FXML
    private TableColumn<WorkerUser,String> email;

    @FXML
    private TableColumn<WorkerUser,String> phone;

    @FXML
    private TextField Restaurant;
    @FXML
    private TableColumn<WorkerUser, String> Restaurantcl;
    @FXML
    private TableColumn<WorkerUser, String> usernamcl;

    private URL location;
	private ResourceBundle resources;
    @FXML
    private TableColumn<WorkerUser, String> password;

    @FXML
    private TextField enterid;

    @FXML
    private TextField enteremail;

    @FXML
    private TextField enterphone;

    @FXML
    private TextField enterfirstname;

    @FXML
    private TextField enterusername;

    @FXML
    private TextField enterlastname;

    @FXML
    private TextField enterpassword;

    @FXML
    private Button edit;

    @FXML
    private Button AddNewWorkerBt;

    @FXML
    private Button remove;
    public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/EditWorkers_HomePage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Edit Worker");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    @FXML
    void AddNewWorkerBt(ActionEvent event) {
    	WorkerUser WorkerUser=new WorkerUser(enterid.getText(),enterfirstname.getText(),enterlastname.getText(),enterusername.getText(),enterpassword.getText(),enteremail.getText(),enterphone.getText(),RestaurantManagerHomePage.restaurantmanager.getIDRestaurant());
		MessagesClass msg1 = new MessagesClass(Messages.AddNewWorker,WorkerUser);
		 ClientUI.chat.accept(msg1);
		 System.out.println(RestaurantManagerHomePage.restaurantmanager.getIDRestaurant()+"the id of res");

		 initialize(location,resources);

    }

    @FXML
    void editbt(ActionEvent event) {
    	WorkerUser WorkerUser=new WorkerUser(enterid.getText(),enterfirstname.getText(),enterlastname.getText(),enterusername.getText(),enterpassword.getText(),enteremail.getText(),enterphone.getText(),RestaurantManagerHomePage.restaurantmanager.getIDRestaurant());
		MessagesClass msg1 = new MessagesClass(Messages.editworkers,WorkerUser);
		 ClientUI.chat.accept(msg1);
		 System.out.println(RestaurantManagerHomePage.restaurantmanager.getIDRestaurant()+"the id of res");
		 initialize(location,resources);

    }

    @FXML
    void removebt(ActionEvent event) {
    	WorkerUser WorkerUser=new WorkerUser(enterid.getText(),enterfirstname.getText(),enterlastname.getText(),enterusername.getText(),enterpassword.getText(),enteremail.getText(),enterphone.getText(),RestaurantManagerHomePage.restaurantmanager.getIDRestaurant());
		MessagesClass msg1 = new MessagesClass(Messages.deleteworker,WorkerUser);
		 ClientUI.chat.accept(msg1);
		 System.out.println(RestaurantManagerHomePage.restaurantmanager.getIDRestaurant()+"the id of res");

		 initialize(location,resources);

    }
 
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("RestaurantManager/EditWorker");
		Restaurant.setText(RestaurantManagerHomePage.Resturauntm.getResturaunt_Name());
		MessagesClass msg1 = new MessagesClass(Messages.GetAllWorker,RestaurantManagerHomePage.restaurantmanager.getIDRestaurant());//insert messageclass to help me send information to server
		 ClientUI.chat.accept(msg1);
		listM=FXCollections.observableArrayList(ChatClient.allworker);
		id.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("UserID"));
		firstname.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("FirstName"));
		lastname.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("LastName"));
		email.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("Email"));
		phone.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("PhoneNumber"));
		Restaurantcl.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("RestaurantName"));
		usernamcl.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("UserName"));
		password.setCellValueFactory(new PropertyValueFactory<WorkerUser, String>("Password"));
		workertable.setItems(listM);	
 		settext();		
	}
	private void settext()
	{
		workertable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				WorkerUser w=workertable.getItems().get(workertable.getSelectionModel().getSelectedIndex());	
				enterid.setText(w.getUserID());
				enteremail.setText(w.getEmail());
				enterfirstname.setText(w.getFirstName());
				enterusername.setText(w.getUserName());
				enterlastname.setText(w.getLastName());
				enterpassword.setText(w.getPassword());
				Restaurant.setText(w.getRestaurantName());
				enterphone.setText(w.getPhoneNumber());
			}
		});
		
		
	}

    @FXML
    void logOut(ActionEvent event) {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		RestaurantManagerHomePage aFrame = new RestaurantManagerHomePage();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	public void UpdatetablWorkerUser(String userID, String firstName, String lastName, String userName, String password, String email,
					String phoneNumber, int iDRestaurant)
	{ 
	
		
			javafx.application.Platform.runLater(new Runnable() {
				@Override
				public void run() {
					WorkerUser temp =new WorkerUser( userID,  firstName,  lastName,  userName,  password,  email,phoneNumber,iDRestaurant) ;
					System.out.println("updated: "+temp);

			
					}
			});	
		}
}

