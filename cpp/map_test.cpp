#include<iostream>
#include<map>

using namespace std;

int compare_s(string x, string y)
{
  return x.compare(y);
}

int main(void)
{
  map<string, int> m;
  map<string, int>::iterator it;
  m["nitesh"] = 1;
  cout<<m.begin()->first<<":"<<m.begin()->second<<endl;
  m["nitesh"] = 2;
  for(it = m.begin(); it != m.end(); it++)
    cout<<it->first<<":"<<it->second<<endl;
  pair<map<string, int>::iterator, bool> ret;
  ret = m.insert(pair<string, int>("nitesh", 3));
  if(!ret.second)
    cout<<"not insert"<<endl;

  map<string, int>* mm = new map<string, int>();
  ret = mm->insert(pair<string, int>("nit", 4));
  if(ret.second)
    cout<<"insert"<<endl;
  it = mm->find("nit");
  if(it == mm->end())
    cout<<"NOT found"<<endl;
  else
    cout<<"found the key "<<it->first<<endl;
  return 0;
}
