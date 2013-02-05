import pickle
import pickletools
from collections import defaultdict

d = defaultdict(int)
d[1] = 3
d[2] = 4

n = {}
n[1] = 'one'

def p():
  f = open('t.pick', 'w')
  pickle.dump(d, f)
  f.close()

def main():
  p()

if __name__ == '__main__':
  main()
