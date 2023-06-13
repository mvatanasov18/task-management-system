# task-management-system


## Table of Contents

- [Introduction](#introduction)
- [Installation](#installation)

### Introduction <a id="introduction"></a>
The project is a Task Management System designed to facilitate efficient task tracking and<br>
collaboration within teams or projects. It provides a centralized platform for organizing, <br>
assigning, and monitoring tasks, ensuring transparency and productivity throughout the<br>
project lifecycle.
### Installation <a id="installation"></a>
to run this project you need:
java17 - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html <br>
sql server express - https://go.microsoft.com/fwlink/p/?linkid=2216019&clcid=0x409&culture=en-us&country=us <br>
ssms - when you install sql server there is a button "install ssms" (click on it) <br>
maven - https://maven.apache.org/download.cgi <br>
(follow a tutorial on how to install maven for example https://youtu.be/p0LPfK_oNCM?t=377)<br>

clone/download the repository<br>
open ssms, connect to your sqlexpress instance, right-click on the \<your-pc-name\>\\SQLEXPRESS box and select properties <br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/b52483dd-5eec-4769-8a06-2fc147f0e820)
<br>
then go to security and make sure that in "Server authentication" the "SQL Server and Windows authentication" option is selected:<br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/cd707c7f-7a38-4ca7-95b1-e856cc24e52a)
<br>if it is not, select it and open sql server configuration manager and click on "sql server services"<br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/c7c2cafe-d1b8-4424-ad98-da29b86a4878)
<br>
then right-click on "sql server" and restart it after that restart "sql server browser"<br> 
<br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/6681dcc3-98a2-4155-95af-153193882094)
<br>
also while you are there make sure that both  "sql server" and "sql server browser" are running<br>
make sure that every protocol is enabled:<br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/ed212ded-97af-48e4-87f9-d0733a78b02d)
<br>
make sure that TCP port is 1433:<br>
![image](https://github.com/mvatanasov18/task-management-system/assets/54852701/5f7c68e7-5cdc-42c8-a165-ece8227c9d9a)
<br>
**__NOTE:__** after every change you should restart "sql server" and "sql server browser" in order the changes to take place<br>

after you have set up the sql server, you can go to ssms, create a "New Query", copy the insides of schema.sql file <br>
located under the resources folder of the project and execute the query<br>
this should create a new login "taskUser" as well as a new database "TaskManagement"<br>

then open application.properties and replace "\<pc-name\>\\\\SQLEXPRESS" your sql server instance's name. <br>
to see your server name, open ssms and in the left window (object explorer) you will be able to see it<br>
don't forget to escape '\\'<br>

<ol>
  <li>Open a command prompt or terminal window.</li>
  <li>Navigate to the root directory of the Spring Boot project where the pom.xml file is located.</li>
  <li>Build the project by running the following Maven command:</li>
<code>mvn clean install</code><br>
  <li>Once the build is successful, you can run the application using the following command:</li>
<code>mvn spring-boot:run</code>
</ol>
