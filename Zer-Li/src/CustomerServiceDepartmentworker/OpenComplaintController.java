package CustomerServiceDepartmentworker;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 * a controller for the NewComplaintFrame, gives the ability to create a new
 * complaint.
 * 
 * @author Alex
 * 
 * @extends LoginControl
 * @implements Initializable
 *
 */
public class OpenComplaintController extends LoginContol implements Initializable {
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
	public Button create;
	@FXML
	public Label EmplHandlingIDLabel;
	@FXML
	public TextField EmpHandlingIDField;

	public static complaintEntry currentComplaint;

	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/CustomerServiceDepartmentworker/NewComplaintsFrame.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Open a Complaint"); // name of the title of the window
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// attach an event to the save button
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					SaveButtonClickHandler(event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// generate a new complaint:
		currentComplaint = new complaintEntry();
		// bind the GUI fields
		topicField.textProperty().bindBidirectional(currentComplaint.getTopic());
		detailsField.textProperty().bindBidirectional(currentComplaint.getDetails());
		customerIDField.textProperty().bindBidirectional(currentComplaint.getCustomerID());
		EmpHandlingIDField.textProperty().bindBidirectional(currentComplaint.getEmpHandlingIDString());
	}

	/**
	 * A save complaint button (create button) event handler.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void SaveButtonClickHandler(ActionEvent event) throws IOException {
		// get current date and time for the new complaint
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		// Time
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date time = new Date();
		System.out.println("current value in customerID field: "+Integer.parseInt(customerIDField.textProperty().getValue())+" emp: "+Integer.parseInt(EmpHandlingIDField.textProperty().getValue()));
		// save the new data to a new complaint and send it to the server
		complaint newComplaint = new complaint(currentComplaint.getCompliantID().getValue(),
				Integer.parseInt(customerIDField.textProperty().getValue()), Integer.parseInt(EmpHandlingIDField.textProperty().getValue()),
				currentComplaint.getTopic().getValue(), timeFormat.format(time), dateFormat.format(date),
				currentComplaint.getStatus().getValue(), currentComplaint.getDetails().getValue());
		// mark complaint as a new one
		newComplaint.newComplaint = true;
		// create a connection to the client
		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		// send a request to generate a new complaint
		myClient.SendARequestForANewComplaint(newComplaint);
	}

	/***
	 * open a new edit complaint, opens the "ManageComplaintFrame"
	 * 
	 * @param event
	 */
	public void UpdateComplaintProgressButton(ActionEvent event) {
		progressComplaintController editFrame = new progressComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
			/*
			 * if (mainstage != null) mainstage.toBack();
			 */
		}
	}

	/**
	 * opens a closing complaint report window
	 * 
	 * @param event
	 */
	public void closeComplaintButton(ActionEvent event) {
		closeComplaintController editFrame = new closeComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
		}
	}
}
