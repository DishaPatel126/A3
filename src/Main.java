//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        // Create a MapPlanner object with a degree threshold for turns (e.g., 45 degrees)
//        MapPlanner planner = new MapPlanner(45);
//
//        // Add some streets to the map
//        planner.addStreet("A", new Point(0, 0), new Point(0, 5));
//        planner.addStreet("B", new Point(0, 5), new Point(5, 5));
//        planner.addStreet("C", new Point(5, 5), new Point(5, 0));
//        planner.addStreet("D", new Point(5, 0), new Point(0, 0));  // This creates a square
//
//        // Add more streets
//        planner.addStreet("E", new Point(0, 5), new Point(10, 5));
//        planner.addStreet("F", new Point(10, 5), new Point(10, 0));
//        planner.addStreet("G", new Point(10, 0), new Point(5, 0));
//
//        // Set depot location (assume it's on street A in the middle)
//        Location depot = new Location("A", new Point(0, 2), StreetSide.Right);
//        planner.depotLocation(depot);
//
//        // Find and print the furthest street from the depot
//        String furthestStreet = planner.furthestStreet();
//        System.out.println("Furthest Street from Depot: " + furthestStreet);
//
//        // Set a destination location (assume it's on street F at the midpoint)
//        Location destination = new Location("F", new Point(10, 2), StreetSide.Right);
//
//        // Calculate the route with no left turns from the depot to the destination
//        try {
//            Route route = planner.routeNoLeftTurn(destination);
//            System.out.println("Route found from Depot to Destination with no left turns:");
//
//            // Print the streets and turns in the route using the getter methods
//            List<String> streetsInRoute = route.getStreets();
//            List<TurnDirection> turnsInRoute = route.getTurns();
//
//            for (int i = 0; i < streetsInRoute.size(); i++) {
//                System.out.println("Street: " + streetsInRoute.get(i) + ", Turn: " + turnsInRoute.get(i));
//            }
//
//            // Pass the streets map from planner to the route length method
//            System.out.println("Total route length: " + route.length(planner.getStreets()));
//        } catch (Exception e) {
//            System.out.println("No valid route found: " + e.getMessage());
//        }
//    }
//}


import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a new MapPlanner object with a degree threshold for turns (e.g., 45 degrees)
        MapPlanner planner = new MapPlanner(45);

        // Add streets to the map
        planner.addStreet("A", new Point(6, 0), new Point(0, 0));
        planner.addStreet("B", new Point(0, 0), new Point(2, 2));
        planner.addStreet("C", new Point(2, 2), new Point(10, 2));
        planner.addStreet("D", new Point(2, 2), new Point(2, 6));  // Another square block

        planner.addStreet("E", new Point(10, 2), new Point(10, 6));
        planner.addStreet("F", new Point(2, 6), new Point(0, 6));
        planner.addStreet("G", new Point(2, 6), new Point(10, 6));
        planner.addStreet("H", new Point(2, 6), new Point(2, 10));

        planner.addStreet("I", new Point(10, 6), new Point(10, 8));
        planner.addStreet("J", new Point(10, 8), new Point(6, 8));
        planner.addStreet("K", new Point(10, 10), new Point(10, 8));
        planner.addStreet("L", new Point(2, 10), new Point(10, 10));

        // Set depot location (assume it's on street Y near the middle)
        Location depot = new Location("G", new Point(6, 6), StreetSide.Right);
        planner.depotLocation(depot);

        // Find and print the furthest street from the depot
        String furthestStreet = planner.furthestStreet();
        System.out.println("Furthest Street from Depot: " + furthestStreet);

        Location destination = new Location("D", new Point(2, 4), StreetSide.Right);

        // Set a destination location (assume it's on street j near the midpoint)
//        Location destination = new Location("J", new Point(8, 8), StreetSide.Right);

        // Calculate the route with no left turns from the depot to the destination
        try {
            Route route = planner.routeNoLeftTurn(destination);
            System.out.println("Route found from Depot to Destination with no left turns:");

            // Print the streets and turns in the route using the getter methods
            List<String> streetsInRoute = route.getStreets();
            List<TurnDirection> turnsInRoute = route.getTurns();

            for (int i = 0; i < streetsInRoute.size(); i++) {
                System.out.println("Street: " + streetsInRoute.get(i) + ", Turn: " + turnsInRoute.get(i));
            }

            // Pass the streets map from planner to the route length method
            System.out.println("Total route length: " + route.length(planner.getStreets()));
        } catch (Exception e) {
            System.out.println("No valid route found: " + e.getMessage());
        }
    }
}

