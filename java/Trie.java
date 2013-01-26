package interviewstreet;

import java.nio.CharBuffer;

public interface Trie
{
  public void insert(CharBuffer string);
  public boolean find(CharBuffer string);
  public void print();
  public int size();
  public int nodes();
}
