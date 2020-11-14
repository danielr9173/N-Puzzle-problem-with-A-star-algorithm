/*
 * <h1> AStar </h1>
 * The AStar class is the object that implements the A* algorithm to solve the N-puzzle problem
 * @author Daniel Ramirez
 * @version 1.0
 * @since 11-10-2020
 */

import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {

    private final int minCount;  //A variable to for the minimum amount of moves it can take
    private int totCount=0; //A variable to for the total amount of states

    /**
     * A AStar class constructor for the starting state of the puzzle.
     * @param initial The starting state of the puzzle
     */
    public AStar(PuzzleBoard initial)
    {
        PriorityQueue<State> openState = new PriorityQueue<>();  //Initialize the minimum PriorityQueue of openStates
        HashMap<Integer[],PuzzleBoard> closedState = new HashMap<>();

        State current;                          //Initialize the current state
        openState.add(new State(initial));      //inserts the starting state into the openState PriorityQueue

        while(true) {                   //Infinite loop until the puzzle is solved
            current = openState.poll(); //remove the minimum state from the queue and assign it as the current state
            closedState.put(current.getState().getConfiguration(),current.getState()); //add the current state to the close state

            totCount++;         //Increment the total counter
            //System.out.println("Move #" + current.moves);
            System.out.println();

            //current.getState().printArray(); for some reason it is bugging out when executed

            for(int i = 0; i<current.getState().Board.length; i++) {
                for(int j = 0; j<current.getState().Board.length; j++) {     //Display the current state board
                    if(current.getState().findElement(i,j) < 10)
                        System.out.print("|  "+current.getState().findElement(i,j));
                    else
                        System.out.print("| "+current.getState().findElement(i,j));
                    System.out.print(" |");
                }
                System.out.println();
            }

            if(current.getState().isGoal()) {  //Check to see if the current state is the goal state
                minCount = current.getMoves(); //assign the amount of moves it took from that current state
                break;      // * IMPORTANT * Break from the infinite loop
            }

            for(PuzzleBoard b : current.getState().neighbours()) {     //get each neighbour or child states from the current state
                if(current.getParent() == null || !b.equal(current.getParent().getState()))  //Check to see if its a either the starting state or the parent state
                    openState.add(new State(b,current,current.getMoves() + 1)); //insert the correct child states to the queue
            }
        }

    }

    /**
     * These are simple getter methods for the minCount and totCount
     */
    public int getMinCount() {
        return minCount;
    }
    public int getTotCount() {
        return totCount;
    }
}