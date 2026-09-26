import java.util.ArrayList;
import java.util.PriorityQueue;

public class Tests {
    public static void main(String[] args) {
        testDynamicArray();
        testLinkedList();
        testMinHeap();
        System.out.println("All tests passed successfully!");
    }

    private static void testDynamicArray() {
        DynamicArray arr = new DynamicArray();
        ArrayList<Integer> ref = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            arr.add(i);
            ref.add(i);
        }

        arr.add(50, 999);
        ref.add(50, 999);

        arr.remove(25);
        ref.remove(25);

        if (arr.size() != ref.size()) throw new RuntimeException("DynamicArray: Size mismatch");

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != ref.get(i)) throw new RuntimeException("DynamicArray: Data mismatch at " + i);
        }

        if (arr.contains(999) != ref.contains(999)) throw new RuntimeException("DynamicArray: Contains mismatch");
    }

    private static void testLinkedList() {
        LinkedList list = new LinkedList();
        java.util.LinkedList<Integer> ref = new java.util.LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list.add(i);
            ref.add(i);
        }

        list.add(50, 999);
        ref.add(50, 999);

        list.remove(25);
        ref.remove(25);

        if (list.size() != ref.size()) throw new RuntimeException("LinkedList: Size mismatch");

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != ref.get(i)) throw new RuntimeException("LinkedList: Data mismatch at " + i);
        }

        if (list.contains(999) != ref.contains(999)) throw new RuntimeException("LinkedList: Contains mismatch");
    }

    private static void testMinHeap() {
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> ref = new PriorityQueue<>();

        int[] values = {5, 3, 8, 1, 9, 2, 7};
        for (int v : values) {
            heap.insert(v);
            ref.add(v);
        }

        if (heap.peekMin() != ref.peek()) throw new RuntimeException("MinHeap: Peek mismatch");

        while (!ref.isEmpty()) {
            if (heap.extractMin() != ref.poll()) throw new RuntimeException("MinHeap: Extract mismatch");
        }
    }
}