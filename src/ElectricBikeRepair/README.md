# ElectricBikeRepair

## Run in WSL Ubuntu

### Using Java directly

```bash
cd src/ElectricBikeRepair
mkdir -p target/classes
javac -d target/classes src/main/java/se/ebikerepair/startup/Main.java
java -cp target/classes se.ebikerepair.startup.Main
```

### Using Maven

```bash
cd src/ElectricBikeRepair
mvn compile
mvn exec:java -Dexec.mainClass="se.ebikerepair.startup.Main"
```

## Run Tests in WSL Ubuntu

```bash
cd src/ElectricBikeRepair
mvn test
```
or
```bash
cd src/ElectricBikeRepair
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