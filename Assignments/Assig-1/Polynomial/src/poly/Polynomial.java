package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Polynomial total = new Polynomial();
		Node tmp = poly;
		Node tmp2 = p.poly;

		while (tmp2 != null || tmp != null) {
			Node newNode = null;
			float newCoeff = 0;

			if (tmp == null) {
				newNode = new Node(tmp2.term.coeff, tmp2.term.degree, null);
				tmp2 = tmp2.next;
			}
			else if(tmp2 == null) {
				newNode = new Node(tmp.term.coeff, tmp.term.degree, null);
				tmp = tmp.next;
			}
			else if (tmp.term.degree == tmp2.term.degree) {
				newCoeff = tmp.term.coeff + tmp2.term.coeff; 
				if (newCoeff != 0) {
					newNode = new Node(newCoeff, tmp.term.degree, null);
				}
				tmp2 = tmp2.next;
				tmp = tmp.next;
			}
			else if (tmp.term.degree > tmp2.term.degree) {
				newNode = new Node(tmp2.term.coeff, tmp2.term.degree, null);
				tmp2 = tmp2.next;
			}
			else if (tmp.term.degree < tmp2.term.degree) {
				newNode = new Node(tmp.term.coeff, tmp.term.degree, null);
				tmp = tmp.next;
			}
			if (total.poly == null) total.poly = newNode;
			else {
				for (Node n = total.poly; n != null; n = n.next) {
					if (n.next == null) {
						n.next = newNode;
						break;
					}
				}
			}
		}
		return total;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Polynomial product = new Polynomial();
		
		for (Node tmp = poly; tmp != null; tmp = tmp.next) {
			Polynomial y = new Polynomial();
			Node newNode = null;
			float tmpCoeff = 0;
			int tmpDegree = 0;

			for (Node tmp2 = p.poly; tmp2 != null; tmp2 = tmp2.next) {
				tmpCoeff = tmp.term.coeff * tmp2.term.coeff;
				// If tmpCeff is zero do not store term since it's
				// value is zero regardless of degree
				if (tmpCoeff != 0) {
					tmpDegree = tmp.term.degree + tmp2.term.degree;
					newNode = new Node(tmpCoeff, tmpDegree, null);
					
					if (y.poly == null) y.poly = newNode;
					else {
						for (Node n = y.poly; n != null; n = n.next) {
							if (n.next == null) {
								n.next = newNode;
								break;
							}
						}
					}
				}
			}
			product = product.add(y);
		}
		return product;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		/** COMPLETE THIS METHOD **/
		float result = 0;
		for (Node tmp = poly; tmp != null; tmp = tmp.next) {
			result += tmp.term.coeff * ( Math.pow(x, tmp.term.degree) );
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
