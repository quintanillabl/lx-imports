
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
   //url = 'jdbc:mysql://10.10.1.228/produccion'
   url = 'jdbc:mysql://10.10.1.229/siipapx'
  //url = 'jdbc:mysql://localhost/sw2'
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
            pooled = true
            dbCreate="update"
            //url = 'jdbc:mysql://10.10.1.228/impapx2?autoReconnect=true'
            //url = 'jdbc:mysql://10.10.1.228/paperx2?autoReconnect=true'
            url = 'jdbc:mysql://localhost/paperx2?autoReconnect=true'
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
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    
    
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
