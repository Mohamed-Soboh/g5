package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PaymentNormalUserController implements Initializable {

	@FXML
	private TextField w4cnumtxt;

	@FXML
	private TextField employertxt;

	@FXML
	private Text errtxt;

	@FXML
	void BackFunction(ActionEvent event) throws Exception {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		PickUpFormController PUFC = new PickUpFormController();
		PUFC.start(primaryStage);
	}

	@FXML
	void SubmitFunction(ActionEvent event) throws Exception {
		if (String.valueOf(w4cnumtxt.getText()).equals("")) 
		{
			errtxt.setText("Please enter code");
			return;
		}
		if (!Integer.valueOf(w4cnumtxt.getText()).equals(ChatClient.w4c.getCode())) {
			errtxt.setText("Wrong W4C code");
			return;
		}
		// need to check if employer exists
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		ShowInfoOfOrderController SIOO = new ShowInfoOfOrderController();
		SIOO.start(primaryStage);
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		return qrCodeResult.getText();
	}

	private String getFilePath() {
		String path = "";
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int s = j.showSaveDialog(j);
		if (s == JFileChooser.APPROVE_OPTION) {
			path = j.getSelectedFile().getPath();
			return path;
		}
		return "";
	}

	@FXML
	void QR_Import(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.hide();
		String filePath = getFilePath();
		stage.show();
		if (filePath.equals("")) {
			return;
		} else {
			String charset = "UTF-8";
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			try {
				String code = readQRCode(filePath, charset, hintMap);
				w4cnumtxt.setText(code);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/PaymentNormalUserFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Pick up info page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!ChatClient.user.getUserType().equals("Bussiness"))
			employertxt.setVisible(false);
	}
}
