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
        double angle = calculateAngle(this, nextStreet);
        if (angle <= degreeThreshold) {
            return TurnDirection.Straight;
        } else if (angle > degreeThreshold && angle <= 90) {
            return TurnDirection.Right;
        } else if (angle > 90 && angle <= 180) {
            return TurnDirection.Left;
        } else {
            return TurnDirection.UTurn;
        }
    }

    private double calculateAngle(Street s1, Street s2) {
        double dx1 = s1.end.getX() - s1.start.getX();
        double dy1 = s1.end.getY() - s1.start.getY();
        double dx2 = s2.end.getX() - s2.start.getX();
        double dy2 = s2.end.getY() - s2.start.getY();
        double angle = Math.toDegrees(Math.acos((dx1 * dx2 + dy1 * dy2) / (Math.sqrt(dx1 * dx1 + dy1 * dy1) * Math.sqrt(dx2 * dx2 + dy2 * dy2))));
        return angle;
    }
}