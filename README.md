Description:
I used Postgres database which you need to download [Download Postgresapp](https://postgresapp.com/ ).  

It should be accessible via Applications on Mac.  Run the Postgres Admin.  You can use the below scripts:
Note - When creating the Postgres server - the default user is your **system user name.**

```
# CREATE TABLE people (
id integer,
firstname text,
lastname text
);

INSERT INTO people (id, firstName, lastName) VALUES (1, 'first', 'last');
```

You will also want to install Redis Cache:
[Install and Config Redis on Mac](https://medium.com/@petehouston/install-and-config-redis-on-mac-os-x-via-homebrew-eb8df9a4f298)

You can run the below commands:
```
# brew install redis
brew services start postgresql
brew services stop postgresql
redis-cli ping
```

Couple of gotchas related to changing the database schema while testing:

FLUSHDB - clear current database cache
FLUSHALL - clear all datatabase cache

I also had to do a clean maven build when the database structure was updated.
not sure entirely what the issue was

Make sure you have the schema in the table entity:
```
# @Table(name = "people", schema = "public")
```

And make sure the DTO implements Serializable
```
# public class PeopleDto implements Serializable {
```


Outstanding Issues:
When you insert and update data it's not updating the cache.


Resources
[Spring Boot Cache with Redis](https://medium.com/@MatthewFTech/spring-boot-cache-with-redis-56026f7da83a)

[Caching Data in Spring using Redis](http://caseyscarborough.com/blog/2014/12/18/caching-data-in-spring-using-redis/)

[Install and Config Redis on Mac](https://medium.com/@petehouston/install-and-config-redis-on-mac-os-x-via-homebrew-eb8df9a4f298)

[Redis Implementation for Database Operations in Spring Boot](https://medium.com/@alexanderang.24/redis-implementation-for-database-operations-in-spring-boot-2e5bc6f7f5af)

[Spring Boot Redis Cache](https://www.devglan.com/spring-boot/spring-boot-redis-cache)

[GitHub Redis Example](https://github.com/plankrun/learn-cache-redis)

[Creating Postgres Database](https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e)

[Download Postgresapp](https://postgresapp.com/ )
