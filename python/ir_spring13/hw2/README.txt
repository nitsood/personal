README
======

This readme is divided into the following sections:
	1. Files included in the zip
	2. Usage of the rankers
	3. Part-by-part description	
	   3.1 Part 1: Vector space ranking using cosine similarity
	   3.2 Part 2: PageRank on users
	   3.3 Part 3: Integrated ranking
	   3.4 Part 4: Search engine optimization task
	   3.5 Part 5: (bonus) Topic Sensitive Page Ranking


1. Files included in the zip:
=============================

The files included in hw1_nitesh.zip are:

part1.py, part2.py, part3.py, part5_bonus.py, README.txt

The tweet corpus must be present in the same directory as the source files.


2. Usage of the rankers:
========================

Each of the files part1.py, part2.py and part3.py must be run individually, as follows.

        part1 usage: python part1.py mars_tweets_medium.json
	(approx run time: 12 seconds)

        part2 usage: python part2.py mars_tweets_medium.json
	(approx run time: 19 seconds)

        part3 usage: python part3.py mars_tweets_medium.json
	(approx run time: 33 seconds)

	part5 usage: python part5_bonus.py mars_tweets_medium.json
	(approx run time: 25 seconds)

The input json file has to be present in the same directory.

Hit Ctrl+C to exit the program.


3. Part-by-part description:
============================


3.1 Part 1: Vector space ranking using cosine similarity
--------------------------------------------------------

This ranking system ranks tweets, in response to a query, based on the cosine similarity of the tweet and the query vectors. This system takes in a user query on the command prompt and gives ranked results. The output contains the rank, cosine value, tweet id, user id of the tweeter and the tweet's text

A few sample runs:

Query: Geo
(This query has only 5 tweets with a non-zero similarity)

>> Geo

Tweet Rankings ==========

Rank: 1, Value: 0.492464955983
Tweet id: 232331173743972352
User id: 18003321
Tweet: I have the Mars Rover feed going, but is it on TV anywhere? Nat Geo maybe?

Rank: 2, Value: 0.488098399489
Tweet id: 232320131341103104
User id: 99534355
Tweet: Why the shit is http://t.co/7LJuJR5K on Nat Geo for the Mars mission?

Rank: 3, Value: 0.432450105239
Tweet id: 232318328344039424
User id: 21276261
Tweet: Anybody want to see Mars? Nat Geo has started their live mars landing coverage. http://t.co/6S491NGA

Rank: 4, Value: 0.370153078856
Tweet id: 232327500599001088
User id: 143129870
Tweet: Watching @NASAJPL attempt to crash land a pimped out Geo Tracker on Mars. T-minus 2 hours and falling. http://t.co/EghDxK2o

Rank: 5, Value: 0.33656444433
Tweet id: 232324468490518528
User id: 21276261
Tweet: Nat Geo &amp; NASA are prepping for a Mars landing, while on AdultSwim BlackDynamite is going to the moon to stop OJ Simpson. Space is the place

>>


Query: ngkjsdnf

>> ngkjsdnf

No match

>>


Query: red PLANet
(There is an interesting thing to note in this run. The first two results dont contain both the query words together, however they are ranked high because the tweet size is small. For tweets 3, 4 and 5, which all contain the words 'red planet', they get a slightly lower cosine value because the number of words are higher and so the normalization constant is higher for these, bringing down the net cosine value.)

>> red PLANet

Tweet Rankings ==========

Rank: 1, Value: 0.679869548209
Tweet id: 232303837799665664
User id: 567229646
Tweet: Planet Mars =)

Rank: 2, Value: 0.658949227313
Tweet id: 232321793803829249
User id: 217562392
Tweet: Is Mars red?

Rank: 3, Value: 0.619205537138
Tweet id: 232322366053691392
User id: 465881191
Tweet: Mars landing soon!! #Ready #Red planet

Rank: 4, Value: 0.611910839985
Tweet id: 232332665796313088
User id: 27369123
Tweet: @MarsCuriosity
Mars rover Curiosity closing in on red planet at 8,000 mph

Rank: 5, Value: 0.599586744582
Tweet id: 232315008057495552
User id: 92980141
Tweet: Red planet rover, red planet rover, let curiosity come over #fb  ( @marscuriosity live at http://t.co/jIq6o1Ln)


3.2 Part 2: PageRank on users
-----------------------------

For page ranking, the linear iterative method has been used. The out-links and in-links graphs for each relevant user were created separately. A relevant user is defined as a user who has at least one out-link or at least one in-link. Thus, dangling users are not taken into account for the pagerank calculation. After the required data structures are built in memory, the program prints the top 50 users ranked based on the link analysis, the links being the users mentioned(out-links) and the users who mention the current user(in-links). A sample run is shown below.


Setting up page rank for users..
Done
Ranking users..
Done

User Rankings (rank, user_id, screen_name) ==========

1. 15473958 MarsCuriosity
2. 11348282 NASA
3. 49573859 iamwill
4. 20612844 davelavery
5. 18948541 SethMacFarlane
6. 347420129 NASA_ESPANOL
7. 4620451 BadAstronomer
8. 18164420 nasahqphoto
9. 448269139 1CatfishKnight1
10. 15536091 SekerekGerg
11. 19802879 NASAJPL
12. 66699013 Jenna_Marbles
13. 19637934 rainnwilson
14. 64019328 ComedyPosts
15. 66476989 kelly_heather
16. 376949805 MSL_101
17. 86336234 Astro_Flow
18. 352947624 GSElevator
19. 90227660 24HorasTVN
20. 12702822 starstryder
21. 15947277 marsroverdriver
22. 8515492 jovemnerd
23. 61497333 ASTRO_SAL
24. 34777829 FellipeC
25. 21035409 michaelianblack
26. 210541257 pepiTOCH
27. 10228272 YouTube
28. 5971922 BoingBoing
29. 397655438 SciShow
30. 326255267 BuzzFeedAndrew
31. 733322844 UrbanAstroNYC
32. 17978616 phirm
33. 24978242 rmaza2008
34. 21724904 CatherineQ
35. 2467791 washingtonpost
36. 24129206 universetoday
37. 98566995 El_Universo_Hoy
38. 551608380 RetweetTheSongs
39. 1344951 wired
40. 19725644 neiltyson
41. 972651 mashable
42. 1786261 JPL
43. 18080822 Lindseymgreen
44. 72420267 carsonmyers
45. 65688328 rossneumann
46. 100093892 SantiContreras
47. 249478001 claratma
48. 6132422 jonrussell
49. 19658826 newscientist
50. 33884545 CNNEE


3.3 Part 3: Integrated ranking
------------------------------

The integrated ranking system has been built incorporating the results of vector space ranking and page ranking accoring to the relation:

        integrated_score = d*cosine_similarity + (1-d)*page_rank

where 'd' is a number in [0, 1]. The value used in the part3 source is d=0.7, as this ensures that a higher weightage is given to the cosine similarity of the query-tweet pair. This weighting seems to be correct because there are some example queries which almost exactly match some tweets in the corpus but the page rank of the tweeter is zero, since he/she has not mentioned anyone nor have they been mentioned. This weighted sum also helps when cosine values of two tweets are the same, but one of the tweeter has a better page rank.

As an example, consider the query 'mars rover'. The integrated system returns the top 5 results as shown below. 

>> mars rover

Integrated Tweet Rankings ==========

Rank 1, value: 0.700012577929
Tweet id: 232316564811165697
User id: 245237059
Tweet: #Rover #Mars

Rank 2, value: 0.700010190088
Tweet id: 232326618092290048
User id: 23636966
Tweet: Mars Rover

Rank 3, value: 0.700005078339
Tweet id: 232308572124504064
User id: 307165946
Tweet: Mars Rover

Rank 4, value: 0.700005078339
Tweet id: 232331024892321793
User id: 224079848
Tweet: Mars rover!!!!

Rank 5, value: 0.7
Tweet id: 232320614017413122
User id: 711354504
Tweet: Mars rover :)

Now consider the same query run on the vector space ranking system (part1). It produces the top 5 results as shown below.

>> mars rover

Tweet Rankings ==========

Rank: 1, Value: 1.0
Tweet id: 232320614017413122
User id: 711354504
Tweet: Mars rover :)

Rank: 2, Value: 1.0
Tweet id: 232332221149745152
User id: 402875715
Tweet: mars rover '

Rank: 3, Value: 1.0
Tweet id: 232331336210337792
User id: 262868872
Tweet: Mars Rover

Rank: 4, Value: 1.0
Tweet id: 232316463300620288
User id: 19894001
Tweet: Mars Rover!!!

Rank: 5, Value: 1.0
Tweet id: 232316564811165697
User id: 245237059
Tweet: #Rover #Mars


Let's consider the results for two tweets viz., 'Mars rover :)' and '#Rover #Mars'. The cosine similarity for these 2 tweets w.r.t to the query is both 1.0, which is correctly reported by the vector space ranking system(part1). Now, in the integrated system, '#Rover #Mars' is higher than 'Mars rover :)' because its tweeter has a higher page rank. So in the integrated formula above, the page rank value has made the difference between the tweets.

Thus, it can be observed that the integrated system is better at ranking because it considers both factors, cosine similarity of the query-tweet pair as well as the page rank of the tweeeter. 


3.4. Part 4: Search engine optimization task
--------------------------------------------

The page that I have built is:

	http://www.diaryofurjnaswxkfjjkn.wordpress.com/

I used the following techniques to try and get a good rank for this page:

  1) Used wordpress.com which is a quite popular blogging platform
  2) Used Google Webmaster Tools to get the page verified. 
  3) Put the keywords urjnasw xkfjjkn in the <title> tag (title of the blog).
  4) Tried to add these keywords wherever I could on the blog, increasing the keyword density.
  5) Got my blog linked to from friends' blogs.
  6) Tried to post as much original content as I could.
  7) Posted a lot of currently trending vidoes (like Gangnam Style) as well as videos that seemed popular on YouTube.
  8) Used popluar tags (eg, Music) that would increase the blog's visibility across Wordpress.
  9) Linked to popular and highly ranked sites like Samsung.com, Apple.com
  

3.5 Part 5 (bonus): Topic Sensitive Page Ranking
------------------------------------------------

A topic sensitive page ranker has been built using the following process:

- Group each tweeter based on certain keywords in the user 'description' field. This is a static dictionary declared globally as:

lda_dist = defaultdict(set)
lda_dist["Technology"]={"scientist","science","tech","technical","software","computers","computer","web","nerd","geek","it","mars","nasa","technology","developer","programming", "algorithm"}
lda_dist["Social"]={"love","lover","college","twitter","tweets","people","world","family","father","mother","wife"}
lda_dist["News"]={"sports","games","news","web","media","journalism","editor"}
lda_dist["Arts"]={"arts","movies","music","paint","musician","director","blogger","writer","producer","guitar", "theater"}

This dictionary was built by going through some tweets manually and mainly by looking at the results of a free online Linear Dirichlet Allocation(LDA) tool - http://code.google.com/p/topic-modeling-tool/wiki/TopicModelingTool. 

Thus there are the 4 groups used above, as well as a 'Miscellaneous' category for those users who don't fit into any of the above. 

- Example: The top mentioned user i.e. MarsCuriosity as the 'IT' keyword in its description, thus it goes into the 'Technology' category. If users match more than one keywords, they may be in more than one category, which is fine and is so in real life as well. Here, MarsCuriosity only matches the Technology group.

- The page rank algorithm of part2 is then run on all users, for all topics. There are some modifications. I don't consider the pagerank values of the incoming-links of those users not in the same group. For example, if I am currently running pagerank for the group Technology, and I am ranking the user MarsCuriosity, which is in Technology itself, I won't consider the pagerank of a user X in group Social, even if X mentions MarsCuriosity. If a user Y mentioning the user MarsCuriosity is in the current group being ranked i.e. Technology, then the pagerank of Y is also taken into account while calculating the rank of MarsCuriosity.

- The second modification from part2 is that I will not consider ranking a user if it does not belong to the current group. For example, if I am currently ranking the group Social, and a user X is not in Social, then X is excluded from the page rank calculations. If we don't do this, the users like MarsCuriosity who have a huge amount of links from all groups, will be ranked high in all groups, which I do not want. 

- The third modification from part2 is that I will not consider the random teleportation factor (alpha) while ranking a user not belonging to the current group, since teleportation is only allowed within the group.

The topic sensitive page ranking shows up rankings for different topics as shown below. 

Topic: News === Rankings (rank, user_id, screen_name) ===
1. 11348282 NASA
2. 19802879 NASAJPL
3. 12437282 RobertPearlman
4. 89617904 silverrockets
5. 218549284 EliKellendonk
6. 21440516 88Styles
7. 376949805 MSL_101
8. 24129206 universetoday
9. 21029359 MorganPalmer
10. 65933862 nutjob4life

Topic: Arts === Rankings (rank, user_id, screen_name) ===
1. 5870022 00mb
2. 20713213 polarisdotca
3. 66476989 kelly_heather
4. 17755136 Zonified
5. 346352194 CindyStarfall
6. 17298249 zanegrant
7. 43133006 ramballo
8. 246114061 avishkasha
9. 19770255 taniazygar
10. 64926349 ceilck

Topic: Technology === Rankings (rank, user_id, screen_name) ===
1. 15473958 MarsCuriosity
2. 11348282 NASA
3. 15947277 marsroverdriver
4. 41506644 IndyShakes
5. 19802879 NASAJPL
6. 347420129 NASA_ESPANOL
7. 18164420 nasahqphoto
8. 86336234 Astro_Flow
9. 376949805 MSL_101
10. 429905502 AttivissimoLIVE

Topic: Miscellaneous === Rankings (rank, user_id, screen_name) ===
1. 17384099 esaoperations
2. 21436960 esa
3. 249478001 claratma
4. 359120567 SkyVasNormandy
5. 49573859 iamwill
6. 20612844 davelavery
7. 14846573 Sondas
8. 125830272 dramadiffusion
9. 36557265 kansasdrums
10. 532975158 M_A_Larson

Topic: Social === Rankings (rank, user_id, screen_name) ===
1. 19802879 NASAJPL
2. 4620451 BadAstronomer
3. 19078594 scottEweinberg
4. 64813030 R0SSYB00P
5. 16343260 misslazyj
6. 31703554 richardsonkelly
7. 19658826 newscientist
8. 23117133 kscosmosphere
9. 300000982 1D_trackers
10. 138165553 TeatroBonecos


As can be seen above, there are some expected results, i.e. the user universetoday, which gives news, has been ranked in the top10 of the News category. Similarly, technology related users i.e. MarsCuriosity, NASA, NASAJPL are all in the top 10 of the Technology category. RobertPearlman, who is the editor of a space magazine, is in the News category. taniazygar, a singer, is in the Arts category. Of course, there are a few results which are out of place, but that is because the classifying keywords may not be strong enough to guess the correct category of a user. 

===================================================================

