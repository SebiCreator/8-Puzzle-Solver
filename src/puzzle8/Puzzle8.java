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
		//Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		System.out.println(b);

		int heuris = 1;
		var res = A_Star.aStar(b,heuris);
		if (res == null) {
			return;
		}
		System.out.println("-------Heurisk: " + heuris + "---------" );
		System.out.println("Züge: " + res.size());
		for(var e: res){
			e.niceBoard();
		}

		/*System.out.println("--------- H 1 --------");
		Deque<Board> res = A_Star.aStar(b,1);
		int n = res == null ? -1 : res.size();
		System.out.println("Anz.Zuege: " + n + ": " + res);

		System.out.println("--------- H 2 --------");
		Deque<Board> res2 = A_Star.aStar(b,2);
		int n2 = res2 == null ? -1 : res2.size();
		System.out.println("Anz.Zuege: " + n2 + ": " + res2);*/

	}


	public static void comparison(){
		Board b = new Board();
		//Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt

		System.out.println("---- Original Board ----");
		System.out.println(b);

		var res = IDFS.idfs(b);
		System.out.println("----IDFS (max Tiefe: 30)----");
		if (res != null){
			System.out.println("Steps: " + res.size());
			System.out.println(res);
		} else{
			System.out.println("Null for IDFS");
		}

		var res2 = A_Star.aStar(b,1);
		System.out.println("----AStar(1)----");
		if(res2 != null){
			System.out.println("Steps: " + res2.size());
			System.out.println(res2);
		} else {
			System.out.println("Null for AStar1");
		}

		var res3 = A_Star.aStar(b,2);
		System.out.println("----AStar(2)----");
		if(res3 != null){
			System.out.println("Steps: " + res3.size());
			System.out.println(res3);
		} else {
			System.out.println("Null for AStar2");
		}

		var res4 = A_Star.aStar2(b);
		System.out.println("----AStar2----");
		if (res4 != null){
			System.out.println("Steps: " + res4.size());
			System.out.println(res4);
		} else {
			System.out.println("Null for AStarV2");
		}





	}


	public static void main(String[] args) {
		testIDFS();
	}

}
