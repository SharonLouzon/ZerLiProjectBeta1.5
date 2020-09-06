package BranchManager;

import java.io.Serializable;
import java.util.ArrayList;

import common.Branch;
/** this class contains Branches, that were retrieved from the Database.
 * 
 * @author Elias
 * @implements Serializable
 */
public class SpecialBranchesMessage implements Serializable {
	private ArrayList<Branch> allBranches = new ArrayList<Branch>();
	private ArrayList<BranchManager> allBranchManagers = new ArrayList<BranchManager>();

	public SpecialBranchesMessage() { 

	}
/** a getter method that returns all the current branches.
 * 
 * @return ArrayList<Branch> 
 */
	public ArrayList<Branch> getAllBranches() {
		return allBranches;
	}
/** a setter method, sets the current branches arrayList
 * 
 * @param allBranches type ArrayList<Branch>
 */
	public void setAllBranches(ArrayList<Branch> allBranches) {
		this.allBranches = allBranches;
	}
	
	
	
/**a getter method, this method return all the branch managers, that were received 
 * from the db
 * @return ArrayList<BranchManager>
 */
	public ArrayList<BranchManager> getAllBranchManagers() {
		return allBranchManagers;
	}
/**a setter method that sets all the branch managers list
 * 
 * @param allBranchManagers
 */
	public void setAllBranchManagers(ArrayList<BranchManager> allBranchManagers) {
		this.allBranchManagers = allBranchManagers;
	}

}
