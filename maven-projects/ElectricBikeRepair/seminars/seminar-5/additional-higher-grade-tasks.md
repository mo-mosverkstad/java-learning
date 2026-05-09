Below is a **faithful transcription of the text from the attached images**, preserving **content, structure, headings, bullet points, and code formatting** as closely as possible.  
I have **not added, removed, or rephrased anything**, only reformatted it into clean, copy‑ready text.

***

## Additional Higher Grade Tasks

**Object-Oriented Design, IV1350**

These tasks are only for deciding the final grade. The requirement to pass the course, with grade E, is to pass all seminars.

***

### Grading

Maximum score from these tasks is **3**, which is added to the higher grade score from the seminar tasks. The total max higher grade score is **3** from these tasks and **4** from the seminar tasks, which makes a total of **7**. Grade limits are as follows.

*   **6 higher grade points** give grade **A**
*   **5 higher grade points** give grade **B**
*   **3 higher grade points** give grade **C**
*   **1 higher grade points** give grade **D**
*   **No higher grade points** are required for grade **E**

***

## Task 1, Inheritance (1p)

In your *Repair Electric Bike* program, use the *Template Method* design pattern for the observers you wrote in task 2a in seminar 4. The template class you create must have the structure described by the pseudocode below. You are allowed to change class, variable, parameter and method names, and also to add classes, variables, parameters and methods. **The structure with the try-catch block and the abstract methods must however remain unchanged.** The grade is not affected no matter how simple or advanced the view is.

    1 // This is the method defined in the observer interface.
    2 public void repairOrderUpdated(RepairOrderDTO repairOrder) {
    3     handleRepairOrderUpdate();
    4 }
    5
    6 private void handleRepairOrderUpdate() {
    7     try {
    8         doHandleRepairOrderUpdate();
    9     } catch (Exception e) {
    10        handleErrors(e);
    11    }
    12 }
    13
    14 protected abstract void doHandleRepairOrderUpdate()
    15     throws Exception;
    16
    17 protected abstract void handleErrors(Exception e);

*Listing 1: Pseudocode for the template class*

*   In the **Method** chapter of your report, explain how you worked and how you reasoned when implementing the template method pattern.
*   In the **Result** chapter of your report, briefly explain source code for all classes you changed when implementing the template method pattern. Include links to your git repository, and make sure the repository is public. Also include a printout of a sample run.
*   In the **Discussion** chapter of your report, thoroughly motivate that your code is a correct implementation of the template method pattern. Also thoroughly explain the benefits of using template method in your application. Alternatively, if you can’t see any benefits, thoroughly motivate why that is the case.

***

## Task 2, Inheritance vs Composition (1p)

This task relates to the section *Second Reason to Prefer Composition: Inheritance Breaks Encapsulation* in chapter 9.3 in *A First Course in Object-Oriented Development*. Make sure to understand that section before proceeding. The section describes how a class can be adapted using inheritance, and how it can be adapted using composition.

The task is now to adapt any class in the java libraries from Oracle. You are free to choose any class to adapt, **except a list**, and also free to choose how you adapt it. Write one new class that adapts using inheritance, and another new class that adapts using composition, as described in the course book section mentioned above. Also write a main method which instantiates your new classes and executes the adaptions. The program must include printouts illustrating how your classes work. A suggestion, if it’s hard to find a class to adapt, is to choose `java.util.Random`.

*   In the **Method** chapter of your report, explain how you worked and how you reasoned when writing the adapting classes.
*   In the **Result** chapter of your report, briefly explain the source code. Include links to your git repository, and make sure the repository is public. Also include a printout of a sample run.
*   In the **Discussion** chapter of your report, thoroughly compare adaption using inheritance and adaption using composition. There are comparison criteria in section 9.3 in the course book, and it is fine to use other criteria as well. Also draw a conclusion, is inheritance or composition preferred or are they equal? You are of course not required to make the same conclusion as is made in the book.

***

## Task 3, Testing Output (1p)

Write unit tests for all methods that print to `System.out` in your *Repair Electric Bike* program. This must include at least the main method, the View class and the repair order printout. If you have additional methods printing to `System.out`, also those must be tested. The tests of the View class must test all printouts in that class, but you only have to test printout containing information. It’s not required to test printouts which exist only to make the view more readable.

*   In the **Method** chapter of your report, explain how you worked and how you reasoned when writing the unit tests.
*   In the **Result** chapter of your report, tell which tests you have written for this task and briefly explain the tests. Include links to your git repository, and make sure the repository is public.
*   In the **Discussion** chapter of your report, thoroughly evaluate your unit tests using assessment criteria related to testing from the document *assessment-criteria-seminar3.pdf*, which is available on the Seminar Tasks page in Canvas.

***

# **Page 4 — “Assessment Criteria For Seminar 3, Implementation”**

**Assessment Criteria For Seminar 3, Implementation**  
*Object-Oriented Design, IV1350*

***

The assessed person’s score will **not** be affected by your comments. *Try to give concrete suggestions. Motivate your comments, give examples, try not to write just “yes” or “no”. You are not required to evaluate all bullets below, you may choose those you find most interesting.* Make sure to discuss and/or ask the teacher about questions regarding your own or the assessed solution(s).

*   Is the code easily understood? Do all declarations have explaining names? Are code conventions followed?
*   Is there duplicated code?
*   Are there methods or classes that are too long?
*   Are primitive data or static members used instead of objects?
*   Are there methods with unnecessarily long parameter lists?
*   Is there one comment per public declaration and no comments inside methods?
*   Are the MVC and Layer patterns still used correctly?
*   Is there still low coupling, high cohesion and good encapsulation?
*   Is a framework, like JUnit, used for the tests?
*   Are all tests self-evaluating, that is, do they print an informative message if a test fails, and do they print nothing if a test passes?
*   Are the tests placed in the same package as the SUT, but in a different directory?
*   Is there one test class per tested class?
*   Are all branches of if-statements tested?
*   Do tests pass all interesting parameter values to the tested methods?
*   If the assessed person wrote unit tests for all classes, are the tests complete?
*   Are there tests for all public and package private methods (except simple setters/getters)?
*   If the assessed person wrote unit tests for only two classes, are the tests meaningful?
*   Is the use of programming best practices explained in the report?
*   Is the method and result explained in the report? Is there a discussion? Is the discussion relevant?



