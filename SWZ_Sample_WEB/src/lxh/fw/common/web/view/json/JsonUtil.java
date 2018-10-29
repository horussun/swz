/*
 * Class: JsonUtil
 * Description:
 * Version: 1.0
 * Author: Jack
 * Creation date: 2012-4-28
 * (C) Copyright IBM Corporation 2011. All rights reserved.
 */
package lxh.fw.common.web.view.json;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;

import com.ibm.json.java.JSONObject;

/**
 * 工具类，用于讲普通java对象转化为json对象
 */
public class JsonUtil {
    public static JSONObject beanToJson(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = objectToJson(props[i].getName());
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        try {
            return JSONObject.parse(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String objectToJson(Object object) {
        StringBuilder json = new StringBuilder();
        if (object == null) {
            json.append("\"\"");
        } else {
            json.append("\"").append(object.toString()).append("\"");
        } 
//        else {
//            json.append(beanToJson(object));
//        }
        return json.toString();
    }
    
    public static void main (String[] arg){
        
    }
}
