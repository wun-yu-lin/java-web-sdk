# Java Web SDK

## Overview

The `java-web-sdk` is a Spring Boot autoconfiguration module designed to provide common utilities and integrations for Java web applications. It simplifies the setup of various functionalities, including centralized logging with ELK stack integration, email notifications via Gmail, and Discord notifications.

## Features

*   **Spring Boot Auto-configuration**: Easily integrate the SDK into your Spring Boot applications.
*   **Configurable Properties**: Customize SDK behavior through `application.yml` or `application.properties` with the `java-sdk` prefix.
*   **Logging**:
    *   Standard logging capabilities.
    *   Integration with ELK (Elasticsearch, Logstash, Kibana) for centralized log management.
*   **Notification Services**:
    *   **Gmail/Email Notification**: Send email notifications using configurable SMTP settings.
    *   **Discord Notification**: Send messages to Discord channels via webhooks.


## Installation

To use this SDK in your project, follow these steps:

### 1. Clone the Repository

```bash
git clone https://github.com/wun-yu-lin/java-web-sdk.git
cd to java-web-sdk/
```

### 2.1.1 Build the JAR Locally with Maven

Use Maven to build and install the SDK into your local Maven repository:

```bash
./mvn clean install
```

After the build, the JAR will be installed into your local Maven repository (`~/.m2/repository/org/mssearch/java-sdk/`).

### 2.1.1 Add Dependency to Your Project
Add the following dependency to your project's `pom.xml`. Make sure the version matches the one you just built:

```xml
<dependency>
    <groupId>org.mssearch</groupId>
    <artifactId>java-sdk</artifactId>
    <version>0.0.0-RELEASE</version>
</dependency>
```

##  2.2. Installation using jar file
You can also use the SDK by downloading the JAR file from the release page.
```
cd to your project path
mkdir lib
paste released java-sdk-*.jar file to lib/
```
Add the following dependency to your project's `pom.xml`. Make sure the version matches the one you just built:
```xml
<dependency>
    <groupId>org.mssearch</groupId>
    <artifactId>java-sdk</artifactId>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/java-sdk-0.0.0-RELEASE.jar</systemPath>
</dependency>
```



## Usage

Once added as a dependency, the SDK's auto-configuration will automatically apply. To enable the SDK's features, annotate your main application class with `@EnableWebSdk`.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sdk.mssearch.javasdk.EnableWebSdk;

@SpringBootApplication
@EnableWebSdk
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

You can configure its features in your `application.yml` (or `application.properties`) file under the `java-sdk` prefix.

### Example Configuration (`see application.yml.template`)

```yaml
java-sdk:
  server-name: my-application
  logger:
    enable-notify: true
    mail-notify: your-email@example.com
    notify-service-list: local, mail, discord
  logger-elk:
    enable-elk: true
    logstash-host: localhost
    logstash-port: 5000
  gmail:
    smtp-host: smtp.gmail.com
    smtp-port: 587
    protocol: smtp
    mail-username: your-gmail@gmail.com
    mail-password: your-app-password # Use an App Password for Gmail
    smtp-auth: true
    smtp-starttls-enable: true
    smtp-starttls-required: true
  discord:
    webhook-url: https://discord.com/api/webhooks/your-webhook-id/your-webhook-token
```

### Accessing Services

The SDK provides various services and utilities that can be injected into your Spring components. For example, you can use `SdkLoggerFactory` for logging or `DefaultNotifier` for sending notifications.

```java
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;
import sdk.mssearch.javasdk.notify.Notifier;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private static final Logger logger = SdkLoggerFactory.getLogger(MyService.class);
    

    public void ErrorNotify() {
        logger.error(new Exception(), "Example error");
    }
}
```

## Development

### Building the Project

To build the project using Maven:

```bash
./mvnw clean install
```

### Code Quality

The project uses SpotBugs and PMD for static code analysis. You can run these checks via Maven:

```bash
./mvnw spotbugs:check
./mvnw pmd:check
```
