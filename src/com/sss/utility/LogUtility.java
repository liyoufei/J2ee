package com.sss.utility;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LogUtility {

    public  static  void saveLog(LinkedHashMap<String,String> map) {
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            File file = new File("D:\\project\\J2ee\\untitled\\web\\log\\log.xml");
            Document document = builder.parse(new FileInputStream(file));

            Element element = document.getDocumentElement();

            Iterator<Map.Entry<String,String>> iterator= map.entrySet().iterator();
            Element actionNode = document.createElement("action");

            //将map中的数据便利后存入xml文件
            while(iterator.hasNext())
            {
                Map.Entry<String,String> entry = iterator.next();
                Element childElement = document.createElement(entry.getKey());
                childElement.setTextContent(entry.getValue());
                actionNode.appendChild(childElement);

            }

            element.appendChild(actionNode);

            //xml文件的格式化，更加美观
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);

            Writer outxml = new FileWriter(file);
            XMLSerializer serializer = new XMLSerializer(outxml, format);
            serializer.serialize(document);


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
