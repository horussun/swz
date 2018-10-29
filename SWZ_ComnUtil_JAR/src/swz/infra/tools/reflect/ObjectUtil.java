package swz.infra.tools.reflect;

import java.lang.reflect.Method;

public class ObjectUtil {
	
	public static Object getInstance(String className) throws Exception {

		Class<Object> clazz = null;
		Object returnInst = null;
		String expCode = null;
		String expMsg = null;

		try {

			// Initiate a class with the given class name.
			clazz = (Class<Object>) Class.forName(className);
			returnInst = clazz.newInstance();

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return returnInst;

	}
	
	public static Object invokeMethod(Object obj, Method method, Object[] args,
			boolean retry) throws Exception {

		Object returnValue = null;
 

		try {

			returnValue = method.invoke(obj, args);

			// The most possible reason for exception would be not matched
			// argument.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
