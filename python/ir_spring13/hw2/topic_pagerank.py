import re
import sys
import math
import json
from collections import defaultdict
from collections import OrderedDict

n_users = 0
n_groups = 16

"""user_names is a dictionary of user_id -> screen_name"""
user_names = OrderedDict()

"""user_names is a dictionary of user_id -> index_number"""
user_numbers = OrderedDict()

"""rev_user_numbers is a dictionary of index_number -> user_id"""
rev_user_numbers = OrderedDict()

incoming_links = defaultdict(set)
outgoing_links = defaultdict(set)

label_dist = defaultdict(set)

lda_dist = defaultdict(set)
lda_dist["Technology"]={"scientist","science","tech","technical","software","computers","computer","web","nerd","geek","it","mars","nasa","technology","developer","programming", "algorithm"}
lda_dist["Social"]={"love","lover","college","twitter","tweets","people","world","family","father","mother","wife"}
lda_dist["News"]={"sports","games","news","web","media","journalism","editor"}
lda_dist["Arts"]={"arts","movies","music","paint","musician","director","blogger","writer","producer","guitar", "theater"}

tele = 0.1
precision = 0.00001


"""tokenize a tweet and convert to lower case"""
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


def parse_json(json_obj, tweet_ctr):
  u = json_obj['user']
  user_id = u['id']
  screen_name = u['screen_name']
  desc = ''
  if('description' in u):
    desc = u['description']

  add_desc_user(user_id, desc, False)
  mentioned_users = get_users_mentioned(json_obj)
  
  if(len(mentioned_users) > 0):
    add_user(user_id, screen_name, desc, False)

  for m in mentioned_users.keys():
    add_user(m, mentioned_users[m], '', True)  
    
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
def add_user(user_id, screen_name, desc, is_mentioned):
  global n_users
  if(user_id in user_names):
    pass
  else:
    user_names[user_id] = screen_name
    user_numbers[user_id] = n_users
    rev_user_numbers[n_users] = user_id
    n_users += 1
  return


def add_desc_user(user_id, desc, is_mentioned):
  if(is_mentioned == False and desc == ''):
    label_dist['Miscellaneous'].add(user_id)
    return
  elif(is_mentioned == True):
    return
  desc_tokens = tokenize(desc)
  found = False
  for d in desc_tokens:
    for k in lda_dist.keys():
      if(d in lda_dist[k]):
        found = True
        label_dist[k].add(user_id)
  if(found == False):
    label_dist['Miscellaneous'].add(user_id)
  return


def remove_dangling():
  global label_dist
  ins = set(incoming_links.keys())
  for k, v in label_dist.iteritems():
    x = v.intersection(ins)
    label_dist[k] = x
  return


def topic_rank_users():
  groups = label_dist.keys()
  gr_no = 0
  for g in groups:
    rank_users(g, label_dist[g], len(label_dist[g]))
  return


def rank_users(gr, group_users, n):
  ranked = defaultdict(float)
  start = 1.0000
  page_rank_old = [0.0000]*n_users
  for us in group_users:
    page_rank_old[user_numbers[us]] = start
  
  page_rank_new = [0.0000]*n_users
  user_ids = user_names.keys()
  ctr = 0
  print 
  while True:
    to_break = True
    for i in xrange(0, n_users):
      u = user_ids[i]
      if(u not in group_users):
        continue
      i_links = incoming_links[u]
      if(len(i_links) == 0):
        if(i in group_users):
          page_rank_new[i] = tele
        else:
          page_rank_new[i] = 0.0000
      else:
        x = 0.0000
        for inc in i_links:
          idx = user_numbers[inc]
          if(inc not in group_users):
            continue
          outcount = 0.0000
          yy = outgoing_links[inc] #always >0
          for y in yy:
            if(y in group_users):
              outcount += 1
          if(outcount != 0):
            x += page_rank_old[idx]/outcount
        x = ((1-tele)*x)
        if(rev_user_numbers[i] in group_users):
          x += tele
        page_rank_new[i] = x
      if(math.fabs(page_rank_new[i]-page_rank_old[i]) > precision):
        to_break = False
    if(to_break == True):
      break
    page_rank_old[:] = page_rank_new
    ctr += 1
  
  for i in xrange(0, n_users):
    ranked[rev_user_numbers[i]] = page_rank_new[i]
  keys = sorted(ranked, key=ranked.get, reverse=True)[:10]
  print 'Topic: {0} === Rankings (rank, user_id, screen_name) ==='.format(gr)
  c = 1
  for k in keys:
    print '{0}. {1} {2}'.format(c, k, user_names[k])
    c += 1
  return


def parse(filename):
  lines = read_file(filename)
  tweet_ctr = 1
  for line in lines:
    data = json.loads(line)
    parse_json(data, tweet_ctr)
    tweet_ctr += 1
  return

def print_lda():
  s = 0
  for k in label_dist.keys():
    print 'label {0} {1}'.format(k, len(label_dist[k]))
    s += len(label_dist[k])
  print 'total {0}'.format(s)

def main():
  n = len(sys.argv)
  if(n != 2):
    print 'usage: python part5_bonus.py <input_json_file>'
    sys.exit(1)
  filename = str(sys.argv[1])
  parse(filename)
  remove_dangling()
  topic_rank_users()
  return

if __name__ == '__main__':
  main()
