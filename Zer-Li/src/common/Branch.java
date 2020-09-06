package common;

import java.io.Serializable;
/**
 * class of branch of zerli
 * @author Haim hadad
 *
 */
public class Branch implements Serializable
{
	private String branchID="";
	private String branchName;
	private String brancAdress;
	
	/**
	 * constructor of branch
	 * @param branchID id of branch
	 * @param branchName name of branch
	 * @param brancAdress address of branch
	 */
	public Branch(String branchID, String branchName, String brancAdress)
	{
		this.branchID=branchID;
		this.branchName = branchName;
		this.brancAdress = brancAdress;
	}
	
	
	
	/**
	 * getter of branch name
	 * @return string, name of a branch
	 */
	public String getBranchName() 
	{
		return branchName;
	}
	/**
	 * setter of branch name
	 * @param branchName  a new name for a branch
	 */
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * getter of address of branch
	 * @return string address of branch
	 */
	public String getBrancAdress() {
		return brancAdress;
	}
	/**
	 * setter of address of branch
	 * @param brancAdress string new address  
	 */
	public void setBrancAdress(String brancAdress) {
		this.brancAdress = brancAdress;
	}

	/**
	 * to string method of branch
	 */
	//tostring method:
	public String toString()
	{
		String branchStr= ""+this.branchName;
		return branchStr;
	}

//commit

/**
 * getter of id of branch
 * @return string
 */
	public String getBranchID() {
		return branchID;
	}



	/**
	 * setter of id of branch
	 * @param branchID string of id of branch
	 */
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
}
