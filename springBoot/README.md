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
            refer   enceColumnName="id"
        )
    )
    private List<Student> students;

    public void addStudents(Student student) {
        if(students == null) students = new ArrayList<>();
        students.add(student);
    }
}



```
