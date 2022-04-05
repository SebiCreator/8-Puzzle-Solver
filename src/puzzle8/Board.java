package puzzle8;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Klasse Board für 8-Puzzle-Problem
 * @author Sebastian Kaeser
 */
public class Board {

	private static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";
	/**
	 * Problmegröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];
	private static int[] goalBoard = new int[]{0,1,2,3,4,5,6,7,8};

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		List<Integer> possible = new ArrayList<>(
				List.of(0,1,2,3,4,5,6,7,8)
		);
		Random rand = new Random();
		for(int i=0; i < N+1; i++){
			int r = rand.nextInt(possible.size());
			this.board[i] = possible.remove(r);
		}
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
	    assert(board.length == N+1);
		for(int i=0; i <N+1; i++){
			this.board[i] = board[i];
		}
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {
		int counter = 0;
		for(int i=0; i < this.board.length; i++){
			for(int j=i; j < this.board.length; j++){
				if (this.board[j] < this.board[i]){
					counter ++;
				}
			}
		}
		return counter  % 2 == 0;
	}
	
	/**
	 * Heurstik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int counter = 0;
		for(int i=0; i < N+1; i++){
			if(this.board[i] != goalBoard[i] && this.board[i] != 0){
				counter++;
			}
		}
		return counter;
	}


	/**
	 * Heurstik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int sum = 0;
		for(int i=0; i < N+1; i++){
		    int num = this.board[i];
		    if(num == 0){
		    	continue;
			}
		    int rCol = i % 3;
		    int rRow = (int) (i / 3);
		    int optCol = num % 3;
		    int optRow = (int) (num / 3);
		   	int dist = Math.abs(rCol - optCol) + Math.abs(rRow - optRow);
		   	sum += dist;
		}
		return sum;

	}

	int rowColToIdx(int row, int col){
		return (row * 3) + col;
	}

	boolean boardConstraint(int num){
		if (num >= 0 && num <= 2){
			return true;
		}
		return false;
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();
		int nullRow = 0;
		int nullCol = 0;
		int nullIdx = 0;
		for(int i=0; i < N+1; i++){
			if(board[i] == 0){
				nullRow = (int) (i/3);
				nullCol = i % 3;
				nullIdx = i;
			}
		}

		List<Integer> idxs = new ArrayList<>();
		if(boardConstraint(nullRow-1)){
			idxs.add(rowColToIdx(nullRow-1,nullCol));
		}
		if(boardConstraint(nullRow+1)){
			idxs.add(rowColToIdx(nullRow+1,nullCol));
		}
		if(boardConstraint(nullCol-1)){
			idxs.add(rowColToIdx(nullRow,nullCol-1));
		}
		if(boardConstraint(nullCol+1)){
			idxs.add(rowColToIdx(nullRow,nullCol+1));
		}
		for(int i=0; i < idxs.size(); i++){
			int[] newBoard = Arrays.copyOf(board,N+1);
			newBoard[nullIdx] = newBoard[idxs.get(i)];
			newBoard[idxs.get(i)] = 0;
			Board b = new Board(newBoard);
			boardList.add(b);
		}
		return boardList;
	}
	
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		for(int i=0; i < N+1; i++){
			if(this.board[i] != goalBoard[i]){
				return false;
			}
		}
		return true;
	}

	String dont0(int num){
		if (num == 0){
			return " ";
		}
		else{
			return String.valueOf(num);
		}
	}

	public void niceBoard(){
		System.out.println("\n|---|---|---|");
		for(int i=0; i < 9; i+=3){
			System.out.printf("| %s | %s | %s |\n",
					dont0(board[i]),dont0(board[i+1]),dont0(board[i+2]));
			System.out.println("|---|---|---|");
		}
		System.out.println("\n");
	}

	 //TODO: Colored Changes
	void niceBoard(Board old){
		List<Integer> changed = new ArrayList<>();
		for(int i=0; i < N+1; i++){
			if(this.board[i] != old.board[i]){
				changed.add(i);
			}
		}
		System.out.print("\n|---|---|---|\n|");
		for(int i=0; i < 9; i++){
			if(changed.contains(i)){
				System.out.print(ANSI_BLUE);
				System.out.printf(" %s ",dont0(this.board[i]));
				System.out.print(ANSI_RESET);
				System.out.print("|");
			} else{
				System.out.printf(" %s |",dont0(this.board[i]));
			}
			if(i == 2 || i == 5){
				System.out.print("\n|---|---|---|\n|");
			}
			if(i == 8){
				System.out.print("\n|---|---|---|");
			}
		}
		System.out.println("\n\n");
	}



	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});

		for(var e: b.possibleActions()){
		    e.niceBoard(b);
		}





//		System.out.println(b);
//		System.out.println(b.parity());
//		System.out.println(b.h1());
//		System.out.println(b.h2());
//
//		for (Board child : b.possibleActions())
//			System.out.println(child);
//
//		System.out.println(goal.isSolved());
	}
}
	
