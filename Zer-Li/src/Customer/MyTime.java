package Customer;

import java.io.Serializable;
/**
 * class of time
 * @author haim hadad
 *
 */
public class MyTime implements Serializable, Comparable,Cloneable
{
	private String hour;
	private String minutes;
	private String seconds;

	/**
	 * constructor 
	 * @param hour hour in day
	 * @param minutes minute in hour
	 * @param seconds seconds in minute
	 */
	public MyTime(String hour, String minutes, String seconds) 
	{
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * this method allow to copy instance of this class
	 */
	@Override
	public Object clone () throws CloneNotSupportedException
	{
		return super.clone();
		
	}
	
	/**
	 * this method allow to compare and check if time is equalto another time
	 */
	@Override
	public int compareTo(Object anothertime) 
	{
		if(anothertime instanceof MyTime)
		{
			MyTime check = (MyTime)anothertime;
			
			if( check.getHour().equals(this.hour) && check.getMinutes().equals(this.minutes) && check.getSeconds().equals(this.seconds))
			{
				return 1;
			}
		}
		return 0;
	}

	/**
	 * getter of hour
	 * @return string
	 */
	public String getHour() 
	{
		return hour;
	}
	/**
	 * setter of hour
	 * @param hour string
	 */
	public void setHour(String hour) 
	{
		this.hour = hour;
	}

	/**
	 * getter of minutes
	 * @return minute string
	 */
	public String getMinutes() 
	{
		return minutes;
	}

	/**
	 * setter of minutes
	 * @param minutes string
	 */
	public void setMinutes(String minutes) 
	{
		this.minutes = minutes;
	}
	
	/**
	 * getter of seconds
	 * @return second string
	 */
	public String getSeconds() 
	{
		return seconds;
	}

	/**
	 * setter of seconds
	 * @param seconds string
	 */
	public void setSeconds(String seconds) 
	{
		this.seconds = seconds;
	}

	/**
	 * this method will return string of time in normal form
	 */
	// tostring method:
	public String toString() {
		String str = this.hour+":"+this.minutes+":"+this.seconds;
		return str;
	}


}
