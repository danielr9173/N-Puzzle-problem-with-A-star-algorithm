/*
 * <h1> PuzzleBoard </h1>
 * The PuzzleBoard class is the object that has all the attributes and methods for a puzzle board state
 * @author Daniel Ramirez
 * @version 1.0
 * @since 11-09-2020
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class PuzzleBoard {

    public int [][] Board; //This is a two dimensional array of the board
    private Integer[] configuration; //This is the configuration of the board when it is randomly generated.
    private int Nsize;  //This the N-size for the dimensions of the board

    /**
     * A PuzzleBoard class constructor for just initializing each of the the boards
     * @param board The puzzle board array
     */
    public PuzzleBoard(int [][]board) {
        this.Board = board;
    }

    /**
     * A PuzzleBoard class constructor for just initializing the stating board to the right dimensions and
     * each element of the array.
     * @param size The N-size of the dimensions of the board
     */
    public PuzzleBoard(int size) {
        Nsize = size;
        Board = new int[size][size];
        configuration = new Integer[size*size];
        initializeStates();
    }

    /**
     * This method just initializes the randomized starting state
     */
    private void initializeStates() {
        int num=0;
        for(int a=0; a<(Nsize*Nsize);a++) //this will create a ordered list of numbers to be randomly shuffled round.
            configuration[a] = a;
        Collections.shuffle(Arrays.asList(configuration)); //randomly shuffle each number.

        for(int i=0; i<Nsize; i++){
            for(int j=0; j<Nsize; j++){
                Board[i][j] = configuration[num];
                num++;
            }
        }
    }

    /**
     * Just a simple getter method for the configuration array
     * @return An array of the state values
     */
    public Integer[] getConfiguration() {
        return configuration;
    }

    /**
     * This method is for simply printing the board array
     */
    public void printArray(){
        for(int j=0;j<Nsize;j++) {
            for (int i = 0; i < Nsize; i++) {
                if(Board[j][i] < 10)
                    System.out.print("|  "+ Board[j][i]);
                else
                    System.out.print("| "+ Board[j][i]);
                System.out.print(" |");
            }
            System.out.println();
        }
    }

    /**
     * This method is for checking if the starting state is solvable.
     * @return boolean This will return true if the puzzle is solvable
     */
    public boolean isSolvable() {
        int inversions=0;

        for(int i=0; i<(Nsize*Nsize)-1; i++) {
            for(int j=i+1; j<(Nsize*Nsize); j++) {
                if(configuration[i]>configuration[j] && configuration[i]!=0 && configuration[j]!=0) {
                    inversions++;
                }
            }
        }
        return Math.pow(-1, inversions) == 1;
    }

    /**
     * This method is for calculating the difference of the current board state to the goal.
     * @return count This is the difference of the current board state
     */
    public int heuristicEsti() {
        int count=0;
        for(int i = 0; i< Board.length; i++) {
            for(int j = 0; j< Board.length; j++) {
                if(Board[i][j]!= Board.length*i+j+1 && Board[i][j]!=0)
                    count++;
            }
        }
        return count;
    }

    /**
     * This method is for getting the cost of the current state to the goal state
     * @return cost The cost of the current board state
     */
    public int manhattan() {
        int cost = 0;
        for (int row = 0; row < Board.length; row++) {
            for (int col = 0; col < Board.length; col++) {
                if (Board[row][col] != 0) {
                    int x = (Board[row][col] - 1) / Board.length;
                    int y = (Board[row][col] - 1) % Board.length;
                    cost += Math.abs(row - x) + Math.abs(col - y);
                }
            }
        }
        return cost;
    }

    /**
     * This method gets the current element of the array
     * @param row The row number of the two dimensional array
     * @param col The column number of the two dimensional array
     * @return The integer of the element
     */
    public int findElement(int row, int col) {
        return Board[row][col];
    }

    /**
     * This method simply checks of the current board state is the goal state
     * @return true if the current state is the goal state
     */
    public boolean isGoal() {
        for(int i = 0; i< Board.length; i++) {
            for(int j = 0; j< Board.length; j++) {
                if(Board[i][j]!= Board.length*i+j+1 && Board[i][j]!=0)
                    return false;
            }
        }
        return true;
    }

    /**
     * This method swaps the current state to with another calculated state in the neighbours() method
     * @param row1 The row index of the number that is being swapped
     * @param col1 The column index of the number that is being swapped
     * @param row2 The new row index of the number that is being swapped
     * @param col2 The new column index of the number that is being swapped
     * @return copy This is the copy array of the swapped number
     */
    private int [][] swap(int row1,int col1,int row2,int col2) {
        int[][] copy = new int[Board.length][Board.length];
        for (int row = 0; row < Board.length; row++)
            System.arraycopy(Board[row], 0, copy[row], 0, Board.length);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    /**
     * This methods is an iterable method which can be iterated through within a loop and will calculate each child of
     * this current state. Each child will be inserted into a linked list which will then be returned to the priority
     * queue in the AStar class.
     * @return children This will be the child states of the current board states
     */
    public Iterable<PuzzleBoard> neighbours() {
        LinkedList<PuzzleBoard> children = new LinkedList<>();
        int [] zeroIndex = getZeroIndex();

        if(zeroIndex[0] > 0)
            children.add(new PuzzleBoard(swap(zeroIndex[0], zeroIndex[1], zeroIndex[0]-1, zeroIndex[1])));  //Swaps the zeroIndex if it is going up
        if(zeroIndex[0] < Board.length-1)
            children.add(new PuzzleBoard(swap(zeroIndex[0], zeroIndex[1], zeroIndex[0]+1, zeroIndex[1])));  //Swaps the zeroIndex if it is going down
        if(zeroIndex[1] > 0)
            children.add(new PuzzleBoard(swap(zeroIndex[0], zeroIndex[1], zeroIndex[0], zeroIndex[1]-1)));  //Swaps the zeroIndex if it is going left
        if(zeroIndex[1] < Board.length-1)
            children.add(new PuzzleBoard(swap(zeroIndex[0], zeroIndex[1], zeroIndex[0], zeroIndex[1]+1)));  //Swaps the zeroIndex if it is going right

        return children;
    }

    /**
     * This method finds the zero value within the two dimensional array and get the index of the row and column.
     * @return location This will be the current index of the zero
     */
    private int [] getZeroIndex() {
        int [] location=new int[2];
        for(int i = 0; i< Board.length; i++) {
            for(int j = 0; j< Board.length; j++) {
                if(Board[i][j]==0) {
                    location[0]=i;
                    location[1]=j;
                    break;
                }
            }
        }
        return location;
    }

    /**
     * This method checks if the give state is equal to the current state of the board.
     * @param y The give board state to be compared
     * @return true if the boards equals each other
     */
    public boolean equal(PuzzleBoard y) {
        for (int row = 0; row < Board.length; row++)
            for (int col = 0; col < Board.length; col++)
                if ( y.Board[row][col] != this.Board[row][col]) return false;

        return true;
    }
}