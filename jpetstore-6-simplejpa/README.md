jpetstore-6-simplejpa
---------------------

#####This project is an exercise to port the original JPetStore-6 from RDBMS/MyBatis to NoSQL/SimpleDB using JPA/SimpleJPA.#####

######BEWARE that SimpleJPA does not support:######
* Creation of DELETE and UPDATE queries from EntityManager, e.g.,
```java
Query query = em.createQuery("delete from Category");
```
* Creation of named queries from EntityManager, e.g.,
```java
Query query = em.createNamedQuery("Category.getCategoryList");
```

######Technologies and software used:######
* [Java SE 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Spring](http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/)
* [JPA](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)
* [SimpleJPA](https://github.com/appoxy/simplejpa)
* [SimpleDB](http://aws.amazon.com/simpledb/)
* [AWS SDK for Java](http://aws.amazon.com/sdkforjava/)
* [Apache Maven](http://maven.apache.org/index.html)
* [JUnit](http://junit.org/)
* [Guava Libraries](https://code.google.com/p/guava-libraries/)
* [IntelliJ IDEA](http://www.jetbrains.com/idea/)
