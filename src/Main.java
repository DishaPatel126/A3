import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a MapPlanner object with a degree threshold for turns (e.g., 45 degrees)
        MapPlanner planner = new MapPlanner(45);

        // Add some streets to the map
        planner.addStreet("A", new Point(0, 0), new Point(0, 5));
        planner.addStreet("B", new Point(0, 5), new Point(5, 5));
        planner.addStreet("C", new Point(5, 5), new Point(5, 0));
        planner.addStreet("D", new Point(5, 0), new Point(0, 0));  // This creates a square

        // Add more streets
        planner.addStreet("E", new Point(0, 5), new Point(10, 5));
        planner.addStreet("F", new Point(10, 5), new Point(10, 0));
        planner.addStreet("G", new Point(10, 0), new Point(5, 0));

        // Set depot location (assume it's on street A in the middle)
        Location depot = new Location("A", new Point(0, 2), StreetSide.Right);
        planner.depotLocation(depot);

        // Find and print the furthest street from the depot
        String furthestStreet = planner.furthestStreet();
        System.out.println("Furthest Street from Depot: " + furthestStreet);

        // Set a destination location (assume it's on street F at the midpoint)
        Location destination = new Location("F", new Point(10, 2), StreetSide.Right);

        // Calculate the route with no left turns from the depot to the destination
        try {
            Route route = planner.routeNoLeftTurn(destination);
            System.out.println("Route found from Depot to Destination with no left turns:");

            // Print the streets and turns in the route
            List<String> streetsInRoute = route.streets;
            for (int i = 0; i < streetsInRoute.size(); i++) {
                System.out.println("Street: " + streetsInRoute.get(i) + ", Turn: " + route.turns.get(i));
            }
            System.out.println("Total route length: " + route.length());
        } catch (Exception e) {
            System.out.println("No valid route found: " + e.getMessage());
        }
    }
}
