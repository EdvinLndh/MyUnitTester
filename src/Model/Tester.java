package Model;

import se.umu.cs.unittest.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: Runs the tests of the given class.
 * Author: Edvin Lindholm
 * Cas: edli0007
 * C-user: c19elm
 * Version: 1.0
 * Date: 2020-11-15
 */

public class Tester {

    private final Class<?> testClass;

    private Object testObject;

    private int success, fail, failExc;

    private Method setUp;
    private Method tearDown;

    /**
     * @param testClass String containing name of class to instantiate.
     */
    public Tester(String testClass) throws ClassNotFoundException,
            NoClassDefFoundError {
        super();
        this.testClass = Class.forName(testClass);

        success = 0;
        fail = 0;
        failExc = 0;

        setUp = null;
        tearDown = null;
    }

    /**
     * @return Methods of the given testclass.
     */
    public Method[] getMethods() {
        return testClass.getMethods();
    }

    /**
     * Runs given test.
     *
     * @param test Method to test.
     * @return Result of test in string format.
     * @throws InvocationTargetException Couldn't invoke methods.
     * @throws IllegalAccessException    Couldn't access class.
     */
    public String runTest(Method test) throws InvocationTargetException,
            IllegalAccessException {
        // If class had a setup method, run before test.
        String resultString;
        String methodName = test.getName();
        try {
            if (setUp != null) setUp.invoke(testObject);

            // If method doesn't start with test, no need to run it.
            if(!checkTest(methodName, test)) {
                return "";
            }

            // Run test and check result.
            test.setAccessible(true);
            Object result = test.invoke(testObject);

            if ((boolean) result) {
                // Test successful!
                success++;
                resultString = methodName + ": SUCCESS" + '\n';
            } else {
                // Test unsuccessful.
                fail++;
                resultString = methodName + ": FAIL" + '\n';
            }

            // Test unsuccessful with exception.
        } catch (InvocationTargetException e) {
            failExc++;
            resultString = methodName + ": FAIL Generated a "
                    + e.getTargetException() + '\n';
        }
        catch (IllegalAccessException e ) {
            resultString = "Method " + methodName  + " is not accessible. \n";
        }
        // If teardown function is defined, run it after each test.
        if (tearDown != null) tearDown.invoke(testObject);

        return resultString;
    }

    /**
     * Checks if test method is according to standard.
     *
     * @param methodName Name of method.
     * @param test Test method
     * @return Boolean, true if okay test method.
     */
    private boolean checkTest(String methodName, Method test) {
        return methodName.startsWith("test")
                && test.getGenericReturnType() == boolean.class
                && test.getParameterCount() == 0;
    }

    /**
     * Asserts that a class is according to specification.
     *
     * @return true, if class was according to specification, otherwise false.
     */
    public String assertClass() {
        try {
            if (this.testClass == null) {
                return "Couldn't find class, try again. \n";
            }
            // Check if class is an interface.
            else if (this.testClass.isInterface()) {
                // No object can be created.
                return "Class is an interface, try again. \n";
            } else if (!TestClass.class.isAssignableFrom(this.testClass)) {
                return "Class does not implement TestClass interface,"
                        + " try again. \n";
            } else {
                // Create object, and run tests.
                testObject =
                        this.testClass.getDeclaredConstructor().newInstance();
                return null;
            }
        } catch (NoSuchMethodException
                | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            return (e.getMessage() + " try again. \n");
        }

    }

    /**
     * Checks if class has setUp and tearDown methods, if they do,
     * the according private variables gets updated.
     *
     * @param testArray Array with test methods to go through.
     */
    public void getSetUpAndTearDown(Method[] testArray) {
        // Loop through methods and see if setUp or tearDown methods exist.
        for (Method method : testArray) {
            String methodName = method.getName();
            if (methodName.equals("setUp")) {
                setUp = method;
            } else if (methodName.equals("tearDown")) {
                tearDown = method;
            }
        }
    }

    /**
     * Gives conclusion to the tested class.
     */
    public String concludeTest() {

        return ('\n' + "Conclusion:"
                + '\n'
                + success + " tests succeeded."
                + '\n'
                + fail + " tests failed."
                + '\n'
                + failExc + " tests failed with an exception \n"
                + "------------------------------------------------"
                + "\n");
    }

    /**
     * @return Amount of successful tests.
     */
    public int getSuccess() {
        return success;
    }

    /**
     * @return Amount of failed tests.
     */
    public int getFail() {
        return fail;
    }

    /**
     * @return Amount of tests failed with exception.
     */
    public int getFailExc() {
        return failExc;
    }

    /**
     * @return setUp method.
     */
    public Method getSetUp() {
        return setUp;
    }

    /**
     * @return tearDown method.
     */
    public Method getTearDown() {
        return tearDown;
    }
}
