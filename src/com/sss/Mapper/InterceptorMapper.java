package com.sss.Mapper;

public class InterceptorMapper {

    private String name;
    private String classPath;
    private String preDo;
    private String afterDo;

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

    public String getPreDo() {
        return preDo;
    }

    public void setPreDo(String preDo) {
        this.preDo = preDo;
    }

    public String getAfterDo() {
        return afterDo;
    }

    public void setAfterDo(String afterDo) {
        this.afterDo = afterDo;
    }

    @Override
    public String toString() {
        return "InterceptorMapper{" +
                "name='" + name + '\'' +
                ", classPath='" + classPath + '\'' +
                ", preDo='" + preDo + '\'' +
                ", afterDo='" + afterDo + '\'' +
                '}';
    }
}
