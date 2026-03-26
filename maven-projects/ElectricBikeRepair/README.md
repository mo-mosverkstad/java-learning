# ElectricBikeRepair

## Run in WSL Ubuntu

### Using Java directly

```bash
cd maven-projects/ElectricBikeRepair
mkdir -p target/classes
javac -d target/classes src/main/java/se/ebikerepair/startup/Main.java
java -cp target/classes se.ebikerepair.startup.Main
```

### Using Maven

```bash
cd maven-projects/ElectricBikeRepair
mvn compile
mvn exec:java -Dexec.mainClass="se.ebikerepair.startup.Main"
```

## Run Tests in WSL Ubuntu

```bash
cd maven-projects/ElectricBikeRepair
mvn test
```
or
```bash
cd maven-projects/ElectricBikeRepair
mvn package
```

# How to run mvn in wsl ubutun

Here's how to set up and run Maven in WSL Ubuntu:

## 1. Install Java (if not already installed)
```bash
sudo apt update
sudo apt install default-jdk -y
```

## 2. Install Maven
```bash
sudo apt install maven -y
```

## 3. Verify installation
```bash
java -version
mvn -version
```

## 4. Navigate to your project and run
In WSL, your Windows drives are mounted under `/mnt/`. So:
```bash
cd "your/maven/folder" <In this folder there is pom.xml>
mvn compile
mvn test
```

**Tip:** The path has spaces, so wrap it in quotes.

## Quick reference of common Maven commands
- `mvn compile` — compile source code
- `mvn test` — run tests
- `mvn package` — build a JAR/WAR
- `mvn clean` — remove the `target/` directory
- `mvn clean install` — clean, compile, test, and install to local repo


# Java version in pom.xml

In current Java version defined in pom.xml is as below:

```xml
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```

So the default Java version is 11, which has been installed by the ubuntu command:

```bash
sudo apt install default-jdk -y
```

If you want to use Java 17 new feature, it should be do as below:

1, Install Java 17 in wsl ubuntu using:

```bash
sudo apt install openjdk-17-jdk
```

2, update pom.xml to version 17:

```xml
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```

If you want to use Java 21 new feature, it should be do as below:

1, Install Java 21 in wsl ubuntu using:

```bash
sudo apt update
sudo apt install openjdk-21-jdk -y
```

2, update pom.xml to version 21:

```xml
    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```

To switch between multiple installed Java versions:

```bash
sudo update-alternatives --config java
```

# Disabled java problem visibility
In VS Code, press Ctrl+, to open Settings, then search for "problems visibility" and uncheck the option to disable it.

