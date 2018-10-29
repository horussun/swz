package swz.sample.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

public class Dom4jTest {
	/*
	 * <?xml version="1.0" encoding="UTF-8"?>  
	   <beans xmlns="http://www.springframework.org/schema/beans"  
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
          xmlns:aop="http://www.springframework.org/schema/aop"  
          xmlns:tx="http://www.springframework.org/schema/tx"  
          xsi:schemaLocation="  
          http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd  
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd  
          http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("beans","http://www.springframework.org/schema/beans");
		
		root.addAttribute("\nxmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");  
        root.addAttribute("\nxmlns:aop", "http://www.springframework.org/schema/aop");  
        root.addAttribute("\nxmlns:tx", "http://www.springframework.org/schema/tx");  
        root.addAttribute("\nxsi:schemaLocation", "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd");
        
        //Element beans = document.addElement("beans"); 
        //beans.addNamespace(args1,args2);
        
        //QName qname = DocumentFactory.getInstance().createQName("schemaLocation", "xsi", "http://www.w3.org/2001/XMLSchema-instance"); 
        //rootElement.addAttribute(qname, "http://schemas.accounting.org.cn/2004/datainterface/gssm gssm.xsd");
        //root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")); 
	}

}
