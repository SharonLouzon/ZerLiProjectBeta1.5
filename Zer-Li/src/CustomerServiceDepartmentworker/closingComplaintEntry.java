package CustomerServiceDepartmentworker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**an entity that contains the closing Complaint data
 * used for the GUI Bidirectional binding.
 * @author Alex
 *
 */
public class closingComplaintEntry {
	
	
	private SimpleIntegerProperty complaintID;
	private SimpleIntegerProperty customerID;
	private SimpleStringProperty details;
	
	
	/**closing complaint constructor. 
	 * 
	 * @param ComplaintID
	 * @param CustomerID
	 */
	public closingComplaintEntry(int ComplaintID,int CustomerID)
	{
		complaintID=new SimpleIntegerProperty();
		customerID=new SimpleIntegerProperty();
		details=new SimpleStringProperty();
		
		setComplaintID(ComplaintID);
		setCustomerID(CustomerID);

		
	}


/**complaintID getter. used for the GUI elements binding.
 * 
 * @return complaintID type of SimpleIntegerProperty
 */
	public SimpleIntegerProperty getComplaintID() {
		return complaintID;
	}

/**a setter method that saves the current ComplaintID
 * 
 * @param ComplaintID type of int
 */

	public void setComplaintID(int ComplaintID) {
		this.complaintID.setValue(ComplaintID);
	}


/** a getter method that returns the customerID value
 * 
 * @return customerID SimpleIntegerProperty
 */
	public SimpleIntegerProperty getCustomerID() {
		return customerID;
	}


/**a setter method, that sets the cutsomer ID
 * 
 * @param CustomerID type of int
 */
	public void setCustomerID(int CustomerID) {
		this.customerID.setValue(CustomerID);
	}


/** a getter method that returns the details of the complaint
 * 
 * @return details type SimpleStringProperty
 */
	public SimpleStringProperty getDetails() {
		return details;
	}


/**a setter method that sets the details of the complaint
 * 
 * @param Details
 */
	public void setDetails(String Details) {
		this.details.setValue(Details);
	}

}
