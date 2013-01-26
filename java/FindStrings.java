package interviewstreet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class FindStrings
{
  public static void main(String args[])
  {
    String s = "abcdefghijklmnopqrstuvwxyz";
    Random r = new Random();
    StringBuilder sb = new StringBuilder("");
    List<String> input = new ArrayList<String>(50);
    int sz = 2000;
    for(int j=0; j<50; j++)
    {
      for(int i=0; i<sz; i++)
      {
        int k = r.nextInt(26);
        sb.append(s.charAt(k));
      }
      String x = sb.toString();
      input.add(x);
    }
    //String x = "crsaugvkijcbikmlntpmgfsnhpredojdulqpilgelvhvafmdoszwptcloztcwczpndwkhmjwkuuebjkyxhboqxtzoxlgahiigrwuoxoxzlddngdmniyhrqrqnphyytuwtmukczwhxojdcykdtlohqyzsifjtbyvbcuxkuhivkvadewgnodvnnclrhcwywnmliqwejoxxwbfxtirnzbynoabuzomoxxqjxucwmbxtowndtkatrnyvdfywpbnivtkvudlveqjctmazmaoiclgtvcqhatiosdmnepbafmrlflngfdpwxmuxmybjuvxqfawltjvjnlziiqmlawnhsxkbaofvjcughbeooxuckaoiplcugnirolbkemevvnfrgpzdyxvuvhrpxpdmvsjbyzrpzntvfuxgwjjzscoaamvdhhalcddxbibkcyyeuuxdwxiwqawesrvlkwrmxfiluwinjvojjssytbxmrnyjvhbrelkbyjpznaplcnoymxoxenxqtplzrrnqzmdtwlgneodwwwgabxdwbvffozhdnoshfdzyxfwiprdtykjudsfqymqtcknqmgyuydublebtcmtnmylinidpkzhdbducbwdmpabrvchjdvuupfbiafocgidpvgrdfnbscingztogzgbhtihsjfqdrnfhowsshsoezwxstpwpzuddjjelxzdzarrddweodyueaydozaxkajqwvtykpqlmjatatuwczgmzpnguirjxghidwbvnicwerzjuiazwwhrbygeqmhzopqnpohlteivjaypaurtiolynwifknnzyeiceyijnkezughkxesmlritnpevtojnipazyfdrlzkkuszuuiahwbhmcygqxmjsuqyttqhvcsbdrhvgmppkhzdqdzyaliicaluyvzjcxrtgratbsagqzpcciafkmfhoqdcdcztckpnzswzjmfbevmkfwfscdnazunvdozkjzibgjmaphytjzutmjxfsejnnzrhkrstiqyekdmoixbolmqthvrexouikbgekkbxspceqfjswocvuuddzxubspbcwkjpyqlxmignpeywyfptlegjxnqsreawfhyhsqbezexkffuyxtwnuvqblsffailmcsdefkpjjtkqpimnifezxdnocsuzfgcnoxaehcbxbgaghqtfscicrwlmetufbgcerfuzolgyxjiwjbokoqfymfaiiurxhmiiroqbhsgcbphqbgdqaolhjzcdfddbqedlziuoqoyaexnjlnzfpjjkuvfphzbgweptmmbczdzhfwekvugtexwthzhmwuzimakehxjvkcwczigyhmeziruticavexeqatcahkeqbknvvjebwrtrjpcorkjgnaysgszfdxvodkrwycyiwwvwhwyazdttetjtbbofmptmyudqmelbbfrpvefqmsdnysiefbcpybndxwvboakcirqynsidbogtdbazokbcxtfbfiwrdaulldkwofmzokhaaxnwpidqzdrllpeyafeetcolaycucfoozkiithxtincbhtkdhdsffzthqwylzkwujerleovlweztaseobvftlmulxclutwftgiqkxmbbiuadlbjikmpalqegmvgbhdlplpmoiqjudwjdducdsextlhbhaqaxzxvamcjakhocuwwiitxthswmdqexutbgdmifqnnxisqxmrllakcrnwzhdtnqdubgtucelthwmuhswkvnmczraxnhobnenezbvucdfcnzyjlkslajeqcldxpxjdftlxsrecbnxbpniodthmrtsliclzdcacoopqvndrwazidlbklwrweandfqtwdapgyvmnatoxlwfoolgtzuhvvrcxwxaczzndzjdujcznhccexzbgdgmskisldjdchfzmeiqydkiilalgwdjhxjxzrfpubvchbawwlqlokspgguzicgrpyyvkngfuuaxgvqmybvqqarfbhfwminuyexwk";
    //String x = "aab";
    
    for(String x : input)
    {
      //System.out.println(x);
    
      Set<String> subs = new TreeSet<String>();
    
      long start = System.currentTimeMillis();
      for(int i=0; i<x.length(); i++)
      {
        for(int j=i; j<x.length(); j++)
          subs.add(x.substring(i, j+1));
      }
    
    //List<String> sublist = new ArrayList<String>();
    //sublist.addAll(subs);
    //Collections.sort(sublist);
      long end = System.currentTimeMillis();
      System.out.println("No of unique substrings: "+subs.size());
      int t = (int) ((end - start)/1000);
      System.out.println("Time: "+t);
      //System.out.println(subs);
    }
  }
}