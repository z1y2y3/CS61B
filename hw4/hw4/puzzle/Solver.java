package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private SearchNode goal;

    /*
        Constructor which solves the puzzle, computing
        everything necessary for moves() and solution() to
        not have to solve the problem again. Solves the
        puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        goal = null;
        MinPQ<SearchNode> pq = new MinPQ<>(SearchNode.getComparator());
        SearchNode first = new SearchNode(initial, 0, null);
        pq.insert(first);

        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();

            // WorldState.isGoal()
            if (node.getEstimatedDistance() == 0) {
                goal = node;
                break;
            }
            // for neighbors
            for (WorldState x : node.getWorldState().neighbors()) {
                if (node.getPrev() == null) {
                    SearchNode y = new SearchNode(x, node.getMoves() + 1, node);
                    pq.insert(y);
                    continue;
                }
                if (!x.equals(node.getPrev().getWorldState())) {
                    SearchNode y = new SearchNode(x, node.getMoves() + 1, node);
                    pq.insert(y);
                }
            }
        }

    }

    /*
        Returns the minimum number of moves to solve the puzzle starting
        at the initial WorldState.
     */
    public int moves() {
        return goal.getMoves();
    }

    /*
        Returns a sequence of WorldStates from the initial WorldState
        to the solution.
     */
    public Iterable<WorldState> solution() {
        List<WorldState> solution = new ArrayList<>();

        SearchNode current = goal;
        while (current != null) {
            solution.add(0, current.getWorldState());
            current = current.getPrev();
        }

        return solution;
    }
}
