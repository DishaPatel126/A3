import java.util.ArrayList;
import java.util.List;

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

    public int legs() {
        return streets.size();
    }

    public double length() {
        double length = 0;
        for (String streetId : streets) {
            length += streets.get(Integer.parseInt(streetId)).length();
        }
        return length;
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