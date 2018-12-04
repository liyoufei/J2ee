package com.sss.utility;

import jdk.nashorn.internal.objects.annotations.Property;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtility {

    /**
     * 对bean对象的属性设置
     * @param object 目标bean对象
     * @param property 目标的属性
     * @param value 要设置的值
     */
    public static void injectBean(Object object,String property,Object value){

        try{
            PropertyDescriptor propertyDescriptor =  new PropertyDescriptor(property,object.getClass());
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(object,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 对bean对象的属性值获取
     * @param object 目标Bean对象
     * @param property 属性名称
     * @return 获取到的属性值
     */
    public static Object getBean(Object object,String property){

        try{
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property,object.getClass());
            Method method = propertyDescriptor.getReadMethod();
            return method.invoke(object);
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }
}
