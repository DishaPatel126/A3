import java.util.*;

public class MapPlanner {
    private int degrees;
    private Location depot;
    private Map<String, Street> streets;
    private Map<String, Set<String>> adjacencyList;

    // Constructor
    public MapPlanner(int degrees) {
        this.degrees = degrees;
        this.streets = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    // Getter for streets map
    public Map<String, Street> getStreets() {
        return streets;
    }

    // Set depot location
    public boolean setDepotLocation(Location depot) {
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
//            System.out.println("Street: " + street.getId() + ", Distance: " + distance);

            if (distance > maxDistance) {
                maxDistance = distance;
                furthestStreetId = street.getId();
            }
        }
        return furthestStreetId;
    }

    // Get streets adjacent to the current street
    public List<String> getNextStreets(String currentStreetId) {
//        System.out.println("Adjacency List: " + adjacencyList);

        return new ArrayList<>(adjacencyList.getOrDefault(currentStreetId, new HashSet<>()));
    }

    // Calculate route with no left turn constraint
    public Route routeNoLeftTurn(Location destination) throws Exception {
        // Starting point is the depot
        Location currentLocation = depot;
        String currentStreetId = currentLocation.getStreetId();

        // Create a new route starting from the depot
        Route route = new Route();

        while (!currentStreetId.equals(destination.getStreetId())) {
            // Get the next street options (adjacent streets)
            List<String> nextStreets = getNextStreets(currentStreetId);
//            System.out.println("Current Street: " + currentStreetId + ", Next Streets: " + nextStreets);

            boolean rightTurnFound = false;

            // Check the turn direction for each next street
            for (String nextStreetId : nextStreets) {
                Street currentStreet = streets.get(currentStreetId);
                Street nextStreet = streets.get(nextStreetId);
                TurnDirection turnDirection = currentStreet.calculateTurnDirection(nextStreet, degrees);
//                System.out.println("Current Street: " + currentStreetId + ", Next Street: " + nextStreetId + ", Turn: " + turnDirection);

                // Prioritize Right Turns, then Straight paths
                if (turnDirection == TurnDirection.Right) {
                    route = new Route(route, nextStreetId, turnDirection);  // Add to the route
                    currentStreetId = nextStreetId;  // Move to the next street
                    rightTurnFound = true;
                    break;
                }
            }

            // If no right turn is found, allow straight paths
            if (!rightTurnFound) {
                for (String nextStreetId : nextStreets) {
                    Street currentStreet = streets.get(currentStreetId);
                    Street nextStreet = streets.get(nextStreetId);
                    TurnDirection turnDirection = currentStreet.calculateTurnDirection(nextStreet, degrees);

                    if (turnDirection == TurnDirection.Straight) {
                        route = new Route(route, nextStreetId, turnDirection);
                        currentStreetId = nextStreetId;
                        rightTurnFound = true;
                        break;
                    }
                }
            }

            // If no valid right turn or straight path is found, throw an exception
            if (!rightTurnFound) {
                throw new Exception("No valid right turn or straight route available");
            }
        }

        return route;
    }

    // Helper methods
    private double distanceFromDepot(Point point) {
        return Math.sqrt(Math.pow(point.getX() - depot.getPoint().getX(), 2) + Math.pow(point.getY() - depot.getPoint().getY(), 2));
    }
}
