import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumComPlayer extends JFrame{
	private JButton p0, p1, p2, p3, exit;
	private JLabel background, numPlayer;
	private JPanel topPanel, playerPanel, bigPanel, exitPanel;
	private GridBagConstraints gbc;
	private JTextField info;
	private static int numComp;
	private int numHumanPlayer;
		
	public NumComPlayer(int numHuman) {
		
		super("New Game in progress...");
		setSize(345, 450);
		numHumanPlayer = numHuman;
		makeGUI();
		
		pack();
		setDefaultLookAndFeelDecorated(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("0 Computer Player selected");
				numComp = 0;
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				
				
			}
			});
		
		
		p1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("1 Computer Players selected");
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				numComp = 1;
				
			}
			});
		
		
		p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("2 Computer Players selected");
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				numComp = 2;
				
			}
			});
		
	
		p3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("3 Computer Players selected");
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				numComp = 3;
				
			}
			});		
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			});
	}		
	
	public void makeGUI() {
		URL url = Blokus.class.getResource("resources/OtherMenuPic2.png");
        ImageIcon back = new ImageIcon(url);
        background = new JLabel(back);
        background.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        
        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.setOpaque(false);
		
		numPlayer = new JLabel("Number of Computer Players :");
		numPlayer.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		numPlayer.setForeground(Color.WHITE);
		
		p0 = new JButton("0");
		p0.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		p0.setBackground(Color.WHITE);
		p0.setForeground(Color.BLACK);
		
		p1 = new JButton("1");
		p1.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		p1.setBackground(Color.WHITE);
		p1.setForeground(Color.BLACK);
		
		p2 = new JButton("2");
		p2.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		p2.setBackground(Color.WHITE);
		p2.setForeground(Color.BLACK);
		
		p3 = new JButton("3");
		p3.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		p3.setBackground(Color.WHITE);
		p3.setForeground(Color.BLACK);

		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.setOpaque(false);
		topPanel.add(numPlayer);
		
		
		playerPanel = new JPanel();
		playerPanel.setLayout(new FlowLayout());
		playerPanel.setOpaque(false);
		//playerPanel.add(numPlayer);
		if(numHumanPlayer ==2| numHumanPlayer ==3) {
			topPanel.add(p0);
		}
		if(numHumanPlayer == 1 | numHumanPlayer ==2| numHumanPlayer ==3) {
			topPanel.add(p1);
		}
		if(numHumanPlayer == 1 | numHumanPlayer ==2) {
			topPanel.add(p2);
		}
		if(numHumanPlayer ==1) {
			topPanel.add(p3);
		}
		
		
		bigPanel.add(topPanel, BorderLayout.NORTH);
		//bigPanel.add(playerPanel, BorderLayout.CENTER);


		exit = new JButton("Exit");
		exit.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout());
		exitPanel.setOpaque(false);
		exitPanel.add(exit);		
		bigPanel.add(exitPanel, BorderLayout.SOUTH);     
		gbc.gridx = 0;
		gbc.gridy = 1;
		background.add(bigPanel, gbc);
		
		info = new JTextField("", 20);
		info.setEditable(false);
		info.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 4;
		
		gbc.fill = GridBagConstraints.BOTH;
		background.add(info, gbc);
		
		getContentPane().add(background);

	}
	public static int getNumComp(){
		return numComp;
	}
	
}
