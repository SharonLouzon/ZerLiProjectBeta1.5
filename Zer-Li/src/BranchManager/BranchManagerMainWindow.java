package BranchManager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Customer.CustomerMainWindow;
import Users.LoginContol;
import client.ChatClient;
import common.Branch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
/**a controller class foe the branchManager GUI in JavaFX
 * generates all the necessary elemnts for the GUI, and their interactions. 
 * 
 * @author Elias
 *
 */
public class BranchManagerMainWindow extends LoginContol implements Initializable
{
	public static ObservableList<Branch> allBranches= FXCollections.observableArrayList();
	public static ObservableList<BranchManager> allBrancheManagers= FXCollections.observableArrayList();


   

    @FXML
    private Button btnBrowseBranchReport;

    @FXML
    private Button btnDiscountingOnItem;

    @FXML
    private Button btnCreatePaymentAccount;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnLogout;

    
    /**
     * Method that hides the current frame 
     * and view of the selected frame,
     * when the user clicks on [btnBrowseBranchReport] button.
     * @param Action event   
     */
    @FXML
    void BrowseBranchReport(ActionEvent event) {

    	btnBrowseBranchReport.getScene().getWindow().hide(); //hiding primary window
		
 	   	Stage primaryStage = new Stage();
 	   OwnReportBrowseControl aFrame = new OwnReportBrowseControl();

				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					System.out.println("Cannot start creat report Window");
				}

    }
    /**
     * Method that hide the current frame 
     * and view the selected frame  ,
     * when user click on [btnCreatePaymentAccount] button.
     * @param Action event   
     */
    
    @FXML
    void CreatePaymentAccount(ActionEvent event)
    {
    	btnCreatePaymentAccount.getScene().getWindow().hide(); //hiding primary window
		
 	   	Stage primaryStage = new Stage();
 	   CreatePaymentAccountController aFrame = new CreatePaymentAccountController();

				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					System.out.println("Cannot start creat Account Window");
				}

				
    }
    /**
     * Method that hides the current frame 
     * and shows the selected frame,
     * when user click on [btnDiscountingOnItem] button.
     * @param Action event   
     */
    @FXML
    void DiscountingOnItem(ActionEvent event) {
btnCreatePaymentAccount.getScene().getWindow().hide(); //hiding primary window
		
 	       	Stage primaryStage = new Stage();
 	       DiscountingOnItemsControl aFrame = new DiscountingOnItemsControl();

				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					System.out.println("Cannot start creat Account Window");
				}

				
    }
    @FXML
    void seeAccount(ActionEvent event) 
    {

    }  
    
    /**
    * Logout event, when user click on the logout button
    * @param Action event   
    */

    @FXML
    void logoutEvent(ActionEvent event) throws IOException
    {
    	changeEntry(UserNameToCheck);
    	
		System.out.println("return to main menu");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
		LoginContol aFrame = new LoginContol(); // create Login Frame
		Stage arg0 = new Stage();
		aFrame.start(arg0);
		
    }
    
    /**
    * Method that closes the current window and opens the home main window
    * @param handler event
    * 
    */
    @FXML
    void goHome(ActionEvent event) 
    {

    }
    
    /**A method that starts the window
     * @param Stage
     */
	public void start(Stage primaryStage) throws IOException  
	{		
	  	Pane root = FXMLLoader.load(getClass().getResource("/BranchManager/BranchManagerMainFrame.fxml"));
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Branch Manager Main Window"); // name of the title of the window
		primaryStage.setScene(scene);	
		int port=PORT;
		   String ip=ServerIP;
		   try 
		   {
			myClient = new ChatClient(ip,port);	//create new client to get all users in db (server)
		 	allBranches.clear();  
		 	allBrancheManagers.clear();
			
		 	SpecialBranchesMessage branchMessage = new SpecialBranchesMessage();
			myClient.sendRequestToGetAllBranchManagersAndBranches(branchMessage);

		
		
		   
			myClient.sendRequestToGetAllBranches();
		   } 
		   catch (IOException e) 
		   {
			   System.out.println("Cannot create client");	  
		   }
		
		primaryStage.show();
		
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}

	public static String getBranchIdOfBranchManager()
	{
		int myID = LoginContol.userID; //get the branch manager id from the first window
		String branchIDtoReturn="";
		for(int i=0; i<allBrancheManagers.size(); i++)	//we scan all branch managers to get the current manager branchID
		{
			BranchManager bManager=allBrancheManagers.get(i);
			if(bManager.getBranchManagerID() == myID)
			{
				branchIDtoReturn=bManager.getBranchID();
				break;
			}
		}
		
		return branchIDtoReturn;
	}
	

    /**
    * Method that initialize current window after its root element has been completely processed.
    * @param URL             The location used to resolve relative paths for the root object.
    * @param ResourceBundle  The resources used to localize the root object.
    * 
    */

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		System.out.println(""+this.allBrancheManagers);
		System.out.println(""+this.allBranches);

	} 
}
