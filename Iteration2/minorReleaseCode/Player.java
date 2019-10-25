import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Player implements java.io.Serializable
{
   public LinkedList<Piece> pieces;
   //if the movement is first time
   public boolean firstMove = true;
   //if the player can continue to play
   public boolean continuePlay = true;
   //set player as human player as default
   public boolean isComputer = false;
   
   public Player(int color) 
   {
      
      int[][][] shapes = Piece.getAllPieces();
      
      pieces = new LinkedList<Piece>();
      
      for (int i = 0; i < shapes.length; i++)
      {
         pieces.add(new Piece(shapes[i], color));
      }
   }
   
   
   public int getScore()
   {
	  
      int score = 0;
      //player earns +15 points if all of their pieces have been placed on the board
      //how to record the last piece removed(5 bonus points)??
      if (pieces.size() == 0) {
    	  score += 15;
      }
      for (Piece piece : pieces)
      {
         score -= piece.getRemainSquarePoints();
      }
      return score;
   }
   
   public void setComputerPlayer() {
	   this.isComputer = true;
   }
   public boolean isComputerPlayer() {
	   return this.isComputer;
   }
   
   
}
