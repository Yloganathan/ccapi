# ccapi

Setup Database
---
1. `npm install postgresql`
2. `Brew install gradle`
3. `createDB ccapi`
4. `source scripts/settings.sh`
5. Run `./gradlew stage`

How to start the ccapi application
---
1. Run `gradle run` to build and run the application
2. To check that your application is running enter url [http://localhost:8080](http://localhost:8080)

Health Check
---
1. To see your applications health enter url [http://localhost:8081/healthcheck](http://localhost:8081/healthcheck)

Routes 
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/2a29c9640093482367a8)
---
1. POST    /accounts 
           `example shape:
           {
           	"name": "wonderwoman"
           }`
2. GET     /accounts/{id} 
3. GET     [/health](http://localhost:8080/health)
4. POST    /journals 
           `example shape:
           {
	            "accountId":"1",
	            "type":"purchase",
	            "amount":"499.99"
           }`
 

 
