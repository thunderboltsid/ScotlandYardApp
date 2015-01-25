/**
 * Created by siddharthshukla on 23/01/15.
 */
public class Node {
    private int id;
    private int x;
    private int y;

    public static final String NODE = "node";

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "node\t" + id + "\t" + x + "\t" + y;
    }
}