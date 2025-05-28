
# Hotel-Rating-Microservices

In this project we are created userService, ratingService and hotelService
** This is step by step implimentation of microservices and configration.**


Note: 
1.Here for every service we are using Spring boot version is  <version>2.7.17</version> 
2.Java Version is   <java.version>1.8</java.version>

Make Sure are using this versions in every microservice.


 First create applications according to requirment.

 **A)  Service Registry / Discovery server**
 
1. Here we impliment eureka server.
Here we created spring boot project called as ServiceRegistry.
Here we have to add dependency's as  cluod bootStrap and Eureka server.

2.Open the project in IDE. In main application add annotation as 
@EnableEurekaServer
as follows
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}
}

3. Configure application.properties

Set the service name and instance hostname

eureka.instance.hostname=localhost
eureka.instance.prefer-ip-address=false
eureka.instance.instance-id=${spring.application.name}:${server.port}

Disable registration and make this server a client to itself

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

server.port=8092

4. Now run application and In chrome put localhost:8083
   Here you will get eureka server UI.


**B) Register applications with eureka server**

1. In order to register othere services with service registery add this dependency and configure application. properties file as follow

Add this dependency below java version 
 <spring-cloud.version>2021.0.8</spring-cloud.version> 


<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

 <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement> 


2.Configure application.properties 

MicroService Configration

eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true

Register the server as a client to itself
eureka.client.service-url.defaultZone=http://localhost:8093/eureka

spring.application.name:RATING_SERVICE

3. Add @EnableEurekaClient at strting point of application.


After doing all this configration your service will get register with eureka server.



**C) How to do communication between microservices:**

1.In this concept we are going to use RestTemplate. For this first we have to create RestTemplate bean in starting point of our project (also we can create seprate class as config. In that class add annotation as @Configration and create bean).

As follows:

@SpringBootApplication
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

2. In Services layer inject @Autowired of restTemplate. Which url we want for call that url insert in required method as follows.
As follows:

     @Autowired
     private RestTemplate restTemplate;

 	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

	public User getByUserId(Integer id) {

        User user = userDAO.findById(id).orElseThrow(null);

        Rating[] ratings = restTemplate.getForObject("http://localhost:9097/rating/get/user/"+user.getId(), Rating[].class);

        logger.info("{Rating data.....}"+ratings);
        List<Rating> ratingList1 = Arrays.stream(ratings).collect(Collectors.toList());

        user.setRatings(ratingList1);

        List<Rating> ratingList = ratingList1.stream().map(rating -> {
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:9096/hotel/get/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status ", forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

3. Then when we are going to call url from postman we will get user data as well as rating and hotel data that will come from other microservice application.


**D) Replecing Host and Port of Microservices:**

Here we are removing host and port form url. Instaed of host and port we can use service name that is register in eureka server for example HOTEL_SERVICE, RATING_SERVICE in service method.Follow following steps 

1.configure application.properties correctly as

	eureka.ribbon.eureka.enabled= true
	This configuration tells Ribbon to use Eureka Server for load balancing. Make sure to replace "http://localhost:8761/eureka/" with the actual URL of your Eureka Server.


2. Add anotaion as @LoadBalanced over the restTemplate method in starting point of our project.

3. Replace localhost and port from service method of UserService with the name of that url service. Which is registred in eureka server.

Run userService application, it will work.



**E) Feign Client:**

1.It is alternate way of restTemplate.
Feign is a declarative HTTP client for Java applications. It was developed by Netflix to make it easier for developers to write Java HTTP clients for their services.
In the context of Spring Cloud, Feign allows you to write an interface that defines HTTP requests to other services in your application. Feign will then automatically generate an implementation of that interface at runtime. This allows you to write simple and concise code to make HTTP requests.

2. Add dependency in pom.xml as OpenFiegn

   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

3. Go Starting point of your project and add annotaion as @EnableFeignClients

4.For simple understanding create separet pakage as exrenalService. In that service create Interface called as HotelService (boz here we using hotel service http request for getById as follows). Also parallely add annoataions as @FeignClient(name="HOTEL-SERVICE")(put correct name that should be register with eureka server). In that craete method for get with requred parameters as follows


import com.UserService.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotel/get/{hotelId}")
    Hotel getHotel(@PathVariable Integer hotelId);

}


5. Now open serviceImpl class from userServiceImpl and comment out restTempalte call thet we created earlyer and modify as follows. Also inject @Autowired of HoteService as,

    @Autowired
    private HotelService hotelService;

Hotel hotel = hotelService.getHotel(rating.getHotelId());

  rating.setHotel(hotel);

6. Run your application that should be runn smothly. The test API in USER-SERVICES getById in postmam. That should be work smothly.



**F) API Gatway:**


API Gateway is a component of microservices architecture that serves as the single entry point for all clients, managing access to backend microservices.
Here we dont need to call every service by using different ports and names.
The API Gateway is a server. It is a single entry point into a system. API Gateway encapsulates the internal system architecture. It provides an API that is tailored to each client. It also has other responsibilities such as authentication, monitoring, load balancing, caching, request shaping and management, and static response handling. 


1) Add dependencies as : Cloud BootStrap, Gatway, WebFlux/webFlux and eureka discovery client 

   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

2) Add @EnableEurekaClient annotation in starting point of project.

3) Configure applicatin.properties as :

server.port:8097


spring.application.name:API_GATEWAY


MicroService Configration
eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true

Register the server as a client to itself

eureka.client.service-url.defaultZone=http://localhost:8093/eureka

Basic configuration for Spring Cloud Gateway

spring.cloud.gateway.routes[0].id=USER_SERVICE
spring.cloud.gateway.routes[0].uri= lb://USER_SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/user/**
#spring.cloud.gateway.routes[0].filters[0]=AddRequestHeader=HeaderName,HeaderValue

spring.cloud.gateway.routes[1].id=HOTEL_SERVICE
spring.cloud.gateway.routes[1].uri=lb://HOTEL_SERVICE
spring.cloud.gateway.routes[1].predicates[0]=Path=/hotel/**

spring.cloud.gateway.routes[2].id=RATING_SERVICE
spring.cloud.gateway.routes[2].uri=lb://RATING_SERVICE
spring.cloud.gateway.routes[2].predicates[0]=Path=/rating/**

// Assume in one applicatio consist multiple services with multiple url name. In this condition if we want to call that controller through API gatway. 
We can configure API gatway application.properties as follows (assume in hotel application consist another controller as staff)

spring.cloud.gateway.routes[0].predicates[0]=Path=/hotel/**,/staff/**
or 
spring.cloud.gateway.routes[0].predicates[0]=Path=/**




**G) Config Server:**

It is mainly used for common configration.
In a microservices architecture, a config server plays a crucial role. It centralizes the configuration of all services in a system.
The Config Server uses a version control system (such as Git) to manage configurations. This ensures traceability, versioning, and reliability.


1. Add dependencys as Config Server and Eureka Discovery Clint as follows

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-config-server</artifactId>
    </dependency>

   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

2. Add @EnableConfigServer in starting point of our project. 


3. Before that alway create config server repository in git hub. Bcoz the uri of that git repository we have to put in application.properties file.


 Parrlely configure application.properties as follows


server.port=8093
spring.application.name: CONFIG-SERVER
spring.cloud.config.server.bootstrap=true
spring.cloud.config.server.git.uri: https://github.com/samirAttar/ConfigServer-Hote-Rating

#MicroSErvice Configration
eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true

Register the server as a client to itself
eureka.client.service-url.defaultZone=http://localhost:9099/eureka




**H) Implimentation of config clint:**



1.In ordere to impliment in othere services. Open user service.
2.Go in the pom.xml file. Add dependency as Config Client

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>

Configure application.properties as 

	spring.config.import= configserver:http://localhost:8093

 (port number should be configServer port number)


3. After that in application.properties you can comment earlier eureka configration. Becoz it will automatically import the configration from git server.
