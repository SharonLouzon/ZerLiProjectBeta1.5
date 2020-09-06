package BranchManager;

import java.io.Serializable;
/**
 * class of branch manager
 * @author Elias qubety
 *
 */
public class BranchManager implements Serializable
{
	private int branchManagerID;
	private String branchManagerName;
	private String branchManagerEmail;
	private String branchID;
	
	
	/**
	 * constructor of branch manager
	 * @param branchManagerID integer id of branch manager
	 * @param branchManagerName string name of branch manager
	 * @param branchManagerEmail string email of manager
	 * @param branchID string id of branch
	 */
	public BranchManager(int branchManagerID, String branchManagerName, String branchManagerEmail, String branchID) {
		super();
		this.branchManagerID = branchManagerID;
		this.branchManagerName = branchManagerName;
		this.branchManagerEmail = branchManagerEmail;
		this.branchID = branchID;
	}
	
	/**
	 * getter of id of branch
	 * @return integer id of branch manager
	 */
	public int getBranchManagerID() {
		return branchManagerID;
	}
	/**
	 * setter of id of branch manager
	 * @param branchManagerID integer new id of branch
	 */
	public void setBranchManagerID(int branchManagerID) {
		this.branchManagerID = branchManagerID;
	}
	/**
	 * getter of name of branch manager
	 * @return string name of branch manager
	 */
	public String getBranchManagerName() {
		return branchManagerName;
	}
	/**
	 * setter of branch manager name
	 * @param branchManagerName string  new name of branch manager
	 */
	public void setBranchManagerName(String branchManagerName) {
		this.branchManagerName = branchManagerName;
	}
	/**
	 * getter of branch manager email
	 * @return string email of branch manager
	 */
	public String getBranchManagerEmail() {
		return branchManagerEmail;
	}
	/**
	 * setter of branch manager email
	 * @param branchManagerEmail new email of branch manager
	 */
	public void setBranchManagerEmail(String branchManagerEmail) {
		this.branchManagerEmail = branchManagerEmail;
	}
	/**
	 * getter of id of branch
	 * @return string id of branch
	 */
	public String getBranchID() {
		return branchID;
	}
	/**
	 * setter of id of branch
	 * @param branchID string new id of branch
	 */
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	/**
	 * to string method, print all important details of branch manager 
	 */
	@Override
	public String toString() {
		return "BranchManager [branchManagerID=" + branchManagerID + ", branchManagerName=" + branchManagerName
				+ ", branchManagerEmail=" + branchManagerEmail + ", branchID=" + branchID + "]";
	}
}
