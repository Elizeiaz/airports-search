package ru.renue;

import ru.renue.csvReader.CsvFileParser;


public class Main {
    public static void main(String[] args) {
        String src = "src/main/java/ru/renue/tempData/airports.csv";

        long start = System.currentTimeMillis();

        var parser = new CsvFileParser(src);
        var list = parser.getByColumn(1);

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}
