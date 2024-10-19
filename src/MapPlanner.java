import java.util.*;

// Main class: MapPlanner
public class MapPlanner {
    private int degrees;
    private Location depot;
    private Map<String, Street> streets;
    private Map<String, Set<String>> adjacencyList; // To store the graph

    // Constructor
    public MapPlanner(int degrees) {
        this.degrees = degrees;
        this.streets = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    // Set depot location
    public boolean depotLocation(Location depot) {
        if (depot == null || !streets.containsKey(depot.getStreetId())) {
            throw new IllegalArgumentException("Invalid depot location.");
        }
        this.depot = depot;
        return true;
    }

    // Add street to the map
    public boolean addStreet(String streetId, Point start, Point end) {
        if (streets.containsKey(streetId)) {
            return false; // Street already exists
        }
        Street street = new Street(streetId, start, end);
        streets.put(streetId, street);

        // Add street connections in adjacency list
        adjacencyList.putIfAbsent(streetId, new HashSet<>());
        for (String id : streets.keySet()) {
            if (!id.equals(streetId) && streets.get(id).intersects(street)) {
                adjacencyList.get(streetId).add(id);
                adjacencyList.get(id).add(streetId);
            }
        }
        return true;
    }

    // Find the furthest street from the depot
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

    // Calculate route with no left turn constraint
    public Route routeNoLeftTurn(Location destination) {
        if (depot == null || !streets.containsKey(destination.getStreetId())) {
            throw new IllegalStateException("Depot or destination location not set.");
        }

        // Use a modified version of Dijkstra's algorithm to avoid left turns
        PriorityQueue<RouteNode> pq = new PriorityQueue<>(Comparator.comparingDouble(RouteNode::getDistance));
        Map<String, Double> distances = new HashMap<>();
        Map<String, Route> routes = new HashMap<>();
        Set<String> visited = new HashSet<>();

        pq.offer(new RouteNode(depot.getStreetId(), 0, new Route()));
        distances.put(depot.getStreetId(), 0.0);

        while (!pq.isEmpty()) {
            RouteNode current = pq.poll();
            String currentStreetId = current.getStreetId();
            double currentDistance = current.getDistance();

            if (visited.contains(currentStreetId)) continue;
            visited.add(currentStreetId);

            if (currentStreetId.equals(destination.getStreetId())) {
                return current.getRoute(); // Found the destination
            }

            for (String neighbor : adjacencyList.get(currentStreetId)) {
                if (canMakeRightTurn(currentStreetId, neighbor)) {
                    double newDist = currentDistance + streets.get(neighbor).length();
                    if (newDist < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        Route newRoute = new Route(current.getRoute(), neighbor, TurnDirection.Right);
                        pq.offer(new RouteNode(neighbor, newDist, newRoute));
                    }
                }
            }
        }
        throw new IllegalStateException("No route found to destination with no left turns.");
    }

    // Helper methods
    private double distanceFromDepot(Point point) {
        return Math.sqrt(Math.pow(point.getX() - depot.getPoint().getX(), 2) + Math.pow(point.getY() - depot.getPoint().getY(), 2));
    }

    // Check if right turn is possible based on current and next street
    private boolean canMakeRightTurn(String currentStreetId, String nextStreetId) {
        Street current = streets.get(currentStreetId);
        Street next = streets.get(nextStreetId);
        return current.calculateTurnDirection(next, degrees) == TurnDirection.Right;
    }
}
