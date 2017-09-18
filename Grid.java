import java.util.List;

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

	public void printGrid(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(grid[i][j].getValue() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void backTrackingAlgo(int i, int j){
		int counter = grid[i][j].getCounter();
		List<Integer> posVals = grid[i][j].getPossibleValues();
		
		if(grid[i][j].hasPossibleValues() && (grid[i][j].getPossibleArraySize() > counter)){
			
			int value = posVals.get(counter);
			
			if(grid[i][j].getValue() == 0){
				System.out.println("Adding At " + i + "," + j);
				System.out.println("counter = " + posVals.get(counter));
				addValueToGrid(i,j,posVals.get(counter));

			} else {
				System.out.println("Changing At " + i + "," + j);
				changeValueInGrid(i,j,posVals.get(counter));
			}
			
			grid[i][j].setCounter(counter++);
			
			if(i==8 && j==8){
				return;
			} else if(i<8 && j==8){
				backTrackingAlgo(i+1, 0);
				return;
			} else{
				backTrackingAlgo(i, j+1);
				return;
			}
		} else {
			
			resetValueInGrid(i,j);
			grid[i][j].setCounter(0);
			
			if(i==8 && j==8){
				return;
			} else if(j==0){
				backTrackingAlgo(i-1,8);
				return;
			} else { 
				backTrackingAlgo(i,j-1);
				return;
			}
		}
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
		Cell[] arr = new Cell[4];
		arr = nonHorizOrVertValues(row,col,value,arr);	
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
		Cell[] arr = new Cell[4];
		arr = nonHorizOrVertValues(row,col,value,arr);	
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
		Cell[] arr = new Cell[4];
		arr = nonHorizOrVertValues(row,col,value,arr);	
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
			Cell[] arr = new Cell[4];
			arr = nonHorizOrVertValues(row,col,value,arr);	
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

	public void printPossibilities(int i, int j) {
		grid[i][j].printPossibilities();
	}

	private Cell[] nonHorizOrVertValues(int row, int col, int value, Cell[] arr) {
		
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

}
