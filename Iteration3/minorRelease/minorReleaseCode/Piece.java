import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Piece implements java.io.Serializable
{
	private int[][] grid;
	private int color;
	public static final int sizeOfPiece = 7;
	
	//piece's square   
	public static final int piece = 3;
	//piece's adjoining area
	public static final int adjoining = 2;
	//piece's corner
	public static final int corner = 1;
	//blank square
	public static final int blank = 0;
   
	public static final int defaultSize = 130;
      
	public Piece(int[][] shape, int color)
	{
		grid = (int[][]) shape.clone();
		this.color = color;
	}
	
	//1 to 5 square pieces
	public static int[][][] getAllPieces()
	{  
		//21 pieces per color
		int[][][] squares = new int[21][sizeOfPiece][sizeOfPiece];
	    int i = 0;
	      /*the cursor pointer is always pointed to [3,3]
	       *squares[0,11] are 5-squares pieces
	       *squares[12,16] are 4-squares pieces
	       *squares[17,18] are 3-squares pieces
	       *squares[19] is 2-squares piece
	       *squares[20] is 1-squares piece
	       */
	      
	      //5-square pieces
	      /*00000
	      */
	    squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {1, 2, 2, 2, 2, 2, 1},
	          {2, 3, 3, 3, 3, 3, 2},
	          {1, 2, 2, 2, 2, 2, 1},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //5-square pieces 
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 1, 2, 1, 0, 0, 0},
	          {0, 2, 3, 2, 2, 2, 1},
	          {0, 2, 3, 3, 3, 3, 2},
	          {0, 1, 2, 2, 2, 2, 1},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	       //5-square pieces
	     squares[i++] = new int[][] {
	          {0, 0, 1, 2, 1, 0, 0},   
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 2, 3, 2, 1, 0},
	          {0, 0, 2, 3, 3, 2, 0},
	          {0, 0, 1, 2, 3, 2, 0},
	          {0, 0, 0, 1, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 1, 2, 3, 2, 2, 1},
	          {0, 2, 3, 3, 3, 3, 2},
	          {0, 1, 2, 2, 2, 2, 1},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 1, 2, 1, 0, 0}, 
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 2, 3, 2, 0},
	          {0, 0, 0, 1, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 1, 2, 1, 0, 0}, 
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 2, 3, 2, 3, 2, 0},
	          {0, 1, 2, 1, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 3, 3, 2, 0},
	          {0, 0, 1, 2, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 0, 1, 2, 1, 0}, 
	          {0, 0, 1, 2, 3, 2, 0},
	          {0, 1, 2, 3, 3, 2, 0},
	          {0, 2, 3, 3, 2, 1, 0},
	          {0, 1, 2, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	     //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 1, 2, 1, 0, 0},   
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	     //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 1, 2, 1, 0, 0},   
	          {0, 0, 2, 3, 2, 0, 0},   
	          {0, 0, 2, 3, 2, 2, 1},
	          {0, 0, 2, 3, 3, 3, 2},
	          {0, 0, 1, 2, 2, 2, 1},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	     //5-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 1, 2, 2, 1, 0},   
	          {0, 0, 2, 3, 3, 2, 0},
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 2, 3, 3, 2, 0, 0},
	          {0, 1, 2, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	     };
	       
	     //4-square pieces
	     squares[i++] = new int[][] { 
	          {0, 0, 1, 2, 1, 0, 0},    
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //4-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 1, 2, 2, 1, 0},
	          {0, 1, 2, 3, 3, 2, 0},
	          {0, 2, 3, 3, 2, 1, 0},
	          {0, 1, 2, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //4-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 1, 2, 2, 1, 0, 0},
	          {0, 2, 3, 3, 2, 0, 0},
	          {0, 2, 3, 3, 2, 0, 0},
	          {0, 1, 2, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //4-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 1, 2, 3, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //4-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0}, 
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 1, 2, 2, 2, 2, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 2, 3, 2, 0},
	          {0, 0, 0, 1, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //3-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 2, 3, 3, 3, 2, 0},
	          {0, 1, 2, 2, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //3-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},   
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 2, 3, 2, 1, 0},
	          {0, 0, 2, 3, 3, 2, 0},
	          {0, 0, 1, 2, 2, 1, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //2-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},    
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	     //1-square pieces
	       squares[i++] = new int[][] { 
	          {0, 0, 0, 0, 0, 0, 0},    
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 2, 3, 2, 0, 0},
	          {0, 0, 1, 2, 1, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0},
	          {0, 0, 0, 0, 0, 0, 0}
	       };
	       
	       return squares;
	    }

	public void rotatePiece()
	{
		int[][] temp = new int[sizeOfPiece][sizeOfPiece];
      
		for (int x = 0; x < sizeOfPiece; x++)
			for (int y = 0; y < sizeOfPiece; y++)
				temp[sizeOfPiece - y - 1][x] = grid[x][y];            
		grid = temp;
   }
	public void flipPiece()
	{
		int[][] temp = new int[sizeOfPiece][sizeOfPiece];
	      for (int x = 0; x < sizeOfPiece; x++)
	         for (int y = 0; y < sizeOfPiece; y++)
	            temp[sizeOfPiece - x - 1][y] = grid[x][y];
	      grid = temp;
	   }
    public int getValue(int x, int y)
   	{
	   return grid[x][y];
   	}
   
   	public int getColor()
   	{
   		return color;
   	}
   	
    //return the number of remaining squares
   	public int getRemainNumSquare()
   	{
   		int num = 0;
   		for (int y = 0; y < sizeOfPiece; y++)
   			for (int x = 0; x < sizeOfPiece; x++)
   				if (grid[x][y] == piece) num++;
   		return num;
   	}
   
   	public BufferedImage render(int size)
   	{
   		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
   		int cellSize = size / (sizeOfPiece);
   		Graphics2D g = (Graphics2D) image.getGraphics();
   		g.setColor(Color.WHITE);
   		g.fillRect(0, 0, size, size);
      
   		for (int x = 0; x < sizeOfPiece; x++)
   		{
   			for (int y = 0; y < sizeOfPiece; y++)
   			{
   				if (grid[x][y] == piece)
   				{
   					g.setColor(Board.getColor(color));
   					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
   					g.setColor(Color.BLACK);
   					g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
   				}
   			}
   		}
   		return image;
   	}
   
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      for (int y = 0; y < sizeOfPiece; y++)
      {
         for (int x = 0; x < sizeOfPiece; x++)
         {
            sb.append(grid[x][y]);
            sb.append(" ");
         }
         sb.append("\n");
      }
      return sb.toString();
   }
   
   

}
