package puzzle8;

import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Sebastian Kaeser
 */
public class Puzzle8 {
	
	public static void main(String[] args) {
		Board b = new Board(); // Loesbares Puzzle b zufällig genrieren.
		System.out.println(b);

//		Deque<Board> res = A_Star.aStar(b);
//		int n = res == null ? -1 : res.size();
//		System.out.println("Anz.Zuege: " + n + ": " + res);
		
		var res = IDFS.idfs(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);
		int i=1;
		for(var e: res){
			System.out.printf("---- Runde %s von %s ----\n",i++,res.size());
			e.niceBoard();
		}
	}
}
