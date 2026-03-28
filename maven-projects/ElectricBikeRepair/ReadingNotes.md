# Chapter 4 Analysis

Domain model (DM) is performed by UML class diagram

System sequence diagram is performed by UML sequence diagram

## 4.1 Domain Model

> “The domain model represents real‑world entities, not program classes.”

A **UML class diagram** is used to construct the **domain model**, but the elements in the DM represent things that exist in reality, not **classes** in an object oriented program. Therefore, it might be better to call them **entities** instead of classes.

The DM is a very good tool for discussions about the program that is being developed. It can ensure that all parties (**developers**, **clients**, **users**, etc) share a **common view** of the tasks of the program. All parties develop a **common nomenclature**.

The software should be quite similar to the DM to be an appropriate model of the reality. This will also make sure that **class names** in the software means something to domain experts.

The black triangular arrow in figure 4.2c shows in which direction the class-association class sequence shall be read, it does not tell anything about the association’s direction. It is up to the diagram author to decide if such black triangles shall be used or not. They are most commonly used if class-association-class shall be read from **right to left, or bottom up**.


### Step 1, Use Noun Identification to Find Class Candidates

It is also far more problematic to have **too few classes**, since it is much easier to cancel existing classes than to f ind new ones.

### Step 2, Use a Category List to Find More Class Candidates

### Step 3, Choose Which Class Candidates to Keep
Therefore, if it is really unclear if a class shall be removed or not, **just let it stay**, at least for now.

**Class Program** is removed. The unexperienced developer easily falls into the trap of modeling the program, instead of the reality, if the class Program is present.

### Step 4, Decide Which Classes Fit Better as Attributes

A simple, but very useful, guideline is that an attribute is a **string**, **number**, **time** or **boolean** value. A class that contain just one such value is a strong candidate to become an attribute.

Another important rule is that an attribute can **not have an attribute**.

A third rule is that when it is hard to decide if something is an attribute or a class, let it **remain a class**. Better to have too many classes than too few.

----

* telephone number -> class: cc, ac, sn (telephoneNumberDTO)?

* email address -> class: username@domainname / string?

* name -> string ~~class: first name, middle name and family name~~ Unless it is relevant to split it into first name and last name, it can be an attribute of Customer. It can also be an attribute of Receptionist and Technician, if needed

* bike brand -> enum class / string?

* bike model -> enum class / string?

* Cost is a number, and could become an attribute of ProposedRepairTask. But then what about **Currency**? Is that not a string that should be an attribute of Amount? This is something that should be discussed with the customer, but now let’s just decide we do not need to keep track of currencies. Therefore, **Currency is removed (currency is SEK by default)** and Amount becomes attribute of ProposedRepairTask, and it is same as totalCost, which is the attribute of RepairOrder.

----

### Step 5, Add Associations

----
* The association **Customer takes Receipt** just tells what the customer is doing. Try to **avoid associations telling what users do**, that is instead showed in the **System Sequence Diagram**, which is explained in the next section.
=>
So the association between **Receptionist** and **RepairOrder** should be removed, similar as the association between **Technician** and **ProposedRepairTask** ???

* The class **Address has no association**. This is OK for classes that exist just to group data, and do not have a specific meaning but are used in many places.
=> class **Date**, class **RepairOrderState**, class **TelephoneNumber** and so on.

----
### Step 6, Anything To Change?

## 4.3 System Sequence Diagram

Strictly speaking, creating an SSD is not part of the analysis, but instead belongs to **gathering requirements**. Here, we consider it under the analysis section since it is a preparation for program construction.

While the domain model is very much a matter of discussion, the SSD is **more straight forward** to create. It shall reflect the interactions of the requirements specification, **no less and no more**.

1. The customer arrives and asks to rent a car.
2. The customer describes the desired car.

Bullets one and two do not contain any interaction between the actor (cashier) and the system. Remember that it is completely uninteresting for the SSD what happens “outside” the actor. Therefore, **it would be wrong to include the customer**.

3. The cashier registers the customer’s wishes.
4. The program tells that such a car is available.

In bullet three, there is an interaction that shall be included. A system operation shall have a name that starts with a verb, and describes what is done. The system operation in bullet three can be named **searchMatchingCar**.

----
System operations:

13. Technician asks system for repair order.
14. System presents repair order.
15. Technician performs diagnostic.
16. Technician enters diagnostic report and proposed repair tasks.
17. System updates repair order, by adding diagnostic report and proposed repair tasks.

* How to get the repair order from system? RepairOrderId? How to store all of repair orders in RepairOrderRegistry? List<id:string, RepairOrder>

System <-- getRepairOrder(RepairOrderId)-- Technician

System --  foundRepairOrder -------------> Technician

System <-- createDiagnosticReport(diagnosticDescription) -- Technician

System --- createConfirmed --------------> Technician

loop [moreProposedRepairTask]

System <-- createProposedRepairTask(foundRepairOrder, proposedRepairTaskDescription) -- Technician

System --- createConfirmed --------------> Technician

----

# Chapter 5 Design

## 5.2 Design Concepts

### Encapsulation

* **visibility**: public / private, static, final (it is safest to consider the final modifier to be part of the public interface because the final value could not be changed.)  

### High Cohesion

The goal is that a class shall represent **one single** abstraction, which is clearly identified by the class name. Furthermore, the class shall **have knowledge** about that abstraction, **not about anything else**, and perform tasks related to that abstraction, not to anything else.

----
* TelephoneNumber is taken from Customer.
* Bike also

----

### Low Coupling

Low coupling means there are as **few dependencies** as possible in the program. It is not possible to tell a maximum al lowed number, what matters is that there are no dependencies which are not required.

----
* RepairOrder -> DiagnosticOrder -> ProposedRepairTask && RepairOrder -> ProposedRepairTask
* RepairOrder -> Customer -> Bike && RepairOrder -> Bike

----

## 5.3 Architecture

The MVC(Model-View-Controller) Architectural Pattern

Controller: + systemOperation1() ...

As an example, consider the **main** method. Its task is to **start** the program, which is not related to any layer mentioned so far. Therefore, yet a new layer must be introduced, whose responsibility is to start the application.

Note that some layers have a particularly close relation.

* controller layer -> model layer (As a consequence, model shall only be called by controller and never by any other layer)
* dbhandler layer -> database layer (data shall only be called by dbhandlerandnever by any other layer)
* controller layer -> dbhandler layer (Apart from this, layers may be bypassed. It is for example perfectly fine to call dbhandler directly from controller, instead of going via model.)

The **DTO** (Data Transfer Object) Design Pattern:  a long parameter list means a large public interface, and thereby a big risk that it is changed.

A DTO, on the other hand, is read-only, it has **only get methods**. This means it is **immutable**, none of its fields can ever change value. Also, since it can’t change state, it has no history. It’s just **a snapshot** of what something looked like at a particular instance in time.

----
Customer -> Bike vs CustomerDTO -> BikeDTO

----

## 5.4 A Design Method

1. **Use the patterns MVC and layer**. This means to create one package for each layer
2. **Design one system operation at a time**. When creating the design, use **interaction diagrams**. **Do not use a class diagram**, which has no notion of time or execution flow. Whether to use sequence or communication diagrams is a matter of taste.
3. **Strive for high cohesion, low coupling, and a high degree of encapsulation with a small, well-defined public interface**. 
   * ***The domain model*** helps to find new classes that can be introduced. If the ***DM*** is changed, all involved parties must agree on that change.
   * When designing, **favor objects over primitive data** and **avoid static members**, since neither primitive data nor static members are object oriented. When using these, the entire object concept is completely ignored, and the prime tool (objects) to handle encapsula tion, cohesion and coupling is thrown away.
4. **Maintain a class diagram**. Such a diagram tends to become very big and messy, it is permitted to omit parts to make the diagram clearer. If that is done, it should be clearly specified
5. **Implement the new design in code**.

## 5.5 Designing the RentCar Case Study
1. Step 1, Use the Patterns MVC and Layer, the design looks as in ***figure 5.20*** and ***figure 5.24***.
2. Step 2, Design One System Operation at a Time,
   * The view is not designed here, instead the view package contains a single class, View, which is a placeholder for a real view, that certainly would consist of more classes.
   * The system operations are designed in the order they are executed according to the SSD, ***figure 5.22***.
   * The first operation in the SSD is searchMatchingCar. The first step is to create an **interaction diagram**. 
3. Step 3, Strive for encapsulation with a small, well-defined public interface, high cohesion and low coupling
   * The question, which is the type of the parameter searchedCar and the return value foundCar, is the first of a large number of design decisions. The answer shall be guided by the concepts encapsulation, cohesion and coupling. -> **both a DTO and an entity?** searchedCarDTO only getter methods without business logic, located in dbhandler layer.
   * The fact that the **same name** is used implies that it is in fact the very same object in all three places, which is important information for the reader.
   * Data can **not appear out of nothing**, since searchedCar is a parameter in method call two, it must be clear from where this object comes.
   *  For now, we just choose the simplest solution, namely to let Car be a **DTO**, place it in dbhandler, and not add an entity. This decision might have to be changed later.
   *  Here instead, since there is no database, CarRegistry will just look in **an array** of available cars.
   *  Figure 5.27 Call to dbhandler layer is added. (loop is not part of program, just decided by casher)
   *  Figure 5.28 The start sequence in the main method.
4. Step 4, Maintain a Class Diagram With All Classes.  Note that it is **not mandatory** to include all attributes, methods and references if they do not add any important information, but only obscure the diagram. For example, it is common not to include references to **DTOs**, since they are used in many different layers and are considered as data types.
5. Before leaving searchMatchingCar, we **evaluate** it according to the criteria encapsula tion, cohesion and coupling.
   * Encapsulation
     * All methods in searchMatchingCar are public ― not ideal but acceptable early in design.
     * Public methods are necessary because layers must communicate and the system still has few methods.
     * All fields are private, which is considered good encapsulation practice.
   * Cohesion
     * Each class performs exactly its intended responsibility:
       * Main: starts the program.
       * View: delegates calls to the controller.
       * Controller: executes a system operation that triggers the search in CarRegistry.
       * CarRegistry: contains only the search method.
       * CarDTO: contains no methods (pure data holder).
   * Coupling
     * Dependencies follow the expected layer structure: View → Controller → DBHandler.
     * This unidirectional dependency chain matches the layer pattern design.
     * Main is allowed to depend on classes in other layers because its role is to initialize the system.
     * Coupling in Main is low since it references only one class per layer.
     * Excessive references within the same layer would indicate too high coupling, but that is not the case here.
     * Overall cohesion is high: every class focuses on one task.

## 5.6 Another system operations
### Notes
Note that the CarRegistry method that marks the car as being booked is called **setStateOfBookedCar**, and not bookCar. The reason is that booking the car is business logic, which belongs in the model.

It is a quite interesting decision to **let Rental**, instead of Controller **call setStateOfBookedCar**. The motive is that the controller should not have detailed knowledge about all details of all system operations. That would lead towards spider-in-the-web design, with the controller as spider.

Main creates two objects in the dbhandler layer. This is a warning sign that it might be getting unnecessarily high coupling to that layer. Also, the dbhandler layer might have a bit bad encapsulation, since it has to reveal the existence of CarRegistry and RentalRegistry to main. These problems can be solved by changing that startup design to the one in figure 5.35, where the class **RegistryCreator** is responsible for creating the registries, thus hiding their existence to main.

The design class diagram, figure 5.36, is now becoming quite big. In order to reduce it, the **DTOs are omitted**. Another option would have been to **split it into more, smaller diagrams**. 

Generally, it is a bit dangerous to force an **amount** to have a specific primitive type. For ex ample it is not clear whether an amount can have decimals or not. By introducing the **Amount class**, the primitive type of the amount is encapsulated inside that class, and can thus easily be changed.

**CashPayment class**: Figure 5.38 Payment and CashRegister handling the pay system operation. (With both Amount and CashPayment classes, think about class Cost in assignment).

printer page 87-88 -> create a new layer or to extend (and rename) dbhandler to handle interaction with any other system, so far databases and printers. -> **intergation**

Let’s not include **Printer** in RegistryCreator, since, after all, a printer connection is completely different from a database connection. The RegistryCreator will be responsible only for connecting to the database. -> ***Figure 5.40 The complete startup design.***

Page 89, Figure 5.40: Why is the CashRegister object created by Controller, when all other objects are created by main and sent to Controller? This is a **trade-off** between two contradicting arguments. On one hand, coupling is lowered if main is not associated to all other objects created during startup. On the other hand, cohesion of the Controller constructor is increased if it does not create loads of other objects, besides controller.

### Evaluate the Completed RentCar Design page 89
* **Encapsulation**
  * No method, class, package, or layer currently has *lowerable* visibility — everything that is public must be public for now.
  * However, the design still exposes many public methods, which is **not ideal** for good encapsulation.
  * Adjusting responsibilities can reduce public exposure — e.g., moving operations from Controller into lower layers. (*when saving to db, it should be done in the module, instead of controller*)

* **Coupling**
  * Coupling is relatively high:
    * **Controller is associated with nearly all classes in the model and integration layers.**
  * This could be improved if:
    * Model objects collaborated directly with each other. (*Controller only knows RepairOrder, no idea about DiagnosticOrder or ProposedRepairTask*)
    * Model objects called integration classes without always going through Controller.
  * Example improvement:
    * Change `Receipt getReceipt()` in `Rental` to `void printReceipt(Printer printer)`.
    * Move the printer call from **Controller → Rental**, so Rental handles its own printing.
  * Benefits of this change:
    * Removes Controller → Receipt dependency.
    * Removes Controller → Printer dependency.
    * Allows Receipt constructor to become **package‑private**.
    * Overall reduced coupling and improved layering.
  * Further possible refinements:
    * Let `CashPayment` call `CashRegister` (or vice versa), removing another Controller dependency.
  * Current design is acceptable but can still evolve toward lower coupling.

* **Cohesion**
  * No issues detected.
  * All layers, classes, and methods perform their intended purposes and **nothing more**.
  * Cohesion is considered **good** throughout the design.

Page 90: RetalRegistry: saveRental(Rental)? Maybe saveRental(RentalDTO) is better? No, keep Rental. To controller, it could call some key module in module layer and call registry for data retrieve or saving.

## Common mistakes
1. **Spider‑in‑the‑web class**
   *   A central class (often the Controller) becomes overly connected to many peripheral classes.
   *   Fix by reducing its associations and letting peripheral classes communicate directly.
   *   Example: redesigning receipt printing to remove unnecessary Controller links.
2. **Too much primitive data**
   *   Excessive use of primitive parameters instead of proper objects.
   *   Long parameter lists or many attributes suggest missing abstractions.
   *   Using primitives weakens encapsulation, cohesion, and coupling control.
3. **Unwarranted static methods or fields**
   *   Static members should only exist for strong, justified reasons.
   *   Static data/methods are not tied to objects → reduces ability to use OO principles effectively.
4. **Too few classes**
   *   A model with only one or very few classes often has poor cohesion.
   *   Each class should represent one responsibility; too few classes suggests overloaded classes.
5. **Too few layers**
   *   A missing layer (View, Controller, Model, Startup, Integration) requires justification.
   *   Names may vary, but their *roles* must exist.
   *   Missing layers makes responsibility separation unclear.
6. **Too few methods**
   *   Even if existing methods have high cohesion, some required system tasks may simply be missing.
   *   If functionality isn’t implemented, new methods must be added.
7. MVC or Layered Architecture used incorrectly**
   *   **No I/O outside the View**.
   *   **Model must not call databases or external systems**.
   *   **Integration layer must only call external systems (no business logic)**.
   *   **Calls must always flow from higher layers → lower layers**.
8. **Data appears out of nothing**
   *   Designs must be implementable: a method cannot be called with parameters the caller cannot possibly know.
   *   Example: a controller method needing `paidAmt` even though the View never provides it.
9. **Calling an object without a reference**
   *   An object must have a reference to another before making a call to it.
   *   The design must show how the reference was obtained.
   *   Example: an object attempting to call a method on another object it never received.
10. **Wrong message numbering**
    *   Message numbers must follow dot‑notation:
        *   Call 1 → calls 1.1 → calls 1.1.1, etc.
    *   Using sequential unrelated numbers (1, 2, 3, 4) suggests unclear causality.
11. **Objects not named when required**
   *   Any object appearing more than once in a communication diagram needs a name.
    *   Without a name, it is unclear that multiple appearances refer to the same instance.
12. **Wrong return type**
   *   Common mistake: making Controller methods `void` when they should return data to the View.
13. **Erroneous constructor**
   *   Constructor must:
       *   Have the same name as the class.
       *   Return the class type.
       *   Have the «create» stereotype in design diagrams.
14. **Too large class diagram**
    *   Can become unreadable if overly detailed or too condensed.
    *   Solutions:
        *   Split into multiple smaller diagrams.
        *   Remove unnecessary details (e.g., DTOs, private members).
    *   Full class design must still exist somewhere for coding.
15. **Too large communication diagram**
   *   Becomes messy when:
       *   Too many system operations are included.
       *   Too many alternative flows are shown at once.
    *   Solution: split into multiple diagrams for clarity.


# Questions

## Page 83-84: Please carefully read the chapter 5 design in the attached object-oriented-development.pdf, and answer my question follow the attached pdf taste and principle:

If I have the system operations to retrieve the rental and update the rental. To the system operation "retrieve the rental", the object of class RentalDTO would be retrieved from RentalRegistry, the attributes of this RentalDTO could not be changed in the view layer. If I want to continue the next system operation "Update the rental", should I create a new class Rental in the module layer? If yes, is it redundant of both RentalDTO and Rental?

Great question, Sanders — and you’re asking it exactly the way **Chapter 5 (Design)** wants you to: from the perspective of **MVC + Layers**, **DTO vs Entity**, **immutability**, **cohesion/coupling**, and **where business logic lives**.

Below is a crisp answer in the book’s “taste and principle”, with short citations to the attached PDF.

***

## Short answer

**Yes, you should have both a `Rental` (entity) and a `RentalDTO` (data transfer object).**\
They are **not redundant**: they serve **different purposes** in the architecture.

*   **`RentalDTO`**: an **immutable snapshot** used only to **transfer data across layers** (e.g., controller ⇄ view). It carries values and **no business logic**; view must not mutate it. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)
*   **`Rental`**: a **model entity** that **encapsulates business rules and state changes** for a rental (approve/reject/update tasks, etc.). Updates must be made **on this entity**, not on the DTO. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## Why both are needed (per Chapter 5)

1.  **DTO pattern purpose**\
    Chapter 5 introduces DTOs to **avoid long parameter lists**, keep the **public interface small and stable**, and **pass data through layers**. A DTO is a “just data container”, typically **read‑only/immutable**, and **exists only to carry data between layers**. The book explicitly distinguishes DTOs from entities: DTOs are **equal when all attributes are equal**, have **only getters**, and **no history**; entities have identity and **can change state**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

2.  **Where logic belongs**\
    Business logic must live in **Model**, never in the integration layer or view. In the car‑rental case study, the book adds a `Rental` entity to **represent the whole rental transaction**, keep references to related objects, and perform actions like **approve/reject/print receipt** (after refactoring) — i.e., **methods that change state and enforce rules**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

3.  **Immutability at the boundaries**\
    The text stresses making DTOs **immutable** precisely to prevent the view (or other layers) from changing them after passing them downstream. That is exactly your situation: the `RentalDTO` you “retrieve” for the view **shall not be mutated** in the view; instead, the next operation (“Update the rental”) must be expressed as **new input** that the controller sends to the model, which then updates the **`Rental` entity**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

4.  **Avoid spider‑in‑the‑web controllers**\
    Chapter 5 warns against controllers that do all work. Your **update** operation should **delegate** to model services/entities, and those can talk to integration (`RentalRegistry`) to load/persist. This keeps cohesion high and coupling low. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## How to flow the two operations (per book’s style)

### 1) **retrieveRental(rentalId) → RentalDTO**

*   **Controller** receives `rentalId`.
*   **Model service** loads the **`Rental` entity** from **`RentalRegistry`** (integration).
*   Convert the entity to **`RentalDTO`** and return to **Controller → View**.\
    *DTO is immutable and cannot be changed in the view.* [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

### 2) **updateRental(rentalUpdateDTO)** (or a small set of verb‑first operations)

*   **View** constructs a **new DTO** describing the requested changes (e.g., updated tasks/diagnostic/state).\
    (The book prefers passing an **object parameter** rather than many primitives.) [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)
*   **Controller** calls **Model**: load **`Rental`** by id → call **behavioral methods** on the entity (`updateDiagnostic(...)`, `addTask(...)`, `approve()`, `reject()`, etc.).
*   **Model** persists via **`RentalRegistry`**, and if needed, returns a **fresh `RentalDTO` snapshot**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

> **Key point:** you **never mutate `RentalDTO`**; you **apply changes to `Rental`** and emit a **new snapshot DTO** after the update. This mirrors the book’s treatment of `CarDTO` vs. `Rental`/services and its insistence on **immutable DTOs** and **business logic in entities/services**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## Is having both “redundant”?

**No.** Even if some fields look identical, **their responsibilities differ**:

*   **`RentalDTO`** = *representation for transfer* (stable shape, read‑only, no rules).
*   **`Rental`** = *domain object with identity + rules + state transitions* (logic lives here).

The book explicitly discusses this confusion using the car‑rental example: sometimes early designs start with just a DTO, but as soon as **business logic or identity** appears, you **introduce/keep the entity**. It even shows where they **first treated `Car` as `CarDTO`** for simplicity, then brought in **entities and registries** as design matured. Same principle applies to `Rental`. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## Common pitfalls to avoid (book’s “NO!” spirit)

*   **Don’t put logic into `RentalDTO`** (e.g., `matches(...)` or `approve()` inside DTO). That’s a **common mistake** highlighted in the case study: **DTOs are not to contain business logic**. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)
*   **Don’t let Controller become spider‑in‑the‑web.** Move detail work to **`Rental`** or **model services**, and let entities call integration as needed (like the book’s refactoring that moved printing from controller to `Rental`). [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)
*   **Don’t mutate DTOs in the view.** Make them **final/immutable**. Create a **new DTO** for each request or return value. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)
*   **Don’t mix layers.** Integration (`RentalRegistry`) should **persist/fetch**, not implement business rules; **Model** should **enforce rules**; **View** should only present/collect. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## Optional: minimal interaction sketch (book-compliant)

```mermaid
sequenceDiagram
    actor View
    participant Controller
    participant RentalService
    participant RentalRegistry

    %% Retrieve
    View ->> Controller: retrieveRental(rentalId)
    Controller ->> RentalService: getRental(rentalId)
    RentalService ->> RentalRegistry: findById(rentalId)
    RentalRegistry -->> RentalService: Rental (entity)
    RentalService -->> Controller: RentalDTO (from entity)
    Controller -->> View: RentalDTO

    %% Update
    View ->> Controller: updateRental(rentalUpdateDTO)
    Controller ->> RentalService: update(rentalUpdateDTO)
    RentalService ->> RentalRegistry: findById(rentalUpdateDTO.id)
    RentalRegistry -->> RentalService: Rental (entity)
    RentalService ->> RentalService: apply business rules on Rental
    RentalService ->> RentalRegistry: save(Rental)
    RentalRegistry -->> RentalService: ok
    RentalService -->> Controller: updated RentalDTO
    Controller -->> View: updated RentalDTO
```

*(Controller thin; DTO round-trips; entity handles rules; integration persists) — exactly as Chapter 5 recommends.* [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

***

## Conclusion

*   Keep **`RentalDTO`** for **data transfer** (immutable).
*   Keep/introduce **`Rental`** for **business behavior and state**.
*   Drive updates by **calling behavior on the entity**, then **map back** to a new DTO.\
    This is the clean, **non‑redundant** design the book teaches throughout Chapter 5 and the car‑rental case study. [\[object-ori...evelopment | PDF\]](https://ericsson-my.sharepoint.com/personal/sanders_wang_ericsson_com/Documents/jobs/RADIO_RTI/MyCCode/ood/object-oriented-development.pdf)

If you’d like, I can sketch the **package layout** (controller/model/integration) and a **tiny DTO↔entity assembler** the book implicitly relies on, still in the same style.




https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax



----
* TelephoneNumber: in view it is string as argument of searchCustomer(telephoneNumber:string), in model layer class TelephoneNumber to convert string to E164 international format for id in CustomerRegistry.
* module layer: RepairOrder, DiagnosticOrder, ProposedRepairTask.
* intergration layer: CustomerRegistry (function: retrieveCustomer; class: CustomerDTO, BikeDTO), RepairOrderRegistry (function: saveRepairOrder, retrieveRepairOrder???)

Questions:
1. The object retrieved from registry is DTO or entity? If it is entity with logic business, the view could directly call the functions to handle?

----

# Chapter 6 Programming
## 6.1 Dividing the Code in Packages
* Structured package naming hierarchy

* Some classes don’t belong to any specific layer because they provide general services used across multiple layers. These utility classes, such as those for string parsing or file handling, are placed in a separate package like util. In the car‑rental application, this package is named ***se.kth.iv1350.carRental.util***.

## 6.2 Code Conventions
*   **Package name**
    *   Type: *package*
    *   Description: Parts start lowercase; begin with reversed internet domain → product name → specific package.
    *   Example: `se.kth.iv1350.rentCar.model` or `se.kth.iv1350.rentcar.model`

*   **Class / Interface**
    *   Type: *class, interface*
    *   Description: Full descriptive name; each word starts with uppercase (PascalCase).
    *   Example: `CashRegister`

*   **Method**
    *   Type: *method*
    *   Description: Full description; starts lowercase; non-initial words start uppercase (camelCase).
    *   Example: `calculateTotalCost`

*   **Variable / Field / Parameter**
    *   Type: *variable, field, parameter*
    *   Description: Same rules as methods (camelCase).
    *   Example: `paidRental`

*   **Constant**
    *   Type: *constant (final static field)*
    *   Description: All uppercase letters; words separated by underscores.
    *   Example: `MILES_PER_KM`

## 6.3 Comments
Public interfaces must include **Javadoc** comments to generate API documentation.

Javadoc should state what a unit does — **not how** — and must document **method behavior, parameters, and return values** using proper tags.

**Avoid comments inside methods** — use them as a signal to refactor long methods instead.

## 6.4 Code Smell and Refactoring
**Code smells** signal poor design and should be removed through refactoring to improve code quality without changing behavior.

* **Duplicated Code**: Duplicated code is a severe code smell that must be eliminated because even a single repeated statement can lead to errors, inefficiency, and maintenance disasters.
* **Long Method**:
  * A method is too long if its name cannot fully explain it — use **smaller, well‑named** methods instead of comments to make code clearer.
  * Reducing method calls does **not meaningfully improve performance**, so optimizing by avoiding them is pointless.
* **Large Class**: A class is too large when its cohesion is low — splitting related fields into a new class improves clarity and design.
* **Long Parameter List**:
  * Long parameter lists are a code smell — **replace primitive parameters with objects** to improve cohesion, encapsulation, and maintainable design. Sometimes the object itself has already contains the paramter, use **this** to get it.
  * Remove unnecessary parameters by letting the method obtain the needed data itself instead of passing it in.
* **Excessive Use of Primitive Variables**: Primitive Obsession is a code smell—use objects instead of excessive primitive fields to improve cohesion, encapsulation, and overall design quality.
  * **Group** closely related fields and methods into a new class to improve cohesion and ensure data is always valid.
  * Never use arrays to store unrelated primitive values—replace them with objects so each piece of data has a clear, meaningful name.
  * Use **enumerations** instead of strings or integers to avoid errors and make code meanings explicit.
* **Meaningless Names**: All declared program elements must have meaningful names. (packages, classes, interfaces, methods, fields, parameters, local variables, etc.).
  * **Avoid one‑letter identifiers**
      *   Prefer `Person person = new Person();` over `Person p = new Person();`.
      *   **Exceptions:**
          *   When the abstraction is naturally one letter (e.g., coordinate `x`).
          *   Loop variables (`i`, `j`, `k`, …) in nested loops.
  *   **Do not use meaningless temporary names like `tmp` or `temp`**
        *   Use descriptive names even for short‑lived variables (e.g., during swaps).
  *   **Never distinguish identifiers by adding numbers**
        *   Use meaningful names such as `fromAccount` and `toAccount`, **not** `account1` and `account2`.
  *   **Do not fear long names**
        *   What matters is clarity.
        *   Example: `firstCustomerBuyingCampaignItem` is acceptable if it clearly explains the variable’s purpose.
* **Anonymous Values**:
  *   **Always give every value an explaining name**
      *   Unnamed values (literals) make code hard to understand and maintain.
      *   Named values are safer than comments because the compiler enforces correctness, while comments can become outdated.
  *   **Do not place raw numeric values directly in method calls**
      *   Example: avoid `connect(10000)`.
      *   Prefer meaningful named constants like:
          *   `private static final int MILLIS_PER_SECOND = 1000;`
          *   `private static final int CONNECT_TIMEOUT_SECS = 10;`
      *   Use them in expressions such as:
          *   `connect(CONNECT_TIMEOUT_SECS * MILLIS_PER_SECOND);`
  *   **This naming practice applies to all primitive values—including strings**
      *   Example: avoid raw string values like `"\""` when building file paths.
      *   Use a constant:
          *   `private static final String PATH_SEPARATOR = "\\";`
      *   Then write:
          *   `openFile(dirName + PATH_SEPARATOR + fileName);`
  *   **Sometimes a method provides the clearest name for a value**
      *   Especially useful in conditional expressions (`if` statements).
      *   Example: instead of checking `if (c == 10)` for Unix end‑of‑line, create:
          *   `boolean isUnixEol(char c)`
      *   Use it as:
          *   `if (isUnixEol(ch))`
* **Complicated Flow Control**: Poorly structured **if‑statements and loops** become confusing when deeply nested or filled with unnecessary flags, making the logic hard to understand.

## 6.5 Coding Case Study
Even with a complete upfront design, you should implement system operations one at a time in execution order so each part can be tested and understood as soon as it is written.

### The searchMatchingCar system operation
Implementing the first system operation requires careful handling of test data, search criteria, and output generation, since some design details—like where to store cars and how to display results—must be decided during coding.

DTO: final field variables.

### The registerCustomer system operation
Registering a customer is simple, but the CustomerDTO must be safely stored because its immutability ensures Rental cannot be affected by external changes.

DTOs stored in Rental are safe only as long as they remain pure data holders without logic, but if they gain business behavior they must be treated as entity objects rather than DTOs.

**A DTO may be used in the view, but an entity object may not.**

### The bookCar system operation
Implementing the bookCar operation exposes that CarRegistry **cannot store immutable DTOs** and must instead maintain mutable CarData objects that reflect a real datastore.

The matches method belongs in CarRegistry, not in CarDTO, because matching logic is business logic and should not be placed inside a DTO.

Once cars can be marked as booked, the search must exclude booked cars, so findCar is renamed findAvailableCar to reflect the new behavior.

Parameter order and constructor signatures were refactored for clearer logic and to support booking behavior, but the case of booking an already booked car still remains unresolved.

### The pay system operation
To generate the receipt text in the pay operation, Receipt creates a formatted string by querying complete objects from Rental — preferring to return whole objects rather than primitive data.

The total rental cost is simply taken from the car’s price for now, but cost calculation is delegated to CashPayment so it can later incorporate more data and logic as the system grows.

## 6.6 Common Mistakes
*   **Incomplete comments**
    *   Every public class, method, etc. must have a Javadoc comment.
    *   Method Javadocs must document parameters and return values using `@param` and `@return`.
    *   Even getters/setters should have brief comments to avoid accidentally missing documentation.

*   **Excessive comments**
    *   Only Javadoc comments should be used.
    *   If extra comments seem necessary, the code is likely too complex or has low cohesion.

*   **Comments written too late**
    *   Write comments together with the code (or before).
    *   Early commenting clarifies intent and ensures comments remain useful and accurate.
    *   Writing comments last makes it burdensome and more likely to be skipped.

*   **Design mistakes introduced during coding**
    *   Even if avoided in design, mistakes can reappear in implementation.
    *   Common issues include:
        *   Using primitives where objects should be used.
        *   Overuse of `static`.
        *   Placing input/output outside the view layer.

*   **Code smells from Section 6.4**
    *   Many problems come from **meaningless names** and **unnamed values**, which must be avoided.


# Chapter 7 Testing
## 7.1 Unit Tests and The JUnit Framework
Unit tests verify that individual pieces of code — typically single methods — work as intended by checking their behavior in isolation, as illustrated by testing the equals method in **JUnit**.

### Framework
A framework like JUnit provides **reusable functionality** and **controls program flow** — calling application code at the correct times — so developers don’t have to manage crucial tasks such as testing or security themselves.

Using a framework is beneficial because it is **well‑tested**, **widely supported**, **ensures correct flow control**, and **prevents bugs** by avoiding the need to write new code.

### JUnit
#### **JUnit Annotation Summary**

*   **`@Test`**
    *   Marks a method as a test method.
    *   The method will run when the test suite is executed.

*   **`@Disabled("reason")`**
    *   Skips the annotated test method.
    *   Useful for tests not yet implemented or temporarily turned off.

*   **`@BeforeEach`**
    *   Runs *before every test method*.
    *   Used to prepare or reset test state.

*   **`@AfterEach`**
    *   Runs *after every test method*.
    *   Used for cleanup tasks.

*   **`@BeforeAll`**
    *   Runs *once before all tests* in the class.
    *   Typically used for expensive setup (requires static method in JUnit 5 unless using special test instance settings).

*   **`@AfterAll`**
    *   Runs *once after all tests* in the class.
    *   Used for global cleanup (also usually static).

### **JUnit Assertion Summary**

*   **`fail("explanation")`**
    *   Forces the test to fail unconditionally.
    *   Useful at code locations that should never be reached.

*   **`assertTrue(condition, "explanation")`**
    *   Passes if the condition is **true**.

*   **`assertFalse(condition, "explanation")`**
    *   Passes if the condition is **false**.

*   **`assertEquals(expected, actual, "explanation")`**
    *   Passes if **expected** and **actual** are equal.
    *   Works for any Java type.

*   **`assertNull(object, "explanation")`**
    *   Passes if the object is **null**.

*   **`assertNotNull(object, "explanation")`**
    *   Passes if the object is **not null**.

Each test method runs with fresh setup and cleanup, and JUnit executes them by following a standard pattern: prepare objects, define the expected result, call the method under test, and compare expected with actual.

## 7.2 Unit Testing Best Practices
Start writing unit tests early — any test is better than none — and improve them over time rather than delaying until they become too burdensome to write.

* **Write lots of tests** — never rely on manual testing—and keep all tests (disable them instead of deleting) so your test suite continuously grows and remains useful over time.
* **Tests for every known bug** - When a bug is discovered, immediately create a failing test for it before fixing the code, ensuring every known failure condition is permanently covered by a test.
* **Test code should not be over‑designed** — feel free to write simpler, more flexible code and small hacks in tests, since they don’t require the same level of structure as production code.
* **Organization** - Place test classes in the same package as the code they test, but keep them in a parallel test directory so package‑private methods are accessible while production code and tests remain cleanly separated. Each class should have a corresponding test class named with “Test” appended, with test methods starting with “test” and containing only a few assertions to keep results clear and easy to interpret.
* **Testing takes time, but it is worth that time** - Writing tests takes time, but that investment quickly pays off by making future code changes safer, faster, and more confident thanks to easy, reliable verification.
* **Independent and self-evaluating** - Tests should run with a single command, evaluate themselves automatically, and never depend on execution order so they always produce the same results.
* **What to test?** - Test all externally accessible code (public, protected, and package‑private), skip private and trivial methods, and ensure tests cover branches, boundary cases, invalid inputs, and correct failure behavior.

## 7.3 When Testing is Difficult
* **Hard to give input**: Even when input comes from files, databases, or complex object structures, tests must create all needed inputs themselves and clean everything up afterward so they remain fully independent and repeatable.
* **Hard to read output**: Even when a method’s output is hard to access, you should not break encapsulation with new getters; instead, test observable effects through related operations to verify correct behavior.
* **Complex dependencies**: When higher‑layer classes depend on lower‑layer components, simply test through the entire stack and identify the lowest failing class, allowing bug localization without modifying the system under test.

You should **never** change the SUT just to make testing easier — there is always a way to test without harming its design.

## 7.4 Unit Testing Case Study

NetBeans Support for Unit Testing

IntelliJ Support for Unit Testing

***Maven Support for Unit Testing*** (It is used by us)

### Writing the Tests

Tests should be written bottom‑up, starting with classes that have no dependencies and moving upward, so each new test can run immediately and confirm that the tested class works as intended.

This unusual workflow — writing the whole program before writing tests—is only used here to mix theory and practice, whereas normally tests are written **before or immediately** after the methods they verify.

Because the entire program was written before testing, the first step is to find **a class with no dependencies** — making Amount the natural starting point for unit tests. (DM or PD should be checked)

### The First Tested Class, Amount

Only **public, protected, and package‑private methods** need testing, while private methods and trivial constructors (like those in Amount) are skipped since they contain no logic.

Testing the ***equals*** method requires covering all branches — **null input**, **wrong type**, **equal amounts**, and **unequal amounts** — ensuring every execution path in the method is exercised.

Although edge cases like null are already covered, an extra test is added for comparing Amount objects representing zero, even though no special behavior is expected for other extreme numeric values.

The equals method has no illegal parameter values or required preconditions, so once all parameter cases are tested and pass, testing is complete.

***

The remaining Amount methods ***(minus, plus, and toString)*** do not depend on each other, so their tests can be written in any order, starting with minus.

Since ***minus*** has only one execution path and unchecked overflow, a few straightforward tests — verifying positive, negative, and zero results — are enough, especially now that equals is confirmed to work correctly.

The tests for ***plus*** mirror those for minus, and toString is tested with positive, negative, and zero values — concluding the fully successful 15‑test suite for the Amount class.

### The First Problematic Test, a void Method

Testing ***CarRegistry*** requires checking the effect of ***setBookedStateOfCar*** (a void method) through ***findAvailableCar***, and adding an equals method to CarDTO becomes necessary — and justified — because it improves the class design rather than harming it.

(CustomerRegistry: save is a void method)

### More Difficult Tests

***CustomerDTO, AddressDTO, and DrivingLicenseDTO*** require **no** tests because they only store values, while ***CashRegister and RentalRegistry*** cannot yet be meaningfully tested due to incomplete functionality, so their methods can only be invoked without verification.

Since no fully independent classes remain, tests must now be written for all remaining model and integration classes, but missing error handling makes it impossible to meaningfully test invalid inputs until that functionality is added later.

Even complex methods like createReceiptString can be fully tested with enough setup work, as long as the expected result is constructed independently so the test cannot share the same bug as the SUT.

### Testing User Interface

The void method printReceipt can still be tested by redirecting System.out to an in‑memory stream, allowing the test to compare the captured output with the expected result.

This is the only user‑interface test included, but command‑line input can be tested by redirecting System.in, and graphical or web UIs can also be tested using specialized UI‑testing frameworks.

### The last classes, Controller and Main

Testing the final classes revealed missing read operations in the design, leading to adding methods like ***findRentalByCustomerName*** and ***getRentingCustomer*** — changes that improve the system rather than harm it, since stored data must be retrievable to be testable and meaningful.

Testing the ***controller*** is complex because it sits high in the layer stack, requiring extensive setup and indirect verification — such as checking printed receipts — since private or package ‑ private methods cannot be exposed just to simplify testing.

Testing ***Main*** is difficult because it only creates objects and no UI exists yet, but minimal verification is still possible by checking for expected output or inspecting the program state.

## 7.5 Common Mistakes

* **Too few tests**: Having too few tests is the most serious mistake — aim to cover all branches, as well as extreme and illegal parameter values, to thoroughly verify behavior.
* **Too many assertions in the same test method**: Place as few assertions as possible in each test method—ideally one—because clearer, smaller tests are easier to name and understand, and they allow more assertions to run before a failure stops execution.
* **Not self-evaluating**: Tests must evaluate results with assertions — not with if statements or manual inspection — to remain fully self‑evaluating and reliable.
* **Producing output**: A test should never print to System.out, because output from many tests quickly becomes confusing and hides real results.
* **Worsen SUT design**: **Never compromise or weaken the SUT’s design** just to make testing easier — there is almost always a way to test it without modifying the SUT.

# Chapter 8 Handling Failure
## 8.2 Exception Handling Best Practices
* **Always use exceptions for error handling**: Never use return values to signal errors — exceptions provide clearer semantics, avoid confusion with valid results, keep error information separate from normal flow, and maintain high cohesion.
* **Useexceptionsonlyforerrorhandling**: Exceptions should never be required for successful execution, and when they are, it signals a flawed public interface — adding methods like hasNext avoids forcing callers to use exceptions for normal control flow.
* **Checked or unchecked exception**: Checked exceptions should signal business‑rule violations like booking an already‑booked car, while unchecked exceptions should be used for unrecoverable technical failures where the client cannot meaningfully respond.
* **Name the exception after the error condition**: Exception classes should have clear, descriptive names ending with Exception, but not be overly specific—use fewer, more general exception types and place detailed context in the exception message to balance clarity with a manageable interface. [For the book‑car operation, the already‑booked case should use a clearly named checked exception like ***AlreadyBookedException***, while datastore failures should use a more general unchecked ***CarRegistryException***, balancing clarity with a manageable number of exception classes.]
  * public class ***AlreadyBookedException*** extends Exception
  * public class ***CarRegistryException*** extends RuntimeException
* **Include information about the error condition**: Exception objects should store both the data describing the error condition and a developer‑focused message, ensuring useful debugging information without exposing internal details to the user interface. [For AlreadyBookedException, include detailed data such as the relevant CarDTO and a clear message like “Unable to book <regNo>: already booked,” while CarRegistryException can only provide a general datastore‑failure message because no more specific information is available.]
* **Use functionality provided in java.lang.Exception**: Custom exception classes should extend Exception, pass messages to the superclass constructor, and encapsulate fixed messages when appropriate—storing only the varying data (like the specific car) in fields to maintain cohesion and clarity.
* **Use the correct abstraction level for exceptions**: Low‑level technical exceptions (like database errors) should not propagate to the user interface; instead, they should be caught and wrapped in a higher‑level, user‑safe exception, preserving the original cause internally via exception chaining. However Exceptions should propagate to higher layers only when they are meaningful there; otherwise, they should be caught and handled earlier rather than being intercepted at every layer.
  * Since propagating ***CarRegistryException*** up to the controller introduces no new dependency and simplifies the design, the method is updated to throw this exception, rename the setter to rentCar for clarity, and add supporting methods like isBooked and getCarByRegNo to maintain proper cohesion and behavior.
  * ***CarRegistryException*** must be caught in the controller—never allowed to reach the view—so the controller wraps it in a high‑level OperationFailedException that informs the view of the failure without exposing integration‑layer details.
* **Write Javadoc comments for all exceptions**: Always document every thrown exception in Javadoc using **@throws**, clearly stating the condition under which it is thrown, both to guide users of the method and to force the author to precisely define the exception’s purpose.
  * CarRegistry methods throw CarRegistryException for datastore failures or when an expected existing car cannot be found—while methods that simply search (like findAvailableCar) do not throw exceptions when no match exists—so Javadoc @throws tags must clearly document these conditions even though the exception is unchecked.
  * Since rentCar allows CarRegistryException to propagate and also throws AlreadyBookedException, its Javadoc must document both exceptions using @throws to clearly describe the conditions under which each is thrown.