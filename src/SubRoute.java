public class SubRoute {
    private String streetId;
    private TurnDirection turnDirection;
    private double length;

    public SubRoute(String streetId, TurnDirection turnDirection, double length) {
        this.streetId = streetId;
        this.turnDirection = turnDirection;
        this.length = length;
    }

    public double length() {
        return length;
    }

    public String getStreetId() {
        return streetId;
    }

    public TurnDirection getTurnDirection() {
        return turnDirection;
    }

    public Route extractRoute() {
        return null;
    }

    public boolean isLoop() {
        // Placeholder logic for loops
        return false;
    }

    public boolean isKeyTurn() {
        return turnDirection != TurnDirection.Straight;
    }
}
