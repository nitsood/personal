#include<vector>
#include<iostream>
#include<string.h>

using namespace std;

void reverse(char*, int);

int main(void)
{
  vector<int>* v = new vector<int>();
  v->push_back(1);
  cout<<"size: "<<v->size()<<endl;

  vector<string> vv(5);
  vector<int> vvv(6);
  //vv.reserve(5);
  vv.push_back("nit4");
  vv.push_back("nit4");
  vv.push_back("nit4");
  vv.push_back("nit4");
  vv.push_back("nit4");
  vv.push_back("nit4");
  vvv.insert(vvv.begin(), 55);
  cout<<"size: "<<vv.size()<<endl;
  cout<<"cap: "<<vv.capacity()<<endl;

  vector<string>::iterator it;
  for(it = vv.begin(); it != vv.end(); it++)
    cout<<"ele: "<<*it<<endl;

  cout<<vvv[0]<<endl;

  string s("nitesh");
  cout<<"len: "<<s.length()<<endl;
  cout<<s.substr(0, 3)<<endl;

  //string ns(reverse(s.c_str(), s.length()));
  char* buf = new char[s.length()+1];
  strcpy(buf, s.c_str());
  reverse(buf, s.length());
  string ns(buf);
  cout<<ns<<endl;
  cout<<"new string len"<<ns.length()<<endl;
}

void reverse(char* str, int len)
{
  char p;
  int i, j;
  for(i=0, j=len-1; i <= j; i++, j--)
  {
    p = str[i];
    str[i] = str[j];
    str[j] = p;
  }
  str[len] = '\0';
  //return str;
}
