import static java.lang.System.exit;

/**
 * Created by siddharthshukla on 16/11/14.
 */
public class controller {
    int NoOfDetectives;
    String filename;
    int NoOfMoves;
    private mrx x;
    private detective[] d;
    private ScotlandView view;

    public int getNoOfDetectives() {
        return this.NoOfDetectives;
    }

    public int getNoOfMoves() {
        return this.NoOfMoves;
    }

    public String getFilename() {
        return this.filename;
    }

    controller(int n, int m, String filename) {
        this.NoOfDetectives = n;
        this.NoOfMoves = m;
        this.filename = filename;
    }

    public void update(detective d) {
        view.repaint();

        if (d.getLocation() == x.getLocation()) {
            exit(666);
        }

    }

    public void update() {
        view.repaint();

        for (int i = 0; i < getNoOfDetectives(); i++) {
            detective[] d = this.d;
            if (d[i].getLocation() == x.getLocation()) {
                exit(555);
            }
        }
    }


    public void gameController() throws Exception {//n is the number of detectives
        map gameMap = new map(getFilename());
        mrx x = new mrx();
        gameMap.parse();
        Node[] nodes = gameMap.getNodes();
        d = new detective[getNoOfDetectives()];
        for (int i = 0; i < getNoOfDetectives(); i++) {
            d[i] = new detective(x.getLocation());// to make sure Mr.X and detectives don't land on the same spot.
        }
        ScotlandView view = new ScotlandView(x, d, gameMap, nodes, gameMap.getAllConnections(), this);
        if (view != null) {
            view.resetView(x, d, gameMap, this);
            view.setVisible(true);
        } else {
            view = new ScotlandView(x, d, gameMap, nodes, gameMap.getAllConnections(), this);
            view.setVisible(true);
        }
        System.out.println("Begin Game : Mr. X and " + getNoOfDetectives() + " detectives.");
        for (int i = 0; i <= getNoOfMoves(); i++) {
            System.out.println("Turn Nr: " + (i + 1));
            for (int j = 0; j < getNoOfDetectives(); j++) {
                if (x.getLocation() == d[j].getLocation()) {
                    game.gameOver(x, j + 1);
                }
            }
            System.out.print("Move for Mr.X : ");
            view.setStatusBar("Mrx moves!");
            x.move();
            view.repaint();
            for (int j = 0; j < getNoOfDetectives(); j++) {
                System.out.print("Move for Detective " + (j + 1) + " : ");
                view.setStatusBar("Detective " + (j + 1) + " moves!");
                d[j].move();
                view.repaint();
                if (x.getLocation() == d[j].getLocation()) {//loop to check for collisions during the turns of detectives
                    view.setStatusBar("MrX captured! by detective " + (j + 1));
                    game.gameOver(x, j + 1);//Game over
                }
            }
        }
        view.setStatusBar("MrX wins!");
        game.gameOver(x, 0);
        exit(0);
    }
}
