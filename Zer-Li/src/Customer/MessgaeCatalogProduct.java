package Customer;

import java.io.Serializable;
/**
 * this class of message from customer to get all catalog product by a branch
 * @author haim hadad
 *
 */
public class MessgaeCatalogProduct implements Serializable	
{
	private final String msg = "Give Me All CatalogItems";
	private String branchID="";
	/**
	 * Constructor, get id of branch
	 * @param branchID
	 */
	public MessgaeCatalogProduct(String branchID) 
	{
		this.branchID = branchID;
	}
	/**
	 * getter of id of branch
	 * @return int id
	 */
	public String getBranchID() 
	{
		return branchID;
	}
	/**
	 * setter of branch id
	 * @param branchID
	 */
	public void setBranchID(String branchID) 
	{
		this.branchID = branchID;
	}
	/**
	 * getter of message to server
	 * @return string message to server
	 */
	public String getMsg() 
	{
		return msg;
	}
}
