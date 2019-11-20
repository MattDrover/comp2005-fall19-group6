import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LoadMenu extends JFrame{

	private JLabel background;

	public LoadMenu() {
		
		setTitle("Load Menu");
		setSize(345, 450);
		
		makeGUI();
		
		pack();
		setDefaultLookAndFeelDecorated(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void makeGUI() 
	{
		
        //Create an Image icon and sets a JLabel background as the Image
		URL url = Blokus.class.getResource("resources/OtherMenuPic2.png");
        ImageIcon back = new ImageIcon(url);
        background = new JLabel(back);
        background.setLayout(new BorderLayout());
        
        getContentPane().add(background);
        
	}
}
