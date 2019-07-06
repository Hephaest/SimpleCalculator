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
