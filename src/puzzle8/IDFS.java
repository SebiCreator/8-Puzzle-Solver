package puzzle8;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author Sebastian Kaeser
 */
public class IDFS {

	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		if(curBoard.isSolved()){
			return path;
		}
		if (limit == 0){
			return null;
		}
		for(var e: curBoard.possibleActions()){
			if(e.isSolved()){
				path.add(e);
				return path;
			}
			if(path.contains(e)){
				continue;
			}
			path.add(e);
			Deque<Board> res = dfs(e,path,limit-1);
			if(res != null){
				return res;
			}

			path.remove(e);

		}

		return null;
	}


	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 5; limit < 30; limit++) {
			System.out.println("Maximum Limit ==> " + limit);
			Deque<Board> result = dfs(curBoard,path,limit);
			if (result != null)
				return result;
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		Deque<Board> res =  idfs(curBoard, path); 
		return res;
	}
}
