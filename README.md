目录
=================
  * [Calculator Demo](#clculator-demo)
  * [A Simple Java Calculator](#a-simple-java-calculator)
      * [GUI](#gui)
      * [Event Listener](#event-listener)
      * [Expression Algorithm](#expression-algorithm)
      
# Calculator Demo

<p align="center"><img src ="images/calculator.gif"></p>

# A Simple Java Calculator
[![LICENSE](https://img.shields.io/cocoapods/l/AFNetworking.svg)](https://github.com/Hephaest/Simple-Java-Caculator/blob/master/LICENSE)
[![JDK](https://img.shields.io/badge/JDK-8u202%20-orange.svg)](https://www.oracle.com/technetwork/java/javase/8u202-relnotes-5209339.html)
[![Dependencies](https://img.shields.io/badge/Dependencies-up%20to%20date-green.svg)](https://github.com/Hephaest/Simple-Java-Caculator/tree/master/src)

English | [中文](README_CN.md)

Last updated on `2019/07/08`<br>
A simple calculator has following functionalities：
1. Results display box
2. Numbers from 0～9
3. Delete operation
4. Clear operation
5. Search history operation
6. Calculate final results
7. Parenthesis Priority Calculation

The following flow chart helps you easier to understand these functionalities:

<p align="center"><img src ="images/163725038-5ad8ae0824e2a_articlex.png"></p>

## GUI
The following code is written according to my design:
```java
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
```

## Event Listener
The code is shown as follows:
```java
/**
 * @author Hephaest
 * @since  2019/07/02
 * JDK 1.8
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;
/**
 * Listener class
 * The input result is stored in the arraylist of the string, and then is invoked by another class to calculate the final result.
 */
public class Listener implements ActionListener
{
	private Calculator cl;
	private ArrayList<String> list=new ArrayList<String>();
	private ArrayList<String> his=new ArrayList<String>();// This list is used to add the final result.
	private ArrayList<String> arr = new ArrayList<String>();// Split a whole string of characters in the list into a single character, then concatenation.
	private String[] arrayStr = new String[] {};// Store a single history.
	private String out = "";
	private String output = "";
    
	public Listener(Calculator cl)
    {
    	this.cl = cl;
    }
    
	public void actionPerformed(ActionEvent event)
	{
		JButton button = (JButton) event.getSource();
		
		/**
		 * click "=" then calculate the final result. If it is an error expression, type "Input Error!" in the text box.
		 */
		if(button.getText().equals("="))
		{
			try
			{	
				Function f = new Function();
				double result = f.compute(out);
				cl.text.setText(Double.toString(result));
			} catch(Exception e) {
				cl.text.setText("Input Error!");
			}
		} else if(button.getText().equals("×")) {
        	
		/**
    		 * click "×" then transform into "*".
    		 */
			if(list.isEmpty())
			{
				arr.add("*");
				output += "*";
				out = output;
				cl.text.setText(output);
			} else {
				list.add("*");
				output += "*";
				out = output;
				cl.text.setText(output);
			}
		} else if(button.getText().equals("÷")) {
        	    /**
		     * Click "÷" then transform into "/".
		     */
			if(list.isEmpty())
			{
				arr.add("/");
				output += "/";
				out = output;
				cl.text.setText(output);
			} else {
				list.add("/");
				output += "/";
				out = output;
				cl.text.setText(output);
			}
		} else if(button.getText().equals("DEL")) {
        	    /**
		     * Click "DEL" then delete the last character in the expression.
		     */
			if(list.isEmpty())
			{
				arr.remove(arr.size()-1);
		    		output = "";
	      			for(int i = 0; i < arr.size(); i++) output += arr.get(i);
	 			out = output;
	 			cl.text.setText(output);
			} else {
				list.remove(list.size()-1);
				String output = "";
				for(int i = 0; i < list.size(); i++) output+=list.get(i);
	 			out = output;
	 			cl.text.setText(output);
			}
		} else if(button.getText().equals("AC")) {
        	/**
    		 * Click "AC" then delete list, and then save the expression to the list.
    		 */
			his.add(out);
			list.clear();
			output="";
 			cl.text.setText(output);
		} else if(button.getText().equals("Replay")) {
        	/**
    		 * Click "Replay" then text box diplays the expression.
    		 */
			output=his.get(his.size()-1);
			cl.text.setText(output);
			arr.clear();
			
			// Split the previous expression into a single character array of characters.
			char[] a=output.toCharArray();
			for(int i=0;i<a.length;i++)
			{
				arr.add(String.valueOf(a[i]));
			}
			his.remove(his.size()-1);
		} else {
        	/**
    		 * Other buttons can be directly added into the expression.
    		 */
			if(list.isEmpty())
			{
				arr.add(button.getText());
				output+=button.getText();
				out=output;
				cl.text.setText(output);
			} else {
				list.add(button.getText());
				output+=button.getText();
				out=output;
				cl.text.setText(output);
			}
		}
	}
}
```
## Expression Algorithm

Need to **firstly convert the infix expression into a suffix and mark the multi-digit operators**, and then process the suffix expression.

```java
/**
 * @author Hephaest
 * @since  2018/07/13
 * JDK 1.8
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
public class function {
	private String[] str=new String[10];
	private int begin;
	public function(){}
	/**
	 * Infix expression is converted to a postfix expression.
	 * @param exp Infix expression.
	 * @return the correct final result.
	 */
	public double compute(String exp) 
	{
		char[] ch = exp.toCharArray();
		Stack <Character> stack = new Stack<>();
		String convertToPostfix = new String();
		int size = ch.length;
		begin = 0;
		for (int i = 0; i < size; i++) {
		  // the left parenthesis directly into the stack.
		  if(ch[i] == '(') stack.push(ch[i]);
		  else if(ch[i] == ')') {
			// The right parenthesis is popped (appended to the suffix expression) until the element on the stack is left parenthesis or 0
			char popValue = stack.pop();
			do 
			{
			convertToPostfix = convertToPostfix.concat(String.valueOf(popValue));
			popValue = stack.pop();
			}while(!stack.isEmpty() && popValue != '(');
		  } else if(checkOperator(ch[i])) {
    		  /*
    		   * If it is operator：
    		   * 1. Push it into the stack if it is empty stack.
    		   * 2. Check whether the operator has a higher priority than the top element of the stack.
    		   * 	Push directly into the stack if the answer is yes.
    		   *    Pop the top element of the stack (append to the suffix expression) and push the current operator into the stack.
    		   */
			if(stack.isEmpty()) stack.push(ch[i]);
			else {
				char popValue = stack.pop();
				while(checkPriority(popValue,ch[i]))
				{
				  convertToPostfix = convertToPostfix.concat(String.valueOf(popValue));
				  if(stack.isEmpty()) break;
				  popValue = stack.pop();
				}
				if(!checkPriority(popValue,ch[i])) stack.push(popValue);
				stack.push(ch[i]);
			}
		  } else if(checkDigital(ch[i])) {
    		  /*
    		   * Append to the suffix expression if it is a digit.
    		   * Operators with more than one number need to be checked:
    		   * 	1. Calculate the start and end positions of the operator.
    		   * 	2. Pass the number into the string array (global variables, the next step is needed).
    		   */
			  if(i + 1 < size && i - 1 >= 0)
			  {
				  if(checkDigital(ch[i - 1]) && !checkDigital(ch[i + 1]))
				  {
					  int end = i;
					  int j = end;
					  while(checkDigital(ch[j]))
					  {
						  j--;
					  }
					  j++;
					  List<String> elements = new LinkedList<>();
					  do
					  {
						  elements.add(String.valueOf(ch[j]));
						  j++;
					  } while(j <= end);
					  str[begin] = String.join("", elements);
					  System.out.println(str[begin]);
					  begin++; 
				  }
			  }
			  convertToPostfix=convertToPostfix.concat(String.valueOf(ch[i]));
		    }
		 }
		// After the first traverse, the remaining operators in the stack are popped out of the stack (append to the suffix expression).
		while(!stack.isEmpty())
		{
			char popValue = stack.pop();
			convertToPostfix = convertToPostfix.concat(String.valueOf(popValue));
		}
		System.out.println(convertToPostfix);
		return computeResult(convertToPostfix);
	}
	
  	/**
	 * Calculate the suffix expression.
	 * @param convertToPostfix String of postfix expressions.
	 * @return calculate the final result.
	 */
	public double computeResult(String convertToPostfix)
	{
		int[] index=new int[10];
		/*
		 * Determine if there is a multi-digit operator, and find the initial position of the suffix expression
		 * if the answer is yes.
		 */
		for(int i = 0;i < begin; i++)
		{
			index[i] = convertToPostfix.indexOf(str[i]);
			System.out.println(index[i]);
		}
		char[] ch = convertToPostfix.toCharArray();
		Stack <Double> stack = new Stack<>();
		double result = 0;
		for (int i = 0; i < ch.length; i++) {
			// If it is an operator, pop the two elements at the top of the stack, remember to FILO.
			if(checkOperator(ch[i]))
			{
				double num2=stack.pop();
				System.out.println("num2" + num2);
				System.out.print("\n");
				double num1=stack.pop();
				System.out.println("num1" + num1);
				System.out.print("\n");
				switch(ch[i])
				{
					case '*':
						result = num2 * num1;
						break;
					case '/':
						result = num1 / num2;
						break;
					case '+':
						result = num1 + num2;
						break;
					case '-':
						result = num1 - num2;
						break;
				}
				System.out.println(result);
				stack.push(result);
			} else {
      			/*
			 * For multi-bit operators, you need to concatenate a single character and put it on the stack as a double type.
			 * One-digit operators can be placed directly on the stack. Note that 48 (0 character data) is subtracted from characters to numbers.
			 */
				int stop = 0;
				for(int j = 0; j < begin; j++)
				{
					if(i == index[j])
					{
						int start=i;
						List<String> elements = new LinkedList<>();
						do
						{
							elements.add(String.valueOf(ch[i]));
							i++;
						} while(i < str[j].length() + start);
						i--;
						String test=String.join("", elements);
						stack.push(Double.valueOf(test));
						stop=1;
						break;
					}
				}
				if(stop == 0) stack.push((double)ch[i]-48);
			}
		}
		System.out.print("\n");
		System.out.print(result);
		return result;
	}
	
  	/**
	 * Check operator.
	 * @param c current character.
	 * @return boolean result.
	 */
	public boolean checkOperator(char c)
	{
		int result;
		switch(c)
		{
			case '+':
			case '-':
			case '*':
			case '/':
				result = 1;
				break;
			default:
				result = 0;
		}
		if(result == 1) return true;
		else return false;
	}
	
 	/**
	 * Check digits.
	 * @param c current character.
	 * @return boolean result.
	 */
	public boolean checkDigital(char c)
	{
		int num = c;
		num -= 48;
		if(num >= 0 && num <= 9) return true;
		else return false;
	}
	
 	/**
	 * Compare priority.
	 * @param popOne the top element of the stack.
	 * @param checkOne the pushed element.
	 * @return boolean result.
	 */
	public boolean checkPriority(char popOne,char checkOne)
	{
		if((popOne == '*' || popOne == '/') && (checkOne == '+' || checkOne == '-')) return true;
		else if(popOne == checkOne) return true;
		else return false;
	}
}

```
