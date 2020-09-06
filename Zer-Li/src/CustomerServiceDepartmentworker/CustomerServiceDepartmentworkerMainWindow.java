package CustomerServiceDepartmentworker;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.IOException;
import java.lang.*;
import javafx.concurrent.Task;
import Customer.CatalogItemGUI;
import Users.LoginContol;
import client.ChatClient;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.beans.binding.*;
/**customerServiceDepratment main menu controller class
 * 
 * @author Alex
 *
 */
public class CustomerServiceDepartmentworkerMainWindow extends LoginContol implements Initializable {

	public static Stage mainStageReference;
	public static // a version with a complaintRow class instead of a String
	ObservableList<complaintRow> upgradedList = FXCollections.observableArrayList();
	public static ArrayList<complaint> activeComplaints;

	@FXML
	public Button newComplaint;

	@FXML
	public ListView<complaintRow> complaintsList;

	@FXML
	private GridPane Griddy;
	@FXML
	private Button GenerateNewSurvey;

	@FXML
	private AnchorPane MainFrame;
	@FXML
	private Button FileANewReportButton;
	static int iterations;

	public static int pressedComplaintIndex = -1;
	// the unique key of the complaint, sent from the caller
	public static int complaintID;
	public static int customerServiceID;

	/** create a chatClient instance, for com with the server
	 * 
	 */
	public ChatClient cClient;
/**open the main menu window
 * 
 */
	public void start(Stage primaryStage) throws IOException {

		// will be used to track back to the main window
		mainStageReference = primaryStage;

		ChatClient cClient = new ChatClient("localhost", 5555);
		cClient.sendRequestForComplaintsList();

		Parent root = FXMLLoader.load(getClass()
				.getResource("/CustomerServiceDepartmentworker/CustomerServiceDepartmentworkerMainWindow.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Customer Service Department Worker Main Menu"); // name of the title of the window
		primaryStage.setScene(scene);
		primaryStage.show();

		Thread updateTimersThread = new Thread() {
			public void run() {
				for (int i = 0; i < 10000000; i++) {
					iterations = i;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							// get current time
							// seconds:
							DateTimeFormatter seconds = DateTimeFormatter.ofPattern("ss");
							LocalDateTime now = LocalDateTime.now();
							// minutes:
							DateTimeFormatter minutes = DateTimeFormatter.ofPattern("MM");
							// hours:
							DateTimeFormatter hours = DateTimeFormatter.ofPattern("HH");

							// upgradedList.get(0).timerTextSetter(hours.format(now),minutes.format(now),seconds.format(now));
							for (complaintRow row : upgradedList)
								row.timerTextSetter(hours.format(now), minutes.format(now), seconds.format(now));
						}
					});
				}
			}
		};
		updateTimersThread.start();

		// Can't close the window without logout
		// primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}

	/*
	 * Stackoverflow add button to list hack
	 * https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-
	 * image-button
	 */

	/**listview cell personalization
	 * 
	 * @author Alex
	 *
	 */
	static class XCell extends ListCell<complaintRow> {

		HBox hbox = new HBox();

		// probably all of this is redundant (AZ)
		Label label = new Label("(empty)");
		Pane pane = new Pane();

		// (AZ) adding a timer label
		Label time = new Label("00:00");
		Button button = new Button("update...>)");

		complaintRow lastItem;

		public XCell() {
			super();
			time.paddingProperty().setValue(new Insets(0, 10, 0, 0));
			hbox.getChildren().addAll(label, pane, time, button);
			HBox.setHgrow(pane, Priority.ALWAYS);
		}

		@Override
		protected void updateItem(complaintRow item, boolean empty) {
			super.updateItem(item, empty);// calls the xCell constructor, must be useful too bro
			setText(null); // No text in label of super class
			if (empty) {
				lastItem = null;
				setGraphic(null);
			} else {
				lastItem = item;
				label.textProperty().bind(item.complaintLabelGetter());
				time.textProperty().bind(item.timerTextGetter());
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println(lastItem + " : " + event + "complaint index: " + lastItem.ComplaintIndex);
						pressedComplaintIndex = lastItem.ComplaintIndex;
						/*
						 * call the edit button click from the complaintRow instance it will open a new
						 * window or generate a new stage
						 */
						// get the index of the clicked button
						item.buttonEventHandler();

					}
				});
				setGraphic(hbox);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		newComplaint.setText("New Complaint");

		// point the complaintList to the observable upgradedList
		complaintsList.setItems(upgradedList);
		// define the cell style
		complaintsList.setCellFactory(new Callback<ListView<complaintRow>, ListCell<complaintRow>>() {
			@Override
			public ListCell<complaintRow> call(ListView<complaintRow> param) {
				return new XCell();
			}
		});

		Button logoutBtn = (new Button("LogoutNow"));
		AnchorPane.setTopAnchor(logoutBtn, 10.0);
		AnchorPane.setRightAnchor(logoutBtn, 130.0);
		MainFrame.getChildren().add(logoutBtn);
		logoutBtn.setOpacity(0);
		logoutBtn.setOnAction(e -> BtnLogoutPressed());

	}
/**logout button click event handler
 * 
 */
	@FXML
	private void BtnLogoutPressed() {
		changeEntry(UserNameToCheck);
		System.out.println("return to main menu");
		MainFrame.getScene().getWindow().hide(); // hiding primary window
		LoginContol aFrame = new LoginContol(); // create Login Frame
		Stage arg0 = new Stage();
		try {
			aFrame.start(arg0);
		} catch (IOException e) {
			System.out.println("Cannot commit logout");
		}
	}
/**open a new complaint button event handler,
 * opens a new window, where the user can open a new
 * complaint.
 * @param event
 */
	@FXML
	private void openNewComlaintWindow(ActionEvent event) {
		OpenComplaintController editFrame = new OpenComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
		}
	}
/**generate a new survey mouse click event
 * 
 * @param event
 * @throws IOException
 */
	@FXML
	void gerateNewSurvey(ActionEvent event) throws IOException {
		System.out.println("asking the server to genrate a new survey");
		int port = LoginContol.PORT;
		String ip = LoginContol.ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		myClient.sendRequestForANewQuarterlySurvey();
	}
/**open a new quarterly complaint report window
 * 
 * @param event
 */
	@FXML
	void OpenFileNewReportWindow(ActionEvent event) {
		// open a new Report window, opens the "reportController"
		reportController editFrame = new reportController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
		}
	}
}