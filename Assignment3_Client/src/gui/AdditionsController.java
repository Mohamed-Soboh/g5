package gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import common.Item;
import common.ViewOrderDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdditionsController {
	public static LinkedHashMap<Item, Object> additions;
	public static LinkedHashMap<Integer, LinkedHashMap<Item, Object>> Order;
	public static ArrayList<ViewOrderDetails> arlist;
	public static double OverAllSum;
	public static Stage stageAdditions;


	public void start(Stage primaryStage) throws Exception {
		//header of the page
		Label Additionslbl=new Label("Pick additions to your meal");
		Additionslbl.setTextFill(Color.web("#eb8b00"));
		Additionslbl.setFont(new Font("Arial", 35));
		Additionslbl.setTranslateX(10);
		Additionslbl.setTranslateY(50);
		Font font1=Font.font("Arial", FontWeight.BOLD, 25);
		Font font=Font.font("Arial", 23);
		Font font2=Font.font("Arial",  20);
		OverAllSum = 0;
		AnchorPane pane = new AnchorPane();
		pane.setStyle("-fx-background-color: #555555;");
		pane.getChildren().add(Additionslbl);
		
		HBox hbox=new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);
		
		//create salad menu additions
		VBox saladBox = new VBox(5);
		saladBox.setPrefSize(200, 360);
		Label saladlbl=new Label(" Salad");
		saladBox.setStyle("-fx-background-color: #707070");
		saladlbl.setFont(font1);
		saladlbl.setTextFill(Color.web("#eb8b00"));
		ScrollPane saladPane = new ScrollPane(saladBox);
		saladPane.setContent(saladBox);
		saladPane.setFitToWidth(true); // set content width to viewport width
		saladPane.setPannable(true); // allow scrolling via mouse dragging
		saladPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		saladPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		saladPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		saladlbl.setTranslateX(10);
		saladlbl.setTranslateY(100);
		
		//create mainDish menu additions
		VBox mainDishBox = new VBox(5);
		mainDishBox.setPrefSize(200, 360);
		mainDishBox.setStyle("-fx-background-color: #707070");	
		Label mainDishlbl=new Label(" MainDish");
		mainDishlbl.setFont(font1);
		mainDishlbl.setTextFill(Color.web("#eb8b00"));
		ScrollPane mainDishPane = new ScrollPane(mainDishBox);
		mainDishPane.setContent(mainDishBox);
		mainDishPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		mainDishPane.setFitToWidth(true); // set content width to viewport width
		mainDishPane.setPannable(true); // allow scrolling via mouse dragging
		mainDishPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainDishPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		mainDishlbl.setTranslateX(240);
		mainDishlbl.setTranslateY(100);
		
		//create dessert menu additions
		VBox dessertBox = new VBox(5);
		dessertBox.setPrefSize(200, 360);
		dessertBox.setStyle("-fx-background-color: #707070");
		Label dessertlbl=new Label(" Dessert");
		dessertlbl.setFont(font1);
		dessertlbl.setTextFill(Color.web("#eb8b00"));
		ScrollPane dessertPane = new ScrollPane(dessertBox);
		dessertPane.setContent(dessertBox);
		dessertPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		dessertPane.setFitToWidth(true); // set content width to viewport width
		dessertPane.setPannable(true); // allow scrolling via mouse dragging
		dessertPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		dessertPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		dessertlbl.setTranslateX(465);
		dessertlbl.setTranslateY(100);

		//create drinks menu additions
		VBox drinkBox = new VBox(5);
		drinkBox.setPrefSize(200, 360);
		drinkBox.setStyle("-fx-background-color: #707070");
		Label drinklbl=new Label(" Drink");
		drinklbl.setFont(font1);
		drinklbl.setTextFill(Color.web("#eb8b00"));
		ScrollPane drinkPane = new ScrollPane(drinkBox);	
		drinkPane.setContent(drinkBox);
		drinkPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		drinkPane.setFitToWidth(true); // set content width to viewport width
		drinkPane.setPannable(true); // allow scrolling via mouse dragging
		drinkPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		drinkPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		drinklbl.setTranslateX(690);
		drinklbl.setTranslateY(100);
	
		int countItemQuantity = 0;
		for (Item it : RestaurantController.spinners.keySet()) {
			Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
			countItemQuantity += spin.getValue().intValue();
		}

		int i = 0;
		arlist = new ArrayList<ViewOrderDetails>();
		//add additions to gui and category
		for (Item it : RestaurantController.spinners.keySet()) {

			Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
			for (int k = 0; k < spin.getValue().intValue(); k++) {
				CheckBox[] CB = new CheckBox[it.getAdditions().size()];
				for (int j = 0; j < it.getAdditions().size(); j++) {
					CheckBox checkbox = new CheckBox(it.getAdditions().get(j).getName());
					CB[j] = checkbox;
					CB[j].setFont(font2);
					CB[j].setTextFill(Color.web("#eb8b00"));
				}
				OverAllSum += it.getPrice();//calculate price of picked items
			
				ViewOrderDetails VOD = new ViewOrderDetails(CB, it);
				arlist.add(VOD);
				     Label lbl = new Label(it.getItem_Name());
				     lbl.setFont(font);
				     lbl.setTextFill(Color.web("#eb8b00"));
				if (it.getCat().name().equals("Salad")) {
					saladBox.getChildren().add(lbl);
					saladBox.getChildren().addAll(CB);
				} else if (it.getCat().name().equals("MainDish")) {
					mainDishBox.getChildren().add(lbl);
					mainDishBox.getChildren().addAll(CB);
				} else if (it.getCat().name().equals("Dessert")) {
					dessertBox.getChildren().add(lbl);
					dessertBox.getChildren().addAll(CB);
				} else {
					drinkBox.getChildren().add(lbl);
					drinkBox.getChildren().addAll(CB);
				}
				i++;
			}
		}
		Button submitBtn = new Button("Show Order");
		Button backBtn = new Button("Back");
		submitBtn.setTranslateX(715);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(70);
		backBtn.setTranslateY(525);
		backBtn.setOnAction(backToRestaurantController);
		submitBtn.setOnAction(ShowOrder);

		hbox.getChildren().addAll(saladPane, mainDishPane, dessertPane,drinkPane);
		pane.getChildren().addAll(saladlbl,mainDishlbl,dessertlbl,drinklbl);
		pane.getChildren().addAll(submitBtn, backBtn, hbox);
		Scene scene = new Scene(pane, 915, 600);
		scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
		backBtn.setId("button-back");
		submitBtn.setId("button-submit");
		primaryStage.setTitle("Additions Info");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//go back to restaurant menu
	EventHandler<ActionEvent> backToRestaurantController = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			stageAdditions = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stageAdditions.hide();
			//stage.close();
			//Stage primaryStage1 = new Stage();
			//RestaurantController RC = new RestaurantController(RestaurantController.restaurant);
			try {
				//RC.start(primaryStage1);
				RestaurantController.stageRestaurant.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	//show users order
	EventHandler<ActionEvent> ShowOrder = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			 stageAdditions = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//stage.close();
			 stageAdditions.hide();
			Stage primaryStage1 = new Stage();
			ShowOrderController SOC = new ShowOrderController();
			try {
				SOC.start(primaryStage1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
}
