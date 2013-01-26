package interviewstreet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TrieImpl
{
  private Node root;
  private int sz;
  private int numNodes;
  
  public TrieImpl()
  {
    root = new Node('*');
    sz = 0;
    numNodes = 0;
  }
  
  public void insert(String str)
  {
    insert(str.toCharArray());
  }
  
  public void insert(char str[])
  {
    Node curr = root;
    //if(str.length == 0)
    //  curr.setEndOfWord();
    //else
    //{
      for(int i=0; i<str.length; i++)
      {
        Node child = curr.getChild(str[i]);
        if(child != null)
          curr = child;
        else
        {
          System.out.println("adding node"+numNodes);
          curr.addChild(new Node(str[i]));
          ++numNodes;
          curr = curr.getChild(str[i]);
        }
        
        //if(str.length-1 == i)
        //  curr.setEndOfWord();
      }
    //}  
    ++sz;
  }
  
  public boolean find(String s)
  {
    return find(s.toCharArray());
  }
  
  public boolean find(char s[])
  {
    Node curr = root;
    if(curr == null)
      return false;
    
    for(int i=0; i<s.length; i++)
    {
      Node child = curr.getChild(s[i]);
      if(child != null)
        curr = child;
      else
        return false;
    }
    
    return true;
    //if(curr.isEndOfWord()) return true;
    //else return false;
  }
  
  public int size()
  {
    return sz;
  }
  
  public int nodes()
  {
    return numNodes;
  }
  
  public static void main(String args[]) throws IOException
  {
    TrieImpl trie = new TrieImpl();
    File input = new File("/home/nitsood/Work/eclipse" +
    		"/java/workspace/CodeChef/src/interviewstreet/single_string_test");
    FileInputStream fis = new FileInputStream(input);
    InputStreamReader ir = new InputStreamReader(fis);
    BufferedReader br = new BufferedReader(ir);
    
    List<String> naive = new ArrayList<String>();
    String t = br.readLine();
    /*while(t != null)
    {
      trie.insert(t);
      //naive.add(t);
      t = br.readLine();
    }*/
    for(int i=0; i<t.length(); i++)
      trie.insert(t.substring(i));
    
    //String x = "dcnufkwewrbzzhxpglhipaotuqhkfzgnjk";
    String x = "nopbu";
    long start = System.nanoTime();
    long end = 0L;
    /*for(String s : naive)
    {
      if(s.equals(x))
      {
        end = System.nanoTime();
        System.out.println("Found");
        break;
      }
    }*/
    boolean b = trie.find(x);
    if(b) { end = System.nanoTime(); System.out.println("Found"); }
    System.out.println("Time: "+(end-start)/1000+" microsec");
    
    /*trie.insert("bat");
    trie.insert("ball");
    trie.insert("balls");
    trie.insert("cat");
    trie.insert("catnap");
    trie.insert("dog");
    */
    System.out.println("Nodes: "+trie.nodes());
    System.out.println("Number of strings in trie: "+trie.size());
    //System.out.println(trie.find("catnap"));
    
    br.close();
    ir.close();
    fis.close();
  }
  
  public static class Node
  {
    private char letter;
    private BitSet endOfWord;
    private Collection<Node> children;
    
    Node(char l)
    {
      letter = l;
      children = new LinkedList<Node>();
      endOfWord = new BitSet(1);
    }
    
    Node getChild(char l)
    {
      if(children != null)
      {
        for(Node c : children)
          if(c.letter == l) return c;
      }
      
      return null;
    }
    
    char getLetter()
    {
      return letter;
    }
    
    /*void setEndOfWord()
    {
      endOfWord.set(0);
    }*/
    
    /*boolean isEndOfWord()
    {
      return endOfWord.get(0);
    }*/
    
    void addChild(Node child)
    {
      children.add(child);
    }
  }
}