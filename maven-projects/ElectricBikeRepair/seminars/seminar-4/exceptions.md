# Exception Changes

## 1. InvalidTelephoneNumberException (checked exception)

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
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
    RepairOrder repairOrder = new RepairOrder(foundCustomer);
    repairOrder.updateProblem(problemDTO);
    repairOrderRegistry.save(repairOrder);
    return repairOrder.getId();
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionPreparationActions

```java
private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
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
    } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | IllegalArgumentException | IllegalStateException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }
}
```

---

## 2. NonExistentTelephoneNumberException (checked exception)

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
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
    RepairOrder repairOrder = new RepairOrder(foundCustomer);
    repairOrder.updateProblem(problemDTO);
    repairOrderRegistry.save(repairOrder);
    return repairOrder.getId();
}
```

### Propagates in: `se.ebikerepair.view.View` — proceedReceptionPreparationActions

```java
private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
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
    } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | IllegalArgumentException | IllegalStateException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }
}
```


---

# Code Review: Exception Changes vs Chapter 8 Principles

## Principles Compliance

| Principle | Status | Notes |
|---|---|---|
| §8.4 Checked for business errors | ✅ | Both exceptions extend `Exception` (checked) — correct for recoverable situations |
| §8.5 Handle at right level | ✅ | Exceptions propagate to View where they're caught once |
| §8.6 Layered responsibilities | ✅ | Controller propagates, View catches and displays |
| §8.7 Specific exception classes | ✅ | Replaced generic `IllegalArgumentException` with domain-specific names |
| §8.7 Named after the problem | ✅ | `InvalidTelephoneNumber` and `NonExistentTelephoneNumber` clearly describe the problem |
| §8.8 No implementation leakage | ⚠️ | Null input leaks `NullPointerException` instead of the declared checked exception |
| §8.10 No swallowing | ✅ | Every catch produces a user message |
| §8.10 No overly generic catches | ⚠️ | View still catches `IllegalArgumentException` / `IllegalStateException` (acceptable for now) |

## What's Done Well

- **Specific exception classes (§8.7)** — Replaced generic `IllegalArgumentException` with `InvalidTelephoneNumberException` and `NonExistentTelephoneNumberException`. This follows the guideline: *"Use specific exception classes, not generic ones"* and *"Name exceptions after the problem, not the fix."*

- **Checked exceptions for business logic (§8.4)** — Both exceptions extend `Exception` (checked). The notes say: *"Business logic errors should usually be represented by checked exceptions, since the calling code often can recover meaningfully."* An invalid phone number or a non-existent customer are recoverable situations where the View can display a message and continue — checked is the correct choice here.

- **Handling at the right level (§8.5, §8.6)** — Exceptions propagate from integration/util → controller → View, where they are caught once in `proceedActions`. The notes say: *"Let exceptions propagate until a layer that understands the context and can translate the exception into meaningful feedback."* The View is that layer.

- **No swallowing (§8.10)** — No exception is caught and ignored anywhere. Every catch results in a user-facing message.

- **Controller propagates, doesn't catch (§8.6)** — Controller methods declare `throws` and pass exceptions through. The notes say the controller *"should mostly propagate exceptions"* — this is followed correctly.

- **Informative messages (§8.7)** — Both exceptions include the actual problematic value in the message, making debugging straightforward.

## Issues Found

### 1. [Medium] Null input causes NullPointerException instead of InvalidTelephoneNumberException

**File:** `se.ebikerepair.util.TelephoneNumber` — constructor (lines 18–21)

When `telephoneNumber` is null, calling `telephoneNumber.trim()` throws an unchecked `NullPointerException`. The comment says this is intentional, but per §8.7 the caller expects `InvalidTelephoneNumberException` for all invalid inputs. A `NullPointerException` leaks an implementation detail (§8.8) and forces callers to catch two different exception types for the same category of problem.

**Suggested fix:**

```java
if (telephoneNumber == null || telephoneNumber.trim().isEmpty()) {
    throw new InvalidTelephoneNumberException(telephoneNumber == null ? "<null>" : "<empty>");
}
```

### 2. [Low] View catches IllegalArgumentException and IllegalStateException — overly broad for unchecked exceptions

**File:** `se.ebikerepair.view.View` — `proceedActions` (line 46)

Per §8.10, catching overly generic exceptions is a common mistake. `IllegalArgumentException` and `IllegalStateException` are unchecked exceptions typically representing programming errors (§8.4). Catching them in the View masks potential bugs. Per the notes §1.2, these should ideally be replaced with domain-specific exceptions (e.g. `RepairOrderNotFoundException`) in future iterations. For now, this is acceptable but worth noting for Seminar 8 evolution.

### 3. [Low] proceedTechnicianDiagnosticActions declares throws NonExistentTelephoneNumberException but cannot throw it

**File:** `se.ebikerepair.view.View` — `proceedTechnicianDiagnosticActions` (lines 50–58) and `proceedReceptionConfirmationActions` (line 63)

These methods only call `technicianController.findRepairOrder()` / `receptionistController.findRepairOrder()` which are defined in `Controller` (not `ReceptionistController`) and do not throw `NonExistentTelephoneNumberException`. The `throws` clause is unnecessarily broad. Only `proceedReceptionPreparationActions` actually calls methods that can throw `NonExistentTelephoneNumberException`.

**Suggested fix:** Remove `NonExistentTelephoneNumberException` from the `throws` clause of `proceedTechnicianDiagnosticActions` and `proceedReceptionConfirmationActions`, keeping only `InvalidTelephoneNumberException`.

### 4. [Info] Exception placed in integration layer but represents a domain/business concept

**File:** `se.ebikerepair.integration.NonExistentTelephoneNumberException`

Per §8.6 and §1.2 of the notes, "customer not found" is a business rule violation, not a technical failure. The notes suggest domain exceptions should be thrown by model or controller, while integration exceptions represent technical failures (registry unavailable, data corruption). `NonExistentTelephoneNumberException` is currently in the `integration` package because `CustomerRegistry` throws it, which is pragmatic. However, conceptually it's a domain exception. This is fine for now but worth considering if you restructure for Seminar 8.

### 5. The take-away (in §8.6):

The **Controller** is the translation layer — it should translate technical/unchecked exceptions into domain-level checked exceptions before they reach the View

The **View** should only catch checked exceptions that it knows how to present

This prevents the **View** from catching IllegalArgumentException / IllegalStateException directly (which was flagged as issue #2 in the review)


---

# Unchecked Exceptions (work in progress)

## 3. ResourceNotFoundException (new)

### Added: `se.ebikerepair.util.ResourceNotFoundException`

```java
package se.ebikerepair.util;

class ResourceNotFoundException extends RuntimeException{
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

---

## 4. InvalidResourceURIException (new)

### Added: `se.ebikerepair.util.InvalidResourceURIException`

```java
package se.ebikerepair.util;

class InvalidResourceURIException extends RuntimeException{
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

---

## 5. CannotReadFileException (new)

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

---

## 6. CannotWriteFileException (new)

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

---

## Half-finished: Catching unchecked exceptions in Controller

### `se.ebikerepair.controller.ReceptionistController` — createRepairOrder (has TODO comment)

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    // try catch unchecked exceptions...
    CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
    RepairOrder repairOrder = new RepairOrder(foundCustomer);
    repairOrder.updateProblem(problemDTO);
    repairOrderRegistry.save(repairOrder);
    return repairOrder.getId();
}
```

### TODO: Methods that still need unchecked exception wrapping in Controller

The following public controller methods interact with registries (which now throw unchecked exceptions) and should wrap `RuntimeException` into a checked `OperationFailedException`:

`se.ebikerepair.controller.Controller` — findRepairOrder

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

`se.ebikerepair.controller.ReceptionistController` — searchCustomer

```java
public CustomerDTO searchCustomer(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
    CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
    return foundCustomer;
}
```

`se.ebikerepair.controller.ReceptionistController` — createRepairOrder

```java
public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException{
    // try catch unchecked exceptions...
    CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
    RepairOrder repairOrder = new RepairOrder(foundCustomer);
    repairOrder.updateProblem(problemDTO);
    repairOrderRegistry.save(repairOrder);
    return repairOrder.getId();
}
```

`se.ebikerepair.controller.ReceptionistController` — acceptRepairOrder

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

`se.ebikerepair.controller.ReceptionistController` — rejectRepairOrder

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
