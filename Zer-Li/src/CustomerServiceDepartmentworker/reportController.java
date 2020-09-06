package CustomerServiceDepartmentworker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Users.LoginContol;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class reportController extends LoginContol implements Initializable {
	@FXML
	private Label Topic;
	@FXML
	private Label quarter;
	@FXML
	private Label year;
	@FXML
	private Label report;
	@FXML
	private Label expertID;
	@FXML
	private TextField expertIDField;
	@FXML
	private ComboBox<String> quarterBox;
	@FXML
	private ComboBox<String> yearBox;
	@FXML
	private TextField reportField;
	@FXML
	private Button submitButton;
	// years for the combo box
	public static ObservableList<String> years = FXCollections.observableArrayList();
	// quarters for the combo box
	public static ObservableList<String> quarters = FXCollections.observableArrayList("1", "2", "3", "4");
	public static expertReportEntry erp;

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/CustomerServiceDepartmentworker/ReportWindow.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("report "); // name of the title of the window
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quarterBox.setItems(quarters);
		generateyears();
		// link the years list to a combo box
		yearBox.setItems(years);
		// generate new expert report entry and bind it
		erp = new expertReportEntry();
		reportField.textProperty().bindBidirectional(erp.getReport());
		expertIDField.textProperty().bindBidirectional(erp.getExpertIdStringProperty());
		// create an event to the submit button
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					submitHandler(event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

	}

	/**
	 * <h1>generates years for the combo box</h1>
	 * <p>
	 * inserts the years to a lsit with the same name
	 * </p>
	 */
	private void generateyears() {
		for (int i = 2018; i > 1990; i--)
			years.add(String.valueOf(i));
	}
	/**<h1>submit button vent handler</h>
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void submitHandler(ActionEvent event) throws IOException
	{

		try {
			expertReport er=new expertReport(Integer.valueOf(erp.getExpertIdStringProperty().getValue()),Integer.valueOf( quarterBox.getSelectionModel().getSelectedItem().toString()), Integer.valueOf(yearBox.getSelectionModel().getSelectedItem().toString()), erp.getReport().getValue());
			int port = LoginContol.PORT;
			String ip = LoginContol.ServerIP;
			myClient = new ChatClient(ip, port); // create new client
			myClient.sendREquestForANewReport(er);
		}
		catch(NullPointerException ex)
		{
			System.out.print("please select a year and/or a quarter");
		}
	}

}
