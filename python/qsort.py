def qsort(array):
  return qsort_internal(array, 0, len(array)-1)

#find a way to overload methods!
def qsort_internal(array, left, right):
  if(left < right):
    pivot_idx = (left+right)/2
    pivot_n_idx = partition(array, left, right, pivot_idx)
    array = qsort_internal(array, left, pivot_n_idx-1)
    array = qsort_internal(array, pivot_n_idx+1, right)
  return array

def partition(array, l, r, pi):
  pivot = array[pi]
  array = swap(array, pi, r)
  store = l
  for i in range(l, r):
    if(array[i] <= pivot):
      array = swap(array, store, i)
      store += 1
  array = swap(array, store, r)
  return store
 
#try to write a better swap function that modifies the list but 
#does not return it; try to see if its possible to do by reference rather
#than copy
def swap(array, n1, n2):
  a = array[n1]
  b = array[n2]
  a = a + b
  b = a - b
  a = a - b
  array[n1] = a
  array[n2] = b
  return array

def main():
  array = [1, 2, 3, 4]
  array = qsort(array)
  print array

if __name__ == '__main__':
  main()
