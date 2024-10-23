// Street class
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

    public boolean intersects(Street other) {
        return this.start.equals(other.start) || this.start.equals(other.end) || this.end.equals(other.start) || this.end.equals(other.end);
    }

    public TurnDirection calculateTurnDirection(Street nextStreet, int degreeThreshold) {
        // Use the cross product to determine if the turn is left or right
        double crossProduct = calculateCrossProduct(this, nextStreet);

        // Check if the turn is left or right based on cross product
        if (crossProduct > 0) {
            // Left turn
            return TurnDirection.Left;
        } else if (crossProduct < 0) {
            // Right turn
            return TurnDirection.Right;
        } else {
            // Straight, but also consider if the streets cross the depot and block the movement
            return TurnDirection.Straight;
        }
    }


    // Method to check if the straight movement is effectively a left turn
    private boolean shouldBlockStraightMovement(Street current, Street next) {
        // Depot coordinates (6, 6) for reference, adapt if dynamic depot location is used
        int depotX = 6;
        int depotY = 6;

        // Check if the current and next streets are on opposite sides of the depot
        if ((current.start.getX() < depotX && next.start.getX() > depotX) ||
                (current.start.getX() > depotX && next.start.getX() < depotX)) {
            return true; // Block the straight movement as it's crossing the depot
        }
        return false;
    }


    private double calculateCrossProduct(Street s1, Street s2) {
        double dx1 = s1.end.getX() - s1.start.getX();
        double dy1 = s1.end.getY() - s1.start.getY();
        double dx2 = s2.end.getX() - s2.start.getX();
        double dy2 = s2.end.getY() - s2.start.getY();

        // Cross product: determinant of the 2x2 matrix [dx1, dy1; dx2, dy2]
        return dx1 * dy2 - dy1 * dx2;  // This gives the direction of the turn
    }
}