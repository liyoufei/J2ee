package com.sss.config;

import com.sss.Mapper.BeanMapper;
import com.sss.Mapper.FieldMapper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class ParseDi {

    private ArrayList<BeanMapper> beanMappers;

    private ArrayList<FieldMapper> fieldMappers;

    public ArrayList<BeanMapper> getBeanMappers(){
        return this.beanMappers;
    }

    public ArrayList<FieldMapper> getFieldMapperss(){
        return this.fieldMappers;
    }
    public ParseDi(String configFile){
        parse(configFile);
    }

    private  void parse(String configFile){
        try {

            ArrayList<BeanMapper> beanMappers = new ArrayList<>();


            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {


                BeanMapper beanMapper;
                ArrayList<FieldMapper> fieldMappers = new ArrayList<>();
                FieldMapper fieldMapper;

                @Override
                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    if(qName.equalsIgnoreCase("bean")){
                        beanMapper = new BeanMapper();
                        beanMapper.setId(attributes.getValue("id"));
                        beanMapper.setClassPath(attributes.getValue("class"));


                    }
                    if(qName.equalsIgnoreCase("field")){
                        fieldMapper = new FieldMapper();
                        fieldMapper.setName(attributes.getValue("name"));
                        fieldMapper.setBeanRef(attributes.getValue("bean-ref"));
                    }


                }

                @Override
                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    if(qName.equalsIgnoreCase("field")){
                        fieldMappers.add(fieldMapper);

                    }
                    if(qName.equalsIgnoreCase("bean")){
                        beanMapper.setFieldMappers(fieldMappers);
                        fieldMappers = new ArrayList<FieldMapper>();
                        beanMappers.add(beanMapper);
                    }

                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {


                }

            };



            saxParser.parse(configFile, handler);
            this.beanMappers = beanMappers;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
