package ru.renue.searcher;

import ru.renue.csvParser.CellData;

import java.util.*;

public class Searcher {
    boolean isNumeric;
    ArrayAlgorithm m_searchAlgorithm;

    HashMap<String, ArrayList<Integer>> valueCache;
    LinkedList<String> orderedValues;


    public Searcher(ArrayList<CellData> data, ArrayAlgorithm searchAlgorithm) {
        m_searchAlgorithm = searchAlgorithm;
        valueCache = new HashMap<>();
        isNumeric = false;

        prepareDataStructures(data);
    }

    public CellData[] search(String pattern) {
        var searchedStrings = isNumeric
                ? searchNumericArray(pattern)
                : m_searchAlgorithm.search(orderedValues, pattern);
        var result = new LinkedList<CellData>();

        try {
            for (String str : searchedStrings) {
                for (int id : valueCache.get(str)) {
                    result.add(new CellData(id, str));
                }
            }
        } catch (NullPointerException ignored){

        }


        return result.toArray(new CellData[0]);
    }

    private void prepareDataStructures(ArrayList<CellData> data) {
        LinkedList<String> orderedList;

        for (CellData cellData : data) {
            addToValueCache(cellData.getId(), cellData.getValue().trim());
        }

        if (data.size() > 0) {
            try {
                Double.parseDouble(data.get(0).getValue());
                isNumeric = true;
            } catch (NumberFormatException ignored) {
            }
        }

        var tree = new TreeSet<String>();

        for (CellData cellData : data) {
            tree.add(cellData.getValue().trim());
        }
        orderedList = new LinkedList<>(tree);

        if (isNumeric) {
            orderedList = orderNumericStrings(new ArrayList<>(tree));
        }

        orderedValues = orderedList;
    }

    private LinkedList<String> orderNumericStrings(ArrayList<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                try {
                    Double.valueOf(s);
                } catch (NumberFormatException e) {
                    return 1;
                }

                try {
                    Double.valueOf(t1);
                } catch (NumberFormatException e) {
                    return -1;
                }

                return Double.valueOf(s).compareTo(Double.valueOf(t1));
            }
        });

        return new LinkedList<>(list);
    }


    private String[] searchNumericArray(String pattern) {
        LinkedList<String> list = new LinkedList<>();
        for (String str : orderedValues) {
            if (str.startsWith(pattern))
                list.add(str);
        }

        return list.toArray(new String[0]);
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
