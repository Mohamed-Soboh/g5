package gui;

import java.io.IOException;
import java.util.Properties;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentSuccessfulController {
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
		String[] emailReceipients = { "bgshshv7@gmail.com" }; // Enter list of email
																								// recepients
		String emailSubject = "Bite Me Admins";
		String emailBody = "*******************";
		mimeMessage = new MimeMessage(newSession);

		for (int i = 0; i < emailReceipients.length; i++) {
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
		}
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
	void BackToHomePage(ActionEvent event) throws Exception {
		if(ChatClient.user.getUserType().equals("Normal"))
		{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			NormalUserHomePageController NUHPC = new NormalUserHomePageController();
			NUHPC.start(primaryStage);
		}
		
		else if(ChatClient.user.getUserType().equals("Bussiness"))
		{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			bussinessForm BF = new bussinessForm();
			BF.start(primaryStage);
		}
	}

	public void start(Stage primaryStage) throws Exception {
		sendToClientEmail();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/PaymentSuccessfulFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("normal user");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
