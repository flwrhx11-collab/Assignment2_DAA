public class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == size) {
            add(x);
            return;
        }
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
            if (current.next == null) tail = current;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            if (current.data == x) return true;
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }
}
