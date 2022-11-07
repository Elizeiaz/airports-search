package ru.renue;

import ru.renue.csvParser.CellData;
import ru.renue.csvParser.CsvFileParser;
import ru.renue.csvParser.ICsvParser;
import ru.renue.searcher.BinarySearcher;
import ru.renue.searcher.Searcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String src = "classes/airports.csv";
        checkFileExist(src);
        var parser = new CsvFileParser(src);

        int columnIndex = getColumnIndexFromCli(args);
        var list = getData(parser, columnIndex);
        var searcher = new Searcher(list, new BinarySearcher());

        String inputPattern;
        while (!Objects.equals(inputPattern = inputCli("Введите строку:").toLowerCase(), "!quit")) {
            long start = System.currentTimeMillis();

            var resultId = searcher.search(inputPattern);
            var result = ResultBuilder.buildResult(resultId, parser);

            for (String str : result)
                System.out.println(str);

            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
            System.out.printf("\nКоличество найденных строк: %s\nЗатраченное на поиск время: %s мс%n\n\n",
                    resultId.length, elapsed);
        }
    }

    private static ArrayList<CellData> getData(ICsvParser parser, int columnIndex) {
        ArrayList<CellData> list;

        while (true) {
            try {
                list = parser.getByColumn(columnIndex - 1);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + "\n");
                columnIndex = getColumnIndexFromCli();
            }
        }

        return list;
    }

    private static int getColumnIndexFromCli(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-"))
                continue;

            if (isInteger(arg))
                return Integer.parseInt(arg);
        }

        return getColumnIndexFromCli();
    }

    private static int getColumnIndexFromCli() {
        String enterText = "Введите номер колонки:";
        String cliInputValue = inputCli(enterText);
        while (!isInteger(cliInputValue)) {
            cliInputValue = inputCli(enterText);
        }
        return Integer.parseInt(cliInputValue);
    }

    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String inputCli(String enterText) {
        System.out.println(enterText);
        Scanner scan = new Scanner(System.in);
        var str = scan.nextLine();

        if (str.equals(" "))
            str = "";

        return str;
    }

    private static boolean checkFileExist(String path) {
        File f = new File(path);
        if (!(f.exists() && !f.isDirectory())) {
            System.out.println("Отсутствует CSV файл.");
            System.exit(0);
            return false;
        }
        return true;
    }

    private static class ResultBuilder {
        private static String[] buildResult(CellData[] searched, ICsvParser parser) {
            int length = searched.length;

            var arrayId = new Integer[length];
            for (int i = 0; i < length; i++) {
                arrayId[i] = searched[i].getId();
            }

            var fullLines = parser.getLinesById(arrayId);

            var result = new String[length];

            boolean isNumeric = false;
            if (length > 0)
                isNumeric = isNumeric(searched[0].getValue());

            var sb = new StringBuilder();
            for (var i = 0; i < length; i++) {
                result[i] = concatenateLine(sb, searched[i].getValue(), fullLines[i], isNumeric).toString();
                sb.setLength(0);
            }
            return result;
        }

        private static boolean isNumeric(String value) {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private static StringBuilder concatenateLine(StringBuilder sb, String value, String line, boolean isNumeric) {
            if (isNumeric) {
                sb.append(value);
            } else {
                sb.append("\"");
                sb.append(value);
                sb.append("\"");
            }

            sb.append("[");
            sb.append(line);
            sb.append("]");

            return sb;
        }
    }
}
