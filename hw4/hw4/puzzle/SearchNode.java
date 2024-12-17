package hw4.puzzle;

import java.util.Comparator;

public class SearchNode {
    /* current worldState*/
    private WorldState worldState;
    /*the number of moves made to reach this world state from the initial state.*/
    private int moves;
    /*a reference to the previous search node.*/
    private SearchNode prev;

    /* estimatedDistanceToGoal() */
    private int estimatedDistance;

    public SearchNode(WorldState worldState, int moves, SearchNode prev) {
        this.worldState = worldState;
        this.moves = moves;
        this.prev = prev;
        this.estimatedDistance = worldState.estimatedDistanceToGoal();
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public int getMoves() {
        return moves;
    }

    public SearchNode getPrev() {
        return prev;
    }

    public int getEstimatedDistance() {
        return estimatedDistance;
    }

    public static Comparator<SearchNode> getComparator() {
        return new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return (o1.getEstimatedDistance() + o1.getMoves())
                        - (o2.getEstimatedDistance() + o2.getMoves());
            }
        };
    }
}
