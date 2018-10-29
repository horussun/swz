package com.ms.freemarker;

import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;  
import java.io.Writer;    
import java.util.Map;

import freemarker.template.Configuration;  
import freemarker.template.Template;  
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import sun.misc.BASE64Encoder;

public class DocUtil {
	public Configuration configure=null;
	
	public DocUtil(){
		configure=new Configuration(Configuration.VERSION_2_3_25);
		configure.setDefaultEncoding("utf-8");
	}
	
	public void createDoc(Map<String,Object> dataMap,String tempFile,String saveFile){
		
		try {
			Template template=null;
			configure.setClassForTemplateLoading(this.getClass(), "/");
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			template=configure.getTemplate(tempFile);
			File outFile=new File(saveFile);
            Writer out=null;
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getImageStr(String imgFile){
        InputStream in=null;
        byte[] data=null;
        try {
            in=new FileInputStream(imgFile);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(data);
    }
    
}
