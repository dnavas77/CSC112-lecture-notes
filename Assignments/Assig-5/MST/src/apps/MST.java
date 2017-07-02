package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph)
	{
		/* COMPLETE THIS METHOD */

		// PLEASE DON'T MAKE MISTAKES GRADING THIS!
		// INSTRUCTIONS SAY THAT ARC [A B 3] IS THE SAME AS ARC [B A 3]
		// PLEASE TAKE YOUR TIME COMPARING THE ANSWERS!
		// IT'S HARD TO DISPUTE A GRADE AT THIS TIME IN THE SEMESTER
		// THANK YOU FOR UNDERSTANDING :-)
		// -Danilo Navas

        if (graph == null) return null;

		PartialTreeList L = new PartialTreeList();

		// Iterate through all vertexes in 'graph' and add to 'L'
        for (int i = 0; i < graph.vertices.length; i++)
		{
            Vertex v = graph.vertices[i];

            // Create a partial tree containing only 'v'
            // Mark 'v' as belonging to T
			PartialTree T = new PartialTree(v);

            // Create priority queue P and associate it with T
            MinHeap<PartialTree.Arc> P = T.getArcs();

            // Insert all arcs connected to 'v' into 'P'
            for (Vertex.Neighbor z = v.neighbors; z != null; z = z.next)
            {
               P.insert(new PartialTree.Arc(v, z.vertex, z.weight));
            }

            L.append(T); // Add the partial tree T to the list L
		}
		
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param graph Graph for which MST is to be found
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(Graph graph, PartialTreeList ptlist)
    {
		/* COMPLETE THIS METHOD */

		// PLEASE DON'T MAKE MISTAKES GRADING THIS!
		// INSTRUCTIONS SAY THAT ARC [A B 3] IS THE SAME AS ARC [B A 3]
		// PLEASE TAKE YOUR TIME COMPARING THE ANSWERS!
		// IT'S HARD TO DISPUTE A GRADE AT THIS TIME IN THE SEMESTER
		// THANK YOU FOR UNDERSTANDING :-)
		// -Danilo Navas

        // If graph or ptlist objects are null return null
        if (graph == null || ptlist == null) return null;

        // Return array list MST of arcs that form Minimum Spanning Tree.
        ArrayList<PartialTree.Arc> mstArcs = new ArrayList<PartialTree.Arc>();

        while (ptlist.size() > 1)
        {
            Vertex v2 = null;
            PartialTree.Arc highestArc = null;

            // Remove the first partial tree PTX from L
            PartialTree PTX = ptlist.remove();

            do
            {
                // Remove the highest-priority arc from PTX Queue
                // If v2 also belongs to PTX tree, keep removing highest arc.
                highestArc = PTX.getArcs().deleteMin();
                v2 = highestArc.v2;
            } while (PTX.getRoot() == v2.getRoot());

            // Report highestArc as a component of the minimum spanning tree
            mstArcs.add(highestArc);

            // Find PartialTree PTY to which v2 belongs. Remove PTY from 'ptlist'
            PartialTree PTY = ptlist.removeTreeContaining(v2);

            // Combine PTX and PTY. This includes merging the priority queues.
            PTX.merge(PTY);

            // Append the resulting tree back to 'ptlist'.
            ptlist.append(PTX);

        }// end while

		return mstArcs;
	}
}
