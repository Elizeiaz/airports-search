package ru.renue;

import ru.renue.csvParser.CsvFileParser;
import ru.renue.searcher.BinarySearcher;
import ru.renue.searcher.Searcher;

import java.util.ArrayList;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {
        String src = "src/main/java/ru/renue/tempData/airports.csv";

        long start = System.currentTimeMillis();

        var parser = new CsvFileParser(src);
        var list = parser.getByColumn(1);
        var searcher = new Searcher(list, new BinarySearcher());

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}
