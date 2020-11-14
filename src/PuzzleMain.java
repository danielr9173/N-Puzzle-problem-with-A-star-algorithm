/*
 * <h1> PuzzleMain </h1>
 * The PuzzleMain class is the main file that can run the program within the main function.
 * @author Daniel Ramirez
 * @version 1.0
 * @since 11-09-2020
 */

import java.util.Scanner;

public class PuzzleMain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); //Initialize the scanner to get the user input
        int Nvalue;  //Holds the N-size for the dimensions of the board

        System.out.println("\n---- | Please enter the N-size of the board. For example, N = 3 is a 3x3 board size |----");
        Nvalue = input.nextInt();   //Ask the user for the N-size then get the input

        double start = System.currentTimeMillis(); //Get the start time before the process starts

        PuzzleBoard puzzle = new PuzzleBoard(Nvalue); //Initialize the puzzle board with the random starting state
        System.out.println("---- | Now generating a random starting state |----\n");
        puzzle.printArray(); //Display the randomize starting state

        if(puzzle.isSolvable()){  //Check to see if the starting state can even be solved before proceeding to solve
            System.out.println("\n---- | Now solving the puzzle! |----");
            AStar solve = new AStar(puzzle);                //Initialize the AStar object with the starting puzzle to begin solving the puzzle.
            double finish = System.currentTimeMillis();     //After done solving get the finish time then display the results
            System.out.println("\n---- | The minimum amount of moves from the start state to the goal state is "+solve.getMinCount()+" moves |----");
            System.out.println("---- | The total amount of states taken to solve this puzzle was "+solve.getTotCount()+" states |----");
            System.out.println("---- | Total execution time was "+(finish-start)/1000 +" seconds");
        }
        else
            System.out.println("\n---- | This starting state is an unsolvable puzzle. Please try again |----");
    }
}
