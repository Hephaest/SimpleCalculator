/**
 * @author Hephaest
 * @since  2019/07/02
 * JDK 1.6
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Function {
	private String[] str=new String[10];
	private int begin;
	public Function(){}
	/**
	 * 中缀表达式转换成后缀表达式
	 * @param exp 在计算器上显示的文本 中缀表达式
	 * @return 正确的计算结果
	 */
	public double compute(String exp)
	{
		char[] ch = exp.toCharArray();
		Stack <Character> stack = new Stack<>();
		String convertToPostfix = new String();
		int size = ch.length;
		begin = 0;
		for (int i = 0; i < size; i++) {
			//遇到左括号直接入栈
			if(ch[i] == '(') stack.push(ch[i]);
			else if(ch[i] == ')') {
				//遇到右括号出栈(追加到后缀表达式), 直到出栈的元素为左括号或为0
				char popValue = stack.pop();
				do
				{
					convertToPostfix = convertToPostfix.concat(String.valueOf(popValue));
					popValue = stack.pop();
				}while(!stack.isEmpty() && popValue != '(');
			} else if(checkOperator(ch[i])) {
				/*
				 * 遇到运算符需要判断：
				 * 1.是否为空栈，是的话直接入栈
				 * 2.即将入栈的运算符是否比栈顶元素优先级高
				 *     是，直接入栈
				 *    否，栈顶元素出栈（追加到后缀表达式），当前运算符入栈
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
				 * 单个数字直接追加到后缀表达式
				 * 含有不止一个数字的操作符需要做记录：
				 *     1.计算该操作符的起始位置和终止位置
				 *     2.把数字传到字符串数组里（全局变量，下一步需要用到）
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
		//第一遍结束后把栈中剩下的操作符依次出栈（追加到后缀表达式）
		while(!stack.isEmpty())
		{
			char popValue = stack.pop();
			convertToPostfix = convertToPostfix.concat(String.valueOf(popValue));
		}
		System.out.println(convertToPostfix);
		return computeResult(convertToPostfix);
	}

	/**
	 * 计算后缀表达式
	 * @param convertToPostfix 后缀表达式的字符串
	 * @return 计算结果
	 */
	public double computeResult(String convertToPostfix)
	{
		int[] index=new int[10];
		/*
		 * 判断是否有多位数的操作符，有的话找到在后缀表达式的初始位置
		 * 如果没有的话就不会执行
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
			//如果是运算符，pop出栈顶的两个元素，记住先进后出
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
				 * 对于多位操作符，需要把单个字符连接起来然后作为一个双精度数放入栈中
				 * 一位数的操作符直接放入栈即可，注意从字符变成数字时要减去48(0的字符型数据)
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
	 * 判断是否是运算符
	 * @param c 当前字符
	 * @return 布尔型结果
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
	 * 判断是否是数字
	 * @param c 当前字符
	 * @return 布尔型结果
	 */
	public boolean checkDigital(char c)
	{
		int num = c;
		num -= 48;
		if(num >= 0 && num <= 9) return true;
		else return false;
	}

	/**
	 * 判断即将入栈的优先级是否更高
	 * @param popOne 栈顶元素
	 * @param checkOne 即将入栈元素
	 * @return 布尔型结果
	 */
	public boolean checkPriority(char popOne,char checkOne)
	{
		if((popOne == '*' || popOne == '/') && (checkOne == '+' || checkOne == '-')) return true;
		else if(popOne == checkOne) return true;
		else return false;
	}
}
