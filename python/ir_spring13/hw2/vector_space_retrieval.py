import signal
import sys
import math
import re
import json
from bisect import insort
from collections import defaultdict
from collections import OrderedDict

"handling ctrl+C"
def signal_handler(signal, frame):
  print '\nBye'
  sys.exit(0)


"""one liner used only for defaultdict's declaration"""
def no_use():
  return defaultdict(float)

"""dictionary holds the tf-idf normalized values"""
tf = defaultdict(no_use)

"""dictionary holds the idf values"""
idf = defaultdict(float)

tweets = defaultdict()
tweeters = defaultdict()

cos_sum = 0.0


"""This class exposes API for the vector space ranking"""
class VectorSpaceRanker:
  def __init__(self, f):
    self.filename = f
    self.ranked = None
    print 'Setting up vector space..'
    parse(self.filename)
    print 'Done'
    print 'Calculating tf-idf..'
    calculate_tfidf()
    print 'Done'
    print
    return
  def rank_tweets(self, query):
    q_str = defaultdict(float)
    tokens = tokenize(query)
    for token in tokens:
      q_str[token] += 1
    self.ranked = rank_tweets(q_str)
  def get_ranked_tweets_consolidated(self):
    results = defaultdict(tuple)
    if(self.ranked is None):
      return None
    for t in self.ranked:
      doc = t[0]
      tup = (tweeters[doc], tweets[doc], t[1]) #(user_id, tweet, cos normalized)
      results[doc] = tup
    return results
  def get_ranked_tweets(self):
    return self.ranked
    

#######################################################


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
  f = open(filename, 'r')
  lines = f.readlines()
  f.close()
  return lines


######################################################


"""method that computes log base2 tf values"""
def parse_json(json_obj, tweet_ctr):
  s = json_obj['text']
  tweet_ctr = json_obj['id']
  tweets[tweet_ctr] = s
  tweeters[tweet_ctr] = json_obj['user']['id']
  tokens = tokenize(s)
  for token in tokens:
    tf[tweet_ctr][token] += 1
  unique_tokens = set(tokens)
  
  #make the tokens unique to do idf calc
  for token in unique_tokens:
    idf[token] += 1
  terms = tf[tweet_ctr].keys()
  
  #calculate log base2 values
  for k in terms:
    tf[tweet_ctr][k] = 1+math.log(tf[tweet_ctr][k], 2)
  return


"""method that computes tf and idf values"""
def parse(filename):
  lines = read_file(filename)
  tweet_ctr = 1
  for line in lines:
    data = json.loads(line)
    parse_json(data, tweet_ctr)
    tweet_ctr += 1
  
  #once the json is parsed, we need to run over the idf dict once more
  terms = idf.keys()
  n = len(tweets.keys())
  for k in terms:
    idf[k] = math.log(n/idf[k], 2)
  return


"""method that computes tf-idf and does normalization"""
def calculate_tfidf():
  tweet_ids = tf.keys()
  for twt_id in tweet_ids:
    terms = tf[twt_id].keys()
    sum_of_sq = 0
    for term in terms:
      tf[twt_id][term] *= idf[term]
      sum_of_sq += tf[twt_id][term]*tf[twt_id][term]

    #calculate the normalized values
    norm = math.sqrt(sum_of_sq)
    if(norm == 0.0):
      continue
    for term in terms:
      tf[twt_id][term] /= norm
  return


"""tweet ranker"""
def rank_tweets(query_struct):
  top_ranked = []
  docs = tf.keys()
  
  #build the tf-idf dict for the query; this is constant for all tweets
  query_terms = query_struct.keys()
  sum_of_sq = 0
  valid_tweet = False
  for term in query_terms:
    if(term not in idf):
      return None
    val = query_struct[term]
    val = 1+math.log(val, 2)
    val *= idf[term]
    sum_of_sq += val*val
    query_struct[term] = val
  norm = math.sqrt(sum_of_sq)
  if(norm != 0):
    for term in query_terms:
      query_struct[term] /= norm
  
  #now iterate through all tweets computing their score
  global cos_sum
  cos_sum = 0.0
  for doc in docs:
    cosine_sum = 0.0
    terms = tf[doc]
    for t in terms:
      cosine_sum += query_struct[t]*tf[doc][t]
    if(cosine_sum == 0.0):
      continue
    tup = (doc, cosine_sum)
    cos_sum += cosine_sum
    top_ranked.append(tup)
  return sorted(top_ranked, key=fun, reverse=True)

def fun(tup):
  return tup[1]


###########################################################


def print_result(top_ranked):
  print '\nTweet Rankings =========='
  ctr = 1
  final = top_ranked[:50]
  for key in final:
    print
    print 'Rank: {0}, Value: {1}'.format(ctr, key[1])
    print 'Tweet id: {0}'.format(key[0])
    print 'User id: {0}'.format(tweeters[key[0]])
    print 'Tweet: {0}'.format(tweets[key[0]].encode('utf-8'))
    ctr += 1
  print
  return


def main():
  signal.signal(signal.SIGINT, signal_handler)
  prompt = '>>'
  n = len(sys.argv)
  if(n != 2):
    print 'usage: python part1.py <input_json_file>'
    sys.exit(1)
  filename = str(sys.argv[1])
  v = VectorSpaceRanker(filename)
  while(True):
    print prompt,
    query = raw_input()
    if(query == ''):
      continue
    v.rank_tweets(query)
    top = v.get_ranked_tweets() 
    if(top is not None and len(top) > 0):
      print_result(top)
    else:
      print '\nNo match\n'
  return


if __name__ == '__main__':
  main()
