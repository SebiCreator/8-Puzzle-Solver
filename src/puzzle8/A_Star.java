package puzzle8;

import java.util.*;

/**
 * @author Sebastian Kaeser
 */
public class A_Star {
    // cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
    // pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25).
    // In cost und pred sind genau alle Knoten der closedList und openList enthalten!
    // Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
    private static HashMap<Board, Integer> cost = new HashMap<>();
    private static HashMap<Board, Board> pred = new HashMap<>();

    private static HashSet<Board> closedList = new HashSet<>();
    // openList als Prioritätsliste.
    // Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
    private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();

    private static void expandBoard(Board current, int heu) {
        if (!cost.containsKey(current)) {
            cost.put(current, 0);
        }
        for (var e : current.possibleActions()) {
            if (closedList.contains(e)) {
                continue;
            }


            int nextG = 0;
            if (heu == 1) {
                nextG = cost.get(current) + e.h1();

                if (openList.get(e) != null && nextG >= e.h1()) {
                    continue;
                }
            }
            if (heu == 2) {
                nextG = cost.get(current) + e.h2();

                if (openList.get(e) != null && nextG >= e.h2()) {
                    continue;
                }
            }


            if (!pred.containsKey(e)) {
                pred.put(e, current);
            } else {
                pred.replace(e, current);
            }

            if (!cost.containsKey(e)) {
                cost.put(e, nextG);
            } else {
                cost.replace(e, nextG);
            }

            if (openList.get(e) != null) {
                openList.change(e, nextG);
            } else {
                openList.add(e, nextG);
            }

        }
    }


    public static Deque<Board> aStar(Board startBoard, int heu) {
        openList.clear();
        pred.clear();
        cost.clear();
        closedList.clear();
        openList.add(startBoard, 0);

        while (!openList.isEmpty()) {
            Board current = openList.removeMin();
            if (current.isSolved()) {
                List<Board> path = new LinkedList<>();
                while (current != null) {
                    path.add(current);
                    current = pred.get(current);
                }
                Collections.reverse(path);
                return (Deque<Board>) path;
            }
            closedList.add(current);
            expandBoard(current, heu);
        }
        return null; // Keine Lösung
    }
}
