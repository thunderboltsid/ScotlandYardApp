import junit.framework.Assert;

/**
 * Created by siddharthshukla on 3/12/14.
 */
public class statusbarTest {
    public void testApp(){
        Assert.assertEquals(new statusbar().getMessage(), "Players are on the map!");
    }

}