package com._51cto.tomcat;

import com._51cto.servlet.HttpServlet;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/*
    实现Runnable,获取客户端传递过来的socket
    首先解析请求，设置响应头，根据请求路径获取对应Servlet
 */
public class MyHandler implements Runnable{
    private Socket socket;
    private PrintWriter pw;

    public MyHandler(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {
        //设置响应头
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println();

            /*
            解析请求
            */
            MyRequest request = new MyRequest();
            MyResponse response = new MyResponse(pw);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            while (true){
                String msg = br.readLine();//GET /index?method=test1&test=1 HTTP/1.1
                if(msg == null || "".equals(msg.trim())){
                    break;
                }

                String[] msgs = msg.split(" ");
                if(msgs.length == 3 && msgs[2].equalsIgnoreCase("HTTP/1.1")){
                    //请求方式
                    request.setMethod(msgs[0]);

                    String[] pathAndParams = msgs[1].split("\\?");
                    //设置请求路径
                    request.setPath(pathAndParams[0]);
                    HashMap<String,Object> requestParams = new HashMap<>();
                    if(pathAndParams.length == 2){
                        String[] params = pathAndParams[1].split("&");
                        for (String param : params){
                            String key = param.split("=")[0];
                            String value = param.split("=")[1];
                            requestParams.put(key,value);
                        }
                        //设置请求参数
                        request.setParameter(requestParams);
                    }
                }
            }

            //获取对应的HttpServlet对象
            HttpServlet httpServlet = request.getServlet();
            //System.out.println(httpServlet);
            dispatcher(httpServlet,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void dispatcher(HttpServlet httpServlet, MyRequest request, MyResponse response) {
        try{
            if(httpServlet == null){
                response.write("<h1>404 Not Found</h1>");
                return;
            }
            httpServlet.service(request,response);
        }catch (Exception e){
            response.write("<h1>500 Server Error</h1>");
        }
    }
}
