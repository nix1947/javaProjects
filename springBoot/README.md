- [Spring-data-jpa](#spring-data-jpa)
- [User Model](#user-model)
- [User Repository](#user-repository)
- [Test UserRepository](#test-userrepository)
- [Embeddable and Embedded](#embeddable-and-embedded)
- [JPA repositories.](#jpa-repositories)
- [Using Custom query](#using-custom-query)
- [Using native queries](#using-native-queries)
- [Native Query with name param](#native-query-with-name-param)
- [Transaction and Modifying Annotation](#transaction-and-modifying-annotation)
- [Relationship](#relationship)
- [One to Many](#one-to-many)
- [Paging and Sorting](#paging-and-sorting)
- [Sorting](#sorting)
- [Many to Many](#many-to-many)
- [Creating Services](#creating-services)
- [Creating models.](#creating-models)
- [Configure password encoder](#configure-password-encoder)
- [Sending email to activate the user.](#sending-email-to-activate-the-user)
- [Relation of User and Verification Token](#relation-of-user-and-verification-token)
- [Using logger](#using-logger)
- [Getting the current Path in java Spring](#getting-the-current-path-in-java-spring)
- [Copy attributes between `Entity` and `Model`](#copy-attributes-between-entity-and-model)
- [Whitelisted URls in spring Security](#whitelisted-urls-in-spring-security)
- [Set the default date in column hibernate.](#set-the-default-date-in-column-hibernate)


# Spring tutorials

## User Model
Here table name is given to `tbluser` so as not to conflict with the database global naming. 

- User and Role has many to many relation through User_Role table using `UserRole.java` class
- For adding extra attributes use `UserRole.java` class 
  
-Role and and Permission has many to many relationship
- Role and Permission table are joined through `role_permission` table


**User.java**
```java 

// User.java
package com.pearlinfotech.das.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_staff")
    private boolean isStaff;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_locked")
    private boolean isLocked;


    @ManyToOne // when referencing id attribute use ManyToOne
    //@JoinColumn(name = "created_by") // @Column is not allowed, use JoinColumn for ReferenceColumn
    private User createdBy;

    @ManyToOne
    private User modifiedBy;

    @OneToMany(mappedBy = "role")  // When referencing sets using OneToMany
    private Set<UserRole> roles = new HashSet<>();


}

```

**Role.java**

```java
// Role.java 
package com.pearlinfotech.das.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User modifiedBy;

    @OneToMany(mappedBy = "user")
    // UserRole is the user_role table
    // mapped to user attributed in UserRole class.
    private Set<UserRole> users;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> permissions;


}

```

**UserRole.java**
```java
// user_role
// UserRole.java
package com.pearlinfotech.das.account.model;


import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    // One To Many Relationship with User, You can use OneToMany relationship in User table aswell.
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

```

**Permission.java** 
```java
package com.pearlinfotech.das.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "permission", nullable = false, unique = true)
    private String permission;

    @Column(name = "module_name")
    private String module;

}

```

**RolePermission.java**
```java
package com.pearlinfotech.das.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles_permissions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {

    @Id
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

}

```

## User Repository
```java 
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

```

## Test UserRepository
Hover over `UserRepository` press `ALT + ENTER` and generate test to test the UserRepository. 
*Use `@DataJpaTest` as it won't impact the database, using `@SpringBootTest` will impact the database, right now as we need to test for the insert so we are using `@SpringBootTest`


## Embeddable and Embedded  
```java

@Embeddable // Embeddable won't create table
@AttributeOverrides({
    @AttributeOverride(
        name="name",
        column=@Column(name="guardian_name")
    ),
    @AttributeOverride(
        name="email",
        column=@Column(name="guardian_email)
    )
})
class Guardian {
    @Id 
    private id Long; 
    private String name; 
    private String phone; 
}

class Student {
    @Id
    private id Long 

    @Embedded
    private guardian Guardian;

}
```

## JPA repositories. 
- To define custom method just write the method signature that's it. 
- For example `findByUsername`

```java
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUsername(String username);

    // use of Containing
    public List<User>findByFirstNameContaining(String firstName);

    public List<User> findByLastNameNotNull(String lastName);
}

```
*For more reference consult spring-data-jpa reference doc `jpa.query-methods`*

## Using Custom query
- `@Query` annotation is used. 
  
```java 
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUsername(String username);

    // JPQL query, these are based on class name not table name
    @Query("select user from User user where user.email = ?1")
    public User getUserByUsername(String username);
}

```

## Using native queries 
```java
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query(
            value = "select * from users where users.email_address=?1",
            nativeQuery = true
    )
    public User getUserByNativeQuery(String username);
}

```

## Native Query with name param
```java
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     @Query(
            value = "select * from users where users.email_address=:username",
            nativeQuery = true
    )
    public User getUserByNativeQueryNamedParam(@Param("username") String username);

}

```

## Transaction and Modifying Annotation
```java
package com.pearlinfotech.das.account.repository;

import com.pearlinfotech.das.account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Modifying
    @Transactional
    @Query(
        value = "update users set username =:username where email =:email",
        nativeQuery = true
    )
    int updateNameByEmail(@Param("email") String email, @Param("username") String username);
}

```

## Relationship
**Course.java**
```java
class Course {
    @Id 
    @SequenceGenerator(
        name="course_sequence",
        sequenceName="course_sequence",
        allocationSize=1     
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="course_sequence" 
    )
    private Long id; 

}
```

**CourseMaterial.java**
```java
class CourseMaterial {
    @Id 
    @SequenceGenerator(
        name="course_sequence",
        sequenceName="course_material_sequence",
        allocationSize=1     
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="course_material_sequence" 
    )
    private Long id;
    @OneToOne(mappedBy="course") // course is attribute in CourseMaterial
    private CourseMaterial courseMaterial;



    
    @OneToOne(
        cascade = CascadeType.ALL // cascadeType.all will save parent child 
        fetch = FetchType.Lazy // only Fetch courseMaterial, on Eager fetch all.
    )
    @JoinColumn(
        name="course_id" // Name of the key in CourseMaterialTable
        referencedColumnName="id" // Attribute in Course mode private Long id
    )
    private Course course;
}
```

## One to Many
- A teacher can teach Multiple courses, 
- Teacher to Course (one To Many) one teacher can teach multiple course
- Course to Teacher (Many To one) many course can be taught by one teacher
  
```java
@Entity
@Builder
class Teacher {
    @Id
    @Column(name="id")
    private Long id;

    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="teacher"
    )
    private <List>Course courses; 
}

@Entity
@Builder
class Course{

    @Id
    @Column(name="id")
    private Long id; 

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.Lazy
    )
    @JoinColumn(
        name="teacher_id",
        referenceColumnName="id"
    )
    private Teacher teacher
    
}

```

## Paging and Sorting 
- JPA repository extends Paging and Sorting 

```java

public void findAllPagination() {
    Pageable firstPageWithThreeRecords = PageRequest.of(1, 2) //page, size

    List<Course> courses = courseRepository.findAll(firstPageWithThreeRecords).getContent();

    Long totalElements = courseRepository.findAll(firstPageWithThreeRecords).getTotalElements();
}

```

## Sorting

```java
    
    public void findAllSorting() {
        Long currentPageNumber = 0;
        Pageable page = PageRequest.of(currentPageNumber, 10, Sort.by("title").descending().and(Sort.by("credit"))) //currentPage and size 

    List<Course> courses = courseRepository.findAll(page).getContent();

    }

```

**In JPA courseRepository**
```java 

Page<Course> findByTitleContaining(String title, Pageable pageRequest) {

}


```

## Many to Many 
```java

Class Student {
    @Id
    private Long id; 


}

class Course {
    private Long id;

    @ManyToMany
    @JoinTable(
        name="student_course",
        joinColumns = @JoinColumn(
            name="course_id",
            referenceColumnName="id"
        ),
        inverseJoinColumns=@JoinColumn(
            name="student_id",
            referenceColumnName="id"
        )
    )
    private List<Student> students;

    public void addStudents(Student student) {
        if(students == null) students = new ArrayList<>();
        students.add(student);
    }
}



```
<<<<<<< HEAD

## Creating Services 
**Create UserService interface and its implementation**
```java 
package com.pearlinfotech.das.account.service;

public interface UserService {
}

```
**Implementation class**
- Implement UserServiceImpl class with `@Service` implementation
  
```java
package com.pearlinfotech.das.account.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
}

```

## Creating models. 
- Models are created to mapped with the `Client Request` 
- Entity are created to mapped with database columns.
- In model only the necessary fields that are facing to user are placed.
- For less complex you can use directly entity
- All the validation of code are added to the models or controller.  
  
```java 
package com.pearlinfotech.das.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String matchingPassword;
}

```

## Configure password encoder 
- Create config folder in a package with `WebSecurityConfig.java` class. 
- Add `Spring-security` 
- Create `passwordEncoder` function that return `BcryptPasswordEncoder`
- To use the `passwordEncoder` function annotate with `Bean` 

```java 
package com.pearlinfotech.das.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}
```

- Now use the `passwordEncoder` bean in `userServiceImpl` class as below

```java
package com.pearlinfotech.das.account.service;

import com.pearlinfotech.das.account.entity.User;
import com.pearlinfotech.das.account.model.UserModel;
import com.pearlinfotech.das.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return user;

    }
}
```

## Sending email to activate the user. 
- use `ApplicationEventPublisher` class to work with events. 
- Create an `ApplicationEventPublisher` object as `publisher` and autowired with the controller.
- Get the request from the browser, Save the new user 
  - ` User user = userService.registerUser(userModel);`
- After saving new User emit an event using `publisher` object and generate the tokens and save the token to verify the user
- Create `event` package to host all the `ApplicationEvent` type objects, here `RegistrationCompleteEvent` class is used which  which  ultimately send  by the `publisher` object and consumed by the `EventListeners` where `RegistrationCompleteEvent` listener has been created to consume the `user` to save the tokens for `user`. 

  
  
```java 
@package com.pearlinfotech.das.account.controller;

import com.pearlinfotech.das.account.entity.User;
import com.pearlinfotech.das.account.event.RegistrationCompleteEvent;
import com.pearlinfotech.das.account.model.UserModel;
import com.pearlinfotech.das.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;


    @PostMapping
    public String registerUser(@RequestBody UserModel userModel) {
        User user = userService.registerUser(userModel);
        // Send the success link using event to the user.
        publisher.publishEvent(new RegistrationCompleteEvent(user, "url"));
        return "success";

    }
}

``` 
**ApplicationEvent**
- ApplicationEvent is a data structure which is carrying `user` and `url` towards `RegistrationCompleteEventListener` where `RegistrationCompleteEventListener` will generate token and save the tokens for the user. 
  
```java
package com.pearlinfotech.das.account.event;

import com.pearlinfotech.das.account.entity.User;
import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;

    }

}

```


```java 
// event/listener/RegistrationCompleteEventListener.java
package com.pearlinfotech.das.account.event.listener;

import com.pearlinfotech.das.account.entity.User;
import com.pearlinfotech.das.account.event.RegistrationCompleteEvent;
import com.pearlinfotech.das.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the verification token for the user with the link.
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(user, token);

        // Once the link created send mail to the user
        String url = event.getApplicationUrl() + "verifyUserRegistration?token=" + token;
        log.info("Click the link to verifyYour account: {}", url);

    }
}


```

## Relation of User and Verification Token 
- User and VerificationToken has `OneToOne` relationship 

```java 
// entity/VerificationToken.java with 10 
package com.pearlinfotech.das.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "verification_token")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_TIME = 10; // 10 minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN")
    )
    private User user;


    public VerificationToken(User user, String token) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);

    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());

    }
}

```

## Using logger
- To use logger annotate the class to `@Slf4j` and use it
```java
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    log.info("ApplicationListener is calling");
}
```

## Getting the current Path in java Spring 
```java
import javax.servlet.http.HttpServletRequest;


   private String getCurrentPath(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
```

## Copy attributes between `Entity` and `Model`

## Whitelisted URls in spring Security
- Use SecurityFilterChain in `WebSecurityConfig.java` file. 
- To white list url define `WHITE_LIST_URLS` 


```java 
    private static final String[] WHITE_LIST_URLS = {
            "/register"
    };
```

- Create `securityFilterChain` bean as shown below.
  
```java 
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll();

        return http.build();

    }
```
- The complete implementation is as follow in `WebSecurityConfig`
 

```java
  package com.pearlinfotech.das.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/api/register"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll();

        return http.build();

    }
}
```

## Set the default date in column hibernate.
- Spring data jpa way
```java 
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
```
- **Database way**
  
```sql
ALTER TABLE users MODIFY created_date DATE NOT NULL DEFAULT (CURRENT_DATE)
```
=======
>>>>>>> 3fc05b546b809651454911ba7c9f72ecfc3336e9
