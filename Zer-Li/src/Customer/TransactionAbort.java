package Customer;

import java.io.Serializable;
/**
 * class of cancel order request
 * @author haim hadad
 *
 */
public class TransactionAbort implements Serializable
{
	private int orderID;
	private double orderPrice;
	private final double refund;
	boolean commit=false;
	
	/**
	 * constructor of request to abort cancel
	 * @param orderID id of order
	 * @param orderPrice price of order
	 * @param refund refund amount of to payment
	 */
	public TransactionAbort(int orderID,double orderPrice , double refund) 
	{
		super();
		this.orderID = orderID;
		this.orderPrice=orderPrice;
		this.refund = refund;
	}

	/**
	 * gettr of id of order
	 * @return int
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * setter of id of order
	 * @param orderID
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * getter of refund amount
	 * @return double
	 */
	public double getRefund() {
		return refund;
	}

	/**
	 * this method prints all transaction abort details
	 */
	@Override
	public String toString() 
	{
		return " Order No:"+this.orderID+" will get --> "+(this.refund*100 )+"% refund!!!";
	}
	
	/**
	 * getter of flag that says if the abort been made
	 * @return boolean
	 */
	public boolean isCommit() {
		return commit;
	}

	/**
	 * setter, used by server, set answer about abort (true = been made)
	 * @param commit boolean
	 */
	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	/**
	 * getter of the order price before refund
	 * @return double
	 */
	public double getOrderPrice() {
		return orderPrice;
	}

	/**
	 * setter of the order price, return order
	 * @param orderPrice new price for the order abort
	 */
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
