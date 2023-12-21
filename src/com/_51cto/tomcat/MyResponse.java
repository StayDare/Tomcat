package com._51cto.tomcat;

import java.io.*;

/*
    响应
    对客户端socket输出

    write()方法 向浏览器输出信息

    forward(String dest) 根据资源信息，将其输出到浏览器
 */
public class MyResponse {
    private PrintWriter printWriter;

    public MyResponse(PrintWriter printWriter){
        this.printWriter = printWriter;
    }

    public void write(String msg){
        if(msg != null){
            printWriter.write(msg);
            printWriter.flush();
        }
    }

    public void forward(String dest){//test1.html
        try{
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(dest);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = br.readLine())!=null){
                write(line);
            }
            is.close();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
