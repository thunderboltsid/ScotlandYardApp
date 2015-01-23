import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by siddharthshukla on 03/12/14.
 */
public class ScotlandView extends JFrame{
    private int X = 1200;
    private int MARGIN = 50;
    private String filename;
    map gameMap;
    private JPanel jp;
    private JFrame jf;
    private StatusBar statusBar;
    private mrx x;
    private detective[] d;
    private Node[] nodes;
    private Connection[] connections;
    public void setStatusBar(String message)throws Exception{
        statusBar.setMessage(message);
        Thread.sleep(200);
    }

    public void initializeMenu(final controller control){

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newgame = new JMenuItem("New game");
        JMenuItem quit = new JMenuItem("Quit");
        this.filename = control.getFilename();
        file.add(newgame);
        file.add(quit);
        menu.add(file);

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            control.gameController();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            try {
                                setStatusBar("Error, couldn't start the game!");
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        final JFrame frame = this;
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.setJMenuBar(menu);
    }

    public void resetView(mrx x, detective[] d, map gameMap, controller control){
        this.gameMap = gameMap;
        this.x = x;
        this.d = d;
        jp.repaint();

    }

    ScotlandView(mrx x, detective[] d, map gameMap, Node[] nodes, Connection[] connections, controller control){
        super("Scotland Yard");
        int panelHeight = 800;

        int panelWidth = 600;
        this.gameMap = gameMap;
        this.nodes = nodes;
        this.connections = connections;
        setSize(X + MARGIN,2*X/3 + MARGIN);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jp =  new ScotlandPanel(X, (2*X)/3);
        statusBar = new StatusBar();
        add(jp);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        initializeMenu(control);
    }

    class ScotlandPanel extends JPanel{
        private int X;
        private int Y;
        private int RADIUS = 10;
        private Color TAXI = Color.BLUE;
        private Color BUS  = Color.ORANGE;
        private Color FERRY = Color.GREEN;
        private Color SUBWAY = Color.BLACK;
        public ScotlandPanel(int X, int Y){
            setPreferredSize(new Dimension(X + MARGIN, Y + MARGIN));
            this.Y = Y;
            this.X = X;
            final ScotlandPanel sc = this;
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    sc.removeAll();
                    sc.repaint();
                }
            });
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                paintC(g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void paintC(Graphics g)throws Exception{
            X = this.getWidth() - MARGIN;
            Y = this.getHeight() - MARGIN;
            for(int i=0; i < gameMap.location.length; i++){
                g.setColor(Color.black);

                g.fillOval((MARGIN/2 + gameMap.location[i][1] *X)/100 - RADIUS/2, ( MARGIN/2 + gameMap.location[i][2]*Y)/(100) - RADIUS/2, RADIUS, RADIUS);
            }

            for(int i=0; i<connections.length; i++){
                drawConnection(connections[i],g);
            }

            drawMrX(g);
            drawPlayers(g);
        }

        private Color getColor(Connection c){
            Color res = null;

            if (c.getMean().equals(Connection.BUS)) {
                res = BUS;

            } else if (c.getMean().equals(Connection.TAXI)) {
                res = TAXI;

            } else if (c.getMean().equals(Connection.FERRY)) {
                res = FERRY;

            } else if (c.getMean().equals(Connection.SUBWAY)) {
                res = SUBWAY;

            }

            return res;

        }

        private void drawConnection(Connection c, Graphics g) {

            g.setColor(getColor(c));

            Node from = gameMap.getNodeFromId(c.getFrom());
            Node to = gameMap.getNodeFromId(c.getTo());
            int x1 = (MARGIN/2 + from.getX()*X)/100;
            int y1 = ( MARGIN/2 + from.getY()*Y)/100;

            int x2 = (MARGIN/2 + to.getX()*X)/100;
            int y2 = ( MARGIN/2 + to.getY()*Y)/100;

            g.drawLine(x1,y1,x2,y2);
            g.setColor(Color.BLACK);
        }

        private void drawPlayers(Graphics g){
            Font font = new Font("Serif", Font.PLAIN, 14);
            g.setFont(font);
            for(int i=0; i<d.length; i++){
                g.setColor(Color.red);
                int pos = d[i].getLocation();
                int x = (MARGIN/2 + gameMap.location[pos-1][1]*X)/100;
                int y = ( MARGIN/2 + gameMap.location[pos-1][2]*Y)/100;
                g.drawString("Detective "+(i+1), x,y);
                g.setColor(Color.BLACK);
            }
        }

        private void drawMrX(Graphics g){
            g.setColor(Color.BLUE);
            int pos = x.getLocation();
            int x = (MARGIN/2 + gameMap.location[pos-1][1]*X)/100;
            int y = ( MARGIN/2 + gameMap.location[pos-1][2]*Y)/100;
            g.drawString("Mr X", x,y);
            g.setColor(Color.BLACK);
        }
    }
}
