package com._51cto.servlet;
/*
    对web.xml解析后里面<servlet-mapping>中的信息，与这个类的对象映射
 */
public class MyServletMapping {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
