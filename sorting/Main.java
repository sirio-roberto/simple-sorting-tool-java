package sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String VALID_SORTING_TYPES = "byCount,natural";
    private static final String VALID_DATA_TYPES = "word,line,long";
    private static final String VALID_ARGS = "-sortingType,-dataType,-inputFile,-outputFile";

    private static final String FILE_NAME_REGEX = "[a-zA-Z0-9._]+";

    private static final StringBuilder OUTPUT_STR = new StringBuilder();

    private static String inputFileName = null;
    private static String outputFileName = null;
    private static Scanner scanner;

    public static void main(final String[] args) {
        if (areArgsInvalid(args)) {
            return;
        }
        getFileNamesFromArgs(args);
        instantiateScanner();

        boolean natural = isNaturalSorting(args);
        String dataType = getDataTypeFromArgs(args);

        switch (dataType) {
            case "long" -> handleLongInput(natural);
            case "line" -> handleLineInput(natural);
            default -> handleWordInput(natural);
        }
        handleOutput();
    }

    private static void instantiateScanner() {
        try {
            if (inputFileName == null) {
                scanner = new Scanner(System.in);
            } else {
                scanner = new Scanner(new File(inputFileName));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getFileNamesFromArgs(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-inputFile".equals(args[i])) {
                inputFileName = args[i + 1];
            }
            if ("-outputFile".equals(args[i])) {
                outputFileName = args[i + 1];
            }
        }
    }

    private static boolean areArgsInvalid(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-sortingType".equals(args[i]) && (i == args.length - 1 || !VALID_SORTING_TYPES.contains(args[i+ 1]))) {
                System.out.println("No sorting type defined!");
                return true;
            }
            if ("-dataType".equals(args[i]) && (i == args.length - 1 || !VALID_DATA_TYPES.contains(args[i+ 1]))) {
                System.out.println("No data type defined!");
                return true;
            }
            if ("-inputFile".equals(args[i]) && (i == args.length - 1 || !args[i+ 1].matches(FILE_NAME_REGEX))) {
                System.out.println("No file name defined!");
                return true;
            }
            if ("-outputFile".equals(args[i]) && (i == args.length - 1 || !args[i+ 1].matches(FILE_NAME_REGEX))) {
                System.out.println("No file name defined!");
                return true;
            }
        }
        for (String arg : args) {
            if (arg.startsWith("-") && !VALID_ARGS.contains(arg)) {
                System.out.printf("\"%s\" is not a valid parameter. It will be skipped.\n", arg);
            }
        }
        return false;
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

        OUTPUT_STR.append(String.format("Total words: %s.\n", wordList.size()));

        if (isNatural) {
            wordList.stream()
                    .sorted(String::compareTo)
                    .forEach(i -> OUTPUT_STR.append(i).append(" "));
        } else {
            Map<String, Long> valueOccurMap = new HashMap<>();
            for (String s : wordList) {
                long occurrences = wordList.stream().filter(v -> v.equals(s)).count();
                valueOccurMap.put(s, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparing(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> OUTPUT_STR.append(String.format("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), wordList.size()))));
        }
    }

    private static void handleLineInput(boolean isNatural) {
        List<String> lineList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineList.add(line);
        }
        OUTPUT_STR.append(String.format("Total words: %s.\n", lineList.size()));

        if (isNatural) {
            lineList.stream()
                    .sorted(String::compareTo)
                    .forEach(l -> OUTPUT_STR.append(l).append("\n"));
        } else {
            Map<String, Long> valueOccurMap = new HashMap<>();
            for (String s : lineList) {
                long occurrences = lineList.stream().filter(v -> v.equals(s)).count();
                valueOccurMap.put(s, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparing(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> OUTPUT_STR.append(String.format("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), lineList.size()))));
        }
    }

    private static void handleLongInput(boolean isNatural) {
        List<Long> longList = new ArrayList<>();
        while (scanner.hasNext()) {
            String scannedStr = scanner.next();
            if (!scannedStr.matches("-?\\d+")) {
                System.out.printf("\"%s\" is not a long. It will be skipped.\n", scannedStr);
            } else {
                long number = Long.parseLong(scannedStr);
                longList.add(number);
            }
        }

        OUTPUT_STR.append(String.format("Total numbers: %s.\n", longList.size()));

        if (isNatural) {
            longList.stream()
                    .sorted(Long::compareTo)
                    .forEach(i -> OUTPUT_STR.append(i).append(" "));
        } else {
            Map<Long, Long> valueOccurMap = new HashMap<>();
            for (long l : longList) {
                long occurrences = longList.stream().filter(v -> v == l).count();
                valueOccurMap.put(l, occurrences);
            }

            valueOccurMap.keySet().stream()
                    .sorted(Comparator.comparingLong(k -> k))
                    .sorted(Comparator.comparingLong(valueOccurMap::get))
                    .forEach(k -> OUTPUT_STR.append(String.format("%s: %s time(s), %s%%\n",
                            k, valueOccurMap.get(k), getPercentage(valueOccurMap.get(k), longList.size()))));
        }
    }

    private static void handleOutput() {
        if (outputFileName == null) {
            System.out.println(OUTPUT_STR);
        } else {
            try (FileWriter writer = new FileWriter(outputFileName)) {
                writer.write(OUTPUT_STR.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
