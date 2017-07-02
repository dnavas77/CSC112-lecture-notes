public class PS3
{
  public static void main(String[] args)
  {
    DLLNode front = new DLLNode("one", null, null);
    front = addAfter("two", "one");
    front = moveToFront(front, "tree");
  }
  
  /*
   * Moves target to front of DLL
   */
  public static DLLNode moveToFront(DLLNode front, DLLNode target)
  {
    // if target is null return front
    if (target == null) return front;

    // if front is null return null
    if (front == null) return null;

    for (DLLNode tmp = front; tmp != null; tmp = tmp.next)
    {
      if (tmp == target)
      {
        // if tmp is already the front, return front.
        if (tmp.prev == null) return front;

        // if tmp is the last node
        if (tmp.next == null)
        {
          tmp.next = front;
          front.prev = tmp;
          tmp.prev.next = null;
          tmp.prev = null;

          return front;
        }

        // if tmp is any node between the front and the tail
        tmp.prev.next = tmp.next;
        tmp.next.prev = tmp.prev;
        front.prev = tmp;
        tmp.next = front;
        tmp.prev = null;
        break;
      }
    }

    // if target is not found return front
    return front;
  }

}
