def longest_uniq_sub(s):
  seen = {}
  i = 0
  max_len = 1
  max_start = 0
  seen[s[0]] = 1
  for j in range(1, len(s)):
    if(s[j] in seen):
      i += 1
    else:
      seen[s[j]] = 1
      if(j-i+1 > max_len):
        max_len = j-i+1
        max_start = i
  return s[max_start:max_start+max_len]

def test(got, expected):
  if got == expected:
    prefix = ' OK '
  else:
    prefix = '  X '
  print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))

def main():
  test(longest_uniq_sub('abca'), 'abc')
  test(longest_uniq_sub('niteshnqxyz'), 'iteshnqxyz')
  test(longest_uniq_sub('abcab'), 'abc')
  test(longest_uniq_sub('abcabc'), 'abc')
  test(longest_uniq_sub('dxabcbba'), 'dxabc')
  test(longest_uniq_sub('aaaaaa'), 'a')
  test(longest_uniq_sub('bbbaaa'), 'ba')
  test(longest_uniq_sub('dcdcdcdc'), 'dc')

if __name__ == '__main__':
  main()
