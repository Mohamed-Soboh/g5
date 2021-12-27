package gui;

import java.net.URL;
import java.util.ResourceBundle;
import common.CEOuser;
import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CeoHomePageController implements Initializable{
	public static CEOuser GetCEO;
    @FXML
    private Tab ceohomepage;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button manager;

    @FXML
    private Button users;

    @FXML
    private Button bussiness;

    @FXML
    private Button reports;

    @FXML
    private Button res;

    @FXML
    private Tab managerpage;

    @FXML
    private Text managername;

    @FXML
    private Tab resturantpage;

    @FXML
    private Button CreateReport;

    @FXML
    private Text resId;
    @FXML
    private AnchorPane bitememanageranchore;
    @FXML
    private Button creatrmenu;

    @FXML
    private Button edittworker;

    @FXML
    private Text managername1;

    @FXML
    private Tab normaluserpage;

    @FXML
    private Text username;

    @FXML
    private Button neworder;

    @FXML
    private Button orderhistory;

    @FXML
    private Button creatEdit;

    @FXML
    private Button logout;
    @FXML
    private AnchorPane usersanchor;

    @FXML
    private AnchorPane manageranchor;

    @FXML
    void AcceptRestaurant(ActionEvent event) {
    	
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

		CreateNewNormalAccountController aFrame = new CreateNewNormalAccountController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   

    @FXML
    void CreateReport(ActionEvent event) {

    }

    @FXML
    void Editworkerbt(ActionEvent event) {

    }

    @FXML
    void addnewbussiness(ActionEvent event) {

    }

    @FXML
    void bussinessbt(ActionEvent event) {

    }

    @FXML
    void editbt(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void logoutbtn(ActionEvent event) {

    }

    @FXML
    void managerbt(ActionEvent event) {////////////////////////////////////////////////

    }

    @FXML
    void neworder(ActionEvent event) {

    }

    @FXML
    void orderhistory(ActionEvent event) {

    }

    @FXML
    void report(ActionEvent event) {

    }

    @FXML
    void reportsbt(ActionEvent event) {

    }

    @FXML
    void resbt(ActionEvent event) {

    }

    @FXML
    void updatecreatemenu(ActionEvent event) {

    }

    @FXML
    void usersbt(ActionEvent event) {///////////////////////////////////////////////////

    }




    public void start(Stage primaryStage) throws Exception {
	Parent root1 = FXMLLoader.load(getClass().getResource("/gui/CEO_Home_Page.fxml"));
	Scene scene = new Scene(root1);
	primaryStage.setTitle("CEO Home Page");
	primaryStage.setScene(scene);
	primaryStage.show();
}
  
    @FXML
    void logoutceo(ActionEvent event) {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
        
		stage.close();	
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.userceo,0));
		LogInForm aFrame = new LogInForm(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start1(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ChatClient.userceo=null;
    }



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MessagesClass msg = new MessagesClass(Messages.GetCEO, ChatClient.userceo);
		ClientUI.chat.accept(msg);
		GetCEO = ChatClient.GetCEO;
		//managername.setText(ChatClient.GetCEO.getFirstName());
		// TODO Auto-generated method stub
	      slider.setTranslateX(-220);
	        Menu.setOnMouseClicked(event -> {
	            TranslateTransition slide = new TranslateTransition();
	            slide.setDuration(Duration.seconds(0.4));
	            slide.setNode(slider);

	            slide.setToX(0);
	            slide.play();

	            slider.setTranslateX(-220);

	            slide.setOnFinished((ActionEvent e)-> {
	                Menu.setVisible(false);
	                MenuClose.setVisible(true);
	            });
	        });

	        MenuClose.setOnMouseClicked(event -> {
	            TranslateTransition slide = new TranslateTransition();
	            slide.setDuration(Duration.seconds(0.4));
	            slide.setNode(slider);

	            slide.setToX(-220);
	            slide.play();

	            slider.setTranslateX(0);

	            slide.setOnFinished((ActionEvent e)-> {
	                Menu.setVisible(true);
	                MenuClose.setVisible(false);
	            });
	        });		}

}
