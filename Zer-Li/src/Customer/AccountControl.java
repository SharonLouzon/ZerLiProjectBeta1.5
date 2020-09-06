package Customer;


import java.io.IOException;

import Users.LoginContol;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *  controller of account, not implemented because we had no time 
 * @author Haim hadad
 */
public class AccountControl  extends LoginContol 
{
	
    @FXML
    private Button btnAccount;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCatalog;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCart;

    @FXML
    private Button btnCustomise;

    
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
    * Method that colse current window and open home main window
    * @param handler event
    * @return This text should describe the return type and permissible range of values
    */
    @FXML
    void goHome(ActionEvent event) 
    {
    	
    	btnHome.getScene().getWindow().hide(); //hiding primary window
		
 	   	Stage primaryStage = new Stage();
 	   	CustomerMainWindow aFrame = new CustomerMainWindow();
			try {
				aFrame.start(primaryStage);
			} catch (IOException e) {
				System.out.println("Cannot start Customer main Window");
			}
    }
    
    
    /**
    * Description of the function include the var of the function
    * @param Method that opens the current window
    */
	public void start(Stage primaryStage) throws IOException  
	{		
	  	Pane root = FXMLLoader.load(getClass().getResource("/Customer/AccountFrame.fxml"));
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Customer Account"); // name of the title of the window
		primaryStage.setScene(scene);		
		primaryStage.show();
		
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	} 
}
