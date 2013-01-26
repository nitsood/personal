package interviewstreet;

public class DoublyLinkedListPrototype1<E>
{
  private Node<E> first, last;
   
  public void push(E data)
  {
    if(first == null && last == null)
    {
      last = new Node<E>(data, null, null);
      first = last;
    }
    else
    {
      Node<E> e = new Node<E>(data, null, null);
      last.next = e;
      e.prev = last;
      last = last.next;
    }
  }
  
  /*public E pop()
  {
    
  }*/
  
  /*
   * deletes a node anywhere in the list in O(1);
   * Nodes are matched using object reference matching only
   */
  public Node<E> delete(Node<E> t)
  {
    Node<E> f = null;
    
    if(first == last)
    {
      f = first;
      first = last = null;
      return f;
    }
    else
    {
      f = first;
      while(f != null)
      {
        if(f == t)
        {
          f.prev.next = f.next;
          if(f.next != null) f.next.prev = f.prev; //in case node to be deleted is last
          f.prev = f.next = null;
          return t;
        }
        
        f = f.next;
      }
      
      return null;
    }
  }
  
  public E delete(E t)
  {
    Node<E> f = null;
    
    if(first == last)
    {
      f = first;
      first = last = null;
      return f.data;
    }
    else
    {
      f = first;
      while(f != null)
      {
        if(f.data == t)
        {
          f.prev.next = f.next;
          if(f.next != null) f.next.prev = f.prev; //in case node to be deleted is last
          f.prev = f.next = null;
          return t;
        }
        
        f = f.next;
      }
      
      return null;
    }
  }
  
  public void printList()
  {
    Node<E> f = first;
    while(f != null)
    {
      System.out.print(f.data+" ");
      f = f.next;
    }
  }
  
  public void printReverse()
  {
    Node<E> l = last;
    while(l != null)
    {
      System.out.print(l.data+" ");
      l = l.prev;
    }
  }
  
  public static void main(String[] args)
  {
    DoublyLinkedListPrototype1<Integer> dl = new DoublyLinkedListPrototype1<Integer>();
    String s = "I would't";
    String s1[] = s.split("\\s");
    for(String x : s1)
    {
      System.out.println(x);
    }
    
    dl.push(4);
    dl.push(42);
    dl.push(2);
    dl.push(-9);
    dl.push(26);
    dl.printList();
    System.out.println();
    //dl.printReverse();
    
    dl.delete(26);
    dl.printList();
    System.out.println();
    dl.printReverse();
  }
  
  public static class Node<U>
  {
    private U data;
    private Node<U> next;
    private Node<U> prev;
    
    Node(U d, Node<U> n, Node<U> p)
    {
      data = d;
      next = n;
      prev = p;
    }
  }
}
