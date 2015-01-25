/**
 * Created by siddharthshukla on 23/01/15.
 */
public class Connection {
    private String mean;
    private int from;
    private int to;

    public static final String TAXI = "taxi";
    public static final String FERRY = "ferry";
    public static final String BUS = "bus";
    public static final String SUBWAY = "subway";


    public Connection(String name, int x, int y) {
        this.mean = name;
        from = x;
        to = y;
    }

    public Connection reverted() {
        return new Connection(mean, to, from);
    }

    public Connection(Connection another) {
        this.mean = another.mean;
        this.from = another.from;
        this.to = another.to;
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