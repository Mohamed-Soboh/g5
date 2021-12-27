package server;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mysql.cj.conf.ConnectionUrl.Type;

import javafx.fxml.Initializable;
import common.Addition;
import common.BranchManager;
import common.CEOuser;
import common.Category;
import common.Delivery;
import common.GroupDelivery;
import common.HRUser;
import common.Item;
import common.Menu;
import common.Normal;
import common.Order;
import common.RestaurantManager;
import common.RestaurantReport;
import common.Resturaunt;
import common.User;
import common.Visa;
import common.W4CBussiness;
import common.W4CNormal;
import common.BussinessUser;
import common.WorkerUser;

public class mysqlConnection implements Initializable {
	public static User user;
	public static User user1 = null;
	public static ArrayList<String> list;
	public static ArrayList<BussinessUser> requestsList;
	public static ArrayList<User> getlistofnormalaccount;
	public static ArrayList<User> usersfromBiteMeDB;

	public static ArrayList<Resturaunt> getAllresturaunt;
	public static RestaurantManager restaurantManager;
	public static WorkerUser WorkerUser;
	public static BranchManager branchManager;
	public static HRUser HRManager;
	public static ArrayList<RestaurantReport> ReportList;
	public static ArrayList<RestaurantReport> ReportListForManager;
	public static Connection conn;
	public static Resturaunt Resturaunt;
	public static ArrayList<Normal> NormalUsersNotAccepted;
	public static BussinessUser BussinessUser;
	public static CEOuser CEOuser1;

	@SuppressWarnings("deprecation")
	public static Connection connectToDB() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			String s1 = "jdbc:mysql://localhost/assignment3?serverTimezone=IST";
			String s2 = "root";
			String s3 = "Aa123456";
			conn = DriverManager.getConnection(s1, s2, s3);
			System.out.println("SQL connection succeed");
			return conn;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}

	public static Connection connectToBiteMeDB() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
//		"jdbc:mysql://localhost/assigment2?serverTimezone=IST", "root","912000bashar"
		try {
			String s1 = "jdbc:mysql://localhost/biteme_data?serverTimezone=IST";
			String s2 = "root";
			String s3 = "Aa123456";
			conn = DriverManager.getConnection(s1, s2, s3);
			System.out.println("SQL connection succeed To BiteMe DB");
			return conn;
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}

	public static void updateW4CforBussiness(BussinessUser user) {
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement("UPDATE assignment3.w4cbussiness SET money= ? WHERE IDuser = ?");
			ps.setDouble(1, user.getW4c().getMoney());
			ps.setString(2, user.getID());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean GetGroupNumber(int number) throws SQLException {
		ResultSet rs = null;
		String query = "select * from assignment3.group_delivery where code=" + number;
		Statement st;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs.next();
	}

	public static void InsertGroup(GroupDelivery gp) {
		String sql = "insert into assignment3.group_delivery (code,group_size) values (?,?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, gp.getGroupNum());
			preparedStmt.setInt(2, gp.getGroupSize());
			preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void soldItems(Order solditems) {
		Statement st;
		ResultSet rs = null;
		String sql;
		int soldupdate = 0;
		int updatesold = 0;
		for (int i = 0; i < solditems.getItems().size(); i++) {
			String query2 = "select quantity from assignment3.menu where IDRestaurant="
					+ solditems.getRes().getResturauntID() + " and Item_ID=" + solditems.getItems().get(i).getItem_ID();
			try {
				st = conn.createStatement();
				rs = st.executeQuery(query2);
				if (rs.next() == true) {
					updatesold = rs.getInt(1);
					updatesold -= solditems.getItems().get(i).getQuantity();
					sql = "UPDATE assignment3.menu SET quantity= ? where IDRestaurant="
							+ solditems.getRes().getResturauntID() + " and Item_ID="
							+ solditems.getItems().get(i).getItem_ID();
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, updatesold);
					preparedStmt.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String query = "select Sold from assignment3.solditem where IDRestaurant="
					+ solditems.getRes().getResturauntID() + " and ItemID=" + solditems.getItems().get(i).getItem_ID()
					+ " and Month=" + solditems.getMonth() + " and Year=" + solditems.getYear();
			try {
				st = conn.createStatement();
				rs = st.executeQuery(query);
				if (rs.next() == true) {
					soldupdate = rs.getInt(1);
					soldupdate += solditems.getItems().get(i).getQuantity();
					sql = "UPDATE assignment3.solditem SET Sold= ? where IDRestaurant="
							+ solditems.getRes().getResturauntID() + " and ItemID="
							+ solditems.getItems().get(i).getItem_ID() + " and Month=" + solditems.getMonth()
							+ " and Year=" + solditems.getYear();
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, soldupdate);
					preparedStmt.execute();

				} else {
					sql = "insert into assignment3.solditem (IDRestaurant,ItemID,Sold,Month,Year) values (?,?,?,?,?)";
					PreparedStatement preparedStmt = conn.prepareStatement(sql);
					preparedStmt.setInt(1, solditems.getRes().getResturauntID());
					preparedStmt.setInt(2, solditems.getItems().get(i).getItem_ID());
					preparedStmt.setInt(3, solditems.getItems().get(i).getQuantity());
					preparedStmt.setInt(4, solditems.getMonth());
					preparedStmt.setInt(5, solditems.getYear());
					preparedStmt.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void newDelivery(Delivery del) {
		String query = " insert into assignment3.delivery (deliveryNum,orderNum,name,phonenumber,address,deliveryType,deliveryDate,clientTxt)"
				+ " values (?, ?, ?, ?, ?, ?,?,?)";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, del.getDeliveryNum());
			preparedStmt.setInt(2, del.getOrderNum());
			preparedStmt.setString(3, del.getFirstname());
			preparedStmt.setString(4, del.getPhonenumber());
			preparedStmt.setString(5, del.getAddress());
			preparedStmt.setString(6, del.getDeliveryType());
			preparedStmt.setString(7, del.getDate() + " " + del.getHour() + ":" + del.getMinute());
			preparedStmt.setString(8, del.getClientText());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void newOrder(Order order) {
		String query = " insert into assignment3.order (orderNum,RestaurantID,userID,submitDate,totalPrice,pickupTime)"
				+ " values (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, order.getOrderNum());
			preparedStmt.setInt(2, order.getRes().getResturauntID());
			preparedStmt.setString(3, order.getUser().getID());
			preparedStmt.setString(4, order.getCurrentDateAndTime());
			preparedStmt.setDouble(5, order.getTotalPrice());
			preparedStmt.setString(6, order.getDate() + " " + order.getHour() + ":" + order.getMinute());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "insert into assignment3.order_items (orderNum,item_ID,quantity) values(?,?,?)";
		try {
			for (int i = 0; i < order.getItems().size(); i++) {
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, order.getOrderNum());
				preparedStmt.setInt(2, order.getItems().get(i).getItem_ID());
				preparedStmt.setInt(3, order.getItems().get(i).getQuantity());
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void order_items_additions(Object itemAddition) {
		ArrayList<Item> item_addition = (ArrayList<Item>) itemAddition;
		String query = "insert into assignment3.order_item_addition (orderNum,item_ID,name) values (?,?,?)";
		/*
		for (Item i : item_addition) {
			for (Addition a : i.getAdditions()) {
				try {
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, i.getOrderNum());
					preparedStmt.setInt(2, i.getItem_ID());
					preparedStmt.setString(3, a.getName());
					preparedStmt.setInt(4, i.getIndex());
					preparedStmt.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		for (Item i : item_addition) {
			try {
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, i.getOrderNum());
				preparedStmt.setInt(2, i.getItem_ID());
				preparedStmt.setString(3, i.getAdditions_names());
				//preparedStmt.setInt(4, i.getIndex());
				preparedStmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getOrderID() {
		Statement st;
		ResultSet rs = null;
		int orderNum = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(*) FROM assignment3.order");
			if (rs.next()) {
				orderNum = rs.getInt(1);
				if (orderNum == 0)
					return orderNum;
			}
			rs = st.executeQuery("SELECT * FROM assignment3.order ORDER BY orderNum DESC LIMIT 1");
			if (rs.next()) {
				orderNum = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return orderNum;
	}
	
	public static int getIND() {
		Statement st;
		ResultSet rs = null;
		int ind = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(*) FROM assignment3.order_item_addition");
			if (rs.next()) {
				ind = rs.getInt(1);
				if (ind == 0)
					return ind;
			}
			rs = st.executeQuery("SELECT * FROM assignment3.order_item_addition ORDER BY ind DESC LIMIT 1");
			if (rs.next()) {
				ind = rs.getInt(4);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ind;
	}

	public static Normal getNormalUser(User user) throws SQLException {
		Normal nuser = null;

		Statement st;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("Select * from assignment3.normaluser where ID=" + user.getID());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (rs.next()) {
			try {
				nuser = new Normal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nuser;

	}

	public static void LogOutAllAccounts() throws SQLException {
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? ");
		ps.setInt(1, 0);
		ps.executeUpdate();
	}

	public static void UpdateStatusOfUsers(User user1) throws SQLException {
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.users SET status= ? WHERE ID = ?");
		ps.setString(1, user1.getStatus());
		ps.setString(2, user1.getID());
		ps.executeUpdate();

	}

	public static void BussinessAccountHasBeenAccepted(User user) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, "Active");
		ps.setString(3, user.getID());
		ps.executeUpdate();
	}

	static int IDForW4C() {
		Random rand = new Random();
		Statement stmt, stmt1;
		try {
			while (true) {
				int W4C = rand.nextInt((999 - 100) + 1) + 100;
				stmt = conn.createStatement();
				stmt1 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cnormal where code= '" + W4C + "'");
				if (!rs.next()) {
					ResultSet rs1 = stmt
							.executeQuery("SELECT * FROM assignment3.w4cbussiness where code= '" + W4C + "'");
					if (!rs1.next()) {
						System.out.println(W4C);
						return W4C;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 5;
	}

	static User LogInChecker(User username) throws SQLException {
		Statement stmt, stmt1;

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.users where UserName='" + username.getUserName()
				+ "' and Password='" + username.getPassword() + "'");
		if (rs.next() == true) {
			User user = new User(rs.getString(1), username.getUserName(), rs.getString(3), rs.getString(4),
					Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
			if (user.getIsLoggedIn() == 1) {
				return null;
			} else
				return user;
		} else {

			return null;
		}
	}

	public static String InsertNewBussinessAccount(User user, BussinessUser bussinessUser) throws SQLException {
		System.out.println("i am getting into login query");
		try {
			String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getUserName());
			preparedStmt.setString(3, user.getPassword());
			preparedStmt.setString(4, user.getUserType());
			preparedStmt.setInt(5, user.getIsLoggedIn());
			preparedStmt.setInt(6, user.getConfirm());
			preparedStmt.setString(7, user.getStatus());
			preparedStmt.execute();

			String query1 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,W4C,Company)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, bussinessUser.getID());
			preparedStmt1.setString(2, bussinessUser.getFirstName());
			preparedStmt1.setString(3, bussinessUser.getLastName());
			preparedStmt1.setString(4, bussinessUser.getPhoneNumber());
			preparedStmt1.setString(5, bussinessUser.getEmail());
			preparedStmt1.setFloat(6, bussinessUser.getW4C());
			preparedStmt1.setString(7, bussinessUser.getCompany());
			preparedStmt1.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	static User selectuserfromNormalUserTable(User username) {// check if the password and the user name is correct , if
		// yes then send the
// right message

		Statement stmt;
		int status;
		boolean valid = false;
		String usernameStatus;
		String Status;
		System.out.println("i am getting into login query");
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + username.getID() + "'");
			if (rs.next() == true) {
				Status = rs.getString(10);
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));

				usernameStatus = username.getUserName();
				status = rs.getInt("IsLoggedIn");
			}
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void AcceptNewNormalUser(User username) throws SQLException {
		System.out.println(username.getID());
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, "Active");
		ps.setString(3, username.getID());
		ps.executeUpdate();
	}

	public static void updateClientStatus(User user, int status) {
		System.out.println("user status updated ...." + user.getUserName());
		PreparedStatement ps;
		try {
			System.out.print(user);
			ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? WHERE UserName = ?");
			ps.setInt(1, status);
			ps.setString(2, user.getUserName());
			ps.executeUpdate();
			if (ps.executeUpdate() == 0) {
				System.out.println("Table Update Error!");
			} else {
				System.out.println("update " + user.getUserName() + " status to:" + status);
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void deleteId(String id, String sqltable) throws SQLException {
		System.out.println(id + "111111");
		System.out.println(sqltable + "222222222");
		PreparedStatement st, st1, st2 = null;
		if (sqltable.equals("normaluser")) {
			st = conn.prepareStatement("DELETE FROM assignment3.normaluser WHERE ID = ?");
			st.setString(1, id);
			st.executeUpdate();
			st1 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
			st1.setString(1, id);
			st1.executeUpdate();
		}
		if (sqltable.equals("restaurant")) {
			st = conn.prepareStatement("DELETE FROM assignment3.restaurant WHERE IDRestaurant = ?");
			st.setInt(1, Integer.parseInt(id));
			st.executeUpdate();
		} else
			st = conn.prepareStatement("DELETE FROM assignment3.bussinessuser WHERE ID = ?");
		st.setString(1, id);
		st.executeUpdate();
		st2 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
		st2.setString(1, id);
		st2.executeUpdate();

	}

	static boolean getTheRequestList(String company) {
		requestsList = new ArrayList<BussinessUser>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * From assignment3.users where UserType='Bussiness' and confirm=0");
			BussinessUser request;
			while (RS.next()) {
//				request = new BussinessUser(RS.getString(1), RS.getString(2), RS.getString(3), RS.getString(4),
//						RS.getString(5), Float.parseFloat(RS.getString(6)), RS.getString(7), 0);
				String id = RS.getString(1);
				ResultSet RS1 = ps1.executeQuery(
						"SELECT * From assignment3.bussinessuser where ID='" + id + "'and Company='" + company + "'");
				if (RS1.next()) {
					request = new BussinessUser(RS1.getString(1), RS1.getString(2), RS1.getString(3), RS1.getString(4),
							RS1.getString(5), Float.parseFloat(RS1.getString(6)), RS1.getString(7), 0);
					requestsList.add(request);

				}
			}
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	static boolean getAllUsers() {
		getlistofnormalaccount = new ArrayList<User>();
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from assignment3.users where UserType= 'Normal' and confirm = 1");
			User request;
			while (rs.next()) {
				request = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
				getlistofnormalaccount.add(request);
				System.out.print(getlistofnormalaccount);
			}
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	static void putdatafrombitemeDB() {////////////////////////////////////////////////////////////////////////// WTF
		usersfromBiteMeDB = new ArrayList<User>();
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from biteme_data.bitemeuser");
			User request;
			while (rs.next()) {
				request = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
				usersfromBiteMeDB.add(request);
			}
			System.out.print(usersfromBiteMeDB);

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void isidedatafrombmtosassignment(ArrayList<User> user) {
		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * from biteme_data.bitemeuser");
			User request;
			while (rs.next()) {
				request = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)), rs.getString(7));
				usersfromBiteMeDB.add(request);
			}
			System.out.print(usersfromBiteMeDB);

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void getAllResturaunt() {
		getAllresturaunt = new ArrayList<Resturaunt>();
		try {
			Statement ps = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * from assignment3.restaurant");

			Resturaunt request;
			while (RS.next()) {
				request = new Resturaunt(Integer.parseInt(RS.getString(1)), RS.getString(2), RS.getString(3));
				getAllresturaunt.add(request);

			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<RestaurantReport> GetReportForManager(int restaurantID, int month, int year)
			throws SQLException {
		ReportListForManager = new ArrayList<RestaurantReport>();
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;
		int month2 = month + 2;
		int ItemID;
		int sold;
		int quantity1 = 0;
		RestaurantReport rsp;
		System.out.println("Getting Report List from DB");
		try {
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmt4 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='" + restaurantID
					+ "' and Month>='" + month + "' and Month <='" + month2 + "' and Year='" + year + "'");
			System.out.println("dd");
			while (rs2.next() == true) {
				System.out.println("jj");
				ItemID = Integer.parseInt(rs2.getString(2));
				sold = Integer.parseInt(rs2.getString(3));
				ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");
				System.out.println(ItemID);
				System.out.println(restaurantID);
				ResultSet rs4 = stmt4.executeQuery("SELECT * FROM assignment3.menu where IDRestaurant='" + restaurantID
						+ "' and Item_ID= '" + ItemID + "'");
				if (rs4.next() == true) {
					quantity1 = Integer.parseInt(rs4.getString(3));
					System.out.println(quantity1);
				}
				while (rs3.next() == true) {
					System.out.println("tat");
					System.out.println("Babi");
					rsp = new RestaurantReport(ItemID, rs3.getString(2), quantity1, Float.parseFloat(rs3.getString(3)),
							sold, sold * Float.parseFloat(rs3.getString(3)));
					System.out.println("add");
					ReportListForManager.add(rsp);
					System.out.println("ZZZZ");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(ReportListForManager);

		return ReportListForManager;
	}

	static RestaurantManager GetRestaurantManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM restaurantmanager where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				System.out.println("I'm getting RestaurantManager from DB");
				restaurantManager = new RestaurantManager(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)));
				System.out.println(restaurantManager);

				return restaurantManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	static WorkerUser GetRestaurantWorker(User user) {///////////////////////////////////////////
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.restaurantworker where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				System.out.println("I'm getting RestaurantManager from DB");
				WorkerUser = new WorkerUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), Integer.parseInt(rs.getString(6)));
				return WorkerUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	static BranchManager GetBranchManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branchmanager where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				branchManager = new BranchManager(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				return branchManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	static CEOuser CeoUser(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ceouser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				CEOuser1 = new CEOuser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				return CEOuser1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	public static W4CBussiness getW4CBussiness(User user) {
		W4CBussiness w4c = null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.w4cbussiness where IDuser=" + user.getID());
			if (rs.next()) {
				w4c = new W4CBussiness(rs.getInt(1), user, rs.getDouble(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return w4c;
	}

	static BussinessUser GetBissnessUser(User user) {
		Statement stmt;
		int status;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.bussinessuser where ID=" + user.getID());
			if (rs.next() == true) {
				W4CBussiness w4c = getW4CBussiness(user);
				// System.out.println(w4c.toString());
				BussinessUser = new BussinessUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), w4c);
				return BussinessUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding BissessUser!!!");
		return null;
	}

	static Resturaunt getrestaurantname(int id) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT *  FROM assignment3.restaurant where IDRestaurant= " + id);
		if (rs.next() == true) {
			Resturaunt = new Resturaunt(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3));
			return Resturaunt;
		}
		return null;

	}

	static HRUser GetHRManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * From hruser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				HRManager = new HRUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				return HRManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	public static ArrayList<RestaurantReport> GetReportForRestaurant(RestaurantManager restaurantManager, int month,
			int year) throws SQLException {
		ReportList = new ArrayList<RestaurantReport>();
		Statement stmt;
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;

		int IDRestaurant;
		int ItemID;
		int sold;
		int quantity1 = 0;
		RestaurantReport rsp;
		System.out.println("Getting Report List from DB");
		try {
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmt4 = conn.createStatement();

			System.out.println("aa");
			System.out.println(restaurantManager.getUserName());

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM assignment3.restaurantmanager where ID='" + restaurantManager.getUserID() + "'");
			System.out.println("bb");
			if (rs.next() == true) {
				System.out.println("cc");
				IDRestaurant = Integer.parseInt(rs.getString(6));
				System.out.println(IDRestaurant);
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM assignment3.solditem where IDRestaurant='"
						+ IDRestaurant + "' and Month='" + month + "' and Year='" + year + "'");
				System.out.println("dd");
				while (rs2.next() == true) {
					System.out.println("jj");
					ItemID = Integer.parseInt(rs2.getString(2));
					sold = Integer.parseInt(rs2.getString(3));
					ResultSet rs4 = stmt4.executeQuery("SELECT * FROM assignment3.menu where IDRestaurant='"
							+ IDRestaurant + "' and Item_ID= '" + ItemID + "'");
					if (rs4.next() == true) {
						quantity1 = Integer.parseInt(rs4.getString(3));
						System.out.println(quantity1);
					}
					ResultSet rs3 = stmt3.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + ItemID + "'");
					System.out.println("mm");
					while (rs3.next() == true) {
						System.out.println("tat");
						rsp = new RestaurantReport(ItemID, rs3.getString(2), quantity1,
								Float.parseFloat(rs3.getString(3)), sold, sold * Float.parseFloat(rs3.getString(3)));
						System.out.println("add");
						ReportList.add(rsp);
						System.out.println("ZZZZ");
					}

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("z");
		return ReportList;
	}

	public static void Create_acceptRestaurant(Resturaunt res) {
		PreparedStatement ps;
		try {
			String query1 = " insert into assignment3.restaurant (IDRestaurant,RestaurantName,location)"
					+ " values (?, ?, ?)";
			ps = conn.prepareStatement(query1);
			ps.setInt(1, res.getResturauntID());
			ps.setString(2, res.getResturaunt_Name());
			ps.setString(3, res.getLocation());
			if (ps.executeUpdate() == 0) {
				System.out.println("Table Update Error!");
			} else {
				System.out.println("Create new restaraunt " + res.getResturauntID());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static boolean GetTheRequestNormalAccount() {
		NormalUsersNotAccepted = new ArrayList<Normal>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * FROM assignment3.users where confirm= 0 ");
			Normal request;
			while (RS.next()) {
				String ID = RS.getString(1);
				ResultSet RS1 = ps1.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + ID + "'");
				while (RS1.next()) {
					request = new Normal(RS1.getString(1), RS1.getString(2), RS1.getString(3), RS1.getString(4),
							RS1.getString(5), Integer.parseInt(RS1.getString(6)), Float.parseFloat(RS1.getString(7)));
					NormalUsersNotAccepted.add(request);
				}
			}
			return true;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void InsertNewNormalAccountWithVisa(Visa visa, User user, Normal NorUser) throws SQLException {
		InsertNewNormalAccountWithOutVisa(user, NorUser);
		String query1 = " insert into assignment3.visa (userID,Number,CVV,Year,CardHolderName,Month)"
				+ " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		preparedStmt1.setString(1, visa.getUserID());
		preparedStmt1.setString(2, visa.getNumber());
		preparedStmt1.setInt(3, visa.getCVV());
		preparedStmt1.setInt(4, visa.getYear());
		preparedStmt1.setString(5, visa.getCardHolderName());
		preparedStmt1.setInt(6, visa.getMonth());
		preparedStmt1.execute();

	}

	public static String InsertNewNormalAccountWithOutVisa(User user, Normal NorUser) throws SQLException {
		Statement stmt;

		System.out.println("i am getting into login query");
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.users where UserName='" + user.getUserName() + "'");
			if (rs.next() == true) {
				return "UserNameAlreadyExists";
			}
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + user.getID() + "'");
			if (rs1.next() == true) {
				return "IDAlreadyExists";
			}
			String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getUserName());
			preparedStmt.setString(3, user.getPassword());
			preparedStmt.setString(4, user.getUserType());
			preparedStmt.setInt(5, user.getIsLoggedIn());
			preparedStmt.setInt(6, user.getConfirm());
			preparedStmt.setString(7, user.getStatus());
			preparedStmt.execute();

			String query1 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, NorUser.getID());
			preparedStmt1.setString(2, NorUser.getFirstName());
			preparedStmt1.setString(3, NorUser.getLastName());
			preparedStmt1.setString(4, NorUser.getEmail());
			preparedStmt1.setString(5, NorUser.getPhoneNumber());
			preparedStmt1.setInt(6, NorUser.getVisaIsAvailable());
			preparedStmt1.execute();

			String query2 = " insert into assignment3.w4cnormal (code,IDuser)" + " values (?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, String.valueOf(NorUser.getW4C()));
			preparedStmt2.setString(2, user.getID());
			preparedStmt2.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	public static boolean updatePassword(User user) {
		System.out.println("user password updated ...." + user.getUserName());
		PreparedStatement ps;
		try {

			ps = conn.prepareStatement("UPDATE assignment3.users SET Password= ? WHERE ID = ?");
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getID());

			if (ps.executeUpdate() == 0) {
				System.out.println("Table Update Error!");
				return false;
			} else {
				System.out.println("update user with ID:" + user.getID() + " password to " + user.getPassword());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static String InsertNewBussinessAccount(BussinessUser user) throws SQLException {
		Statement stmt;
		System.out.println("i am getting into login query");
		try {
			String query = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,W4C,Company,Confirm)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getFirstName());
			preparedStmt.setString(3, user.getLastName());
			preparedStmt.setString(4, user.getPhoneNumber());
			preparedStmt.setString(5, user.getEmail());
			preparedStmt.setFloat(6, user.getW4C());
			preparedStmt.setString(7, user.getCompany());
			preparedStmt.setInt(8, 0);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	private static ArrayList<Addition> getAdditions(int itemId) {
		String sql = "select * from assignment3.item_addition where Item_ID=" + itemId;
		Statement st;
		ResultSet rs = null;
		ArrayList<Addition> list = new ArrayList<Addition>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Addition tmp = new Addition(rs.getString("name"));
				list.add(tmp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// get item for specific menu
	private static Item getItems(int itemId) {
		String sql = "select * from assignment3.item where Item_ID=" + itemId;
		Statement st;
		ResultSet rs = null;
		Item item = null;
		ArrayList<Addition> list;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String cat = rs.getString("category");
				Category c = Category.valueOf(cat.toString());
				list = getAdditions(itemId);
				item = new Item(itemId, rs.getString("Item_name"), rs.getDouble("Item_price"), c, list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}

	// get menu for each restaurant, calls getItems function to get items for menu
	private static Menu getMenu(int ResId) {
		Menu m = null;
		Item item = null;
		Statement st = null;
		HashMap<Item, Integer> items2 = new HashMap<>();
		// ArrayList<Item> items = new ArrayList<>();
		ResultSet rs = null;
		String sql = "select * from assignment3.menu where IDRestaurant=" + ResId;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int itemId = rs.getInt("Item_ID");
				item = getItems(itemId);
				items2.put(item, rs.getInt("quantity"));
				// items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		m = new Menu(items2);
		return m;
	}

	// return all restaurants from data base to display to the client
	public static ArrayList<Resturaunt> getAllResturaunts() {
		ArrayList<Resturaunt> resList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "select * from assignment3.restaurant";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int ResId = rs.getInt("IDRestaurant");
				Menu m = getMenu(ResId);
				Resturaunt r = new Resturaunt(ResId, rs.getString("RestaurantName"), m);
				resList.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resList;
	}

	public static W4CNormal getW4C(User user) {
		Statement stmt;
		W4CNormal w4c = null;
		String sql = null;
		if (user.getUserType().equals("Normal"))
			sql = "select * from assignment3.w4cnormal where IDuser=" + user.getID();
		else if (user.getUserType().equals("Bussiness"))
			sql = "select * from assignment3.w4cbussiness where IDuser=" + user.getID();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (user.getUserType().equals("Normal")) {
					w4c = new W4CNormal(rs.getInt(1), user);
					user.setW4c(w4c);
					System.out.println(user.getW4c().getCode());
				} else if (user.getUserType().equals("Bussiness")) {
					w4c = new W4CBussiness(rs.getInt(1), user, rs.getInt(2));
					user.setW4c(w4c);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return w4c;
	}

	public static void generate_qr(String image_name, String qrCodeData) {
		try {
			System.out.println(image_name + " " + qrCodeData);
			String filePath = "D:\\" + image_name + ".png";
			String charset = "UTF-8"; // or "ISO-8859-1"
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, 200, 200, hintMap);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
					new File(filePath));
			System.out.println("QR Code image created successfully!");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
