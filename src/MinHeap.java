import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] heap;
    private int size;

    public MinHeap() {
        heap = new int[10];
        size = 0;
    }

    public void insert(int x) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = x;
        size++;
        heapifyUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) throw new NoSuchElementException();
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) throw new NoSuchElementException();
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index] < heap[parentIndex]) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < size && heap[leftChild] < heap[smallest]) {
            smallest = leftChild;
        }
        if (rightChild < size && heap[rightChild] < heap[smallest]) {
            smallest = rightChild;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}