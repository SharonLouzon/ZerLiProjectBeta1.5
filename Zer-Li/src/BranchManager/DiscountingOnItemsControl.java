package BranchManager;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import java.util.Vector;

import Users.LoginContol;
import Users.User;
import Users.LoginContol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import BranchManager.Reports;
import Customer.CatalogItemGUI;
import client.ChatClient;

/**
 * window of adding new sale to catalog item in specific branch
 * @author Elias qubety
 */
public class DiscountingOnItemsControl  extends LoginContol  implements Initializable
{
    @FXML
    private Button selectbtn;

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
 
    @FXML
    private Button btnCart;
    @FXML
    private TextField IDItemtext;

    @FXML
    private TextField percenttxt;

    @FXML
    private Button discountingBtn;
    
    @FXML
    private TableView<catalogitemsofbranch> MyTableV;

    @FXML
    private TableColumn<catalogitemsofbranch, Integer> ItemIDCol;

    @FXML
    private TableColumn<catalogitemsofbranch, Double> PriceCol;

    @FXML
    private TableColumn<catalogitemsofbranch, String> BranchIDCol;

    
	 public static ObservableList<catalogitemsofbranch> catalogitemsofbranchlist= FXCollections.observableArrayList();


	  
	 /**
	  * this method save/update new sale in database, according to the branch id that manager belong to
	  * @param event ActionEvent on click of a button
	  */
    @FXML
    void DisCountingPercent(ActionEvent event) {
 	   
    	
    	 int percent ;
    	 percent = Integer.parseInt( percenttxt.getText());
   if(!IDItemtext.getText().isEmpty() && percent>0 && percent <100   ) 
   {
	   String branchID="";
	   int branchManagerID=LoginContol.userID;
	  for(int i =0 ; i< BranchManagerMainWindow.allBrancheManagers.size() ; i++)
	  {
		  if(branchManagerID == BranchManagerMainWindow.allBrancheManagers.get(i).getBranchManagerID())
		  {
			  branchID = BranchManagerMainWindow.allBrancheManagers.get(i).getBranchID();
		  }
	  }
	   
	    
		PercentMSG PerMSG =new PercentMSG(IDItemtext.getText(), percenttxt.getText(),branchID);
	   System.out.println(PerMSG);	  

	 
		  int port=PORT ;
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
 	   
     myClient.sendRequestToUpdatePrice(PerMSG); //send request to get all users from db (server)

 	Alert alert = new Alert(AlertType.INFORMATION);
	   alert.setTitle("Discounting succeeded!");
	   alert.setHeaderText(null);
	   alert.setContentText("Discounting success!");

	   alert.showAndWait();
	   
   } 
   else  
   {
	   System.out.println("Cannot create client");
	   Alert alert = new Alert(AlertType.ERROR);
	   alert.setTitle("Discounting failed");
	   alert.setHeaderText("Discounting failed");
	   alert.setContentText("Error in the details");

	   alert.showAndWait();
   }
    	   
    }
    
    /**
     * this method shows window of reports browsing
     * @param event ActionEvent  click on button
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
     * this method shows window of creating payment account
     * @param event ActionEvent  click on button
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
     * this method shows window of adding new product to sales
     * @param event ActionEvent click on button
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
    void GetBranchReports(ActionEvent event) {

    }
    
    /**
    * Method that colse current window and open home main window
    * @param handler event
    * @return This text should describe the return type and permissible range of values
    */
    @FXML
    void goHome(ActionEvent event) 
    {
    	btnHome.getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		BranchManagerMainWindow aFrame = new BranchManagerMainWindow();
		try 
		{
			aFrame.start(primaryStage);
		} catch (IOException e) {
			System.out.println("Cannot start Customer main Window");
		}
    }

     @FXML
    void selectedItemGetId(MouseEvent event) {
    	 ObservableList<catalogitemsofbranch> myselectedrow=MyTableV.getSelectionModel().getSelectedItems();
    	 IDItemtext.setText(myselectedrow.get(0).getItemID()+"");  
    	System.out.println("mouse !!!!"); 
    } 

     /**
      * Logout event, when user click on logout button
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
     * Description of the function include the var of the function
     * @param Method that opens the current window
     */
	public void start(Stage primaryStage) throws  IOException 
	{		
		 int port=PORT ;
	 	   String ip=ServerIP,ThisBranchId,MSG;
	 	  catalogitemsofbranchlist.clear();
	 	   try 
	 	   {
	 		myClient = new ChatClient(ip,port);	//create new client to get all users in db (server)
	 		myClient.setLoginControl(this); 
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   
	 	    ThisBranchId=BranchManagerMainWindow.getBranchIdOfBranchManager();
	 	   System.out.println(ThisBranchId);
	 	   MSG="Give me all catalog items of branch"+ThisBranchId;
	 	  System.out.println(MSG.substring(0,35));
	 	 myClient.sendRequestToGetAllReports(MSG); 

		
		Parent root = FXMLLoader.load(getClass().getResource("/BranchManager/DiscountingOnItemFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Open New Payment Account To Customer"); // name of the title of the window
		primaryStage.setScene(scene);
	  	
		
 	   
		
		primaryStage.show();

		 
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
		   
	   
		
	}

 


    /**
    * Method that initialize current window after its root element has been completely processed.
    * @param URL             The location used to resolve relative paths for the root object.
    * @param ResourceBundle  The resources used to localize the root object.
    * 
    */

	/**
	 * this method define table view
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		 
	     ItemIDCol.setCellValueFactory(new PropertyValueFactory<catalogitemsofbranch, Integer>("ItemID"));
	     PriceCol.setCellValueFactory(new PropertyValueFactory<catalogitemsofbranch, Double>("Price"));
	     BranchIDCol.setCellValueFactory(new PropertyValueFactory<catalogitemsofbranch, String>("BranchID"));
	     MyTableV.setItems(catalogitemsofbranchlist); 
	     ItemIDCol.setStyle( "-fx-alignment: CENTER;");
	     PriceCol.setStyle( "-fx-alignment: CENTER;");
	     BranchIDCol.setStyle( "-fx-alignment: CENTER;");
	   
	     
	}
}
