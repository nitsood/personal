package pkg1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public enum Command
{
  LOAD
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      try
      {
        String filename = args[0];
        List<String> kb = new ArrayList<String>();
      
        File input = new File("/home/nitsood/Work/eclipse/java/workspace/AI_homework_6/src/pkg1/"+filename);
        
        FileInputStream fis = new FileInputStream(input);
        Scanner sc = new Scanner(fis);
      
        while(sc.hasNextLine())
          kb.add(sc.nextLine().trim());
      
        if(kb.size() == 0)
        {
          System.out.println("KB is empty. Exiting.");
          System.exit(0);
        }
      
        bc.setKB(kb);
      }
      catch(FileNotFoundException e)
      {
        e.printStackTrace();
        System.exit(1);
      }
    }
    
    @Override
    public String getCommandText()
    {
      return "load";
    }
  },
  SHOW
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      List<String> kb = bc.getKB();
      for(String st : kb)
        System.out.println(st);
    }
    
    @Override
    public String getCommandText()
    {
      return "show";
    }
  },
  CLEAR
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      bc.deleteKB();
    }
    
    @Override
    public String getCommandText()
    {
      return "clear";
    }
  },
  QUIT
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      System.out.println("Bye");
      System.exit(0);
    }
    
    @Override
    public String getCommandText()
    {
      return "quit";
    }
  },
  TRACE
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      System.out.println("System trace on");
      bc.setTrace(true);
    }
    
    @Override
    public String getCommandText()
    {
      return "trace";
    }
  },
  NOTRACE
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      System.out.println("System trace off");
      bc.setTrace(false);
    }
    
    @Override
    public String getCommandText()
    {
      return "notrace";
    }
  },
  QUERY
  {
    @Override
    public void execute(BackChain bc, String ... args)
    {
      Stack<String> goalStack = new Stack<String>();
      goalStack.push(args[0]);
      boolean res = evaluate(bc, goalStack);
      
      if(res) System.out.println("yes");
      else System.out.println("fail");
    }
    
    @Override
    public String getCommandText()
    {
      return ""; //there is no text for this command
    }
  },
  NEXT_MOVE
  {
    /*
     * this command is only for tic-tac-toe FOL
     */
    @Override
    public void execute(BackChain bc, String ... args)
    {
      String plyr = args[0];
      String template = "play_m_n_o";
      List<String> queries = new ArrayList<String>();
      for(int i=1; i<=3; i++)
      {
        for(int j=1; j<=3; j++)
        {
          String t = new String(template);
          t = t.replaceAll("m", plyr);
          t = t.replaceAll("n", String.valueOf(i));
          t = t.replaceAll("o", String.valueOf(j));
          queries.add(t);
        }
      }
     
      //System.out.println("Queries generated: ");
      //for(String q : queries) System.out.println(q);
      //System.out.println();
      
      //bc.setTrace(false);
      for(String q : queries)
      {
        Stack<String> goalStack = new Stack<String>();
        goalStack.push(q);
        boolean res = evaluate(bc, goalStack);
        
        if(res)
          System.out.println(q);
        //if(res) System.out.println("yes");
        //else System.out.println("fail");
      }
    }
    
    @Override
    public String getCommandText()
    {
      return "nextmove"; //there is no text for this command
    }
  }
  ;
  
  public abstract void execute(BackChain bc, String ... args);
  public abstract String getCommandText();
  
  public static void printIfTrace(BackChain bc, Stack<String> goalStack)
  {
    //this method will print the stack if the trace is on
    if(bc.getTrace())
    {
      if(bc.getNegativeLiteralHandler())
        System.out.println(goalStack+" (negation-as-failure)");
      else
        System.out.println(goalStack);
    }
  }
  
  /*
   * the main static method that evaluates a symbol as a query against the knowledge base
   */
  public static boolean evaluate(BackChain bc, Stack<String> goalStack)
  {
    printIfTrace(bc, goalStack);
    
    if(goalStack.empty())
      return true;
    
    String q = goalStack.pop();
    if(bc.isFact(q))
    {
      return evaluate(bc, goalStack);
    }
    else
    {
      List<String> matchingRules = bc.searchAll(q);
      if(matchingRules.size() == 0)
        return false;
      
      boolean result = false;
      for(String rule : matchingRules)
      {
        int prevSize = goalStack.size();
        List<String> antecedents = getAntecedents(rule);
        for(String a : antecedents)
          goalStack.push(a);
        
        result = evaluate(bc, goalStack);
        
        if(result) return result;
        else 
        {
          int currSize = goalStack.size();
          while(!goalStack.empty() && (currSize != prevSize))
          {
            goalStack.pop();
            currSize = goalStack.size();
          }
          //for(String a : antecedents)
          //  goalStack.remove(a);
        }
      }
      
      return result;
    }
  }
  
  public static List<String> getAntecedents(String rule)
  {
    List<String> ants = new ArrayList<String>();
    
    //first get the antecedent string by splitting with >
    String[] antecedentString = rule.split(">");
    
    //next split with ^
    String[] antecedents = antecedentString[0].split("\\^");
    for(String s : antecedents)
      ants.add(s.trim());
        
    return ants;
  }
}
