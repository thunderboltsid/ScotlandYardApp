import junit.framework.Assert;

public class detectiveTest {
    public void testApp(){
        Assert.assertNotSame(new detective(1).getLocation(), 0);
    }

}