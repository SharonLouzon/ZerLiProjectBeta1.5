package Expert;



import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import Customer.CustomOrderControl;
import Customer.OrdersControl;
import Users.LoginContol;
import Users.User;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * this class implements expert window - expert can analyze the survey result.
 * @author Sharon
 * @version 1.0
 */

public class SurveyAnalayzingControl extends LoginContol implements Initializable

{
	/**
	 * SurveyResultList is a float vector that includes all the results of the survey that filled by the branch worker. 
	 */
	public static Vector<Float> SurveyResultList=new Vector<Float>();
	
	/**
	 * boolean stepAns is true if we are in step 1 - after filling the surveys and false otherwise
	 */
	public static boolean stepAns;
	/**
	 * Button for logout
	 */
    @FXML
    private Button btnLogout;
	/**
	 * Button to see account
	 */
    @FXML
    private Button btnAccount;
	/**
	 * Button go to home page.
	 */
    @FXML
    private Button btnHome;
    

    /**
     * AnchorPane show message to expert that there are no survey result ready.
     */
    @FXML
    private AnchorPane AnchorPaneNoSurvey;
    /**
     * AnchorPane show the survey result 
     */
    @FXML
    private AnchorPane AnchorPaneShowResult;
    /**
     * Label of result number 1
     */
    @FXML
    private Label q1Result;
    /**
     * Label of result number 2
     */
    @FXML
    private Label q2Result;
    /**
     * Label of result number 3
     */   
    @FXML
    private Label q3Result;
    /**
     * Label of result number 4
     */   
    @FXML
    private Label q4Result;
    /**
     * Label of result number 5
     */
    @FXML
    private Label q5Result;
    /**
     * Label of result number 6
     */   
    @FXML
    private Label q6Result;
    /**
     * Label of result number 7
     */    
    @FXML
    private Button seeResult;
    
    /**
     * hid current window and go to home page.
     * if fail to start the home page window send message
     * @param event pressed the home button
     */
    @FXML
    void goHome(ActionEvent event) 
    {
    		btnHome.getScene().getWindow().hide(); //hiding primary window
 	   		Stage primaryStage = new Stage();
 	   		Pane root=null;
		   SurveyAnalayzingControl aFrame = new SurveyAnalayzingControl();
		   try {
			   		aFrame.start(primaryStage);
		   } catch (Exception e1) {
			   		System.out.println("cannot start Expert Window");
		   }
    	

    }
    
    /**
     * hide current window and go to login window.
     * change the entry number to zero - only 1 person with same user can log in.
     * @param event pressed the logout button
     * @throws IOException if an I/O error occurs when opening.
     */
    @FXML
    void logoutEvent(ActionEvent event) throws IOException 
    {
    	changeEntry(UserNameToCheck); //change the entry number to zero
    	
		System.out.println("return to main menu");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
		LoginContol aFrame = new LoginContol(); // create Login Frame
		Stage arg0 = new Stage();
		aFrame.start(arg0);
    }


    /**
     * client send request to server to Get Satisfaction Survey Result and insert it inside the vector (SurveyResultList)
     */
    public void getSurveyResultList()
    {
	 	   int port=PORT;
	 	   String ip=ServerIP;
	 	   try 
	 	   {
	 		 myClient = new ChatClient(ip,port);	//create new client
	 		 myClient.setAnalayzingControl(this);
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   	myClient.sendRequestToGetSatisfactionSurveyResult(); //send request to change entry in db (server)

    }
    
    /**
     * put the vector (SurveyResultList) inside the labels of the results.
     */
    
    public void setSurveyResultInfields()
    {
    	
    	if(SurveyResultList.size()>0)
    	{
    		//round to 1 decimal place
    		float q1 = (float) (Math.round(SurveyResultList.get(0)*10.0)/10.0);
    		float q2 = (float) (Math.round(SurveyResultList.get(1)*10.0)/10.0);
    		float q3 = (float) (Math.round(SurveyResultList.get(2)*10.0)/10.0);
    		float q4 = (float) (Math.round(SurveyResultList.get(3)*10.0)/10.0);
    		float q5 = (float) (Math.round(SurveyResultList.get(4)*10.0)/10.0);
    		float q6 = (float) (Math.round(SurveyResultList.get(5)*10.0)/10.0);
    		
    		//convert to string
	    	System.out.println(SurveyResultList);
	    	String q1STR = String.valueOf(q1);
	    	String q2STR = String.valueOf(q2);
	    	String q3STR = String.valueOf(q3);
	    	String q4STR = String.valueOf(q4);
	    	String q5STR = String.valueOf(q5);
	    	String q6STR = String.valueOf(q6);
	
	    	//put results in the labels
			q1Result.setText(q1STR);
			q2Result.setText(q2STR);
			q3Result.setText(q3STR);
			q4Result.setText(q4STR);
			q5Result.setText(q5STR);
			q6Result.setText(q6STR);
    	}
    	seeResult.setVisible(false);
    	AnchorPaneShowResult.setVisible(true);
    	
    }
    
    
    
    /**
     * check if stepAns variable is true or false
     * if true call getSurveyResultList function
     * else change AnchorPane and show error massage.
     * @param event pressed the see Result button
     */
    
    @FXML
    void askForResult(ActionEvent event) 
    {
    	
    	if (stepAns) //if stepAns is true (branch worker filled all surveys) we can see survey result
    	{
    		getSurveyResultList();
    	}
    	else //stepAns is false (branch worker didn't fill all surveys) we can't see survey result
    	{
    		seeResult.setVisible(false);
    		AnchorPaneNoSurvey.setVisible(true);
    	}
    	
    }
    /**
     * client send request to server to check if step = 1 in DB table of satisfactionsurvies (meaning check if branch worker finish to fill surveys)
     */
    public void checkIfStep1()
    {
	 	   int port=PORT;
	 	   String ip=ServerIP;
	 	   try 
	 	   {
	 		 myClient = new ChatClient(ip,port);	//create new client
	 		 myClient.setAnalayzingControl(this);
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   	myClient.sendRequestToCheckStep1(); //send request to change entry in db (server)
    }
    
    
    /**
     * initialize the AnchorPanes
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		seeResult.setVisible(true);
		AnchorPaneNoSurvey.setVisible(false);
		AnchorPaneShowResult.setVisible(false);
	}
	
    
	/**
	 * show the fxml file on the screen
	 * call to checkIfStep1 function to check if step = 1 on DB
	 * Does not allow to close the window by pressing on the x button , we can close window only after logout
	 * @throws IOException if an I/O error occurs when opening.
	 */
	public void start(Stage primaryStage) throws IOException
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/Expert/SurveyAnalayzeFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("expert Main Menu"); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();

	  	
	  	checkIfStep1(); //check if branch worker finish to fill surveys (step = 1 in DB table of satisfactionsurvies)
	  	
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}		
}
