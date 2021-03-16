package edu.unca.csci202;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * Gameboard implements the minesweeper game. The goal is to find all the mines in the grid without blowing them up.
 * Gameboard prompts the user to play minesweeper, and declares the user a winner if the user successfully sweeps the 
 * minefield, or declares the user lost if unsuccessful.
 * 
 * @author Jordan Satterfield
 */
public class Gameboard {
	
	
	private int dimensions = 8;
	Cell[][] board;
	int count = 0;
	int row;
	int column;
	
	private boolean valid_input = false;
	public boolean play = false;
	public boolean peek = false;
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	
	String input;
	
	/**
	 * Constructor for the game board.
	 * Creates a 2D-grid: dimensions * dimensions
	 */
	public Gameboard() {
		this.board = new Cell[dimensions][dimensions];
	}
	
	
	/**
	 * Creates the minesweeper gameboard.
	 * Assigns cells to each index of the gameboard.
	 * Randomly places mines.
	 * Calculates the content of the cells (the number of adjacent mines).
	 */
	public void makeBoard() {
		int mines = 10;
		this.count = 0;
		this.peek = false;
		for (int i = 0; i < dimensions; i ++) { // generates the gameboard
			for (int j = 0; j < dimensions; j ++) {
				
				this.board[i][j] = new Cell();
				this.board[i][j].xpos = j;
				this.board[i][j].ypos = i;
			}
		}
		while (mines > 0) { // randomly places the mines throughout the gameboard
			int placement1,placement2;
			placement1 = rand.nextInt(dimensions); 
			placement2 = rand.nextInt(dimensions);
			if (this.board[placement1][placement2].has_mine == false) {
				this.board[placement1][placement2].has_mine = true;
				this.board[placement1][placement2].content = "M";
				mines --;
			}
			else {
				continue;
			}
		}
		
		int adjacent_mines = 0;
		for (int i = 0; i < dimensions; i ++) { // calculates the # of mines adjacent to each cell
			for (int j = 0; j < dimensions; j++) {
				if (this.board[i][j].has_mine == false) {
					adjacent_mines = 0;
					for (int k = i - 1; k <= i+1; k++) {
						for (int l = j - 1; l <= j + 1; l++) {
							try {
								if (this.board[k][l].has_mine == true) {
									adjacent_mines ++;
								}
								else {
									continue;
								}
							} catch (Exception IndexOutOfBoundsException) {
								continue;
							}
						}
					}
				}
				else {
					continue;
				}
				this.board[i][j].content = Integer.toString(adjacent_mines); // puts the # of adjacent mines as the content of the cell
				
			}
		}
	}
	
	
	/**
	 * @author Jordan Satterfield
	 * Prints the board to the console.
	 */
	public void displayBoard() {
		for (int i = 0; i < dimensions; i ++) { // displays the gameboard
			for (int j = 0; j < dimensions; j ++) {
				System.out.print(this.board[i][j].display + " ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Implements the game.
	 * Prompts the user for the row and column number of the cell.
	 * Asks the user if the cell is a mine.
	 * Keeps track of which cells have been chosen.
	 * Declares user winner/loser
	 */
	public void run() {

		while (!play) {
			
			valid_input = false;
			while (!valid_input) { // checks if user wants to play a game
				System.out.print("Play Minesweeper? (Y/N): ");
				input = scan.next();
				if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
					valid_input = true;
					if (input.equalsIgnoreCase("Y")) {
						play = true;
					}
					else {
						System.exit(0);
					}
				}
				else {
					continue;
				}
			
			}
			
			if (play) {
				
				// creates the board using makeBoard() method.
				boolean once = true;
				if (once) {
					makeBoard();
					once = false;
				}
				
				valid_input = false;
				while (play) {
					displayBoard();
					
					boolean place_mine = false;
										
					/*count keeps track of the number of cells chosen; 
					 if every cell has been correctly chosen, the user wins*/
					if (count == (dimensions*dimensions)) { 
						System.out.println("You Win!!");
						play = false;
						continue;
					}
					
					/*Asks the user if they would like to peek, if yes, all mines are displayed on the board*/
					if (!this.peek) {
						System.out.print("Would you like to peek? (Y/N): ");
						input = scan.next();
						if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
							valid_input = true;
							if (input.equalsIgnoreCase("Y")) { 
								this.peek = true;
								for (int i = 0; i < dimensions; i++) {
									for (int j = 0; j < dimensions; j++) {
										if (this.board[i][j].has_mine == true) {
											this.board[i][j].display = this.board[i][j].content;
											this.board[i][j].already_chosen = true;
											this.count ++;
										}
										System.out.print(this.board[i][j].display + " ");
									}
									System.out.println();
								}	
							}
	
						}
						else {
							System.out.println("Oops, try again.");
							continue;
						}
					}

					valid_input = false;
					while (!valid_input) {
						System.out.print("Pick a row (1-" + dimensions + "): "); // user enters the row where mine will be placed
						input = scan.next();
						try {
							this.row = Integer.parseInt(input) - 1;
							if (this.row >= 0 && this.row <= dimensions) {
								valid_input = true;
							}
							else {
								System.out.println("Oops, try again.");
							}
						} 
						catch (NumberFormatException e) {
							System.out.println("Oops, try again.");
							continue;
						}
					}
					
					valid_input = false;
					while (!valid_input) {
						System.out.print("Pick a column (1-" + dimensions + "): "); // user enters the column where mine will be placed
						input = scan.next();
						try {
							this.column = Integer.parseInt(input) - 1;
							if (this.column >= 0 && this.column <= dimensions) {
								valid_input = true;
							}
							else {
								System.out.println("Oops, try again.");
							}
						} 
						catch (NumberFormatException e) {
							System.out.println("Oops, try again.");
							continue;
						}
					}
				
					valid_input = false;
					while (!valid_input) {
						System.out.print("Is this a mine? (Y/N)");
						input = scan.next();
						if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
							valid_input = true;
							if (input.equalsIgnoreCase("Y")) {
								place_mine = true;
							}
						}
						else {
							System.out.println("Oops, try again.");
							continue;
						}
					}
					
					/*checks to see if cell has already been chosen so 
					 * count doesn't accidentally get incremented*/
				  
					if (place_mine) {
						if (this.board[this.row][this.column].has_mine == false) { // if wrong placement of mine
							System.out.println("You lose.");
							play = false;
						}
						else { // if correct placement of mine
							this.board[this.row][this.column].display = this.board[this.row][this.column].content;
							if (this.board[this.row][this.column].already_chosen == false) {
								this.board[this.row][this.column].already_chosen = true;
								this.count ++;
							}
						}
					}
					else if (!place_mine){
						if (this.board[this.row][this.column].has_mine == true) { // if user opens cell with a mine
							System.out.println("You lose.");
							play = false;
						}
						else { // if user opens cell without mine
							if (this.board[this.row][this.column].content.equalsIgnoreCase("0")) {
								expand(this.board[this.row][this.column].ypos, this.board[this.row][this.column].xpos);
							}
							else {
								this.board[this.row][this.column].display = this.board[this.row][this.column].content;
								if (this.board[this.row][this.column].already_chosen == false) {
									this.board[this.row][this.column].already_chosen = true;
									this.count ++;
								}
							}	
						}
					}
				}
			}
			continue;
		}
	}
	
	
	/**
	 * Expands the area around the chosen cell by loading the chosen cell
	 * and all adjacent cells that have no adjacent mines.
	 * @param row - the row number of the chosen cell.
	 * @param column - the column number of the chosen cell.
	 */
	public void expand(int row, int column) {
		Stack<Cell> st = new Stack<Cell>();
		Cell current;
		
		for (int i = this.row - 1; i <= this.row + 1; i ++) {
			for (int j = this.column - 1; j <= this.column + 1; j ++) {
				try {
					if (!this.board[i][j].already_chosen && this.board[i][j].content.equalsIgnoreCase("0")) {
						st.push(this.board[i][j]);
						this.board[i][j].already_chosen = true;
						this.board[i][j].display = this.board[i][j].content;
						this.count ++;
					}
					else if (Integer.parseInt(this.board[i][j].content) > 0 && !this.board[i][j].already_chosen) {
						this.board[i][j].display = this.board[i][j].content;
						this.board[i][j].already_chosen = true;
						this.count ++;
					}	
					else {
						continue;
					}
				} catch (IndexOutOfBoundsException e) {
					continue;
				}	
			}	
		}
		
		while (!st.isEmpty()) {
			current = st.pop();
	
			for (int i = current.ypos - 1; i <= current.ypos + 1; i ++) {
				for (int j = current.xpos - 1; j <= current.xpos + 1; j ++) {
					try {
						if (!this.board[i][j].already_chosen && this.board[i][j].content.equalsIgnoreCase("0")) {
							st.push(this.board[i][j]);
							this.board[i][j].already_chosen = true;
							this.board[i][j].display = this.board[i][j].content;
							this.count ++;
						}
						else if (Integer.parseInt(this.board[i][j].content) > 0 && !this.board[i][j].already_chosen){
							this.board[i][j].display = this.board[i][j].content;
							this.board[i][j].already_chosen = true;
							this.count ++;
						}	
						else {
							continue;
						}
					} catch (IndexOutOfBoundsException e) {
						continue;
					}	
				}	
			}
		}
	}
}
