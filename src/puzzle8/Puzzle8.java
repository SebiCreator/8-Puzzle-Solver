package puzzle8;

import java.awt.*;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Hauptprogramm für 8-Puzzle-Problem.
 * @author Sebastian Kaeser
 */



public class Puzzle8 {

	public static void printNice(Deque<Board> results){
	    if (results == null){
			System.out.println("---- Kein Ergebnis! ----");
		}
		Board last = null;
		int i = 0;
		for(var e: results){
			Board curr = e;
			if(last != null){
				System.out.printf("---- Runde %s von %s  ----\n",++i,results.size()-1);
				curr.niceBoard(last);
			}
			last = curr;
		}
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
		System.out.println("\n----AStar(1)----");
		if(res2 != null){
			System.out.println("Steps: " + res2.size());
			System.out.println(res2);
		} else {
			System.out.println("Null for AStar1");
		}

		var res3 = A_Star.aStar(b,2);
		System.out.println("\n----AStar(2)----");
		if(res3 != null){
			System.out.println("Steps: " + res3.size());
			System.out.println(res3);
		} else {
			System.out.println("Null for AStar2");
		}

	}


	public static void main(String[] args) {
	    comparison();
	}

}
