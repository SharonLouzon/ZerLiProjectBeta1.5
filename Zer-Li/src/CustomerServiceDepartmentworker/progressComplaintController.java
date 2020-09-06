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
import javafx.event.EventHandler;
import Users.LoginContol;
/**a controller class for the report progress window
 * 
 * @author Alex
 *
 */
public class progressComplaintController extends LoginContol implements Initializable {
	@FXML
	private Label topicTitle;
	@FXML
	private Label DetailsTitle;
	@FXML
	private TextField topic;
	@FXML
	private TextField details;
	@FXML
	private Button Save;
	@FXML
	private Button CloseComplaint;
	public static progressEntry pEntry;

	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader
					.load(getClass().getResource("/CustomerServiceDepartmentworker/progressComplaintWindow.fxml"));
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

		// add Save button click event
		Save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					SaveButtonClickHandler(event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		int index = CustomerServiceDepartmentworkerMainWindow.pressedComplaintIndex;
		pEntry = new progressEntry(CustomerServiceDepartmentworkerMainWindow.activeComplaints.get(index));
		topic.textProperty().bindBidirectional(pEntry.getTopic());
		details.textProperty().bindBidirectional(pEntry.getDetails());
	}
/**save comaplaint progress button click event handler
 * save the complaint progress to the DB via the server.
 * @param event
 * @throws IOException
 */
	public void SaveButtonClickHandler(ActionEvent event) throws IOException {
		// save the new data to a new complaintProgress and send it to the server
		complaintProgress cp = new complaintProgress(
				ManageComplaintController.currentComplaint.getCompliantID().getValue(),
				ManageComplaintController.currentComplaint.getEmpHandlingID().getValue(), pEntry.getTopic().getValue(),
				pEntry.getDetails().getValue());

		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		myClient.sendRequestSaveProgress(cp);
	}

}
