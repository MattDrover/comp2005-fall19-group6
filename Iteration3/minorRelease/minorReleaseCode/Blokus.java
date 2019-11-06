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

public class Blokus extends JFrame implements java.io.Serializable{

	public static final int NUMOFCOLOR = 4;
	private static int numOfHumanPlayer;
	private static int numOfComputerPlayer;
	private String scoreSetting;
	private String difficulty;
	private boolean hints;
	private boolean colourDefi;

	private Board board;
	private Player[] players;
	private int turn = -1;
	//used when three players
	private static int sharedNum = 3;	
	private int altTurn = 0;
	//
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
	private JButton surrender;
	
	public Blokus() {		
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
	public Blokus(int numHuman, int numComputer, String scoSetting, boolean hintOption, boolean colorDef, String diff) {
		
		board = new Board();
		numOfHumanPlayer= numHuman;
		numOfComputerPlayer = numComputer;
		scoreSetting = scoSetting;
		hints = hintOption;
		colourDefi = colorDef;
		difficulty = diff;
		players = new Player[NUMOFCOLOR];
		players[0] = new Player(Board.BLUE);
		players[1] = new Player(Board.YELLOW);
		players[2] = new Player(Board.RED);
		players[3] = new Player(Board.GREEN);
        
		if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==1)) {
			//[0]blue [3]red is humanPlayer; [1]yellow [4] green is compPlayer;
			players[1].setComputerPlayer();
			players[3].setComputerPlayer();
		}
		//3 players
		if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==2)) {
			players[1].setComputerPlayer();
			players[2].setComputerPlayer();
		}
		if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==3)) {
			players[1].setComputerPlayer();
			players[2].setComputerPlayer();
			players[3].setComputerPlayer();
		}
		//3 players
		if((numOfHumanPlayer == 2)&(numOfComputerPlayer ==1)) {
			players[2].setComputerPlayer();
		}
		if((numOfHumanPlayer == 2)&(numOfComputerPlayer ==2)) {
			players[2].setComputerPlayer();
			players[3].setComputerPlayer();
		}

		if((numOfHumanPlayer == 3)&(numOfComputerPlayer ==1)) {
			players[3].setComputerPlayer();
		}		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startGame();
		startNewTurn();
		}
	public static class PieceLabel extends JLabel{  
		public int pieceIndex;	
		public PieceLabel(int pieceIndex, Piece bp, int size){
		   super(new ImageIcon(bp.render(size)));
		   this.pieceIndex = pieceIndex;
		   }
		}
    //return if the piece has available movement left
	//can be used to give hint later
	public boolean checkAvailableMovement() {
		int count = 0;
			for (int i = 0; i < players[turn].pieces.size(); i++){
			  for(int w = 0; w < 20; w++) {
				  for(int h = 0; h <20; h++) {
					  if (true== board.isLegalMoveComputer(players[turn].pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, players[turn].firstMove)){
						  count++;
						  }
					  }
				  } 
			  }
        //System.out.println(count);
		if (count >=1) {return true;}
		else {
			players[turn].continuePlay = false;
			return false;
			}    
		}
	//argument: players[turn]
	private void getHint(Player p) {
		if(hints == false) {
			StringBuffer hintInfo = new StringBuffer();
			hintInfo.append("Hint option disabled.");
			JOptionPane.showMessageDialog(this, hintInfo.toString(), "Hint!", JOptionPane.INFORMATION_MESSAGE );
			JOptionPane.getRootFrame().dispose();  
			return;
		}
		
			for (int i = 0; i < p.pieces.size(); i++){
				for(int w = 0; w < 20; w++) {
					for(int h = 0; h <20; h++) {
						if (true== board.isLegalMoveComputer(p.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, p.firstMove)){
							StringBuffer hintInfo = new StringBuffer();
							String hintResult = board.getLocation(p.pieces.get(i), w - Piece.sizeOfPiece / 2, h - Piece.sizeOfPiece / 2, p.firstMove);
							hintInfo.append("The "+ (i+1) +"th piece on the panel can be placed at "+ hintResult);
							hintInfo.append("\n");
							hintInfo.append("( Upper-left corner is (0,0), lower-right corner is(19,19) )");
							JOptionPane.showMessageDialog(this, hintInfo.toString(), "Hint!", JOptionPane.INFORMATION_MESSAGE );
						
							JOptionPane.getRootFrame().dispose();  
							return;
					  }
				  }
		  }
		}
		 StringBuffer hintInfo = new StringBuffer();
		 String hintResult = "";
		 hintInfo.append("No available movement");
		 hintInfo.append("\n");
         JOptionPane.showMessageDialog(this, hintInfo.toString(), "Hint!", JOptionPane.INFORMATION_MESSAGE );
         JOptionPane.getRootFrame().dispose(); 
	}
	private void startGame(){
		class BoardListener implements MouseListener, MouseMotionListener
		{
			public void mouseClicked(MouseEvent e)
            {  
				try
                 {
            	   //all pieces
                   board.placePiece(players[turn].pieces.get(pieceIndex), 
                		   selected.x - Piece.sizeOfPiece / 2, 
                		   selected.y - Piece.sizeOfPiece / 2, 
                		   players[turn].firstMove, 
                		   scoreSetting,
                		   players[turn].getPiecesSize(), pieceIndex, players[turn]);
                   drawBoard();
                   players[turn].pieces.remove(pieceIndex);
                   players[turn].firstMove = false;
                   //pieces remained
                   if (checkAvailableMovement()==false) {
                	   players[turn].continuePlay = false;
                   }
                   if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==2)){
                	   
                   }
                   if((numOfHumanPlayer == 2)&(numOfComputerPlayer ==1)){

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
         class surrenderListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
               players[turn].continuePlay = false;
               startNewTurn();
            }
         }
         class hintListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
            	getHint(players[turn]);
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
         //rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.PAGE_AXIS));
         rightPanel.setLayout(new GridBagLayout());
         GridBagConstraints cons = new GridBagConstraints(); 
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
         hint.addActionListener(new hintListener());
         save = new JButton("Save");
         save.setFont(new Font("Serif", Font.PLAIN,20));
         save.addActionListener(new saveListener());
         
         load = new JButton("Load");
         load.setFont(new Font("Serif", Font.PLAIN,20));
         load.addActionListener(new loadListener());
         
         exit = new JButton("Exit");
         exit.setFont(new Font("Serif", Font.PLAIN,20));
         exit.addActionListener(new exitListener());
         
         //surrender = new JButton("Surrender");
         //surrender.setFont(new Font("Serif", Font.PLAIN,20));
         //surrender.addActionListener(new surrenderListener());
         cons.fill = GridBagConstraints.HORIZONTAL; 
         cons.weighty = 0.5;
         cons.gridx = 0;
         cons.gridy = 0;
         rightPanel.add(rotate,cons);
         
         cons.gridx = 0;
         cons.gridy = 1;
         rightPanel.add(flip,cons);
         cons.gridx = 0;
         cons.gridy = 2;         
         rightPanel.add(hint,cons);
         cons.gridx = 0;
         cons.gridy = 3;  
         //rightPanel.add(surrender,cons);
         cons.gridx = 0;
         cons.gridy = 3;  
         rightPanel.add(save,cons);
         cons.gridx = 0;
         cons.gridy = 4;  
         rightPanel.add(load,cons);
         cons.gridx = 0;
         cons.gridy = 5;  
         rightPanel.add(exit,cons);
         cons.gridx = 0;
         cons.gridy = 6;  
         rightPanel.add(turnLabel,cons);
         
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
              turn.close();
              turnFile.close(); 
              
              FileOutputStream scoreFile = new FileOutputStream("scoreSetting.ser");
              ObjectOutputStream score = new ObjectOutputStream(scoreFile);
              score.writeObject(this.scoreSetting);
              score.close();
              scoreFile.close(); 
              
              FileOutputStream altTurnFile = new FileOutputStream("altTurn.ser");
              ObjectOutputStream at = new ObjectOutputStream(altTurnFile);
              at.writeObject(this.altTurn);
              at.close();
              altTurnFile.close(); 
              
              FileOutputStream diffFile = new FileOutputStream("difficulty.ser");
              ObjectOutputStream diff = new ObjectOutputStream(diffFile);
              diff.writeObject(this.difficulty);
              diff.close();
              diffFile.close(); 
              
              FileOutputStream hintsFile = new FileOutputStream("hints.ser");
              ObjectOutputStream hf = new ObjectOutputStream(hintsFile);
              hf.writeObject(this.hints);
              hf.close();
              hintsFile.close(); 
              
              FileOutputStream colourDFile = new FileOutputStream("colourDefi.ser");
              ObjectOutputStream cd = new ObjectOutputStream(colourDFile);
              cd.writeObject(this.colourDefi);
              cd.close();
              colourDFile.close(); 
    	  } catch (Exception e) {
    		
    		e.printStackTrace();
    	  }
    	    
      }
      public void load() {
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
    	         
    	         FileInputStream scoreFileInput = new FileInputStream("scoreSetting.ser");
    	         ObjectInputStream scoreObjectInput = new ObjectInputStream(scoreFileInput);
    	         String tempScoreSetting = (String) scoreObjectInput.readObject();
    	         setScoreSetting(tempScoreSetting);
    	         scoreObjectInput.close();
    	         scoreFileInput.close();  
    	         
    	         FileInputStream atFileInput = new FileInputStream("turn.ser");
    	         ObjectInputStream atObjectInput = new ObjectInputStream(atFileInput);
    	         int atTurn = (int) atObjectInput.readObject();
    	         setAltTurn(atTurn);
    	         atObjectInput.close();
    	         atFileInput.close();     	         
    	             	         
    	         pack();
    	         
    	  }
    	  
    	  catch(IOException i) {
    	         i.printStackTrace();
    	         return;
    	  } catch (ClassNotFoundException e1) {
    		
			e1.printStackTrace();
			return;
		}
      }

      private void setAltTurn(int atTurn) {
		this.altTurn = atTurn;
		
	}
      private void setScoreSetting(String tempScoreSetting) {
    	  this.scoreSetting = tempScoreSetting;
    	  }
      private void setTurn(int turnNum) {
		this.turn = turnNum;
		}
      private void exit() {
    	  setVisible(false);
    	  //System.exit(0);
    	  }
      private void drawBoard(){
         boardImage.setImage(board.render());
         label.repaint();
         }
      
      private void drawBorder(){
         JComponent piece = (JComponent) piecePanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
         }
      
      private void clearBorder(){
         JComponent piece = (JComponent) piecePanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         }
      
      private void displayMessage(String message, String title){
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
      
      private void startNewTurn(){
         turn++;
         turn %= NUMOFCOLOR;

         if (isGameFinished()){gameOver();}       
         if (!players[turn].continuePlay){
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

         if (players[turn].isComputerPlayer() == true) {
             if((numOfHumanPlayer == 1)&(numOfComputerPlayer ==2)){
            	 if(turn ==2) {
             		altTurn += 1;
             		if(altTurn % sharedNum == 1) {
             			players[3].setHumanPlayer();
             		}
             		else{
             			players[3].setComputerPlayer();
              	}
            		 
            	 }
             }
             if((numOfHumanPlayer == 2)&(numOfComputerPlayer ==1)){
            	if(turn != 3) {
            		altTurn += 1;
            		if(altTurn % sharedNum == 0) {
            			players[3].setComputerPlayer();
            		}
            		else{
             		players[3].setHumanPlayer();
             	}
             }
             }
        	 try {
				pieceIndex =board.computerPlacePiece(players[turn].pieces, players[turn].firstMove, scoreSetting, players[turn].getPiecesSize(), players[turn]);
                drawBoard();
                players[turn].pieces.remove(pieceIndex);
                players[turn].firstMove = false;
                //pieces remained
                if (checkAvailableMovement()==false) {
             	   players[turn].continuePlay = false;
                }
                startNewTurn();
			} catch (Board.IllegalMoveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         

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
      private void gameOver(){
    	  if(scoreSetting == "basicScoring") {
    		  int blueScore = players[0].getBasicScore();
    		  int yellowScore = players[1].getBasicScore();
    		  int redScore = players[2].getBasicScore();
    		  int greenScore = players[3].getBasicScore();
    		  int blueRed = blueScore + redScore;
    		  int yelloGreen = yellowScore +greenScore;
    		  //
              if(numOfHumanPlayer + numOfComputerPlayer == 2) {
        		 StringBuffer resultInfo = new StringBuffer();
     	         String winner ="";
     	         resultInfo.append("Player(Blue & red) - suqares remianed: " + blueRed);
     	         resultInfo.append("\n");
     	         resultInfo.append("Player(Yellow & green) - suqares remianed: " + yelloGreen);
     	         resultInfo.append("\n");
     	         if(blueRed < yelloGreen) {winner = "Player(Blue & red) ";}
     	         else {winner = "Player(Yellow & green)";}
     	         resultInfo.append("Winner is " + winner);
     	         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
     	         System.exit(0); 
              }
              if(numOfHumanPlayer + numOfComputerPlayer == 3) {
            	  StringBuffer resultInfo = new StringBuffer();
            	  int minNumSquare = 100;
            	  String winner ="";
            	  for (int i = 0; i < NUMOFCOLOR-1; i++)
            	  {   
            		  int currentNumSquare = players[i].getBasicScore(); 
            		  int temp = i + 1; 
            		  resultInfo.append("Player" + temp +" ");
            		  resultInfo.append(Board.getPieceColor(getPlayerColor(i)));
            		  resultInfo.append(" "+"suqares remianed");
            		  resultInfo.append(": ");
            		  resultInfo.append(currentNumSquare);
            		  resultInfo.append("\n");
            		  if(currentNumSquare < minNumSquare) {
            			  minNumSquare = currentNumSquare;
            			  winner = "Player"+ temp + " "+ Board.getPieceColor(getPlayerColor(i));
    	            }
    	            
    	         }
    	         resultInfo.append("Winner is " + winner);
    	         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
    	         System.exit(0); 
              }
              //(numOfHumanPlayer + numOfComputerPlayer == 4)
              else {
            	  StringBuffer resultInfo = new StringBuffer();
            	  int minNumSquare = 100;
            	  String winner ="";
            	  for (int i = 0; i < NUMOFCOLOR; i++)
            	  {   
            		  int currentNumSquare = players[i].getBasicScore(); 
            		  int temp = i + 1; 
            		  resultInfo.append("Player" + temp +" ");
            		  resultInfo.append(Board.getPieceColor(getPlayerColor(i)));
            		  resultInfo.append(" "+"suqares remianed");
            		  resultInfo.append(": ");
            		  resultInfo.append(currentNumSquare);
            		  resultInfo.append("\n");
            		  if(currentNumSquare < minNumSquare) {
            			  minNumSquare = currentNumSquare;
            			  winner = "Player"+ temp + " "+ Board.getPieceColor(getPlayerColor(i));
    	            }
    	            
    	         }
    	         resultInfo.append("Winner is " + winner);
    	         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
    	         System.exit(0); 
              }
    	  }
    	  //advanced scoring
    	  else {
    		  players[0].calculateAdvancedScore();
    		  players[1].calculateAdvancedScore();
    		  players[2].calculateAdvancedScore();
    		  players[3].calculateAdvancedScore();
    		  int blueScore = players[0].getAdvancedScore();
    		  int yellowScore = players[1].getAdvancedScore();
    		  int redScore = players[2].getAdvancedScore();
    		  int greenScore = players[3].getAdvancedScore();
    		  
              if(numOfHumanPlayer + numOfComputerPlayer == 2) {
            	  int blueRed = blueScore + redScore;
            	  int yelloGreen = yellowScore +greenScore;
            	  StringBuffer resultInfo = new StringBuffer();
            	  String winner ="";
            	  resultInfo.append("Player(Blue & red) - scores:" + blueRed);
            	  resultInfo.append("\n");
            	  resultInfo.append("Player(Yellow & green) - scores:" + yelloGreen);
            	  resultInfo.append("\n");
            	  if(blueRed > yelloGreen) {winner = "Player(Blue & red) ";}
            	  else {winner = "Player(Yellow & green)";}
            	  resultInfo.append("Winner is " + winner);
            	  JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
            	  System.exit(0); 
              }
              if(numOfHumanPlayer + numOfComputerPlayer == 3) {
      	         StringBuffer resultInfo = new StringBuffer();
      	         int maxScore = 0;
      	         String winner ="";
      	         for (int i = 0; i < NUMOFCOLOR-1; i++)
      	         {   
      	             int currentScore = players[i].getAdvancedScore(); 
      	        	 resultInfo.append("Player"+ " ");
      	        	 resultInfo.append(Board.getPieceColor(getPlayerColor(i)));
      	        	 resultInfo.append(": ");
      	        	 resultInfo.append(currentScore);
      	        	 resultInfo.append("\n");
      	        	 if(currentScore > maxScore) {
      	            	maxScore = currentScore;
      	            	winner = "Player "+ Board.getPieceColor(getPlayerColor(i));
      	            }
      	            
      	         }
      	         resultInfo.append("Winner is " + winner);
      	         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
      	         System.exit(0);             	  
              }
              //(numOfHumanPlayer + numOfComputerPlayer == 4)
              else {
     	         StringBuffer resultInfo = new StringBuffer();
     	         int maxScore = 0;
     	         String winner ="";
     	         for (int i = 0; i < NUMOFCOLOR; i++)
     	         {   
     	             int currentScore = players[i].getAdvancedScore(); 
     	        	 resultInfo.append("Player"+ " ");
     	        	 resultInfo.append(Board.getPieceColor(getPlayerColor(i)));
     	        	 resultInfo.append(": ");
     	        	 resultInfo.append(currentScore);
     	        	 resultInfo.append("\n");
     	        	 if(currentScore > maxScore) {
     	            	maxScore = currentScore;
     	            	winner = "Player "+ Board.getPieceColor(getPlayerColor(i));
     	            }
     	            
     	         }
     	         resultInfo.append("Winner is " + winner);
     	         JOptionPane.showMessageDialog(this, resultInfo.toString(), "Game Result", JOptionPane.INFORMATION_MESSAGE );
     	         System.exit(0); 
              }
   		  
    	  }

      }
      
}
