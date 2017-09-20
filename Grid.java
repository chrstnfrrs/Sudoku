

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
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(grid[i][j].getValue() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public void addValueToGrid(int row, int col, int value) {
		if(grid[row][col].containsPossibleValue(value)){
			grid[row][col].setValue(value);
			
			System.out.println("Added: "+grid[row][col] + "\n");
			
			removePossibleValues(row,col,value);
			
		} 
		else {
			System.out.println("Cannot Add Value Due to Error at: " + findValue(row, col, value) + "\n");
		}
	}
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
	private void removePossibleValues(int row, int col, int value){
		grid[row][col].removePossibleValue(value);
		for(int i=0; i<9; i++){
			if(i!=col && grid[row][i].containsPossibleValue(value))
				grid[row][i].removePossibleValue(value);			
			if(i!=row && grid[i][col].containsPossibleValue(value))
				grid[i][col].removePossibleValue(value);
		}
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(arr[i].containsPossibleValue(value)){
				arr[i].removePossibleValue(value);
			}
		}
	}
	private void addPossibleValues(int row, int col, int value){
		grid[row][col].addPossibleValue(value);
		for(int i=0; i<9; i++){
			if(i!=col && !(grid[row][i].containsPossibleValue(value)))
				grid[row][i].addPossibleValue(value);
			if(i!=row && !(grid[i][col].containsPossibleValue(value)))
				grid[i][col].addPossibleValue(value);
		}
		Cell[] arr = nonHorizOrVertValues(row, col, new Cell[4]);	
		for(int i=0;i<arr.length;i++){
			if(!arr[i].containsPossibleValue(value)){
				arr[i].addPossibleValue(value);
			}
		}
	}
	public void changeValueInGrid(int row, int col, int value){
		if(value == 0){
			
		}else if(grid[row][col].containsPossibleValue(value)){
			int oldValue = grid[row][col].getValue();
			grid[row][col].setValue(value);
			addPossibleValues(row,col,oldValue);
			removePossibleValues(row,col,value);
			System.out.println("Changed: "+grid[row][col] + "\n");
		}
		else {
			System.out.println("CANNOT ADD VALUE HERE");
		}
	}
	public void resetValueInGrid(int row, int col){
		int value = grid[row][col].getValue();
		if(value!=0){
			grid[row][col].setValue(0);
			grid[row][col].addPossibleValue(value);
			for(int i=0; i<9; i++){
				if(i!=col && (findValue(row,i,value).equals("N/A")))
					grid[row][i].addPossibleValue(value);
				if(i!=row && (findValue(i,col,value).equals("N/A")))
					grid[i][col].addPossibleValue(value);
			}
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
	public void printPossibilities(int row, int col) {
		grid[row][col].printPossibilities();
	}
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
	private static int getNextRow(int row, int col){
			
		 col++;

		  if (col > 8) {
			  col = 0;
			  row++;
		  }

		  if (row > 8)
			  return -1; 

		  return row;
	 }
	private static int getNextCol(int col){
			
		  col++;

		  if (col > 8) {
		   col = 0;
		  }

		  return col;
	 }
}
