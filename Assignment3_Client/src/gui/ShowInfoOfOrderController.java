package gui;

import common.Pickup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ShowInfoOfOrderController {

	public void start(Stage primaryStage) throws Exception {
		Label Additionslbl = new Label("Your Order");
		Additionslbl.setTextFill(Color.web("#eb8b00"));
		Additionslbl.setFont(new Font("Arial", 35));
		Additionslbl.setTranslateX(10);
		Additionslbl.setTranslateY(50);
		Font font1 = Font.font("Arial", FontWeight.BOLD, 25);
		Font font = Font.font("Arial", 23);
		Font font2 = Font.font("Arial", 20);
		Label Sum = new Label();

		if (PickUpFormController.Pickup == true)
			Sum.setText("Total Price: " + String.valueOf(AdditionsController.OverAllSum) + "$");

		Sum.setTextFill(Color.web("#eb8b00"));
		Sum.setFont(new Font("Arial", 35));
		Sum.setTranslateX(600);
		Sum.setTranslateY(50);
		AnchorPane pane = new AnchorPane();
		pane.setStyle("-fx-background-color: #555555;");
		pane.getChildren().addAll(Sum, Additionslbl);

		HBox hbox = new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);

		VBox saladBox = new VBox(5);
		saladBox.setPrefSize(200, 260);
		Label saladlbl = new Label(" Salad");
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

		VBox mainDishBox = new VBox(5);
		mainDishBox.setPrefSize(200, 260);
		mainDishBox.setStyle("-fx-background-color: #707070");
		Label mainDishlbl = new Label(" MainDish");
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

		VBox dessertBox = new VBox(5);
		dessertBox.setPrefSize(200, 260);
		dessertBox.setStyle("-fx-background-color: #707070");
		Label dessertlbl = new Label(" Dessert");
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

		VBox drinkBox = new VBox(5);
		drinkBox.setPrefSize(200, 260);
		drinkBox.setStyle("-fx-background-color: #707070");
		Label drinklbl = new Label(" Drink");
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

		VBox vbox5 = new VBox(5);
		vbox5.setPrefSize(800, 100);
		vbox5.setStyle("-fx-background-color: #707070");
		Label pickupOrdeliverylbl = new Label();
		Label datelbl = new Label();
		Label timelbl = new Label();
		if (PickUpFormController.Pickup == true) {

			pickupOrdeliverylbl.setText("PickUp Information");
			datelbl.setText("Date: " + PickUpFormController.pickup.getDate());
			timelbl.setText(
					"Time: " + PickUpFormController.pickup.getHour() + ":" + PickUpFormController.pickup.getMinute());
			pickupOrdeliverylbl.setFont(font1);
			pickupOrdeliverylbl.setTextFill(Color.web("#eb8b00"));
			datelbl.setFont(font);
			datelbl.setTextFill(Color.web("#eb8b00"));
			timelbl.setFont(font);
			timelbl.setTextFill(Color.web("#eb8b00"));
			vbox5.getChildren().add(pickupOrdeliverylbl);
			vbox5.getChildren().addAll(datelbl, timelbl);

		} else if (DeliveryInfoController.Delivery == true) { // not working
			pickupOrdeliverylbl.setText("delivery Information");
		}

		ScrollPane pickupOrdeliveryPane = new ScrollPane(vbox5);
		pickupOrdeliveryPane.setTranslateX(10);
		pickupOrdeliveryPane.setTranslateY(145);
		pickupOrdeliveryPane.setContent(vbox5);
		pickupOrdeliveryPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		pickupOrdeliveryPane.setFitToWidth(true); // set content width to viewport width
		pickupOrdeliveryPane.setPannable(true); // allow scrolling via mouse dragging

		for (int j = 0, salads = 1, main = 1, dessert = 1, drinks = 1, temp = 0; j < AdditionsController.arlist
				.size(); j++) {

			Label lbl = new Label();
			lbl.setFont(font);
			lbl.setTextFill(Color.web("#eb8b00"));
			if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Salad")) {
				lbl.setText(salads + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				saladBox.getChildren().addAll(lbl);
				salads++;
			} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("MainDish")) {
				lbl.setText(main + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				mainDishBox.getChildren().add(lbl);
				main++;
			} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Dessert")) {
				lbl.setText(dessert + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				dessertBox.getChildren().add(lbl);
				dessert++;
			} else {
				lbl.setText(drinks + " " + AdditionsController.arlist.get(j).getItem().getItem_Name());
				drinkBox.getChildren().add(lbl);
				drinks++;
			}

			CheckBox[] c = AdditionsController.arlist.get(j).getCB();
			for (int k = 0; k < c.length; k++) {
				Label lbl2 = new Label(c[k].getText());
				if (c[k].isSelected()) {
					lbl2.setFont(font2);
					if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Salad")) {
						saladBox.getChildren().add(lbl2);
					} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("MainDish")) {
						mainDishBox.getChildren().add(lbl2);
					} else if (AdditionsController.arlist.get(j).getItem().getCat().name().equals("Dessert")) {
						dessertBox.getChildren().add(lbl2);
					} else {
						drinkBox.getChildren().add(lbl2);
					}
				}
			}
		}

		Button submitBtn = new Button("Confirm");
		Button backBtn = new Button("Cancel");
		submitBtn.setTranslateX(690);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(70);
		backBtn.setTranslateY(525);
		
		submitBtn.setOnAction(SubmitOrder);
		
		VBox vbox6 = new VBox();

		hbox.getChildren().addAll(saladPane, mainDishPane, dessertPane, drinkPane);
		pane.getChildren().addAll(saladlbl, mainDishlbl, dessertlbl, drinklbl);
		vbox6.getChildren().addAll(hbox, pickupOrdeliveryPane);
		pane.getChildren().addAll(submitBtn, backBtn, vbox6);

		Scene scene = new Scene(pane, 915, 600);
		scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
		backBtn.setId("button-back");
		submitBtn.setId("button-submit");
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	EventHandler<ActionEvent> SubmitOrder= new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
        	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			PaymentSuccessfulController PSC = new PaymentSuccessfulController();
			try {
				PSC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
        }
    };
}
