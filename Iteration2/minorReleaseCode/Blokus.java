import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Blokus extends JFrame implements java.io.Serializable
{
   //the number of players
   public static final int NUMOFCOLOR = 4;
   private static int numOfHumanPlayer;
   private static int numOfComputerPlayer;
   private Board board;
   private Player[] players;
   private int turn = -1;
   
   private int pieceIndex;
   private Point selected;
   //Panel
   private JPanel totalPanel;
   private JPanel picPanel;
   private JPanel leftPanel;
   private JPanel piecePanel;
   private JPanel boardPanel;
   private JPanel rightPanel;
  
   //Label
   private JLabel label;
   private JLabel playerStatLabel;
   private JLabel turnLabel;
   private JLabel scoreLabel;
   
   private ImageIcon boardImage;
   //Button 
   private JButton rotate;
   private JButton flip;
   private JButton hint;
   private JButton save;
   private JButton load;
   private JButton exit;
   
   public Blokus() 
   {
      
      board = new Board();
      //need to be updated at later iteration
      players = new Player[NUMOFCOLOR];
      //the playing order is blue, yellow, red, green
      players[0] = new Player(Board.BLUE);
      players[1] = new Player(Board.YELLOW);
      players[2] = new Player(Board.RED);
      players[3] = new Player(Board.GREEN);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      startGame();
      startNewTurn();
   }
   public Blokus(Board b) {
	      board = b;
	      players = new Player[NUMOFCOLOR];
	      players[0] = new Player(Board.BLUE);
	      players[1] = new Player(Board.YELLOW);
	      players[2] = new Player(Board.RED);
	      players[3] = new Player(Board.GREEN);
	      numOfHumanPlayer= 4;
	      numOfComputerPlayer = 0;
	      
	      if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==1)) {
	    	  players[1].setComputerPlayer();
	    	  players[3].setComputerPlayer();
	      }

	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      startGame();
	      startNewTurn();	   
   }
   public Blokus(int numHuman, int numComputer) 
   {
      
      board = new Board();
      players = new Player[NUMOFCOLOR];
      players[0] = new Player(Board.BLUE);
      players[1] = new Player(Board.YELLOW);
      players[2] = new Player(Board.RED);
      players[3] = new Player(Board.GREEN);
      numOfHumanPlayer= numHuman;
      numOfComputerPlayer = numComputer;
      
      if((numOfHumanPlayer == 1)&(numComputer ==1)) {
    	  players[1].setComputerPlayer();
    	  players[3].setComputerPlayer();
      }

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      startGame();
      startNewTurn();
   }
   public static class PieceLabel extends JLabel
   {  
      public int pieceIndex;
      
      public PieceLabel(int pieceIndex, Piece bp, int size)
      {
         super(new ImageIcon(bp.render(size)));
         this.pieceIndex = pieceIndex;
      }
   }
   
   public boolean checkAvailableMovement() {
	   int count = 0;
       for (int i = 0; i < players[turn].pieces.size(); i++)
       {
    	  for(int w = 0; w < 20; w++) {
    		  for(int h = 0; h <20; h++) {
    			  if (true== board.isLegalMoveComputer(players[turn].pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, players[turn].firstMove))
    			  {
    				  count++;
    			  }
    	  }
       } 
      
       }
       //System.out.println(count);
       if (count >=1) {
    	   return true;
       }
       else {
    	   players[turn].continuePlay = false;
    	   return false;
       }
       
  }
    private void startGame()
    {
    	class BoardListener implements MouseListener, MouseMotionListener
        {
    		public void mouseClicked(MouseEvent e)
            {  
               try
                 {
            	   //all pieces
            	   
                   board.placePiece(players[turn].pieces.get(pieceIndex), selected.x - Piece.sizeOfPiece / 2, selected.y - Piece.sizeOfPiece / 2, players[turn].firstMove);
                   drawBoard();
                   players[turn].pieces.remove(pieceIndex);
                   players[turn].firstMove = false;
                   //pieces remained
                   if (checkAvailableMovement()==false) {
                	   players[turn].continuePlay = false;
                   }
                   
                   startNewTurn();
                 }
               catch (Board.IllegalMoveException ex)
                  {
                     displayMessage(ex.getMessage(), "Error");
                  }
            }
            public void mouseMoved(MouseEvent e)
            {
               Point p = board.getLocation(e.getPoint(), Board.DISPLAY_SIZE);
               if (!p.equals(selected))
               {
                  selected = p;
                  board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
                  drawBoard();
               }
            }
            public void mouseExited(MouseEvent e)
            {
               selected = null;
               board.resetDisplay();
               drawBoard();
            }
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){} 
            public void mouseReleased(MouseEvent e){} 
            public void mouseDragged(MouseEvent e){}
         }
         
         class rotateListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	rotate();
            }
         }
         class flipListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	flipPiece();
            }
         }
         class saveListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	save();
            }
         }
         
         class loadListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	load();
            }
         }
         class exitListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	exit();
            }
         }         
         //top panel
         //picture of the game's logo on the top
         picPanel = new JPanel();
         URL url = Blokus.class.getResource("resources/blokusPic.png");
         ImageIcon blokusPic = new ImageIcon(url);
         JLabel picLabel = new JLabel(blokusPic);
         picPanel.add(picLabel);

         //left panel
         //all n-square pieces 
         piecePanel = new JPanel();
         piecePanel.setLayout(new BoxLayout(piecePanel, BoxLayout.PAGE_AXIS));
         JScrollPane scroll = new JScrollPane(piecePanel);
         scroll.getVerticalScrollBar().setUnitIncrement(Piece.defaultSize / 3);
         scroll.setPreferredSize(new Dimension(Piece.defaultSize +20, Board.DISPLAY_SIZE - 30));

         //contain piecePanel and rotate button
         leftPanel = new JPanel();
         leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
         leftPanel.add(scroll);
         //leftPanel.add(rotate);
         //leftPanel.add(flip);
         //middle panel
         //gamePanel display on the middle
         boardPanel = new JPanel();
         boardImage = new ImageIcon(board.render());
         label = new JLabel(boardImage);
         BoardListener bListener = new BoardListener();
         label.addMouseListener(bListener);
         label.addMouseMotionListener(bListener);
         boardPanel.add(label);
         
         //right panel
         //rightPanel contains information of the game, function 'hint','save',and 'exit'.
         rightPanel = new JPanel();
         rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.PAGE_AXIS));
         //infoLabel displayed on the rightPanel
         //infoLabel displayed based on the choices of different number of human players and computer players from previous menu 
         
         //need to be edited later
         turnLabel = new JLabel("");
         turnLabel.setFont(new Font("Serif", Font.PLAIN,22));
         scoreLabel = new JLabel("");
         
         rotate = new JButton("Rotate");
         rotate.setFont(new Font("Serif", Font.PLAIN,22));
         rotate.setPreferredSize(new Dimension(Piece.defaultSize, 30));
         rotate.addActionListener(new rotateListener());
         flip = new JButton("Flip");
         flip.setFont(new Font("Serif", Font.PLAIN,22));
         flip.setPreferredSize(new Dimension(Piece.defaultSize, 30));
         flip.addActionListener(new flipListener());
         
         hint = new JButton("Need a hint?");
         hint.setFont(new Font("Serif", Font.PLAIN,20));
         
         save = new JButton("Save");
         save.setFont(new Font("Serif", Font.PLAIN,20));
         save.addActionListener(new saveListener());
         
         load = new JButton("Load");
         load.setFont(new Font("Serif", Font.PLAIN,20));
         load.addActionListener(new loadListener());
         
         exit = new JButton("Exit");
         exit.setFont(new Font("Serif", Font.PLAIN,20));
         exit.addActionListener(new exitListener());
         
         rightPanel.add(rotate);
         rightPanel.add(flip);
         rightPanel.add(hint);
         rightPanel.add(save);
         rightPanel.add(load);
         rightPanel.add(exit);
         rightPanel.add(turnLabel);
         
         //pack all panel
         totalPanel = new JPanel();
         totalPanel.setLayout(new BorderLayout());
         totalPanel.add(picPanel,BorderLayout.NORTH);
         totalPanel.add(leftPanel,BorderLayout.WEST);
         totalPanel.add(boardPanel,BorderLayout.CENTER);
         totalPanel.add(rightPanel,BorderLayout.EAST);         
         getContentPane().add(totalPanel);
         
         pack();
         setVisible(true);
      }
      private void setBoard(Board n) {
    	  this.board = n;
      }
      
      private void setPlayers(Player[] p) {
    	  this.players = p;
      }
      
      private void rotate()
      {
         players[turn].pieces.get(pieceIndex).rotatePiece();
         drawBoard();
      }
      private void flipPiece()
      {
         players[turn].pieces.get(pieceIndex).flipPiece();
       
         drawBoard();
      }    
      private void save() {
          try {
              FileOutputStream boardfileOut = new FileOutputStream("board.ser");
              ObjectOutputStream out = new ObjectOutputStream(boardfileOut);
              out.writeObject(this.board);
              out.close();
              boardfileOut.close();

              FileOutputStream p1fileout = new FileOutputStream("players.ser");
              ObjectOutputStream p1 = new ObjectOutputStream(p1fileout);
              p1.writeObject(this.players);
              p1.close();
              p1fileout.close();
              
              FileOutputStream turnFile = new FileOutputStream("turn.ser");
              ObjectOutputStream turn = new ObjectOutputStream(turnFile);
              turn.writeObject(this.turn);
              turnFile.close();
              turn.close();    		
    	  } catch (Exception e) {
    		
    		e.printStackTrace();
    	  }
    	    
      }
      public void load() {
    	  //Board e = null;
    	  try {
    	         FileInputStream fileIn = new FileInputStream("board.ser");
    	         ObjectInputStream in = new ObjectInputStream(fileIn);
    	         Board e = (Board) in.readObject();
    	         in.close();
    	         fileIn.close(); 
    	         setBoard(e);
    	         drawBoard();

    	         FileInputStream a = new FileInputStream("players.ser");
    	         ObjectInputStream b = new ObjectInputStream(a);
    	         Player [] p1 = (Player []) b.readObject();
    	         setPlayers(p1);
    	         b.close();
    	         a.close(); 
    	         
    	         
    	         FileInputStream c = new FileInputStream("turn.ser");
    	         ObjectInputStream d = new ObjectInputStream(c);
    	         int turn = (int) d.readObject();
    	         setTurn(turn);
    	         c.close();
    	         d.close(); 
    	         drawBoard(); 
    	         piecePanel.removeAll();
    	         
    	         for (int i = 0; i < players[turn].pieces.size(); i++)
    	         {
    	            PieceLabel pLa = new PieceLabel(i, players[turn].pieces.get(i), Piece.defaultSize);
    	            pLa.addMouseListener(new PieceListener());
    	            pLa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    	            piecePanel.add(pLa);
    	         }
    	         pieceIndex = 0;
    	         drawBorder();
    	         piecePanel.repaint();
    	         
    	         pack();
    	         
    	  }
    	  
    	  catch(IOException i) {
    	         i.printStackTrace();
    	         return;
    	  } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
    		System.out.println("class not found");
			e1.printStackTrace();
			return;
		}
      }

      private void setTurn(int turnNum) {
		this.turn = turnNum;
		
	}
	private void exit() {
    	  System.exit(0);
      }
      private void drawBoard()
      {
         boardImage.setImage(board.render());
         label.repaint();
      }
      
      private void drawBorder()
      {
         JComponent piece = (JComponent) piecePanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
      }
      
      private void clearBorder()
      {
         JComponent piece = (JComponent) piecePanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
      }
      
      private void displayMessage(String message, String title)
      {
         JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
      }
      
      private class PieceListener implements MouseListener
      {
         public void mouseClicked(MouseEvent e)
         {
            
            PieceLabel currentPiece = (PieceLabel) e.getComponent();
            clearBorder();
            pieceIndex = currentPiece.pieceIndex;
            drawBorder();
         }
         public void mousePressed(MouseEvent e){}
         public void mouseReleased(MouseEvent e){}
         public void mouseEntered(MouseEvent e){}
         public void mouseExited(MouseEvent e){}
      }
      
      private void startNewTurn()
      {
         turn++;
         turn %= NUMOFCOLOR;

         if (isGameFinished()){gameOver();}       
         if (!players[turn].continuePlay)
         {
            startNewTurn();
            return;
         }
         piecePanel.removeAll();
         
         for (int i = 0; i < players[turn].pieces.size(); i++)
         {
            PieceLabel pLa = new PieceLabel(i, players[turn].pieces.get(i), Piece.defaultSize);
            pLa.addMouseListener(new PieceListener());
            pLa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            piecePanel.add(pLa);
         }
         pieceIndex = 0;
         drawBorder();
         piecePanel.repaint();
         
         pack();
      }
      
      private boolean isGameFinished()
      {
         for (int i = 0; i < NUMOFCOLOR; i++)
         {
            if (players[i].continuePlay) return false;
         }
         return true;
      }
      private int getPlayerColor(int index)
      {
         switch (index)
         {
            case 0: return Board.BLUE;
            case 1: return Board.YELLOW;
            case 2: return Board.RED;
            case 3: return Board.GREEN;
            default: return 0;
         }
      }
      //all players have no available movements, display scores of players and winner
      private void gameOver()
      {
         StringBuffer resultInfo = new StringBuffer();
         int maxScore = players[0].getScore(); 
         String winner ="";
         for (int i = 0; i < NUMOFCOLOR; i++)
         {
        	int temp = i+1; 
        	resultInfo.append("Player" + temp +" ");
            resultInfo.append(Board.getPieceColor(getPlayerColor(i)));
            resultInfo.append(": ");
            resultInfo.append(players[i].getScore());
            resultInfo.append("\n");
            if(players[i].getScore() > maxScore) {
            	maxScore = players[i].getScore();
            	winner = "Player"+temp + " "+Board.getPieceColor(getPlayerColor(i));
            }
            
         }
         resultInfo.append("Winner is" + winner);
         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
         System.exit(0);
      }
      

   
   //public static void main(String[] args) 
   //{
   //   Blokus b= new Blokus(4,0);
      
      
   //}

}
