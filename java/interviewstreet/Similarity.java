package interviewstreet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Similarity
{
  
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
    Scanner sc = new Scanner(System.in);
    //String x = "ababaa";
    
    String numCases = sc.nextLine();
    int t = Integer.parseInt(numCases);
    List<String> input = new ArrayList<String>(t);
    for(int i=0; i<t; i++)
      input.add(sc.nextLine());
    
    for(String s: input)
      System.out.println(findSimilarity(s));
    
    /*String s = "abcdefghijklmnopqrstuvwxyz";
    Random r = new Random();
    StringBuilder sb = new StringBuilder("");
    List<String> input = new ArrayList<String>(50);
    int sz = 100000;
    /*for(int i=0; i<sz; i++)
    {
      int k = r.nextInt(26);
      sb.append(s.charAt(k));
    }*/
    /*for(int i=0; i<sz/2; i++)
      sb.append("ab");
    String y = sb.toString();
    //sb.append("a");
    String x = sb.toString();
    //String x = "abbaa";
    /*for(int j=0; j<50; j++)
    {
      for(int i=0; i<sz; i++)
      {
        int k = r.nextInt(26);
        sb.append(s.charAt(k));
      }
      String x = sb.toString();
      input.add(x);
    }*/
    
    
    //buildLcpArray(arr, x);
    /*List<String> suffixArray = new ArrayList<String>();
    
    for(int i=1; i<x.length(); i++)
      suffixArray.add(x.substring(i));
    
    System.out.println(suffixArray);
    Collections.sort(suffixArray);
    System.out.println(suffixArray);
    */
    //for(int i=0; i<arr.length; i++)
    
    //System.out.println(x);
    //System.out.println("--------");
    //System.out.println(y);
    
    //System.out.println(arr);
    //System.out.println(x.startsWith(y));
  }
  
  public static int findSimilarity(String text)
  {
    //Map<String, Integer> m = new TreeMap<String, Integer>();
    
    char t[] = text.toCharArray();
    int c = 0;
    for(int i=0; i<text.length(); i++)
      c = c + linearMatch(t, i);
    
    return c;
  }
  
  /*public static int[] buildLcpArray(int[] sa, String text)
  {
    int n = text.length();
    int rank[] = new int[sa.length];
    int lcp[] = new int[sa.length];
    char A[] = text.toCharArray();
    
    for(int i=0; i<n; i++)
      System.out.print(sa[i]+" ");
    System.out.print("\n");
    
    for(int i=0; i<n; i++)
      rank[sa[i]] = i;
    
    for(int i=0; i<n; i++)
      System.out.print(rank[i]+" ");
    System.out.print("\n");
    
    int h=0;
    for(int i=0; i<n; i++)
    {
      if(rank[i] > 1)
      {
        int k = sa[rank[i]-1];
        while(A[i+h] == A[k+h])
          h = h+1;
        
        lcp[rank[i]] = h;
        if(h > 0)  
          h = h-1;
      }
    }
    
    for(int i=0; i<n; i++)
      System.out.print(sa[i]+" ");
    
    for(int i=0; i<n; i++)
      System.out.print(lcp[i]+" ");
    System.out.print("\n");
    
    return lcp;
  }*/
  
  public static int linearMatch(char[] text, int spos)
  {
    int c = 0;
    int n = text.length;
    int startpos = 0;
    for(int i=spos; i<n; i++, startpos++)
    {
      if(text[i] == text[startpos])
        c++;
      else 
        break;
    }
    
    return c;
  }
}
