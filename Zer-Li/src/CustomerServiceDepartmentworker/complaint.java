package CustomerServiceDepartmentworker;

import java.io.Serializable;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * an entity class that contains the complaint data for the server-client
 * communication
 * 
 * @author Alex
 *
 */
public class complaint implements Serializable {
	/*
	 * This class is the 'complaints' entity it will store a single entry form the
	 * DB
	 */
	private int ComplaintID;
	private int CustomerID;
	private int EmpHandling;
	private String Topic;
	private String TimeComplaint;
	private String DateComplaint;
	private String Status;
	private String Details;
	public boolean newComplaint;
/**complaint constructor
 * 
 * @param complaintID
 * @param customerID
 * @param empHandling
 * @param topic
 * @param timeComplaint
 * @param dateComplaint
 * @param status
 * @param details
 */
	public complaint(int complaintID, int customerID, int empHandling, String topic, String timeComplaint,
			String dateComplaint, String status, String details) {

		// Generate and instantiate the properties
		ComplaintID = complaintID;
		CustomerID = customerID;
		EmpHandling = empHandling;
		Topic = topic;
		TimeComplaint = timeComplaint;
		DateComplaint = dateComplaint;
		Status = status;
		Details = details;
	}
/**a getter for the complaintID
 * 
 * @return ComplaintID
 */
	public int getComplaintID() {
		return ComplaintID;
	}
/**a setter for the complaintID
 * 
 * @param complaintID
 */
	public void setComplaintID(int complaintID) {
		ComplaintID = complaintID;
	}
/** a getter for the customerID
 * 
 * @return CustomerID
 */
	public int getCustomerID() {
		return CustomerID;
	}
/** a setter for the customerID
 * 
 * @param customerID
 */
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
		;
	}
/** a getter for the Employee Handling id
 * 
 * @return EmpHandling
 */
	public int getEmpHandling() {
		return EmpHandling;
	}
/** a setter for the EmpHandling
 * 
 * @param empHandling
 */
	public void setEmpHandling(int empHandling) {
		EmpHandling = empHandling;
	}
/** a getter for the Topic 
 * 
 * @return Topic type of String
 */
	public String getTopic() {
		return Topic;
	}
/** a setter for the Topic
 * 
 * @param topic type of String
 */
	public void setTopic(String topic) {
		Topic = topic;
	}
/**time complaint getter 
 * 
 * @return TimeComplaint
 */ 
	public String getTimeComplaint() {
		return TimeComplaint;
	}
/**a getter for the time of the complaint
 * 
 * @param timeComplaint
 */
	public void setTimeComplaint(String timeComplaint) {
		TimeComplaint = timeComplaint;
	}
/** a getter for the date of the complaint
 * 
 * @return DateComplaint
 */
	public String getDateComplaint() {
		return DateComplaint;
	}
/** a setter for the DateCOmplaint
 * 
 * @param dateComplaint type of string
 */
	public void setDateComplaint(String dateComplaint) {
		DateComplaint = dateComplaint;
	}
/** a getter for the status
 * 
 * @return Status
 */
	public String getStatus() {
		return Status;
	}
/** a setter for the status.
 * 
 * @param status
 */
	public void setStatus(String status) {
		Status = status;
	}
/** a setter for the details.
 * 
 * 
 * @param details type of String
 */
	public void setDetails(String details) {
		Details = details;
	}
/** a getter for the details
 * 
 * @return Details
 */
	public String getDetails() {
		return Details;
	}

}
