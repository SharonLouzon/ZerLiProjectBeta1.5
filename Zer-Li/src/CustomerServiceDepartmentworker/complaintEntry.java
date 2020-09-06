package CustomerServiceDepartmentworker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**a complaint Entity that used for the data binding in the GUI,
 * used mainly in the CustomerServiceDepartmentworkerMainWindow controller
 * 
 * @author Alex 
 *
 */
public class complaintEntry {

	public SimpleIntegerProperty CompliantID;
	public SimpleIntegerProperty CustomerID;
	public SimpleIntegerProperty EmpHandlingID;
	public SimpleStringProperty Topic;
	public SimpleStringProperty Time;
	public SimpleStringProperty Date;
	public SimpleStringProperty Status;
	public SimpleStringProperty details;
	
	
	/**complaint entry constructor
	 * 
	 */
	public complaintEntry()
	{
		CompliantID=new SimpleIntegerProperty();
		CustomerID=new SimpleIntegerProperty();
		EmpHandlingID=new SimpleIntegerProperty();
		Topic =new SimpleStringProperty();
		Time=new SimpleStringProperty();
		Date=new SimpleStringProperty();
		Status=new SimpleStringProperty();
		details=new SimpleStringProperty();
		
	}
	/**complaint entry constructor that gets complaint
	 * 
	 * @param c
	 */
	public complaintEntry(complaint c)
	{
		this();
		
		setCompliantID(c.getComplaintID());
		setCustomerID(c.getCustomerID());
		setTime(c.getTimeComplaint());
		setDate(c.getDateComplaint());
		setDetails(c.getDetails());
		setEmpHandlingID(c.getEmpHandling());
		setStatus(c.getStatus());
		setTopic(c.getTopic());
		
	}
	
	
	/**complaintID getter
	 * 
	 * @return CompliantID
	 */
	public SimpleIntegerProperty getCompliantID() {
		return CompliantID;
	}
	/**complaintID setter
	 * 
	 * @param compliantID
	 */
	public void setCompliantID(int compliantID) {
		CompliantID.setValue(compliantID);
	}
	/**costumerID setter
	 * 
	 * @return SimpoleStringProperty
	 */
	public SimpleStringProperty getCustomerID() {
		return new SimpleStringProperty(String.valueOf(CustomerID.getValue()));
	}
	/**customer ID getter of a different type
	 * 
	 * @return SimpleStringProperty
	 */
	public SimpleIntegerProperty getCustomerIDInteger()
	{
		return CustomerID;
	}
	/**a costumerID setter
	 * 
	 * @param customerID type of int
	 */
	public void setCustomerID(int customerID) {
		CustomerID.setValue(customerID);
	}
/**employee id getter
 * 	
 * @return SimpleStringProperty
 */
	public SimpleStringProperty getEmpHandlingIDString() {
		return new SimpleStringProperty(String.valueOf(EmpHandlingID.getValue()));
	}
	
	/**employeeID getter
	 * 
	 * @return SimpleIntegerProperty
	 */
	public SimpleIntegerProperty getEmpHandlingID()
	{
		return EmpHandlingID;
	}
	/** employee handling ID setter
	 * 
	 * @param empHandlingID
	 */
	public void setEmpHandlingID(int empHandlingID) {
		EmpHandlingID.setValue(empHandlingID);
	}
	/**Topic getter
	 * 
	 * @return SimpleStringProperty
	 */
	public SimpleStringProperty getTopic() {
		return Topic;
	}
	/**topic  setter
	 * 
	 * @param topic
	 */
	public void setTopic(String topic) {
		Topic.setValue(topic);;
	}
	/** time getter 
	 * 
	 * @return SimpleStringProperty
	 */
	public SimpleStringProperty getTime() {
		return Time;
	}
	/** time getter 
	 * 
	 * @param time
	 */
	public void setTime(String time) {
		Time.setValue(time);
	}
	/**date getter
	 * 
	 * @return SimpleStringProperty
	 */
	public SimpleStringProperty getDate() {
		return Date;
	}
	/**a setter for the date of the complaint
	 * 
	 * @param date String
	 */
	public void setDate(String date) {
		Date.setValue(date);
	}
	/** a status of the copmplaint getter, can be 
	 * open or closed
	 * 
	 * @return SimpleStringProperty
	 */
	public SimpleStringProperty getStatus() {
		return Status;
	}
	/** a setter for the status
	 * 
	 * @param status SimpleStringProperty
	 */
	public void setStatus(String status) {
		Status.setValue(status);
	}
	/** a details getter, saves the details of the
	 * complaint
	 * @return SimpleStringProperty
	 */
	public SimpleStringProperty getDetails() {
		return details;
	}
	/** a setter of the details 
	 * 
	 * @param details
	 */
	public void setDetails(String details) {
		this.details.setValue(details);
	}
	
	
	
	
}
