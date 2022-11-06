package ru.renue;

import ru.renue.csvParser.CsvFileParser;
import ru.renue.searcher.BinarySearcher;
import ru.renue.searcher.Searcher;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;


public class Main {
    public static void main(String[] args) {
        String src = "src/main/java/ru/renue/tempData/airports.csv";

        long start = System.currentTimeMillis();

        var parser = new CsvFileParser(src);
        var list = parser.getByColumn(1);
        var binarySearcher = new BinarySearcher(); //delete
        var searcher = new Searcher(list, new BinarySearcher());
        var resultId = searcher.search("Bo");
        System.out.println(resultId.length);

//        var linkedList = new LinkedList<String>();
//        linkedList.add("a");
//        linkedList.add("b");
//        linkedList.add("c");
//        linkedList.add("d");
//        linkedList.add("d");
//        linkedList.add("d");
//        linkedList.add("d");
//        linkedList.add("e");
//        linkedList.add("f");
//        linkedList.add("g");
//
//        System.out.println(Arrays.toString(Arrays.stream(binarySearcher.search(linkedList, "g")).toArray()));

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
    }
}
