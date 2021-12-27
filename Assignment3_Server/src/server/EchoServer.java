package server;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientController;
import common.BussinessUser;
import common.Delivery;
import common.GroupDelivery;
import common.Messages;
import common.MessagesClass;
import common.Normal;
import common.Order;
import common.RestaurantManager;
import common.Resturaunt;
import common.User;
import common.Visa;
import common.W4CNormal;
import common.clientDetails;
import common.clientsInfo;
import gui.SharedDeliveryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	static W4CNormal w4c = null;
	static ArrayList<clientsInfo> clientsGroup = new ArrayList<>();
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	public void clientConnected(ConnectionToClient client) {
		System.out.println("->Client Connected");
		try {

			UpdateClient(client.getInetAddress().getLocalHost(), client.getInetAddress().getHostName(), "Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client DisConnected");
		try {

			UpdateClient(client.getInetAddress().getLocalHost(), client.getInetAddress().getHostName(), "Disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UpdateClient(InetAddress HostName, String IP, String Status) {
		ServerUI.aFrame.UpdateClient(HostName, IP, Status);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		// System.out.println("Message received: " + ((MessagesClass) msg).getMsgType()
		// + " from " + client);
		MessagesClass message = (MessagesClass) msg;
		User user = null;
		User user1 = null;
		int month;
		int year;
		RestaurantManager manager;
		int ResturantID;
		Visa visa = null;

		switch (message.getMsgType()) {
		//// general//////
		case Login:
			user = (User) message.getMsgData();
			try {
				if (mysqlConnection.LogInChecker(user) != null) {
					message = new MessagesClass(Messages.loginSucceeded, mysqlConnection.LogInChecker(user));
					if (!mysqlConnection.LogInChecker(user).getStatus().equals("Locked")) {
						mysqlConnection.updateClientStatus(user, 1);
					}

					try {
						client.sendToClient(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {

					message = new MessagesClass(Messages.loginerror, null);
					try {
						client.sendToClient(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
			break;
		case GetNormalUser:
			Normal nuser = null;
			try {
				nuser = mysqlConnection.getNormalUser((User) message.getMsgData());
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			message = new MessagesClass(Messages.GetNormalUser, nuser);
			try {
				client.sendToClient(message);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;

		case insidealldatafromBiteMeDB:
			ArrayList<User> userfromBM;

			message = new MessagesClass(Messages.GotBranchManager, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();

			}

			break;
		case GetBranchManager:
			user = (User) message.getMsgData();

			message = new MessagesClass(Messages.GotBranchManager, mysqlConnection.GetBranchManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case GetCEO:
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.GetCEO, mysqlConnection.CeoUser(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case GetHRManager:
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.GotHRManager, mysqlConnection.GetHRManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case updateStatus:
			// update the client status to 0 -> that mean he log out
			if ((User) message.getMsgData() != null)
				mysqlConnection.updateClientStatus((User) message.getMsgData(), 0);
			try {
				client.sendToClient(new MessagesClass(Messages.LogedOut, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case W4C:
			w4c = mysqlConnection.getW4C((User) message.getMsgData());
			try {
				client.sendToClient(new MessagesClass(Messages.W4C, w4c));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;

		case getallrestaurant:
			// update the client status to 0 -> that mean he log out
			mysqlConnection.getAllResturaunt();
			try {
				client.sendToClient(new MessagesClass(Messages.getallrestaurant, mysqlConnection.getAllresturaunt));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case updateStatusofusers:
			// update the client status to 0 -> that mean he log out
			User normal = (User) message.getMsgData();

			try {
				mysqlConnection.UpdateStatusOfUsers(normal);
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.updateStatusofusers, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case GotW4C:
			int num = mysqlConnection.IDForW4C();
			message = new MessagesClass(Messages.GotW4C, num);
			try {
				client.sendToClient(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;

		case updateandinsidebussinesstousers:
			User userb;
			userb = (User) message.getMsgData();
			if ((User) message.getMsgData() != null)
				try {
					mysqlConnection.BussinessAccountHasBeenAccepted(userb);
				} catch (SQLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			try {
				client.sendToClient(new MessagesClass(Messages.updateandinsidebussinesstousers, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case Disconnected:
			clientDisconnected(client);
			break;
		case clientConnected:
			clientConnected(client);

			break;

		case ClientMassage:
			message = new MessagesClass(Messages.ClientMassage, null);
			ServerPortFrameController.c = true;
			try {
				client.sendToClient(message);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			break;
		case GettempData:
			String company = (String) message.getMsgData();
			try {
				mysqlConnection.getTheRequestList(company);
				client.sendToClient(new MessagesClass(Messages.GettempData, mysqlConnection.requestsList));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// clientDisconnected(client);

			break;

		case deleteid:
			try {
				mysqlConnection.deleteId((String) message.getMsgData(), (String) message.getMsgData1());
				client.sendToClient(new MessagesClass(Messages.deleteid, null));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case CreateNewNormalAccountWithOutVisa:
			user = (User) message.getMsgData();
			Normal NorUser;
			NorUser = (Normal) message.getMsgData1();
			try {
				message = new MessagesClass(Messages.loginerror,
						mysqlConnection.InsertNewNormalAccountWithOutVisa(user, NorUser));
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			break;

		case CreateNewNormalAccountWithVisa:
			user = (User) message.getMsgData1();
			visa = (Visa) message.getMsgData();
			Normal NorUser1;
			NorUser1 = (Normal) message.getMsgData2();
			try {
				mysqlConnection.InsertNewNormalAccountWithVisa(visa, user, NorUser1);
				message = new MessagesClass(Messages.CreateNewNormalAccountWithVisa, null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getthenormalendifainedacoount:
			try {
				mysqlConnection.GetTheRequestNormalAccount();
				client.sendToClient(new MessagesClass(Messages.getthenormalendifainedacoount,
						mysqlConnection.NormalUsersNotAccepted));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case Createaccepttresturaunt:
			Resturaunt res = (Resturaunt) message.getMsgData();
			mysqlConnection.Create_acceptRestaurant(res);

			try {
				client.sendToClient(new MessagesClass(Messages.Createaccepttresturaunt, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case acceptnewnormaluser:
			user = (User) message.getMsgData();
			try {
				mysqlConnection.AcceptNewNormalUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.acceptnewnormaluser, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getallusers:
			try {
				mysqlConnection.getAllUsers();
				client.sendToClient(new MessagesClass(Messages.getallusers, mysqlConnection.getlistofnormalaccount));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// clientDisconnected(client);

			break;
		case GetbissnessUser:
			user = (User) message.getMsgData();
			if (mysqlConnection.GetBissnessUser(user) != null) {
				message = new MessagesClass(Messages.GetbissnessUser, mysqlConnection.GetBissnessUser(user));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				message = new MessagesClass(Messages.loginerror, null);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case RestaurantReport:
			month = message.getMonth();
			year = message.getYear();
			manager = (RestaurantManager) message.getMsgData();
			try {
				if (mysqlConnection.GetReportForRestaurant(manager, month, year) != null) {
					try {
						message = new MessagesClass(Messages.ReportList,
								mysqlConnection.GetReportForRestaurant(manager, month, year));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// mysqlConnection.updateClientStatus(normalUsers, 1);
					try {
						client.sendToClient(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					message = new MessagesClass(Messages.loginerror, null);
					try {
						client.sendToClient(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;
		case ReportForManager:
			month = message.getMonth();
			year = message.getYear();
			ResturantID = message.getResID();
			try {
				mysqlConnection.GetReportForManager(ResturantID, month, year);
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			message = new MessagesClass(Messages.ReportForManager, mysqlConnection.ReportListForManager);
			try {
				client.sendToClient(message);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;

		case getRestaurantManager:// get resm
			user = (User) message.getMsgData();
			message = new MessagesClass(Messages.getRestaurantManager, mysqlConnection.GetRestaurantManager(user));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case getRestaurantWorker:// get resm//////////////////////////////////////////////////////////////////
			user = (User) message.getMsgData();

			if (mysqlConnection.GetRestaurantWorker(user) != null) {
				message = new MessagesClass(Messages.getRestaurantWorker, mysqlConnection.GetRestaurantWorker(user));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				message = new MessagesClass(Messages.loginerror, null);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case getrestaurantname:
			int idrestaurant = (int) message.getMsgData();
			try {

				message = new MessagesClass(Messages.getrestaurantname,
						mysqlConnection.getrestaurantname(idrestaurant));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			break;

		case GetAllRestaurants:
			message = new MessagesClass(Messages.GetAllRestaurants, mysqlConnection.getAllResturaunts());
			try {
				client.sendToClient(message);
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;

		case QRcreateW4C:
			User user2 = (User) message.getMsgData();
			mysqlConnection.generate_qr(user2.getUserName(), String.valueOf(w4c.getCode()));
			message = new MessagesClass(Messages.QRcreateW4C, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getOrderID:
			int orderNum = mysqlConnection.getOrderID();
			message = new MessagesClass(Messages.getOrderID, orderNum);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getIND:
			int ind = mysqlConnection.getIND();
			message = new MessagesClass(Messages.getIND, ind);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case newOrder:
			mysqlConnection.newOrder((Order) message.getMsgData());
			message = new MessagesClass(Messages.newOrder, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case newDelivery:
			mysqlConnection.newDelivery((Delivery) message.getMsgData());
			message = new MessagesClass(Messages.newDelivery, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case soldItems:
			mysqlConnection.soldItems((Order) message.getMsgData());
			message = new MessagesClass(Messages.soldItems, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case updateW4CforBussiness:
			mysqlConnection.updateW4CforBussiness((BussinessUser) message.getMsgData());
			message = new MessagesClass(Messages.updateW4CforBussiness, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case partnersGroupNumber:

			mysqlConnection.InsertGroup((GroupDelivery) message.getMsgData());

			clientsGroup.add(new clientsInfo(client, ((GroupDelivery) message.getMsgData()).getGroupNum()));
			message = new MessagesClass(Messages.partnersGroupNumber, null);

			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case sharedGroup:
			try {
				client.sendToClient(new MessagesClass(Messages.error, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getGroupNumber:
			boolean exists = false;
			try {
				exists = mysqlConnection.GetGroupNumber((int) message.getMsgData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.getGroupNumber, exists);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case updateTable:
			sendToAllClients(new MessagesClass(Messages.joinGroup, (clientDetails) message.getMsgData()));
			// sendToAllClients(new MessagesClass(Messages.joinGroup, null));

			break;

		case closeGroupDelivery:
			sendToAllClients(new MessagesClass(Messages.closeGroupDelivery, (int) message.getMsgData()));
			break;

		case order_items_additions:
			mysqlConnection.order_items_additions(message.getMsgData());
			message = new MessagesClass(Messages.order_items_additions, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;

		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		mysqlConnection.connectToDB();
		mysqlConnection.connectToBiteMeDB();
		mysqlConnection.putdatafrombitemeDB();
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
