package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Addition;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.User;
import common.ViewOrderDetails;
import common.clientDetails;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TypeOfOrderController extends Thread implements Initializable {
	@FXML
	private Button DeliveryBtn;

	@FXML
	private Button PickUpBtn;

	@FXML
	private Button backBtn;

	@FXML
	private TextField sharedtxt;

	@FXML
	private ButtonBar sharedBar;

	@FXML
	private Text errtxt;

	public static Alert alert;
	private ActionEvent e;
	public static boolean shared=false;
	public static User user;
	public static int num;

	@FXML
	//go back to show order of user page
	void BackToRestPage(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		ShowOrderController SOC = new ShowOrderController();
		try {
			SOC.start(primaryStage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	//go to delivery form
	void DeliveryFunction(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		DeliveryInfoController DIC = new DeliveryInfoController();
		DIC.start(primaryStage);
	}

	@FXML
	//go to pickup form
	void PickUpFunction(ActionEvent event) throws Exception {
		shared=false;
		if(ChatClient.Bussinessuser.getLock() == 1)
			return;
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		PickUpFormController PUFC = new PickUpFormController();
		PUFC.start(primaryStage);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/TypeOfOrderFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Type Of Order");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	//if user is business and he picked shared delivery
	void SharedBtn(ActionEvent e) {
		this.e = e;
		//get group number from text field
		MessagesClass msg = new MessagesClass(Messages.getGroupNumber, Integer.parseInt(sharedtxt.getText()));
		ClientUI.chat.accept(msg);

		//check if group number exists in date base
		if (ChatClient.group_exist == true) {
			shared=true;
			user=ChatClient.user;
			clientDetails cd = new clientDetails(ChatClient.Bussinessuser.getFirstName(),
					Integer.parseInt(sharedtxt.getText()), "Joined", 0);
			cd.setNuser(ChatClient.user);
			//cd.setArlist(AdditionsController.arlist);
			ChatClient.Bussinessuser.setGroup_code(cd.getCode());
			ChatClient.Bussinessuser.setOverAllPrice(AdditionsController.OverAllSum);
			cd.setUser(ChatClient.Bussinessuser);

			//////////////////////////////////////////////////////////////////////
			MessagesClass msgc = new MessagesClass(Messages.getOrderID, null);
			ClientUI.chat.accept(msgc);
			int orderNum = ChatClient.orderNum;
			orderNum++;
			
			MessagesClass msgi = new MessagesClass(Messages.getIND, null);
			ClientUI.chat.accept(msgi);
			int ind = ChatClient.ind;
			ind++;
			
			ArrayList<Item> items = new ArrayList<>();
			for (Item it : RestaurantController.spinners.keySet()) {
				Spinner<Integer> spin = (Spinner<Integer>) RestaurantController.spinners.get(it);
				if (spin.getValue().intValue() > 0)
					items.add(new Item(it.getItem_ID(), spin.getValue().intValue()));
			}
			String CurrentDateAndTime = String
					.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

			//make order object of the user and save it
			Order order = new Order(orderNum, RestaurantController.restaurant, ChatClient.user, CurrentDateAndTime,
					AdditionsController.OverAllSum, items, "", "", "");
			ChatClient.Bussinessuser.setOrder(order);
			
			ArrayList<Item> item_addition=new ArrayList<>();
			ArrayList<Item> item_additions=new ArrayList<>();
			ArrayList<Addition> additions;
			String str="";
			for(int i=0;i<AdditionsController.arlist.size();i++)
			{
				additions=new ArrayList<>();
				str="";
				CheckBox[] c = AdditionsController.arlist.get(i).getCB();
				for (int k = 0; k < c.length; k++) {
					if (c[k].isSelected())
					{
						Addition add=new Addition(c[k].getText());
						additions.add(add);
						str+=add.getName();
						str+=" ";
					}
				}
				item_addition.add(new Item(orderNum,AdditionsController.arlist.get(i).getItem().getItem_ID(),additions,ind++));
				item_additions.add(new Item(orderNum,AdditionsController.arlist.get(i).getItem().getItem_ID(),str,ind++));
			}
			cd.setItem_addition(item_additions);
			/*
			MessagesClass msg5 = new MessagesClass(Messages.order_items_additions,item_addition);
			ClientUI.chat.accept(msg5);
			*/
			//update the admin table to show him that this user has joined the created room
			MessagesClass msg1 = new MessagesClass(Messages.updateTable, cd);
			ClientUI.chat.accept(msg1);
			
			//pops alert to user and tells him to wait until everyone else joined the room
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Waiting for other member to join");
			alert.showAndWait();
			
			//set lock to user so that he can't interact anymore
			ChatClient.Bussinessuser.setLock(1);
			
			//run thread so when everyone joined the room and admins clicked on finish order everyone else is free to go
			runThread();
		} else {
			errtxt.setText("Wrong code, try again");
			return;
		}
	}

	private void runThread() {
		Thread t = new Thread(() -> {
			while (ChatClient.Bussinessuser.getLock() == 1)
				try {
					Thread.sleep(1);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			Platform.runLater(() -> {
				
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				PaymentSuccessfulController PSC = new PaymentSuccessfulController();
				try {
					PSC.start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});
		});
		t.start();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!ChatClient.user.getUserType().equals("Bussiness")) {
			sharedBar.setVisible(false);
		}
	}

}
