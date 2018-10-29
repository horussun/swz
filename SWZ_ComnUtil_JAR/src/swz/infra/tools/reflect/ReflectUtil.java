package swz.infra.tools.reflect;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class ReflectUtil {

	private static final Logger logger = Logger.getLogger(ReflectUtil.class);

	/**
	 * @param obj
	 * @param method
	 * @param arg
	 * @return Object
	 * @throws GeneralException
	 */
	public static Object invokeMethodByMethodAndArgs(Object obj,
			String methodName, Object[] mgrParamValues,
			Class<Object>[] mgrParamTypes) {
		Object returnValue = null;
		String expMsg = null;
		Method method = null;

		try {
			method = obj.getClass().getMethod(methodName, mgrParamTypes);

			if (method != null) {
				returnValue = ObjectUtil.invokeMethod(obj, method,
						mgrParamValues, true);

			} else {
				String clazzName = obj.getClass().getName();

				logger.error(expMsg);
				throw new Exception(
						"Error in ReflectUtil.invokeMethodByMethodAndArgs for method : "
								+ clazzName + "." + methodName);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return returnValue;

	}

	public static Object invokeSetterMethodByAttributeAndArgs(Object obj,
			String attributeName, Object[] mgrParamValues,
			Class<Object>[] mgrParamTypes) {
		Object returnValue = null;
		String expMsg = null;
		Method method = null;
		
		String firstLetterOfAttributeName = attributeName.substring(0, 1);
		
		String methodName = "set" + firstLetterOfAttributeName.toUpperCase() + attributeName.substring(1, attributeName.length());
		
		try {
			method = obj.getClass().getMethod(methodName, mgrParamTypes);

			if (method != null) {
				returnValue = ObjectUtil.invokeMethod(obj, method,
						mgrParamValues, true);

			} else {
				String clazzName = obj.getClass().getName();

				logger.error(expMsg);
				throw new Exception(
						"Error in ReflectUtil.invokeMethodByMethodAndArgs for method : "
								+ clazzName + "." + methodName);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return returnValue;

	}
	
	public static boolean hasMethodByAttributeAndArgs(Object obj,
			String attributeName, Object[] mgrParamValues,
			Class<Object>[] mgrParamTypes) {
		boolean hasMethod = false;
		String expMsg = null;
		Method method = null;
		
		String firstLetterOfAttributeName = attributeName.substring(0, 1);
		
		String methodName = "get" + firstLetterOfAttributeName.toUpperCase() + attributeName.substring(1, attributeName.length());
		try {
			method = obj.getClass().getMethod(methodName, mgrParamTypes);
	
			if (method != null) {
				hasMethod =  true;
			} else {
				hasMethod = false;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return hasMethod;
	}
	
	public static Object invokeGetterMethodByAttributeAndArgs(Object obj,
			String attributeName, Object[] mgrParamValues,
			Class<Object>[] mgrParamTypes) {
		Object returnValue = null;
		String expMsg = null;
		Method method = null;
		
		String firstLetterOfAttributeName = attributeName.substring(0, 1);
		
		String methodName = "get" + firstLetterOfAttributeName.toUpperCase() + attributeName.substring(1, attributeName.length());
		
		try {
			method = obj.getClass().getMethod(methodName, mgrParamTypes);

			if (method != null) {
				returnValue = ObjectUtil.invokeMethod(obj, method,
						mgrParamValues, true);

			} else {
				String clazzName = obj.getClass().getName();

				logger.error(expMsg);
				throw new Exception(
						"Error in ReflectUtil.invokeMethodByMethodAndArgs for method : "
								+ clazzName + "." + methodName);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return returnValue;

	}
	


}
