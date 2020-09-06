package Customer;

import java.io.Serializable;

/**
 *  class of self arrival delivery
 * @author haim hadad
 *
 */
public class BranchShipment extends Delivery implements Serializable
{
	private String BranchID;
	private String BranchName;
	private String BranchAdress;
	
	
/**
 *  constructor if you know the order id
 * @param OrderID the id of the order
 * @param DeliveryID the id of the delivery
 * @param BranchID the id of the branch
 * @param BranchName the name of the branch, usually called by the name of the city
 * @param BranchAdress the address of the branch
 */
	public BranchShipment(int OrderID, int DeliveryID,String BranchID, String BranchName, String BranchAdress) 
	{
		super(OrderID,DeliveryID, 0 ); //we sending 0 because self arrival is free of charge
		this.BranchID= BranchID;
		this.BranchName = BranchName;
		this.BranchAdress=BranchAdress;
	}

	
	/**
	 * the same constructor, but use it when you do not know the orderID
	 * @param BranchID
	 * @param BranchName
	 * @param BranchAdress
	 */
	public BranchShipment(String BranchID,String BranchName,String BranchAdress) 
	{
		super(0);
		this.BranchID= BranchID;
		this.BranchName = BranchName;
		this.BranchAdress=BranchAdress;
	}

	/**
	 *  getter of branch name
	 * @return the name of the branch
	 */
	public String getBranchName() 
	{
		return BranchName;
	}

	
	/**
	 * setter of branch name
	 * @param branchName new name for a branch
	 */
	public void setBranchName(String branchName) 
	{
		BranchName = branchName;
	}
	
	/**
	 * getter of branch address
	 * @return address of the branch
	 */

	public String getBranchAdress()
	{
		return this.BranchAdress;
	}
	
	
	/**
	 * setter of address of the branch 
	 * @param BranchAdress put new branch address
	 */
	public void setBranchAdress(String BranchAdress)
	{
		this.BranchAdress=BranchAdress;
	}

	/**
	 * getter of the branchID
	 * @return id of branch
	 */
	public String getBranchID() {
		return BranchID;
	}

	/**
	 * setter of the branch id
	 * @param branchID put new id of branch
	 */
	public void setBranchID(String branchID) {
		BranchID = branchID;
	}


	/**
	 * to string that print id of branch, name of branch and address of branch
	 */
	@Override
	public String toString() {
		return "BranchShipment [BranchID=" + BranchID + ", BranchName=" + BranchName + ", BranchAdress=" + BranchAdress	+ "]";
	}
	
	
}
