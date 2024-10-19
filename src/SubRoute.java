// SubRoute class
class SubRoute {
    // Subset of streets and turns in a larger route
    public Route extractRoute() {
        // Extract a subroute from this route
        return null; // Placeholder
    }
}

// Enum for TurnDirection
enum TurnDirection {
    Right, Left, Straight, UTurn;
}

// Enum for StreetSide
enum StreetSide {
    Left, Right;
}

// Helper class for Dijkstra's algorithm
class RouteNode {
    private String streetId;
    private double distance;
    private Route route;

    public RouteNode(String streetId, double distance, Route route) {
        this.streetId = streetId;
        this.distance = distance;
        this.route = route;
    }

    public String getStreetId() {
        return streetId;
    }

    public double getDistance() {
        return distance;
    }

    public Route getRoute() {
        return route;
    }
}