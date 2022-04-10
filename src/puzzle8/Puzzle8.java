package puzzle8;

import java.awt.*;
import java.util.Deque;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Sebastian Kaeser
 */



public class Puzzle8 {

	public static void testIDFS() {
		Board b = new Board(); // Loesbares Puzzle b zufällig genrieren.
		System.out.println(b);


		var res = IDFS.idfs(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);
		/*
		int i = 1;
		for (var e : res) {
			System.out.printf("---- Runde %s von %s ----\n", i++, res.size());
			e.niceBoard();
		} */

		Board last = null;
		int i=0;
		for(var e: res){
			Board curr = e;
			if(last != null){
				System.out.printf("---- Runde %s von %s ----\n", ++i, res.size());
				curr.niceBoard(last);
			}
			last = curr;
		}

	}

	public static void testAStar() {
		Board b = new Board(); // Loesbares Puzzle b zufällig genrieren.
		//Board b = new Board(new int[]{0,2,1,3,4,5,6,8,7});
		System.out.println(b);
		Deque<Board> res = A_Star.aStar(b);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);

	}


	public static void main(String[] args) {
		testAStar();
	}

}
