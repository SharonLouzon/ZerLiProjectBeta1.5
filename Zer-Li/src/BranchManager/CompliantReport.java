package BranchManager;
/**
 * class of report of branch
 * @author Elias qubety
 */
public class CompliantReport 
{
	int CountOfCompliants;
	String branchID;
	
	
	/**
	 * constructor of complaint reports
	 * @param countOfCompliants  integer amount of complaints
	 * @param branchID string id of  branch
	 */
	public CompliantReport(int countOfCompliants, String branchID) 
	{
		super();
		CountOfCompliants = countOfCompliants;
		this.branchID = branchID;
	}
	/**
	 * Default constructor
	 */
	public CompliantReport() 
	{
		super();
	}
	
	/**
	 * getter of number of reports
	 * @return integer  amount of complaint reports
	 */
	public int getCountOfCompliants() 
	{
		return CountOfCompliants;
	}
	
	/**
	 * setter of amount of complaint reports
	 * @param countOfCompliants integer of amount of complaints
	 */
	public void setCountOfCompliants(int countOfCompliants) 
	{
		CountOfCompliants = countOfCompliants;
	}
	
	/**
	 * getter of branch id
	 * @return string id of a branch
	 */
	public String getBranchID() 
	{
		return branchID;
	}
	
	/**
	 * setter of id of branch
	 * @param branchID string id of branch
	 */
	public void setBranchID(String branchID) 
	{
		this.branchID = branchID;
	}
	
	/**
	 * to string method, return string with all important details
	 */
	@Override
	public String toString() 
	{
		return "CompliantReport [CountOfCompliants=" + CountOfCompliants + ", branchID=" + branchID + "]";
	}
	
	
	

}
