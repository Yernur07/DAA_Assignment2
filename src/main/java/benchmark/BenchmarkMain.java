package benchmark;

import structures.DynamicArray;
import structures.IntList;
import structures.MinHeap;
import structures.MyLinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkMain {

    private static final int[] SIZES = {
            100,
            1_000,
            10_000,
            100_000
    };

    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;

    public static void main(String[] args) throws Exception {

        File resultsDirectory = new File("results");

        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        File csvFile = new File(
                resultsDirectory,
                "results.csv"
        );

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(csvFile))) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            for (int n : SIZES) {

                runW1(writer, n);
                runW2(writer, n);
                runW3(writer, n, "head");
                runW3(writer, n, "middle");
                runW4(writer, n);
            }
        }

        System.out.println(
                "Benchmark finished."
        );

        System.out.println(
                "Results saved to results/results.csv"
        );
    }

    private static void runW1(
            PrintWriter writer,
            int n) {

        int[] data = generateData(n);

        int[] indexes = generateIndexes(n);

        benchmarkList(
                writer,
                "W1",
                "-",
                "DynamicArray",
                n,
                () -> {

                    DynamicArray array =
                            new DynamicArray();

                    fill(array, data);

                    array.resetCounters();

                    for (int index : indexes) {
                        array.get(index);
                    }

                    return new MetricsResult(
                            array.getSteps(),
                            array.getMoves(),
                            array.getComparisons()
                    );
                }
        );

        benchmarkList(
                writer,
                "W1",
                "-",
                "MyLinkedList",
                n,
                () -> {

                    MyLinkedList list =
                            new MyLinkedList();

                    fill(list, data);

                    list.resetCounters();

                    for (int index : indexes) {
                        list.get(index);
                    }

                    return new MetricsResult(
                            list.getSteps(),
                            list.getMoves(),
                            list.getComparisons()
                    );
                }
        );
    }

    private static void runW2(
            PrintWriter writer,
            int n) {

        int[] data = generateData(n);

        int[] queries = generateSearchQueries(
                data,
                n
        );

        benchmarkList(
                writer,
                "W2",
                "-",
                "DynamicArray",
                n,
                () -> {

                    DynamicArray array =
                            new DynamicArray();

                    fill(array, data);

                    array.resetCounters();

                    for (int query : queries) {
                        array.contains(query);
                    }

                    return new MetricsResult(
                            array.getSteps(),
                            array.getMoves(),
                            array.getComparisons()
                    );
                }
        );

        benchmarkList(
                writer,
                "W2",
                "-",
                "MyLinkedList",
                n,
                () -> {

                    MyLinkedList list =
                            new MyLinkedList();

                    fill(list, data);

                    list.resetCounters();

                    for (int query : queries) {
                        list.contains(query);
                    }

                    return new MetricsResult(
                            list.getSteps(),
                            list.getMoves(),
                            list.getComparisons()
                    );
                }
        );
    }

    private static void runW3(
            PrintWriter writer,
            int n,
            String variant) {

        int[] data = generateData(n);

        benchmarkList(
                writer,
                "W3",
                variant,
                "DynamicArray",
                n,
                () -> runInsertRemove(
                        new DynamicArray(),
                        data,
                        variant
                )
        );

        benchmarkList(
                writer,
                "W3",
                variant,
                "MyLinkedList",
                n,
                () -> runInsertRemove(
                        new MyLinkedList(),
                        data,
                        variant
                )
        );
    }

    private static MetricsResult runInsertRemove(
            IntList list,
            int[] data,
            String variant) {

        fill(list, data);

        list.resetCounters();

        for (int i = 0; i < 1_000; i++) {

            int index;

            if (variant.equals("head")) {
                index = 0;
            } else {
                index = list.size() / 2;
            }

            list.add(index, i);
        }

        for (int i = 0; i < 1_000; i++) {

            int index;

            if (variant.equals("head")) {
                index = 0;
            } else {
                index = list.size() / 2;
            }

            list.remove(index);
        }

        return new MetricsResult(
                list.getSteps(),
                list.getMoves(),
                list.getComparisons()
        );
    }

    private static void runW4(
            PrintWriter writer,
            int n) {

        int[] data = generateData(n);

        benchmarkHeap(
                writer,
                n,
                data
        );
    }

    private static void benchmarkList(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            Operation operation) {

        for (int i = 0; i < WARMUP_RUNS; i++) {
            operation.run();
        }

        long[] times =
                new long[MEASURED_RUNS];

        MetricsResult result = null;

        for (int i = 0; i < MEASURED_RUNS; i++) {

            long start =
                    System.nanoTime();

            result = operation.run();

            long end =
                    System.nanoTime();

            times[i] =
                    end - start;
        }

        Arrays.sort(times);

        long median =
                times[MEASURED_RUNS / 2];

        double timeMs =
                median / 1_000_000.0;

        writer.printf(
                "%s,%s,%s,%d,%.4f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                timeMs,
                result.steps,
                result.moves,
                result.comparisons
        );
    }

    private static void benchmarkHeap(
            PrintWriter writer,
            int n,
            int[] data) {

        for (int i = 0; i < WARMUP_RUNS; i++) {

            runHeap(data);
        }

        long[] times =
                new long[MEASURED_RUNS];

        MetricsResult result = null;

        for (int i = 0; i < MEASURED_RUNS; i++) {

            long start =
                    System.nanoTime();

            result = runHeap(data);

            long end =
                    System.nanoTime();

            times[i] =
                    end - start;
        }

        Arrays.sort(times);

        long median =
                times[MEASURED_RUNS / 2];

        double timeMs =
                median / 1_000_000.0;

        writer.printf(
                "W4,-,MinHeap,%d,%.4f,%d,%d,%d%n",
                n,
                timeMs,
                result.steps,
                result.moves,
                result.comparisons
        );
    }

    private static MetricsResult runHeap(
            int[] data) {

        MinHeap heap =
                new MinHeap();

        for (int value : data) {
            heap.insert(value);
        }

        heap.resetCounters();

        int previous =
                Integer.MIN_VALUE;

        for (int i = 0; i < data.length; i++) {

            int current =
                    heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException(
                        "Heap output is not sorted"
                );
            }

            previous = current;
        }

        return new MetricsResult(
                heap.getSteps(),
                heap.getMoves(),
                heap.getComparisons()
        );
    }

    private static void fill(
            IntList list,
            int[] data) {

        for (int value : data) {
            list.add(value);
        }
    }

    private static int[] generateData(
            int n) {

        Random random =
                new Random(42);

        int[] data =
                new int[n];

        for (int i = 0; i < n; i++) {

            data[i] =
                    random.nextInt(
                            1_000_000
                    );
        }

        return data;
    }

    private static int[] generateIndexes(
            int n) {

        Random random =
                new Random(42);
        int[] indexes =
                new int[10_000];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] =
                    random.nextInt(n);
        }
        return indexes;
    }
    private static int[] generateSearchQueries(
            int[] data,
            int n) {
        Random random =
                new Random(42);
        int[] queries =
                new int[1_000];
        for (int i = 0; i < 500; i++) {
            queries[i] =
                    data[random.nextInt(n)];
        }
        for (int i = 500; i < 1_000; i++) {
            queries[i] =
                    1_000_001
                            + random.nextInt(
                            1_000_000
                    );
        }
        return queries;
    }
    private interface Operation {
        MetricsResult run();
    }
    private static class MetricsResult {
        long steps;
        long moves;
        long comparisons;
        MetricsResult(
                long steps,
                long moves,
                long comparisons) {
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}