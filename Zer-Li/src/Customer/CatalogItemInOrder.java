package Customer;

import java.io.Serializable;

/**
 * class of catalog items that customer want to buy
 * @author Haim hadad
 *
 */
public class CatalogItemInOrder extends ItemInOrder implements Serializable
{		
	/**
	 * constructor of catalog item, load abstract class, this constructor created in cases that we do not know the id of order
	 * @param itemId id of product
	 * @param itemName name of product
	 * @param price price of product
	 */
	public CatalogItemInOrder(int itemId,String itemName, double price)
	{
		super(itemId, itemName, price);
		
	}
	
	/**
	 * constructor of catalog item, load abstract class, this constructor created in cases that we do know the id of order
	 * @param orderid id of order
	 * @param itemId id of product
	 * @param itemName name of product
	 * @param price price of product
	 */
	public CatalogItemInOrder(int orderid, int itemId,String itemName, double price)
	{
		super(orderid, itemId, itemName , price);

	}
}
  