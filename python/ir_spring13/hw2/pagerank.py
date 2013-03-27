import sys
import math
import json
import heapq
from collections import defaultdict
from collections import OrderedDict


"""user_names is a dictionary of user_id -> screen_name"""
user_names = OrderedDict()

"""user_names is a dictionary of user_id -> index_number"""
user_numbers = OrderedDict()

"""rev_user_numbers is a dictionary of index_number -> user_id"""
rev_user_numbers = OrderedDict()

incoming_links = defaultdict(set)
outgoing_links = defaultdict(set)

n_users = 0
tele = 0.1
precision = 0.00000001
pr_sum = 0.0


"""This class exposes API to do page ranking on users"""
class UserRanker:
  def __init__(self, f):
    self.filename = f
    print
    print 'Setting up page rank for users..'
    parse(self.filename)
    print 'Done'
    print 'Ranking users..'
    self.res = rank_users()
    print 'Done'
    print
    return
  def get_users_to_print(self):
    return self.res
  def get_ranked_users(self):
    normalized = {}
    keys = self.res.keys()
    for k in keys:
      tup = (user_names[k], self.res[k]/pr_sum)
      normalized[k] = tup
    return normalized


###########################################################


"""tokenize text and convert to lower case"""
def tokenize(text):
  tok = re.split(r'\W+', text, flags=re.UNICODE)
  lower_tok = []
  for t in tok:
    if(t is None or t == ''):
      continue
    lower_tok.append(t.lower())
  return lower_tok


def read_file(filename):
  global n_tweets
  f = open(filename, 'r')
  lines = f.readlines()
  f.close()
  n_tweets = len(lines)
  return lines


#########################################################


"""method that returns the user mentions as a dict"""
def get_users_mentioned(json_obj):
  mentions = json_obj['entities']['user_mentions']
  user_id = json_obj['user']['id']
  mentioned_users = {}
  for m in mentions:
    u = m['id']
    if(u == user_id): #self-link
      continue
    mentioned_users[u] = m['screen_name']
  return mentioned_users


"""method to compute the outgoing and incoming links"""
def parse_json(json_obj, tweet_ctr):
  u = json_obj['user']
  user_id = u['id']
  screen_name = u['screen_name']
  mentioned_users = get_users_mentioned(json_obj)
  
  if(len(mentioned_users) > 0):
    add_user(user_id, screen_name)

  for m in mentioned_users.keys():
    add_user(m, mentioned_users[m])  
    
    outgoing_links[user_id].add(m)
    if(user_id in incoming_links):
      pass
    else:
      incoming_links[user_id] = set()

    incoming_links[m].add(user_id)
    if(m in outgoing_links):
      pass
    else:
      outgoing_links[m] = set()
  return


"""method adds and creates a logical id for the user"""
def add_user(user_id, screen_name):
  global n_users
  if(user_id in user_names):
    pass
  else:
    user_names[user_id] = screen_name
    user_numbers[user_id] = n_users
    rev_user_numbers[n_users] = user_id
    n_users += 1
  return


"""method that ranks users using the linear iterative method"""
def rank_users():
  n = n_users
  ranked = defaultdict(float)
  fac = float(1)/n
  page_rank_old = [fac]*n
  page_rank_new = [0.0]*n
  user_ids = user_names.keys()
  ctr = 0
  while True:
    to_break = True
    for i in xrange(0, n):
      u = user_ids[i]
      i_links = incoming_links[u]
      if(len(i_links) == 0):
        page_rank_new[i] = tele/n
      else:
        x = 0.0
        for inc in i_links:
          idx = user_numbers[inc]
          y = len(outgoing_links[inc])
          x += page_rank_old[idx]/y
        x = tele/n + ((1-tele)*x)
        page_rank_new[i] = x
      if(math.fabs(page_rank_new[i]-page_rank_old[i]) > precision):
        to_break = False
    if(to_break == True):
      break
    page_rank_old[:] = page_rank_new
    ctr += 1
  
  #after computing page ranks, we need to find top 50
  global pr_sum
  for i in xrange(0, len(page_rank_new)):
    ranked[rev_user_numbers[i]] = page_rank_new[i]
    pr_sum += page_rank_new[i]
  return ranked
  

"""main parsing method for the json"""
def parse(filename):
  lines = read_file(filename)
  tweet_ctr = 1
  for line in lines:
    data = json.loads(line)
    parse_json(data, tweet_ctr)
    tweet_ctr += 1
  return


##########################################################


def print_results(ranked):
  print
  results = heapq.nlargest(50, ranked, key=ranked.get)
  print 'User Rankings (rank, user_id, screen_name) =========='
  print
  ctr = 1
  for r in results:
    print '{0}. {1} {2}'.format(ctr, r, user_names[r])
    ctr += 1
  print
  return


def main():
  n = len(sys.argv)
  if(n != 2):
    print 'usage: python part2.py <input_json_file>'
    sys.exit(1)
  filename = sys.argv[1]
  u = UserRanker(filename)
  print_results(u.get_users_to_print())
  return

if __name__ == '__main__':
  main()
