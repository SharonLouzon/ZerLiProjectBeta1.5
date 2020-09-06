package Customer;

import java.io.Serializable;
import java.util.Comparator;

/**
 * class of flower that custom product contains
 * @author haim hadad
 *
 */
public class Flower implements Serializable
{	
	private int flowerID;
	private String flowerColor;
	private String flowerName;
	private double flowerPrice;
	private String flowerPriceWIthCoins;
	// i hate github
	/**
	 * deafult constructor
	 */
	public Flower()
	{
		
	}
	/**
	 * constructor of flower
	 * @param flowerID int id of flower
	 * @param flowerColor string color of flower
	 * @param flowerName string name of flower
	 * @param flowerPrice double price of flower
	 */
	public Flower(int flowerID, String flowerColor, String flowerName, double flowerPrice) 
	{
		this.flowerID = flowerID;
		this.flowerColor = flowerColor;
		this.flowerName = flowerName;
		this.flowerPrice = flowerPrice;
		flowerPriceWIthCoins=""+ flowerPrice+"$";
	} 

	/**
	 * getter of id of flower
	 * @return integer id of flower
	 */
	public int getFlowerID() 
	{
		return flowerID;
	}
	/**
	 * setter of id of flower
	 * @param flowerID
	 */
	public void setFlowerID(int flowerID) 
	{
		this.flowerID = flowerID;
	}

	/**
	 * getter of flower color
	 * @return string color of flower
	 */
	public String getFlowerColor() 
	{
		return flowerColor;
	}

	/**
	 * setter of color of flower
	 * @param flowerColor string
	 */
	public void setFlowerColor(String flowerColor) 
	{
		this.flowerColor = flowerColor;
	}

	
	/**
	 * getter of name of flower
	 * @return string
	 */
	public String getFlowerName() 
	{
		return flowerName;
	}

	/**
	 * setter of name of flower
	 * @param flowerName string
	 */
	public void setFlowerName(String flowerName) 
	{
		this.flowerName = flowerName;
	}

	/**
	 * getter of price of flower
	 * @return double 
	 */
	public double getFlowerPrice() 
	{
		return flowerPrice;
	}
	
	/**
	 * setter of flower price
	 * @param flowerPrice double
	 */
	public void setFlowerPrice(double flowerPrice) 
	{
		this.flowerPrice = flowerPrice;
	}

	/**
	 * to string method that creates string of flower details
	 */
	@Override
	public String toString() 
	{
		return "Flower [flowerID=" + flowerID + ", flowerColor=" + flowerColor + ", flowerName=" + flowerName
				+ ", flowerPrice=" + flowerPrice + "]";
	}

	/**
	 * getter of flower price with $
	 * @return string
	 */
	public String getFlowerPriceWIthCoins() {
		return flowerPriceWIthCoins;
	}

	/**
	 * setter of flower price with $
	 * @param flowerPriceWIthCoins string
	 */
	public void setFlowerPriceWIthCoins(String flowerPriceWIthCoins) {
		this.flowerPriceWIthCoins = flowerPriceWIthCoins;
	}

	
	
	
}
