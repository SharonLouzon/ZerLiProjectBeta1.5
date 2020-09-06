package BranchManager;

import java.io.Serializable;
/**
 *  class of amount of discount on specific item in specific branch
 * @author Elias qubeti
 */
public class PercentMSG implements Serializable 
{
	String itemId;
	String Percent ;
	String BranchID;
	
	/**
	 * constructor 
	 * @param itemId string id of item
	 * @param percent string percentages of discount
	 * @param BranchID string id of branch
	 */
	public PercentMSG(String itemId, String percent, String BranchID) 
	{
		this.itemId = itemId;
		Percent = percent;
		this.BranchID=BranchID;
	}
	
	/**
	 * getter of id of item
	 * @return string id of item
	 */
	public String getItemId() 
	{
		return itemId;
	}
	
	/**
	 * setter of id of item
	 * @param itemId string new id for product
	 */
	public void setItemId(String itemId) 
	{
		this.itemId = itemId;
	}
	
	/**
	 * getter of amount in percentage of discount
	 * @return string amount of discount
	 */
	public String getPercent() 
	{
		return Percent;
	}
	
	/**
	 * setter of percentage of discount
	 * @param percent  string new percentage
	 */
	public void setPercent(String percent) 
	{
		Percent = percent;
	}

	/**
	 * to string method, return string with all important details
	 */
	@Override
	public String toString() 
	{
		return "PercentMSG [itemId=" + itemId + ", Percent=" + Percent + "]";
	}
	/**
	 * getter of id of branch
	 * @return string
	 */
	public String getBranchID() 
	{
		return BranchID;
	}
	/**
	 * setter of id of specific branch
	 * @param branchID string 
	 */
	public void setBranchID(String branchID) 
	{
		BranchID = branchID;
	}
	
	
}
