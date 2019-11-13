import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	private JButton start, load, settings, exit; 
	private JLabel background , sign;
	private JPanel panelButtons;
	private GridBagConstraints gbc; 
	
	public MainMenu() 
	{
		
		super("Welcome To Blokus!");
		setSize(345,450);
		
		getContentPane().setLayout(new BorderLayout());
		
		makeGUI();
		
		//House Keeping and Behaviour
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Listeners for each button
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					new NewGameMenu();
					//setVisible(false);
					
			}
			});
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Blokus().load();
				setVisible(false);
				return;
			}
			}); 
		
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SettingsMenu();
			}
			}); 
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			});
		
	}
	
	//Method to draw and place components
	public void makeGUI() 
	{
		
        //Create an Image icon and sets a JLabel background as the Image
		URL url = Blokus.class.getResource("resources/MainMenuPic.png");
        ImageIcon back = new ImageIcon(url);
        background = new JLabel(back);
        background.setLayout(new BorderLayout());
        
        sign = new JLabel("Group 6");
        sign.setForeground(Color.BLACK);
        
        //New Font(Works on windows, need to check linux and mac..)
        sign.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        

		
        //Panel for Buttons and Custom Size and Layout with GridBagLayout
		panelButtons = new JPanel();
		panelButtons.setLayout(new GridBagLayout());
		panelButtons.setOpaque(false);
		gbc = new GridBagConstraints();
		
		
		//New Game Button
		start = new JButton("New Game");
		start.setBackground(Color.WHITE);
		start.setForeground(Color.BLACK);

		
		start.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelButtons.add(start, gbc);
		
		
		//Load Game Button
		load = new JButton("Load Game");
		load.setBackground(Color.WHITE);
		load.setForeground(Color.BLACK);

		
		load.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelButtons.add(load, gbc);
		
		
		//Settings Button
		settings = new JButton("Settings");
		settings.setBackground(Color.WHITE);
		settings.setForeground(Color.BLACK);
	
		settings.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelButtons.add(settings, gbc);
		
		
		//Exit Button
		exit = new JButton("Exit");
		exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		
		exit.setFont(new Font("Berlin Sans FB", Font.BOLD, 17));
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelButtons.add(exit, gbc);
		
		

		//Adding Button Panel in JLabel with background at the back and centered
		background.add(panelButtons, BorderLayout.CENTER);
		background.add(sign, BorderLayout.SOUTH);
		getContentPane().add(background);
		pack();
		setVisible(true);
		
	}
	

	public static void main(String [] args) 
	{
		new MainMenu();
	}
}
