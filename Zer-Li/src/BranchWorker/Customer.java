package BranchWorker;

import java.io.Serializable;
/**
 * class for the user details.
 * the branch worker use this class to pick customer detail in the combobox.
 * @author Sharon & Elias
 *
 */
public class Customer implements Serializable {
	int customerID;
	String customerName;
	String customerlastName;
	String  address;
	String  email;
	int  phoneNumber;
	
	/**
	 * constructor
	 * @param customerID integer id of customer
	 * @param customerName string private name of customer
	 * @param customerlastName  string last name of customer
	 * @param address string address of customer
	 * @param email string  email of customer
	 * @param phoneNumber string phone number of customer
	 */
	public Customer(int customerID, String customerName, String customerlastName, String address, String email,
			int phoneNumber) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerlastName = customerlastName;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Default constructor
	 */
	public Customer() 
	{
		super();
	}
	
	/**
	 * getter id of customer
	 * @return integer id of customer
	 */
	public int getCustomerID() 
	{
		return customerID;
	}
	/**
	 * setter id of customer
	 * @param customerID integer new id of customer
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * getter of name of customer
	 * @return id of customer
	 */
	public String getCustomerName() {
		return customerName;
	}
	
	/**
	 * setter of customer name
	 * @param customerName
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	/**
	 * getter of customer last name
	 * @return string
	 */
	public String getCustomerlastName() {
		return customerlastName;
	}
	
	/**
	 * setter of customer last name
	 * @param customerlastName string
	 */
	public void setCustomerlastName(String customerlastName) {
		this.customerlastName = customerlastName;
	}
	/**
	 * getter of address of customer
	 * @return string
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * setter of address of customer
	 * @param address string
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * getter of email of customer
	 * @return string
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * setter of email of customer
	 * @param email string
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * getter of phone number
	 * @return integer
	 */
	public int getPhoneNumber() 
	{
		return phoneNumber;
	}
	/**
	 * setter of phone number
	 * @param phoneNumber integer
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * tostring method that return string with all customer details
	 */
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", customerName=" + customerName + ", customerlastName="
				+ customerlastName + ", address=" + address + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
	
	

}
