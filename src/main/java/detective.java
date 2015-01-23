/**
 * Created by siddharthshukla on 17/10/14.
 */
public class detective extends player {
    detective(int loc){
        this.setLocation(randomValExcept(loc, 199));
        this.setBlackToken(0);
        System.out.println("Detective created at " + this.getLocation());
    }

}
