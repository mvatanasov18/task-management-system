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
maven - https://maven.apache.org/download.cgi <br>
(follow a tutorial on how to install maven for example https://youtu.be/p0LPfK_oNCM?t=377)<br>

clone/download the repository<br>
when you have installed sql server, open sqlcmd, copy the code form schema.sql file (in the resources folder)<br>
and paste it in sqlcmd (may take some time to execute all command so be patient)<br>
then open application.properties and replace "DESKTOP-6V5OJSN\\\\SQLEXPRESS" you sql server instance.<br>
don't forget to escape '\\'<br>
to see what is your instance, in cmd run:<br>
<code>sqlcmd -S localhost -E -Q "SELECT @@SERVERNAME"</code><br>
<ol>
  <li>Open a command prompt or terminal window.</li>
  <li>Navigate to the root directory of your Spring Boot project where the pom.xml file is located.</li>
  <li>Build the project by running the following Maven command:</li>
<code>mvn clean install</code><br>
  <li>Once the build is successful, you can run the application using the following command:</li>
<code>mvn spring-boot:run</code>
</ol>
