package BranchManager;

import java.io.IOException;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;

import BranchManager.BranchManagerMainWindow;
import ChainManager.ChainManagerMainWindow;
import ChainWorker.CatalogEditControl;
import Customer.CustomerMainWindow;
import CustomerServiceDepartmentworker.CustomerServiceDepartmentworkerMainWindow;
import Expert.SurveyAnalayzingControl;
import ServerDB.EchoServer;
import ServerDB.ServerGuiApp;
import SystemManager.ManagmentControl;
import client.ChatClient;
import common.Branch;
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
import Users.LoginContol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import BranchManager.PaymentAccount;

/**
 * this class show window of creation of new payment account
 * @author Elias qubety
 */
public class CreatePaymentAccountController extends LoginContol implements Initializable
{
	public static ObservableList<String> PaymentType= FXCollections.observableArrayList();
	public static ObservableList<String> SubscriptionType= FXCollections.observableArrayList();

	
    private static final PaymentAccount NULL = null;

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
    private Label UserNameLabel;

    @FXML
    private Label Password;

    @FXML
    private Button ConnectBtn;

    @FXML
    private Label UserNameLabel1;

    @FXML
    private Label UserNameLabel2;

    @FXML
    private Label UserNameLabel3;

    @FXML
    private Label UserNameLabel4;

    @FXML
    private Label UserNameLabel5;

    @FXML
    private Label UserNameLabel6;

    @FXML
    private Label UserNameLabel7;

    @FXML
    private Label PhoneNumberLabel;
    
    @FXML
    private TextField phoneNumberTxt;
    
    @FXML
    private TextField txtf1;

    @FXML
    private TextField txtf2;


    @FXML
    private PasswordField passtxt;

    

    @FXML
    private TextField txtf4;

    @FXML
    private TextField txtf5;

    @FXML
    private TextField txtf6;

    @FXML
    private TextField txtf7;
    
    @FXML
    private ComboBox<String> comboBoxPaymentType;
    
    @FXML
    private ComboBox<String> ComboBoxSubscriptionType;

   
    @FXML
    void BrowseBranchReport(ActionEvent event) {

    }

    @FXML
    void CreatePaymentAccount(ActionEvent event) {

    }

    @FXML
    void DiscountingOnItem(ActionEvent event) {

    	
    }

    /***
     * this method save new payment account on database
     * @param event ActionEvent click on button
     */
    @FXML
    void SaveOnDB(ActionEvent event) {
   //  System.out.println("aaaa");
     
     int port=PORT;
	   String ip=ServerIP;
	   try 
	   {
		myClient = new ChatClient(ip,port);	//create new client to get all users in db (server)
		myClient.setLoginControl(this);
		   PaymentAccount paymentA = new PaymentAccount() ; 
		   
		   paymentA.setUserName(txtf1.getText());
		   paymentA.setCustomerID(Integer.parseInt(txtf2.getText()) );
		   paymentA.setPassword( passtxt.getText());
		   paymentA.setPhoneNumber(phoneNumberTxt.getText());
		   paymentA.setPaymentType(comboBoxPaymentType.getValue());
		   paymentA.setCreditCardNum(txtf4.getText());
		   paymentA.setCreditCardExpDate(txtf5.getText());
		   paymentA.setCvvCreditCardNum(Integer.parseInt(txtf6.getText()));
		   paymentA.setCreditCardType(txtf7.getText());
		   paymentA.setSubscriptionType(ComboBoxSubscriptionType.getValue());
		   paymentA.setBranchID(BranchManagerMainWindow.getBranchIdOfBranchManager());
		   
	   		LocalDateTime now = LocalDateTime.now(); 
	   		int years=now.getYear();
	   		int mounth=now.getMonthValue();
	   		int day=now.getDayOfMonth();
	   		String expDate="";
	   		
	   		
	   		if(paymentA.getSubscriptionType().equals("Monthly"))
	   		{
	   			if(mounth==12)
	   			{
	   				mounth=1;
	   				years++;
	   			}
	   			
	   			else
	   			{
	   				mounth++;
	   			}
	   			day=28;
	   			
	   		}
	   		else
	   		{
			 years++;

	   		}
		   
	   		String DayStr="";
	   		String MounthStr="";
	   		String yearsStr=""+years;
	   		if(mounth<10)
	   		{
	   			 MounthStr="0"+mounth;
	   		}
	   		else
	   		{
	   			 MounthStr=""+mounth;

	   		}
	   		
	   		if(day<10)
	   		{
	   			 DayStr="0"+day;
	   		}
	   		else
	   		{
	   			 DayStr=""+day;

	   		}
		   
	   		expDate=yearsStr+"-"+MounthStr+"-"+DayStr;
		   paymentA.setExpAccountDate(expDate);
		   
		   
		   System.out.println(paymentA);	  

		   myClient.sendRequestToSaveObjectOnDB(paymentA); //send request to get all users from db (server)
		   Alert alert = new Alert(AlertType.INFORMATION);
		   alert.setTitle("The creation succeeded!");
		   alert.setHeaderText(null);
		   alert.setContentText("Payment account created successfully!");

		   alert.showAndWait();

	   } 
	   catch (Exception e) 
	   {
		   System.out.println("Cannot create client");
		   Alert alert = new Alert(AlertType.ERROR);
		   alert.setTitle("Payment account creation failed");
		   alert.setHeaderText("Payment account creation failed");
		   alert.setContentText("Error in the details");

		   alert.showAndWait();
	   }
	   
	   
	 
	   
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

    /**
     * Logout event, when user click on logout button
     * @param Action event   
     */

    /**
     * this method making logout from the system
     * @param event ActionEvent click on button
     * @throws IOException when loading of login window failed
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
	public void start(Stage primaryStage) throws IOException 
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/BranchManager/NewPaymentAccountFrame.fxml"));
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		if( (PaymentType.isEmpty() && SubscriptionType.isEmpty()) )
		{
		PaymentType.add("Cash");
		PaymentType.add("CreditCard");
		comboBoxPaymentType.setItems(PaymentType);		
		SubscriptionType.add("Monthly");
		SubscriptionType.add("Yearly");
		ComboBoxSubscriptionType.setItems(SubscriptionType);
		}
		
		//System.out.println(""+BranchManagerMainWindow.allBranches);
		System.out.println("xxx "+BranchManagerMainWindow.allBrancheManagers);
		System.out.println("yyy "+BranchManagerMainWindow.allBranches);
		System.out.println("The id of branch is:" +BranchManagerMainWindow.getBranchIdOfBranchManager());
		
		
	}
}
