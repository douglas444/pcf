# pattern categorization framework

This is a framework for pattern categorization. It is composed of two modules: 
*pcf-core* and *pcf-gui*. The *pcf-core* defines the Java's interfaces that an 
external project needs to implement in order to be compatible with this framework. 
There are three main Java's interfaces: *Interceptable*, *HighLevelCategorizer* and *LowLevelCategorizer*.

The *pcf-gui* module is an application with a graphical interface that allows the user to load 
implementations of the Java's interfaces defined by the *pcf-core* module, 
and execute the pattern categorization task with the loaded implementations. 
In order to execute the pattern categorization task, it is necessary to load at least one 
implementation for each one of the three main Java's interfaces of the *pcf-core*.

## Requirements

* Apache Maven 3.6.3 or higher
* Java 8

## Maven Dependencies

### pcf-core:
None

### pcf-gui:
* Apache Commons Lang 3.11 (available at Maven Central Repository)
* JAXB API 2.3.0 (available at Maven Central Repository)
* Jakarta XML Binding API 2.3.2 (available at Maven Central Repository)
* JAXB Runtime 2.3.2 (available at Maven Central Repository)
* pcf-core 1.0-SNAPSHOT (https://github.com/douglas444/pcf)

## How to build/install the project

To build the *pcf-gui*'s JAR and install the *pcf-core* at your *Maven Local Repository*, 
execute the following command line from the root folder:

```
mvn clean install assembly:single
```

Once the process is successfully finished, 
the *pcf-gui*'s JAR will be available at the ```pcf-gui/target``` folder as 
```pcf-gui-jar-with-dependencies.jar``` (you can rename it as you wish), and 
the *pcf-core* will be installed at your *Maven Local Repository*.

## How to use pcf-core as a maven dependency

Once you have installed *pcf-core*, import it at your 
*Maven* project by including the following dependency 
to your project's pom.xml file (edit the version if necessary):

```xml
<dependency>
    <groupId>br.ufu.facom</groupId>
    <artifactId>pcf-core</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

## How to start pcf-gui application

Once you have the ```pcf-gui-jar-with-dependencies.jar```, 
you can execute it through the following command line: ```java -jar pcf-gui-jar-with-dependencies.jar```
