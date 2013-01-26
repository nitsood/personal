package interviewstreet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class CustomStack extends Stack<Integer>
{
  private static final long serialVersionUID = 1L;
  private Integer minElem = Integer.MAX_VALUE;
  
  public Integer push(Integer item)
  {
    System.out.println("Spl push");
    if(item < minElem)
      minElem = item;
    
    return super.push(item);
  }
  
  public Integer min()
  {
    return minElem;
  }
  
  public static void main(String[] args)
  {
    // TODO Auto-generated method 
    Stack<Integer> cs = new CustomStack();
    cs.push(4);
    cs.push(10);
    cs.push(-1);
    cs.push(5);
    cs.push(19);
    
    Map<String, String> m = new LinkedHashMap<String, String>();
    Set<String> s = new HashSet<String>();
    
    String s1 = new String("hello");
    String s2 = new String("hello");
    
    
    System.out.println(s1.equals(s2));
    System.out.println(s1.hashCode());
    
    CustomStack csArr[] = new CustomStack[10];
    System.out.println(csArr[5]);
    
    //System.out.println("min is: "+((CustomStack)cs).min());
  }  
}
