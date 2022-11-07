package ru.renue.searcher;

import java.util.LinkedList;

public class BinarySearcher extends ArrayAlgorithm {
    @Override
    public String[] search(LinkedList<String> array, String pattern) {
        int firstMatch = binarySearch(array, pattern);
        if (firstMatch == -1)
            return new String[0];

        var list = new LinkedList<String>();

        var startPos = getLeftEdge(array, pattern, firstMatch);
        while (startPos < array.size() && byteCompare(pattern, array.get(startPos)) == 0) {
            list.add(array.get(startPos));
            startPos++;
        }

        return list.toArray(new String[0]);
    }

    // first match item, else -1
    private int binarySearch(LinkedList<String> array, String pattern) {
        int left = 0;
        int right = array.size() - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            int compareResult = byteCompare(pattern, array.get(mid));
            if (compareResult > 0) {
                left = mid + 1;
            } else if (compareResult < 0) {
                right = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    // -1 - pattern < value; 0 - value startsWith Pattern; 1 - pattern > value;
    private int byteCompare(String pattern, String value) {
        String lValue = value.toLowerCase();
        String lPatter = pattern.toLowerCase();

        if (lValue.startsWith(lPatter))
            return 0;

        for (var i = 0; i < Math.min(lPatter.length(), lValue.length()); i++) {
            if (lPatter.charAt(i) < lValue.charAt(i))
                return -1;
            else if (lPatter.charAt(i) > lValue.charAt(i)) {
                return 1;
            }
        }

        return 1;
    }

    private int getLeftEdge(LinkedList<String> array, String pattern, int startPos) {
        int left = startPos;

        while (left > 0 && byteCompare(pattern, array.get(left - 1)) == 0) {
            left--;
        }

        return left;
    }

}
