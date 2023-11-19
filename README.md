# Hotel-Rating-Microservices
In this project we are created userService, ratingService and hotelService
** This is step by step implimentation of microservices and configration.**

 # First create applications according to requirment.

 # A)  Service Registry / Discovery server
 
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

# Set the service name and instance hostname
eureka.instance.hostname=localhost
eureka.instance.prefer-ip-address=false
eureka.instance.instance-id=${spring.application.name}:${server.port}

# Disable registration and make this server a client to itself
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

server.port=8092

4. Now run application and In chrome put localhost:8083
   Here yiu will get eureka server UI.

---------------------------------------------------

# B) Register applications with eureka server

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

#MicroSErvice Configration
eureka.client.fetch-registry=true
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true

# Register the server as a client to itself
eureka.client.service-url.defaultZone=http://localhost:8093/eureka

spring.application.name:RATING_SERVICE

3. Add @EnableEurekaClient i strting point of application.


After doing all this configration your service will get register with eureka server.

------------------------------------------------

# C) How to do communication between microservices:

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

------------------------------------------------------------------


   
