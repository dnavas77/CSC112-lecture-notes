package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr)
	{
		// COMPLETE THIS METHOD
		if (intervals == null) return;
		
		if (lr == 'l') // sort by left end point
		{
			lQuickSort(intervals, 0, intervals.size() - 1);
			
			// for debugging purposes only
//			System.out.println("Intervals sorted by left end point");
//			for (Interval inter: intervals)
//			{
//				System.out.println(inter);
//			}
//			System.out.println("End.");
			
		}
		else if (lr == 'r') // sort by right end point
		{
			rQuickSort(intervals, 0, intervals.size() - 1);
			
			// for debugging purposes only
//			System.out.println("Intervals sorted by right end point");
//			for (Interval inter: intervals)
//			{
//				System.out.println(inter);
//			}
//			System.out.println("End.");
		}
		else
		{
			System.out.println("Invalid value for lr parameter");
			return;
		}
	}
	
	/**
	 * Quicksort for left endpoint values in intervals array list.
	 */
	private static void lQuickSort(ArrayList<Interval> intervals, int left, int right)
	{
	      int i = left, j = right;
	      Interval tmp;
	      int pivot = intervals.get((left + right) / 2).leftEndPoint;

	      // partition
	      while (i <= j)
	      {
	            while (intervals.get(i).leftEndPoint < pivot)  i++;
	            while (intervals.get(j).leftEndPoint > pivot)	j--;

	            if (i <= j)
	            {
	                  tmp =	intervals.get(i);
	                  intervals.set(i, intervals.get(j));
	                  intervals.set(j, tmp);

	                  i++;
	                  j--;
	            }
	      };

	      // recursion
	      if (left < j)	 lQuickSort(intervals, left, j);
	      if (i < right) lQuickSort(intervals, i, right);
	}
	
	
	/**
	 * Quicksort for right endpoint values in intervals array list.
	 */
	private static void rQuickSort(ArrayList<Interval> intervals, int left, int right)
	{
	      int i = left, j = right;
	      Interval tmp;
	      int pivot = intervals.get((left + right) / 2).rightEndPoint;

	      // partition
	      while (i <= j)
	      {
	            while (intervals.get(i).rightEndPoint < pivot)  i++;
	            while (intervals.get(j).rightEndPoint > pivot)	j--;

	            if (i <= j)
	            {
	                  tmp =	intervals.get(i);
	                  intervals.set(i, intervals.get(j));
	                  intervals.set(j, tmp);

	                  i++;
	                  j--;
	            }
	      };

	      // recursion
	      if (left < j)	 rQuickSort(intervals, left, j);
	      if (i < right) rQuickSort(intervals, i, right);
	}

	
	/**
	 * Given a set of intervals (left sorted and right sorted),
	 * extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints
	(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals)
	{
		// COMPLETE THIS METHOD
		ArrayList<Integer> points = new ArrayList<Integer>();

		// Add all left end points in leftSortedIntervals. No duplicates.
		for (Interval tmp: leftSortedIntervals)
		{
			if (points.contains(tmp.leftEndPoint) == false)
				points.add(tmp.leftEndPoint);

			if (points.contains(tmp.rightEndPoint) == false)
				points.add(tmp.rightEndPoint);
		}
		
		alQuickSort(points, 0, points.size() - 1); // Array List Quick Sort
		
//		for (int a: points)
//		{
//			System.out.println(a);
//		}
		
		return points;
	}
	
	/**
	 * Sort Array List using Quick Sort 
	 */
	private static void alQuickSort(ArrayList<Integer> al, int left, int right)
	{
	      int i = left, j = right, tmp;
	      int pivot = al.get((left + right) / 2);

	      // partition
	      while (i <= j)
	      {
	            while (al.get(i) < pivot)  i++;
	            while (al.get(j) > pivot)	j--;

	            if (i <= j)
	            {
	                  tmp =	al.get(i);
	                  al.set(i, al.get(j));
	                  al.set(j, tmp);

	                  i++;
	                  j--;
	            }
	      };

	      // recursion
	      if (left < j)	 alQuickSort(al, left, j);
	      if (i < right) alQuickSort(al, i, right);
	}
	
}
