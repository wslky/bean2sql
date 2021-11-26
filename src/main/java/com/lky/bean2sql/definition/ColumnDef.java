package com.lky.bean2sql.definition;

/**
 * @Description column定义信息
 * @Author lky
 * @Date 2021-11-14 16:18
 */
public class ColumnDef {
    private String type;
    private String name;
    private boolean isQKey = false;

    @Override
    public String toString() {
        return "ColumnDef{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", isQKey=" + isQKey +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQKey() {
        return isQKey;
    }

    public void setQKey(boolean QKey) {
        isQKey = QKey;
    }

    public ColumnDef() {
    }

    public ColumnDef(String type, String name, boolean isQKey) {
        this.type = type;
        this.name = name;
        this.isQKey = isQKey;
    }
}
