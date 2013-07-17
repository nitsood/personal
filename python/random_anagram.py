import random

def anagram(s):
  if s is None or len(s) == 0 or len(s) == 1:
    return s
  li = []
  for i in xrange(0, len(s)):
    li.append(s[i])
  for i in xrange(0, len(s)):
    r1 = get_rand(0, len(s)-1)
    r2 = get_rand(0, len(s)-1)
    temp = li[r1]
    li[r1] = li[r2]
    li[r2] = temp
  return ''.join(li)

def get_rand(begin, end):
  return random.randint(begin, end)

def main():
  print(anagram('manage'))
  print(anagram('i am an aggie'))
  print(anagram('mmmmm'))
  print(anagram('g'))
  print(anagram('23abc'))
  print(anagram(None))
  return

if __name__ == '__main__':
  main()
