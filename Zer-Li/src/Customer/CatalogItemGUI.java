package Customer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.mysql.jdbc.Blob;

import Catalog.CatalogItem;
import ChainWorker.CatalogEditControl;
import Users.LoginContol;
import common.MyFile;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class of visual catalog item, inserted to table view
 * @author haim hadad
 *
 */
public class CatalogItemGUI extends CatalogItem
{
	private Button plusBtn;
	private Button minusBtn;
	private ImageView  img;
	private CatalogOrderControl CatalogWindow;
	private CatalogEditControl CatalogEditWindow;
	private String ItemPriceWithCoin;
	private Label priceItemLabel=null;
	
	/**
	 * constructor of catalog item in tableview (the catalog)
	 * @param itemID	id of product
	 * @param itemName	name of product
	 * @param itemType  type of product
	 * @param itemDescription  description of product
	 * @param itemPhoto        image file of the product
	 * @param Price            price of the product
	 * @param window           catalog controller class
	 */
	public CatalogItemGUI(int itemID, String itemName, String itemType, String itemDescription, MyFile itemPhoto, double Price, CatalogOrderControl window) 
	{
		super(itemID, itemName, itemType, itemDescription, itemPhoto, Price);
		//this.img= new ImageView(new ByteArrayInputStream(itemPhoto.getMybytearray()));
		this.CatalogWindow = window; //this in order to controll the label
		
		
		
		if(!itemPhoto.getFileName().equals(null))
		{
			
	
			Image fileImage =  new Image(new ByteArrayInputStream( itemPhoto.getMybytearray()) );
			img = new ImageView(fileImage);
			//img=new ImageView(file.toURI().toString());
			
			img.setFitHeight(150);
			img.setFitWidth(120);
		}

		plusBtn = new Button( " + ");
		minusBtn = new Button( " - ");
		this.ItemPriceWithCoin = ""+this.getItemPrice()+"$";
		plusBtn.setOnAction(e->AddButtonClicked());
		minusBtn.setOnAction(e->RemoveButtonClicked());
		

	}
	
	

	public CatalogItemGUI(int itemID, String itemName, String itemType, String itemDescription, MyFile itemPhoto, double Price, CatalogEditControl window) 
	{
		super(itemID, itemName, itemType, itemDescription, itemPhoto, Price);
		this.CatalogEditWindow = window; //this in order to controll the label

		if(!itemPhoto.getFileName().equals(null))
		{
			Image fileImage =  new Image(new ByteArrayInputStream( itemPhoto.getMybytearray()) );
			img = new ImageView(fileImage);
			
			img.setFitHeight(150);
			img.setFitWidth(120);
		}
		

		plusBtn = new Button( " + ");
		minusBtn = new Button( " - ");
		this.ItemPriceWithCoin = ""+this.getItemPrice()+"$";
		plusBtn.setOnAction(e->AddButtonClicked());
		minusBtn.setOnAction(e->RemoveButtonClicked());
		
	}


	/**
	 * method of dynamic button, that will add a catalog item to the cart (cart = item in order)
	 */
	private void AddButtonClicked() 
	{
		//System.out.println(""+this.getItemID()+", "+this.getItemName()+", "+this.getItemType()+", "+this.getItemDescription()+", "+this.getItemPrice());
		ItemInOrder newItem = new CatalogItemInOrder(this.getItemID(),this.getItemName(), this.getItemPrice());
		ObservableList<ItemInOrder> prepareItemsInOrder= OrdersControl.ItemsInOrderList;
		
		for(int i=0 ; i< prepareItemsInOrder.size(); i++)	//scan all item that chosen before
		{
			ItemInOrder oldItem = prepareItemsInOrder.get(i);
			
			if(newItem.compareTo(prepareItemsInOrder.get(i))==1)	//if you found similar item, just increase its quantity
			{
				OrdersControl.ItemsInOrderList.get(i).incQty();
				System.out.println("" +	OrdersControl.ItemsInOrderList.get(i).getItemQty() );
				return;

			}

		}
		
		prepareItemsInOrder.add(newItem);	//if not found, create a new item in order
			
	}

	/**
	 * method of dynamic button, that will remove a catalog item to the cart (cart = item in order)
	 */
	private void RemoveButtonClicked()
	{
		//System.out.println(""+this.getItemID()+", "+this.getItemName()+", "+this.getItemType()+", "+this.getItemDescription()+", "+this.getItemPrice());
		ItemInOrder newItem = new CatalogItemInOrder(this.getItemID(),this.getItemName(),this.getItemPrice());
		ObservableList<ItemInOrder> prepareItemsInOrder= OrdersControl.ItemsInOrderList;
		for(int i=0 ; i< prepareItemsInOrder.size(); i++)
		{
			ItemInOrder oldItem = prepareItemsInOrder.get(i);
			
			if(newItem.compareTo(prepareItemsInOrder.get(i))==1)
			{
				oldItem.decQty();
				System.out.println("" +	OrdersControl.ItemsInOrderList.get(i).getItemQty() );

				if(oldItem.getItemQty() <= 0)
				{
					OrdersControl.ItemsInOrderList.remove(i);	//delete the item if there is no quantity of it
					//System.out.println("" +	OrdersControl.ItemsInOrderList.get(i).getItemQty() );

				}
				return;
			}
		}
		

	}


	/**
	 * getter of image file
	 * @return MyFile class (image), from lab 8
	 */
	public ImageView getImg()
	{
		return this.img;
	}
	
	/**
	 * setter of Image
	 */
	public void setImg(ImageView iphoto)
	{
		this.img = iphoto;
	}
	
	/**
	 * setter of MyFile class from lab 8
	 * @param iphoto MyFile class (includes image)
	 */
	public void setImg(MyFile iphoto)
	{
		this.img= new ImageView(iphoto.getFileName());
	}
	
	/**
	 * getter of button
	 * @return adder to cart button
	 */
	public Button getPlusBtn()
	{
		return this.plusBtn;
	}
	
	/**
	 * setter of button
	 */
	public void setPlusBtn(Button anotherBuyBtn)
	{
		this.plusBtn = anotherBuyBtn;
	}
	
	/**
	 * getter of button
	 * @return remover from cart button
	 */
	public Button getMinusBtn()
	{
		return this.minusBtn;
	}
	
	/**
	 * setter of remover button
	 * @param anotherEditBtn button that remove item in order
	 */
	public void setMinusBtn(Button anotherEditBtn)
	{
		this.minusBtn = anotherEditBtn;
	}

	/**
	 * getter of price with $
	 * @return string of price with $
	 */
	public String getItemPriceWithCoin() 
	{
		return ItemPriceWithCoin;
	}

	/**
	 * getter of label of price
	 * @return return label of price that will be colored in red if it is a sale price
	 */
	public Label getPriceItemLabel() {
		return priceItemLabel;
	}

	/**
	 * setter of price label
	 * @param priceItem label of price amount
	 */
	public void setPriceItemLabel(Label priceItem) {
		this.priceItemLabel = priceItem;
	}
	
	

}
