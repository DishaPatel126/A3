import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Route class
class Route {
    private List<String> streets;
    private List<TurnDirection> turns;

    public Route() {
        streets = new ArrayList<>();
        turns = new ArrayList<>();
    }

    public Route(Route existingRoute, String nextStreet, TurnDirection turn) {
        this.streets = new ArrayList<>(existingRoute.streets);
        this.turns = new ArrayList<>(existingRoute.turns);
        streets.add(nextStreet);
        turns.add(turn);
    }

    // Getter for streets
    public List<String> getStreets() {
        return streets;
    }

    // Getter for turns
    public List<TurnDirection> getTurns() {
        return turns;
    }

    public int legs() {
        return streets.size();
    }

    // Calculate the total length of the route using the streets map from MapPlanner
    public double length(Map<String, Street> streetMap) {
        double totalLength = 0;
        for (String streetId : streets) {
            Street street = streetMap.get(streetId);
            if (street != null) {
                totalLength += street.length();  // Use the length method from the Street class
            }
        }
        return totalLength;
    }

    public List<SubRoute> loops() {
        // Method to calculate loops (intersections visited twice)
        return new ArrayList<>(); // Placeholder
    }

    public Route simplify() {
        // Simplifies the route by keeping only the turns
        Route simplified = new Route();
        simplified.streets.addAll(streets);
        simplified.turns.addAll(turns);
        return simplified;
    }
}
