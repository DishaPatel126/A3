// Location class
class Location {
    private String streetId;
    private Point point;
    private StreetSide side;

    public Location(String streetId, Point point, StreetSide side) {
        this.streetId = streetId;
        this.point = point;
        this.side = side;
    }

    public String getStreetId() {
        return streetId;
    }

    public Point getPoint() {
        return point;
    }

    public StreetSide getSide() {
        return side;
    }
}