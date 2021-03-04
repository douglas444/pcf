# pattern categorization framework

This is a framework for pattern categorization.
It is composed of two modules: pcf-core and pcf-gui.

The pcf-core defines the interfaces that an external project needs to implement 
in order to be compatible with the framework.

There are three main interfaces: Interceptable, HighLevelCategorizer and LowLevelCategorizer.
A project is said to be compatible with one of those interfaces if it does implement it.

The pcf-gui module is an application with a graphical interface that allows the user to load 
implementations of the interfaces defined at the pcf-core module and execute the pattern categorization task. 
In order to use this application, it is necessary 
to load at least one implementation for each one of the three main interfaces.

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

## How do I build/install the JAR from the source code?

To build/install the JAR, execute the following command line from the root folder:

```mvn clean install assembly:single```

Once the process is finished, the pcf-gui JAR will be available at the ```pcf-gui/target``` folder as 
```pcf-gui-jar-with-dependencies.jar``` (you can rename it as you wish). 
The pcf-core will be installed at your local maven repository.

## How do I use pcf-core as a maven dependency in my own project?

Once you have installed pcf-core, import it at your maven project by including the following dependency 
to your pom.xml (edit the version if necessary):

```
<dependency>
    <groupId>br.ufu.facom</groupId>
    <artifactId>pcf-core</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

## How to start pcf-gui application?

Once you have the ```pcf-gui-jar-with-dependencies.jar```, 
you can execute it through the following command line: ```java -jar pcf-gui-jar-with-dependencies.jar```
