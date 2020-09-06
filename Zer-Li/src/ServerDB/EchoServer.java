package ServerDB;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Statement;

import CustomerServiceDepartmentworker.closingComplaint;
import CustomerServiceDepartmentworker.compensation;
import CustomerServiceDepartmentworker.complaint;
import CustomerServiceDepartmentworker.complaintProgress;
import CustomerServiceDepartmentworker.expertReport;
import BranchManager.BranchManager;
import BranchManager.PaymentAccount;
import BranchManager.PercentMSG;
import BranchManager.ReportHandler;
import BranchManager.Reports;
import BranchManager.SpecialBranchesMessage;
import BranchManager.catalogitemsofbranch;
import BranchWorker.Customer;
import BranchWorker.Survey;
import BranchWorker.satisfactionSurvey;
import Catalog.CatalogItem;
import Customer.BranchShipment;
import Customer.CatalogItemInOrder;
import Customer.CustomItemInOrder;
import Customer.CustomerTransaction;
import Customer.Date;
import Customer.Delivery;
import Customer.Flower;
import Customer.ItemInOrder;
import Customer.MessgaeCatalogProduct;
import Customer.MyTime;
import Customer.PrivateShipment;
import Customer.TransactionAbort;
import Users.User;
import client.Message;
import common.Branch;
import common.MyFile;
import javafx.fxml.Initializable;
import ocsf.server.*;
/**The following class represents the Server in the Server-client communication.
 * it handles all the requests from the client. In addition, it communicates with
 * the Database. 
 * 
 * @author Sharon, Haim, Elias and Alex
 *
 */
public class EchoServer extends AbstractServer implements Initializable {
	// Class attributes *************************************************
	/**
	 * Connection to DB
	 */
	private Connection ServerDataBase;
	/**
	 * boolean Connection with account Succeeded 
	 */
	private boolean DB_ACCOUNT;

	// Constructors ****************************************************

	/**
	 * Constructor of database controller
	 * @param port port number from text box
	 * @param UserName user name of database in mySql
	 * @param Password password of database in mySql
	 * @param DataBaseName name of database in mySql
	 */
	public EchoServer(int port, String UserName, String Password, String DataBaseName) {
		super(port);
		ServerDataBase = connectToDB(UserName, Password, DataBaseName);

		// run a new thread to check if its the time to create the quarterly reports
		Thread quarterlyReportGeneratorThread = new Thread() {
			public void run() {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date date = new java.util.Date();
				Connection serverDataBaseThreadCopy = connectToDB(UserName, Password, DataBaseName);
				while (true) {
					ReportHandler rp = new ReportHandler();
					rp.generateQuarterItemsReport(serverDataBaseThreadCopy, 1);
					System.out.println("The thread just generated reports.");
					// sleep for 24 hours
					try {
						Thread.sleep(86400000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		quarterlyReportGeneratorThread.start();

	}

/**Handles Messages from the client.
 * <p>The method get the msg as an object from the client, and discovers its original form,
 * and performs the needed operation on it in accordance</p>
 * @param msg sent from the client as Object
 * @param ConnectionToClient client
 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		// ------------------------------------instanceof
		// String-------------------------------------------------------
		if (msg instanceof String) {

			String DiscoverMessage = (String) msg;

			// -----------------------Alex's Changes--------------------------//

			// handle a request to generate quarterly reports
			if (DiscoverMessage.equals("generateANewQuarterlyReport")) {

				// find the current quarter
				DateFormat monthFormat = new SimpleDateFormat("MM");
				java.util.Date date = new java.util.Date();
				int Month = Integer.parseInt(monthFormat.format(date));
				DateFormat yearFormat = new SimpleDateFormat("yyyy");
				int Year = Integer.parseInt(yearFormat.format(date));
				int quarter;
				// find out to which quarter the current date belongs
				if (Month <= 3 && Month >= 1)
					quarter = 1;
				else {
					if (Month <= 6 && Month >= 4)
						quarter = 2;
					else {
						if (Month >= 7 && Month <= 9)
							quarter = 3;
						else
							quarter = 4;
					}
				}
				System.out.print("current month is" + Month + " and the quarter is: " + quarter + "the year is: " + Year);
				// send a query to the DB to generate a new report

				try {
					Statement statementquery;
					statementquery = (Statement) ServerDataBase.createStatement();
					PreparedStatement ps1 = ServerDataBase.prepareStatement(
							"INSERT INTO satisfactionsurvies VALUES (0,?,?,NULL,NULL,NULL,NULL,NULL,NULL)");
					ps1.setInt(1, quarter);
					ps1.setInt(2, Year);
					ps1.executeUpdate();
					ps1.close();

					statementquery.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;

			}
			// get all the complaints from the DB
			if (DiscoverMessage.equals("ComplaintsList")) {
				System.out.println("requesting all the active complaints from the database");
				ArrayList<complaint> Complaints = new ArrayList<complaint>();
				try {
					// get the items from the DB
					Statement st = (Statement) ServerDataBase.createStatement();

					ResultSet rs = st.executeQuery("SELECT * FROM complaints");
					while (rs.next()) {
						int i = 1;
						complaint Complaint = new complaint(rs.getInt(i++), rs.getInt(i++), rs.getInt(i++),
								rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i), "no details");
						Complaints.add(Complaint);
						System.out.print("complaint: " + Complaint.getComplaintID() + " " + Complaint.getCustomerID()
								+ " " + Complaint.getDateComplaint() + " " + Complaint.getDetails() + " "
								+ Complaint.getEmpHandling() + "\n");
					}
					rs.close();
					st.close();
					Message Msg = new Message(Complaints, "ComplaintsList");
					//send the result back to the client
					this.sendToAllClients(Msg);
					System.out.println("sending message to clients from complaints");

				} catch (SQLException e) {
					System.out.print("Sorry something went wrong with the SQL expression\n");
					e.printStackTrace();
				} catch (Exception e) // SQLException
				{
					System.out.println("Error, sorry something went wrong, could not get the complaints list");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			// ------------------------End of Alex's
			// Changes--------------------------------------------------

			if (DiscoverMessage.equals("Give Me All Branches")) // i changed condition here
			{
				System.out.println("Get all Branches  from DB");

				ArrayList<Branch> BranchesFromDB = new ArrayList<Branch>();
				try {
					BranchesFromDB = PutOutAllBranches(BranchesFromDB);

					Message Msg = new Message(BranchesFromDB, "Branch");

					this.sendToAllClients(Msg);

				} catch (SQLException e) {
					System.out.println("error-can't get users data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			// -----------------------------------------------------------//
			if (DiscoverMessage.equals("Give Me All Users")) {
				System.out.println("Get all Users from DB");

				ArrayList<User> UsersFromDB = new ArrayList<User>();
				try {
					UsersFromDB = PutOutAllUsers(UsersFromDB);

					Message Msg = new Message(UsersFromDB, "User");
					// this.sendToAllClients(Msg);
					try {
						client.sendToClient(Msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (SQLException e) {
					System.out.println("error-can't get users data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			// -----------------------------------------------------------//
			if (DiscoverMessage.equals("Give Me All CatalogItems")) {
				System.out.println("Get all CatalogItems from DB");

				ArrayList<CatalogItem> CatalogItemsFromDB = new ArrayList<CatalogItem>();
				try {
					CatalogItemsFromDB = PutOutAllCatalogItems(CatalogItemsFromDB);

					Message Msg = new Message(CatalogItemsFromDB, "CatalogItem");

					this.sendToAllClients(Msg);

				} catch (SQLException e) {
					System.out.println("error-can't get catalogItems data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			// -----------------------------------------------------------//

			if (DiscoverMessage.equals("Give Me All Reports")) {
				System.out.println("Get all Reports from DB");

				ArrayList<Reports> ReportsFromDB = new ArrayList<Reports>();
				try {
					ReportsFromDB = PutOutAllReports(ReportsFromDB);

					Message Msg = new Message(ReportsFromDB, "AllReports");

					this.sendToAllClients(Msg);
				} catch (SQLException e) {
					System.out.println("error-can't get Reports data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			if ((DiscoverMessage.length()) >= 28) // from here there is a process that check if client asking to change
													// entry status of userName
			{

				String checkSubString = DiscoverMessage.substring(0, 28); // cut msg string to "Please change Entry of
																			// user:" in case login control sent request
				System.out.println(checkSubString); // or cut msg string to ""Please delete item with ID: " in case
													// chain worker sent request

				if (checkSubString.equals("Please change Entry of user:")) {
					String cutUserNameFromStringMessage = DiscoverMessage.substring(29, (DiscoverMessage.length()));
					// need to change entry
					System.out.println("Changing the entry numbers of userName: " + cutUserNameFromStringMessage);
					changeEntryInDB(cutUserNameFromStringMessage);
					return;
				}

				// ------------------------

				if (checkSubString.equals("Please delete item with ID: ")) {
					String cutItemIDFromStringMessage = DiscoverMessage.substring(28, (DiscoverMessage.length()));
					// need to change entry
					System.out.println("Delete the item with itemID: " + cutItemIDFromStringMessage);

					int id = Integer.parseInt(cutItemIDFromStringMessage);

					deleteItemInDB(id);
					return;
				}

				// ------------------------

				if (checkSubString.equals("Please Check if Unique ID:  ")) {
					String cutItemIDFromStringMessage = DiscoverMessage.substring(28, (DiscoverMessage.length()));
					// need to change entry
					System.out.println("Check if Unique item ID: " + cutItemIDFromStringMessage);

					int id = Integer.parseInt(cutItemIDFromStringMessage);
					Boolean ans;

					ans = checkUniqueIDInDB(id);

					if (ans)
						System.out.println("true");
					else
						System.out.println("false");

					Message Msg = new Message(ans, "Answer if Unique item ID: " + id);

					this.sendToAllClients(Msg);

					return;
				}

			} // end of if ( (DiscoverMessage.length()) >= 28 )

			// -----------------------------------------------------------//

			if (DiscoverMessage.contains("Give Me All Satisfaction Survey Results")) {
				System.out.println("Get all All Satisfaction Survey Results");
				ArrayList<Float> SatSurveyResults = new ArrayList<Float>();
				try {
					SatSurveyResults = PutAllSatSurveyResults(SatSurveyResults);
				} catch (SQLException e) {
					System.out.println("fail to get Satisfaction Survey Results list!");
				}
				Message Msg = new Message(SatSurveyResults, "Satisfaction Survey list");

				this.sendToAllClients(Msg);

				return;
			}
			// -----------------------------------------------------------//
			if (DiscoverMessage.contains("Please Check if step = 0")) 
			{
				System.out.println("Check if step =0");
				Survey surveyExist = new Survey();
				boolean ansStep = false;
				try {
					ansStep = CheckIfStep0();
					if (ansStep)
					{
						System.out.println("ansStep true");
						surveyExist = getSurveyDetails(surveyExist);
						System.out.println(surveyExist);
					}
						
					
					else
					{
						System.out.println("ansStep false");
						surveyExist.setansStep(false);
					}
						
				} catch (SQLException e) {
					System.out.println("fail to get answer: if step = 0");
				}
				Message Msg = new Message(surveyExist, "Answer if step is 0");

				this.sendToAllClients(Msg);

				return;
			}

			// -----------------------------------------------------------//

			if (DiscoverMessage.contains("Please Check if step = 1")) {
				System.out.println("Check if step =1");
				boolean ansStep = false;
				try {
					ansStep = CheckIfStep1();
					if (ansStep)
						System.out.println("ansStep true");
					else
						System.out.println("ansStep false");
				} catch (SQLException e) {
					System.out.println("fail to get answer: if step = 1");
				}
				Message Msg = new Message(ansStep, "Answer if step is 1");

				this.sendToAllClients(Msg);

				return;
			}

			// -----------------------------------------------------------//

			if ((DiscoverMessage.substring(0, 24)).equals("Give me all ReportBranch")) {
				System.out.println("Get all Reports Branch from DB");

				ArrayList<Reports> ReportsFromDB = new ArrayList<Reports>();
				try {
					System.out.println(DiscoverMessage.substring(24, DiscoverMessage.length()));

					ReportsFromDB = PutOutAllBranchReports(ReportsFromDB,
							DiscoverMessage.substring(24, DiscoverMessage.length()));

					Message Msg = new Message(ReportsFromDB, "AllBranchReportE");

					this.sendToAllClients(Msg);
				} catch (SQLException e) {
					System.out.println("error-can't get Reports data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			// -----------------------------------------------//
			if ((DiscoverMessage.substring(0, 35)).equals("Give me all catalog items of branch")) {
				System.out.println("Get all catalog items of branch  from DB");

				ArrayList<catalogitemsofbranch> catalogitemsofbranchFromDB = new ArrayList<catalogitemsofbranch>();
				try {
					System.out.println(DiscoverMessage.substring(35, DiscoverMessage.length()));

					catalogitemsofbranchFromDB = PutOutAllCatalogItemsOfBranch(catalogitemsofbranchFromDB,
							DiscoverMessage.substring(35, DiscoverMessage.length()));

					Message Msg = new Message(catalogitemsofbranchFromDB, "catalog items of branch");

					this.sendToAllClients(Msg);
				} catch (SQLException e) {
					System.out.println("error-can't get catalog items data from db");
					this.sendToAllClients("GetFail");
				}
				return;
			}

			if (DiscoverMessage.equals("Give Me All Branches managers")) {
				System.out.println("Get all Branche managers from DB");

				ArrayList<BranchManager> BrancheManagersFromDB = new ArrayList<BranchManager>();
				try {
					BrancheManagersFromDB = PutOutAllBranchManagers(BrancheManagersFromDB);

					Message Msg = new Message(BrancheManagersFromDB, "BranchManager");

					// this.sendToAllClients(Msg);
					try {
						client.sendToClient(Msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					System.out.println("error-can't get branchManagers data from db");
					// this.sendToAllClients("GetFail");

				}
				return;
			}

		} // end of if (msg instanceof String)
		
		
		
		/**Checking if the msg is an instance of objects other than Strings*/

		// -----------------------------------instanceof
		// PaymentAccount-------------------------------------------------------
		if (msg instanceof PaymentAccount) {
			System.out.println("100");
			System.out.println("Set Payment Account on DB");

			try {
				AddNewPaymentAccount(msg);
			} catch (SQLException e) {
				System.out.println("error-can't Set Payment Account on DB");
				this.sendToAllClients("GetFail");
			}
			return;
		}

		// ---------------------------------------instanceof
		// Survey---------------------------------------------------------
		if (msg instanceof satisfactionSurvey) {
			System.out.println("yes");
			System.out.println("Set Survey Info on DB");

			try {
				SavesatisfactionSurveyInfo(msg);
			} catch (SQLException e) {
				System.out.println("error-can't Set Survey Info on DB");
				this.sendToAllClients("GetFail");
			}
			return;
		}
		// ---------------------------------------instanceof
		// Customer---------------------------------------------------------
		if (msg instanceof Customer) {

			System.out.println("Get all Branche Customers from DB");

			ArrayList<Customer> CustomerFromDB = new ArrayList<Customer>();
			try {
				CustomerFromDB = PutOutAllCustomers(CustomerFromDB);

				Message Msg = new Message(CustomerFromDB, "all Customer");

				// this.sendToAllClients(Msg);
				try {
					client.sendToClient(Msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("error-can't get Customer data from db");
				// this.sendToAllClients("GetFail");

			}
			return;
		}

		// ---------------------------------------instanceof
		// CatalogItem----------------------------------------------------
		if (msg instanceof CatalogItem) {

			CatalogItem givenItem = (CatalogItem) msg;
			// first we check if we need to add new item or to edit exist item:
			int tempID = givenItem.getItemID();
			Boolean ans;
			ans = checkUniqueIDInDB(tempID);

			if (ans) // ans true = this is unique id , means we need to add item to catalog
			{
				System.out.println("Adding item with id: " + tempID);
				addItemInDB(givenItem);

				return;
			}

			else // ans false = this is Not unique id , means we need to edit item in catalog
			{
				System.out.println("Editing item with id: " + tempID);
				editItemInDB(givenItem);

				return;
			}

		}
		// ---------------------------------------instanceof
		// PercentMSG----------------------------------------------------
		if (msg instanceof PercentMSG) 
		{

			System.out.println("server know to add sale!!!!!!!!!!!!!!");
			System.out.println("server know to add sale!!!!!!!!!!!!!!");
			System.out.println("server know to add sale!!!!!!!!!!!!!!");
			System.out.println("server know to add sale!!!!!!!!!!!!!!");

			PercentMSG salenItem = (PercentMSG) msg;
			double oldPrice=0;
			Integer itemID = new Integer(salenItem.getItemId());
			ArrayList<CatalogItem> allCatalogItems = new ArrayList<CatalogItem>();
			try 
			{
				allCatalogItems = this.PutOutAllCatalogItems(allCatalogItems);
			} 
			catch (SQLException e) 
			{
				System.out.println("Cannot get all catalog items to make a sale");

			}
			for ( int i =0 ; i<allCatalogItems.size(); i++ )
			{
				if(itemID == allCatalogItems.get(i).getItemID())
				{
					oldPrice = allCatalogItems.get(i).getItemPrice();
				}
			}
			
			catalogitemsofbranch currenSale;
			currenSale = editItemPriceInDB(salenItem, oldPrice);
			Message saleMsg = new Message (currenSale , "Sale added");
			try {
				client.sendToClient(saleMsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Editing item with id: " + salenItem.getItemId());
			return;

		}
		// ---------------------------------------instanceof
		// report----------------------------------------------------
		if (msg instanceof Reports) {

			Reports givenItem = (Reports) msg;

			 
			  try { // insert the data into the table
			    Statement statementquery =(Statement) ServerDataBase.createStatement(); // query to check if table //
			   
			  
			  PreparedStatement ps1 = ServerDataBase.prepareStatement("INSERT INTO reports VALUES (?,?,?,?,? )");
			    
			 
			  ps1.setInt(1, givenItem.getReportType());
			  ps1.setString(2,givenItem.getReportYear() ); 
			  ps1.setInt(3,givenItem.getReportQuarter()); 
			   ps1.setString(4, givenItem.getCsvFILE());
			  ps1.setString(5, givenItem.getBranchID());
			  
			  
			  ps1.executeUpdate();
			   ps1.close();
			  
			  statementquery.close(); 
			 } 
			   catch (SQLException e)
			   { 
			   e.printStackTrace(); 
			   }
			 
		}

		// ---------------------------------------instanceof
		// MessgaeCatalogProduct----------------------------------------------------
		if (msg instanceof MessgaeCatalogProduct) {/***
													 * this if condition will give the client all the catalog items of a specific
													 * branch
													 */
			System.out.println("Get all CatalogItems of a branch from DB");
			MessgaeCatalogProduct specialMessage = (MessgaeCatalogProduct) msg;
			String branchID = specialMessage.getBranchID();

			ArrayList<CatalogItem> CatalogItemsFromDB = new ArrayList<CatalogItem>();
			try {

				CatalogItemsFromDB = PutOutAllCatalogItems(CatalogItemsFromDB); // get all catalog items
				CatalogItemsFromDB = PutOutAllBranchCatalogItems(CatalogItemsFromDB, branchID); // get all catalog items
																								// new prices

				Message Msg = new Message(CatalogItemsFromDB, "CatalogItem");
				// this.sendToAllClients(Msg);
				try {
					client.sendToClient(Msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("error-can't get catalogItems data from db");
				this.sendToAllClients("GetFail");
			}
			return;
		}

		// ---------------------------------------instanceof
		// CustomerTransaction----------------------------------------------------
		if (msg instanceof CustomerTransaction) {
			System.out.println("server got request to save order");
		}

		if (msg instanceof CustomerTransaction) { /**
													 * this part responsible to check if payment account valid and save
													 * later the data of orders in the db
													 */
			System.out.println("server got request to save order");

			CustomerTransaction myOrder = (CustomerTransaction) msg;
			if (myOrder.getMsgToServer().equals("Save this order in db")) {

				boolean isApproved;
				String PA_userName = myOrder.getPaymentAccountUserName();
				String PA_Password = myOrder.getPaymentAccountPassword();
				String branchID = myOrder.getOrderbranchID();
				Date dateOfOrder = myOrder.getOrderCompletedDate();

				try {
					myOrder = checkIfAccountOK(myOrder, PA_userName, PA_Password, branchID, dateOfOrder);
					isApproved = myOrder.isApproved();
					if (isApproved == false) {
						System.out.println("payment account not approved");
						try {
							client.sendToClient(myOrder);
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					System.out.println("payment account  approved");
					myOrder = SaveOrderInDB(myOrder); // here we define a orderID and DeliveryID
					String msgToClient = "Your order been Placed.\nOrderID: " + myOrder.getOrderID()
							+ " , Total Price: " + myOrder.getOrdertotalPrice() + "$\nDeliveryID: "
							+ myOrder.getOrderCustomerDelivery().getDeliveryID();
					myOrder.setMsgToClient(msgToClient);
					try {
						client.sendToClient(myOrder);
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (SQLException e) {
					System.out.println("cannot connect to db to check payment account");

				}
			}

			else if (myOrder.getMsgToServer().equals("Give me all customer active orders")) {
				ArrayList<CustomerTransaction> allCustomerOrders = new ArrayList<CustomerTransaction>();
				// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				try {
					allCustomerOrders = PutOutAllCustomerOrders(allCustomerOrders, myOrder.getOrderbranchID(),
							myOrder.getBranchName(), myOrder.getCustomerID());

					Message Msg = new Message(allCustomerOrders, "CustomerTransaction");
					// this.sendToAllClients(Msg);
					try {
						client.sendToClient(Msg);
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (SQLException e) {
					System.out.println("error-cannot get customer order!!");
					return;
				}
			}

		}

		// ---------------------------------------instanceof
		// SpecialBranchesMessage----------------------------------------------------

		if (msg instanceof SpecialBranchesMessage) // Elias condition to get branches and branches managers
		{

			System.out.println("Server got request to get all branches and branches managers!!");
			SpecialBranchesMessage eliasMessage = (SpecialBranchesMessage) msg;
			ArrayList<Branch> allTheBranchesFromDb = new ArrayList<Branch>();
			ArrayList<BranchManager> allTheBranchManagersFromDb = new ArrayList<BranchManager>();

			try {
				allTheBranchesFromDb = this.PutOutAllBranches(allTheBranchesFromDb);
				eliasMessage.setAllBranches(allTheBranchesFromDb);

			} catch (SQLException e) {
				System.out.println("cannot put out all branches for elias");
			}

			try {
				allTheBranchManagersFromDb = this.PutOutAllBranchManagers(allTheBranchManagersFromDb);
				eliasMessage.setAllBranchManagers(allTheBranchManagersFromDb);

			} catch (SQLException e) {
				System.out.println("cannot put out all branch managers for elias");
			}

			try {
				client.sendToClient(eliasMessage);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// ---------------------------------------instanceof
		// Flower----------------------------------------------------
		if (msg instanceof Flower) {
			System.out.println("Server got message about flowers form client!!!!!!");
			try {
				ArrayList<Flower> allFlowersFromDB = new ArrayList<Flower>();
				try {
					allFlowersFromDB = putOutAllFlowers(allFlowersFromDB);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Message flowerMsg = new Message(allFlowersFromDB, "Flower");
				client.sendToClient(flowerMsg);
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (msg instanceof TransactionAbort) {
			TransactionAbort orderCancellation = (TransactionAbort) msg;
			try {
				cancelOrder(orderCancellation.getOrderID());
				changeDebtRegistration(orderCancellation);
				orderCancellation.setCommit(true);
				try {
					client.sendToClient(orderCancellation);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Cannot send to client positive abort of order");
				}
				System.out.println("server sent positive abort of order");
			}

			catch (SQLException e) {
				e.printStackTrace();
				orderCancellation.setCommit(false);
				try {
					client.sendToClient(orderCancellation);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Cannot send to client negative abort of order");
				}
				System.out.println("server sent negative abort of order");

			}

			return;
		}

		// -----------------------------------------------------------//

		// ---------------------------------------instance of
		// Complaints----------------------------------------------------

		// inserts a new complaint into the DB
				if (msg instanceof complaint) {
					complaint cmp = (complaint) msg;
					if (cmp.newComplaint) {

						try {
							System.out.println("inserting a new complaint");

							/*
							 * generate a new complaint ID, it extracts the current complaint ids, and
							 * increments tha id counter by 1 and sets it to be the new id
							 */
							Statement complaintIDsQuery = (Statement) ServerDataBase.createStatement(); // query to check if
							// table
							// filled

							PreparedStatement idsQuery = ServerDataBase.prepareStatement("SELECT ComplaintID FROM complaints");
							ResultSet rs = idsQuery.executeQuery();
							ArrayList<Integer> ids = new ArrayList<Integer>();
							while (rs.next())
								ids.add(rs.getInt(1));
							// get the id with the max id value
							int max = Collections.max(ids);
							int newID = ++max;
							// get current date and time, to record the complaints opening timestamp:
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date date = new java.util.Date();
							DateFormat timeFormat = new SimpleDateFormat("HH:mm");
							java.util.Date time = new java.util.Date();
							System.out.println("the new ID is: " + newID + "the current date is: " + dateFormat.format(date)
									+ timeFormat.format(time));

							System.out.println("complaintID" + newID + " " + cmp.getCustomerID() + " " + dateFormat.format(time)
							+ " " + cmp.getDetails() + " " + cmp.getEmpHandling() + " " + cmp.getStatus() + " "
							+ timeFormat.format(date) + " " + cmp.getTopic());
							// insert the data into the table
							Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if
										 																// table
																										// filled

							PreparedStatement ps1 = ServerDataBase
									.prepareStatement("INSERT INTO complaints VALUES (?,?,?,?,?,?,?)");
							ps1.setInt(1, newID);
							ps1.setString(2, Integer.toString(cmp.getCustomerID()));
							ps1.setString(3, Integer.toString(cmp.getEmpHandling()));
							ps1.setString(4, cmp.getTopic());
							ps1.setString(5, timeFormat.format(date));
							ps1.setString(6, dateFormat.format(time));
							ps1.setString(7, "open");
							ps1.executeUpdate();

							ps1.close();

							statementquery.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return;
					} else {
						System.out.println("starting to handle a request for updating a complaint");
						complaint c = (complaint) msg;
						if (c != null) {

							try {
								String selectStatement = "UPDATE complaints SET Topic= ? , Status= ? WHERE ComplaintID= ? AND CustomerID= ? AND EmpHendelingID= ?";
								// 1 2 3 4 5 6 7
								PreparedStatement statement = ServerDataBase.prepareStatement(selectStatement);
								System.out.println("complaint info:" + c.getTopic() + " " + c.getTimeComplaint() + " "
										+ c.getDateComplaint() + " " + c.getStatus() + " " + c.getComplaintID() + " "
										+ c.getCustomerID() + " " + c.getEmpHandling());
								statement.setString(1, c.getTopic());
								// statement.setString(2, c.getTimeComplaint());
								// statement.setString(3, c.getDateComplaint());
								statement.setString(2, c.getStatus());
								statement.setInt(3, c.getComplaintID());
								statement.setInt(4, c.getCustomerID());
								statement.setInt(5, c.getEmpHandling());
								int i = statement.executeUpdate();
								System.out.println("Edited " + i + " rows");
								statement.close();
								return;
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

							}
						}

					}
					return;
				}
		
		if (msg instanceof complaintProgress) {
			complaintProgress cp = (complaintProgress) msg;

			try {
				System.out.println("inserting a new complaint progress");
				// insert the data into the table
				Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if
																							// table
																							// filled

				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"INSERT INTO complaintprogress (complaintID,Topic,Details) VALUES ( ? , ? ,? )");

				// INSERT INTO complaints VALUES (?,?,?,?,?,?,?);
				// (`ComplaintID`, `CustomerID`, `EmpHendelingID`, `Topic`, `TimeComplaint`,
				// `DateComplaint`, `Status`) VALUES

				ps1.setInt(1, cp.getComplaintID());
				ps1.setString(2, cp.getTopic());
				ps1.setString(3, cp.getDetails());
				int i=ps1.executeUpdate();
				System.out.println("added new complaint progress, affected rows: "+i);
				ps1.close();

				statementquery.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;

		}

		if(msg instanceof closingComplaint)
		{
			closingComplaint cc=(closingComplaint)msg;
			
			

			try {
				System.out.println("closing a complaint");
				// insert the data into the table
				Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if
																							// table
																							// filled

				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"INSERT INTO complaintclosingreports VALUES (?,?,?,?,?)");

				// INSERT INTO complaints VALUES (?,?,?,?,?,?,?);
				// (`ComplaintID`, `CustomerID`, `EmpHendelingID`, `Topic`, `TimeComplaint`,
				// `DateComplaint`, `Status`) VALUES

				// get current date and time, to record the complaints opening timestamp:
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = new java.util.Date();
				DateFormat timeFormat = new SimpleDateFormat("HH:mm");
				java.util.Date time = new java.util.Date();
				
				ps1.setInt(1, cc.getComplaintID());
				ps1.setInt(2, cc.getCutsomerID());
				ps1.setString(3, dateFormat.format(date));
				ps1.setString(4, timeFormat.format(date));
				ps1.setString(5, cc.getDetails());
				int i=ps1.executeUpdate();
				System.out.println("closed a complaint, affected rows: "+i);
				ps1.close();

				statementquery.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
			
			
			
		}
		
		if(msg instanceof expertReport)
		{
			expertReport er=(expertReport)msg;
			try {
				System.out.println("Inserting a new expert report");
				// insert the data into the table
				Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if
																							// table
																							// filled

				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"INSERT INTO expertsreports (ExpertID, SurveyQarter, SurveyYear, SurveyReport) VALUES (? , ? , ? , ? )");

				// INSERT INTO complaints VALUES (?,?,?,?,?,?,?);
				// (`ComplaintID`, `CustomerID`, `EmpHendelingID`, `Topic`, `TimeComplaint`,
				// `DateComplaint`, `Status`) VALUES

				ps1.setInt(1, er.getExpertID());
				ps1.setInt(2, er.getQuarter());
				ps1.setInt(3, er.getYear());
				ps1.setString(4, er.getReport());
				int i=ps1.executeUpdate();
				System.out.println("save a new report, rows affected: "+i);
				ps1.close();

				statementquery.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
			
			
			
		}
		
		if(msg instanceof compensation)
		{
			compensation c=(compensation)msg;
			
			
			
			
			try {
				System.out.println("Inserting a compensation");
				// insert the data into the table
				Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if
																							// table
																							// filled

				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"INSERT INTO compensations VALUES ( ? , ? , ? , ? , ? )");

				// INSERT INTO complaints VALUES (?,?,?,?,?,?,?);
				// (`ComplaintID`, `CustomerID`, `EmpHendelingID`, `Topic`, `TimeComplaint`,
				// `DateComplaint`, `Status`) VALUES

				ps1.setInt(1, c.getCompensationID());
				ps1.setInt(2, c.getCustomerID());
				ps1.setInt(3, c.getCsde_id());
				ps1.setDouble(4, c.getCompensationAmount());
				ps1.setString(5, c.getIsPaid());
				int i=ps1.executeUpdate();
				System.out.println("save a compensation, rows affected: "+i);
				ps1.close();

				statementquery.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			return;
		}
	
		// -----------------------------------------------------------//

	} // end of handleMessageFromClient
	
/**
 * this method will check customer debt, and change it according to cancellation time
 * @param orderCancellation class with details bout amount of refund
 * @throws SQLException in case of error in working with table
 */
	private void changeDebtRegistration(TransactionAbort orderCancellation) throws SQLException 
	{ 								
		int orderID = orderCancellation.getOrderID();
		if (orderCancellation.getRefund() == 0) // no refund
			return;
		else if (orderCancellation.getRefund() == 0.5) // 50% refund
		{
			Statement st = null;
			double priceAfterRefund = orderCancellation.getOrderPrice() * 0.5;

			st = (Statement) ServerDataBase.createStatement();
			String sql = "SELECT OrderPrice FROM customerbilling WHERE OrderID = '" + orderID + "'";
			ResultSet rs = null;
			rs = st.executeQuery(sql);
			while (rs.next()) {

				String insertTableSQL1 = "UPDATE customerbilling SET OrderPrice = " + priceAfterRefund
						+ " WHERE OrderID='" + orderID + "';";
				st.executeUpdate(insertTableSQL1);
				rs.close();
				st.close();
				break; // break the loop to prevent crash

			}
			return;
		}

		else if (orderCancellation.getRefund() == 1) {
			Statement st = null;

			st = (Statement) ServerDataBase.createStatement();
			String sqlDeleteRow = "DELETE FROM customerbilling WHERE OrderID = " + orderID + ";";

			st.executeUpdate(sqlDeleteRow);
			st.close();

			return;
		}
	}
	/**
	 * this method will delete row of orderId in customerOrder table in database
	 * @param orderID id of order
	 * @throws SQLException error in reading customer orders table
	 */
	private void cancelOrder(int orderID) throws SQLException 
	{ 
		Statement st = null;

		st = (Statement) ServerDataBase.createStatement();
		String sqlDeleteRow = "DELETE FROM customerorders WHERE OrderID = " + orderID + ";";

		st.executeUpdate(sqlDeleteRow);
		st.close();

	}
	/**
	 * this method bring all customers in a table
	 * @param customersFromDB empty array list of customers 
	 * @return full arraylist of customers
	 * @throws SQLException error if read from table failed
	 */
	private ArrayList<Customer> PutOutAllCustomers(ArrayList<Customer> customersFromDB) throws SQLException 
	{

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from customers ");

		while (rs.next()) {

			int CustomerID = rs.getInt(1);
			String CustomersName = "" + rs.getString(2);
			String CustomersLASTName = "" + rs.getString(3);
			String Customersaddress = "" + rs.getString(4);
			String Customersemail = "" + rs.getString(5);
			int CustomerNUMphone = rs.getInt(6);
			Customer myCustomer1 = new Customer(CustomerID, CustomersName, CustomersLASTName, Customersaddress,
					Customersemail, CustomerNUMphone);
			customersFromDB.add(myCustomer1);

		}
		System.out.println(customersFromDB);

		rs.close();
		st.close();

		return customersFromDB;

	}

	// ***********************************************************************************************************************************************************************************
	// Class methods
	// ********************************************************************************************************************************************************************
	// ***********************************************************************************************************************************************************************************
	/**
	 * this method bring all active survies of quarter
	 * @param Survey empty arraylist of survies
	 * @return full array list of survies
	 * @throws SQLException when reading of table failed
	 */
	private Survey getSurveyDetails(Survey surveyExist) throws SQLException //only if step = 0 
	{
		surveyExist.setansStep(true);
		Statement st = (Statement) ServerDataBase.createStatement();
		ResultSet rs = st.executeQuery("SELECT SurviesQuarter,SurviesYear from satisfactionsurvies where Step=0;");
		while (rs.next())
		{
			surveyExist.setQarSurvey(rs.getInt(1));
			surveyExist.setSurveyYear(rs.getString(2));
		}
		
		System.out.println("im in getSurveyDetails func the result : " + surveyExist);

		rs.close();
		st.close();
		
		return surveyExist;
	}
	// ***********************************************************************************************************************************************************************************
	/**
	 * check if service department worker create new survey.
	 * @return boolean answer if step = 0 in DB (meaning the service department worker create new survey)
	 * @throws SQLException when sql script failed
	 */
	private boolean CheckIfStep0() throws SQLException {
		Statement st = (Statement) ServerDataBase.createStatement();
		ResultSet rs = st.executeQuery("SELECT Step from satisfactionsurvies where Step=0;");

		while (rs.next())
		{
			return true; // step is 1 , mean the branch worker finish to fill surveys
		}

		rs.close();
		st.close();

		return false;
	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * check if branch worker finish to fill surveys and results exist in the DB
	 * @return boolean answer if step = 1 in DB (meaning the branch worker finish to fill surveys and results exist in the DB)
	 * @throws SQLException when sql script failed
	 */
	private boolean CheckIfStep1() throws SQLException {
		Statement st = (Statement) ServerDataBase.createStatement();
		ResultSet rs = st.executeQuery("SELECT Step from satisfactionsurvies where Step=1;");

		while (rs.next()) // there is the same id in DB
		{
			return true; // step is 1 , mean the branch worker finish to fill surveys
		}

		rs.close();
		st.close();

		return false;
	}

	// ***********************************************************************************************************************************************************************************
	
	/**
	 * this method bring all survies
	 * @param SatSurveyResults empty arraylist of survies
	 * @return full  arraylist of survies
	 * @throws SQLException when reading of table failed
	 */
	private ArrayList<Float> PutAllSatSurveyResults(ArrayList<Float> SatSurveyResults) throws SQLException 
	{

		Statement st = (Statement) ServerDataBase.createStatement();
		ResultSet rs = st.executeQuery("SELECT q1,q2,q3,q4,q5,q6 from satisfactionsurvies where Step=1;");

		while (rs.next()) {
			float q1 = rs.getFloat(1);
			float q2 = rs.getFloat(2);
			float q3 = rs.getFloat(3);
			float q4 = rs.getFloat(4);
			float q5 = rs.getFloat(5);
			float q6 = rs.getFloat(6);

			SatSurveyResults.add(q1);
			SatSurveyResults.add(q2);
			SatSurveyResults.add(q3);
			SatSurveyResults.add(q4);
			SatSurveyResults.add(q5);
			SatSurveyResults.add(q6);

		}
		rs.close();
		st.close();

		return SatSurveyResults;

	}
	
	// ***********************************************************************************************************************************************************************************
	/**
	 * this method will return all active orders of specific customer
	 * @param allCustomerOrders empty arraylist of all customer orders
	 * @param orderbranchID specific branch id
	 * @param branchName specific branch name
	 * @param customerID id of customer	
	 * @return full array list of customer orders
	 * @throws SQLException when reading of table failed
	 */
	private ArrayList<CustomerTransaction> PutOutAllCustomerOrders(ArrayList<CustomerTransaction> allCustomerOrders,
			String orderbranchID, String branchName, int customerID) throws SQLException 
	{ 
		Statement st = null;

		st = (Statement) ServerDataBase.createStatement();
		String sql = "SELECT * FROM customerorders WHERE CustomerID = '" + customerID + "'";
		ResultSet rs = null;
		rs = st.executeQuery(sql);
		while (rs.next()) {
			int completedStatus = rs.getInt(13);
			String myBranchID = rs.getString(4);

			if (completedStatus == 0 && myBranchID.equals(orderbranchID)) {
				int myOrderID = rs.getInt(1);
				double myOrderPrice = rs.getDouble(5);
				Date mySupplyDate = this.convertSqlDateToDateOfHaim(rs.getDate(6));
				MyTime mySupplyHour = this.convertSqlTimeToTimeOfHaim(rs.getTime(7));
				CustomerTransaction myOrder = new CustomerTransaction();
				myOrder.setOrderID(myOrderID);
				myOrder.setCustomerID(customerID);
				myOrder.setBranchName(branchName);
				myOrder.setOrderSupplyDate(mySupplyDate);
				myOrder.setOrdersupplyTime(mySupplyHour);
				myOrder.setOrderTotalPrice(myOrderPrice);
				myOrder.setMsgToClient("show this order on cancellation table");
				allCustomerOrders.add(myOrder);
			}
		}
		rs.close();
		st.close();
		return allCustomerOrders;

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method responsible to get all flowers from flowers table
	 * @param allFlowersFromDB empty arraylist of flowers
	 * @return full array list of flowers
	 * @throws SQLException if read from table failed
	 */
	private ArrayList<Flower> putOutAllFlowers(ArrayList<Flower> allFlowersFromDB)
			throws SQLException { /** this method responsible to put out all of the flowers in the db */
		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from flowers ");

		while (rs.next()) {
			int flowerID = rs.getInt(1);
			String flowerColor = "" + rs.getString(2);
			String flowerName = "" + rs.getString(3);
			Double flowerPrice = rs.getDouble(4);

			Flower FlowerReturnToClient = new Flower(flowerID, flowerColor, flowerName, flowerPrice);
			allFlowersFromDB.add(FlowerReturnToClient);

		}
		rs.close();
		st.close();

		return allFlowersFromDB;
	}

	// ***********************************************************************************************************************************************************************************
	
	/**
	 * saveOrderInDB method responsible to save order information on 7 tables in db
	 * @param myOrder CustomerTransaction with no order id and delivery id
	 * @return CustomerTransaction with message to customer
	 */
	private CustomerTransaction SaveOrderInDB(CustomerTransaction myOrder) {

		try {
			int randomDeliveryID = getRandomDeliveryIdFromDB();
			System.out.println("Your random deliveryID: " + randomDeliveryID);

			int randomOrderID = getRandomOrderIdFromDB();
			System.out.println("Your random orderID: " + randomOrderID);
			myOrder.setOrderID(randomOrderID); // put orderID for customer transaction
			// myOrder.getOrderCustomerDelivery().setOrderID(randomOrderID); //put orderID
			// in Delivery of transaction
			Delivery tempDelivery = myOrder.getOrderCustomerDelivery();
			tempDelivery.setOrderID(randomOrderID);
			tempDelivery.setDeliveryID(randomDeliveryID);
			myOrder.setOrderCustomerDelivery(tempDelivery);

			// myOrder.getOrderCustomerDelivery().setDeliveryID(randomDeliveryID); //put
			// deliveryID in Delivery of transaction

		} catch (SQLException e) {
			System.out.println("Cannot find random orderID or DeliveryID");
		}

		// here we got OrderID and DeliveryID and begins to save on 7 tables!!

		// saving on customerOrder table
		finally {

			saveOrderOnCustomerOrderTable(myOrder); // save data in customerOrder table!!
			saveOrderOnCustomerBillingTable(myOrder);
			saveItemInOrderOnCatalogCustomProducts(myOrder);
			saveDeliveryOfCustomerOrder(myOrder);

			// end of saving on customerOrder table
		}

		return myOrder;
	}
	// ***********************************************************************************************************************************************************************************
	/**
	 * this methods responsible on saving delivery data in branchShipment/privateShipment tables
	 * @param myOrder CustomerTransaction that contains delivery details class
	 */
	
	private void saveDeliveryOfCustomerOrder(CustomerTransaction myOrder) 
	{ 										
		Delivery orderDelivery = myOrder.getOrderCustomerDelivery();
		BranchShipment orderBranchShipment;
		PrivateShipment orderPrivateShipment;

		if (orderDelivery instanceof BranchShipment) {
			System.out.println("Server is going to save delivery in branchShipment Table");
			try {
				orderBranchShipment = (BranchShipment) orderDelivery;
				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"insert into branchshipments (OrderID,BranchID,DeliveryID,DeliveryPrice) values (?,?,?,?)");

				int orderID = myOrder.getOrderID();
				String branchID = myOrder.getOrderbranchID();
				int deliveryID = orderBranchShipment.getDeliveryID();
				double deliveryPrice = orderBranchShipment.getPrice();

				ps1.setInt(1, orderID);
				ps1.setString(2, branchID);
				ps1.setInt(3, deliveryID);
				ps1.setDouble(4, deliveryPrice);
				ps1.executeUpdate();

				ps1.close();

				System.out.println("Data saved in branchShipment table!!");

			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // OrderID

		}

		if (orderDelivery instanceof PrivateShipment) {
			System.out.println("Server is going to save delivery in privateShipment Table");
			orderPrivateShipment = (PrivateShipment) orderDelivery;
			try {
				PreparedStatement ps1 = ServerDataBase.prepareStatement(
						"insert into privateshipments (OrderID,DeliveryID,DeliveryPrice,Adresse,PhoneNumber,Adress) values (?,?,?,?,?,?)");

				int orderID = myOrder.getOrderID();
				int deliveryID = orderPrivateShipment.getDeliveryID();
				double deliveryPrice = orderPrivateShipment.getPrice();
				String adresse = orderPrivateShipment.getAddressee();
				String phoneNumber = orderPrivateShipment.getPhoneNumber();
				String adress = orderPrivateShipment.getAddress();

				ps1.setInt(1, orderID);
				ps1.setInt(2, deliveryID);
				ps1.setDouble(3, deliveryPrice);
				ps1.setString(4, adresse);
				ps1.setString(5, phoneNumber);
				ps1.setString(6, adress);
				ps1.executeUpdate();

				ps1.close();

				System.out.println("Data saved in branchShipment table!!");

			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // O

		}
	}

	// *****************************************************************************************************************************************************************************
	/** this methods responsible on saving data in catalog/custom item tables
	 * @param myOrder CustomerTransaction that contains all products in order
	 */
	private void saveItemInOrderOnCatalogCustomProducts(CustomerTransaction myOrder) // this method will be continued
	{ 
		System.out.println("Server prepare to save on CustomerOrderTable");
		int OrderID = myOrder.getOrderID();
		int customerID = myOrder.getCustomerID();
		ArrayList<ItemInOrder> allItems = myOrder.getProductsList();
		ArrayList<CatalogItemInOrder> allCatalogItems = new ArrayList<CatalogItemInOrder>(); // list of catalog items
																								// from items in order
		ArrayList<CustomItemInOrder> allCustomItems = new ArrayList<CustomItemInOrder>(); // list of custom items from
																							// items in order

		try {

			PreparedStatement ps1 = ServerDataBase.prepareStatement(
					"insert into catalogiteminorder (OrderID,ItemID,CustomerID,Quantity,ItemPrice) values (?,?,?,?,?)");
			for (int i = 0; i < allItems.size(); i++) {
				if (allItems.get(i) instanceof CatalogItemInOrder) {
					ItemInOrder curretnItemOfCatalog = allItems.get(i);
					CatalogItemInOrder currentCatalogItem = (CatalogItemInOrder) curretnItemOfCatalog;
					allCatalogItems.add(currentCatalogItem);
				} else if (allItems.get(i) instanceof CustomItemInOrder) {
					ItemInOrder curretnItemOfCustom = allItems.get(i);
					CustomItemInOrder currentCustomItem = (CustomItemInOrder) curretnItemOfCustom;
					allCustomItems.add(currentCustomItem);
				}
			}

			for (int i = 0; i < allCatalogItems.size(); i++) // insert all catalogItems to table of catalogItems
			{

				int itemID = allCatalogItems.get(i).getItemID();
				int quantityOfItem = allCatalogItems.get(i).getItemQty();
				double priceOfItem = allCatalogItems.get(i).getItemPrice();
				ps1.setInt(1, OrderID);
				ps1.setInt(2, itemID);
				ps1.setInt(3, customerID);
				ps1.setInt(4, quantityOfItem);
				ps1.setDouble(5, priceOfItem);
				ps1.executeUpdate();
			}
			System.out.println("Data saved in catalogIteminOrder table!!");

			ps1.close();

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // OrderID

		// here we will insert customItems to customItems table
		Statement st = null;
		ArrayList<Integer> allCustomItemsID = new ArrayList<Integer>(); // we will collect all customItemsID in order to
																		// prevent duplicant ID
		try {
			st = (Statement) ServerDataBase.createStatement();

			ResultSet rs = st.executeQuery("select * from customeiteminorder ");

			while (rs.next()) {

				int ItemID = rs.getInt(1);
				Integer itemID_db = new Integer(ItemID);
				allCustomItemsID.add(itemID_db); // here we get all itemsId in order to prevent duplications of the id
			}

			rs.close();
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // OrderID
		try {
			PreparedStatement ps2 = ServerDataBase.prepareStatement("insert into customeiteminorder (OrderID,ItemID,CustomerID,Quantity,ItemPrice,itemtype,DominantColor) values (?,?,?,?,?,?,?)");
			for (int k = 0; k < allCustomItems.size(); k++) // we will give random itemID to customItems
			{
				Random rand = new Random();
				int myRandomNum = rand.nextInt(99999998) + 1;
				while (allCustomItemsID.contains(myRandomNum)) {
					myRandomNum = rand.nextInt(99999998) + 1; // find random itemID that is not at the table
				}

				// orderID kept up^^
				int itemID = myRandomNum;
				// customerID kept up^^
				int quantityOfItem = allCustomItems.get(k).getItemQty();
				double priceOfItem = allCustomItems.get(k).getItemPrice();
				String itemType = allCustomItems.get(k).getItemType();
				String dominantColor = allCustomItems.get(k).getItemDominantColor();

				ps2.setInt(1, OrderID);
				ps2.setInt(2, itemID);
				ps2.setInt(3, customerID);
				ps2.setInt(4, quantityOfItem);
				ps2.setDouble(5, priceOfItem);
				ps2.setString(6, itemType);
				ps2.setString(7, dominantColor);
				ps2.executeUpdate();
			}
			ps2.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Data saved in customIteminOrder table!!");

	}

	// **********************************************************************************************************
	
	/**
	 * this methods responsible on saving data in customer billing table
	 * @param myOrder
	 */
	private void saveOrderOnCustomerBillingTable(CustomerTransaction myOrder) 
	{
		
		System.out.println("Server prepare to save on CustomerBillingTable");

		// put new row in customerbilling table!!

		try {

			PreparedStatement ps1 = ServerDataBase
					.prepareStatement("insert into customerbilling (CustomerID,OrderID,OrderPrice) values (?,?,?)");
			int customerID = myOrder.getCustomerID();
			int OrderID = myOrder.getOrderID();
			Double totalPrice = myOrder.getOrdertotalPrice();

			ps1.setInt(1, customerID);
			ps1.setInt(2, OrderID);
			ps1.setDouble(3, totalPrice); // totalPrice

			ps1.executeUpdate();
			ps1.close();

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // OrderID

		System.out.println("Data saved in customer billing table!!");

	}

	// ****************************************************************************************************************************

	/**
	 * this methods responsible on saving data in customerOrders table
	 * @param myOrder CustomerTransaction with id of order and delivery
	 */
	private void saveOrderOnCustomerOrderTable(	CustomerTransaction myOrder) { 
		System.out.println("Server prepare to save on CustomerOrderTable");

		// put new row in catalogitems table!!

		try {

			PreparedStatement ps1 = ServerDataBase.prepareStatement(
					"insert into customerorders (OrderID,CustomerID,DeliveryID,BranchID,OrderPrice,SupplyDate,SupplyHour,CompletedDate,CompletedTime,Greeting,PaymentType,ImmediateOrder,CompletedStatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			int OrderID = myOrder.getOrderID();
			int CustomerId = myOrder.getCustomerID();
			int DeliveryID = myOrder.getOrderCustomerDelivery().getDeliveryID();
			String BranchID = myOrder.getOrderbranchID();
			Double totalPrice = myOrder.getOrdertotalPrice();
			String supplyDate = "" + myOrder.getOrdersupplyDate();
			String supplyTime = "" + myOrder.getOrdersupplyTime();
			String completedDate = "" + myOrder.getOrderCompletedDate();
			String completedTime = "" + myOrder.getOrderCompletedTime();
			String greeting = "" + myOrder.getGreeting();
			String paymentType = "" + myOrder.getPaymentType();
			boolean isImmidate = myOrder.getIsExpeditedDelivery();
			String answerIsImmidate = "";
			if (isImmidate == true) {
				answerIsImmidate = "Yes";
			} else {
				answerIsImmidate = "No";

			}
			int completedStatus = myOrder.getCompleteStatus();

			ps1.setInt(1, OrderID);
			ps1.setInt(2, CustomerId); /// CustomerID
			ps1.setInt(3, DeliveryID); // DeliveryID
			ps1.setString(4, BranchID); // BranchID
			ps1.setDouble(5, totalPrice); // totalPrice
			ps1.setString(6, "" + supplyDate); // supply date
			ps1.setString(7, "" + supplyTime); // supply hour time
			ps1.setString(8, "" + completedDate); // completed date
			ps1.setString(9, "" + completedTime); // completed hour time
			ps1.setString(10, greeting); // greeting
			ps1.setString(11, paymentType); // greeting
			ps1.setString(12, answerIsImmidate); // if customer want expedited delivery
			ps1.setInt(13, completedStatus); // completedStauts , 0 = order not sent to customer, 1 = order did sent to
												// customer
			ps1.executeUpdate();
			ps1.close();

			/*
			 * ps1.setInt(1, myOrder.getOrderID()); ps1.setInt(2, myOrder.getCustomerID());
			 * ///CustomerID ps1.setInt(3,
			 * myOrder.getOrderCustomerDelivery().getDeliveryID()); //DeliveryID
			 * ps1.setString(4, myOrder.getOrderbranchID()); //BranchID ps1.setDouble(5,
			 * myOrder.getOrdertotalPrice()); //totalPrice ps1.setString(6, "" +
			 * myOrder.getOrdersupplyDate()); //supply date ps1.setString(7, "" +
			 * myOrder.getOrdersupplyTime()); //supply hour time ps1.setString(8, "" +
			 * myOrder.getOrderCompletedDate()); //completed date ps1.setString(9, "" +
			 * myOrder.getOrderCompletedTime()); //completed hour time ps1.setString(10,
			 * myOrder.getGreeting()); //greeting ps1.setString(11,
			 * myOrder.getPaymentType()); //greeting ps1.setString(12, answerIsImmidate);
			 * //if customer want expedited delivery ps1.setInt(13,
			 * myOrder.getCompleteStatus()); //completedStauts , 0 = order not sent to
			 * customer, 1 = order did sent to customer ps1.executeUpdate(); ps1.close();
			 */

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // OrderID

		System.out.println("Data saved in customer order!!");

	}

	// ***********************************************************************************************************************************************************************************

	/**
	 * this method responsible to add new sales of catalog products
	 * @param OB class that contains id of branch, idd of product and amount of discount
	 * @param oldPrice original price of the product
	 * @return catalogitemsofbranch
	 */
	private catalogitemsofbranch editItemPriceInDB(PercentMSG OB, double oldPrice) 
	{

		int tempItemid = Integer.parseInt(OB.getItemId());
		double percent = Double.parseDouble(OB.getPercent());
		percent=percent/100;
		double newPrice = oldPrice - percent * oldPrice;
		String branchIDold=OB.getBranchID();
		boolean editOrAdd=false; // edit= true, add = false
		ArrayList<catalogitemsofbranch> catalogitemsofbranchFromDB = new ArrayList<catalogitemsofbranch>();
		try 
		{
			catalogitemsofbranchFromDB = PutOutAllCatalogItemsOfBranch(catalogitemsofbranchFromDB, branchIDold);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for ( int i=0; i< catalogitemsofbranchFromDB.size() ; i++)
		{
			if (catalogitemsofbranchFromDB.get(i).getBranchID().equals(branchIDold) && tempItemid == catalogitemsofbranchFromDB.get(i).getItemID() )
			{
				editOrAdd=true;
				break;
			}
		}
		if ( editOrAdd == false) //we will add
		{

			try 
			{

				PreparedStatement ps1 = ServerDataBase.prepareStatement("insert into catalogitemsofbranch (ItemID,BranchID,Price) values (?,?,?)");
				ps1.setInt(1, tempItemid);
				ps1.setString(2, branchIDold);
				ps1.setDouble(3, newPrice); // totalPrice

				ps1.executeUpdate();
				ps1.close();

			}

			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Cannot add to table of catalogitemsofbranch");

			} // OrderID


		}
		else	//we will edit
		{
			try 
			{
				Statement st = null;
				st = (Statement) ServerDataBase.createStatement();
				

				String sql = "UPDATE catalogitemsofbranch SET Price='" + newPrice + "' WHERE ItemID = '" + tempItemid + "' AND BranchID ='"+ branchIDold+ "'";
				st.executeUpdate(sql);
				st.close();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Cannot update sale");
			}
		}
		
		
		catalogitemsofbranch currentSale = new catalogitemsofbranch(tempItemid, branchIDold, newPrice);
		return currentSale;
		
		

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * saveOrderInDB method responsible to put order id and delivery id on customer transaction
	 * @param myOrder CustomerTransaction type with all details of order
	 * @return CustomerTransaction with id to order and to delivery
	 */
	private CustomerTransaction saveOrderInDB(CustomerTransaction myOrder) 
	{
		try {
			int randomOrderID = getRandomOrderIdFromDB();
			System.out.println("Your random orderID: " + randomOrderID);
			myOrder.setOrderID(randomOrderID); // put orderID for customer transaction
			myOrder.getOrderCustomerDelivery().setOrderID(randomOrderID); // put orderID in Delivery of transaction

		} catch (SQLException e) {
			System.out.println("Cannot find random orderID");
		}

		try {
			int randomDeliveryID = getRandomDeliveryIdFromDB();
			myOrder.getOrderCustomerDelivery().setDeliveryID(randomDeliveryID); // put deliveryID in Delivery of
																				// transaction
			System.out.println("Your random deliveryID: " + randomDeliveryID);

		} catch (SQLException e) {
			System.out.println("Cannot find random orderID");
		}

		// here we got OrderID and DeliveryID

		return null;
	}

	// ***********************************************************************************************************************************************************************************

	/**
	 * this method create a random id of delivery of order that not found in order table
	 * @return int, unique delivery id
	 * @throws SQLException if reading of the table failed
	 */
	private int getRandomDeliveryIdFromDB() throws SQLException 
	{
		Statement st = (Statement) ServerDataBase.createStatement();
		ArrayList<Integer> allDeliveryID = new ArrayList<Integer>();
		ResultSet rs = st.executeQuery("select * from customerorders ");

		while (rs.next()) // taking all orderID from customerOrder table
		{
			int currentDeliveryID = rs.getInt(3);
			allDeliveryID.add(currentDeliveryID);
		}
		rs.close();
		st.close();
		Random rand = new Random();
		int myRandomNum = rand.nextInt(99999998) + 1;
		while (allDeliveryID.contains(myRandomNum)) {
			myRandomNum = rand.nextInt(99999998) + 1;
		}

		return myRandomNum;
	}

	// ***********************************************************************************************************************************************************************************
	
	/**
	 * this method create a random id of order that not found in orders table
	 * @return int, unique order id
	 * @throws SQLException if reading of the table failed
	 */
	private int getRandomOrderIdFromDB() throws SQLException {
		Statement st = (Statement) ServerDataBase.createStatement();
		ArrayList<Integer> allOrdersID = new ArrayList<Integer>();
		ResultSet rs = st.executeQuery("select * from customerorders ");

		while (rs.next()) // taking all orderID from customerOrder table
		{
			int currentOrderID = rs.getInt(1);
			allOrdersID.add(currentOrderID);
		}
		rs.close();
		st.close();
		Random rand = new Random();
		int myRandomNum = rand.nextInt(99999998) + 1;
		while (allOrdersID.contains(myRandomNum)) {
			myRandomNum = rand.nextInt(99999998) + 1;
		}

		return myRandomNum;
	}

	// ***********************************************************************************************************************************************************************************
	
	/**
	 * this method check if account exist and if the branch id compatible between the one in database to the chosen one that customer picked
	 * @param myOrder customerTransaction order
	 * @param pA_userName username of payment account
	 * @param pA_Password password of payment account
	 * @param branchID id of branch that chosen
	 * @param dateOfOrder current date to check if the account not expired
	 * @return CustomerTransaction with message to client
	 * @throws SQLException if reading of payment account table failed
	 */
	private CustomerTransaction checkIfAccountOK(CustomerTransaction myOrder, String pA_userName, String pA_Password,
			String branchID, Date dateOfOrder)
			throws SQLException { /** this method check if payment account of order is ok */
		System.out.println("Server enterd to the method of checking account");
		System.out.println("User name: " + pA_userName + " Passowrd: " + pA_Password + " branchID: " + branchID);

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from paymentaccounts ");

		while (rs.next()) {
			String DB_PA_UserName = rs.getString(1);
			String DB_PA_Password = rs.getString(3);
			String DB_BranchID = rs.getString(11);
			java.sql.Date myDate = rs.getDate(12);
			String DB_subscriptionType = rs.getString(10);
			Date DB_PA_expDate = convertSqlDateToDateOfHaim(myDate);
			System.out.println(
					"" + DB_PA_expDate.getYear() + " " + DB_PA_expDate.getMounth() + " " + DB_PA_expDate.getDay());
			if (pA_userName.equals(DB_PA_UserName) && pA_Password.equals(DB_PA_Password)) {
				System.out.println("UserName and Password and branch approved");
				rs.close();
				st.close();
				if (!branchID.equals(DB_BranchID)) {

					myOrder.setApproved(false);
					myOrder.setMsgToClient("Your account is not belong to this branch");
					return myOrder;
				}

				else if (dateOfOrder.compareTo(DB_PA_expDate) == 1 || dateOfOrder.compareTo(DB_PA_expDate) == -1) {
					System.out.println("branch and date exp approved");
					String payTypeCustomerChoose = myOrder.getPaymentType();
					if (payTypeCustomerChoose.equals("Subscription")) {
						double priceAfter = myOrder.getOrdertotalPrice();

						if (DB_subscriptionType.equals("Monthly")) {
							priceAfter = priceAfter * 0.9;
						}

						if (DB_subscriptionType.equals("Yearly")) {
							priceAfter = priceAfter * 0.75;
						}
						myOrder.setOrderTotalPrice(priceAfter);
					}

					myOrder.setApproved(true);
					return myOrder;
				}

				else {
					myOrder.setApproved(false);
					myOrder.setMsgToClient("Your account is expierd");
					return myOrder;

				}
			}

		}
		rs.close();
		st.close();

		myOrder.setApproved(false);
		myOrder.setMsgToClient("You inserted wrong account details");
		return myOrder;
	}
	// ***********************************************************************************************************************************************************************************
	/**
	 * this method converts sql time to our project time class called MyTime
	 * @param time sql time type
	 * @return MyTime class
	 */
	private MyTime convertSqlTimeToTimeOfHaim(Time time) {
		String someTime = "" + time;
		String hour = someTime.substring(0, 2);
		String minute = someTime.substring(3, 5);
		String seconds = someTime.substring(6, someTime.length());
		MyTime haimTime = new MyTime(hour, minute, seconds);
		return haimTime;
	}
	/**
	 * this method converts sql date type to our project date class
	 * @param myDate sql type
	 * @return date class of our project
	 */
	private Date convertSqlDateToDateOfHaim(java.sql.Date myDate) 
	{ /*
									 * this method responsible for convert sql date type to our project date type
									 **/
		String someDate = "" + myDate;
		int year = Integer.parseInt(someDate.substring(0, 4));
		int mounth = Integer.parseInt(someDate.substring(5, 7));
		int day = Integer.parseInt(someDate.substring(8, someDate.length()));
		Date haimDate = new Date(year, mounth, day);
		return haimDate;
	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method return all catalog products on sale of specific branch
	 * @param catalogItemsFromDB empty arraylist of a branch
	 * @param branchID id of branch
	 * @return full array list of products on sale
	 */
	private ArrayList<CatalogItem> PutOutAllBranchCatalogItems(ArrayList<CatalogItem> catalogItemsFromDB,
			String branchID) { 
		Statement st = null;

		try {
			st = (Statement) ServerDataBase.createStatement();
			String sql = "SELECT * FROM catalogitemsofbranch WHERE BranchID = '" + branchID + "'";
			ResultSet rs = null;
			rs = st.executeQuery(sql);
			while (rs.next()) {

				int newItemID = rs.getInt(1);
				String newBranchID = rs.getString(2);
				double newItemPrice = rs.getDouble(3);
				for (int i = 0; i < catalogItemsFromDB.size(); i++) {
					int oldItemID = catalogItemsFromDB.get(i).getItemID();
					if (oldItemID == newItemID) // if the item in table catalogitemsofbranch exit, take the new price
					{
						catalogItemsFromDB.get(i).setItemPrice(newItemPrice);
						catalogItemsFromDB.get(i).setSale(true);
						break;
					}
				}

			}
			rs.close();
			st.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot update new price of branch");
		}
		;

		return catalogItemsFromDB;
	}

	// ****************************************************************************************************************************

	/**
	 * bring out all branch managers
	 * @param branchManagersFromDB empty array list of branch managers
	 * @return full array list of branch managers
	 * @throws SQLException SQLException if read from table failed

	 */
	private ArrayList<BranchManager> PutOutAllBranchManagers(ArrayList<BranchManager> branchManagersFromDB)
			throws SQLException {
		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from branchmanagers ");

		while (rs.next()) {
			int BranchManagerID = rs.getInt(1);
			String BranchManagerName = "" + rs.getString(2);
			String BranchManagerEmail = "" + rs.getString(3);
			String BranchID = rs.getString(4);

			BranchManager BranchManagerReturnToClient = new BranchManager(BranchManagerID, BranchManagerName,
					BranchManagerEmail, BranchID);
			branchManagersFromDB.add(BranchManagerReturnToClient);
			System.out.println("" + BranchManagerReturnToClient);

		}
		rs.close();
		st.close();

		return branchManagersFromDB;
	}

	/**
	 * method bring out all users branches
	 * @param branchesFromDB empty array list of branches
	 * @return full array list of branches
	 * @throws SQLException SQLException if read from table failed
	 */
	private ArrayList<Branch> PutOutAllBranches(ArrayList<Branch> branchesFromDB) throws SQLException {
		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from branches ");

		while (rs.next()) {
			String BranchID = "" + rs.getString(1);
			String BranchName = "" + rs.getString(2);
			String BranchAdress = "" + rs.getString(3);

			Branch BranchReturnToClient = new Branch(BranchID, BranchName, BranchAdress);
			branchesFromDB.add(BranchReturnToClient);
			System.out.println("" + BranchReturnToClient);

		}
		rs.close();
		st.close();

		return branchesFromDB;
	}

	// ***********************************************************************************************************************************************************************************

	// this method get all information from the DB and sends it to the comboBox of
	// the clientGUI
	/**
	 * this method bring out all users account 
	 * @param UsersFromDB empty array list of users
	 * @return full array list of users
	 * @throws SQLException if read from table failed
	 */
	private ArrayList<User> PutOutAllUsers(ArrayList<User> UsersFromDB) throws SQLException {

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from Users ");

		while (rs.next()) {
			int ID = rs.getInt(1);
			String UserName = "" + rs.getString(2);
			String Password = "" + rs.getString(3);
			String Permition = "" + rs.getString(4);
			int Status = rs.getInt(5);
			int Entry = rs.getInt(6);

			User UsersReturnToClient = new User(ID, UserName, Password, Permition, Status, Entry);
			UsersFromDB.add(UsersReturnToClient);

		}
		rs.close();
		st.close();

		return UsersFromDB;

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method finds all report in a table regardless to any branch
	 * @param ReportsFromDB empty arraylist of report clas
	 *@return full arraylist (if report exist)
	 * @throws SQLException error if read from table failed
	 */
	private ArrayList<Reports> PutOutAllReports(ArrayList<Reports> ReportsFromDB) throws SQLException {

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from reports ");

		while (rs.next()) {
			int ReportType = rs.getInt(1);
			String ReportYear = rs.getString(2);

			int ReportQuarter = rs.getInt(3);
			String  CsvFILE = rs.getString(4);
			String BranchID = rs.getString(5);

			Reports UsersReturnToClient = new Reports(ReportType, ReportYear, ReportQuarter, CsvFILE, BranchID);
			System.out.println(UsersReturnToClient);
			ReportsFromDB.add(UsersReturnToClient);

		}
		rs.close();
		st.close();

		return ReportsFromDB;

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method finds all report in a table of specific branch
	 * @param ReportsFromDB empty arraylist of report class
	 * @param mybranchid id of specifi branch
	 * @return full arraylist (if report exist)
	 * @throws SQLException error if read from table failed
	 */
	private ArrayList<Reports> PutOutAllBranchReports(ArrayList<Reports> ReportsFromDB, String mybranchid)
			throws SQLException {

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from reports where BranchID=" + mybranchid + "");

		while (rs.next()) {
			int ReportType = rs.getInt(1);
			String ReportYear = rs.getString(2);

			int ReportQuarter = rs.getInt(3);
			String  CsvFILE = rs.getString(4);
			String BranchID = rs.getString(5);

			Reports UsersReturnToClient = new Reports(ReportType, ReportYear, ReportQuarter, CsvFILE, BranchID);
			System.out.println(UsersReturnToClient);
			ReportsFromDB.add(UsersReturnToClient);

		}
		rs.close();
		st.close();

		return ReportsFromDB;

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method bring out all catalog products of specific branch that on sale 
	 * @param catalogitemsofbranchFromDB empty arraylist of catalog products
	 * @param mybranchid id of specific branch
	 * @return not empty array list (in case of exist products) of products on sale
	 * @throws SQLException if the read from the table failed
	 */
	private ArrayList<catalogitemsofbranch> PutOutAllCatalogItemsOfBranch(
			ArrayList<catalogitemsofbranch> catalogitemsofbranchFromDB, String mybranchid) throws SQLException {

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from catalogitemsofbranch where BranchID=" + mybranchid + "");

		while (rs.next()) {
			int ItemID = rs.getInt(1);
			String BranchID = rs.getString(2);

			double PriceID = rs.getDouble(3);

			catalogitemsofbranch UsersReturnToClient = new catalogitemsofbranch(ItemID, BranchID, PriceID);
			System.out.println(UsersReturnToClient);
			catalogitemsofbranchFromDB.add(UsersReturnToClient);

		}
		rs.close();
		st.close();

		return catalogitemsofbranchFromDB;

	}

	// ***********************************************************************************************************************************************************************************

	/**
	 * method that put out all data about catalog products from catalog table
	 * @param CatalogItemsFromDB empty arraylist of 
	 * @return CatalogItemsFromDB
	 * @throws SQLException exception when sql fail
	 */
	private ArrayList<CatalogItem> PutOutAllCatalogItems(ArrayList<CatalogItem> CatalogItemsFromDB)
			throws SQLException {

		Statement st = (Statement) ServerDataBase.createStatement();

		ResultSet rs = st.executeQuery("select * from catalogitems ");

		while (rs.next()) {
			// get normal values from catalogitem table
			int ItemID = rs.getInt(1);
			String ItemName = "" + rs.getString(2);
			String ItemType = "" + rs.getString(3);
			String ItemDescription = "" + rs.getString(4);
			// end to get normal values from catalogitem table

			// from now we get file from longblob column
			String userDir = System.getProperty("user.dir");
			userDir = userDir + "" + "\\ZerLiProject_G13\\Zer-Li\\src\\Catalog\\catalogItemsImages\\" + ItemID + ".jpg";
			File image = new File(userDir);
			FileOutputStream fos = null;

			try {
				fos = new FileOutputStream(image);

			} catch (FileNotFoundException e) {
				System.out.println("Cannot create FileOutputStream");
			}

			Blob getSizeFile = (Blob) rs.getBlob(5);
			byte[] buffer = getSizeFile.getBytes(1, (int) getSizeFile.length());

			// byte[] buffer = new byte[102017];
			InputStream is = rs.getBinaryStream(5);
			try {
				while (is.read(buffer) > 0) {
					fos.write(buffer);

				}
				fos.close();

				MyFile ItemImage = createFile(userDir);
				double price = rs.getDouble(6);
				CatalogItem SingleCatalogItem = new CatalogItem(ItemID, ItemName, ItemType, ItemDescription, ItemImage,
						price);
				CatalogItemsFromDB.add(SingleCatalogItem);

			}

			catch (IOException e) {
				System.out.println("Cannot read buffer");
			}

		}

		rs.close();
		st.close();

		return CatalogItemsFromDB;

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * this method change flag of users, and making to server to not allow simultaneously login of user account
	 * @param userName string user name account
	 */
	public synchronized void changeEntryInDB(String userName) {
		Statement st = null;

		try {
			st = (Statement) ServerDataBase.createStatement();
			String sql = "SELECT entry FROM users WHERE UserName = '" + userName + "'";
			ResultSet rs = null;
			rs = st.executeQuery(sql);
			while (rs.next()) {

				int UserEntry = rs.getInt(1);
				if (UserEntry == 1)// logout - change the entry from 1 to 0
				{
					String insertTableSQL1 = "UPDATE users SET Entry = " + 0 + " WHERE UserName='" + userName + "';";
					st.executeUpdate(insertTableSQL1);

				} else// login - change the entry from 0 to 1
				{
					String insertTableSQL2 = "UPDATE users SET Entry = " + 1 + " WHERE UserName='" + userName + "';";
					st.executeUpdate(insertTableSQL2);

				}

				rs.close();
				st.close();
				break; // break the loop to prevent crash
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot update entry");
		}
		;
	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * method that delete row in catalog table
	 * @param ItemID specific id of product
	 */
	public synchronized void deleteItemInDB(int ItemID) {
		Statement st = null;

		try {
			st = (Statement) ServerDataBase.createStatement();
			String sqlDeleteRow = "DELETE FROM catalogitems WHERE ItemID = " + ItemID + ";";

			st.executeUpdate(sqlDeleteRow);
			st.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot DELETE item");
		}
		;
	}

	// ***********************************************************************************************************************************************************************************
   /**
    * this method write  a file to server hard disk
    * @param bFile array bytes
    * @param fileDest the path on server hard disk
    */
	private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {

        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
	// ***********************************************************************************************************************************************************************************
	/**
	 * this method add new catalog item to catalog table in database
	 * @param givenItem class catalog item
	 */
    public synchronized void addItemInDB(CatalogItem givenItem) {
		
		System.out.println("we got here -< addItemDB");


		try {
			PreparedStatement ps1 = ServerDataBase.prepareStatement(
					"insert into catalogitems (ItemID,ItemName,ItemType,Description,Photo,Price) values (?,?,?,?,?,?)");

			// put new row in catalogitems table!!
	
			ps1.setInt(1, givenItem.getItemID());
			ps1.setString(2, givenItem.getItemName());
			ps1.setString(3, givenItem.getItemType());
			ps1.setString(4, givenItem.getItemDescription());
			InputStream inputStream = null;
			
    		byte[] bytes = givenItem.getItemPhoto().mybytearray;
    		
    		String userDir = System.getProperty("user.dir");
			userDir = userDir + "" + "\\ZerLiProject_G13\\Zer-Li\\src\\client\\image\\"+givenItem.getItemID()+".jpg";   		
    		writeBytesToFileClassic(bytes, userDir);
			
			
			try {
				inputStream = new FileInputStream(new File(userDir));
				ps1.setBlob(5, inputStream);
				ps1.setDouble(6, givenItem.getItemPrice());

			} catch (FileNotFoundException e) {
				System.out.println("Can't create inputStream");
			}

			ps1.executeUpdate();

			ps1.close();
			// statementquery.close();
			// rs.close();
		}

		catch (SQLException e) // if the adressing to the table failed
		{
			System.out.println("fail to add item!!!");

			// return "catalog's item insertion failed!!\n"; // message
		}

		// return "catalog's item inserted to the catalogItems table successfully!!\n";
		
		

	}

	// ***********************************************************************************************************************************************************************************

	/**
	 * this method edit item in catalog, first deletes its row, and then inserts the updated row
	 * @param givenItem class catalogItem
	 */
	public synchronized void editItemInDB(CatalogItem givenItem) {

		deleteItemInDB(givenItem.getItemID());
		addItemInDB(givenItem);
	}

	// ***********************************************************************************************************************************************************************************

	/**
	 * check if specific catalog item id exit in catalog table
	 * @param ItemID id of product
	 * @return boolean false = there is no similar id, true = there is a similar id
	 */
	public synchronized boolean checkUniqueIDInDB(int ItemID) {
		Statement st = null;

		try {
			st = (Statement) ServerDataBase.createStatement();
			String sqlSelectID = "SELECT ItemID FROM catalogitems WHERE ItemID =" + ItemID + ";";
			ResultSet rs = null;
			rs = st.executeQuery(sqlSelectID);

			while (rs.next()) // there is the same id in DB
			{
				// int IDinDB = rs.getInt(1); // *******i can delete this
				// System.out.println(""+IDinDB); // *******i can delete this
				return false;
			}
			rs.close();
			st.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("failed to find answer");
		}
		;

		return true; // unique id
	}

	// ***********************************************************************************************************************************************************************************

	// Add New Payment Account to the data base
	/**
	 * method that create new row in databse at table of payment account
	 * @param PA1 payment account class
	 * @throws SQLException error if connection/insertion to database failed
	 */
	public synchronized void AddNewPaymentAccount(Object PA1) throws SQLException {
		try {
			PaymentAccount PA = (PaymentAccount) PA1;

			Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if table filled

			PreparedStatement ps1 = ServerDataBase
					.prepareStatement("INSERT INTO paymentaccounts VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

			ps1.setString(1, PA.getUserName());
			ps1.setInt(2, PA.getCustomerID());
			ps1.setString(3, PA.getPassword());
			ps1.setString(4, PA.getPhoneNumber());
			ps1.setString(5, PA.getPaymentType());
			ps1.setString(6, PA.getCreditCardNum());
			ps1.setString(7, PA.getCreditCardExpDate());
			ps1.setInt(8, PA.getCvvCreditCardNum());
			ps1.setString(9, PA.getCreditCardType());
			ps1.setString(10, PA.getSubscriptionType());
			ps1.setString(11, PA.getBranchID());
			ps1.setString(12, PA.getExpAccountDate());

			ps1.executeUpdate();
			ps1.close();

			statementquery.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot create st");
		}
		;
	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * Add Survey Information to the data base
	 * @param OB object of satisfactionSurvey -includs the details of the survey
	 * @throws SQLException error if connection/insertion to database failed
	 */
	public synchronized void SavesatisfactionSurveyInfo(Object OB) throws SQLException {
		satisfactionSurvey mysatSurvey = (satisfactionSurvey) OB;
		Statement st = null;
		try {
			st = (Statement) ServerDataBase.createStatement();

			String sql = "update satisfactionsurvies set Step=" + 1 + " , Q1='" + mysatSurvey.getQ1() + "', Q2='"
					+ mysatSurvey.getQ2() + "' ,Q3='" + mysatSurvey.getQ3() + "', Q4='" + mysatSurvey.getQ4() + "' ,Q5="
					+ mysatSurvey.getQ5() + ", Q6='" + mysatSurvey.getQ6() + "'  where SurviesYear='"
					+ mysatSurvey.getSurveyYear() + "'  and SurviesQuarter=" + mysatSurvey.getQarSurvey() + "  ";
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot update entry");
		}
		;

	}

	/**
	 *console message about connection of server
	 */
	// ***************************************************
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	// ***************************************************
	/**
	 *console message about abort of server
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}


	// ***********************************************************************************************************************************************************************************

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	/**
	 * getter of successful / unsuccessful connection to database
	 * @return boolean  true = successful connection , false = unsuccessful connection
	 */
	public boolean getStatusDBLogin() {
		return this.DB_ACCOUNT;
	}

	// ***********************************************************************************************************************************************************************************
	
	/**
	 * prepare default catalog
	 * @return string confirmation to server text area
	 */
	public String prepareCatlog() {
		// here we prepare to take the images from their directory, it is not important
		// where the project located, the code will find the images anyway
		String userDir = System.getProperty("user.dir");
		userDir = userDir + "" + "\\ZerLiProject_G13\\Zer-Li\\src\\client\\image";
		System.out.println("" + userDir);
		String Photo1Path = "" + userDir + "\\BridalBouquet.jpg";
		String Photo2Path = "" + userDir + "\\flowerArrangement.jpg";
		String Photo3Path = "" + userDir + "\\ClusterFlowers.jpg";
		String Photo4Path = "" + userDir + "\\FloweringPlant.jpg";
		String Photo5Path = "" + userDir + "\\BridalBouquet2.jpg";
		String Photo6Path = "" + userDir + "\\ClusterFlowers2.jpg";
		String Photo7Path = "" + userDir + "\\ClusterFlowers3.jpg";
		String Photo8Path = "" + userDir + "\\flowerArrangement2.jpg";
		String Photo9Path = "" + userDir + "\\flowerArrangement3.jpg";
		String Photo10Path = "" + userDir + "\\FloweringPlant2.png";

		ArrayList<CatalogItem> ListOfItemToPutInMySQL = new ArrayList<CatalogItem>();

		// here we create new catalog items, and put it into arraylist
		CatalogItem itemNo1 = new CatalogItem(11111, "BestBrideFlowers", "Bridal Bouquet",
				"Bouquet for christian's weddings", createFile(Photo1Path), 39.25);
		CatalogItem itemNo2 = new CatalogItem(22222, "The best of all bouqets", "Flower Arrangement",
				"Bouquet for special ceremonies", createFile(Photo2Path), 78.22);
		CatalogItem itemNo3 = new CatalogItem(33333, "The bouqets of the stingy people", "Cluster Flowers",
				"Bouquet for people who has not too\nmuch money", createFile(Photo3Path), 21.74);
		CatalogItem itemNo4 = new CatalogItem(44444, "Beautifull flowers in big\nplant", "Flowering Plant",
				"Plants for big gardens", createFile(Photo4Path), 150);
		CatalogItem itemNo5 = new CatalogItem(55555, "The bouqets of weddings ", "Bridal Bouquet",
				"Bouquet for all weddings", createFile(Photo5Path), 55.65);
		CatalogItem itemNo6 = new CatalogItem(66666, "The bouqet of all bouqets", "Cluster Flowers",
				"Bouquet for grand parties", createFile(Photo6Path), 43.22);
		CatalogItem itemNo7 = new CatalogItem(77777, "The bouqets of children", "Cluster Flowers",
				"Bouquet for babies or little\nchildren", createFile(Photo7Path), 111.22);
		CatalogItem itemNo8 = new CatalogItem(88888, "The bouqets women", "Flower Arrangement", "Bouqets for old women",
				createFile(Photo8Path), 77.2);
		CatalogItem itemNo9 = new CatalogItem(99999, "The bouqets of men", "Flower Arrangement", "Bouquet for old men",
				createFile(Photo9Path), 21.74);
		CatalogItem itemNo10 = new CatalogItem(11110, "The Sad flowers", "Flowering Plant", "Plants for funreals",
				createFile(Photo10Path), 150);
		ListOfItemToPutInMySQL.add(itemNo1);
		ListOfItemToPutInMySQL.add(itemNo2);
		ListOfItemToPutInMySQL.add(itemNo3);
		ListOfItemToPutInMySQL.add(itemNo4);
		ListOfItemToPutInMySQL.add(itemNo5);
		ListOfItemToPutInMySQL.add(itemNo6);
		ListOfItemToPutInMySQL.add(itemNo7);
		ListOfItemToPutInMySQL.add(itemNo8);
		ListOfItemToPutInMySQL.add(itemNo9);
		ListOfItemToPutInMySQL.add(itemNo10);

		try {

			Statement statementquery = (Statement) ServerDataBase.createStatement(); // query to check if table filled
			ResultSet rs = statementquery.executeQuery("select * from catalogitems ");
			while (rs.next()) // here we check if the table already filled
			{
				statementquery.close();
				rs.close();
				return "You already inserted the items to the catalog!\n"; //// message to put in the gui
			}

			PreparedStatement ps1 = ServerDataBase.prepareStatement(
					"insert into catalogitems (ItemID,ItemName,ItemType,Description,Photo,Price) values (?,?,?,?,?,?)");
			PreparedStatement ps2 = ServerDataBase.prepareStatement("insert into itemstypes (categorytype) values (?)");
			for (int row = 0; row < ListOfItemToPutInMySQL.size(); row++) {
				// this loop put new rows in catalogitems table!!
				CatalogItem itemFromCatalog = ListOfItemToPutInMySQL.get(row);
				ps1.setInt(1, itemFromCatalog.getItemID());
				ps1.setString(2, itemFromCatalog.getItemName());
				ps1.setString(3, itemFromCatalog.getItemType());
				ps1.setString(4, itemFromCatalog.getItemDescription());
				InputStream inputStream = null;
				String filePath = itemFromCatalog.getItemPhoto().getFileName();
				try {
					inputStream = new FileInputStream(new File(filePath));
					ps1.setBlob(5, inputStream);
					ps1.setDouble(6, itemFromCatalog.getItemPrice());

				}

				catch (FileNotFoundException e) {
					System.out.println("Can't create inputStream");
				}
				if (row < 4) {
					ps2.setString(1, itemFromCatalog.getItemType());
					ps2.executeUpdate(); // safe close of the statements
				}
				ps1.executeUpdate();

			}
			ps1.close();
			ps2.close();
			statementquery.close();
			rs.close();
		}

		catch (SQLException e) // if the adressing to the table failed
		{
			System.out.println(e.getMessage());

			return "Deafult catalog's item insertion failed!!\n"; // message to put in the gui
		}

		return "Deafult catalog's item inserted to the catalogItems table successfully!!\n"; //// message to put in the
																								//// gui

	}

	// ***********************************************************************************************************************************************************************************
	/**
	 * create a file of any format
	 * @param path the path to to file on hard disk 
	 * @return MyFile class, contains byte array
	 */
	private MyFile createFile(String path) {
		MyFile fileToCreate = new MyFile(path);

		String LocalfilePath = path;

		try {

			File newFile = new File(LocalfilePath);

			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			fileToCreate.initArray(mybytearray.length);
			fileToCreate.setSize(mybytearray.length);
			bis.read(fileToCreate.getMybytearray(), 0, mybytearray.length);
			return fileToCreate;

		} catch (Exception e) {
			System.out.println("Can't create file");
		}
		return null;

	}

	// ***********************************************************************************************************************************************************************************
	/**connect To the database Method
	 * @param UserName userName to enter mysql
	 * @param Password password to enter mysql
	 * @param DataBaseName the database name
	 * @return	 Connection on success
	 */
	protected Connection connectToDB(String UserName, String Password, String DataBaseName) {
		Connection ServerDataBase = null;
		try {
			String DataBaseAdress = "jdbc:mysql://localhost:3306/" + DataBaseName;
			ServerDataBase = DriverManager.getConnection(DataBaseAdress, UserName, Password);
			System.out.print("Server connected to Database sucessfully!\n");
			this.DB_ACCOUNT = true;

		} catch (SQLException ex) {
			System.out.print("Sorry we had a problem, could not connect to DB server\n");
			this.sendToAllClients("DBConnectFail");
			this.DB_ACCOUNT = false;
		}
		return ServerDataBase;

	}
}