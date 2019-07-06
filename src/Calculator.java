/**
 * @author Hephaest
 * @since  2019/07/02
 * JDK 1.8
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
/**
 * Calculator class is used to create GUI
 */
public class Calculator extends JFrame
{
  	// Create a text box.
	JTextField text = new JTextField();
	JPanel row2 = new JPanel();
	
	// Create buttons.
	String[][] buttons = {{"7","8","9","DEL","AC"},{"4","5","6","×","÷"},{"1","2","3","+","-"},{"0","(",")","Replay","="}};
	JButton[][]button = new JButton[4][5];
	
	/**
	 * The calculator interface is similar to Casio fx-82ES PLUS A.
	 */
	public Calculator()
	{
		super("CASIO");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// Set the size of the box.
		text.setPreferredSize(new Dimension(30, 40));
		text.setHorizontalAlignment(SwingConstants.TRAILING);
		text.setEditable(false);
		getContentPane().add(text, BorderLayout.NORTH);
		// Declare each button.
		add(row2, BorderLayout.CENTER);
		GridLayout layout2 = new GridLayout(4,5,5,5);
		row2.setLayout(layout2);
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[0].length; j++)
			{
				button[i][j] = new JButton(buttons[i][j]);
				row2.add(button[i][j]);
			}
		}
		add(row2);
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * This method is to ensure across operation system could have an ability to show window.
	 */
	private static void setLookAndFeel() 
	{
		try {
		    UIManager.setLookAndFeel(
			"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		    );
		} catch (Exception exc) {
		    // ignore error.
		}
	}
    }
	/**
	 * Main function.
	 */
	public static void main(String[] args) 
	{
		Calculator.setLookAndFeel();
		Calculator cl = new Calculator();
		cl.listener();
	}
	
	/**
	 * Event listener which gives responses to buttons.
	 */
	public void listener()
	{
		Listener l = new Listener(this);
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[0].length; j++)
			{
				button[i][j].addActionListener(l);
			}
		}
	}
}
