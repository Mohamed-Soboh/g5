// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import gui.AdditionsController;
import gui.DeliveryInfoController;
import gui.RestaurantController;
import gui.SharedDeliveryController;
import gui.ShowInfoOfOrderController;
import gui.TypeOfOrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	public static boolean awaitResponse = false;
	public static ArrayList<Resturaunt> restaurantList;
	public static User user;
	public static W4CNormal w4c;
	public static BranchManager branchManager;
	public static Normal normaluser;
	public static ArrayList<User> getlistofnormalaccount;
	public static ArrayList<RestaurantReport> arrList;
	public static ArrayList<Normal> ArrListToAcceptNewAccounts;
	public static BussinessUser Bussinessuser;
	public static int W4CC;
	// ArrListToAcceptNewAccounts
	public static RestaurantManager restaurantManager;
	public static boolean updateError = false;
	public static Resturaunt Resturaunt;
	public static ArrayList<Resturaunt> getallresturant;
	public static User userceo;

	public static HRUser HRmanager1;
	public static ArrayList<BussinessUser> bussinessUser;
	public static WorkerUser WorkerUser;
	public static String ErrorMessage;
	public static CEOuser GetCEO;
	public static int orderNum;
	public static int ind;
	public static boolean group_exist;
	public static ArrayList<String> clients;
	public static int numCode;
	private static URL location;
	private static ResourceBundle resources;
	public static ArrayList<clientDetails> listClients;
	public static int group_code;
	public static ArrayList<Order> groupOrder;
	/*
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */

	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;

		// System.out.println("--> handleMessageFromServer");

		MessagesClass message = (MessagesClass) msg;

		switch (message.getMsgType()) {

		case order_items_additions:
			break;

		case closeGroupDelivery:
			group_code = (int) message.getMsgData();
			System.out.println(group_code);
			if (user.getUserType().equals("Bussiness")) {
				System.out.println("i'm bussiness");
				if (Bussinessuser.getGroup_code() == group_code) {
					Bussinessuser.setLock(0);
					TypeOfOrderController.currentThread().notifyAll();
				}
			}
			break;
		case joinGroup:
			/*
			boolean exist=false;
			if (user.getUserType().equals("Bussiness")
					&& DeliveryInfoController.randGroup == ((clientDetails) message.getMsgData()).getCode()) {
				for (int i = 0; i < SharedDeliveryController.clients.size(); i++) {
					if (((clientDetails) message.getMsgData()).getUser().equals(SharedDeliveryController.clients.get(i).getUser()))
					{
						exist=true;
						break;
					}
				}
			}
			if(exist==false)
				SharedDeliveryController.clients.add((clientDetails) message.getMsgData());
			*/
			if (user.getUserType().equals("Bussiness")
					&& DeliveryInfoController.randGroup == ((clientDetails) message.getMsgData()).getCode()) {
				SharedDeliveryController.clients.add((clientDetails) message.getMsgData());
			}
			 
			break;
		case loginSucceeded:
			User ifCEOuser = (User) message.getMsgData();
			if (!ifCEOuser.getUserType().equals("CEO")) {
				user = (User) message.getMsgData();
			} else {
				// user = (User) message.getMsgData();
				userceo = (User) message.getMsgData();
			}
			break;

		case GetNormalUser:
			normaluser = (Normal) message.getMsgData();
			break;

		case getRestaurantWorker:
			WorkerUser = (WorkerUser) message.getMsgData();
			break;
		case GetbissnessUser:
			Bussinessuser = (BussinessUser) message.getMsgData();
			System.out.println(Bussinessuser);
			break;
		case loginerror:
			ErrorMessage = (String) message.getMsgData();
			break;
		case GetCEO:
			GetCEO = (CEOuser) message.getMsgData();
			break;
		case getRestaurantManager:

			restaurantManager = (RestaurantManager) message.getMsgData();
			break;
		case getrestaurantname:
			Resturaunt = (Resturaunt) message.getMsgData();
			break;

		case GotBranchManager:
			branchManager = (BranchManager) message.getMsgData();
			break;

		case GotHRManager:
			HRmanager1 = (HRUser) message.getMsgData();
			break;
		case ResturauntRequestSent:
			break;
		case GettempData:
			bussinessUser = (ArrayList<BussinessUser>) message.getMsgData();
			break;
		case getallrestaurant:
			getallresturant = (ArrayList<Resturaunt>) message.getMsgData();
			break;

		case updateStatusofusers:

			break;
		case GotW4C:
			W4CC = (Integer) message.getMsgData();

			break;
		case getallusers:
			getlistofnormalaccount = (ArrayList<User>) message.getMsgData();
			break;
		case ReportForManager:
			arrList = (ArrayList<RestaurantReport>) message.getMsgData();
			break;
		case ReportList:
			arrList = (ArrayList<RestaurantReport>) message.getMsgData();
			break;
		case forgetPassword:
			updateError = (boolean) message.getMsgData();
			break;
		case getthenormalendifainedacoount:
			ArrListToAcceptNewAccounts = (ArrayList<Normal>) message.getMsgData();
			break;

		case acceptnewnormaluser:
			break;

		case deleteid:
			break;
		case updateandinsidebussinesstousers:
			break;
		case Createaccepttresturaunt:
			break;
		case W4C:
			w4c = (W4CNormal) message.getMsgData();
			break;

		case GetAllRestaurants:
			restaurantList = new ArrayList<>();
			restaurantList = (ArrayList<Resturaunt>) message.getMsgData();
			break;

		case QRcreateW4C:

			break;

		case getOrderID:
			orderNum = (int) message.getMsgData();
			break;

		case getIND:
			ind = (int) message.getMsgData();
			break;

		case newOrder:
			break;

		case newDelivery:
			break;

		case soldItems:
			break;
		case updateW4CforBussiness:
			break;

		case partnersGroupNumber:

			break;

		case sharedGroup:
			break;
		case getGroupNumber:
			group_exist = (boolean) message.getMsgData();
			break;

		case updateTable:
			// clients = (ArrayList<clientDetails>) message.getMsgData();

			break;

		default:
			break;
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		awaitResponse = true;
		try {
			sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait for response
		while (awaitResponse) {
			try {

				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {

			sendToServer(new MessagesClass(Messages.Disconnected, null));

			closeConnection();

		} catch (IOException e) {
		}
		// System.exit(0);
	}
}
//End of ChatClient class
