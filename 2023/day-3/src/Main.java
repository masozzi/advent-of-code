import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern PATTERN_PART_1 = Pattern.compile("\\d+");
    private static final Pattern PATTERN_PART_2 = Pattern.compile("\\*");

    private static long result_part_1 = 0L;
    private static long result_part_2 = 0L;

    private static String previousLine = null;
    private static String currentLine = null;
    private static String nextLine = null;

    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("../input.txt");
        BufferedReader br = new BufferedReader(file);

        currentLine = br.readLine();
        previousLine = ".".repeat(currentLine.length());
        br.lines().forEach(line -> {
            nextLine = line;
            parseLinePart1();
            parseLinePart2();

            previousLine = currentLine;
            currentLine = line;
        });
        br.close();

        // This is so we process the last line.
        nextLine = ".".repeat(currentLine.length());
        parseLinePart1();
        parseLinePart2();

        System.out.println("RESULT: " + result_part_1);
        System.out.println("RESULT: " + result_part_2);
    }

    public static void parseLinePart1() {
        Matcher m = PATTERN_PART_1.matcher(currentLine);
        m.results().forEach(r -> {
            var start = Math.max(r.start() - 1, 0);
            var end = Math.min(r.end() + 1, currentLine.length());

            if (!hasSymbol(start, end)) return;
            result_part_1 += Long.parseLong(r.group());
        });
    }

    public static void parseLinePart2() {
        System.out.println(currentLine);
        Matcher m = PATTERN_PART_2.matcher(currentLine);
        m.results().forEach(r -> gearRatio(r.start()));
    }

    private static void gearRatio(final int index) {
        StringBuilder sb = new StringBuilder();

        var start = readLeft(index, previousLine);
        var end = readRight(index, previousLine);
        sb.append(previousLine, start, end);
        sb.append(".");

        start = readLeft(index, currentLine);
        end = readRight(index, currentLine);
        sb.append(currentLine, start, end);
        sb.append(".");

        start = readLeft(index, nextLine);
        end = readRight(index, nextLine);
        sb.append(nextLine, start, end);

        Matcher m = PATTERN_PART_1.matcher(sb);
        var resList = m.results().map(r -> Long.parseLong(r.group())).toList();
        if (resList.size() != 2) return;

        long res = 1;
        System.out.print("NUM:");
        for (var l : resList) {
            System.out.print(" " + l);
            res *= l;
        }
        System.out.println(" MUL: " + res);

        result_part_2 += res;
    }

    private static int readLeft(final int index, final String line) {
        if (index <= 0) return 0;

        var c = line.charAt(index - 1);
        if (!Character.isDigit(c)) return index;

        return readLeft(index - 1, line);
    }

    private static int readRight(final int index, final String line) {
        if (index >= line.length() - 1) return line.length();

        var c = line.charAt(index + 1);
        if (!Character.isDigit(c)) return index + 1;

        return readRight(index + 1, line);
    }

    public static boolean hasSymbol(final int start, final int end) {
        String line;

        line = previousLine.substring(start, end);
        if (!line.replaceAll("[\\d.]", "").isEmpty()) return true;

        line = currentLine.substring(start, end);
        if (!line.replaceAll("[\\d.]", "").isEmpty()) return true;

        line = nextLine.substring(start, end);
        return !line.replaceAll("[\\d.]", "").isEmpty();
    }
}