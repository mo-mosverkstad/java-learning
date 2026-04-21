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
    // when (telephoneNumber == null) throw NullPointerException (unchecked exception)
    if (telephoneNumber.trim().isEmpty()) {
        throw new InvalidTelephoneNumberException("<empty>");
    }

    String digits = telephoneNumber.replaceAll("[\\s\\-\\(\\)]", "");

    if (!digits.matches("\\+?[0-9]+")) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    String normalized;

    if (digits.startsWith("+")) {
        normalized = digits.substring(1);
    } else if (digits.startsWith("00")) {
        normalized = digits.substring(2);
    } else if (digits.startsWith("0")) {
        normalized = "46" + digits.substring(1);
    } else {
        normalized = "46" + digits;
    }

    if (normalized.length() < 7) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    cc = extractCountryCode(normalized);
    String remaining = normalized.substring(cc.length());

    if (remaining.isEmpty() || (remaining.length() < 8 || remaining.length() > 9)) {
        throw new InvalidTelephoneNumberException(telephoneNumber);
    }

    ac = extractAreaCode(remaining);
    sn = remaining.substring(ac.length());

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
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
    CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
    return foundCustomer;
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
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
    printout("3. Technician - Requested repair order: ", repairOrderDTO);

    String repairOrderId = repairOrderDTO.id();
    String diagnosticTaskName = "Mechanical Safety Check";
    ResultDTO result = new ResultDTO(true, true, "Chain and gears should be replaced.");
    technicianController.addDiagnosticResult(repairOrderId, diagnosticTaskName, result);
    printout("4. Technician - Updated diagnostic task: ", diagnosticTaskName);

    RepairTaskDTO repairTask1 = new RepairTaskDTO("Replace Chain", "Removal of worn or stretched chain and installation of a new compatible e‑bike chain. Includes lubrication, tension adjustment, and drivetrain alignment check.", new Cost(500, "SEK"), 1);
    RepairTaskDTO repairTask2 = new RepairTaskDTO("Replace gears", "Replacement of worn or damaged rear cassette/freewheel or front chainrings. Includes removal of old components, installation of new gear set, indexing and tuning of derailleur(s).", new Cost(400, "SEK"), 2);
    technicianController.addRepairTask(repairOrderId, repairTask1);
    technicianController.addRepairTask(repairOrderId, repairTask2);
    printout("5. Technician - Created proposed repair tasks 01: ", repairTask1);
    printout("6. Technician - Created proposed repair tasks 02: ", repairTask2);
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionConfirmationActions

```java
private void proceedReceptionConfirmationActions(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
    RepairOrderDTO repairOrderDTO = receptionistController.findRepairOrder(telephoneNumber);
    printout("7. Reception - found repair order: ", repairOrderDTO);

    printout("8. Reception - Accepted order");
    String repairOrderId = repairOrderDTO.id();
    receptionistController.acceptRepairOrder(repairOrderId);
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
    CustomerDTO customer = customers.get(telephoneNumber);
    if (customer == null){
        throw new NonExistentTelephoneNumberException(telephoneNumber);
    }
    return customer;
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
    CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
    return foundCustomer;
}
```

### Propagates in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
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

## 3. ResourceNotFoundException (unchecked)

### Added: `se.ebikerepair.util.ResourceNotFoundException`

```java
package se.ebikerepair.util;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found in resources", resourceName));
    }
}
```

### Throws in: `se.ebikerepair.util.JsonFileHandler` — constructor

```java
public JsonFileHandler(String resourceName) {
    URL resource = getClass().getClassLoader().getResource(resourceName);
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

### Propagates (unchecked) through: `se.ebikerepair.integration.CustomerRegistry` — constructor

```java
public CustomerRegistry() {
    jsonFileHandler = new JsonFileHandler("customers.json");
    for (CustomerDTO customerDTO : jsonFileHandler.readList(CustomerDTO.class)){
        customers.put(customerDTO.telephoneNumber(), customerDTO);
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
    // Normal main use case scenario
    view.proceedActions("0707654321", "SK-2024-055");

    // Negative use case, this telephone number could not be used to find the customer
    // view.proceedActions("0707654322", "SK-2024-055");

    // Negative use case, this telephone number format is invalid
    // view.proceedActions("11707654322", "SK-2024-055");
}
```

---

## 4. InvalidResourceURIException (unchecked)

### Added: `se.ebikerepair.util.InvalidResourceURIException`

```java
package se.ebikerepair.util;

public class InvalidResourceURIException extends RuntimeException{
    public InvalidResourceURIException(String resourceName, Exception exception) {
        super(String.format("Invalid resource URI: %s", resourceName), exception);
    }

    public InvalidResourceURIException(String resourceName) {
        new InvalidResourceURIException(String.format("Invalid resource URI: %s", resourceName), null);
    }
}
```

### Throws in: `se.ebikerepair.util.JsonFileHandler` — constructor

```java
public JsonFileHandler(String resourceName) {
    URL resource = getClass().getClassLoader().getResource(resourceName);
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

### Throws in: `se.ebikerepair.util.JsonFileHandler` — readList

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

### Throws in: `se.ebikerepair.util.JsonFileHandler` — writeList

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

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

---

## 5. CannotReadFileException (unchecked)

### Added: `se.ebikerepair.util.CannotReadFileException`

```java
package se.ebikerepair.util;

public class CannotReadFileException extends RuntimeException {
    public CannotReadFileException(String name, Exception exception){
        super(String.format("Failed to read %s : %s", name, exception.getMessage()), exception);
    }
}
```

### Throws in: `se.ebikerepair.util.JsonFileHandler` — readList

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

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

---

## 6. CannotWriteFileException (unchecked)

### Added: `se.ebikerepair.util.CannotWriteFileException`

```java
package se.ebikerepair.util;

public class CannotWriteFileException extends RuntimeException {
    public CannotWriteFileException(String name, Exception exception){
        super(String.format("Failed to write %s : %s", name, exception.getMessage()), exception);
    }
}
```

### Throws in: `se.ebikerepair.util.JsonFileHandler` — writeList

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

### Caught in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
        throw new FailedOperationException("Fail to create repair order", exception);
    }
}
```

---

## 7. FailedOperationException (checked — controller-level wrapper)

### Added: `se.ebikerepair.controller.FailedOperationException`

```java
package se.ebikerepair.controller;

public class FailedOperationException extends Exception{
    public FailedOperationException(String message, Exception exception){
        super(message, exception);
    }
}
```

### Throws in: `se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException{
    try{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer);
        repairOrder.updateProblem(problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }
    catch (ResourceNotFoundException | InvalidResourceURIException | CannotReadFileException | CannotWriteFileException exception){
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

# TODO: Methods that still need unchecked exception wrapping

The following public controller methods also interact with registries but do not yet catch unchecked exceptions and wrap them in `FailedOperationException`:

### `se.ebikerepair.controller.Controller` — findRepairOrder

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

### `se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
    CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
    return foundCustomer;
}
```

### `se.ebikerepair.controller.ReceptionistController` — acceptRepairOrder

```java
public void acceptRepairOrder(String repairOrderId) throws IllegalStateException{
    RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
    if (repairOrder == null) {
        throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
    }
    repairOrder.accept();
    repairOrderRegistry.save(repairOrder);
    printer.print(repairOrder);
}
```

### `se.ebikerepair.controller.ReceptionistController` — rejectRepairOrder

```java
public void rejectRepairOrder(String repairOrderId) throws IllegalStateException{
    RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
    if (repairOrder == null) {
        throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
    }
    repairOrder.reject();
    repairOrderRegistry.save(repairOrder);
}
```

### `se.ebikerepair.startup.Main` — main (startup exceptions not yet caught)

```java
public static void main(String[] args) {
    RegistryCreator registryCreator = new RegistryCreator();
    Printer printer = new Printer();
    ControllerCreator controllerCreator = new ControllerCreator(registryCreator, printer);
    View view = new View(controllerCreator);
    // Normal main use case scenario
    view.proceedActions("0707654321", "SK-2024-055");

    // Negative use case, this telephone number could not be used to find the customer
    // view.proceedActions("0707654322", "SK-2024-055");

    // Negative use case, this telephone number format is invalid
    // view.proceedActions("11707654322", "SK-2024-055");
}
```
