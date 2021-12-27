package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.WorkerUser;

import common.MessagesClass;
import common.Order;
import common.RestaurantManager;
import common.Resturaunt;
import common.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RestaurantManagerHomePage implements Initializable {
	ObservableList<Order> listM;
	ObservableList<Order> dataList;
	@FXML
	private TableView<Order> orderstable;

	@FXML
	private TableColumn<Order, Integer> ordernum;

	@FXML
	private TableColumn<Order, Double> totalpricenum;

	@FXML
	private TableColumn<Order, String> pickuptime;

	@FXML
	private Text pathtext;
	@FXML
	private Button CreateReport;
	@FXML
	private Text managername;

	@FXML
	private Text clock = new Text();
	public static String sClock;

	@FXML
	private Button creatrmenu;

	@FXML
	private Button edittworker;

	@FXML

	public static Resturaunt Resturauntm;
	@FXML
	private Text resId;
	public static RestaurantManager restaurantmanager;
	WorkerUser WorkerUser;

	@FXML
	void CreateReport(ActionEvent event) throws IOException {
		RestaurantManager manager;
		MessagesClass msg = new MessagesClass(Messages.getRestaurantManager, ChatClient.user);
		ClientUI.chat.accept(msg);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ReportListForRestaurantController aFrame1 = new ReportListForRestaurantController();
		Stage primaryStage11 = new Stage();
		try {
			aFrame1.start(primaryStage11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws Exception {
		;
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/RestaurantManagerHomePage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Home");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void Editworkerbt(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		EditWorkers_HomePage aFrame1 = new EditWorkers_HomePage();
		Stage primaryStage11 = new Stage();
		try {
			aFrame1.start(primaryStage11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {
		// FXMLLoader loader = new FXMLLoader();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		stage.close();
		ClientUI.chat.accept(new MessagesClass(Messages.updateStatus, ChatClient.user, 0));
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
	void updatecreatemenu(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		EditUpdateMenu aFrame1 = new EditUpdateMenu();
		Stage primaryStage11 = new Stage();
		try {
			aFrame1.start(primaryStage11);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
			pathtext.setText("LogIn->ResturauntManager");
			MessagesClass msg = new MessagesClass(Messages.getRestaurantManager, ChatClient.user);
			ClientUI.chat.accept(msg);
			restaurantmanager = ChatClient.restaurantManager;
			System.out.println(restaurantmanager + "im in init in manager of restaurant");
			MessagesClass msg1 = new MessagesClass(Messages.getrestaurantname, restaurantmanager.getIDRestaurant());
			ClientUI.chat.accept(msg1);
			Resturauntm = ChatClient.Resturaunt;
			System.out.print(Resturauntm);
			managername.setText("Manager ," + restaurantmanager.getFirstName());
			resId.setText(ChatClient.Resturaunt.getResturaunt_Name());
			MessagesClass msg11 = new MessagesClass(Messages.GetAllOreders, restaurantmanager.getIDRestaurant());
			ClientUI.chat.accept(msg11);
			listM = FXCollections.observableArrayList(ChatClient.AllOrder);/** array list of normal users */
			ordernum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
			totalpricenum.setCellValueFactory(new PropertyValueFactory<Order, Double>("TotalPrice"));
			pickuptime.setCellValueFactory(new PropertyValueFactory<Order, String>("pickupTime"));
			orderstable.setItems(listM);

		

		

		final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
