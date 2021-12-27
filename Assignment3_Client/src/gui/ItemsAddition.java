package gui;

import java.io.IOException;
import java.net.URL;
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
import common.ItemAddition;
import common.Messages;
import common.MessagesClass;
import common.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ItemsAddition implements Initializable {
	ObservableList<ItemAddition> listM;
	ObservableList<ItemAddition> dataList;
	@FXML
	private TableView<ItemAddition> itemsadditiontable;

	@FXML
	private TableColumn<ItemAddition, String> item;

	@FXML
	private TableColumn<ItemAddition, String> addition;

	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/Items&Addition.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Items&Addition");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		MessagesClass msg1 = new MessagesClass(Messages.GetOrderItems, agreeRestaurantrequset.idorder);
		ClientUI.chat.accept(msg1);

		listM = FXCollections.observableArrayList(ChatClient.AllOrderItem);/** array list of normal users */
		item.setCellValueFactory(new PropertyValueFactory<ItemAddition, String>("item"));
		addition.setCellValueFactory(new PropertyValueFactory<ItemAddition, String>("addition"));
		itemsadditiontable.setItems(listM);
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

	private MimeMessage draftEmail() throws AddressException, MessagingException, IOException {
		String emailReceipients = ChatClient.str; // Enter list of email
		String emailSubject = "Bite Me Admins";
		String emailBody = "We confired Your Order";
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

	 void sendToClientEmail() {

		setupServerProperties();
		try {
			draftEmail();
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
	@FXML
	void closebt(ActionEvent event) {
		sendToClientEmail();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
