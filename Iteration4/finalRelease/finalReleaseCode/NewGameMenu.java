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

public class NewGameMenu extends JFrame {
	private JButton p1, p2, p3, p4, easy, medium, hard, start, exit;
	private JLabel background, numPlayer, difficulty;
	private JPanel panelPlayer, panelDifficulty, panelStart, bigPanel;
	private GridBagConstraints gbc;
	private JTextField info;
	private int numHuman, numComp;
	private String diff;
	
	public NewGameMenu() 
	{
		
		super("New Game in progress...");
		setSize(345, 450);
		
		makeGUI();
		
		pack();
		setDefaultLookAndFeelDecorated(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//if(numplayer set && difficulty set)
		//{
			//info.setText("Press Start to Begin. Have Fun!");
		//}
		
		//should p1 exist ? because you can't play blokus with one player. Minimum of two and at least one human
		
		p1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("1 Player selected");
				numHuman = 1;
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				new NumComPlayer(1);
				}
			});
		p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("2 Players selected");
				numHuman = 2;
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				new NumComPlayer(2);
				
			}
			});
		
		p3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("3 Players selected");
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				numHuman = 3;
				new NumComPlayer(3);
			}
			});
		
		p4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("4 Players selected");
				//String numHumStr = JOptionPane.showInputDialog("Please specify number of Human Players");
				//if(Integer.parseInt(numHumStr) < ) validation..
				//numHuman = Integer.parseInt(numHumStr);
				numHuman = 4;
				numComp = 0;
				//the number of computer player is 0 when the num of humanPlayer is 4
				
				
			}
			});
		
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("Easy Difficulty selected for Computer Players");
				//code here
				diff = "easy";
				
			}
			});
		
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("Medium Difficulty selected for Computer Players");
				//code here
				diff = "medium";
				
			}
			});
		
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info.setText("Hard Difficulty selected for Computer Players");
				//code here
				diff = "hard";
			}
			});
		
		
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int compTemp = NumComPlayer.getNumComp(); 
				new Blokus(numHuman, compTemp, SettingsMenu.scoreSystem, SettingsMenu.hint, SettingsMenu.colorDef, diff);
				setVisible(false);
			}
			});
		
		
		
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			});
	}
	
	public void makeGUI() 
	{
		URL url = Blokus.class.getResource("resources/OtherMenuPic2.png");
        ImageIcon back = new ImageIcon(url);
        background = new JLabel(back);
        background.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        
        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.setOpaque(false);
		
		numPlayer = new JLabel("Num of HumPlayers:");
		numPlayer.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		numPlayer.setForeground(Color.WHITE);
		
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
		
		p4 = new JButton("4");
		p4.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		p4.setBackground(Color.WHITE);
		p4.setForeground(Color.BLACK);
		
		panelPlayer = new JPanel();
		panelPlayer.setLayout(new FlowLayout());
		panelPlayer.setOpaque(false);
		panelPlayer.add(numPlayer);
		panelPlayer.add(p1);
		panelPlayer.add(p2);
		panelPlayer.add(p3);
		panelPlayer.add(p4);
		
		bigPanel.add(panelPlayer, BorderLayout.NORTH);
		
		difficulty = new JLabel("Difficulty :");
		difficulty.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		difficulty.setForeground(Color.WHITE);
		
		easy = new JButton("Easy");
		easy.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
		easy.setBackground(Color.WHITE);
		easy.setForeground(Color.BLACK);
		
		medium = new JButton("Medium");
		medium.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
		medium.setBackground(Color.WHITE);
		medium.setForeground(Color.BLACK);
		
		hard = new JButton("Hard");
		hard.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
		hard.setBackground(Color.WHITE);
		hard.setForeground(Color.BLACK);
		
		panelDifficulty = new JPanel();
		panelDifficulty.setLayout(new FlowLayout());
		panelDifficulty.setOpaque(false);
		panelDifficulty.add(difficulty);
		panelDifficulty.add(easy);
		panelDifficulty.add(medium);
		panelDifficulty.add(hard);
		
		bigPanel.add(panelDifficulty, BorderLayout.CENTER);
		
		
		
		start = new JButton("Start");
		start.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		start.setBackground(Color.WHITE);
		start.setForeground(Color.BLACK);
		

		exit = new JButton("Exit");
		exit.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		
		panelStart = new JPanel();
		panelStart.setLayout(new FlowLayout());
		panelStart.setOpaque(false);
		panelStart.add(start);
		panelStart.add(exit);
		
		bigPanel.add(panelStart, BorderLayout.SOUTH);
		
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
	
}
