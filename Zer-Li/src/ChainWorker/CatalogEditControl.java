package ChainWorker;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import Catalog.CatalogItem;
import Customer.CatalogItemGUI;
import Customer.OrdersControl;
import Users.LoginContol;
import client.ChatClient;
import common.MyFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * class for managing catalog items.
 * this class implements Chain worker window - chain worker can delete /edit / add item to catalog.
 * @author Sharon
 * @version 1.0
 */
public class CatalogEditControl extends LoginContol implements Initializable
{
	/**
	 * catalogList is a list of the catalog details (itemID , itemName , itemType , itemDescription , itemPhoto , Price, orderFromCatalog )
	 */
	public static ObservableList<CatalogItemGUI> catalogList= FXCollections.observableArrayList();

	/**
	 * int variable to Check if user pressed load already
	 */
	private int loadPressed = 0;
	/**
	 *  int variable use as a flag to check on which button we pressed recently : 
	 *  pressedBtn=1 add item ,  
	 *  pressedBtn=2 edit item
	 */
	private int pressedBtn = 0;
	/**
	 * Boolean variable
	 * the server return the answer true to ansUniqueID if the given item id is Unique 
	 * else it return false.
	 */
	public static Boolean ansUniqueID= false;

	
		//on top of the screen:
		/**
		 * Button go to home page.
		 */
    	@FXML
    	private Button btnHome;
    	/**
		 * Button to see account
		 */
	    @FXML
	    private Button btnAccount; 
	    /**
		 * Button for logout
		 */
	    @FXML
	    private Button btnLogout;
	    
	    //content
	    /**
		 * Label with the title of the current page
		 */
		@FXML
		private Label titleLabel;	
		/**
		 * by click on this Button screen change to add item screen
		 */
	    @FXML
	    private Button btnAddItem;
	    /**
		 * by click on this Button screen change to edit item screen
		 */
	    @FXML
	    private Button btnEditItem;
	    /**
		 * by click on this Button- delete the item in raw in the table
		 */
	    @FXML
	    private Button btnDeleteItem;   
	    /**
		 * by click on this Button screen change to edit item screen
		 */
	    @FXML
	    private Button btnEditCatalog;
	    /**
		 * All the catalog table
		 */
	    @FXML
	    private TableView<CatalogItemGUI> CatalogTable;
	    /**
		 * The Description Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, String> CatalogItemDescriptionColumn;
	    /**
		 * The Type Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, String> CatalogItemTypeColumn;
	    /**
		 * The Image Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, ImageView> CatalogImageColumn;
	    /**
		 * The Price Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, String> CatalogPriceColumn;
	    /**
		 * The Item Name Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, String> CatalogItemNameColumn;
	    /**
		 * The Item ID Column in the catalog table
		 */
	    @FXML
	    private TableColumn<CatalogItemGUI, Integer> CatalogItemIDColumn;
	    /**
		 * AnchorPane includes the catalog table and the add & edit & delete buttons
		 */
	    @FXML
	    private AnchorPane AnchorPaneCatalog;
	    

	    //change contact - add or edit new item screen
	    /**
		 * AnchorPane for add or edit item screen
		 */
	    @FXML
	    private AnchorPane anchorPaneAddItem;

	    /**
		 * Button for saving the new item or the edited item in catalog
		 */
	    @FXML
	    private Button saveBtn;
	    /**
		 * button to back to the catalog screen
		 */
	    @FXML
	    private Button backBtn;
	    /**
		 * TextField for filling ID
		 */
	    @FXML
	    private TextField ItemIDTextField;
	    /**
		 * TextField for filling Name
		 */
	    @FXML
	    private TextField itemNameTextField;
	    /**
		 * TextField for filling description
		 */
	    @FXML
	    private TextField descriptionTextField; 
	    /**
		 * TextField for filling type
		 */
	    @FXML
	    private TextField typeTextField;
	    /**
		 * TextField for filling ID
		 */
	    @FXML
	    private TextField priceTextField; 
	    /**
		 * TextField for filling image address
		 */
	    @FXML
	    private TextField imageTextField; 
	    /**
		 * ImageView to check if the address is correct and to show the chain worker the image he upload 
		 */
	    @FXML
	    private ImageView newImage;
	    /**
		 * button to display the picture in the imageview
		 */
	    @FXML
	    private Button loadBtn;

	    
	    //****************************************************************
	    
	    /**
	     * hide current window and go to login window.
	     * change the entry number to zero - only 1 person with same user can log in.
	     * @param event pressed on the logout button
	     * @throws IOException if an I/O error occurs when opening.
	     */
	    @FXML
	    void logoutEvent(ActionEvent event) throws IOException
	    {
	    	loadPressed=0;
	    	changeEntry(UserNameToCheck);
	    	
			System.out.println("return to main menu");
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
			LoginContol aFrame = new LoginContol(); // create Login Frame
			Stage arg0 = new Stage();
			OrdersControl.ItemsInOrderList.clear();
			catalogList.clear();
			aFrame.start(arg0);
	    }
	    
	    //****************************************************************
	    /**
	     * call to backevent function
	     * @param event pressed on home button
	     * @throws IOException if an I/O error occurs
	     */
		@FXML
		void goHome(ActionEvent event) throws IOException 
		{
			backEvent(event);
		}
		
		 //****************************************************************
		/**
		 * update the "pressedBtn" flag to 1 - meaning we press on add item
		 * initialize "loadPressed" flag to zero - meaning user didn't pressed load already
		 * change the title of the page
		 * and change the anchorPane to show the add item screen
		 * @param event pressed on add item button
		 */
	    @FXML
	    void AddItemEvent(ActionEvent event) 
	    {    	
	    	pressedBtn=1; //we pressed on add item
	    	loadPressed=0;
	    	titleLabel.setText("ADD NEW ITEM");
	    	AnchorPaneCatalog.setVisible(false);
	    	anchorPaneAddItem.setVisible(true);
	    	
	    }
	    
	    //****************************************************************
	    /**
	     * get the bytes and the destination address and create the file by the given bytes in the destination address.
	     * @param bFile bytes of the file
	     * @param fileDest the destination address to put the file.
	     */
	    private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {

	        FileOutputStream fileOuputStream = null;

	        try {
	            fileOuputStream = new FileOutputStream(fileDest);
	            fileOuputStream.write(bFile);

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fileOuputStream != null) {
	                try {
	                    fileOuputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	    }

	    
	    
	    /**
	     * edit the selected item.
	     * put the details of the selected item in the text fields , call to writeBytesToFileClassic function 
	     * update the "pressedBtn" flag to 2 - meaning we press on edit item
		 * initialize "loadPressed" flag to zero - meaning user didn't pressed load already
		 * change the title of the page
		 * change the anchorPane to show the add item screen
		 * in case we didn't select item in catalog - show message.
	     * @param event pressed on edit item button
	     * @throws FileNotFoundException
	     * @throws IOException if an I/O error occurs.
	     */
	    @FXML
	    void EditItemEvent(ActionEvent event) throws FileNotFoundException, IOException 
	    {
	    	ObservableList<CatalogItemGUI> itemsInRow = CatalogTable.getSelectionModel().getSelectedItems();
	    	if(!itemsInRow.isEmpty())
	    	{
	    		ItemIDTextField.setText(""+itemsInRow.get(0).getItemID());
	    		itemNameTextField.setText(""+itemsInRow.get(0).getItemName());
	    		descriptionTextField.setText(""+itemsInRow.get(0).getItemDescription());
	    		typeTextField.setText(""+itemsInRow.get(0).getItemType());
	    		priceTextField.setText(""+itemsInRow.get(0).getItemPrice());

	    		
	    		//*********************************** create the photo in the computer.
	    		
	    		byte[] bytes = itemsInRow.get(0).getItemPhoto().getMybytearray();
	    		
	    		String userDir = System.getProperty("user.dir");
				userDir = userDir + "" + "\\ZerLiProject_G13\\Zer-Li\\src\\ChainWorker\\images\\"+itemsInRow.get(0).getItemID()+".jpg";   		
	    		writeBytesToFileClassic(bytes, userDir);
	    		//***********************************
	    		
	    		imageTextField.setText(""+userDir);   		
	    		
		    	pressedBtn=2; //we pressed on edit item
		    	loadPressed=0;
		    	titleLabel.setText("EDIT ITEM");
		    	AnchorPaneCatalog.setVisible(false);
		    	ItemIDTextField.setDisable(true); //can't edit ID
		    	
		    	anchorPaneAddItem.setVisible(true);
		    	
		    	
		    	
		    	showImage(event);                          //computer "press" automatic on load image. show image to costumer
	    	}
	    	else
	    	{
	    		Alert incorrectImageAlert = new Alert(AlertType.WARNING);
	    		incorrectImageAlert.setTitle("No selected Items");
	    		incorrectImageAlert.setHeaderText("You didn't select row in the table");
	    		incorrectImageAlert.setContentText("Please select row in the table");
	    		incorrectImageAlert.showAndWait();
	    	}
	    	
	    	
	    	
	    }
	    
	    
	    //****************************************************************
	    /**
	     * delete the selected item from table and call function askToDeleteItem
	     * if didn't selected item - show message
	     * @param event clicked on delete item button
	     */
	    
	    @FXML
	    void DeleteItemEvent(ActionEvent event) 
	    {
	    	ObservableList<CatalogItemGUI> itemsInRow = CatalogTable.getSelectionModel().getSelectedItems();
	    	int itemID;
	    	if(!itemsInRow.isEmpty())
	    	{
	    		itemID= itemsInRow.get(0).getItemID();
	    		System.out.println("we choose: "+itemID); //for me to check ******** can delete this
	    		askToDeleteItem(itemID);
	    		
	    		CatalogTable.getItems().removeAll(itemsInRow);
	    	}
	    	else
	    	{
	    		Alert incorrectImageAlert = new Alert(AlertType.WARNING);
	    		incorrectImageAlert.setTitle("No selected Items");
	    		incorrectImageAlert.setHeaderText("You didn't select row in the table");
	    		incorrectImageAlert.setContentText("Please select row in the table");
	    		incorrectImageAlert.showAndWait();
	    	}
	    }
	    
	    //****************************************************************
	    /**
	     * send Request To server to delete item with the given ID
	     * @param itemID the item ID to delete
	     */
	    
	    public void askToDeleteItem(int itemID)
	    {
	 	   int port=PORT;
	 	   String ip=ServerIP;
	 	   try 
	 	   {
	 		 myClient = new ChatClient(ip,port);	//create new client
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   	myClient.sendRequestToDeleteItem(itemID); //send request to change entry in db (server)
	    }
	    
	    //****************************************************************
	    /**
	     * send Request To Check if the given ID Unique
	     * @param itemID the item ID that need to be check if Unique.
	     */
	    
	    public void checkUniqueID(int itemID)
	    {
	 	   int port=PORT;
	 	   String ip=ServerIP;
	 	   try 
	 	   {
	 		 myClient = new ChatClient(ip,port);	//create new client
	 		 myClient.setCatalogEditControl(this);
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   	myClient.sendRequestToCheckUniqueID(itemID); //send request to change entry in db (server)
	    }
	    
	    //****************************************************************
	    /**
	     * Performs input tests and if pass all the tests call functions to add item to catalog or to edit depend on the pressedBtn flag.
	     * if fail input tests show Appropriate message
	     * @param event pressed the save button
	     */
	    @FXML
	    void saveEvent(ActionEvent event) //input checks and if correct input: add item or edit item depends on what we pressed
	    {    		
			if(! (ItemIDTextField.getText().equals("") || itemNameTextField.getText().equals("") || descriptionTextField.getText().equals("") || typeTextField.getText().equals("") || priceTextField.getText().equals("")) )
			{
		    	if(!(imageTextField.getText().equals(null) || imageTextField.getText().equals("")) ) //not empty image text field
		    	{
		    		System.out.println("input is not empty");
		    		
		    		if(isParsableInt(ItemIDTextField.getText())) //if item id can convert to int
		    		{
		    			int checkID=Integer.parseInt(ItemIDTextField.getText());
		    			if(checkID>0) //if item id positive
		    			{
		    				
		    				if(isDouble(priceTextField.getText())) //if item price can convert to double
				    		{
				    			double checkprice= Double.parseDouble(priceTextField.getText());
				    			if(checkprice>0)
				    			{
						    		if(loadPressed==1)
						    		{
							    		if(newImage.getImage().errorProperty().getValue().equals(false) ) //no error, we load correct image.
							    		{
							    			System.out.println("correct image");

							    			int IDfromTextField = Integer.parseInt(ItemIDTextField.getText()); //convert string to int
							    			
							    			
							    	    	if(pressedBtn==1) //add item
							    	    	{
							    	    		checkUniqueID(IDfromTextField); //check if there is no id like this. if unique id - go to func AddorEditItems to add item.
							    	    	}
							    	    	
							    	    	
							    	    	
							    	    	if(pressedBtn==2) //edit item
							    	    	{
							    	    		AddorEditItems(IDfromTextField); //edit item
							    	    	}
							    			
							    		}
							    		else//Incorrect Image Adress
							    		{
							    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
								    		incorrectImageAlert.setTitle("Incorrect Image Adress");
								    		incorrectImageAlert.setHeaderText("Incorrect Image Adress");
								    		incorrectImageAlert.setContentText("Please load correct image adress!");
								    		incorrectImageAlert.showAndWait();
							    		}
						    		}
						    		else //Did not press the load button
						    		{
						    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
							    		incorrectImageAlert.setTitle("Did not press the load button");
							    		incorrectImageAlert.setHeaderText("You Did not press the load button");
							    		incorrectImageAlert.setContentText("Please press on the load first");
							    		incorrectImageAlert.showAndWait();
						    		}
				    			}
				    			else //price is negative
					    		{
					    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
						    		incorrectImageAlert.setTitle("Negative or zero item price");
						    		incorrectImageAlert.setHeaderText("You entered Negative or zero item ID number");
						    		incorrectImageAlert.setContentText("Please enter positive number");
						    		incorrectImageAlert.showAndWait();
					    		}
				    		}	
		    				else //price is not double
				    		{
				    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
					    		incorrectImageAlert.setTitle("price is not number");
					    		incorrectImageAlert.setHeaderText("You did not enter valid number in the price field");
					    		incorrectImageAlert.setContentText("Please enter number in the price field");
					    		incorrectImageAlert.showAndWait();
				    		}
		    				
		    				
		    				
		    			}
		    			else //negative id number
			    		{
			    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
				    		incorrectImageAlert.setTitle("Negative or zero item ID number");
				    		incorrectImageAlert.setHeaderText("You entered Negative or zero item ID number");
				    		incorrectImageAlert.setContentText("Please enter positive number");
				    		incorrectImageAlert.showAndWait();
			    		}
		    		
		    		}
		    		else //id is not integer
		    		{
		    			Alert incorrectImageAlert = new Alert(AlertType.WARNING);
			    		incorrectImageAlert.setTitle("Item ID is not number");
			    		incorrectImageAlert.setHeaderText("You did not enter valid numbers in item ID!");
			    		incorrectImageAlert.setContentText("Please press enter numbers in item ID");
			    		incorrectImageAlert.showAndWait();
		    		}
		    		
		    	}
		    	else //Empty picture address
			    {
			    		Alert incorrectImageAlert = new Alert(AlertType.WARNING);
			    		incorrectImageAlert.setTitle("Empty picture address");
			    		incorrectImageAlert.setHeaderText("Empty picture address");
			    		incorrectImageAlert.setContentText("Please enter and load image adress!");
			    		incorrectImageAlert.showAndWait();
			    }
		    	
			}
		    else //empty fields
			{
				Alert incorrectImageAlert = new Alert(AlertType.WARNING);
		    	incorrectImageAlert.setTitle("Empty fields");
		    	incorrectImageAlert.setHeaderText("You did not fill all the fields");
		    	incorrectImageAlert.setContentText("Please fill the fields!");
		    	incorrectImageAlert.showAndWait();
			}
			
			
	    }
	    
	    
	    //****************************************************************
	    /**
	     * check if we can convert the textfield to integer
	     * @param input the textfield of the item ID that entered by the user
	     * @return boolean answer. true if can convert to integer, else false.
	     */
	    
	    public static boolean isParsableInt(String input)
	    {
	        boolean parsable = true;
	        try{
	            Integer.parseInt(input);
	        }catch(NumberFormatException e){
	            parsable = false;
	        }
	        return parsable;
	    }
	    
	    /**
	     * check if we can convert the textfield to double
	     * @param input the textfield of the price that entered by the user
	     * @return boolean answer. true if can convert to integer, else false.
	     */
	    
	    public static boolean isDouble(String input)
	    {
	    	boolean isDoublePrice = true;
	        try{
	        	 Double.parseDouble(input);
	        }catch(NumberFormatException e){
	        	isDoublePrice = false;
	        }
	        return isDoublePrice;
	    }
	    
	    
	    //****************************************************************
	    /**
	     * check if the given ID is Unique and if so- go to add item function.
	     * else show massage -can't add the item with this ID because item with that ID already exist.
	     * @param itemID the item ID to add
	     */

	    public void checkUniqueIDResult(int itemID)
	    {
			if(ansUniqueID==true)
			{
				System.out.println("unique item ID"); //for me i can delete this
				AddorEditItems(itemID);
			}
			else if(ansUniqueID==false) //ID already exists 
			{
				Alert incorrectImageAlert = new Alert(AlertType.WARNING);
	    		incorrectImageAlert.setTitle("ID already exists");
	    		incorrectImageAlert.setHeaderText("You entered Item ID that already exists");
	    		incorrectImageAlert.setContentText("Please try again with different item ID!");
	    		incorrectImageAlert.showAndWait();
			}
	    }
	    
	    
	    //****************************************************************
	    /**
	     * send request to Add or edit item depend on which function call it.
	     * if checkUniqueIDResult call this function - it will add new item
	     * if saveEvent call this function - it will edit new item
	     * @param itemID item ID to add OR edit.
	     */
	    
	    public void AddorEditItems(int itemID)
	    {
	 	   int port=PORT;
	 	   String ip=ServerIP;
	 	   try 
	 	   {
	 		 myClient = new ChatClient(ip,port);	//create new client
	 	   } 
	 	   catch (IOException e) 
	 	   {
	 		   System.out.println("Cannot create client");	  
	 	   }
	 	   
	 	   String tempID=ItemIDTextField.getText();
	 	   int newID = Integer.parseInt(tempID); //convert string id to int id
	 	   
	 	   String newName=itemNameTextField.getText();
	 	   String newpDescription=descriptionTextField.getText();
	 	   String newType=typeTextField.getText();
	 	   
	 	   String tempImageAdress=imageTextField.getText();
	 	   
	 	   
	 	   
	 	   MyFile newImageFile = createFile(tempImageAdress);	
	 	   
	 	   String tempPrice=priceTextField.getText();
	 	   Double newPrice = Double.parseDouble(tempPrice);  //convert string Price to double Price
	 	   
	 	 
	 	   CatalogItem newItem=new CatalogItem(newID,newName,newpDescription,newType,newImageFile ,newPrice);

	 	  
	 	   	myClient.sendRequestToAddOrEditItem(newItem); //send request to change entry in db (server)
	    }
	    
	    
	  //****************************************************************
	    /**
	     * create myfile object from the picture address
	     * if fail print message to console
	     * @param path the address of the picture in the computer
	     * @return myfile object or null if fail to create
	     */
	    
		private MyFile createFile(String path) 
		{
			MyFile fileToCreate = new MyFile(path);

			String LocalfilePath = path;

			try {

				File newFile = new File(LocalfilePath);

				byte[] mybytearray = new byte[(int) newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				fileToCreate.initArray(mybytearray.length);
				fileToCreate.setSize(mybytearray.length);
				bis.read(fileToCreate.getMybytearray(), 0, mybytearray.length);
				return fileToCreate;

			} 
			catch (Exception e) 
			{
				System.out.println("Can't create file");
			}
			return null;

		}
	    
	    

	    //****************************************************************
	    /**
	     * back to catalog management window
	     * @param event pressed on back button
	     * @throws IOException if an I/O error.
	     */

	    @FXML
	    void backEvent(ActionEvent event) throws IOException 
	    {
	    	loadPressed=0;

	    	
	    	catalogList.clear();
	    	
			int port = PORT;
			String ip = ServerIP;
			myClient = new ChatClient(ip, port); // create new client
			myClient.setCatalogEditControl(this);
			myClient.setchooseControl("CatalogEditControl");
			myClient.sendRequestToGetAllCatalogItems();
	    	
	    	
	    	
	    	
	    	anchorPaneAddItem.setVisible(false);
	    	titleLabel.setText("EDIT CATALOG");
	    	
	    	AnchorPaneCatalog.setVisible(true);
	    	
	    	ItemIDTextField.setDisable(false); //can edit ID              //*******************************
	    	
	    	ItemIDTextField.clear();
	    	itemNameTextField.clear();
	    	descriptionTextField.clear();
	    	typeTextField.clear();
	    	priceTextField.clear();
	    	imageTextField.clear();
	    	newImage.setImage(null);
	    }
	   
	    //****************************************************************
	    /**
	     * show the image in the ImageView by using the address the user entered
	     * change the loadPressed flag to 1 - meaning load button has been pressed.
	     * @param event pressed load button
	     */
	    @FXML
	    void showImage(ActionEvent event) 
	    {	
	    	loadPressed=1;
	    	String imgAdress = "file:"+imageTextField.getText();
	    	System.out.println("address: "+imgAdress); //***********************for checking
	    	Image image = new Image(imgAdress);
	    	newImage.setImage(image);
	    }

	 
	    //****************************************************************
	    /**
	     * initialize the catalog details.
	     */

		@Override
		public void initialize(URL location, ResourceBundle resources) 
		{
			CatalogItemNameColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI, String>("itemName"));
			CatalogItemIDColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI, Integer>("itemID"));
			CatalogItemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI, String>("itemDescription"));
			CatalogItemTypeColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI, String>("itemType"));
			CatalogImageColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI,  ImageView>("img"));
			CatalogPriceColumn.setCellValueFactory(new PropertyValueFactory<CatalogItemGUI, String>("ItemPriceWithCoin"));


			CatalogTable.setItems(catalogList);
			
		}

		 //****************************************************************
		/**
		 * show the fxml file of the control edit frame on the screen
		 * Does not allow to close the window by pressing on the x button , we can close window only after logout
		 * @throws IOException if an I/O error occurs when opening.
		 */

	public void start(Stage primaryStage) throws IOException 
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/ChainWorker/CatalogEditFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Catalog Managment"); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();
	  	
		int port = PORT;
		String ip = ServerIP;
		myClient = new ChatClient(ip, port); // create new client
		myClient.setCatalogEditControl(this);
		myClient.setchooseControl("CatalogEditControl");
		myClient.sendRequestToGetAllCatalogItems();
			  	
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}
}