package Tests;

import se.umu.cs.unittest.TestClass;

public class Test2 implements TestClass {

    private Cat myCat;

    public void setUp() {
        myCat= new Cat();
    }

    public void tearDown() {
        myCat = null;
    }

    // Not allowed to have parameters.
    public Test2() {
        myCat = new Cat();
    }

    // Should succeed.
    public boolean testSound() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myCat.getSound().equals("Meow");
    }

    // Should fail.
    public boolean testName() {
        return myCat.getName().equals("Not Smokey");
    }
    // Should succeed.
    public boolean testLegs() {
        return myCat.getLegs() == 4;
    }

    public boolean testLegsException() {
        myCat = null;
        return myCat.getLegs() == 1;
    }

    public boolean testTest(String abc) {
        return abc.equals("boi");
    }

    public String testBoi() {
        return null;
    }

    // Shoudln't go trough.
    public boolean doNothing() {

        return false;
    }

}
