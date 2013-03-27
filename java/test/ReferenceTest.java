package interviewstreet;

import java.util.*;

public class ReferenceTest
{
  
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
    Set<Integer> lx = new HashSet<Integer>();
    lx.add(1);
    lx.add(2);
    lx.add(3);
    System.out.println(lx);
    lx = modify(lx);
    System.out.println(lx);
    
    String s1 = "nitesh";
    String s2 = "a";
    Integer i1 = 4;
    Integer i2 = 4;
    System.out.println(i1.compareTo(i2));
    
    char a = 'a';
    int x = a;
    System.out.println(x);
  }
  
  public static Set<Integer> modify(Set<Integer> li)
  {
    li.remove(2);
    li.add(4);
    return li;
  }
}
