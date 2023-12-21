package com._51cto.servlet;

import com._51cto.tomcat.MyRequest;
import com._51cto.tomcat.MyResponse;

public abstract class HttpServlet {
    public void doGet(MyRequest request, MyResponse response){}
    public void doPost(MyRequest request, MyResponse response){}
    public void service(MyRequest request, MyResponse response){
        if(request.getMethod().equalsIgnoreCase("GET")){
            doGet(request,response);
        }
        if(request.getMethod().equalsIgnoreCase("POST")){
            doPost(request,response);
        }
    }
}
