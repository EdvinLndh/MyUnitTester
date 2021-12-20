import Model.Tester;
import org.junit.jupiter.api.Test;
import se.umu.cs.unittest.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class TesterTest {

    TestClass class1 = new TestClass() {

    };

    @Test
    void runTest() throws InvocationTargetException,
            IllegalAccessException, ClassNotFoundException {
        // Functional test.
        Tester test2 = new Tester("Tests.Test1");
        test2.assertClass();
        Method[] methodList = test2.getMethods();
        test2.getSetUpAndTearDown(methodList);

        for(Method m : methodList) {
            String mName = m.getName();
            String result;
            switch (mName) {
                case "testIncrement":
                    result = test2.runTest(m);
                    assertEquals(m.getName() + ": SUCCESS" + '\n', result);

                    break;
                case "testFailing":
                    result = test2.runTest(m);
                    assertEquals(m.getName() + ": FAIL" + '\n', result);

                    break;
                case "testFailingByException":
                    try {
                        test2.runTest(m);

                    } catch (IllegalAccessException e) {
                        result = test2.runTest(m);
                        assertEquals(m.getName() + ": FAIL Generated a "
                                + e.toString() + '\n', result);
                    }
                    break;
            }
        }
    }

    @Test
    void assertClass() throws ClassNotFoundException {
        // Functional test.
        Tester test2 = new Tester("Tests.Test1");

        // Non-functional test.
        try{
            Tester test3 = new Tester("NotATest");
            // Should print error.
            String result = test3.assertClass();
            assertSame( "Couldn't find class, try again. \n", result);
        } catch( ClassNotFoundException e) {
            System.out.println("Exception thrown.");
        }

        // Interface, also not a testclass.
        Tester test4 = new Tester("Interface");

        // Not a testclass.
        Tester test5 = new Tester("Controller.Worker");


        // Should print error.
        String result2 = test4.assertClass();
        assertSame("Class is an interface, try again.", result2);

        // Should print error.
        String result3 = test5.assertClass();
        assertSame("Class does not implement TestClass interface," +
                " try again.", result3);

        // Okay class, shouldn't print error.
        String result4 = test2.assertClass();
        assertNull(result4);
    }

    @Test
    void getSetUpAndTearDown() throws ClassNotFoundException {
        // Functional test.
        Tester test2 = new Tester("Tests.Test1");
        Tester test1 = new Tester(class1.getClass().getName());

        Method[] methodList = test1.getMethods();
        test1.getSetUpAndTearDown(methodList);
        Method[] methodList2 = test2.getMethods();
        test2.getSetUpAndTearDown(methodList2);

        // Test that setUp is initialized correctly.
        assertNull(test1.getSetUp());
        assertNotNull(test2.getSetUp());

        // Test that tearDOwn is initialized correctly.
        assertNull(test1.getTearDown());
        assertNotNull(test2.getTearDown());

    }

    @Test
    void concludeTest() throws InvocationTargetException,
            IllegalAccessException, ClassNotFoundException {
        // Functional test.
        Tester test2 = new Tester("Tests.Test1");

        test2.assertClass();
        Method[] methodList = test2.getMethods();
        test2.getSetUpAndTearDown(methodList);


        for(Method m : methodList) {
            test2.runTest(m);
        }
        String result = test2.concludeTest();

        assertEquals('\n' + "Conclusion:"
                + '\n'
                + test2.getSuccess() + " tests succeeded."
                + '\n'
                + test2.getFail() + " tests failed."
                + '\n'
                + test2.getFailExc()
                + " tests failed with an exception \n"
                + "------------------------------------------------"
                + "\n", result);

    }
}