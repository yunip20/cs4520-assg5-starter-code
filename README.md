# Assignment 5: Crafting with Compose

https://github.com/yunip20/cs4520-assg5-starter-code.git

This project utilizes Jetpack Compose and WorkManager to create a simple product list viewer. While it sources the data from an api server using Retrofit, this application also aims to provide seamless access to the list even in offline cases by retreiving data from a locally stored Room database.   
 
### Brief Project Overview

* A two-screen single-activity application
  * First Screen: LogIn Page 
  * Second Screen: Product List
* Once the user logs in with the correct username and password, the application opens the product list page, displaying a list of food (colored in yellow) and equipment (colored in red) 
* Each product is labelled with a name, an optional field of expiry date, and price  
* The list of products are loaded from a server if the device is connected to a network and locally, if the device is offline  

### Project Structure

The project structure looks something like this: 
   * model- manages the data and logic   
   * ui - contains the screen and viewmodel  
   
The RetrofitInstance works as a client to the ProductApiService, and creates the apiService for use in a factory pattern. 
ProductDao stores the database logic such as the methods for inserting or retreiving data from the database. 
ProductData class is the data structure used to store each product to the database 
ProductDatabase follows a singleton pattern, building and instantiating the database itself. The database also provides access to ProductDao. 
WorkerManager also follows a singleton pattern, and stores the worker, which has the job of retreiving data from the server and updating the database every 1 hour. 