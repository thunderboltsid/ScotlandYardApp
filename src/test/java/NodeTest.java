import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by siddharthshukla on 23/01/15.
 */
public class NodeTest extends TestCase {

    public void testApp() {
        Assert.assertEquals(((new Node(22, 44, 66)).getId()), 22);
        Assert.assertEquals(((new Node(22, 44, 66)).getX()), 44);
        Assert.assertEquals(((new Node(22, 44, 66)).getY()), 66);
    }
}
