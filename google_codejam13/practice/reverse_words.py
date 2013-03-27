def rev(s):
  tokens = s.split()
  if(len(tokens) == 1):
    return ''.join(tokens[0])
  return ' '.join(tokens[::-1])
  #print out

def main():
  f1 = open('B-large-practice.in', 'r')
  inputs = f1.readlines()
  sz = int(inputs[0])
  res = []
  for i in xrange(1, sz+1):
    res.append('Case #{0}: {1}\n'.format(i, rev(inputs[i])))
  f2 = open('out', 'w')
  for r in res:
    f2.write(r)
  f1.close()
  f2.close()
  return

if __name__ == '__main__':
  main()
