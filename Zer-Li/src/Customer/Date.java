package Customer;

import java.io.Serializable;

/**
 * class of date time of our project
 * @author éhaim hadad
 *
 */
public class Date implements Serializable, Comparable, Cloneable
{
	private int year;
	private int mounth;
	private int day;
	
	/**
	 * constractor
	 * @param y int years
	 * @param m int month
	 * @param d int day of month
	 */
	public Date(int y, int m, int d)
	{
		this.year = y;
		this.mounth = m;
		this.day = d ;
		
	}
	
	/**
	 * this method allow to copy instances
	 */
	@Override
	public Object clone () throws CloneNotSupportedException
	{
		return super.clone();
		
	}
	
	/**
	 * getter of year
	 * @return integer of year
	 */
	public int getYear() 
	{
		return year;
	}
	
	/**
	 * setter of year
	 * @param year integer
	 */
	public void setYear(int year) 
	{
		this.year = year;
	}
	
	/**
	 * getter of month
	 * @return
	 */
	public int getMounth() 
	{
		return mounth;
	}
	
	/**
	 * setter of month
	 * @param mounth int
	 */
	public void setMounth(int mounth) 
	{
		this.mounth = mounth;
	}
	
	/**
	 * getter of day
	 * @return int
	 */
	public int getDay() 
	{
		return day;
	}
	
	/**
	 * setter of day in month
	 * @param day int
	 */
	public void setDay(int day) 
	{
		this.day = day;
	}


	/**
	 * this method allow to compare between two instances of date class, by who come first
	 */

	@Override
	public int compareTo(Object someday) 
	{	/**this method tells if this date equal to another date or it is sooner or later*/
		if (someday instanceof Date)
		{
			Date anotherday = (Date)someday;
			if(this.day == anotherday.getDay() && this.mounth== anotherday.getMounth() && this.year == anotherday.getYear() )
			{
				return 1;  //this date is the same date to the another date
			}
			
			if(this.day < anotherday.getDay() && this.mounth <= anotherday.getMounth() && this.year <= anotherday.getYear() )
			{

				return -1;  //this date is sooner
			}
			
			if(this.day >= anotherday.getDay() && this.mounth < anotherday.getMounth() && this.year <= anotherday.getYear() )
			{

				return -1;  //this date is sooner
			}
			
						
			if(this.day >= anotherday.getDay() && this.mounth >= anotherday.getMounth() && this.year < anotherday.getYear() )
			{
				System.out.println("condition 4");

				return -1;	//this date is sooner
			}
			

	
		}
		return 0;	//this date is later
	}
	
	/**
	 * tostring mathod that prints date in sql form
	 */
		public String toString()
		{
			String str= "";
			if(this.mounth <10 && this.day<10)
			{
			 str= ""+this.year+ "-0"+this.mounth+ "-0"+ this.day; 
			}
			else if(this.mounth <10 && this.day>=10)
			{
			 str= ""+this.year+ "-0"+this.mounth+ "-"+ this.day; 
			}
			
			else if(this.mounth <10 && this.day>=10)
			{
			 str= ""+this.year+ "-0"+this.mounth+ "-0"+ this.day; 
			}
			
			else if(this.mounth >=10 && this.day<10)
			{
			 str= ""+this.year+ "-"+this.mounth+ "-0"+ this.day; 
			}
			//s
			else if(this.mounth >=10 && this.day>=10)
			{
			 str= ""+this.year+ "-"+this.mounth+ "-"+ this.day; 
			}
			return str;
		}
}
