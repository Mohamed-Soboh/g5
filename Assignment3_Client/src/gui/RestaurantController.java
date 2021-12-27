package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.javafx.collections.MappingChange.Map;

import common.Addition;
import common.Category;
import common.Item;
import common.Resturaunt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantController implements Initializable {
	
	public static Resturaunt restaurant;	
	public static HashMap<Item,Object> spinners;
	ArrayList<Item> item;
	HashMap<Item,Integer> menu;
	
	public static Stage stageRestaurant;
	
	//constructor for creating menu of restaurant
	public RestaurantController(Resturaunt r)
	{
		restaurant=r;
		menu=restaurant.getMenu().getMenu();
	}

	//set menu to categories (Salad,MainDish,Desser,Drink)
	public void start(Stage primaryStage) throws IOException {
		
		Font font=Font.font("Arial", FontWeight.BOLD, 15);
		Font font1=Font.font("Arial", FontWeight.BOLD, 25);
	
		AnchorPane pane=new AnchorPane();
		pane.setStyle("-fx-background-color: #555555;");
		pane.setPrefSize(800, 600);
		Label label=new Label(restaurant.getResturaunt_Name());
		label.setTextFill(Color.web("#eb8b00"));
		label.setFont(new Font("Arial", 50));
		label.setTranslateX(400);
		
		Label meallbl=new Label("Pick your meal");
		meallbl.setTextFill(Color.web("#eb8b00"));
		meallbl.setFont(new Font("Arial", 35));
		meallbl.setTranslateX(10);
		meallbl.setTranslateY(50);
		
		pane.getChildren().addAll(meallbl,label);
		
		HBox hbox=new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);
	
	
		//create salad menu
		VBox saladBox=new VBox(5);
		saladBox.setStyle("-fx-background-color: #707070");
		saladBox.setPrefSize(200, 360);
		Label saladlbl=new Label("Salad");
		saladlbl.setFont(font1);
		saladlbl.setTextFill(Color.web("#eb8b00"));
		saladlbl.setTranslateX(10);
		saladlbl.setTranslateY(100);
		
		ScrollPane saladPane = new ScrollPane();
		saladPane.setContent(saladBox);
		saladPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		saladPane.setFitToWidth(true); // set content width to viewport width
		saladPane.setPannable(true); // allow scrolling via mouse dragging
		saladPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		saladPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		//create mainDish menu
		VBox mainDishBox=new VBox(5);
		Label mainDishlbl=new Label("MainDish");
		mainDishBox.setPrefSize(200, 360);
		mainDishBox.setStyle("-fx-background-color: #707070");
		mainDishlbl.setFont(font1);
		mainDishlbl.setTextFill(Color.web("#eb8b00"));
		mainDishlbl.setTranslateX(240);
		mainDishlbl.setTranslateY(100);
		
		
		ScrollPane mainDishPane = new ScrollPane();
		mainDishPane.setContent(mainDishBox);
		mainDishPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		mainDishPane.setFitToWidth(true); // set content width to viewport width
		mainDishPane.setPannable(true); // allow scrolling via mouse dragging
		mainDishPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainDishPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		//create dessert menu
		VBox dessertBox=new VBox(5);
		Label dessertlbl=new Label("Dessert");
		dessertBox.setStyle("-fx-background-color: #707070");
		dessertBox.setPrefSize(200, 360);
		dessertlbl.setFont(font1);
		dessertlbl.setTextFill(Color.web("#eb8b00"));
		dessertlbl.setTranslateX(465);
		dessertlbl.setTranslateY(100);
		
		ScrollPane dessertPane = new ScrollPane();
		dessertPane.setContent(dessertBox);
		dessertPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		dessertPane.setFitToWidth(true); // set content width to viewport width
		dessertPane.setPannable(true); // allow scrolling via mouse dragging
		dessertPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		dessertPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		

		//create drinks menu
		VBox drinkBox=new VBox(5);
		Label drinklbl=new Label("Drink");
		drinkBox.setStyle("-fx-background-color: #707070");
		drinkBox.setPrefSize(200, 360);
		drinklbl.setFont(font1);
		drinklbl.setTextFill(Color.web("#eb8b00"));
		drinklbl.setTranslateX(690);
		drinklbl.setTranslateY(100);
		
		ScrollPane drinkPane = new ScrollPane();
		drinkPane.setContent(drinkBox);
		drinkPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		drinkPane.setFitToWidth(true); // set content width to viewport width
		drinkPane.setPannable(true); // allow scrolling via mouse dragging
		drinkPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		drinkPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		Line line = new Line();
		line.setStartX(0); 
		line.setEndX(230.0); 
		line.setStartY(10);
		line.setEndY(10);
		
		pane.getChildren().add(line);
		
		//drinkBox.getChildren().add(line);
		spinners=new HashMap<Item,Object>();
		item=restaurant.getMenu().getItems();
		
		//Initialize each item quantity according to database
		for (int i = 0; i < item.size(); i++)
		{
			HBox tmp=new HBox(10);
			Item it=(Item)item.get(i);

			Category c=it.getCat();
			Spinner<Integer> spinner=new Spinner<>();
			int initialValue = 0;
	        // Value factory.
	        SpinnerValueFactory<Integer> valueFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, menu.get(it), initialValue);
	        spinner.setValueFactory(valueFactory);
	        spinner.setPrefSize(70, 5);
	       
	        spinners.put(it, spinner);
			Label lbl=new Label(it.getItem_Name()+" "+it.getPrice()+"$");			
			lbl.setFont(font);
			lbl.setTextFill(Color.web("#eb8b00"));
			tmp.getChildren().addAll(spinner,lbl);
			
			switch(c) {
			//add each item to VBox according to its category
			case Drink:
				drinkBox.getChildren().add(tmp);
				
				break;
			case Dessert:
				dessertBox.getChildren().add(tmp);
				
				break;
			case Salad:
				saladBox.getChildren().add(tmp);
				
				break;
			case MainDish:
				mainDishBox.getChildren().add(tmp);
				
				break;
			}
		}
		
		//add all label to gui
		hbox.getChildren().addAll(saladPane,mainDishPane,dessertPane,drinkPane);
		pane.getChildren().addAll(saladlbl,mainDishlbl,dessertlbl,drinklbl);
		pane.getChildren().add(hbox);
		Button submitBtn=new Button("Next");
		Button backBtn=new Button("Back");
		submitBtn.setTranslateX(715);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(70);
		backBtn.setTranslateY(525);
		pane.getChildren().addAll(backBtn,submitBtn);
		backBtn.setOnAction(backToUserHomePage);
		submitBtn.setOnAction(Additions);
		
		Scene scene = new Scene(pane,915,600);
		scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
		backBtn.setId("button-back");
		submitBtn.setId("button-submit");
		primaryStage.setTitle("Resturaunt's Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	   
	//go to additions page
    EventHandler<ActionEvent> Additions = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
        	
        	int countItemQuantity=0;
        	//count selected items quantity by user
        	for(Item it: spinners.keySet())
        	{
        		System.out.println(it.toString());
        		Spinner<Integer> spin=(Spinner<Integer>) spinners.get(it);
        		countItemQuantity+=spin.getValue().intValue();
        	}
        	//check if user didn't pick any item
        	//if user didn't pick any items pops alert
        	if(countItemQuantity==0)
        	{
        		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
    			alert.setTitle("Message");
    			alert.setContentText("Fill in all information");
    			alert.getDialogPane().setPrefSize(200, 100);
    			Optional<ButtonType> result = alert.showAndWait();
    			if(result.get()==ButtonType.CLOSE)
    			{
    				return;
    			}
        	}
        	
        	 stageRestaurant= (Stage) ((Node) e.getSource()).getScene().getWindow();
        	 stageRestaurant.hide();
    		Stage primaryStage1 = new Stage();
    		AdditionsController AC=new AdditionsController();
			try {
				AC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    };
    
    //go back to restaurants list
	EventHandler<ActionEvent> backToUserHomePage = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
        	Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
    		stage.close();
    		Stage primaryStage1 = new Stage();
    		ResturauntMenuController RMC = new ResturauntMenuController();
			try {
				RMC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    };
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//t.setText("d");
	}
}
