package com.sss.controller;



import com.sss.Mapper.*;
import com.sss.config.ParseConfig;
import com.sss.config.ParseDi;
import com.sss.proxy.ActionProxy;
import com.sss.utility.BeanUtility;
import com.sss.utility.DiUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;


public class SimpleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        String actionName = uri.substring(uri.indexOf("/")+1,uri.indexOf("."));

        handleAction(actionName,req,resp);




    }

    private void handleAction(String actionName,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

        //解析配置文件获得相关的mapper
        ParseConfig parseBySAX = new ParseConfig("D:\\project\\J2ee\\untitled\\src\\controller.xml");
        ParseDi parseDi = new ParseDi("D:\\project\\J2ee\\untitled\\src\\di.xml");

        //全部解析得到的bean
        ArrayList<BeanMapper> beanMappers = parseDi.getBeanMappers();
        //确定要依赖的bean集合
        ArrayList<BeanMapper> di = new ArrayList<>();

        ArrayList<ActionMapper> actionMappers = parseBySAX.getActionMappers();
        ArrayList<InterceptorMapper> interceptorMappers = parseBySAX.getInterceptorMappers();

        ActionMapper actionMapper = new ActionMapper();
        InterceptorMapper interceptorMapper = new InterceptorMapper();

        //获取跟uri匹配的action以及相关的interceptor,查找有没有依赖注入的bean
        for (ActionMapper actionMapper1 : actionMappers
        ) {
            if(actionMapper1.getName().equalsIgnoreCase(actionName)){
                actionMapper = actionMapper1;

                //获取拦截器配置信息
                for (InterceptorMapper interceptorMapper1 : interceptorMappers
                ) {
                    if(interceptorMapper1.getName().equalsIgnoreCase(actionMapper.getIntercepttorRef())){
                        interceptorMapper = interceptorMapper1;
                    }
                }

                //获取依赖的bean
                ArrayList<FieldMapper> fieldMappers = new ArrayList<>();
                for (BeanMapper beanMapper : beanMappers){

                    //找到di文件中的action
                    if(actionMapper.getClassPath().equalsIgnoreCase(beanMapper.getClassPath())){
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
                                di.add(beanMapper);
                                break;
                            }
                        }
                    }

                    if (di.isEmpty()){
                        req.setAttribute("message","错误的bean依赖");
                        req.getRequestDispatcher("pages/error.jsp").forward(req,resp);
                    }
                }
                break;
            }
        }

        //不可识别的请求
        if(actionMapper.getName() == null){
            req.setAttribute("message","不可识别的请求");
            req.getRequestDispatcher("pages/error.jsp").forward(req,resp);
        }else if(interceptorMapper.getName() != null){

            //有拦截器的存在
            //动态代理实现拦截器的调用
            try{

                Class action = Class.forName(actionMapper.getClassPath());
                Object aAction = action.newInstance();

//                //登录验证依赖测试
//                if(!di.isEmpty()){
//                    if(req.getParameter("userName") != null){
//                        Class bean = Class.forName(di.get(0).getClassPath());
//                        Object aBean = bean.newInstance();
//                        BeanUtility.injectBean(aBean,"userName",req.getParameter("userName"));
//                        BeanUtility.injectBean(aBean,"userPassword",req.getParameter("userPassword"));
//                        BeanUtility.injectBean(aAction,"userBean",aBean);
//                    }
//
//                }

                aAction = DiUtility.handleDi(aAction,beanMappers);

                //action代理类生成（参数分别为装载类，代理目标类的接口，Proxy实例）
                Object actionProxy =  Proxy.newProxyInstance(action.getClassLoader(),action.getInterfaces(),new ActionProxy(aAction,interceptorMapper));

                //在request请求有参数的情况下，决定使用带参数的执行方法，将数值传入
                Method method = actionProxy.getClass().getMethod(actionMapper.getMethod());
                String resultName = (String) method.invoke(actionProxy);

                handleResult(resultName,resp,req,actionMapper);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try{
                //默认的action执行
                Class aClass = Class.forName(actionMapper.getClassPath());
                Object object = aClass.newInstance();
                Method method = aClass.getMethod(actionMapper.getMethod());
                String resultName  = String.valueOf(method.invoke(object));
                handleResult(resultName,resp,req,actionMapper);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void handleResult(String resultName,HttpServletResponse resp,HttpServletRequest req,ActionMapper actionMapper) throws IOException,ServletException{

        //根据相关的action返回值获取相对应的result处理程序
        ResultMapper resultMapper = new ResultMapper();
        ArrayList<ResultMapper> resultMappers = actionMapper.getResultMappers();
        for (ResultMapper resultMapper1 : resultMappers
        ) {

            if(resultMapper1.getResultName().equalsIgnoreCase(resultName)){
                resultMapper = resultMapper1;
            }

        }

        if(resultMapper.getResultName() == null){

            req.setAttribute("message","您请求的资源不存在");
            req.getRequestDispatcher("pages/error.jsp").forward(req,resp);
        }else{
            if(resultMapper.getType().equalsIgnoreCase("redirect")){
                resp.sendRedirect(resultMapper.getValue());
            }else if(resultMapper.getType().equalsIgnoreCase("forward")){
                req.getRequestDispatcher(resultMapper.getValue()).forward(req,resp);
            }else {
                req.setAttribute("message","您请求的页面找不到啦");
                req.getRequestDispatcher("pages/error.jsp").forward(req,resp);
            }
        }
    }


}