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
    private scotlandview view;
    public boolean game_state ;

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

    public void gameController() throws Exception {//n is the number of detectives
        map gameMap = new map(getFilename());
        mrx x = new mrx();
        gameMap.parse();
        node[] nodes = gameMap.getnodes();
        d = new detective[getNoOfDetectives()];
        for (int i = 0; i < getNoOfDetectives(); i++) {
            d[i] = new detective(x.getLocation());// to make sure Mr.X and detectives don't land on the same spot.
        }
        scotlandview view = new scotlandview(x, d, gameMap, nodes, gameMap.getAllconnections(), this);
        if (view != null) {
            view.resetView(x, d, gameMap, this);
            view.setVisible(true);
        } else {
            view = new scotlandview(x, d, gameMap, nodes, gameMap.getAllconnections(), this);
            view.setVisible(true);
        }
        game_state = true;
        System.out.println("Begin Game : Mr. X and " + getNoOfDetectives() + " detectives.");
        for (int i = 0; i <= getNoOfMoves(); i++) {
            System.out.println("Turn Nr: " + (i + 1));
            for (int j = 0; j < getNoOfDetectives(); j++) {
                if (x.getLocation() == d[j].getLocation()) {
                    game.gameOver(x, j + 1);
                }
            }
            System.out.print("Move for Mr.X : ");
            view.setstatusbar("Mrx moves!");
            x.move();
            view.repaint();
            for (int j = 0; j < getNoOfDetectives(); j++) {
                System.out.print("Move for Detective " + (j + 1) + " : ");
                view.setstatusbar("Detective " + (j + 1) + " moves!");
                d[j].move();
                view.repaint();
                if (x.getLocation() == d[j].getLocation()) {//loop to check for collisions during the turns of detectives
                    view.setstatusbar("MrX captured! by detective " + (j + 1));
                    game_state = false;
                    game.gameOver(x, j + 1);//Game over
                }
            }
        }
        view.setstatusbar("MrX wins!");
        game.gameOver(x, 0);
        exit(0);
    }
    public boolean gameEnds(){
        return game_state;
    }
}
