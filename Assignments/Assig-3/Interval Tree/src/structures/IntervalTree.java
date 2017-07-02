package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// For debugging purposes only.
//		System.out.println(root.leftChild.leftChild.leftChild.splitValue);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);

		// For debugging purposes only.
//		for (Interval i: root.rightChild.rightIntervals)
//		{
//			System.out.println(i);
//		}
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints)
	{
		// COMPLETE THIS METHOD
		Queue<IntervalTreeNode> tmpQ = new Queue<IntervalTreeNode>();

		for (int p: endPoints) { tmpQ.enqueue(new IntervalTreeNode(p, p, p)); }
		
		return buildTreeFromQ(tmpQ);
	}
	
	/****************************************************
	 * Build tree from Queue method
	 ****************************************************/
	private static IntervalTreeNode buildTreeFromQ(Queue<IntervalTreeNode> Q)
	{
		float v1 = 0, v2 = 0, x = 0;
		IntervalTreeNode tmp = null, t1 = null, t2 = null, N = null;
		int qSize = Q.size;
		
		if (Q.size == 1) { tmp = Q.dequeue(); return tmp; }
		
		while (qSize > 1)
		{
			t1 = Q.dequeue();
			t2 = Q.dequeue();
			
			v1 = findMaxSplitValue(t1);
			v2 = findMinSplitValue(t2);
			
			x = (v1 + v2) / 2;
			
			N = new IntervalTreeNode(x, v2, v1);
			N.leftChild = t1;
			N.rightChild = t2;
			
			Q.enqueue(N);
			
			qSize = qSize - 2;
		}
	
		if (qSize == 1) { tmp = Q.dequeue(); Q.enqueue(tmp); }
		
		return buildTreeFromQ(Q);
	}
	
	// Helper method find maximum split value for 'n' tree node parameter.
	private static float findMaxSplitValue(IntervalTreeNode n)
	{
		ArrayList<Float> leaves = new ArrayList<Float>();
		visitNode(n, leaves);
		
		return leaves.get(leaves.size() - 1);
	}
	
	// Helper method find minimum split value for 'n' tree node parameter.
	private static float findMinSplitValue(IntervalTreeNode n)
	{
		ArrayList<Float> leaves = new ArrayList<Float>();
		visitNode(n, leaves);
		
		return leaves.get(0);
	}
	
	// Find each leaf node in tree (n) and save to Array List (leaves).
	private static void visitNode(IntervalTreeNode n, ArrayList<Float> leaves)
	{
		if(n.leftChild != null)	visitNode(n.leftChild, leaves);
	    if(n.rightChild != null) visitNode(n.rightChild, leaves);
	    if(n.leftChild == null && n.rightChild == null) leaves.add(n.splitValue);
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree
	(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals)
	{
		// COMPLETE THIS METHOD
		for (Interval lsort: leftSortedIntervals)
		{
			insertLInterval(this.root, lsort);
		}
		
		for (Interval rsort: rightSortedIntervals)
		{
			insertRInterval(this.root, rsort);
		}
	}
	// Private method to insert leftSortedIntervals into Interval Tree. 
	private void insertLInterval(IntervalTreeNode T, Interval lsort)
	{
		if (T.splitValue >= lsort.leftEndPoint && T.splitValue <= lsort.rightEndPoint)
		{
			if (T.leftIntervals == null) T.leftIntervals = new ArrayList<Interval>();
			T.leftIntervals.add(lsort);
		}
		else if (T.splitValue > lsort.rightEndPoint)
		{
			insertLInterval(T.leftChild, lsort);
		}
		else if (T.splitValue < lsort.leftEndPoint)
		{
			insertLInterval(T.rightChild, lsort);
		}
	}
	// Private method to insert rightSortedIntervals into Interval Tree.
	private void insertRInterval(IntervalTreeNode T, Interval rsort)
	{
		if (T.splitValue >= rsort.leftEndPoint && T.splitValue <= rsort.rightEndPoint)
		{
			if (T.rightIntervals == null) T.rightIntervals = new ArrayList<Interval>();
			T.rightIntervals.add(rsort);
		}
		else if (T.splitValue > rsort.rightEndPoint)
		{
			insertRInterval(T.leftChild, rsort);
		}
		else if (T.splitValue < rsort.leftEndPoint)
		{
			insertRInterval(T.rightChild, rsort);
		}
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q)
	{
		// COMPLETE THIS METHOD
		ArrayList<Interval> results = new ArrayList<Interval>();
		
		return findIntervalsHelper(this.root, results, q);
	}
	
	private ArrayList<Interval> findIntervalsHelper
	(IntervalTreeNode T, ArrayList<Interval> results, Interval q)
	{
		// If rightIntervals is null, leftIntervals is also. Do not continue. Return null.
		if (T.rightIntervals == null) return null;

		// If we're at leaf node return null.
		if (T.leftChild == null && T.rightChild == null) return null;

		if (T.splitValue >= q.leftEndPoint  &&
			T.splitValue <= q.rightEndPoint &&
			q.rightEndPoint >= q.leftEndPoint)
		{
			for (Interval intv: T.leftIntervals)
			{
				results.add(intv);
			}
			findIntervalsHelper(T.leftChild, results, q);
			findIntervalsHelper(T.rightChild, results, q);
		}
		else if (T.splitValue > q.rightEndPoint)
		{
			int i = 0;
			while (i < T.leftIntervals.size())
			{
				if ( (q.leftEndPoint  >= T.leftIntervals.get(i).leftEndPoint  && 
					  q.leftEndPoint  <= T.leftIntervals.get(i).rightEndPoint &&
					  q.rightEndPoint > T.leftIntervals.get(i).rightEndPoint) ||
					
					 (q.rightEndPoint >= T.leftIntervals.get(i).leftEndPoint  && 
					  q.rightEndPoint <= T.leftIntervals.get(i).rightEndPoint &&
					  q.leftEndPoint  <  T.leftIntervals.get(i).leftEndPoint) ||

					 (q.leftEndPoint >= T.leftIntervals.get(i).leftEndPoint  &&
					 q.rightEndPoint <= T.leftIntervals.get(i).rightEndPoint &&
					 q.rightEndPoint >= q.leftEndPoint))
				{
					results.add(T.leftIntervals.get(i));
				}
				i ++;
			}
			findIntervalsHelper(T.leftChild, results, q);
		}
		else if (T.splitValue < q.leftEndPoint)
		{
			int i = 0;
			while (i < T.rightIntervals.size())
			{
				if ( (q.leftEndPoint  >= T.rightIntervals.get(i).leftEndPoint  && 
					  q.leftEndPoint  <= T.rightIntervals.get(i).rightEndPoint &&
					  q.rightEndPoint > T.rightIntervals.get(i).rightEndPoint) || 
					
					 (q.rightEndPoint >= T.rightIntervals.get(i).leftEndPoint   && 
					  q.rightEndPoint <= T.rightIntervals.get(i).rightEndPoint  &&
					  q.leftEndPoint  <  T.rightIntervals.get(i).rightEndPoint) ||

					 (q.leftEndPoint  >= T.rightIntervals.get(i).leftEndPoint  &&
					  q.rightEndPoint <= T.rightIntervals.get(i).rightEndPoint &&
					  q.rightEndPoint >= q.leftEndPoint))
					
				{
					results.add(T.rightIntervals.get(i));
				}
				i++;
			}
			findIntervalsHelper(T.rightChild, results, q);
		}
		
		return results;
	}
	
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}
