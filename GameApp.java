import java.util.Scanner;

public class GameApp {

	private static Grid grid;
	private static Scanner scnr;
	
	public static void main(String[] args) 
	{
		scnr = new Scanner(System.in);
		grid = new Grid();
		
		System.out.println("Welcome to the Sudoku Solver Application\n");
		
		boolean go = true;
		while(go)
		{	
			System.out.println("A: Would you like to add values to the puzzle?\n"
					+ "B: Would you like to change the puzzle?\n"
					+ "C: Would you like to reset a cell?\n"
					+ "D: Would you like to view possibilities for a cell?\n"
					+ "E: Would you like to see the puzzle?\n"
					+ "F: Would you like to solve the puzzle?\n"
					+ "Z: Testing\n"
					+ "Q: Would you like to Quit?");
			
			String input = scnr.next();
			input = input.toUpperCase();
			
			switch(input)
			{
				case "A":
					addValues();
					break;
				case "B":
					changeValues();
					break;
				case "C":
					resetValue();
					break;
				case "D":
					printPossibilities();
					break;
				case "E":
					grid.printGrid();
					break;
				case "F":
					grid.backTrackingAlgo(0,0);
					break;
				case "Z":
			
					break;
				case "Q":
					System.out.println("Thanks for playing!");
					go = false;
					break;
				default:
					System.out.println(input + " is invalid input.");
					break;
			}
		}
	}
	private static void addValues() 
	{
		int row = getValueLoop("Please specify the row you would like to add a value to: ");
		int col = getValueLoop("Please specify the column you would like to add a value to: ");
		int value = getValueLoop("Please specify the value you would like to add at [" + row + "," + col + "]: ");
		grid.addValueToGrid(row-1,col-1,value);
		
	}
	private static void changeValues(){
		int row = getValueLoop("Please specify the row you would like to change a value from: ");
		int col = getValueLoop("Please specify the column you would like to change a value from: ");
		int value = getValueLoop("Please specify the value you would like to change to at [" + row + "," + col + "]: ");
		grid.changeValueInGrid(row-1,col-1,value);
	}
	public static void resetValue() 
	{
		int row = getValueLoop("Please specify the row of the cell you would like to reset: ");
		int col = getValueLoop("Please specify the column of the cell you would like to reset: ");
		grid.resetValueInGrid(row-1,col-1);
		
	}
	private static void printPossibilities() {
		int row = getValueLoop("Please specify the row of the cell you would like to view possibilities for: ");
		int col = getValueLoop("Please specify the column of the cell you would like to view possibilities for: ");
		grid.printPossibilities(row-1,col-1);
		
	}
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
