public class LinkedListDeque<Type> {
    private IntNode sentinel;
    private int size;

    private class IntNode {
        public IntNode prev;
        public Type item;
        public IntNode next;

        public IntNode(Type item, IntNode prev, IntNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new IntNode(null, sentinel, sentinel);
        sentinel.next = sentinel; // before this step, sentinel.next and sentinel.prev equals null
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(Type item) {
        size += 1;
        /*
        if (size == 1) {
            sentinel.next = new IntNode(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        }

        if (size != 1) {
            sentinel.next = new IntNode(item, sentinel, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
        } */
        sentinel.next = new IntNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(Type item) {
        size += 1;
        sentinel.prev = new IntNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    public boolean isEmpty() {
        if (size == 0)  return true;
        else return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int count = size;
        sentinel = sentinel.next;
        while (count > 0) {
            System.out.print(sentinel.item + " ");
            count--;
            sentinel = sentinel.next;
        }
    }

    public Type removeFirst() {
        if (size == 0) return null;
        else {
            size--;
            Type n = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return n;
        }
    }

    public Type removeLast() {
        if (size == 0) return null;
        else {
            size--;
            Type n = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return n;
        }
    }

    public Type get(int index) {
        if (index >= size && index < 0) return null;
        else {
            int count = 0;
            IntNode temp = sentinel.next;
            while (count < index) {
                temp = temp.next;
                count ++;
            }
            return temp.item;
        }
    }

    public Type getRecursive(int index) {
        if (index >= size && index < 0) return null;
        else {
            IntNode temp = getAddress(index);
            return temp.item;
        }
    }

    /** Recursive helper method. */
    public IntNode getAddress(int index) {
        if (index == 0) return sentinel.next;
        else return getAddress(index - 1).next;
    }




}
