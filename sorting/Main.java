package sorting;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        if (hasSortingArg(args)) {
            handleIntSorting();
            return;
        }

        String dataType = getDataTypeFromArgs(args);

        switch (dataType) {
            case "long" -> handleLongInput();
            case "line" -> handleLineInput();
            default -> handleWordInput();
        }
    }

    private static void handleIntSorting() {
        List<Integer> intList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            int number = scanner.nextInt();
            intList.add(number);
        }

        System.out.printf("""
                Total numbers: %s.
                Sorted data:""", intList.size());

        intList.stream()
                .sorted(Integer::compareTo)
                .forEach(i -> System.out.print(" " + i));
    }

    private static boolean hasSortingArg(String[] args) {
        for (String arg : args) {
            if ("-sortIntegers".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static void handleWordInput() {
        List<String> wordList = new ArrayList<>();
        while (scanner.hasNext()) {
            String word = scanner.next();
            wordList.add(word);
        }
        String longest = wordList.stream()
                .max(Comparator.comparing(String::length))
                .orElse("");
        long occurrence = wordList.stream()
                .filter(l -> l.equals(longest))
                .count();

        System.out.printf("""
                Total words: %s.
                The longest word: %s (%s time(s), %d%%).
                """, wordList.size(), longest, occurrence, getPercentage(occurrence, wordList.size()));
    }

    private static void handleLineInput() {
        List<String> lineList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineList.add(line);
        }
        String longest = lineList.stream()
                .max(Comparator.comparing(String::length))
                .orElse("");
        long occurrence = lineList.stream()
                .filter(l -> l.equals(longest))
                .count();

        System.out.printf("""
                Total lines: %s.
                The longest line:
                %s
                (%s time(s), %d%%).
                """, lineList.size(), longest, occurrence, getPercentage(occurrence, lineList.size()));
    }

    private static void handleLongInput() {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            longList.add(number);
        }
        long greatest = longList.stream()
                .max(Long::compare)
                .orElse(0L);
        long occurrence = longList.stream()
                .filter(l -> l == greatest)
                .count();

        System.out.printf("""
                Total numbers: %s.
                The greatest number: %s (%s time(s), %d%%).
                """, longList.size(), greatest, occurrence, getPercentage(occurrence, longList.size()));
    }

    private static int getPercentage(long qty, long total) {
        return (int) ((double) qty / total * 100);
    }

    private static String getDataTypeFromArgs(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-dataType".equals(args[i])) {
                return args[i + 1];
            }
        }
        return "word";
    }
}
