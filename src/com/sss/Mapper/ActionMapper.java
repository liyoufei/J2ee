package com.sss.Mapper;

import java.util.ArrayList;

public class ActionMapper {
    //action名
    private String name;
    //类路径
    private String classPath;
    //方法名
    private String method;
    //拦截器引用
    private String intercepttorRef;
    //对应各个结果集
    private ArrayList<ResultMapper> resultMappers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<ResultMapper> getResultMappers() {
        return resultMappers;
    }

    public void setResultMappers(ArrayList<ResultMapper> resultMappers) {
        this.resultMappers = resultMappers;
    }

    public String getIntercepttorRef() {
        return intercepttorRef;
    }

    public void setIntercepttorRef(String intercepttorRef) {
        this.intercepttorRef = intercepttorRef;
    }

    @Override
    public String toString() {
        return "ActionMapper{" +
                "name='" + name + '\'' +
                ", classPath='" + classPath + '\'' +
                ", method='" + method + '\'' +
                ", intercepttorRef='" + intercepttorRef + '\'' +
                ", resultMappers=" + resultMappers +
                '}';
    }
}
