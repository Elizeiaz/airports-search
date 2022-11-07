package ru.renue.csvParser;

public abstract class CsvParser implements ICsvParser {
    protected CellData splitByColumn(String str, int columnId) {
        String[] values = str.split(",");

        int id;
        try {
            id = Integer.parseInt(values[0]);
        } catch (NumberFormatException e) {
            throw e;
        }

        if (columnId > values.length || columnId < 0)
            throw new IndexOutOfBoundsException("Incorrect column id: " + columnId);

        String value = values[columnId];
        if (value.charAt(0) == '\"') {
            value = value.replaceAll("^\"|\"$", "");
        }

        return new CellData(id, value);
    }
}
