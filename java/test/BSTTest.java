package interviewstreet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BSTTest
{
  
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
    
    Map<String, String> m = new LinkedHashMap<String, String>();
    m.put("a", "A");
    m.put("b", "B");
    m.put("c", "C");
    m.put("d", "D");
    m.put("e", "E");
    m.put("f", "F");
    m.put("g", "G");
    
    m.remove("f");
    m.remove("g");
    //Map.Entry<String, String> entry = null;
    //for(Map.Entry<String, String> entry : m.entrySet())
    //  System.out.println(entry.getKey()+":"+entry.getValue());
    
    List<String> x = new ArrayList<String>();
    x.add("a");
    x.add("b");
    x.add("c");
    
    printG(x);
  }
 
  public static <T extends Number> void printT(List<T> list)
  {
    for(T t : list)
      System.out.println(t);  
  }
  
  public static void printG(List<?> list)
  {
    for(Object t : list)
      System.out.println(t);  
  }
  
  public static Node insert(Node t, int data)
  {
    if(t == null)
      t = new Node(data);
    else
    {
      if(t.data > data)
        t.left = insert(t.left, data);
      else if(t.data <= data)
        t.right = insert(t.right, data);
    }
    
    return t;
  }
  
  public static Node find(Node t, int data)
  {
    if(t == null)
      return null;
    
    if(t.data > data)
      return find(t.left, data);
    else if(t.data < data)
      return find(t.right, data);
    else
      return t;
  }
  
  public static void printInOrder(Node t)
  {
    if(t != null)
    {
      printInOrder(t.left);
      System.out.print(t.data+" ");
      printInOrder(t.right);
    }
  }
  
  public static class Node
  {
    //package private stuff 
    int data;
    Node left;
    Node right;
    
    Node(int d)
    {
      data = d;
    }
  }
}
