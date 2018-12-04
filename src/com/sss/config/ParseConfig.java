package com.sss.config;

import com.sss.Mapper.ActionMapper;
import com.sss.Mapper.InterceptorMapper;
import com.sss.Mapper.ResultMapper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class ParseConfig {
    private ArrayList<ActionMapper> actionMappers;

    private ArrayList<InterceptorMapper> interceptorMappers;

    public ArrayList<ActionMapper> getActionMappers(){
        return this.actionMappers;
    }

    public ArrayList<InterceptorMapper> getInterceptorMappers(){
        return this.interceptorMappers;
    }
    public ParseConfig(String configFile){
        parse(configFile);
    }

    private  void parse(String configFile){
        try {
            ArrayList<ActionMapper> actionMappers = new ArrayList<>();
            ArrayList<InterceptorMapper> interceptorMappers = new ArrayList<>();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {


                ActionMapper actionMapper;
                InterceptorMapper interceptorMapper;
                ArrayList<ResultMapper> results;

                @Override
                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    if(qName.equalsIgnoreCase("interceptor")){
                        interceptorMapper = new InterceptorMapper();
                        interceptorMapper.setName(attributes.getValue("name"));
                        interceptorMapper.setClassPath(attributes.getValue("class"));
                        interceptorMapper.setPreDo(attributes.getValue("preDo"));
                        interceptorMapper.setAfterDo(attributes.getValue("afterDo"));
                    }

                    if(qName.equalsIgnoreCase("controller")){

                    }

                    if(qName.equalsIgnoreCase("action")){
                        results = new ArrayList<>();
                        actionMapper = new ActionMapper();
                        actionMapper.setName(attributes.getValue("name"));
                        actionMapper.setClassPath(attributes.getValue("class"));
                        actionMapper.setMethod(attributes.getValue("method"));

                    }

                    if(qName.equalsIgnoreCase("interceptor-ref")){
                        actionMapper.setIntercepttorRef(attributes.getValue("name"));
                    }

                    if(qName.equalsIgnoreCase("result")){
                        ResultMapper resultMapper = new ResultMapper();
                        resultMapper.setResultName(attributes.getValue("name"));
                        resultMapper.setType(attributes.getValue("type"));
                        resultMapper.setValue(attributes.getValue("value"));
                        results.add(resultMapper);
                    }


                }

                @Override
                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    if(qName.equalsIgnoreCase("action")){
                        //把所有的result集添加进action中，并把现有的action添加至list中
                        actionMapper.setResultMappers(results);
                        actionMappers.add(actionMapper);
                    }

                    if(qName.equalsIgnoreCase("interceptor")){
                        interceptorMappers.add(interceptorMapper);
                    }

                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {


                }

            };

            saxParser.parse(configFile, handler);

            this.actionMappers = actionMappers;
            this.interceptorMappers = interceptorMappers;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

