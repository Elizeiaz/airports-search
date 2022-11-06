package ru.renue.csvReader;

import java.util.ArrayList;

public interface ICsvParser {
    ArrayList<CellData> getByColumn(int columnIndex);

    public String[] getLinesById(int[] arrayId);
}
