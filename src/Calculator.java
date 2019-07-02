/**
 * @author Hephaest
 * @since  2019/07/02
 * JDK 1.6
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
 * Calculator类用来创造GUI
 */
public class Calculator extends JFrame
{
	//新建文本框
	JTextField text = new JTextField();
	// set up row 2
	JPanel row2 = new JPanel();
	//创建按钮们
	String[][] buttons = {{"7","8","9","DEL","AC"},{"4","5","6","×","÷"},{"1","2","3","+","-"},{"0","(",")","Replay","="}};
	JButton[][]button = new JButton[4][5];

	/**
	 * 这个计算机的界面我模拟的是卡西欧fx-82ES PLUS A
	 * 但是仅有其中的部分功能
	 */
	public Calculator()
	{
		super("CASIO");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		//设置文本框的尺寸、位置以及禁止键盘输入
		text.setPreferredSize(new Dimension(30, 40));
		text.setHorizontalAlignment(SwingConstants.TRAILING);
		text.setEditable(false);
		getContentPane().add(text, BorderLayout.NORTH);
		//声明每一个按钮代表的意义
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

	private static void setLookAndFeel()
	{
		//这条使跨操作系统也能看到计算机的GUI
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
			);
		} catch (Exception exc) {
			// ignore error
		}
	}

	public static void main(String[] args)
	{
		Calculator.setLookAndFeel();
		Calculator cl = new Calculator();
		cl.listener();
	}

	/**
	 * 事件监听器，一旦按下按钮就要根据操作历史进行相应的反应
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
