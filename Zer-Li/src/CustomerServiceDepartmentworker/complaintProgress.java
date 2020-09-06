package CustomerServiceDepartmentworker;

import java.io.Serializable;

/**
 * <h1>complaintProgress Fields</h1>
 * <p>
 * keeps the entries to be sent to the DB of the complaint progress
 * @implements Serializable 
 * 
 * @author Alex
 *         </p>
 */
public class complaintProgress implements Serializable {

	private int ComplaintID;
	private int EmpHandlingID;
	private String Topic;
	private String Details;
/**constructor
 * 
 * @param complaintID
 * @param empHandlingID
 * @param topic
 * @param details
 */
	public complaintProgress(int complaintID, int empHandlingID, String topic, String details) {
		// set the fields
		setComplaintID(complaintID);
		setEmpHandlingID(empHandlingID);
		setTopic(topic);
		setDetails(details);
	}
/**complaintID getter
 * 
 * @return int
 */
	public int getComplaintID() {
		return ComplaintID;
	}
/** a setter for the complaint ID
 * 
 * @param complaintID
 */
	public void setComplaintID(int complaintID) {
		this.ComplaintID = complaintID;
	}
/**employee handling of the complaint ID
 * 
 * @return int 
 */
	public int getEmpHandlingID() {
		return EmpHandlingID;
	}
/**set the Id of the employee that handles the comaplint
 * 
 * @param empHandlingID
 */
	public void setEmpHandlingID(int empHandlingID) {
		EmpHandlingID = empHandlingID;
	}
/**the topic of the complaint getter
 * 
 * @return
 */
	public String getTopic() {
		return Topic;
	}
/** the topic setter
 * 
 * @param topic
 */
	public void setTopic(String topic) {
		Topic = topic;
	}
/** the details of the complaint progress
 * getter
 * @return String
 */
	public String getDetails() {
		return Details;
	}
/**the details setter
 * 
 * @param details type of String
 */
	public void setDetails(String details) {
		Details = details;
	}

}
