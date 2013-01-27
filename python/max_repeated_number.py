import sys

def find_max_repeated(array):
  if(array == None or len(array) == 0):
    return None
  array = sorted(array)
  max_count = 0
  max_so_far = 0
  current_max = 0
  i = 1
  while(i < len(array)):
    if(array[i-1] != array[i]):
      current_max = i
      i += 1
    else:
      while(i < len(array) and array[i-1] == array[i]):
        i += 1
        continue
      c = i-current_max+1
      if(c > max_count):
        max_count = c
        max_so_far = current_max
  return array[max_so_far]

def test(got, expected):
  if got == expected:
    prefix = ' OK '
  else:
    prefix = '  X '
  print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))

def main():
  test(find_max_repeated([-4, 1, 3, 4, 3, 6, 1, 3]), 3)
  test(find_max_repeated([-4, 1, 3, 4, 3, 6, 1, 3, 1]), 1)
  test(find_max_repeated([-1, -1, -1, -1]), -1)
  test(find_max_repeated([1, 2, 3, 4]), 1)
  test(find_max_repeated([-1, -1, -1, -1]), -1)

if __name__ == '__main__':
  sys.exit(main())
