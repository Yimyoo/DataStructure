public class LinkedListDeque<Blom> implements Deque<Blom> {
    private IntNode sentinel;
    private int size;

    private class IntNode {
        public IntNode prev;
        public Blom item;
        public IntNode next;

        public IntNode(Blom item, IntNode prev, IntNode next) {
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

    @Override
    public void addFirst(Blom item) {
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

    @Override
    public void addLast(Blom item) {
        size += 1;
        sentinel.prev = new IntNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)  return true;
        else return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int count = size;
        sentinel = sentinel.next;
        while (count > 0) {
            System.out.print(sentinel.item + " ");
            count--;
            sentinel = sentinel.next;
        }
    }

    @Override
    public Blom removeFirst() {
        if (size == 0) return null;
        else {
            size--;
            Blom n = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return n;
        }
    }

    @Override
    public Blom removeLast() {
        if (size == 0) return null;
        else {
            size--;
            Blom n = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return n;
        }
    }

    @Override
    public Blom get(int index) {
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

    public Blom getRecursive(int index) {
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
