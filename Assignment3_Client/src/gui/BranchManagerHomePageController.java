package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.BranchManager;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BranchManagerHomePageController implements Initializable{
	
	public static BranchManager branchManager;

    static BranchManager getBranchManager() {
		return branchManager;
	}
	public static void setBranchManager(BranchManager branchManager) {
		BranchManagerHomePageController.branchManager = branchManager;
	}
	@FXML
    private Text managername;
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
 		Parent root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    //ChangeUserPermissions
//    @FXML
//    void acceptnewaccount(ActionEvent event) {
//    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		stage.close();
//		AcceptNewNormalAccountM aFrame = new AcceptNewNormalAccountM();
//		Stage primaryStage = new Stage();
//		try {
//			aFrame.start(primaryStage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    @FXML
    void report(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ReportListForManagerController aFrame = new ReportListForManagerController(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void addnewbussiness(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		CreateNewBussinessAccount aFrame = new CreateNewBussinessAccount();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
    	//FXMLLoader loader = new FXMLLoader();
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    
		stage.close();	
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user));
		LogInForm aFrame = new LogInForm(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start1(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void AcceptRestaurant(ActionEvent event) {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		AcceptRestaurat aFrame = new AcceptRestaurat(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void ChangeUserPermissions(ActionEvent event) {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		ChangeUserPermissionsPage aFrame = new ChangeUserPermissionsPage(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void CreateNewNormalAccount(ActionEvent event) {
    	ClientUI.chat.accept(new MessagesClass(Messages.GotW4C,null));
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		CreateNewNormalAccountController aFrame = new CreateNewNormalAccountController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MessagesClass msg = new MessagesClass(Messages.GetBranchManager, ChatClient.user);
		ClientUI.chat.accept(msg);
		branchManager = ChatClient.branchManager;
		managername.setText(ChatClient.branchManager.getFistName());

	}

}
 
 