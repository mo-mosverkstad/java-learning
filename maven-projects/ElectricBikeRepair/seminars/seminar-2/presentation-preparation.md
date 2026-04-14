Great job finishing Seminar 2 — your report is solid, and you’re well‑prepared for the seminar. Below is a **complete 10–20 minute presentation plan**, followed by **typical seminar questions you are very likely to be asked**, with **model answers** aligned with Leif Lindbäck’s course philosophy and *your* actual solution.

You can use this **as‐is for slides or as speaker notes**.

***

# Seminar 2 Presentation

**Object‑Oriented Design – Repair Electric Bike**

**Target length:** \~12–15 minutes (comfortable), extensible to 20 minutes with discussion.

***

## 1. Introduction (1–2 minutes)

### Slide / Talking points

*   Seminar 2 focuses on the **design phase**, not code implementation.
*   Goal: design a **well‑structured object‑oriented architecture**.
*   Based on:
    *   Seminar 1 **Domain Model (DM)** and **System Sequence Diagram (SSD)**
    *   Principles from **Section 4.1 & Chapter 5** of *A First Course in Object‑Oriented Development*.

### Key message

> “The goal is to show *how* the system is structured and *why* the design decisions follow object‑oriented principles.”

***

## 2. Requirements Recap (1 minute)

### Slide / Talking points

Seminar 2 requirements:

*   MVC + Layer pattern
*   High cohesion, low coupling, strong encapsulation
*   Design **all system operations**
*   Include:
    *   Interaction diagrams for all behavior
    *   Startup design
    *   Class diagram consistent with interaction diagrams
*   View and persistence are **out of scope**

 All are explicitly addressed in the report.

***

## 3. Architecture Overview – MVC + Layer (2–3 minutes)

### Slide

**Layered Architecture Diagram**

### Talking points

*   The system uses **MVC inside a layered architecture**.
*   Layers used:
    *   **Startup** – object creation & wiring
    *   **View** – user interaction only
    *   **Controller** – orchestration, no business logic
    *   **Model** – domain logic and state
    *   **Integration** – DTOs, registries, printing, JSON

### Emphasize

*   Direction of dependencies strictly enforced.
*   Controllers never depend on View.
*   Model never depends on higher layers.

***

## 4. Design Method (2 minutes)

### Slide

**Design steps**

### Talking points

1.  Identify layers using MVC + Layer patterns
2.  Design **one system operation at a time**
3.  Draw **communication diagrams** (instead of sequence diagrams)
4.  Apply cohesion, coupling, encapsulation continuously
5.  Maintain class diagram as design evolves

### Tooling

*   UML diagrams created using **Astah**
*   Code implementation used only to **verify feasibility**, not required by assignment

***

## 5. System Operations & Interaction Diagrams (4–5 minutes)

### Recommended slides

One slide per **2–3 system operations** or grouped logically.

### Example: `searchCustomer`

*   View passes raw telephone number
*   Controller validates & normalizes it
*   Registry lookup
*   DTO returned to View

### Explain briefly for:

*   `createRepairOrder`
*   `findRepairOrder`
*   `addDiagnosticResult`
*   `createProposedRepairTask`
*   `acceptRepairOrder` / `rejectRepairOrder`
*   **Startup**

### Key design decisions to highlight

*   Controllers delegate all business logic
*   DTOs used at layer boundaries
*   No entity is ever passed to View

***

## 6. Full Class Diagram (2 minutes)

### Slide

**Full class diagram**

### Talking points

*   All classes in interaction diagrams are present
*   Each package corresponds to one layer
*   Model contains entities:
    *   RepairOrder, Problem, DiagnosticReport, Cost, etc.
*   Integration contains DTOs and registries
*   Clear separation of responsibilities

***

## 7. Design Quality Evaluation (4 minutes)

This is often where **teachers ask most questions**.

***

### High Cohesion

*   Each class has **one responsibility**:
    *   RepairOrder → repair‑order behavior
    *   Problem → problem data
    *   DiagnosticReport → diagnostic tasks
    *   Cost → monetary value
    *   TelephoneNumber → parsing/normalization

***

### Low Coupling

*   Controllers call **high‑level domain methods only**
*   Model has **no knowledge of controllers or view**
*   DTOs isolate model from external layers
*   Utility logic isolated (e.g., TelephoneNumber)

***

### Encapsulation

*   All entity fields are private
*   State changes only via domain methods
*   DTOs are immutable snapshots
*   Internal collections never leaked

***

## 8. Type Correctness & Object Usage (2 minutes)

### Talking points

*   Explicit parameter sources (e.g., phone number → TelephoneNumber)
*   Avoid primitive obsession:
    *   Cost instead of float
    *   ProblemDTO instead of multiple primitives
*   Static usage minimized
    *   Only constants allowed
*   Java naming conventions consistently applied

***

## 9. Conclusion (1 minute)

### Key takeaway

> “The design clearly separates responsibilities, keeps model logic encapsulated, and follows object‑oriented design principles as defined in the course literature.”

Optional:

*   Ready for evolution (GUI, database, new features)
*   Easy to test and maintain

***

# Likely Seminar Questions & Model Answers

These are **very likely** to be asked.

***

### Q1: *Why did you choose communication diagrams instead of sequence diagrams?*

 **Answer**  
Communication diagrams are more compact and make object relationships clearer. Since the focus is on **who collaborates with whom**, they scale better when designing many system operations.

***

### Q2: *Why do controllers not contain business logic?*

 **Answer**  
According to MVC and the course guidelines, controllers coordinate flow but **do not own domain behavior**. Business logic belongs in model entities, which improves cohesion and prevents controller classes from becoming “god objects.”

***

### Q3: *Why are DTOs immutable?*

 **Answer**  
Immutability ensures that Views and Controllers cannot accidentally modify model state. It strengthens encapsulation and allows safe data transfer across layers without sharing mutable references.

***

### Q4: *Why is `RepairOrder.updateProblem(ProblemDTO)` preferred over exposing `getProblem()`?*

 **Answer**  
Because it preserves encapsulation. RepairOrder owns the Problem; therefore, all modifications must go through RepairOrder. Exposing internal objects would allow external classes to break invariants.

***

### Q5: *Why is `TelephoneNumber` a separate class instead of a static helper method?*

 **Answer**  
Telephone number parsing has its own abstraction and rules. Encapsulating it in a dedicated class improves cohesion, avoids duplicated logic, and allows future extensions without impacting other parts of the system.

***

### Q6: *Why do you separate ReceptionistController and TechnicianController?*

 **Answer**  
They represent different roles with different responsibilities and permissions. Separating them improves cohesion and avoids role‑checking logic inside methods.

***

### Q7: *What would be the first change if you add a database later?*

 **Answer**  
Only the Integration layer would change. Registries could be replaced or extended with database-backed implementations without touching controllers or model entities.

***

### Q8: *What part of the design was hardest?*

 **Suggested honest answer**  
Balancing encapsulation with usability — especially deciding which operations belong inside entities versus controllers.

***

# Final Tip for the Seminar

When answering questions:

*   Always refer back to **cohesion, coupling, encapsulation**
*   Explicitly say *“According to the course guidelines…”*
*   Use **your own class names** in answers — examiners like that


# Detail part


Perfect — this is exactly the right direction for a strong seminar presentation.  
Below is a **refined, example‑driven structure** where **each concrete design choice is explicitly mapped to**:

*    **High encapsulation**
*    **High cohesion**
*    **Low coupling**

You can use this **directly as speaking points or slide content**.

***

# Core Design Decisions Explained Through OOD Principles

## 1. Keeping **both Entity and DTO versions**

### Design choice

Every important domain concept exists in **two forms**:

*   A **mutable entity** in the Model layer (e.g. `RepairOrder`)
*   An **immutable DTO** in the Integration layer (e.g. `RepairOrderDTO`)

***

### Why this improves **encapsulation**

*   Entities (`RepairOrder`, `Problem`, `DiagnosticReport`) never leave the Model.
*   External layers cannot mutate internal state accidentally.
*   DTOs expose *data only*, never behavior.

> This prevents invariant‑breaking updates from outside the model.

***

### Why this improves **cohesion**

*   Entities focus on **business behavior**
*   DTOs focus on **data transfer**
*   No class is forced to “do two jobs”

***

### Why this improves **low coupling**

*   View and Controller depend on DTOs, not entities.
*   Model can change internally without breaking external code.
*   Replacing View (CLI → GUI → REST) requires no Model changes.

***

## 2. `RepairOrder` owns and controls all its state

### Design choice

Instead of:

```java
repairOrder.getProblem().update(problemDTO);
```

You intentionally wrote:

```java
repairOrder.updateProblem(problemDTO);
```

***

### Why this improves **encapsulation**

*   Internal objects (`Problem`, `DiagnosticReport`) cannot be modified directly.
*   `RepairOrder` guards its own invariants.
*   No external code can partially update internal state.

> “Exporting behavior, not structure” — core OO principle.

***

### Why this improves **cohesion**

*   All repair‑order changes are concentrated in one place.
*   `RepairOrder` truly represents the repair‑order abstraction.
*   Internal details are hidden and locally consistent.

***

### Why this improves **low coupling**

*   Controllers do not need to understand internal structure.
*   Changing `Problem` internals does not affect any controller.
*   Removes navigation chains (`a.getB().getC()`), which Lindbäck warns against.

***

## 3. `Problem` manages **problem data only**

### Design choice

`Problem` contains:

*   description
*   broken bike reference

And nothing else.

***

### Why this improves **high cohesion**

*   `Problem` represents *one domain concept*
*   No cost calculation
*   No diagnostic logic
*   No printing logic

> A very small class, but a *very strong abstraction.*

***

### Why this improves **encapsulation**

*   Updates are controlled via entity methods.
*   Invalid combinations of problem data cannot be created externally.

***

### Why this improves **low coupling**

*   Other parts of the system treat `Problem` as a black box.
*   Changes to problem representation don’t ripple outward.

***

## 4. `DiagnosticReport` owns diagnostic tasks

### Design choice

All diagnostic tasks are:

*   Created
*   Updated
*   Aggregated

inside `DiagnosticReport`.

***

### Why this improves **high cohesion**

*   Everything related to diagnostics is in one place.
*   `RepairOrder` does not know task details.
*   `DiagnosticTask` logic isn’t duplicated elsewhere.

***

### Why this improves **encapsulation**

*   Diagnostic results are updated through controlled methods.
*   No external class modifies diagnostic tasks directly.

***

### Why this improves **low coupling**

*   Controllers just “tell” that a result exists.
*   Model decides what that means internally.

***

## 5. `Cost` encapsulates money as a value object

### Design choice

Instead of passing `float` or `double`, you introduced:

```java
Cost(amount, currency)
```

***

### Why this improves **high cohesion**

*   Money logic is centralized.
*   No scattered arithmetic across classes.
*   Cost aggregation belongs *only* to `Cost`.

***

### Why this improves **encapsulation**

*   Currency consistency is enforced internally.
*   Invalid operations are caught early.

***

### Why this improves **low coupling**

*   If money representation changes (precision, currency rules),
    only `Cost` is affected.
*   No controller or entity logic changes.

***

## 6. `TelephoneNumber` as a dedicated utility object

### Design choice

All parsing and normalization logic is placed in:

```java
TelephoneNumber
```

***

### Why this improves **high cohesion**

*   One responsibility: phone numbers.
*   No registry, no controller, no model modifies phone strings.

***

### Why this improves **encapsulation**

*   Validation rules are hidden.
*   Controllers just ask for `.toE164()`.

***

### Why this improves **low coupling**

*   Change in phone format rules requires exactly one class change.
*   No duplication of parsing logic.

***

## 7. Controllers only coordinate — never decide

### Design choice

Controllers:

*   Validate input
*   Call domain methods
*   Store or retrieve via registries

They do **not**:

*   Inspect entity fields
*   Make business decisions
*   Enforce invariants

***

### Why this improves **low coupling**

*   Controllers are thin and easily replaceable.
*   Model does not depend on controller logic.

***

### Why this improves **high cohesion**

*   Controllers focus only on orchestration.
*   Domain logic remains in entities.

***

### Why this improves **encapsulation**

*   Domain rules cannot be bypassed.
*   State changes go through controlled paths only.

***

## 8. RegistryCreator only creates registries

### Design choice

`RegistryCreator`:

*   Creates `CustomerRegistry`
*   Creates `RepairOrderRegistry`
*   Does nothing else

***

### Why this improves **high cohesion**

*   One responsibility: object creation.
*   No persistence logic, no business logic.

***

### Why this improves **low coupling**

*   Creation logic isolated in startup.
*   Controller and Model unaware of instantiation details.

***

### Why this improves **encapsulation**

*   Registry lifecycle can change without touching domain logic.

***

## Final Summary Sentence (Excellent for Closing Slide)

> “Each design decision explicitly enforces one or more of high encapsulation, high cohesion, and low coupling — not by convention, but by construction.”