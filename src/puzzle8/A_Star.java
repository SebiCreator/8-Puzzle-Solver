package puzzle8;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Sebastian Kaeser
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();

	private static HashSet<Board> closedList = new HashSet<>();
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();

	private static void expandBoard(Board current){
		for(var e: current.possibleActions()){
			if(closedList.contains(e)){
				continue;
			}
			if(!cost.containsKey(e)){
				cost.put(e,0);
			}

			int nextG = cost.get(current) + e.h1();

			if(openList.get(e) != null && nextG >= e.h1()){
				continue;
			}

			pred.replace(e,current);
			cost.replace(e,nextG);

			if (openList.get(e) != null){
				openList.change(e,nextG);
			} else{
				openList.add(e,nextG);
			}

		}
	}
	
	public static Deque<Board> aStar(Board startBoard) {
		openList.add(startBoard,0);

		while(!openList.isEmpty()){
			Board current = openList.removeMin();
			if(current.isSolved()){
				Deque<Board> path = new LinkedList<>();
				for(var r=pred.get(current); pred.get(r) == null ;r = pred.get(r)){
					path.add(r);
				}
				return path;
			}
			closedList.add(current);
			expandBoard(current);
		}
		return null; // Keine Lösung
	}
}
