import java.util.Random;

/**
 * Created by siddharthshukla on 17/10/14.
 */
class player {
    private int location;
    private int subwayToken;
    private int taxiToken;
    private int busToken;
    private int blackToken;

    public player() {
        this.location = 1;
        this.subwayToken = 4;
        this.taxiToken = 10;
        this.busToken = 8;
    }

    public int getLocation() {
        return this.location;
    }

    public int getSubwayToken() {
        return this.subwayToken;
    }

    public int getTaxiToken() {
        return this.taxiToken;
    }

    public int getBusToken() {
        return this.busToken;
    }

    public int getTotalTokens() {
        return this.busToken + this.taxiToken + this.subwayToken + this.subwayToken + this.blackToken;
    }

    public void setSubwayToken(int tok) {
        this.subwayToken = tok;
    }

    public void setTaxiToken(int tok) {
        this.taxiToken = tok;
    }

    public void setBusToken(int tok) {
        this.busToken = tok;
    }

    public void setLocation(int loc) {
        this.location = loc;
    }

    public int getBlackToken() {
        return this.blackToken;
    }

    public void setBlackToken(int tok) {
        this.blackToken = tok;
    }

    public boolean hasBlackToken() {
        if (this.getBlackToken() != 0) return true;
        else return false;
    }

    public static int randomVal(int n) {//Generates random number between 1 and n
        int randVal;
        Random randomGenerator = new Random();
        randVal = 1 + randomGenerator.nextInt(n);//Random no between 1 and n
        return randVal;
    }

    public static int randomValExcept(int n, int m) {//returns values between 1 and m except n
        int randomVal;
        Random randomGenerator = new Random();
        randomVal = 1 + randomGenerator.nextInt(m);
        while (randomVal == n || randomVal == 0)
            randomVal = 1 + randomGenerator.nextInt(m);
        return randomVal;
    }

    public boolean isValidMove(int loc) {
        return true;
    }

    public void move() throws Exception {
        final String filename = "src/main/resources/london.map";
        map gameMap = new map(filename);
        if (this.getTotalTokens() < 1) {
            System.out.println("Oops...Player is stuck : No tokens");
        }
        int[] taxi = gameMap.searchTaxiRoute(this.getLocation());
        int[] subway = gameMap.searchSubwayRoute(this.getLocation());
        int[] ferry = gameMap.searchFerryRoute(this.getLocation());
        int[] bus = gameMap.searchBusRoute(this.getLocation());
        boolean canMove = false;
        int iterations = 0;
        if (ferry.length != 0 && this.getBlackToken() > 0) {
            canMove = true;
            this.setLocation(ferry[0]);
            System.out.println("Player used a ferry and moved to " + this.getLocation());
        } else if (subway.length != 0 && this.getSubwayToken() > 0) {
            canMove = true;
            this.setLocation(subway[0]);
            System.out.println("Player used subway and moved to " + this.getLocation());
        } else if (bus.length != 0 && this.getBusToken() > 0) {
            canMove = true;
            this.setLocation(bus[0]);
            System.out.println("Player used a bus and moved to " + this.getLocation());
        } else if (taxi.length != 0 && this.getTaxiToken() > 0) {
            canMove = true;
            this.setLocation(taxi[0]);
            System.out.println("Player used a taxi and moved to " + this.getLocation());
        } else {
            System.out.println("Player is screwed : Can't move");
        }
    }
}

