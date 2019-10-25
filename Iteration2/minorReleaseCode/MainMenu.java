import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JFrame{
    JFrame frame;
    

	JLabel askNumOfPlayers;
	JLabel askDiff;
	JLabel hints;
	JLabel colourDef;
	
	JPanel numPanel;
	JPanel diffPanel;
	JPanel hintPanel;
	JPanel colourPanel;
	JPanel functionPanel;
	
    JButton player1;
    JButton player2;
    JButton player3;
    JButton player4;
    
    JButton easy;
    JButton medium;
    JButton hard;
    JButton hintsYes;
    JButton hintsNo;
    
    JButton colourYes;
    JButton colourNo;
    
	JButton startBtn;
	JButton loadBtn;
	JButton exitBtn;

	public MainMenu(){
		
		askNumOfPlayers=new JLabel("Number of the Players:");
		askDiff= new JLabel("Difficulty:");
		hints = new JLabel("Need Hints?");
		colourDef = new JLabel("Do you have colour deficiencies?");
		
		player1 = new JButton("1");
		player2 = new JButton("2");
		player3 = new JButton("3");
		player4 = new JButton("4");
		
		hintsYes = new JButton("Yes");
		hintsNo = new JButton("No");
		easy = new JButton("Easy");
		medium = new JButton("Medium");
		hard = new JButton("Hard");
		
		colourYes = new JButton("Yes");
		colourNo = new JButton("No");

		
		startBtn= new JButton("Start");
		loadBtn = new JButton("Load");
		exitBtn = new JButton("Exit");

		frame = new JFrame("Blokus");
		frame.setSize(650,400);
		frame.setLayout(new GridLayout(5,1));
		Container c = frame.getContentPane();
		
		//getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		//getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
		
		//Panel
		numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout());
		numPanel.add(askNumOfPlayers);
		numPanel.add(player1);
		numPanel.add(player2);
		numPanel.add(player3);
		numPanel.add(player4);
		
		diffPanel = new JPanel();
		diffPanel.setLayout(new FlowLayout());
		diffPanel.add(askDiff);
		diffPanel.add(easy);
		diffPanel.add(medium);
		diffPanel.add(hard);
        
		hintPanel = new JPanel();
		hintPanel.setLayout(new FlowLayout());
		hintPanel.add(hints);
		hintPanel.add(hintsYes);
		hintPanel.add(hintsNo);

		colourPanel = new JPanel();
		colourPanel.setLayout(new FlowLayout());
		colourPanel.add(colourDef);
		colourPanel.add(colourYes);
		colourPanel.add(colourYes);
        
		functionPanel = new JPanel();
		functionPanel.setLayout(new FlowLayout());
		functionPanel.add(startBtn);
		functionPanel.add(loadBtn);
		functionPanel.add(exitBtn);
		
		c.add(numPanel);
		c.add(diffPanel);
		c.add(hintPanel);
		c.add(colourPanel);
		c.add(functionPanel);
		pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setResizable(true);
		frame.setVisible(true);
		
		
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Blokus(4,0);
				//System.exit(0);
			}
			});
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Blokus loadGame = new Blokus(4,0);
				loadGame.load();
				//System.exit(0);
			}
			});        
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});


}

	public static void main(String[] args){
		
		new MainMenu();
	
	}

}
