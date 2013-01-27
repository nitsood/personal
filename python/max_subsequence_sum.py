import sys

def find_max_subseq(array):
  s = []
  idx = []
  s.insert(0, array[0])
  for i in range(1, len(array)):
    e = max(s[i-1], s[i-1]+array[i], array[i])
    s.insert(i, e)
  return s[-1]

def test(got, expected):
  if got == expected:
    prefix = ' OK '
  else:
    prefix = '  X '
  print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))

def main():
  test(find_max_subseq([7, 8, -10, -20, 40]), 55)
  test(find_max_subseq([-7, -8, -10, -20, -40]), -7)
  test(find_max_subseq([-7, -8, 100, -20, -40]), 100)
  test(find_max_subseq([-7, -8, -10, -20, 40, 1]), 41)
  test(find_max_subseq([-7, -8, -10, -2, -99, -1]), -1)
  test(find_max_subseq([-7, -8, -10, 2, -99, 2]), 4)

if __name__ == '__main__':
  sys.exit(main())
