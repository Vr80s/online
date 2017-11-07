package com.xczhihui.attachment.center.server.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZIPUtil {

	/**
	 * 拷贝文件
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void copyFile(File source,File target){
		FileInputStream in = null;
		FileOutputStream out = null;
		try{
			ByteBuffer byteBuf = ByteBuffer.allocate(1024*10);
			in = new FileInputStream(source);
			FileChannel inch = in.getChannel();
			out = new FileOutputStream(target);
			FileChannel outch = out.getChannel();
			int len=0;
			while((len=inch.read(byteBuf))!=-1){
				byteBuf.flip();
				int length = 0;
				while((length=outch.write(byteBuf))!=0){
					outch.write(byteBuf);
				}
				byteBuf.clear();
			}
			in.close();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deleteFile(File file){
		if (file.isDirectory()) {
            String[] children = file.list();
            for (int i=0; i<children.length; i++) {
               deleteFile(new File(file, children[i]));
            }
        }
		file.delete();
	}
	
	public static void compressedFile(String resourcesPath,String targetPath) throws Exception{  
        File resourcesFile = new File(resourcesPath);     //源文件  
        File targetFile = new File(targetPath);           //目的  
        //如果目的路径不存在，则新建  
        if(!targetFile.exists()){       
            targetFile.mkdirs();    
        }  
        String targetName = resourcesFile.getName()+".zip";   //目的压缩文件名  
        FileOutputStream outputStream = new FileOutputStream(targetPath+ File.separator+targetName);  
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        out.setEncoding(System.getProperty("sun.jnu.encoding"));
        createCompressedFile(out, resourcesFile, "");  
        out.close();    
    }  
      
    /** 
     * @desc 生成压缩文件。 
     *               如果是文件夹，则使用递归，进行文件遍历、压缩 
     *       如果是文件，直接压缩 
     * @param out  输出流 
     * @param file  目标文件 
     * @return void 
     * @throws Exception  
     */  
	public static void createCompressedFile(ZipOutputStream out,File file,String dir) throws Exception{  
        //如果当前的是文件夹，则进行进一步处理  
        if(file.isDirectory()){  
            //得到文件列表信息  
            File[] files = file.listFiles();  
            //将文件夹添加到下一级打包目录  
//            out.putNextEntry(new ZipEntry(dir+"/"));  
            dir = dir.length() == 0 ? "" : dir +"/";  
            //循环将文件夹中的文件打包  
            for(int i = 0 ; i < files.length ; i++){  
                createCompressedFile(out, files[i], dir + files[i].getName());         //递归处理  
            }  
        }else{   //当前的是文件，打包处理  
            //文件输入流  
            FileInputStream fis = new FileInputStream(file);  
            out.putNextEntry(new ZipEntry(dir));  
            //进行写操作  
            int j =  0;  
            byte[] buffer = new byte[1024];  
            while((j = fis.read(buffer)) > 0){  
                out.write(buffer,0,j);  
            }  
            //关闭输入流  
            fis.close();  
        }  
    }  
}
