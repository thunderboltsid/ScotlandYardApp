/**
 * Created by siddharthshukla on 03/12/14.
 */
public class connection {
    private String mean;
    private int from;
    private int to;

    public static final String TAXI = "taxi";
    public static final String FERRY = "ferry";
    public static final String BUS = "bus";
    public static final String SUBWAY = "subway";


    public connection(String name, int x, int y) {
        this.mean = name;
        from = x;
        to = y;
    }

    public connection reverted() {
        return new connection(mean, to, from);
    }

    public String getMean() {
        return mean;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from + " -" + mean + "- " + to;
    }
}