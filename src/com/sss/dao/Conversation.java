package com.sss.dao;

import com.sss.Mapper.ClassMapper;
import com.sss.Mapper.PropertyMapper;
import com.sss.bean.UserBean;
import com.sss.config.ParseOR;
import com.sss.utility.BeanUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Conversation {
    //读取配置文件
    private static ParseOR parseOR = new ParseOR("D:\\project\\J2ee\\untitled\\src\\or_mapping.xml");
    //获得类映射
    private static ArrayList<ClassMapper> classMappers = parseOR.getClassMappers();
    //驱动配置
    private static HashMap<String,String> jdbc = parseOR.getJdbc();
    private static Connection connection;

    private static void openDBConnection(){
        try{
            Class.forName(jdbc.get("driver_class")).newInstance();
            connection = DriverManager.getConnection(jdbc.get("url_path"),jdbc.get("db_username"),jdbc.get("db_password"));

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private static boolean closeDBConnection(){
        try{
            if(connection.isClosed()){
                return true;
            }else {
                connection.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    //获得对象
    public static Object getObjectByName(Object object){

        try{
            openDBConnection();
            Class aclass = object.getClass();
            String name = (String) BeanUtility.getBean(object,"userName");
            String sql = "select * from user where name = '"+name+"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return new UserBean(resultSet.getString("name"),resultSet.getString("password"));


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            closeDBConnection();
        }

    }

    public static Object getObject(Object object){

        try{
            openDBConnection();
            Class aclass = object.getClass();
            for (ClassMapper classMapper : classMappers
            ) {
                if(aclass.getTypeName().equalsIgnoreCase(classMapper.getName())){
                    Object bean = aclass.newInstance();
                    String table = classMapper.getTable();
                    Integer id = (Integer) BeanUtility.getBean(object,classMapper.getId());
                    //此时用java自身的自省机制，对获得bean对象的id
                    BeanUtility.injectBean(bean,classMapper.getId(),id);
                    Statement statement = connection.createStatement();
                    String sql = "select * from " + table + " where id = " + id;
                    ResultSet resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    //获得所有的属性映射
                    ArrayList<PropertyMapper> propertyMappers = classMapper.getPropertyMappers();
                    for (PropertyMapper property : propertyMappers
                    ) {
                        //若lazy属性为true则不加载
                        if(property.getLazy().equalsIgnoreCase("false")){
                            BeanUtility.injectBean(bean,property.getName(),resultSet.getString(property.getColumn()));
                        }

                    }

                    return bean;

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            closeDBConnection();
        }
        return null;
    }

    public static Object getObjectIngore(Object object){

        try{
            openDBConnection();
            Class aclass = object.getClass();
            for (ClassMapper classMapper : classMappers
            ) {
                if(aclass.getTypeName().equalsIgnoreCase(classMapper.getName())){
                    Object bean = aclass.newInstance();
                    String table = classMapper.getTable();
                    Integer id = (Integer) BeanUtility.getBean(object,classMapper.getId());
                    BeanUtility.injectBean(bean,classMapper.getId(),id);
                    Statement statement = connection.createStatement();
                    String sql = "select * from " + table + " where id = " + id;
                    ResultSet resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    ArrayList<PropertyMapper> propertyMappers = classMapper.getPropertyMappers();
                    for (PropertyMapper property : propertyMappers
                    ) {

                        BeanUtility.injectBean(bean,property.getName(),resultSet.getString(property.getColumn()));

                    }

                    return bean;

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            closeDBConnection();
        }
        return null;
    }

    //删除对象
    public static boolean deleteObject(Object object){
        try{
            openDBConnection();
            Class aClass = object.getClass();

            for (ClassMapper classMapper : classMappers
                 ) {
                if(aClass.getTypeName().equalsIgnoreCase(classMapper.getName())){
                    Integer id  = (Integer) BeanUtility.getBean(object,classMapper.getId());
                    String sql = "delete from " + classMapper.getTable() + " where id = " + id;
                    Statement statement = connection.createStatement();
                    return statement.execute(sql);

                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            closeDBConnection();
        }

        return false;
    }

    //增加对象
    public static boolean insertObject(Object object){
        try{
            openDBConnection();
            System.out.println(object);
            Class aClass = object.getClass();

            for (ClassMapper classMapper : classMappers
            ) {
                if(aClass.getTypeName().equalsIgnoreCase(classMapper.getName())){
                    Integer id  = (Integer) BeanUtility.getBean(object,classMapper.getId());
                    StringBuilder key = new StringBuilder();
                    key.append("(");
                    StringBuilder value = new StringBuilder();
                    value.append("(");
                    for (PropertyMapper property : classMapper.getPropertyMappers()
                         ) {
                        key.append(property.getColumn());
                        key.append(",");
                        //若为String属性，要加上单引号
                        if(property.getType().equalsIgnoreCase("String")){
                            value.append("'");
                            value.append(BeanUtility.getBean(object,property.getName()));
                            value.append("',");
                        }else {
                            value.append(BeanUtility.getBean(object,property.getName()));
                            value.append(",");
                        }

                    }
                    key.deleteCharAt(key.length()-1);
                    key.append(")");
                    value.deleteCharAt(value.length()-1);
                    value.append(")");
                    String sql = "insert into " + classMapper.getTable() + " " + key.toString() +" values " + value.toString();
                    System.out.println(sql);
                    Statement statement = connection.createStatement();
                    return statement.execute(sql);

                }
            }
        }catch (Exception e){

            e.printStackTrace();
            return false;

        }finally {
            closeDBConnection();
        }
        return false;
    }

    //修改对象
    public static boolean updateObject(Object object){
        try{
            openDBConnection();
            Class aClass = object.getClass();

            for (ClassMapper classMapper : classMappers
            ) {
                if(aClass.getTypeName().equalsIgnoreCase(classMapper.getName())){
                    Integer id  = (Integer) BeanUtility.getBean(object,classMapper.getId());
                    StringBuilder update = new StringBuilder();
                    for (PropertyMapper property : classMapper.getPropertyMappers()
                    ) {

                        update.append(property.getColumn());

                        if(property.getType().equalsIgnoreCase("String")){
                            update.append("='");
                            update.append(BeanUtility.getBean(object,property.getName()));
                            update.append("',");
                        }else {
                            update.append("=");
                            update.append(BeanUtility.getBean(object,property.getName()));
                            update.append(",");
                        }

                    }
                    update.deleteCharAt(update.length()-1);

                    String sql = "update " + classMapper.getTable() + " set " + update.toString() +" where id = " + BeanUtility.getBean(object,classMapper.getId());
                    Statement statement = connection.createStatement();
                    return statement.execute(sql);

                }
            }
        }catch (Exception e){

            e.printStackTrace();
            return false;

        }finally {
            closeDBConnection();
        }
        return false;
    }

}
