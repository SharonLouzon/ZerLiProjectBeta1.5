package Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import Users.LoginContol;
import client.ChatClient;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * this is controller of cancel window
 * @author haim hadad
 */
public class CancelOrderControl extends LoginContol implements Initializable
{
	public static ObservableList<CustomerTransaction> allCustomerOrder= FXCollections.observableArrayList();

	   @FXML
	    private Button btnLogout;

	    @FXML
	    private Button btnCatalog;

	    @FXML
	    private ImageView imgController;

	    @FXML
	    private Label basketStatusLabel;

	    @FXML
	    private Button btnAccount;

	    @FXML
	    private Label branchLabelAtCatalog;

	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button btnCart;

	    @FXML
	    private Label branchLabelAtCancel;
	
	    @FXML
	    private Button customizeBtn;
	    
	    @FXML
	    private TableView<CustomerTransaction> ordersTable;
	    
	    @FXML
	    private TableColumn<CustomerTransaction, Integer> orderIDColmun;
	    
	    @FXML
	    private TableColumn<CustomerTransaction, String> branchNameColmun;

	    @FXML
	    private TableColumn<CustomerTransaction, String> supplyDateColmun;

	    @FXML
	    private TableColumn<CustomerTransaction, String> supplyHourColmun;
	     
	    @FXML
	    private TableColumn<CustomerTransaction, String> priceColmun;
	    
	    
	    @FXML
	    private Button cancelOrderBtn;
	    
	    /**
	     * this method will be called by client after server confirmation, and will tell customer about his refunds
	     * @param orderCancellation   kind of message from server that tell about amount of refund and if id updated in db
	     */
	    public void actionAfterPositiveAbort(TransactionAbort orderCancellation)
	    {	
	    	if(orderCancellation.getRefund() == 0)
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Your order has been canceled");
		    	alert.setHeaderText("Your order cancel successfully!");
		    	alert.setContentText("You will get not refund");
		    	alert.showAndWait();
	    	}
	    	
	    	else if(orderCancellation.getRefund() == 0.5)
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Your order has been canceled");
		    	alert.setHeaderText("Your order canceled successfully!");
		    	alert.setContentText("You will 50% refund, your debt is: "+orderCancellation.getOrderPrice()*orderCancellation.getRefund()+" $");
		    	alert.showAndWait();
	    	}
	    	else if(orderCancellation.getRefund() == 1)
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Your order has been canceled");
		    	alert.setHeaderText("Your order canceled successfully!");
		    	alert.setContentText("You will 100% refund");
		    	alert.showAndWait();
	    	}
	    	
	    	for(int i =0 ; i<this.allCustomerOrder.size() ; i++)
	    	{
	    		if(this.allCustomerOrder.get(i).getOrderID() == orderCancellation.getOrderID())
	    		{
	    			this.allCustomerOrder.remove(i);
	    		}
	    	}
	    }
	    
	    
	    
	    /**
	     * method that work when customer ask to cancel order (press on button)
	     * @param event click event
	     */
	    @FXML
	    void cancelOrderBtnPressed(ActionEvent event) 
	    {
	    	ObservableList<CustomerTransaction> orderInRow = ordersTable.getSelectionModel().getSelectedItems();
	    	if(orderInRow.isEmpty())
	    	{
	    		Alert incorrectImageAlert = new Alert(AlertType.WARNING);
	    		incorrectImageAlert.setTitle("No selected order");
	    		incorrectImageAlert.setHeaderText("You didn't select row in the table");
	    		incorrectImageAlert.setContentText("Please select row in the table");
	    		incorrectImageAlert.showAndWait();
	    		return;
	    	} 
	    	TransactionAbort cancelOrder = calculateRefund(orderInRow.get(0));
	    	System.out.println(""+cancelOrder);
		  	int port = LoginContol.PORT;
			String ip = LoginContol.ServerIP;
			try 
			{
				myClient = new ChatClient(ip, port);
			} 
			catch (IOException e)
			{
				e.printStackTrace();
				System.out.println("Cannot connect to server");
			} // create new client
	    	myClient.setCancelControl(this);
	    	myClient.sendRequestToCancelOrder(cancelOrder);
	    	
	    }
	    
	    /**
	     * this method calculates the amount of refund, it called automatic but the controller
	     * @param order this type is a customer order, with some part of the transaction in the database, only data that important will be here, such as orderID, price, date of supply, hour of supply and so on....
	     * @return kind of message from client to server that tell about amount of refund and it contains a boolean attribute that will tell later about if the update in db been made
	     */
	    private TransactionAbort calculateRefund(CustomerTransaction order) 
	    {
	    	LocalDateTime now = LocalDateTime.now();
	    	Date supplyDate = order.getOrdersupplyDate();
	    	MyTime supplyHour = order.getOrdersupplyTime();
	    	int yearSupply = supplyDate.getYear();
	    	int mounthSupply = supplyDate.getMounth();
	    	int daySupply = supplyDate.getDay();
	    	Integer hourSupply = new Integer(supplyHour.getHour());
	    	Integer minutesSupply = new Integer(supplyHour.getMinutes());
	    	Integer secondsSupply = new Integer(supplyHour.getSeconds());

	    	LocalDateTime fromDateTime = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
	    	LocalDateTime toDateTime = LocalDateTime.of(yearSupply, mounthSupply, daySupply, hourSupply, minutesSupply, secondsSupply);

	    	LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );

	    	long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
	    	tempDateTime = tempDateTime.plusYears( years );

	    	long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
	    	tempDateTime = tempDateTime.plusMonths( months );

	    	long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
	    	tempDateTime = tempDateTime.plusDays( days );


	    	long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
	    	tempDateTime = tempDateTime.plusHours( hours );

	    	long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
	    	tempDateTime = tempDateTime.plusMinutes( minutes );

	    	long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);

	    	System.out.println( years + " years " + months + " months " +  days + " days " + hours + " hours " +  minutes + " minutes " +   seconds + " seconds.");
	    	if(years>0 || months>0 || days>0 || hours>3) 
	    	{
	    		TransactionAbort customerRefund = new TransactionAbort(order.getOrderID(),order.getOrdertotalPrice(), 1); //give 100% refund
		    	return customerRefund;

	    	}
	    	
	    	else if (years == 0 && months == 0 && days ==0 && hours>=1 && hours< 3)
	    	{
	    		TransactionAbort customerRefund = new TransactionAbort(order.getOrderID(),order.getOrdertotalPrice(), 0.5); //give 50% refund
		    	return customerRefund;

	    	}
	    	
	    	else if(years == 0 && months == 0 && days ==0 && hours>=0)
	    	{
	    		TransactionAbort customerRefund = new TransactionAbort(order.getOrderID(),order.getOrdertotalPrice(), 0); //give 0% refund
		    	return customerRefund;

	    	}
	    	//it working
	    	return null;
		}

	    /**
	     *  this method closes the current window and open the customize buy window
	     * @param event event of click on button in the menu
	     */
		@FXML
	    void customizeBtnPressed(ActionEvent event) 
	    {
	    	Stage primaryStage = new Stage();
	       	CustomOrderControl aFrame = new CustomOrderControl();
	  	  
	 			
	 				try 
	 				{
						aFrame.start(primaryStage);
					} 
	 				catch (Exception e) 
	 				{
						System.out.println("Cannot open customize window");
					}
	 			
	 				customizeBtn.getScene().getWindow().hide(); //hiding primary window
	    }

		/**
		 * this method will close current window, and will open the main window of buying process
		 * @param event event of click on a button
		 */
	    @FXML
	    void goHome(ActionEvent event) 
	    {
	    	btnHome.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			CustomerMainWindow aFrame = new CustomerMainWindow();
			try 
			{
				aFrame.start(primaryStage);
			} catch (IOException e) {
				System.out.println("Cannot start Customer main Window");
			}
	    }

	    /**
	     * this method opens the account details window of the customer, we had no time to write it.....
	     * @param event event type of click on a button
	     */
	    @FXML
	    void seeAccount(ActionEvent event) 
	    {
	    	btnAccount.getScene().getWindow().hide(); // hiding primary window

			Stage primaryStage = new Stage();
			AccountControl aFrame = new AccountControl();
			try 
			{
				aFrame.start(primaryStage);
			} 
			catch (IOException e) {
				System.out.println("Cannot start Account Window");
			}
	    }

	    /**
	     * this method close all user window and will get him out from the system, it will send message to server to change the flag (that prevent a second login)
	     * @param event	action even of click on button
	     */
	    @FXML
	    void logoutEvent(ActionEvent event) 
	    {
	    	changeEntry(UserNameToCheck);
			CustomerMainWindow.chosenBranchID="";
			CustomerMainWindow.chosenBranchName="";
			System.out.println("return to main menu"); 
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
			LoginContol aFrame = new LoginContol(); // create Login Frame
			Stage arg0 = new Stage();
			try {
				aFrame.start(arg0);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				System.out.println("Cannot commit logout");
			}
	    }

	    /**
	     * this method will bring customer to the cart, that will show him items in order, and will give him a chance to create new order
	     * @param event of click on a button
	     */
	    @FXML
	    void btnCartPressed(ActionEvent event) 
	    {
	    	btnCart.getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			OrdersControl aFrame = new OrdersControl();
			try 
			{
				aFrame.start(primaryStage);
			} catch (IOException e) {
				System.out.println("Cannot start Cart Window");
			}
	    }

	    /**
	     * this method will open the catalog of specific branch
	     * @param event click  on a button
	     */
	    @FXML
	    void btnCatalogPressed(ActionEvent event) 
	    {
	    	System.out.println("I got the event of catalog button");	  

	 	   	Stage primaryStage = new Stage();
	 	   CatalogOrderControl aFrame = new CatalogOrderControl();
	 	  
				try 
				{
					aFrame.start(primaryStage);
				} 
				catch (IOException e) 
				{
					System.out.println("Cannot start catalog Window");
				}
		    	btnCatalog.getScene().getWindow().hide(); //hiding primary window	
	    }

	

	/**
	 * this method will open the cancel of order window
	 */
	public void start(Stage primaryStage)  
	{		
		Parent root;
		try 
		{
			root = FXMLLoader.load(getClass().getResource("/Customer/CancelOrderFrameWindow.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Cancel Order request"); // name of the title of the window
			primaryStage.setScene(scene);
		  	primaryStage.show();
		  	int port = LoginContol.PORT;
			String ip = LoginContol.ServerIP;
			myClient = new ChatClient(ip, port); // create new client
			//myClient.sendRequestToGetAllBranchManagers();
			this.allCustomerOrder.clear();
			myClient.sendRequestToGetAllCustomerOrder(LoginContol.userID,CustomerMainWindow.chosenBranchID, CustomerMainWindow.chosenBranchName);
			//Can't close the window without logout
			primaryStage.setOnCloseRequest( event -> {event.consume();} );
		} 
		catch (IOException e) 
		{
			System.out.println("Cannot open cancel window");
		}
		
	}

	/**
	 * this method will load all previous customer orders that still active (active = not supplied)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		branchLabelAtCancel.setText("Your branch: "+CustomerMainWindow.chosenBranchName);
		branchLabelAtCancel.setVisible(true);
		
		orderIDColmun.setCellValueFactory(new PropertyValueFactory<CustomerTransaction, Integer>("OrderID"));
		    
		branchNameColmun.setCellValueFactory(new PropertyValueFactory<CustomerTransaction, String>("branchName"));
		    	    
		supplyDateColmun.setCellValueFactory(new PropertyValueFactory<CustomerTransaction, String>("supplyDateStr"));
		    
		supplyHourColmun.setCellValueFactory(new PropertyValueFactory<CustomerTransaction, String>("supplyTimeStr"));
		
		priceColmun.setCellValueFactory(new PropertyValueFactory<CustomerTransaction, String>("orderPriceWithCoin"));

		   
		orderIDColmun.setStyle( "-fx-alignment: CENTER;");
		branchNameColmun.setStyle( "-fx-alignment: CENTER;");
		supplyDateColmun.setStyle( "-fx-alignment: CENTER;");
		supplyHourColmun.setStyle( "-fx-alignment: CENTER;");
		priceColmun.setStyle( "-fx-alignment: CENTER;");
		ordersTable.setItems(allCustomerOrder);
		System.out.println(""+allCustomerOrder);

		
	}
}
