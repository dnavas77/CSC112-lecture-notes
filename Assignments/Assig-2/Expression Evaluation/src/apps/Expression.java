package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;      
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
	/**
	 * Positions of opening brackets
	 */
	ArrayList<Integer> openingBracketIndex; 
    
	/**
	 * Positions of closing brackets
	 */
	ArrayList<Integer> closingBracketIndex; 

    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
        scalars = null;
        arrays = null;
        openingBracketIndex = null;
        closingBracketIndex = null;
    }

    /**
     * Matches parentheses and square brackets. Populates the openingBracketIndex and
     * closingBracketIndex array lists in such a way that closingBracketIndex[i] is
     * the position of the bracket in the expression that closes an opening bracket
     * at position openingBracketIndex[i]. For example, if the expression is:
     * <pre>
     *    (a+(b-c))*(d+A[4])
     * </pre>
     * then the method would return true, and the array lists would be set to:
     * <pre>
     *    openingBracketIndex: [0 3 10 14]
     *    closingBracketIndex: [8 7 17 16]
     * </pre>
     * 
     * See the FAQ in project description for more details.
     * 
     * @return True if brackets are matched correctly, false if not
     */
    public boolean isLegallyMatched() {
    	// COMPLETE THIS METHOD
    
    	if (expr == null) return false;

    	openingBracketIndex = new ArrayList<Integer>();	
    	closingBracketIndex = new ArrayList<Integer>();
    	int exprLength = expr.length();
    	int[] closingBracketHelper = new int[exprLength];
    	int lastOpeningIndex = 0;
    	int i = 0;
    	boolean indexZeroMatched = false;
    	
    	for (i = 0; i < exprLength; i++) {
    		if (expr.charAt(i) == '(' || expr.charAt(i) == '[') {
    			openingBracketIndex.add(i);
    			lastOpeningIndex = openingBracketIndex.size() - 1;
    		}
    		else if (expr.charAt(i) == ')' || expr.charAt(i) == ']') {
    			if (lastOpeningIndex == 0 && indexZeroMatched == false) {
    				closingBracketHelper[lastOpeningIndex] = i;
    				indexZeroMatched = true;
    			}
    			else if (lastOpeningIndex > 0 && closingBracketHelper[lastOpeningIndex] == 0) {
    				closingBracketHelper[lastOpeningIndex] = i;
    				lastOpeningIndex--;
    			}
    			else closingBracketHelper[i] = i;
    		}
		}
    
    	// Populate "closingBracketIndex" ArrayList with "closingBracketHelper" data.
    	for(int val: closingBracketHelper) {
    		if (val > 0) closingBracketIndex.add(val);
    	}
    	
    	if (openingBracketIndex.size() == closingBracketIndex.size()) return true;
    
    	// Brackets are not legally matched, return false
    	return false;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, the constructors for ScalarSymbol and ArraySymbol
     * will initialize values to zero and null, respectively.
     * The actual values will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    	// COMPLETE THIS METHOD
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
//        String[] tokens = expr.split("\\+|\\-|\\/|\\*|\\(|\\)|\\]");
  
    	String newExpr = expr.replaceAll(" ", "");
    	newExpr = newExpr.replaceAll("\\[", "[ ");
    	newExpr = newExpr.replaceAll("\\(|\\)|\\]|\\+|\\-|\\/|\\*", " ").trim();
        String[] tokens = newExpr.split(" ");

        String[] scalarsHelper = new String[expr.length()];
        String[] arraysHelper = new String[expr.length()];
        int i = 0, scalarsIndex = 0, arraysIndex = 0;
        boolean symbolExists = false;
         
        // trim whitespace from each token
        for(i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        
        // Store variables into scalars list and arrays into arrays list 
        for(i = 0; i < tokens.length; i++) {
            if(!tokens[i].matches("-?[0-9]+") && !tokens[i].equals("")) {
                if (tokens[i].indexOf('[') == -1) {// store variables
                	symbolExists = symbolExistsInArray(scalarsHelper, tokens[i]);
                	if (symbolExists == false) {
                		scalarsHelper[scalarsIndex] = tokens[i];
                		scalarsIndex++;
                	}
                }
                else
                {// store arrays
                	symbolExists = symbolExistsInArray(arraysHelper, tokens[i].substring(0, tokens[i].length()-1));
                	if (symbolExists == false) {
                		arraysHelper[arraysIndex] = tokens[i].substring(0, tokens[i].length()-1);
                    	arraysIndex++;
                	}
                 }
                  
            }
        }//end for
        
        /* populate ArrayLists (scalars, arrays) respectively */
        ScalarSymbol ss;
        for(String str: scalarsHelper) {
        	if (str != null) {
        		ss = new ScalarSymbol(str);
        		scalars.add(ss);
        	}
        }
        ArraySymbol ab;
        for(String str: arraysHelper) {
        	if (str != null) {
        		ab = new ArraySymbol(str);
        		arrays.add(ab);
        	}
        }

    }// end method 
   
    /**
     * Helper method by Danilo Navas.
     * @return true or false
     */
    private boolean symbolExistsInArray(String[] arr, String item) {
    	for (String s: arr) {
    		if (s != null) {
    			if(s.equals(item)) return true; 
    		}
    	}
    	return false;
    }
    
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions. (Note: you can use one or more private helper methods
     * to implement the recursion.)
     * @return Result of evaluation
     */
    public float evaluate() {
    	// COMPLETE THIS METHOD
    	return (float) evalHelper(0, expr.length()-1);
    }
    
    //////////////////////////
    private int evalHelper(int lindex, int rindex) {
    	int signPosition;
    	int lNumber = -1;//method to get number
    	boolean numberFlag = false;
    	
    	String firstToken = getFirstToken(lindex);
   		signPosition = findSignPosition(lindex + firstToken.length(), rindex);
   		
    	if(firstToken.trim().matches("-?[0-9]+"))
    	{
    		lNumber = Integer.parseInt(firstToken.trim());
    		numberFlag = true;
    	}
    	else if(isFirstTokenVariable(firstToken.trim()))
    	{
    		lNumber = getVariableValue(firstToken.trim());
    		numberFlag = true;
    	}
    	if(numberFlag)
    		if(signPosition != -1)
    		{    			    		
	    		switch(expr.charAt(signPosition))
	    		{ //+-*/
	    			case '+':
	    				return lNumber + evalHelper(signPosition+1,rindex);
	    			case '-':
	    				return lNumber - evalHelper(signPosition+1,rindex);
	    			case '*':
	    				return lNumber * evalHelper(signPosition+1,rindex);
	    			case '/':
	    				return lNumber / evalHelper(signPosition+1,rindex);
		    	}
    		} else
    		{
    			return lNumber;
    		}
  
    	else  if(expr.charAt(lindex) == '(')
    	{
    		int rbIndex = findClosingBracket(lindex); //right bracket index
    		
    		if(signPosition != -1)
    		{
	    		switch(expr.charAt(signPosition))
	    		{ //+-*/
				case '+':
					return evalHelper(lindex+1,rbIndex-1) + evalHelper(signPosition+1,rindex-1);
				case '-':
					return evalHelper(lindex+1,rbIndex-1) - evalHelper(signPosition+1,rindex-1);
				case '*':
					return evalHelper(lindex+1,rbIndex-1) * evalHelper(signPosition+1,rindex-1);
				case '/':
					return evalHelper(lindex+1,rbIndex-1) / evalHelper(signPosition+1,rindex-1);
			
    		
	    		}
    		}
    		else
    		{
    			return evalHelper(lindex+1,rindex-1);
    		}
    	}
    	else // is an array
    	{
    		int leftBracketIndex = lindex + firstToken.length(),
    			rightBracketIndex = findClosingBracket(leftBracketIndex);
    		
    		if(signPosition != -1)
    		{
    			switch(expr.charAt(signPosition))
	    		{ //+-*/
	    		case '+':
					return arrayValue(firstToken.trim(),evalHelper(leftBracketIndex+1,rightBracketIndex-1)) + evalHelper(signPosition+1,rindex-1);
				case '-':
					return arrayValue(firstToken.trim(),evalHelper(leftBracketIndex+1,rightBracketIndex-1)) - evalHelper(signPosition+1,rindex-1);
				case '*':
					return arrayValue(firstToken.trim(),evalHelper(leftBracketIndex+1,rightBracketIndex-1)) * evalHelper(signPosition+1,rindex-1);
				case '/':
					return arrayValue(firstToken.trim(),evalHelper(leftBracketIndex+1,rightBracketIndex-1)) / evalHelper(signPosition+1,rindex-1);
	    		}
    		}
    		else
    		{
    			return arrayValue(firstToken.trim(),evalHelper(leftBracketIndex+1,rightBracketIndex-1));
    		}
    	}
    	return 0;
    }
    		 

    ////////////////////////////
    
    private String getFirstToken(int lindex) {
    	String token = "";
    	
    	for (int i = lindex; i < this.expr.length(); i++)
    	{
    		String t = "" + this.expr.charAt(i);
    		
    		if(t.matches("[ a-zA-Z0-9]+"))
    		{
    			token += t;
    		}
    		else break;
		}
    	
    	return token;
    }

    ////////////////////////
    private boolean isFirstTokenVariable(String tok)
    {
    	for (int i = 0; i < scalars.size(); i++) {
    		if (scalars.get(i).name.equals(tok))
    		{
    			return true;
    		}
		}
    	
    	return false;
    }
    
    private int getVariableValue(String tok)
    {	
    	for (int i = 0; i < scalars.size(); i++) {
    		if (scalars.get(i).name.equals(tok))
    		{
    			return scalars.get(i).value;
    		}
		}
    	
    	return 0;
    }
    
    private int findSignPosition(int index, int rindex)
    {
    	for (int i = index; i < rindex; i++)
    	{
    		if (this.expr.charAt(i) == '+' || this.expr.charAt(i) == '-' || this.expr.charAt(i) == '*' || this.expr.charAt(i) == '/')
			{
				return i;
			}
		}
    	return -1;
    }
    
    private int findClosingBracket(int lindex)
    {
    	for (int i = 0; i < openingBracketIndex.size(); i++)
    	{
			if (openingBracketIndex.get(i) == lindex)
			{
				return closingBracketIndex.get(i);
			}
		}
    	
    	return -1;
    }
    
    private int arrayValue(String name, int index)
    {
    	int value = 0;

    	for (int i = 0; i < arrays.size(); i++) {
			if(arrays.get(i).name.equals(name))
			{
				value = arrays.get(i).values[index];
			}
		}
    	
    	return value;
    }
    
   
    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    	for (ArraySymbol as: arrays) {
    		System.out.println(as);
    	}
    }

}
