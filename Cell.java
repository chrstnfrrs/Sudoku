import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Cell {

	private int row;
	private int col;
	private int value;
	private int counter;
	private List<Integer> possibleValues;
	
	public Cell(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
		this.counter = 0;
		possibleValues = new ArrayList<Integer>();
		for(int i=0; i<9; i++)
		{
			possibleValues.add(i+1);
		}
	}
	
	public int getRow(){
		return row;
	}
	
	public void setRow(int num){
		this.row = num;
	}
	
	public int getCol(){
		return col;
	}
	
	
	public void setCol(int num){
		this.col = num;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int num){
		this.value = num;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int num){
		this.counter = num;
	}
	
	public int getPossibleArraySize(){
		return possibleValues.size();
	}
	
	public List<Integer> getPossibleValues(){
		return possibleValues;
	}
	
	public void addPossibleValue(int num){
		if(!possibleValues.contains(num))
			possibleValues.add(num);
		else
			System.out.println("Adding Duplicate Value to PossibleValue Arraylist");
	}
	
	public int removePossibleValue(int num){
		if(possibleValues.contains(num)){
			return possibleValues.remove(possibleValues.indexOf(num));
		}else if(possibleValues.isEmpty()){
			return -1;
		}else{
			throw new NoSuchElementException();
		}
	}
	
	public boolean containsPossibleValue(int num){
		if(possibleValues.contains(num)){
			return true;
		}
		return false;
	}
	
	public boolean hasPossibleValues(){
		if(possibleValues.size()>0){
			return true;
		}
		return false;
	}
	
	public void printPossibilities() {
		for(int i=0;i<possibleValues.size();i++){
			System.out.println(possibleValues.get(i));
		}
	}
	
	@Override
	public String toString(){
		return "[" + (row+1) + "," + (col+1) + "] = " + value;
	}

}
