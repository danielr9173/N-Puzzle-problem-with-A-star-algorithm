/*
 * <h1> State </h1>
 * The State class is the object that implements from the Comparable library so that each state within the minimum
 * priority queue can be compared to each other.
 * @author Daniel Ramirez
 * @version 1.0
 * @since 11-10-2020
 */

public class State implements Comparable<State>{

    private final PuzzleBoard state;  //A board object of the current state
    private int moves = 0;  //A variable for the amount of moves taken for this current state
    private final State parent;  //A state object of the parent state to this current state
    private int manhattan = 0;     //The manhattan distance of the current state
    private int heuristicEsti = 0; //The heuristic estimation of the current state

    /**
     * A State class constructor for the starting state of the puzzle.
     * @param board A board object of the starting state
     */
    public State(PuzzleBoard board) {
        this.state = board;
        parent = null; //Set the starting state parent to null because it is the root
    }

    /**
     * A State class constructor for each state of the puzzle after each iterations.
     * @param board A board object of the current state
     * @param parent A state object of the parent state to this current state
     * @param moves A variable for the amount of moves taken for this current state
     */
    public State(PuzzleBoard board, State parent, int moves) {
        this.state = board;
        this.parent = parent;
        this.moves = moves;
        manhattan = board.manhattan()+moves;
        heuristicEsti = board.heuristicEsti()+moves;
    }

    /**
     * These are simple getter methods for the state, moves, parent, and heuristicEsti
     */
    public PuzzleBoard getState() {
        return state;
    }
    public int getMoves() {
        return moves;
    }
    public State getParent() {
        return parent;
    }
    public int getHeuristicEsti() {
        return heuristicEsti;
    }


    /**
     * This is overridden method which will provide the State class the functionalities to compare other states
     * within the minimum priority queue.
     */
    @Override
    public int compareTo(State that) {

        if(this.manhattan-that.manhattan > 0) //Gets the comparision between other states
            return 1;
        return -1;
    }
}