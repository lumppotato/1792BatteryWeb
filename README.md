Spring boot app for 1792 rtr battery management
Currently using mariahdb 
Java V25


Install tomcat 11
Install mariahdb or mysql
Database Dump Added User: admin , PW: admin
you will have to add the connection to your database



Layout
src > main > java > com.r1792 - Main Java Folder contains the main application and securityConfig (for logging in)
                        controller - this is where addresses for web get mapped
                        models - these are the objects
                        repository - these control what each has access to
                        service - access data for each type of model
                        tools - currently not used but was planning on using it for calculations for battery statics and rfid determinations

            resources - these are the web resources
                        static - Images and styles
                        templates - HTML pages
                application.properties - used to store database connection info