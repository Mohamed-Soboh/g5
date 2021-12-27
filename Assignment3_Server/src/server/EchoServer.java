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
import common.Addition;
import common.BussinessUser;
import common.Item;
import common.Messages;
import common.MessagesClass;
import common.Normal;
import common.RestaurantManager;
import common.Resturaunt;
import common.User;
import common.Visa;
import common.W4CNormal;
import common.WorkerUser;
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

		System.out.println("Message received: " + ((MessagesClass) msg).getMsgType() + " from " + client);
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
					if (!mysqlConnection.LogInChecker(user).getStatus().equals("Locked")){
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
		case GetallAvailableCompany:

			try {
				message = new MessagesClass(Messages.GetallAvailableCompany, mysqlConnection.GetallAvailableCompany());
			} catch (SQLException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			break;
		case GetAllOreders:
			try {
				message = new MessagesClass(Messages.GetAllOreders, mysqlConnection.GetAllOrder((int) message.getMsgData()));
			} catch (NumberFormatException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			} catch (SQLException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e13) {
				// TODO Auto-generated catch block
				e13.printStackTrace();
			}

			break;
		case AddNewUser:// add new user
		
			try {
				message = new MessagesClass(Messages.AddNewUser, mysqlConnection.AddNewUser((User) message.getMsgData(), message.getMsgData1(),
						(String) message.getW4c()));
			} catch (SQLException e12) {
				// TODO Auto-generated catch block
				e12.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			break;
		case AddNewUserwithvisa:
			try {
				message = new MessagesClass(Messages.AddNewUserwithvisa,mysqlConnection.AddNewUserwithvisa((User) message.getMsgData(),
						message.getMsgData1(),(Visa)message.getMsgData3(),(String) message.getW4c()));
			} catch (SQLException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e11) {
				// TODO Auto-generated catch block
				e11.printStackTrace();
			}

			break;
		case getCompanyList:/// take company list from musql
			try {
				message = new MessagesClass(Messages.getCompanyList, mysqlConnection.getCompanyList());
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CompanyConfirmed:
			String Cname1 = (String) message.getMsgData();
			try {
				mysqlConnection.companyConfirm(Cname1);
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

		case CompanyRequest:
			String Cname = (String) message.getMsgData();
			try {
				mysqlConnection.confirmCompane(Cname);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case CheckCompany:
			String Cname11 = (String) message.getMsgData();
			try {
				message = new MessagesClass(Messages.CheckCompany, mysqlConnection.companyChecker(Cname11));
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case GetAllUsersFromUsersTable://///// take all users

			try {
				message = new MessagesClass(Messages.GetAllUsersFromUsersTable,
						mysqlConnection.TakeAllUserThatNotConfiredyet());
			} catch (SQLException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e10) {
				// TODO Auto-generated catch block
				e10.printStackTrace();
			}
			break;
		case insidealldatafromBiteMeDB:
			ArrayList<User> userfromBM;
			try {
				mysqlConnection.insidealldatafromBiteMeDB();
			} catch (SQLException e9) {
				// TODO Auto-generated catch block
				e9.printStackTrace();
			}
			message = new MessagesClass(Messages.insidealldatafromBiteMeDB, null);

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
		case getnormaluser:

			message = new MessagesClass(Messages.getnormaluser,
					mysqlConnection.Getnormaluser((User) message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case updateStatus:
			// update the clieLoginnt status to 0 -> that mean he log out
			if ((User) message.getMsgData() != null)
				mysqlConnection.updateClientStatus((User) message.getMsgData(), (int) message.getMsgData1());
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
			String location = (String) message.getMsgData();
			mysqlConnection.getAllResturaunt(location);
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
		case GetAllitems:
			int resid1 = (int) message.getMsgData();
			try {
				client.sendToClient(new MessagesClass(Messages.GetAllitems, mysqlConnection.getallitems(resid1)));
			} catch (IOException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			} catch (SQLException e8) {
				// TODO Auto-generated catch block
				e8.printStackTrace();
			}

			break;
		case GetAllitemsfromitem:/// get main dish
			int resid = (int) message.getMsgData();
			String type = (String) message.getMsgData1();
			try {
				try {
					client.sendToClient(new MessagesClass(Messages.GetAllitemsfromitem,
							mysqlConnection.getallmaindish(resid, type)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e7) {
				// TODO Auto-generated catch block
				e7.printStackTrace();
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
		case CreateNewBussinessAccont:
			BussinessUser Account = (BussinessUser) message.getMsgData1();
			String str = (String) message.getW4c();
			user = (User) message.getMsgData();
			try {
				String s2 = mysqlConnection.InsertNewBussinessAccount(user, Account, str);
				message = new MessagesClass(Messages.loginerror, s2);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case CreateNewBussinessAccontWithVisa:
			BussinessUser Account1 = (BussinessUser) message.getMsgData3();
			String str1 = (String) message.getW4c();
			user = (User) message.getMsgData1();
			visa = (Visa) message.getMsgData();
			try {
				String s2 = mysqlConnection.InsertNewBussinessAccountWithVisa(visa, user, Account1, str1);
				message = new MessagesClass(Messages.loginerror, s2);

			} catch (SQLException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}

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
				
			try {
				client.sendToClient(new MessagesClass(Messages.Createaccepttresturaunt,
						mysqlConnection.Create_acceptRestaurant(res)));
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			} catch (SQLException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
					
			break;
		case GetAllWorker:
			int RestaurantId = (int) message.getMsgData();

			try {
				client.sendToClient(
						new MessagesClass(Messages.GetAllWorker, mysqlConnection.GetAllWorkers(RestaurantId)));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case AddNewWorker://///// add new worker in worker table and user
			WorkerUser WorkerUser = (WorkerUser) message.getMsgData();
			try {
				try {
					client.sendToClient(
							new MessagesClass(Messages.AddNewWorker, mysqlConnection.AddNewWorker(WorkerUser)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;

		case deleteworker://// removed worker from user and workerstable
			WorkerUser WorkerUser1 = (WorkerUser) message.getMsgData();
			try {
				try {
					mysqlConnection.RemoveWorker(WorkerUser1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.sendToClient(new MessagesClass(Messages.AddNewWorker, null));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			break;
		case editworkers:///// edit worker

			WorkerUser WorkerUser2 = (WorkerUser) message.getMsgData();
			try {
				mysqlConnection.EditWorker(WorkerUser2);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.editworkers, null));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
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
		case GetOrderItems:
			try {
				message = new MessagesClass(Messages.GetOrderItems,mysqlConnection.GetallOrederItems((int)message.getMsgData()));
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			break;
			
		case DeleteOrder:
			mysqlConnection.deleteOrder((int)message.getMsgData());
			
				message = new MessagesClass(Messages.DeleteOrder,null);
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			
		case CheckDelivery:
			message = new MessagesClass(Messages.CheckDelivery,mysqlConnection.CheckDelivery((int)message.getMsgData()));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case getuser:
			try {
				message = new MessagesClass(Messages.getuser,mysqlConnection.getuser((String)message.getMsgData()));
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				client.sendToClient(message);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
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
		case UpdateItem:
			try {
				mysqlConnection.UpdateItem((Item) message.getMsgData(), (String) message.getMsgData1());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.UpdateItem, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case RemoveItem:
			try {
				mysqlConnection.RemoveItem((Item) message.getMsgData());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.RemoveItem, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case RemoveItemAddition:
			try {
				mysqlConnection.RemoveItemAddition((Item) message.getMsgData(), (String) message.getMsgData1());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // remove addition ftom item_addition and addition
			message = new MessagesClass(Messages.RemoveItemAddition, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case AddItems:
			try {
				System.out.println((String) message.getW4c() + "im heree in additems");
				mysqlConnection.AddItems((Item) message.getMsgData(), (Addition) message.getMsgData1(),
						(int) message.getMsgData3(), (String) message.getW4c());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = new MessagesClass(Messages.AddItems, null);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

		System.out.println("Server listening for connections on port " + getPort());
	}

	public static void importdata() {
		try {
			mysqlConnection.insidealldatafromBiteMeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
