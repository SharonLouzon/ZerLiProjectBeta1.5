package Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ServerDB.ServerGuiApp;
import Users.LoginApplication;
import Users.LoginContol;
import client.ChatClient;
import common.Branch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * this main window of customer
 * @author haim hadad
 *
 */
public class CustomerMainWindow extends LoginContol implements Initializable
{
	public static ObservableList<Branch> AllBranches= FXCollections.observableArrayList();
	public static ObservableList<String> AllBranchesNames= FXCollections.observableArrayList();
	public static String chosenBranchID="";
	public static String chosenBranchName="";  
	
    @FXML 
    private Button btnCancelOrder;

    @FXML
    private Button btnCart;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCustomise;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCatalog;

    @FXML
    private Button btnAccount;

    @FXML
    private ImageView background;

    @FXML
    private ComboBox<String> comboBranch;
    
    /**
     * this method will open cancel order window
     * @param event of click on a button
     */
    @FXML
    void btnCancelOrderPressed(ActionEvent event) 
    {
    	Stage primaryStage = new Stage();
    	CancelOrderControl aFrame = new CancelOrderControl();
  	  
 			
 				try 
 				{
					aFrame.start(primaryStage);
				} 
 				catch (Exception e) 
 				{
					System.out.println("Cannot open cancel window");
				}
 			
 				btnCancelOrder.getScene().getWindow().hide(); //hiding primary window
    }
    
    
    /**
     *  this method closes the current window and open the customize buy window
     * @param event event of click on button in the menu
     */
    @FXML
    void btnCustomisePressed(ActionEvent event)
    {
       	Stage primaryStage = new Stage();
       	CustomOrderControl aFrame = new CustomOrderControl();
  	  
 			
 				try 
 				{
					aFrame.start(primaryStage);
				} 
 				catch (Exception e) 
 				{
					System.out.println("Cannot open customize window");
				}
 			
 			btnCustomise.getScene().getWindow().hide(); //hiding primary window

    }
    
    
    
    /**
     * this method will bring customer to the cart, that will show him items in order, and will give him a chance to create new order
     * @param event of click on a button
     */
    @FXML
    void btnCartPressedEvenet(ActionEvent event)
    {
       	Stage primaryStage = new Stage();
       	OrdersControl aFrame = new OrdersControl();
  	  
 			try 
 			{
 				aFrame.start(primaryStage);
 			} 
 			catch (IOException e) 
 			{
 				System.out.println("Cannot start catalog Window");
 			}
 	    	btnCatalog.getScene().getWindow().hide(); //hiding primary window
    }
    
    /**
     * this method close all user window and will get him out from the system, it will send message to server to change the flag (that prevent a second login)
     * @param event	action even of click on button
     */
    @FXML
    void logoutEvent(ActionEvent event) throws IOException
    {
    	changeEntry(UserNameToCheck);
		CustomerMainWindow.chosenBranchID="";
		CustomerMainWindow.chosenBranchName="";
		System.out.println("return to main menu"); 
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
		LoginContol aFrame = new LoginContol(); // create Login Frame
		Stage arg0 = new Stage();
		aFrame.start(arg0);
		
    } 
    
    /**
     * this method will open the catalog of specific branch
     * @param event click  on a button
     */
    @FXML
    void showCatalog(ActionEvent event) 
    { 	/**this method shows catalog*/
		   System.out.println("I got the event of catalog button");	  

 	   	Stage primaryStage = new Stage();
 	   CatalogOrderControl aFrame = new CatalogOrderControl();
 	  
			try 
			{
				aFrame.start(primaryStage);
			} 
			catch (IOException e) 
			{
				System.out.println("Cannot start catalog Window");
			}
	    	btnCatalog.getScene().getWindow().hide(); //hiding primary window	

    }
    
    /**
     * this method opens the account details window of the customer, we had no time to write it.....
     * @param event event type of click on a button
     */
    @FXML
    void seeAccount(ActionEvent event) 
    {
    	btnAccount.getScene().getWindow().hide(); //hiding primary window
    	
 	   	Stage primaryStage = new Stage();
		AccountControl aFrame = new AccountControl();
			try 
			{
				aFrame.start(primaryStage);
			} 
			catch (IOException e) 
			{
				System.out.println("Cannot start Account Window");
			}
		
    }   
    
    
    /**
     * this method will open the main winow of buying process
     */
	public void start(Stage primaryStage) throws IOException  
	{		
	  	Pane root = FXMLLoader.load(getClass().getResource("/Customer/customerMainMenuWindow.fxml"));
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Customer Main Menu"); // name of the title of the window
		primaryStage.setScene(scene);		
		primaryStage.show();
		
		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		myClient.setMainCustomerControler(this);
		//myClient.sendRequestToGetAllBranchManagers();
		CustomOrderControl.allFlowers.clear();
		myClient.sendRequestToGetAllFlowers();

		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	} 

	/**
	 * this method will ask from server to get all branches, it will be called only by responding from server
	 */
	public void sendReuestToGetAllBranchesForCustomer()
	{
		AllBranches.clear();
		myClient.sendRequestToGetAllBranches();

	}

   
    /**
     * this method will check which branch customer chose
     * @param event click on combo box
     */
    @FXML
    void PickBranchComboPressed(ActionEvent event) 
    {	/**this method get the id of chosen branch for buying*/
		OrdersControl.oneBranchName.clear();
    	OrdersControl.oneBranchName.add(comboBranch.getValue());	//we transfer the branch name to the branch list of delivery, customer will be able to pick order in one single branch (if self arrival chosen)
    	for(int i=0; i<AllBranches.size();i++)
    	{
    		if(comboBranch.getValue().equals(AllBranches.get(i).getBranchName()))
    		{
    			if(!chosenBranchID.equals(""))	//if customer want to change branche, delete old list of itemsInOrder
    			{
    				OrdersControl.ItemsInOrderList.clear();
    			}
    			this.chosenBranchID=AllBranches.get(i).getBranchID();
    			this.chosenBranchName=AllBranches.get(i).getBranchName();
    			break;
    		}
    	}
    	
    	if(!this.chosenBranchID.equals(""))
    	{
    		btnCatalog.setDisable(false);	//if branch chosen, allow user to open the catalog
    		btnCustomise.setDisable(false); //if branch chosen, allow user to open the customizing
    		btnCancelOrder.setDisable(false);

    	}
    	System.out.println(""+chosenBranchID);;
    	//this.chosenBranchID
    }

    /**
     * this method will load branches to combobox, and it will prevent access to everything until he will choose catalog
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{/**loading all branches to combobox*/
		comboBranch.setItems(AllBranchesNames);	

		if(!this.chosenBranchName.equals("")) //if customer already chose branch from previous entrances, keep his choice
		{
			comboBranch.setValue(this.chosenBranchName);
    		btnCatalog.setDisable(false);	//if branch chosen, allow user to open the catalog
    		btnCustomise.setDisable(false); //if branch chosen, allow user to open the customizing
    		btnCancelOrder.setDisable(false);
    		OrdersControl.oneBranchName.clear();
    		OrdersControl.oneBranchName.add(this.chosenBranchName);

		}
		else
		{
			btnCatalog.setDisable(true);
    		btnCustomise.setDisable(true); 
    		btnCancelOrder.setDisable(true);
		}

		
	}
}
