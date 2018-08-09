public interface Deque<Blom> {
    public void addFirst(Blom item);
    public void addLast(Blom item);
    public boolean isEmpty();
    public Blom removeFirst();
    public Blom removeLast();
    public Blom get(int index);
    public int size();
    public void printDeque();
}
