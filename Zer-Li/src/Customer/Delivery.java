package Customer;

import java.io.Serializable;
/**
 * abstract class of delivery with common details to all delivery
 * @author haim hadad
 */
public abstract class Delivery implements Serializable, Cloneable
{
	private int OrderID;
	private int DeliveryID;
	private double Price;
	
	/**
	 * constructor
	 * @param OrderID id of order
	 * @param DeliveryID id of delivery
	 * @param Price price of the order
	 */
	public Delivery(int OrderID, int DeliveryID, double Price )
	{
		this.OrderID=OrderID;
		this.DeliveryID=DeliveryID;
		this.Price=Price;
	}
	/**
	 * this method allow to copy instance of class
	 */
	@Override
	public Object clone () throws CloneNotSupportedException
	{
		return super.clone();
		
	}
	
	/**
	 * kind of deafult constructor, if orderID and deliveryID are unknown
	 * @param Price double, new price of the delivery
	 */
	public Delivery (double Price )
	{
		this.Price=Price;
	}

	/**
	 * getter of id of the order
	 * @return int id of the order
	 */
	public int getOrderID() {
		return OrderID;
	}

	/**
	 * setter of order id
	 * @param orderID int
	 */
	public void setOrderID(int orderID) 
	{
		OrderID = orderID;
	}

	/**
	 * getter of id of delivery
	 * @return int
	 */
	public int getDeliveryID() 
	{
		return DeliveryID;
	}
	
	/**
	 * setter of id of delivery
	 * @param deliveryID
	 */
	public void setDeliveryID(int deliveryID) 
	{
		DeliveryID = deliveryID;
	}
	
	/**
	 * getter of price of the order
	 * @return double type price
	 */
	public double getPrice() 
	{
		return Price;
	}

	/**
	 * setter of the price of the order
	 * @param price double
	 */
	public void setPrice(double price) 
	{
		Price = price;
	}
	
	
}
