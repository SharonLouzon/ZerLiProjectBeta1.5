package Users;

import java.io.Serializable;
/**
 * Class for implements user with specified id,UserName,password,permission,Status,entry
 * @author Sharon
 * @version 1.0
 */
public class User implements Serializable
{
	//variables
	/**
	 * User ID
	 */
	private int UserID;
	/**
	 * UserName
	 */
	private String UserName;
	/**
	 * User Password
	 */
	private String Password;
	/**
	 * User permission - branch manager/ branch worker / chain manager/ chain worker /customer / expert etc.. 
	 */
	private String Permition;
	/**
	 * User Status - block (0) or not block (1)
	 */
	private boolean Status;
	/**
	 * User Entry - show if user entered already or not
	 */
	private int Entry;

	//Constructor
	/**
	 * this constructs a user 
	 * with specified id,UserName,password,Permition,Status,entry
	 * @param id the ID of the user
	 * @param UserName the username of the user
	 * @param password the password of the user
	 * @param Permition the permission of the user: expert, customer, chainworker and etc.
	 * @param Status user block or not
	 * @param entry the user entered the app or not.
	 */
	public User(int id, String UserName, String password, String Permition, int Status, int entry) 
	{
		this.UserID = id;
		this.UserName = UserName;
		this.Password = password;
		this.Permition = Permition;
		if(Status==1) this.Status = true;
		else  this.Status =false;
		this.Entry= entry;
	}

	//getters and setters:
	/**
	 * get the user ID
	 * @return integer user ID
	 */
	public int getId() {
		return this.UserID;
	}
	/**
	 * set the user ID to given ID
	 * @param id user ID
	 */
	public void setId(int id) {
		this.UserID = id;
		System.out.println("ID set to "+id);
	}
	/**
	 * get the username
	 * @return string username
	 */
	public String getUserName() {
		return this.UserName;
	}
	/**
	 * set the user name to given UserName
	 * @param UserName the new username
	 */
	public void setUserName(String UserName) {
		this.UserName = UserName;
		System.out.println("UserName set to "+UserName);
	}

	/**
	 * get the user password
	 * @return string of user password
	 */
	public String getPassword() {
		return this.Password;
	}

	/**
	 * set the user password
	 * @param Password the new user password
	 */
	public void setPassword(String Password) {
		this.Password = Password;
		System.out.println("Password set to "+Password);
	}
	/**
	 * get the user permission
	 * @return string of the user permition
	 */
	public String getPermition() {
		return this.Permition;
	}

	/**
	 * set the user permission
	 * @param Permition the new user permission
	 */
	public void setPermition(String Permition) {
		this.Permition = Permition;
		System.out.println("Permition set to "+Permition);
	}
	/**
	 * get the user statues - block (0) or not block (1)
	 * @return boolean status of user.
	 */
	public Boolean getStatus() {
		return this.Status;
	}

	/**
	 * set the user Status  - block (0) or not block (1)
	 * @param Status the new user status
	 */
	public void setPermition(Boolean Status) {
		this.Status = Status;
		if(Status==true)
		System.out.println("Status set to not block");
		else
			System.out.println("Status set to block");
	}
	/**
	 *  set the user Entry - if user entered already or not
	 * @param Entry the new entry -user login or logout.
	 */
	public void setEntry(int Entry) {
		this.Entry = Entry;
		System.out.println("Password set to "+Entry);
	}
	/**
	 * get the user Entry - if user entered already or not
	 * @return integer of user Entry
	 */
	public int getEntry() {
		return this.Entry;
	}	
	

	/**
	 * tostring method show string with all user details.
	 * @return string of all user details
	 */
	public String toString(){
		String AllDetails= ""+UserID+ " "+UserName+ " "+ Password + " " + Permition+ " "+Status+ " " + Entry;
		return AllDetails;
	}

}
