[![Build Status](https://travis-ci.org/cosminj/java-properties-loader.svg?branch=master)](https://travis-ci.org/cosminj/java-properties-loader)
[![Coverage Status](https://coveralls.io/repos/cosminj/java-properties-loader/badge.svg?branch=master&service=github)](https://coveralls.io/github/cosminj/java-properties-loader?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/568e323e9c1b9802c5000000/badge.svg?style=flat)](https://www.versioneye.com/user/projects/568e323e9c1b9802c5000000)

# java-properties-loader
A pure java framework for loading up dozens of properties from the classpath, System.getProperties and remote URLs that return JSON.

## Technical Characteristics

- Initialization supports loading and combining properties from multiple URIs
- To add a new known property to the system, simply edit

```java
public enum ReferenceProperty {
    // ...
    // add your new property and type
}

public class Value {
    // ...
    // add your type definition and validation here
}
```

- Property files must be specified by a uri such as file://, http:// or the custom classpath:/

- The URI suffix determines the file format -  .properties and .json are supported for now.

- If a property is defined more than once, the last source to set that property value “wins” and is considered the final property value

## Sample Property Files

#### classpath:resources/jdbc.properties
```properties
JDBC_DRIVER=com.mysql.jdbc.Driver
JDBC_URL=jdbc:mysql://localhost/test
JDBC_USERNAME=username123
JDBC_PASSWORD=password123
hibernate.generate_statistics=true
hibernate.show_sql=true
jpa.showSql=true
```

#### file:///tmp/aws.properties
```properties
aws_access_key=AKIAJSF6XRIJNJTTTL3Q
aws_secret_key=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
aws_account_id=12345678
aws_region_id=us-east-1
```

#### http://localhost/global/config.json
```json
{
   “auth.endpoint.uri”: “https://authserver/v1/auth”,
   “job.timeout”: 3600,
   “job.maxretry”: 4,
   “sns.broadcast.topic_name”: “broadcast”,
   “sns.broadcast.visibility_timeout”: 30,
   “population.density”: 2.4,
   “jpa.showSql”: false
}
```

- A sample output by the Main.java example is:

```csv
propertyName, propertyType, propertyValue
```

Here is partial output for the properties files provided:

```csv
aws_access_key, java.lang.String, AKIAJSF6XRIJNJTTTL3Q
aws_account_id, java.lang.Integer, 12345678
aws_region_id, com.amazonaws.regions.Regions, us-east-1
aws_secret_key, java.lang.String, pmqnweEYvdiw7cvCdTOES48sOUvK1rGvvctBsgsa
jpa.showSql, java.lang.Boolean, false
```
