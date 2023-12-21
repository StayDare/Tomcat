package com._51cto.servlet;

import com._51cto.util.XMLUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    解析web.xml初始化
 */
public class MyServletContainer {
    private static Map<String,Object> servletMaps = new HashMap<>();
    private static Map<String,Object> servletMappingMaps = new HashMap<>();
    //path为键，HttpServlet为值
    private static Map<String,HttpServlet> servletContainers = new HashMap<>();
    static {
        try {
            Map<String, Map<String, Object>> maps = XMLUtils.parseWebXML();
            if(maps != null){
                servletMaps = maps.get("servletMaps");
                servletMappingMaps = maps.get("servletMappingMaps");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpServlet getHttpServlet(String path) {
        if(path == null || "".equals(path.trim()) || "/".equals(path.trim())){
            path = "/index";
        }

        //如果servletContainers里有path对应的HttpServlet对象
        if(servletContainers.containsKey(path)){
            return servletContainers.get(path);
        }

        //判断url-pattern为/index是否存在
        if(!servletMappingMaps.containsKey(path)){
            return null;
        }

        MyServletMapping servletMapping = (MyServletMapping) servletMappingMaps.get(path);
        String name = servletMapping.getName();

        if(!servletMaps.containsKey(name)){
            return null;
        }
        MyServlet servlet = (MyServlet) servletMaps.get(name);
        String clazz = servlet.getClazz();
        System.out.println(clazz);

        if(clazz == null || "".equals(clazz.trim())){
            return null;
        }

        HttpServlet httpServlet = null;
        try {
            httpServlet = (HttpServlet) Class.forName(clazz).getConstructor().newInstance();
            servletContainers.put(path,httpServlet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpServlet;
    }
}
