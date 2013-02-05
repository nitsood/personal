import pickle
import pickletools
from collections import defaultdict

x = defaultdict(int)
y = {}

def r():
  global x, y #need to decl global in each function that tries to modify global var
  f = open('t.pick', 'r')
  x = pickle.load(f)
  f.close()

def pr():
  print x

def main():
  r()
  pr()

if __name__ == '__main__':
  main()
