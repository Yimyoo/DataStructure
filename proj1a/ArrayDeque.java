public class ArrayDeque<Blom> {
    private Blom[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Create an empty dequeList. */
    public ArrayDeque() {
        items = (Blom []) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Add an item to the first of the list. */
    public void addFirst(Blom item) {
        if (size == items.length) { // If the list is full
            resize(size + 1);
            items[nextFirst] = item;
            size ++;
        }
        else {
            items[nextFirst] = item;
            nextFirst -= 1;
            size ++;
            if (nextFirst < 0)  nextFirst = items.length - 1; // make it recursive
        }
    }

    /** Add an item to the back of the list. */
    public void addLast(Blom item) {
        if (size == items.length) { // If the list is full
            resize(size + 1);
            items[nextLast] = item;
            size ++;
        }
        else {
            items[nextLast] = item;
            nextLast += 1;
            size ++;
            if (nextLast >= items.length)   nextLast = 0; // make it recursive
        }

    }

    /** Resize the list if it's full. */
    public void resize(int size) {
        Blom[] newArray = (Blom []) new Object[size];
        System.arraycopy(items, 0, newArray, 0, size - 1);
        items = newArray;
        nextFirst = items.length - 1;
        nextLast = items.length - 1;
    }

    /** Check whether the list is empty. */
    public boolean isEmpty() {
        if (size == 0)  return true;
        else    return false;
    }

    /** Get the size of the list. */
    public int size() {
        return size;
    }

    /** Print the list. */
    public void printDeque() {
        if(size == items.length) { // print starting from first of the list
            int count = size - 1;
            while (count >= 0) {
                System.out.println(items[count]);
                count --;
            }
        }
        else { // print starting from nextFirst
            if (nextFirst >= nextLast) {
             int count = nextFirst + 1;
             while (count < items.length) {
                 System.out.println(items[count]);
                 count ++;
             }
             count = 0;
             while (count < nextLast) {
                 System.out.println(items[count]);
                 count ++;
             }
            }
            else if (nextFirst < nextLast) {
                int count = nextFirst + 1;
                while (count < nextLast) {
                    System.out.println(items[count]);
                    count ++;
                }
            }
        }

    }

    /** Remove the first of the list. */
    public void removeFirst() {
        if (nextFirst == items.length - 1) {
            System.out.println("Remove first of the list:" + items[0]);
            items[0] = null;
            nextFirst = 0;
            size --;
        }
        else {
            System.out.println("Remove first of the list:" + items[nextFirst + 1]);
            items[nextFirst + 1] = null;
            nextFirst ++;
            size --;
        }
        usageFactor();
    }

    /** Remove the back of the list. */
    public void removeLast() {
        if (nextLast == 0) {
            System.out.println("Remove last of the list:" + items[items.length - 1]);
            items[items.length - 1] = null;
            nextLast = items.length - 1;
            size --;
        }
        else {
            System.out.println("Remove last of the list:" + items[nextLast - 1]);
            items[nextLast - 1] = null;
            nextLast --;
            size --;
        }
        usageFactor();
    }

    /** Get the index item of the list. */
    public Blom get(int index) {
        return items[index];
    }

    /** Check whether the usage factor is larger than 0.25, if not, make it to 0.25. */
    public void usageFactor() {
        double ratio = (double)size / (items.length - 1);
        if (ratio < 0.25 && size != 0) {
            int factor = 4;
            Blom[] newArray = (Blom []) new Object[size * factor];
            int count2 = size;
            if (nextFirst >= nextLast) {
                int count1 = nextFirst + 1;
                while (count1 < items.length - 1) {
                    newArray[count2] = items[count1];
                    count1 ++;
                    count2 ++;
                }
                count1 = 0;
                while (count1 < nextLast) {
                    newArray[count2] = items[count1];
                    count1 ++;
                    count2 ++;
                }
            }
            else if (nextFirst < nextLast) {
                int count1 = nextFirst + 1;
                while (count1 < nextLast) {
                    newArray[count2] = items[count1];
                    count1 ++;
                    count2 ++;
                }
            }
            items = newArray;
            nextFirst = size - 1;
            nextLast = count2;
        }
        if (size == 0){
            Blom[] newArray = (Blom []) new Object[8];
            items = newArray;
            nextFirst = 4;
            nextLast = 5;
        }
    }

}
