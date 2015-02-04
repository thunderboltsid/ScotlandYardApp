import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by siddharthshukla on 3/12/14.
 */
public class nodeTest extends TestCase {

    public void testApp() {
        Assert.assertEquals(((new node(22, 44, 66)).getId()), 22);
        Assert.assertEquals(((new node(22, 44, 66)).getX()), 44);
        Assert.assertEquals(((new node(22, 44, 66)).getY()), 66);
    }
}
