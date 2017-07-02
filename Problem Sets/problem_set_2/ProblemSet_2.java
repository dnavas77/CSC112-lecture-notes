// ProblemSet_2.java

public class ProblemSet_2
{
  public static void main(String[] args)
  {
    IntNode front = new IntNode(10, null);

    front = addToFront(44, front);
    front = addToFront(33, front);

    front = addBefore(front, 10, 99);
    front = addBefore(front, 10, 77);

    for (IntNode tmp=front; tmp!=null; tmp=tmp.next)
    {
      System.out.println(tmp);
    }
  }//main

  /*
   * Add Node before target.
   */
  public static IntNode addBefore(IntNode front, int target, int newItem)
  {
    // 'front' is null
    if (front == null) return null;

    // target is the first node
    if (front.data = target) return new IntNode(newItem, front);

    // target is second or later node.
    for (IntNode temp=front; temp!=null; temp=temp.next)
    {    
      if (temp.next.data == target)
      {
        IntNode a = new IntNode(newItem, temp.next);
        temp.next = a;
        return front;
      }
    }
    // target is not found return same node.
    return front;
  }

  /*
   * Add Node to Front
   */
  public static IntNode addToFront(int data, IntNode head)
  {
    // if param 'head' is null create 'head'.
    if (head == null) return new Node(data, null);

    // param 'head' is not null.
    return new IntNode(data, head);
  }
}