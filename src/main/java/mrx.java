/**
 * Created by siddharthshukla on 17/10/14.
 */
public class mrx extends player {
    mrx() {
        this.setLocation(randomVal(199));
        this.setBlackToken(5);
        System.out.println("Mr. X created at position " + this.getLocation());
    }
}
