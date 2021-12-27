package gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	public static SharedDeliveryController SDC;
	
	public void start(Stage primaryStage) throws Exception {
		// take current date, hour and minutes
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date CurrentDate = sdformat.parse(NormalUserHomePageController.sClock);
		long twoHour = 2 * 1000 * 60 * 60;

		// create header of page
		Label headerlbl = new Label("Your Order");
		headerlbl.setTextFill(Color.web("#eb8b00"));
		headerlbl.setFont(new Font("Arial", 35));
		headerlbl.setTranslateX(10);
		headerlbl.setTranslateY(10);
		Font font1 = Font.font("Arial", FontWeight.BOLD, 25);
		Font font = Font.font("Arial", 23);
		Font font2 = Font.font("Arial", 20);
		AnchorPane pane = new AnchorPane();
		Label SumDiscount = new Label();
		Label Sum = new Label();

		// if order is pick up
		if (PickUpFormController.Pickup == true) {
			Date picked_Date = sdformat.parse(PickUpFormController.pickup.getDate() + " "
					+ PickUpFormController.pickup.getHour() + ":" + PickUpFormController.pickup.getMinute());

			Sum.setText("Total Price: " + String.valueOf(String.format("%.2f", AdditionsController.OverAllSum)) + "$");

			// if users order is needed at >=2hours then add discount of 10%
			if (picked_Date.getTime() >= CurrentDate.getTime() + twoHour) {
				AdditionsController.OverAllSum *= 0.9;
				SumDiscount.setText("Price After Discount: "
						+ String.valueOf(String.format("%.2f", AdditionsController.OverAllSum)) + "$");
				SumDiscount.setTextFill(Color.web("#eb8b00"));
				SumDiscount.setFont(new Font("Arial", 35));
				SumDiscount.setTranslateX(450);
				SumDiscount.setTranslateY(50);
				pane.getChildren().add(SumDiscount);
			}
			Label wallet = new Label();
			// if user is business then show wallet balance
			if (ChatClient.user.getUserType().equals("Bussiness")) {
				wallet.setText("Wallet balance: " + String.valueOf(ChatClient.Bussinessuser.getW4c().getMoney()) + "$");
				wallet.setTextFill(Color.web("#eb8b00"));
				wallet.setFont(new Font("Arial", 35));
				wallet.setTranslateX(10);
				wallet.setTranslateY(50);
				pane.getChildren().add(wallet);
			}
		}
		// if order is delivery
		else if (DeliveryInfoController.Delivery == true) {

			Label wallet = new Label();
			// if user is business then show wallet balance
			if (ChatClient.user.getUserType().equals("Bussiness")) {
				wallet.setText("Wallet balance: " + String.valueOf(ChatClient.Bussinessuser.getW4c().getMoney()) + "$");
				wallet.setTextFill(Color.web("#eb8b00"));
				wallet.setFont(new Font("Arial", 35));
				wallet.setTranslateX(10);
				wallet.setTranslateY(50);
				pane.getChildren().add(wallet);
			}
			// if delivery is basic then add 25 to total price
			if (DeliveryInfoController.del.getDeliveryType().equals("Basic 25$"))
				AdditionsController.OverAllSum += 25;

			Sum.setText("Total Price: " + String.valueOf(String.format("%.2f", AdditionsController.OverAllSum)) + "$");

			Date date = sdformat.parse(DeliveryInfoController.del.getDate() + " " + DeliveryInfoController.del.getHour()
					+ ":" + DeliveryInfoController.del.getMinute());
			// if users order is needed at >=2hours then add discount of 10%
			if (date.getTime() >= CurrentDate.getTime() + twoHour) {
				AdditionsController.OverAllSum *= 0.9;
				SumDiscount.setText("Price After Discount: "
						+ String.valueOf(String.format("%.2f", AdditionsController.OverAllSum)) + "$");
				SumDiscount.setTextFill(Color.web("#eb8b00"));
				SumDiscount.setFont(new Font("Arial", 35));
				SumDiscount.setTranslateX(450);
				SumDiscount.setTranslateY(50);
				pane.getChildren().add(SumDiscount);
			}

		}
		Sum.setTextFill(Color.web("#eb8b00"));
		Sum.setFont(new Font("Arial", 35));
		Sum.setTranslateX(600);
		Sum.setTranslateY(10);

		//set price and header to gui
		pane.setStyle("-fx-background-color: #555555;");
		pane.getChildren().addAll(Sum, headerlbl);

		HBox hbox = new HBox(10);
		hbox.setTranslateX(10);
		hbox.setTranslateY(140);

		//show salads chosen by user
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

		//show mainDish chosen by user
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

		//show desserts chosen by user
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

		//show drinks chosen by user
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
		Label namelbl = new Label();
		Label addresslbl = new Label();
		Label opentxt = new Label();
		if (PickUpFormController.Pickup == true) {
			//show user pickup details
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

		} else if (DeliveryInfoController.Delivery == true) { 
			//show user delivery details
			pickupOrdeliverylbl.setText("Delivery Information");
			datelbl.setText("Date: " + DeliveryInfoController.del.getDate());
			timelbl.setText(
					"Time: " + DeliveryInfoController.del.getHour() + ":" + DeliveryInfoController.del.getMinute());
			namelbl.setText("To: " + DeliveryInfoController.del.getFirstname());
			addresslbl.setText("Address: " + DeliveryInfoController.del.getAddress());
			if (!DeliveryInfoController.del.getClientText().equals(""))
				opentxt.setText("Your Comment: " + DeliveryInfoController.del.getClientText());
			pickupOrdeliverylbl.setFont(font1);
			pickupOrdeliverylbl.setTextFill(Color.web("#eb8b00"));
			datelbl.setFont(font);
			datelbl.setTextFill(Color.web("#eb8b00"));
			timelbl.setFont(font);
			timelbl.setTextFill(Color.web("#eb8b00"));
			namelbl.setFont(font);
			namelbl.setTextFill(Color.web("#eb8b00"));
			addresslbl.setFont(font);
			addresslbl.setTextFill(Color.web("#eb8b00"));
			opentxt.setFont(font);
			opentxt.setTextFill(Color.web("#eb8b00"));
			vbox5.getChildren().addAll(pickupOrdeliverylbl, namelbl, datelbl, timelbl, addresslbl, opentxt);
		}

		ScrollPane pickupOrdeliveryPane = new ScrollPane(vbox5);
		pickupOrdeliveryPane.setTranslateX(10);
		pickupOrdeliveryPane.setTranslateY(145);
		pickupOrdeliveryPane.setContent(vbox5);
		pickupOrdeliveryPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // never show a vertical ScrollBar
		pickupOrdeliveryPane.setFitToWidth(true); // set content width to viewport width
		pickupOrdeliveryPane.setPannable(true); // allow scrolling via mouse dragging

		for (int j = 0, salads = 1, main = 1, dessert = 1, drinks = 1; j < AdditionsController.arlist.size(); j++) {

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
		Button backBtn = new Button("Back");
		submitBtn.setTranslateX(690);
		submitBtn.setTranslateY(525);
		backBtn.setTranslateX(70);
		backBtn.setTranslateY(525);

		backBtn.setOnAction(BackFunction);
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
		primaryStage.setTitle("Order Information");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//submit order
	EventHandler<ActionEvent> SubmitOrder = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {

			//if order was shared delivery open SharedDeliveryController
			if (DeliveryInfoController.Delivery == true && ChatClient.user.getUserType().equals("Bussiness")
					&& DeliveryInfoController.deliveryType.equals("Shared")) {
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				SDC = new SharedDeliveryController();
				try {
					SDC.start(primaryStage);
					return;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			//if business user doesn't have enough money in his w4c card pop alert
			if (ChatClient.user.getUserType().equals("Bussiness")
					&& AdditionsController.OverAllSum > ChatClient.Bussinessuser.getW4c().getMoney()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Payment");
				alert.setHeaderText("Ops you don't have enough money in your W4C balance");
				alert.setContentText("Pay with W4C and the rest with your credit card?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					ChatClient.Bussinessuser.getW4c().setMoney(0);
					MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
					ClientUI.chat.accept(msg);
				} else {
					return;
				}
			}
			
			//if business user have the required money in his w4c card -> update w4c balance
			if (ChatClient.user.getUserType().equals("Bussiness")
					&& AdditionsController.OverAllSum < ChatClient.Bussinessuser.getW4c().getMoney()) {
				ChatClient.Bussinessuser.getW4c()
						.setMoney(ChatClient.Bussinessuser.getW4c().getMoney() - AdditionsController.OverAllSum);
				MessagesClass msg = new MessagesClass(Messages.updateW4CforBussiness, ChatClient.Bussinessuser);
				ClientUI.chat.accept(msg);
			}
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

	//go back to payment page
	EventHandler<ActionEvent> BackFunction = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			PaymentNormalUserController PNUC = new PaymentNormalUserController();
			try {
				PNUC.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	};
}
