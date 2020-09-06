package Customer;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;

import javafx.collections.ObservableList;

/**
 * abstract of custom/catalog product in order
 * @author haim hadad
 *
 */
public abstract class ItemInOrder implements Comparable,Serializable
{
	private Integer orderID= null;
	private int itemID;
	private String itemName;
	private int itemQty = 0;
	private double itemPrice=0;
	private double itemTotalPrice=0;
	private String itemTotalPriceWithCoin="";

	/**
	 * constructor of item in order in case order id is unknown
	 * @param itemId id of item in order (from catalog / customize)
	 * @param itemName name of item in order (from catalog / customize)
	 * @param price price of item in order (from catalog / customize)
	 */
	public ItemInOrder(int itemId,String itemName, double price)
	{
		this.itemID = itemId;
		this.itemName = itemName;
		this.itemPrice = price;
		this.itemTotalPrice = this.itemPrice;
		itemTotalPriceWithCoin=""+itemTotalPrice+"$";
		this.itemQty=1;
	}
	
	/**
	 * constructor of item in order in case order id is known
	 * @param order id of item in order (from catalog / customize)
	 * @param itemId id of item in order (from catalog / customize)
	 * @param itemName name of item in order (from catalog / customize)
	 * @param price price of item in order (from catalog / customize)
	 */
	public ItemInOrder(int orderid, int itemId,String itemName, double price)
	{
		this.orderID = new Integer(orderid);
		this.itemID = itemId;
		this.itemName = itemName;
		this.itemPrice = price;
		this.itemTotalPrice = this.itemPrice;
		itemTotalPriceWithCoin=""+itemTotalPrice+"$";
 
		this.itemQty=1;	
	}
	
	/**
	 * this mathod compare between item in order to another item in order by their id
	 */
	@Override
	public int compareTo(Object anotherItemInOrder) 
	{
		if(anotherItemInOrder instanceof ItemInOrder) 
		{
			if( ((ItemInOrder)anotherItemInOrder).getItemID()==this.itemID )
			{
				Integer newOrderID=((ItemInOrder)anotherItemInOrder).getOrderID();
				Integer oldOrderID=  this.orderID ;
				
				if(newOrderID==null && oldOrderID==null)
				{
					return 1;

				}
				else if( oldOrderID == newOrderID )
				{
					return 1;

				}
			}

		}
		return 0;

	}
	/**
	 * getter of id of order
	 * @return int id
	 */
	public Integer getOrderID() 
	{
		return orderID;
	}

	/**
	 * setter of id of order
	 * @param orderID new id of order
	 */
	public void setOrderID(int orderID) 
	{
		this.orderID = orderID;
	}

	/**
	 * setter of id of item
	 * @param id new id of item
	 */
	public void setItemID(int id) 
	{
		this.itemID=id;
	}
	
	/**
	 * getter of id of item
	 * @return new id of item
	 */
	public int getItemID() 
	{
		return itemID;
	}

	/**
	 * getter of quantity of item
	 * @return amount of similar items
	 */
	public int getItemQty() 
	{
		return itemQty;
	}

	/**
	 * setter of quantity of item
	 * @param itemQty new amount of similar item
	 */
	public void setItemQty(int itemQty) 
	{
		this.itemQty = itemQty;
		this.itemTotalPrice=this.itemPrice*itemQty;
		this.itemTotalPrice=this.itemTotalPrice*100/100;
		this.itemTotalPriceWithCoin=""+this.itemTotalPrice+"$";
	}

	/**
	 * getter of price of item
	 * @return price of item
	 */
	public double getItemPrice() 
	{
		return itemPrice;
	}
	
	/**
	 * setter of price of item
	 * @param itemSinglePrice 
	 */
	public void setItemPrice(double itemSinglePrice) 
	{
		this.itemPrice=itemSinglePrice;
		this.itemTotalPrice=this.itemQty*this.itemPrice;
	}
	/**
	 * getter of price of item
	 * @return double type
	 */
	public double getTotalPrice()
	{
		return this.itemTotalPrice;
	}
	
	/**
	 * this method increase in 1 the quantity of this item
	 */
	public void incQty()
	{
		this.itemQty++;
		this.itemTotalPrice=this.itemTotalPrice + this.itemPrice;
	}
	
	
	/**
	 * this method decrease in 1 the quantity of this item
	 */
	public void decQty()
	{
		this.itemQty--;
		this.itemTotalPrice=this.itemTotalPrice - this.itemPrice;

	}

	/**
	 * getter of name of item
	 * @return string name of the item
	 */
	public String getItemName() 
	{
		return itemName;
	}

	/**
	 * setter of item name
	 * @param itemName new name for item
	 */
	public void setItemName(String itemName) 
	{
		this.itemName = itemName;
	}
	/**
	 * getter price with $
	 * @return string
	 */
	public String getItemTotalPriceWithCoin()
	{
		return this.itemTotalPriceWithCoin;
	}
	/**
	 * setter of item price with $
	 * @param newPrice
	 */
	public void setItemTotalPriceWithCoin(String newPrice)
	{
		this.itemTotalPriceWithCoin = newPrice;
	}
	
	

}
