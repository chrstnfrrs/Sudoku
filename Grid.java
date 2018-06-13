import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grid {
	private static Cell[][] grid;
	
	/**
	 * Constructor to create a Grid object
	 */
	public Grid(){
		grid = new Cell[9][9];
		for(int row=0; row<9;row++){
			for(int col=0; col<9; col++){
				grid[row][col] = new Cell(row,col,0);
			}
		}
	}
	/*
	 * Prints out values held by cells in the grid.
	 */
	public void printGrid(){
		System.out.println();
		for(int a=0;a<9;a++)
			System.out.print(" ---");
		System.out.println();
		for(int i=0;i<9;i++){
			System.out.print("| ");
			for(int j=0;j<9;j++){
				if(grid[i][j].getValue()!=0)
					System.out.print(grid[i][j].getValue() + " | ");
				if(grid[i][j].getValue()==0)
					System.out.print("  | ");
			}
			System.out.println();
			for(int k=0;k<9;k++){
				System.out.print(" ---");
			}
			System.out.println();
		}
		System.out.println();
	}
	/*
	 * Adds value to grid at row,col
	 */
	public void addValueToGrid(int row, int col, int value) {
		// Checks to make sure value is possible for cell at row, col
		if(grid[row][col].containsPossibleValue(value)){
			// Sets value at row, col
			grid[row][col].setValue(value);
			
			System.out.println("Added: "+grid[row][col] + "\n");
			// Changes possible values for cells affected by change.
			removePossibleValues(row,col,value);
			
		} 
		else {
			System.out.println("Cannot Add Value Due to Error at: " + findValue(row, col, value) + "\n");
		}
	}
	/*
	 * Checks to see if value is in the row, col, or 3 x 3 square
	 */
	private String findValue(int row, int col, int value){
		for(int i=0; i<9; i++){
			if(grid[row][i].getValue() == value)
				return grid[row][i].toString();			
			if(grid[i][col].getValue() == value)
				return grid[i][col].toString();
		}
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(arr[i].getValue()==value){
				return arr[i].toString();
			}
		}
		return "N/A"; 
	}
	/*
	 *  removes possible values in possible arrays for cells affected by 
	 *  changes to cell at row,col
	 */
	private void removePossibleValues(int row, int col, int value){
		grid[row][col].removePossibleValue(value);
		// removes possible values from cells in rows and cols
		for(int i=0; i<9; i++){
			if(i!=col && grid[row][i].containsPossibleValue(value))
				grid[row][i].removePossibleValue(value);			
			if(i!=row && grid[i][col].containsPossibleValue(value))
				grid[i][col].removePossibleValue(value);
		}
		// removes possible values from cells in 3 x 3 square
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(arr[i].containsPossibleValue(value)){
				arr[i].removePossibleValue(value);
			}
		}
	}
	/*
	 * add possible values to possible arrays for cells affected by row,col
	 */
	private void addPossibleValues(int row, int col, int value){
		grid[row][col].addPossibleValue(value);
		// adds values for horizontal and vertical cells
		for(int i=0; i<9; i++){
			if(i!=col && !(grid[row][i].containsPossibleValue(value)))
				grid[row][i].addPossibleValue(value);
			if(i!=row && !(grid[i][col].containsPossibleValue(value)))
				grid[i][col].addPossibleValue(value);
		}
		// adds values for other cells in the 3x3
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(!arr[i].containsPossibleValue(value)){
				arr[i].addPossibleValue(value);
			}
		}
	}
	/*
	 * Changes value in grid
	 */
	public void changeValueInGrid(int row, int col, int value){
		// if value is 0, uses resetValueInGrid method
		if(value == 0){
			resetValueInGrid(row,col);
		// changes value of cell at row,col and adjusts possible array for necessary cells.
		}else if(grid[row][col].containsPossibleValue(value)){
			int oldValue = grid[row][col].getValue();
			grid[row][col].setValue(value);
			addPossibleValues(row,col,oldValue);
			removePossibleValues(row,col,value);
			System.out.println("Changed: "+grid[row][col] + "\n");
		}
		else {
			System.out.println(value+": is not a possible value for [" + row +","+col+"].");
		}
	}
	/*
	 * Used to reset a cell in the grid to 0.
	 */
	public void resetValueInGrid(int row, int col){
		// sets value to the value held by cell at row,col
		int value = grid[row][col].getValue();
		// checks to make sure value needs to be reset
		if(value!=0){
			// sets value of cell at row,col to zero
			grid[row][col].setValue(0);
			// adds value back to cell at row,col 's array of possible values
			grid[row][col].addPossibleValue(value);
			// adds value back to the array of possible value for cells in row,col row and col
			for(int i=0; i<9; i++){
				if(i!=col && (findValue(row,i,value).equals("N/A")))
					grid[row][i].addPossibleValue(value);
				if(i!=row && (findValue(i,col,value).equals("N/A")))
					grid[i][col].addPossibleValue(value);
			}
			// adds value back to the array of possible value for cells in 3x3 but not in row or col
			Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
			for(int i=0;i<arr.length;i++){
				if((findValue(arr[i].getRow(),arr[i].getCol(),value).equals("N/A"))){
					arr[i].addPossibleValue(value);
				}
			}
			System.out.println("Reset: "+grid[row][col] + "\n");
		} else {
			System.out.println("Value is already 0");
		}
	}	
	/*
	 * Prints possible values for cell at row, col
	 */
	public void printPossibilities(int row, int col) {
		grid[row][col].printPossibilities();
	}
	/*
	 * Returns an array of cells in 3 x 3 square that are not horizontal or vertical
	 * to the cell at row, col.
	 */
	private static Cell[] nonHorizOrVertValues(int row, int col, Cell[] arr) {
		
		int modRow = row%3;
		int modCol = col%3;
		
		switch(modRow){
			case 0:
				switch(modCol){
					case 0:
						arr[0] = grid[row+1][col+1];
						arr[1] = grid[row+1][col+2];
						arr[2] = grid[row+2][col+1];
						arr[3] = grid[row+2][col+2];
						break;
					case 1:
						arr[0] = grid[row+1][col-1];
						arr[1] = grid[row+2][col-1];
						arr[2] = grid[row+1][col+1];
						arr[3] = grid[row+2][col+1];
						break;
					case 2:
						arr[0] = grid[row+1][col-2];
						arr[1] = grid[row+2][col-2];
						arr[2] = grid[row+1][col-1];
						arr[3] = grid[row+2][col-1];
						break;
				}
				break;
			case 1:
				switch(modCol){
					case 0:
						arr[0] = grid[row-1][col+1];
						arr[1] = grid[row-1][col+2];
						arr[2] = grid[row+1][col+1];
						arr[3] = grid[row+1][col+2];
						break;
					case 1:
						arr[0] = grid[row-1][col-1];
						arr[1] = grid[row+1][col-1];
						arr[2] = grid[row-1][col+1];
						arr[3] = grid[row+1][col+1];
						break;
					case 2:
						arr[0] = grid[row-1][col-2];
						arr[1] = grid[row-1][col-1];
						arr[2] = grid[row+1][col-2];
						arr[3] = grid[row+1][col-1];
						break;
				}
				break;
			case 2:
				switch(modCol){
					case 0:
						arr[0] = grid[row-2][col+1];
						arr[1] = grid[row-2][col+2];
						arr[2] = grid[row-1][col+1];
						arr[3] = grid[row-1][col+2];
						break;
					case 1:
						arr[0] = grid[row-2][col-1];
						arr[1] = grid[row-1][col-1];
						arr[2] = grid[row-2][col+1];
						arr[3] = grid[row-1][col+1];
						break;
					case 2:
						arr[0] = grid[row-2][col-2];
						arr[1] = grid[row-1][col-2];
						arr[2] = grid[row-2][col-1];
						arr[3] = grid[row-1][col-1];
						break;
				}
				break;
		}
		return arr;
	}
	/*
	 * Brute force recursive backtracking algorithm to solve sudoku puzzles.
	 */
	public boolean backTrackingAlgo(int row, int col) {
		 
		 // if row is -1 the grid is filled.
		 if (row == -1){
		   return true;
		 }

		  // if the grid currently has a value it does not need to be solved.
		 if (grid[row][col].getValue() != 0) {
			 // the grid progresses to the next cell
			 return backTrackingAlgo(getNextRow(row,col), getNextCol(col));
		 }
		  
		  // tries each possible value if there is no value at current place in grid
		 for (int x = 1; x < 10; x++) {
			 // checks if valid
			 boolean valid = isValid(row, col, x);
			 // if the value is not valid it continues to try values
			 if (!valid){ 
				 continue;
			 }
			 // sets the cells value to x
			 grid[row][col].setValue(x);;
			 // the grid progresses to the next cell
			 boolean solved = backTrackingAlgo(getNextRow(row,col), getNextCol(col));
			 // if solved, return
			 // else, try more values
			 if (solved){
				 return true;
			 } else {
				 grid[row][col].setValue(0);
			 }
		  }
		  
		 // if reached grid is not solvable
		  return false;
	}
	/*
	 * Checks if the value is valid for the potential cell in grid at row, col
	 */
	public static boolean isValid(int row, int col, int value) {

		if (grid[row][col].getValue() != 0) {
			throw new RuntimeException();
		}
		
		// if the row contains value, return false
		for (int y = 0; y < 9; y++) {
			if (grid[row][y].getValue() == value){
				return false;
			}
		}

		// if the column contains value, return false
		for (int x = 0; x < 9; x++) {
			if (grid[x][col].getValue() == value){
				return false;
			}
		}
		
		// checks rows within 3x3 not in row or col, if contains value, return false
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(arr[i].getValue()==value){
				return false;
			}
		}
		  
			return true;
	}
	/*
	 * Increments col. If col is greater than the largest possible index, row is incremented.
	 * If row is greater than the largest possible index -1 is returned to indicate the
	 * puzzle is solved. Returns row.
	 */
	private static int getNextRow(int row, int col){
			
		 col++;

		  if (col > 8) {
			  row++;
		  }

		  if (row > 8)
			  return -1; 

		  return row;
	 }
	/*
	 * Increments col. If col is greater than the largest possible index, col is reset
	 * to 0. Returns col.
	 */
	private static int getNextCol(int col){
			
		  col++;

		  if (col > 8) {
		   col = 0;
		  }

		  return col;
	 }
	/*
	 * Resets grid to empty status.
	 */
	public void gridReset(){
		grid = new Cell[9][9];
		for(int row=0; row<9;row++){
			for(int col=0; col<9; col++){
				grid[row][col] = new Cell(row,col,0);
			}
		}
	}
	
	/*
	 * Empties possible values for the grid.
	 */
	public void emptyPossibleValues(){
		for(int row=0;row<9;row++){
			for(int col=0;col<9;col++){
				grid[row][col].emptyPossibleValues();
			}
		}
	}
	
	public boolean finished(){
		for(int row=0; row<9; row++){
			for(int col=0; col<9; col++){
				if(grid[row][col].getValue() == 0)
					return false;
			}
		}
		return true;
	}
	
	public void loadEasy(int num) throws FileNotFoundException{
		
		File file = new File("src/puzzles/Easy_" + num + ".txt");
		Scanner scnr = new Scanner(file);
		scnr.nextLine();
		
		for(int i=1; scnr.hasNextInt(); i++){
			int x = scnr.nextInt() - 1;
			int y = scnr.nextInt() - 1;
			int z = scnr.nextInt();
			
			addValueToGrid(x,y,z);
		}
	}
	public void loadMod(int num) throws FileNotFoundException{
		File file = new File("src/puzzles/Med_" + num + ".txt");
		Scanner scnr = new Scanner(file);
		scnr.nextLine();
		
		for(int i=1; scnr.hasNextInt(); i++){
			int x = scnr.nextInt() - 1;
			int y = scnr.nextInt() - 1;
			int z = scnr.nextInt();
			
			addValueToGrid(x,y,z);
		}
		
	}
	public void loadDif(int num)throws FileNotFoundException{
		File file = new File("src/puzzles/Hard_" + num + ".txt");
		Scanner scnr = new Scanner(file);
		scnr.nextLine();
		
		for(int i=1; scnr.hasNextInt(); i++){
			int x = scnr.nextInt() - 1;
			int y = scnr.nextInt() - 1;
			int z = scnr.nextInt();
			
			addValueToGrid(x,y,z);
		}
		
	}
}
