package CustomerServiceDepartmentworker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SurveyController 
{
	public void start(Stage primaryStage) throws Exception 
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/CustomerServiceDepartmentworker/InsertNewSurvieResulatFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Insert New Result For Satisfaction Survey "); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();
	}
}
