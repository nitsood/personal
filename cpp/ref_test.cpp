#include<iostream>

using namespace std;

int main(void)
{
  int a = 2;
  int& ref = a;
  int b = 3;
  ref = 4;

  cout<<a;
}
