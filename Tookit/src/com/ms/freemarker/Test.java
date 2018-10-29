package com.ms.freemarker;

import java.util.HashMap;  
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//DocUtil du = new DocUtil();
		
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put("title_name", "用户信息");
		//dataMap.put("user_name", "张三");  
		//dataMap.put("org_name", "微软公司");  
		//dataMap.put("dept_name", "事业部");
		
		/*
		List<String> Strs=new ArrayList<String>();
		Strs.add("1111111111111111111");
		Strs.add("222222222222222");
		Strs.add("333333333333333");
		dataMap.put("firstDeductItem", Strs);
		 */
		
		//du.createDoc(dataMap, "svcSpec.ftl", "C:/test1.doc");
		
		SvcUtil svc = new SvcUtil();
		svc.getSvcInfo();
	}

}
