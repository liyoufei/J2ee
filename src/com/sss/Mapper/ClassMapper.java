package com.sss.Mapper;

import java.util.ArrayList;

public class ClassMapper {

    private String name;
    private String table;
    private String id;
    private ArrayList<PropertyMapper> propertyMappers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<PropertyMapper> getPropertyMappers() {
        return propertyMappers;
    }

    public void setPropertyMappers(ArrayList<PropertyMapper> propertyMappers) {
        this.propertyMappers = propertyMappers;
    }

    @Override
    public String toString() {
        return "ClassMapper{" +
                "name='" + name + '\'' +
                ", table='" + table + '\'' +
                ", id='" + id + '\'' +
                ", propertyMappers=" + propertyMappers +
                '}';
    }
}
