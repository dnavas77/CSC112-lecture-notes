// DATA STRUCTURES - CSC112.

//////////////////////////////////////////////////////////
// Tuesday, Sept 2, 2015. Lecture-1
//////////////////////////////////////////////////////////

/*
Textbook: Data Structures Outside In with Java
  NO PIAZZA :(
  Assignments on SAKAI!

  Grading:
    Assignments: 35%
    Midterm 1: 15%
    Midterm 2: 15%
    Final:  30%
    Recitation problems: 5%

  What you should learn in this class: Big-O, Recursion, OOP, Searching, Sorting.

  What's a Data Structure: a way of organizing and storing data.

  DATA STRUCTURES:
  Linear: array, linked list, stack, queue.
  Trees: Binary tree, binary search tree, AVL tree, heap.
  Graphs: undirected, directed, weighted.
  Hash table.

  Running Time/Space analysis
  Big-O: worst case, best case, average case.

  Array: [2] [3] [5] [1] [6]
  Efficiency: Big-O of 1.

  Linked List:
    - Data is sequentially stored
    <circle divided in half> --> <circle divided in half> --> <circle divided in half>
    - Every piece of data has two parts. A value (data section) and a pointer (next).
    - Each value has a pointer to the next one.
    - It's comprised of nodes.
    - There's a special node called head/front which is the first node.
    - Last node is called Tail/Rear (points to null).
    - You must at least have a head.
    - How to add node in the beginning of the list?
        * create node (value / next).
        * point -next to current head.
        * point -head to the new node.
*/

//////////////////////////////////////////////////////////
// Thursday, Sept 4, 2015. Lecture-2
//////////////////////////////////////////////////////////

  /*

  Topic: Linked List.

  Operations:
  - Add node
  -

  How to create Linked List:
  - Simple linked list: Node head = null
  - Next simple linked list: head = new Node()

  How to create a new Node:
  1. Node temp = new Node()
    a) temp.data = 10
  2. temp.next = head;
  3. head = temp

  */
    public class Node
    {
        // data (fields)
        public int data = 0;
        public Node next = null; // points to the next node.

        // operations (methods)
        public Node (int data, Node next)
        {
            this.data = data;
            this.next = next;
        }
    }

  // Shorthand notation:
    Node head = new Node (10, new Node(8, null));
    head = temp;

//////////////////////////////////////////////////////////
// Thursday, Sept 10, 2015. Lecture-3
//////////////////////////////////////////////////////////

  // LINKED LIST PROGRAMMED.

    // NODE class
    public class Node
    {

        public int data;
        public Node next;

        public Node(int data, Node next)
        {
            this.data = data;
            this.next = next;
        }
    } // end Node


    // LinkedList class
    class LinkedList
    {
        public static void main(String[] args)
        {
            Node head = new Node(5, null);

            // head on left becomes new node and head in second argument becomes 'next'
            head = addToFront(10, head);
            head = addToFront(20, head);
            head = addToFront(30, head);
        }

        // return new node which becomes the new head.
        public static Node addToFront(int data, Node head)
        {
            // if param 'head' is null create 'head'.
            if (head == null) return new Node(data, null);

            // param 'head' is not null.
            return new Node(data, head);
        }

        // if data found return 1, if not, return 0.
        public static boolean search(Node head, int data)
        {
            for (Node tmp = head; tmp != null; tmp = tmp.next)
            {
                if (tmp.data == data) return true;
            }
            return false;
        } // end

        public static Node addToRear(int data, Node head)
        {
            // if param 'head' is null create 'head'.
            if (head == null) return new Node(data, null);

            // find last node where node.next equals null
            for (Node tmp = head; tmp != null; tmp = tmp.next)
            {
                if (tmp.next == null)
                {
                    tmp.next = new Node(data, null);
                    break;
                }
            }            
            return head;
        } // end
    }

//////////////////////////////////////////////////////////
// Tuesday, Sept 15, 2015. Lecture-4
//////////////////////////////////////////////////////////

// Prof. finished 'addToRear()' from previous class.

// Delete from front
    public static Node removeFromFront(Node head)
    {
        if (head == null) return null;

        // 'head' is not null
        return head.next;
    }

		// Delete target Node
    public static Node delete(int target, Node head)
    {
        if (head == null) return null;
        if (head.data == target) head.next;

        Node tmp = head;
        while (tmp != null)
        {
            if (tmp.next != null && tmp.next.data == target)
            {   // TODO: how to delete every node with target data?
                tmp.next = tmp.next.next;
                return head;
            }
            tmp = tmp.next;
        }
        // target is not found. return head.
        return head;
    }//end


//========== Refactor node into a proper Link list class definition
public class StringNode
{
    public String data;
    public StrNode next;

    public StringNode(String data, StrNode next)
    {
        this.data = data;
        this.next = next;
    }
}

//============= Create Link List class definition
public class StringLinkedList
{
    private StrNode head;
    private int size;

    public StringLinkedList()
    {
        head = null;
        size = 0;
    }

    // add to front
    public void addToFront(String data)
    {
        StrNode s = new StrNode();
        s.data = data;
        s.next = head;
        head = s;
        size++;
    }

    // Add after 
		public void addAfter(String toadd, String target)
		{
			StrNode = null;
			for (tmp=this.head; tmp != null; tmp=tmp.next)
			{
				if (tmp.data.equals(target))
				{
					StrNode n = new StrNode(toadd);
					n.next = tmp.next;	
					tmp.next = n;
					size++;
					return; //removing return adds new string after every target.
				}			
			}			
		}	

    // delete from front

    // delete target node
}

//////////////////////////////////////////////////////////
// Thursday, Sept 17, 2015. Lecture-5
//////////////////////////////////////////////////////////

// completing methods for StringLinkedList Class

// NEW TOPIC: double linked list
public void delete(String target)
{
	for (StrNode tmp=this.head; tmp != null; tmp=tmp.next)
	{
		if (tmp.data.equals(target))
		{
			if (tmp.next == null)
				tmp.next.prev = tmp.prev;				

			if (tmp.prev != null)
				tmp.prev.next = tmp.next;	
			else
				head = tmp.next;
		}
	}
	size--;
}

//######### CIRCULAR LINKED LIST ###########
public class CLL
{
			StrNode tail;
			int size;

			public void addToFront(String data)
			{
				StrNode n = new StrNode(data);

				if (tail == null)
				{
					tail = n;
					tail.next = tail;
				}

				n.next = tail.next;
				tail.next = n;				
			}	

			public void addToRear(String data)
			{

			}

			public boolean search(String data)
			{
			
			}
}

//////////////////////////////////////////////////////////
// Thursday, Sept 22, 2015. Lecture-6
//////////////////////////////////////////////////////////

// TOPIC: Continued.. Create String Linked List Class

public static void main(String[] args)
{
	StringLinkedList list = new StringLinkedList();
	list.addToFront("hello");
	list.addAfter("Godbye!", "hello");
}	

public String toString()
{
	if(head == null) return "";
	
	String s = "";

	for(StrNode tmp = head; tmp != null; tmp = tmp.next)
	{
		s = s + "-->" + tmp.data;
	}
	return s;
}

//>>>>>>>>>> EXCEPTIONS
int divide(int a, int b)
{
	if (b == 0) throw new DivideByZeroException;	
	return a/b;
}

// How to recover from exeptions
try(//try this)
{
	// code to try
} catch(//exception)
{
	// do this if exception happens.				
} catch(//another exception)
{
	// do this if another exception happens.
}

//>>>>>>>>>> THROWS
int divide(int a, int b) throws DivideByZeroException
{
	// stuff	
}

// >>>>>> CREATING custom EXEPTIONS
public class MyExceptions extends Exception
{
	public MyException()
	{
		super();					
	}				
	// custom exceptions here.				
}


/* <<<<<<<<<<<< EFFICIENCY ANALYSIS  >>>>>>>>>>>>>> */
									Array					LinkedList					CLL						DLL
addToFront		best: O(1)				best: O(1)			best: O(1)		best: O(1)
							worst: O(n)				worst: O( 			worst: O(			

AddToRear			best: O(1)				best: O(1)			best: O(1)		best: O(1) if access to tail, O(n) otherwise
							worst: O(n)

Search				best: O(1)				best: O(n)  		best: O(n)		best: O(1)
							worst: O(n) 			

Delete				best: O(1)  			best: O(
							worst: O(n)  // Same for all other data structures >>>>>>>>>>



// <<<<<<<<<<<<   GENERICS  >>>>>>>>>>>>>>>
class Node <T>
{
	T data;
	Node<T> next;
}


class LinkedList <T>
{
	Node<T> head;			
}


//////////////////////////////////////////////////////////
// Tuesday, Sept 24, 2015. Lecture-7
//////////////////////////////////////////////////////////


// TOPIC: >>>> STACK -- Last In <> First Out

>> METHODS:

// push: add data to bottom of stack
// pop: remove from top of stack

public class Stack<T>
{
	Node<T> head;
	
	public void push(T data)
	{
		head = new Node(data, head);
	}

	public T pop()
	{
		if (head == null) throw new java.util.NoSuchElementException();

		T tmp = this.head.data;
		this.head = this.head.next;
		return tmp;	
	}
}

// check if syntax in string is valid algorithm


this is a string {{{{{ (((((( {{ ((( [[[ {{ ((

>> Push in stack only opening symbols
>> If the next closing symbol matches head then pop head.
>> If the next closing symbol doens't match head... syntax error
>> If stack is empty return true.... else return false


//////////////////////////////////////////////////////////
// Tuesday, Sept 29, 2015. Lecture-8
//////////////////////////////////////////////////////////

// TOPIC: ArrayList

// Looking at the ArrayList API in the Oracle docs site.

// Using ArrayList to create a Stack
public class Stack<T>
{
	ArrayList<T> data;

	public Stack()
	{
		data = new ArrayList<T>();	
	}

	public void push(T data)
	{
			data.add(data);		
	}

	public T pop()
	{
		if (data.size == 0)
						// throw exception
						
		return data.remove(data.size() -1);
	}
	
}

// >>>>> EFFICIENCY ANALYSYS of Stack with ArrayList
Push: Best Case: O(1) 
			Worst Case: O(n): we see this case once every xN times

Pop: O(1) in any case worst, best.
|
|# of
| ops
|									|
|									|
|									|
|_______10_______20_________ n = items to add


>>>>>> QUEUE (First In, First Out)

Operations: 
	enqueue (push)
	dequeue (pop)

public class Queue <T>
{
	T tail = null;

	public void enqueue(T node)
	{
		// Case 1: list is empty	
	}

	public T dequeue() // delete from front
	{
		if (tail == null) // Throw exeption.

		// there's only one node in list which is tail				
		if(tail == tail.next)
		{
			T tmp = tail.data;
			tail = null;
			return tmp
		}	

		T tmp = tail.next;
		tail.next = tail.next.next;

		return tmp;	
	}	
}

// QUEUE with ArrayList
enqueue()
{
	// is a bad idea because when we move head up we have
	// a lot of space empty below head.
}

// >> BOUNDED(limited) QUEUES

//////////////////////////////////////////////////////////
// Thursday, Oct 01, 2015. Lecture-9
//////////////////////////////////////////////////////////

// TOPIC: Bounded Queue

public class BoundedQueue<T> {
	T[] items;
	int size;
	int front, rear;

	public BoundedQueue(int cap) {
		items = (T []) new Object[cap];
		size = 0;
		front = 0;
		rear = cap-1;	
	}	

	public void enqueue(T item) throws QueueFullException {
		if (size == items.length) {
			throw new QueueFullException("queue is full");
		}	
		rear = (rear + 1) % items.length;
		items[rear] = item;
		size++;
	}
}	

//>>>>>> Algorithms:  

Linear search:
	1-define input size
	2-what to count
	3-best case: fist item in list is item you look for. O(1)
		worst case: item is last one or it's not on list. O(n)
		average case: 
				-average case for failure is "n".
				-average case for success: (Sum of cost) / n 

				For each item in n
				n +1 / 2	

				// Average
				Sum Probability (i) * cost(2)

// >>> BLOCK SEQUENTIAL SEARCH
[][][][][][][] array number in order

[][][]  [][][]  [][][] divide array in blocks
n = m*s // n = number of items in array
				// m = number of blocks
				// s = size of each block

- search for item you look for in last elem in block-1
- if your item is greater look in next block-1
- if your item is smaller, do binary search in current block

Best case: O(1)
Worst case: f(n) = 2m +(s-1) ----  O(8) // item you look is oen before last elem in last block
								2m = two checks per block
								s-1 = item you look for is one elem before last in last block

Average Case:
BLOCK 1:
	1 elem cost: 1
	2 elem cost: 3
	3 elem cost: 4
	1 + 3 + 4 + ... + s-1 + 2

BLOCK 2:
	1 + 3 + 4 + ... (s-1 + 2) + 2S // 2 times size of block	

BLOCK 3:
	1 + 3 + 4 + ... (s-1 + 2) + 4S	
	

BLOCK M:
	1 + 3 + 4 + ... (s-1 + 2) + 2(m-1)S	
	

equation to get each item cost
m [( (s+1)(s+2) - 2) / 2]

Average time O(m + s)

//////////////////////////////////////////////////////////
// Tuesday, Oct 06, 2015. Lecture-10
//////////////////////////////////////////////////////////

//>> TOPIC: BINARY SEARCH
* List has to be sorted
1		5		10		20		23		30		41

look for 3:
	divide elems by 2
	is mid number == target?, no
		is mid number > target? no
			get rid of half the numbers and repeat
		
Best Case:
			mid element is the target. O(1)			

Worst Case:
			log(base 10)
				f(x) = 2 log n
							O(log n)

// ITERATIVE SOLUTION
public class BinarySearch {
		public static <T extends Comparable<T>>
		
		while(lo <= hi) {
				int mid = (lo + hi) / 2;
				int c = target.CompareTo(arr[mid]);
				
				if (c == 0) return mid;
		}
}

// RECURSIVE SOLUTION
// Base Case: lo >= hi

return recursiveBinarySearch(arr, target, lo, mid-1)
return recursiveBinarySearch(arr, target, mid+1, hi);

// >>>>>>>>>>>> BINARY SEARCH TREE <<<<<<<<<<<<

1) Empty/null tree node
2) Node who's left child is a BST
	a) value of L subtree < root
		node whose R child is a BST
	b) value of R subtree > root

3) Each node has 2 next nodes, Right next and Left next

Make a binary tree with following numbers
	3, 10, 5, 1, 8, 20, 4, 6, 7, 0	
					
										
										   	3
										  /   \		
										1	      10
									/        /	
								0			   5 
											 / 	 \
											4     8
													 /
													6 
													 \
														7
// Tree node list
public class BSTNode <T extends Comparable<T>> {
		T data;
		BSTNode<T> left;
		BSTNode<T> right;
}

// Tree
public class BST <T extends Comparable<T>> {
		BSTNode<T> root;	

		public T search(T target) {
				BSTNode<T> tmp = root;
				while(tmp != null) {
						int c = target.compareTo(tmp.data);	

						if (c == 0) return tmp.data;
						else if (c < 0) tmp = tmp.left;
						else tmp = tmp.right;
				}
				return null;
		}
		
		// Recursive Search
		public T search(T target) {
				return recSearch(target, root);
		}

		private T recSearch(T target, BSTNode<T> root) {
				if(root == null) return null;

				int c = target.compareTo(root.data);
				if(c == 0) return root.data;
				else if (c < 0) return recSearch(target, root.left);
				else return recSearch(target, root.right);	
		}
}

//////////////////////////////////////////////////////////
// Thursday, Oct 08, 2015. Lecture-11
//////////////////////////////////////////////////////////

// Finished writing search and recSearch from past class (see above).

// More BinarySearch Tree stuff
public void insert(T data) {
		BSTNode<T> n = new BSTNode<T>();	

		// if tree is empty, root has to change to new node above.
		n.data = data;
	  if(root == null) root = n; return;	

		// search where to put new node
		BSTNode<T> tmp = root;
		BSTNode<T> parent = null;
		int c = 0;
		while(tmp != null) {
				c = target.compareTo(tmp.data);	
				parent = tmp;

				if (c == 0) return;
				else if (c < 0) tmp = tmp.left;
				else tmp = tmp.right;
		}

		// attach new node after finding leaf
		if(c < 0) parent.left = n;
		else parent.right = n;

}// end insert()


public void delete(T target) {
		// 1. Find Node
		// 2. Find in order predecessor or successor
		// 3. Replace step 1 with step 2.
		// 4. Delete in order predecessor/successor
		
}

// >>>>> Topic: IN ORDER TRAVERSAL
LVR
V = root
L = left
R = right

LVR traversal prints the tree in ascending order

//////////////////////////////////////////////////////////
// Tuesday, Oct 13, 2015. Lecture-12
//////////////////////////////////////////////////////////

// Binary Search Tree Delete

public void delete(T item) throws NoSuchElementException {
	// p = parent, x = node we want to delete
	BSTNode<T> x = root, p = null;
	int c= 0;

	while(x != null) {// if x == null, item is not there.
		c = item.compareTo(x.data);

		if(c == 0) break;

		p = x;
		x = c < 0 ? x.left : x.right;
	}

	// no match
	if (x== 0) return;

	if (x.left != null && x.right != null) {
		// find inorder predecessor
		BSTNode<T> y = x.left; // left turn
		p = x;
		while(y.right != null) {
			p = y;
			y = y.right;
		}
		// copy y data into x
		x.data = y.data;
		// set x to y, for sliding into case 1 or case 2
		x = y;
	}

	// we're at root
	if (p == null) {
		root = x.left != null ? x.left : x.right;
		size --;
		return;
	}

	// case 1. 
	if (x.left == null && x.right == null) {
		if(x == p.left) p.left = null
		else p.right = null;
	}

	// Case 2
	if (x == p.left) {
		p.left = x.right == null ? x.left : x.right;
	}
	else {
		p.right = x.right == null ? x.left : x.right;
	}
	
}// end delete


// >>>>>> In order traversal on BST
public void inOrder(BSTNode<T> root) {
	if(root == null) return; 
	inOrder(root.left);	
	System.out.println(root.data);
	inOrder(root.right);
}

// Efficiency Analysis

Best case: target is root. O(1)

Worst Case time to get to leaf from root.
O(n) // becase if the tree is like a linked list we have to go through
				// all the nodes to find node or not find it.

Delete: Search + find in order predecessor(O(1)) + pointer reassignment
	Worst case: O(n)
	Best case:  O(1)


Tree Sort:
	1) inserts all items into BST
			worst case: n^2 // 0 + 1 + 2 + 3 ... n-1 	

			best case (symmetrical tree): 2n(log n)
			log(0) + log(2) + log (3) + ... + log(n-1)

	2) in order traversal of BST
		worst case: n time
		best case: n time	

>>>>>>>>>>>>> Data Structure AVL <<<<<<<<<<<<<<<
>>>>>>>>>> Self Balancing Binary Search Tree <<<<<<<

1. every AVL must be BST
2. for every node x, difference in heights of its subtrees <= 1 (0,1) 

//////////////////////////////////////////////////////////
// Thursday, Oct 15, 2015. Lecture-13
//////////////////////////////////////////////////////////

//>>> TOPIC:  AVL Continued...
//>> BALANCE FACTOR
	- Left High (high of left subtree is one bigger than right)   /
	- Right High (high of right subtree is one bigger than left) \
	- Equal High (same high on left and right side)  --

If tree becomes unbalanced, we need to rotate tree.
WATCH YOUTUBE VIDEO ON THIS

// Case 1: Rotation

// Case 2: TWO Rotations
If the 2 nodes have different balancing, first do a right rotation, then a left
rotation or viceversa depending on the rotation needed.

// Rotation to the left
tmp = R.left;
R.left = x;
x.right = tmp;
P.right = R; // P tree above to the left of our current tree being rotated. 

// EFFICIENCY ANALYLSIS
Search: same as BST
Best case: item is root
Worst: O(log n)
Insertion: O(log n)
Sort a tree:
	- Insert into tree. O(nlogn)
	- In order traversal O(n)	
	- total time: nlogn + n

//////////////////////////////////////////////////////////
// Tuesday, Oct 20, 2015. Lecture-14
//////////////////////////////////////////////////////////

Hypotesis: after 1 rotation the AVL is balanced

more		P
stuff		 \
to the 	  X  // X is unbalanced
left		 /  \
			/\	 /\
		  /	
		 /

Proof: 
	- Add a node
	- what do we need to show about P to proof that after rotation
		it continues being an AVL.
	- after 1 rotation heights are the same from P perspective
	

>>>>>>>>>>>>>>> NEW DATA STRUCTURE: BINARY TREE <<<<<<<<<<<<<<

0, 1, or 2 child nodes

Strict: every node has 0 or 2 children
Complete: every level is filled from left to right with the only empty level being last 

			O
	    /   \	
		O     O
	  / \   / \
	 O   O O   O
	/ \ 
  O   O	COMPLETE TREE

LVR = In Order
LRV = Post Order
VLR = Pre Order

When building a tree for a mathematical expression, to evaluate the tree
we use LRV because we need to get value for Left side and Right side before
we can go up the tree. All operators are in the middle of the tree and
the operands (numeric values) are leaf nodes.

LRV gives you a postfix notation on a tree with a mathematical expression

Given following data: produce a binary tree using LVR
		A B C D E F

It's impossible to find original tree by arranging data by LVR.
To fix issue we need to use another order to find original tree, VLR.

A B C D E F   // In Order  LVR
D B A C F E		// Pre Order VLR

Given data above find original tree.
	- Pre Order gives you the root in the beginning of data
	- find root in "In Order", node to left go to the left
  		node to the right goes to the right	


//////////////////////////////////////////////////////////
// Thursday, Oct 22, 2015. Lecture-15
//////////////////////////////////////////////////////////


// Continuation of previous clase
//	See notebook for this dat
// Download class notes from Sakai to see how to code
// algorithm to find original tree from In Order and Pre Order


>>>>>>>>> HOFFMAN CODING <<<<<<<<

See notebook notes.


//////////////////////////////////////////////////////////
// Tuesday, Oct 27, 2015. Lecture-16
//////////////////////////////////////////////////////////

<<<<< NEW STRUCTURE HASH TABLE >>>>>

See notebook notes



//////////////////////////////////////////////////////////
// Thursday, Oct 29, 2015. Lecture-17
//////////////////////////////////////////////////////////

Hash Table continued... See Notebook for notes


//////////////////////////////////////////////////////////
// Tuesday, Nov 3, 2015. Lecture-18
//////////////////////////////////////////////////////////







