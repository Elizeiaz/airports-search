package ru.renue.csvReader;

public class CellData {
    private int m_id;
    private String m_value;

    public int getId(){
        return m_id;
    }

    public String getValue(){
        return m_value;
    }

    public CellData(int id, String value){
        m_id = id;
        m_value = value;
    }
}
