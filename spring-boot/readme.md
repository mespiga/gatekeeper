# Backend for Cilnet cloud services

## Setup
* Install java 1.8
* mvn clean install
* edit nano spring-boot/src/main/java/app/properties/ReportConstants.java and change STAGING to true if you are in dev or staging env
* java -jar  -Dspring.profiles.active=staging target/cloud-cilnet-0.0.1.jar