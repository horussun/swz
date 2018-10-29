package com.olymtech.cas.client.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtil {
    public String getIP(){
	String ip = "127.0.0.1";
	try {
	    ip = InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return ip;
    }

    // 得到HTTP协议的端口号
    public String getPort() {
	String port = "8080";
	try {
	    Class serverFactoryClass = Util.getClass("org.apache.catalina.ServerFactory");
	    Object server = Util.invokeStaticMethod(serverFactoryClass, "getServer");
	    Object servicesArray = Util.invokeMethod(server, "findServices");
	    for (int i = 0, k = Array.getLength(servicesArray); i < k; i++) {
		Object service = Array.get(servicesArray, i);
		Object connectorsArray = Util.invokeMethod(service, "findConnectors");
		for (int j = 0, m = Array.getLength(connectorsArray); j < m; j++) {
		    Object connector = Array.get(connectorsArray, j);
		    String protocol = (String) Util.invokeMethod(connector, "getProtocol");
		    if (protocol != null && protocol.toUpperCase().startsWith("HTTP")) {
			Integer p = (Integer) Util.invokeMethod(connector, "getPort");
			port = p.toString();
			return port;
		    }
		}
	    }
	} catch (Exception e) {
	    port = "8080";
	}

	return port;
    }

    // ---------------------------------------
    private static class Util {
	/**
	 * 从所有的线程类加载器中加载类
	 * 
	 * @param classname
	 * @return
	 */
	public static Class getClass(String classname) {
	    Thread[] threads = getAllThread();
	    Class clazz = null;
	    for (int i = 0; i < threads.length; i++) {
		try {
		    // 获得线程上下文的类加载器
		    ClassLoader loader = threads[i].getContextClassLoader();
		    if (loader == null) {
			continue;
		    }
		    // 加载类
		    clazz = loader.loadClass(classname);

		    // 加载成功返回
		    return clazz;
		} catch (ClassNotFoundException e) {
		}
	    }
	    return clazz;
	}

	/**
	 * 反射调用一个对象的某个无参方法
	 * 
	 * @param obj
	 * @param methodName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(Object obj, String methodName) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
		IllegalAccessException, InvocationTargetException {
	    Method method = obj.getClass().getMethod(methodName, new Class[0]);
	    return method.invoke(obj, new Object[0]);
	}

	/**
	 * 反射调用一个类的某个无参静态方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeStaticMethod(Class clazz, String methodName) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
		IllegalAccessException, InvocationTargetException {
	    Method method = clazz.getMethod(methodName, new Class[0]);
	    return method.invoke(null, new Object[0]);
	}

	/**
	 * 获得所有线程
	 * 
	 * 该方法由 SageZK 提供
	 * 
	 * @return
	 */
	private static Thread[] getAllThread() {
	    ThreadGroup root = Thread.currentThread().getThreadGroup();
	    ThreadGroup ttg = root;
	    while ((ttg = ttg.getParent()) != null) {
		root = ttg;
	    }
	    Thread[] tlist = new Thread[(int) (root.activeCount() * 1.2)];
	    // return java.util.Arrays.copyOf(tlist, root.enumerate(tlist,
	    // true));
	    return copyOf(tlist, root.enumerate(tlist, true));
	}

	private static Thread[] copyOf(Thread[] tlist, int length) {
	    Thread[] threads = new Thread[length];
	    System.arraycopy(tlist, 0, threads, 0, length);
	    return threads;
	}
    }
}
