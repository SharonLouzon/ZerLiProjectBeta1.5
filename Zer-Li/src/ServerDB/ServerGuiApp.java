package ServerDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import client.ChatClient;
import common.ChatIF;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
/**
 * main class that run server
 * @author team 13
 *
 */
public class ServerGuiApp extends Application
{

	private ServerDbController ServerWindow;
	public static  boolean serverIsConnected=false;

	
	public static void main(String[] args)
	{
        launch(args);		

	}

	@Override
	public void start(Stage arg0) throws Exception 
	{	
		ServerWindow = new ServerDbController();
		ServerWindow.start(arg0);

	}

}
