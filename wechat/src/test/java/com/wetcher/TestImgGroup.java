package com.wetcher;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
 
 
public class TestImgGroup{
    /**
     * 将图片进行合成
     * @param bigPath 主图图片路径
     * @param smallPath 商品图片路径
     * @param erweimaPath 二维码图片路径
     */
    public static final void overlapImage(String bigPath, String smallPath,String erweimaPath) {
        try {
          
            
//          URL url = new URL("http://test-www.xczhihui.com"); 
//          BufferedImage small = ImageIO.read(url.openStream());   
            
          BufferedImage big = ImageIO.read(new File(bigPath));
          BufferedImage small = ImageIO.read(new File(smallPath));
          BufferedImage erweima = ImageIO.read(new File(erweimaPath));
          
          int width=2015; 
          int height=1136;
          Image image=big.getScaledInstance(width, height, Image.SCALE_SMOOTH);
          
          BufferedImage bufferedImage2=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
          Graphics2D g = bufferedImage2.createGraphics();
          int x = 707;
          int y = 268;
          
          int x1 = 684;
          int y1 = 245;
          g.drawImage(image, 0, 0,null);
          g.drawImage(small, x+320, y-5, 800, 600, null);
          g.drawImage(erweima, x1-575, y1+100, 596, 596, null);
          
          Font font=new Font("宋体",Font.PLAIN , 40);
          g.setFont(font);
          g.setPaint(Color.DARK_GRAY);
          int numWidth =x+320;
          int numHright=y+650;
          int num=0;
          g.drawString("商品名称:" , numWidth,numHright);  
          num += 50;  
          g.setPaint(Color.DARK_GRAY);
          Font font1=new Font("宋体",Font.BOLD , 40);
          g.setFont(font1);
          g.drawString("售价:", numWidth,numHright+num); 
          num += 50; 
          Font font2=new Font("宋体",Font.PLAIN , 40);
          g.setFont(font2);
          g.setPaint(Color.DARK_GRAY);
          g.drawString("原产地:", numWidth, numHright+num); 
          num += 50;  
          g.drawString("配送方式:",numWidth, numHright+num); 
          g.dispose();
          ImageIO.write(bufferedImage2, "jpg", new File("C:\\\\Users\\\\yangxuan\\\\Desktop\\\\group_img\\hehe.jpg"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      public static final void main(String[] args) {
          
         overlapImage("C:\\Users\\yangxuan\\Desktop\\group_img\\red_big.jpg", 
                 "C:\\Users\\yangxuan\\Desktop\\group_img\\small.jpg",
                 "C:\\Users\\yangxuan\\Desktop\\group_img\\erweima.jpg");
      }
 
 
}