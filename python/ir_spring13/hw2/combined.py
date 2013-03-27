import signal
import sys
from collections import defaultdict
from part1 import VectorSpaceRanker
from part2 import UserRanker


"""to handle ctrl+C"""
def signal_handler(signal, frame):
  print '\nBye'
  sys.exit(0)


"""method that prints the integrated results"""
def print_integrated(sorted_keys, results, tweets, users, n):
  keys = sorted_keys[:n]
  ctr = 1
  print '\nIntegrated Tweet Rankings =========='
  for k in keys:
    print
    print 'Rank {0}, value: {1}'.format(ctr, results[k])
    print 'Tweet id: {0}'.format(k)
    print 'User id: {0}'.format(tweets[k][0])
    print 'Tweet: {0}'.format(tweets[k][1].encode('utf-8'))
    ctr += 1
  print 
  return


"""compute the integrated ranking"""
def rank(v, u, rf, query):
  integrated_results = defaultdict(float)
  v.rank_tweets(query)
  ranked_tweets = v.get_ranked_tweets_consolidated()
  if(ranked_tweets is None or len(ranked_tweets) == 0):
    print '\nNo match\n'
    return
  ranked_users = u.get_ranked_users()
  tweet_ids = ranked_tweets.keys()
  for twt_id in tweet_ids:
    user_id = ranked_tweets[twt_id][0]
    pr = 0.0
    if(user_id in ranked_users):
      pr = ranked_users[user_id][1]
    cos = ranked_tweets[twt_id][2]
    integrated_results[twt_id] = rf*cos + (1-rf)*pr
  sorted_keys = sorted(integrated_results, key=integrated_results.get, reverse=True)
  print_integrated(sorted_keys, integrated_results, ranked_tweets, ranked_users, 50)
  return


def main():
  signal.signal(signal.SIGINT, signal_handler)
  prompt = '>>'
  n = len(sys.argv)
  if(n != 2):
    print 'usage: python part3.py <input_json_file>'
    sys.exit(1)
  filename = str(sys.argv[1])
  u = UserRanker(filename)
  v = VectorSpaceRanker(filename)
  ranking_fact = 0.7
  while(True):
    print prompt,
    query = raw_input()
    if(query == ''):
      continue
    rank(v, u, ranking_fact, query)
  return

if __name__ == '__main__':
  main()
