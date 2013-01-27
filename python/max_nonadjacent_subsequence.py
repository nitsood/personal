import sys

def find_max_subseq_nonadjacent(array):
  w = []
  w.insert(0, array[0])
  w.insert(1, max(array[0], array[1]))
  for i in range(2, len(array)):
    e = max(w[i-1], w[i-2]+array[i])
    w.insert(i, e)
  return w

def main():
  list = [7, 2, 3, 9, 20]
  print find_max_subseq_nonadjacent(list)

if __name__ == '__main__':
  sys.exit(main())
