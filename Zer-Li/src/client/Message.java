package client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for the messages the server send to client.
 * @author Sharon,Haim,Elias and Alex
 *
 */
public class Message implements Serializable
{
	/**
	 * the object server send - can be from any class
	 */
	private Object obj;
	/**
	 * string message
	 */
	private String type;
	/**
	 * constructor of message to send
	 * @param array
	 * @param msgType
	 */
	public Message(Object array, String msgType)
	{
		this.obj=array;
		this.type=msgType;
	}
	/**
	 * get the object
	 * @return Object - can be from any class
	 */
	public Object getMsgObject()
	{
		return this.obj;
		
	}
	/**
	 * set the object
	 * @param obj from any class
	 */
	public void setObject(Object obj) 
	{
		this.obj=obj;
	}
	/**
	 * get string message
	 * @return string message
	 */
	public String getMsgType()
	{
		return this.type;
		
	}
	/**
	 * set string message
	 * @param newMsgType
	 */
	public void setMsgType(String newMsgType)
	{
		this.type=newMsgType;
		
	}
}
