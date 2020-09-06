package Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import Users.LoginContol;
import client.ChatClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * window of creating customer order
 * @author haim hadad
 *
 */
public class OrdersControl extends LoginContol implements Initializable
{
	public static ObservableList<ItemInOrder> ItemsInOrderList= FXCollections.observableArrayList();
	public static ObservableList<String> oneBranchName= FXCollections.observableArrayList();
	private static int totalQuantity=0;
	private static double totalPrice=0;
	private static boolean checkboxFilled=false;
	private static String textGreeting="";
	private static Date  supplyTimeDate  ;
	private static MyTime supplyTime;
	private static String selfArrivalBranch="";
	private static boolean expeditedSupplying=false;
	private static Delivery myShipment;	
	private ObservableList<String> hourList = FXCollections.observableArrayList();
	private ObservableList<String> MinutesList = FXCollections.observableArrayList();
	private LocalDateTime now;
	private boolean isPrivateShipment=false;
   
	@FXML
    private TableView<ItemInOrder> ItemInOrderTable;		//screen1
	
    @FXML
    private TableColumn<ItemInOrder, Integer> itemInOrderIDcolmun;  //screen1
    
    @FXML
    private TableColumn<ItemInOrder, String> itemInOrderNameColmuns;	//screen1
    
    @FXML
    private TableColumn<ItemInOrder, Integer> itemInOrderQtyColmun;		//screen1
    
    @FXML
    private TableColumn<ItemInOrder, String> itemInOrderPriceColumns;	//screen1
    @FXML
    private  TextArea txtGreeting;	//screen1

    @FXML
    private Button logoutBtn;

    @FXML
    private Button accountBtn;

    @FXML
    private Button btnCatalog;

    @FXML
    private CheckBox AddGreetingChkBox; 	//screen1
   
    @FXML
    private Button goToDelivery;	//screen1


    @FXML
    private Button btnHome;

    @FXML
    private Label totalPriceLabel;	//screen1
    
    @FXML
    private Label totalProductsAmountLabel; //screen1
    
    @FXML
    private Label yourinvoiceLabel; //screen1
    
    @FXML
    private Label YourcartLabel; //screen1
    
    @FXML
    private Label DeliverLabel; 

    @FXML
    private Label CheckoutLabel;
    
    @FXML
    private Label messageAfterGreeting; //screen1

    @FXML
    private Button BackToCartBtn; //screen2
    
    @FXML
    private Button goToCheckoutBtn; //screen2
    

    @FXML
    private Label dateLabel; //screen2
    
    @FXML
    private Label SupplyTimeLabel; //screen2
    
    @FXML
    private Label ShipmentLabel; //screen2
    
    @FXML
    private Label HourLabel; //screen2

    @FXML
    private DatePicker ComboDate;  //screen2
    
    
    @FXML
    private ComboBox<String> comboBoxHour;  //screen2
    
    @FXML
    private RadioButton branchRadio;  //screen2
    
    @FXML
    private RadioButton privateAdressRadio;  //screen2
    
    @FXML
    private Label aditionalCostLabel;	//screen2
    
    @FXML
    private Label adressShipmentLabel;	//screen2
    
    @FXML
    private Label adresseeShipmentLabel;	//screen2
    
    @FXML
    private Label phoneNumberShipmentLabel;	//screen2
    
    @FXML
    private TextField phoneNumberTxt;	//screen2

    @FXML
    private TextField adressShipmentTxt;	//screen2
    
    @FXML
    private TextField adresseeShipmentTxt;	//screen2
    
    @FXML
    private ComboBox<String> KidometPhone;	//screen2
    
    @FXML
    private ComboBox<String> comboBranch;	//screen2
    
    @FXML
    private Label makafKidometNumPhone; 	//screen2
    

    @FXML
    private ToggleGroup delivery;			//screen 2
    
    @FXML
    private Button backToDeliveryBtn;		//screen 3
    
    
    @FXML
    private Label CheckotFinalMainLabel;	//screen 3
    
    
    @FXML
    private Label PA_userNameLabe;			//screen 3
    
    
    @FXML
    private TextField PA_userName_txt;		//screen 3
    
    
    @FXML
    private Label PA_PasswordLabe;			//screen 3
    
    @FXML
    private TextField PA_password_txt;		//screen 3
    
    @FXML
    private Label payMethodLabel;			//screen 3
    
    
    @FXML
    private ComboBox<String> payMethodcomboBox;	//screen 3
    
    @FXML
    private Button payButton;					//screen 3
    
    @FXML
    private Label totalPriceResult;			//screen 3

    
    /**
     * this methos return to deliver screen from payment screen
     * @param event on click
     */
    @FXML
    void backToDeliveryBtnPressed(ActionEvent event) 
    {
    	ShowScreenTWO();	//show screen number 3

    }
    
    /**
     * this method transfer transaction details to server in order to approve the order
     * @param event on click
     */
    @FXML
    void payButtonPressed(ActionEvent event) 
    {	/**this method responsible to prepare the order to be saved on the db, it will send also the details of payment account to commit verification*/
    	String PAUserName="";
    	String PAPassword="";
    	String PAMethod="";
    	PAUserName = PA_userName_txt.getText();
    	PAPassword = PA_password_txt.getText();
    	PAMethod = payMethodcomboBox.getValue();
    	if(PAUserName.equals("") || PAPassword.equals("") || payMethodcomboBox.getValue() == null)	//check if customer did not forget to insert details of payment
    	{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("You missed some details");
    		alert.setHeaderText("Please fill again your details!");
    		alert.showAndWait();
    		return;
    	}
    	//if everything ok, we continue to collect order details
    	CustomerTransaction newDeal = new CustomerTransaction();		//prepare order
    
    	
    	ArrayList<ItemInOrder> itemsToBuy =new ArrayList<ItemInOrder>();
    	itemsToBuy = fillInItemsToBuy(itemsToBuy);
    	newDeal.setProductsList(itemsToBuy);
    	newDeal.setPaymentAccountPassword(PAPassword);
    	newDeal.setPaymentAccountUserName(PAUserName);
    	newDeal.setIsExpeditedDelivery(this.expeditedSupplying);
    	newDeal.setPaymentType(PAMethod);
    	System.out.println("your greeting in client is   ==>" + this.textGreeting);
    	newDeal.setGreeting(this.textGreeting);
    	LocalDateTime now = LocalDateTime.now();
    	String nowCompletedHour=""+now.getHour();
    	String nowCompletedMinute=""+now.getMinute();
    	String nowCompletedSeconds=""+now.getSecond();
    	MyTime completedTime = new MyTime(nowCompletedHour, nowCompletedMinute, nowCompletedSeconds); //time of committing a deal
    	newDeal.setOrderCompletedTime(completedTime);
    	Date CompletedDateOrder = new Date(now.getYear(), now.getMonthValue(), now.getDayOfMonth()); //date of committing a deal
    	newDeal.setOrderCompletedDate(CompletedDateOrder);
    	MyTime supplyTimeOrder;
		try 
		{
			supplyTimeOrder = (MyTime)this.supplyTime.clone();	//copy time that customer choose
	    	System.out.println("" + supplyTimeOrder);

	    	newDeal.setOrdersupplyTime(supplyTimeOrder);	
 
		} 
		catch (CloneNotSupportedException e) 
		{
			System.out.println("Cannot copy time");
		}
    	try 
    	{
			Date supplyDateOrder= (Date)supplyTimeDate.clone();	//copy date that customer choose
	    	System.out.println("" + supplyDateOrder);

			newDeal.setOrderSupplyDate(supplyDateOrder);
		} 
    	catch (CloneNotSupportedException e) 
    	{
			System.out.println("Cannot copy date");
		}
    	
    	double totalPriceOfOrder=this.totalPrice;	//getting total price of order
    	newDeal.setOrderTotalPrice(totalPriceOfOrder);
    	
    	String branchID = CustomerMainWindow.chosenBranchID;	//getting branchID of order
    	newDeal.setOrderbranchID(branchID);
    	
    	try 
    	{
			Delivery OrderDelivery =(Delivery)myShipment.clone();
			newDeal.setOrderCustomerDelivery(OrderDelivery);
	    	System.out.println("" + OrderDelivery);

		} 
    	catch (CloneNotSupportedException e) 
    	{
			System.out.println("Cannot copy delivery");

		}
    	
    	int customerID = LoginContol.userID;	//get customerID
    	newDeal.setCustomerID(customerID);  	
		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		try 
		{
			myClient = new ChatClient(ip, port);
			myClient.setOrderControlOfBuyningProcess(this);
	    	myClient.sendRequestToSaveCustomerOrder(newDeal);	//send request to save order in db

		} 
		catch (IOException e) 
		{
			System.out.println("Cannot send order to server");
		} // create new client


    	
    }
    
    
    
    /**
     * this method convert observableList to array list
     * @param itemsToBuy all item that customer want to buy (custom/catalog)
     * @return items in order at arraylist
     */
    private ArrayList<ItemInOrder> fillInItemsToBuy(ArrayList<ItemInOrder> itemsToBuy) 
    {
    	for(int i=0; i<this.ItemsInOrderList.size() ; i++)
    	{
    		itemsToBuy.add( this.ItemsInOrderList.get(i) );
    	}
    	
    	return itemsToBuy;

	}


	@FXML
    void payMethodcomboBoxPressed(ActionEvent event) 
    {

    }
    

    /**
     * this method allow textbox when customer pick a first digits of phone from combobox
     * @param event on click on combobox
     */
    @FXML
    void KidometChosen(ActionEvent event) 
    {
    	
    		phoneNumberTxt.setDisable(false);
    
    }
    
    /**
     * this method prepare screen to insert branch shipment when radio button picked
     * @param event on pick of radio button
     */
    @FXML
    void branchRadioChosen(ActionEvent event) //if customer chose a self arrival delivery
    {
    	this.comboBranch.setDisable(false);
    	this.comboBoxHour.setDisable(false);
    	this.adressShipmentTxt.setDisable(true);
    	this.adresseeShipmentTxt.setDisable(true);
    	this.KidometPhone.setDisable(true);
    	this.phoneNumberTxt.setDisable(true);
    	this.adressShipmentTxt.clear();
    	this.adresseeShipmentTxt.clear();
    	this.phoneNumberTxt.clear();
    	KidometPhone.valueProperty().set(null);
    	isPrivateShipment=false;	//flag
    	
    	
    }

    /**
     * this method prepare screen to insert private shipment when radio button picked
     * @param event on pick of radio button
     */
    @FXML
    void privateAdressRadioChosen(ActionEvent event) 
    {
    	comboBranch.valueProperty().set(null);
    	this.comboBranch.setDisable(true);
    	this.comboBoxHour.setDisable(false);
    	this.adressShipmentTxt.setDisable(false);
    	this.adresseeShipmentTxt.setDisable(false);
    	this.KidometPhone.setDisable(false);
    	this.phoneNumberTxt.setDisable(true);
    	this.adressShipmentTxt.clear();
    	this.adresseeShipmentTxt.clear();
    	this.phoneNumberTxt.clear();
    	isPrivateShipment=true; //flag
    	
    }
    
    /**
     *  this method load time to combobox when date picked
     * @param event datepicker click
     */
    @FXML
    void comboBoxDatePressed(ActionEvent event) 
    {	/**this method responsible for screen of checkout*/
    	supplyTimeDate=null;
    	supplyTime=null;
    	hourList.clear();
    	 MinutesList.clear();
    	supplyTimeDate= new Date(ComboDate.getValue().getYear(), ComboDate.getValue().getMonthValue(), ComboDate.getValue().getDayOfMonth());
    	comboBoxHour.setDisable(false);
    	comboBoxHour.setDisable(false);
    	LocalDateTime now = LocalDateTime.now(); 

    	if(supplyTimeDate.getYear()==now.getYear() && supplyTimeDate.getMounth() == now.getMonthValue() && supplyTimeDate.getDay() == now.getDayOfMonth())	//in case customer chose curretn day
    	{
    		int hourTime= now.getHour();	
    		for (int i = hourTime+1 ; i<24;i++)	//it will show him the next hour up to 23 Oclock
    		{
    			if(i<10)
    				hourList.add("0"+i);
    			else
    				hourList.add(""+i);

    		}
    	}
    	
    	else	//if user chose the future, it will show him all hour per day
    	{
    		for (int i = 0 ; i<10;i++)
    		{
    			hourList.add("0"+i);
    		}
    		
    		for (int i = 10 ; i<24;i++)
    		{
    			hourList.add(""+i);
    		}
    	}
    	comboBoxHour.setItems(hourList); 

    }
    
    /**
     * this method save customer coice about hour from combobx
     * @param event click on combobox
     */
    
    @FXML
    void comboBoxHourPressed(ActionEvent event) 
    {
    	supplyTime=null;
    	System.out.println(""+supplyTimeDate);
    	System.out.println(""+supplyTime);
		
    }
    
    
    /**
     * this method show screen of payment in order process
     * @param event on click even of button
     */
    @FXML
    void goToCheckoutBtnPressed(ActionEvent event) 
    {	/**this method show screen 3 and prepare important data to load customer order table*/
    	String ErrorMsg="";
    	if(ComboDate.getValue() == null)	//check is customer picked date
    		ErrorMsg=ErrorMsg+"Supply date.\n";
    	
    	if(comboBoxHour.getValue() == null)	//check is customer picked hour
    		ErrorMsg=ErrorMsg+"Supply time.\n";
    	
    	if(delivery.getSelectedToggle() ==null ) //check is customer picked kind of any delivery
    		ErrorMsg=ErrorMsg+"Delivery.\n";
    	else 	//if user did pick a king of delivery, then....
    		{
    			if(branchRadio.isSelected() == true)	//if he chose self arrival delivery,
    			{
    				if(comboBranch.getValue()== null)	//check if customer picked a branch in combobox
    				{	//not picked
    		    		ErrorMsg=ErrorMsg+"Branch to self arrival delivery.\n";
    				}
    			}
    			
    			else if(privateAdressRadio.isSelected() == true)	//if he chose self arrival delivery,
    			{
    				if(adressShipmentTxt.getText().equals(""))	//check if customer entered adress to text field
    				{	//not picked
    		    		ErrorMsg=ErrorMsg+"Adress for private shipment.\n";
    				}
    				if(adresseeShipmentTxt.getText().equals(""))	//check if customer entered adressee to text field
    				{	//not picked
    		    		ErrorMsg=ErrorMsg+"Adressee for private shipment.\n";
    				}
    				
    				if(KidometPhone.getValue() ==null) //check if customer entered phone number by check kidomet
    				{
    		    		ErrorMsg=ErrorMsg+"Phone number.\n";

    				}
    				
    				else
    				{
    					if(phoneNumberTxt.getText().equals(""))
        		    		ErrorMsg=ErrorMsg+"Phone number.\n";

    				}
    				if(KidometPhone.getValue().length() ==3 && phoneNumberTxt.getText().length() != 7)
    		    		ErrorMsg=ErrorMsg+"Invalid Phone number.\n";
        			else if(KidometPhone.getValue().length() ==2 && phoneNumberTxt.getText().length() != 7)
    		    		ErrorMsg=ErrorMsg+"Invalid Phone number.\n";
    			}
    			
    			

    			calculateTotalPriceAndQuantity();
    			double tempPrice = this.totalPrice;
    			if(this.isPrivateShipment ==true)
    						tempPrice = this.totalPrice+14.99;
    			String statusPrice = "Your order price is: "+tempPrice+"$.\n(There may be another discount)";
    			totalPriceResult.setText(statusPrice);
    			
    		
    		}
    	
    	if(!ErrorMsg.equals(""))	//if there is an error (at least one) show error message and get out from this method, to prevent customer to arrive to payments level
    	{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("You cannot procced to checkout!");
    		alert.setHeaderText("The folowing details are false or missing:");
    		alert.setContentText(ErrorMsg);
    		alert.showAndWait();
    		return;
    	}
    	int chosenYear=ComboDate.getValue().getYear();
    	int chosenMounth=ComboDate.getValue().getMonthValue();
    	int chosenDay=ComboDate.getValue().getDayOfMonth();
        LocalDateTime now = LocalDateTime.now(); 	//here we will check if customer picked expedited delivery for boolean value of customerorder class
    	int nowYear=now.getYear();
    	int nowMounth=now.getMonthValue();
    	int nowDay=now.getDayOfMonth();
    	supplyTimeDate =new Date(chosenYear ,chosenMounth , chosenDay);	//here we create date of supplying for customerOrder class
    	supplyTime = new MyTime(comboBoxHour.getValue(), "00", "00");	//here we keep the date of supplying that customer wanted

    	
    	if(nowYear == chosenYear && nowMounth == chosenMounth && nowDay== chosenDay) //if customer want the supplying today....
    	{
    		myShipment=null;
    		int nowHour = now.getHour();
    		int supplyHour = Integer.parseInt(supplyTime.getHour());
    		if ((supplyHour-nowHour) <=3 )	//if customer want his order in the next 3 hours, then define this order to be expedited
    		{
    			expeditedSupplying=true;
    		}
    		
    		else
    		{
    			expeditedSupplying=false;		//if customer want his order in more than 3 hours, then this order is not expedited

    		}
    	}

    	else
    	{
			expeditedSupplying=false;	//if customer want his order in more than 1 day later, then this order is not expedited

    	}
        
     
    	if(branchRadio.isSelected())
    	{
    		String myBranchName=comboBranch.getValue();	
    		String myBranchAdress="";
    		for (int i=0; i < CustomerMainWindow.AllBranches.size() ; i++)	//we look for the address of the branch's name
    		{
    			String checkBranchName=CustomerMainWindow.AllBranches.get(i).getBranchName();	//keep the current scanned branch
    			if(checkBranchName.equals(myBranchName))
    			{
    				myBranchAdress=CustomerMainWindow.AllBranches.get(i).getBrancAdress();
    				break;
    			}
    		}
    		
    		myShipment=new BranchShipment(CustomerMainWindow.chosenBranchID,myBranchName,myBranchAdress);	//create delivery of self arrival, 
    		System.out.println(""+((BranchShipment)myShipment).getBranchName()+", "+ ((BranchShipment)myShipment).getBranchAdress());
    	}
    	
    	else if(privateAdressRadio.isSelected())
    	{
    		String Address=adressShipmentTxt.getText();
    		String Adressee=adresseeShipmentTxt.getText();
    		String PhoneNum=KidometPhone.getValue()+""+phoneNumberTxt.getText();
    		myShipment = new PrivateShipment(14.99,Adressee, Address, PhoneNum);  
    		System.out.println(((PrivateShipment)myShipment).getAddressee()+", " +((PrivateShipment)myShipment).getPhoneNumber()+", "+((PrivateShipment)myShipment).getAddress());
    	}
    	
		this.totalPrice=this.totalPrice+myShipment.getPrice();	//updating the total price according to the delivery
		System.out.println("Total price:"+this.totalPrice);
    	ShowScreenThree();	//show screen number 3
    	
    	}
    
    /**
     * this method bring back to the delivery screen from payment screen
     * @param event
     */
    @FXML
    void BackToCartBtnPressed(ActionEvent event) 
    {
    
    	ShowScreenONE();
    }
    
    /**
     * this method show screen of delivery from first screen of invoice
     * @param event on click event
     */
    
    @FXML
    void goToDeliveryPressed(ActionEvent event) 
    {
    	this.textGreeting=this.txtGreeting.getText();
    	ShowScreenTWO();
    	
    }
    
    /**
     * this method opens the account details window of the customer, we had no time to write it.....
     * @param event event type of click on a button
     */
    @FXML
    void AccountBtnPressed(ActionEvent event) 
    {
    	checkboxFilled =AddGreetingChkBox.isSelected();
    	textGreeting = txtGreeting.getText();
    	accountBtn.getScene().getWindow().hide(); // hiding primary window
    	Stage primaryStage = new Stage();
    	AccountControl aFrame = new AccountControl();
    	try {
    		aFrame.start(primaryStage);
    		} 
    	catch (IOException e) 
    	{
		System.out.println("Cannot start Account Window");
    	}

    }
    
    /**
     * this method close all user window and will get him out from the system, it will send message to server to change the flag (that prevent a second login)
     * @param event	action even of click on button
     */
    @FXML
    void logoutPressed(ActionEvent event)throws IOException 
	{
		CustomerMainWindow.chosenBranchName="";
    	CustomerMainWindow.chosenBranchID="";
		changeEntry(UserNameToCheck);
		System.out.println("return to main menu");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		LoginContol aFrame = new LoginContol(); // create Login Frame
		Stage arg0 = new Stage();
		OrdersControl.ItemsInOrderList.clear();
		aFrame.start(arg0);

	}

    /**
	 * this method will close current window, and will open the main window of buying process
	 * @param event event of click on a button
	 */ 
    
    @FXML
    void HomePresssed(ActionEvent event) 
    {
    	checkboxFilled =AddGreetingChkBox.isSelected();
    	textGreeting = txtGreeting.getText();
		btnHome.getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		CustomerMainWindow aFrame = new CustomerMainWindow();
		try 
		{
			aFrame.start(primaryStage);
		} 
		catch (IOException e) 
		{
			System.out.println("Cannot start Customer main Window");
		}
    }



    @FXML
    void textFieldfilled(ActionEvent event) 
    {
    
    }
    
    
    /**
     * this method enable text area for customer to insert greeting
     * @param event on click event
     */
    @FXML
    void ChkBoxPressed(ActionEvent event) 
    {

    	if(AddGreetingChkBox.isSelected())
    	{
    		if(ItemsInOrderList.isEmpty())
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Your cart is empty!");
    			alert.setHeaderText("Your invoice is empty");
    			alert.setContentText("You did not selected any product");
    			alert.showAndWait();
    			AddGreetingChkBox.setSelected(false);
    			return;
    		}
    		
    		txtGreeting.setDisable(false);
    		ThreadLabelTxtArea preparLabel = new ThreadLabelTxtArea(this.txtGreeting, this.messageAfterGreeting);
    		preparLabel.start();
    	}
    	
    	else
    	{
    		
    		txtGreeting.setDisable(true);
    		//messageAfterGreeting.setVisible(false);


    	}
    }
	
	/**
	 * this method show cart window
	 */
	public void start(Stage primaryStage) throws IOException 
	{
		Pane root = FXMLLoader.load(getClass().getResource("/Customer/OrderrsControl.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Create an order"); // name of the title of the window
		primaryStage.setScene(scene);
		primaryStage.show();
		

		// Can't close the window without logout
		primaryStage.setOnCloseRequest(event -> {
			event.consume();
		});
	} 

	/**
	 * this method calculate total price of all chosen items (single price * quantity * number of items)
	 */
	public static void calculateTotalPriceAndQuantity()
	{
		int amountItems = 0;
		  double totaPprice=0;
		  ObservableList<ItemInOrder> allItemToBuy = ItemsInOrderList;
		  for(int i=0 ; i< allItemToBuy.size(); i++)
		  {
			  amountItems = amountItems + allItemToBuy.get(i).getItemQty();
			  totaPprice = totaPprice + allItemToBuy.get(i).getTotalPrice();
		  }
		   totalQuantity = amountItems;
		   totalPrice = totaPprice;
		  
	}
	
	/**
	 * this method cut double number in string up to two digits after point
	 * @param num total price
	 * @return string that contains double number up to 2 digits after point
	 */
	  public static String CutDoubleNumUpToTwoDigitAfterPoint(double num)
	  {
		  String cutDoubleNum = "" +num;
		  String result="";
		  for (int i = 0; i < cutDoubleNum.length();i++)
		  {
			  if(cutDoubleNum.charAt(i) == '.')
			  {
				  result=result+""+cutDoubleNum.charAt(i);
				  if((i+2) < cutDoubleNum.length())
				  {
					  result=result+""+cutDoubleNum.charAt(i+1) +""+cutDoubleNum.charAt(i+2);	//cut up to 2 digits after point, in case there is more than 2 digits after point
					  break;
				  }
				  else
				  {
					  result=result+""+cutDoubleNum.charAt(i+1); //cut up to 1 digits after point, in case there is less than 2 digits after point
					  break;

				  }
				  
			  }
			  else
			  {
				  result=result+""+cutDoubleNum.charAt(i);
			  }
		  }
		  return result;
	  }

	  /**
	   * this method prepare firt screen of cart
	   */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		ShowScreenONE();
		itemInOrderIDcolmun.setCellValueFactory(new PropertyValueFactory<ItemInOrder, Integer>("itemID"));
		itemInOrderNameColmuns.setCellValueFactory(new PropertyValueFactory<ItemInOrder, String>("itemName"));
		itemInOrderQtyColmun.setCellValueFactory(new PropertyValueFactory<ItemInOrder, Integer>("itemQty"));
		itemInOrderPriceColumns.setCellValueFactory(new PropertyValueFactory<ItemInOrder,  String>("itemTotalPriceWithCoin"));
		itemInOrderIDcolmun.setStyle( "-fx-alignment: CENTER;");
		itemInOrderNameColmuns.setStyle( "-fx-alignment: CENTER;");
		itemInOrderQtyColmun.setStyle( "-fx-alignment: CENTER;");
		itemInOrderPriceColumns.setStyle( "-fx-alignment: CENTER;");
	    ItemInOrderTable.setItems(ItemsInOrderList);
	    calculateTotalPriceAndQuantity();
	    double totalPrice = getTotalPrice();
		int totalQuantity = getTotalQuantity();
		  String fixNum = "" +CutDoubleNumUpToTwoDigitAfterPoint(totalPrice);  
	    totalProductsAmountLabel.setText("Total products: " + totalQuantity + " items.");
	    totalPriceLabel.setText("Toal price: "+ fixNum +"$.");
	    //checkboxFilled 
	    if (ItemsInOrderList.isEmpty()) //in case invoice is empte, we clear checkbox and text area, no matta what contend they had from previous stages (in case we kept data there and then we deleted items in the invoice, then we should delete check box and text area and start over)
	    {
	    	goToDelivery.setDisable(true);
	    	checkboxFilled=false;
	    	textGreeting="";
    		txtGreeting.setDisable(true);
    		AddGreetingChkBox.setSelected(checkboxFilled); 
    		txtGreeting.setText(textGreeting);
	    }
	    else	//in case invoice is not empty! (invoice = items in the table!)
	    {
	    	if(checkboxFilled)	//if checkbox left open at the previous stage restore content of checkbox and text area (which means the greeting we inserted
	    	{
	    		AddGreetingChkBox.setSelected(checkboxFilled); 
	    		txtGreeting.setText(textGreeting);
	    		txtGreeting.setDisable(false);
	    	}
	    
	    	else  //in case we inserted greeting, but canceled the check box
	    	{
	    		txtGreeting.setText(textGreeting);
	    		txtGreeting.setDisable(true);
	    	}
	    }
	    
    	BackToCartBtn.setVisible(false);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        comboBranch.setItems(this.oneBranchName); //**************
    	LocalDate minDate = LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth());
        LocalDate maxDate = LocalDate.of(2100, Month.DECEMBER, 31);
        ComboDate.setDayCellFactory((p) -> new DateCell() {
           
        	
        	@Override
            public void updateItem(LocalDate ld, boolean bln) 	//this part is making dates to be disabled before the current day
            {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(minDate) || ld.isAfter(maxDate));
            }
        });
        Platform.runLater(() -> {
        	ComboDate.getEditor().clear();
        });
        ComboDate.setDisable(false);
          comboBoxHour.setDisable(true);
          ObservableList<String> phoneKidomet= FXCollections.observableArrayList("02","03","02","04","08","09","050","052","053","054","055","058");
     	 KidometPhone.setItems(phoneKidomet);
         ObservableList<String> PaymentTypeToChoose= FXCollections.observableArrayList("Immidate","Subscription");

     	payMethodcomboBox.setItems(PaymentTypeToChoose);
     	oneBranchName.clear();
     	oneBranchName.add(CustomerMainWindow.chosenBranchName);
	}

	
	/**
	 * getter total quantity of items in order
	 * @return int
	 */
	public static int getTotalQuantity() 
	{
		return totalQuantity;
	}

	/**
	 * setter that put new value of quantity of item in order
	 * @param totalQuantity new int
	 */
	public static void setTotalQuantity(int totalQuantity) 
	{
		OrdersControl.totalQuantity = totalQuantity;
	}

	/**
	 * getter of total price
	 * @return double
	 */
	public static double getTotalPrice() 
	{
		return totalPrice;
	}

	/**
	 * setter of total price
	 * @param totalPrice new total price
	 */
	public static void setTotalPrice(double totalPrice) 
	{
		OrdersControl.totalPrice = totalPrice;
	}
	
	/**
	 * this method show screen one of invoice and greeting on cart frame
	 */
	private void ShowScreenONE()
	{
		goToDelivery.setVisible(true);
		BackToCartBtn.setVisible(false);
		yourinvoiceLabel.setVisible(true);
    	ItemInOrderTable.setVisible(true);
    	totalProductsAmountLabel.setVisible(true);
    	totalPriceLabel.setVisible(true);
    	AddGreetingChkBox.setVisible(true);
    	txtGreeting.setVisible(true);
    	YourcartLabel.setTextFill(Color.web("#34fffc"));
       	DeliverLabel.setTextFill(Color.WHITE);
    	BackToCartBtn.setVisible(false);
    	goToDelivery.setVisible(true);
    	messageAfterGreeting.setVisible(false);
    	SupplyTimeLabel.setVisible(false);	//from here it is the second part of window = delivery
    	dateLabel.setVisible(false);
    	ComboDate.setVisible(false);
    	HourLabel.setVisible(false);
    	comboBoxHour.setVisible(false);
    	ShipmentLabel.setVisible(false);
    	branchRadio.setVisible(false);
    	comboBranch.setVisible(false);
    	privateAdressRadio.setVisible(false);
    	aditionalCostLabel.setVisible(false);
    	adressShipmentLabel.setVisible(false);
    	adressShipmentTxt.setVisible(false);
    	adresseeShipmentLabel.setVisible(false);
    	adresseeShipmentTxt.setVisible(false);
    	phoneNumberShipmentLabel.setVisible(false);
    	KidometPhone.setVisible(false);
    	phoneNumberTxt.setVisible(false);
    	phoneNumberTxt.setDisable(true);
    	goToCheckoutBtn.setVisible(false);
    	totalPriceResult.setVisible(false);
    	//end of screen 2 = delivery
	}
	
	/**
	 * this method show screen two of delivery  on cart frame
	 */
	private void ShowScreenTWO()
	{	/**this method show screen 2 of delivery, no matta where you come from*/
		goToDelivery.setVisible(false);
		goToCheckoutBtn.setVisible(true);
		yourinvoiceLabel.setVisible(false);
    	ItemInOrderTable.setVisible(false);
    	totalProductsAmountLabel.setVisible(false);
    	totalPriceLabel.setVisible(false);
    	AddGreetingChkBox.setVisible(false);
    	txtGreeting.setVisible(false);
    	YourcartLabel.setTextFill(Color.WHITE);
    	DeliverLabel.setTextFill(Color.web("#34fffc"));
    	BackToCartBtn.setVisible(true);
    	goToDelivery.setVisible(false);
    	messageAfterGreeting.setVisible(false);
    	SupplyTimeLabel.setVisible(true);	//from here it is the second part of window = delivery
    	dateLabel.setVisible(true);
    	ComboDate.setVisible(true);
    	HourLabel.setVisible(true);
    	comboBoxHour.setVisible(true);
    	ShipmentLabel.setVisible(true);
    	branchRadio.setVisible(true);
    	comboBranch.setVisible(true);
    	privateAdressRadio.setVisible(true);
    	aditionalCostLabel.setVisible(true);
    	adressShipmentLabel.setVisible(true);
    	adressShipmentTxt.setVisible(true);
    	adresseeShipmentLabel.setVisible(true);
    	adresseeShipmentTxt.setVisible(true);
    	phoneNumberShipmentLabel.setVisible(true);
    	KidometPhone.setVisible(true);
    	phoneNumberTxt.setVisible(true);
    	phoneNumberTxt.setDisable(true);
    	backToDeliveryBtn.setVisible(false);		//from here it is the third part of window = checkout	
    	CheckotFinalMainLabel.setVisible(false);	
     	PA_userNameLabe.setVisible(false);			
    	PA_userName_txt.setVisible(false);		
    	PA_PasswordLabe.setVisible(false);			
    	PA_password_txt.setVisible(false);		
     	payMethodLabel.setVisible(false);			
     	payMethodcomboBox.setVisible(false);	
    	payButton.setVisible(false);					
		CheckoutLabel.setTextFill(Color.WHITE);
		totalPriceResult.setVisible(false);
		if(phoneNumberTxt.getText().length() >0)
		{
			phoneNumberTxt.setDisable(false);
		}
	    	
    	
    	//end of screen 2 = delivery
    	
	}
	/**
	 * this method show screen three of payment on cart frame
	 */
	
	private void ShowScreenThree()
	{	/**this method show screen 2 of delivery, no matta where you come from*/
		

		DeliverLabel.setTextFill(Color.WHITE);
    	CheckoutLabel.setTextFill(Color.web("#34fffc"));
    	BackToCartBtn.setVisible(false);
    	goToDelivery.setVisible(false);
    	messageAfterGreeting.setVisible(false);
    	SupplyTimeLabel.setVisible(false);	//from here it is the second part of window = delivery
    	dateLabel.setVisible(false);
    	ComboDate.setVisible(false);
    	HourLabel.setVisible(false);
    	comboBoxHour.setVisible(false);
    	ShipmentLabel.setVisible(false);
    	branchRadio.setVisible(false);
    	comboBranch.setVisible(false);
    	privateAdressRadio.setVisible(false);
    	aditionalCostLabel.setVisible(false);
    	adressShipmentLabel.setVisible(false);
    	adressShipmentTxt.setVisible(false);
    	adresseeShipmentLabel.setVisible(false);
    	adresseeShipmentTxt.setVisible(false);
    	phoneNumberShipmentLabel.setVisible(false);
    	KidometPhone.setVisible(false);
    	phoneNumberTxt.setVisible(false);
    	phoneNumberTxt.setDisable(false);
    	backToDeliveryBtn.setVisible(true);		//from here it is the third part of window = checkout	
    	CheckotFinalMainLabel.setVisible(true);	
     	PA_userNameLabe.setVisible(true);			
    	PA_userName_txt.setVisible(true);		
    	PA_PasswordLabe.setVisible(true);			
    	PA_password_txt.setVisible(true);		
     	payMethodLabel.setVisible(true);			
     	payMethodcomboBox.setVisible(true);	
    	payButton.setVisible(true);					
    	goToCheckoutBtn.setVisible(false);
    	totalPriceResult.setVisible(true);
    	
    	
    	//end of screen 2 = delivery
    	
	}
	
	/**
	 * this method restart buying process when payment approved
	 */
	public void endBuyingProcess()
	{	/**this method finish the buying process and return to main menu screen*/
		 this.ItemsInOrderList.clear();

		this.totalQuantity=0;
		this.totalPrice=0;
		this.checkboxFilled=false;
		this.textGreeting="";
		this.supplyTimeDate=null;  ;
		this.supplyTime=null;;
		this.selfArrivalBranch="";
		this.expeditedSupplying=false;
		myShipment=null;
		btnHome.getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		CustomerMainWindow aFrame = new CustomerMainWindow();
		try 
		{
			aFrame.start(primaryStage);
		} 
		catch (IOException e) 
		{
			System.out.println("Cannot start Customer main Window");
		}
		
	}
}
