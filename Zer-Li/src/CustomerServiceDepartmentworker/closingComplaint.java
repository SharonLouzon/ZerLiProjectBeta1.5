package CustomerServiceDepartmentworker;

import java.io.Serializable;
/**an entity that contains the closingComplaint data,
 * it sent from the client to the server, in order to save the data into the database. 
 * 
 * @author Alex
 *
 */
public class closingComplaint implements Serializable {
	private int complaintID;
	private int cutsomerID;
	private String details;
	
	closingComplaint(closingComplaintEntry cce)
	{
		setComplaintID(cce.getComplaintID().getValue());
		setCutsomerID(cce.getCustomerID().getValue());
		setDetails(cce.getDetails().getValue());
	}
	
	
	/**a getter
	 * 
	 * @return	complaintID	the value of SImpleIntegerProperty 
	 */
	public int getComplaintID() {
		return complaintID;
	}
	/**a setter
	 * 
	 * @param complaintID	sets the complaintID value
	 */
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}
	/**a getter
	 * 
	 * @return cutsomerID
	 */
	public int getCutsomerID() {
		return cutsomerID;
	}
	/**a setter
	 * 
	 * @return cutsomerID
	 */
	public void setCutsomerID(int cutsomerID) {
		this.cutsomerID = cutsomerID;
	}
	/**a getter
	 * 
	 * @return details
	 */
	public String getDetails() {
		return details;
	}
	/**a setter
	 * 
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	

}
