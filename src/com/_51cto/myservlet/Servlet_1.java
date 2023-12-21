package com._51cto.myservlet;

import com._51cto.servlet.HttpServlet;
import com._51cto.tomcat.BaseServlet;
import com._51cto.tomcat.MyRequest;
import com._51cto.tomcat.MyResponse;

public class Servlet_1 extends BaseServlet {
    public String test1(MyRequest request , MyResponse response){
        return "test1.html";
    }
}
