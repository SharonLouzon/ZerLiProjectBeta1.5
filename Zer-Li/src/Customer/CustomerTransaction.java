package Customer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * clas of customer order
 * @author haim hadad
 *
 */
public class CustomerTransaction implements Serializable
{	/**this class is responsible for the customer order, and check if payment account exist in table*/
	private int OrderID;
	private int customerID;
	private Delivery OrderCustomerDelivery;
	private String OrderbranchID;
	private String branchName;	//
	private Double OrdertotalPrice;
	private Date OrdersupplyDate;
	private String supplyDateStr="";	//
	private MyTime OrdersupplyTime;
	private String supplyTimeStr="";	//
	private Date OrderCompletedDate;
	private MyTime OrderCompletedTime;
	private String Greeting;
	private String PaymentType;
	private boolean IsExpeditedDelivery;
	private int CompleteStatus=0;
	private String PaymentAccountUserName;
	private String PaymentAccountPassword;
	private ArrayList<ItemInOrder> productsList;
	private boolean isApproved=false;
	private String orderPriceWithCoin="";
	private String msgToClient="";
	private String msgToServer="";

	/**
	 * deafult constructor
	 */
	public CustomerTransaction() //constructor is empty because is will be too long to insert attributes
	{
		
	}

	/**
	 * getter of id if of order
	 * @return id of order
	 */
	public int getOrderID() {
		return OrderID;
	}

	/**
	 * setter of id of order
	 * @param orderID id of order
	 */
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	/**
	 * getter of id if of customer
	 * @return id of customer
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * setter of id if of customer
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	/**
	 * getter of deliver abstract class
	 * @return a delivery class (branchshipment/privateshipment)
	 */
	public Delivery getOrderCustomerDelivery() {
		return OrderCustomerDelivery;
	}

	/**
	 * setter of delivery class (branchshipment/privateshipment)
	 * @param orderCustomerDelivery set a delivery (branchshipment/privateshipment)
	 */
	public void setOrderCustomerDelivery(Delivery orderCustomerDelivery) {
		OrderCustomerDelivery = orderCustomerDelivery;
	}

	/**
	 * getter of id of branch
	 * @return string id of branch
	 */
	public String getOrderbranchID() {
		return OrderbranchID;
	}

	/**
	 * setter of id of branch
	 * @param orderbranchID
	 */
	public void setOrderbranchID(String orderbranchID) {
		OrderbranchID = orderbranchID;
	}

	/**
	 * getter of price
	 * @return price of order
	 */
	public Double getOrdertotalPrice() {
		return OrdertotalPrice;
	}

	/**
	 * setter of price
	 * @param ordertotalPrice double , price of order
	 */
	public void setOrderTotalPrice(Double ordertotalPrice) 
	{
		OrdertotalPrice = ordertotalPrice;
		orderPriceWithCoin=""+OrdertotalPrice+" $";
	}

	/**
	 * getter of Date class of supply date
	 * @return Date class that contains year, month, day
	 */
	public Date getOrdersupplyDate() {
		return OrdersupplyDate;
	}

	/**
	 * setter of Date class of supply date
	 * @param ordersupplyDate Date class that contains year, month, day
	 */
	public void setOrderSupplyDate(Date ordersupplyDate) {
		OrdersupplyDate = ordersupplyDate;
	}

	/**
	 * getter of Time class of supply time
	 * @return MyTime class that contains hour, minutes, seconds
	 */
	public MyTime getOrdersupplyTime() {
		return OrdersupplyTime;
	}

	/**
	 * setter of Time class of supply time
	 * @param  MyTime class that contains hour, minutes, seconds
	 */
	public void setOrdersupplyTime(MyTime ordersupplyTime) {
		OrdersupplyTime = ordersupplyTime;
	}

	/**
	 * getter of Date class of date of commit of order
	 * @return  MyTime class that contains hour, minutes, seconds
	 */
	public Date getOrderCompletedDate() {
		return OrderCompletedDate;
	}

	/**
	 * setter of Date class of commit of order
	 * @param ordersupplyDate Date class that contains year, month, day
	 */
	public void setOrderCompletedDate(Date orderCompletedDate) {
		OrderCompletedDate = orderCompletedDate;
	}

	/**
	 * getter of MyTime class of hour of commit  
	 * @return MyTime class that contains hour, minutes, seconds 
	 */
	public MyTime getOrderCompletedTime() {
		return OrderCompletedTime;
	}

	/**
	 * setter of time of commit of order 
	 * @param orderCompletedTime
	 */
	public void setOrderCompletedTime(MyTime orderCompletedTime) {
		OrderCompletedTime = orderCompletedTime;
	}

	/**
	 * getter of greeting of order
	 * @return string that contains greeting
	 */
	public String getGreeting() {
		return Greeting;
	}

	/**
	 * setter of greeting
	 * @param greeting string, contains greeting of order
	 */
	public void setGreeting(String greeting) {
		Greeting = greeting;
	}

	/**
	 * getter of payment type (immediate/subscription)
	 * @return string, type of payment
	 */
	public String getPaymentType() {
		return PaymentType;
	}

	/**
	 * setter of payment type (immediate/subscription)
	 * @param paymentType new type of payment
	 */
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}

	/**
	 * getter of flag that say if it is fast supplying
	 * @return boolean, true= fast, false = ordinary
	 */
	public boolean getIsExpeditedDelivery() {
		return IsExpeditedDelivery;
	}

	/**
	 * setter of flag that say if it is fast supplying
	 * @param isExpeditedDelivery boolean, new status of flag
	 */
	public void setIsExpeditedDelivery(boolean isExpeditedDelivery) {
		IsExpeditedDelivery = isExpeditedDelivery;
	}

	/**
	 * getter of flag that say if the order supplied
	 * @return boolean , true = supplied, false = still in process
	 */
	public int getCompleteStatus() {
		return CompleteStatus;
	}

	/**
	 * setter of flag that say if the order supplied
	 * @param completeStatus new status of flat
	 */
	public void setCompleteStatus(int completeStatus) {
		CompleteStatus = completeStatus;
	}

	/**
	 * getter of user name of payment account
	 * @return string
	 */
	public String getPaymentAccountUserName() {
		return PaymentAccountUserName;
	}

	/**
	 * setter of user name of payment account
	 * @param paymentAccountUserName string
	 */
	public void setPaymentAccountUserName(String paymentAccountUserName) {
		PaymentAccountUserName = paymentAccountUserName;
	}

	/**
	 * getter ofpassword of payment account
	 * @return string
	 */
	public String getPaymentAccountPassword() {
		return PaymentAccountPassword;
	}

	/**
	 * setter of password of payment account
	 * @param PaymentAccountPassword string
	 */
	public void setPaymentAccountPassword(String paymentAccountPassword) {
		PaymentAccountPassword = paymentAccountPassword;
	}

	/**
	 * getter of all products in order (custom/catalog)
	 * @return ItemInOrder in kind of custom or catalog
	 */
	public ArrayList<ItemInOrder> getProductsList() {
		return productsList;
	}

	/**
	 * setter of all products in order (custom/catalog)
	 * @param ItemInOrder class
	 */
	public void setProductsList(ArrayList<ItemInOrder> productsList) {
		this.productsList = productsList;
	}

	/**
	 * flag that server load and tell if the transaction approved
	 * @return boolean, true = user name & password approved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * setter of flag that server load and tell if the transaction approved
	 * @param isApproved boolean
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * getter that bring message from client to server about his order
	 * @return String
	 */
	public String getMsgToClient() {
		return msgToClient;
	}

	/**
	 * setter that bring message to client about his order
	 * @param msgToClient String
	 */
	public void setMsgToClient(String msgToClient) {
		this.msgToClient = msgToClient;
	}

	/**
	 * getter of price of order with $
	 * @return string
	 */
	public String getOrderPriceWithCoin() {
		return orderPriceWithCoin;
	}

	/**
	 * setter of price of order with $
	 * @param orderPriceWithCoin string
	 */
	public void setOrderPriceWithCoin(String orderPriceWithCoin) {
		this.orderPriceWithCoin = orderPriceWithCoin;
	}

	/**
	 * getter of name of branch that order been made in it
	 * @return string
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * setter of name of branch that order been made in it
	 * @param branchName string
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * getter of supply date in string
	 * @return string
	 */
	public String getSupplyDateStr() {
		return supplyDateStr;
	}

	/**
	 * getter of date of supply in string, at form of calendar
	 * @param supplyDateStr string
	 */
	public void setSupplyDateStr(String supplyDateStr) 
	{
		String dateStrToIsrael=""+supplyDateStr.substring(8, supplyDateStr.length())+"/"+supplyDateStr.substring(5, 7)+"/"+supplyDateStr.substring(0, 4);
		
		this.supplyDateStr = dateStrToIsrael;
	}

	/**
	 * getter of to string of MyTime class of time of supply
	 * @return string
	 */
	public String getSupplyTimeStr() {
		return supplyTimeStr;
	}

	/**
	 * getter of to string of MyTime class of time of supply
	 * @param supplyTimeStr string
	 */
	public void setSupplyTimeStr(String supplyTimeStr) 
	{
		this.supplyTimeStr = supplyTimeStr;
	}

	/**
	 * getter of message  from server to client
	 * @return string
	 */
	public String getMsgToServer() {
		return msgToServer;
	}

	/**
	 * setter of of message  from server to client
	 * @param msgToServer string
	 */
	public void setMsgToServer(String msgToServer) {
		this.msgToServer = msgToServer;
	}

	/**
	 * this method return order details in string type
	 */
	@Override
	public String toString() {
		return "CustomerTransaction [OrderID=" + OrderID + ", customerID=" + customerID + ", OrderbranchID="
				+ OrderbranchID + ", branchName=" + branchName + ", OrdertotalPrice=" + OrdertotalPrice
				+ ", OrdersupplyDate=" + OrdersupplyDate + ", OrdersupplyTime=" + OrdersupplyTime + "]";
	}
	
	
	
}

