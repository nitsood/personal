class Time
  def round_off(seconds = 60)
    Time.at((self.to_f / seconds).round * seconds)
  end
  def round_up(seconds = 60)
    Time.at((self.to_f/seconds).ceil * seconds)
  end
  def floor(seconds = 60)
    Time.at((self.to_f / seconds).floor * seconds)
  end
end

t1 = Time.at(1372747785)
t2 = t1 - 45
puts "t1: #{t1}"
puts "t2: #{t2}"
puts t1.round_up(60)
puts t2.round_up(60)
# t.floor(15.minutes)
#

irb(main):013:0> now = Time.now
=> Tue Jul 02 02:03:04 -0500 2013
irb(main):014:0> now.to_f
=> 1372748584.46255
irb(main):015:0> Time.at(1372748584)
=> Tue Jul 02 02:03:04 -0500 2013
irb(main):016:0> a = now.to_f
=> 1372748584.46255
irb(main):017:0> a/60
=> 22879143.0743758
irb(main):018:0> min = a/60
=> 22879143.0743758
irb(main):019:0> min.ceil
=> 22879144
irb(main):020:0> Time.at(min.ceil*60)
=> Tue Jul 02 02:04:00 -0500 2013
irb(main):021:0> a = now.to_f.to_i
=> 1372748584
irb(main):022:0> min = a/60
=> 22879143
irb(main):023:0> min.ceil
=> 22879143
irb(main):024:0> Time.at(min.ceil*60)
=> Tue Jul 02 02:03:00 -0500 2013
irb(main):025:0> nitsood@neptune:~/Work/spring13/personal/rails$ ruby -v
ruby 1.9.3p448 (2013-06-27 revision 41675) [i686-linux]


Test with version
Test with platform

Ruby docs 1.9.3 Time t.to_f you can pass precision also
http://ruby-doc.org/core-1.9.3/Time.html#method-i-to_f
