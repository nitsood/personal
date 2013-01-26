package interviewstreet;

public class StackPrototype2<T>
{
  private Node<T> top;
  
  public void push(T data)
  {
    if(top == null)
      top = new Node<T>(data, null);
    else
    {
      Node<T> t = new Node<T>(data, null);
      t.next = top;
      top = t;
    }
  }
  
  public void printStack()
  {
    Node<T> t = top;
    while(t != null)
    {
      System.out.print(t.data+" ");
      t = t.next;
    }
  }
  
  public static void main(String[] args)
  {
    StackPrototype2<Integer> st = new StackPrototype2<Integer>();
    st.push(5);
    st.push(4);
    st.push(3);
    st.printStack();
    System.out.println();
    StackPrototype2<String> st2 = new StackPrototype2<String>();
    st2.push("Five");
    st2.push("Four");
    st2.push("Three");
    st2.printStack();
  }
  
  public static class Node<U>
  {
    private U data;
    private Node<U> next;
    
    Node(U d, Node<U> n)
    {
      data = d;
      next = n;
    }
  }
}
