## Etransact Mini Bank    
A simple Mini Bank that allows transfer of funds between two account holders. 
Account holders can also be created and closed using the application.


  
# Table of Contents  
  
- [Etransact Mini Bank    ](#etransact-mini-bank)  
- [Table of Contents](#table-of-contents)  
- [Usage](#usage)  
   - [Installation](#installation)  
 - [APIs](#api)  
  
## Usage  
  
### Installation  
To Install project you will need gradle on your machine.
check gradle on your system using **gradle** 
```bash  
gradle --version
``` 
If you will like to run application using docker Simple 
```bash
 ./gradlew build    
docker-compose up --build
``` 
if you do not have docker you can use 
```bash
./gradlew build && java -jar build/libs/account-managment-0.1.0.jar
```


## Api
API Can be reference [here](https://documenter.getpostman.com/view/10151664/TVKBYdeA)