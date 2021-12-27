package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.Resturaunt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ResturauntMenuController implements Initializable{
	  	
	    @FXML
	    private Button backToHomePagebtn;
	    Button[] buttons;
	    Button back;

		public void start(Stage primaryStage) throws IOException {
			MessagesClass message=new MessagesClass(Messages.GetAllRestaurants,null);//Get arraylist of all restaurants available
			ClientUI.chat.accept(message);
			buttons=new Button[ChatClient.restaurantList.size()];//create button array of all restaurants
			BuildButtonsForRestaurants(ChatClient.restaurantList,primaryStage);
		}
		
		//build gui according to restaurants number and set functionalities
		private void BuildButtonsForRestaurants(ArrayList<Resturaunt> restaurantList,Stage primaryStage) {
			AnchorPane pane1 = new AnchorPane();
			pane1.setStyle("-fx-background-color: #555555;");
			
			//set header and put BM image into title
			VBox vbox=new VBox(10);
			VBox head=new VBox(10);
			head.setMaxSize(100, 600);
			vbox.setMaxSize(700, 600);
			Image imProfile = new Image(getClass().getResourceAsStream("/icons/BITEME.png"));
			ImageView image=new ImageView(imProfile);
			image.setFitHeight(200);
			image.setFitWidth(600);
			image.setTranslateX(100);
			image.setTranslateY(-100);
			Label label = new Label("Restaurants Menu");
			label.setStyle("-fx-background-color: #444444;");
			back=new Button();
			
			back.setText("Back");
			back.setTranslateX(340);
			back.setTranslateY(520);
			back.setMinSize(120, 50);
			back.setOnAction(backToUserHomePage);
			head.getChildren().add(back);
			label.setTextFill(Color.web("#eb8b00"));
			label.setFont(new Font("Arial", 30));
			label.setTranslateX(40);
			label.setTranslateY(-150);
			head.getChildren().add(image);
			head.getChildren().add(label);
			
			//create buttons and set ID's for all restaurants
			for (int i = 0; i < restaurantList.size(); i++) {
				Resturaunt r=(Resturaunt)restaurantList.get(i);
				Button b=new Button();
				b.setText(r.getResturaunt_Name());
				b.setTranslateY(10);
				b.setTranslateX(175);
				b.setMinSize(200, 50);
				buttons[i]=b;
				buttons[i].setId("button-restaurant");
				buttons[i].setOnAction(GoToRestaurant);
			}
			
			vbox.getChildren().addAll(buttons);
			vbox.setPrefSize(200, 300);
			vbox.setStyle("-fx-background-color: #555555;");

			//add scroll pane to be able to scroll through restaurants
			ScrollPane pane = new ScrollPane(vbox);
			pane.setContent(vbox);
			vbox.setStyle("-fx-background-color: #555555;");	
			pane.setTranslateX(100);
			pane.setTranslateY(200);
			pane.setPrefSize(600, 300);
		    pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		    pane.setFitToWidth(true); // set content width to viewport width
		    pane.setPannable(true); // allow scrolling via mouse dragging
		    pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		    pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		    
		    
		    pane1.getChildren().addAll(head,pane);
			Scene scene = new Scene(pane1, 800, 600);	
			scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
			back.setId("button-back");
			primaryStage.setTitle("Resturaunt's Menu");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		//if press button back then go back to home page
		EventHandler<ActionEvent> backToUserHomePage = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
        		stage.close();
        		Stage primaryStage1 = new Stage();
				NormalUserHomePageController NUHP = new NormalUserHomePageController();
				try {
					//NUHP.start(primaryStage1);
					NormalUserHomePageController.homePageStage.show();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };
        
        //if chose restaurant go to restaurant menu
        EventHandler<ActionEvent> GoToRestaurant = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
        		stage.close();
        		Stage primaryStage = new Stage();
				Button b=(Button)e.getSource();
				String restaurantName=b.getText();
				Resturaunt r=null;
				for(Resturaunt res:ChatClient.restaurantList)
				{
					if(restaurantName.equals(res.getResturaunt_Name())) {
						r=res;
						break;
					}
				}
				RestaurantController RC=new RestaurantController(r);
				try {
					RC.start(primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };
        
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
				
		}
}
