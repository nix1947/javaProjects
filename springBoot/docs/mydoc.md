## `application.properties`
- We can use `application.properties` or `application.yaml`

**Default application properties for postgresql**
```bash
spring.datasource.url= jdbc:postgresql://localhost:5433/idas 
        spring.datasource.username=postgres
        spring.datasource.password=postgres
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.hibernate.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        server.servlet.context-path=/api
        logging.level.org.springframework: DEBUG

```

## Components and Beans
**Component**:
- Component is a class that spring boot detect and automatically create an instance and this instance
is called a `Bean`
- To define a component annotate a class with any type of `@Component, @Service, @Repository, @Controller, @RestController` so spring boot automatically create an object from that class
- Generic is `@Component`
- 

**Autowiring**: Process in which one objects connect to another. 

## Separation of concerns

## Accessing the bean 
- Bean name start with first letter with lowercase
```java
public class IdasApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(IdasApplication.class, args);
        DocumentController dc = ctx.getBean("documentController", DocumentController.class);
        System.out.println(dc);
    }
}
```

## Bean scope and initialization. 
- Components
  - Singleton Component: Only instance is created by spring, this is the default one
  - Prototype Component: New instance is created and return to the caller. 
  - Default scope of spring is 'singleton'
  - You can annotate by `@Scope("singleton")` to create 
  - Singleton beans are created at application start-up
  - All singleton bean created during application startup, annotate the component with `@Lazy` which allows, it is only created when we first time access the component.
  - Use `@Scope("prototype")` to create prototype bean 
  - There are several (FOUR) scopes available
  - `prototype, request: bean lifetime for that request only, session: bean lifetime for that session, application: bean live for the lifetime of webserver`
  - Using lazy speedup startup time.
  - 
  ```java 
     @Component
     @Scope("Singleton") // this is default in spring
     public class SomeComponent {
    }
    ```
    
    **Lazy bean** 
  ```java
    @Component
    @Lazy 
   public class Component {
  }
  ```
  

## Autowiring
- Two objects need to talk to each other spring boot connect two each other using autowiring(Dependency injection)
so they communicate with each other.
- You can mark `@Autowired(required=false)` if the dependency is optional. 

```java
import com.pearlinfotech.com.idas.modules.document.repository.DocumentEntityRepository;

import javax.persistence.Entity;

@Service
public class DocumentService {
  @Autowired
  DocumentEntityRepository dr;

  //@Qualifer can be used if interface has multiple implementation
  @Autowired
  @Qualifier("primaryRepositoryImpl")
  DocumentEntityRepository repo;

}
```

- We can also autowire a collection as shown below. 
```java 
@Service 
public class BankServiceImpl implements BankService {
  private Collection<BankRepository> repositories; 
}
```

- You can also autowire a `Map<String, T>`, where keys are beanName and values are Bean Instances
```java 
@Service 
public class BankServiceImpl implements BankService {
// This could be oracle, mysql, and pgsql bank repositores
    private Map<String, BankRepository> repositoriesMap; 
}
```

## Injecting values in the beans, via `@Value`
```java
@Component 
public class MyComponent {
    @Value( "${name}") // get value from application.properties
    private String hostname;
    
    @Value("#{5 * 7.5}") // Inject SpEL expression
    private double workingWeek; 
}
```

## SPEL (Spring expresssion language)
- Its major objects is to inject values in spring beans.
```java
@Component 
public class MyComponent {
    @Value( "${name}") // get value from application.properties
    private String hostname;
    
    @Value("#{info.cities. [startsWith('B')]}") // info is the bean. aka Info Class with @Component annotation
    private String firstCity;

  @Value("#{info.cities.? [startsWith('B')]}") // info is the bean. aka Info Class with @Component annotation
  private List<String> allCities;

  @Value("#{info.cities.$ [startsWith('B')]}") // info is the bean. aka Info Class with @Component annotation
  private String lastCity;



}

```


- Spel can also used to autowired method parameters.

```java 
  @Component 
  public class MyComponent {
   @Autowired
   // systempropertis is std spel variable allows to access system properties in os
   public void setUsername(@Value("#{systemProperties['user.name']}") String name)
        this.username = name; 
      }
  }
 ```


## Working with command line arguments.
- Commands passed from the cmd are access using `args` in main method.
- We can also autowire command-line args into component. 
- To access args in bean, use `ApplicationArguments args`

```java 
public class BeanWithArgs {
  public BeanWithArgs(ApplicationArguments args) {
    you can access command line argument here
  }
}
```

## Configuration Class. 
- A configuration class called special method called `bean method` A bean method is a factory it creates an object initilize it 
and hands it over to spring so that it can added to application context so that it can 
access it from elsewhere in your application. 

**Defining configuration Class** 
 - Annotate class with `@Configuration` decorator
 - Annotate methods with `@Bean` and create return object 
 - When method with `@Bean` decorator return the object spring boot put the bean in app context to use

```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
  @Bean
  public MyBean myBean() { // myBean is the name of bean that you use to access from app ctx. 
    MyBean b = new MyBean(); // Do not annotate the MyBean class with @Component otherwise spring will create the bean it is POJO: Plain Old java Object
    b.title = "hello world";
    return b;
  }
}
```

- You can have as many as method in `configuration` class with `@Bean` method. 

**Accessing the Bean**
- Use `@Autowired` to access the bean. 

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
  @Autowired
  MyBean mb;
}
```

**Locating configuration class and bean method** 
- Configuration class are special types of components
- When spring boot app start 
  - It scan for component and configuration class 
  - It looks in packages and subpackages
- You can provide the alternative location to scan packages as shown below using `scanBasePackages` attribute. 

```java
@SpringBootApplication(scanBasePackages = {"mypackage1", "mypackage2"})
public class IdasApplication {


    public static void main(String[] args) {

        SpringApplication.run(IdasApplication.class, args);

    }

}
```

**Configuration technique** 

- Customizing Bean name 
  - By default bean name is same as the method name
  - You can specify different bean name using `name attribute`

```java 

@Configuration
public class MyConfig {
  @Bean
  public MyBean myBean() { // myBean is the name of bean that you use to access from app ctx. 
    MyBean b = new MyBean(); // Do not annotate the MyBean class with @Component otherwise spring will create the bean it is POJO: Plain Old java Object
    b.title = "hello world";
    return b;
  }
  
  @Bean(name={"B1", "B2"})
  public MyBean bean2() {
    return new MyBean(222)
  }
}
```

**Lazily create a singleton bean**

```java

import org.springframework.context.annotation.Lazy;

@Configuration
public class MyConfig {
  
  @Bean(name = {"B1", "B2"})
  @Lazy
  public MyBean bean2() {
    return new MyBean(222);
  }

  // proto return new instance on every call. 
  @Bean(name = "proto")
  @Scope("prototype")
  public MyBean prototype() {
    return new MyBean(222);
  }
}
```

**Configure bean dependencies with each other** 
***In a single class***

```java
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

@Configuration
public class MyConfig {
  @Bean
  public DataSource dataSource() { // methodName is a beanName
    DataSource ds = new DataSource();
    return ds;  // singleton objects are created by bean
  }

  @Bean
  public TransactionManager transactionManager() { // methodname is beanName
    TransactionManager tsx = new TransactionManager(); 
    tsx.setDataSource(datasource()); // calling dataSource method defined above
    return tsx; 
  }
}
```

***Another method, passing dependence bean in a constructor of other bean***
```java
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

@Configuration
public class MyConfig {
  @Bean
  public DataSource dataSource() { // methodName is a beanName
    DataSource ds = new DataSource();
    return ds;  // singleton objects are created by bean
  }

  @Bean
  public TransactionManager transactionManager(DataSource ds) { // methodname is beanName
    TransactionManager tsx = new TransactionManager(); 
    tsx.setDataSource(ds); // calling dataSource method defined above
    return tsx; 
  }
}
```

## Springboot techniques. 
- setting application properties at command line 
- Defining YAML properties file. 
- Using spring profiles(prod profile, dev profile)
- Using spring boot actuator. 

**Application properties**
- Application properties are the way to configure your bean, passing parameter to the bean to configure things like  connection string to db, name message quee and so on. 
- properties can be overridden from command line as well.

**Using application properties in Bean**
- Use `@Value` annotation in bean.

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
  @Value("${name}")
  private String dbConnection;
}
```
- Spring boots lets you to define application properties in many places  with higher to lower priority such as 
  - Command line arguments
  - Environment variables `SPRING_APPLICATION_JSON`
  - Operating System environment variables
  - Application properties outside JAR
  - Application properties inside JAR 

**Specifying with properties file to use** 
***Location of properties file*** 
SpringApplication looks in the following places to find properties file(highest priority first)
- `/config` subdirectory of your java app directory 
- Your java app directory 
- `/config` package on class path 
- Root package on class path 

Lowest priority is at bottom. 
By default when spring boot start it looks `application.properties` or `application.yaml` to load you can 
tell spring boot to use different file. 

**Using System.setProperty**
```java
@SpringBootApplication
public class IdasApplication {


    public static void main(String[] args) {
        // look for app2.properties
        System.setProperty("spring.config.name", "app2");
        SpringApplication.run(IdasApplication.class, args);

    }

}

```

- Alternatively you can set `SPRING_CONFIG_NAME` environment variable with file name outside of project.

**USing command line** 
You can also used a command-line argument to pass the file name as shown below. 
```bash
--spring.config.name=app2
```

**Defining Yaml file properties** 
-YAML is very useful when you have complex hierarchical name properteis. 
***example.yaml***
```yaml
contact:
  email: info@gmail.com
  ph: 9779818284095
  
```
***Accessing yaml properties in bean***

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
  @Value("${contact.email}")
  private String email;
}
```

***Another way to use yaml properties in bulk in bean***

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "contact")
public class MyBean {
  private String email;
  private String ph;

  // When using this above you also need to provide getters and setters are essentail


  public String getEmail() {
    return email;
  }

  public String getPh() {
    return ph;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPh(String ph) {
    this.ph = ph;
  }
}
```

To use the above technique you also need to add the new dependency `spring-boot-configuration-processor` with groupId `org.springframework.boot` so it can process the annotation.
`application.properties` win over `application.yaml` if both files are present.

## Spring profiles. 
- Profile allow to run application in diffferent mode, so that you can run some properties in one mode and different other property in other mode. classis example would be `dev` vs `prod` mode. 
- in dev mode use this con string and in prod use other conn string. 
- Profile are really important, every commercial spring application use profile. 
- It allow seggretage configuration into different areas. 

**Defining profile specific component**
- You can annotate component class with @Profile

```java
import org.springframework.stereotype.Component;

@Component
@Profile("development")
public class MyBean4Dev {
  @Override
  public String toString() {
    return  "MyBean"
  }
}
```
When you run the spring app in dev mode this bean is created otherwise it won't. 

The below code run in `production` mode.
```java
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class MyBean4Prod {
  @Override
  public String toString() {
    return  "MyBean"
  }
}
```

**Defining profile specific properties** 
`---` seperator is used to seperate the profile
first one is the default profile, as you see,`address` properties is override in `dev` and `prod` mode while leaving the port as defult to `8000`
```yaml
apiserver:
  address:192.168.1.1
  port:8000

----
spring:
  config:
    activate:
      on-profile:development
apiserver:
  address:127.0.0.1
---
spring:
  config:
    activate:
      on-profile: production

apiserver:
  address:192.168.1.2
```

**Setting the active profile** 
To set active profile, you must tell spring what is the active profile
set `spring.profiles.active` property such as 
```bash
spring.profiles.active=development 
```
from command line argument
```bash
--spring.profiles.active=production 
```
you should use the combination of `application.properties` and `application.yaml` to define profiles.


## Using springboot actuator. 
- spring boot actuator is a part of spring boot, it allows to get your application information at run time to see how it is doing
such as how much memory it is using, what rest endpoint does it have, how many threads have been allocated

- It provides information in various different ways including `HTTP` endpoints
- You can also use `JMX` java management extension it allows you to monitor an application from outside and spring boot provide information via `JMX` 
- You can also do `telnet, ssh`
- In order to use actuator you need to add the following dependencies 

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
- **Enable actuator endpoints** in application.properties file. 
```bash
  management.endpoints.web.exposure.include=*
```

If you don't set the property above-mentioned only the '/actuator/health', '/actuator/info' are enabled by default. 
use `/actuator/mappings` to views all the urls of actuator. 
```http request
/actuator/health
/actuator/metrics: help to identify bottlencks

```

## Integrating with datasource
- JPA: java persistence api
- Spring provide vertical APIs for data access many technologies, including JDBC, JPA(Java persistance API, an ORM), etc
- Spring provide Declarative transaction management.
  - You can have transactional boundaries declared via configuration
  - Enforced by a spring transaction manager
  - Implemented by spring-transaction manager bean
- Provide automatic connection management
  - Acquire/release connection automatically (Use concept of connection pool)
- Also support many data supports for many dat access technologies. 


**JPA**
- A standard ORM(Object /relation mapping)
- JPA is a specification
  - Implemented by the hibernate library
  - Also implemented by java EE(enterprise edition)
  - To use jpa in spring, we need to add hibernate in classpath

**Concepts of JPA**

- Entity class: A java class with fields mapped to a database table
- Entity Manager: A class in jpa which fetches/saves entities to the database. it has methods for CRUD operation, to perform operation it needs to connect to database.
- Entity Manager factory:
  - Creates and configure entity manager so it can connect to database, it is a bean that is created for you automatically by spring boot
  - It is the one which create entity manager, you won't create entity manager directly. 

**Add jpa to spring boot**
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifictId>spring-boot-starter-data-jpa</artifictId>
</dependency>
```

**Configuration in application.properties**
```bash 
spring.datasource.url= jdbc:postgresql://localhost:5433/idas 
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.jpa.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
server.servlet.context-path=/api

```

**Defining Entity class** 
- Annotate with `Entity`
- If columns name are different use `@Column` decoration.

```java
@Entity
@Table(name="employee")
public class Employee {
  @Id  // Must declare field with @Id for primary key
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long employeeId = -1;
  private String name; 
  private String regiion; 
  @Column(name="salary")   // If you want to explictly define the different column name use @Column annotation
  private double dosh; 
  
  // Plus constructors, getters/setters,
  // equals(), and hashCode()
```
- Entity class are put in the main app packages and subpackages
- In complex, you can put elsewhere and tell spring boot using `@EntityScan` decorator such

```java
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"myentitypackage1", "mep2"})
public class Application {
    
}
```

**Seeding the Database with data**
- For convenience during development/testing, you can seed the database with some sample data
- To seed the database, you can use `@PostConstruct` decorator to populate the table with data in `init` method.
- jdbCTemplate is the spring boot helper class

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@Component
public class SeedDb {
  @Autowired
  JdbcTemplate jdbcTemplate;


  @PostConstruct
  public void init() {
      // Reason to do in this method is because, autowired jdbcTemplate is not available yet.
    jdbcTemlate.update(
            "insert into Employees(name, salary, region) values(?, ?, ?)", new Object[]{"Manoj", 21000, "LOondon"}
    );
  }
}
```

**Entity Manager class** 
- We create a `Repository` bean to create an entity Manager. 
- Use EntityManager to do the work by injecting it using `@PersistenceContext` decorator.
- 

```java
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

@Repository
public class EmployeeRepository {
  @PersistenceContext
  private EntityManager entityManager; // entityManager is created by EntityManagerfactory

  //define here methods for CURD operation 
}
```

**Using querymethods in entityManager class**
- Find entity by primary key 

```java
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

@Repository
public class EmployeeRepository {
  @PersistenceContext
  private EntityManager entityManager; // entityManager is created by EntityManagerfactory

  //define here methods for CURD operation 
  public Employee getEmployee(long employeeId) {
      return entityManager.find(Employee.class, employeeId);
  }
}
```

**Working with Queries jpql and sql**
- jpql is the object-oriented version of sql. instead using table name, you specify classname and with columns you use field.s
- define `createQuery()` method in `EntityManager` and use jpql or sql
- Execute the query via one of these methods
  - `getSingleResult()` and `getResultList()`
- When you use JPA to do crud operation, you must use ensure your code run inside `@Transactional`
- JPA has implicit save. 

```java
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class EmployeeRepository {
  @PersistenceContext
  private EntityManager entityManager; // entityManager is created by EntityManagerfactory

  //define here methods for CURD operation 
  public Employee getEmployee(long employeeId) {
    return entityManager.find(Employee.class, employeeId);
  }

  // Using of custom query using jpql to get count
  public long getEmployeeCount() {
    String jpql = "select count(e) from employee e";
    TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
    return query.getSingleResult();
  }

  // List all employees.
  public List<Employee> getEmployees() {


    String jpql = "select e from Employee e";
    TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
    return query.getResultList();
  }

  @Transactional
  public void insertEmployee(Employee e) {
    entityManager.persist(e);
  }
  
  @Transactional
  public void updateEmployee(Employee e) {
      Employee entity = entityManager.find(Employee.class, e.getEmployeeId());
      entity.setName(e.getName());
  }
  
  // Delete entity 
  @Transactional
  public void deleteEmployee(long employeeId) {
      Employee e = entityManager.find(Employee.class, e.getEmployeeId());
      entityManager.remove(e);
  }
}

```

## Working with spring data repositories. 
- Spring data define a general purpose repository interface. 
- some standard API methods for CRUD repository are 
  - `count, delete, deleteAll, deleteAll(Iterable,T>, entities), deleteById, findAll, findAllbyId(iterable), findById, saveAll`
  - Spring data support for Paging and Sorting `PagingAndSortingRepository`
- Spring data also provide technology specific extensions as well such as `MongoRepository and others,`
- You can also define your own domain-specific interfaces.
  - Extend CrudRepository or (sub interface) and specify the entity type and pk type
- You can define specific query methods for your entities
  - Spring data reflects on methods names to create queries
  - You can provide an explicit query string for complex queries. 
- Use `spring-bot-starter-data-jpa`


**Defining Repository**

```java
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
  List<Employee> findByRegion(String region);

  @Query("select emp from Employee emp where emp.dosh >=?1 and emp.dosh <=?2")
    // jpql query
  List<Employee> findInSalaryRange(double from, double to);

  Page<Employee> findSalaryGreaterThan(double salary, Pageable pageable);
}
```

## Getting started with REST. 

**Define a Restcontroller**
- `@Controller or @RestController`
- `@RequestMapping(optional base url)`
- `@CrossOrigin(optional CORS support`
- Define methods annotated with one of the following:
  - `@GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @RequestMapping`
  - Request mapping a just a general form of other form.

```java
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/simple")
@CrossOrigin
public class SimpleController {
  private Map<Long, Product> catlog = new HashMap();

  @GetMapping(value = "/products", produces = {"application/json", "applicatin/xml"})
  // Need to add jackson xml jar to produce xml 
  public Collection<Product> getProducts() {
    return catlog.values();

  }

  @GetMapping(value = "/products/{id}", produces = {"application/json", "application/xml"}, consumes={"application/json", "application/xml"})
  // Need to add jackson xml jar to produce xml 
  public ResponseEntity<Product> getProducts(@PathVariable Long id) {
    Product p = catlog.get(id);
    if (p == null) return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok().body(p);

  }
  
  @GetMapping(
          value="/products",
          produces = {"application/json", "application/xml"}
  )
  public responseEntity<Collection<Product>> getProductsMoreThan(
          @RequestParam(value="min", required=False, defaultValue="0.0") double min) {
      Collection<Product> products = catlog.values()
              .stream()
              .filter(p -> p.getPrice() > min)
              .collect(Collectors.toList());
      
      return ResponseEntity.ok().body(products);
      
  }
  

}
```

- Hoping ResponseEntity gives control over entire HTTP response body
- We can set HTTP headers and status code
- Just returning the data only populate the http response body but it doesn't set the HTTP headers or status code.

**Rest Template**
- Spring enables you to implement client code to consume REST services via ta `RestTemplate` class 
- Include the `spring-boot-starter-web` dependency in pom.xml


## Spring security  
**Spring security autoconfiguration Set the default user***
```bash
spring.security.user.name = "username"
spring.security.user.password="password"
```





**Add spring security dependency to project in `pom.xml`**
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>


 <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-test</artifactId>
      <scope>test</scope>
    </dependency>
```

**Overriding default configuration of spring security** 
- Create a `config` package with a class name of your choice such as `SecurityConfig` and override the defaults.
```bash 
modules
    ├───account
    │   ├───config
          SecurityConfig.java

```
- Inside `SecurityConfig.java` create atleast two beans `userDetailService` and `passwordEncoder`
- UserDetailService bean should return the UserDetails manager with at least on user.
- Add `@EnableWebSecurity` `@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)` decorators in `SecurityConfig.java` class
- Create `SecurityFilterChain` bean to customize permitDeny parameters.

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
      .disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.DELETE)
      .hasRole("ADMIN")
      .antMatchers("/admin/**")
      .hasAnyRole("ADMIN")
      .antMatchers("/user/**")
      .hasAnyRole("USER", "ADMIN")
      .antMatchers("/login/**")
      .anonymous()
      .anyRequest()
      .authenticated()
      .and()
      .httpBasic()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return http.build();
}
```

**Simple Authentication**
```java
package com.pearlinfotech.com.idas.modules.account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService() {
        var userManager = new InMemoryUserDetailsManager();

        var user = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .roles("ADMIN")
                .build();

        userManager.createUser(user);

        return userManager;
    }


    // ########Password EncoderBean#########
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // ######AUTHORIZATION BEAN##################
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.httpBasic();
        http.authorizeRequests()
                .anyRequest().permitAll();


        return http.build();
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
    }

}



```


- spring-bootstarter-security dependency has `spring-security-config and spring-security-web libraries` dependencies.
- In a Spring Web application, this servlet is the `DispatcherServlet`, which handles all incoming requests to the application.
- `Filter` sits before the Serverlet and it can make change the req res object, one or more filter can configure using `FilterChain`. `FilterChain` are interface from `javax.servelet` package.
- `DispatcherServlet` handles all incomming request while `DelegatingFilterProxy`  a special used to enable spiring security.
- If the filter is the last in the chain it has only one method `doFilter()`
  - Filter implementation need to implement three methods `init(), doFilter(), destroy()`
  -For instance, if Spring Security needs to perform a username and password-based authentication, it delegates the request to a filter named UsernamePasswordAuthenticationFilter that is responsible for authenticating the user based on the supplied
    credentials. Similarly, for HTTP basic authentication Spring Security uses BasicAuthenticationFilter
```java
public interface  Filter {
  public default void init(FilterConfig filterConfig) throws  ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException;
  public default void destroy() {}
}
```

**Simple Spring Security Workflow**
![img.png](img.png)

- The AuthenticationFilter intercepts the request and delegates the authentication responsibility to the AuthenticationManager. To implement the authentication logic, the AuthenticationManager uses an authentication provider. To check the username and the password, the AuthenticationProvider uses a UserDetailsService and a PasswordEncoder

**Some classes**
- `UserDetails`: Describe the users for Spring security it is an interface
- `GrantedAuthority`: Allows us to define actions that user can execute.
- `UserDetailsManager`: It extends the UserDetails service plus it is used to  describe actions like creating users and modifying and deleting passwords. There are other UserDetails manager suchas `JdbcUserDetailsManager, LdapUserDetailsManager, InMemoryUserDetailsManager`,  The UserDetailsManager adds behavior that refers to adding, modifying, or deleting the user, which is a required functionality in most web app.
- `UserDetailsService`: The UserDetailsService is only responsible for retrieving the user by username.
- `GrantedAuthority`: . To describe the authorities in Spring Security, you use the GrantedAuthority interface, The GrantedAuthority interface has only one
  abstract method, you often find examples in which we use a lambda expression for its implementation. Another possibility is to use the `SimpleGrantedAthority` class to create authority instance
- Dependencies between the components involved in user management. The UserDetailsService returns the details of a user, finding the user by its name. The UserDetails contract describes the user. A user has one or more authorities, represented by the GrantedAuthority interface. To add operations such as create, delete, or change password to the user, the UserDetailsManager contract extends UserDetailsService to add operations.

![img_1.png](img_1.png)

**Describing User**
-  For Spring Security, a user definition should respect the UserDetails contract.

**Minimum implementation of userDetails***
```java AppUser.java
public class AppUser implements UserDetails {
 @Override
 public String getUsername() {
 return "bill";
 }
 
 @Override
 public String getPassword() {
 return "12345";
 }
 
@Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
 return List.of(() -> "READ");
 }
 
  @Override
 public boolean isAccountNonExpired() {
 return true;
 }
 @Override
 public boolean isAccountNonLocked() {
 return true;
 }
 @Override
 public boolean isCredentialsNonExpired() {
 return true;
 }
 @Override
 public boolean isEnabled() {
 return true;
 }
 
}

```
- Another method is to use a `UserBuilder` from spring is core which is the preferable way
- The User class from the org.springframework.security.core.userdetails package is a simple way to build instances of the UserDetails type
```java
import org.springframework.security.core.userdetails.User; 

UserDetails u = User.withUsername("bill")
 .password("12345")
 .authorities("read", "write")
 .accountExpired(false)
 .disabled(true)
 .build();
```

**Create a User Entity**
```java

```

*****PasswordEncoder*****: 
  - It encodes password and verifies if the password matches an existing encoding.
  - the PasswordEncoder is mandatory for the Basic authentication flow.
  - PasswordEncoder exists together with the default `UserDetailsService`.
  -  Spring Boot also chooses an authentication method when configuring the defaults, HTTP Basic access authentication. It’s the most straightforward access authentication method. Basic authentication only requires the client to send a username and a password through the HTTP Authorization header. In the value of the header, the client attaches the prefix Basic, followed by the Base64 encoding of the string that contains the username and password, separated by a colon (:)

*****UserDetailsService*****:

**DelegatingFilterProxy and the FilterChainProxy and SecurityFilterChain**

- These act as the entry point for an HTTP request into the Spring Security infrastructure
- If you are configuring multiple SecurityFilterChains in your application, you need to ensure the order of the chains. You can use Spring’s @Order annotation to  order the SecurityFilterChains


**User authentication**
- `SecurityContextHolder`: This class associates the SecurityContext instance  to the current execution thread. A SecurityContext contains information about an authenticated principal, such as username, user authorities, and other
  user identification details
- The `SecurityContextPersistenceFilter` manages the SecurityContext instance,  This filter tries to retrieve the SecurityContext from a SecurityContextRepository
- The `HttpSessionSecurityContextRepository` implementation tries to load the SecurityContext from the HTTP Session, In the beginning, as we are not authenticated,
  an empty security context is added to the SecurityContextHolder 

**Spring security autoconfiguration**
- The SecurityAutoConfiguration is at the heart of Spring Security autoconfiguration. It leverages three other classes, SpringBootWebSecurityConfiguration, WebSecurityEnablerConfiguration, and SecurityDataConfiguration, to perform the  autoconfiguration

### portable service abstraction  

### AOP: Aspect oriented programming
- AspectJ


### Unit testing
- Unit testing is writing test for each of the individuals methods. 
- Junit is a unit testing framework.
- Junit falls under the automated testing, where you can integrate with `CI`
- Absence of failure is success 
- Create a Java Project in Eclipse without using Modules 
- Add sum method 
- Create a new Test source folder 
- Use Eclipse Menu to create New JUnit 5 Test Case, it would automatically ask to add JUnit 5 to class path.

```textmate
assertEquals(1,2)//Lot of types
assertTrue
assertFalse
assertNotNull
assertNull
    
@BeforeAll: these should be static method
@BeforeEach: these should be static method
@AfterAll
@AfterEach
```


```java
package com.pearlinfotech.com.idas.modules.practice.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MathTest {



  @BeforeAll
  static void beforeAll() {
    System.out.println("Run before running all methods");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("afterAll");
  }


  @BeforeEach
  void beforeEach() {
    System.out.println("BeforeEach is run before every test");
  }

  @AfterEach
  void afterEach() {
    System.out.println("Run after completion of every test");
  }

  @Test
  void test1() {
    System.out.println("test1");
  }

  @Test
  void test2() {
    System.out.println("test2");
  }

  @Test
  void test3() {
    System.out.println("test3");
  }

  

 
  @Test
  public void simpleTest() {
    System.out.println("This is testing");
  }

  @Test
  public void sumTest() {
    Math test = new Math();

    int result = test.sum(new int[] {1,2,3});
    assertEquals(result, 6);

  }

}
```

## Mockito

## Jar version overriding
If you like to use the different package version, you can overwrite using `properties attribute` in pom.xml file
It is not recommended to override a version of jars, rather upgrade spring
```xml
<properties>
		<java.version>19</java.version>
        <hibernate-validator.version>5.2.3.Final</hibernate-validator.version>
</properties>
```


## Password Encoder.
- Create a `PasswordConfig` class inside security package and return the `PasswordEncoder` instance form this class `PasswordEncoder` is an interface
- `PasswordEncoder` has three methods `encode, upgradeEncoding, matches`

```java
// PasswordConfig.java
package com.pearlinfotech.com.idas.modules.account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);

  }
}

```

- Use the `PasswordEncoder` bean in `SecuityConfig` as shown below. 
```java
// SecurityConfig.java
public class SecurityConfig {

  @Autowired
  private final PasswordEncoder passwordEncoder;

  public SecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}
```
- Here passwordEncoder is declared as final so, it needs to be initialize inside constructor, even though it is `@Autowired`

## User detail
- username unique, password(encoded), roles and authorities

## Intellij shortcuts
```java
ctrl + o -> to see the list of suggested 
ctrl +p -> to see the list of parameters.
ctrl + alt + v -> extract to variable
```

## Role and authorities(Permissions)


## Adding jwt authentication 


### Create certs directory in resource folder.
```aidl
resource
    certs
    static
    templates
    application.properties
```

- Generate `public key` and `private key` for cets.
```bash
  # generate  key pair
  openssl genrsa -out keypair.pem 2048
  # generate public key from key pair
  openssl rsa -in keypair.pem -pubout -out public.pem
  #Generate private key
  openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

- Create configuration to use these public and private key.

### Create configuration class to  Rsa public and private key under security folder  

```java RsaConfiguration.java 

@ConfigurationProperties(prefix = "rsa")
public record RsaConfiguration(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
}

```

- Register this configuration inApplication
```java 
@SpringBootApplication
@EnableConfigurationProperties(RsaConfiguration.class) // Registration done here.
public class IdasApplication {


    public static void main(String[] args) {

        SpringApplication.run(IdasApplication.class, args);

    }

}
 

```

- **Set the private and public key in application.properties file**
```aidl

# Rsa key properties.
rsa.private-key=classpath:certs/private.pem 
rsa.public-key=classpath:certs/public.pem

```

- **Configure the JWT decoder Bean in `SecurityConfig.java`** class 

```java

@Bean
public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaConfiguration.rsaPublicKey()).build();
        }

```

**Using cliRestClient**

```bash
# Using basic Auth
htpp :8080 --auth admin:12345 -v


#Using Authorization header 

http :8080/api/documents 'Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzE2MzI0MjIsImlhdCI6MTY3MTYyODgyMiwic2NvcGUiOiJST0xFX0FETUlOIFJPTEVfQUtQTCJ9.LFeAmn9dkFFo7iH1dzVr0t-5uEfADGhgx0LvNIvHccE6-m05QtFlA32fdm1kyBXCkaTOhX4fdqEO3Dtg_SeRrzPWLvPB9JaAj-pIovezn2a3J55rlrWVKYJ-PuMQebzp0Q675M7_vz4OPvj4AFzimus9v5Ch1Apu56Idsgpfw9jndfjB9aTKRpYeeG2fBzkcmBokCXjCvq-TNHoVlpU9MtNhst6mgiquue3fpz5aDNuDSw8TUJrGBifMfubRrgo4ZyytZRTqNZJMqDIHuCADF4ImRP0hUmuzqeQ7NBdkOzN1zNefBFLJo0mCu6j8hPq1udynn0E4id6BdQiPDl1k0A'
```


**Enable method level security**
- Add `@EnableMethodSecurity(prePostEnabled=true`) in `SecurityConfig` for method level authorization
```java

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    
```

**use @JsonIgnore** annotation to cover the `stackoverflow error` in one to many or many to many fields

**`@Temporal(TemporalType.TIMESTAMP)` is used to define TIMESTAMP database type in spring boot.

### CreatedDate, updatedDate and modifiedBy and createdBy defination. 
- Create `config` package and add `JpaConfig.java`
- implement `AuditorAware` interface and override a `getCurrentAuditor()` method to get the userName detials
- Create `AuditorAware` bean with `EnableJpaAuditing` annotation in `JpaConfig.java` file.
- 
**JpaConfig.java** 
```java
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    public class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Principal principal =  SecurityContextHolder.getContext().getAuthentication();
            return  Optional.of(principal.getName());
        }
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}

```

**Use `@CreatedAt`, `@LastModifed` annotation in `Entity`
- `@EntityListeners(AuditingEntityListener.class)` in the `Entity` class
```java
import javax.persistence.Entity;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
  @LastModifiedBy
  @Column(name="lastModifiedBy")
  private String lastModifiedBy;

  @CreatedBy
  @Column(name="createdBy", updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name="createdAt", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedDate
  @Column(name="lastModifiedAt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedAt;

}
```

## Unique on multiple column
```java
- In a Spring application, you can create a unique constraint on multiple columns by using the @UniqueConstraint annotation on the @Table annotation.
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "username"})
})
public class User {
    // ...
}
```

## Using `@RequestParam` in controller. 
- To use `@RequestParam` you need to use spring validator
- Simply put, we can use `@RequestParam` to extract query parameters, form parameters, and even files from the request
- Method parameters annotated with @RequestParam are required by default.
- We can configure our @RequestParam to be optional, though, with the required attribute: `@RequestParam(required = false) String id`

```java

@RestController
public class MyController {

    @PostMapping("/users")
    public void createUser(@RequestParam("name") String name,
                           @RequestParam("age") int age,
                           @RequestParam("avatar") MultipartFile avatar) {
        // Do something with the name, age, and avatar variables
    }
}
```
## Pagination 

## Register and Login 

## Document Saving 

## Document scanning 

## AuditHistory and envers

## DTO and Validation 
- Add spring validator 
- Create an exception handling class to process errors
- Create DTO class to match the user request 
- Add @Valid DTOClass class in Spring controller. 


**Adding spring validator**
```java

<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>


```

**Create Exception Handling class** in `exception` package
- All the exceptions are handled using this class
```java 
package com.pearlinfotech.com.idas.modules.account.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidDtoArgument(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        // grab key and values
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;


    }
    
    // Handle RoleNotFound
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoleNotFoundException.class)
    public Map<String, String> handleRoleNotFoundException(RoleNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }



}

```

**Create DTO class inside DTO package**

```java
package com.pearlinfotech.com.idas.modules.account.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    private String roleName;
}

```

**Use DTO in controller**
```java
    // Create a new role
    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody @Valid RoleRequest roleRequest) {

        try {
            var newRole = roleService.createRole(roleRequest);
            return new ResponseEntity<>(newRole, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Role" + roleRequest.getRoleName()+  "already exist");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }


```



## Composite Key  defining 
- First create an Embeeded class

```java
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable // this is the key 
class CompositeKey implements Serializable {
  private String email;
  private String phone;

}
```

- Use the above Composite key in entity class

```java


import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

public class Employee {
  @EmbeddedId
  private CompositeKey pk;
  private String employeeCode;
}
```

## Spring Boot with Swagger


## Entity Rest code generator tool
- Use `Entity Spring Rest CODE generator` with jetBran plugins

## Miscellaneous

**Component Scan**
```java
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({'com.example.controller.*', 'com.example.service.*'})
public class IdasApplication {
}
```

**Extracting value from application.properties**

```java
import org.springframework.beans.factory.annotation.Value;

public class MyEntity {
  @Value("${app.name}}")
  private String name;

  @Value("${app.lastName:IAMDEFAULTVALUE}}")
  private String lastName;
}
```
- JackSon library will convert java objects in json.

**Build application**
- Use `maven` spring boot build plugin to build to jar.
- Builded jar file is dispatched to `target` folder
- to run jar file use command `java -jar myapp.jar`

**`@JsonResponse` annotation to ignore fields. 
- Use this annotation to not to populate the fields in JsonResponse, this useful to stop `recursive` serialization.

**`@JsonProperty` is used to rename the field while rendering the data.

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
  private int id;

  // in response it is rendered as `first_name` as a key rather than firstName
  @JsonProperty("first_name")
  private String firstName
}
```

**Use `Builder.Default` to set the default value in entity from `Lombok`
```java
public class Role {
  @Column(name = "uuid", unique = true)
  @Builder.Default
  private String uuid = UUID.randomUUID().toString();
  
  
  @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
  @JsonIgnore
  @Builder.Default
  private Set<RoleAuthority> authorities = new HashSet<>();

}
```
- In `lombok` `@Data` is equivalent to `@Getter, @Setter, @ToString, @EqualAndHashCode`

**Use `@EntityScan` in `Application.java` to scan all the entity if you want explictly.

- Some repository exists are `JpaRepository, PagingAndSortingRepository, CrudRepository`
- use `@EnableJpaRepositores` in application to enable jparepository.
- Use `@Valid` annotation using spring validation
- Use `@PathVariable` annotation
- `@RequestParam` are called queryParameters in url `?name=20` 

## Pagination 
- Use `Pageable` interface

**Paging**
```java
public List<User> getAllUserwithPageable(int pageNo, int pageSize) {
        // Zero index based paging.
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return userRepository.findAll(pageable).getContent();
}

```

**Sorting**
```java
public List<User> sortingUser()  {
    Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
    return userRepository.findAll(sort);
    
}
```


## Jpql 
- Use `@Query` annotation  in repository interface
- Use `@Modifying` and `@Transactional` annotation for update query while using `jpql`

```java
import com.pearlinfotech.com.idas.modules.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<T, User> {
    @Query("From student where firstName=:firstname and lastName=: last_name")
    User getByLastNameAndFirstName(String lastName, @Param("last_name") String firstName);
}
```


## Spring boot file upload and download
**Store file in file system** 

***Entity***

```java
public class FileData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String type;
  private String filePath;
}
```

***FileDataRepository.java***
```java
public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String fileName);
}
```

***StorageService.java***
```java
@Service
public class StorageService {

  @Autowired
  private FileDataRepository fileDataRepository;

  private final String FOLDER_PATH="/Users/javatechie/Desktop/MyFIles/";

  
  public String uploadImageToFileSystem(MultipartFile file) throws IOException {
    String filePath=FOLDER_PATH+file.getOriginalFilename();

    FileData fileData=fileDataRepository.save(FileData.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .filePath(filePath).build());

    file.transferTo(new File(filePath));

    if (fileData != null) {
      return "file uploaded successfully : " + filePath;
    }
    return null;
  }

  public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
    Optional<FileData> fileData = fileDataRepository.findByName(fileName);
    String filePath=fileData.get().getFilePath();
    byte[] images = Files.readAllBytes(new File(filePath).toPath());
    return images;
  }
}
```

***Controller.java***
```java
@PostMapping("/fileSystem")
	public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file) throws IOException {
		String uploadImage = service.uploadImageToFileSystem(file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadImage);
	}

	@GetMapping("/fileSystem/{fileName}")
	public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
		byte[] imageData=service.downloadImageFromFileSystem(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
```
