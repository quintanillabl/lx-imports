
dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'org.hibernate.cache.SingletonEhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

dataSource_importacion{
  dialect = org.hibernate.dialect.MySQL5InnoDBDialect
  driverClassName = 'com.mysql.jdbc.Driver'
  username = 'root'
  password = 'sys'
  url = 'jdbc:mysql://10.10.1.228/produccion'
  //url = 'jdbc:mysql://localhost/produccion'
  dbCreate = ''
  readOnly=true
  properties {
    maxActive = 10
    maxIdle = 10
    minIdle = 3
    initialSize = 3
    minEvictableIdleTimeMillis = 60000
    timeBetweenEvictionRunsMillis = 60000
    maxWait = 10000
    validationQuery = "/* ping */"
  }
}

// environment specific settings
environments {
    development {
        dataSource {
            //dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            pooled = true
            dbCreate=""
            url = 'jdbc:mysql://localhost/imapx2?autoReconnect=true'
            //url = 'jdbc:mysql://localhost/paperx2?autoReconnect=true'
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            username = "root"
            password = "sys1"
            properties {
                maxActive = 10
                maxIdle = 10
                minIdle = 3
                initialSize = 3
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                maxWait = 10000
            }
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    /*
    production {
      pooled = true
      dbCreate = ""
      url = "jdbc:mysql://10.10.1.228:3306/paperx2"
      driverClassName = "com.mysql.jdbc.Driver"
      dialect = org.hibernate.dialect.MySQL5InnoDBDialect
      username = "root"
      password = "sys"
      properties {
        // Documentation for Tomcat JDBC Pool
        // http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#Common_Attributes
        // https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/tomcat/jdbc/pool/PoolConfiguration.html
        jmxEnabled = false
        initialSize = 5
        maxActive = 25
        minIdle = 3
        maxIdle = 10
        maxWait = 10000
        maxAge = 10 * 60000
        timeBetweenEvictionRunsMillis = 5000
        minEvictableIdleTimeMillis = 60000
        validationQuery = "SELECT 1"
        validationQueryTimeout = 3
        validationInterval = 15000
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = false
        ignoreExceptionOnPreLoad = true
        // http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#JDBC_interceptors
        jdbcInterceptors = "ConnectionState;StatementCache(max=200)"
        defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED // safe default
        // controls for leaked connections 
        abandonWhenPercentageFull = 100 // settings are active only when pool is full
        removeAbandonedTimeout = 120
        removeAbandoned = true
        // use JMX console to change this setting at runtime
        logAbandoned = false // causes stacktrace recording overhead, use only for debugging
      }
      dbProperties{
        // Mysql specific driver properties
           // http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html
           // let Tomcat JDBC Pool handle reconnecting
           autoReconnect=true
           // truncation behaviour 
           jdbcCompliantTruncation=false
           // mysql 0-date conversion
           zeroDateTimeBehavior='convertToNull'
           // Tomcat JDBC Pool's StatementCache is used instead, so disable mysql driver's cache
           cachePrepStmts=false
           cacheCallableStmts=false
           // Tomcat JDBC Pool's StatementFinalizer keeps track
           dontTrackOpenResources=true
           // performance optimization: reduce number of SQLExceptions thrown in mysql driver code
           holdResultsOpenOverStatementClose=true
           // enable MySQL query cache - using server prep stmts will disable query caching
           useServerPrepStmts=false
           // metadata caching
           cacheServerConfiguration=true
           cacheResultSetMetadata=true
           metadataCacheSize=100
           // timeouts for TCP/IP
           connectTimeout=15000
           socketTimeout=120000
           // timer tuning (disable)
           maintainTimeStats=false
           enableQueryTimeouts=false
           // misc tuning
           noDatetimeStringSync=true
      }

    }
    */

    
    production {
        dataSource {
          pooled = true
          dbCreate=""
          url="jdbc:mysql://10.10.1.228/paperx2?autoReconnect=true"
          driverClassName = "com.mysql.jdbc.Driver"
          dialect = org.hibernate.dialect.MySQL5InnoDBDialect
          username = "root"
          password = "sys"
          properties {
              maxActive = 10
              maxIdle = 10
              minIdle = 3
              initialSize = 3
              minEvictableIdleTimeMillis=1800000
              timeBetweenEvictionRunsMillis=1800000
              numTestsPerEvictionRun=3
              testOnBorrow=true
              testWhileIdle=true
              testOnReturn=true
              maxWait = 10000
          }
          
        }
    }

    impapx2 {
      dataSource {
        pooled = true
        dbCreate=""
        url="jdbc:mysql://10.10.1.228/impapx2?autoReconnect=true"
        //url="jdbc:mysql://localhost/impapx2?autoReconnect=true"
        driverClassName = "com.mysql.jdbc.Driver"
        dialect = org.hibernate.dialect.MySQL5InnoDBDialect
        username = "root"
        password = "sys"
        properties {
            maxActive = 10
            maxIdle = 10
            minIdle = 3
            initialSize = 3
            minEvictableIdleTimeMillis=1800000
            timeBetweenEvictionRunsMillis=1800000
            numTestsPerEvictionRun=3
            testOnBorrow=true
            testWhileIdle=true
            testOnReturn=true
            maxWait = 10000
        }
        
      }
    }
    
}
