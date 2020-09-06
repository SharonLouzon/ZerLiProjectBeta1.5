package ChainManager;
 
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import BranchManager.CreatePaymentAccountController;
import BranchManager.Reports;
import Customer.CatalogItemGUI;
import client.ChatClient;
/*** the chain manager main menu window.
 * from this class its possible browse reports and compare between reports.
 * 
 * @author Elias
 *
 */
public class ChainManagerMainWindow  extends LoginContol  implements Initializable
{
	  
	public static ObservableList<Reports> ReportList= FXCollections.observableArrayList();
	@FXML
    private Button btnBrowseReport;

    @FXML
    private Button CompareReportsBtn;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Reports> tableV;

    @FXML
    private TableColumn<Reports, Integer> ReportTypeCol;

    @FXML
    private TableColumn<Reports, Year> ReportYearCol;

    @FXML
    private TableColumn<Reports, Integer> ReportQuarterCol;

    @FXML
    private TableColumn<Reports, Image> ImageCol;

    @FXML
    private TableColumn<Reports, String> BranchIDCol;

    @FXML
    private Button btnCart;
/**a btnBrowseReport event handler, the emthod opens a new window to browse 
 * reports of a specific branch. 
 * @param event type of ActionEvent
 */
    @FXML
    void BrowseReport(ActionEvent event) {
    	btnBrowseReport.getScene().getWindow().hide(); //hiding primary window
		
 	   	Stage primaryStage = new Stage();
 	   BranchReportBrowseControl  aFrame = new BranchReportBrowseControl();

				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					System.out.println("Cannot start creat Account Window");
				}

    	
    }
/**A CompareReportsBtn click even handler,
 * the method opens a new window in which its possible to 
 * compare between reports.
 * @param event
 */
    @FXML
    void CompareReports(ActionEvent event) {
    	CompareReportsBtn.getScene().getWindow().hide(); //hiding primary window
		
 	   	Stage primaryStage = new Stage();
 	   CompareBranchesReportsBrowseControl aFrame = new CompareBranchesReportsBrowseControl();

				try {
					aFrame.start(primaryStage);
				} catch (Exception e) {
					System.out.println("Cannot start creat Account Window");
				}

    }

    @FXML
    void goHome(ActionEvent event) {

    }

   /**logout button event handler
    *  
    * @param event
    * @throws IOException
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
    /** the chain manager main window starter.
     * @param primaryStage type of Stage
     */
	public void start(Stage primaryStage) throws  IOException 
	{		
	    
		Parent root = FXMLLoader.load(getClass().getResource("/ChainManager/ChainManagerMainWindow.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Open New Payment Account To Customer"); // name of the title of the window
		primaryStage.setScene(scene);
	  	
 	   
		
		primaryStage.show();

		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
		 
	}

 



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		 
	}
}
