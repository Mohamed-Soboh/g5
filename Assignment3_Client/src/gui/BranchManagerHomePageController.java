package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.BranchManager;
import common.Messages;
import common.MessagesClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author moham
 *
 */
public class BranchManagerHomePageController implements Initializable{
	
	/**
	 * branchmanager to put all the information of the branch and save it
	 */
	public static BranchManager branchManager;
	/**
	 * use to clock text
	 */
	@FXML
	private Text clock = new Text();
	public static String sClock;
	  /**
	 * to display the location of manager
	 */
	@FXML
	    private Text locationofmanager;

	    /**
	     * pathtext the path 
	     */
	    @FXML
	    private Text pathtext;
    static BranchManager getBranchManager() {
		return branchManager;
	}
	public static void setBranchManager(BranchManager branchManager) {
		BranchManagerHomePageController.branchManager = branchManager;
	}
	@FXML
    private Text managername;
	
    /**
     * @param primaryStage to start the qui and display it
     * @throws Exception
     * this method to display the gui and start it
     */
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
 		Parent root = loader.load(this.getClass().getResource("/gui/BranchManager_Home_Page.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Manager Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    /**
     * @param event use to accept account button
     * to see the external users that registered and manager choose with type to be 
     */
    @FXML
    void acceptnewaccount(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		AcceptNewNormalAccountM aFrame = new AcceptNewNormalAccountM();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * @param event report action button
     * to go to report page 
     */
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
    

    /**
     * @param event action of button
     * inside to list to confirm the company
     */
    @FXML
    void confirmCompany(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ConfirmCompanyController aFrame = new ConfirmCompanyController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    /**
     * @param event logout button
     * @throws IOException
     * return back to login page 
     */
    @FXML
    void logout(ActionEvent event) throws IOException {

    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user,0));/**change the status of manager*/
		LogInForm aFrame = new LogInForm(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start1(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    /**
     * @param event use to accept account button
     *confirm restaurant and accept it 
     */
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

    /**
     * @param event action of change button
     * switch to ChangeUserPermissionsPage and change the status (active frozen ....)
     */
    @FXML
    void ChangeUserPermissions(ActionEvent event) {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		ChangeUserPermissionsPage aFrame = new ChangeUserPermissionsPage(); 
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * @param event use to accept account button
     * to see the external users that registered and manager choose with type to be 
     */
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
		pathtext.setText("LogIn->BranchManagerHomePage");
		MessagesClass msg = new MessagesClass(Messages.GetBranchManager, ChatClient.user);/**sed to server to get all the data*/
		ClientUI.chat.accept(msg);
		branchManager = ChatClient.branchManager;
		locationofmanager.setText(branchManager.getLocation());/**put the location */
		managername.setText(ChatClient.branchManager.getFistName());
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");/**clock*/
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				clock.setText(LocalDateTime.now().format(format));
				sClock = String.valueOf(LocalDateTime.now().format(format));
				// System.out.println(clock.getText());
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

}
 
 