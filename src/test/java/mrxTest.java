import junit.framework.Assert;

/**
 * Created by siddharthshukla on 3/12/14.
 */
public class mrxTest {
    public void testApp(){
        Assert.assertNotSame(new mrx().getLocation(), 0);
    }
}