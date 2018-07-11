public class ArrayDequeTest {
    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");
        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Character> lld1 = new ArrayDeque<Character>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addLast('a');

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast('b');
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addFirst('c');
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);

        lld1.addLast('d');
        passed = checkSize(4, lld1.size()) && passed;

        lld1.addLast('e');
        lld1.addFirst('f');
        lld1.addLast('g');
        lld1.addFirst('h');
        lld1.addLast('i');
        lld1.addFirst('j');
    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(1);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addLast(4);
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        //passed = checkEmpty(true, lld1.isEmpty()) && passed;

        //printTestStatus(passed);

    }

    public static void printTest() {
        System.out.println("Print test.");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        // when nextFirst < nextLast
        lld1.addFirst(1);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addFirst(2);
        lld1.addLast(4);
        lld1.printDeque();

        // when nextLast < nextFirst
        lld1.addLast(5);
        lld1.addLast(6);
        lld1.addLast(7);
        lld1.printDeque();

        //when they are same
        lld1.addLast(8);
        lld1.printDeque();

        //when the list if full
        lld1.addLast(9);
        lld1.printDeque();
    }

    public static void usageFactorTest() {
        System.out.println("Usage factor function test.");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        // when nextFirst < nextLast
        lld1.addFirst(1);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;
        lld1.addFirst(2);
        lld1.removeLast();

        // when nextFirst > nextLast
        lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(1);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addFirst(6);
        lld1.addFirst(7);
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        printTest();
        usageFactorTest();
    }
}
