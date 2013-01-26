package interviewstreet;

public class StackPrototype1
{
  private Node top;
  
  public void push(int data)
  {
    if(top == null)
      top = new Node(data, null);
    else
    {
      Node t = new Node(data, null);
      t.next = top;
      top = t;
    }
  }
  
  public void printStack()
  {
    Node t = top;
    while(t != null)
    {
      System.out.print(t.data+" ");
      t = t.next;
    }
  }
  
  public static void main(String[] args)
  {
    StackPrototype1 st = new StackPrototype1();
    st.push(5);
    st.push(4);
    st.push(3);
    st.printStack();
  }
  
  public static class Node
  {
    private int data;
    private Node next;
    
    Node(int d, Node n)
    {
      data = d;
      next = n;
    }
  }
}
