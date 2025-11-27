# Spring boot app for 1792 rtr battery management
## Currently using mariahdb 
## Java V25
 
## How to run / develope
### Install tomcat 11
### Install mariahdb or mysql
- Install it then log into it "mariadb -u root -p"
- 2. Create the database
CREATE DATABASE batterydb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

- 3. (Recommended) Give the user 1792 full rights on this database
GRANT ALL PRIVILEGES ON batterydb.* TO '1792'@'%';

- 4. Apply the changes
FLUSH PRIVILEGES;

- 5. Verify it worked
SHOW DATABASES LIKE 'batterydb';

- 6. Exit
EXIT;

 
### Database Dump Added User: admin , PW: admin it will created automatically on first run
### you will have to add the connection to your database



# Layout
## src > main > java > com.r1792 - Main Java Folder contains the main application and securityConfig (for logging in)
                        controller - this is where addresses for web get mapped
                        models - these are the objects
                        repository - these control what each has access to
                        service - access data for each type of model
                        tools - currently not used but was planning on using it for calculations for battery statics and rfid determinations

            resources - these are the web resources
                        static - Images and styles
                        templates - HTML pages
####                application.properties - used to store database connection info