package gui;

import java.io.IOException;  
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.text.ParagraphView;



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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReportListForRestaurantController implements Initializable {

	@FXML
    private TableView<RestaurantReport> ReportTbl;
    @FXML
    private TableColumn<RestaurantReport, Integer> ItemIDCLM;

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
    private Text totalwin;
    
    @FXML
    private Button Back;
    
    @FXML
    private Text TextID;
    
    @FXML
    private ComboBox<Integer> Year;

    @FXML
    private ComboBox<Integer> Month;

    @FXML
    private Button GetReport;

    @FXML
    private Text Errorinput;
    

    @FXML
    private Button Converter;
	ObservableList<RestaurantReport> listM;
    private boolean lock = true;

    @FXML
    void ConertBtn(ActionEvent event) throws IOException, DocumentException  {
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
        PdfWriter.getInstance(doc, new FileOutputStream(path+"\\RestaurantManagerReport.pdf"));
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
    void BackBt(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();	
		RestaurantManagerHomePage aFrame = new RestaurantManagerHomePage(); 
		Stage primaryStage1 = new Stage();
		try {
			aFrame.start(primaryStage1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TextID.setText("Report for restaurant "+ChatClient.Resturaunt.getResturaunt_Name());
		Month.getItems().removeAll(Month.getItems());
		Month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		Year.getItems().removeAll(Year.getItems());
		Year.getItems().addAll(2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014);
	}

	private void UpdateTable() {
		listM = FXCollections.observableArrayList(ChatClient.arrList);
		System.out.println(ChatClient.arrList);
		ItemIDCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("ItemID"));
		ItemNameCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, String>("ItemName"));
		QuantityCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("Quantity"));
		Price1CLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Float>("Price1"));
		SoldCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Integer>("Sold"));
		InComeCLM.setCellValueFactory(new PropertyValueFactory<RestaurantReport, Float>("InCome"));
		ReportTbl.setItems(listM);
	}


	public void start(Stage primaryStage11) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("/gui/ReportListForRestaurant.fxml"));
		Scene scene = new Scene(root1);
		primaryStage11.setTitle("Create new account");
		primaryStage11.setScene(scene);
		primaryStage11.show();
		
	}
	
    @FXML
    void GetReportBt(ActionEvent event) {
    	ReportTbl.getItems().clear();
		System.out.println(ChatClient.restaurantManager);

    	MessagesClass msg1 = new MessagesClass(Messages.RestaurantReport,ChatClient.restaurantManager,Month.getValue(),Year.getValue());
    	ClientUI.chat.accept(msg1);
    	UpdateTable();
		float total = 0;
		for(int i=0;i<ChatClient.arrList.size();i++) {
			total+=ChatClient.arrList.get(i).getInCome();
		}
		totalwin.setText("Our total win in this month is: "+total);
    }

}
