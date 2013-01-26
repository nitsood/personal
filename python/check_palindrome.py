def check_pal(n):
  if(n < 10):
    return 'true'
  num = n
  digits = find_digits(num)
  while(num != 0):
    divider = 10**(digits-1)
    last_digit = num%10
    first_digit = num/divider
    chopper = divider*first_digit
    if(first_digit != last_digit):
      return 'false'
    num %= chopper #chop the first digit
    num /= 10 #chop the last digit
    digits -= 2
  return 'true'

def find_digits(n):
  num = n
  ctr = 0
  while(num != 0):
    num /= 10
    ctr += 1
  return ctr

def test(got, expected):
  if got == expected:
    prefix = ' OK '
  else:
    prefix = '  X '
  print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))

def main():
  test(check_pal(112211), 'true')
  test(check_pal(11221), 'false')
  test(check_pal(1), 'true')
  test(check_pal(88), 'true')
  test(check_pal(29), 'false')
  test(check_pal(999999), 'true')
  test(check_pal(112311), 'false')

if __name__ == '__main__':
  main()
