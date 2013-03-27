package pkg1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Proportionalizer
{
  private static final String DOMAIN = "domain";
  private static final String FORALL = "forall";
  
  public static void main(String[] args)
  {
    try
    {
      String filename = "/home/nitsood/Work/eclipse/java/workspace/AI_homework_6/src/pkg1/rules";
    
      File input = new File(filename);
      FileInputStream fis = new FileInputStream(input);
      Scanner sc = new Scanner(fis);
    
      Domains domains = new Domains();
      List<String> rules = new ArrayList<String>();; //also includes facts
      
      while(sc.hasNextLine())
      {
        /*
         * if the string starts with 'domain' then its a domain stmt;
         * else its either a fact or a rule
         */
        
        String s = sc.nextLine();
        if(s.trim().equals("") || s.startsWith("#"))
          continue;
        else if(s.startsWith(DOMAIN))
        {
          String[] sp = s.split(" ");
          List<String> vals = Arrays.asList(sp);
          domains = domains.addDomain(vals.get(1), vals.subList(2, vals.size()));
        }
        else
          rules.add(s);
      }
      
      List<String> x = proportionalizeQuantifiedRules(rules, domains);
      
      SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ss");
      String suff = sdf.format(new Date());
      
      //write these rules to a timestamped file
      filename = "/home/nitsood/Work/eclipse/java/workspace/AI_homework_6/src/pkg1/prop_"+suff+".txt";
      
      File output = new File(filename);
      FileOutputStream fos = new FileOutputStream(output);
      OutputStreamWriter wr = new OutputStreamWriter(fos);
      for(String a : x)
      {
        wr.write(a+"\n");
        wr.flush();
      }
      
      System.out.println("Propositional logic rules written to file "+filename);
      fis.close();
      fos.flush();
      fos.close();
    }
    catch(FileNotFoundException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
    catch(IOException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
  }  
  
  private static List<String> proportionalizeQuantifiedRules(List<String> rules, Domains d)
  {
    /*
     * quantified: those that begin with forall
     * unquantified: those that do not
     */
    List<String> props = new ArrayList<String>();
    
    for(String r : rules)
    {
      if(r.startsWith(FORALL))
        props.addAll(handleQuantified(r, d)); //quants.add(r);
      else
        props.add(handleUnquantified(r)); //unquants.add(r);
    }
    
    return props;
  }
  
  private static String handleUnquantified(String s)
  {
    String converted = String.valueOf(s);
    converted = converted.trim();
    converted = converted.replaceAll("\\(\\)","");
    converted = converted.replaceAll("[(|,]", "_");
    converted = converted.replaceAll("[)]", "");
    
    return converted;
  }
  
  private static List<String> handleQuantified(String r, Domains doms)
  {
    List<String> converted = new ArrayList<String>();
    String[] spl1 = r.split(":");
    String onlyRule = spl1[1].trim();
    String[] spl2 = spl1[0].split("\\s");
      
    //hence, all the variables of this rule are contained in spl2[1..n]
    //spl[1] contains the rule, without the quantifier, i.e. 'forall' part
      
    List<String> varsInThisRule = new ArrayList<String>();
    for(int i=1; i<spl2.length; i++)
      varsInThisRule.add(spl2[i]);
       
    //now generate all possible substitutions of the variables using their domains
    List<String> subs = new ArrayList<String>();
    String varString = combine(varsInThisRule, "");
    subs = gen(varString, "", subs, doms);
     
    for(String s : subs)
    {
      String[] sp = s.split(", ");
      Theta th = new Theta(varsInThisRule, Arrays.asList(sp));
      converted.add(substitute(varsInThisRule, onlyRule, th));
    }
 
    //now for each 'converted' rule, replace the braces and commas with underscore
    List<String> replaced = new ArrayList<String>();
    for(String c : converted)
      replaced.add(handleUnquantified(c));
    
    return replaced;
  }
  
  private static String substitute(List<String> vars, String rule, Theta substitution)
  {
    //vars is the variables contained in this quantified rule
    //rule is the rule itself
    //substitution is the set of assignments for the variables
    
    for(String var : vars)
    {
      //for each var replace the var in the rule
      rule = rule.replaceAll(var, substitution.getVal(var));
    }
    
    return rule;
  }
  
  private static String combine(Collection<String> coll, String delim)
  {
    StringBuilder st = new StringBuilder("");
    for(String c : coll)
      st = st.toString().equals("") ? st.append(c) : st.append(delim).append(c);
      
    return st.toString();
  }
  
  /*
   * input: a concatenated string of the variables e.g. AB or ABC
   * s: initially empty, it is built up during the recursion
   * substitutions: initially empty, it is built up during the recursion
   * d: domains for the variables concerned
   */
  private static List<String> gen(String input, String s, List<String> substitutions, Domains d)
  {
    if(input.equals(""))
    {
      substitutions.add(s);
      return substitutions;
    }
    
    String c = String.valueOf(input.charAt(0));
    List<String> vals = d.getDomainValues(c);
    
    if(vals == null)
      throw new RuntimeException("unable to get domain of the variable "+c);
    
    for(String v : vals)
      substitutions = gen(input.length() == 1 ? "" : input.substring(1), 
          s.equals("") ? s.concat(v) : s.concat(", "+v),
          substitutions,
          d);
    
    return substitutions;
  }
  
  /*
   * represents a domain for a given variable
   */
  public static class Domains
  {
    private Map<String, List<String>> doms = new HashMap<String, List<String>>();
    
    Domains(){}
    
    Domains addDomain(String var, List<String> values)
    {
      doms.put(var, values);
      return this;
    }
    
    List<String> getDomainValues(String var)
    {
      return doms.get(var);
    }
    
    Map<String, List<String>> getAllDomains()
    {
      return doms;
    }
    
    static void printDomains(Domains d)
    {
      System.out.print("Printing domains: ");
      for(Map.Entry<String, List<String>> e : d.getAllDomains().entrySet())
        System.out.println(e.getKey()+": "+e.getValue());
    }
    
    int size()
    {
      return doms.size();
    }
  }
  
  /*
   * represents a variable assignment
   */
  public static class Theta
  {
    private Map<String, String> substitutions = new HashMap<String, String>();
    
    Theta(List<String> vars, List<String> vals)
    {
      for(int i=0; i<vars.size(); i++)
        substitutions.put(vars.get(i), vals.get(i));
    }
    
    public String getVal(String var)
    {
      return substitutions.get(var);
    }
  }
}
