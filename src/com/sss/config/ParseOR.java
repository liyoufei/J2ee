package com.sss.config;


import com.sss.Mapper.ClassMapper;
import com.sss.Mapper.PropertyMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseOR {

    private HashMap<String,String> jdbc;
    private ArrayList<ClassMapper> classMappers;

    public HashMap<String, String> getJdbc() {
        return jdbc;
    }

    public void setJdbc(HashMap<String, String> jdbc) {
        this.jdbc = jdbc;
    }

    public ArrayList<ClassMapper> getClassMappers() {
        return classMappers;
    }

    public void setClassMappers(ArrayList<ClassMapper> classMappers) {
        this.classMappers = classMappers;
    }

    public  ParseOR(String configFile) {

        dom(configFile);
        //parseOR(configFile);
    }

    private  void dom(String configFile){
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setIgnoringComments(true);
            builderFactory.setIgnoringElementContentWhitespace(true);
            builderFactory.setValidating(false);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            File file = new File(configFile);
            Document document = builder.parse(new FileInputStream(file));

            Element element = document.getDocumentElement();

            //数据库驱动相关
            NodeList nodeList = element.getElementsByTagName("property");
            HashMap<String,String> map = new HashMap<>();
            for (int i = 0;i < nodeList.getLength(); ++i){
                Node node = nodeList.item(i);
                if(node.getParentNode().getNodeName().equalsIgnoreCase("jdbc")){
                    if(node instanceof Element){
                        Element element1 = (Element) node;
                        map.put(element1.getElementsByTagName("name").item(0).getTextContent(),element1.getElementsByTagName("value").item(0).getTextContent());
                    }


                }

            }
            //数据库连接配置为hashmap存储
            setJdbc(map);

            //Bean相关
            NodeList nodeList1 = document.getDocumentElement().getElementsByTagName("class");
            ArrayList<ClassMapper> classMappers = new ArrayList<>();
            for (int i = 0; i < nodeList1.getLength(); ++i){
                ClassMapper classMapper = new ClassMapper();
                ArrayList<PropertyMapper> propertys = new ArrayList<>();
                Node node = nodeList1.item(i);
                if(node instanceof Element){
                    Element element1 = (Element) node;
                    classMapper.setName(element1.getElementsByTagName("name").item(0).getTextContent());
                    classMapper.setTable(element1.getElementsByTagName("table").item(0).getTextContent());
                    classMapper.setId(element1.getElementsByTagName("name").item(1).getTextContent());
                    NodeList nodeList2 = element1.getElementsByTagName("property");
                    for(int j =0;j<nodeList2.getLength();++j){
                        Node node1 =nodeList2.item(j);
                        if(node1 instanceof Element){
                            PropertyMapper propertyMapper = new PropertyMapper();
                            Element element2 = (Element) node1;
                            propertyMapper.setName(element2.getElementsByTagName("name").item(0).getTextContent());
                            propertyMapper.setLazy(element2.getElementsByTagName("lazy").item(0).getTextContent());
                            propertyMapper.setColumn(element2.getElementsByTagName("column").item(0).getTextContent());
                            propertyMapper.setType(element2.getElementsByTagName("type").item(0).getTextContent());
                            propertys.add(propertyMapper);

                        }

                    }

                    classMapper.setPropertyMappers(propertys);
                    propertys = new ArrayList<>();

                }
                classMappers.add(classMapper);


            }
            setClassMappers(classMappers);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
