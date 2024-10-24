// Street.java
class Street {
    private String id;
    private Point start, end;

    public Street(String id, Point start, Point end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public Point getMidPoint() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    public double length() {
        return Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
    }

    // Corrected: Use proper intersection detection (sharing any endpoint)
    public boolean intersects(Street other) {
        // Check if start or end of this street matches start or end of the other street
        return this.start.equals(other.start) || this.start.equals(other.end) ||
                this.end.equals(other.start) || this.end.equals(other.end);
    }


    // Correct the turn direction calculation
    public TurnDirection calculateTurnDirection(Street nextStreet, int degreeThreshold) {
        // Calculate direction vectors for the two streets
        double dx1 = end.getX() - start.getX();
        double dy1 = end.getY() - start.getY();
        double dx2 = nextStreet.end.getX() - nextStreet.start.getX();
        double dy2 = nextStreet.end.getY() - nextStreet.start.getY();

        // Cross product helps determine left or right turn
        double crossProduct = dx1 * dy2 - dy1 * dx2;

        if (crossProduct > 0) {
            return TurnDirection.Left;
        } else if (crossProduct < 0) {
            return TurnDirection.Right;
        } else {
            return TurnDirection.Straight;
        }
    }

}
