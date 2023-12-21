package com._51cto.tomcat;

import com._51cto.servlet.HttpServlet;
import com._51cto.servlet.MyServletContainer;

import java.util.Map;

/*
    请求
 */
public class MyRequest {
    private String method;//请求方式
    private String path;//请求路径
    private Map<String,Object> parameter;//请求参数

    //获取HttpServlet对象
    public HttpServlet getServlet(){
        //根据路径从MyServletContainer里获取HttpServlet对象
        return MyServletContainer.getHttpServlet(path);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getParameter() {
        return parameter;
    }

    public void setParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
    }
}
