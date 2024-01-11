## Postgresql cli commands

```
\l -- to list database
\c -- to use database
\dt -- to list tables
```

## Creating enum and using enum package
```
-- require pg contrib to be installed.
CREATE EXTENSION IF NOT EXISTS "pg_enum";
 ```

## Alter column 
```sql
alter table listed_companies add column ipoalloted_date date null
add column total_listed_share number(11) null
add column public_holding number(2) null
```



