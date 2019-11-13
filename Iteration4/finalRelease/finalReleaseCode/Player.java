import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Player implements java.io.Serializable{
	public LinkedList<Piece> pieces;
	//if the movement is first time
	public boolean firstMove = true;
	//if the player can continue to play
	public boolean continuePlay = true;
	//set player as human player as default
	public boolean isComputer = false;
	private int score = 0;
	
	public Player(int color){
		int[][][] shapes = Piece.getAllPieces();
		pieces = new LinkedList<Piece>();
		for (int i = 0; i < shapes.length; i++){
			pieces.add(new Piece(shapes[i], color));
			}
		}
	/*Basic Scoring
    * count the number of squares in their remaining pieces
    * The player with the lowest number of squares win
    */
	public int getBasicScore() {
		for (Piece piece : pieces){
			score += piece.getRemainNumSquare();
			}
		return score;
		}
    /*Advanced Scoring
    * counts the number of squares in their remaining pieces
    */
	public int getAdvancedScore(){


		return score;
		}
    /*player earns +15 points if all of their pieces have been placed on the board
     * how to record the last piece removed(5 bonus points)??
     */ 
	public void calculateAdvancedScore() {
		if (getPiecesSize() == 0) {
			score = score + 15;
			//+ 5 points if last piece placed on the board was the one square
		}
		else {
			for (Piece piece : pieces){
			score -= piece.getRemainNumSquare();
			}
		}		
	}
	public void setComputerPlayer() {
		this.isComputer = true;
		}
	public void setHumanPlayer() {
		this.isComputer = false;
		}
	public boolean isComputerPlayer() {
		return this.isComputer;
		}
	public int getPiecesSize() {
		return pieces.size();
	}
	public void bonus5Score() {
		score = score +5;
		}
}
