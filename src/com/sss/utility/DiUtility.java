package com.sss.utility;

import com.sss.Mapper.BeanMapper;
import com.sss.Mapper.FieldMapper;

import java.util.ArrayList;

public class DiUtility {

    /**
     * 对于可能出现的嵌套，多种依赖进行处理
     * @param object 目标对象
     * @param beanMappers 配置文件表现的依赖关系
     * @return 注入后的对象
     */
    public static Object handleDi(Object object, ArrayList<BeanMapper> beanMappers){
        try{
            //获取依赖的bean
            ArrayList<FieldMapper> fieldMappers = new ArrayList<>();
            for (BeanMapper beanMapper : beanMappers){

                //找到di文件中的class
                if(object.getClass().getTypeName().equalsIgnoreCase(beanMapper.getClassPath())){
                    //获得依赖的bean信息
                    fieldMappers = beanMapper.getFieldMappers();

                }
            }
            if(!fieldMappers.isEmpty()){
                for (FieldMapper fieldMapper :fieldMappers
                ) {
                    for (BeanMapper beanMapper : beanMappers
                    ) {
                        if(fieldMapper.getBeanRef().equalsIgnoreCase(beanMapper.getId())){
                            String className = beanMapper.getClassPath();
                            //对字符串进行处理获取在依赖bean中的属性名
                            String property = className.substring(beanMapper.getClassPath().lastIndexOf(".")+1);
                            //递归的进行依赖注入
                            BeanUtility.injectBean(object,property,handleDi(Class.forName(className).newInstance(),beanMappers));


                        }
                    }
                }

            }
            return object;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
