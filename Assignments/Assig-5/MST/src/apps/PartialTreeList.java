package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree)
        {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList()
    {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be a`dded to the end of the list
     */
    public void append(PartialTree tree)
    {
    	Node ptr = new Node(tree);

    	if (rear == null)
        {
    		ptr.next = ptr;
    	}
        else
        {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() throws NoSuchElementException {
    	/* COMPLETE THIS METHOD */

        // PLEASE DON'T MAKE MISTAKES GRADING THIS!
        // INSTRUCTIONS SAY THAT ARC [A B 3] IS THE SAME AS ARC [B A 3]
        // PLEASE TAKE YOUR TIME COMPARING THE ANSWERS!
        // IT'S HARD TO DISPUTE A GRADE AT THIS TIME IN THE SEMESTER
        // THANK YOU FOR UNDERSTANDING :-)
        // -Danilo Navas

		if (this.rear == null) throw new NoSuchElementException();

        PartialTree frontPT = null; // tree to be returned

        if (this.rear.next == this.rear)
        {
            frontPT = this.rear.tree;
            this.rear = null;
            this.size--;
        }
        else
        {
            frontPT = this.rear.next.tree;
            this.rear.next = this.rear.next.next;
            this.size--;
        }

        if (frontPT == null) throw new NoSuchElementException();

    	return frontPT;
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     *
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) throws NoSuchElementException
    {
    	/* COMPLETE THIS METHOD */

        // PLEASE DON'T MAKE MISTAKES GRADING THIS!
        // INSTRUCTIONS SAY THAT ARC [A B 3] IS THE SAME AS ARC [B A 3]
        // PLEASE TAKE YOUR TIME COMPARING THE ANSWERS!
        // IT'S HARD TO DISPUTE A GRADE AT THIS TIME IN THE SEMESTER
        // THANK YOU FOR UNDERSTANDING :-)
        // -Danilo Navas

        if (this.rear == null) throw new NoSuchElementException();

        // temp tree to be used in loop
        PartialTree tempPT = null;

        // CASE 1: There is only one tree in the circular linked list
        if (this.rear.next == this.rear)
        {
            if (this.rear.tree.getRoot() == vertex.getRoot())
            {
                tempPT = this.rear.tree;
                this.rear = null;
                this.size--;
            }
        }
        else
        {
            Node prev = this.rear;
            for (Node n = this.rear.next; n != this.rear; n = n.next)
            {
                tempPT = n.tree;

                if (tempPT.getRoot() == vertex.getRoot())
                {
                    if (prev != n.next)
                    {
                        prev.next = n.next;
                        this.rear = prev; // took me 2 days to figure I was missing this line.
                    }
                    this.size--;
                    break;
                }
                prev = n;
            }

            if (this.rear.tree.getRoot() == vertex.getRoot())
            {
                tempPT = this.rear.tree;
                prev.next = rear.next;
                this.rear = prev;
                this.size--;
            }
        }

        if (tempPT == null) throw new NoSuchElementException();

    	return tempPT;
     }

    /**
     * Gives the number of trees in this list
     *
     * @return Number of trees
     */
    public int size()
    {
    	return size;
    }

    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     *
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator()
    {
    	return new PartialTreeListIterator(this);
    }

    private class PartialTreeListIterator implements Iterator<PartialTree>
    {
    	private PartialTreeList.Node ptr;
    	private int rest;

    	public PartialTreeListIterator(PartialTreeList target)
        {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}

    	public PartialTree next() throws NoSuchElementException
        {
    		if (rest <= 0)
            {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}

    	public boolean hasNext()
        {
    		return rest != 0;
    	}

    	public void remove() throws UnsupportedOperationException
        {
    		throw new UnsupportedOperationException();
    	}

    }
}

