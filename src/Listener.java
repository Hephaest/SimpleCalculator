/**
 * @author Hephaest
 * @since  2018/04/19
 * JDK 1.6
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
 * Listener 类
 * @author phili2
 * 用来将按钮输入的结果通过链表的方式一个字一个字存储在字符串里，然后调用另一类计算整个字符串，返回一个值
 */
public class Listener implements ActionListener
{
	private Calculator cl;
	private ArrayList<String> list=new ArrayList<String>();
	private ArrayList<String> his=new ArrayList<String>();//这个链表用来添加每一次得到的最终的结果
	private ArrayList<String> arr = new ArrayList<String>();
	private String[] arrayStr = new String[] {};
	private String out="";
	private String output="";
    
	public Listener(Calculator cl)
    {
    	this.cl=cl;
    }
    
	public void actionPerformed(ActionEvent event)
	{
		JButton button = (JButton) event.getSource();
		if(button.getText().equals("="))
		{
			try
			{	
				function f = new function();
				double result=f.compute(output);
				cl.text.setText(Double.toString(result));
			}catch(Exception e)
			{
				cl.text.setText("Input Error!");
			}
			
		}
		else if(button.getText().equals("×"))
		{
			if(list.isEmpty())
			{
				arr.add("*");
				output+="*";
				out=output;
				cl.text.setText(output);
			}
			else
			{
				list.add("*");
				output+="*";
				out=output;
				cl.text.setText(output);
			}
		}
		else if(button.getText().equals("÷"))
		{
			if(list.isEmpty())
			{
				arr.add("/");
				output+="/";
				out=output;
				cl.text.setText(output);
			}
			else
			{
				list.add("/");
				output+="/";
				out=output;
				cl.text.setText(output);
			}
		}
		else if(button.getText().equals("DEL"))
		{
			if(list.isEmpty())
			{
				arr.remove(arr.size()-1);
		         output="";
	             for(int i=0;i<arr.size();i++)
	 			 {
	 				output+=arr.get(i);
	 			 }
	 			out=output;
	 			cl.text.setText(output);
			}
			else
			{
	             list.remove(list.size()-1);
	             String output="";
	             for(int i=0;i<list.size();i++)
	 			 {
	 				output+=list.get(i);
	 			 }
	 			out=output;
	 			cl.text.setText(output);
			}
		}
		else if(button.getText().equals("AC"))
		{
			his.add(out);
			list.clear();
			output="";
 			cl.text.setText(output);
		}
		else if(button.getText().equals("Replay"))
		{
			output=his.get(his.size()-1);
			cl.text.setText(output);
			arr.clear();
			char[] a=output.toCharArray();
			for(int i=0;i<a.length;i++)
			{
				arr.add(String.valueOf(a[i]));
			}
			his.remove(his.size()-1);
		}
		else
		{
			if(list.isEmpty())
			{
				arr.add(button.getText());
				output+=button.getText();
//				out=output;
				cl.text.setText(output);
			}
			else
			{
				list.add(button.getText());
				output+=button.getText();
//				out=output;
				cl.text.setText(output);
			}
		}
	}
}
