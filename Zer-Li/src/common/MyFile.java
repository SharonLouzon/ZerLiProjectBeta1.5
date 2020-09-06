package common;

import java.io.Serializable;
// CAN BE SENT FROM CLIENT TO SERVER
/**<h1>MyFile Class</h1>
 * <p>This class assist in sending files such as images from the server to the client 
 * and vice versa.
 * saves, the file in binary format in a 'byte[]' array.
 *  </p>
 * @author Sharon,Haim,Elias
 *
 */
public class MyFile implements Serializable {
	
	private String Description=null;
	private String fileName=null;	
	private int size=0;
	public  byte[] mybytearray;
	
/**the following method generates a binary array.
 * 
 * @param size
 */
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	/** this method saves the file name.
	 * 
	 * @param fileName
	 */
	public MyFile( String fileName) {
		this.fileName = fileName;
	}
	
	/**a getter for the file name
	 * 
	 * @return filename type of String
	 */
	public String getFileName() {
		return fileName;
	}
/** a setter for the file name.
 * 
 * @param fileName
 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**return the size of the file in bytes.
	 * 
	 * @return size type of int
	 */
	public int getSize() {
		return size;
	}

	/**a method to set the file size
	 * 
	 * @param size in bytes, variable type is int.
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
	/**a getter method to get the file binary representation
	 * 
	 * @return mybytearray type byte[]
	 */

	public byte[] getMybytearray() {
		return mybytearray;
	}
	/**a method to get the array from a specific location.
	 * 
	 * @param i starting location, type int
	 * @return mybytearray type byte[]
	 */
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	/**a method to save the file.
	 * 
	 * @param mybytearray type byte[] 
	 */
	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}
/**returns the description of the file
 * 
 * @return Description type String
 */
	public String getDescription() {
		return Description;
	}

	
	/**a method that sets the file's description.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		Description = description;
	}	
}

