# Managing-Coin-Collection  
Managing coin collection:  
User has a large collection of Coins that he has collected over the years and currently adding to the collection.
User wants to build an application to manage the collection. 
It should have the following features. 
1. Addition. 
  a. The user should be able to add a new coin to the collection. 
  b. The coin has following details available 
     i. Country 
     ii. Denomination
     iii. Year of minting 
     iv. Current value 
     v. Acquired date
  c. User should also be able to bulk upload the data by reading from a file 
2. Search 
  a. User should be able to create a list on: 
     i.Country
     ii. Year of Minting
     iii. Current Value (sorted)
   b. User should be able to search a specific coin based on: 
      i.Country + Denomination 
      ii. Country + Year of Minting 
      iii. Country + Denomination + Year of Minting
      iv. Acquired Date + Country
3. Persist 
a. The application should on startup load the existing coin collection from 
database into a collection in the application. 
b. The application should provide a choice to the user, that will enable the 
current state of the collection to be stored in the database.
