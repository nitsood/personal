package pkg1;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class BackChain
{
  private List<String> kb = new LinkedList<String>();   
  private boolean trace = false;
  private boolean isNegativeLiteralHandler = false;
  
  private static final String PROMPT = "$> ";
  
  public static void main(String[] args)
  {
    //interactive command prompt
    Scanner sc = new Scanner(System.in);
    BackChain backChainer = new BackChain();
    
    while(true)
    {
      System.out.print(PROMPT);
      String cmd = sc.nextLine();
      
      if(cmd == null || cmd.trim().equals("\n") || cmd.trim().equals("")) //just to be safe
        continue;
      
      backChainer.parseAndExec(cmd);
    }
  }
  
 private void parseAndExec(String cmd)
 {
   cmd = cmd.trim();
   String split[] = cmd.split(" ");
   Command thisCommand = null;
    
   for(Command command : Command.values())
   {
     if(command.getCommandText().equals(split[0]))
     {
       thisCommand = command; //found a matching command keyword
       break;
     }
   }
    
   //assuming no wrong input, if no command text matches, then its a query
   String[] args = null;
   if(thisCommand == null)
   {
     thisCommand = Command.QUERY;
     args = new String[1];
     args[0] = split[0];
   }
   else
   {
     if(split.length > 1)
     {
       args = new String[split.length - 1];
       for(int i=1; i<split.length; i++)
         args[i-1] = split[i];
     }  
   }
    
    thisCommand.execute(this, args);
  }
  
  public void setKB(List<String> kb)
  {
    this.kb = kb;
  }
  
  public List<String> getKB()
  {
    return kb;
  }
  
  public void deleteKB()
  {
    kb.clear();
  }
  
  public void setTrace(boolean tr)
  {
    this.trace = tr;
  }
  
  public boolean getTrace()
  {
    return this.trace;
  }
  
  public void setNegativeLiteralHandler(boolean ng)
  {
    this.isNegativeLiteralHandler  = ng;
  }
  
  public boolean getNegativeLiteralHandler()
  {
    return isNegativeLiteralHandler;
  }
  
  public boolean isFact(String literal)
  {
    /*
     * there will be 2 checks here:
     * 
     * 1. if the literal is positive, then only a matching fact will be searched
     * 2. if the literal is negative, then we will handle it by trying to prove that its negation (-A) is false, by using 
     *     A as a query to the kb. 
     */
      
    if(!literal.startsWith("-"))
    {
      for(String s : kb)
      {
        if(s.equals(literal))
          return true;
      }
        
      return false;
    }
    else
    {
      Stack<String> goalStack = new Stack<String>();
      goalStack.push(literal.substring(1));
      boolean eval = Command.evaluate(clone(this, false, true), goalStack);
      
      if(getTrace())
      {
        if(eval) System.out.println("yes (negation-as-failure)");
        else System.out.println("fail (negation-as-failure)");
      }
      
      return !eval;
    }
  }
  
  public List<String> searchAll(String symbol)
  {
    List<String> matchingRules = new ArrayList<String>();
    for(String s : kb)
    {
      if(s.endsWith(symbol))
      {
        //this can be a fact or a rule
        if(s.equals(symbol))
          continue;
        else
          matchingRules.add(s);
      }
    }
    
    //nothing found
    return matchingRules;
  }
  
  public BackChain clone(BackChain src, boolean copyTrace, boolean isNlHandler)
  {
    BackChain newbc = new BackChain();
    newbc.setKB(src.getKB());
    newbc.setTrace(src.getTrace());
    newbc.setNegativeLiteralHandler(isNlHandler);
    
    return newbc;
  }
}
