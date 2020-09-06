package BranchManager;
/** An entity method that contains all the Revenue Report data.
 * 
 * @author Elias
 *
 */
public class revenueReport {
	
	int OrderID;
	double OrderPrice;
	/**a getter method for the orderID
	 * 
	 * @return int OrderID
	 */
	public int getOrderID() {
		return OrderID;
	}
	
	/**a setter method that saves the order ID.
	 * 
	 * @param orderID
	 */
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	
	/**a getter method that returns the current order's price.
	 *  
	 * @return double OrderPrice
	 */
	public double getOrderPrice() {
		return OrderPrice;
	}
	
	/** a setter method that returns the current order price.
	 * 
	 * @param orderPrice
	 */
	public void setOrderPrice(double orderPrice) {
		OrderPrice = orderPrice;
	}
	/**a method that returns the string representation of the revenueReport class.
	 * @override String toSting() of Object class.
	 */
	@Override
	public String toString() {
		return "revenueReport [OrderID=" + OrderID + ", OrderPrice=" + OrderPrice + "]";
	}
	public revenueReport(int orderID, double orderPrice) {
		super();
		OrderID = orderID;
		OrderPrice = orderPrice;
	}
	
	/** revenueReport constructor.
	 * 
	 */
	public revenueReport() {
		super();
	}
	
	

}
