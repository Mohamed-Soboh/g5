package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Company;
import common.Messages;
import common.MessagesClass;
import common.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfirmCompanyController implements Initializable {
	/**
	 * this observable list used to table view
	 */
	ObservableList<Company> listM;
	/**
	 * com in parameter of company class
	 */
	Company com;
	private URL location;
	private ResourceBundle resources;
	/**
	 * table view table
	 */
	@FXML
    private TableView<Company> Table;
	 /**
	 * path text
	 */
	@FXML
	    private Text pathtext;
    /**
     * Columns in table view
     */
    @FXML
    private TableColumn<Company, String> NameClm;

    @FXML
    private Label title;

    /**
     * @param event of button action that send to server to confirm the company that i clicked 
     */
    @FXML
    void confirmBtn(ActionEvent event) {
    	ClientUI.chat.accept(new MessagesClass(Messages.CompanyConfirmed, com.getCompanyname()));
    	Table.getItems().clear();
    	initialize(location, resources);
    }
    
    /**
     * @param event back action button return back to BranchManagerHomePageController 
     */
    @FXML
    void backBtn(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		BranchManagerHomePageController aFrame = new BranchManagerHomePageController();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);/**back to branch manager home page*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @param primaryStage start method
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/ConfirmCompany.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("FirstPage");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ClientUI.chat.accept(new MessagesClass(Messages.getCompanyList, null));
		listM=FXCollections.observableArrayList(ChatClient.CompanyList);/** put company that not confirmed yet in table*/
		NameClm.setCellValueFactory(new PropertyValueFactory<Company, String>("companyname"));
 		Table.setItems(listM);	
 		settext();
	}
	
	/**
	 * update table  
	 */
	public void Updatetable() {
		pathtext.setText("BranchManagerHomePageController->ConfirmCompany");
		MessagesClass msg1 = new MessagesClass(Messages.getCompanyList, null);
		ClientUI.chat.accept(msg1);
		listM=FXCollections.observableArrayList(ChatClient.CompanyList);
		NameClm.setCellValueFactory(new PropertyValueFactory<Company, String>("companyname"));
 		Table.setItems(listM);	
 		settext();
	}
	
	/**
	 * this mouse method when i clicked in company from table get all the information about it
	 */
	private void settext()
	{
		Table.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				com=Table.getItems().get(Table.getSelectionModel().getSelectedIndex());	
				
			}
			
		});
		
		
	}

}

