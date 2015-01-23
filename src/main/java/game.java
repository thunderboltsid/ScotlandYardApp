import java.io.*;
import java.util.*;

import static java.lang.System.exit;

/**
 * Created by siddharthshukla on 16/11/14.
 */
public class game {

    static int gameOver(mrx x, int n) throws IOException {
        if (n == 0) {
            System.out.println("Maximum number of turns reached. Game Over.");
            System.in.read();
            exit(333);
        }
        System.out.println("Game's Over. Detective " + n + " and Mr.X collided at node " + x.getLocation());
        System.in.read();
        exit(666);
        return x.getLocation();
    }

    public static void main(String args[]) throws Exception {
        final String filename = "src/main/resources/london.map";
        System.out.println("Starting the game.");
        controller control = new controller(5,21,filename);
        control.gameController();
        System.out.println("Oops...Something fucked up.");
        return;
    }
}
