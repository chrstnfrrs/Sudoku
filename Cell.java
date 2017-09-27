import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Cell {

	private int row;
	private int col;
	private int value;
	private List<Integer> possibleValues;
	
	/*
	 * Constructor for Cell objects
	 */
	public Cell(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
		possibleValues = new ArrayList<Integer>();
		for(int i=0; i<9; i++)
		{
			possibleValues.add(i+1);
		}
	}
	/*
	 * Accessor method for row field variable
	 */
	public int getRow(){
		return row;
	}
	/*
	 * Mutator method for row field variable
	 */
	public void setRow(int num){
		this.row = num;
	}
	/*
	 * Accessor method for col field variable
	 */
	public int getCol(){
		return col;
	}	
	/*
	 * Mutator method for col field variable
	 */
	public void setCol(int num){
		this.col = num;
	}	
	/*
	 * Accessor method for value field variable
	 */
	public int getValue(){
		return value;
	}	
	/*
	 * Mutator method for value field variable
	 */
	public void setValue(int num){
		this.value = num;
	}		
	/*
	 * Accessor method for possibleValues list
	 */
	public List<Integer> getPossibleValues(){
		return possibleValues;
	}	
	/*
	 * Method adds possible values to list of possible values
	 */
	public void addPossibleValue(int num){
		// checks if list already contains value
		if(!possibleValues.contains(num))
			possibleValues.add(num);
		else
			System.out.println("Adding Duplicate Value to PossibleValue Arraylist");
	}	
	/*
	 * Method removes possible values from list of possible values
	 */
	public int removePossibleValue(int num){
		if(possibleValues.contains(num)){
			return possibleValues.remove(possibleValues.indexOf(num));
		}else if(possibleValues.isEmpty()){
			return -1;
		}else{
			throw new NoSuchElementException();
		}
	}	
	public void emptyPossibleValues(){
		for(int i=0; i<possibleValues.size();){
			possibleValues.remove(i);
		}
	}
	/*
	 * Checks if list contains value
	 */
	public boolean containsPossibleValue(int num){
		if(possibleValues.contains(num)){
			return true;
		}
		return false;
	}	
	/*
	 * Prints possible values for cell
	 */
	public void printPossibilities() {
		for(int i=0;i<possibleValues.size();i++){
			System.out.print(possibleValues.get(i) + " ");
		}
		System.out.println();
	}	
	@Override
	public String toString(){
		return "[" + (row+1) + "," + (col+1) + "] = " + value;
	}

}
