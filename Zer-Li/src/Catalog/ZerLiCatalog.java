package Catalog;

import java.util.ArrayList;
import java.util.Vector;
/**a static class that contains all the cagtalog items in a single array Vector of the class CatalogItem.
 * 
 * @author Haim
 *
 */
public class ZerLiCatalog 
{
	private static Vector<CatalogItem> AllCatalogItems=new Vector<CatalogItem>();
	
	
	/** a getter, returns the vector array of the catalog items.
	 * the method is static, and does not need an object instance to use it.
	 * @return AllCatalogItems Vector<CatalogItem>
	 */
	public static Vector<CatalogItem> getCatalog()
	{
		return AllCatalogItems;
	}
	/** a setter method that sets the catalog items array 
	 * 
	 * @param CatalogItemsArray type ArrayList<CatalogItem>
	 */
	public static void setCatalog(ArrayList<CatalogItem> CatalogItemsArray)
	{
		AllCatalogItems.clear();
		for(int i=0 ; i < CatalogItemsArray.size() ; i++)
		{
			AllCatalogItems.add(CatalogItemsArray.get(i));
		}
	}

}
