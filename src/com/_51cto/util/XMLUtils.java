package com._51cto.util;

import com._51cto.servlet.MyServlet;
import com._51cto.servlet.MyServletMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
    工具类解析web.xml
 */
public class XMLUtils {
    //定义一个方法来解析
    public static Map<String, Map<String,Object>> parseWebXML() throws ParserConfigurationException, IOException, SAXException {
        Map<String, Map<String,Object>> result = new HashMap<>();
        /*
            "ServletMaps" --- HashMap
                                name,MyServlet
            "ServletMappingMaps" --- HashMap
                                url,MyServletMapping
         */

        //创建解析工厂
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //获取解析器
        DocumentBuilder db = dbf.newDocumentBuilder();
        //读取src下web.xml文件
        InputStream is = com.sun.javaws.jnl.XMLUtils.class.getClassLoader().getResourceAsStream("web.xml");
        //解析输入流，生成Document对象
        Document document = db.parse(is);
        //获取根节点
        Element root = document.getDocumentElement();
        //获取根节点所有子节点
        NodeList xmlNodes = root.getChildNodes();
        //遍历
        for (int i = 0; i < xmlNodes.getLength(); i++) {
            Node config = xmlNodes.item(i);

            if(config != null && config.getNodeType() == Node.ELEMENT_NODE){
                //获取标签名
                String nodeName = config.getNodeName();
                /*
                先解析servlet节点
                */
                if("servlet".equals(nodeName)){
                    Map<String,Object> servletMaps = null;
                    if(!result.containsKey("servletMaps")){
                        result.put("servletMaps",new HashMap<>());
                    }
                    servletMaps = result.get("servletMaps");

                    //获取servlet标签下所有子节点
                    NodeList childNodes = config.getChildNodes();

                    MyServlet myServlet = new MyServlet();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);

                        if(item != null && item.getNodeType() == Node.ELEMENT_NODE){
                            String nodeName1 = item.getNodeName();
                            String nodeValue = item.getTextContent();
                            if(nodeName1.equals("servlet-name")){
                                myServlet.setName(nodeValue);
                            }else if(nodeName1.equals("servlet-class")){
                                myServlet.setClazz(nodeValue);
                            }
                        }
                    }

                    servletMaps.put(myServlet.getName(),myServlet);
                }

                /*
                    解析servlet-mapping
                 */
                if("servlet-mapping".equals(nodeName)){
                    Map<String,Object> servletMappingMaps = null;
                    if(!result.containsKey("servletMappingMaps")){
                        result.put("servletMappingMaps",new HashMap<>());
                    }
                    servletMappingMaps = result.get("servletMappingMaps");
                    //获取servlet标签下所有子节点
                    NodeList childNodes = config.getChildNodes();

                    MyServletMapping myServletMapping = new MyServletMapping();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);

                        if(item != null && item.getNodeType() == Node.ELEMENT_NODE){
                            String nodeName1 = item.getNodeName();
                            String nodeValue = item.getTextContent();
                            if(nodeName1.equals("servlet-name")){
                                myServletMapping.setName(nodeValue);
                            }else if(nodeName1.equals("url-pattern")){
                                myServletMapping.setUrl(nodeValue);
                            }
                        }
                    }

                    servletMappingMaps.put(myServletMapping.getUrl(),myServletMapping);
                }
            }
        }

        return result;
    }
}
