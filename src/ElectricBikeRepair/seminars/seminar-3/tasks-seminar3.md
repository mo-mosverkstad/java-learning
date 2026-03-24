# ✅ **tasks-seminar3.pdf + assessment-criteria-seminar3.pdf**

***

# **Page 1 — “Seminar 3, Implementation”**

**Seminar 3, Implementation**  
*Object-Oriented Design, IV1350*

***

## **Intended Learning Outcomes**

This seminar concerns the learning outcomes *develop an object-oriented program by applying established guidelines for object-oriented programming* and *discuss the quality of a program referring to established guidelines for object-oriented programming.*

***

## **Goal**

*   Practice translating design model to source code.
*   Practice writing unit tests.

***

## **Literature**

*   Chapters six and seven in *A First Course in Object Oriented Development.*

***

## **Grading**

There are three possible grades:

**Fail (0 points)**  
To pass the course you must pass all four seminars. If you fail this seminar you have to report it again at the end of the course, at the fifth seminar.

**1 point**  
Active participation in seminar discussions. Written solution submitted in Canvas proving that you can, with minor defects, apply at least two best practices of object-oriented programming. It shall also prove that you can write a basic unit test.

**2 points**  
Active participation in seminar discussions. Written solution submitted in Canvas proving that you can, without defects, apply several best practices of object-oriented programming. In the written solution you must also satisfactorily explain and motivate their use. It shall also prove that you understand how to write and use unit tests.

***

# **Page 2 — “Tasks (page 1)”**

## **Tasks**

There are two tasks, task one is to write the program and task two is to write tests for the program. As can be seen in task two, to pass (1p) it’s sufficient to write tests for two classes. Still, you’re encouraged to try to write more tests, since tests are important.

***

## **Task 1**

Write a program implementing the basic flow and the startup for *Repair Electric Bike*, which you designed in seminar two. **You do not have to program any alternative flows or add any other functionality.** You are also not required to code the view, you may replace the user interface with one single class, *View*, which contains hard-coded calls to the controller. Neither is there any requirement on databases or external systems. Instead of a database, you can just store data in the objects in the integration layer, which should have been responsible for calling the database if there had been one. The external systems can simply be omitted, but there must be objects responsible for calling them.

The solution must meet the following requirements:

*   The code shall be compilable and executable. You have to program in Java, since everyone must be able to understand your solution at the seminar. Also, don’t use exceptions now, since that's a topic of seminar four, not understood by everyone now at seminar three.

*   **If the user interface is replaced with hard-coded method calls, the class calling the controller must print *everything* that is returned by the controller. Also the repair order printout must be printed to `System.out`.**

*   Your code must follow all guidelines presented in chapter six in the textbook. Regarding comments this means there must be one comment for each public declaration.

*   Try to follow the design from seminar two, but it is perfectly OK to change the design if you discover flaws. The solution must however have high cohesion, low coupling and good encapsulation with a well-defined public interface.

*   In the *Method* chapter of your report, explain how you worked and how you reasoned when writing the program.

*   In the *Result* chapter of your report, briefly explain the program. Include links to your git repository, and make sure the repository is public. **Also include a printout of a sample run.**

*   In the *Discussion* chapter of your report, evaluate your program using applicable assessment criteria from the document *assessment-criteria-seminar3.pdf*, which is available on the *Seminar Tasks* page in Canvas.

***

# **Page 3 — “Tasks (page 2)”**

## **Task 2**

Write tests for your program.

*   **To pass (1 point)** you must write unit tests for two classes. Try to find something more interesting to test than get/set methods. You have to write new test classes, you’re not allowed to use the given tests, that is *AmountTest* from the textbook and *MainTest* from the lecture *Practice Programming and Unit Testing.*

*   **To pass with distinction (2 points)** you must write unit tests for all classes in the layers *controller*, *model*, and *integration*, except classes that have just getters and constructors that only store values. It is also not required to test that output to `System.out` is correct, just ignore testing methods that only produce output to `System.out`.

*   In the *Method* chapter of your report, explain how you worked and how you reasoned when writing the unit tests. Explain how you chose which tests to write.

*   In the *Result* chapter of your report, briefly explain the tests. Include links to your git repository, and make sure the repository is public.

*   In the *Discussion* chapter of your report, evaluate your unit tests using applicable assessment criteria from the document *assessment-criteria-seminar3.pdf*, which is available on the *Seminar Tasks* page in Canvas.

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
