import java.util.Random;

public class Benchmark {
    private static final int[] N_VALUES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final Random RAND = new Random(42);

    public static void main(String[] args) {
        System.out.println("Starting Benchmarks...");
        for (int n : N_VALUES) {
            System.out.println("--- n = " + n + " ---");
            workload1(n);
            workload2(n);
            workload3(n);
            workload4(n);
        }
    }

    private static void workload1(int n) {
        long totalTimeArray = 0;
        long totalTimeList = 0;

        for (int r = 0; r < RUNS; r++) {
            DynamicArray arr = new DynamicArray();
            LinkedList list = new LinkedList();
            for (int i = 0; i < n; i++) {
                int val = RAND.nextInt();
                arr.add(val);
                list.add(val);
            }
            int[] indices = new int[10000];
            for (int i = 0; i < 10000; i++) {
                indices[i] = RAND.nextInt(n);
            }

            long start = System.nanoTime();
            for (int idx : indices) {
                arr.get(idx);
            }
            totalTimeArray += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int idx : indices) {
                list.get(idx);
            }
            totalTimeList += (System.nanoTime() - start);
        }
        System.out.println("W1 (Random Access) Array avg time: " + (totalTimeArray / RUNS) + " ns");
        System.out.println("W1 (Random Access) List avg time:  " + (totalTimeList / RUNS) + " ns");
    }

    private static void workload2(int n) {
        long totalTimeArray = 0;
        long totalTimeList = 0;

        for (int r = 0; r < RUNS; r++) {
            DynamicArray arr = new DynamicArray();
            LinkedList list = new LinkedList();
            for (int i = 0; i < n; i++) {
                int val = RAND.nextInt();
                arr.add(val);
                list.add(val);
            }
            int[] searchValues = new int[1000];
            for (int i = 0; i < 1000; i++) {
                searchValues[i] = RAND.nextInt();
            }

            long start = System.nanoTime();
            for (int val : searchValues) {
                arr.contains(val);
            }
            totalTimeArray += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int val : searchValues) {
                list.contains(val);
            }
            totalTimeList += (System.nanoTime() - start);
        }
        System.out.println("W2 (Search) Array avg time: " + (totalTimeArray / RUNS) + " ns");
        System.out.println("W2 (Search) List avg time:  " + (totalTimeList / RUNS) + " ns");
    }

    private static void workload3(int n) {
        long timeArrayInsert0 = 0, timeListInsert0 = 0;
        long timeArrayRemove0 = 0, timeListRemove0 = 0;
        long timeArrayInsertMid = 0, timeListInsertMid = 0;
        long timeArrayRemoveMid = 0, timeListRemoveMid = 0;

        for (int r = 0; r < RUNS; r++) {
            DynamicArray arr = new DynamicArray();
            LinkedList list = new LinkedList();
            for (int i = 0; i < n; i++) {
                arr.add(i);
                list.add(i);
            }

            long start = System.nanoTime();
            for (int i = 0; i < 1000; i++) arr.add(0, i);
            timeArrayInsert0 += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) list.add(0, i);
            timeListInsert0 += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) arr.remove(0);
            timeArrayRemove0 += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) list.remove(0);
            timeListRemove0 += (System.nanoTime() - start);

            int mid = n / 2;
            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) arr.add(mid, i);
            timeArrayInsertMid += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) list.add(mid, i);
            timeListInsertMid += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) arr.remove(mid);
            timeArrayRemoveMid += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++) list.remove(mid);
            timeListRemoveMid += (System.nanoTime() - start);
        }

        System.out.println("W3 (Insert 0) Array: " + (timeArrayInsert0 / RUNS) + " ns | List: " + (timeListInsert0 / RUNS) + " ns");
        System.out.println("W3 (Remove 0) Array: " + (timeArrayRemove0 / RUNS) + " ns | List: " + (timeListRemove0 / RUNS) + " ns");
        System.out.println("W3 (Insert Mid) Array: " + (timeArrayInsertMid / RUNS) + " ns | List: " + (timeListInsertMid / RUNS) + " ns");
        System.out.println("W3 (Remove Mid) Array: " + (timeArrayRemoveMid / RUNS) + " ns | List: " + (timeListRemoveMid / RUNS) + " ns");
    }

    private static void workload4(int n) {
        long totalInsertTime = 0;
        long totalExtractTime = 0;

        for (int r = 0; r < RUNS; r++) {
            MinHeap heap = new MinHeap();
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = RAND.nextInt();
            }

            long start = System.nanoTime();
            for (int val : values) {
                heap.insert(val);
            }
            totalInsertTime += (System.nanoTime() - start);

            start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                heap.extractMin();
            }
            totalExtractTime += (System.nanoTime() - start);
        }
        System.out.println("W4 (Heap Insert n) avg time:  " + (totalInsertTime / RUNS) + " ns");
        System.out.println("W4 (Heap Extract n) avg time: " + (totalExtractTime / RUNS) + " ns");
    }
}