package interviewstreet;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Scanner;

public class Flowers
{
  public static void main(String args[])
  {
    //int sz = 5;
    //Random r = new Random();
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int k = sc.nextInt();
    int cost[] = new int[n];
    for(int i=0; i<n; i++)
      cost[i] = sc.nextInt();
    
    /*System.out.print("Flowers: ");
    for(int i=0; i<sz; i++)
      System.out.print(cost[i]+" ");
    System.out.println("\n");
    */
    
    Arrays.sort(cost);
    /*System.out.print("Flowers: ");
    for(int i=0; i<sz; i++)
      System.out.print(cost[i]+" ");
    System.out.println("\n");
    */
    System.out.println(findMinCost(cost, n, k));
  }
  
  public static int findMinCost(int cost[], int n, int k)
  {
    if(n <= k)
      return findTrivialCost(cost);
    else
    {
      int sum = 0;
      PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
      for(int i=0; i<k; i++) pq.add(0);
      
      for(int i=n-1; i>=0 ; i--)
      {
        Integer fl = pq.poll();
        if(fl != null)
        {
          sum += (fl+1)*cost[i];
          pq.add(fl+1);
        }
        //System.out.println("Queue: "+pq);
        //System.out.println("Cost: "+sum);
        //System.out.println("\n");
      }
      return sum;
    }
  }
  
  public static int findTrivialCost(int cost[])
  {
    int sum = 0;
    for(int i=0; i<cost.length; i++)
      sum += cost[i];
    return sum;
  }
}