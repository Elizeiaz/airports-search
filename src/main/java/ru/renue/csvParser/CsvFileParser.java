package ru.renue.csvParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvFileParser extends CsvParser {
    private final String CSV_SPLITTER = ",";

    private final HashMap<Integer, Long> cachePointer;

    private final String src;

    public CsvFileParser(String src) {
        this.src = src;
        cachePointer = new HashMap<>();
    }

    public String getSrc() {
        return src;
    }

    // todo: Тут нужен LinkedList??
    @Override
    public String[] getLinesById(int[] arrayId) {
        var stringArray = new String[arrayId.length];

        try (RandomAccessFile reader = new RandomAccessFile(src, "r")) {
            for (var i = 0; i < arrayId.length; i++) {
                var id = arrayId[i];

                if (cachePointer.containsKey(id)) {
                    reader.seek(cachePointer.get(id));
                    stringArray[i] = reader.readLine();
                } else {
                    // if we don't find in the cache, can try research in the original document
                    throw new UnsupportedOperationException();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringArray;
    }

    @Override
    public ArrayList<CellData> getByColumn(int columnIndex) {
        var list = new ArrayList<CellData>();

        try (var reader = new BufferedReader(new FileReader(src))) {
            String line;
            long pointer = 0;

            while ((line = reader.readLine()) != null) {
                CellData cellData = splitByColumn(line, columnIndex);
                list.add(cellData);

                cachePointer.put(cellData.getId(), pointer);
                pointer += line.getBytes().length + 1;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
