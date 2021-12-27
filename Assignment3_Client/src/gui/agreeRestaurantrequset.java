package gui;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import client.ChatClient;
import client.ClientUI;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Order;
import common.WorkerUser;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class agreeRestaurantrequset implements Initializable {
	String sqltable;
	ObservableList<Order> listM;
	ObservableList<Order> dataList;
	ObservableList List1 = FXCollections.observableArrayList();

	@FXML
	private TableView<Order> tabel;
	@FXML
	private ListView<String> additionlist;

	@FXML
	private ListView<String> itemlist;
	@FXML
	private TableColumn<Order, Integer> ordernumber;
	static int idorder;
	@FXML
	private TableColumn<Order, String> userid;

	@FXML
	private TableColumn<Order, String> submitdate;

	@FXML
	private TableColumn<Order, Double> price;

	@FXML
	private TableColumn<Order, String> pickuptime;

	@FXML
	private Text pathtext;
	
	@FXML
	private Text errtxt;
	
	@FXML
	private DatePicker datetxt;

	@FXML
	private ComboBox<String> minutetxt;

	@FXML
	private ComboBox<String> hourtxt;
	
	URL location;
	ResourceBundle resources;

	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/agreeRestaurantrequset.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("confirm order page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void Send_Delivery_Confirm(ActionEvent event) throws ParseException {
		if (datetxt.getValue() == (null) || hourtxt.getSelectionModel().isEmpty()
				|| minutetxt.getSelectionModel().isEmpty()) {
			errtxt.setText("  Enter details");
			return;
		}
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date); // assigns calendar to given date
		int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int minutes = calendar.get(Calendar.MINUTE);

		String pickedDate = datetxt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Date CurrentDate = sdformat.parse(RestaurantWorkerHomePage.sClock);
		Date picked_Date = sdformat.parse(pickedDate);
		int hour = Integer.parseInt(hourtxt.getValue());
		int minute = Integer.parseInt(minutetxt.getValue());
		if ((hours < hour && CurrentDate.compareTo(picked_Date) == 0)
				|| (minutes < minute && hours == hour && CurrentDate.compareTo(picked_Date) == 0)
				|| CurrentDate.compareTo(picked_Date) < 0) {
			String str="Delivery on it's way\n"+"Expected Date and Time\n"+"Date: "+pickedDate+"\nTime: "+hourtxt.getValue()+":"+minutetxt.getValue();
			
			MessagesClass msg=new MessagesClass(Messages.CheckDelivery,idorder);
			ClientUI.chat.accept(msg);
			
			if(ChatClient.deliveryExists==false)
				return;
			
			sendToClientEmail(str);
			msg=new MessagesClass(Messages.DeleteOrder,idorder);
			ClientUI.chat.accept(msg);
			initialize(location,resources);
		}
		else {
			errtxt.setText("Enter Valid information");
			return;
		}
	}

	@FXML
	void Send_Pickup_Confirm(ActionEvent event) {
		
	}

	@FXML
	void logout(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		RestaurantWorkerHomePage aFrame = new RestaurantWorkerHomePage(); // create StudentFrame
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pathtext.setText("LogIn->WorkerPage->acceptOrders");

		int i;
		for (i = 0; i < 24; i++) {
			if (i < 10)
				hourtxt.getItems().add("0" + String.valueOf(i));
			else
				hourtxt.getItems().add(String.valueOf(i));
		}

		for (i = 0; i < 60; i++) {
			if (i < 10)
				minutetxt.getItems().add("0" + i);
			else
				minutetxt.getItems().add(String.valueOf(i));
		}

		MessagesClass msg11 = new MessagesClass(Messages.GetAllOreders,
				RestaurantWorkerHomePage.WorkerUser.getIDRestaurant());
		ClientUI.chat.accept(msg11);
		listM = FXCollections.observableArrayList(ChatClient.AllOrder);/** array list of normal users */
		ordernumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
		userid.setCellValueFactory(new PropertyValueFactory<Order, String>("userid"));
		submitdate.setCellValueFactory(new PropertyValueFactory<Order, String>("CurrentDateAndTime"));
		price.setCellValueFactory(new PropertyValueFactory<Order, Double>("TotalPrice"));
		pickuptime.setCellValueFactory(new PropertyValueFactory<Order, String>("pickupTime"));
		tabel.setItems(listM);
		settext();
	}

	Session newSession = null;
	MimeMessage mimeMessage = null;

	private void sendEmail() throws MessagingException {
		String fromUser = "g5.biteme@gmail.com"; // Enter sender email id
		String fromUserPassword = "Biteme123"; // Enter sender gmail password , this will be authenticated by gmail smtp
												// server
		String emailHost = "smtp.gmail.com";
		Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
		System.out.println("Email successfully sent!!!");
	}

	private MimeMessage draftEmail(String str) throws AddressException, MessagingException, IOException {
		String emailReceipients = ChatClient.str; // Enter list of email
		String emailSubject = "Bite Me Admins";
		String emailBody = str;
		mimeMessage = new MimeMessage(newSession);
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients));
		mimeMessage.setSubject(emailSubject);

		// CREATE MIMEMESSAGE
		// CREATE MESSAGE BODY PARTS
		// CREATE MESSAGE MULTIPART
		// ADD MESSAGE BODY PARTS ----> MULTIPART
		// FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object

		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(emailBody);
		// bodyPart.setContent(emailBody, "html/text");
		MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(bodyPart);
		mimeMessage.setContent(multiPart);
		return mimeMessage;
	}

	private void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties, null);
	}

	void sendToClientEmail(String str) {

		setupServerProperties();
		try {
			draftEmail(str);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sendEmail();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void settext() {
		tabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Order w = tabel.getItems().get(tabel.getSelectionModel().getSelectedIndex());
				idorder = w.getOrderNum();

				String userid = w.getUserid();
				MessagesClass msg11 = new MessagesClass(Messages.getuser, userid);
				ClientUI.chat.accept(msg11);

				ItemsAddition aFrame = new ItemsAddition(); // create StudentFrame
				Stage primaryStage = new Stage();
				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

	public void loadlist(int id) {

		List1.removeAll(List1);
		for (int i = 0; i < ChatClient.Items.size(); i++) {
			if (id == ChatClient.Items.get(i).getItem_ID()) {
				for (int j = 0; j < ChatClient.Items.get(i).getAdditions().size(); j++) {
					List1.addAll(ChatClient.Items.get(i).getAdditions().get(j).getName());
				}
			}
		}
		itemlist.getItems().addAll(List1);

	}
}
