## Synopsis
The project refers to the business layer of the Cloud-Wifi application. Here we can expose different endpoints so the front-end can access to obtain different data.
Also it is the main project to communicate with the CMX api.
The support database of these project is the PostgreSQL and the access is made through Spring repository.
The project also have support of the redis to store all the macAdress information for the sessions made in the MAC Tracer functionality. 


## Project Structure
The Principal file of the project is the /spring-boot/src/main/java/app/AppMongoApplication.java and it is the spring-boot main file. (see https://spring.io/guides/gs/spring-boot/).

The package organization of the project is:
1. config
	In the config package we wil lput all the files fif we need to do a specific configuration. (e.g. Redis Configuration or WebSocket configuration)
2. connectors
	Package for the different connector that we use. (e.g. Http Connector, Sql Connector, Listener Connectors)
3. controllers
	Where we defined the different controllers that represents a component that receives HttpServletRequest and HttpServletResponse. 
	In these controllers we will exposed the endpoints for 3rd party could have access to different data.
4. data
	Data for the different access communication. (redis, postgres, cmx objects) In the data package we will put all the repositories that we need.
5. exceptions
	Package to put all the Exception classes that we need.
6. filters
	In these section we will put all the classes that have a filter to be executed before/after the Http request.
7. properties
	Section to put all the properties needed.
8. services
	In the services packages we will put all the communication that the backend need to make to other entities(e.g. CMX)
9. utils
	Utility package.

	
## Installation
1. Please go to project home directory (e.g. /home/devadmin/staging/spring-boot/)
2. Run the comand "mvn clean install" to run the clean phase and the install phase. (see https://maven.apache.org/run.html)
3. After running the comand, see if the BUILD was made with success.
4. Run the command  "java -jar -Dspring.profiles.active=<profile> (staging/prod) <home directory>/target/cloud-cilnet-0.0.1.jar"


## Filters
Before each request came to hte controller there are some filters that are running to validate if all the requests are correct:
1. Security Filters
	Is in the Security Filter that we add the other two filters (CorsFilter and the AuthenticationFilter). 
	Also, is in the filter that we tell  what are the paths that we do not want to run the remaining filters)
	```
	 @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/api/user")
            .antMatchers("/api/authenticate")
            .antMatchers("/api/product")
            .antMatchers("/api/websocket")
            .antMatchers(HttpMethod.OPTIONS, "/**");
    }
	```
2. CorsFilter
	Add respective Headers to the response.
3. AuthenticationFilter
	Each request should came with a JWT Token so we could validate the authenticity of the request. So here we eill validade if the header "x-authorization"
	is filled and if it came with the correct value. If not, a security exception is throw to the user.
	
## How To
### Create a new controller
	1. Go to the controllers package
	2. Add a new classe with the anotation @RestController and @RequestMapping(<path to the controller>)
		2.1 Or use the existing one
	3. Add a request mapping annotation. (  @RequestMapping(method = RequestMethod.GET, value="/maps", produces = {"application/json","text/json"}  )   
	4. Implement the controller logic

### Create a new Service
	1. Go to the services package
	2. Create a new subpackage refering the service type.
		2.1 Or use the existing one
	3. Create a classe with the annotation @Service
	4. Do the respective logic
		

### Create a repository
	1. Go to the Data package
	2. Add or use the respective data type
	3. Create a classe with the annotation @Repository , or extend a Spring-Boot Repository classe.
		3.1. For JPA access create a Entity classe twi the respective javax.persistence annotations.

### Communicate between Controller , Service and repository
To communicate between All the types just add the annotation     @Autowired   before the declaration of the respective field.
```
	public class CMXController {
    @Autowired
    private SiteRepository siteRepository;
    
    @Autowired  
    private CmxNotificationSubscriptionAPI notificationSubAPI;
	
```
    

## Tests
1. To perform a test just go to the file "src/test/java/app.AppMongoApplicationTests" and do your Junit Test.
2. Run the Junit test.
(There are some test examples in the file.)

## Examples
The flow example of one of the requests are:
1. The request came to a specific controller (e.g CMXController)
2. It will enter in the filters and validate if the request is OK.
3. In the Controller method check in database if the respective user exists. (e.g. userRepository.findById(Long.parseLong(userId));)
4. If yes, iterate over the products and call CMX to obtain the respective data (e.g.  String result = httpsClient.doGet("/api/config/v1/maps");
)
5. retrieve the information to the user. (e.g.                      
					```
					res.put("campuses", (JSONArray)campusList);
                    res.put("info","ok");
                    return res;
					```