package Customer;

import java.io.Serializable;
/**
 * class of customer delivery to specific address
 * @author haim hadad
 *
 */
public class PrivateShipment extends Delivery implements Serializable
{
	
	private String addressee;
	private String address;
	private String phoneNumber;
	
	/**
	  * constructor in case order id  and delivery id are known
	 * @param OrderID id of order
	 * @param DeliveryID id of delivery
	 * @param Price price of shipment
	 * @param addressee the person name that we send to him
	 * @param address the address of the addressee
	 * @param phoneNumber phone number of addressee
	 */
	public PrivateShipment(int OrderID, int DeliveryID, double Price, String addressee, String address, String phoneNumber ) 
	{
		super(OrderID, DeliveryID, Price);
		this.addressee=addressee;
		this.address=address;
		this.phoneNumber=phoneNumber;
	}
	/**
	 * constructor in case order id  and delivery id are unknown
	 * @param Price price of shipment
	 * @param addressee the person name that we send to him
	 * @param address the address of the addressee
	 * @param phoneNumber phone number of addressee
	 */
	public PrivateShipment( double Price, String addressee, String address, String phoneNumber ) 
	{
		super( Price);
		this.addressee=addressee;
		this.address=address;
		this.phoneNumber=phoneNumber;
	}
	/**
	 * getter of addressee name
	 * @return string
	 */
	public String getAddressee() 
	{
		return addressee;
	}

	/**
	 * setter of addressee name
	 * @param addressee string
	 */
	public void setAddressee(String addressee) 
	{
		this.addressee = addressee;
	}

	/**
	 * getter of address of addressee
	 * @return string
	 */
	public String getAddress() 
	{
		return address;
	}

	/**
	 * setter of adress of addressee
	 * @param address string new address
	 */
	public void setAddress(String address) 
	{
		this.address = address;
	}

	/**
	 * getter phone number of addressee
	 * @return string
	 */
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	/**
	 * setter phone number of a
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * to string method, print important detail of shipment
	 */
	@Override
	public String toString() {
		return "PrivateShipment [addressee=" + addressee + ", address=" + address + ", phoneNumber=" + phoneNumber
				+ "]";
	}
	
	
}
