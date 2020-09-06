package BranchManager;
/**
 * Holds each entry of the sales report
 * @author Elias qubety
 *
 */

public class ordersReportEntry 
{
	private int itemType;
	private int itemQuantity;
	
	/**
	 * constructor 
	 * @param ItemType integer type of report
	 * @param ItemQuantity integer amount of items
	 */
	public ordersReportEntry(int ItemType,int ItemQuantity)
	{
		itemType=ItemType;
		itemQuantity=ItemQuantity;
	}

	/**getter of item type
	 * @return integer of item type
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * setter of new item type
	 * @param itemType integer 
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	/**
	 * getter of amount of item
	 * @return
	 */
	public int getItemQuantity() 
	{
		return itemQuantity;
	}

	/**
	 * setter of amount of items
	 * @param itemQuantity integer
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

}
