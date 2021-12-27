package gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Messages;
import common.MessagesClass;
import common.RestaurantReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportListForManagerController implements Initializable{

	  @FXML
	    private TableView<RestaurantReport> ReportTbl;

	    @FXML
	    private TableColumn<RestaurantReport, Integer> ItemCLM;

	    @FXML
	    private TableColumn<RestaurantReport, String> ItemNameCLM;

	    @FXML
	    private TableColumn<RestaurantReport, Integer> QuantityCLM;

	    @FXML
	    private TableColumn<RestaurantReport, Float> Price1CLM;

	    @FXML
	    private TableColumn<RestaurantReport, Integer> SoldCLM;

	    @FXML
	    private TableColumn<RestaurantReport, Float> InComeCLM;

	    @FXML
	    private Button Converter;

	    @FXML
	    private TextField RestaurantID;

	    @FXML
	    private ComboBox<Integer> Year;

	    @FXML
	    private ComboBox<String> Months;

	    @FXML
	    private Button GetReport;

	    @FXML
	    private Button Back;

	    @FXML
	    private Text repot;
	    ObservableList<RestaurantReport> listM;

	    //ClientController chat = new ClientController(FirstPageController.ip, 5555);

    @FXML
    void ConertBtn(ActionEvent event) throws FileNotFoundException, DocumentException {
    	String path="";//C:\\Users\\moham\\g5-project\\
    	JFileChooser j=new JFileChooser();
    	j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int s=	j.showSaveDialog(j);
       if(s==JFileChooser.APPROVE_OPTION)
       {
    	   path=j.getSelectedFile().getPath();
    	   System.out.println(path);

    	   
       }
        Document doc=new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(path+"\\BranchManage_RestaurantReport.pdf"));
        doc.open();
        PdfPTable tablepdf=new PdfPTable(6);
        
        tablepdf.addCell("ItemID");
        tablepdf.addCell("Item Name");
        tablepdf.addCell("Quantity");
        tablepdf.addCell("Price");
        tablepdf.addCell("Sold");
        tablepdf.addCell("Income");

System.out.println(ReportTbl.getItems().get(0).getInCome());
System.out.println(ReportTbl.getItems().size());


        for(int i=0;i<ReportTbl.getItems().size();i++)
        {
        	String Id=String.valueOf(ReportTbl.getItems().get(i).getItemID());
        	String name=ReportTbl.getItems().get(i).getItemName();
        	String Quantity=String.valueOf(ReportTbl.getItems().get(i).getQuantity());
        	String price=String.valueOf(ReportTbl.getItems().get(i).getPrice1());
        	String sold=String.valueOf(ReportTbl.getItems().get(i).getSold());
        	String income=String.valueOf(ReportTbl.getItems().get(i).getInCome());


        	tablepdf.addCell(Id);
        	tablepdf.addCell(name);
        	tablepdf.addCell(Quantity);
        	tablepdf.addCell(price);
        	tablepdf.addCell(sold);
        	tablepdf.addCell(income);
        	
        	
        }
 
        doc.add(tablepdf);
        doc.close();
    }

    @FXML
    void GetReport(ActionEvent event) {
    	ReportTbl.getItems().clear();
		int ID = Integer.parseInt(RestaurantID.getText());
		int year = Year.getValue();
    	switch (Months.getValue()) {
    	case "1,2,3":
    		MessagesClass msg1 = new MessagesClass(Messages.ReportForManager,ID,1,year);
    		ClientUI.chat.accept(msg1);
    		UpdateTable();
    		break;
    		
    	case "4,5,6":
    		MessagesClass msg2 = new MessagesClass(Messages.ReportForManager,ID,4,year);
    		ClientUI.chat.accept(msg2);
    		UpdateTable();
    		break;
    		
    	case "7,8,9":
    		MessagesClass msg3 = new MessagesClass(Messages.ReportForManager,ID,7,year);
    		ClientUI.chat.accept(msg3);
    		UpdateTable();
    		break;
    		
    	case "10,11,12":
    		MessagesClass msg4 = new MessagesClass(Messages.ReportForManager,ID,10,year);
    		ClientUI.chat.accept(msg4);
    		UpdateTable();
    		UpdateTable();
    		break;
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		repot.setText("Report for "+BranchManagerHomePageController.getBranchManager().getLocation()+" restaurants. ");

		Months.getItems().removeAll(Months.getItems());
		Months.getItems().addAll("1,2,3","4,5,6","7,8,9","10,11,12");
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014);
	}
	
	private void UpdateTable() {
 		listM = FXCollections.observableArrayList(ChatClient.arrList);
		ItemCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("ItemID"));
		ItemNameCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, String>("ItemName"));
		QuantityCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("Quantity"));
		Price1CLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Float>("Price1"));
		SoldCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("Sold"));
		InComeCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Float>("InCome"));
		ReportTbl.setItems(listM);
	}
	
    @FXML
    void BackBt(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		BranchManagerHomePageController aFrame = new BranchManagerHomePageController(); 
		Stage primaryStage1 = new Stage();
		try {
			aFrame.start(primaryStage1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public void start(Stage primaryStage11) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/ReportListForManager.fxml"));
		Scene scene = new Scene(root1);
		primaryStage11.setTitle("Create new account");
		primaryStage11.setScene(scene);
		primaryStage11.show();
		
	}
}
