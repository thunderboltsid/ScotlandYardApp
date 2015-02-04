import javax.swing.*;
import java.awt.*;

/**
 * Created by siddharthshukla on 03/12/14.
 */

public class statusbar extends JLabel {
    private String message;

    public statusbar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        message = "Players are on the map!";
        setMessage(message);
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        setText(" " + message);
    }

}
