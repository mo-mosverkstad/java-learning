# Exception Changes (current implementation)

## 1. InvalidTelephoneNumberException (checked)

### Added: `se.ebikerepair.util.InvalidTelephoneNumberException`

```java
package se.ebikerepair.util;

public class InvalidTelephoneNumberException extends Exception {
    public InvalidTelephoneNumberException(String telephoneNumber){
        super(String.format("The phone number %s is not a valid phone number", telephoneNumber));
    }
}
```

### Throws in: `se.ebikerepair.util.TelephoneNumber` — constructor

```java
public TelephoneNumber(String telephoneNumber) throws InvalidTelephoneNumberException {
    if (telephoneNumber.trim().isEmpty()) {
        throw new InvalidTelephoneNumberException("<empty>");
    }

    String digits = telephoneNumber.replaceAll("[\\s\\-\\(\\)]", "");

    if (!digits.matches("\\+?[0-9]+")) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    // ... normalization logic ...

    if (normalized.length() < 7) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    // ... extraction logic ...

    if (remaining.isEmpty() || (remaining.length() < 8 || remaining.length() > 9)) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    // ...

    if (sn.isEmpty()) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }
}
```

### Propagates in: `se.ebikerepair.controller.Controller` — findRepairOrderIds

```java
private List<String> findRepairOrderIds(String telephoneNumber) throws InvalidTelephoneNumberException{
    String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
    List<RepairOrder> repairOrders = repairOrderRegistry.findRepairOrdersByTelephoneNumber(phoneNumberE164);
    return repairOrders.stream()
            .sorted(Comparator.comparing(RepairOrder::getCreatedDate).reversed())
            .map(RepairOrder::getId)
            .collect(Collectors.toList());
}
```

### Propagates in: `se.ebikerepair.controller.Controller` — findRepairOrder

```java
public RepairOrderDTO findRepairOrder(String telephoneNumber) throws InvalidTelephoneNumberException, IllegalArgumentException, IllegalStateException{
    List<String> repairOrderIds = findRepairOrderIds(telephoneNumber);
    if (repairOrderIds.isEmpty()) {
        throw new IllegalArgumentException("There is no repair order created for this customer.");
    }
    String id = repairOrderIds.get(0);
    RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(id);
    if (repairOrder == null) {
        throw new IllegalStateException("Repair order not found for id: " + id);
    }
    return repairOrder.toDTO();
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        return customerRegistry.find(phoneNumberE164);
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to search customer", exception);
    }
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionPreparationActions

```java
private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException {
    CustomerDTO foundCustomer = receptionistController.searchCustomer(telephoneNumber);
    printout("1. Reception - Found customer:", foundCustomer);

    BikeDTO foundBike = foundCustomer.getBikeBySerialNumber(bikeSerialNumber);
    String repairOrderId = receptionistController.createRepairOrder(telephoneNumber, new ProblemDTO("Broken bike chain", foundBike));
    printout("2. Reception - Created repair order with id: ", repairOrderId);
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedTechnicianDiagnosticActions

```java
private void proceedTechnicianDiagnosticActions(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
    RepairOrderDTO repairOrderDTO = technicianController.findRepairOrder(telephoneNumber);
    // ...
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionConfirmationActions

```java
private void proceedReceptionConfirmationActions(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
    RepairOrderDTO repairOrderDTO = receptionistController.findRepairOrder(telephoneNumber);
    // ...
}
```

### Caught in: `se.ebikerepair.view.View` — proceedActions

```java
public void proceedActions(String telephoneNumber, String bikeSerialNumber) {
    try{
        proceedReceptionPreparationActions(telephoneNumber, bikeSerialNumber);
        proceedTechnicianDiagnosticActions(telephoneNumber);
        proceedReceptionConfirmationActions(telephoneNumber);
    } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | FailedOperationException | IllegalArgumentException | IllegalStateException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }
}
```

---

## 2. NonExistentTelephoneNumberException (checked)

### Added: `se.ebikerepair.integration.NonExistentTelephoneNumberException`

```java
package se.ebikerepair.integration;

public class NonExistentTelephoneNumberException extends Exception {
    public NonExistentTelephoneNumberException(String telephoneNumber) {
        super(String.format("No customer found for telephone number: %s", telephoneNumber));
    }
}
```

### Throws in: `se.ebikerepair.integration.CustomerRegistry` — find

```java
public CustomerDTO find(String telephoneNumber) throws NonExistentTelephoneNumberException {
    initRegistry();
    CustomerDTO customer = customers.get(telephoneNumber);
    if (customer == null){
        throw new NonExistentTelephoneNumberException(telephoneNumber);
    }
    return customer;
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        return customerRegistry.find(phoneNumberE164);
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to search customer", exception);
    }
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        // ...
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionPreparationActions

```java
private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException {
    CustomerDTO foundCustomer = receptionistController.searchCustomer(telephoneNumber);
    // ...
}
```

### Caught in: `se.ebikerepair.view.View` — proceedActions

```java
public void proceedActions(String telephoneNumber, String bikeSerialNumber) {
    try{
        proceedReceptionPreparationActions(telephoneNumber, bikeSerialNumber);
        proceedTechnicianDiagnosticActions(telephoneNumber);
        proceedReceptionConfirmationActions(telephoneNumber);
    } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | FailedOperationException | IllegalArgumentException | IllegalStateException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }
}
```

---

## 3. NoDatabaseException (unchecked)

### Added: `se.ebikerepair.data.NoDatabaseException`

```java
package se.ebikerepair.data;

public class NoDatabaseException extends RuntimeException {
    public NoDatabaseException(String databaseName){
        super(String.format("No database %s found or connected", databaseName));
    }
}
```

### Throws in: `se.ebikerepair.data.DatabaseHandler` — initDataHandler, readList, writeList

```java
public void initDataHandler(String resourceName){
    databaseName = resourceName;
    throw new NoDatabaseException(databaseName);
}

public <T> List<T> readList(Class<T> clazz){
    throw new NoDatabaseException(databaseName);
}

public <T> void writeList(List<T> list){
    throw new NoDatabaseException(databaseName);
}
```

### Propagates (unchecked) through: `se.ebikerepair.integration.CustomerRegistry` — initRegistry

```java
private void initRegistry() {
    if (dataHandler.isInitialized()) return;
    dataHandler.initDataHandler("customers");
    for (CustomerDTO customerDTO : dataHandler.readList(CustomerDTO.class)){
        customers.put(customerDTO.telephoneNumber(), customerDTO);
    }
}
```

### Propagates (unchecked) through: `se.ebikerepair.integration.CustomerRegistry` — find

```java
public CustomerDTO find(String telephoneNumber) throws NonExistentTelephoneNumberException {
    initRegistry();
    CustomerDTO customer = customers.get(telephoneNumber);
    if (customer == null){
        throw new NonExistentTelephoneNumberException(telephoneNumber);
    }
    return customer;
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        return customerRegistry.find(phoneNumberE164);
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to search customer", exception);
    }
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

---

## 4. ResourceNotFoundException (unchecked)

### Added: `se.ebikerepair.data.ResourceNotFoundException`

```java
package se.ebikerepair.data;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found in resources", resourceName));
    }
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — initDataHandler

```java
public void initDataHandler(String resourceName) {
    URL resource = getClass().getClassLoader().getResource(String.format("%s.json", resourceName));
    if (resource == null) {
        throw new ResourceNotFoundException(resourceName);
    }
    File file;
    try {
        file = new File(resource.toURI());
    } catch (URISyntaxException e) {
        throw new InvalidResourceURIException(resourceName, e);
    }
    jsonFile = file;
}
```

### Propagates (unchecked) through: `se.ebikerepair.model.DiagnosticReport` — loadDiagnosticTasks

```java
private static List<DiagnosticTask> loadDiagnosticTasks() {
    JsonFileHandler handler = new JsonFileHandler();
    handler.initDataHandler("diagnosticTasks");
    return handler.readList(DiagnosticTask.class);
}
```

### Propagates (unchecked) through: `se.ebikerepair.integration.CustomerRegistry` — initRegistry

```java
private void initRegistry() {
    if (dataHandler.isInitialized()) return;
    dataHandler.initDataHandler("customers");
    for (CustomerDTO customerDTO : dataHandler.readList(CustomerDTO.class)){
        customers.put(customerDTO.telephoneNumber(), customerDTO);
    }
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        return customerRegistry.find(phoneNumberE164);
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to search customer", exception);
    }
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        // ...
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

---

## 5. InvalidResourceURIException (unchecked)

### Added: `se.ebikerepair.data.InvalidResourceURIException`

```java
package se.ebikerepair.data;

public class InvalidResourceURIException extends RuntimeException{
    public InvalidResourceURIException(String resourceName, Exception exception) {
        super(String.format("Invalid resource URI: %s", resourceName), exception);
    }

    public InvalidResourceURIException(String resourceName) {
        new InvalidResourceURIException(String.format("Invalid resource URI: %s", resourceName), null);
    }
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — initDataHandler

```java
public void initDataHandler(String resourceName) {
    URL resource = getClass().getClassLoader().getResource(String.format("%s.json", resourceName));
    if (resource == null) {
        throw new ResourceNotFoundException(resourceName);
    }
    File file;
    try {
        file = new File(resource.toURI());
    } catch (URISyntaxException e) {
        throw new InvalidResourceURIException(resourceName, e);
    }
    jsonFile = file;
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — readList

```java
public <T> List<T> readList(Class<T> clazz) {
    if (jsonFile == null) throw new InvalidResourceURIException(jsonFile.getName());
    // ...
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — writeList

```java
public <T> void writeList(List<T> list) {
    if (jsonFile == null) {
        throw new InvalidResourceURIException(jsonFile.getName());
    }
    // ...
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to search customer", exception);
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to create repair order", exception);
}
```

---

## 6. CannotReadFileException (unchecked)

### Added: `se.ebikerepair.data.CannotReadFileException`

```java
package se.ebikerepair.data;

public class CannotReadFileException extends RuntimeException {
    public CannotReadFileException(String name, Exception exception){
        super(String.format("Failed to read %s : %s", name, exception.getMessage()), exception);
    }
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — readList

```java
public <T> List<T> readList(Class<T> clazz) {
    if (jsonFile == null) throw new InvalidResourceURIException(jsonFile.getName());
    try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        List<T> list = GSON.fromJson(reader, listType);
        return list != null ? list : Collections.emptyList();
    } catch (IOException e) {
        throw new CannotReadFileException(jsonFile.getName(), e);
    }
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to search customer", exception);
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to create repair order", exception);
}
```

---

## 7. CannotWriteFileException (unchecked)

### Added: `se.ebikerepair.data.CannotWriteFileException`

```java
package se.ebikerepair.data;

public class CannotWriteFileException extends RuntimeException {
    public CannotWriteFileException(String name, Exception exception){
        super(String.format("Failed to write %s : %s", name, exception.getMessage()), exception);
    }
}
```

### Throws in: `se.ebikerepair.data.JsonFileHandler` — writeList

```java
public <T> void writeList(List<T> list) {
    if (jsonFile == null) {
        throw new InvalidResourceURIException(jsonFile.getName());
    }
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8)) {
        GSON.toJson(list, writer);
    } catch (IOException e) {
        throw new CannotWriteFileException(jsonFile.getName(), e);
    }
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to search customer", exception);
}
```

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
} catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
    throw new FailedOperationException("Fail to create repair order", exception);
}
```

---

## 8. CannotCreateDataHandlerException (unchecked)

### Added: `se.ebikerepair.integration.CannotCreateDataHandlerException`

```java
package se.ebikerepair.integration;

public class CannotCreateDataHandlerException extends RuntimeException{
    public CannotCreateDataHandlerException(String dataHandlerClassName, Exception exception){
        super(String.format("Cannot create the data handler %s", dataHandlerClassName), exception);
    }
}
```

### Throws in: `se.ebikerepair.integration.CustomerRegistry` — constructor

```java
public CustomerRegistry() {
    try{
        dataHandler = DataHandlerFactory.create(DATA_HANDLER_CLASS_NAME);
    }
    catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception){
        throw new CannotCreateDataHandlerException(DATA_HANDLER_CLASS_NAME, exception);
    }
}
```

### Propagates (unchecked) through: `se.ebikerepair.integration.RegistryCreator` — constructor

```java
public RegistryCreator(){
    customerRegistry = new CustomerRegistry();
    repairOrderRegistry = new RepairOrderRegistry();
}
```

### Not yet caught — propagates to `se.ebikerepair.startup.Main` — main

```java
public static void main(String[] args) {
    RegistryCreator registryCreator = new RegistryCreator();
    Printer printer = new Printer();
    ControllerCreator controllerCreator = new ControllerCreator(registryCreator, printer);
    View view = new View(controllerCreator);
    view.proceedActions("0707654321", "SK-2024-055");
}
```

---

## 9. FailedOperationException (checked — controller-level wrapper)

### Added: `se.ebikerepair.controller.FailedOperationException`

```java
package se.ebikerepair.controller;

public class FailedOperationException extends Exception{
    public FailedOperationException(String message, Exception exception){
        super(message, exception);
    }
}
```

### Throws in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        return customerRegistry.find(phoneNumberE164);
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to search customer", exception);
    }
}
```

### Throws in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try {
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    } catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException | NoDatabaseException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionPreparationActions

```java
private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException {
    CustomerDTO foundCustomer = receptionistController.searchCustomer(telephoneNumber);
    printout("1. Reception - Found customer:", foundCustomer);

    BikeDTO foundBike = foundCustomer.getBikeBySerialNumber(bikeSerialNumber);
    String repairOrderId = receptionistController.createRepairOrder(telephoneNumber, new ProblemDTO("Broken bike chain", foundBike));
    printout("2. Reception - Created repair order with id: ", repairOrderId);
}
```

### Caught in: `se.ebikerepair.view.View` — proceedActions

```java
public void proceedActions(String telephoneNumber, String bikeSerialNumber) {
    try{
        proceedReceptionPreparationActions(telephoneNumber, bikeSerialNumber);
        proceedTechnicianDiagnosticActions(telephoneNumber);
        proceedReceptionConfirmationActions(telephoneNumber);
    } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | FailedOperationException | IllegalArgumentException | IllegalStateException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }
}
```

---

# Summary Table

| # | Exception | Package | Checked? | Thrown by | Caught by |
|---|-----------|---------|----------|-----------|-----------|
| 1 | `InvalidTelephoneNumberException` | `util` | ✅ checked | `TelephoneNumber` constructor | `View.proceedActions` |
| 2 | `NonExistentTelephoneNumberException` | `integration` | ✅ checked | `CustomerRegistry.find` | `View.proceedActions` |
| 3 | `NoDatabaseException` | `data` | ❌ unchecked | `DatabaseHandler` (all methods) | `ReceptionistController.searchCustomer`, `ReceptionistController.createRepairOrder` |
| 4 | `ResourceNotFoundException` | `data` | ❌ unchecked | `JsonFileHandler.initDataHandler` | `ReceptionistController.searchCustomer`, `ReceptionistController.createRepairOrder` |
| 5 | `InvalidResourceURIException` | `data` | ❌ unchecked | `JsonFileHandler.initDataHandler`, `readList`, `writeList` | `ReceptionistController.searchCustomer`, `ReceptionistController.createRepairOrder` |
| 6 | `CannotReadFileException` | `data` | ❌ unchecked | `JsonFileHandler.readList` | `ReceptionistController.searchCustomer`, `ReceptionistController.createRepairOrder` |
| 7 | `CannotWriteFileException` | `data` | ❌ unchecked | `JsonFileHandler.writeList` | `ReceptionistController.searchCustomer`, `ReceptionistController.createRepairOrder` |
| 8 | `CannotCreateDataHandlerException` | `integration` | ❌ unchecked | `CustomerRegistry` constructor | **Not yet caught** — propagates to `Main` |
| 9 | `FailedOperationException` | `controller` | ✅ checked | `ReceptionistController.searchCustomer`, `createRepairOrder` | `View.proceedActions` |

---

# Requirements Checklist (Task 1)

## Task 1a — Alternative flow 5a (unknown phone number)

✅ **Done.** `NonExistentTelephoneNumberException` is thrown by `CustomerRegistry.find()`, propagates through `ReceptionistController.searchCustomer()`, and is caught in `View.proceedActions()` which shows an informative message.

## Task 1b — Simulate database failure

✅ **Done.** `DatabaseHandler` always throws `NoDatabaseException`. Switching `DATA_HANDLER_CLASS_NAME` to `"se.ebikerepair.data.DatabaseHandler"` in `CustomerRegistry` simulates the failure. It's caught in `ReceptionistController.searchCustomer()` and wrapped in `FailedOperationException`.

## Chapter 8 bullets

| Requirement | Status | Notes |
|---|---|---|
| Choose between checked and unchecked | ✅ | Checked: `NonExistentTelephoneNumberException`, `InvalidTelephoneNumberException`, `FailedOperationException`. Unchecked: `NoDatabaseException`, `ResourceNotFoundException`, etc. |
| Correct abstraction level | ✅ | Data-layer exceptions in `data`, integration in `integration`, controller wraps them in `FailedOperationException` |
| Name after error condition | ✅ | All exception names describe the error condition |
| Include info about error condition | ✅ | All exceptions include descriptive messages with context (phone number, resource name, etc.) |
| Use `java.lang.Exception` functionality | ✅ | All extend `Exception` or `RuntimeException`, use `super(message)` and `super(message, cause)` |
| Javadoc comments for all exceptions | ❌ **Missing** | None of the 9 exception classes have javadoc comments |
| Object shall not change state if exception thrown | ✅ | `CustomerRegistry.find()` throws before modifying state; `initRegistry()` guards with `isInitialized()` |
| Notify users | ✅ | `View.proceedActions()` catches and prints `ERROR_PREFIX + e.getMessage()` |
| Notify developers | ❌ **Missing** | No file logger. The task requires writing error reports to a log file when exceptions indicate the program is not functioning as intended (e.g. `FailedOperationException` / database failures) |
| Unit tests for exception handling | ⚠️ **Partial** | Tests exist for `NonExistentTelephoneNumberException` and `InvalidTelephoneNumberException`. No tests for `NoDatabaseException` / `FailedOperationException` / `CannotCreateDataHandlerException` scenarios |

## Program output

| Requirement | Status | Notes |
|---|---|---|
| UI shows informative message when exception caught in view | ✅ | `View.proceedActions()` catches and prints `"Error: " + e.getMessage()` |
| Error report written to log file for exceptions indicating program malfunction | ❌ **Missing** | No `FileLogger` implemented. `FailedOperationException` (wrapping `NoDatabaseException`) should trigger a file log entry |

---

# TODO: Remaining work

## 1. Add javadoc to all 9 exception classes

Every exception needs a class-level javadoc and constructor javadoc. Example:

```java
/**
 * Thrown when a telephone number does not exist in the customer registry.
 */
public class NonExistentTelephoneNumberException extends Exception {
    /**
     * Creates an exception indicating the given telephone number was not found.
     *
     * @param telephoneNumber the telephone number that was not found
     */
    public NonExistentTelephoneNumberException(String telephoneNumber) {
        super(String.format("No customer found for telephone number: %s", telephoneNumber));
    }
}
```

Exceptions that need javadoc:

- `se.ebikerepair.util.InvalidTelephoneNumberException`
- `se.ebikerepair.integration.NonExistentTelephoneNumberException`
- `se.ebikerepair.integration.CannotCreateDataHandlerException`
- `se.ebikerepair.data.NoDatabaseException`
- `se.ebikerepair.data.ResourceNotFoundException`
- `se.ebikerepair.data.InvalidResourceURIException`
- `se.ebikerepair.data.CannotReadFileException`
- `se.ebikerepair.data.CannotWriteFileException`
- `se.ebikerepair.controller.FailedOperationException`

## 2. Add unit tests for database failure exception handling

Tests needed:

- `FailedOperationException` is thrown by `searchCustomer` when the database is down (use `DatabaseHandler` or mock the failure)
- `CannotCreateDataHandlerException` is thrown when `DataHandlerFactory.create()` receives an invalid class name

## 3. Implement FileLogger (notify developers)

- Create a `FileLogger` class (e.g. in `util/` or a new `log/` package) that writes error messages + stack traces to a file
- In `View.proceedActions()`, when catching `FailedOperationException` (which wraps infrastructure exceptions like `NoDatabaseException`), write the error to the log file in addition to showing the user message
- `NonExistentTelephoneNumberException` and `InvalidTelephoneNumberException` are expected user errors — these do NOT need file logging
- `FailedOperationException` wraps infrastructure failures — these DO need file logging (program is not functioning as intended)
