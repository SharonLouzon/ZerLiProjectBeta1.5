package Customer;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.ResourceBundle;

import Users.LoginContol;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * window of custom order of customer
 * @author haim hadad
 *
 */
public class CustomOrderControl extends LoginContol implements Initializable
{
	public static ObservableList<Flower> allFlowers= FXCollections.observableArrayList();
	public static ObservableList<Flower> CustomerFlowers= FXCollections.observableArrayList();	//flowers that customer choose
	public static ArrayList<ItemInOrder> basket = new ArrayList<ItemInOrder>();
	public static int numberOfColors=0;
	private String ItemType  ="";
	private int min=0;
	private int max=0; 
	private String ItemColor ="";
	private double priceComposition=0;
	private int quantityComposition=0;
	private int quantityOfItems=0;
	
	 
		@FXML
	    private Label minLabel;

	    @FXML
	    private Button btnLogout;

	    @FXML
	    private Button createItemBtn;

	    @FXML
	    private ComboBox<String> DominantColorCombo;

	    @FXML
	    private Label itemTypeLabel;

	    @FXML
	    private Label MaxLabel;

	    @FXML
	    private ImageView imgController;

	    @FXML
	    private Label PriceRangeLabel;

	    @FXML
	    private Label DominantColorLabel;

	    @FXML
	    private ComboBox<String> itemTypeCombo;

	    @FXML
	    private TextField priceRangeMinTxt;

	    @FXML
	    private Button btnAccount;

	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button btnCart;

	    @FXML
	    private TextField priceRangeMaxTxt;

	    @FXML
	    private Button btnCatalog;
	    
	    @FXML
	    private Label labelItemPrice;
	    
	    @FXML
	    private Label labelQtyFlower;
	    
	    @FXML
	    private TableView<Flower> flowersCompositionTable;
	    
	    @FXML
	    private TableColumn<Flower, Integer> FlowerIDClomun;
	    
	    @FXML
	    private TableColumn<Flower, String> FlowerNameClomun;
	    	    
	    @FXML
	    private TableColumn<Flower, String> FlowerColorClomun;
	    
	    @FXML
	    private TableColumn<Flower, String> FlowerPriceClomun;
	    
	    
	    @FXML
	    private Button addToCartBtn;
	    
	    
	    @FXML
	    private Button removeBtnCart;
	    
	    @FXML
	    private Label basketStatusLabel;

	    @FXML
	    private Label branchLabelAtCatalog; //added this label

	    @FXML
	    private Button cancelBtn;
	    
	    /**
	     * this method update cart and added all custom items that customer chose in single window
	     */
	    private void addBasketToCart()
	    {
	    	
	    		for(int i =0; i< basket.size() ; i++)
	    		{
	    			OrdersControl.ItemsInOrderList.add(basket.get(i));
	    		}
	    	
	    }
	    
	    /**
	     * this method will open cancel order window
	     * @param event of click on a button
	     */
	    @FXML
	    void cancelBtnPressed(ActionEvent event) 
	    {
	    	addBasketToCart();
			this.basket.clear();
	    	Stage primaryStage = new Stage();
	    	CancelOrderControl aFrame = new CancelOrderControl();
	  	  
	 			
	 				try 
	 				{
						aFrame.start(primaryStage);
					} 
	 				catch (Exception e) 
	 				{
						System.out.println("Cannot open cancel window");
					}
	 			
	 				cancelBtn.getScene().getWindow().hide(); //hiding primary window

	    }
	    
	    /**
	     * this method remove a custom item from the basket (basket = temporary place to store items)
	     */
	    @FXML
	    void removeBtnCartPressed(ActionEvent event)
	    {	
	    	quantityOfItems--;
	    	if(this.quantityOfItems>0)
	    	{
	    		basket.get(basket.size()-1).setItemQty(quantityOfItems);
	    	}
	    	
	    	else
	    	{
		    	basket.remove(basket.size()-1);
		    	removeBtnCart.setDisable(true);

	    	}
	    	
	    	basketStatusLabel.setTextFill(Color.web("#ff0000"));
	    	basketStatusLabel.setText("Removed from cart,"+" Quantity: "+ quantityOfItems);
	    	basketStatusLabel.setVisible(true);
	    	
	    	addToCartBtn.setDisable(false);

	    }
	    
	   /**
	    * this method take a custom item and put it on the basket, basket=place before cart
	    * @param event on click
	    */
	    @FXML
	    void addToCartBtnPressed(ActionEvent event)
	    {	
	    	
	    	if(this.quantityOfItems==0)
	    	{
	    		ArrayList<Flower> myFlowers = new ArrayList <Flower>();
	    		for(int i = 0 ; i< this.CustomerFlowers.size(); i++)
	    		{
	    		myFlowers.add(this.CustomerFlowers.get(i));
	    		}
	    		CustomItemInOrder myProduct= new CustomItemInOrder(0, "CustomItem-"+LoginContol.userID, this.ItemType ,this.priceComposition, myFlowers,this.ItemColor);
	    		basket.add(myProduct);
	    		quantityOfItems++;
	    	}
	    	else
	    	{
	    		quantityOfItems++;
	    		basket.get(basket.size()-1).setItemQty(quantityOfItems);
	    	}
	    	basketStatusLabel.setTextFill(Color.web("#09ff00"));
	    	basketStatusLabel.setText("Added to cart,"+" Quantity: "+ quantityOfItems);
	    	basketStatusLabel.setVisible(true);
	    	removeBtnCart.setDisable(false);
	    }

	    /**
	     * this method will open the catalog of specific branch
	     * @param event click  on a button
	     */
	    @FXML
	    void btenCatalogPressed(ActionEvent event) 
	    {
	    	addBasketToCart();
			this.basket.clear();

			   System.out.println("I got the event of catalog button");	  

		 	   	Stage primaryStage = new Stage();
		 	   CatalogOrderControl aFrame = new CatalogOrderControl();
		 	  
					try 
					{
						aFrame.start(primaryStage);
					} 
					catch (IOException e) 
					{
						System.out.println("Cannot start catalog Window");
					}
			    	btnCatalog.getScene().getWindow().hide(); //hiding primary window	

	    }
	    
	    
		/**
		 * this method will close current window, and will open the main window of buying process
		 * @param event event of click on a button
		 */

	    @FXML
	    void goHome(ActionEvent event) 
	    {
	    	addBasketToCart();
			this.basket.clear();
	    	Stage primaryStage = new Stage();
			CustomerMainWindow aFrame = new CustomerMainWindow();
			try 
			{
				aFrame.start(primaryStage);
			} 
			catch (IOException e) 
			{
				System.out.println("Cannot start Customer main Window");
			}
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	

	    }

	    /**
	     * this method opens the account details window of the customer, we had no time to write it.....
	     * @param event event type of click on a button
	     */
	    @FXML
	    void seeAccount(ActionEvent event) 
	    {
	    	addBasketToCart();
			this.basket.clear();


	    }

	    /**
	     * this method close all user window and will get him out from the system, it will send message to server to change the flag (that prevent a second login)
	     * @param event	action even of click on button
	     */
	    @FXML
	    void logoutEvent(ActionEvent event) throws IOException 
	    {
			this.basket.clear();
	    	changeEntry(UserNameToCheck);
			CustomerMainWindow.chosenBranchName="";
	    	CustomerMainWindow.chosenBranchID="";
			System.out.println("return to main menu");
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
			LoginContol aFrame = new LoginContol(); // create Login Frame
			Stage arg0 = new Stage();
			aFrame.start(arg0);
	    }

	    /**
	     * this method will bring customer to the cart, that will show him items in order, and will give him a chance to create new order
	     * @param event of click on a button
	     */
	    @FXML
	    void btnCartPressed(ActionEvent event) 
	    {
	    	addBasketToCart();
			this.basket.clear();
	      	Stage primaryStage = new Stage();
	       	OrdersControl aFrame = new OrdersControl();
 			this.btnCart.getScene().getWindow().hide(); //hiding primary window

	 			try 
	 			{
	 				aFrame.start(primaryStage);
	 			} 
	 			catch (IOException e) 
	 			{
	 				System.out.println("Cannot start catalog Window");
	 			}
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	

	    }

	    /**
	     * this method save the choice of type of custom that chosen in combo box
	     * @param event on click
	     */
	    @FXML
	    void itemTypeComboPressed(ActionEvent event) 
	    {
	    	this.ItemType=itemTypeCombo.getValue();
	    	shutDownBasket();
	    	this.quantityOfItems=0;
	    }

	    /**
	     * this method save the choice of color of custom that chosen in combo box
	     * @param event on click
	     */ 
	    @FXML
	    void DominantColorComboPressed(ActionEvent event) 
	    {
	    	this.ItemColor=DominantColorCombo.getValue();
	    	shutDownBasket();
	    	this.quantityOfItems=0;
	    }
	    
	    /**
	     * this method check composition of flowers
	     * @param event on click
	     */
	    @FXML
	    void createItemBtnPressed(ActionEvent event) 
	    {
	    	this.quantityOfItems=0;
	    	CustomerFlowers.clear();
	    	basketStatusLabel.setVisible(false);
	    	addToCartBtn.setDisable(true);
	    	removeBtnCart.setDisable(true);
	    	this.priceComposition=0;
	    	this.quantityComposition=0;
	    	if( this.ItemType.equals("") || this.priceRangeMinTxt.getText().equals("") || this.priceRangeMaxTxt.getText().equals(""))
	    	{
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("You forgot to insert data of serach");
	    		alert.setHeaderText("Please fill all necessary fields!");
	    		alert.showAndWait();
	    		return;
	    	}
	    	
	    	Integer minField = new Integer(this.priceRangeMinTxt.getText());
	    	int tempIntMin = (int)minField;
	    	Integer maxField = new Integer(this.priceRangeMaxTxt.getText());
	    	int tempIntMax = (int)maxField;
	    	int result = tempIntMax - tempIntMin;
	    	if( result <= 0 )
	    	{
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("You inserted wrong values of price range");
	    		alert.setHeaderText("Your max range is lower or equal to the min range");
	    		alert.setContentText("Ooops, You inserted wrong range");
	    		alert.showAndWait();
	    		return;
	    	}
	    	
	    	if(tempIntMax >250)
	    	{
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Sorry, There are some limits...");
	    		alert.setHeaderText("There is a limit for a custom product.");
	    		alert.setContentText("Our chain allowes a price up to 250$");
	    		alert.showAndWait();
	    		return;
	    	}
	    	
	    	this.max=tempIntMax;
	    	this.min=tempIntMin;
	    	ArrayList<Flower> flowerComposion = new ArrayList<Flower>();
	    	sortFlowers();
			System.out.println("all flowers before sorting:\n" +this.allFlowers+"\n\n");

	    	
	    	if(ItemColor.equals(""))
	    	{
	    		flowerComposion = createCompositeWithoutAnyColor(flowerComposion);
	    		
	    		System.out.println("total price of composiotn is : " +this.priceComposition +" quantity of flowers is:"+this.quantityComposition);
	    		System.out.println("all flowers are:\n" +flowerComposion);
	    	
	    	}
	    	else
	    	{
	    		flowerComposion = createCompositeWithtColor(flowerComposion);

	    	}
	    	if(this.quantityComposition<15 || this.priceComposition>this.max || this.priceComposition<this.min)
    		{
		    	CustomerFlowers.clear();
		    	this.labelQtyFlower.setText("Number of flowers: 0 items");
		    	this.labelItemPrice.setText("Item price: 0$");

    			this.quantityComposition=0;
    			this.priceComposition=0;
    			flowerComposion.clear();
    			Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Item not fount");
	    		alert.setHeaderText("We didn't find an item for you.");
	    		alert.showAndWait();

	    		return;
    		}
	    	CustomerFlowers.clear();

	    	for(int i=0 ; i < flowerComposion.size(); i++ )
	    	{
	    		CustomerFlowers.add(flowerComposion.get(i));
	    	}
	    	
	    
	    	flowersCompositionTable.setItems(CustomerFlowers);
	    	this.labelQtyFlower.setText("Number of flowers: "+this.quantityComposition+" items");
	    	this.labelItemPrice.setText("Item price: "+this.priceComposition+"$");
	    	addToCartBtn.setDisable(false);
	    }

	
	
	
	
	
	/**
	 * this method create a composition of flowers according to price range
	 * @param flowerComposion	empty arrayList of flowers class
	 * @return full arraylist in flowers (that doesn`t means that is will be ok, it thould contain at least 15 flowers)
	 */
	private ArrayList<Flower> createCompositeWithtColor(ArrayList<Flower> flowerComposion) //flowerComposion = basket 
	{
		ArrayList<String> colorsInserted = new ArrayList<String>();
		double NowpriceOfcomposite=0;
		for(int i=0 ; i < this.allFlowers.size() ; i++)	//scan all flower and put  the cheapest flower in the basket, we mean the flower with the color that customer choose, array already sorted by price from cheapest to most expensive
		{
			if(this.allFlowers.get(i).getFlowerColor().equals(this.ItemColor))	
			{
				flowerComposion.add(this.allFlowers.get(i));
				colorsInserted.add(this.allFlowers.get(i).getFlowerColor());
				NowpriceOfcomposite =  this.allFlowers.get(i).getFlowerPrice();
				break;
			}
		}
		
		for(double i=NowpriceOfcomposite ; i<= (this.max - NowpriceOfcomposite) ;i= i+NowpriceOfcomposite)
		{
			flowerComposion.add(flowerComposion.get(0));
		}
		NowpriceOfcomposite = flowerComposion.get(0).getFlowerPrice() * flowerComposion.size();
		
		int maxFlowersOfCheapestColor = flowerComposion.size() ;
		
		for(int i = 0; i < ( maxFlowersOfCheapestColor / 2); i++)
		{
			flowerComposion.remove(0);
		}
		NowpriceOfcomposite = flowerComposion.get(0).getFlowerPrice() * flowerComposion.size();

		System.out.println("total price of composiotn is : " +NowpriceOfcomposite +" quantity of flowers is:"+flowerComposion.size());
		System.out.println("all flowers are:\n" +flowerComposion);
		
		ArrayList <Flower> allDiffrentColorFlowers = new ArrayList <Flower>();
		for(int i=0 ; i < this.allFlowers.size() ; i++)	//scan all flower and put  the cheapest flower in the basket, we mean the flower with the color that customer choose, array already sorted by price from cheapest to most expensive
		{ 
			String currentColor = this.allFlowers.get(i).getFlowerColor();
			
			if (! colorsInserted.contains(currentColor) )	//if the color wasn't picked
			{
				allDiffrentColorFlowers.add(this.allFlowers.get(i));	//put all the cheapest flowers from different color that customer chose
				colorsInserted.add(currentColor);
			}
		}
		System.out.println(" quantity of flowers is:"+allDiffrentColorFlowers.size());
		System.out.println("all flowers from different colors are:\n" +allDiffrentColorFlowers);

		
		
		
		int currentFlowerFromDiffrentColor=0;
		double currentFlowerPriceFromDiffrentColor=0;

		
		for(int i = 0; i < ( maxFlowersOfCheapestColor / 2)  ; i++)	//from here, put in the basket all cheapest flowers, until we get the highest limit
		{
			currentFlowerPriceFromDiffrentColor = allDiffrentColorFlowers.get(currentFlowerFromDiffrentColor).getFlowerPrice();
			
			if ( ( NowpriceOfcomposite +  currentFlowerPriceFromDiffrentColor) > this.max) // we reach to the highest limit ... 
			{
				break;
			}
			
			else
			{
				flowerComposion.add(allDiffrentColorFlowers.get(currentFlowerFromDiffrentColor));	//take a flower from list of flowers in different colors from customer choice
				NowpriceOfcomposite = NowpriceOfcomposite +  currentFlowerPriceFromDiffrentColor;
				currentFlowerFromDiffrentColor++;	
				if(currentFlowerFromDiffrentColor == (allDiffrentColorFlowers.size()-1))	//if the different flowers are over, start over and retake different flower, until you reach the limit
				{
					currentFlowerFromDiffrentColor=0;
				}
			}
		}		
		
		
		
		
		
		
		System.out.println("total price of composiotn is : " +NowpriceOfcomposite +" quantity of flowers is:"+flowerComposion.size());
		System.out.println("all flowers are:\n" +flowerComposion);
		this.quantityComposition=flowerComposion.size();
		this.priceComposition=NowpriceOfcomposite;
		
		
		
			return flowerComposion;
	}

	/**
	 * this method create a composition of flowers according to price range and color
	 * @param flowerComposion	empty arrayList of flowers class
	 * @return full arraylist in flowers (that doesn`t means that is will be ok, it thould contain at least 15 flowers)
	 */
	private ArrayList<Flower> createCompositeWithoutAnyColor(ArrayList<Flower> flowerComposion) 
	{	/**this method give a flower composition, at least 15 flowers at the customer range*/
		
		ArrayList<String> colorsInserted = new ArrayList<String>();
		double NowpriceOfcomposite=0;
		for(int i=0 ; i < this.allFlowers.size() ; i++)	//scan all flower and put in the basket all of the cheapest flowers in different colors
		{
			if( ! colorsInserted.contains( allFlowers.get(i).getFlowerColor() ))	//if the color didn't count already
			{
				if(NowpriceOfcomposite < this.max)	//if the total price of our composition is lower than the lower limit that customer choose put the flower in the basket
				{
					flowerComposion.add(this.allFlowers.get(i));
					NowpriceOfcomposite = NowpriceOfcomposite + this.allFlowers.get(i).getFlowerPrice();
					colorsInserted.add(this.allFlowers.get(i).getFlowerColor());
				}
				else
				{
					break;
				}
				
			}
			if(NowpriceOfcomposite < this.max && i==(this.allFlowers.size()-1))
			{
				i=0;
				colorsInserted.clear();
			}
		}
		int i;
		while(NowpriceOfcomposite > this.max )
		{
			
			for( i =flowerComposion.size()-1 ; i >= 0  ; i--)	
			{
				if( ( NowpriceOfcomposite - flowerComposion.get(i).getFlowerPrice() ) <= this.max  && ( NowpriceOfcomposite - flowerComposion.get(i).getFlowerPrice() ) >=this.min)  
				{
					NowpriceOfcomposite = NowpriceOfcomposite - flowerComposion.get(i).getFlowerPrice();
					flowerComposion.remove(i);
					break;
				}
			}
			if(i==-1)
			{
				flowerComposion.clear();
				this.priceComposition=0;
				this.quantityComposition=0;
				NowpriceOfcomposite = 0;
				break;
			}
		}
		
		if(! flowerComposion.isEmpty())
		{
			this.priceComposition = NowpriceOfcomposite;
			this.quantityComposition=flowerComposion.size();

		Collections.sort(flowerComposion, new Comparator<Flower>()
		{
				@Override
				public int compare(Flower firstFlower, Flower anotherFlower) {
				double comparePrice =  anotherFlower.getFlowerPrice();
						double result = firstFlower.getFlowerPrice() - comparePrice;
						if(result ==0)
						return 0;
						else if(result >0)
								return 1;
						else
								return -1;
				}
	
			});
		}

		return flowerComposion; 
		}

	/**
	 * this method sort flowers by their price
	 */
	private static void sortFlowers()
	{
		FXCollections.sort(allFlowers, new Comparator<Flower>()
		{

			@Override
			public int compare(Flower firstFlower, Flower anotherFlower) {
				double comparePrice =  anotherFlower.getFlowerPrice();
				double result = firstFlower.getFlowerPrice() - comparePrice;
				if(result ==0)
					return 0;
				else if(result >0)
							return 1;
				else
					return -1;
			}
	
		});
	}
	
	
	
	
	/**
	 * this method show the current window
	 */
	
	public void start(Stage primaryStage) throws IOException  
	{		
		Parent root = FXMLLoader.load(getClass().getResource("/Customer/CustomOrderFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Customer Custom Order"); // name of the title of the window
		primaryStage.setScene(scene);
	  	primaryStage.show();
		//Can't close the window without logout
		primaryStage.setOnCloseRequest( event -> {event.consume();} );
	}

	/**
	 * this method load item type and colors to comboboxes
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		this.basket.clear();
		ObservableList<String> allTypes= FXCollections.observableArrayList("Bridal Bouquet","Flower Arrangement","Cluster Flowers","Flowering Plant");		
		itemTypeCombo.setItems(allTypes);
		ObservableList<String> allFlowerColors= FXCollections.observableArrayList();
		allFlowerColors.add("");	//an optional to not pick a color
		this.numberOfColors=0;
		for(int i=0 ; i< allFlowers.size() ; i++)
		{
			String currentColor = allFlowers.get(i).getFlowerColor();
			if(! allFlowerColors.contains(currentColor))	//if there is no coloer in the list of combobox
			{
				allFlowerColors.add(currentColor);
				this.numberOfColors++;

			}
		}
		
		
		DominantColorCombo.setItems(allFlowerColors);
		priceRangeMinTxt.textProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	priceRangeMinTxt.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		priceRangeMaxTxt.textProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	priceRangeMaxTxt.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		
		   FlowerIDClomun.setCellValueFactory(new PropertyValueFactory<Flower, Integer>("flowerID"));
		    
		   FlowerNameClomun.setCellValueFactory(new PropertyValueFactory<Flower, String>("flowerName"));
		    	    
		   FlowerColorClomun.setCellValueFactory(new PropertyValueFactory<Flower, String>("flowerColor"));
		    
		   FlowerPriceClomun.setCellValueFactory(new PropertyValueFactory<Flower, String>("flowerPriceWIthCoins"));
		   
		   FlowerIDClomun.setStyle( "-fx-alignment: CENTER;");
		   FlowerNameClomun.setStyle( "-fx-alignment: CENTER;");
		   FlowerColorClomun.setStyle( "-fx-alignment: CENTER;");
		   FlowerPriceClomun.setStyle( "-fx-alignment: CENTER;");

		   priceRangeMinTxt.textProperty().addListener((obs, oldText, newText) -> {
			   shutDownBasket();
			    // ...
			});
		   priceRangeMaxTxt.textProperty().addListener((obs, oldText, newText) -> {
			   shutDownBasket();
			    // ...
			});
			branchLabelAtCatalog.setText("Your branch: "+CustomerMainWindow.chosenBranchName);
			branchLabelAtCatalog.setVisible(true);
 
	}
	/**
	 * this method making preparations before exiting from the current window
	 */
	private void shutDownBasket()
	{
		basketStatusLabel.setVisible(false);
		addToCartBtn.setDisable(true);
		removeBtnCart.setDisable(true);
		CustomerFlowers.clear();
    	this.quantityOfItems=0;

	}
}
