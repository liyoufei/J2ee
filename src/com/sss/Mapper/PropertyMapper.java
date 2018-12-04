package com.sss.Mapper;

public class PropertyMapper {

    private String name;
    private String value;
    private String column;
    private String type;
    private String lazy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLazy() {
        return lazy;
    }

    public void setLazy(String lazy) {
        this.lazy = lazy;
    }

    @Override
    public String toString() {
        return "PropertyMapper{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", column='" + column + '\'' +
                ", type='" + type + '\'' +
                ", lazy='" + lazy + '\'' +
                '}';
    }
}
