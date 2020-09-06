package CustomerServiceDepartmentworker;

import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
 /**a complaint row for the GUI in the  CustomerServiceDepartmentworkerMainWindow window
  * used for binding to the fields  
  * @author Alex
  *
  */
public class complaintRow {

	public Stage mainstage;
	private StringProperty labelText;
	private StringProperty timerText;

	/** complaint opening time 
	 * 
	 */
	private int hours = 0;
	/** complaint opening time
	 * 
	 */
	private int minutes = 0;
	/** complaint opening time
	 * 
	 */
	private int seconds = 0;

	/**saves the current location of the complaint the observable list 'upgradedList'
	 * 
	 */
	public int ComplaintIndex=-1;
	
	/**empty constructor initiates the variables with dafeult values
	 * 
	 */
	public complaintRow() {
		// default Values
		labelText = new SimpleStringProperty(" ");
		timerText = new SimpleStringProperty("00:20");

		/** generate random time, demi values for demo
		 * 
		 */
		Random rn = new Random();
		hours = rn.nextInt(24);
		minutes = rn.nextInt(60);
		seconds = rn.nextInt(60);
	}
/**a constructor 
 * also calls the empty constructor for some basic settings
 * 
 * @param complaintText
 * @param complaintIndex
 * @param time
 * @param stg
 */
	public complaintRow(String complaintText,int complaintIndex, String time, Stage stg) {
		this(stg);
		ComplaintIndex=complaintIndex;
		complaintLabelSetter(complaintText);
		GetComplaintOpeningTime(time);
	}
/**constructor 
 * 
 * @param stg
 */
	public complaintRow(Stage stg) {
		this();
		mainstage = stg;

	}

	/** List row elements binding
	 * a getter for the complaint label
	 * @return Label
	 */
	public StringProperty complaintLabelGetter() {
		return labelText;
	}
/**timerText getter
 * 
 * @return timerText StringProperty
 */
	public StringProperty timerTextGetter() {
		return timerText;
	}
/**complaintLabel setter 
 * @param str string
 */
	public void complaintLabelSetter(String str) {
		if (!str.isEmpty())
			labelText.set(str);
	}
/**a timer text setter,
 * gets the time in Hours, Minutes and seconds and sets it in the proper fields
 * @param Hours
 * @param Minutes
 * @param Seconds
 */
	public void timerTextSetter(String Hours, String Minutes, String Seconds) {

		int hh =Math.abs(Integer.parseInt(Hours) - hours);
		int mm = Math.abs(Integer.parseInt(Minutes) - minutes);
		int ss = Math.abs(Integer.parseInt(Seconds) - seconds);
		// add leading zero's if the time digit is less than 10
		String HH = "";
		String MM = "";
		String SS = "";
		if (hh < 10)
			HH = "0";
		if (mm < 10)
			MM = "0";
		if (ss < 10)
			SS = "0";
		timerText.set(HH + hh + ":" + MM + mm + " : " + SS + ss);
	}

	/**parse time to 'hours','minutes' & 'seconds' of type int
	 * 
	 * @param time
	 */
	public void GetComplaintOpeningTime(String time) {

		String[] Time=time.split(":");
		hours=Integer.parseInt(Time[0]);
		minutes=Integer.parseInt(Time[1]);
	}

	
	
	
	
	/**<hh1>event handler for the button</h1>
	 * <p>@author Alex</p>
	 */
	public void buttonEventHandler() {
		// open a new edit complaint, opens the "ManageComplaintFrame"
		ManageComplaintController editFrame = new ManageComplaintController();
		try {
			editFrame.start(new Stage());
		} catch (Exception e) {
			System.out.print("Could not open an edit window\n");
			e.printStackTrace();
			if (mainstage != null)
				mainstage.toBack();
		}
	}

}
