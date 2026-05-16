Good focus — you identified the **three most likely oral-exam topics** for Seminar 4:

✅ Exception Handling  
✅ Design Patterns (MVC, Observer, Template Method, DTO, etc.)  
✅ Unit Testing

Below is a **carefully prepared set of realistic teacher questions + strong answers**, tailored to your **ElectricBikeRepair project + course material**.

***

# ✅ 🔥 1. Exception Handling Questions

***

## ❓ Q1: How is exception handling structured in your system?

✅ **Good answer:**

The system follows the layered architecture and handles exceptions at different levels:

* **Model layer** throws exceptions when business rules fail
* **Controller layer** may propagate exceptions
* **View layer** catches exceptions and presents user-friendly messages

Additionally, I use centralized handling (e.g., in observers or template methods) to ensure consistent behavior.

***

## ❓ Q2: Why should business logic throw exceptions instead of printing errors?

✅ Answer:

Because:

* The model should **not depend on the user interface**
* Printing is a responsibility of the **view layer**
* Throwing exceptions allows:
  * flexible error handling
  * reuse of business logic in different contexts

👉 This ensures **low coupling and high cohesion**

***

## ❓ Q3: What is wrong with catching exceptions in the model layer?

✅ Answer:

Catching exceptions in the model layer:

* hides errors from higher layers
* mixes responsibilities (logic + UI decision)
* reduces flexibility

Instead:
👉 The model should **throw exceptions**
👉 The view should **decide how to display them**

***

## ❓ Q4: What is the purpose of the `try-catch` block in Template Method?

✅ Answer:

It ensures that:

* **all subclasses follow the same error handling structure**
* execution flow is controlled in one place

Example:

```java
try {
    doHandle();
} catch (Exception e) {
    handleErrors(e);
}
```

This:

* enforces consistency
* prevents duplicate error handling logic

***

## ❓ Q5: What are common mistakes in exception handling?

✅ Answer:

* Catching exceptions too early (wrong layer)
* Ignoring exceptions
* Logging business exceptions (forbidden by book)
* Mixing UI logic into model
* Not providing meaningful error messages

***

# ✅ 🔥 2. Design Patterns Questions

***

## ❓ Q6: Which design patterns did you use and why?

✅ Example answer (adapt to your code):

I used several patterns:

* **MVC pattern** → separates UI, control, business logic
* **Observer pattern** → notify views/loggers of changes
* **Template Method pattern** → standardize observer behavior
* **DTO pattern** → transfer data between layers

Each pattern improves:

* maintainability
* extensibility
* clarity

***

## ❓ Q7: Explain how the Observer pattern works in your system.

✅ Answer:

* A **subject (RepairOrder)** keeps a list of observers
* Observers are notified when the state changes
* Each observer reacts differently

Example:

* View → prints notification
* Logger → writes logs

Benefits:

* low coupling
* easy to add new observers

***

## ❓ Q8: Why use Template Method for observers?

✅ Answer:

Because observers share:

* same execution structure
* but different behaviors

Template Method:

* fixes algorithm structure
* allows subclasses to customize behavior

Benefits:

* avoids code duplication
* enforces consistent execution

***

## ❓ Q9: What problem does DTO solve?

✅ Answer:

DTO solves long parameter list problem:

Without DTO:

```java
method(name, phone, address, ...)
```

With DTO:

```java
method(CustomerDTO)
```

Benefits:

* simpler method signature
* easier maintenance
* reduces coupling

***

## ❓ Q10: When should inheritance NOT be used?

✅ Answer (important, from Chapter 9.3):

Inheritance should not be used when:

* subclass depends on internal behavior of superclass
* strong coupling is created
* encapsulation is broken

Instead:
👉 prefer composition

***

# ✅ 🔥 3. Unit Testing Questions

***

## ❓ Q11: How do you test `System.out` output?

✅ Answer:

By redirecting output:

```java
ByteArrayOutputStream output = new ByteArrayOutputStream();
System.setOut(new PrintStream(output));
```

Then:

```java
String result = output.toString();
assertTrue(result.contains("expected text"));
```

***

## ❓ Q12: What makes a unit test good?

✅ Answer:

According to course:

* self-evaluating (uses assertions)
* no output on success
* covers meaningful scenarios
* easy to understand
* independent

***

## ❓ Q13: Why test both normal and error cases?

✅ Answer:

Because:

* system must handle real-world scenarios
* errors are part of behavior

Testing only success:
❌ incomplete  
✅ robust testing includes failures

***

## ❓ Q14: Why is testing important in iterative development?

✅ Answer:

From course book:

* ensures system works after changes
* enables safe refactoring
* provides quick feedback

Without tests:
👉 changes become risky and slow

***

## ❓ Q15: What is a self-evaluating test?

✅ Answer:

A test that:

* uses assertions
* does not print anything on success
* fails with clear message when incorrect

Example:

```java
assertTrue(output.contains("Expected"));
```

***

# ✅ 🔥 4. Advanced / Higher-Grade Questions

***

## ❓ Q16: How do your patterns improve cohesion and coupling?

✅ Answer:

* MVC → separates responsibilities → high cohesion
* Observer → reduces dependencies → low coupling
* DTO → isolates data flow → low coupling

***

## ❓ Q17: What would happen if View called Model directly?

✅ Answer:

* high coupling
* UI changes affect business logic
* violates MVC

***

## ❓ Q18: How could your design be extended?

✅ Answer:

* add new observers (no modification needed)
* add new views
* replace database layer

***

## ❓ Q19: What design decision was the hardest?

✅ Good answer idea:

* DTO vs Entity decision
* Observer design
* where to place logic

Teachers LOVE reflection answers.

***

## ❓ Q20 (very common): Where should exception handling logic be placed?

✅ Answer:

* Exceptions are **thrown in model**
* **handled in view or template structure**

This preserves:

* separation of concerns
* clean architecture

***

# ✅ ✅ Final Advice (VERY IMPORTANT)

When answering in seminar:

👉 Always connect to:

* **cohesion**
* **coupling**
* **encapsulation**
* **layer separation**

👉 Avoid:

* code-only answers
* guessing

👉 Instead:

* explain *why*

***

# ✅ If you want next

I can:

✅ Simulate a **real teacher oral exam (interactive)**
✅ Evaluate your **answers like a grader**
✅ Identify **weak points before seminar**

Just tell me 👍

---

