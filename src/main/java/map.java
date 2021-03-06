/**
 * Created by siddharthshukla on 17/10/14.
 */

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class map {
    String filename;
    int location[][] = new int[199][3];
    int ferryArr[][] = new int[20][2];
    int busArr[][] = new int[100][2];
    int taxiArr[][] = new int[500][2];
    int subwayArr[][] = new int[50][2];
    int ferryCount = 0, busCount = 0, taxiCount = 0, subwayCount = 0;
    //id node
    private Map<Integer, node> nodes;
    //from id, connection
    private Map<Integer, ArrayList<connection>> connections;
    private BufferedReader br;

    map(String filename) throws FileNotFoundException {
        this.filename = filename;
        nodes = new HashMap<Integer, node>();
        connections = new HashMap<Integer, ArrayList<connection>>();
        parseMap();
        InputStream in = getClass().getResourceAsStream("london.map");
        this.br = new BufferedReader(new InputStreamReader(in));
    }

    void parseMap() throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(filename));
        try {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("#") || line.isEmpty()) continue;
                if (line.startsWith("node")) {
                    int val1, val2, val3;
                    StringTokenizer linebreaker = new StringTokenizer(line);
                    linebreaker.nextToken();
                    val1 = Integer.parseInt(linebreaker.nextToken());
                    val2 = Integer.parseInt(linebreaker.nextToken());
                    val3 = Integer.parseInt(linebreaker.nextToken());
                    this.location[val1 - 1][0] = val1;
                    this.location[val1 - 1][1] = val2;
                    this.location[val1 - 1][2] = val3;
                }
                if (line.startsWith("ferry")) {
                    int val1, val2;
                    StringTokenizer linebreaker = new StringTokenizer(line);
                    linebreaker.nextToken();
                    val1 = Integer.parseInt(linebreaker.nextToken());
                    val2 = Integer.parseInt(linebreaker.nextToken());
                    this.ferryArr[ferryCount][0] = val1;
                    this.ferryArr[ferryCount++][1] = val2;
                }
                if (line.startsWith("bus")) {
                    int val1, val2;
                    StringTokenizer linebreaker = new StringTokenizer(line);
                    linebreaker.nextToken();
                    val1 = Integer.parseInt(linebreaker.nextToken());
                    val2 = Integer.parseInt(linebreaker.nextToken());
                    this.busArr[busCount][0] = val1;
                    this.busArr[busCount++][1] = val2;
                }
                if (line.startsWith("subway")) {
                    int val1, val2;
                    StringTokenizer linebreaker = new StringTokenizer(line);
                    linebreaker.nextToken();
                    val1 = Integer.parseInt(linebreaker.nextToken());
                    val2 = Integer.parseInt(linebreaker.nextToken());
                    this.subwayArr[subwayCount][0] = val1;
                    this.subwayArr[subwayCount++][1] = val2;
                }
                if (line.startsWith("taxi")) {
                    int val1, val2;
                    StringTokenizer linebreaker = new StringTokenizer(line);
                    linebreaker.nextToken();
                    val1 = Integer.parseInt(linebreaker.nextToken());
                    val2 = Integer.parseInt(linebreaker.nextToken());
                    this.taxiArr[taxiCount][0] = val1;
                    this.taxiArr[taxiCount++][1] = val2;
                }
            }
        } finally {
            if (!in.hasNextLine())
                in.close();
        }
        return;
    }

    public void parse() throws Exception {
        if (br == null) {
            throw new Exception("Map not found!");
        }
        BufferedReader reader = br;

        try {
            //reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                parseLine(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseLine(String text) throws ParseException {
        if (text.length() > 0)
            if (text.charAt(0) != '#') {
                if (text.contains(connection.TAXI)) {
                    connection c = parseconnection(text, connection.TAXI);
                    addconnections(c);
                } else if (text.contains(connection.SUBWAY)) {
                    connection c = parseconnection(text, connection.SUBWAY);
                    addconnections(c);
                } else if (text.contains(connection.FERRY)) {
                    connection c = parseconnection(text, connection.FERRY);
                    addconnections(c);
                } else if (text.contains(connection.BUS)) {
                    connection c = parseconnection(text, connection.BUS);
                    addconnections(c);
                } else if (text.contains(node.NODE)) {
                    node c = parsenode(text);
                    if (c != null) nodes.put(c.getId(), c);
                }
            }
    }

    private void addconnections(connection c) {
        addconnection(c);
        addconnection(c.reverted());
    }

    private void addconnection(connection c) {
        if (nodes.containsKey(c.getFrom()) && nodes.containsKey(c.getTo())) {
            if (connections.get(c.getFrom()) != null) {
                connections.get(c.getFrom()).add(c);
            } else {
                ArrayList<connection> value = new ArrayList<connection>();
                value.add(c);
                connections.put(c.getFrom(), value);
            }
        }
    }

    private node parsenode(String text) throws ParseException {
        Pattern intsOnly = Pattern.compile("\\d+"); //yes, it is neat.
        Matcher makeMatch = intsOnly.matcher(text);
        ArrayList<String> ids = new ArrayList<String>();
        while (makeMatch.find()) {
            ids.add(makeMatch.group());
        }
        if (ids.size() < 3) {
            System.out.println(ids);
            System.out.println(text);
            throw new ParseException(text, 0);
        } else {
            int id = Integer.parseInt(ids.get(0));
            int from = Integer.parseInt(ids.get(1));
            int to = Integer.parseInt(ids.get(2));
            node c = new node(id, from, to);
            return c;
        }
    }

    private connection parseconnection(String text, String mean) throws ParseException {
        Pattern intsOnly = Pattern.compile("\\d+"); //popo
        Matcher makeMatch = intsOnly.matcher(text);
        ArrayList<String> ids = new ArrayList<String>();
        while (makeMatch.find()) {
            ids.add(makeMatch.group());
        }

        if (ids.size() < 2) {
            throw new ParseException(text, 0);
        } else {
            int from = Integer.parseInt(ids.get(0));
            int to = Integer.parseInt(ids.get(1));
            connection c = new connection(mean, from, to);
            return c;
        }
    }

    public node getnodeFromId(int n) {
        return nodes.get(n);
    }

    public connection[] getAllconnections() {
        Collection<ArrayList<connection>> c = connections.values();
        Set<connection> result = new HashSet<connection>();

        for (ArrayList<connection> it : c) {
            for (connection el : it) {
                result.add(el);
            }
        }

        return result.toArray(new connection[result.size()]);
    }

    public node[] getnodes() {
        Collection<node> c = nodes.values();
        return c.toArray(new node[c.size()]);
    }

    public int[] searchFerryRoute(int loc) {
        int i;
        int j = 0;
        int arr[] = new int[10];
        for (i = 0; i < this.ferryArr.length; i++)
            if (loc == this.ferryArr[i][0]) {
                arr[j++] = this.ferryArr[i][1];
                j++;
            }
        if (arr[0] == 0)
            return new int[0];
        return arr;
    }

    public int[] searchSubwayRoute(int loc) {
        int i;
        int j = 0;
        int arr[] = new int[10];
        for (i = 0; i < this.subwayArr.length; i++)
            if (loc == this.subwayArr[i][0]) {
                arr[j++] = this.subwayArr[i][1];
                j++;
            }
        if (arr[0] == 0)
            return new int[0];
        return arr;

    }

    public int[] searchBusRoute(int loc) {
        int i;
        int j = 0;
        int arr[] = new int[10];
        for (i = 0; i < this.busArr.length; i++)
            if (loc == this.busArr[i][0]) {
                arr[j] = this.busArr[i][1];
                j++;
            }
        if (arr[0] == 0)
            return new int[0];
        return arr;
    }

    public int[] searchTaxiRoute(int loc) {
        int i;
        int j = 0;
        int arr[] = new int[10];
        for (i = 0; i < this.taxiArr.length; i++)
            if (loc == this.taxiArr[i][0]) {
                arr[j] = this.taxiArr[i][1];
                j++;
            }
        if (arr[0] == 0)
            return new int[0];
        return arr;

    }
}
