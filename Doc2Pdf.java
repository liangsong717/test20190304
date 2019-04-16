package com.fineway.specialReport.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class Doc2Pdf {
	public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void doc2pdf(String fromPath,String toPath) {    
    	
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        String system_os = System.getProperty("os.name");  
        System.out.println("system_os:"+system_os);
        if(system_os.toLowerCase().startsWith("win")){  
           if(fromPath.startsWith("/")) {
        	   fromPath=fromPath.substring(1);
           }
           if(toPath.startsWith("/")) {
        	   toPath=toPath.substring(1);
           }
        } 
        System.out.println("from:"+fromPath+"\nto:"+toPath);
        try {
            long old = System.currentTimeMillis();
            File file = new File(toPath);  //新建一个空白pdf文档
            if(file.exists()) {file.delete();}
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(fromPath);                    //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            os.close();
            System.out.println("【doc to pdf】共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}