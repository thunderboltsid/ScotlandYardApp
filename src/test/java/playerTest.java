import junit.framework.Assert;

/**
 * Created by siddharthshukla on 3/12/14.
 */
public class playerTest {
    public void testApp(){
        Assert.assertEquals(new player().getLocation(),1);
    }

}