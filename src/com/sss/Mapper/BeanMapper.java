package com.sss.Mapper;

import java.util.ArrayList;

public class BeanMapper {

    private String id;
    private String classPath;
    ArrayList<FieldMapper> fieldMappers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public ArrayList<FieldMapper> getFieldMappers() {
        return fieldMappers;
    }

    public void setFieldMappers(ArrayList<FieldMapper> fieldMappers) {
        this.fieldMappers = fieldMappers;
    }

    @Override
    public String toString() {
        return "BeanMapper{" +
                "id='" + id + '\'' +
                ", classPath='" + classPath + '\'' +
                ", fieldMappers=" + fieldMappers +
                '}';
    }
}
