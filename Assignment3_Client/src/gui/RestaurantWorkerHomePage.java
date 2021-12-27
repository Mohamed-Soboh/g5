package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Messages;
import common.WorkerUser;

import common.MessagesClass;
import common.Resturaunt;
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

public class RestaurantWorkerHomePage  implements Initializable {
	 static WorkerUser WorkerUser;
		public static String sClock;

		public static Resturaunt Resturauntm;
	@FXML
	private Text clock;

	@FXML
	private Text name;

	@FXML
	private Text resname;

	@FXML
	private Text pathtext;
	public void start(Stage primaryStage) throws Exception {
		;
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/ResturauntWorker.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("ResturauntWorker home page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	@FXML
	void agreerequset(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		agreeRestaurantrequset aFrame = new agreeRestaurantrequset(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("LogIn->WorkerPage");
		MessagesClass msg = new MessagesClass(Messages.getRestaurantWorker, ChatClient.user);
		ClientUI.chat.accept(msg);
		WorkerUser = ChatClient.WorkerUser;
		name.setText(WorkerUser.getFirstName());
		System.out.println(WorkerUser);
		MessagesClass msg1 = new MessagesClass(Messages.getrestaurantname, WorkerUser.getIDRestaurant());
		ClientUI.chat.accept(msg1);
		Resturauntm = ChatClient.Resturaunt;
		resname.setText(Resturauntm.getResturaunt_Name());
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				clock.setText(LocalDateTime.now().format(format));
				sClock = String.valueOf(LocalDateTime.now().format(format));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

}
