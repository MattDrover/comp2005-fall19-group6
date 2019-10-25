import javax.swing.*;

//import Blokus.BlokusPieceLabel;
//import Blokus.PieceListener;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;



public class Board implements java.io.Serializable
{

   
   public static final Color COLOROFBOARD = new Color(255,250,240);
   public static final Color COLOROFLINE = Color.BLACK;
   public static final String ERRORPROMPT= "Illegal Move!";
   
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
   //
   //public boolean isValidMove(Piece currentPiece, int nx, int ny) throws IllegalMoveException
   //{
   //   return isLegalMove(currentPiece, nx, ny, false);
   //}
   
   
   public int computerPlacePiece(Piece currentPiece, int nx, int ny, boolean firstMover) throws IllegalMoveException
   {
      if(isLegalMoveComputer(currentPiece, nx, ny, firstMover) == false) {
    	  
    	  return 0;
      }

      for (int x = 0; x < Piece.sizeOfPiece; x++)
      {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {
            if (currentPiece.getValue(x, y) == Piece.piece) grid[x + nx][y + ny] = currentPiece.getColor();
         }
      }
      return 1;
   }
   
   
   //placePiece method for the first time movement
   public void placePiece(Piece bp, int nx, int ny, boolean firstMover) throws IllegalMoveException
   {
      
	  isLegalMove(bp, nx, ny, firstMover);
      
      for (int x = 0; x < Piece.sizeOfPiece; x++)
      {
         for (int y = 0; y < Piece.sizeOfPiece; y++)
         {
            if (bp.getValue(x, y) == Piece.piece) {
            	grid[x + nx][y + ny] = bp.getColor();
            }
         }
      }

   }

   //placePiece method for the movement that is not the first time
   public void placePiece(Piece currentPiece, int nx, int ny) throws IllegalMoveException
   {
      placePiece(currentPiece, nx, ny, false);
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
         case BLUE: return new Color(153, 204, 255);
         case YELLOW: return Color.YELLOW;
         case RED: return new Color(255,102,102);
         case GREEN: return new Color(0,204,102);
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
