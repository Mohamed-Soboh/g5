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

import client.ChatClient;
import javafx.fxml.Initializable;
import common.Addition;
import common.BranchManager;
import common.CEOuser;
import common.Category;
import common.Company;
import common.HRUser;
import common.Item;
import common.ItemAddition;
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
	public static ArrayList<Item> Itemss;
	public static ArrayList<Item> AllItems;
	public static ArrayList<Company> companys;
	public static ArrayList<Resturaunt> getAllresturaunt;
	public static RestaurantManager restaurantManager;
	public static WorkerUser WorkerUser;
	public static BranchManager branchManager;
	public static HRUser HRManager;
	public static Normal Normaluser;
	public static ArrayList<RestaurantReport> ReportList;
	public static ArrayList<RestaurantReport> ReportListForManager;
	public static Connection conn;
	public static Resturaunt Resturaunt;
	public static ArrayList<Normal> NormalUsersNotAccepted;
	public static BussinessUser BussinessUser;
	public static CEOuser CEOuser1;
	public static ArrayList<User> TakeAllUserThatNotConfiredyet;
	public static ArrayList<User> userfrombitemedata;
	public static ArrayList<Company> CompanyList;
	public static ArrayList<Order> AllOrder;
	public static ArrayList<Item> Allitemsoforders;
	public static ArrayList<ItemAddition> itemsandAddition;

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
			String s3 = "mohamedsoboh132";
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
			String s3 = "mohamedsoboh132";
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

/////////////////////////////////////////////////////////////////

	public static void insidealldatafromBiteMeDB() throws SQLException {
		userfrombitemedata = new ArrayList<User>();
		Statement ps = conn.createStatement();
		Statement ps1 = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * From biteme_data.users");
		User user = null;
		while (RS.next()) {
			try {
				user = new User(RS.getString(1), RS.getString(4), RS.getString(5), RS.getString(8), RS.getString(2),
						RS.getString(6), RS.getString(7), RS.getString(3), RS.getString(14), RS.getString(13), 0,
						Integer.parseInt(RS.getString(11)), RS.getString(12), Integer.parseInt(RS.getString(10)),
						Integer.parseInt(RS.getString(15)));
				userfrombitemedata.add(user);
				String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
						+ " values (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, user.getID());
				preparedStmt.setString(2, user.getUserName());
				preparedStmt.setString(3, user.getPassword());
				preparedStmt.setString(4, user.getUserType());
				preparedStmt.setInt(5, 0);
				preparedStmt.setInt(6, user.getConfirm());
				preparedStmt.setString(7, user.getStatus());
				preparedStmt.execute();
			} catch (Exception e) {
				System.out.println("importing data ...");
			}
			switch (user.getUserType()) {
			case "BranchManager":
				String query1 = " insert into assignment3.branchmanager (ID,FirstName,LastName,Email,PhoneNumber,location)"
						+ " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
				preparedStmt1.setString(1, user.getID());
				preparedStmt1.setString(2, user.getFirstname());
				preparedStmt1.setString(3, user.getLastname());
				preparedStmt1.setString(4, user.getEmail());
				preparedStmt1.setString(5, user.getPhonenumber());
				preparedStmt1.setString(6, user.getLocation());
				preparedStmt1.execute();
				break;
			case "Normal":
				String query2 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
						+ " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
				preparedStmt2.setString(1, user.getID());
				preparedStmt2.setString(2, user.getFirstname());
				preparedStmt2.setString(3, user.getLastname());
				preparedStmt2.setString(4, user.getEmail());
				preparedStmt2.setString(5, user.getPhonenumber());
				preparedStmt2.setInt(6, user.getVisaavailable());
				preparedStmt2.execute();
				break;
			case "Bussiness":
				String query3 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
						+ " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
				preparedStmt3.setString(1, user.getID());
				preparedStmt3.setString(2, user.getFirstname());
				preparedStmt3.setString(3, user.getLastname());
				preparedStmt3.setString(4, user.getEmail());
				preparedStmt3.setString(5, user.getPhonenumber());
				preparedStmt3.setString(6, user.getCompany());
				preparedStmt3.execute();
				break;
			case "CEO":
				String query4 = " insert into assignment3.ceouser (ID,FirstName,LastName,Email,PhoneNumber)"
						+ " values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
				preparedStmt4.setString(1, user.getID());
				preparedStmt4.setString(2, user.getFirstname());
				preparedStmt4.setString(3, user.getLastname());
				preparedStmt4.setString(4, user.getEmail());
				preparedStmt4.setString(5, user.getPhonenumber());
				preparedStmt4.execute();
				break;
			case "RestaurantManager":
				String query5 = " insert into assignment3.restaurantmanager (ID,FirstName,LastName,Email,PhoneNumber,IDRestaurant)"
						+ " values (?, ?, ?, ?, ?,?)";
				PreparedStatement preparedStmt5 = conn.prepareStatement(query5);
				preparedStmt5.setString(1, user.getID());
				preparedStmt5.setString(2, user.getFirstname());
				preparedStmt5.setString(3, user.getLastname());
				preparedStmt5.setString(4, user.getEmail());
				preparedStmt5.setString(5, user.getPhonenumber());
				preparedStmt5.setInt(6, user.getIdrestaurant());
				preparedStmt5.execute();
				break;
			case "HR":
				Statement stmt;
				String query6 = " insert into assignment3.hruser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
						+ " values (?, ?, ?, ?, ?,?)";
				PreparedStatement preparedStmt6 = conn.prepareStatement(query6);
				preparedStmt6.setString(1, user.getID());
				preparedStmt6.setString(2, user.getFirstname());
				preparedStmt6.setString(3, user.getLastname());
				preparedStmt6.setString(4, user.getEmail());
				preparedStmt6.setString(5, user.getPhonenumber());
				preparedStmt6.setString(6, user.getCompany());
				preparedStmt6.execute();

				break;
			case "Worker":
				String query7 = " insert into assignment3.restaurantworker (ID,FirstName,LastName,Email,PhoneNumber,restaurantWorker)"
						+ " values (?, ?, ?, ?, ?,?)";
				PreparedStatement preparedStmt7 = conn.prepareStatement(query7);
				preparedStmt7.setString(1, user.getID());
				preparedStmt7.setString(2, user.getFirstname());
				preparedStmt7.setString(3, user.getLastname());
				preparedStmt7.setString(4, user.getEmail());
				preparedStmt7.setString(5, user.getPhonenumber());
				preparedStmt7.setInt(6, user.getIdrestaurant());
				preparedStmt7.execute();

				break;
			}
		}
	}

	public static void deletealldata() throws SQLException {
		Statement ps = conn.createStatement();

		PreparedStatement st, st1, st2 = null, st3 = null;

		ResultSet rs = ps.executeQuery("SELECT * FROM assignment3.users where confirm=0");
		while (rs.next() == true) {
			String ID = rs.getString(1);
			String type = rs.getString(4);

			if (type.equals("Normal")) {
				st = conn.prepareStatement("DELETE FROM assignment3.normaluser  where ID='" + ID + "'");
				st.executeUpdate();
			} else if (type.equals("Bussiness")) {
				st = conn.prepareStatement("DELETE FROM assignment3.bussinessuser  where ID='" + ID + "'");
				st.executeUpdate();
			} else if (type.equals("BranchManager")) {
				st = conn.prepareStatement("DELETE FROM assignment3.branchmanager");
				st.executeUpdate();
			} else if (type.equals("CEO")) {
				st = conn.prepareStatement("DELETE FROM assignment3.ceouser");
				st.executeUpdate();
			} else if (type.equals("RestaurantManager")) {
				st = conn.prepareStatement("DELETE FROM assignment3.restaurantmanager");
				st.executeUpdate();
			} else if (type.equals("HR")) {
				st = conn.prepareStatement("DELETE FROM assignment3.hruser");
				st.executeUpdate();
			} else if (type.equals("Worker")) {
				st = conn.prepareStatement("DELETE FROM assignment3.restaurantworker");
				st.executeUpdate();
			}
		}
		st = conn.prepareStatement("DELETE FROM assignment3.users WHERE confirm = ?");
		st.setInt(1, 0);
		st.executeUpdate();
	}

	// public WorkerUser(String userID, String firstName, String lastName, String
	// userName, String password, String email,
	// String phoneNumber, int iDRestaurant) {
	public static void LogOutAllAccounts() throws SQLException {
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? ");
		ps.setInt(1, 0);
		ps.executeUpdate();
	}

	public static String AddNewUser(User user, Object msgData1, String table) throws SQLException {
		PreparedStatement st;
		try {
			Statement stmt = conn.createStatement();

			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + user.getID() + "'");
			if (rs1.next() == true) {
				return "IDAlreadyExists";
			}
			ResultSet rs3 = stmt.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + user.getID() + "'");
			if (rs3.next() == true) {
				return "IDAlreadyExists";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (table.equals("normaluser")) {
			W4CNormal w4c = (W4CNormal) msgData1;
			String code = String.valueOf(w4c.getCode());
			String query1 = " insert into assignment3.normaluser (ID,FirstName,LastName,Email,Phone,VisaIsAvailable)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, user.getID());
			preparedStmt1.setString(2, user.getFirstname());
			preparedStmt1.setString(3, user.getLastname());
			preparedStmt1.setString(4, user.getEmail());
			preparedStmt1.setString(5, user.getPhonenumber());
			preparedStmt1.setInt(6, user.getVisaavailable());
			preparedStmt1.execute();

			String query6 = " insert into assignment3.w4cnormal (code,IDuser)" + " values (?, ?)";
			PreparedStatement preparedStmt6 = conn.prepareStatement(query6);
			preparedStmt6.setString(1, code);
			preparedStmt6.setString(2, w4c.getUser().getID());
			preparedStmt6.execute();

		} else {
			W4CBussiness w4c = (W4CBussiness) msgData1;
			String code = String.valueOf(w4c.getCode());
			String query2 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,Email,PhoneNumber,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, user.getID());
			preparedStmt2.setString(2, user.getFirstname());
			preparedStmt2.setString(3, user.getLastname());
			preparedStmt2.setString(4, user.getEmail());
			preparedStmt2.setString(5, user.getPhonenumber());
			preparedStmt2.setString(6, user.getCompany());
			preparedStmt2.execute();

			String query4 = " insert into assignment3.w4cbussiness (code,money,IDuser)" + " values (?, ?, ?)";
			PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
			preparedStmt4.setString(1, code);
			preparedStmt4.setDouble(2, w4c.getMoney());
			preparedStmt4.setString(3, w4c.getUser().getID());
			preparedStmt4.execute();

		}
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? , UserType=? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, "Active");
		ps.setString(3, user.getUserType());
		ps.setString(4, user.getID());
		ps.executeUpdate();

		return "updated";
	}

	public static String AddNewUserwithvisa(User user, Object msgData1, Visa visa, String table) throws SQLException {
		String str = AddNewUser(user, msgData1, table);
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
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.normaluser SET VisaIsAvailable= ? WHERE ID = ?");
		ps.setInt(1, 1);
		ps.setString(2, user.getID());
		ps.executeUpdate();

		return str;
	}

	public static void confirmCompane(String cname) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.company where companyname='" + cname + "'");
		if (rs.next() != true) {
			String query1 = " insert into assignment3.company (companyname,confirm)" + " values (?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, cname);
			preparedStmt1.setInt(2, 0);
			preparedStmt1.execute();
		}
	}

	public static void companyConfirm(String cname) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.company SET confirm= ? WHERE companyname = ?");
		ps.setInt(1, 1);
		ps.setString(2, cname);
		ps.executeUpdate();
	}

	public static ArrayList<Company> getCompanyList() {
		Statement stmt;
		CompanyList = new ArrayList<Company>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.company where confirm='" + 0 + "'");
			while (rs.next() == true) {
				Company company = new Company(rs.getString(1), rs.getInt(2));
				CompanyList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CompanyList;
	}

	public static ArrayList<User> TakeAllUserThatNotConfiredyet() throws SQLException {
		TakeAllUserThatNotConfiredyet = new ArrayList<>();
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT * From assignment3.users where UserType= 'null'");
		User users;
		while (RS.next()) {
			String id = RS.getString(1);

			for (int i = 0; i < userfrombitemedata.size(); i++) {
				if (id.equals(userfrombitemedata.get(i).getID())) {
					TakeAllUserThatNotConfiredyet.add(userfrombitemedata.get(i));
				}
			}
		}
		return TakeAllUserThatNotConfiredyet;
	}

	public static ArrayList<Company> GetallAvailableCompany() throws SQLException {
		companys = new ArrayList<Company>();
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT* From assignment3.company where confirm=1");
		Company company;
		while (RS.next()) {
			company = new Company(RS.getString(1), Integer.parseInt(RS.getString(2)));
			companys.add(company);
		}
		return companys;

	}

	///////////////
	public static ArrayList<Order> GetAllOrder(int id) throws NumberFormatException, SQLException {
		AllOrder = new ArrayList<Order>();
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("SELECT* From assignment3.order where RestaurantID='" + id + "'");
		Order order;
		while (RS.next()) {
			order = new Order(Integer.parseInt(RS.getString(1)), Double.parseDouble(RS.getString(5)), RS.getString(4),
					RS.getString(6), RS.getString(3));
			AllOrder.add(order);
		}
		return AllOrder;
	}

	/////////////////////
	public static void UpdateItem(Item item, String str) throws SQLException {
		PreparedStatement ps, ps1;
		ps1 = conn.prepareStatement("UPDATE assignment3.menu SET quantity= ? WHERE Item_ID = ?");
		ps1.setInt(1, item.getQuantity());
		ps1.setInt(2, item.getItem_ID());
		ps1.executeUpdate();
		ps = conn.prepareStatement("UPDATE assignment3.item SET Item_name= ?, Item_price=? WHERE Item_ID = ?");
		ps.setString(1, item.getItem_Name());
		ps.setDouble(2, item.getPrice());
		ps.setInt(3, item.getItem_ID());
		ps.executeUpdate();

	}

//////////////////
	public static void RemoveItem(Item item) throws SQLException {
		PreparedStatement st, st1;
		st1 = conn.prepareStatement("DELETE FROM assignment3.menu WHERE Item_ID = ?");
		st1.setInt(1, item.getItem_ID());
		st1.executeUpdate();
		st = conn.prepareStatement("DELETE FROM assignment3.item WHERE Item_ID = ?");
		st.setInt(1, item.getItem_ID());
		st.executeUpdate();

	}

	//////////////////
	public static void RemoveItemAddition(Item item, String addition) throws SQLException {
		PreparedStatement st, st1;
		st = conn.prepareStatement("DELETE FROM assignment3.item_addition WHERE name=? and Item_ID = ?");
		st.setString(1, addition);
		st.setInt(2, item.getItem_ID());
		st.executeUpdate();

		st1 = conn.prepareStatement("DELETE FROM assignment3.addition WHERE name = ?");
		st1.setString(1, addition);
		st1.executeUpdate();

	}

	/////////////////
	public static void AddItems(Item item, Addition addition, int resid, String str) throws SQLException {
		if (addition == null) {
			String query = " insert into assignment3.item (item_ID,Item_name,Item_price,category)"
					+ " values (?, ?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, item.getItem_ID());
			preparedStmt.setString(2, item.getItem_Name());
			preparedStmt.setDouble(3, item.getPrice());
			preparedStmt.setString(4, str);
			preparedStmt.execute();
			String query1 = " insert into assignment3.menu (IDRestaurant,item_ID,quantity)" + " values (?, ?,?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setInt(1, resid);
			preparedStmt1.setInt(2, item.getItem_ID());
			preparedStmt1.setInt(3, item.getQuantity());
			preparedStmt1.execute();
		} else {
			String query = " insert into assignment3.addition (name)" + " values (?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, addition.getName());
			preparedStmt.execute();
			String query2 = " insert into assignment3.item_addition (item_ID,name)" + " values (?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setInt(1, item.getItem_ID());
			preparedStmt2.setString(2, addition.getName());
			preparedStmt2.execute();

		}
	}

	public static ArrayList<WorkerUser> GetAllWorkers(int id) throws Exception// get all worker
	{
		ArrayList<WorkerUser> allworker = new ArrayList<WorkerUser>();
		Statement ps = conn.createStatement();
		Statement ps1 = conn.createStatement();
		WorkerUser WorkerUser;
		ResultSet RS = ps
				.executeQuery("SELECT * From assignment3.restaurantworker where restaurantWorker='" + id + "'");
		while (RS.next()) {
			String idres = RS.getString(1);
			ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.users where ID='" + idres + "'");
			if (RS1.next()) {
				WorkerUser = new WorkerUser(RS1.getString(1), RS.getString(2), RS.getString(3), RS1.getString(2),
						RS1.getString(3), RS.getString(4), RS.getString(5), id);
				WorkerUser.setRestaurantName(getrestaurantname(id).getResturaunt_Name());
				allworker.add(WorkerUser);

			}
		}
		return allworker;
	}

	public static void UpdateStatusOfUsers(User user1) throws SQLException {
		// System.out.println("user status updated ...." + user1.getUserName());
		PreparedStatement ps;

		ps = conn.prepareStatement("UPDATE assignment3.users SET status= ? WHERE ID = ?");
		ps.setString(1, user1.getStatus());
		ps.setString(2, user1.getID());
		ps.executeUpdate();

	}

	public static void BussinessAccountHasBeenAccepted(User user) throws SQLException {/// this method that accepted
		PreparedStatement ps;
		ps = conn.prepareStatement("UPDATE assignment3.users SET confirm= ? , status= ? WHERE ID = ?");
		ps.setString(1, "1");
		ps.setString(2, "Active");

		ps.setString(3, user.getID());
		ps.executeUpdate();
	}

	// add new business account
	public static String InsertNewBussinessAccount(User user, BussinessUser bussinessUser, String str)
			throws SQLException {

		System.out.println("i am getting into login query");
		try {
			Statement stmt = conn.createStatement();
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

			String query1 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, bussinessUser.getID());
			preparedStmt1.setString(2, bussinessUser.getFirstName());
			preparedStmt1.setString(3, bussinessUser.getLastName());
			preparedStmt1.setString(4, bussinessUser.getPhoneNumber());
			preparedStmt1.setString(5, bussinessUser.getEmail());
			preparedStmt1.setString(6, bussinessUser.getCompany());
			preparedStmt1.execute();

			String query2 = " insert into assignment3.w4cbussiness (code,money,IDuser)" + " values (?, ?, ?)";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			preparedStmt2.setString(1, str);
			preparedStmt2.setFloat(2, bussinessUser.getW4C());
			preparedStmt2.setString(3, bussinessUser.getID());
			preparedStmt2.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Updated";
	}

	// add new bussiness account with visa
	static String InsertNewBussinessAccountWithVisa(Visa visa, User user, BussinessUser bussinessUser, String str)
			throws SQLException {
		String value = InsertNewBussinessAccount(user, bussinessUser, str);
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
		return value;

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

			String query1 = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
			preparedStmt1.setString(1, bussinessUser.getID());
			preparedStmt1.setString(2, bussinessUser.getFirstName());
			preparedStmt1.setString(3, bussinessUser.getLastName());
			preparedStmt1.setString(4, bussinessUser.getPhoneNumber());
			preparedStmt1.setString(5, bussinessUser.getEmail());
			preparedStmt1.setString(6, bussinessUser.getCompany());
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
		PreparedStatement ps, rs;
		try {
			ps = conn.prepareStatement("UPDATE assignment3.users SET IsLoggedIn= ? WHERE UserName = ?");
			ps.setInt(1, status);
			ps.setString(2, user.getUserName());

			if (ps.executeUpdate() != 0) {
				System.out.println("update " + user.getUserName() + " status to:" + status);

			} else {
				rs = conn.prepareStatement("UPDATE bitemeuser SET IsLoggedIn= ? WHERE UserName = ?");
				rs.setInt(1, status);
				rs.setString(2, user.getUserName());
				rs.executeUpdate();
			}
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void deleteId(String id, String sqltable) throws SQLException {
		PreparedStatement st, st1, st2 = null, st3 = null;
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
			st3 = conn.prepareStatement("DELETE FROM assignment3.bussinessuser WHERE ID = ?");
		st3.setString(1, id);
		st3.executeUpdate();
		st2 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
		st2.setString(1, id);
		st2.executeUpdate();

	}

	static boolean getTheRequestList(String company) {
		requestsList = new ArrayList<BussinessUser>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps
					.executeQuery("SELECT * From assignment3.users where UserType='Bussiness' and status='Frozen'");
			BussinessUser request;
			while (RS.next()) {
				String id = RS.getString(1);
				ResultSet RS1 = ps1.executeQuery(
						"SELECT * From assignment3.bussinessuser where ID='" + id + "'and Company='" + company + "'");
				if (RS1.next()) {
					request = new BussinessUser(RS1.getString(1), RS1.getString(2), RS1.getString(3), RS1.getString(4),
							RS1.getString(5), 0, RS1.getString(6), 0);
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

	static void getAllResturaunt(String location) {
		getAllresturaunt = new ArrayList<Resturaunt>();
		try {
			Statement ps = conn.createStatement();
			ResultSet RS = ps.executeQuery("SELECT * from assignment3.restaurant where location='" + location + "'");

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
		Statement stmt, stmt1;
		int status;
		try {
			stmt1 = conn.createStatement();
			stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM assignment3.restaurantmanager where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				System.out.println("I'm getting RestaurantManager from DB");

				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.restaurant where IDRestaurant='"
						+ Integer.parseInt(rs.getString(6)) + "'");
				if (rs1.next() == true) {
					restaurantManager = new RestaurantManager(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)));
					System.out.println(restaurantManager);

					return restaurantManager;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}
	
	public static void deleteOrder(int orderid)
	{
		Statement stmt;
		try {
			String SQL = "delete from assignment3.order where orderNum=?";
			PreparedStatement pstmt = null;

			// get a connection and then in your try catch for executing your delete...

			pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
			SQL="delete from assignment3.order_items where orderNum=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
			SQL="delete from assignment3.order_item_addition where orderNum=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderid);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean CheckDelivery(int orderid)
	{
		String sql="select COUNT(*) from assignment3.delivery where orderNum="+orderid;
		Statement st = null;
		try {
			st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()==true)
			{
				String SQL = "delete from assignment3.delivery where orderNum=?";
				PreparedStatement pstmt = null;
				pstmt = conn.prepareStatement(SQL); 
				pstmt.setInt(1, orderid);
				pstmt.executeUpdate();
				return true;
			}
				
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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

	private static String getitemname(int itemid) throws SQLException {
		Statement stmt, stmt1;
		stmt1 = conn.createStatement();
		String str = null;
		ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.item where Item_ID='" + itemid + "'");
		if (rs1.next() == true) {
			str = rs1.getString(2);

		}
		return str;
	}

/////////////////////////////////////
	public static ArrayList<ItemAddition> GetallOrederItems(int ordernum) throws SQLException {
		Statement stmt, stmt1;

		stmt = conn.createStatement();
		stmt1 = conn.createStatement();
		 ArrayList<ItemAddition> itemsandAddition1 = new ArrayList<>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.order_item_addition where orderNum='" + ordernum + "'");
		while (rs.next() ) {
			int itemid = rs.getInt(2);
			String str = rs.getString(3);
			ItemAddition a=(new ItemAddition(getitemname(itemid), str));
			itemsandAddition1.add(a);
		}
		System.out.print("****&****");
		System.out.print(itemsandAddition1);
		return itemsandAddition1;
	}

	public static String getuser(String id) throws SQLException {
		Statement stmt, stmt1;
		int status;
		String mail = null;
		stmt = conn.createStatement();
		stmt1 = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + id + "'");
		if (rs.next() == true) {
			String type = rs.getString(4);
			switch (type) {
			case "Normal":
				ResultSet rs1 = stmt1.executeQuery("SELECT * FROM assignment3.normaluser where ID='" + id + "'");
				if (rs1.next() == true) {
					mail = rs1.getString(4);
					return mail;
				}
				break;
			case "Bussiness":
				ResultSet rs2 = stmt1.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + id + "'");
				if (rs2.next() == true) {
					mail = rs2.getString(4);
					return mail;
				}
				break;

			}
		}
		return mail;
	}

	static BranchManager GetBranchManager(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.branchmanager where ID='" + user.getID() + "'");
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.ceouser where ID='" + user.getID() + "'");
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

	static BussinessUser GetBissnessUser(User user) {
		Statement stmt;
		int status;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM assignment3.bussinessuser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				BussinessUser = new BussinessUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5),
						rs.getString(4), rs.getString(6));
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
			ResultSet rs = stmt.executeQuery("SELECT * From assignment3.hruser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				HRManager = new HRUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6));
				return HRManager;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error in finding !!!");
		return null;

	}

	public static Normal Getnormaluser(User user) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * From assignment3.normaluser where ID='" + user.getID() + "'");
			if (rs.next() == true) {
				Normaluser = new Normal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), Integer.parseInt(rs.getString(6)));
				return Normaluser;
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

	public static String AddNewWorker(WorkerUser WorkerUser) throws SQLException {
		Statement stmt;

		stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM assignment3.users where UserName='" + WorkerUser.getUserName() + "'");
		if (rs.next() == true) {
			return "UserNameAlreadyExists";
		}
		ResultSet rs1 = stmt.executeQuery("SELECT * FROM assignment3.users where ID='" + WorkerUser.getUserID() + "'");
		if (rs1.next() == true) {
			return "IDAlreadyExists";
		}
		String query = " insert into assignment3.users (ID,UserName,Password,UserType,IsLoggedIn,confirm,status)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, WorkerUser.getUserID());
		preparedStmt.setString(2, WorkerUser.getUserName());
		preparedStmt.setString(3, WorkerUser.getPassword());
		preparedStmt.setString(4, "Worker");
		preparedStmt.setInt(5, 0);
		preparedStmt.setInt(6, 1);
		preparedStmt.setString(7, "Active");
		preparedStmt.execute();

		String query1 = " insert into assignment3.restaurantworker (ID,FirstName,LastName,Email,PhoneNumber,restaurantWorker)"
				+ " values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
		preparedStmt1.setString(1, WorkerUser.getUserID());
		preparedStmt1.setString(2, WorkerUser.getFirstName());
		preparedStmt1.setString(3, WorkerUser.getLastName());
		preparedStmt1.setString(4, WorkerUser.getEmail());
		preparedStmt1.setString(5, WorkerUser.getPhoneNumber());
		preparedStmt1.setInt(6, WorkerUser.getIDRestaurant());
		preparedStmt1.execute();
		return "Updated";

	}

	public static void RemoveWorker(WorkerUser WorkerUser) throws SQLException {
		PreparedStatement st, st1;
		st = conn.prepareStatement("DELETE FROM assignment3.restaurantworker  WHERE ID = ?");
		st.setString(1, WorkerUser.getUserID());
		st.executeUpdate();
		st1 = conn.prepareStatement("DELETE FROM assignment3.users WHERE ID = ?");
		st1.setString(1, WorkerUser.getUserID());
		st1.executeUpdate();
	}

	public static void EditWorker(WorkerUser WorkerUser) throws SQLException {
		PreparedStatement ps, rs;

		ps = conn.prepareStatement(
				"UPDATE assignment3.restaurantworker SET FirstName= ?,LastName=?,Email=?,PhoneNumber=?  WHERE ID = ?");
		ps.setString(1, WorkerUser.getFirstName());
		ps.setString(2, WorkerUser.getLastName());
		ps.setString(3, WorkerUser.getEmail());
		ps.setString(4, WorkerUser.getPhoneNumber());
		ps.setString(5, WorkerUser.getUserID());
		ps.executeUpdate();
		rs = conn.prepareStatement("UPDATE assignment3.users SET UserName= ?,Password=? WHERE ID = ?");
		rs.setString(1, WorkerUser.getUserName());
		rs.setString(2, WorkerUser.getPassword());
		rs.setString(3, WorkerUser.getUserID());
		rs.executeUpdate();

	}

	public static String Create_acceptRestaurant(Resturaunt res) throws SQLException {
		PreparedStatement ps;
		try {
			String query1 = " insert into assignment3.restaurant (IDRestaurant,RestaurantName,location)"
					+ " values (?, ?, ?)";
			ps = conn.prepareStatement(query1);
			ps.setInt(1, res.getResturauntID());
			ps.setString(2, res.getResturaunt_Name());
			ps.setString(3, res.getLocation());
			ps.execute();
		} catch (Exception e) {
			return "This Id is Existes";
		}

		return "updated";

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
			String query = " insert into assignment3.bussinessuser (ID,FirstName,LastName,PhoneNumber,Email,Company)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getID());
			preparedStmt.setString(2, user.getFirstName());
			preparedStmt.setString(3, user.getLastName());
			preparedStmt.setString(4, user.getPhoneNumber());
			preparedStmt.setString(5, user.getEmail());
			preparedStmt.setString(6, user.getCompany());
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

	public static String companyChecker(String cname11) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM assignment3.company where confirm='" + 1 + "' and companyname='" + cname11 + "'");
			if (rs.next() == true) {
				return "companyExist";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

	public static ArrayList<Item> getallmaindish(int id, String type) throws SQLException {
		Item Items = null;
		ArrayList<Addition> addition = new ArrayList<Addition>();
		Itemss = new ArrayList<Item>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("select * from assignment3.menu where IDRestaurant='" + id + "'");
			while (RS.next()) {
				int itemid = RS.getInt(2);
				ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.item where Item_ID='" + itemid + "'");
				if (RS1.next()) {
					String cat = RS1.getString("category");
					Category c = Category.valueOf(cat.toString());
					addition = GetAllAddition(itemid);
					Items = new Item(itemid, RS1.getString(2), Double.parseDouble(RS1.getString(3)),
							(ArrayList<Addition>) addition, Integer.parseInt(RS.getString(3)), c);
					Items.setCate(cat);
					Itemss.add(Items);

				}
			}
			return Itemss;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Addition> GetAllAddition(int itemid) throws SQLException {
		ArrayList<Addition> addition = new ArrayList<Addition>();
		Addition add;
		Statement ps = conn.createStatement();
		ResultSet RS = ps.executeQuery("select * from assignment3.item_addition where Item_ID='" + itemid + "'");
		while (RS.next()) {
			add = new Addition(RS.getString(2));
			addition.add(add);
			System.out.println(add + "im here");
		}
		return addition;
	}

	public static ArrayList<Item> getallitems(int id) throws SQLException {
		Item Items = null;

		AllItems = new ArrayList<Item>();
		try {
			Statement ps = conn.createStatement();
			Statement ps1 = conn.createStatement();
			ResultSet RS = ps.executeQuery("select * from assignment3.menu where IDRestaurant='" + id + "'");
			while (RS.next()) {
				int itemid = RS.getInt(2);
				ResultSet RS1 = ps1.executeQuery("SELECT * From assignment3.item where Item_ID='" + itemid + "'");
				if (RS1.next()) {
					Items = new Item(itemid, RS1.getString(2), Double.parseDouble(RS1.getString(3)),
							Integer.parseInt(RS.getString(3)));
					System.out.print(Items);
					AllItems.add(Items);

				}
			}
			return AllItems;
		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void generate_qr(String image_name, String qrCodeData) {
		try {
			System.out.println(image_name + " " + qrCodeData);
			String filePath = "C:\\" + image_name + ".png";
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
