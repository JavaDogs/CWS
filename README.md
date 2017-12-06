[CWS](https://javadog.io/) [![CircleCI](https://circleci.com/gh/JavaDogs/cws.png?style=shield)](https://circleci.com/gh/JavaDogs/cws) [![Coverity](https://scan.coverity.com/projects/13955/badge.svg)](https://scan.coverity.com/projects/javadogs-cws) [![SonarQube](https://sonarcloud.io/api/badges/gate?key=io.javadog:cws)](https://sonarcloud.io/dashboard?id=io.javadog:cws) [![Codacy](https://api.codacy.com/project/badge/Grade/78366d7059554164a3f65ceabe986598)](https://www.codacy.com/app/cws/cws) [![Software License](https://img.shields.io/badge/license-Apache+License+2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
--

# CWS - Cryptographic Web Store
CWS, Cryptographic Web Store or CryptoStore, is designed to be used as a backend
component in other systems. Facilitating protected storing, sharing or
collaboration of data between entrusted parties. It is written purely in Java EE,
allowing it to be deployed in a wide range of different environments, depending
on the need.

The CWS focuses on Circles of Trust, where Members of a Circle will have varying
level of access to the Data belonging to the Circle. This is achieved by a
combination of Symmetric and Asymmetric Encryption, guaranteeing that only
Members with Access can view Data. Storage is safe as all data is stored
encrypted.

Communication is achieved via a public API, which allows both REST and SOAP
based WebService requests. This will give a high degree of flexibility for
anyone to integrate it into their system.

# Build, Install & Run
The current version of CWS has reached the point where it can build, deploy and
run in [WildFly](http://www.wildfly.org/) using PostgreSQL as database. To test,
please make sure that you have Java (8+), [Maven](https://maven.apache.org/) and
[PostgreSQL](https://www.postgresql.org/) installed and running, as well as a
local copy of the CWS sources.

In the accessories folder, you can find the configuration for WildFly 10 and 11, the
files are located in the same folder structure as you need to add them to your
local WildFly installation.

The database files can all be found in the cws-model module under
`src/main/resources/postgresql` where the `01-install.sql` script will create
the database, user (with password) and setup the database with tables & data for
the initial run.

Then do the following:

```
Setup your database: The following will create a database named cws and a user 
named cws_user as well as set up all needed tables.

$ psql postgres
$ \i 01-install.sql

Then build the package and deploy it:

$ cd /path/to/cws/sources
$ mvn clean verify
$ cp cws-war/target/cws.war ${WILDFLY_HOME}/standalone/deployments
$ cd accessories/configuration/wildfly-XX
$ cp -R * ${WILDFLY_HOME}
$ ${WILDFLY_HOME}/bin/standalone.sh -c standalone-cws.xml
```
Now, you should have a running version of CWS which can be reached from the
following SOAP based URL's:

```
http://localhost:8080/cws/system?wsdl
http://localhost:8080/cws/share?wsdl
```

# Who is this for
It is not designed for anyone particular, but as it provides a rather general
API and scales depending on the deployment - it can be used by anyone who finds
it useful. 

# Release Plan
CWS Development has reached the final phase for version 1.0. With the following
milestones still pending, for details, see milestones in GitHub. The original
goal for releasing the final 1.0 was on December 31st, 2017. However, as the
OpenJDK will [include unlimited strength cryptography](https://bugs.openjdk.java.net/browse/JDK-8170157)
as of Java 8u162, to be released on [January 16th, 2018](http://www.oracle.com/technetwork/java/javase/8u152-relnotes-3850503.html)
the release of CWS 1.0 has been postponed until January 31st, 2018.

- [0.6](https://javadog.io/downloads/cws-0.6.1.tgz) - Released November 17th, 2017; Code Completion Release
- 0.7 - Scheduled for December, 2017; Documentation Release
- 0.8 - Scheduled for December, 2017; REST Release
- 0.9 - Scheduled for January, 2018; Auditing & Review Release
- 1.0 - Scheduled for January 31st, 2018; Final 1.0 Release

# Software License
The CWS is released under Apache License 2 or APL2.

# Contact
Kim Jensen <kim at javadog.io>
