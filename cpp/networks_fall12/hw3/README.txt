Team 10: Nitesh Sood and Nathaniel Lum
ECEN 602 Assignment 3

README
======

Abstract:
---------

The aim of this project is to implement a proxy HTTP server and HTTP client. The proxy server should implement a cache which replaces web pages in a Least Recently Used (LRU) fashion. It should also implement a CONDITIONAL-GET or HEAD mechanism to retrieve web pages which are expired in the cache and may have changed on the web server.

Design of the HTTP Client:
--------------------------

The client is a simple HTTP client that issues GET requests to the proxy server and displays the resulting HTTP response (headers+body) on the console. The client exits after taking one request. To run subsequent requests the client needs to be started again. 

Design of the HTTP Proxy Server:
--------------------------------

A web page in the proxy's cache are modeled as an Entity, which is a class that contains a EntityHeader reference and a string body. The EntityHeader class encapsulates the required HTTP response header fields that were needed for this project, like Expires, Last-modified, Date and another field introduced by us, called last-accessed which stores the time at which a particular page was accessed last. There are two other fields Content-type and Content-encoding but are not used.

The LRU cache is implemented as a C++ map data structure with the a string key mapping to an Entity reference. The key is the URL using which the page was accessed on the client side, and which was passed to the proxy as a GET request. The least recently used cache entry is found by examining the last-accessed timestamp of the Entity. The entry with the earliest last-accessed time is replaced.

When the proxy server receives a GET request, it checks its own cache to see if the page request can be served. One of the following 3 steps will be taken:

1) If the page is not found in the cache, the proxy will forward the GET request to the web server and return the queried page to the client. It will also add this page to its own cache in an LRU manner.

2) If the page exists in the cache, and it is 'fresh', i.e. the Expires timestamp is still in the future, then the page will be served from the cache. 

3) If the page exists in the cache but it is 'stale', i.e. its Expires timestamp has passed, then the proxy will issue a CONDITIONAL-GET to the web server using an If-Modified-Since header in its request. The If-Modified-Since header will contain the timestamp from the Expires field. (Note: if the Expires timestmp is absent, then the Last-modified or the Date timestamps are considered, in that order, to infer whether the page has expired or not). 

   The web server can return one of two responses to the CONDITIONAL-GET:
   
   a) 200 OK: If the page was modified on the web server, then it returns the modified page as a normal HTTP response with code 200.
   
   b) 304 Not Modified: If the page was not modified, then it returns a 304 HTTP response.

The proxy server analyzes this response from the web server and takes appropriate action. If the response code was 200, it stores this new modified page in the cache by replacing its outdated version, and if the response is 304, the version in the cache is sent back to the client.

Source Layout:
--------------

HttpProxy.h: header file containing class and some method declarations
HttpProxy.cpp: file containing class and method definitions. These methods are used in support of the main file.
ProxyServer.cpp: file containing proxy server code.
ProxyClient: file containing client side code.

Usage:
------

1) make

2) start the server: ./sout <proxy_server_ip> <proxy_server_port>

3) start the client and request a page: ./clout <proxy_server_ip> <proxy_server_port> <url_to_retrieve>
