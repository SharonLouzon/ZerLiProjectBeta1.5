package Users;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the class to run the application
 * it's include main method. 
 * @author Sharon
 *
 */
public class LoginApplication  extends Application
{
	/**
	 * LoginContol variable to create instance of LoginContol to access the start function in LoginContol class
	 */
	private LoginContol LoginWindow;
	

	public static void main(String[] args)
	{
        launch(args);		

	}

	/**
	 * open login window
	 */
	@Override
	public void start(Stage arg0) throws Exception 
	{	
		
		LoginWindow = new LoginContol();
		LoginWindow.start(arg0);

	}


}
