package com._51cto.servlet;
/*
    对web.xml解析后里面<servlet>中的信息，与这个类的对象映射
 */
public class MyServlet {
    private String name;
    private String clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
