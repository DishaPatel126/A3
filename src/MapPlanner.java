import java.util.*;

public class MapPlanner {
    private int turnThreshold;
    private Location depot;
    private Map<String, Street> streets;
    private Map<String, Map<String, TurnDirection>> adjacencyList;

    public MapPlanner(int turnThreshold) {
        this.turnThreshold = turnThreshold;
        this.streets = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    public Map<String, Street> getStreets() {
        return streets;
    }

    public boolean setDepotLocation(Location depot) {
        if (depot == null || !streets.containsKey(depot.getStreetId())) {
            throw new IllegalArgumentException("Invalid depot location.");
        }
        this.depot = depot;
        return true;
    }

    public boolean addStreet(String streetId, Point start, Point end) {
        if (streets.containsKey(streetId)) {
            return false;  // Street already exists
        }
        Street street = new Street(streetId, start, end);
        streets.put(streetId, street);

        // Initialize adjacency list entry for the new street
        adjacencyList.putIfAbsent(streetId, new HashMap<>());

        // Check for intersections with existing streets
        for (String id : streets.keySet()) {
            if (!id.equals(streetId)) {
                Street otherStreet = streets.get(id);

                // Intersect if either start or end of one street matches the start or end of the other
                if (street.intersects(otherStreet)) {
                    TurnDirection turnDirection = street.calculateTurnDirection(otherStreet, turnThreshold);
                    adjacencyList.get(streetId).put(id, turnDirection);
                    adjacencyList.get(id).put(streetId, otherStreet.calculateTurnDirection(street, turnThreshold));
                }
            }
        }
        return true;
    }

    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (String street : adjacencyList.keySet()) {
            System.out.print(street + " -> ");
            for (Map.Entry<String, TurnDirection> entry : adjacencyList.get(street).entrySet()) {
                System.out.print("[" + entry.getKey() + ": " + entry.getValue() + "] ");
            }
            System.out.println();
        }
    }

    public String furthestStreet() {
        if (depot == null) {
            throw new IllegalStateException("Depot location not set.");
        }

        String furthestStreetId = "";
        double maxDistance = -1;

        for (Street street : streets.values()) {
            double distance = distanceFromDepot(street.getMidPoint());

            if (distance > maxDistance) {
                maxDistance = distance;
                furthestStreetId = street.getId();
            }
        }
        return furthestStreetId;
    }

    public List<String> getNextStreets(String currentStreetId) {
        return new ArrayList<>(adjacencyList.getOrDefault(currentStreetId, new HashMap<>()).keySet());
    }

    public Route routeNoLeftTurn(Location destination) throws Exception {
        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingDouble(RouteNode::getDistance));
        Map<String, Double> distanceMap = new HashMap<>();
        Map<String, Route> routeMap = new HashMap<>();

        String depotStreetId = depot.getStreetId();
        Street depotStreet = streets.get(depotStreetId);

        queue.add(new RouteNode(depotStreet, new Route(), 0));
        distanceMap.put(depotStreetId, 0.0);

        while (!queue.isEmpty()) {
            RouteNode current = queue.poll();
            Street currentStreet = current.getStreet();
            Route currentRoute = current.getRoute();
            double currentDistance = current.getDistance();

            if (currentStreet.getId().equals(destination.getStreetId())) {
                return currentRoute;  // Return the final route once we reach the destination
            }

            for (String neighbor : getNextStreets(currentStreet.getId())) {
                Street neighborStreet = streets.get(neighbor);
                TurnDirection turn = currentStreet.calculateTurnDirection(neighborStreet, turnThreshold);

                if (turn == TurnDirection.Right || turn == TurnDirection.Straight) {
                    double newDist = currentDistance + neighborStreet.length();

                    if (newDist < distanceMap.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                        distanceMap.put(neighbor, newDist);
                        // Extend the route by adding the next street and the turn direction
                        Route newRoute = new Route(currentRoute, neighbor, turn);
                        routeMap.put(neighbor, newRoute);
                        queue.add(new RouteNode(neighborStreet, newRoute, newDist));
                    }
                }
            }
        }

        throw new Exception("No valid route found.");
    }

    private double distanceFromDepot(Point point) {
        return Math.sqrt(Math.pow(point.getX() - depot.getPoint().getX(), 2) + Math.pow(point.getY() - depot.getPoint().getY(), 2));
    }

    // Helper class for Dijkstra's algorithm
    private static class RouteNode {
        private Street street;
        private Route route;
        private double distance;

        public RouteNode(Street street, Route route, double distance) {
            this.street = street;
            this.route = route;
            this.distance = distance;
        }

        public Street getStreet() {
            return street;
        }

        public Route getRoute() {
            return route;
        }

        public double getDistance() {
            return distance;
        }
    }
}
