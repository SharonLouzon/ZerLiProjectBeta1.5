package Users;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;

import BranchManager.BranchManagerMainWindow;
import BranchWorker.FillSurveyControl;
import ChainManager.BranchReportBrowseControl;
import ChainManager.ChainManagerMainWindow;
import ChainWorker.CatalogEditControl;
import Customer.CustomerMainWindow;
import CustomerServiceDepartmentworker.CustomerServiceDepartmentworkerMainWindow;
import Expert.SurveyAnalayzingControl;
import ServerDB.EchoServer;
import ServerDB.ServerGuiApp;
import SystemManager.ManagmentControl;
import client.ChatClient;
import common.ChatIF;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
/**
 * LoginContol class for user to login the application.
 * this class send user to his main window by his permission.
 * @author Sharon
 * @version 1.0
 */
public class LoginContol  
{
	/**
	 * list of all the users 
	 */
	public static Vector<User> AllUsers=new Vector<User>();
	/**
	 * save the string of the username textbox 
	 */	
    protected static String UserNameToCheck;
	/**
	 * keep the userID for the next windows
	 */   
    protected static int userID;
	/**
	 * keep permition to the next windows
	 */    
    protected static String userRole="";	//this is role/ permission name
	/**
	 * Variable of type ChatClient for dialog with server
	 */
	protected ChatClient myClient;
	/**
	 * the IP of the computer where the DB find.
	 */	
	protected static String ServerIP = "localhost";
	/**
	 * the port for connecting the server
	 */
	protected static int PORT = 5555;
	
	/**
	 * button to login the app
	 */
    @FXML
    private Button LoginBtn;
	/**
	 * userName label
	 */
    @FXML
    private Label UserNameLabel;
	/**
	 * the background image of the login
	 */
    @FXML
    private ImageView ImageZerli;
	/**
	 * the password label
	 */
    @FXML
    private Label Password;
	/**
	 * text field to fill the userName
	 */    
    @FXML
    private TextField txtUserName;
	/**
	 * text field to fill the Password
	 */   
    @FXML
    private PasswordField txtPassword;
	/**
	 * exit button
	 */
    @FXML
    private Button btnExit;
	/**
	 * TextField to fill the ServerIP
	 */
    @FXML
    private TextField txtServerIP;
	/**
	 * TextField to fill the port
	 */
    @FXML
    private TextField txtPORT;

    
    /**
     * exit the Zer-Li application
     * @param event pressed the exit button
     */
    @FXML
    void Exit(ActionEvent event) 
    {
		System.out.println("exit Zer-li Application");
		System.exit(0);	
    }
    
    
    
    
    /**
     * check input test and send Request To system to Get All Users details
     * @param event pressed on login button
     */
   @FXML
    void ConnectToSystemEvent(ActionEvent event) 
    {
	   String checkPort;
	   String DeafultPort = txtPORT.getPromptText();//convert string to int
	   if(txtPORT.getText().isEmpty()) //no one entered port number .. take the deafult port
		   checkPort=DeafultPort;
	   else //someone entered number in port 
		   checkPort=txtPORT.getText(); //get the port number from the text field
	   
	   if(isParsableInt(checkPort)) //if port can convert to int
	   {
		   PORT = Integer.parseInt(checkPort); //convert string to int
			
		   int port=PORT;
		   ServerIP=txtServerIP.getText();
		   String ip=ServerIP;
		   try 
		   {
			myClient = new ChatClient(ip,port);	//create new client to get all users in db (server)
			myClient.setLoginControl(this);
		   } 
		   catch (IOException e) 
		   {
			   System.out.println("Cannot create client");	  
		   }
		   
		   myClient. sendRequestToGetAllUsers()   ;

		   
	   }
		else //port is not integer
		{
			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
	   		incorrectImageAlert.setTitle("Port is not a number");
	   		incorrectImageAlert.setHeaderText("You did not enter valid numbers in Port!");
	   		incorrectImageAlert.setContentText("Please enter valid numbers in Port (e.g 5555)");
	   		incorrectImageAlert.showAndWait();
		}
	   
	 
	   
	}
   
   /**
    * check if it possible to convert the txtPORT to integer
    * @param input the port
    * @return boolean answer if possible to convert the txtPORT to integer or not
    */
   public static boolean isParsableInt(String input) //
   {
       boolean parsable = true;
       try{
           Integer.parseInt(input);
       }catch(NumberFormatException e){
           parsable = false;
       }
       return parsable;
   }
   
   
    /**
     * check if user exist in system
     */
   public void CheckUserExist()
   {
	   String userPermition="";

	   UserNameToCheck = txtUserName.getText();		//get username from textfield
	   
	   String PasswordToCheck = txtPassword.getText();		//get password from passwordfield (kind of textfield)
	   int userEntry=1;
	   int i=0;
	   
	   
	   for(i=0 ; i< AllUsers.size() ; i++)				//scan all user to check the details we just got from human
	   {
		   User person= AllUsers.get(i);
		   if(person.getUserName().equals(UserNameToCheck))		//if you found the userName in the list (vector) of users
		   {
			   

				   if(person.getPassword().equals(PasswordToCheck)) //then check if his password correct
				   {
					   //password here is correct
					   if(person.getStatus()==false)	//check if the system manager blocked this account
					   {
						   //error message on borbidden account
						   Alert BorbbidenAccountAlert = new Alert(AlertType.WARNING);
						   BorbbidenAccountAlert.setTitle("Account forbidden");
						   BorbbidenAccountAlert.setHeaderText("Your account has been blocked");
						   BorbbidenAccountAlert.setContentText("Please contact the system manager!");
						   BorbbidenAccountAlert.showAndWait();
					   }
					   else	
					   {
						   userPermition = person.getPermition(); 	//all details approved
						   this.userID=person.getId();	//keep the userID for the next windows
						   this.userRole=person.getPermition();	//keep permition to the next windows
						   if(person.getEntry()==1)	//check if the account already in use
						   {
							   //error message on borbidden account
							   Alert BorbbidenAccountAlert = new Alert(AlertType.WARNING);
							   BorbbidenAccountAlert.setTitle("Account already in use");
							   BorbbidenAccountAlert.setHeaderText("Your account already in use");
							   BorbbidenAccountAlert.setContentText("Please logout! in any problame, contact the system manager.");
							   BorbbidenAccountAlert.showAndWait();
						   }
						   else	
						   {
							   userEntry = person.getEntry(); 	//all details approved  
							  
						   }	
						  
					   }
					  				   			   
					   
					   break;
				   }
			   
				   else
				   {
					   //password here is incorrect
					   Alert WrongPassword = new Alert(AlertType.ERROR);
					   WrongPassword.setTitle("Wrong password");
					   WrongPassword.setHeaderText("You inserted a wrong password!");
					   WrongPassword.setContentText("Please, try again.");
					   WrongPassword.showAndWait();
					   break;

				   }
				  
			 }
	   }
	   
	   if( i== AllUsers.size())
	   {
		   //userName here is incorrect
		   Alert WrongPassword = new Alert(AlertType.ERROR);
		   WrongPassword.setTitle("Wrong UserName");
		   WrongPassword.setHeaderText("You inserted a invalid username!");
		   WrongPassword.setContentText("Please, try again.");
		   WrongPassword.showAndWait();
	   }
	   
	   
	   myClient=null;		//safe step
	   AllUsers.clear(); 		//delete all users that we got in order to get updated list of users in the next pressing on the button
	   txtUserName.clear();
	   txtPassword.clear();
	   
	   //from here we will open new window according to the permission
	   //only 1 user with same username and password can enter
	   
	   

	   if( (! userPermition.equals("") )&& userEntry==0 )
	   {
		   LoginBtn.getScene().getWindow().hide(); //hiding primary window
	   Stage primaryStage = new Stage();
		Pane root=null;
	   
	   if(userPermition.equals("expert"))
	   {
		   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

		   SurveyAnalayzingControl aFrame = new SurveyAnalayzingControl();
		   try {
			   		aFrame.start(primaryStage);
		   } catch (Exception e1) {
			   		System.out.println("cannot start Expert Window");
		   }
		   
	   }
	   
	   
	   
	   else if(userPermition.equals("systemmanager"))
	   {
		   
		   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

		   ManagmentControl aFrame = new ManagmentControl();
		   try {
			   		aFrame.start(primaryStage);
		   } catch (Exception e1) {
			   		System.out.println("cannot start SystemManager Window");
		   }
	   }
	   
	   
	   else if(userPermition.equals("branchworker"))
	   {		
			   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

			   FillSurveyControl aFrame = new FillSurveyControl();
				try {
					aFrame.start(primaryStage);
				} catch (IOException e) {
					System.out.println("Cannot start branchworker Window");
				}
			
	   }
	   
	   
	   else if(userPermition.equals("branchmanager"))
	   {			
			   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

			   BranchManagerMainWindow aFrame = new BranchManagerMainWindow();
				try {
					aFrame.start(primaryStage);
				} catch (IOException e) {
					System.out.println("cannot start BranchManager Window");
				}
	   }
	   
	   
	   else if(userPermition.equals("chainworker"))
	   {
			changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

			CatalogEditControl aFrame = new CatalogEditControl();

			try {
					aFrame.start(primaryStage);
			} catch (Exception e) {
					System.out.println("Cannot start chain worker Window");
			}
					
	   }
	   
	   else if(userPermition.equals("customer") )
	   {
		   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

		   CustomerMainWindow aFrame = new CustomerMainWindow();
			try {
				aFrame.start(primaryStage);
			} catch (IOException e) {
				System.out.println("cannot start CustomerMainWindow");
			}
	   }
	   
	   else if(userPermition.equals("customerservicedepartmentemployee"))
	   {
		   
		   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

		   CustomerServiceDepartmentworkerMainWindow aFrame = new CustomerServiceDepartmentworkerMainWindow();
		   try {
			   		aFrame.start(primaryStage);
		   } catch (Exception e) {
					System.out.println("cannot start customer service department employee Window");
		   }
	   }
	   
	   
	   else if(userPermition.equals("chainmanager"))
	   {		
			   changeEntry(UserNameToCheck); //user loged in - change the entry in DB to 1

			   BranchReportBrowseControl aFrame = new BranchReportBrowseControl();
				try {
					aFrame.start(primaryStage);
				} catch (IOException e) {
					System.out.println("cannot start chain Manager Window");
				}
	   }
	   }
	   
   }
   
   
   
   /**
    * send Request To server to change entry of user (in case user login)
    * @param UserName change the entry of the given username
    */
   public void changeEntry(String UserName)
   {
	   int port=PORT;
	   String ip=ServerIP;
	   try 
	   {
		 myClient = new ChatClient(ip,port);	//create new client
		 //myClient.setLoginControl(this);
	   } 
	   catch (IOException e) 
	   {
		   System.out.println("Cannot create client");	  
	   }
	   
	   	myClient.sendRequestToChangeEntry(UserName); //send request to change entry in db (server)
   }
  

   
	/**
	 * show the fxml file of login frame on the screen
	 * allow to close the window by pressing on the x window button ,or with the exit button
	 * @throws IOException if an I/O error occurs when opening.
	 */
	public void start(Stage primaryStage) throws IOException  
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/Users/LoginWindow.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login To The System"); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();
	}

	 
}
