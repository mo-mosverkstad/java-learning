# ✅ **tasks-seminar1.pdf + assessment-criteria-seminar1.pdf**

***

## **Page 1 — “Seminar 1, Analysis”**

**Seminar 1, Analysis**  
*Object-Oriented Design, IV1350*

### **Intended Learning Outcomes**

This seminar concerns the learning outcome *interpret and clarify a given specification by applying established guidelines for analysis.*

### **Goal**

*   Get acquainted with a UML modeling tool.
*   Practice creating a domain model.
*   Practice creating a system sequence diagram.

### **Literature**

*   Chapter four in *A First Course in Object Oriented Development.*

### **Grading**

There are three possible grades:

**Fail (0 points)**  
To pass the course you must pass all four seminars. If you fail this seminar you have to report it again at the end of the course, at the fifth seminar.

**1 point**  
Active participation in seminar discussions. Written report submitted in Canvas including domain model and system sequence diagram with minor defects.

**2 points**  
Active participation in seminar discussions. Written report submitted in Canvas including domain model and system sequence diagram without defects. In the written report you must also explain and discuss the result and explain the method.

***

## **Page 2 — “Tasks”**

### **Tasks**

### **Task 1**

*   Make a domain model for an electric bike repair workshop. In the current iteration we are implementing both the basic flow and the alternative flows described in the *Repair Electric Bike* requirements specification below. Remember to include also what's written under *Business Rules and Clarifications* below.
*   Your domain model shall **not** contain a class called *Program*, *System* or something else that represents the entire program.
*   In the *Method* chapter of your report, explain how you used a category list and noun identification to identify classes. Also explain how you decided on attributes and associations.
*   In the *Result* chapter of your report, show and briefly explain the domain model.
*   In the *Discussion* chapter of your report, evaluate your domain model using applicable assessment criteria from the document *assessment-criteria-seminar1.pdf*, which is available on the *Seminar Tasks* page in Canvas.

### **Task 2**

*   Draw a system sequence diagram (SSD) illustrating basic and alternative flows of *Repair Electric Bike*, whose requirements specification follows below.
*   In the *Method* chapter of your report, explain how you worked when developing the SSD.
*   In the *Result* chapter of your report, show and briefly explain the SSD.
*   In the *Discussion* chapter of your report, evaluate your SSD using applicable assessment criteria from the document *assessment-criteria-seminar1.pdf*, which is available on the *Seminar Tasks* page in Canvas.

***

## **Page 3 — “Requirements Specification (Page 1)”**

# **Requirements Specification for Repair Electric Bike**

The scenarios below describe basic flow and alternative flows for servicing bikes, at an electric bike repair workshop, figure 1.

*(Image of a bicycle repair workshop — omitted in OCR)*

**Figure 1: A bike repair workshop.**

***

### **Basic Flow**

1.  Customer arrives at workshop with bike to repair.
2.  Receptionist greets customer and asks for customer’s phone number.
3.  Customer tells phone number.
4.  Receptionist enters customer’s phone number.
5.  System searches customer registry for customer details (name and email address), and for details about the customer’s bike (brand, model and serial number).
6.  System presents customer details and details of the customer's bike. This data is known since the customer has consulted the workshop previously.
7.  Receptionist asks customer if details of customer and bike are correct.
8.  Customer confirms that details are correct.
9.  Receptionist asks customer for a description of the problem with the bike.

***

## **Page 4 — “Requirements Specification (Page 2)”**

10. Receptionist enters customer’s description.
11. System creates a repair order containing customer details, bike details, problem description and date.
12. Customer waits while a technician inspects the bike.
13. Technician asks system for repair order.
14. System presents repair order.
15. Technician performs diagnostic.
16. Technician enters diagnostic report and proposed repair tasks.
17. System updates repair order, by adding diagnostic report and proposed repair tasks.
18. Receptionist informs customer about diagnostic report, proposed repair tasks, cost for each proposed repair task, and total cost.
19. Customer accepts proposed repair tasks and cost.
20. Receptionist registers that customer accepted repair order.
21. System prints repair order. The printout contains all repair order data, including estimation of when reparation will be completed.
22. Receptionist gives the printed repair order to customer.
23. Customer leaves.

***

### **Alternative Flows**

#### **5a. Unknown phone number**

1.  System fails to find customer details.
2.  System tells receptionist that phone number doesn't exist in customer registry.

***

#### **19a. Repair order not accepted**

1.  Customer doesn't accept repair order, and doesn't want to consult the bike repair workshop.
2.  Receptionist specifies that repair order is rejected.
3.  System registers that repair order is cancelled, but doesn't delete it from the repair order registry.

***

## **Page 5 — “Business Rules and Clarifications”**

# **Business Rules and Clarifications**

### **Customer Registry**

The customer registry contains name, phone number and email address of all customers who have ever consulted the workshop. The registry also contains brand, model and serial number of customer's bikes.

### **Repair Order**

A repair order contains customer and bike information, date, customer's problem description, diagnostic report of the bike, repair tasks and cost for each repair task. It also contains information about its own state. A repair order can be in the following states:

*   Newly created (no diagnostic report or repair tasks)
*   Ready for approval (a technician has entered diagnostic report and repair tasks)
*   Rejected (the customer didn’t want to do the proposed repair tasks)
*   Accepted (the customer accepted the proposed repair tasks)
*   Completed (the reparation has been done, but the customer hasn’t payed yet)
*   Payed (the customer has payed)

The last two states are not included in the scenario above.

### **Repair Order Registry**

The repair order registry contains all repair orders ever created. Note that repair orders are never deleted.

***

## **Page 6 — “Assessment Criteria for Seminar 1”**

# **Assessment Criteria For Seminar 1, Analysis**

*Object-Oriented Design, IV1350*

The assessed person’s score will **not** be affected by your comments. *Try to give concrete suggestions. Motivate your comments, give examples, try not to write just “yes” or “no”.* Make sure to discuss and/or ask the teacher about questions regarding your own or the assessed solution(s).

*   Typical mistakes are to create a “programmatic DM” or a “naïve DM”. Is the DM either of those?
*   Can you understand the DM? Is it a correct description of the problem domain?
*   Does the DM have a reasonable number of classes? Are important classes missing? Are there irrelevant classes?
*   Are there “spider-in-the-web” classes?
*   Do you agree with the choices between class and attribute?
*   Is there a reasonable number of associations? Do you understand them? Are important associations missing? Are there classes without associations? If so, is that a mistake?
*   Are naming conventions followed in DM and SSD?
*   Is UML used correctly in DM and SSD?
*   Are the correct objects included in the SSD?
*   Are the correct operations and return values included in the SSD?
*   Is the method and result explained in the report? Is there a discussion? Is the discussion relevant?
