import junit.framework.Assert;

/**
 * Created by siddharthshukla on 3/12/14.
 */
public class gameTest {
    public void testApp()throws Exception{
        Assert.assertNotSame(new game().gameOver(new mrx(), 2), 0);
    }

}