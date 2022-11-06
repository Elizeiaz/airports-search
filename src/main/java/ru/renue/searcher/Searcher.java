package ru.renue.searcher;

import ru.renue.csvParser.CellData;

import java.util.*;

public class Searcher {
    ArrayAlgorithm m_searchAlgorithm;

    HashMap<String, ArrayList<Integer>> valueCache;
    LinkedList<String> orderedValues;


    public Searcher(ArrayList<CellData> data, ArrayAlgorithm searchAlgorithm) {
        m_searchAlgorithm = searchAlgorithm;
        valueCache = new HashMap<>();

        prepareDataStructures(data);
    }

    public Integer[] search(String pattern) {
        var idList = new LinkedList<Integer>();
        var searchedStrings = m_searchAlgorithm.search(orderedValues, pattern);

        for (String str : searchedStrings) {
            idList.addAll(valueCache.get(str));
        }

        return idList.toArray(new Integer[0]);
    }

    private void prepareDataStructures(ArrayList<CellData> data) {
        var tree = new TreeSet<String>();

        for (CellData cellData : data) {
            tree.add(cellData.getValue());

            addToValueCache(cellData.getId(), cellData.getValue());
        }

        orderedValues = new LinkedList<>(tree);
    }

    private void addToValueCache(int id, String value) {
        if (valueCache.containsKey(value)) {
            valueCache.get(value).add(id);
            return;
        }

        var idList = new ArrayList<Integer>();
        idList.add(id);
        valueCache.put(value, idList);
    }
}
