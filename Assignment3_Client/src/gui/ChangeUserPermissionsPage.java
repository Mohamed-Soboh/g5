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

public class ChangeUserPermissionsPage implements Initializable{
	String sqltable;
    private static ObservableList<User> temptable=FXCollections.observableArrayList();
	ObservableList<User> listM;
	ObservableList<User> dataList;
	private URL location;
	private ResourceBundle resources;
    @FXML
    private Text HRusername;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> IDcl;

    @FXML
    private TableColumn<User, String> firstnamecl;

    @FXML
    private TableColumn<User, String> lastnamecl;

    @FXML
    private TableColumn<User, String> emailcl;

    @FXML
    private TableColumn<User, String> phonecl;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TextField getID;

    @FXML
    private Text statustext;

    @FXML
    private ComboBox<String> statustype;
    public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/ChangeUserPermissionsPage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Create new account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    @FXML
    void changebt(ActionEvent event) {
          String ID,Status;
          ID=getID.getText();
            Status=statustype.getValue();
            if(!ID.isEmpty())
            {
            	User user=new User(ID,null,null,null,0,0,Status);
              MessagesClass msg1 = new MessagesClass(Messages.updateStatusofusers,user);//insert messageclass to help me send information to server
              System.out.print("update status");
              ClientUI.chat.accept(msg1);
              statustext.setText("Updated To "+Status);
              initialize(location,resources);
            }
            else
                statustext.setText("Please put ID .");

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

    @FXML
    void updateBt(ActionEvent event) {
/////////////////
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//HRusername.setText(username);
 		this.location = location;
		this.resources = resources;
		statustype.getItems().removeAll(statustype.getItems());
		statustype.getItems().addAll("Active", "Locked", "Frozen");
		statustype.getSelectionModel().select("Active");
		MessagesClass msg1 = new MessagesClass(Messages.getallusers,sqltable);//insert messageclass to help me send information to server
		System.out.println(msg1);
		 ClientUI.chat.accept(msg1);
		listM=FXCollections.observableArrayList(ChatClient.getlistofnormalaccount);
		IDcl.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
		firstnamecl.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
		lastnamecl.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		emailcl.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
 		table.setItems(listM);	
 		settext();
	}
	public void Updatetabl(String iD, String userName, String password, String userType, int isLoggedIn, int confirm,
			String status) {
	//
//			
			javafx.application.Platform.runLater(new Runnable() {
				@Override
				public void run() {
					//clientArrayList.add(new clientDetails(Host.toString(), IP, Status));
					User temp =new User( iD,  userName,  password,  userType,  isLoggedIn,  confirm,status) ;
//	public User(String firstName, String lastName, String iD, String email, String phoneN, String status) {

					System.out.println("updated: "+temp);
//					//clients.addAll(clientArrayList);new 
//					temptable.add(temp);
//					//tableServer.setItems(clients);
//					///tableServer.refresh();
//					
					}
			});	
		}
	private void settext()
	{
		table.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				User w=table.getItems().get(table.getSelectionModel().getSelectedIndex());	
				getID.setText(w.getID());
			}
			
		});
		
		
	}
}
