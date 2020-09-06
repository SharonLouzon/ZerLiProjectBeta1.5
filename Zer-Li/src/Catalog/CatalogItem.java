package Catalog;

import java.io.Serializable;

import common.MyFile;
/** an entity class that represents a single item from the Catalogue 
 * the class is used in the GUI, implements Serializable, in order to allow the object  to
 * be sent between the server and the client
 * @author Haim
 *
 */
public class CatalogItem implements Serializable
{
	private int itemID;
	private String itemName;
	private String itemType;
	private String itemDescription;
	private MyFile itemPhoto;
	private double itemPrice;
	private boolean isSale=false;
 
/**catalogItem constructor 
 * 
 * @param itemID type int
 * @param itemName type String
 * @param itemType type String 
 * @param itemDescription type String
 * @param itemPhoto type of the class MyFile
 * @param Price type of double
 */
	public CatalogItem(int itemID, String itemName,	String itemType , String itemDescription , MyFile itemPhoto ,double Price ) 
	{
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemDescription = itemDescription;
		this.itemPhoto = itemPhoto;	
		this.itemPrice= Price;
	}
	

	
	/** a getter for the itemID
	 * 
	 * @return itemId type int
	 */
	public int getItemID()
	{
		return this.itemID;
	}
	/** a setter method for the itemID
	 * 
	 * @param itemID type int
	 */
	public void setItemID(int itemID)
	{
		this.itemID = itemID;

	}
	
	/**a getter method returns the Item Name.
	 * 
	 * @return ItemName type String 
	 */
	public String getItemName()
	{
		return this.itemName;

	}
	
	/** a setter method, sets the Items Name.
	 * 
	 * @param itemName type String
	 */
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	/*** a setter method, return the item type.
	 * 
	 * @return ItemType Type String
	 */
	public String getItemType()
	{
		return this.itemType;
	}
	
	/** a setter method, sets  the Item Type.
	 *  
	 * @param ItemType type String
	 */
	public void setItemType(String ItemType)
	{
		this.itemType=ItemType;
	}
	/** a getter method, gets the item description.
	 * 
	 * @return itemDescription type String
	 */
	public String getItemDescription()
	{
		return this.itemDescription;

	}
	/** a setter method gets the item description.
	 * 
	 * @param itemDescription type String 
	 */
	public void setItemDescription(String itemDescription)
	{
		this.itemDescription=itemDescription;
	}
	
/** a getter method, returns the itemPhoto, from the type MyFile.
 * 
 * @return itemPhoto type MyFile
 */
	public MyFile getItemPhoto()
	{
		return this.itemPhoto;

	}
	/** a setter method for the photo of the item.
	 * 
	 * @param itemPhoto type MyFile
	 */
	public void setIitemPhoto(MyFile itemPhoto)
	{
		this.itemPhoto=itemPhoto;
	}
	/** a getter method that returns the itemPrice.
	 * 
	 * @return getItemPrice type double
	 */
	public double getItemPrice()
	{
		return this.itemPrice;
	}
	/** setter method, sets the current price of the item.
	 * 
	 * @param price type double
	 */ 
	public void setItemPrice(double price)
	{
		this.itemPrice = price;

	}
	
 
	
	public String toString()
	{
		String CatalogProduct = ""+this.itemID+" "+this.itemName+" " +this.itemType+" "+ this.itemDescription  ;
		CatalogProduct=CatalogProduct+ " "+this.itemPhoto.getFileName()+" "+this.itemPrice;
		return CatalogProduct;
	}




	public boolean isSale() {
		return isSale;
	}




	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}
}