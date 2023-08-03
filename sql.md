## Basic 
```
-- create table
-- drop table
-- truncate table
-- insert data in table
-- delete data in table
-- update data in table
-- ER diagram
-- Window function
-- Trigger
-- database function
-- view
-- aggregration (min, max, count, sum,)
```

## Features
```concurrent access, access management```

## Data types
```
varchar,
integer,
timestamp
date,
text
```

## Constraints in database
```unique, not null, primary key, foreign key, check```
```
-- Create Department table
CREATE TABLE Department (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(50) NOT NULL
);

-- Create Employee table with various constraints
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department_id INT,
    salary DECIMAL(10, 2) DEFAULT 0,
    hire_date DATE,
    email VARCHAR(100) UNIQUE,
    
    -- Check constraints
    CONSTRAINT chk_salary CHECK (salary >= 0),
    CONSTRAINT chk_hire_date CHECK (hire_date <= CURRENT_DATE),
    
    -- Foreign key constraint
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES Department(department_id),
    
    -- Additional check constraints
    CONSTRAINT emp_name_length CHECK (LENGTH(first_name) + LENGTH(last_name) <= 100),
    
    -- Default constraint
    CONSTRAINT emp_name_default DEFAULT 'John' || ' ' || 'Doe'
);

-- Insert sample department data
INSERT INTO Department (department_id, department_name)
VALUES
    (1, 'HR'),
    (2, 'Engineering'),
    (3, 'Marketing');

```

## Importing database
```
$ createdb mydb
$ psql parch < parch_and_posey.sql
```


## Create query from sql
`Reference: https://stackoverflow.com/questions/16818796/generate-sql-insert-script-from-excel-worksheet/16819629`

```sql
="INSERT INTO grievance_category VALUES('"&A2&"','"&B2&"','"&C2&"',  '"&D2&"',    '"&E2&"', '"&F2&"', '"&G2&"', '"&H2&"' );"
="update tblgroupendowment set name = '" & A2 & "' where policyNo = '" & B2 & "'"
-- Conditional Operators <, <=, >, >=, <>(not equal) !=(not equal)
SELECT * FROMemployees WHERE date_part('year',hire_date) between '2000' and '2005' ORDER BY hire_date DESC;
```





## Offset and fetch
```sql
-- Fetch the nth to nth record using offset and fetch
select * from employees order by salary desc offset 5 rows fetch next 5 rows only;
-- Using limit and offset
select * from employees order by salary limit 100 offset 100
```

## Logical operators
```sql
-- SQL Logical operator: and, or, not, in, like, some, exists, between, any, all, is null

-- IS null
select * from employees where salary is null order by salary; 

-- between 
select * from employees where salary between '20000' and '30000' order by salary; 

-- in 
select * from employees where department_id in ('8', '9') order by department_id; 

-- like: use % or _ 
select * from employees where first_name like 'm%';
select * from employees where first_name like '_ano%' 

-- all operator 
select department_id, * from employees where salary > all(select salary from employees where department_id = 8) order by salary desc;

-- any subquery
-- some is an alias of any can be used interchangebly
-- The ANY operator compares a value to any value in a set according to the condition as shown
select department_id,* from employees where salary > any(select salary from employees where department_id=8)

-- exists exists(select * from employees where department_id=10) -> return true if any row fetched 
-- so query becomes. select department_id, * from employees where true | false
select department_id, * from employees where exists(select * from employees where department_id=10)
```

## Inner join
```sql
SELECT
	first_name,
	last_name,
	job_title,
	department_name
FROM
	employees e
INNER JOIN departments d ON d.department_id = e.department_id
INNER JOIN jobs j ON j.job_id = e.job_id
WHERE
	e.department_id IN (1, 2, 3);
```

## Other joins example.

```sql
-- Self join
SELECT 
    e.first_name || ' ' || e.last_name AS employee,
    m.first_name || ' ' || m.last_name AS manager
FROM
    employees e
        LEFT JOIN
    employees m ON m.employee_id = e.manager_id
ORDER BY manager;


-- Left join.
SELECT
	r.region_name,
	c.country_name,
	l.street_address,
	l.city
FROM
	regions r
LEFT JOIN countries c ON c.region_id = r.region_id
LEFT JOIN locations l ON l.country_id = c.country_id
WHERE
	c.country_id IN ('US', 'UK', 'CN');
	
	
-- FULL OUTER JOIN
-- In theory, a full outer join is the combination of a left join and a right join.
SELECT
	basket_name,
	fruit_name
FROM
	fruits
FULL OUTER JOIN baskets ON baskets.basket_id = fruits.basket_id;


-- SQL CROSS JOIN
-- A cross-join is a join operation that produces the Cartesian product of two or more tables.
-- In Math, a Cartesian product is a mathematical operation that returns a product set of multiple sets.
-- For example, with two sets A {x,y,z} and B {1,2,3}, the Cartesian product of A x B is the set of all ordered pairs (x,1), (x,2), (x,3), (y,1) (y,2), (y,3), (z,1), (z,2), (z,3).
-- In some database systems such as PostgreSQL and Oracle, you can use the INNER JOIN clause with the condition that always evaluates to true to perform a cross join such as

SELECT
	sales_org,
	channel
FROM
	sales_organization
INNER JOIN sales_channel ON 1 = 1;


-- The following query is equivalent to the statement that uses the CROSS JOIN clause above:
SELECT
	sales_org,
	channel
FROM
	sales_organization,
	sales_channel;
	
-- Cross join using cross join keyword.
SELECT
	sales_org,
	channel
FROM
	sales_organization
CROSS JOIN sales_channel; 
```
