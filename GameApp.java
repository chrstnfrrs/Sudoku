import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GameApp {

	private static Grid grid;
	private static Scanner scnr;
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		scnr = new Scanner(System.in);
		grid = new Grid();
		
		System.out.println("Welcome to the Sudoku Solver Application\n");
		
		boolean go = true;
		while(go)
		{	
			System.out.println("A: Would you like to load a puzzle?\n"
					+ "B: Would you like to add values to the puzzle?\n"
					+ "C: Would you like to change the puzzle?\n"
					+ "D: Would you like to reset a cell?\n"
					+ "E: Would you like to view possibilities for a cell?\n"
					+ "F: Would you like to see the puzzle?\n"
					+ "G: Would you like to solve the puzzle?\n"
					+ "H: Would you like to reset to an empty puzzle?\n"
					+ "Q: Would you like to Quit?");
			
			String input = scnr.next();
			input = input.toUpperCase();
			
			switch(input)
			{
				case "A":
					System.out.println("This case is not yet finished.");
					System.out.println("A: Easy\nB: Moderate\nC:Difficult");
					
					String secondInput = scnr.next();
					secondInput = secondInput.toUpperCase();
					
					Random rand = new Random();
					int num = rand.nextInt(10)+1;
					
					switch(secondInput){
						case "A":
							grid.loadEasy(num);
							break;
						case "B":
							grid.loadMod(num);
							break;
						case "C":
							grid.loadDif(num);
							break;
					}
					System.out.println(num);
					break;
				case "B":
					addValues();
					break;
				case "C":
					changeValues();
					break;
				case "D":
					resetValue();
					break;
				case "E":
					printPossibilities();
					break;
				case "F":
					grid.printGrid();
					break;
				case "G":
					grid.backTrackingAlgo(0,0);
					grid.printGrid();
					grid.emptyPossibleValues();
					System.out.println("The puzzle is solved!\n\nResetting grid...\n");
					grid.gridReset();
					break;
				case "H":
					grid.gridReset();
					break;
				case "Q":
					System.out.println("Thanks for playing!");
					go = false;
					break;
				default:
					System.out.println(input + " is invalid input.");
					break;
			}
			
			if(grid.finished()){
				System.out.println("The puzzle is solved!\n\nResetting grid...\n");
			}
		}
	}
	/*
	 * Prompts user to get user input to add values to grid
	 */
	private static void addValues() 
	{
		int row = getValueLoop("Please specify the row you would like to add a value to: ");
		int col = getValueLoop("Please specify the column you would like to add a value to: ");
		int value = getValueLoop("Please specify the value you would like to add at [" + row + "," + col + "]: ");
		grid.addValueToGrid(row-1,col-1,value);
		
	}
	/*
	 * Prompts user to get user input to change values in grid
	 */
	private static void changeValues(){
		int row = getValueLoop("Please specify the row you would like to change a value from: ");
		int col = getValueLoop("Please specify the column you would like to change a value from: ");
		int value = getValueLoop("Please specify the value you would like to change to at [" + row + "," + col + "]: ");
		grid.changeValueInGrid(row-1,col-1,value);
	}
	/*
	 * Prompts user to get user input to reset value in grid
	 */
	public static void resetValue() 
	{
		int row = getValueLoop("Please specify the row of the cell you would like to reset: ");
		int col = getValueLoop("Please specify the column of the cell you would like to reset: ");
		grid.resetValueInGrid(row-1,col-1);
		
	}
	/*
	 * Prompts user to get user input to print possible values for cell in grid
	 */
	private static void printPossibilities() {
		int row = getValueLoop("Please specify the row of the cell you would like to view possibilities for: ");
		int col = getValueLoop("Please specify the column of the cell you would like to view possibilities for: ");
		grid.printPossibilities(row-1,col-1);
		
	}
	/*
	 * Gets user input, checks if valid type
	 */
	private static int getValueLoop(String prompt)
	{
		boolean badInput = true;
		int x = 0;
		while(badInput)
		{
			System.out.print(prompt);
			if(scnr.hasNextInt()){
				x = scnr.nextInt();
				badInput = checkVal(x);
			}
			else {
				if(x==0){
					scnr.nextLine();
					x=-1;
				}
				String str = scnr.nextLine();
				System.out.println("Incorect input: " + str);
			}
		}
		return x;
	}
	/*
	 * Checks if user input is within bounds
	 */
	private static boolean checkVal(int x) 
	{
		if(x>0 && x<10)
		{
			return false;
		} 
		else 
		{ 
			return true;
		}
		
	}
	
	
}
