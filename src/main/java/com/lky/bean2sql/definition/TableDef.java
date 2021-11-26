package com.lky.bean2sql.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description table定义信息
 * @Author lky
 * @Date 2021-11-14 16:22
 */
public class TableDef {

    private String name;

    @Override
    public String toString() {
        return "TableDef{" +
                "name='" + name + '\'' +
                ", columnDefs=" + columnDefs +
                '}';
    }

    private List<ColumnDef> columnDefs = new ArrayList<>();

    public TableDef() {
    }

    public TableDef(String name, List<ColumnDef> columnDefs) {
        this.name = name;
        this.columnDefs = columnDefs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnDef> getColumnDefs() {
        return columnDefs;
    }

    public void setColumnDefs(List<ColumnDef> columnDefs) {
        this.columnDefs = columnDefs;
    }
}
