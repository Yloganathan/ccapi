# ccapi

Setup Database
---
1. npm install postgresql
2. Brew install gradle
3. createDB ccapi
4. Run `./gradlew stage`

How to start the ccapi application
---
1. Run `gradle run` to build and run the application
2. To check that your application is running enter url `http://localhost:8080`

Health Check
---
1. To see your applications health enter url `http://localhost:8081/healthcheck`

Routes
---
1. POST    /accounts (com.capone.ccapi.resources.AccountResource)
2. GET     /accounts/{id} (com.capone.ccapi.resources.AccountResource)
3. GET     /health (com.capone.ccapi.resources.HealthResource)
4. POST    /journals (com.capone.ccapi.resources.JournalResource)
 

 
