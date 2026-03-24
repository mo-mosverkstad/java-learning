# ✅ **tasks-seminar2.pdf + assessment-criteria-seminar2.pdf**

***

# **Page 1 — “Seminar 2, Design”**

**Seminar 2, Design**  
*Object-Oriented Design, IV1350*

***

## **Intended Learning Outcomes**

This seminar concerns the learning outcomes *develop an object-oriented program by applying established guidelines for object-oriented architecture and design* and *discuss the quality of a program referring to established guidelines for object-oriented architecture and design.*

***

## **Goal**

*   Practice basic object-oriented design

***

## **Literature**

*   Section 4.1 and chapter 5 in *A First Course in Object Oriented Development.*

***

## **Grading**

There are three possible grades:

**Fail (0 points)**  
To pass the course you must pass all four seminars. If you fail this seminar you have to report it again at the end of the course, at the fifth seminar.

**1 point**  
Active participation in seminar discussions. Written solution submitted in Canvas proving that you can, with minor defects, follow a method for designing an object-oriented program.

**2 points**  
Active participation in seminar discussions. Written solution submitted in Canvas proving that you can, without defects, use cohesion, coupling and encapsulation to design an object-oriented program. In the written solution you must also motivate how these principles are used.

***

# **Page 2 — “Task”**

## **Task**

Design a program that can handle all parts, including alternative flows, of the *Repair Electric Bike* scenario, specified in the document with tasks for seminar one. Remember to also design the startup, that is, the main method. The task is only to design the program, you’re not required to write any code and you shall not include any code in the report. Still, if you find designing hard, it’s a good idea to code some part of it. The meaning of the diagrams becomes much clearer when implemented in code.

The solution must meet the following requirements:

*   The design must have high cohesion, low coupling and good encapsulation with a well-designed public interface.
*   The solution must be divided into layers, as specified by the *MVC* and *Layer* patterns.
*   You are not required to follow the domain model and system sequence diagram you made for seminar one; you may change them now if you find flaws in them. However, do keep your existing analysis model (DM and SSD) unless there are obvious reasons to change it.
*   You do not have to design the view, it can be replaced with a class called *View*.
*   You do not have to design the data layer, it can be omitted.
*   In the *Method* chapter of your report, explain how you worked and give examples of how you reasoned when creating the design.
*   In the *Result* chapter of your report, include and briefly explain interaction diagram(s) (that is, either communication or sequence diagrams) of the entire design. There must be interaction diagram(s) describing all functionality of the *Repair Electric Bike* scenario, both the basic and alternative flows, and also the main method. The chapter must also contain, and briefly explain, a class diagram showing all classes in the interaction diagram(s).
*   In the *Discussion* chapter of your report, evaluate your design using applicable assessment criteria from the document *assessment-criteria-seminar2.pdf*, which is available on the *Seminar Tasks* page in Canvas.

As a preparation for seminar three, which takes a lot of time to solve, it’s certainly a good idea to already now think about how the methods you design can be implemented and tested. This is however just an advice, not a requirement, and shall not be covered in the report.

***

# **Page 3 — “Assessment Criteria For Seminar 2, Design”**

**Assessment Criteria For Seminar 2, Design**  
*Object-Oriented Design, IV1350*

***

The assessed person’s score will **not** be affected by your comments. *Try to give concrete suggestions. Motivate your comments, give examples, try not to write just “yes” or “no”.* Make sure to discuss and/or ask the teacher about questions regarding your own or the assessed solution(s).

*   Is it easy to understand the design?
*   Are the MVC and Layer patterns used correctly? Are there enough layers? Enough packages? Enough classes? Does the controller handle any logic (it should not)? Is there view-related code in the model (there should not be any)?
*   Is there low coupling, high cohesion and good encapsulation?
*   Is the use of low coupling, high cohesion and encapsulation explained in the report?
*   Are parameters, return values and types correctly specified? Is it clear from where values come?
*   Are objects used instead of primitive data as much as possible?
*   Are there static methods or attributes? If so, is that a mistake?
*   Objects shall be kept intact as much as possible. Is that the case, or do objects hand out their attributes?
*   Are Java naming conventions followed?
*   Is the method and result explained in the report? Is there a discussion? Is the discussion relevant?
