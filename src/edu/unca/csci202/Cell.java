package edu.unca.csci202;

/**
 * Cell is what the Gameboard class uses as each individual cell.
 * @author Jordan Satterfield
 * @version 1.2
 */

public class Cell {
	boolean has_mine; // determines if there is a mine in the cell.
	String display; //what is displayed on the array.
	String content; // str of the content: mine or number.
	boolean already_chosen = false; // determines if the cell has already been chosen by the user.
	public int xpos; // the column in which the cell is.
	public int ypos; // the row in which the cell is.
	
	/**
	 * Constructor for Cell class
	 */
	public Cell() {
		this.has_mine = false;
		this.display = "-";
		this.already_chosen = false;
	}
}
