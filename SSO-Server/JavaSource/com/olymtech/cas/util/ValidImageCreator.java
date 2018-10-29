package com.olymtech.cas.util;


/*
 * 创建日期 2005-7-19
 * Company: Java天下
 * Author: java_zhangyu
 */

/**
 * 
Java天下核心技术研发部


 * @author zy
 *
 * 创建图形验证码 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import java.awt.Graphics;
public class ValidImageCreator {
    public String sRand="";
    public ValidImageCreator(){
        
    }
    public Color getRandColor(int fc,int bc){//给定范围获得随机颜色
       Random random = new Random();
       if(fc>255) fc=255;
       if(bc>255) bc=255;
       int r=fc+random.nextInt(bc-fc);
       int g=fc+random.nextInt(bc-fc);
       int b=fc+random.nextInt(bc-fc);
       return new Color(r,g,b);
     }
    /**
     * 通过文件创建图像
     * 格式为jpg类型
     * */
     public void creatImage(String fileName,String content){
         //在内存中创建图象
       int width=60, height=20;
       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       // 获取图形上下文
       Graphics2D g = image.createGraphics();
       //Graphics g=image.getGraphics();
       //生成随机类
       Random random = new Random();
       // 设定背景色
       g.setColor(getRandColor(200,250));
       g.fillRect(0, 0, width, height);
       //设定字体
       g.setFont(new Font("Times New Roman",Font.PLAIN,18));
       g.setColor(Color.black);//黑色文字
       g.drawString(content,10,15);
       g.dispose();
       try{
        File f=new File(fileName);
        if(!f.exists()){
            f.createNewFile();
        }else{
            Thread.sleep(200);
            f.delete();
            f.createNewFile();
        }
        ImageIO.write(image, "jpg", f);
       }catch(Exception e){
           e.printStackTrace();
       }
     }
     /**
      * 创建图像
      * 格式为jpg类型
      * @param content - String 图片输出内容
      * @return java.awt.image.BufferedImage
      * @since 2005-7-19
      * */
     public BufferedImage getBufferedImage(String content){
      int width=60, height=20;
      return getBufferedImage(content,width,height);
     }
     /**
      * 创建图像
      * 格式为jpg类型
      * @param content - String 图片输出内容
      * @param width - int 图片宽度
      * @param height - int 图片高度
      * @return java.awt.image.BufferedImage
      * @since 2005-7-19
      * */
      public BufferedImage getBufferedImage(String content,int width,int height){
          //在内存中创建图象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g = image.createGraphics();
        //Graphics g=image.getGraphics();
        //生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman",Font.PLAIN,20));
        g.setColor(Color.black);//黑色文字
        g.drawString(content,10,15);
        for (int i = 0; i < 15; i++) {
     	   int x = random.nextInt(width);
     	   int y = random.nextInt(height);
     	   int xl = random.nextInt(12);
     	   int yl = random.nextInt(12);
     	   g.setColor(getRandColor(50,250)); 
     	   g.drawLine(x, y, x + xl, y + yl);
       }
        g.dispose();
        return image;
      }
      /**
       * 将现有BufferedImage融合进Response
       * @param response - javax.servlet.http.ServletResponse 将使用的response对象
       * @param img - java.awt.image.BufferedImage
       * @since 2005-7-19
       * */
      public void response(HttpServletResponse response,BufferedImage img){
       try{
        response.setContentType("image/jpg;charset=GB2312");//设定输出的类型
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
         encoder.encode(img);//对图片进行输出编码
       }catch(Exception e){
        e.printStackTrace();
       }
      }
     /**
      * 返回一个4位的验证码
      * */
     public String getContent() throws InterruptedException{
         String content="";
         for(int i=0;i<4;i++){
             content+=getChar();
             Thread.sleep(new Random().nextInt(10)+10);//休眠以控制字符的重复问题
         }
         return content;
     }

     /**
      * 获取随机字符
      * */
     public char getChar(){
         Random random=new Random();
         char ch='0';
         LinkedList ls=new LinkedList();
         for(int i=2;i<10;i++){//0-9
        	 ls.add(String.valueOf(48+i));
         }
         for(int i=0;i<26;i++){//A-Z
        	 if(i==11 || i==14){
        		 continue;
        	 }
             ls.add(String.valueOf(65+i));
         }
         for(int i=0;i<26;i++){//a-z
        	 if(i==11 || i==14){
        		 continue;
        	 }
             ls.add(String.valueOf(97+i));
         }
         int index=random.nextInt(ls.size());
         if(index>(ls.size()-1)){
             index=ls.size()-1;
         }
         ch=(char)Integer.parseInt(String.valueOf(ls.get(index)));
         return ch;
     }
     /**
       测试
     */
     public static void main(String []args) throws Exception{
    	 ValidImageCreator c=new ValidImageCreator();
         c.creatImage("me.jpg",c.getContent());
     }
}