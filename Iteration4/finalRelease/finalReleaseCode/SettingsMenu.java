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

public class SettingsMenu extends JFrame {

	private JLabel background;
	private JPanel colorPanel, hintPanel, scorePanel, bigPanel;
	private JLabel colorLabel, hintLabel, scoreLabel;
	private JButton colorYes, colorNo, hintYes, hintNo, scoreBasic, scoreAdvance, exit;
	private JTextField info;
	
	private GridBagConstraints gbc;
	
	static boolean colorDef;
	static boolean hint;
	static String scoreSystem;
	
	
	public SettingsMenu() 
	{
		super("Settings Menu");
		setSize(345,450);
		
		makeGUI();
		pack();
		setDefaultLookAndFeelDecorated(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		colorYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Color deficiency feature Activated", "Color Deficiency", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				colorDef = true;
				new SelectColorMenu();
				
			}
			});
		
		colorNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Color deficiency feature Desactivated", "Color Deficiency", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				colorDef = false;
				
			}
			});
		
		hintYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hint feature Activated", "Hints", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				hint = true;
				
			}
			});
		
		hintNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hint feature Desactivated", "Hints", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				hint = false;
				
			}
			});
		
		scoreBasic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Scoring system set to Basic", "Scoring System", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				scoreSystem = "basicScoring";
				
			}
			});
		
		scoreAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Scoring system set to Advance", "Scoring System", JOptionPane.INFORMATION_MESSAGE, null);
				//code here
				scoreSystem = "advancedScoring";
				
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
        
        
        colorLabel = new JLabel("Any color deficiencies ?");
        colorLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        colorLabel.setForeground(Color.WHITE);
        
        colorYes = new JButton("Yes");
        colorYes.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        colorYes.setBackground(Color.WHITE);
		colorYes.setForeground(Color.BLACK);
        
        colorNo = new JButton("No");
        colorNo.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        colorNo.setBackground(Color.WHITE);
		colorNo.setForeground(Color.BLACK);
        
        colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout());
        colorPanel.setOpaque(false);
        colorPanel.add(colorLabel);
        colorPanel.add(colorYes);
        colorPanel.add(colorNo);
        
        hintLabel = new JLabel("Need hints during gameplay ?");
        hintLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        hintLabel.setForeground(Color.WHITE);
        
        hintYes = new JButton("Yes");
        hintYes.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        hintYes.setBackground(Color.WHITE);
		hintYes.setForeground(Color.BLACK);
        
        hintNo = new JButton("No");
        hintNo.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        hintNo.setBackground(Color.WHITE);
		hintNo.setForeground(Color.BLACK);
        
        

        hintPanel = new JPanel();
        hintPanel.setLayout(new FlowLayout());
        hintPanel.setOpaque(false);
        hintPanel.add(hintLabel);
        hintPanel.add(hintYes);
        hintPanel.add(hintNo);
        
        scoreLabel = new JLabel("Scoring System :");
        scoreLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        scoreLabel.setForeground(Color.WHITE);
        
        scoreBasic = new JButton("Basic");
        scoreBasic.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        scoreBasic.setBackground(Color.WHITE);
        scoreBasic.setForeground(Color.BLACK);
		
        scoreAdvance = new JButton("Advance");
        scoreAdvance.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        scoreAdvance.setBackground(Color.WHITE);
        scoreAdvance.setForeground(Color.BLACK);
		
        exit = new JButton("Exit");
        exit.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setOpaque(false);
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreBasic);
		scorePanel.add(scoreAdvance);
		
		
        
        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.setOpaque(false);
        
        bigPanel.add(colorPanel, BorderLayout.NORTH);
        bigPanel.add(hintPanel, BorderLayout.CENTER);
        bigPanel.add(scorePanel, BorderLayout.SOUTH);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        background.add(bigPanel, gbc);
        
        info = new JTextField("", 20);
		info.setEditable(false);
		info.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 6;
		gbc.gridheight = 6;
		
		gbc.fill = GridBagConstraints.BOTH;
		background.add(exit, gbc);

    
        getContentPane().add(background);
	}

	public static boolean getShouldAdd() {
		
		return hint;
	}

}
