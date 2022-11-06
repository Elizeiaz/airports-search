package ru.renue.csvReader;

public abstract class CsvParser implements ICsvParser {
    protected CellData splitByColumn(String str, int columnId) {
        String[] values = str.split(",");

        int id;
        try {
            id = Integer.parseInt(values[0]);
        } catch (NumberFormatException e) {
            throw e;
        }

        String value = values[columnId];
        if (value.charAt(0) == '\"') {
            value = value.replaceAll("^\"|\"$", "");
        }

        return new CellData(id, value);
    }
}
