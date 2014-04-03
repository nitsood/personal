require 'nokogiri'
require 'rest_client'

def ep_list(show_id)
  url = 'http://services.tvrage.com/feeds/episode_list.php'
  response = RestClient.get url, :params => {:sid => show_id}
  # puts "|#{response}|"
  doc = parse(response.to_str)
  episodes = doc.xpath('//Season/episode/title')
  puts "Number of episodes: #{episodes.length}"
  episodes.each do |ep|
    puts "#{ep.content}: #{imdb_rating(ep.content)}"
  end
end

def imdb_rating(ep_title)
  url = 'http://www.omdbapi.com' #&t=...and+the+Bag%27s+in+the+River'
  response = RestClient.get url, :params => {:t => ep_title, :r => 'xml'}
  doc = parse(response.to_str)
  found = doc.xpath('//root').attr('response').content
  if found == 'True'
    node = doc.xpath('//movie')
    return node.attr('imdbRating')
  end
end

def parse(s)
  Nokogiri::XML::Document.parse(s)
end

def main
  # TODO: obtain the sid by a search on the show's name
  ep_list(18164) # Breaking Bad
  # imdb_rating("A-No-Rough-Stuff-Type-Deal")
end

main
