package BranchManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import java.util.Scanner;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import BranchManager.Reports;
import Customer.CatalogItemGUI;
import client.ChatClient;

public class OwnReportBrowseControl  extends LoginContol  implements Initializable
{

    @FXML
    private Label ResultLabel;
    @FXML
    
    private Label ResultLabel1;
    @FXML
    private Label resultLbl;
	 
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
    private Button BrowseBtn;

    @FXML
    private   TableView<Reports> tableV;

    @FXML
    private   TableColumn<Reports,Integer> ReportTypeCol;

    @FXML
    private   TableColumn<Reports, Year> ReportYearCol;

    @FXML
    private   TableColumn<Reports, Integer> ReportQuarterCol;

    @FXML
    private   TableColumn<Reports, String> ImageCol;
    
    @FXML
     private   TableColumn<Reports, String> BranchIDCol;

	public static ObservableList<Reports> ReportList= FXCollections.observableArrayList();

	
	/**
     * Method that get the selection row from the
     * report table and get the csv file path and 
     * send it to ReadCsvReport function.
     * @param MouseEvent    that describe click mouse action
      */
    @FXML
    void GetCsvFileReportFromTable(MouseEvent event) {
    	 ObservableList<Reports> myselectedrow=tableV.getSelectionModel().getSelectedItems();
    	 ResultLabel.setText(myselectedrow.get(0).getCsvFILE()+"");  
    	System.out.println("mouse !!!!"+myselectedrow.get(0).getReportType()); 
    	if(myselectedrow.get(0).getReportType() ==1 ) 
    	{
    		 
    		resultLbl.setText(resultLbl.getText()+" \n"+"reveune Report"); 
    	}
    	if(myselectedrow.get(0).getReportType()==3 ) 
    	{
    		resultLbl.setText(resultLbl.getText()+" \n"+"Order Report"); 
    	}
    	
    	ReadCsvReport(myselectedrow.get(0).getCsvFILE());
    }
    /**
     * Method that get CsvFile path and read it
     * line by line and view the report into [SelfBrowseReportFrame.fxml]  
     * @param String that describe the Csv file source
      */
    void ReadCsvReport(String ReportCsvFile)
    {
    	String filename =    ReportCsvFile;

	     File file =new File(filename);
	     try {
	    	 String data = null ,ViewReportInfo="" ;
	    	 Scanner inputStream =new Scanner(file);
	    	 while(inputStream.hasNext()){
	    		   data=inputStream.nextLine().split(",")[0];	    		  
	    		   ViewReportInfo=ViewReportInfo+data+"\n";
	    		 System.out.println(data+"\n");
	    		 
	    	 }
	    	 
	    	 resultLbl.setVisible(true);
	    	 ResultLabel1.setVisible(true);
	    	 ResultLabel1.setText(ViewReportInfo);
	    	 ViewReportInfo="";
	    	 inputStream =new Scanner(file);

	    	 while(inputStream.hasNext()){
	    		   data=inputStream.nextLine().split(",")[1];	    		  
	    		   ViewReportInfo=ViewReportInfo+data+"\n";
	    		 System.out.println(data+"\n");
	    		 
	    	 }
	    	 ResultLabel.setVisible(true);
	    	 ResultLabel.setText(ViewReportInfo);

	     }
	     catch(FileNotFoundException e)
	     {
   		 System.out.println("Src File Error");

	     }
    }
	
	 @FXML 
    void BrowseBranchReport(ActionEvent event) {
    	
  
   	  
      
    }

    @FXML
    void CreatePaymentAccount(ActionEvent event) {

    }

    @FXML
    void DiscountingOnItem(ActionEvent event) {

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
	 	  ReportList.clear();
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
	 	   MSG="Give me all ReportBranch"+ThisBranchId;
	 	  System.out.println(MSG.substring(0,24));
	 	 myClient.sendRequestToGetAllReports(MSG); 

		
		Parent root = FXMLLoader.load(getClass().getResource("/BranchManager/SelfBrowseReportFrame.fxml"));
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
		
		 
		 ReportTypeCol.setCellValueFactory(new PropertyValueFactory<Reports, Integer>("ReportType"));
	       ReportYearCol.setCellValueFactory(new PropertyValueFactory<Reports, Year>("ReportYear"));
	      ReportQuarterCol.setCellValueFactory(new PropertyValueFactory<Reports, Integer>("ReportQuarter"));
	     ImageCol.setCellValueFactory(new PropertyValueFactory<Reports, String>("Image"));
	     BranchIDCol.setCellValueFactory(new PropertyValueFactory<Reports, String>("BranchID")); 
	     tableV.setItems(ReportList);
	}
}
