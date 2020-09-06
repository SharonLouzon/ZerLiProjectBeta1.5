package Customer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * class of custom product in order
 * @author haim hadad
 *
 */
public class CustomItemInOrder extends ItemInOrder implements Serializable
{
	private String itemType;
	private ArrayList<Flower> flowersInItem;
	private String itemDominantColor="";
	
	/**
	 * constructor of custom item that customer ordered, in case itemID known
	 * @param orderid id of order
	 * @param itemId id of item
	 * @param itemName  name of item
	 * @param itemType	type of item
	 * @param price price of item
	 * @param flowersInItem all flowers that the product contains
	 * @param itemDominantColor dominant color of flowers
	 */
	public CustomItemInOrder(int orderid, int itemId, String itemName,String itemType, double price,ArrayList<Flower> flowersInItem,String itemDominantColor) 
	{ 
		super(orderid, itemId, itemName, price);
		this.flowersInItem = flowersInItem;
		this.itemType=itemType;
		this.itemDominantColor=itemDominantColor;
	} 
	
	/**
	 * same constructor as before, in case itemID is unknown
	 * @param itemId
	 * @param itemName
	 * @param itemType
	 * @param price
	 * @param flowersInItem
	 * @param itemDominantColor
	 */
	public CustomItemInOrder(int itemId, String itemName,String itemType, double price,ArrayList<Flower> flowersInItem,String itemDominantColor) 
	{
		super(itemId, itemName, price);
		this.flowersInItem = flowersInItem;
		this.itemType=itemType;
		this.itemDominantColor=itemDominantColor;
	} 

	/**
	 * getter of all flowers in item
	 * @return arraylist of flowers
	 */
	public ArrayList<Flower> getFlowersInItem() 
	{
		return flowersInItem;
	}

	/**
	 * setter of all flowers in item
	 * @param flowersInItem arraylist of flowers
	 */
	public void setFlowersInItem(ArrayList<Flower> flowersInItem) 
	{
		this.flowersInItem = flowersInItem;
	}

	/**
	 * to string of all details of custom item
	 */
	@Override
	public String toString() {
		return "CustomItemInOrder [flowersInItem=" + flowersInItem + ", getOrderID()=" + getOrderID()
				+ ", getItemQty()=" + getItemQty() + ", getItemName()=" + getItemName()
				+ ", getItemTotalPriceWithCoin()=" + getItemTotalPriceWithCoin() + "]" +flowersInItem;
	}

	/**
	 * getter of dominant color of flowers
	 * @return string
	 */
	public String getItemDominantColor() {
		return itemDominantColor;
	}

	/**
	 * setter of dominant color of flowers
	 * @param itemDominantColor string
	 */
	public void setItemDominantColor(String itemDominantColor) {
		this.itemDominantColor = itemDominantColor;
	}

	/**
	 * getter of item type
	 * @return string
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * setter of item type
	 * @param itemType string
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

 

}
