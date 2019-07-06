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
