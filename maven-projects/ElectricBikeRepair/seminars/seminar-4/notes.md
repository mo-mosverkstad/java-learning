Below is a **full, original Markdown‑formatted summary of Chapter 8 – Error Handling** from  
*Leif Lindbäck, A First Course in Object‑Oriented Development*.  
Everything is **rewritten in my own words**, structured for **study notes and seminar reference**, and fully compliant with copyright rules.

***

# Chapter 8: Handling Failure — Summary

Error handling is a crucial aspect of professional software development. Programs that work correctly in normal situations must also behave predictably when something goes wrong. Chapter 8 introduces principles, patterns, and best practices for handling failures in object‑oriented systems, with particular focus on Java and layered architectures.

***

## 8.1 Purpose of Error Handling

The purpose of error handling is **not** only to prevent program crashes, but to:

*   Detect invalid or unexpected situations
*   Preserve system consistency
*   Provide clear feedback to callers or users
*   Enable recovery where possible
*   Avoid spreading errors to unrelated parts of the system

Errors are inevitable in real systems because programs operate in uncertain environments and receive input that cannot always be trusted.

***

## 8.2 Failures vs. Errors

The book makes an important distinction:

*   **Failure**  
    An undesirable situation in the system (e.g., invalid input, missing data, unavailable resource).

*   **Error**  
    A specific manifestation of a failure during execution.

Not all failures are fatal. Many can be detected early and handled gracefully.

***

## 8.3 Exceptions as a Mechanism

Exceptions are the primary mechanism used for error handling in Java. They provide a structured way to:

*   Interrupt normal control flow
*   Propagate information about a failure
*   Separate normal logic from error handling logic

Key properties:

*   Throwing an exception stops execution of the current method
*   Control is transferred to the nearest matching `catch` block
*   If no handler exists, the exception propagates further up the call stack

***

## 8.4 Checked vs. Unchecked Exceptions

Java distinguishes between two kinds of exceptions:

### Checked Exceptions

*   Must be declared (throws) or caught (try-catch)
*   Represent **expected recoverable problems**
*   Example: An example is a withdraw method in a bank account. If a user tries to withdraw more money than the current balance of the account, the method might throw an exception to indicate that the withdrawal is not allowed.
*   Checked exceptions are used for business logic errors.

### Unchecked Exceptions (Runtime Exceptions)

*   Do not need to be declared (throws)
*   Represent **programming errors or violated preconditions**
*   Example: illegal arguments, invalid state, unreached server.
*   Unchecked exceptions are used for programming errors.

### Recommendation

Business logic errors should usually be represented by **checked exceptions**, since the calling code often can recover meaningfully.

***

## 8.5 Where to Handle Exceptions

A key guideline is **handling exceptions at the right abstraction level**.

### Poor practice

*   Catching an exception too early
*   Logging or swallowing an exception without knowing how to recover

### Recommended practice

*   Let exceptions propagate until a layer that:
    *   Understands the context
    *   Can decide whether recovery is possible
    *   Can translate the exception into meaningful feedback

***

## 8.6 Exception Handling in Layered Architectures

In layered systems (View, Controller, Model, Integration):

### View Layer

*   Handles user-facing error presentation
*   Converts exceptions into human-readable messages

### Controller Layer

*   Should mostly propagate exceptions
*   May translate technical exceptions into domain-level exceptions

### Model Layer

*   Detects business rule violations
*   Throws exceptions when invariants are broken

### Integration Layer

*   Handles technical failures (databases, external systems)
*   Translates low-level exceptions into application-level exceptions

***

## 8.7 Designing Exception Types

Good exception design strengthens program clarity.

Guidelines:

*   Use **specific exception classes**, not generic ones
*   Name exceptions after the *problem*, not the fix
*   Group related exceptions using inheritance
*   Keep exception messages informative but concise

Avoid:

*   Throwing `Exception` or `RuntimeException` directly
*   Overloading one exception type for multiple problems

***

## 8.8 Exception Propagation and Encapsulation

Exceptions are part of a class’s **public interface**.

This means:

*   Changing thrown exceptions is a breaking change
*   Exception types and meanings must be stable
*   Only relevant details should be exposed

Implementation details should **not leak across layers** through exceptions.

***

## 8.9 Logging and Error Reporting

Logging is important, but **logging in business logic is usually a mistake**.

Recommended:

*   Log technical failures near the integration boundary
*   Avoid logging and rethrowing the same exception multiple times
*   Do not log at every catch — log only when ownership of the failure is clear

***

## 8.10 Common Error‑Handling Mistakes

Chapter 8 highlights several frequent mistakes:

*   Catching exceptions and doing nothing
*   Using exceptions for normal control flow
*   Catching overly generic exceptions
*   Mixing error handling with normal logic
*   Hiding errors behind default return values
*   Handling exceptions in layers that cannot recover

***

## 8.11 Summary of Best Practices

Key takeaways from the chapter:

*   Errors are normal; error handling must be designed
*   Exceptions separate normal logic from failure logic
*   Use unchecked exceptions for business rule violations
*   Handle exceptions at the correct architectural level
*   Design meaningful exception hierarchies
*   Avoid swallowing or over‑logging exceptions
*   Treat exceptions as part of the public interface

***

## 8.12 Relation to Later Seminars

In the course progression:

*   Early seminars intentionally **limit exception usage**
*   Later seminars introduce:
    *   Custom exception hierarchies
    *   Error translation across layers
    *   Robust user‑facing error handling

Understanding Chapter 8 is essential before implementing full exception handling in later project iterations.

***

✅ **This Markdown summary is suitable for study notes, seminar preparation, and later implementation planning.**

If you want, I can next:

*   Map Chapter 8 directly onto your **ElectricBikeRepair** system
*   Produce a **Seminar 8 implementation checklist**
*   Create a **“what errors belong in which layer” table**

Just let me know.




Below is a **practical, project‑specific application of Chapter 8 (Error Handling)** to your **ElectricBikeRepair** system, followed by a **Seminar 8 implementation checklist**, and a **clear “which errors belong in which layer” table**.  
Everything is written to be **actionable for your codebase and architecture** (MVC + layers) and consistent with how IV1350 expects Seminar 8 to evolve Seminar 3.

***

# 1) Mapping Chapter 8 Directly onto **ElectricBikeRepair**

This section explains **what changes conceptually and structurally** when you move from Seminar 3 (minimal exceptions) to **Seminar 8 (full error handling)**, using your existing classes and flows.

## 1.1 What changes compared to Seminar 3

In Seminar 3:

*   You already **detect failures** (e.g., missing customer, missing repair order).
*   You throw `IllegalArgumentException` / `IllegalStateException`.
*   Error handling is intentionally *simple*.

In Seminar 8:

*   Errors become **first‑class design elements**.
*   Exceptions are **domain‑specific**, not generic.
*   Each **layer has a clear responsibility** for errors.
*   The View never sees technical details.

You do **not** redesign the workflow — you **refine how failures are represented and propagated**.

***

## 1.2 Typical exception classes for ElectricBikeRepair

You already signal most failures; Seminar 8 is about **naming and placing them correctly**.

### Domain‑level (Model) exceptions

These represent **business rule violations**:

*   `CustomerNotFoundException`
*   `RepairOrderNotFoundException`
*   `InvalidRepairOrderStateException`
*   `DiagnosticTaskNotFoundException`
*   `RepairOrderAlreadyFinalizedException`

These:

*   Extend `RuntimeException`
*   Are thrown by **model or controller**
*   Do **not** mention UI or infrastructure

***

### Integration‑level exceptions

These represent **technical failures** (even if currently in‑memory):

*   `RegistryAccessException`
*   `DataInconsistencyException`

These:

*   Wrap low‑level problems
*   Are thrown in `CustomerRegistry`, `RepairOrderRegistry`
*   Are **translated** before reaching the View

***

### View‑level behavior

The View:

*   **Never throws**
*   **Never logs**
*   **Never inspects exception types**
*   Converts exceptions to **user‑friendly messages**

Example (conceptual, not code):

*   *“No repair order exists for this customer.”*
*   *“The requested operation cannot be performed in the current order state.”*

***

## 1.3 How your existing system operations fit Chapter 8

| System operation    | Typical failure     | Correct layer            |
| ------------------- | ------------------- | ------------------------ |
| searchCustomer      | Telephone not found | Model / Controller       |
| createRepairOrder   | Customer missing    | Controller               |
| findRepairOrder     | No orders exist     | Model                    |
| addDiagnosticResult | Task name invalid   | Model                    |
| addRepairTask       | Order not found     | Model                    |
| acceptRepairOrder   | Wrong state         | Model                    |
| printer.print       | Printer unavailable | Integration → Controller |

***

## 1.4 Exception propagation flow (ideal)

1.  **Model**
    *   Detects invariant violation
    *   Throws domain exception

2.  **Controller**
    *   Possibly translates technical → domain exception
    *   Does **not** catch unless meaningfully recoverable

3.  **View**
    *   Catches
    *   Displays message
    *   Execution continues or stops gracefully

This matches Chapter 8’s *“handle errors at the right abstraction level”* rule.

***

# 2) ✅ Seminar 8 Implementation Checklist (ElectricBikeRepair)

Use this as a **step‑by‑step guide** when implementing Seminar 8.

## Architecture & Design

*   [ ] Custom exception classes created (not generic Java exceptions)
*   [ ] Exception hierarchy grouped by responsibility
*   [ ] No exception types expose implementation details
*   [ ] Exceptions are documented where they are part of the public interface

## Model Layer

*   [ ] All business rule failures throw **unchecked domain exceptions**
*   [ ] No logging in model classes
*   [ ] No `System.out` or user messages
*   [ ] Invariants enforced at state transitions

## Integration Layer

*   [ ] Technical problems wrapped in integration exceptions
*   [ ] No business logic inside integration classes
*   [ ] External failures do **not** leak as low‑level exceptions

## Controller Layer

*   [ ] No swallowing of exceptions
*   [ ] Optional translation of technical → domain exceptions
*   [ ] Controller logic remains focused on coordination

## View Layer

*   [ ] Catches exceptions and presents user‑friendly messages
*   [ ] No stack traces shown to user
*   [ ] No business decisions taken in the View

## Testing

*   [ ] Unit tests assert **exceptions are thrown**, not just returned values
*   [ ] Tests verify correct exception type
*   [ ] Failure paths tested as first‑class behavior

***

# 3) “What Errors Belong in Which Layer” — Reference Table

| Layer           | Handles                        | Examples                                | Must NOT Do            |
| --------------- | ------------------------------ | --------------------------------------- | ---------------------- |
| **View**        | User‑facing error presentation | Display message when order is missing   | Throw exceptions       |
| **Controller**  | Coordination & translation     | Convert technical → domain error        | Decide UI text         |
| **Model**       | Business rule violations       | Invalid state transition                | Catch and hide errors  |
| **Integration** | Technical failures             | Registry inconsistency, printer failure | Enforce business rules |

***

# 4) How this fits your current codebase

✅ You already:

*   Detect failures correctly
*   Separate concerns well
*   Preserve encapsulation

🔜 Seminar 8 adds:

*   **Meaningful naming**
*   **Clear ownership of errors**
*   **Predictable propagation**
*   **Testable failure behavior**

You are **very well positioned** for Seminar 8 — your Seminar 3 design is already compatible with Chapter 8 principles.

***

## Want next?

I can:

*   Design a **concrete exception hierarchy** for your packages
*   Show **before/after examples** (Seminar 3 → Seminar 8)
*   Propose **Seminar 8‑ready test cases**
*   Prepare a **Discussion‑chapter paragraph** for Seminar 8

Just tell me which one you want.




Excellent — below is a **complete, Seminar 8–ready package** tailored specifically to your **ElectricBikeRepair** system and IV1350 expectations.  
Everything is concrete, code‑oriented, and aligned with **Chapter 8 (Error Handling)** and your existing Seminar 3 design.

***

# 1) Concrete Exception Hierarchy for *ElectricBikeRepair*

The goal of Seminar 8 is **clarity, ownership, and layering**, not more code.  
This hierarchy keeps responsibilities explicit and prevents leakage across layers.

## 1.1 Package structure

You do not need fully follow the package structure mentioned in this chapter, all of information mentioned in this chapter is one of options, not mandatory.


    se.ebikerepair.exception
    ├── ApplicationException
    │
    ├── domain
    │   ├── CustomerNotFoundException
    │   ├── RepairOrderNotFoundException
    │   ├── InvalidRepairOrderStateException
    │   ├── DiagnosticTaskNotFoundException
    │   └── RepairOrderAlreadyFinalizedException
    │
    ├── integration
    │   ├── RegistryAccessException
    │   └── DataInconsistencyException
    │
    └── technical
        └── PrinterUnavailableException

***

## 1.2 Base exception

### `ApplicationException`

*   Abstract root for all application‑specific exceptions
*   Extends `RuntimeException`
*   Establishes **semantic boundary** (everything is *our* failure)

Purpose:

*   Enables future grouping
*   Avoids leaking generic Java exceptions
*   Simplifies testing and documentation

***

## 1.3 Domain exceptions (model‑level truth)

Thrown when **business rules or invariants are violated**.

| Exception                            | Thrown when                        |
| ------------------------------------ | ---------------------------------- |
| CustomerNotFoundException            | No customer exists for given phone |
| RepairOrderNotFoundException         | Repair order lookup fails          |
| InvalidRepairOrderStateException     | Accept/reject in invalid state     |
| DiagnosticTaskNotFoundException      | Task name does not match           |
| RepairOrderAlreadyFinalizedException | Order already accepted/rejected    |

Rules:

*   Thrown from **model or controller**
*   Never mention UI or technical details
*   Never logged at this level

***

## 1.4 Integration exceptions (technical failures)

Used only for **external / technical problems**.

| Exception                   | Used in                          |
| --------------------------- | -------------------------------- |
| RegistryAccessException     | Registry unavailable / corrupted |
| DataInconsistencyException  | ID exists but object missing     |
| PrinterUnavailableException | Printer fails to print           |

Rules:

*   Thrown only in **integration layer**
*   Usually **translated** before reaching View
*   May be logged once at boundary

***

# 2) Before / After Examples

**Seminar 3 → Seminar 8**

These show *exactly* how your existing logic evolves.

***

## Example A — Finding a repair order

### Seminar 3 (generic, acceptable)

```java
if (repairOrder == null) {
    throw new IllegalStateException("Repair order not found");
}
```

### Seminar 8 (domain‑specific, correct)

```java
if (repairOrder == null) {
    throw new RepairOrderNotFoundException(repairOrderId);
}
```

✅ Improvements:

*   Communicates *business meaning*
*   Caller can react semantically
*   Stable public interface

***

## Example B — Wrong repair order state

### Seminar 3

```java
if (!order.isNewlyCreated()) {
    throw new IllegalStateException("Invalid state");
}
```

### Seminar 8

```java
if (!order.isAcceptable()) {
    throw new InvalidRepairOrderStateException(order.getState());
}
```

✅ Improvements:

*   Explicit invariant
*   Self‑documenting
*   Testable behavior

***

## Example C — View layer handling

### Seminar 3

```java
catch (Exception e) {
    System.out.println(e.getMessage());
}
```

### Seminar 8

```java
catch (ApplicationException e) {
    viewPrinter.showError(e.getMessage());
}
```

✅ Improvements:

*   No stack traces
*   View owns presentation
*   Predictable recovery

***

# 3) Seminar 8‑Ready Test Cases

In Seminar 8, **failures are first‑class behavior**.

## 3.1 Model tests (business rules)

*   `addDiagnosticResult_invalidTask_throwsDiagnosticTaskNotFoundException`
*   `acceptRepairOrder_alreadyAccepted_throwsInvalidRepairOrderStateException`
*   `findRepairOrder_noOrders_throwsRepairOrderNotFoundException`

Assertions:

*   Exception **type**
*   Optional domain message
*   No side effects after failure

***

## 3.2 Controller tests (translation & propagation)

*   `findRepairOrder_registryCorrupted_throwsApplicationException`
*   `searchCustomer_notFound_propagatesCustomerNotFoundException`

Rules:

*   Controller does **not swallow**
*   May translate integration → domain

***

## 3.3 Integration tests (technical failure)

*   `registry_findById_missingEntity_throwsDataInconsistencyException`
*   `printer_print_unavailable_throwsPrinterUnavailableException`

***

## 3.4 View tests (optional)

You normally **do not unit‑test the View**, but if needed:

*   Verify correct error message shown
*   No exception escapes the View

***

# 4) Discussion‑Chapter Paragraph for Seminar 8

You can **paste this directly** into your report.

```latex
\subsection{Error Handling and Failure Management}

In Seminar~8, error handling was extended to follow the principles described in
Chapter~8 of \textit{A First Course in Object-Oriented Development}. Generic
exceptions used in earlier seminars were replaced with a structured hierarchy of
application-specific exceptions that clearly distinguish between business rule
violations and technical failures. Domain-level exceptions are thrown when model
invariants are violated, while integration-level exceptions represent failures in
external or technical components.

Exceptions are propagated to the layer that has sufficient context to handle them.
Business logic does not log or present errors, while the View layer is responsible
for converting exceptions into user-friendly messages. This design preserves clear
responsibilities across layers, avoids leaking implementation details, and makes
failure behavior explicit and testable. As a result, error handling becomes an
integral part of the system’s design rather than an afterthought.
```

✅ This directly references:

*   Chapter 8 principles
*   Layered responsibility
*   Testability
*   Evolution relative to Seminar 3

***

# ✅ Final Position

With this structure:

*   You are **fully aligned with Chapter 8**
*   You can implement Seminar 8 cleanly and incrementally
*   Your design **already supports** this evolution

If you want next, I can:

*   Draft **JUnit tests for three representative exceptions**
*   Write **Seminar 8 grading‑aligned discussion expansions**
*   Propose **exception naming rules** for future seminars
*   Map exceptions directly to each communication diagram

Just say which one.



# Analysis for rentcarWithExceptions
Here's the full exception handling analysis across the `rentcarWithExceptions` package:

---

## Exception Hierarchy

The codebase defines 3 custom exceptions:

| Exception | Extends | Checked? | Package |
|---|---|---|---|
| `AlreadyBookedException` | `Exception` | Yes | model |
| `CarRegistryException` | `RuntimeException` | No | integration |
| `OperationFailedException` | `Exception` | Yes | controller |

---

## Exception Flow (Layer by Layer)

### 1. Integration Layer — [CarRegistry.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/integration/CarRegistry.java)

- `doGetCarByRegNo` throws `CarRegistryException` (unchecked) when no car matches the given reg number.
- `getCarByRegNo` and `setBookedStateOfCar` both call `doGetCarByRegNo` — neither catches nor declares the exception, which is fine since it's a `RuntimeException`.
- The Javadoc on `getCarByRegNo` and `setBookedStateOfCar` correctly documents `@throws CarRegistryException`.

### 2. Model Layer — [Rental.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/model/Rental.java)

- `rentCar` → calls `bookCar` → calls `carRegistry.getCarByRegNo()` and `carRegistry.setBookedStateOfCar()`.
- If the car is already booked, `bookCar` throws `AlreadyBookedException` (checked). This is properly declared on both `bookCar` and `rentCar`.
- If the car doesn't exist, `CarRegistryException` (unchecked) propagates up unhandled — this is intentional, as the model doesn't know how to recover from a database failure.

### 3. Controller Layer — [Controller.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/controller/Controller.java)

- `bookCar` catches `CarRegistryException`, logs it via `LogHandler`, and wraps it in `OperationFailedException` (checked) — good exception translation across layers.
- `AlreadyBookedException` is **not caught** — it's declared in the `throws` clause and passed through to the view. This is correct: the controller can't decide what to show the user.
- `registerCustomer` and `pay` throw `IllegalStateException` (unchecked) for programming errors (calling methods out of order).
- Constructor declares `throws IOException` from `LogHandler` creation.

### 4. View Layer — [View.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/view/View.java)

- `sampleExecution` has a well-structured try-catch hierarchy:
  - Inner try-catch blocks handle specific expected error scenarios (rebooking a booked car, booking a non-existing car).
  - Outer try-catch catches `AlreadyBookedException` and a generic `Exception` as a safety net.
- Uses `writeToLogAndUI` to both display user-friendly messages and log technical details.

### 5. Startup — [Main.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/startup/Main.java)

- Catches `IOException` from `Controller`/`View` constructors, prints a user message and stack trace.

### 6. Utility — [LogHandler.java](ood/code-master/src/main/java/se/leiflindback/oodbook/rentcarWithExceptions/util/LogHandler.java)

- Constructor throws `IOException` if the log file can't be opened — properly propagated.
- `logException` writes timestamp + message + full stack trace to the log file.

---

## Findings

---


## Summary — What's Done Well

- **Proper exception abstraction levels**: The integration layer throws `CarRegistryException`, the model throws `AlreadyBookedException`, and the controller wraps low-level exceptions into `OperationFailedException`. Each layer speaks its own language.
- **Exception chaining**: `OperationFailedException` preserves the original cause via `super(msg, cause)`.
- **Separation of user messages vs. technical logs**: The view uses `ErrorMessageHandler` for user-facing messages and `LogHandler` for developer-facing logs — clean separation.
- **Checked vs. unchecked choice is deliberate**: `AlreadyBookedException` is checked (recoverable business error), `CarRegistryException` is unchecked (infrastructure failure), `OperationFailedException` is checked (forces the view to handle it).
- **IllegalStateException for programming errors**: Controller methods guard against out-of-order calls with unchecked exceptions — correct usage.

I've also identified 6 findings (3 Medium, 2 Low, 1 Info) which you can inspect in the Code Issues panel for details and suggested fixes.


# in Java, if I new an exception but not throws or try-catch it, what to happen?

If you instantiate a custom exception using the new keyword but do not throw it, it behaves exactly like any other ordinary Java object. [1] 
## Key Behaviors

* No Disruption to Flow: The program will not crash, and execution will continue to the next line of code. Creating an exception is just memory allocation; it is the throw keyword that actually triggers the "emergency" control flow.
* Memory and Stack Trace: Java captures the stack trace at the moment of instantiation, not when it is thrown. This makes exception objects relatively "heavy" or expensive to create in terms of performance compared to basic objects.
* Garbage Collection: If you do not assign it to a variable or pass it to another method, it becomes eligible for garbage collection just like a discarded String or ArrayList. [1, 2, 3, 4, 5] 

## Common Use Cases for "No Throw" Exceptions [6] 
While rare, there are legitimate patterns where you might new an exception without immediately throwing it: [1, 7] 

* Exception Factories: A method might create and return a pre-configured exception object for a caller to throw.
* Error Logging/Reporting: You might create an exception simply to capture the current stack trace for logging purposes without actually stopping the program's execution.
* Deferred Throwing: In asynchronous programming, one thread might create an exception and store it in a "result" object so another thread can check it and throw it later. [1] 

## Important Distinction
If your custom exception is a checked exception (extends Exception but not RuntimeException), you are only forced to use try-catch or throws if you actually throw it. Merely instantiating it does not trigger these compiler requirements. [7, 8] 
Would you like to see a code example comparing a new exception that isn't thrown versus one that is?

[1] [https://softwareengineering.stackexchange.com](https://softwareengineering.stackexchange.com/questions/207232/are-there-legitimate-reasons-for-returning-exception-objects-instead-of-throwing)
[2] [https://rollbar.com](https://rollbar.com/blog/how-to-use-the-throws-keyword-in-java-and-when-to-use-throw/)
[3] [https://medium.com](https://medium.com/@karunakunwar899/stop-throwing-exceptions-a-better-way-to-handle-errors-in-java-60c341d8f686)
[4] [https://www.upgrad.com](https://www.upgrad.com/blog/exception-handling-interview-questions-answers/#:~:text=Exception%20handling%20is%20essential%20for%20robust%20applications%2C,it%20gathers%20and%20stores%20detailed%20execution%20information.)
[5] [https://signoz.io](https://signoz.io/guides/throw-exception-vs-logging/#:~:text=Exceptions%20are%20expensive%20operations%20in%20terms%20of,consumes%20more%20resources%20than%20regular%20code%20execution.)
[6] [https://ahdak.github.io](https://ahdak.github.io/blog/effective-java-part-9/#:~:text=Item%2072%20:%20Favor%20the%20use%20of,these%20classes%20as%20if%20they%20were%20abstract.)
[7] [https://stackoverflow.com](https://stackoverflow.com/questions/22996407/java-throws-runtimeexception)
[8] [https://raygun.com](https://raygun.com/blog/java-exceptions-terminology/)


# Java Checked vs Unchecked Exceptions

In Java, an exception is an unwanted or unexpected event that occurs during the execution of a program, i.e., at run time, that disrupts the normal flow of the program’s instructions. In Java, there are two types of exceptions:

* Checked Exception: These exceptions are checked at compile time, forcing the programmer to handle them explicitly.
* Unchecked Exception: These exceptions are checked at runtime and do not require explicit handling at compile time.

## Types of Checked Exception
* Fully Checked Exception: A checked exception where all its child classes are also checked (e.g., IOException, InterruptedException).
* Partially Checked Exception: A checked exception where some of its child classes are unchecked (e.g., Exception).

Checked exceptions represent invalid conditions in areas outside the immediate control of the program like memory, network, file system, etc. Any checked exception is a subclass of Exception.

## Unchecked Exceptions in Java
Unchecked exception are exceptions that are not checked at the compile time. In Java, exceptions under Error and RuntimeException classes are unchecked exceptions, everything else under throwable is checked. 

Consider the following Java program. It compiles fine, but it throws an ArithmeticException when run. The compiler allows it to compile because ArithmeticException is an unchecked exception.

```java
class Geeks {
    public static void main(String args[]) {
      
        // Here we are dividing by 0 which will not be caught at compile time as there is no mistake but caught at runtime because it is mathematically incorrect
        int x = 0;
        int y = 10;
        int z = y / x;
    }
}
```

Note:
* Unchecked exceptions are runtime exceptions that are not required to be caught or declared in a throws clause.
* These exceptions are caused by programming errors, such as attempting to access an index out of bounds in an array or attempting to divide by zero.
* Unchecked exceptions include all subclasses of the RuntimeException class, as well as the Error class and its subclasses.

The separation into checked and unchecked exceptions sounded like a good idea at the time. Over the years, it has introduced more boilerplate and less aesthetically pleasing code patterns than it solved real problems. The typical pattern within the Java ecosystem is to hide the checked exception within an unchecked one.

```java
try {
    // Some I/O operation here
    } catch( final IOException ex ) {
        // IOException is checked exception.
        throw new RuntimeException( "I/O operation failed", ex );
}
```

| **Checked Exception**                                                                                      | **Unchecked Exception**                                                    |
| ---------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| Checked exceptions are checked at compile time.                                                            | Unchecked exceptions are checked at run time.                              |
| Derived from `Exception`.                                                                                  | Derived from `RuntimeException`.                                           |
| Caused by external factors like file I/O and database connection cause the checked exception.              | Caused by programming bugs like logical errors cause unchecked exceptions. |
| Checked exceptions must be handled using a try-catch block or must be declared using the `throws` keyword. | No handling is required.                                                   |
| Examples: `IOException`, `SQLException`, `FileNotFoundException`.                                          | Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`.        |

