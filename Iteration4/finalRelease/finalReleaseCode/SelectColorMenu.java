import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelectColorMenu extends JFrame {

	private JLabel background;
	private JPanel lbtPanel,firstPanel, secondPanel, thirdPanel, fourthPanel, bigPanel;
	private JLabel lbt, first, second, third, fourth;
	private JButton firstBtn, colorNo, secondBtn, fourthBtn,hintNo, thirdBtn, scoreAdvance, exit;
	private JTextField info;
	
	private GridBagConstraints gbc;
	
	static boolean colorDef;
	static boolean hint;
	static String scoreSystem;
	
	private static Color color1;
	private static Color color2;
	private static Color color3;
	private static Color color4;
	
	public SelectColorMenu() 
	{
		super("Select Color");
		setSize(345,450);
		
		makeGUI();
		pack();
		setDefaultLookAndFeelDecorated(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorPaletteMenu(1);
				
				
			}
			});
		

		secondBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ColorPaletteMenu(2);
				
				
			}
			});
		

		thirdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ColorPaletteMenu(3);
				
			}
			});
		fourthBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ColorPaletteMenu(4);
				
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
        
        lbt = new JLabel("Please select the 4 colors of the Blokus pieces");
        lbt.setFont(new Font("Berlin Sans FB", Font.PLAIN, 17));
        lbt.setForeground(Color.WHITE);
        first = new JLabel("First: ");
        first.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        first.setForeground(Color.WHITE);
        
        firstBtn = new JButton("Select");
        firstBtn.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        firstBtn.setBackground(Color.WHITE);
		firstBtn.setForeground(Color.BLACK);
        
        colorNo = new JButton("No");
        colorNo.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        colorNo.setBackground(Color.WHITE);
		colorNo.setForeground(Color.BLACK);
		
        lbtPanel = new JPanel();
        lbtPanel.setLayout(new FlowLayout());
        lbtPanel.setOpaque(false);
        lbtPanel.add(lbt);
     
		
		
        firstPanel = new JPanel();
        firstPanel.setLayout(new FlowLayout());
        firstPanel.setOpaque(false);
        firstPanel.add(first);
        firstPanel.add(firstBtn);

        
        second = new JLabel("Second:");
        second.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        second.setForeground(Color.WHITE);
        
        secondBtn = new JButton("Select");
        secondBtn.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        secondBtn.setBackground(Color.WHITE);
		secondBtn.setForeground(Color.BLACK);
        
        hintNo = new JButton("No");
        hintNo.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        hintNo.setBackground(Color.WHITE);
		hintNo.setForeground(Color.BLACK);
        
        

        secondPanel = new JPanel();
        secondPanel.setLayout(new FlowLayout());
        secondPanel.setOpaque(false);
        secondPanel.add(second);
        secondPanel.add(secondBtn);

        
        third = new JLabel("Third:");
        third.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        third.setForeground(Color.WHITE);
        
        thirdBtn = new JButton("Select");
        thirdBtn.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        thirdBtn.setBackground(Color.WHITE);
        thirdBtn.setForeground(Color.BLACK);
        
        fourth = new JLabel("Fourth:");
        fourth.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
        fourth.setForeground(Color.WHITE);
        
        fourthBtn = new JButton("Select");
        fourthBtn.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        fourthBtn.setBackground(Color.WHITE);
        fourthBtn.setForeground(Color.BLACK);
        
        scoreAdvance = new JButton("Advance");
        scoreAdvance.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        scoreAdvance.setBackground(Color.WHITE);
        scoreAdvance.setForeground(Color.BLACK);
		
        exit = new JButton("Exit");
        exit.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
        exit.setBackground(Color.WHITE);
		exit.setForeground(Color.BLACK);
		
		thirdPanel = new JPanel();
		thirdPanel.setLayout(new FlowLayout());
		thirdPanel.setOpaque(false);
		thirdPanel.add(third);
		thirdPanel.add(thirdBtn);
	

		fourthPanel = new JPanel();
		fourthPanel.setLayout(new FlowLayout());
		fourthPanel.setOpaque(false);
		fourthPanel.add(fourth);
		fourthPanel.add(fourthBtn);


        bigPanel = new JPanel();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        bigPanel.setOpaque(false);
        bigPanel.add(lbtPanel);
        bigPanel.add(firstPanel);
        bigPanel.add(secondPanel);
        bigPanel.add(thirdPanel);
        bigPanel.add(fourthPanel);
        
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

	public static Color getColor1() {
		return color1;
	}

	public static Color getColor2() {
		return color2;
	}
	public static Color getColor3() {
		return color3;
	}
	public static Color getColor4() {
		return color4;
	}
	public static void setColor1(Color c) {
		color1 = c;
	}
	public static void setColor2(Color c) {
		color2 = c;
	}
	public static void setColor3(Color c) {
		color3 = c;
	}
	public static void setColor4(Color c) {
		color4 = c;
	}

}
