package SystemManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * we had no time to implements this class
 * @author team 13
 *
 */
public class ManagmentControl 
{
	public void start(Stage primaryStage) throws Exception 
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/SystemManager/ManagementFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("System Managment Main Menu"); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();
	  	
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}		
}
