import grails.plugin.springsecurity.SpringSecurityUtils
// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.config.locations=["file:${userHome}/.grails/${appName}-config.groovy"] 

grails.project.groupId = 'com.luxsoft.impapx' // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

// environments {
//     development {
//         grails.logging.jul.usebridge = true
//     }
//     production {
//         grails.logging.jul.usebridge = false
//         // TODO: grails.serverURL = "http://www.changeme.com"
//     }
// }
//grails.plugin.springsecurity.auth.loginFormUrl='/login/login2'
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.plugin.springsecurity.debug.useFilter = true
        grails.plugin.springsecurity.active = true
    }
    production {
        grails.logging.jul.usebridge = false
        //grails.app.context = '/paperx2'
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
    test{
        grails.plugin.springsecurity.debug.useFilter = false
        grails.plugin.springsecurity.active = false
    }
    impapx {
      //grails.app.context = '/impapx'
    }
}


// log4j configuration
log4j.main = {
    
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%-5p  %c{1}: %m%n')
        //console name:'stdout', layout:pattern(conversionPattern: '%-5p [%t] %c{1}: %m%n')

        // rollingFile name:'luxorFileAppender',
        //             maxFileSize:1024,
        //             file:"$logDir/kyo-luxor.log",
        //             maxBackupIndex:7
        //             layout: pattern(conversionPattern: '%-5p [%t] %c{1}: %m%n')
    }
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%-5p [%c{1}] %m%n')
        file name:'file', file:System.properties['user.home']  + '/.grails/lx-imports.log',
             layout: pattern(conversionPattern: '%-5p [%t] %c{1}: %m%n')
        rollingFile name:'importacionLog',
                    maxFileSize:'1MB',
                    file:System.properties['user.home']  + '/.grails/lx-imports.log',
                    maxBackupIndex:7,
                    layout: pattern(conversionPattern: '%-5p %d{DATE} [%c{1}] %m%n')

    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
    environments{
        
      development{
        
        info  'grails.app.controllers.com.luxsoft'
        info  'grails.app.services.com.luxsoft'
        info  'grails.app.jobs'
        debug 'com.luxsoft.impapx'

      }
        
      produccion{
        error 'grails.app.services'
        error 'grails.app.controllers'
        error 'grails.app.jobs'    
      }
        
    }
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.luxsoft.sx4.sec.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.luxsoft.sx4.sec.UsuarioRole'
grails.plugin.springsecurity.authority.className = 'com.luxsoft.sx4.sec.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                       ['permitAll'],
	'/index':                  ['permitAll'],
	'/index.gsp':              ['permitAll'],
	'/assets/**':              ['permitAll'],
	'/**/js/**':               ['permitAll'],
	'/**/css/**':              ['permitAll'],
	'/**/images/**':           ['permitAll'],
	'/**/favicon.ico':         ['permitAll'],
  '/login/**':               ['permitAll'],
  '/logout/**':                     ['permitAll'],
  '/contabilidad/**':               ["hasAnyRole('ADMIN','CONTABILIDAD')"],
  "/console/**":                    ["hasAnyRole('ADMIN')"],
  "/plugins/console*/**":           ["hasAnyRole('ADMIN')"],
  "/jasper/**":                     ['IS_AUTHENTICATED_REMEMBERED']

]

grails.plugin.springsecurity.roleHierarchy = '''
   OPERADOR > USUARIO 
   ADMINISTRACION > CONTABILIDAD > OPERADOR > USUARIO
   ADMIN > ADMINISTRACION
   ADMIN > TESORERIA
   ADMIN > VENTAS
   ADMIN > COMPRAS
   GASTOS > OPERADOR
   VENTAS > OPERADOR
   COMPRAS > OPERADOR
'''
grails.plugin.springsecurity.failureHandler.exceptionMappings = [
   'org.springframework.security.authentication.CredentialsExpiredException': '/usuario/passwordExpired'
]
grails.plugin.springsecurity.apf.storeLastUsername=true
environments{
    
  development{
    luxor{
        empleadosDb{
          url = 'jdbc:mysql://10.10.1.228:3306/rh'
          username = "root"
          password = "sys"
        }
        
    }
    // grails.assets.bundle=true
  }
    
  production{
    luxor{
        empleadosDb{
          url = 'jdbc:mysql://10.10.1.228:3306/rh'
          username = "root"
          password = "sys"
        }
    }
  }
}
beans {
   cacheManager {
      shared = true
  }
}


grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
grails.databinding.dateFormats = ['dd/MM/yyyy', 'dd-MM-yyyy HH:mm:ss.S', "dd-MM-yyyy'T'hh:mm:ss'Z'"]


environments {
    test {
        grails.plugin.databasemigration.updateOnStart = false
        //grails.plugin.databasemigration.autoMigrateScripts = ['RunApp','TestApp']
        //grails.plugin.databasemigration.forceAutoMigrate = true
        //grails.plugin.databasemigration.dropOnStart = true
        // if (loadTestData) {
        //     grails.plugin.databasemigration.updateOnStartFileNames = ['testchangelog.groovy', 'testdata.groovy']
        // } else {
        //     grails.plugin.databasemigration.updateOnStartFileNames = ['testchangelog.groovy']
        // }
    }
}

auditLog {
  //verbose = true // verbosely log all changed values to db
  logIds = true  // log db-ids of associated objects.
   // Note: if you change next 2 properties, you must update your database schema!       
  //tablename = 'my_audit' // table name for audit logs.     
  //largeValueColumnTypes = true // use large column db types for oldValue/newValue.
  TRUNCATE_LENGTH = 1000
  cacheDisabled = true
  //logFullClassName = true
  //replacementPatterns = ["local.example.xyz.":""] // replace with empty string.
  actorClosure = { request, session ->

    if (request.applicationContext.springSecurityService.principal instanceof java.lang.String){
      return request.applicationContext.springSecurityService.principal
    }
    //def username = request.applicationContext.springSecurityService.principal?.username
    def username = request.applicationContext.springSecurityService.getCurrentUser()?.nombre
    if (SpringSecurityUtils.isSwitched()){
      username = SpringSecurityUtils.switchedUserOriginalUsername+" AS "+username
    }
    return username
  }
  stampEnabled = true
  //stampAlways = false
  stampCreatedBy = 'createdBy' // fieldname
  stampLastUpdatedBy = 'lastUpdatedBy' // fieldname

}



