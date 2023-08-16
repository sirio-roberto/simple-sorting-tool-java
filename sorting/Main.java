package sorting;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        boolean natural = isNaturalSorting(args);
        String dataType = getDataTypeFromArgs(args);

        switch (dataType) {
            case "long" -> handleLongInput(natural);
            case "line" -> handleLineInput(natural);
            default -> handleWordInput(natural);
        }
    }

    private static boolean isNaturalSorting(String[] args) {
        for (String arg : args) {
            if ("byCount".equals(arg)) {
                return false;
            }
        }
        return true;
    }

    private static void handleWordInput(boolean isNatural) {
        List<String> wordList = new ArrayList<>();
        while (scanner.hasNext()) {
            String word = scanner.next();
            wordList.add(word);
        }

        System.out.printf("Total words: %s.\n", wordList.size());

        if (isNatural) {
            wordList.stream()
                    .sorted(String::compareTo)
                    .forEach(i -> System.out.print(i + " "));
        } else {
            Map<String, Long> valueOccurMap = new HashMap<>();
            for (String s : wordList) {
                long occurrences = wordList.stream().filter(v -> v.equals(s)).count();
                valueOccurMap.put(s, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparing(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> System.out.printf("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), wordList.size())));
        }
    }

    private static void handleLineInput(boolean isNatural) {
        List<String> lineList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineList.add(line);
        }
        System.out.printf("Total words: %s.\n", lineList.size());

        if (isNatural) {
            lineList.stream()
                    .sorted(String::compareTo)
                    .forEach(System.out::println);
        } else {
            Map<String, Long> valueOccurMap = new HashMap<>();
            for (String s : lineList) {
                long occurrences = lineList.stream().filter(v -> v.equals(s)).count();
                valueOccurMap.put(s, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparing(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> System.out.printf("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), lineList.size())));
        }
    }

    private static void handleLongInput(boolean isNatural) {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            longList.add(number);
        }

        System.out.printf("Total numbers: %s.\n", longList.size());

        if (isNatural) {
            longList.stream()
                    .sorted(Long::compareTo)
                    .forEach(i -> System.out.print(i + " "));
        } else {
            Map<Long, Long> valueOccurMap = new HashMap<>();
            for (long l : longList) {
                long occurrences = longList.stream().filter(v -> v == l).count();
                valueOccurMap.put(l, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparingLong(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> System.out.printf("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), longList.size())));
        }
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
