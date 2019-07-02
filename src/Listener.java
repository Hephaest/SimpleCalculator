/**
 * @author Hephaest
 * @since  2019/07/02
 * JDK 1.6
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * Listener 类
 * 用来将按钮输入的结果通过链表的方式一个字一个字存储在字符串里，然后调用另一类计算整个字符串，返回一个值
 */
public class Listener implements ActionListener
{
	private Calculator cl;
	private ArrayList<String> list=new ArrayList<String>();
	private ArrayList<String> his=new ArrayList<String>();//这个链表用来添加每一次得到的最终的结果
	private ArrayList<String> arr = new ArrayList<String>();//把his里的一整串字符分割成单个字符，再连接
	private String[] arrayStr = new String[] {};//储存单次的历史记录
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
		 * 如果点“=”，计算整个表达式的结果，如果是错误表达式，在文本框输入“Input Error!”
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
			 * 如果点击"×"，先把它转换为"*"
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
			 * 如果点击"÷"，把它转换为"/"
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
			 * 如果点击"DEL"，删除表达式里最后一个字符，每点一次删一个
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
			 * 如果点击"AC"，删除list链表，再删除之前先把表达式保留到his的链表里
			 */
			his.add(out);
			list.clear();
			output="";
			cl.text.setText(output);
		} else if(button.getText().equals("Replay")) {
			/**
			 * 如果点击"Replay"，在文本框里显示上一条表达式
			 */
			output=his.get(his.size()-1);
			cl.text.setText(output);
			arr.clear();
			//把上一条表达式分割成单个字符的字符数组
			char[] a=output.toCharArray();
			for(int i=0;i<a.length;i++)
			{
				arr.add(String.valueOf(a[i]));
			}
			his.remove(his.size()-1);
		} else {
			/**
			 * 其余按钮可以直接加入表达式
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
