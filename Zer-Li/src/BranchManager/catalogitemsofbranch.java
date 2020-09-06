package BranchManager;

import java.io.Serializable;

/**
 * class of catalog product of specific branch
 * @author Elias qubety
 *
 */
public class catalogitemsofbranch implements Serializable 
{
	
	int ItemID;
	String BranchID;
	Double Price;
	/**
	 * getter of id of product
	 * @return integer id of product
	 */
	public int getItemID() 
	{
		return ItemID;
	}
	/**
	 * setter of id of product
	 * @param itemID integer new id of item
	 */
	public void setItemID(int itemID) 
	{
		ItemID = itemID;
	}
	/**
	 * getter of id of branch
	 * @return string id of branch
	 */
	public String getBranchID() 
	{
		return BranchID;
	}
	/**
	 * setter of id of branch
	 * @param branchID string new id of branch
	 */
	public void setBranchID(String branchID) 
	{
		BranchID = branchID;
	}
	/**
	 * getter of price of product
	 * @return double price of product
	 */
	public Double getPrice() 
	{
		return Price;
	}
	/**
	 * setter of new price
	 * @param price double new price for product
	 */
	public void setPrice(Double price) 
	{
		Price = price;
	}
	/**
	 * to string method, return string of all details of product
	 */
	@Override
	public String toString() 
	{
		return "catalogitemsofbranch [ItemID=" + ItemID + ", BranchID=" + BranchID + ", Price=" + Price + "]";
	}
	
	public catalogitemsofbranch(int itemID, String branchID, Double price) {
		super();
		ItemID = itemID;
		BranchID = branchID;
		Price = price;
	}
	
}
