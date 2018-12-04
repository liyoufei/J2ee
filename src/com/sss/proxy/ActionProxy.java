package com.sss.proxy;

import com.sss.Mapper.InterceptorMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ActionProxy implements InvocationHandler {

    //被拦截的action
    private Object action;

    private InterceptorMapper interceptorMapper;

    //拦截器类
    private Class interceptor;
    private Object aInterceptor;

    public ActionProxy(Object action, InterceptorMapper interceptorMapper) {
        this.interceptorMapper = interceptorMapper;
        this.action = action;
        setInterceptor();
    }

    private void setInterceptor(){
        try{
            this.interceptor = Class.forName(this.interceptorMapper.getClassPath());
            this.aInterceptor = interceptor.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void preDo(String actionName){
        try{
            Method preDo = interceptor.getMethod(interceptorMapper.getPreDo(),String.class);
            preDo.invoke(aInterceptor,actionName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void afterDo(String result){
        try{
            Method afterDo  = interceptor.getMethod(interceptorMapper.getAfterDo(),String.class);
            afterDo.invoke(aInterceptor,result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //获取拦截器拦截的方action名
        preDo(action.getClass().getTypeName());
        //每个action方法的执行
        Object result = method.invoke(action,args);
        //将resul值回传至拦截器中记录
        afterDo((String) result);

        return result;
    }
}
