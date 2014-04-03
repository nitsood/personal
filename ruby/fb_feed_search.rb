require 'rest_client'
require 'json'

def search(access_token)
  url = 'https://graph.facebook.com/me/home'
  # payload[:access_token]
  paging, done, total, first_page = [], false, 0, true
  puts Time.now
  until done
    response = []
    if first_page
      response = RestClient.get url, :params => {:access_token => access_token, :limit => 1000, :filter => 'app_2915120374'}
    else
      response = RestClient.get url
    end
    json_obj = JSON.parse(response.to_str)
    data_l = json_obj['data']
    paging = json_obj['paging']
    if paging.nil?
      done = true
      next
    end
    url = paging['next']
    # url.sub('limit=25', 'limit=200')
    # if url == '' or url.length == 0
    #   done = true
    # end
    puts "Got #{data_l.length} items"
    total += data_l.length
    first_page = false
  end
  puts "Total: #{total}"
  puts Time.now
end

def main
  access_token = 'CAACEdEose0cBAFZCelLxNDuSt7lRe8SSIIGgo0SZB0OjJrVQlxzzZBoFsRMxUTsPJEhqXEiDGqUMgduZBHdXb2G3ZBidGihkPTXM0M3EWIPn7KRZC8fXf8hxZATT4iMKZBNrC7ipHWKaUTH1Mqtba12WZA7Xqzy9UeiXjLifMPKuURitYvEZAINNlZB28QkqiRPZAS8ZD'
  search(access_token)
end

main
