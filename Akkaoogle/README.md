Components:  

1. Request handler  
An actor that handles HTTP requests from the user. Uses an asynchronous HTTP library called Mist, provided by Akka  
  
2. Search cheapest product  
Main entry point to execute a search to find the cheapest deal. Searchs both internal and external vendors  
  
3. Internal load balancer  
Load-balancing actor that sends messages to worker actors to find the cheapest product available in the internal database  
  
4. External load balancer  
This actor invokes all the external vendor services and finds the cheapest price among them  
  
5. Find the product price and find vendor price  
Worker actors do the work of finding the price  
  
6. Monitor  
A simple monitor actor logs the failures that happen in external vendor services  
  
7. Data Loader  
An actor that loads data to the database. Could be used to load product data for internal vendors  
  
8. Also includes a supervisory hierarchy to handle failures.  