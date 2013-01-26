package interviewstreet;

import java.util.Arrays;
import java.util.Scanner;

public class Pairs
{
  public static void main(String args[])
  {
    //int diff = 2;
    //int sz = 10;
    //ng list[] = new long[sz];
    
    //for(int i=0; i<sz; i++)
    //  list[i] = r.nextLong();
    
    Scanner sc = new Scanner(System.in);
    int sz = Integer.parseInt(sc.next());
    int diff = Integer.parseInt(sc.next());
    int ilist[] = new int[sz];
    
    for(int i=0; i<sz; i++)
      ilist[i] = Integer.parseInt(sc.next());
    
    //long start = System.currentTimeMillis();
    Arrays.sort(ilist);
    int pairs = 0;
    for(int i=0; i<sz; i++)
    {
      if(Arrays.binarySearch(ilist, ilist[i]+diff) >= 0)
        pairs++;
    }
    //long end = System.currentTimeMillis();
    
    //for(int i=0; i<sz; i++)
    //  System.out.print(ilist[i]+" ");
    
    //int time = (int) ((end-start)/1000);
    //System.out.println("Time: "+(end-start));
    System.out.println(pairs);
  }
}