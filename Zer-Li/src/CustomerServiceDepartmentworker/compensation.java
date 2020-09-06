package CustomerServiceDepartmentworker;
/**the following class is an entity that contains the data for the compensation that is given
 * by the customer service department
 * @author Alex
 *
 */
public class compensation {
	
	private int compensationID;
	private int customerID;
	private int csde_id;
	private double compensationAmount;
	private String isPaid;
	
	
	/**getter method for the CompensationID
	 * 
	 * @return compensationID type of int
	 */
	public  int getCompensationID() {
		return compensationID;
	}
	/**A setter method for the compensationID 
	 * 
	 * @param compensationID type of int
	 */
	public void setCompensationID(int compensationID) {
		this.compensationID = compensationID;
	}
	/** a getter method for the customerID
	 * 
	 * @return customerID type of int
	 */
	public int getCustomerID() {
		return customerID;
	}
	/** a setter method for the customerID
	 * 
	 * @param customerID type int
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**a getter method for the CSDE_ID
	 * 
	 * @return csde_id type of int
	 */
	public int getCsde_id() {
		return csde_id;
	}
	
	/**a setter method for the CSDE_ID 
	 * 
	 * @param csde_id type of int
	 */
	public void setCsde_id(int csde_id) {
		this.csde_id = csde_id;
	}
	/** a getter method for the compensation amount
	 * .
	 * @return  compensationAmount type double
	 */
	public double getCompensationAmount() {
		return compensationAmount;
	}
	/** a setter method for the compensationAmount
	 * 
	 * @param compensationAmount type of double
	 */
	public void setCompensationAmount(double compensationAmount) {
		this.compensationAmount = compensationAmount;
	}
	
	/** a getter method for the isPaid field
	 * 
	 * @return isPaid Type String
	 */
	public String getIsPaid() {
		return isPaid;
	}
	
	
	/**a setter method for the IsPaid
	 * 
	 * @param isPaid type  String
	 */
	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}
	

}
