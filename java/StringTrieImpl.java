package interviewstreet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StringTrieImpl
{
  public static void main(String args[]) throws IOException
  {
    PatriciaTrie trie = new PatriciaTrie();
    File input = new File("/home/nitsood/Work/eclipse" +
        "/java/workspace/CodeChef/src/interviewstreet/single_string_test");
    FileInputStream fis = new FileInputStream(input);
    InputStreamReader ir = new InputStreamReader(fis);
    BufferedReader br = new BufferedReader(ir);
    
    List<String> naive = new ArrayList<String>();
    //String t = br.readLine();
    String t = "aabbaa";
    /*while(t != null)
    {
      trie.insert(t);
      //naive.add(t);
      t = br.readLine();
    }*/
    for(int i=t.length()-1; i>=0 ; i--)
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
    //boolean b = trie.find(x);
    //if(b) { end = System.nanoTime(); System.out.println("Found"); }
    //System.out.println("Time: "+(end-start)/1000+" microsec");
    
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
  
  public static class PatriciaTrie implements Trie
  {
    private Node root;
    private int sz;
    private int numNodes;
    
    PatriciaTrie()
    {
      root = new Node("*");
      sz = 0;
      numNodes = 1;
    }
    
    @Override
    public void insert(CharBuffer string)
    {
      insert(string.toString());    
    }

    private void insert(String x)
    {
      System.out.println("Trying to insert the string "+x);
      Node curr = root;
      int elementsFound = 0;
      
      while(curr != null && !curr.isLeaf() && elementsFound < x.length())
      {
        Node child = curr.getChild(x, elementsFound);
        if(child != null)
        {
          System.out.println("match found: "+child.getSequence()+", moving to the child");
          curr = child;
          elementsFound += child.getSequence().length();
        }
        else
        {
          System.out.println("no match found, current node: "+curr.getSequence());
          curr.addChild(x, elementsFound);
          ++numNodes;
          break;
        }
      }
    }
    
    @Override
    public boolean find(CharBuffer string)
    {
      return find(string.toString());
    }

    /*
     * Finding a string in a Patricia trie. 
     * Algorithm: http://en.wikipedia.org/wiki/Radix_tree
     * 
     * However this implementation does not use any separate Edge class. All logic and string 
     * sequences are within Nodes.
     */
    private boolean find(String x)
    {
      Node curr = root;
      int elementsFound = 0;
      
      while(curr != null && !curr.isLeaf() && elementsFound < x.length())
      {
        Node child = curr.getChild(x, elementsFound);
        if(child != null)
        {
          curr = child;
          elementsFound += child.getSequence().length();
        }
        else curr = null; //end loop
      }
      
      if(curr != null && elementsFound == x.length()) return true;
      else return false;
    }
    
    @Override
    public int size()
    {
      return sz;
    }

    @Override
    public int nodes()
    {
      return numNodes;
    }
    
    @Override
    public void print()
    {
      Node curr = root;
      //final String INDENT = "  ";
      System.out.println(curr.getSequence());
      
      while(curr != null)
      {
        
      }
    }
  }
  
  public static class Node
  {
    //private char sequence[];
    private String sequence;
    private Collection<Node> children;
    private BitSet isLeaf;
    
    Node(String l)
    {
      sequence = l;
      children = new LinkedList<Node>();
      isLeaf = new BitSet(1);
    }
    
    Node getChild(String x, int elementsFound)
    {
      if(children != null)
      {
        for(Node c : children)
        {
          String t = Node.getSuffix(x, elementsFound);
          if(t.startsWith(c.getSequence())) return c;
        }
      }
      
      return null;
    }
    
    Node getLongestMatchingPrefixChild(String x, int elementsFound)
    {
      if(children != null)
      {
        Node match = null;
        int longestPrefix = 0;
        
        for(Node c : children)
        {
          String t = Node.getSuffix(x, elementsFound);
          String label = getSequence();
          int prefix = 0;
          
          for(int i=0; i<label.length(); i++)
            if(label.charAt(i) == t.charAt(i)) ++prefix;
            else break;
          
          if(prefix > longestPrefix)
          {
            longestPrefix = prefix;
            match = c;
          }
        }
        
        return match;
      }
      
      return null;
    }
    
    String getSequence()
    {
      return sequence;
    }
    
    void setSequence(String s)
    {
      sequence = s;
    }
    
    static String getSuffix(String source, int elementsFound)
    {
      return source.substring(elementsFound);
    }
    
    void addChild(String x, int elementsFound)
    {
      Node match = null;
      int longestPrefix = 0;
      if(children != null)
      {
        for(Node c : children)
        {
          System.out.println("Analyzing child: "+c.getSequence());
          String t = Node.getSuffix(x, elementsFound);
          String label = c.getSequence();
          int prefix = 0;
          int limit = Math.min(label.length(), t.length());
          
          for(int i=0; i<limit; i++)
            if(label.charAt(i) == t.charAt(i)) ++prefix;
            else break;
          
          System.out.println("common prefix: "+prefix);
          if(prefix > longestPrefix)
          {
            longestPrefix = prefix;
            match = c;
          }
        }
      }
      
      if(match != null)
      {
        //now we have to split the node
        System.out.println("Splitting the node: "+match.getSequence());
        Node curr = match;
        String pr = curr.getSequence().substring(0, longestPrefix);
        String remain = curr.getSequence().substring(longestPrefix);
        curr.setSequence(pr);
        Node newchild = new Node(remain);
        System.out.println("Added the child "+remain+" to the parent "+curr.getSequence());
        curr.children.add(newchild);
        
      }
      else
      {
        System.out.println("Added the child "+Node.getSuffix(x, elementsFound)+" to the parent "+getSequence());
        children.add(new Node(Node.getSuffix(x, elementsFound)));
      }
    }
    
    private boolean isLeaf()
    {
      return isLeaf.get(0);
    }
    
    private void setLeaf()
    {
      isLeaf.set(0);
    }
  }
}