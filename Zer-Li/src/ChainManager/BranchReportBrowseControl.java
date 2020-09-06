package ChainManager;
 
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
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
import javafx.scene.chart.*;


/**
 * class for managing Reports.
 * this class implements Chain Manager window - chain Manager can  Browse all reports.
 * @author Elias
 * @version 1.0
 */
public class BranchReportBrowseControl  extends LoginContol  implements Initializable
{
	/**
	 * ReportList is a list of the Reports details Reports( reportType,   reportYear, reportQuarter,   csvFILE, branchID)  
	 */
	  
	public static ObservableList<Reports> ReportList= FXCollections.observableArrayList();
	Series<Object, Object> set1;
	
	    /**
		 *  static float parameters the describe report result 
		 */
	private static float  q1=0,q2=0,q3=0,q4=0,q5=0,q6=0;
	 /**
	 * BarChart that view average Reports results  
	 */
	   @FXML
	    private BarChart<Object, Object> SurveyAVG;
	   /**
		 * CategoryAxis that view row in the graph(BarChart)  
		 */
	    @FXML
	    private CategoryAxis qes;
	    /**
		 * NumberAxis that view column in the graph(BarChart)  
		 */
	    @FXML
	    private NumberAxis avg;
	        /**
	 		 * Label that view report title 
	 		 */
    @FXML
    private Label ResultLabel;
         /**
		 * Label that view report column [0]
		 */
    @FXML
    
    private Label ResultLabel1;
    /**
	 * Label that view report column [1]
	 */
    @FXML
    private Label resultLbl;

	/**
	 * Button go to BrowseReport page.
	 */
	@FXML
    private Button btnBrowseReport;

	/**
	 * Button go to CompareReports page.
	 */
    @FXML
    private Button CompareReportsBtn;

	/**
	 * Button go to home page.
	 */
    @FXML
    private Button btnHome;
    /**
	 * Button to see account
	 */
    @FXML
    private Button btnAccount;
    /**
	 * Button for logout
	 */
    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Reports> tableV;
    /**
	 * The  Report Type Column in the Reports table
	 */
    @FXML
    private TableColumn<Reports, Integer> ReportTypeCol;
    /**
	 * The Report Year Column in the Reports table
	 */
    @FXML
    private TableColumn<Reports, Year> ReportYearCol;
    /**
	 * The Report Quarter Column in the Reports table
	 */
    @FXML
    private TableColumn<Reports, Integer> ReportQuarterCol;
    /**
	 * The csvfile Column in the Reports table
	 */
    @FXML
    private TableColumn<Reports, String> ImageCol;
    /**
	 * The Branch ID Column in the Reports table
	 */
    @FXML
    private TableColumn<Reports, String> BranchIDCol;

    /**
	 * by click on this Button screen change to add item screen
	 */
    @FXML
    private Button btnCart;

    
	
	/**
     * Method that gets the selection row from the
     * report table and gets the csv file path and 
     * send it to ReadCsvReport function.
     * @param event that describe click mouse action
      */
    @FXML
    void GetCsvFileReportFromTable(MouseEvent event) {
	  
	    
    	 ObservableList<Reports> myselectedrow=tableV.getSelectionModel().getSelectedItems();
    	 ResultLabel.setText(myselectedrow.get(0).getCsvFILE()+"");  
    	System.out.println("mouse !!!!"+myselectedrow.get(0).getReportType()); 
    	if(myselectedrow.get(0).getReportType() ==4 ) 
    	{
    		ReadCsvReportToGraph(myselectedrow.get(0).getCsvFILE());
    		SurveyAVG.setVisible(true);
    		resultLbl.setVisible(false);
	    	 ResultLabel1.setVisible(false);
	    	 ResultLabel.setVisible(false);
	     
    	     
    	}
    	else 
    	{
    		ReadCsvReport(myselectedrow.get(0).getCsvFILE());  
    		SurveyAVG.setVisible(false);
    		}
    	
     }
	
	/**
     * Method that read the csvFile column 
     * by column and gets the average of 
     * survey questions and insert the values 
     * into the graph 
     * @param csvFILE that describe path of Csv File 
      */
    private void ReadCsvReportToGraph(String csvFILE) {
    	String filename =    csvFILE;
      
	     File file =new File(filename);
	     try {
	    	 String data = null ,ViewReportInfo="" ;
	    	 Scanner inputStream =new Scanner(file);
	    	 for(int i=0;i<6;i++){
	    	 while(inputStream.hasNext()){
	    		 inputStream.nextLine();
	    		   data=inputStream.nextLine().split(",")[i];	    		  
	    	    	System.out.println( "my/////....."+data); 
	    	    	if(i==0)
	    	    	{
	    	    		 q1=Float.parseFloat(data);
	    	    	}
	    	    	else if(i==1)
	    	    	{
	    	    		 q2=Float.parseFloat(data);
	    	    	}
	    	    	else if (i==2)
	    	    	{
	    	    		 q3=Float.parseFloat(data);
	    	    	}
	    	    	 
	    	         else if(i==3)
	    	         {
	    	        	 q4=Float.parseFloat(data);
	    	    	 }
	    	         else if(i==4) {
	    	        	 q5=Float.parseFloat(data);
	    	    	 
	    	         }
	    	         else{
	    	        	 q6=Float.parseFloat(data);
	    	         }
	    	    		
	    	    	}
	    	 inputStream =new Scanner(file);
	    	 }
	    	  
	    	 resultLbl.setVisible(false);
	    	 ResultLabel1.setVisible(false);
	    	 ResultLabel1.setText(ViewReportInfo);
	     
	    	  
	     }
	     catch(FileNotFoundException e)
	     {
  		 System.out.println("Src File Error");

	     }
	     set1.getData().add(new Data<Object, Object>("q1",q1));
	     set1.getData().add(new Data<Object, Object>("q2",q2));
	     set1.getData().add(new Data<Object, Object>("q3",q3));
	     set1.getData().add(new Data<Object, Object>("q4",q4)); 
	     set1.getData().add(new Data<Object, Object>("q5",q5));
	     set1.getData().add(new Data<Object, Object>("q6",q6));
	     SurveyAVG.getData().addAll(set1);
		
	}
    /**
     * Method that read the csvFile row
     * by row and get the report[csvfile]
     * results and view it in the current windows
     * @param ReportCsvFile that describe path of Csv File 
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
    void BrowseReport(ActionEvent event) {

    }

    @FXML
    void CompareReports(ActionEvent event) {

    }

    @FXML
    void goHome(ActionEvent event) {

    }
    
    /**
     * hide current window and go to login window.
     * change the entry number to zero - only 1 person with same user can log in.
     * @param event pressed on the logout button
     * @throws IOException if an I/O error occurs when opening.
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
	 * Method that show the fxml file of the [BranchReportBrowseControl] frame on the screen
	 * Does not allow to close the window by pressing on the x button , we can close window only after logout
	 * the Method send request to the Client to get all reports 
	 * @throws IOException if an I/O error occurs when opening.
	 */
	public void start(Stage primaryStage) throws  IOException 
	{		
	   	 int port=PORT ;
	 	   String ip=ServerIP  ;
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
	 	 
	 	 myClient.sendRequestToGetAllReports("Give Me All Reports"); 

		
		Parent root = FXMLLoader.load(getClass().getResource("/ChainManager/BranchBrowseReportFrame.fxml"));
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
		
		

		// TODO Auto-generated method stub
		 ReportTypeCol.setCellValueFactory(new PropertyValueFactory<Reports, Integer>("ReportType"));
	       ReportYearCol.setCellValueFactory(new PropertyValueFactory<Reports, Year>("ReportYear"));
	      ReportQuarterCol.setCellValueFactory(new PropertyValueFactory<Reports, Integer>("ReportQuarter"));
	       ImageCol.setCellValueFactory(new PropertyValueFactory<Reports, String>("Image"));
	     BranchIDCol.setCellValueFactory(new PropertyValueFactory<Reports, String>("BranchID")); 
	     tableV.setItems(ReportList);
	      set1=new XYChart.Series<>();
	     set1.getData().add(new Data<Object, Object>("q1",q1));
	     set1.getData().add(new Data<Object, Object>("q2",q2));
	     set1.getData().add(new Data<Object, Object>("q3",q3));
	     set1.getData().add(new Data<Object, Object>("q4",q4)); 
	     set1.getData().add(new Data<Object, Object>("q5",q5));
	     set1.getData().add(new Data<Object, Object>("q6",q6));

 	     SurveyAVG.getData().addAll(set1);
	     
	}
}
