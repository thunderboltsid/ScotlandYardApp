import javax.swing.*;
import java.awt.*;
/**
 * Created by siddharthshukla on 03/12/14.
 */

public class StatusBar extends JLabel{

    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));
        setMessage("Players are on the map!");
    }

    public void setMessage(String message){
        setText(" " + message);
    }

}
