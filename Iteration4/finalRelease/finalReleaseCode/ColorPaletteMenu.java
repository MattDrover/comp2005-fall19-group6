import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPaletteMenu extends JFrame {
    private JColorChooser jcc = null;
    private JLabel label = null;
    private JButton submit;
    private int colorNum;
    public ColorPaletteMenu() {
        initializeUI();
    }
    public ColorPaletteMenu(int x) {
    	colorNum = x;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        jcc = new JColorChooser();
        jcc.getSelectionModel().addChangeListener(new ColorSelection());
        getContentPane().add(jcc, BorderLayout.PAGE_START);

        label = new JLabel("Selected Font Color", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 100));
        
        submit = new JButton("Submit");

        getContentPane().add(label, BorderLayout.CENTER);
        getContentPane().add(submit,BorderLayout.SOUTH);
        setVisible(true);
        this.pack();
        
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(colorNum == 1) {
					SelectColorMenu.setColor1(jcc.getColor());
				}
				if(colorNum == 2){
					SelectColorMenu.setColor2(jcc.getColor());
				}
				if(colorNum == 3){
					SelectColorMenu.setColor3(jcc.getColor());
					
				}
				if(colorNum == 4){
					SelectColorMenu.setColor4(jcc.getColor());	
				}
				setVisible(false);
			}
			});
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ColorPaletteMenu().setVisible(true);
            }
        });
    }

    /**
     * A ChangeListener implementation for listening the color
     * selection of the JColorChooser component.
     */
    class ColorSelection implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            Color color = jcc.getColor();
            label.setForeground(color);
        }
    }
    
    
}