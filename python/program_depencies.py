import sys

def print_dependencies(prog_list, start):
  if(start not in prog_list):
    print "No dependency for "+start
  else:
    gen_dep(prog_list, [], start)
  return

def gen_dep(prog_list, seen, p):
  print p
  seen.append(p)
  if(p not in prog_list):
    return
  curr_deps = prog_list.get(p)
  n = len(curr_deps)
  for i in range(0, n):
    dep = curr_deps[i]
    if(dep in seen):
      continue
    else:
      gen_dep(prog_list, seen, dep)
  return

def main():
  prog_list = {}
  li = ['B', 'C', 'D']
  prog_list['A'] = li
  li = ['E', 'A']
  prog_list['B'] = li
  print_dependencies(prog_list, 'A')
  return

if __name__ == '__main__':
  sys.exit(main())
