package CustomerServiceDepartmentworker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Users.LoginContol;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.beans.binding.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import Users.LoginContol;
/**a controller for the ManageComplaintFrame,
 * gives the ability to edit an active complaint. 
 * 
 * @author Alex
 *
 */
public class ManageComplaintController extends LoginContol implements Initializable  {
	@FXML
	public Label topic;
	@FXML
	public Label details;
	@FXML
	public Label customerID;
	@FXML
	public TextField topicField;
	@FXML
	public TextField detailsField;
	@FXML
	public TextField customerIDField;
	@FXML
	public Button save;
	@FXML
	public Label title;
	@FXML
	public Button UpdateProgress;
	@FXML
	public Button closeComplaint;
	
	public static complaintEntry currentComplaint;
 	
	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader
					.load(getClass().getResource("/CustomerServiceDepartmentworker/ManageComplaintFrame.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Manage Open Complaint"); // name of the title of the window
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(CustomerServiceDepartmentworkerMainWindow.pressedComplaintIndex!=-1)
		{

			//attach an event to the save button
			save.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				System.out.println("lol that tickles");
				try {
					SaveButtonClickHandler(event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			});
			
			//open a new update progress window on mouse click.
			UpdateProgress.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					UpdateComplaintProgressButton(event);
				}
				
			});

			//attach an event handler to the closeComplaint button.
			closeComplaint.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					closeComplaintButton(event);
				}
				
			});
			//get current complaint index.
			int index=CustomerServiceDepartmentworkerMainWindow.pressedComplaintIndex;
			//generate a new complaint:
			currentComplaint=new complaintEntry(CustomerServiceDepartmentworkerMainWindow.activeComplaints.get(index));
			//bind the GUI fields
			topicField.textProperty().bindBidirectional(currentComplaint.getTopic());
			detailsField.textProperty().bindBidirectional(currentComplaint.getDetails());
			customerIDField.textProperty().bindBidirectional(currentComplaint.getCustomerID());
		}
		
			
			

	}
	
	
	/**save complaint editting button event handles,
	 * sends a request to the server to update the complaint fields
	 * @param event
	 * @throws IOException
	 */
	public void SaveButtonClickHandler(ActionEvent event) throws IOException
	{
		//save the new data to a new complaint and send it to the server
		complaint editedComplaint=new complaint(currentComplaint.getCompliantID().getValue(), currentComplaint.getCustomerIDInteger().getValue(), currentComplaint.getEmpHandlingID().getValue(), currentComplaint.getTopic().getValue(), currentComplaint.getTime().getValue(), currentComplaint.getDate().getValue(), currentComplaint.getStatus().getValue(), currentComplaint.getDetails().getValue());
		//mark complaint as an edited one
		editedComplaint.newComplaint=false;
		
		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		myClient.sendRequestUpdateComplaint(editedComplaint);
	}
	
/**update Complaint Progress button click handler,
 * opens a new window were the costumer service employee can update the progress of the 
 * complaint.
 * @param event
 */
	public void UpdateComplaintProgressButton(ActionEvent event)
	{
		/** open a new edit complaint, opens the "ManageComplaintFrame"*/
		progressComplaintController editFrame = new progressComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
/*			if (mainstage != null)
				mainstage.toBack();*/
		}
	}
	
	
/**close the complaint event handler
 * opens a window for to file a clsing complaint report	
 * @param event
 */
	public void closeComplaintButton(ActionEvent event)
	{
		/** opens a closing complaint report window*/
		closeComplaintController editFrame = new closeComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
/*			if (mainstage != null)
				mainstage.toBack();*/
		}
	}
}
