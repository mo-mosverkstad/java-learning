# Chapter 4 Analysis

Domain model (DM) is performed by UML class diagram

System sequence diagram is performed by UML sequence diagram

## 4.1 Domain Model

> “The domain model represents real‑world entities, not program classes.”

A **UML class diagram** is used to construct the **domain model**, but the elements in the DM represent things that exist in reality, not **classes** in an object oriented program. Therefore, it might be better to call them **entities** instead of classes.

The DM is a very good tool for discussions about the program that is being developed. It can ensure that all parties (**developers**, **clients**, **users**, etc) share a **common view** of the tasks of the program. All parties develop a **common nomenclature**.

The software should be quite similar to the DM to beanappropriate model of the reality. This will also make sure that **class names** in the software means something to domain experts.

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
So the association between **Receptionist** and **RepairOrder** should be removed, similar as the association between **Technician** and **ProposedRepairTask**

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

System <-- createDiagnosticReport(foundRepairOrder, diagnosticDescription) -- Technician

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
Customer -> Bike vs CustomerDTO

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
1. Step 1, Use the Patterns MVC and Layer, the design looks as in ***figure 5.24***.
2. Step 2, Design One System Operation at a Time,
   * The view is not designed here, instead the view package contains a single class, View, which is a placeholder for a real view, that certainly would consist of more classes.
   * The system operations are designed in the order they are executed according to the SSD, ***figure 5.22***.
   * The first operation in the SSD is searchMatchingCar. The first step is to create an **interaction diagram**. 
3. Step 3, Strive for encapsulation with a small, well-defined public interface, high cohesion and low coupling
   * Thequestion, which is the type of the parameter searchedCar and the return value foundCar, is the first of a large number of design decisions. The answer shall be guided by the concepts encapsulation, cohesion and coupling.
   * The fact that the **same name** is used implies that it is in fact the very same object in all three places, which is important information for the reader.
   * Data can not appear out of nothing, since searchedCar is a parameter in method call two, it must be clear from where this object comes.
   *  For now, we just choose the simplest solution, namely to let Car be a DTO, place it in dbhandler, and not add an entity. This decision might have to be changed later.
   *  Figure 5.27 Call to dbhandler layer is added.
   *  Figure 5.28 The start sequence in the main method.
4. Step 4, Maintain a Class Diagram With All Classes.  Note that it is **not mandatory** to include all attributes, methods and references if they do not add any important information, but only obscure the diagram. For example, it is common not to include references to **DTOs**, since they are used in many different layers and are considered as data types.

Before leaving searchMatchingCar, we evaluate it according to the criteria encapsula tion, cohesion and coupling.
* To start with encapsulation, all methods are public. ... page 79-80

printer page 87-88 -> create a new layer or to extend (and rename) dbhandler to handle interaction with any other system, so far databases and printers.

Why is the CashRegister object created by Controller, when all other objects are created by main and sent to Controller? This is a **trade-off** between two contradicting arguments. On one hand, coupling is lowered if main is not associated to all other objects created during startup. On the other hand, cohesion of the Controller constructor is increased if it does not create loads of other objects, besides controller.


Evaluate the Completed RentCar Design page 89.

Page 90: RetalRegistry: saveRental(Rental).

https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax



----
* TelephoneNumber: convert to string
* module layer: RepairOrder, DiagnosticOrder, ProposedRepairTask.
* intergration layer: CustomerRegistry (CustomerDTO), RepairOrderRegistry (saveRepairOrder, getRepairOrder)


----