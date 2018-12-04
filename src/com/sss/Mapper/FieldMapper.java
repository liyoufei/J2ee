package com.sss.Mapper;

public class FieldMapper {

    private String name;
    private String beanRef;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanRef() {
        return beanRef;
    }

    public void setBeanRef(String beanRef) {
        this.beanRef = beanRef;
    }

    @Override
    public String toString() {
        return "FieldMapper{" +
                "name='" + name + '\'' +
                ", beanRef='" + beanRef + '\'' +
                '}';
    }
}
