package Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
/**
 * thread that print label
 * @author haim hadad
 *
 */
public class ThreadLabelTxtArea extends Thread
{
    @FXML
    private TextArea txtGreeting;
    @FXML
    private Label messageAfterGreeting;

    /**
     * constructor of thread
     * @param txtGreeting text ares
     * @param messageAfterGreeting label to show on frame
     */
    public ThreadLabelTxtArea(TextArea txtGreeting, Label messageAfterGreeting)
    {
    	this.txtGreeting=txtGreeting;
    	this.messageAfterGreeting = messageAfterGreeting;
    	
    }

    /**
     * the method print label on frame at the first letter in thext area
     */
    public void run()
    {
    	while(txtGreeting.getText().equals(""));
		messageAfterGreeting.setVisible(true);
    }
}
