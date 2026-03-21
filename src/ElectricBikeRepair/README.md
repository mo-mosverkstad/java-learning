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