package com._51cto.tomcat;

import com._51cto.servlet.HttpServlet;

import java.lang.reflect.Method;
import java.util.Map;

public class BaseServlet extends HttpServlet {
    @Override
    public void service(MyRequest request, MyResponse response) {
        Map<String, Object> parameter = request.getParameter();
        String methodName = (String) parameter.get("method");
        if(methodName == null){
            methodName = "index";
        }

        Class clazz = this.getClass();
        try {
            Method method = clazz.getMethod(methodName, request.getClass(), response.getClass());

            String dest = (String) method.invoke(this, request, response);
            System.out.println(dest);
            if(dest != null){
                response.forward(dest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
