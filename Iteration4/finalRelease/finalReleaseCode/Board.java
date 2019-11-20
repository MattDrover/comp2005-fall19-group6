import javax.swing.*;

//import Blokus.BlokusPieceLabel;
//import Blokus.PieceListener;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Point;



public class Board implements java.io.Serializable
{

   
   public static final Color COLOROFBOARD = new Color(250,250,250);
   public static final Color COLOROFLINE = Color.BLACK;
   public static final String ERRORPROMPT= "Illegal! Please reconsider your move";
   
   //grids of the board
   private int[][] grid;
   //the selected piece showing on the board
   private int[][] pieceDisplay;
   
   public static final int NONE = 0;
   public static final int BLUE = 1;
   public static final int YELLOW = 2;
   public static final int RED = 3;
   public static final int GREEN = 4;
   //20*20 board
   public static final int SIZEOFBOARD = 20;
   public static final int DISPLAY_SIZE = 500;
   
   private int locationx;
   private int locationy;
   
   public Board()
   {
      grid = new int[SIZEOFBOARD][SIZEOFBOARD];
      pieceDisplay = new int[SIZEOFBOARD][SIZEOFBOARD];
      reset(grid);
      reset(pieceDisplay);
    }
   
   //check if the movement is legal
   public boolean isLegalMove(Piece currentPiece, int nx, int ny, boolean firstMover) throws IllegalMoveException
   {
      boolean corner = false;
      for (int x = 0; x < Piece.sizeOfPiece; x++)
      {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {	
             int value = currentPiece.getValue(x, y);
             boolean inBound = isInBoundary(x + nx, y + ny);
             if (inBound)
             {
                int valueOfGrid = grid[x + nx][y + ny];
                if (valueOfGrid != NONE)
                {
                   if (value == Piece.piece) throw new IllegalMoveException(ERRORPROMPT);
                   if (valueOfGrid == currentPiece.getColor())
                   {
                      if (value == Piece.adjoining) throw new IllegalMoveException(ERRORPROMPT);
                      if (value == Piece.corner) corner = true;
                   }
                }
                else
                {
                   if (firstMover && value == Piece.piece && new Point(x + nx, y + ny).equals(getCorner(currentPiece.getColor())))
                      corner = true;
                }
             }
             else
             {
                if (value == Piece.piece) throw new IllegalMoveException(ERRORPROMPT);
             }
         }
      }
      if (!corner) throw new IllegalMoveException(ERRORPROMPT);

      return true;
   }
   
   //check if the movement is legal
   public boolean isLegalMoveComputer(Piece currentPiece, int nx, int ny, boolean firstMover)
   {
      boolean corner = false;
      for (int x = 0; x < Piece.sizeOfPiece; x++)
      {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {	
             int value = currentPiece.getValue(x, y);
             boolean inBoundary = isInBoundary(x + nx, y + ny);
             if (inBoundary)
             {
                int valueOfGrid = grid[x + nx][y + ny];
                if (valueOfGrid != NONE)
                {
                   if (value == Piece.piece) return false;
                   if (valueOfGrid == currentPiece.getColor())
                   {
                      if (value == Piece.adjoining) return false;
                      if (value == Piece.corner) corner = true;
                   }
                }
                else
                {
                   if (firstMover && value == Piece.piece && new Point(x + nx, y + ny).equals(getCorner(currentPiece.getColor())))
                      corner = true;
                }
             }
             else
             {
                if (value == Piece.piece) return false;
             }
         }
      }
      if (!corner) return false;

      return true;
   }
   public String getLocation(Piece currentPiece, int nx, int ny, boolean firstMover)
   {
	   String result = "";
	   for (int x = 0; x < Piece.sizeOfPiece; x++)
	   {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {	
        	 if (currentPiece.getValue(x, y) == Piece.piece) {

                  	result = result.concat(""+" ("+(x+nx)+", "+(y+ny)+") ");

            }
      }
      
   }
	   return result;
   }
   //
   //public boolean isValidMove(Piece currentPiece, int nx, int ny) throws IllegalMoveException
   //{
   //   return isLegalMove(currentPiece, nx, ny, false);
   //}
   
   public int computerPlacePiece(LinkedList<Piece> pieceList, boolean firstMover, 
		   String scoSetting, int pieceSize, Player player) throws IllegalMoveException{
	   int pIndex = 0;

       for (int i = 0; i < player.pieces.size(); i++){
    	   for(int w = 0; w < 20; w++) {
    		   for(int h = 0; h <20; h++) {
            	  //available movements
                 if (true== isLegalMoveComputer(player.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, player.firstMove)){
                	 pIndex = i;
                	 int xLocation= w;
                	 int yLocation = h;
                	 Piece currentPiece = pieceList.get(i);
                	 //player get 5 bonus score if the last piece placed is the 1-square piece 
                	 if(scoSetting == "advancedScoring") {
                		 if(pieceSize == 1) {
                			 int count = 0;
                			 for (int x = 0; x < currentPiece.sizeOfPiece; x++){
                				 for (int y = 0; y < currentPiece.sizeOfPiece; y++){
                					 if (currentPiece.getValue(x, y) == Piece.piece) {
                						 count += 1;
                						 }
                					 }
                				 }
                			 if (count == 1) {
                				 player.bonus5Score();
                				 }
                			 }		   
                		 }   
                	 for (int x = 0; x < currentPiece.sizeOfPiece; x++)
                	 {
                		 for (int y = 0; y < currentPiece.sizeOfPiece; y++)
                		 {
                			 if (currentPiece.getValue(x, y) == Piece.piece) {
                				 grid[x+w-currentPiece.sizeOfPiece/2][y+h-currentPiece.sizeOfPiece/2] = currentPiece.getColor();
                				
                				 }
                			 }
                		 }return pIndex;
                	 
                	 }
                 }
    		   } 
           }
       
       	return pIndex;
	   }
   //easy difficulty: place the piece with smaller number of squares first 
   public int easyComputerPlacePiece(LinkedList<Piece> pieceList, boolean firstMover, 
		   String scoSetting, int pieceSize, Player player) throws IllegalMoveException{
	   int pIndex = 0;
       int maxTemp = player.pieces.size()-1;
       for (int i = maxTemp; i >=0; i--){
    	   for(int w = 0; w < 20; w++) {
    		   for(int h = 0; h <20; h++) {
            	  //available movements
                 if (true== isLegalMoveComputer(player.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, player.firstMove)){
                	 pIndex = i;
                	 int xLocation= w;
                	 int yLocation = h;
                	 Piece currentPiece = pieceList.get(i);
                	 //player get 5 bonus score if the last piece placed is the 1-square piece 
                	 if(scoSetting == "advancedScoring") {
                		 if(pieceSize == 1) {
                			 int count = 0;
                			 for (int x = 0; x < currentPiece.sizeOfPiece; x++){
                				 for (int y = 0; y < currentPiece.sizeOfPiece; y++){
                					 if (currentPiece.getValue(x, y) == Piece.piece) {
                						 count += 1;
                						 }
                					 }
                				 }
                			 if (count == 1) {
                				 player.bonus5Score();
                				 }
                			 }		   
                		 }   
                	 for (int x = 0; x < currentPiece.sizeOfPiece; x++)
                	 {
                		 for (int y = 0; y < currentPiece.sizeOfPiece; y++)
                		 {
                			 if (currentPiece.getValue(x, y) == Piece.piece) {
                				 grid[x+w-currentPiece.sizeOfPiece/2][y+h-currentPiece.sizeOfPiece/2] = currentPiece.getColor();
                				
                				 }
                			 }
                		 }return pIndex;
                	 
                	 }
                 }
    		   } 
           }
       
       	return pIndex;
	   }
   //medium difficulty: place random pieces
   public int mediumComputerPlacePiece(LinkedList<Piece> pieceList, boolean firstMover, 
		   String scoSetting, int pieceSize, Player player) throws IllegalMoveException{
	   int pIndex = 0;
       while (true) {
    	   Random random = new Random();
    	   int i = random.nextInt(player.pieces.size());
    	   int w = random.nextInt(20);
    	   int h = random.nextInt(20);
           if (true== isLegalMoveComputer(player.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, player.firstMove)){
          	 pIndex = i;
          	 int xLocation= w;
          	 int yLocation = h;
          	 Piece currentPiece = pieceList.get(i);
          	 //player get 5 bonus score if the last piece placed is the 1-square piece 
          	 if(scoSetting == "advancedScoring") {
          		 if(pieceSize == 1) {
          			 int count = 0;
          			 for (int x = 0; x < currentPiece.sizeOfPiece; x++){
          				 for (int y = 0; y < currentPiece.sizeOfPiece; y++){
          					 if (currentPiece.getValue(x, y) == Piece.piece) {
          						 count += 1;
          						 }
          					 }
          				 }
          			 if (count == 1) {
          				 player.bonus5Score();
          				 }
          			 }		   
          		 }   
          	 for (int x = 0; x < currentPiece.sizeOfPiece; x++)
          	 {
          		 for (int y = 0; y < currentPiece.sizeOfPiece; y++)
          		 {
          			 if (currentPiece.getValue(x, y) == Piece.piece) {
          				 grid[x+w-currentPiece.sizeOfPiece/2][y+h-currentPiece.sizeOfPiece/2] = currentPiece.getColor();
          				
          				 }
          			 }
          		 }return pIndex;
          	 
          	 }
       }
       
                 
    		   
           
	   } 
   //hard difficulty: place 5-square pieces first, then 4,3,2,1 
   public int hardComputerPlacePiece(LinkedList<Piece> pieceList, boolean firstMover, 
		   String scoSetting, int pieceSize, Player player) throws IllegalMoveException{
	   int pIndex = 0;

       for (int i = 0; i < player.pieces.size(); i++){
    	   for(int w = 0; w < 20; w++) {
    		   for(int h = 0; h <20; h++) {
            	  //available movements
                 if (true== isLegalMoveComputer(player.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, player.firstMove)){
                	 pIndex = i;
                	 int xLocation= w;
                	 int yLocation = h;
                	 Piece currentPiece = pieceList.get(i);
                	 //player get 5 bonus score if the last piece placed is the 1-square piece 
                	 if(scoSetting == "advancedScoring") {
                		 if(pieceSize == 1) {
                			 int count = 0;
                			 for (int x = 0; x < currentPiece.sizeOfPiece; x++){
                				 for (int y = 0; y < currentPiece.sizeOfPiece; y++){
                					 if (currentPiece.getValue(x, y) == Piece.piece) {
                						 count += 1;
                						 }
                					 }
                				 }
                			 if (count == 1) {
                				 player.bonus5Score();
                				 }
                			 }		   
                		 }   
                	 for (int x = 0; x < currentPiece.sizeOfPiece; x++)
                	 {
                		 for (int y = 0; y < currentPiece.sizeOfPiece; y++)
                		 {
                			 if (currentPiece.getValue(x, y) == Piece.piece) {
                				 grid[x+w-currentPiece.sizeOfPiece/2][y+h-currentPiece.sizeOfPiece/2] = currentPiece.getColor();
                				
                				 }
                			 }
                		 }return pIndex;
                	 
                	 }
                 }
    		   } 
           }
       
       	return pIndex;
	   }
   //placePiece method for the first time movement
   public void placePiece(Piece bp, int nx, int ny, boolean firstMover, String scoSetting, int pieceSize, int pieceIndex, Player player) throws IllegalMoveException{
      
	   isLegalMove(bp, nx, ny, firstMover);	 
	   //player get 5 bonus score if the last piece placed is the 1-square piece 
	   if(scoSetting == "advancedScoring") {
		   if(pieceSize == 1) {
			   int count = 0;
			   for (int x = 0; x < Piece.sizeOfPiece; x++){
			         for (int y = 0; y < Piece.sizeOfPiece; y++){
			            if (bp.getValue(x, y) == Piece.piece) {
			            	count += 1;
			            }
			         }
			      }
	           if (count == 1) {
	    		   player.bonus5Score();
	    		   }
	           }		   
	   }
	   //placePiece 
	   for (int x = 0; x < Piece.sizeOfPiece; x++){
         for (int y = 0; y < Piece.sizeOfPiece; y++){
            if (bp.getValue(x, y) == Piece.piece) {
            	grid[x + nx][y + ny] = bp.getColor();
            }
         }
      }
   }
   //placePiece method for the movement that is not the first time
   public void placePiece(Piece currentPiece, int nx, int ny, String scoSetting, int pieceSize, int pieceIndex, Player player) throws IllegalMoveException
   {
      placePiece(currentPiece, nx, ny, false, scoSetting, pieceSize, pieceIndex, player);
   }
   
   public Point getLocation(Point p, int num)
   {
      return new Point(p.x / (num / SIZEOFBOARD), p.y / (num / SIZEOFBOARD));
   }
   
   public void overlay(Piece currentPiece, int nx, int ny)
   {
      reset(pieceDisplay);
      for (int x = 0; x < Piece.sizeOfPiece; x++)
      {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {
            if (isInBoundary(x + nx - Piece.sizeOfPiece / 2, y + ny - Piece.sizeOfPiece / 2) && currentPiece.getValue(x, y) == Piece.piece)
            {
               pieceDisplay[x + nx - Piece.sizeOfPiece / 2][y + ny - Piece.sizeOfPiece / 2] = currentPiece.getColor();
            }
         }
      }
   }
   
   public BufferedImage render()
   {
      return render(DISPLAY_SIZE);
   }
   
   public BufferedImage render(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int gridSize = size / (SIZEOFBOARD);
      Graphics2D graph = (Graphics2D) image.getGraphics();
      
      for (int x = 0; x < SIZEOFBOARD; x++)
      {
         for (int y = 0; y < SIZEOFBOARD; y++)
         {
            graph.setColor(getColor(grid[x][y]));
            if (pieceDisplay[x][y] != NONE)
            {
               graph.setColor(blend(graph.getColor(), getColor(pieceDisplay[x][y]), 0.2f));
            }
            graph.fillRect(x * gridSize, y * gridSize, gridSize, gridSize);
            graph.setColor(COLOROFLINE);
            graph.drawRect(x * gridSize, y * gridSize, gridSize, gridSize);
            
            if (grid[x][y] == NONE)
            {
               boolean corner = false;
               Point p = new Point(x, y);
               if (getCorner(GREEN).equals(p))
               {
                  graph.setColor(getColor(GREEN));
                  corner = true;
               }
               else if (getCorner(BLUE).equals(p))
               {
                  graph.setColor(getColor(BLUE));
                  corner = true;
               }
               else if (getCorner(RED).equals(p))
               {
                  graph.setColor(getColor(RED));
                  corner = true;
               }
               else if (getCorner(YELLOW).equals(p))
               {
                  graph.setColor(getColor(YELLOW));
                  corner = true;
               }
               if (corner)
               {
                  graph.fillOval(x * gridSize + gridSize / 2 - gridSize / 6, y * gridSize + gridSize / 2 - gridSize / 6, gridSize / 3, gridSize / 3);
               }
            }
         }
      }
      return image;
   }
   
   //combine the color of the board and the color of the piece when placing the piece
   private Color blend(Color color1, Color color2, float m)
   {
      int a = (int)(color1.getRed() * m + color2.getRed() * (1 - m));
      int b = (int)(color1.getGreen() * m + color2.getGreen() * (1 - m));
      int c = (int)(color1.getBlue() * m + color2.getBlue() * (1 - m));
      return new Color(a, b, c);
   }
   
   public void resetDisplay()
   {
      reset(pieceDisplay);
   }
   
   private void reset(int[][] array)
   {
      for (int x = 0; x < SIZEOFBOARD; x++)
         for (int y = 0; y < SIZEOFBOARD; y++)
            array[x][y] = NONE;
   }
   
   private boolean isInBoundary(int x, int y)
   {
      return (x >= 0 && y >= 0 && x < SIZEOFBOARD && y < SIZEOFBOARD);
   }
   
   private Point getCorner(int color)
   {
      switch (color)
      {
         case BLUE: return new Point(SIZEOFBOARD - 1, 0);
         case YELLOW: return new Point(SIZEOFBOARD - 1, SIZEOFBOARD - 1);
         case GREEN: return new Point(0, 0);
         case RED: return new Point(0, SIZEOFBOARD - 1);
         default: throw new IllegalArgumentException();
      }
   }
   
   //color of 4 pieces
   public static Color getColor(int color)
   {
      switch (color)
      {
         case BLUE: {
             if (Blokus.getColourDefi()== true) {
            	return SelectColorMenu.getColor1();
             }
        	 return new Color(30, 144, 255);}
         
         case YELLOW: {
             if (Blokus.getColourDefi()== true) {
            	return SelectColorMenu.getColor2();
             }
        	 return Color.ORANGE;
         }
        	 
         case RED:{
             if (Blokus.getColourDefi()== true) {
            	return SelectColorMenu.getColor3();
             }
             return Color.RED;
         }
        	 
        	 
         case GREEN: {
             if (Blokus.getColourDefi()== true) {
            	return SelectColorMenu.getColor4();
             }
        	 return new Color(0,179,30);
         }
         default: return COLOROFBOARD;

         
      }
   }
   
   
   public static String getPieceColor(int color)
   {
      switch (color)
      {
         case BLUE: return "Blue";
         case YELLOW: return "Yellow";
         case RED: return "Red";
         case GREEN: return "Green";
         default: return "Unknown";
      }
   }
   
   public static class IllegalMoveException extends Exception
   {
      public IllegalMoveException()
      {
         super();
      }
      
      public IllegalMoveException(String message)
      {
         super(message);
      }
   }
   
   public int[][] getGrid() {
	   return this.grid;
   }
   
}
