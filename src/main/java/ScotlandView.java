import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by siddharthshukla on 03/12/14.
 */
public class scotlandview extends JFrame {
    private int X = 13680;
    private int MARGIN = 40;
    private String filename;
    map gameMap;
    private JPanel jp;
    private JFrame jf;
    private statusbar statusBar;
    private mrx x;
    private detective[] d;
    private node[] nodes;
    private connection[] connections;

    public void setstatusbar(String message) throws Exception {
        statusBar.setMessage(message);
        Thread.sleep(200);
    }

    public void initializeMenu(final controller control) {

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
                                setstatusbar("Error, couldn't start the game!");
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

    public void resetView(mrx x, detective[] d, map gameMap, controller control) {
        this.gameMap = gameMap;
        this.x = x;
        this.d = d;
        jp.repaint();

    }

    scotlandview(mrx x, detective[] d, map gameMap, node[] nodes, connection[] connections, controller control) {
        super("Scotland Yard Sim");
        this.gameMap = gameMap;
        this.nodes = nodes;
        this.connections = connections;
        setSize(X + MARGIN, 2 * X / 3 + MARGIN);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jp = new ScotlandPanel(X, (2 * X) / 3);
        statusBar = new statusbar();
        add(jp);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        initializeMenu(control);
    }

    class ScotlandPanel extends JPanel {
        private int X;
        private int Y;
        private int RADIUS = 10;
        private Color TAXI = Color.BLACK;
        private Color BUS = Color.RED;
        private Color FERRY = Color.BLUE;
        private Color SUBWAY = Color.GRAY;

        public ScotlandPanel(int X, int Y) {
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
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                paintC(g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void paintC(Graphics g) throws Exception {
            X = this.getWidth() - MARGIN;
            Y = this.getHeight() - MARGIN;
            for (int i = 0; i < gameMap.location.length; i++) {
                g.setColor(Color.black);
                g.fillOval((MARGIN / 2 + gameMap.location[i][1] * X) / 100 - RADIUS / 2, (MARGIN / 2 + gameMap.location[i][2] * Y) / (100) - RADIUS / 2, RADIUS, RADIUS);
            }
            for (int i = 0; i < connections.length; i++) {
                drawconnection(connections[i], g);
            }
            drawMrX(g);
            drawPlayers(g);
        }

        private Color getColor(connection c) {
            Color res = null;

            if (c.getMean().equals(connection.BUS)) {
                res = BUS;

            } else if (c.getMean().equals(connection.TAXI)) {
                res = TAXI;

            } else if (c.getMean().equals(connection.FERRY)) {
                res = FERRY;

            } else if (c.getMean().equals(connection.SUBWAY)) {
                res = SUBWAY;

            }

            return res;

        }

        private void drawconnection(connection c, Graphics g) {
            g.setColor(getColor(c));
            node from = gameMap.getnodeFromId(c.getFrom());
            node to = gameMap.getnodeFromId(c.getTo());
            int x1 = (MARGIN / 2 + from.getX() * X) / 100;
            int y1 = (MARGIN / 2 + from.getY() * Y) / 100;
            int x2 = (MARGIN / 2 + to.getX() * X) / 100;
            int y2 = (MARGIN / 2 + to.getY() * Y) / 100;
            g.drawLine(x1, y1, x2, y2);
            g.setColor(Color.BLACK);
        }

        private void drawPlayers(Graphics g) {
            Font font = new Font("Comic Sans MS", Font.BOLD, 14);
            g.setFont(font);
            for (int i = 0; i < d.length; i++) {
                g.setColor(Color.BLUE);
                int pos = d[i].getLocation();
                int x = (MARGIN / 2 + gameMap.location[pos - 1][1] * X) / 100;
                int y = (MARGIN / 2 + gameMap.location[pos - 1][2] * Y) / 100;
                g.drawString("Detective " + (i + 1), x, y);
                g.setColor(Color.BLACK);
            }
        }

        private void drawMrX(Graphics g) {
            Font font = new Font("Comic Sans MS", Font.BOLD, 14);
            g.setFont(font);
            g.setColor(Color.RED);
            int pos = x.getLocation();
            int x = (MARGIN / 2 + gameMap.location[pos - 1][1] * X) / 100;
            int y = (MARGIN / 2 + gameMap.location[pos - 1][2] * Y) / 100;
            g.drawString("Mr X", x, y);
            g.setColor(Color.BLACK);
        }
    }
}
